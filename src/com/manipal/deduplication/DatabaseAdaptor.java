package com.manipal.deduplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static main.CFRTAdapter.logger;

/**
 * @author Rajasekaran Raghavan
 * @version v5.1
 * @lastUpdated 28-06-2016
 * @description
 * 		This class contains methods to interact with the database and to inserts, updates and returns result sets
 */
public class DatabaseAdaptor {
	public final String UN_ALIAS_TAG = "INDIVIDUAL_ALIAS";
	public final String FIRST_NAME_TAG = "FIRST_NAME";
	public final String ALIAS_NAME_TAG = "ALIAS_NAME";
	public final String THIRD_NAME_TAG = "THIRD_NAME";
	
	public static Connection conn = null;
	public static Connection updateConnection = null; //separate connection for updating results. NAgaraj 24/12/2016

	/**
	 * This method fetches the last added OFAC list from SANCTIONSLIST table and returns the XML String
	 * @return String
	 */
	public String getofacsanctionslist(){
		Statement st = null;
    	ResultSet rs = null;
    	String strQry =null;
    	String ofac_xml = null;
		try{
			if(conn == null || conn.isClosed()){
				conn = getConnection("jdbc/VIZProd");
			}
    		strQry = "select list_xml from sanctionslist where list_type='OFAC' and rownum<=2 order by updated_datetime desc";
    		st = conn.createStatement();
        	rs=st.executeQuery(strQry);
        	while(rs.next()){
        		if(ofac_xml!=null&&ofac_xml.trim().length()>0){
        			ofac_xml += "##"+rs.getString(1);
        		}else{
        			ofac_xml = rs.getString(1);
        		}
        	}
		}	
    	catch (Exception e) {
    		ofac_xml = "";
    		logger.error("", e);
		}
    	finally{
    		try{
    			if(rs!=null){
    				rs.close();
    			}
    			if(st!=null){
    				st.close();
    			}
    		}catch(Exception e){
    			logger.error("", e);
    		}
        }
		return ofac_xml;
	}
	
	/**
	 * This method fetches the last added xml file from SANCTIONSLIST table and returns the XML String. Date : 28-FEB-2017. Added By : Nagaraj B.
	 * @return String
	 */
	public String getSanctionsListDetails(String listType) {
		String xmlFile = null;
		String cNo_query = "select list_xml from sanctionslist where list_type='" + listType
				+ "' order by updated_datetime desc";
		try (Connection conn = getConnection("jdbc/VIZProd");
				Statement stmt = conn.createStatement();
				ResultSet rs_cNo = stmt.executeQuery(cNo_query);) {
			if (rs_cNo.next()) {
				xmlFile = rs_cNo.getString(1);
			}
		} catch (SQLException e1) {
			logger.error("", e1);
		}
		return xmlFile;
	}
	
	/**
	 * @param xsdPath
	 * @param xmlFile
	 * @return String
	 * 		This methods validates the given xmlFile against the given Xml Schema Definition file
	 * 		and return true if the validation succeeds else return false along with the error message 
	 */
	public String validateXMLSchema(File xsdPath, File xmlFile){
	      try {
	         SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	         Schema schema = factory.newSchema(xsdPath);
	            Validator validator = schema.newValidator();
	            validator.validate(new StreamSource(xmlFile));
	      } catch (IOException e){    
	    	  logger.error("", e);
	    	  return "false";
	      }catch(SAXException e1){
	    	  logger.error("", e1);
	    	  return "false~"+e1.getMessage();
	      }
	      return "true";
	   }
	
	
	/**
	 * This method is used to remove the byte order mark (BOM) from XML string
	 * @param s
	 * @return String
	 */
	public static String removeUTF8BOM(String s) {
        if (s.startsWith("\uFEFF")) {
            s = s.substring(1);
        }
        return s;
    }
	
