/*
 *
 *  Copyright 2017-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package io.github.sds0917.docs.web;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;

import io.github.sds0917.docs.modules.App;
import io.github.sds0917.docs.spring.web.DocumentationCache;
import io.github.sds0917.docs.spring.web.PropertySourcedMapping;
import io.github.sds0917.docs.spring.web.json.Json;
import io.github.sds0917.docs.spring.web.json.JsonSerializer;
import io.github.sds0917.docs.spring.web.plugins.ApisParse;

@Controller
public class DocsController {

	public static final String DEFAULT_URL = "/v2/api-docs";
	private static final String HAL_MEDIA_TYPE = "application/hal+json";

	private final String hostNameOverride;
	private final DocumentationCache documentationCache;
	private final JsonSerializer jsonSerializer;
	@Autowired
	private ApisParse apisParse;

	public DocsController(Environment environment, DocumentationCache documentationCache, JsonSerializer jsonSerializer) {
		this.hostNameOverride = environment.getProperty("springfox.documentation.swagger.v2.host", "DEFAULT");
		this.documentationCache = documentationCache;
		this.jsonSerializer = jsonSerializer;
	}

	@RequestMapping(value = DEFAULT_URL, method = RequestMethod.GET, produces = { APPLICATION_JSON_VALUE, HAL_MEDIA_TYPE })
	@PropertySourcedMapping(value = "${springfox.documentation.swagger.v2.path}", propertyKey = "springfox.documentation.swagger.v2.path")
	@ResponseBody
	public ResponseEntity<Json> getDocumentation(@RequestParam(value = "group", required = false) String groupName, HttpServletRequest servletRequest) {
		apisParse.doParse();
		if (StringUtils.isEmpty(groupName)) {
			groupName = "default";
		}
		App app = documentationCache.documentationByGroup(groupName);
		return ResponseEntity.ok(jsonSerializer.toJson(app));
	}

	private String hostName(UriComponents uriComponents) {
		if ("DEFAULT".equals(hostNameOverride)) {
			String host = uriComponents.getHost();
			int port = uriComponents.getPort();
			if (port > -1) {
				return String.format("%s:%d", host, port);
			}
			return host;
		}
		return hostNameOverride;
	}
}
