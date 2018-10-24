package io.github.sds0917.docs;

import java.io.File;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaAnnotatedElement;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaType;

public class DocsTest {

	private Map<JavaClass, Class<?>> CACHE_CLASS = new ConcurrentHashMap<JavaClass, Class<?>>();
	private Map<JavaMethod, Method> CACHE_METHOD = new ConcurrentHashMap<JavaMethod, Method>();
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void name() {
		try {
			Map<String, Object> json = new HashMap<String, Object>();
			String basePackage = "io.github.sds0917.docs.controller";
			List<String> paths = Arrays.asList("src/main/java".split(","));
			JavaProjectBuilder builder = new JavaProjectBuilder();
			builder.addClassLoader(getClass().getClassLoader());
			for (String path : paths) {
				builder.addSourceTree(new File(path));
			}
			for (JavaClass jc : builder.getClasses()) {
				if (!jc.getPackageName().contains(basePackage)) {
					continue;
				}
				json.put("name", jc.getName());
				json.put("fullyQualifiedName", jc.getGenericFullyQualifiedName());
				json.put("comment", jc.getComment());
				json.put("mvc", doParseMvcAnnotation(jc));
				json.putAll(doParseTags(jc));
				json.put("apis", doParseApis(jc));
				System.out.println(mapper.writeValueAsString(json));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<Map<String, Object>> doParseApis(JavaClass jc) {
		List<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
		for (JavaMethod jm : jc.getMethods()) {
			Map<String, Object> json = new HashMap<String, Object>();
			Method m = doParseJMethod(jm);
			json.put("name", m.toGenericString());
			json.put("comment", jm.getComment());
			json.put("mvc", doParseMvcAnnotation(jm));
			json.putAll(doParseTags(jm));
			array.add(json);
		}
		return array;
	}

	private Map<String, Object> doParseMvcAnnotation(JavaAnnotatedElement element) {
		Map<String, Object> json = new HashMap<String, Object>();
		AnnotatedElement ele = null;
		if (element instanceof JavaMethod) {
			ele = doParseJMethod((JavaMethod) element);
		}
		if (element instanceof JavaClass) {
			ele = doParseJClass((JavaClass) element);
		}
		if (null == ele) {
			return json;
		}
		RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(ele, RequestMapping.class);
		for (Method me : mapping.getClass().getDeclaredMethods()) {
			if (new HashSet<>(Arrays.asList("equals,toString,hashCode,annotationType".split(","))).contains(me.getName())) {
				continue;
			}
			json.put(me.getName(), ReflectionUtils.invokeMethod(me, mapping));
		}
		return json;
	}

	private Class<?> doParseJClass(JavaClass jc) {
		try {
			Class<?> c = CACHE_CLASS.get(jc);
			if (null == c) {
				CACHE_CLASS.put(jc, c = Class.forName(jc.getFullyQualifiedName()));
			}
			return c;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private Method doParseJMethod(JavaMethod jm) {
		try {
			Method m = CACHE_METHOD.get(jm);
			if (null == m) {
				List<Class<?>> paramTypes = new ArrayList<Class<?>>();
				for (JavaType jt : jm.getParameterTypes()) {
					paramTypes.add(Class.forName(jt.getFullyQualifiedName()));
				}
				CACHE_METHOD.put(jm, m = ReflectionUtils.findMethod(Class.forName(jm.getDeclaringClass().getFullyQualifiedName()), jm.getName(), paramTypes.toArray(new Class<?>[] {})));
			}
			return m;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	private Map<String, Object> doParseTags(JavaAnnotatedElement element) {
		String key = "param";
		Map<String, Object> json = new HashMap<String, Object>();
		for (DocletTag docletTag : element.getTags()) {
			String name = docletTag.getName();
			if (null != json.get(name) || (element instanceof JavaMethod && key.equals(name))) {
				continue;
			}
			Set<String> values = new TreeSet<String>();
			for (DocletTag tag : element.getTagsByName(name)) {
				values.add(tag.getValue());
			}
			json.put(name, StringUtils.join(values.toArray(), "、"));
		}

		if (element instanceof JavaMethod) {
			JavaMethod jm = (JavaMethod) element;
			if (CollectionUtils.isEmpty(jm.getParameters())) {
				return json;
			}
			List<Map<String, String>> params = new ArrayList<Map<String, String>>();
			for (JavaParameter jp : jm.getParameters()) {
				Map<String, String> param = new HashMap<String, String>();
				param.put("name", jp.getName());
				param.put("fullyQualifiedName", jp.getFullyQualifiedName());
				DocletTag tag = jm.getTagsByName(key).stream().filter(e -> e.getParameters().stream().filter(p -> p.equals(jp.getName())).count() > 0).findFirst().orElse(null);
				if (null == tag) {
					params.add(param);
					continue;
				}
				param.put("comment", StringUtils.join(tag.getParameters().stream().filter(p -> !jp.getName().equals(p)).toArray(), ""));
				params.add(param);
			}
			json.put("params", params);
		}
		return json;
	}

}