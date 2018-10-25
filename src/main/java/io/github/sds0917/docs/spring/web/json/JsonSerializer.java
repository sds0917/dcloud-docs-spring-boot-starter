package io.github.sds0917.docs.spring.web.json;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSerializer {
	private ObjectMapper objectMapper = new ObjectMapper();

	public JsonSerializer(List<JacksonModuleRegistrar> modules) {
		for (JacksonModuleRegistrar each : modules) {
			each.maybeRegisterModule(objectMapper);
		}
	}

	public Json toJson(Object toSerialize) {
		try {
			return new Json(objectMapper.writeValueAsString(toSerialize));
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Could not write JSON", e);
		}
	}
}