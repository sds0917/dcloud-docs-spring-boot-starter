package io.github.sds0917.docs.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

	private String name;
	private String value;

	public static Tag build() {
		return new Tag();
	}

	public Tag name(String name) {
		this.name = name;
		return this;
	}

	public Tag value(String value) {
		this.value = value;
		return this;
	}

}