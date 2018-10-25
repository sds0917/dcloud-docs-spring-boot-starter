package io.github.sds0917.docs.modules;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

	private String name;
	private List<Api> apis;

	public static App build() {
		return new App();
	}

	public App name(String name) {
		this.name = name;
		return this;
	}

	public App addApi(Api api) {
		if (null == this.apis) {
			this.apis = new ArrayList<Api>();
		}
		this.apis.add(api);
		return this;
	}

}