Alfresco Uploader using Spring Boot and CMIS

#### Requirements

JDK 1.8
Maven 4.0


### Installation

Create and install finra:document content model using Model Manager ( from Alfresco Share) or deploy contentModel.xml

<type name="finra:document">
            <title>FINRA Document</title>
            <parent>cm:content</parent>
            <properties>
                <property name="finra:uniquedocid">
                    <title>Unique Doc Id</title>
                    <type>d:text</type>
                    <mandatory>false</mandatory>
                </property>
                <property name="finra:rating">
                    <title>Rating</title>
                    <type>d:text</type>
                    <mandatory>false</mandatory>
                </property>
                <property name="finra:checksum">
                    <title>CheckSum</title>
                    <type>d:text</type>
                    <mandatory>false</mandatory>
                </property>
            </properties>
        </type>
        
Verify finra:document has created


#### Usage

Run project using springboot:run
Launch http://localhost:8081 and upload document using form

#### Validation

You can validate the uploaded file from Alfresco Share or using below CMIS query

http://localhost:8080/alfresco/api/-default-/public/cmis/versions/1.1/browser/?cmisselector=query&succinct=true&q=select%20*%20from%20finra:document

