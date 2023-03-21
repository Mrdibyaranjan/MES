package com.manipal.deduplication;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.manipal.deduplication.DatabaseAdaptor;

//import mqReader.ConstantConfiguration;
//import mqReader.DatabaseAdaptor;
//import mqReader.NameScreening;

public class WorldCheckListUtility implements Serializable{

	
	public static HashMap<String, ArrayList<String>> addOrUpdateListInMap(HashMap<String, ArrayList<String>> map, String key, String value){
		if(map.get(key)!=null){
			if(!(map.get(key).contains(value)))
				map.get(key).add(value);
			map.put(key, map.get(key));
		}else{
			ArrayList<String> list = new ArrayList<String>();
			list.add(value);
			map.put(key, list);
		}
		return map;
	}
	
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> parseWorldCheckXMLDOM(String fileNameOrData,HashMap<Integer, HashMap<String, ArrayList<String>>> oldhashMap,boolean incrementalFlag){
		
		System.out.println("incrementalFlag=="+incrementalFlag);
		//HashMap<Integer, HashMap<String, ArrayList<String>>> list_details =  new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		HashMap<String, ArrayList<String>> individualDetails = null;
		
	    		try {
	    			
		    		
		    		Document doc = null;
		    		
		    		if(incrementalFlag){
		    			doc = convertStringToDocument(fileNameOrData);
		    		}else{
		    		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    		DocumentBuilder dBuilder;
	    			dBuilder = dbFactory.newDocumentBuilder();
	    			File file = new File(fileNameOrData);
	    			doc = dBuilder.parse(file);
		    		}
	    			doc.getDocumentElement().normalize();
	    			System.out.println("record=="+doc.getElementsByTagName("record"));
	    			
	    			if(doc.getElementsByTagName("record") != null)	{
	    				NodeList nodes = doc.getElementsByTagName("record");
	    				
	    			
	    				//System.out.println("nodes=="+nodes.getLength());
	    				
			    	if(nodes!=null && nodes.getLength()>0){
			    		//ofacListDetails = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
				    	for (int i = 0; i < nodes.getLength(); i++) {
				    		
				    		individualDetails = new HashMap<String, ArrayList<String>>();
				    		
				    		int uid = Integer.parseInt(((Element)doc.getElementsByTagName("record").item(i)).getAttribute("uid"));
				    		String category = ((Element)doc.getElementsByTagName("record").item(i)).getAttribute("category");
				    		String subcategory = ((Element)doc.getElementsByTagName("record").item(i)).getAttribute("sub-category");
				    		
				    		//System.out.println("uid=="+uid);
				    		//System.out.println("category=="+category);
				    		//System.out.println("subcategory=="+subcategory);
				    		
				    		if(individualDetails.get("CATEGORY")!=null){
								individualDetails.get("CATEGORY").add(category);
								individualDetails.put("CATEGORY",individualDetails.get("CATEGORY"));
							}else{
								ArrayList<String> list = new ArrayList<String>();
								list.add(category);
								individualDetails.put("CATEGORY",list);
							}
				    		
				    		if(individualDetails.get("SUBCATEGORY")!=null){
								individualDetails.get("SUBCATEGORY").add(subcategory);
								individualDetails.put("SUBCATEGORY",individualDetails.get("SUBCATEGORY"));
							}else{
								ArrayList<String> list = new ArrayList<String>();
								list.add(subcategory);
								individualDetails.put("SUBCATEGORY",list);
							}
			    			
				        	NodeList subNodes = nodes.item(i).getChildNodes();
				        	
				        	for (int j = 0; j < subNodes.getLength(); j++) {
				        		if(subNodes.item(j).getNodeName().equalsIgnoreCase("person")){
				        			
				        			String ssn= ((Element)doc.getElementsByTagName("person").item(i)).getAttribute("ssn");
				        			String ei=((Element)doc.getElementsByTagName("person").item(i)).getAttribute("e-i");
				        			
				        			if(ei!=null&&ei.trim().equalsIgnoreCase("E")){
				        				ei="ENTITY";
				        			}else{
				        				ei="INDIVIDUAL";
				        			}
				        			
				        			//System.out.println("ssn=="+ssn);
				        			//System.out.println("ei=="+ei);

						    		if(individualDetails.get("SSN")!=null){
										individualDetails.get("SSN").add(ssn);
										individualDetails.put("SSN",individualDetails.get("SSN"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(ssn);
										individualDetails.put("SSN",list);
									}
						    		if(individualDetails.get("ENTITY_TYPE")!=null){
										individualDetails.get("ENTITY_TYPE").add(ei);
										individualDetails.put("ENTITY_TYPE",individualDetails.get("EI"));
									}else{
										ArrayList<String> list = new ArrayList<String>();
										list.add(ei);
										individualDetails.put("ENTITY_TYPE",list);
									}
				        			
				        			for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
										if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("position")){
											if(individualDetails.get("POSITION")!=null){
												individualDetails.get("POSITION").add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("POSITION",individualDetails.get("POSITION"));
											}else{
												ArrayList<String> list = new ArrayList<String>();
												list.add(subNodes.item(j).getChildNodes().item(j2).getTextContent());
												individualDetails.put("POSITION",list);
											}
										}else if (subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("names")){
											
											     Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
											     if(elem.getElementsByTagName("first_name")!=null && elem.getElementsByTagName("first_name").getLength()>0){
											    	 String first_name = elem.getElementsByTagName("first_name").item(0).getTextContent();
											     
											    		if(individualDetails.get("FIRST_NAME")!=null){
															individualDetails.get("FIRST_NAME").add(first_name);
															individualDetails.put("FIRST_NAME",individualDetails.get("FIRST_NAME"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(first_name);
															individualDetails.put("FIRST_NAME",list);
														}
											     }
											     if(elem.getElementsByTagName("last_name")!=null && elem.getElementsByTagName("last_name").getLength()>0){
											    	 String last_name = elem.getElementsByTagName("last_name").item(0).getTextContent();
											    	 if(individualDetails.get("THIRD_NAME")!=null){
															individualDetails.get("THIRD_NAME").add(last_name);
															individualDetails.put("THIRD_NAME",individualDetails.get("THIRD_NAME"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(last_name);
															individualDetails.put("THIRD_NAME",list);
														}
											     }		 
											    		 
											     if(elem.getElementsByTagName("aliases")!=null && elem.getElementsByTagName("aliases").getLength()>0){
											    	 NodeList aliasesNodeList = elem.getElementsByTagName("aliases");
											    	 
											    	 if(aliasesNodeList != null && aliasesNodeList.getLength() > 0) {  
											    		    for(int i1 = 0 ; i1 < aliasesNodeList.getLength();i1++) {  
											    		        Element aliasesElement = (Element)aliasesNodeList.item(i1);  
											    		        NodeList aliasesNameNodeList = aliasesElement.getElementsByTagName("alias");   
											    		        if (aliasesNameNodeList != null && aliasesNameNodeList.getLength() > 0) {  
											    		            for (int j1 = 0; j1 < aliasesNameNodeList.getLength(); j1++) {  
											    		                Element aliasesName = (Element)aliasesNameNodeList.item(j1);  
											    		                if(aliasesName!=null&&aliasesName.getFirstChild()!=null){
											    		                String alias = aliasesName.getFirstChild().getNodeValue();
											    		                
											    		                if(alias!=null&&alias.contains(",")){
											    		                	alias=alias.replaceAll(",","##");
											    		                	if(alias.indexOf("##")>-1)
											    							{
											    								String[] aliasNames = alias.split("##");
											    								String lastName ="";
											    								String firstName = "";
											    								
											    								if(aliasNames != null && aliasNames.length >0)
											    									lastName = aliasNames[0];
											    								if(aliasNames != null && aliasNames.length >=2)
											    									firstName = aliasNames[1];
											    								
											    								alias = firstName+"##"+lastName;
											    								
											    							}
											    		                	
											    		                }
											    		                else{
											    		                	alias+="##";
											    		                }
											    		                
											    		            	if(individualDetails.get("ALIAS_NAME")==null)
																		{
																			ArrayList<String> list = new ArrayList<String>();
																			individualDetails.put("ALIAS_NAME",list);
																		}
																		individualDetails.get("ALIAS_NAME").add(alias);
																		individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
											    		            }  
											    		            }
											    		        }  
											    		    }  
											    		}
											     }
											     
											     if(elem.getElementsByTagName("alternative_spelling")!=null && elem.getElementsByTagName("alternative_spelling").getLength()>0){
											    	 String alternative_spelling = elem.getElementsByTagName("alternative_spelling").item(0).getTextContent();
											    	 
											    	 
											    	/* if(individualDetails.get("ALTERNATIVE_SPELLING")!=null){
															individualDetails.get("ALTERNATIVE_SPELLING").add(alternative_spelling);
															individualDetails.put("ALTERNATIVE_SPELLING",individualDetails.get("ALTERNATIVE_SPELLING"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(alternative_spelling);
															individualDetails.put("ALTERNATIVE_SPELLING",list);
														}*/
											    	 
											    	 //alternative_spelling mapped to Alias Name
											    	 
											    	 if(alternative_spelling!=null&&alternative_spelling.contains(";")){
											    		 
											    		 String arryAlternateSpelling[]=alternative_spelling.split(";");
											    		 String  alternativeSpellingName="";
											    		 for(int a=0;a<arryAlternateSpelling.length;a++){
											    			 
											    			 if(arryAlternateSpelling[a]!=null&&arryAlternateSpelling[a].contains(",")){
												    		    	
											    				 alternativeSpellingName=arryAlternateSpelling[a].replaceAll(",","##");
									    		                	if(alternativeSpellingName.indexOf("##")>-1)
									    							{
									    								String[] aliasNames = alternativeSpellingName.split("##");
									    								String lastName ="";
									    								String firstName = "";
									    								
									    								if(aliasNames != null && aliasNames.length >0)
									    									lastName = aliasNames[0];
									    								if(aliasNames != null && aliasNames.length >=2)
									    									firstName = aliasNames[1];
									    								
									    								alternativeSpellingName = firstName+"##"+lastName;
									    							}
									    		                }
									    		                else{
									    		                	alternativeSpellingName=arryAlternateSpelling[a];
									    		                	alternativeSpellingName+="##";
									    		                }
												    		    
												    		    if(individualDetails.get("ALIAS_NAME")==null)
																{
																	ArrayList<String> list = new ArrayList<String>();
																	individualDetails.put("ALIAS_NAME",list);
																}
																individualDetails.get("ALIAS_NAME").add(alternativeSpellingName);
																individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
											    			 
											    		 }
											    		 
											    	 }else{
											    		 
											    		    if(alternative_spelling!=null&&alternative_spelling.contains(",")){
											    		    	
											    		    	alternative_spelling=alternative_spelling.replaceAll(",","##");
								    		                	if(alternative_spelling.indexOf("##")>-1)
								    							{
								    								String[] aliasNames = alternative_spelling.split("##");
								    								String lastName ="";
								    								String firstName = "";
								    								
								    								if(aliasNames != null && aliasNames.length >0)
								    									lastName = aliasNames[0];
								    								if(aliasNames != null && aliasNames.length >=2)
								    									firstName = aliasNames[1];
								    								
								    								alternative_spelling = firstName+"##"+lastName;
								    							}
								    		                }
								    		                else{
								    		                	alternative_spelling+="##";
								    		                }
											    		    
											    		    if(individualDetails.get("ALIAS_NAME")==null)
															{
																ArrayList<String> list = new ArrayList<String>();
																individualDetails.put("ALIAS_NAME",list);
															}
															individualDetails.get("ALIAS_NAME").add(alternative_spelling);
															individualDetails.put("ALIAS_NAME",individualDetails.get("ALIAS_NAME"));
											    	 }
											     
											     }	
										}else if (subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("agedata")){
											 Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
										     if(elem.getElementsByTagName("dob")!=null && elem.getElementsByTagName("dob").getLength()>0){
										    	 String dob = elem.getElementsByTagName("dob").item(0).getTextContent();
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
				        		else if(subNodes.item(j).getNodeName().equalsIgnoreCase("details")){
				        			
				        			for (int j2 = 0; j2 < subNodes.item(j).getChildNodes().getLength(); j2++) {
				        				
				        			if (subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("passports")){
										     Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
							    		      NodeList passportNameNodeList = elem.getElementsByTagName("passport");   
							    		        if (passportNameNodeList != null && passportNameNodeList.getLength() > 0) {  
							    		            for (int j1 = 0; j1 < passportNameNodeList.getLength(); j1++) {  
							    		                Element passportName = (Element)passportNameNodeList.item(j1);  
							    		                if(passportName!=null&&passportName.getFirstChild()!=null){
							    		                String passport = passportName.getFirstChild().getNodeValue();
							    		                passport=passport.replaceAll("\\s+","");
							    		                if(individualDetails.get("PASSPORT_NUMBER")!=null){
															individualDetails.get("PASSPORT_NUMBER").add(passport);
															individualDetails.put("PASSPORT_NUMBER",individualDetails.get("PASSPORT_NUMBER"));
														}else{
															ArrayList<String> list = new ArrayList<String>();
															list.add(passport);
															individualDetails.put("PASSPORT_NUMBER",list);
														}
							    		                }
										    		}
							    		        }
				        				}else if (subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("countries")){
									     Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
						    		      NodeList countryNameNodeList = elem.getElementsByTagName("country");   
						    		        if (countryNameNodeList != null && countryNameNodeList.getLength() > 0) {  
						    		            for (int j1 = 0; j1 < countryNameNodeList.getLength(); j1++) {  
						    		                Element countryName = (Element)countryNameNodeList.item(j1); 
						    		                if(countryName!=null&&countryName.getFirstChild()!=null){
						    		                String country = countryName.getFirstChild().getNodeValue();
						    		                if(individualDetails.get("COUNTRY")!=null){
														individualDetails.get("COUNTRY").add(country);
														individualDetails.put("COUNTRY",individualDetails.get("COUNTRY"));
													}else{
														ArrayList<String> list = new ArrayList<String>();
														list.add(country);
														individualDetails.put("COUNTRY",list);
													}
						    		                }
									    		}
						    		        }
			        				}else if(subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("place_of_birth")){
											String place_of_birth = subNodes.item(j).getChildNodes().item(j2).getTextContent();
											 if(individualDetails.get("PLACE_OF_BIRTH")!=null){
													individualDetails.get("PLACE_OF_BIRTH").add(place_of_birth);
													individualDetails.put("PLACE_OF_BIRTH",individualDetails.get("PLACE_OF_BIRTH"));
												}else{
													ArrayList<String> list = new ArrayList<String>();
													list.add(place_of_birth);
													individualDetails.put("PLACE_OF_BIRTH",list);
												}
											
									}else if (subNodes.item(j).getChildNodes().item(j2).getNodeName().equalsIgnoreCase("locations")){
									     Element elem = (Element) subNodes.item(j).getChildNodes().item(j2);
						    		      NodeList locationNameNodeList = elem.getElementsByTagName("location");   
						    		        if (locationNameNodeList != null && locationNameNodeList.getLength() > 0) {  
						    		            for (int j1 = 0; j1 < locationNameNodeList.getLength(); j1++) {  
						    		                Element locationName = (Element)locationNameNodeList.item(j1);
						    		                
						    		                if(locationName.getAttribute("country") != null && locationName.getAttribute("country").trim().length() > 0){
						    		                	 String country=locationName.getAttribute("country").trim();
						    		                	 
						    		                	 if(individualDetails.get("COUNTRY")!=null){
																individualDetails.get("COUNTRY").add(country);
																individualDetails.put("COUNTRY",individualDetails.get("COUNTRY"));
															}else{
																ArrayList<String> list = new ArrayList<String>();
																list.add(country);
																individualDetails.put("COUNTRY",list);
															}
						    		                }
						    		                if(locationName.getAttribute("city") != null && locationName.getAttribute("city").trim().length() > 0){
						    		                	String city= locationName.getAttribute("city"); 
						    		                	 
						    		                	 if(individualDetails.get("CITY")!=null){
																individualDetails.get("CITY").add(city);
																individualDetails.put("CITY",individualDetails.get("CITY"));
															}else{
																ArrayList<String> list = new ArrayList<String>();
																list.add(city);
																individualDetails.put("CITY",list);
															}
						    		                }
						    		                if(locationName.getAttribute("state") != null && locationName.getAttribute("state").trim().length() > 0){
						    		                	String state = locationName.getAttribute("state"); 
						    		                	 
						    		                	 if(individualDetails.get("STATE_PROVINCE")!=null){
																individualDetails.get("STATE_PROVINCE").add(state);
																individualDetails.put("STATE_PROVINCE",individualDetails.get("STATE_PROVINCE"));
															}else{
																ArrayList<String> list = new ArrayList<String>();
																list.add(state);
																individualDetails.put("STATE_PROVINCE",list);
															}
						    		                }
						    		                
						    		                if(locationName!=null&&locationName.getFirstChild()!=null){
						    		                	String location = locationName.getFirstChild().getNodeValue();
						    		                	 if(location.contains(",")){
						    		                		 String address[]=location.split(",");
						    		                		 for(int i4=0;i4<address.length;i4++){
						    		                		 if(individualDetails.get("ADDRESS")!=null){
																	individualDetails.get("ADDRESS").add(address[i4]);
																	individualDetails.put("ADDRESS",individualDetails.get("ADDRESS"));
																}else{
																	ArrayList<String> list = new ArrayList<String>();
																	list.add(address[i4]);
																	individualDetails.put("ADDRESS",list);
																}
						    		                		 }
						    		                	 }else{
						    		                		 if(individualDetails.get("ADDRESS")!=null){
																	individualDetails.get("ADDRESS").add(location);
																	individualDetails.put("ADDRESS",individualDetails.get("ADDRESS"));
																}else{
																	ArrayList<String> list = new ArrayList<String>();
																	list.add(location);
																	individualDetails.put("ADDRESS",list);
																}
						    		                	 }
						    		                }
									    		}
						    		        }
			        				}
				        			
				        			
				        			}
				        			
								}
				        		/*
				        		if(individualDetails != null && individualDetails.size() > 0){
						        	//ADDED BELOW CODE FOR FIXED DELTA SCREENING. DATE : 21-FEB-2017. ADDED BY : NAGARAJ B.
						        	if(oldhashMap == null){ // If DB have only one xml file,Execute BELOW CODE.
						        		list_details.put(uid, individualDetails);
						        		//ConstantConfiguration.watchlistLogger.info("OLD HASH MAP DATA-----"+list_details); 
						        	}
						        	else{
						        		//COMAPRING CURRENT DATA ID AND OLD HASH MAP DATA ID.
						        		if(oldhashMap.containsKey(uid)){
						        			if(!oldhashMap.containsValue(individualDetails)){
						        				list_details.put(uid, individualDetails);
						        				System.out.println("uid 000==="+uid);
							        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
						        			}
						        			else{
							        			//IF CURRENT DATA ID = OLD HASH MAP DATA ID MATCHED AND VALUES ALSO MATCHED, THEN NOT REQUIRED TO SCREEN THIS CUSTOMER.
							        			oldhashMap.put(uid, individualDetails);
							        			System.out.println("uid 111==="+uid);
							        			//ConstantConfiguration.watchlistLogger.info("NEW HASH MAP DATA-----"+oldhashMap); 
							        		}
						        		}			        			
						        		else{
						        			//IF CURRENT DATA ID != OLD HASHMAP DATA ID , THIS CUSTOMER NEED TO BE SCREEN.
						        			list_details.put(uid, individualDetails);
						        			System.out.println("uid 222==="+uid);
						        			//ConstantConfiguration.watchlistLogger.info("FINAL HASH MAP DATA-----"+list_details);
						        		}
						        	}
						        	}
				        		*/
				        	}
				        		if(individualDetails != null && individualDetails.size() > 0){
						        	if(oldhashMap == null){ 
						        		oldhashMap =  new HashMap<Integer, HashMap<String,ArrayList<String>>>();
						        		oldhashMap.put(uid, individualDetails);
						        		System.out.println("uid 000==="+uid);
						        	}
						        	else{
						        		oldhashMap.put(uid, individualDetails);
							        	System.out.println("uid 111==="+uid);
							        }
							}
						}
				    	
			    	}
	    			}
	    			}catch (ParserConfigurationException e) {
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			} catch (SAXException e) {
	    				e.printStackTrace();
	    				e.printStackTrace();
	    			} catch (Exception e){
	    				e.printStackTrace();
	    			}
	    
    	
		return oldhashMap;
	}
	

public  static ArrayList<Integer> parseWorldCheckDeletionXML(String xmlDeletion){
		
		 ArrayList<Integer> arrlistDeleteUids = new ArrayList<Integer>();
		
	    		try {
	    				Document doc = convertStringToDocument(xmlDeletion);
	    						
	    				doc.getDocumentElement().normalize();

	    				NodeList nList = doc.getElementsByTagName("profile");

	    				for (int temp = 0; temp < nList.getLength(); temp++) {

	    					Node nNode = nList.item(temp);
	    					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	    						Element eElement = (Element) nNode;
	    						arrlistDeleteUids.add(Integer.parseInt(eElement.getElementsByTagName("uid").item(0).getTextContent()));
	    						//System.out.println("uid : " + eElement.getElementsByTagName("uid").item(0).getTextContent());
	    						//System.out.println("Date : " + eElement.getElementsByTagName("date").item(0).getTextContent());
	    					}
	    				}
	    		} catch (Exception e){
	    				e.printStackTrace();
	    			}
	    
    	
		return arrlistDeleteUids;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//readFileandParse();
		
		WorldCheckListUtility wcl_obj=new WorldCheckListUtility();
		String filepath=System.getProperty("user.dir")+"\\WCL\\world-check-day.xml";
		HashMap<Integer, HashMap<String,ArrayList<String>>> worldchecklist_individual_details=wcl_obj.parseWorldCheckXML(filepath,null,false);
		System.out.println("iiii===="+worldchecklist_individual_details.size());
		System.out.println("get=="+worldchecklist_individual_details.get(275));
		System.out.println("get=="+worldchecklist_individual_details.get(277));
		storeFeedToTable(object2Byte((Object)worldchecklist_individual_details));
		
		//HashMap<Integer, String> retrievedHashMap = (HashMap<Integer, String>)byte2Object(fetchFeedFromTable());
		//System.out.println("iiii===="+retrievedHashMap.size());
		System.out.println("Main 111==="+worldchecklist_individual_details.size());
		//wcl_obj.getDeletedIncrementalFiles(worldchecklist_individual_details,worldchecklist_individual_details);
		System.out.println("Main 222==="+worldchecklist_individual_details.size());
		
	//	wcl_obj.getAddModifyIncrementalFiles(worldchecklist_individual_details);
		System.out.println("Main 333==="+worldchecklist_individual_details.size());
		
	}
	
	
	
	public static void readFileandParse() {

		String strXML="",strXMLComplete="";
		try {
			FileReader fr = new FileReader(System.getProperty("user.dir")+"\\WCL\\world-check-day.xml");
			System.out.println("fr=="+fr);
			BufferedReader in = new BufferedReader(fr);
			while ((strXML = in.readLine()) != null) {
				strXMLComplete+=strXML;
				System.out.println(strXML);
			}
			in.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(strXMLComplete);

	}
	
	public static Connection conn = null;
	
	public static byte[] fetchFeedFromTable(){
		Statement st = null;
    	ResultSet rs = null;
    	String strQry =null;
    	byte[] bytes = null;
		try{
			if(conn == null){
				conn =new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
    		strQry = "SELECT FEED_DATA FROM WORLDCHECKLIST_FEEDS WHERE FEED_TYPE = 'Full Feed'  and SNO =(select max(SNO) from  WORLDCHECKLIST_FEEDS WHERE FEED_TYPE = 'Full Feed') and rownum<2 order by UPDATED_DATE desc";
    		st = conn.createStatement();
    		rs = st.executeQuery(strQry);
    		if(rs.next()){
    			bytes = rs.getBytes(1);
    		}
		}	
		catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
	    }
		return bytes;
	}
	
	public static void storeFeedToTable(byte[] bytes){
		PreparedStatement ps = null;
    	ResultSet rs = null;
    	String strQry =null;
		try{
			if(conn == null){
				conn =new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
    		strQry = "INSERT INTO WORLDCHECKLIST_FEEDS (SNO, FEED_TYPE, UPDATED_DATE, FEED_DATA,STATUS,UPLOADED_BY,FILE_NAME) VALUES (?, ?, ?, ?, ?, ? , ?) ";
    		ps = conn.prepareStatement(strQry);
    		ps.setInt(1, getSerialNumber());
    		ps.setString(2, "Full Feed");
    		ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
    		ps.setBytes(4, bytes);
    		ps.setString(5, "Added");
    		ps.setString(6, "CrossFraud");
    		ps.setString(7, "Initial File");
			ps.executeUpdate();
			
			ConstantConfiguration.watchlistLogger.info("Inserted into WORLDCHECKLIST_FEEDS");
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
			}catch(Exception e){
				//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
				e.printStackTrace();
			}
	    }
	}
	public static Object byte2Object(byte[] byteArray) {
		ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
		ObjectInput in = null;
		Object o = null;
		try {
			GZIPInputStream gzipIn = new GZIPInputStream(bis);
			System.out.println("In Stream ...");
			in = new ObjectInputStream(gzipIn);
			o = in.readObject();
			System.out.println("Return Object ...");
		} catch (IOException e) {
			//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
				e.printStackTrace();
			}
		}
		return o;
	}
	
	public static byte[] object2Byte(Object obj) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut = null;
		ObjectOutputStream out = null;
		byte[] byteArray = null;
		try {
			gzipOut = new GZIPOutputStream(bos);
			System.out.println("object2Byte");
			out = new ObjectOutputStream(gzipOut);
			System.out.println("Out Stream...");
			out.writeObject(obj);
			System.out.println("Write object to Stream...");
			out.flush();
			out.close();
			System.out.println("Flush out stream...");
			byteArray = bos.toByteArray();
			System.out.println("Return Byte ...");
		} catch (IOException e) {
			//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
			e.printStackTrace();
		} finally {
			try {
				//gzipOut.flush();
				gzipOut.close();
				//gzipOut.finish();
				//bos.flush();
				bos.close();
			} catch (IOException e) {
				//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
				e.printStackTrace();
			}
		}
		return byteArray;
	}
	/*public static Connection getConnection() {
		try {	
			String url = "jdbc:oracle:thin:@172.16.16.9:1521:CFESAF";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, "VIZPROD", "VIZPROD");
		}catch (Exception e) {
			ConstantConfiguration.watchlistLogger.error(e);
		}
		return conn;
	}*/
	public static int getSerialNumber(){
		PreparedStatement ps = null;
		Statement st = null;
		ResultSet rs = null;
		
		int serialNo = 0;
		try {
			if(conn == null){
				conn= new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
			st = conn.createStatement();

			rs = st.executeQuery("select WORLDCHECKLIST_FEEDS_SEQ.nextval from dual");
			while (rs.next()) {
				serialNo = rs.getInt(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
		return serialNo;
		}
	
	
	public synchronized int getDeletedIncrementalFiles(HashMap<Integer, HashMap<String,ArrayList<String>>> worldchecklist_individual_details,HashMap<Integer, HashMap<String,ArrayList<String>>> worldchecklist_individual_details_delta){
		ArrayList<Integer> arrlistDeletedUids = null;
		Statement st = null;
		Statement stUpdate = null;
    	ResultSet rs = null;
    	String strQry =null;
    	String strSerialNo="";
    	int cnt=0;
		try{
			if(conn == null){
				conn=new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
    		strQry = "SELECT SNO,FEED_DATA FROM WORLDCHECKLIST_FEEDS WHERE FEED_TYPE = 'Deletion'  and status='Pending'";
    		st = conn.createStatement();
    		rs = st.executeQuery(strQry);
    		while(rs.next()){
    			strSerialNo +=rs.getInt(1)+",";
    			String xmlDeletion=(String)byte2Object(rs.getBytes(2));
    			arrlistDeletedUids=parseWorldCheckDeletionXML(xmlDeletion);
    			//Get ids and Delete from e
    			//System.out.println("strSerialNo=="+strSerialNo);
    			//System.out.println("xmlDeletion=="+xmlDeletion);
    		}
    		
    		if(arrlistDeletedUids!=null){
    		System.out.println(arrlistDeletedUids.size());
    		for(int i=0;i<arrlistDeletedUids.size();i++){
    			//System.out.println("Remove UID=="+arrlistDeletedUids.get(i));
    			if(worldchecklist_individual_details!=null)
    			worldchecklist_individual_details.remove(arrlistDeletedUids.get(i));
    			if(worldchecklist_individual_details_delta!=null)
    			worldchecklist_individual_details_delta.remove(arrlistDeletedUids.get(i));
    		}
    		
    		if(strSerialNo!=null&&!strSerialNo.equals("")){
    		if(conn == null){
				conn=new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
    		if(strSerialNo.length()>1)
    			strSerialNo=strSerialNo.substring(0,strSerialNo.length()-1);
    		
    		ConstantConfiguration.watchlistLogger.info("SerialNo Deletions=="+strSerialNo);
    		
    		strQry="Update WORLDCHECKLIST_FEEDS set status='Completed' where SNO in ("+strSerialNo+")";
    		stUpdate = conn.createStatement();
    		cnt=stUpdate.executeUpdate(strQry);
    		ConstantConfiguration.watchlistLogger.info("Deletion Updated Record Counts="+cnt);
    		}
    		}
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(st!=null){
					st.close();
				}
				if(stUpdate!=null){
					stUpdate.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
		return cnt;
	}
	
	
	public synchronized int getAddModifyIncrementalFiles(HashMap<Integer, HashMap<String,ArrayList<String>>> hmworldchecklist_individual_details_delta){
		Statement st = null;
		Statement stUpdate = null;
    	ResultSet rs = null;
    	String strQry =null;
    	String strSerialNo="";
    	int i=0;
		try{
			if(conn == null){
				conn=new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
    		strQry = "SELECT SNO,FEED_DATA FROM WORLDCHECKLIST_FEEDS WHERE FEED_TYPE = 'Addition'  and status='Pending'";
    		st = conn.createStatement();
    		rs = st.executeQuery(strQry);
    		while(rs.next()){
    			strSerialNo +=rs.getInt(1)+",";
    			String xmlAddModify=(String)byte2Object(rs.getBytes(2));
    			parseWorldCheckXML(xmlAddModify,hmworldchecklist_individual_details_delta,true);
    			//System.out.println("strSerialNo=="+strSerialNo);
    			//System.out.println("xmlAddModify=="+xmlAddModify);
    		}
    		//System.out.println("get=="+worldchecklist_individual_details.get(275));
    		//System.out.println("get=="+worldchecklist_individual_details.get(277));
    		if(hmworldchecklist_individual_details_delta!=null)
    		System.out.println("worldchecklist_individual_details=="+hmworldchecklist_individual_details_delta.size());
    		
    		if(strSerialNo!=null&&!strSerialNo.equals("")){
    		if(conn == null){
				conn=new DatabaseAdaptor().getConnection("jdbc/VIZProd");
			}
    		if(strSerialNo.length()>1)
    			strSerialNo=strSerialNo.substring(0,strSerialNo.length()-1);
    		
    		ConstantConfiguration.watchlistLogger.info("SerialNo Additions=="+strSerialNo);
    		
    		strQry="Update WORLDCHECKLIST_FEEDS set status='Completed' where SNO in ("+strSerialNo+")";
    		stUpdate = conn.createStatement();
    		i=stUpdate.executeUpdate(strQry);
    		ConstantConfiguration.watchlistLogger.info("Addtion Updated Record Counts="+i);
    		}
    		
		}	
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(st!=null){
					st.close();
				}
				if(stUpdate!=null){
					stUpdate.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	    }
		return i;
	}
	
	private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
    }

public static HashMap<Integer, HashMap<String,ArrayList<String>>> parseWorldCheckXML(String fileNameOrData,HashMap<Integer, HashMap<String, ArrayList<String>>> oldhashMap,boolean incrementalFlag){
		
		System.out.println("incrementalFlag=="+incrementalFlag);
		
	    		try {
	    				SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    		        SAXParser saxParser = saxParserFactory.newSAXParser();
	    		        WCLHandler handler = new WCLHandler();
	    		        WCLHandler.oldhashMap=oldhashMap;
	    		        
	    		        if(incrementalFlag){
	    		        	 InputSource is = new InputSource(new StringReader(fileNameOrData));
			    			 saxParser.parse(is, handler);
			    		}else{
	    		        saxParser.parse(new File(fileNameOrData), handler);
			    		}
	    		        System.out.println("Total Records Loaded:"+WCLHandler.oldhashMap.size());
	    		       //System.out.println( object2Byte(WCLHandler.oldhashMap).length/1024);
	    		        ConstantConfiguration.watchlistLogger.info("Total Records Loaded:"+WCLHandler.oldhashMap.size());
	    		        //ConstantConfiguration.watchlistLogger.info("Size In MB:"+object2Byte(WCLHandler.oldhashMap).length/1024);
	    		}catch (ParserConfigurationException e) {
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			} catch (SAXException e) {
	    				e.printStackTrace();
	    				e.printStackTrace();
	    			} catch (Exception e){
	    				e.printStackTrace();
	    			}
    	
		return WCLHandler.oldhashMap;
	}
}
