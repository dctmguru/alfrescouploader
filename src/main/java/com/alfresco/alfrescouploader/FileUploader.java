/**
 * 
 */
package com.alfresco.alfrescouploader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * This is uploader rest controller to upload document to Alfresco repository
 * 
 * @filename CMISConnector.java
 * @author Pravin Patil
 * @year 2020
 */
@RestController
public class FileUploader {

    private final Logger log = LoggerFactory.getLogger(FileUploader.class);
    
    ObjectMapper objectMapper = new ObjectMapper();
	
    @Autowired
    CMISConnector alfrescoConnector;
    
	@RequestMapping(value="/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam(required=true, value="file") MultipartFile file, @RequestParam(required=true, value="jsondata")String jsondata) throws Exception  {
		
		log.info("Uploading document");
 		
		log.debug(jsondata.toString());
		
		FINRADocument finraDocument = objectMapper.readValue(jsondata, FINRADocument.class);
		finraDocument.setFile(file);
		finraDocument.setName(file.getOriginalFilename());
		
		//Populate properties for upload
		HashMap<String, Object> createProperties = finraDocument.getCreateProp();
		HashMap<String, Object> updateProperties = finraDocument.getUpdateProp();
		
		//log.debug("Name: "+finraDocument.getName());
		//log.debug("CheckSum: "+finraDocument.getChecksum());
		//log.debug("Rating: "+finraDocument.getRating());
		//log.debug("Unique Id: "+finraDocument.getUniqueId());
		
		
		log.debug("----createProperties for finra:document --------");
		
		for (Map.Entry<String, Object> entry : createProperties.entrySet()) {
			log.debug(entry.getKey() + " : " + entry.getValue());
	    }
		log.debug("----updateProperties for finra:document --------");
		
		for (Map.Entry<String, Object> entry : updateProperties.entrySet()) {
			log.debug(entry.getKey() + " : " + entry.getValue());
	    }
		//Upload document
    	 	
    	log.info("Upload complete");
    	alfrescoConnector.uploadDocument(createProperties, updateProperties, file, Constants.DEFAULT_SITE_PATH);
    	
    	return new ResponseEntity<>("File is uploaded to Alfresco successfully", HttpStatus.OK);
		
	}

}
