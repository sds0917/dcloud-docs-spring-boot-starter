package io.github.sds0917.docs.spring.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.github.sds0917.docs.modules.App;

public class DocumentationCache {

	private Map<String, App> documentationLookup = new HashMap<String, App>();

	public void addDocumentation(String groupName, App app) {
		documentationLookup.put(groupName, app);
	}

	public App documentationByGroup(String groupName) {
		return documentationLookup.get(groupName);
	}

	public Map<String, App> all() {
		return Collections.unmodifiableMap(documentationLookup);
	}

	public void clear() {
		documentationLookup.clear();
	}

}