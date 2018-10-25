package io.github.sds0917.docs.modules;

import java.util.List;

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
	private String path;
	private List<Tag> tags;
	private List<Url> urls;

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

	public Api path(String path) {
		this.path = path;
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

	public Api tags(List<Tag> tags) {
		this.tags = tags;
		return this;
	}

}