	/**
	 * @param dateString
	 * @return String - FormattedDate
	 * <br>&emsp;&emsp;true - Valid Date
	 * <br>&emsp;&emsp;false - UnParsable Date
	 */
	public String convertDateToValidFormat(String dateString){
		String formattedDate = "";
		try {
			DateFormat fromFormat = new SimpleDateFormat("dd MMM yyyy");
			fromFormat.setLenient(false);
			DateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
			toFormat.setLenient(false);
			Date date = fromFormat.parse(dateString);
			formattedDate = toFormat.format(date);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return formattedDate;
	}
	
	
	 /**
	 * @param dateString
	 * @return boolean
	 * <br>&emsp;&emsp;true - Valid Date
	 * <br>&emsp;&emsp;false - UnParsable Date
	 */
	public boolean isValidDOBForOFAC(String dateString){
		boolean isValid = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
			Date formattedDate;
			formattedDate = sdf.parse(dateString);
			if(formattedDate!=null)
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
		}
		return isValid;
	}
	
	
	public HashMap<Integer, HashMap<String,ArrayList<String>>> prepareLookUpList(String xmlString, String listType, HashMap<Integer, HashMap<String, ArrayList<String>>> oldhashMap){
		Document doc = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		HashMap<Integer, HashMap<String, ArrayList<String>>> list_details =  new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		DatabaseAdaptor dbaObj= new DatabaseAdaptor();
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			if(xmlString!=null && xmlString.trim().length()>0){
		    	xmlString = removeUTF8BOM(xmlString);
		    	xmlString = xmlString.replaceAll("[^\\x20-\\x7e]", "");
		    	xmlString = xmlString.replaceAll("&", " and ");
			}
			//System.out.println("list type--"+listType);
	        InputStream is = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
	        
	   //ofac Entity     
	        if(listType!=null && listType.trim().equalsIgnoreCase("OFAC")){
	   	      if(xmlString!=null && xmlString.trim().length()>0){
			    	doc = dBuilder.parse(is);
			    	doc.getDocumentElement().normalize();
				    	NodeList nodes = doc.getElementsByTagName("sdnEntry");
			    	HashMap<String, ArrayList<String>> individualDetails = null;
			    	if(nodes!=null && nodes.getLength()>0){
			    		//ofacListDetails = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
			    		int dataID = 0;
				    	for (int i = 0; i < nodes.getLength(); i++) {
				    		individualDetails = new HashMap<String, ArrayList<String>>();
				    		
				        	NodeList subNodes = nodes.item(i).getChildNodes();
				        	for (int j = 0; j < subNodes.getLength(); j++) {
				        		Element element = (Element)  nodes.item(i).getChildNodes();
				        		//ConstantConfiguration.watchlistLogger.info("tag name=="+element.getElementsByTagName("sdnType").item(0).getTextContent());   	
				        		if(element.getElementsByTagName("sdnType")!=null && 
				        				element.getElementsByTagName("sdnType").item(0).getTextContent().contains("Entity"))
				        		{
				        			ArrayList<String> entityTypeList = new ArrayList<String>();
						    		entityTypeList.add("ENTITY");
						    		individualDetails.put("ENTITY_TYPE",entityTypeList);
				        			//ConstantConfiguration.watchlistLogger.info("enter into if==ofac==");   			
								if(subNodes.item(j).getNodeName().equalsIgnoreCase("uid")){
									dataID = Integer.parseInt(subNodes.item(j).getTextContent());
									}
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("firstName")){
									if(individualDetails.get(FIRST_NAME_TAG)!=null){
										individualDetails.get(FIRST_NAME_TAG).add(subNodes.item(j).getTextContent());
										individualDetails.put(FIRST_NAME_TAG,individualDetails.get(FIRST_NAME_TAG));	
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put(FIRST_NAME_TAG,list);
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("lastName")){
									//System.out.println("Last Name -- "+subNodes.item(j).getTextContent());
									if(individualDetails.get(THIRD_NAME_TAG)!=null){
										individualDetails.get(THIRD_NAME_TAG).add(subNodes.item(j).getTextContent());
										individualDetails.put(THIRD_NAME_TAG,individualDetails.get(THIRD_NAME_TAG));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put(THIRD_NAME_TAG,list);
									}
					              }
								
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("akaList")){
									for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("aka")){
											Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
											String aliasName = "1##2##3";
											if(elem.getElementsByTagName("firstName")!=null && elem.getElementsByTagName("firstName").getLength()>0){
												aliasName = aliasName.replace("1",
														elem.getElementsByTagName("firstName").item(0).getTextContent());
											}
											if(elem.getElementsByTagName("lastName")!=null && elem.getElementsByTagName("lastName").getLength()>0){
												aliasName = aliasName.replace("2",
														elem.getElementsByTagName("lastName").item(0).getTextContent());
											}
											if(elem.getElementsByTagName("category")!=null && elem.getElementsByTagName("category").getLength()>0){
												aliasName = aliasName.replace("3",
														elem.getElementsByTagName("category").item(0).getTextContent());
											}
											/* Nagaraj 15/11/2016
											if(individualDetails.get("ALIAS_NAME_ENTITY")!=null){
												individualDetails.get("ALIAS_NAME_ENTITY").add(aliasName);
												individualDetails.put("ALIAS_NAME_ENTITY",individualDetails.get("ALIAS_NAME_ENTITY"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(aliasName);
												individualDetails.put("ALIAS_NAME_ENTITY",list);
											}
											*/
											if(individualDetails.get("ALIAS_NAME")!=null){
												individualDetails.get("ALIAS_NAME").add(aliasName);
												individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(aliasName);
												individualDetails.put("ALIAS_NAME",list);
												individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
											}
											//end of changes NAgaraj 15/11/2016
										}
									}
								}
					              else if(subNodes.item(j).getNodeName().equalsIgnoreCase("addressList")){
										for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
											if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("address")){
												Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
												if(elem.getElementsByTagName("address1")!=null && elem.getElementsByTagName("address1").getLength()>0){
													if(individualDetails.get("STREETEntity")!=null){
														individualDetails.get("STREETEntity").add(elem.getElementsByTagName("address1").item(0).getTextContent());
														individualDetails.put("STREETEntity",individualDetails.get("STREETEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("address1").item(0).getTextContent());
														individualDetails.put("STREETEntity",list);
													}
												}
												if(elem.getElementsByTagName("address2")!=null && elem.getElementsByTagName("address2").getLength()>0){
													if(individualDetails.get("STREETEntity")!=null){
														individualDetails.get("STREETEntity").add(elem.getElementsByTagName("address2").item(0).getTextContent());
														individualDetails.put("STREETEntity",individualDetails.get("STREETEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("address2").item(0).getTextContent());
														individualDetails.put("STREETEntity",list);
													}
												}
												if(elem.getElementsByTagName("address3")!=null && elem.getElementsByTagName("address3").getLength()>0){
													if(individualDetails.get("STREETEntity")!=null){
														individualDetails.get("STREETEntity").add(elem.getElementsByTagName("address3").item(0).getTextContent());
														individualDetails.put("STREETEntity",individualDetails.get("STREETEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("address3").item(0).getTextContent());
														individualDetails.put("STREETEntity",list);
													}
												}
												if(elem.getElementsByTagName("city")!=null && elem.getElementsByTagName("city").getLength()>0){
													if(individualDetails.get("CITYEntity")!=null){
														individualDetails.get("CITYEntity").add(elem.getElementsByTagName("city").item(0).getTextContent());
														individualDetails.put("CITYEntity",individualDetails.get("CITYEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("city").item(0).getTextContent());
														individualDetails.put("CITYEntity",list);
													}
												}
												if(elem.getElementsByTagName("country")!=null && elem.getElementsByTagName("country").getLength()>0){
													if(individualDetails.get("COUNTRYEntity")!=null){
														individualDetails.get("COUNTRYEntity").add(elem.getElementsByTagName("country").item(0).getTextContent());
														individualDetails.put("COUNTRYEntity",individualDetails.get("COUNTRYEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("country").item(0).getTextContent());
														individualDetails.put("COUNTRYEntity",list);
													}
												}
												if(elem.getElementsByTagName("stateOrProvince")!=null && elem.getElementsByTagName("stateOrProvince").getLength()>0){
													if(individualDetails.get("STATE_PROVINCEEntity")!=null){
														individualDetails.get("STATE_PROVINCEEntity").add(elem.getElementsByTagName("stateOrProvince").item(0).getTextContent());
														individualDetails.put("STATE_PROVINCEEntity",individualDetails.get("STATE_PROVINCEEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("stateOrProvince").item(0).getTextContent());
														individualDetails.put("STATE_PROVINCEEntity",list);
													}
												}
												if(elem.getElementsByTagName("postalCode")!=null && elem.getElementsByTagName("postalCode").getLength()>0){
													if(individualDetails.get("ZIP_CODEEntity")!=null){
														individualDetails.get("ZIP_CODEEntity").add(elem.getElementsByTagName("postalCode").item(0).getTextContent());
														individualDetails.put("ZIP_CODEEntity",individualDetails.get("ZIP_CODEEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("postalCode").item(0).getTextContent());
														individualDetails.put("ZIP_CODEEntity",list);
													}
												}
											}
										}
									}
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("idList")){
									for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("id")){
											Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
											if((elem.getElementsByTagName("idType")!=null && 
											elem.getElementsByTagName("idType").item(0).getTextContent().contains("Passport"))){	
												if(elem.getElementsByTagName("idNumber")!=null && elem.getElementsByTagName("idNumber").getLength()>0){
													if(individualDetails.get("PASSPORT_NUMBEREntity")!=null){
														individualDetails.get("PASSPORT_NUMBEREntity").add(elem.getElementsByTagName("idNumber").item(0).getTextContent());
														individualDetails.put("PASSPORT_NUMBEREntity",individualDetails.get("PASSPORT_NUMBEREntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(elem.getElementsByTagName("idNumber").item(0).getTextContent());
														individualDetails.put("PASSPORT_NUMBEREntity",list);
													}
												}
												
											}
										}
									}
								}
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("dateOfBirthList")){
									for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("dateOfBirthItem")){
											Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
											if(elem.getElementsByTagName("dateOfBirth")!=null && elem.getElementsByTagName("dateOfBirth").getLength()>0){
												String dob =  elem.getElementsByTagName("dateOfBirth").item(0).getTextContent();
												if(dbaObj.isValidDOBForOFAC(dob))
												{
												if(individualDetails.get("DATE_OF_BIRTHEntity")!=null){
													individualDetails.get("DATE_OF_BIRTHEntity").add(convertDateToValidFormat(dob));
													individualDetails.put("DATE_OF_BIRTHEntity",individualDetails.get("DATE_OF_BIRTHEntity"));
												}else{
													ArrayList<String> list = new ArrayList<String>();
													list.add(convertDateToValidFormat(dob));
													individualDetails.put("DATE_OF_BIRTHEntity",list);
												}
												
												}
												
											}
										}
									}
								}
				        	}	
								
				//ofac individual	
				        		else{

									//ConstantConfiguration.watchlistLogger.info("enter into esle ofac===");   			
									if(subNodes.item(j).getNodeName().equalsIgnoreCase("uid")){
										dataID = Integer.parseInt(subNodes.item(j).getTextContent());
										}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("firstName")){
										if(individualDetails.get("FIRST_NAME")!=null){
											individualDetails.get("FIRST_NAME").add(subNodes.item(j).getTextContent());
											individualDetails.put("FIRST_NAME",individualDetails.get("FIRST_NAME"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("FIRST_NAME",list);
										}
									}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("lastName")){
										if(individualDetails.get("THIRD_NAME")!=null){
											individualDetails.get("THIRD_NAME").add(subNodes.item(j).getTextContent());
											individualDetails.put("THIRD_NAME",individualDetails.get("THIRD_NAME"));
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("THIRD_NAME",list);
										}
						              }
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("akaList")){
										for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
											if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("aka")){
												Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
												String aliasName = "1##2##3";
												if(elem.getElementsByTagName("firstName")!=null && elem.getElementsByTagName("firstName").getLength()>0){
													aliasName = aliasName.replace("1",
															elem.getElementsByTagName("firstName").item(0).getTextContent());
												}
												if(elem.getElementsByTagName("lastName")!=null && elem.getElementsByTagName("lastName").getLength()>0){
													aliasName = aliasName.replace("2",
															elem.getElementsByTagName("lastName").item(0).getTextContent());
												}
												if(elem.getElementsByTagName("category")!=null && elem.getElementsByTagName("category").getLength()>0){
													aliasName = aliasName.replace("3",
															elem.getElementsByTagName("category").item(0).getTextContent());
												}
												if(individualDetails.get("ALIAS_NAME")!=null){
													individualDetails.get("ALIAS_NAME").add(aliasName);
													individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
												}else{
													ArrayList<String> list = new ArrayList<String>();
													list.add(aliasName);
													individualDetails.put("ALIAS_NAME",list);
												}
											}
										}
									}
						              else if(subNodes.item(j).getNodeName().equalsIgnoreCase("addressList")){
											for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
												if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("address")){
													Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
													if(elem.getElementsByTagName("address1")!=null && elem.getElementsByTagName("address1").getLength()>0){
														if(individualDetails.get("STREET")!=null){
															individualDetails.get("STREET").add(elem.getElementsByTagName("address1").item(0).getTextContent());
															individualDetails.put("STREET",individualDetails.get("STREET"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("address1").item(0).getTextContent());
															individualDetails.put("STREET",list);
														}
													}
													if(elem.getElementsByTagName("address2")!=null && elem.getElementsByTagName("address2").getLength()>0){
														if(individualDetails.get("STREET")!=null){
															individualDetails.get("STREET").add(elem.getElementsByTagName("address2").item(0).getTextContent());
															individualDetails.put("STREET",individualDetails.get("STREET"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("address2").item(0).getTextContent());
															individualDetails.put("STREET",list);
														}
													}
													if(elem.getElementsByTagName("address3")!=null && elem.getElementsByTagName("address3").getLength()>0){
														if(individualDetails.get("STREET")!=null){
															individualDetails.get("STREET").add(elem.getElementsByTagName("address3").item(0).getTextContent());
															individualDetails.put("STREET",individualDetails.get("STREET"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("address3").item(0).getTextContent());
															individualDetails.put("STREET",list);
														}
													}
													if(elem.getElementsByTagName("city")!=null && elem.getElementsByTagName("city").getLength()>0){
														if(individualDetails.get("CITY")!=null){
															individualDetails.get("CITY").add(elem.getElementsByTagName("city").item(0).getTextContent());
															individualDetails.put("CITY",individualDetails.get("CITY"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("city").item(0).getTextContent());
															individualDetails.put("CITY",list);
														}
													}
													if(elem.getElementsByTagName("country")!=null && elem.getElementsByTagName("country").getLength()>0){
														if(individualDetails.get("COUNTRY")!=null){
															individualDetails.get("COUNTRY").add(elem.getElementsByTagName("country").item(0).getTextContent());
															individualDetails.put("COUNTRY",individualDetails.get("COUNTRY"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("country").item(0).getTextContent());
															individualDetails.put("COUNTRY",list);
														}
													}
													if(elem.getElementsByTagName("stateOrProvince")!=null && elem.getElementsByTagName("stateOrProvince").getLength()>0){
														if(individualDetails.get("STATE_PROVINCE")!=null){
															individualDetails.get("STATE_PROVINCE").add(elem.getElementsByTagName("stateOrProvince").item(0).getTextContent());
															individualDetails.put("STATE_PROVINCE",individualDetails.get("STATE_PROVINCE"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("stateOrProvince").item(0).getTextContent());
															individualDetails.put("STATE_PROVINCE",list);
														}
													}
													if(elem.getElementsByTagName("postalCode")!=null && elem.getElementsByTagName("postalCode").getLength()>0){
														if(individualDetails.get("ZIP_CODE")!=null){
															individualDetails.get("ZIP_CODE").add(elem.getElementsByTagName("postalCode").item(0).getTextContent());
															individualDetails.put("ZIP_CODE",individualDetails.get("ZIP_CODE"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("postalCode").item(0).getTextContent());
															individualDetails.put("ZIP_CODE",list);
														}
													}
												}
											}
										}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("idList")){
										for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
											if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("id")){
												Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
												if((elem.getElementsByTagName("idType")!=null && 
												elem.getElementsByTagName("idType").item(0).getTextContent().contains("Passport"))){	
													if(elem.getElementsByTagName("idNumber")!=null && elem.getElementsByTagName("idNumber").getLength()>0){
														if(individualDetails.get("PASSPORT_NUMBER")!=null){
															individualDetails.get("PASSPORT_NUMBER").add(elem.getElementsByTagName("idNumber").item(0).getTextContent());
															individualDetails.put("PASSPORT_NUMBER",individualDetails.get("PASSPORT_NUMBER"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(elem.getElementsByTagName("idNumber").item(0).getTextContent());
															individualDetails.put("PASSPORT_NUMBER",list);
														}
													}
													
												}
											}
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("dateOfBirthList")){
										for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
											if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("dateOfBirthItem")){
												Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
												if(elem.getElementsByTagName("dateOfBirth")!=null && elem.getElementsByTagName("dateOfBirth").getLength()>0){
													String dob =  elem.getElementsByTagName("dateOfBirth").item(0).getTextContent();
													if(dbaObj.isValidDOBForOFAC(dob))
													{
													if(individualDetails.get("DATE_OF_BIRTH")!=null){
														individualDetails.get("DATE_OF_BIRTH").add(convertDateToValidFormat(dob));
														individualDetails.put("DATE_OF_BIRTH",individualDetails.get("DATE_OF_BIRTH"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(convertDateToValidFormat(dob));
														individualDetails.put("DATE_OF_BIRTH",list);
													}
													
													}
													
												}
											}
										}
									}		
					        	
								}
								
								
							}
				        	
				        	//list_details.put(dataID, individualDetails);
				        	
				        	if(individualDetails != null && individualDetails.size() > 0){
				         	//ADDED BELOW CODE FOR FIXED DELTA SCREENING. DATE : 21-FEB-2017. ADDED BY : NAGARAJ B.
				        	if(oldhashMap == null){ // If DB have only one xml file,Execute BELOW CODE.
				        		list_details.put(dataID, individualDetails);
				        		//ConstantConfiguration.watchlistLogger.info("OLD HASH MAP DATA-----"+list_details); 
				        	}
				        	else{
				        		//COMAPRING CURRENT DATA ID AND OLD HASH MAP DATA ID.
				        		if(oldhashMap.containsKey(dataID)){
				        			if(!oldhashMap.containsValue(individualDetails)){
				        				list_details.put(dataID, individualDetails);
					        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
				        			}
				        			else{
					        			//IF CURRENT DATA ID = OLD HASH MAP DATA ID MATCHED AND VALUES ALSO MATCHED, THEN NOT REQUIRED TO SCREEN THIS CUSTOMER.
					        			oldhashMap.put(dataID, individualDetails);
					        			//ConstantConfiguration.watchlistLogger.info("NEW HASH MAP DATA-----"+oldhashMap); 
					        		}
				        		}			        			
				        		else{
				        			//IF CURRENT DATA ID != OLD HASHMAP DATA ID , THIS CUSTOMER NEED TO BE SCREEN.
				        			list_details.put(dataID, individualDetails);
				        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
				        		}
				        	}
				        	}
						}
				    	
			    	}
				}
	   	  // ConstantConfiguration.watchlistLogger.info("ofacListDetails total data ==="+ofacListDetails);	    
			    
	        }
	        
	        //PEP LIST INDIVIDUALS
	        else if(listType!=null && listType.trim().equalsIgnoreCase("PEP List")){
		   	      if(xmlString!=null && xmlString.trim().length()>0){
				    	doc = dBuilder.parse(is);
				    	doc.getDocumentElement().normalize();
					    	NodeList nodes = doc.getElementsByTagName("sdnEntry");
				    	HashMap<String, ArrayList<String>> individualDetails = null;
				    	if(nodes!=null && nodes.getLength()>0){
				    		int dataID = 0;
					    	for (int i = 0; i < nodes.getLength(); i++) {
					    		individualDetails = new HashMap<String, ArrayList<String>>();
					    		
					        	NodeList subNodes = nodes.item(i).getChildNodes();
					        	for (int j = 0; j < subNodes.getLength(); j++) {
					        		Element element = (Element)  nodes.item(i).getChildNodes();
					        		ArrayList<String> entityTypeList = new ArrayList<String>();
									if(subNodes.item(j).getNodeName().equalsIgnoreCase("uid")){
										try{
											dataID = Integer.parseInt(subNodes.item(j).getTextContent());
										}catch(Exception e){
											break;
										}
										}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("FIRST_NAME")){
										if(individualDetails.get("FIRST_NAME")!=null){
											individualDetails.get("FIRST_NAME").add(subNodes.item(j).getTextContent());
											individualDetails.put("FIRST_NAME",individualDetails.get("FIRST_NAME"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("FIRST_NAME",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("SECOND_NAME")){
										if(individualDetails.get("SECOND_NAME")!=null){
											individualDetails.get("SECOND_NAME").add(subNodes.item(j).getTextContent());
											individualDetails.put("SECOND_NAME",individualDetails.get("SECOND_NAME"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("SECOND_NAME",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("THIRD_NAME")){
										if(individualDetails.get("THIRD_NAME")!=null){
											individualDetails.get("THIRD_NAME").add(subNodes.item(j).getTextContent());
											individualDetails.put("THIRD_NAME",individualDetails.get("THIRD_NAME"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("THIRD_NAME",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("DATE_OF_BIRTH")){
										if(individualDetails.get("DATE_OF_BIRTH")!=null){
											individualDetails.get("DATE_OF_BIRTH").add(subNodes.item(j).getTextContent());
											individualDetails.put("DATE_OF_BIRTH",individualDetails.get("DATE_OF_BIRTH"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("DATE_OF_BIRTH",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PADDRESS1")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("PADDRESS2")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("CADDRESS1")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("CADDRESS2")){
										if(individualDetails.get("STREET")!=null){
											individualDetails.get("STREET").add(subNodes.item(j).getTextContent());
											individualDetails.put("STREET",individualDetails.get("STREET"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("STREET",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PCITY")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("CCITY")){
										if(individualDetails.get("CITY")!=null){
											individualDetails.get("CITY").add(subNodes.item(j).getTextContent());
											individualDetails.put("CITY",individualDetails.get("CITY"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("CITY",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PZIP_CODE")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("CZIP_CODE")){
										if(individualDetails.get("ZIP_CODE")!=null){
											individualDetails.get("ZIP_CODE").add(subNodes.item(j).getTextContent());
											individualDetails.put("ZIP_CODE",individualDetails.get("ZIP_CODE"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("ZIP_CODE",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PSTATE_PROVINCE")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("CSTATE_PROVINCE")){
										if(individualDetails.get("STATE_PROVINCE")!=null){
											individualDetails.get("STATE_PROVINCE").add(subNodes.item(j).getTextContent());
											individualDetails.put("STATE_PROVINCE",individualDetails.get("STATE_PROVINCE"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("STATE_PROVINCE",list);
										}
									}
									else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PCOUNTRY")
											|| subNodes.item(j).getNodeName().equalsIgnoreCase("CCOUNTRY")){
										if(individualDetails.get("COUNTRY")!=null){
											individualDetails.get("COUNTRY").add(subNodes.item(j).getTextContent());
											individualDetails.put("COUNTRY",individualDetails.get("COUNTRY"));	
										}else{
											ArrayList<String> list = new ArrayList<String>();
											list.add(subNodes.item(j).getTextContent());
											individualDetails.put("COUNTRY",list);
										}
									}
									
					        	}
					        	if(individualDetails != null && individualDetails.size() > 0){
					        		//list_details.put(dataID, individualDetails);
					        		
					        	 	//ADDED BELOW CODE FOR FIXED DELTA SCREENING. DATE : 21-FEB-2017. ADDED BY : NAGARAJ B.
						        	if(oldhashMap == null){ // If DB have only one xml file,Execute BELOW CODE.
						        		list_details.put(dataID, individualDetails);
						        		//ConstantConfiguration.watchlistLogger.info("OLD HASH MAP DATA-----"+list_details); 
						        	}
						        	else{
						        		//COMAPRING CURRENT DATA ID AND OLD HASH MAP DATA ID.
						        		if(oldhashMap.containsKey(dataID)){
						        			if(!oldhashMap.containsValue(individualDetails)){
						        				list_details.put(dataID, individualDetails);
							        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
						        			}
						        			else{
							        			//IF CURRENT DATA ID = OLD HASH MAP DATA ID MATCHED AND VALUES ALSO MATCHED, THEN NOT REQUIRED TO SCREEN THIS CUSTOMER.
							        			oldhashMap.put(dataID, individualDetails);
							        			//ConstantConfiguration.watchlistLogger.info("NEW HASH MAP DATA-----"+oldhashMap); 
							        		}
						        		}			        			
						        		else{
						        			//IF CURRENT DATA ID != OLD HASHMAP DATA ID , THIS CUSTOMER NEED TO BE SCREEN.
						        			list_details.put(dataID, individualDetails);
						        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
						        		}
						        	}
					        	}
					        	
					    	}
				    	}
		   	      }
	        }
	        
	        
	     //un individual   
	        
	        else if(listType!=null && listType.trim().equals("UN List")){
	        	if(xmlString!=null && xmlString.trim().length()>0){
			    	doc = dBuilder.parse(is);
			    	doc.getDocumentElement().normalize();
			    	if(doc.getElementsByTagName("INDIVIDUAL") != null)
				     {
						//System.out.println("Reading UN INDIVIDUAL -- ");

			    		//ConstantConfiguration.watchlistLogger.info("enter into if un==="); 
			    	NodeList nodes = doc.getElementsByTagName("INDIVIDUAL");
			    	HashMap<String, ArrayList<String>> individualDetails = null;
			    	if(nodes!=null && nodes.getLength()>0){
			    		int dataID = 0;
			    		//ofacListDetails = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
				    	for (int i = 0; i < nodes.getLength(); i++) {
				    		individualDetails = new HashMap<String, ArrayList<String>>();
				        	NodeList subNodes = nodes.item(i).getChildNodes();
				        	for (int j = 0; j < subNodes.getLength(); j++) {
								if(subNodes.item(j).getNodeName().equalsIgnoreCase("DATAID")){
									dataID = Integer.parseInt(subNodes.item(j).getTextContent());
								}
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("VERSIONNUM")){
									if(individualDetails.get("VERSIONNUM")!=null){
										individualDetails.get("VERSIONNUM").add(subNodes.item(j).getTextContent());
										individualDetails.put("VERSIONNUM",individualDetails.get("VERSIONNUM"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("VERSIONNUM",list);
									}	
						        }	
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("FIRST_NAME")){
									//System.out.println("First Name -- "+subNodes.item(j).getTextContent());
									if(individualDetails.get("FIRST_NAME")!=null){
										individualDetails.get("FIRST_NAME").add(subNodes.item(j).getTextContent());
										individualDetails.put("FIRST_NAME",individualDetails.get("FIRST_NAME"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("FIRST_NAME",list);
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("SECOND_NAME")){
									if(individualDetails.get("SECOND_NAME")!=null){
										individualDetails.get("SECOND_NAME").add(subNodes.item(j).getTextContent());
										individualDetails.put("SECOND_NAME",individualDetails.get("SECOND_NAME"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("SECOND_NAME",list);
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("THIRD_NAME")){
									if(individualDetails.get("THIRD_NAME")!=null){
										individualDetails.get("THIRD_NAME").add(subNodes.item(j).getTextContent());
										individualDetails.put("THIRD_NAME",individualDetails.get("THIRD_NAME"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("THIRD_NAME",list);
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("FOURTH_NAME")){
									if(individualDetails.get("FOURTH_NAME")!=null){
										individualDetails.get("FOURTH_NAME").add(subNodes.item(j).getTextContent());
										individualDetails.put("FOURTH_NAME",individualDetails.get("FOURTH_NAME"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("FOURTH_NAME",list);
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("AADHAR")){
									//System.out.println("AADHAR Number -- "+subNodes.item(j).getTextContent());
									if(individualDetails.get("AADHAR")!=null){
										individualDetails.get("AADHAR").add(subNodes.item(j).getTextContent());
										individualDetails.put("AADHAR",individualDetails.get("AADHAR"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("AADHAR",list);
									}
					              }else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PAN")){
									//System.out.println("PAN Number -- "+subNodes.item(j).getTextContent());
									if(individualDetails.get("PAN")!=null){
										individualDetails.get("PAN").add(subNodes.item(j).getTextContent());
										individualDetails.put("PAN",individualDetails.get("PAN"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("PAN",list);
									}
					              }else if(subNodes.item(j).getNodeName().equalsIgnoreCase("UN_LIST_TYPE")){
									if(individualDetails.get("UN_LIST_TYPE")!=null){
										individualDetails.get("UN_LIST_TYPE").add(subNodes.item(j).getTextContent());
										individualDetails.put("UN_LIST_TYPE",individualDetails.get("UN_LIST_TYPE"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(subNodes.item(j).getTextContent());
										individualDetails.put("UN_LIST_TYPE",list);
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("NATIONALITY")){
									for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("VALUE")){
											if(individualDetails.get("NATIONALITY")!=null){
												individualDetails.get("NATIONALITY").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("NATIONALITY",individualDetails.get("NATIONALITY"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("NATIONALITY",list);
											}
										}
									}
								}
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase(UN_ALIAS_TAG)){
									for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										Element elemnt = (Element) subNodes.item(j).getChildNodes();
										String quality=elemnt.getElementsByTagName("QUALITY").item(0).getTextContent();
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("ALIAS_NAME")){
											/*if(individualDetails.get(UN_ALIAS_TAG)!=null){
												individualDetails.get(UN_ALIAS_TAG).add(subNodes.item(j).getChildNodes().item(j2).getTextContent()+"##"+quality);
												individualDetails.put(UN_ALIAS_TAG,individualDetails.get(UN_ALIAS_TAG));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent()+"##"+quality);
												individualDetails.put("ALIAS_NAME",list);
											}*/
											if(individualDetails.get("ALIAS_NAME")==null)
											{
												ArrayList<String> list = new ArrayList<String>();
												individualDetails.put("ALIAS_NAME",list);
											}
											individualDetails.get("ALIAS_NAME").add(subNodes.item(j).getChildNodes().item(j2).getTextContent()+"##"+quality);
											individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
										}
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("DATE_OF_BIRTH")){
											Element elem = (Element) subNodes.item(j);
											if(elem.getElementsByTagName("DATE_OF_BIRTH")!=null && elem.getElementsByTagName("DATE_OF_BIRTH").getLength()>0){
												String dob = elem.getElementsByTagName("DATE_OF_BIRTH").item(0).getTextContent();
												if(dob.indexOf(" ")>-1){
													dob = dob.split(" ")[0];
												}
											if(individualDetails.get("ALIAS_DATE_OF_BIRTH")!=null){
												individualDetails.get("ALIAS_DATE_OF_BIRTH").add(dob);
												individualDetails.put("ALIAS_DATE_OF_BIRTH",individualDetails.get("ALIAS_DATE_OF_BIRTH"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(dob);
												individualDetails.put("ALIAS_DATE_OF_BIRTH",list);
											}
										}
									}
									}
								}
								else if(subNodes.item(j).getNodeName().equalsIgnoreCase("INDIVIDUAL_ADDRESS")){
									for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("STREET")){
											if(individualDetails.get("STREET")!=null){
												individualDetails.get("STREET").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("STREET",individualDetails.get("STREET"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("STREET",list);
											}
										}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("CITY")){
											if(individualDetails.get("CITY")!=null){
												individualDetails.get("CITY").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("CITY",individualDetails.get("CITY"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("CITY",list);
											}
										}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("COUNTRY")){
											if(individualDetails.get("COUNTRY")!=null){
												individualDetails.get("COUNTRY").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("COUNTRY",individualDetails.get("COUNTRY"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("COUNTRY",list);
											}
										}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("STATE_PROVINCE")){
											if(individualDetails.get("STATE_PROVINCE")!=null){
												individualDetails.get("STATE_PROVINCE").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("STATE_PROVINCE",individualDetails.get("STATE_PROVINCE"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("STATE_PROVINCE",list);
											}
										}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("ZIP_CODE")){
											if(individualDetails.get("ZIP_CODE")!=null){
												individualDetails.get("ZIP_CODE").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("ZIP_CODE",individualDetails.get("ZIP_CODE"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("ZIP_CODE",list);
											}
										}
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("INDIVIDUAL_DOCUMENT")){
									Element elem = (Element) subNodes.item(j);
									if((elem.getElementsByTagName("TYPE_OF_DOCUMENT")!=null && elem.getElementsByTagName("TYPE_OF_DOCUMENT").getLength()>0
										&&	elem.getElementsByTagName("TYPE_OF_DOCUMENT").item(0).getTextContent().toLowerCase().contains("passport"))
										|| (elem.getElementsByTagName("TYPE_OF_DOCUMENT2")!=null && elem.getElementsByTagName("TYPE_OF_DOCUMENT2").getLength()>0
												&&	elem.getElementsByTagName("TYPE_OF_DOCUMENT2").item(0).getTextContent().toLowerCase().contains("passport"))){
										if(elem.getElementsByTagName("NUMBER")!=null && elem.getElementsByTagName("NUMBER").getLength()>0){
											if(individualDetails.get("PASSPORT_NUMBER")!=null){
												individualDetails.get("PASSPORT_NUMBER").add(elem.getElementsByTagName("NUMBER").item(0).getTextContent());
												individualDetails.put("PASSPORT_NUMBER",individualDetails.get("PASSPORT_NUMBER"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(elem.getElementsByTagName("NUMBER").item(0).getTextContent());
												individualDetails.put("PASSPORT_NUMBER",list);
											}
										}
										if(elem.getElementsByTagName("ISSUING_COUNTRY")!=null && elem.getElementsByTagName("ISSUING_COUNTRY").getLength()>0){
											if(individualDetails.get("ISSUING_COUNTRY")!=null){
												individualDetails.get("ISSUING_COUNTRY").add(elem.getElementsByTagName("ISSUING_COUNTRY").item(0).getTextContent());
												individualDetails.put("ISSUING_COUNTRY",individualDetails.get("ISSUING_COUNTRY"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(elem.getElementsByTagName("ISSUING_COUNTRY").item(0).getTextContent());
												individualDetails.put("ISSUING_COUNTRY",list);
											}
										}
										if(elem.getElementsByTagName("COUNTRY_OF_ISSUE")!=null && elem.getElementsByTagName("COUNTRY_OF_ISSUE").getLength()>0){
											if(individualDetails.get("COUNTRY_OF_ISSUE")!=null){
												individualDetails.get("COUNTRY_OF_ISSUE").add(elem.getElementsByTagName("COUNTRY_OF_ISSUE").item(0).getTextContent());
												individualDetails.put("COUNTRY_OF_ISSUE",individualDetails.get("COUNTRY_OF_ISSUE"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(elem.getElementsByTagName("COUNTRY_OF_ISSUE").item(0).getTextContent());
												individualDetails.put("COUNTRY_OF_ISSUE",list);
											}
										}
									}
								}else if(subNodes.item(j).getNodeName().equalsIgnoreCase("INDIVIDUAL_DATE_OF_BIRTH")){
									Element elem = (Element) subNodes.item(j);
									if(elem.getElementsByTagName("TYPE_OF_DATE")!=null && elem.getElementsByTagName("TYPE_OF_DATE").getLength()>0
											&&	elem.getElementsByTagName("TYPE_OF_DATE").item(0).getTextContent().toLowerCase().contains("exact")){
										if(elem.getElementsByTagName("DATE")!=null && elem.getElementsByTagName("DATE").getLength()>0){
											String dob = elem.getElementsByTagName("DATE").item(0).getTextContent();
											if(dob.indexOf("T")>-1){
												dob = dob.split("T")[0];
											}
											if(individualDetails.get("DATE_OF_BIRTH")!=null){
												individualDetails.get("DATE_OF_BIRTH").add(dob);
												individualDetails.put("DATE_OF_BIRTH",individualDetails.get("DATE_OF_BIRTH"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(dob);
												individualDetails.put("DATE_OF_BIRTH",list);
											}
										}
									}
								}
				        		
							}
				        	
				        	//list_details.put(dataID, individualDetails);
				        	
				        	if(individualDetails != null && individualDetails.size() > 0){
				        	//ADDED BELOW CODE FOR FIXED DELTA SCREENING. DATE : 21-FEB-2017. ADDED BY : NAGARAJ B.
				        	if(oldhashMap == null){ // If DB have only one xml file,Execute BELOW CODE.
				        		list_details.put(dataID, individualDetails);
				        		//ConstantConfiguration.watchlistLogger.info("OLD HASH MAP DATA-----"+list_details); 
				        	}
				        	else{
				        		//COMAPRING CURRENT DATA ID AND OLD HASH MAP DATA ID.
				        		if(oldhashMap.containsKey(dataID)){
				        			if(!oldhashMap.containsValue(individualDetails)){
				        				list_details.put(dataID, individualDetails);
					        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
				        			}
				        			else{
					        			//IF CURRENT DATA ID = OLD HASH MAP DATA ID MATCHED AND VALUES ALSO MATCHED, THEN NOT REQUIRED TO SCREEN THIS CUSTOMER.
					        			oldhashMap.put(dataID, individualDetails);
					        			//ConstantConfiguration.watchlistLogger.info("NEW HASH MAP DATA-----"+oldhashMap); 
					        		}
				        		}			        			
				        		else{
				        			//IF CURRENT DATA ID != OLD HASHMAP DATA ID , THIS CUSTOMER NEED TO BE SCREEN.
				        			list_details.put(dataID, individualDetails);
				        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
				        		}
				        	}
				        	}
						}
				    	
			    	}
			    	//ConstantConfiguration.watchlistLogger.info("ofacListDetails data ind ==="+ofacListDetails);	
			    	
	        	}
			    	
		// un Entity	    	
			     if(doc.getElementsByTagName("ENTITY") != null)
				     {
			    	// ConstantConfiguration.watchlistLogger.info("enter into esle un==="); 
				    	NodeList nodesE = doc.getElementsByTagName("ENTITY");
				    	 HashMap<String, ArrayList<String>> individualDetails = null;
					    	if(nodesE!=null && nodesE.getLength()>0){
					    		int dataID = 0;
					    		//ofacListDetails = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
						    	for (int i = 0; i < nodesE.getLength(); i++) {
						    		individualDetails = new HashMap<String, ArrayList<String>>();
						    		ArrayList<String> entityTypeList = new ArrayList<String>();
						    		entityTypeList.add("ENTITY");
						    		individualDetails.put("ENTITY_TYPE",entityTypeList);
						        	NodeList subNodes = nodesE.item(i).getChildNodes();
						        	for (int j = 0; j < subNodes.getLength(); j++) {
										if(subNodes.item(j).getNodeName().equalsIgnoreCase("DATAID")){
											dataID = Integer.parseInt(subNodes.item(j).getTextContent());
										}
										else if(subNodes.item(j).getNodeName().equalsIgnoreCase("VERSIONNUM")){
											if(individualDetails.get("VERSIONNUMEntity")!=null){
												individualDetails.get("VERSIONNUMEntity").add(subNodes.item(j).getTextContent());
												individualDetails.put("VERSIONNUMEntity",individualDetails.get("VERSIONNUMEntity"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getTextContent());
												individualDetails.put("VERSIONNUMEntity",list);
											}	
								        }	
										else if(subNodes.item(j).getNodeName().equalsIgnoreCase("FIRST_NAME")){
											if(individualDetails.get(FIRST_NAME_TAG)!=null){
												individualDetails.get(FIRST_NAME_TAG).add(subNodes.item(j).getTextContent());
												individualDetails.put(FIRST_NAME_TAG,individualDetails.get(FIRST_NAME_TAG));	
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getTextContent());
												individualDetails.put(FIRST_NAME_TAG,list);
											}
										}
										else if(subNodes.item(j).getNodeName().equalsIgnoreCase("AADHAR")){
											//System.out.println("AADHAR Number -- "+subNodes.item(j).getTextContent());
											if(individualDetails.get("AADHAR")!=null){
												individualDetails.get("AADHAR").add(subNodes.item(j).getTextContent());
												individualDetails.put("AADHAR",individualDetails.get("AADHAR"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getTextContent());
												individualDetails.put("AADHAR",list);
											}
							              }else if(subNodes.item(j).getNodeName().equalsIgnoreCase("PAN")){
											//System.out.println("PAN Number -- "+subNodes.item(j).getTextContent());
											if(individualDetails.get("PAN")!=null){
												individualDetails.get("PAN").add(subNodes.item(j).getTextContent());
												individualDetails.put("PAN",individualDetails.get("PAN"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getTextContent());
												individualDetails.put("PAN",list);
											}
							              }
										/*else if(subNodes.item(j).getNodeName().equalsIgnoreCase("ENTITY_ALIAS")){
											for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
												if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("ENTITY_ALIAS")){
													if(individualDetails.get("ENTITY_ALIAS")!=null){
														individualDetails.get("ENTITY_ALIAS").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("ENTITY_ALIAS",individualDetails.get("ENTITY_ALIAS"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("ENTITY_ALIAS",list);
													}
												}
											}
										}	*/
										else if(subNodes.item(j).getNodeName().equalsIgnoreCase("ENTITY_ALIAS")){
											
											for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
												if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("ALIAS_NAME")){
													/*
													if(individualDetails.get("ALIAS_NAME")!=null){ //changed ENTITY_ALIAS to ALIAS_NAME Nagaraj 15/11/2016
														individualDetails.get("ALIAS_NAME").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														//individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("ALIAS_NAME",list);
													}
													*/
													if(individualDetails.get("ALIAS_NAME")==null)
													{
														ArrayList<String> list = new ArrayList<String>();
														individualDetails.put("ALIAS_NAME",list);
													}
													individualDetails.get("ALIAS_NAME").add(subNodes.item(j).getChildNodes().item(j2).getTextContent()+"##");
													individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
																								}
											}
										}
										else if(subNodes.item(j).getNodeName().equalsIgnoreCase("ENTITY_ADDRESS")){
											for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
												if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("STREET")){
													if(individualDetails.get("STREETEntity")!=null){
														individualDetails.get("STREETEntity").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("STREETEntity",individualDetails.get("STREETEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("STREETEntity",list);
													}
												}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("CITY")){
													if(individualDetails.get("CITYENtity")!=null){
														individualDetails.get("CITYENtity").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("CITYENtity",individualDetails.get("CITYENtity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("CITYENtity",list);
													}
												}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("COUNTRY")){
													if(individualDetails.get("COUNTRYEntity")!=null){
														individualDetails.get("COUNTRYEntity").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("COUNTRYEntity",individualDetails.get("COUNTRYEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("COUNTRYEntity",list);
													}
												}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("STATE_PROVINCE")){
													if(individualDetails.get("STATE_PROVINCEEntity")!=null){
														individualDetails.get("STATE_PROVINCEEntity").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("STATE_PROVINCEEntity",individualDetails.get("STATE_PROVINCEEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("STATE_PROVINCEEntity",list);
													}
												}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("ZIP_CODE")){
													if(individualDetails.get("ZIP_CODEEntity")!=null){
														individualDetails.get("ZIP_CODEEntity").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("ZIP_CODEEntity",individualDetails.get("ZIP_CODEEntity"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
														individualDetails.put("ZIP_CODEEntity",list);
													}
												}
											}
										}
						        	}
						        	
						        	//list_details.put(dataID, individualDetails);
						        	if(individualDetails != null && individualDetails.size() > 0){
						         	//ADDED BELOW CODE FOR FIXED DELTA SCREENING. DATE : 21-FEB-2017. ADDED BY : NAGARAJ B.
						        	if(oldhashMap == null){ // If DB have only one xml file,Execute BELOW CODE.
						        		list_details.put(dataID, individualDetails);
						        		//ConstantConfiguration.watchlistLogger.info("OLD HASH MAP DATA-----"+list_details); 
						        	}
						        	else{
						        		//COMAPRING CURRENT DATA ID AND OLD HASH MAP DATA ID.
						        		if(oldhashMap.containsKey(dataID)){
						        			if(!oldhashMap.containsValue(individualDetails)){
						        				list_details.put(dataID, individualDetails);
							        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
						        			}
						        			else{
							        			//IF CURRENT DATA ID = OLD HASH MAP DATA ID MATCHED AND VALUES ALSO MATCHED, THEN NOT REQUIRED TO SCREEN THIS CUSTOMER.
							        			oldhashMap.put(dataID, individualDetails);
							        			//ConstantConfiguration.watchlistLogger.info("NEW HASH MAP DATA-----"+oldhashMap); 
							        		}
						        		}			        			
						        		else{
						        			//IF CURRENT DATA ID != OLD HASHMAP DATA ID , THIS CUSTOMER NEED TO BE SCREEN.
						        			list_details.put(dataID, individualDetails);
						        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
						        		}
						        	}
						        	}
						    	}
						    	
					    	}
				    	 
				     } 
			    	
		    	}
	        	
	//ConstantConfiguration.watchlistLogger.info("undetails total un ==="+ofacListDetails);    	
	        	
	        }
		} catch (ParserConfigurationException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} catch (SAXException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}
		
//ConstantConfiguration.watchlistLogger.info("Total data ==="+ofacListDetails);    	
		return list_details;
	}
	/**
	 * This method fetches the last added list of list type from SANCTIONSLIST table and returns the XML String
	 * @return String
	 */
	public String getlistxml(String listType)
	{
		Statement st = null;
    	ResultSet rs = null;
    	String strQry =null;
    	String un_xml = "";
		try{
			if(conn == null || conn.isClosed()){
				conn = getConnection("jdbc/VIZProd");
			}
    		strQry = "select list_xml from sanctionslist where list_type='"+listType+"' and rownum<=2 order by updated_datetime desc";
    		st = conn.createStatement();
        	rs=st.executeQuery(strQry);
			while(rs.next()){
        		if(un_xml!=null&&un_xml.trim().length()>0){
        			un_xml += "##"+rs.getString(1);
        		}else{
        			un_xml = rs.getString(1);
        		}
        	}
		}	
    	catch (Exception e) {
    		un_xml = "";
    		logger.error("", e);
		}
    	finally{
    		try{
    			if(rs!=null){
    				rs.close();
    			}
    			if(st!=null){
    				st.close();
    			}
    		}catch(Exception e){
    			logger.error("", e);
    		}
        }
		return un_xml;
	}
	
	/**
	 * This method fetches the last added UN list from SANCTIONSLIST table and returns the XML String
	 * @return String
	 */
	public String getUNlistxml()
	{
		Statement st = null;
    	ResultSet rs = null;
    	String strQry =null;
    	String un_xml = "";
		try{
			if(conn == null || conn.isClosed()){
				conn = getConnection("jdbc/VIZProd");
			}
    		strQry = "select list_xml from sanctionslist where list_type='UN List' and rownum<=2 order by updated_datetime desc";
    		st = conn.createStatement();
        	rs=st.executeQuery(strQry);
			while(rs.next()){
        		if(un_xml!=null&&un_xml.trim().length()>0){
        			un_xml += "##"+rs.getString(1);
        		}else{
        			un_xml = rs.getString(1);
        		}
        	}
		}	
    	catch (Exception e) {
    		un_xml = "";
    		logger.error("", e);
		}
    	finally{
    		try{
    			if(rs!=null){
    				rs.close();
    			}
    			if(st!=null){
    				st.close();
    			}
    		}catch(Exception e){
    			logger.error("", e);
    		}
        }
		return un_xml;
	}
	/*
	public static synchronized  HashMap<String,String> getNextRecord()
	{
		HashMap<String,String> nextRecord = null;
		try {
			if(customerInfoResultSet==null || customerInfoResultSet.next() == false ){
				return nextRecord;
			}else{
				nextRecord = new HashMap<String,String>();
				nextRecord.put("firstName",customerInfoResultSet.getString(2));
				nextRecord.put("lastName",customerInfoResultSet.getString(3));
				nextRecord.put("middleName",customerInfoResultSet.getString(14));
				nextRecord.put("address1",customerInfoResultSet.getString(5));
				nextRecord.put("address2",customerInfoResultSet.getString(6));
				nextRecord.put("cityName",customerInfoResultSet.getString(9));
				nextRecord.put("state",customerInfoResultSet.getString(8));
				nextRecord.put("country",customerInfoResultSet.getString(12));
				nextRecord.put("zipCode",customerInfoResultSet.getString(10));
				nextRecord.put("socialSecurityNo",customerInfoResultSet.getString(13));
				nextRecord.put("aadhaar",customerInfoResultSet.getString(13));
				nextRecord.put("pan",customerInfoResultSet.getString(13));
				nextRecord.put("dob",customerInfoResultSet.getString(4));
				nextRecord.put("accountId",customerInfoResultSet.getString(1));
				nextRecord.put("entityType",customerInfoResultSet.getString(11));
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
		return nextRecord;
	}
	*/
	/*public static synchronized  ArrayList<HashMap<String,String>> getNextRecord()
	{
		ArrayList<HashMap<String,String>> recordList = null;
		HashMap<String,String> nextRecord = null;
		int index = 0;
		try {
			while(customerInfoResultSet!=null && customerInfoResultSet.next() == true && index < 250 ){
				
				nextRecord = new HashMap<String,String>();
				nextRecord.put("firstName",customerInfoResultSet.getString(2));
				nextRecord.put("lastName",customerInfoResultSet.getString(3));
				nextRecord.put("middleName",customerInfoResultSet.getString(14));
				nextRecord.put("address1",customerInfoResultSet.getString(5));
				nextRecord.put("address2",customerInfoResultSet.getString(6));
				nextRecord.put("cityName",customerInfoResultSet.getString(9));
				nextRecord.put("state",customerInfoResultSet.getString(8));
				nextRecord.put("country",customerInfoResultSet.getString(12));
				nextRecord.put("zipCode",customerInfoResultSet.getString(10));
				nextRecord.put("socialSecurityNo",customerInfoResultSet.getString(13));
				nextRecord.put("aadhaar",customerInfoResultSet.getString(13));
				nextRecord.put("pan",customerInfoResultSet.getString(13));
				nextRecord.put("dob",customerInfoResultSet.getString(4));
				nextRecord.put("accountId",customerInfoResultSet.getString(1));
				nextRecord.put("entityType",customerInfoResultSet.getString(11));
				
				if(recordList == null) recordList = new ArrayList<HashMap<String,String>>();
				recordList.add(nextRecord);
				index++;
			}
			if(customerInfoResultSet!=null  && customerInfoResultSet.next() == false){
				
					customerInfoResultSet.close();
					customerInfoResultSet = null;
					if(DatabaseAdaptor.customerInfoStatement != null){
						customerInfoStatement.close();
						customerInfoStatement = null;
					}
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
		return recordList;
	}*/
	
	public static synchronized  ArrayList<HashMap<String,String>> getNextRecord()
	{
		ArrayList<HashMap<String,String>> recordList = null;
		HashMap<String,String> nextRecord = null;
		int index = 0;
		//bug fix for number of records not being processed Nagaraj 1/18/2017
		//we were doing a customerInfoResultSet.next() additionally at the end of while loop i.e. after reading every 250 records
		//this would cause the cursor to move forward skipping 1 record for evry 250 records
		// now this is done only once by detecting the end of records
		Boolean endOfRecords = false; //added to establish the end of records 
		try {
			if(customerInfoResultSet!=null && customerInfoResultSet.next() == false ) endOfRecords = true;//this line added Nagaraj 1/18/2016
			//while(customerInfoResultSet!=null && customerInfoResultSet.next() == true && index < 250 ){ //this line changed to below one Nagaraj 1/18/2016
			
			while(customerInfoResultSet!=null && endOfRecords == false && index < 250 ){
				//ConstantConfiguration.watchlistLogger.info("Account id--"+customerInfoResultSet.getString(1));
				nextRecord = new HashMap<String,String>();
				nextRecord.put("firstName",customerInfoResultSet.getString(2));
				nextRecord.put("lastName",customerInfoResultSet.getString(3));
				nextRecord.put("middleName",customerInfoResultSet.getString(14));
				nextRecord.put("address1",customerInfoResultSet.getString(5));
				nextRecord.put("address2",customerInfoResultSet.getString(6));
				nextRecord.put("cityName",customerInfoResultSet.getString(9));
				nextRecord.put("state",customerInfoResultSet.getString(8));
				nextRecord.put("country",customerInfoResultSet.getString(12));
				nextRecord.put("zipCode",customerInfoResultSet.getString(10));
				nextRecord.put("socialSecurityNo",customerInfoResultSet.getString(13));
				nextRecord.put("aadhaar",customerInfoResultSet.getString(13));
				nextRecord.put("pan",customerInfoResultSet.getString(13));
				nextRecord.put("dob",customerInfoResultSet.getString(4));
				nextRecord.put("accountId",customerInfoResultSet.getString(1));
				nextRecord.put("entityType",customerInfoResultSet.getString(11));
				nextRecord.put("passport",customerInfoResultSet.getString(15));//Anand Added 31-AUG-2018
				
				if(recordList == null) recordList = new ArrayList<HashMap<String,String>>();
				recordList.add(nextRecord);
				index++;
				if(index<250)//Missing Customer Info insert into nextRecord HashMap. Date: 28-FEB-2017. 
				if(customerInfoResultSet!=null && customerInfoResultSet.next() == false ) endOfRecords = true; //progress the cursor and check if record present Nagaraj 1/18/2016
			   
			
			}
			//if(customerInfoResultSet!=null  && customerInfoResultSet.next() == false){
			if(customerInfoResultSet!=null  && endOfRecords == true){
					customerInfoResultSet.close();
					customerInfoResultSet = null;
					if(DatabaseAdaptor.customerInfoStatement != null){
						customerInfoStatement.close();
						customerInfoStatement = null;
					}
			}
		} catch (SQLException e) {
			logger.error("", e);
		}
		return recordList;
	}
	
	
	/**
	 * This method reads customer information from Customer_Profile table
	 * and returns a Vector of HashMaps with Customer Information for Offline Name Screening usage
	 * @param start
	 * @param range
	 * @return Vector CustomerInformation
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector getCustomerInfo(int start, int range){

    	String query =null;
    	Vector customerDetails = new Vector();
		try{
			if(conn == null || conn.isClosed()){
				conn = getConnection("jdbc/EnterpriseDB");
			}
			/*query = "select * from (select a.account_id, a.first_name, a.last_name, to_char(a.DATE_OF_BIRTH,'yyyy-MM-dd'), "
    				+ "b.address1, b.address2, b.address3, b.state, b.city_name, b.zipcode,a.cust_type,b.country, a.SOCIAL_SECURITY_NO ,a.middle_name,rownum rn  "
    				+ "from "+ConstantConfiguration.entUserName+".customer_profile a, "
    				+ ConstantConfiguration.entUserName+".address b where a.address=b.address_id )"
					+ "where rn>= "+start+" and rn<"+range;*/
			
		/*	query = "select a.account_id, a.first_name, a.last_name, to_char(a.DATE_OF_BIRTH,'yyyy-MM-dd'), "
    				+ "b.address1, b.address2, b.address3, b.state, b.city_name, b.zipcode,a.cust_type, b.country, a.SOCIAL_SECURITY_NO ,'' as middle_name "
    				+ "from "+ConstantConfiguration.entUserName+".customer_profile a, "
    				+ ConstantConfiguration.entUserName+".address b  where   "
					+ " rownum between "+start+" and "+range+" and a.address=b.address_id ORDER BY a.account_id ";*/
			
			/*	
			 	JFS new requirement, Secondary customers need to screen for OFFLINE screen.
				Secondary customers details fetching from CUSTOMER_PROFILE_EXTENSION table. 
				Primary customers details fetching from CUSTOMER_PROFILE table.
				Fixed By : Nagaraj B.
				Date : 16-03-2017.
			*/
			
			query = " select account_id, first_name, last_name, dob,  address1, address2, address3, state, city_name, zipcode, cust_type, country, "
					+" SOCIAL_SECURITY_NO, middle_name,passport_no,rownum rn from "
					+ " (select a.account_id, a.first_name, a.last_name, to_char(a.DATE_OF_BIRTH,'yyyy-MM-dd') as dob, "
    				+ " b.address1, b.address2, b.address3, b.state, b.city_name, b.zipcode,a.cust_type, b.country, a.SOCIAL_SECURITY_NO ,'' as middle_name,passport_no "
    				+ " from customer_profile a, "
    				+   " address b  where   "
					+ " a.address=b.address_id $0" //and rownum<=1000 and a.account_id='000000038090'
					+ " union all "
					+ " select a.account_id, a.first_name, a.last_name, to_char(a.DATE_OF_BIRTH,'yyyy-MM-dd') as dob, "
					+ "	b.address1, b.address2, b.address3, b.state, b.city_name, b.zipcode,a.cust_type, b.country, a.SOCIAL_SECURITY_NO ,'' as middle_name,passport_no "
					+ "	from CUSTOMER_PROFILE_EXTENSION a, "
					+   " address b  where a.address=b.address_id $0 )"; 
					
		  				 
			if(ConstantConfiguration.testingMode&&ConstantConfiguration.accountIds!=null&&ConstantConfiguration.accountIds.trim().length()>0){//&&ConstantConfiguration.accountIds.contains("'")
				query=query.replace("$0", " and account_id in ("+ConstantConfiguration.accountIds+")");
			}else{
				query=query.replace("$0", " ");
			}
					
			customerInfoStatement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//customerInfoStatement = conn.createStatement();
			customerInfoStatement.setFetchSize(1000);
    		//ConstantConfiguration.watchlistLogger.info("getCustomerInfo Query -- "+query);
			logger.info("getCustomerInfo Query -- "+query);
    		customerInfoResultSet=customerInfoStatement.executeQuery(query);
    		customerInfoResultSet.setFetchSize(1000);
        	
        	
			/*while(rs.next())
			{
				HashMap customerInfo = new HashMap();
				customerInfo.put("Account_id",rs.getString(1));
				customerInfo.put("First_Name",rs.getString(2));
				customerInfo.put("Last_Name",rs.getString(3));
				customerInfo.put("DATE_OF_BIRTH",rs.getString(4));
				customerInfo.put("address1",rs.getString(5));
				customerInfo.put("address2",rs.getString(6));
				customerInfo.put("address3",rs.getString(7));
				customerInfo.put("state",rs.getString(8));
				customerInfo.put("city_name",rs.getString(9));
				customerInfo.put("zipcode",rs.getString(10));
				customerInfo.put("cust_type",rs.getString(11));
				customerInfo.put("country",rs.getString(12));
				customerInfo.put("SOCIAL_SECURITY_NO",rs.getString(13));
				customerInfo.put("Middle_Name",rs.getString(14));
				customerDetails.add(customerInfo);
			}*/
		}	
    	catch (Exception e) {
    		logger.error("", e);
		}
		
		return customerDetails;
	}

	/** 
	 * Connect to DB2 database
	 */
	/*public static Connection getConnection() {
		try {	
			String url = ConstantConfiguration.url;
			Class.forName(ConstantConfiguration.driver);
			conn = DriverManager.getConnection(url, ConstantConfiguration.user, ConstantConfiguration.password);
		}catch (Exception e) {
			logger.error("", e);
		}
		return conn;
	}*/
	
	
	public Connection getConnection(String dbContext) {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup(dbContext);
			conn = ds.getConnection();
		} catch (NamingException ne) {
			logger.error("", ne);
		}

		catch (SQLException sqle) {
			logger.error("", sqle);
		} catch (Exception e) {
			logger.error("", e);
		}
		return conn;
	}
	
	
	public static ResultSet customerInfoResultSet = null;
	public static Statement customerInfoStatement = null;
	public static int processedCount = 0;
	public static int completedThreadCount = 0; 
	public static Queue matches = null;
	
	/**
	 * This method inserts the Matched Name Screening Information to respective tables based on Online/Offline screening
	 * @param reqid
	 * @param fieldmatched
	 * @param listname
	 * @param contentmatched
	 * @param score
	 * @param version
	 * @param sanctionID
	 * @param watchListMatch
	 * @param screeningType
	 * <br>&emsp;&emsp;0 - Online
	 * <br>&emsp;&emsp;1 - Offline
	 */
	
	
	public synchronized void updateResultsToDB(int screeningType) {
		PreparedStatement pstmt = null;
		String strQry = null;
		if(matches == null)
			return;
		if (screeningType == 0){
			strQry = "insert into "
					+ " SANCTIONSLISTSUMMARY_ONLINE "
					+ "(SD_REQUEST_ID,SFDC_FIELD_MATCHED,SFDC_CONTENT_MATCHED,SD_FIELD_MATCHED,SD_LIST_NAME,SD_CONTENT_MATCHED,"
					+ "SD_SCORE,SD_VERSION,SD_MATCHEDID,WATCHLIST_MATCH,REQUEST_DATE_TIME,TEXT_SCORE,PHONETIC_SCORE,CUSTOMER_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}else{
			strQry = "insert into "
					+" SANCTIONSLISTSUMMARY "
					+ "(SD_ACCOUNT_ID,SFDC_FIELD_MATCHED,SFDC_CONTENT_MATCHED,SD_FIELD_MATCHED,SD_LIST_NAME,SD_CONTENT_MATCHED,"
					+ "SD_SCORE,SD_VERSION,SD_MATCHEDID,WATCHLIST_MATCH,REQUEST_DATE_TIME,TEXT_SCORE,PHONETIC_SCORE,CUSTOMER_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}

		try {
			if (conn == null || conn.isClosed()) {
				conn = getConnection("jdbc/VIZProd");
			}
			pstmt = conn.prepareStatement(strQry);
			int recordCount = 0;
		//	System.out.println("matches.size in updateresults -- "+matches.size());
			while (matches.peek() != null) {
				HashMap<String, String> match = (HashMap<String, String>) matches.poll();
				pstmt.setString(1, match.get("RequestID"));
				pstmt.setString(2, match.get("SFDCFieldMatched"));
				pstmt.setString(3, match.get("SFDCContentMatched"));
				pstmt.setString(4, match.get("FieldMatched"));
				pstmt.setString(5, match.get("ListName"));
				pstmt.setString(6, match.get("ContentMatched"));
				pstmt.setString(7, match.get("Score"));
				pstmt.setString(8, match.get("Version"));
				pstmt.setString(9, match.get("SanctionID"));
				pstmt.setString(10, match.get("WatchListMatch"));
				pstmt.setString(11, match.get("RequestDateTime"));
				pstmt.setString(12, match.get("TextScore"));
				pstmt.setString(13, match.get("SoundexScore"));
				pstmt.setString(14, match.get("CustomerType"));
				recordCount++;
				pstmt.addBatch();

				if(recordCount % 1000 == 0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
			
			
			if(recordCount % 1000 != 0){
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
			//matches = null;
		//	System.out.println("RECORDS INSERTED TO DB -- "+recordCount);
		}

		catch (SQLException e) {
			//ConstantConfiguration.watchlistLogger.error(e.getMessage());
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
			//ConstantConfiguration.watchlistLogger.error(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				//ConstantConfiguration.watchlistLogger.error(e.getMessage());
				logger.error("", e);
			}
		}
	}
	public static synchronized void updateResultsToDB(int screeningType, ArrayList<HashMap<String,String>> matchList) {
		PreparedStatement pstmt = null;
		String strQry = null;
		if(matchList == null)
			return;
		if (screeningType == 0){
			strQry = "insert into "
					+ " SANCTIONSLISTSUMMARY_ONLINE "
					+ "(SD_REQUEST_ID,SFDC_FIELD_MATCHED,SFDC_CONTENT_MATCHED,SD_FIELD_MATCHED,SD_LIST_NAME,SD_CONTENT_MATCHED,"
					+ "SD_SCORE,SD_VERSION,SD_MATCHEDID,WATCHLIST_MATCH,REQUEST_DATE_TIME,TEXT_SCORE,PHONETIC_SCORE,CUSTOMER_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}else{
			strQry = "insert into "
					+ " SANCTIONSLISTSUMMARY "
					+ "(SD_ACCOUNT_ID,SFDC_FIELD_MATCHED,SFDC_CONTENT_MATCHED,SD_FIELD_MATCHED,SD_LIST_NAME,SD_CONTENT_MATCHED,"
					+ "SD_SCORE,SD_VERSION,SD_MATCHEDID,WATCHLIST_MATCH,REQUEST_DATE_TIME,TEXT_SCORE,PHONETIC_SCORE,CUSTOMER_TYPE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}

		try {
			if (updateConnection == null || updateConnection.isClosed()) {
				updateConnection = new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
			pstmt = updateConnection.prepareStatement(strQry);
			int recordCount = 0;
		//	System.out.println("matches.size in updateresults -- "+matches.size());
			for(int index=0;index < matchList.size(); index++) {
				HashMap<String, String> match = (HashMap<String, String>) matchList.get(index);
				pstmt.setString(1, match.get("RequestID"));
				pstmt.setString(2, match.get("SFDCFieldMatched"));
				pstmt.setString(3, match.get("SFDCContentMatched"));
				pstmt.setString(4, match.get("FieldMatched"));
				pstmt.setString(5, match.get("ListName"));
				pstmt.setString(6, match.get("ContentMatched"));
				pstmt.setString(7, match.get("Score"));
				pstmt.setString(8, match.get("Version"));
				pstmt.setString(9, match.get("SanctionID"));
				pstmt.setString(10, match.get("WatchListMatch"));
				pstmt.setString(11, match.get("RequestDateTime"));
				pstmt.setString(12, match.get("TextScore"));
				pstmt.setString(13, match.get("SoundexScore"));
				pstmt.setString(14, match.get("CustomerType"));
				
				recordCount++;
				pstmt.addBatch();

				if(recordCount % 1000 == 0){
					pstmt.executeBatch();
					pstmt.clearBatch();
				}
			}
			
			
			if(recordCount % 1000 != 0){
				pstmt.executeBatch();
				pstmt.clearBatch();
			}
			matchList.clear();
			matchList=null;//Added by Anand 10082018
			//matches = null;
		//	System.out.println("RECORDS INSERTED TO DB -- "+recordCount);
		}

		catch (SQLException e) {
			//ConstantConfiguration.watchlistLogger.error(e.getMessage());
			logger.error("", e);
		} catch (Exception e) {
			//ConstantConfiguration.watchlistLogger.error(e.getMessage());
			logger.error("", e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (Exception e) {
				//ConstantConfiguration.watchlistLogger.error(e.getMessage());
				logger.error("", e);
			}
		}
	}
	
	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/***
	 * Request Date Time conversion. Date: 28-FEB-2017. Added By: Nagaraj B.
	 */
	public String convertDateTOETCFormate(String dateFrmt) throws ParseException{
		
		String formattedDate = null;
		if(dateFrmt.contains("IST")&&dateFrmt.trim().length()>0){
			
			String monthName = dateFrmt.substring(4, 7);
			String dateOfMonth = dateFrmt.substring(8,10);
			String time = dateFrmt.substring(11,19);
			String year = dateFrmt.substring(24,28);
			
			//System.out.println("Month name--"+monthName+"--dateOfMonth--"+dateOfMonth+"--time--"+time+"-year--"+year);
			
			String strDate = dateOfMonth+'-'+monthName+'-'+year+' '+time;
					       
			DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			java.util.Date date = format.parse(strDate);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			formattedDate = df.format(date);
			logger.info(formattedDate);
		}
		
		return formattedDate;
	}
	
}