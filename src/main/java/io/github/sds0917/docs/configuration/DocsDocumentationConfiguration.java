/*
 *
 *  Copyright 2015 the original author or authors.
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

package io.github.sds0917.docs.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.HandlerMapping;

import io.github.sds0917.docs.spring.web.DocumentationCache;
import io.github.sds0917.docs.spring.web.PropertySourcedRequestMappingHandlerMapping;
import io.github.sds0917.docs.spring.web.json.JacksonModuleRegistrar;
import io.github.sds0917.docs.spring.web.json.JsonSerializer;
import io.github.sds0917.docs.web.DocsController;

@Configuration
@ComponentScan(basePackages = { "io.github.sds0917.docs.spring.web", "io.github.sds0917.docs.spring.web.plugins" })
public class DocsDocumentationConfiguration {

	@Bean
	public DocumentationCache documentationCache() {
		return new DocumentationCache();
	}

	@Bean
	public JsonSerializer jsonSerializer(List<JacksonModuleRegistrar> moduleRegistrars) {
		return new JsonSerializer(moduleRegistrars);
	}

	@Bean
	public JacksonModuleRegistrar swagger2Module() {
		return new DocsJacksonModule();
	}

	@Bean
	public DocsController docsController(Environment environment, DocumentationCache documentationCache, JsonSerializer jsonSerializer) {
		return new DocsController(environment, documentationCache, jsonSerializer);
	}

	@Bean
	public HandlerMapping docsControllerMapping(Environment environment, DocsController docsController) {
		return new PropertySourcedRequestMappingHandlerMapping(environment, docsController);
	}

}