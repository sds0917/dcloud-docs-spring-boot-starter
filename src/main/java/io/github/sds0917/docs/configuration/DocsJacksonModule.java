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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import io.github.sds0917.docs.spring.web.json.JacksonModuleRegistrar;

public class DocsJacksonModule extends SimpleModule implements JacksonModuleRegistrar {

	public void maybeRegisterModule(ObjectMapper objectMapper) {
		objectMapper.registerModule(new DocsJacksonModule());
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	public void setupModule(SetupContext context) {
		super.setupModule(context);
		// context.setMixInAnnotations(Object.class, CustomizedSwaggerSerializer.class);
	}

	@JsonAutoDetect
	@JsonInclude(value = Include.NON_EMPTY)
	private class CustomizedSwaggerSerializer {
	}

}
