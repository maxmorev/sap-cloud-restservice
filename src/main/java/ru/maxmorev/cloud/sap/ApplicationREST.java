package ru.maxmorev.cloud.sap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApplicationREST extends SpringBootServletInitializer {

	// Make sure that everything for .war deployment is there
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationREST.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ApplicationREST.class, args);
	}

}
