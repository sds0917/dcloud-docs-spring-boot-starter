package io.github.sds0917.docs.spring.web.plugins;

public class DocumentationPluginImpl implements DocumentationPlugin {

	private boolean enabled = true;

	@Override
	public boolean supports(DocumentationType delimiter) {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public DocumentationType getDocumentationType() {
		return null;
	}

	@Override
	public String getGroupName() {
		return null;
	}

}