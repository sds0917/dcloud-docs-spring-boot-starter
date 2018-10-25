package io.github.sds0917.docs.modules;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {

	private String name;
	private String author;
	private String comment;
	private String method;
	private String path;
	private String headers;
	private List<Param> params;
	private List<Tag> tags;

}