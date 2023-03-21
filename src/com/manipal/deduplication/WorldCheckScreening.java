package com.manipal.deduplication;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.manipal.deduplication.DatabaseAdaptor;

public class WorldCheckScreening extends Thread{
	private volatile HashMap<Integer, HashMap<String, String>> sanctionsInfoOfWCList = null;
	String firstName = null;
	String middleName = null;
	String lastName = null;
	String streetName = null;
	String doorNo = null;
	String city = null;
	String state = null;
	String country = null;
	String zipCode = null;
	String passportNumber = null;
	String aadharNumber = null;
	String panNumber = null;
	String dateOfBirth = null;
	String reqid = null;
	int screeningMode = 0;
	String requestDateTime = null;
	String entityType = null;
	HashMap<Integer, HashMap<String, ArrayList<String>>> wcDetails = null;

	public WorldCheckScreening(String firstName, String middleName, String lastName, String streetName, 
			String doorNo, String city, String state, String country, String zipCode, String passportNumber, 
			String aadharNumber, String panNumber, String dateOfBirth, String reqid, int screeningMode, 
			String requestDateTime, String entityType,HashMap<Integer, HashMap<String, ArrayList<String>>> wcDetails){
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.streetName = streetName;
		this.doorNo = doorNo;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
		this.passportNumber = passportNumber;
		this.aadharNumber = aadharNumber;
		this.panNumber = panNumber;
		this.dateOfBirth = dateOfBirth;
		this.reqid = reqid;
		this.screeningMode = screeningMode;
		this.requestDateTime = requestDateTime;
		this.entityType = entityType;
		this.wcDetails = wcDetails;
	}
	
	@Override
	public void run() {
			sanctionsInfoOfWCList = getScreeningInfoAgainstWCList(firstName,middleName, lastName, streetName, 
					doorNo, city, state, country, zipCode, passportNumber, aadharNumber,panNumber,
					dateOfBirth, reqid, 0,requestDateTime,entityType, wcDetails);	
	}
	
	public HashMap<Integer, HashMap<String, String>> getWCSanctionsInfo() {
	    return sanctionsInfoOfWCList;
	}
	
	public ArrayList<HashMap<String,String>> getMatches() {
	    return matches;
	}
	
	public ArrayList<HashMap<String,String>> matches = null;
	
