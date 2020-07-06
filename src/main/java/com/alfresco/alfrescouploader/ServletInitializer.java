package com.alfresco.alfrescouploader;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** 
 * ServletInitializer class
 * 
 * @filename ServletInitializer.java
 * @author Pravin Patil
 * @year 2020
 */

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AlfrescouploaderApplication.class);
	}

}
