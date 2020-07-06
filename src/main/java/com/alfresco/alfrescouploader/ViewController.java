package com.alfresco.alfrescouploader;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 
 * ViewController class
 * 
 * @filename ViewController.java
 * @author Pravin Patil
 * @year 2020
 */

@Controller
public class ViewController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index";
	}
	
}