	public void insertToSLS(String reqid, String sfdcfieldmatched,
			String sfdccontentmatched, String fieldmatched, String listname,
			String contentmatched, String score, String version,
			String sanctionID, String watchListMatch, int screeningType,
			String requestDateTime, String textScore, String soundexScore,
			String customerType) {//String Percentage
		
		
		if(listname != null && listname.equalsIgnoreCase("WC List")){
			listname = "WC List";
		}
		
		if(matches == null){
			matches = new ArrayList<HashMap<String,String>>();
		}
		//ConstantConfiguration.watchlistLogger.info("requestDateTime-----"+requestDateTime);
		HashMap<String,String> matchedRecord = new HashMap<String,String>();
		try {
			DatabaseAdaptor adapter_obj = new DatabaseAdaptor();
			if(requestDateTime.contains("IST")&&requestDateTime.trim().length()>0){
				requestDateTime = adapter_obj.convertDateTOETCFormate(requestDateTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		matchedRecord.put("RequestID", reqid);
		matchedRecord.put("SFDCFieldMatched", sfdcfieldmatched);
		matchedRecord.put("SFDCContentMatched", sfdccontentmatched);
		matchedRecord.put("FieldMatched", fieldmatched);
		matchedRecord.put("ListName", listname);
		matchedRecord.put("ContentMatched", contentmatched);
		matchedRecord.put("Score", score);
		matchedRecord.put("Version", version);
		matchedRecord.put("SanctionID", sanctionID);
		matchedRecord.put("WatchListMatch", watchListMatch);
		matchedRecord.put("RequestDateTime", requestDateTime);
		matchedRecord.put("TextScore", textScore);
		matchedRecord.put("SoundexScore", soundexScore);
		matchedRecord.put("CustomerType", customerType);
		matchedRecord.put("ScreeningType", Integer.toString(screeningType));
		//matchedRecord.put("Percentage", Percentage);
		//System.out.println("putting to matches");
		//synchronized (matches) {
			matches.add(matchedRecord);
		//}
		//System.out.print(" -- "+matches.size());
	}

	
	public HashMap<Integer, HashMap<String, String>> getScreeningInfoAgainstWCList(String firstName,String middleName, String lastName,
			String streetName, String doorNo, String city, String state, String country, String zipCode,
			String passportNumber, String aadharNumber, String panNumber,String dateOfBirth,
			String requestID, int screeningMode,String reuestDateTime,String entityType,
			HashMap<Integer, HashMap<String, ArrayList<String>>> wcl_individual_details) {
		
			Dedupe dedupeObj = new Dedupe();
			DecimalFormat df = new DecimalFormat("0.00");
		    String versionNo="";
		    int dataID=0;
	        HashMap<String, String> individualSanctionInfo=null;
	        Utilities utilityObj = new Utilities();
		    HashMap<Integer, HashMap<String, String>> sanctionsInfo = new HashMap<Integer, HashMap<String, String>>();
		
		 //System.out.println("Customer Full Name -- "+firstName +" -- "+ middleName+" -- "+lastName+"\n\n");
		
		if(firstName == null){
			firstName = "";
		}
		if(middleName == null){
			middleName = "";
		}
		if(lastName == null){
			lastName = "";
		}
		if(dateOfBirth == null){
			dateOfBirth = "";
		}
		if(streetName == null){
			streetName = "";
		}
		if(passportNumber == null){
			passportNumber = ""; 
		}
		if(doorNo == null){
			doorNo = "";
		}
		//ConstantConfiguration.watchlistLogger.info("screeningMode===Screening"+screeningMode);
		//ConstantConfiguration.watchlistLogger.info("wcl_individual_details===Screening"+wcl_individual_details.size());
		//ConstantConfiguration.watchlistLogger.info("firstName==="+firstName);
		//ConstantConfiguration.watchlistLogger.info("lastName==="+lastName);
		
		        //ConstantConfiguration.watchlistLogger.info("screeningMode===Screening"+screeningMode);
		        //ConstantConfiguration.watchlistLogger.info("worldchecklist===Screening"+worldchecklist.size());
		        
		        if(wcl_individual_details!=null){
		        	
		         double maxFirstnameScore=0;
		         double maxMiddlenameScore=0;
		         double maxLastnameScore=0;
		         double maxAliasnameScore=0;
		         
		         boolean isValidDOB = dedupeObj.isValidDOB(dateOfBirth);

		         
		        for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : wcl_individual_details.entrySet()) {
		          dataID = entry.getKey();
				  HashMap<String, ArrayList<String>> wclIndividual = entry.getValue();
				    
		         individualSanctionInfo = new HashMap<String, String>();
		         double f1score = 0,f2score=0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0,aliasDobscore=0;
		         double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,finalScore = 0,alfscore=0,almscore=0,allscore=0;//aliasScore=0, 
		         //String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null,aliasTypefirst = null;
		         //String addr2 = null,dob = null,otherID = null,alf=null,alm=null,all=null,aliasDob=null,aliasTypemiddle = null,aliasTypelast = null;   
		         
		         String addr1 = null,addr2 = null,dob = null,otherID = null,aliasDob=null;
		         //double f1lvScore=0,f1SoundexScore=0,f2lvScore=0,f2SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0,m2lvScore=0,m2SoundexScore=0;
		         //double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l2lvScore=0,l2SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
		         //double alflvScore=0,alfSoundexScore=0,almlvScore=0,almSoundexScore=0,alllvScore=0,allSoundexScore=0,alf1Score=0,all1Score=0,alm1Score=0;
		       
		         double maxaliasScore=0;
		       //changes for new name screening algo Nagaraj 2016
			     
			       // ConstantConfiguration.watchlistLogger.info("un entity=="+unIndividual);       
		         
		        /**/ 
		         
		        
		         //Only Screen FN(0) or LN(0) Matches with fn(0) or ln(0)
		         boolean skipFlag=false;
		        
		        if(ConstantConfiguration.skipFNLNNotMatch){
		        	skipFlag=true;
		        if( (!firstName.equals("") && wclIndividual.get("FIRST_NAME").get(0).toLowerCase().startsWith(firstName.toLowerCase().charAt(0)+"")) ||
		        	(!lastName.equals("") && wclIndividual.get("THIRD_NAME").get(0).toLowerCase().startsWith(lastName.toLowerCase().charAt(0)+""))||
		        	(!firstName.equals("") && wclIndividual.get("THIRD_NAME").get(0).toLowerCase().startsWith(firstName.toLowerCase().charAt(0)+""))||
		        	(!lastName.equals("") && wclIndividual.get("FIRST_NAME").get(0).toLowerCase().startsWith(lastName.toLowerCase().charAt(0)+""))
		        ){
		        	skipFlag=false;
		        }
		        }
		        //if(skipFlag==false)
		        //ConstantConfiguration.watchlistLogger.info("skipFlag--"+skipFlag + "----firstName:"+firstName + "----lastName:"+lastName+"----LIST FIRST_NAME:"+wclIndividual.get("FIRST_NAME").get(0).toLowerCase()+"-----------LIST THIRD_NAME:"+wclIndividual.get("THIRD_NAME").get(0).toLowerCase());
		      
		         if(entityType.equalsIgnoreCase("1"))//For Individuals  No Need to screen with Entity List
		 		{
		        	 if( wclIndividual.get("ENTITY_TYPE") == null || (wclIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY")) || skipFlag ) continue;
		 		}else{//For Non Individuals No Need to screen with Individuals List
			         if( wclIndividual.get("ENTITY_TYPE") == null || (!wclIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))|| skipFlag) continue;
		 		}
		         
			        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(wclIndividual);
			        
			       // ConstantConfiguration.watchlistLogger.info("Corporate sanctionedEntityNameList for UN==="+sanctionedEntityNameList);
			       
			       /* System.out.println("firstName==="+firstName);
			        System.out.println("middleName==="+middleName);
			        System.out.println("lastName==="+lastName);
			        System.out.println("Account==="+requestID);*/
			        
					if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
					NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
					
				     // ConstantConfiguration.watchlistLogger.info("matchedName UN==="+matchedName);
					
					//if( matchedName == null) continue; //bug. Nagaraj 16/11/2016 
					
					//end of changes for new name screening algo Nagaraj 2016
					/*
			          System.out.println("streetName==================="+streetName);
			          System.out.println("country==================="+country);
			          System.out.println("state==================="+state);
			          System.out.println("city==================="+city);*/
		        		          
					if(streetName!=null && streetName.trim()!="" && wclIndividual.get("ADDRESS") != null){
				          if(country!=null && country.trim()!="" && wclIndividual.get("COUNTRY") != null){
				        	  if(Double.parseDouble(dedupeObj.refinedSoundexCheckForAddress(wclIndividual.get("COUNTRY"), country).split("##")[0]) > 0.8){
					        	  if(state!=null && state.trim()!="" && wclIndividual.get("STATE_PROVINCE") != null){ 
					        		  if(Double.parseDouble(dedupeObj.refinedSoundexCheckForAddress(wclIndividual.get("STATE_PROVINCE"), state).split("##")[0]) > 0.8){
						        		  if(city!=null && city.trim()!="" && wclIndividual.get("CITY") != null){
					    	        		  if(Double.parseDouble(dedupeObj.refinedSoundexCheckForAddress(wclIndividual.get("CITY"), city).split("##")[0]) > 0.8){
							        			  if(dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
							        		     	  addr1 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
							        			      String st2=dedupeObj.getMatchedContent(wclIndividual.get("CITY"), city);
							        				  String st3=dedupeObj.getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
							        				  String st4=dedupeObj.getMatchedContent(wclIndividual.get("COUNTRY"), country);
							        				 
							        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
							        				  addr1Score = 0.75;
							        			  }
							        			  if(dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
							        				  addr2 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
							        		          String st2=dedupeObj.getMatchedContent(wclIndividual.get("CITY"), city);
							        				  String st3=dedupeObj.getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
							        				  String st4=dedupeObj.getMatchedContent(wclIndividual.get("COUNTRY"), country);
							        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
							            			  addr2Score = 0.25;
							            		  }
					    	        		  }
						        		  }else{
						        			  if(dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
						        				  addr1 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
						        				  String st2=dedupeObj.getMatchedContent(wclIndividual.get("CITY"), city);
						        				  String st3=dedupeObj.getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
						        				  addr1=addr1+","+st2+" \n"+st3;
						        				  addr1Score = 0.75/2;
						        			  }
						        			  if(dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
						        				  addr2 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
						        				  String st2=dedupeObj.getMatchedContent(wclIndividual.get("CITY"), city);
						        				  String st3=dedupeObj.getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
						        				  addr2=addr2+","+st2+" \n"+st3;
						        				  addr2Score = 0.25/2;
						            		  }
						        		  }
					        		  }
					        	  }else{
					        		  if(dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
					        			  addr1 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
					        			  String st2=dedupeObj.getMatchedContent(wclIndividual.get("COUNTRY"), country);
					        			  addr1=addr1+","+st2;
					        			  addr1Score = 0.75/3;
				        			  }
				        			  if(doorNo!=null && doorNo.trim()!="" && 
				        					  dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
				        				  addr2 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
				        				  String st2=dedupeObj.getMatchedContent(wclIndividual.get("COUNTRY"), country);
				        				  addr2=addr2+","+st2;
				            			  addr2Score = 0.25/3;
				            		  }
					        	  }
				        	  }
				          }else{
				        	  if(dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
				        		  addr1 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
				        		  addr1Score = 0.75/4;
		       			  }
		       			  if(doorNo!=null && doorNo.trim()!="" && 
		       					dedupeObj.containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
		       				  addr2 = dedupeObj.getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
		       				  addr2Score = 0.25/4;
		           		  }
				          }
			          }
					  if(isValidDOB){//Anand 11-OCT-2018 Moved outside of for loop, it takes more CPU.No need to check for each record  
			          if(wclIndividual.get("DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
			        		  dedupeObj.containsCaseInsensitive(wclIndividual.get("DATE_OF_BIRTH"), dateOfBirth)){
			        	  dob = dedupeObj.getMatchedContent(wclIndividual.get("DATE_OF_BIRTH"), dateOfBirth);
			        	  dobScore = 1;
			        	  versionNo="-";
			          }
			          if(wclIndividual.get("ALIAS_DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
			        		  dedupeObj.containsCaseInsensitive(wclIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth)){
			        	  aliasDob = dedupeObj.getMatchedContent(wclIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth);
			        	  aliasDobscore = 1;
			        	  versionNo="-";
			          }
		         }
		         if(wclIndividual.get("PASSPORT_NUMBER") != null && passportNumber != null && passportNumber.trim() != "" && 
		        		 dedupeObj.containsCaseInsensitive(wclIndividual.get("PASSPORT_NUMBER"), passportNumber)){
		       	  otherID = dedupeObj.getMatchedContent(wclIndividual.get("PASSPORT_NUMBER"), passportNumber);
		       	  otherIDScore = 1;
		       	  versionNo="-";
		         }
		       
		         //Start: Changes  for new name screening algo Nagaraj 2016
		         if( matchedName != null)
		         {
					for(NameScore partNameScore : matchedName.getMatch_scores())
					{
						double weightedScore = 0.0;
						if(partNameScore.getField_name().equalsIgnoreCase("FIRST_NAME"))
						{
							f1score = partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
			                //f1 = partNameScore.getScreened_entity_value();
			                //f1lvScore=partNameScore.getMatch_score();
			                //f1SoundexScore=partNameScore.getMatch_score();
			                weightedScore = f1score;
						}
						else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
						{
							l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
			          		//l3lvScore=partNameScore.getMatch_score();
			          		//l3SoundexScore=partNameScore.getMatch_score();
			          		//l1 = partNameScore.getScreened_entity_value();
			          		weightedScore = l1score;
						}
						else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
						{
							m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
			          		//m3lvScore=partNameScore.getMatch_score();
			          		//m3SoundexScore=partNameScore.getMatch_score();
			          		//m1 = partNameScore.getScreened_entity_value();
			          		weightedScore = m1score;
						}
						//ConstantConfiguration.watchlistLogger.info("Found part name Match --- "+requestID+
							//	"Field "+partNameScore.getField_name()+ " "+
							//	 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
						individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
						if(weightedScore>ConstantConfiguration.FNThreshold)
						{
						insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "WC List", partNameScore.sanctioned_entity_value, 
								df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"",entityType);
						}
						}
		         }
					// End changes for new name screening algo Nagaraj 2016
			     
		         	    f1score=f1score*ConstantConfiguration.FFWeightage; 
		         	    f2score=f2score*ConstantConfiguration.FMWeightage; 
		         	    f3score=f3score*ConstantConfiguration.FLWeightage; 
		         	    m1score=m1score*ConstantConfiguration.MFWeightage; 
		         	    m2score=m2score*ConstantConfiguration.MMWeightage; 
		         	    m3score=m3score*ConstantConfiguration.MLWeightage; 
		         	    l1score=l1score*ConstantConfiguration.LFWeightage; 
		         	    l2score=l2score*ConstantConfiguration.LMWeightage; 
		         	    l3score=l3score*ConstantConfiguration.LLWeightage; 
		         	 
		         	  
			         maxFirstnameScore = Math.max( f1score, Math.max( f2score, f3score ) );
			         maxMiddlenameScore = Math.max( m1score ,Math.max( m2score, m3score )  );
			         maxLastnameScore = Math.max( l1score,  Math.max(l2score, l3score )  );
			         maxaliasScore=Math.max( alfscore , Math.max( almscore , allscore) );
			         
			  	        if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
			  	       { /**
			  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
			  	        	 */
			  	        	if(firstName!=null && middleName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("") && maxAliasnameScore<=0.7 )   
			  	        	{
			  	        		if(maxFirstnameScore==1 || maxFirstnameScore==0.9)
			  	        		{
			  	        		 finalScore = (maxFirstnameScore * ConstantConfiguration.FNWeightage);	
			  	        		 finalScore = finalScore+(1 * ConstantConfiguration.LFNWeightage);
			  	        		}
			  	        		else
			  	        		{
			  	        		 finalScore = finalScore+(maxFirstnameScore * ConstantConfiguration.FNWeightage);
			  	        		}
			  	        	}
			  	        	else 
			  	        	{ 
			  	        		finalScore = finalScore+(maxFirstnameScore * ConstantConfiguration.FNWeightage);
			  	        	}
			  	        	
			  	       }
			  	         
			  	      if(maxMiddlenameScore>ConstantConfiguration.MNThreshold)
			          {
			  	    	/**
			  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
			  	        	 */
			  	    	if(middleName!=null && firstName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("") )       
		 	        	{
		 	        		if(maxMiddlenameScore==1 || maxMiddlenameScore==0.9 )
		 	        		{
		 	        		 finalScore = (maxMiddlenameScore * ConstantConfiguration.MNWeightage);	
		 	        		 finalScore = finalScore+(1 * ConstantConfiguration.MLWeightage);
		 	        		}
		 	        		else
		 	        		{
		 	        		 finalScore =finalScore+ (maxMiddlenameScore * ConstantConfiguration.MNWeightage);
		 	        		}
		 	        	}
		 	        	else 
		 	        	{ 
		 	        		finalScore = finalScore+(maxMiddlenameScore * ConstantConfiguration.MNWeightage);
		 	        	}
			          }
			            
			           if(maxLastnameScore>ConstantConfiguration.LNThreshold)
			           {/**
			  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
			  	        	 */
			        	   if( lastName!=null && middleName.equalsIgnoreCase("") && firstName.equalsIgnoreCase("") && maxAliasnameScore<=0.7 )            
			 	        	{
			 	        		if(maxLastnameScore==1 || maxLastnameScore==0.9)
			 	        		{
			 	        		  finalScore =finalScore + (maxLastnameScore * ConstantConfiguration.LNWeightage);	 
			 	        		  finalScore = finalScore+(1 * ConstantConfiguration.FLNWeightage);	
			 	        		}
			 	        		else
			 	        		{
			 	        			finalScore =finalScore + (maxLastnameScore * ConstantConfiguration.LNWeightage);	
			 	        		}
			 	        	}
			        	   else
			 	        	{
			        		   finalScore =finalScore + (maxLastnameScore * ConstantConfiguration.LNWeightage);
			 	        	}
			    	        
			        	   
			           }  
			           //ConstantConfiguration.AliasWeightage
			          
			         
			           if(maxaliasScore >ConstantConfiguration.AliasThreshold)
			           {
			        	   maxaliasScore=maxaliasScore*ConstantConfiguration.AliasEntityWeightage;
			           }
			           else
			           {
			        	   maxaliasScore=0;
			           }
			           
			           
			         	finalScore= Math.max( finalScore, maxaliasScore );	
			         	
			          if((addr1Score+addr2Score) > ConstantConfiguration.AddressThreshold){
			        	  if(addr1Score>0){
			        		  individualSanctionInfo.put("ADDRESS1", addr1);
			        		 insertToSLS(requestID,"address1",addr1, "ADDRESS1", "WC List", addr1, 
										df.format(addr1Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-",entityType);
			        	  }
			        	  if(addr2Score>0){
				        	  individualSanctionInfo.put("ADDRESS2", addr2);
				        	  insertToSLS(requestID,"address2",addr2,  "ADDRESS2", "WC List", addr2, 
										df.format(addr2Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-",entityType);
			        	  }
			        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
			          }
			          if(dobScore > ConstantConfiguration.DOBThreshold){
			        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
			        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "WC List", dob, 
									df.format(dobScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-",entityType);
			          }
			          if(aliasDobscore > ConstantConfiguration.DOBThreshold){
			        	  individualSanctionInfo.put("DATE_OF_BIRTH",aliasDob);
			        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "Alias_Date_of_Birth", "WC List", aliasDob, 
									df.format(aliasDobscore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-",entityType);
			          }
			          
			          dobScore= Math.max( dobScore * ConstantConfiguration.DOBWeightage ,aliasDobscore * ConstantConfiguration.DOBWeightage);   
			          finalScore = finalScore + dobScore;
			          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
			        	  individualSanctionInfo.put("PASSPORT_NUMBER", otherID);
			        	  finalScore = finalScore + (otherIDScore * ConstantConfiguration.OtherIDWeightage);
			        	  
			        	  insertToSLS(requestID,"Passport_Number",passportNumber, "PASSPORT_NUMBER", "WC List", otherID, 
									df.format(otherIDScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-",entityType);
			          }
			          
		         if(finalScore > 0){
			          individualSanctionInfo.put("SCORE", finalScore+"");
			          individualSanctionInfo.put("LIST_TYPE", "WC List");
			          sanctionsInfo.put(dataID, individualSanctionInfo);
		         }
		         
		         
		         //Clear Objects Anand - 08-Oct-2018
		          //f1 = null; f2=null; f3 = null; m1  = null; m2 = null;m3 = null;l1 = null;l2=null;l3=null;aliasTypefirst = null;
		          //alf=null;alm=null;all=null;aliasTypemiddle = null;aliasTypelast = null; 
		         
		          addr1 = null;
		          addr2 = null;dob = null;otherID = null;aliasDob=null;
		          sanctionedEntityNameList=null;
		          matchedName=null;
		          wclIndividual=null;
		        }
	
		  }
		        
		        //Clear Objects Anand - 08-Oct-2018
		         dedupeObj = null;
				 df = null;
			     versionNo = null;
		         individualSanctionInfo=null;
		         utilityObj = null;
			    
		         firstName = null;
		         middleName = null;
		         lastName = null;
				 streetName = null;
				 doorNo = null;
				 city = null;
				 state = null;
				 country = null;
				 zipCode = null;
				 passportNumber = null;
				 aadharNumber = null;
				 panNumber = null;
				 dateOfBirth = null;
				 requestID = null; 
				 reuestDateTime = null; 
				 entityType = null;
				 wcl_individual_details = null;
		        
		  return  sanctionsInfo;
	}
}
