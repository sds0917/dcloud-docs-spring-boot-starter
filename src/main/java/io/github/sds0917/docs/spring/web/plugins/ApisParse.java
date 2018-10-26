package io.github.sds0917.docs.spring.web.plugins;

import java.io.File;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaAnnotatedElement;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaType;

import io.github.sds0917.docs.modules.Api;
import io.github.sds0917.docs.modules.App;
import io.github.sds0917.docs.modules.Param;
import io.github.sds0917.docs.modules.Tag;
import io.github.sds0917.docs.modules.Url;
import io.github.sds0917.docs.spring.web.DocumentationCache;
import io.github.sds0917.docs.utils.DocsUtil;

@Component
public class ApisParse {
	private final ObjectMapper objectMapper;
	private final DocumentationCache documentationCache;
	private final JavaProjectBuilder builder;
	private static final Map<String, AnnotatedElement> CACHE_ANNOTATED_ELEMENT = new ConcurrentHashMap<String, AnnotatedElement>();

	public ApisParse(ObjectMapper objectMapper, DocumentationCache documentationCache) {
		this.builder = new JavaProjectBuilder();
		this.objectMapper = objectMapper;
		this.documentationCache = documentationCache;
	}

	public ApisParse doParse() {
		String basePackage = "io.github.sds0917.docs.controller";
		List<String> paths = Arrays.asList("src/main/java".split(","));
		builder.addClassLoader(getClass().getClassLoader());
		for (String path : paths) {
			builder.addSourceTree(new File(path));
		}
		App app = App.build();
		for (JavaClass jc : builder.getClasses()) {
			if (!jc.getPackageName().contains(basePackage)) {
				continue;
			}
			Api api = Api.build().name(jc.getName()).type(jc.getFullyQualifiedName()).comment(jc.getComment());
			api = api.mvc(doParseMvcAnnotation(jc)).tags(doParseTags(jc)).author(doGetTagValue(jc, "author")).date(doGetTagValue(jc, "date"));
			app.addApi(api.urls(doParseApis(jc)));
		}
		app = app.name("default");
		documentationCache.addDocumentation(app.getName(), app);
		return this;
	}

	private List<Url> doParseApis(JavaClass jc) {
		List<Url> urls = new ArrayList<Url>();
		for (JavaMethod jm : jc.getMethods()) {
			Method m = doParseJMethod(jm);
			Url url = Url.build().name(jm.getCallSignature()).type(m.toGenericString()).comment(jm.getComment());
			url = url.mvc(doParseMvcAnnotation(jm)).tags(doParseTags(jm)).author(doGetTagValue(jm, "author")).date(doGetTagValue(jm, "date"));
			urls.add(url.params(doParseMethodParams(jm)));
		}
		return urls;
	}

	private List<Param> doParseMethodParams(JavaMethod jm) {
		List<Param> params = new ArrayList<Param>();
		for (JavaParameter jp : jm.getParameters()) {
			JavaClass jc = jp.getJavaClass();
			String comment = jp.getComment();
			DocletTag dt = jm.getTagsByName("param").stream().filter(a -> a.getValue().startsWith(jp.getName())).findFirst().orElse(null);
			if (StringUtils.isBlank(comment) && null != dt) {
				comment = dt.getValue().replace(jp.getName(), StringUtils.EMPTY);
			}
			Class<?> c = doParseJClass(jc);
			if (ClassUtils.isPrimitiveOrWrapper(c) || c.getName().equals(String.class.getName())) {
				Param p = Param.build().name(jp.getName()).type(jp.getFullyQualifiedName()).simpType(jp.getValue()).comment(comment.trim());
				p = p.tags(doParseTags(jp)).author(doGetTagValue(jp, "author")).date(doGetTagValue(jp, "date"));
				params.add(p);
			} else if (c.isArray()) {

			} else {
				Param p = Param.build().name(jp.getName()).type(jp.getFullyQualifiedName()).simpType(jp.getValue()).comment(comment.trim());
				p = p.tags(doParseTags(jp)).author(doGetTagValue(jp, "author")).date(doGetTagValue(jp, "date"));
				try {
					p = p.model(objectMapper.writeValueAsString(doParseFields(jc)));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				params.add(p);
			}
		}
		return params;
	}

	private Map<String, Object> doParseFields(JavaClass jc) {
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		for (JavaField jf : jc.getFields()) {
			if (Arrays.asList("serialVersionUID".split(",")).contains(jf.getName())) {
				continue;
			}
			json.put(jf.getName(), DocsUtil.getValLikeName(jf.getName()));
		}
		return json;
	}

	private Map<String, Object> doParseMvcAnnotation(JavaAnnotatedElement jae) {
		return AnnotatedElementUtils.findMergedAnnotationAttributes(jae instanceof JavaMethod ? doParseJMethod((JavaMethod) jae) : doParseJClass((JavaClass) jae), RequestMapping.class, false, false);
	}

	private Class<?> doParseJClass(JavaClass jc) {
		try {
			String key = jc.getFullyQualifiedName();
			Class<?> c = Class.class.cast(CACHE_ANNOTATED_ELEMENT.get(key));
			if (null == c) {
				c = Class.forName(jc.getFullyQualifiedName());
				CACHE_ANNOTATED_ELEMENT.put(key, c);
			}
			return c;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private Method doParseJMethod(JavaMethod jm) {
		try {
			String key = jm.getDeclaringClass().getFullyQualifiedName() + "." + jm.getCallSignature();
			Method m = Method.class.cast(CACHE_ANNOTATED_ELEMENT.get(key));
			if (null == m) {
				List<Class<?>> paramTypes = new ArrayList<Class<?>>();
				for (JavaType jt : jm.getParameterTypes()) {
					paramTypes.add(Class.forName(jt.getFullyQualifiedName()));
				}
				m = ReflectionUtils.findMethod(Class.forName(jm.getDeclaringClass().getFullyQualifiedName()), jm.getName(), paramTypes.toArray(new Class<?>[] {}));
				CACHE_ANNOTATED_ELEMENT.put(key, m);
			}
			return m;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private String doGetTagValue(JavaAnnotatedElement jae, String name) {
		Set<String> values = new HashSet<String>();
		for (DocletTag dt : jae.getTagsByName(name)) {
			if (!dt.getName().equals(name)) {
				continue;
			}
			values.add(dt.getValue().replace(name, StringUtils.EMPTY));
		}
		return StringUtils.join(values, "、");
	}

	private List<Tag> doParseTags(JavaAnnotatedElement jae) {
		List<Tag> tags = new ArrayList<Tag>();
		for (DocletTag dt : jae.getTags()) {
			tags.add(Tag.build().name(dt.getName()).value(dt.getValue()));
		}
		return tags;
	}

}
