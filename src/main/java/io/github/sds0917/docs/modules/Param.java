package io.github.sds0917.docs.modules;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Param {

	private String name;
	private String comment;
	private String simpType;
	private String type;
	private String defaultValue;
	private String required;
	private List<Tag> tags;

}