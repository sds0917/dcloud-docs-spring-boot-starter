package io.github.sds0917.docs.modules;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api {

	private String name;
	private String author;
	private String date;
	private String comment;
	private String type;
	private List<Tag> tags;
	private List<Url> urls;
	private Map<String, Object> mvc;

	public static Api build() {
		return new Api();
	}

	public Api name(String name) {
		this.name = name;
		return this;
	}

	public Api author(String author) {
		this.author = author;
		return this;
	}

	public Api date(String date) {
		this.date = date;
		return this;
	}

	public Api comment(String comment) {
		this.comment = comment;
		return this;
	}

	public Api type(String type) {
		this.type = type;
		return this;
	}

	public Api urls(List<Url> urls) {
		this.urls = urls;
		return this;
	}

	public Api tags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public Api mvc(Map<String, Object> mvc) {
		this.mvc = mvc;
		return this;
	}

}