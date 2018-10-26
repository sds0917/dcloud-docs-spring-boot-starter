package io.github.sds0917.docs.modules;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {

	private String name;
	private String author;
	private String date;
	private String comment;
	private String type;
	private List<Param> params;
	private List<Tag> tags;
	private Map<String, Object> mvc;

	public static Url build() {
		return new Url();
	}

	public Url name(String name) {
		this.name = name;
		return this;
	}

	public Url author(String author) {
		this.author = author;
		return this;
	}

	public Url date(String date) {
		this.date = date;
		return this;
	}

	public Url comment(String comment) {
		this.comment = comment;
		return this;
	}

	public Url type(String type) {
		this.type = type;
		return this;
	}

	public Url params(List<Param> params) {
		this.params = params;
		return this;
	}

	public Url tags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public Url mvc(Map<String, Object> mvc) {
		this.mvc = mvc;
		return this;
	}

}