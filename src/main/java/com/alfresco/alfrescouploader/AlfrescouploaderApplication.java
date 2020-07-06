package com.alfresco.alfrescouploader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 
 * Class to run SpringBoot application to create document to Alfresco repository.
 * 
 * @filename AlfrescouploaderApplication.java
 * @author Pravin Patil
 * @year 2020
 */

@SpringBootApplication
public class AlfrescouploaderApplication {

	private static Logger log = LoggerFactory.getLogger(AlfrescouploaderApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(AlfrescouploaderApplication.class, args);
	}

}
