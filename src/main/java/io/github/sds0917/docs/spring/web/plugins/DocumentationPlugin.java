package io.github.sds0917.docs.spring.web.plugins;

import org.springframework.plugin.core.Plugin;

public interface DocumentationPlugin extends Plugin<DocumentationType> {

	/**
	 * @return indicator to determine if the plugin is enabled
	 */
	boolean isEnabled();

	DocumentationType getDocumentationType();

	/**
	 * Gets the group name for the plugin. This is expected to be unique for each
	 * instance of the plugin
	 * 
	 * @return group the plugin belongs to
	 */
	String getGroupName();

}