package com.alfresco.alfrescouploader;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Relationship;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.RelationshipDirection;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/** 
 * This is connector for CMIS Rest API call to Alfresco repository
 * 
 * @filename CMISConnector.java
 * @author Pravin Patil
 * @year 2020
 */
@Service
public class CMISConnector
{

	private static Logger log = LoggerFactory.getLogger(CMISConnector.class);
	
    // Set values from "application.properties" file
    @Value("${alfresco.repository.url}")
    String alfrescoUrl;
    @Value("${alfresco.repository.user}")
    String alfrescoUser;
    @Value("${alfresco.repository.pass}")
    String alfrescoPass;

    // CMIS living session
    private Session session;

    @PostConstruct
    public void init()
    {

        String alfrescoBrowserUrl = alfrescoUrl + "/api/-default-/public/cmis/versions/1.1/browser";

        Map<String, String> parameter = new HashMap<String, String>();

        parameter.put(SessionParameter.USER, alfrescoUser);
        parameter.put(SessionParameter.PASSWORD, alfrescoPass);

        parameter.put(SessionParameter.BROWSER_URL, alfrescoBrowserUrl);
        parameter.put(SessionParameter.BINDING_TYPE, BindingType.BROWSER.value());

        //Get session
        SessionFactory factory = SessionFactoryImpl.newInstance();
        session = factory.getRepositories(parameter).get(0).createSession();
        
        log.debug("Session created: "+session);

    }
    
    
    
	/**
     * Creates Document in Alfresco Repository under given path with given Document properties
     * 
     *@param HashMap<String, Object> properties, MultipartFile file, String folderPath
     *@return boolean
     */
	public boolean uploadDocument(HashMap<String, Object> createProperties, HashMap<String, Object> updateProperties, MultipartFile file, String folderPath) 
	{
		
		try 
		{
			//Get folder id
			Folder folder = getFolderbyPath(folderPath);
			
			log.info("Folder Object: "+folder);
			if(folder != null) {
				
				//Set mime type
				MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
			    
				//Set content stream
				//FileInputStream fileInputStream = new FileInputStream(documentBO.getContent()); 
		        DataInputStream dataInputStream = new DataInputStream(file.getInputStream()); 
				ContentStream contentStream = new ContentStreamImpl(file.getOriginalFilename(), BigInteger.valueOf(file.getSize()),
						fileTypeMap.getContentType(file.getOriginalFilename()), dataInputStream);
				//Create document in repository			
				Document docObj = folder.createDocument(createProperties, contentStream, VersioningState.MAJOR);
				
				docObj = (Document) docObj.updateProperties(updateProperties);
				
				log.debug("Uploaded : "+ docObj.getId() + ";  "+ docObj.getName() );
				return true;
			}else {
				log.info("No Folder Object found. Upload Skipped. ");
				return false;
			}
			
		} catch (Exception e) {
			log.error("Document Creation Failed...");
			log.error(e.getMessage());
		}
		return false;
	}
	
	/**
     * Returns Folder object for the given Folder Path
     * 
     *@param String folderPath
     *@return Folder
     */
	public Folder getFolderbyPath(String folderPath) 
	{
		
		try {
			CmisObject cmisObject = session.getObjectByPath(folderPath);
		    Folder folder = null;
		    
		    if (cmisObject.getBaseType() != null && cmisObject.getBaseType().getDisplayName() != null && "Folder".equalsIgnoreCase(cmisObject.getBaseType().getDisplayName())) 
		    {		    
		    	folder = (Folder) cmisObject;
		    	log.debug("Get folder: "+folder);		    	
		    }
		    
		    return folder;
		    
		} catch (CmisObjectNotFoundException cmonotfoundex) {
		  
			//LOGGER.error("Folder has not been found:", cmonotfoundex);
		}
		
		return null;
	}
	
	/**
     * Returns Root Folder object
     * 
     *@param String folderPath
     *@return Folder
     */
    public Folder getRootFolder()
    {
        return session.getRootFolder();
    }    
    
}
