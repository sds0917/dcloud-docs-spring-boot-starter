package io.github.sds0917.docs.modules;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Param {

	private String name;
	private String author;
	private String date;
	private String comment;
	private String simpType;
	private String type;
	private String defaultValue;
	private String required;
	private String model;
	private List<Tag> tags;
	private Map<String, Object> mvc;

	public static Param build() {
		return new Param();
	}

	public Param name(String name) {
		this.name = name;
		return this;
	}

	public Param author(String author) {
		this.author = author;
		return this;
	}

	public Param date(String date) {
		this.date = date;
		return this;
	}

	public Param model(String model) {
		this.model = model;
		return this;
	}

	public Param comment(String comment) {
		this.comment = comment;
		return this;
	}

	public Param simpType(String simpType) {
		this.simpType = simpType;
		return this;
	}

	public Param type(String type) {
		this.type = type;
		return this;
	}

	public Param tags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public Param mvc(Map<String, Object> mvc) {
		this.mvc = mvc;
		return this;
	}

}