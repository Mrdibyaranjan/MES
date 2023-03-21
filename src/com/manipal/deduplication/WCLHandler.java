package com.manipal.deduplication;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WCLHandler extends DefaultHandler {


	public static HashMap<Integer, HashMap<String, ArrayList<String>>> oldhashMap;

    private WCLRecords  wclRecords = null;

    boolean bFirst_Name = false;
    boolean bLast_Name = false;
    boolean bAlias = false;
    boolean bAlternative_Spelling = false;
    boolean bDob= false;
    boolean bPassport= false;
    boolean bCountry= false;
    boolean bCity= false;
    boolean bState= false;
    boolean bAddress= false;
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("record")) {
            String uid = attributes.getValue("uid");
            String category = attributes.getValue("category");
            String subcategory = attributes.getValue("sub-category");
            
            wclRecords = new WCLRecords();
            wclRecords.setUid(Integer.parseInt(uid));
            wclRecords.setCategory(category);
            wclRecords.setSubcategory(subcategory);
            
            uid=null;
            category=null;
            subcategory=null;
            
        }else if (qName.equalsIgnoreCase("person")) {
        	String ssn = attributes.getValue("ssn");
        	String ei = attributes.getValue("e-i");
        	
        	wclRecords.setSsn(ssn);
        	wclRecords.setEi(ei);
        	
        	ssn=null;
        	ei=null;
        	 
        }else if (qName.equalsIgnoreCase("first_name")) {
            bFirst_Name = true;
        }else if (qName.equalsIgnoreCase("last_name")) {
        	bLast_Name = true;
        }else if (ConstantConfiguration.aliasNameLoad && qName.equalsIgnoreCase("alias")) {
        	bAlias = true;
        }else if (ConstantConfiguration.alternativeSpellingLoad && qName.equalsIgnoreCase("alternative_spelling")){
        	bAlternative_Spelling = true;
        }else if (qName.equalsIgnoreCase("dob")) {
        	bDob = true;
        }else if (qName.equalsIgnoreCase("country")) {
        	bCountry = true;
        }else if (qName.equalsIgnoreCase("location")) {
        	 String city = attributes.getValue("city");
             String state = attributes.getValue("state");
             String Country = attributes.getValue("country");
             
             if(city!=null&&city.trim().length()>0){
             if(wclRecords.getArrListCity()==null)
 			{
 				ArrayList<String> list = new ArrayList<String>();
 				list.add(city);
 				wclRecords.setArrListCity(list);
 			}else{
 				ArrayList<String> list = wclRecords.getArrListCity();
 				if(!list.contains(city))
 				list.add(city);
 				wclRecords.setArrListCity(list);
 			}
            }
             
             if(state!=null&&state.trim().length()>0){
             if(wclRecords.getArrListState()==null)
 			{
 				ArrayList<String> list = new ArrayList<String>();
 				list.add(state);
 				wclRecords.setArrListState(list);
 			}else{
 				ArrayList<String> list = wclRecords.getArrListState();
 				if(!list.contains(state))
 				list.add(state);
 				wclRecords.setArrListState(list);
 			}
            }
             
             if(Country!=null&&Country.trim().length()>0){
             if(wclRecords.getArrListCountries()==null)
  			{
  				ArrayList<String> list = new ArrayList<String>();
  				list.add(Country);
  				wclRecords.setArrListCountries(list);
  			}else{
  				ArrayList<String> list = wclRecords.getArrListCountries();
  				if(!list.contains(Country))
  				list.add(Country);
  				wclRecords.setArrListCountries(list);
  			}
            }
             
        	bAddress = true;
        	
        	city=null;
        	state=null;
        	Country=null;
        	
        }else if (qName.equalsIgnoreCase("passport")) {
        	bPassport = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	if (qName.equalsIgnoreCase("record")) {
    		
    		HashMap<String, ArrayList<String>> individualDetails = new HashMap<String, ArrayList<String>>();
    		
    		//System.out.println(wclRecords.getUid());
    		
    		
				ArrayList<String> list = new ArrayList<String>();
				
				list.add(wclRecords.getCategory());
				individualDetails.put("CATEGORY",list);
			
				list = new ArrayList<String>();
				list.add(wclRecords.getSubcategory());
				individualDetails.put("SUBCATEGORY",list);
			
				
				list = new ArrayList<String>();
				list.add(wclRecords.getSsn());
				individualDetails.put("SSN",list);
			
    	    	String ei=wclRecords.getEi();
	    		
	    		if(ei!=null&&ei.trim().equalsIgnoreCase("E")){
					ei="ENTITY";
				}else{
					ei="INDIVIDUAL";
				}
    		
    		    list = new ArrayList<String>();
				list.add(ei);
				individualDetails.put("ENTITY_TYPE",list);
			
				list = new ArrayList<String>();
				list.add(wclRecords.getFirst_name());
				individualDetails.put("FIRST_NAME",list);
    		
    		
				list = new ArrayList<String>();
				list.add(wclRecords.getLast_name());
				individualDetails.put("THIRD_NAME",list);
    		
				
				list = new ArrayList<String>();
				list.add(wclRecords.getDob());
				individualDetails.put("DATE_OF_BIRTH",list);
    		
				individualDetails.put("ALIAS_NAME",wclRecords.getArrListAliases());
		
				individualDetails.put("PASSPORT_NUMBER",wclRecords.getArrListPassports());
				
				individualDetails.put("ADDRESS",wclRecords.getArrListAddress());
				
				individualDetails.put("COUNTRY",wclRecords.getArrListCountries());
				
				individualDetails.put("CITY",wclRecords.getArrListCity());
				
				individualDetails.put("STATE_PROVINCE",wclRecords.getArrListState());
				
				
				
				if(individualDetails != null && individualDetails.size() > 0){
		        	if(oldhashMap == null){ 
		        		oldhashMap =  new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		        		oldhashMap.put(wclRecords.getUid(), individualDetails);
		        	}
		        	else{
		        		oldhashMap.put(wclRecords.getUid(), individualDetails);
		        		
		        		if(oldhashMap.size()%1000==0){
		        			System.out.println("Records Loaded :"+oldhashMap.size());
		        			ConstantConfiguration.watchlistLogger.info("Records Loaded :"+oldhashMap.size());
		        		}
			        }
				}

				wclRecords=null;
				
				list=null;
				ei=null;
				individualDetails=null;
			}
		}

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bFirst_Name) {
        	wclRecords.setFirst_name(new String(ch, start, length));
        	bFirst_Name = false;
        } else if (bLast_Name) {
        	wclRecords.setLast_name(new String(ch, start, length));
        	bLast_Name = false;
        } else if (bDob) {
        	wclRecords.setDob(new String(ch, start, length));
        	bDob = false;
        } else if (bAlias) {
        	if(wclRecords.getArrListAliases()==null)
			{
				ArrayList<String> list = new ArrayList<String>();
				String alias= new String(ch, start, length);
				
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
				list.add(alias);
				wclRecords.setArrListAliases(list);
				list=null;
			}else{
				ArrayList<String> list = wclRecords.getArrListAliases();
				String alias= new String(ch, start, length);
				
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
						lastName=null;
						firstName=null;
					}
                }
                else{
                	alias+="##";
                }
				
				if(!list.contains(alias))
				list.add(alias);
				wclRecords.setArrListAliases(list);
				list=null;
				alias=null;
			}
        	bAlias = false;
        } else if (bAlternative_Spelling) {
        	if(wclRecords.getArrListAliases()==null)
			{
        		ArrayList<String> list = new ArrayList<String>();
        		String alternative_spelling= new String(ch, start, length);
        		
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
    								lastName=null;
    								firstName=null;
    							}
    		                }
    		                else{
    		                	alternativeSpellingName=arryAlternateSpelling[a];
    		                	alternativeSpellingName+="##";
    		                }
			    		    
							list.add(alternativeSpellingName);
							wclRecords.setArrListAliases(list);
							alternativeSpellingName=null;
							arryAlternateSpelling=null;
							alternative_spelling=null;
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
								lastName=null;
								firstName=null;
							}
		                }
		                else{
		                	alternative_spelling+="##";
		                }
		    		    
		    		    list.add(alternative_spelling);
						wclRecords.setArrListAliases(list);
						list=null;
						alternative_spelling=null;
		    	 }
			}else{
				ArrayList<String> list = wclRecords.getArrListAliases();
				String alternative_spelling= new String(ch, start, length);
        		
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
   								lastName=null;
								firstName=null;
   							}
   		                }
   		                else{
   		                	alternativeSpellingName=arryAlternateSpelling[a];
   		                	alternativeSpellingName+="##";
   		                }
		    			 if(!list.contains(alternativeSpellingName))
							list.add(alternativeSpellingName);
							wclRecords.setArrListAliases(list);
							alternativeSpellingName=null;
							alternative_spelling=null;
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
								lastName=null;
								firstName=null;
							}
		                }
		                else{
		                	alternative_spelling+="##";
		                }
		    		    
		    		    list.add(alternative_spelling);
						wclRecords.setArrListAliases(list);
						list=null;
						alternative_spelling=null;
		    	 }
			}
        	
        	bAlternative_Spelling = false;
        } else if (bPassport) {
        	
        	String passport = new String(ch, start, length);
        	
        	if(passport!=null)
        			passport=passport.replaceAll("\\s+","");
        	 
        	if(wclRecords.getArrListPassports()==null)
			{
				ArrayList<String> list = new ArrayList<String>();
				
				list.add(passport);
				wclRecords.setArrListPassports(list);
				list=null;
				passport=null;
			}else{
				ArrayList<String> list = wclRecords.getArrListPassports();
				list.add(passport);
				wclRecords.setArrListPassports(list);
				list=null;
				passport=null;
			}
        	
        	bPassport = false;
        }else if (bAddress) {
        	
        	String location = new String(ch, start, length);
        	
        	if(wclRecords.getArrListAddress()==null)
			{
				ArrayList<String> list = new ArrayList<String>();
				
				if(location.contains(",")){
		       		 String address[]=location.split(",");
		       		 
				for(int i4=0;i4<address.length;i4++){
					list.add(address[i4]);
				}
				wclRecords.setArrListAddress(list);
				list=null;
				location=null;
			}else{
				if(location.trim().length()>0){
				list.add(location);
				wclRecords.setArrListAddress(list);
				list=null;
				location=null;
				}
			}
			}else{
				
				ArrayList<String> list = wclRecords.getArrListAddress();
				
				if(location.contains(",")){
		       		 String address[]=location.split(",");
		       		 
				for(int i4=0;i4<address.length;i4++){
					list.add(address[i4]);
				}
				wclRecords.setArrListAddress(list);
				list=null;
				location=null;
			}else{
				if(location.trim().length()>0){
				list.add(location);
				wclRecords.setArrListAddress(list);
				list=null;
				location=null;
				}
			}
				
			}
       	 	
       	 
        	bAddress = false;
        } else if (bCountry) {
        	
        	String country = new String(ch, start, length);
        	
        	if(wclRecords.getArrListCountries()==null)
			{
				ArrayList<String> list = new ArrayList<String>();
				
				list.add(country);
				wclRecords.setArrListCountries(list);
				list=null;
				country=null;
			}else{
				ArrayList<String> list = wclRecords.getArrListCountries();
				if(!list.contains(country))
					list.add(country);
				wclRecords.setArrListCountries(list);
				list=null;
				country=null;
			}
        	
        	bCountry = false;
        }
        
        
    }
}
