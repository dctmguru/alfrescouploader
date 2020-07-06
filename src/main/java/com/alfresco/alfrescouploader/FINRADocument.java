/**
 * 
 */
package com.alfresco.alfrescouploader;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.activation.MimetypesFileTypeMap;

import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/** 
 * FINRADocument bean Class
 * 
 * @filename FINRADocument.java
 * @author Pravin Patil
 * @year 2020
 */
public class FINRADocument {

	/**
	 * 
	 */
	public FINRADocument() {
		// TODO Auto-generated constructor stub
	}
	
	private static Logger log = LoggerFactory.getLogger(FINRADocument.class);

	public static final String FINRA_RATING="finra:rating";	
	public static final String FINRA_UNIQUE_DOC_ID="finra:uniquedocid";
	public static final String FINRA_CHECKSUM="finra:checksum";
	
	/*name*/
	private String name;
	/*CheckSum*/
	private String checksum;
	/*Rating*/
	private String rating;
	/*Unique Id*/
	private String uniqueId;
	/*Mime type*/
	private String mimeType;	
	/*Content*/
	private MultipartFile file;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public HashMap<String, Object> getCreateProp() throws Exception{
		
		log.debug("Init FINRADocument");
		//Init properties Hashmap
		HashMap<String, Object> properties = new HashMap<String, Object>();		
		 
    
	    //Set Object type
		properties.put(PropertyIds.OBJECT_TYPE_ID, Constants.FINRA_TYPE);
		
		//Set cmis:name
        properties.put(PropertyIds.NAME, file.getOriginalFilename());
        
       
        return properties;
        
	}
  public HashMap<String, Object> getUpdateProp() throws Exception{
		
		log.debug("getAspectProp");
		//Init properties Hashmap
		HashMap<String, Object> properties = new HashMap<String, Object>();			 
    
	  
        //Set ranking
        if(this.getRating() != null && ! this.getRating().equalsIgnoreCase("")) {
        	properties.put(FINRA_RATING, this.getRating());
        }
        
        //Set checkSum
        if(this.getUniqueId() != null && !this.getUniqueId().equalsIgnoreCase("")) {
        	properties.put(FINRA_UNIQUE_DOC_ID, this.getUniqueId());
        }else {
        	properties.put(FINRA_UNIQUE_DOC_ID, Util.getUniqueDocId(file.getOriginalFilename()));
        }
        
      //Set checkSum
        if(this.getChecksum() != null && !this.getChecksum().equalsIgnoreCase("")) {
        	properties.put(FINRA_CHECKSUM, this.getChecksum());
        }else {
        	properties.put(FINRA_CHECKSUM, Util.getFileChecksum(file.getInputStream()));
        }
        return properties;
        
	}


}
