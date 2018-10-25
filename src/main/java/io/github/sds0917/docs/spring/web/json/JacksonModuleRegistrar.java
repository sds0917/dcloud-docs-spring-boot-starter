package io.github.sds0917.docs.spring.web.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface JacksonModuleRegistrar {

	void maybeRegisterModule(ObjectMapper objectMapper);

}