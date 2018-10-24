package io.github.sds0917.docs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.sds0917.docs.config.EnableDocs;

@EnableDocs("io.github.sds0917.docs.controller")
@SpringBootApplication
public class DcloudDocsSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcloudDocsSpringBootApplication.class, args);
	}

}