package com.manipal.deduplication;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.commons.lang3.StringUtils;
 
 class CFPartName {
		public String field_name = new String();
		public String field_value = new String();
		CFPartName(String field_name, String field_value) 
		{ this.field_name = field_name;
		  this.field_value = field_value;
		}
}
 class CFName {
	public ArrayList<CFPartName> partNames = new ArrayList<CFPartName>();
	void addFirstName(String name)
	{
	    CFPartName 	partName = new CFPartName("FIRST_NAME",name);
	    partNames.add(partName);
	}
	void addSecondName(String name)
	{
	    CFPartName 	partName = new CFPartName("SECOND_NAME",name);
	    partNames.add(partName);
	}
	void addThirdName(String name)
	{
	    CFPartName 	partName = new CFPartName("THIRD_NAME",name);
	    partNames.add(partName);
	}
}

public class Utilities {
	public DecimalFormat df = new DecimalFormat("0.00");
	/*Converts the string of names into a list of part names for the entity
	 * INput is the sanctionedEntity Name. This consists of the prime name of the entity as firstname,secondname,third name and a list of aliases.
	 *  
	 */
	
	ArrayList<CFName> getEntityNames(HashMap<String,ArrayList<String>> sanctionedEntityName)
	{
		
		 // ConstantConfiguration.watchlistLogger.info("inside the method=="+sanctionedEntityName);
		if(sanctionedEntityName == null) return null;
		
		CFName mainName = new CFName();
	    ArrayList<CFName> nameList = new ArrayList<CFName>();
	       
	    List<String> name = sanctionedEntityName.get("FIRST_NAME");
	    if(  name!= null) mainName.addFirstName(name.get(0));
	        
		name = sanctionedEntityName.get("SECOND_NAME");
		if(  name!= null) mainName.addSecondName(name.get(0));
				
		name = sanctionedEntityName.get("THIRD_NAME");
		if(  name!= null) mainName.addThirdName(name.get(0));
		
		nameList.add(mainName);
		mainName=null;
		name=null; //Anand Added 11-OCT-2018
		/*Get the list of aliases */
		
		 ArrayList<String> aliasList = sanctionedEntityName.get("ALIAS_NAME");
		 if(aliasList !=null)
       	{
			CFName aliasName = null;
			 for(String s1 : aliasList) {
					String firstName = null;
					String lastName = null;
					if(s1.indexOf("##")>-1)
					{
						aliasName = new CFName();
						String[] aliasNames = s1.split("##");
						if(aliasNames != null && aliasNames.length >0)
							firstName = aliasNames[0];
						if(aliasNames != null && aliasNames.length >=2)
							lastName = aliasNames[1];
						   //firstName=s1.split("##")[0].trim();
						//lastName=s1.split("##")[1].trim();
						if(firstName != null) aliasName.addFirstName(firstName);
						if(lastName != null) aliasName.addThirdName(lastName);
						nameList.add(aliasName);
						aliasName = null;
						aliasNames=null;//Anand Added 11-OCT-2018
					}
	        	}
       	}//end of adding aliases
       	
		 aliasList=null;//Anand Added 11-OCT-2018
		// ConstantConfiguration.watchlistLogger.info("nameList=="+nameList);
		return nameList;
	}
	/*getPartNameMatch(String screenedPartName, String sanctionedPartName)
	 * The algorithm for matching two names. In our case we send sub part names to this routine for matching
	 * Does a Levenshtein check for the text based matching. And a refined soundex for phonetic based matching
	 * If the levenshtein distance is less than 0.5 returns no match or a score of 0
	 * Returns the max of the text based and phonetic based scores
	 */
	public NameScore getPartNameMatch(String screenedPartName, String sanctionedPartName,String screenedPartNameClear,boolean isValidSubPartName1AfterClear)
	{
		NameScore nameMatchScore = new NameScore();
		RefinedSoundex refinedSoundex = null;
		double refinedSoundexScore = 0;
		//double levenshteinScore = 0;
		double lvScore = 0;
		double finalMatchedScore = 0;
		String highestMatch = null;
		//String type=""; 
		//String category="";
		String s1 = screenedPartName.toUpperCase();
		String s2 = sanctionedPartName.toUpperCase();
		
		
		String s3=s1;
		String s4=s2;
		
		
		//removal of Special character from Sanctioned entities
		s1=screenedPartNameClear;//removeExtraWhiteSpacesAndSpecialChars(s3); //Anand Moved outside for loop 11-OCT-2018
		
		//removal of Special character from Screened entities
		s2=removeExtraWhiteSpacesAndSpecialChars(s4);
				

		try{
			if (isValidSubPartName1AfterClear && isValid(s2)) {//Anand S1 isValid moved Moved outside for loop 11-OCT-2018
			int minLength = (s1.length() <= s2.length() ? s1.length()
					: s2.length());
			
			int levenshteinDistance = StringUtils
					.getLevenshteinDistance(s1, s2);
			double	levenshteinScore = 1.0 - (double) levenshteinDistance
					/ minLength;
			
			//moved the handling of initials to top
			/*if (Double.isNaN(levenshteinScore)
					|| (Double.compare(levenshteinScore, 0.0) < 0)) {
				levenshteinScore = 0;
			}
			else*/ 
			//if( minLength == 1 &&   s1.charAt(0) == s2.charAt(0))
			if(s1 == null || s1.trim().length() <= 0 || s2 == null || s2.trim().length() <= 0)
				levenshteinScore = 0.0;
			else if( s1.length() == 1 &&   s1.charAt(0) == s2.charAt(0))
			{ //for handling initials	
				levenshteinScore = 0.8;
			}
			else if (Double.isNaN(levenshteinScore)
					|| (Double.compare(levenshteinScore, 0.0) < 0)) {
				levenshteinScore = 0;
			}
			else if(levenshteinDistance < 2 && minLength > 1 && minLength < 5 && s1.charAt(0) == s2.charAt(0) && levenshteinScore < 0.8 ) 
			{	
				levenshteinScore = 0.8;
			}
			if(levenshteinScore>lvScore)
			{
				lvScore=levenshteinScore;
				highestMatch = s3;
			}
			
	        if (lvScore > 0.5) {
	        		refinedSoundex = new RefinedSoundex();
	        	
					int encS1 = refinedSoundex.encode(s1).length();
					int encS2 = refinedSoundex.encode(s2).length();
					if (encS1 < encS2) {// To divide by the length of
										// the shortest encoded string
						double score = ((double) refinedSoundex
								.difference(s1, s2) / encS2);
						if (score > refinedSoundexScore) {
							refinedSoundexScore = score;
							highestMatch = s3;
							//type=category;
						} 
					} else {
						double score = ((double) refinedSoundex
								.difference(s1, s2) / encS1);
						if (score > refinedSoundexScore) {
							refinedSoundexScore = score;
							highestMatch = s3;
							//type=category;
						} 
					}
				 finalMatchedScore=Math.max(lvScore, refinedSoundexScore);	
			} 
	       
	        else {
	        	if ((lvScore > finalMatchedScore)||(highestMatch==null)) {
	        		finalMatchedScore = lvScore;
					highestMatch = s3;
					//type=category;
				}
			}
		}																															
	
    	} catch (EncoderException e) {
				ConstantConfiguration.watchlistLogger.error(e);
		 }
		nameMatchScore.match_score = finalMatchedScore;
		nameMatchScore.phonetic_score = refinedSoundexScore;
		nameMatchScore.text_matching_score = lvScore;
		
		
		//Anand 12-OCT-2018
		s1 = null;
		s2 = null;
		s3 = null;
		s4 = null;
		screenedPartName=null; 
		sanctionedPartName=null;
		screenedPartNameClear=null;
		refinedSoundex = null; 
		
		
    	return nameMatchScore;
	}
	/*
	 * matches a part name against the part names of a given name
	 */
	public NameScore matchPartName(String fieldName, String screenedPartName, CFName name, String entityType)
	{

		
		double threshold = NameMatch.getThreshold(fieldName);
		NameScore matchScore = null;
		//1.	First check the part name against the different part names
		double score = 0;
		double subPartMaxScore = 0;
		int numOfMatches = 0;
		int numOfSanctionedNameSubParts=0;
		String matchedFieldName = new String("");
		String matchedFieldValue = new String("");
		String partFieldName = new String("");
		String partFieldValue = new String("");
		ArrayList<String> sanctionedSubPartNames = null;
		ArrayList<String> screenedSubPartNames = null;
		
		if(entityType.equalsIgnoreCase("1")){//If individual then split by space otherwise take complete word
			screenedSubPartNames = new ArrayList<String>(Arrays.asList(screenedPartName.split(" ")));
		}else{
			screenedSubPartNames = new ArrayList<String>(Arrays.asList(screenedPartName));
		}
		String subPartName1AfterClear=null;
		
		for( String subPartName1 : screenedSubPartNames)
		 {
			//System.out.println("MAtching with screened entity part name --"+subPartName1);
			 numOfSanctionedNameSubParts = 0;
			 subPartMaxScore = 0.0;
			 
			 subPartName1AfterClear=removeExtraWhiteSpacesAndSpecialChars(subPartName1.toUpperCase());//Anand 11-OCT-2018  //More CPU Hit moved  from getPartNameMatch to here 
			 boolean isValidSubPartName1AfterClear=isValid(subPartName1AfterClear);
			for (CFPartName partName : name.partNames)
			{
				//if(partName.field_value.contains("Saddam") || partName.field_value.contains("TIKRITI"))
				 //System.out.println("MAtching sanctioned Entity part names --  "+ partName.field_name+" "+partName.field_value+"with screened entity part name --"+subPartName1);
		     double subPartScore = 0.0;
		     		     //double subPartMaxScore = 0.0;	   
			//ArrayList<String> sanctionedSubPartNames = new ArrayList<String>(Arrays.asList(partName.field_value.split(" ")));
		     if(entityType.equalsIgnoreCase("1")){
				 sanctionedSubPartNames = new ArrayList<String>(Arrays.asList(partName.field_value.split(" ")));
		     }else{
		    	 sanctionedSubPartNames = new ArrayList<String>(Arrays.asList(partName.field_value));
		     }
				 numOfSanctionedNameSubParts += sanctionedSubPartNames.size();
				  for( String subPartName2 : sanctionedSubPartNames)
				  {
						 //System.out.println("MAtching sanctioned Entity sub part names --  "+ partName.field_name+" "+subPartName2+"with screened entity part name --"+subPartName1);

					  NameScore pnameScore = getPartNameMatch(subPartName1,subPartName2,subPartName1AfterClear,isValidSubPartName1AfterClear);
					  subPartScore = pnameScore.getMatch_score();
					  if(subPartScore > threshold && subPartScore > subPartMaxScore)
					  {
						  subPartMaxScore = subPartScore;
						  partFieldName = partName.field_name;
						  partFieldValue = partName.field_value;
						  numOfMatches++;
						  if(score == 0 || score > subPartMaxScore ) score = subPartMaxScore;
						  //ConstantConfiguration.watchlistLogger.info("Matched subpart "+ subPartName1+"Number Of matches == "+numOfMatches);

					  }
					 
				  }//end of sanctioned entity subpart match
				  
				 
			  }//end of sanctioned Entity part name match
			 //if(subPartMaxScore < threshold) return null;
			 
			 if(matchedFieldName.contains(partFieldName) == false) 
			 {
				 if(matchedFieldName.equalsIgnoreCase(""))matchedFieldName += partFieldName;
				 else matchedFieldName += ","+ partFieldName;
			 }
			  if(matchedFieldValue.contains(partFieldValue) == false) 
			  {
				  if(matchedFieldValue.equalsIgnoreCase(""))matchedFieldValue += partFieldValue;
				  else matchedFieldValue += " "+ partFieldValue;
			  }
			 // if(score == 0 || score > subPartMaxScore ) score = subPartMaxScore; //score is the minimum of all sub part scores
			  
		}//end of screened entity sub name match
		
		  
		//2.	If the part name does not matches with any of the sanctioned entity part names check for sub part names
		//if(score > threshold && (numOfMatches == screenedSubPartNames.size() || numOfMatches >= numOfSanctionedNameSubParts) )
		 // ConstantConfiguration.watchlistLogger.info("Matched Part "+ matchedFieldName+"Number Of matches == "+numOfMatches+" Screened Entity subparts "+screenedSubPartNames.size()+" Sanctioned Entity subparts "+numOfSanctionedNameSubParts);

		 if(matchedFieldName != null && (numOfMatches == screenedSubPartNames.size() || numOfMatches >= numOfSanctionedNameSubParts) )
		 {
			  //ConstantConfiguration.watchlistLogger.info("Matched Part name "+ matchedFieldName+" Value "+screenedPartName+" With "+matchedFieldValue);

			matchScore = new NameScore();
			matchScore.field_name = fieldName;
			matchScore.sanctioned_entity_field_name = matchedFieldName;
			matchScore.screened_entity_value = screenedPartName;
			matchScore.sanctioned_entity_value = matchedFieldValue;
			matchScore.setMatch_score(score);
			//System.out.println("PArt Name Match -- "+fieldName+ "--"+screenedPartName+" MAtched Field "+matchedFieldName+" -- "+matchedFieldValue);
		}
		
		 
		  matchedFieldName = null;
		  matchedFieldValue = null;
		  partFieldName =  null;
		  partFieldValue =  null;
		  sanctionedSubPartNames = null;
		  screenedSubPartNames = null;
		  subPartName1AfterClear=null;
		  
		return matchScore;
		
	}
	
	/*public NameScore matchPartName(String fieldName, ArrayList<String> screenedPartName, ArrayList<String>firstName, ArrayList<String>middleName,ArrayList<String>lastName)
	{

		
		double threshold = NameMatch.getThreshold(fieldName);
		NameScore matchScore = null;
		//1.	First check the part name against the different part names
		double score = 0;
		double subPartMaxScore = 0;
		int numOfMatches = 0;
		int numOfSanctionedNameSubParts=0;
		String matchedFieldName = new String("");
		String matchedFieldValue = new String("");
		String partFieldName = new String("");
		String partFieldValue = new String("");
		ArrayList<String> sanctionedSubPartNames = null;
		//ArrayList<String> screenedSubPartNames = new ArrayList<String>(Arrays.asList(screenedPartName.split(" ")));
		ArrayList<String> screenedSubPartNames = screenedPartName;
		HashMap<String,ArrayList<String>> sanctionedEntityNames = new HashMap<String,ArrayList<String>>();
		sanctionedEntityNames.put("FIRST_NAME", firstName);
		sanctionedEntityNames.put("MIDDLE_NAME", middleName);
		sanctionedEntityNames.put("LAST_NAME", lastName);
		
		for( String subPartName1 : sanctionedEntityNames)
		 {
			//System.out.println("MAtching with screened entity part name --"+subPartName1);
			 numOfSanctionedNameSubParts = 0;
			 subPartMaxScore = 0.0;
			for (CFPartName partName : name.partNames)
			{
				//if(partName.field_value.contains("Saddam") || partName.field_value.contains("TIKRITI"))
				 //System.out.println("MAtching sanctioned Entity part names --  "+ partName.field_name+" "+partName.field_value+"with screened entity part name --"+subPartName1);
		     double subPartScore = 0.0;
		     		     //double subPartMaxScore = 0.0;	   
			//ArrayList<String> sanctionedSubPartNames = new ArrayList<String>(Arrays.asList(partName.field_value.split(" ")));
				 //sanctionedSubPartNames = new ArrayList<String>(Arrays.asList(partName.field_value.split(" ")));
				 numOfSanctionedNameSubParts += sanctionedSubPartNames.size();
				  for( String subPartName2 : sanctionedSubPartNames)
				  {
						 //System.out.println("MAtching sanctioned Entity sub part names --  "+ partName.field_name+" "+subPartName2+"with screened entity part name --"+subPartName1);

					  NameScore pnameScore = getPartNameMatch(subPartName1,subPartName2);
					  subPartScore = pnameScore.getMatch_score();
					  if(subPartScore > threshold && subPartScore > subPartMaxScore)
					  {
						  subPartMaxScore = subPartScore;
						  partFieldName = partName.field_name;
						  partFieldValue = partName.field_value;
						  numOfMatches++;
						  if(score == 0 || score > subPartMaxScore ) score = subPartMaxScore;
						  //ConstantConfiguration.watchlistLogger.info("Matched subpart "+ subPartName1+"Number Of matches == "+numOfMatches);

					  }
					 
				  }//end of sanctioned entity subpart match
				  
				 
			  }//end of sanctioned Entity part name match
			 //if(subPartMaxScore < threshold) return null;
			 
			 if(matchedFieldName.contains(partFieldName) == false) 
			 {
				 if(matchedFieldName.equalsIgnoreCase(""))matchedFieldName += partFieldName;
				 else matchedFieldName += ","+ partFieldName;
			 }
			  if(matchedFieldValue.contains(partFieldValue) == false) 
			  {
				  if(matchedFieldValue.equalsIgnoreCase(""))matchedFieldValue += partFieldValue;
				  else matchedFieldValue += " "+ partFieldValue;
			  }
			 // if(score == 0 || score > subPartMaxScore ) score = subPartMaxScore; //score is the minimum of all sub part scores
			  
		}//end of screened entity sub name match
		
		  
		//2.	If the part name does not matches with any of the sanctioned entity part names check for sub part names
		//if(score > threshold && (numOfMatches == screenedSubPartNames.size() || numOfMatches >= numOfSanctionedNameSubParts) )
		 // ConstantConfiguration.watchlistLogger.info("Matched Part "+ matchedFieldName+"Number Of matches == "+numOfMatches+" Screened Entity subparts "+screenedSubPartNames.size()+" Sanctioned Entity subparts "+numOfSanctionedNameSubParts);

		 if(matchedFieldName != null && (numOfMatches == screenedSubPartNames.size() || numOfMatches >= numOfSanctionedNameSubParts) )
		 {
			  //ConstantConfiguration.watchlistLogger.info("Matched Part name "+ matchedFieldName+" Value "+screenedPartName+" With "+matchedFieldValue);

			matchScore = new NameScore();
			matchScore.field_name = fieldName;
			matchScore.sanctioned_entity_field_name = matchedFieldName;
			matchScore.screened_entity_value = screenedPartName;
			matchScore.sanctioned_entity_value = matchedFieldValue;
			matchScore.setMatch_score(score);
			//System.out.println("PArt Name Match -- "+fieldName+ "--"+screenedPartName+" MAtched Field "+matchedFieldName+" -- "+matchedFieldValue);
		}
					
		return matchScore;
		
	}*/
	 /*
     * matches the screened entity against one name/alias of a single entity
     */
	public NameMatch matchName(String first_name,String middle_name,String last_name, CFName name,String entityType)
    {
    	if(first_name == null){
    		first_name = "";
    	}
    	if(middle_name == null){
    		middle_name = "";
    	}
    	if(last_name == null){
    		last_name = "";
    	}
    	    NameMatch match = null;
    	    NameScore firstNameScore = null;
    	    NameScore secondNameScore = null;
    	    NameScore thirdNameScore = null;
    	   
    	    
    		if(first_name != null && first_name.length() > 0) firstNameScore = matchPartName("FIRST_NAME",first_name,name,entityType);
    		if(middle_name != null && middle_name.length() > 0)  secondNameScore = matchPartName("SECOND_NAME",middle_name,name,entityType);
    		if(last_name != null && last_name.length() > 0)  thirdNameScore = matchPartName("THIRD_NAME",last_name,name,entityType);
    		if((firstNameScore != null && first_name.length() > 1) || (secondNameScore != null && middle_name.length() > 1)|| (thirdNameScore != null&& last_name.length() > 1))
    		//if(firstNameScore != null || secondNameScore != null || thirdNameScore != null)

    		{
    			match = new NameMatch();
    			match.addScore(firstNameScore);
    		 	match.addScore(secondNameScore);
    		    match.addScore(thirdNameScore);
    		      		    		    
    		}
    		return match;
    		
    }
    /*
     * matches the screened entity against list of names of a single entity
     */
	public NameMatch matchNames(String first_name, String middle_name, String last_name, ArrayList<CFName> nameList,String entityType)
	{
		  if(nameList == null || nameList.size() == 0) return null;
		  
	      NameMatch currentMatch;	
	      NameMatch match = null;
	      
	      for (int i=0; i < nameList.size();i++)
	      {
	    	  currentMatch = matchName(first_name,middle_name,last_name,nameList.get(i),entityType);
	    	  if(currentMatch == null) continue;
	    	  if(match == null) match = currentMatch;
	    	  else match.setIfHigher(currentMatch);
	      }
	      return match;
	      
	}
	/*@SuppressWarnings({ "rawtypes", "unchecked" }) 
	public ArrayList<NameMatch> matchNameAgainstSanctionedEntityList(String first_name, String middle_name, String last_name, HashMap<String,String> sanctionedEntityName){
		ArrayList<NameMatch> matches = new ArrayList<NameMatch>();
		String version_no="";
		int data_ID=0;
		HashMap<String, String> individual_sanction_info=null;
		HashMap<Integer, HashMap<String, String>> sanctions_info = new HashMap<Integer, HashMap<String, String>>();
		 double f1score = 0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0;
		 double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0;
         double alfScore=0,almScore=0,allScore=0,f1lvScore=0,f1SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0;
         double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
         String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null;
         String addr2 = null,dob = null,otherID = null,al=null,alf=null,alm=null,all=null,aliasString=null;
         double alf1Score = 0,alf2Score = 0,alm1Score = 0,alm2Score = 0,all1Score = 0,all2Score = 0,alf1wScore = 0,alf2wScore = 0;
		//if(Dedupe.ofac_individual_details!=null && Dedupe.ofac_individual_details.size() > 0){
			Set set = Dedupe.ofac_individual_details.entrySet();
			Iterator it = set.iterator();
			double max_first_name_score = 0;
			double max_middle_name_score = 0;
			double max_last_name_score = 0;
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				data_ID = Integer.parseInt(entry.getKey().toString());
				HashMap<String,ArrayList<String>> ofac_individual = (HashMap<String,ArrayList<String>>)entry.getValue();
				//HashMap<String,List<String>> partnamesList = getPartNames(sanctionedEntityName);
				ArrayList<String> mainName = new ArrayList<String>();
				
				List<CFName> sanctionedEntityNameList = getEntityNames(ofac_individual);
				
				if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
				NameMatch matchedName = matchNames(first_name,middle_name,last_name, sanctionedEntityNameList);
				if( matchedName == null) continue;
				matchedName.setMatched_ID(ofac_individual.get("DATA_ID").get(0));
				
				
				
				
				
				for(NameScore partNameScore : matchedName.getMatch_scores())
				{
					if(partNameScore.getField_name() == "FIRST_NAME")
					{
						f1score = partNameScore.getMatch_score();
		                
		                f1lvScore=partNameScore.getMatch_score();
		                f1SoundexScore=partNameScore.getMatch_score();
					}
					else if(partNameScore.getField_name() == "THIRD_NAME")
					{
						f3score = partNameScore.getMatch_score();
		          		//f3score=  f3score*ConstantConfiguration.FLWeightage;
		          		//f3= matchingf2l.split("##")[1];
		          		f3lvScore=partNameScore.getMatch_score();
		          		f3SoundexScore=partNameScore.getMatch_score();
						
					}
				}
				
				
				if(first_name!=null && !first_name.equalsIgnoreCase("") && !first_name.equalsIgnoreCase("null")) {
					if(ofac_individual.get("FIRST_NAME")!=null){
			          		String matchingf2f=refinedSoundexCheck(ofac_individual.get("FIRST_NAME"), first_name);
			          		f1score = Double.parseDouble(matchingf2f.split("##")[0]);
			                f1 = matchingf2f.split("##")[1];
			                f1lvScore=Double.parseDouble(matchingf2f.split("##")[2]);
			                f1SoundexScore=Double.parseDouble(matchingf2f.split("##")[3]);
			          	}
			          	
			          	if(ofac_individual.get("THIRD_NAME")!=null)
			          	{
			          		String matchingf2l=refinedSoundexCheck(ofac_individual.get("THIRD_NAME"), firstName);
			          		f3score = Double.parseDouble(matchingf2l.split("##")[0]);
			          		//f3score=  f3score*ConstantConfiguration.FLWeightage;
			          		f3= matchingf2l.split("##")[1];
			          		f3lvScore=Double.parseDouble(matchingf2l.split("##")[2]);
			          		f3SoundexScore=Double.parseDouble(matchingf2l.split("##")[3]);
			          	}
			          	
		          }
				
				//Final Score Calculation
			       
		          if(f1score > ConstantConfiguration.FFThreshold){
		        	  individual_sanction_info.put("FIRST_NAME", f1);
		            adapter_obj.insertToSLS(requestID,"First_Name",first_name, "FIRST_NAME", "OFAC List", f1, 
								df.format(f1score),"-", data_ID+"", "-", screeningMode,reuestDateTime,f1lvScore+"",f1SoundexScore+"","1");
		        	   }
		          if(f3score > ConstantConfiguration.FLThreshold){
		        	  individual_sanction_info.put("LAST_NAME", f3);
		            adapter_obj.insertToSLS(requestID,"First_Name",firstName, "LAST_NAME", "OFAC List", f3, 
								df.format(f3score),"-", data_ID+"", "-", screeningMode,reuestDateTime,f3lvScore+"",f3SoundexScore+"","1");
		        	   }
		          
		          f1score= f1score*ConstantConfiguration.FFWeightage;
		   		  f3score= f3score*ConstantConfiguration.FLWeightage;
		   		  
		   		 maxFirstnameScore=Math.max(f1score,f3score);
		   		 
		   		if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
		  	       {
		  	        	
		  	        	if(firstName!=null && middleName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("")  && dateOfBirth.equalsIgnoreCase("") && passportNumber.equalsIgnoreCase("") 
		  	        			&& streetName.equalsIgnoreCase("") && doorNo.equalsIgnoreCase(""))       
		  	        	{
		  	        		if(maxFirstnameScore==1 || maxFirstnameScore==0.9 )
		  	        		{
		  	        		 finalScore = (maxFirstnameScore * ConstantConfiguration.FNWeightage);	
		  	        		 finalScore = finalScore+(1 * ConstantConfiguration.LFNWeightage);
		  	        		}
		  	        		else
		  	        		{
		  	        		 finalScore = (maxFirstnameScore * ConstantConfiguration.FNWeightage);
		  	        		}
		  	        	}
		  	        	else 
		  	        	{ 
		  	        		finalScore = (maxFirstnameScore * ConstantConfiguration.FNWeightage);
		  	        	}
		  	        	
		  	       }
		   		
		   	 if(finalScore > 0){
		          individual_sanction_info.put("SCORE", finalScore+"");
		          individual_sanction_info.put("LIST_TYPE", "OFAC LIST");
		          sanctionsInfo.put(data_ID, individual_sanction_info);
	          }
		   	 
			}
			
			double maxScore = getFinalScoreForHashMap(sanctions_info, "SCORE");
			String watchListMatch = "No Match";
			if(maxScore > ConstantConfiguration.FinalScoreThreshold){
				watchListMatch = "Match";
				}
			
			HashMap<String, String> result=getFinalScoreForHashMapFinal(sanctionsInfo, "SCORE");
			String Listtype="";
			if(result.get("LISTTYPE")!=null && !result.get("LISTTYPE").equalsIgnoreCase(""))
			{
		     Listtype=result.get("LISTTYPE");
			}
			else
			{
				Listtype="-";
			}
		    String FINALDATAID="";
		    if(result.get("FINALDATAID")!=null && !result.get("FINALDATAID").equalsIgnoreCase(""))
		   {
			 FINALDATAID=result.get("FINALDATAID");	
		   }
		    else
		    {
		    	FINALDATAID="-";
		    }
			//if(maxScore > 0){
			adapter_obj.insertToSLS(requestID,"-","-", "-",Listtype, "-", 
					df.format(maxScore),"-",FINALDATAID+"", watchListMatch, screeningMode,reuestDateTime,"-","-","1");
		}
		return matches;
	}*/
	 /*ArrayList<NameMatch> GetNameMatchScore( String fname, String mname, String lname)
	 {
	    ArrayList<NameMatch> matches = new ArrayList<NameMatch>();
	 1.	//Get the list of sanctioned Entities
	 2.	For each sanctioned entity:
	 3.	Get the list of part names
	 ArrayList<String>  sanctionedEntityPartNames  = sanctionedEntity.getPartNames();
	 4.	Preprocess the partnames of the screened entity (Remove all special characters)
	 Fname = preProcess(fname);
	 Mname = preProcess(mname);
	 Lname = preProcess(lname);
	 5.	Preprocess the sanctioned entity part names
	 sanctionedEntityPartNames = preProcess(sanctionedEntityPArtNames);
	 6.	Check the part names
	 firstNameScore = matchPartName(fname, sanctionedEntityPartNames);
	 middleNameScore = matchPartName(mname, sanctionedEntityPartNames);
	 lastNameScore = matchPartName(lname, sanctionedEntityPartNames);

	 7.	Calculate the final score as weighted average of the individual part names
	 matchScore = firstNameScore * weight of fname + middleNameScore * weight of mname + lastNameScore * weight of lname;

	 8.	If the matchScore is greater than name match threshold declare a match 

	 If( matchScore > nameMatchThreshold) 
	 {
	 NameMatch nameMatch = new NameMatch(sanctionedEntity.getID(),sanctionedEntity.getListType(), fname,mname,lname, matchScore, firstNameScore, middleNameScore, lastNameScore);
	 matches.add( nameMatch);
	          }


	     Return(matches);

	 }
	\Matches part names of a screened entity against the part names of a sanctioned entity.
     The inputs are the partname of the screened entity and the various part names of the sanctioned entity.
   All the part names are assumed to be already preprocessed for special characters.
The part names may be in the following form “partname”, “subpartname1 subpartname2 ...”
The output is the match score.

Double matchPartName(fname, sanctionedEntityPartNames, threshold)
{
1.	First check the part name against the different part names
Double score = 0;
Double matchScore = 0;
For ( string partName in sanctionedEntityPartNames)
{
   Score = getMatchScore(fname,partName);
  If(score > matchScore) matchScore = score;
  
}
2.	If the part name matches with any of the sanctioned entity part names return the match score
If(matchScore > threshold) return matchScore;

3.	Else, see if the part name consists of multiple sub parts
}

	 */
	
	public String refinedSoundexCheck(ArrayList<String> listOfStrings, String s2) {
		RefinedSoundex refinedSoundex = new RefinedSoundex();
		double refinedSoundexScore = 0;
		double lvScore = 0;
		double soundexScore = 0;
		double finalMatchedScore = 0;
		String highestMatch = null;
		String type = "";
		String category = "";
		try {
			if (listOfStrings != null) {
				for (String s1 : listOfStrings) {
					String s11 = "", s12 = "", s13 = "", s14 = "", s15 = "", s21 = "", s22 = "", s23 = "", s24 = "",
							s25 = "", s3 = "", s4 = "";
					if (s1.indexOf("##") > -1) {
						category = s1.split("##")[1].trim();
						s1 = s1.split("##")[0].trim();
					}
					s1 = s1.toUpperCase();
					s2 = s2.toUpperCase();
					s3 = s1;
					s4 = s2;
					
					// removal of Special character from Sanctioned entities
					s1 = removeExtraWhiteSpacesAndSpecialChars(s3);

					// removal of Special character from Screened entities
					s2 = removeExtraWhiteSpacesAndSpecialChars(s4);

					if (isValid(s1) && isValid(s2)) {
						int minLength = (s1.length() <= s2.length() ? s1.length() : s2.length());
						int levenshteinDistance = StringUtils.getLevenshteinDistance(s1, s2);
						double levenshteinScore = 1.0 - (double) levenshteinDistance / minLength;

						if (Double.isNaN(levenshteinScore) || (Double.compare(levenshteinScore, 0.0) < 0)) {
							levenshteinScore = 0;
						} else if (minLength == 1 && s1.charAt(0) == s2.charAt(0)) { // for handling initials
							levenshteinScore = 0.8;
						} else if (levenshteinDistance < 2 && minLength > 1 && minLength < 5
								&& s1.charAt(0) == s2.charAt(0) && levenshteinScore < 0.8) {
							levenshteinScore = 0.8;
						}
						if (levenshteinScore > lvScore) {
							lvScore = levenshteinScore;
							highestMatch = s3;
						}

						if (lvScore > 0.5) {
							int encS1 = refinedSoundex.encode(s1).length();
							int encS2 = refinedSoundex.encode(s2).length();
							if (encS1 < encS2) {// To divide by the length of the shortest encoded string
								double score = ((double) refinedSoundex.difference(s1, s2) / encS2);
								if (score > refinedSoundexScore) {
									refinedSoundexScore = score;
									highestMatch = s3;
									type = category;
								}
							} else {
								double score = ((double) refinedSoundex.difference(s1, s2) / encS1);
								if (score > refinedSoundexScore) {
									refinedSoundexScore = score;
									highestMatch = s3;
									type = category;
								}
							}
							finalMatchedScore = Math.max(lvScore, refinedSoundexScore);
						}else {
							if ((lvScore > finalMatchedScore) || (highestMatch == null)) {
								finalMatchedScore = lvScore;
								highestMatch = s3;
								type = category;
							}
						}
					}
				}
			}
		} catch (EncoderException e) {
			ConstantConfiguration.watchlistLogger.error(e);
		}
		if (!type.equalsIgnoreCase("") && !type.equalsIgnoreCase("null")) {
			return type + "&&" + finalMatchedScore + "##" + type + "&&" + highestMatch + "##" + df.format(lvScore)
					+ "&&" + df.format(refinedSoundexScore);
		} else
			return finalMatchedScore + "##" + highestMatch + "##" + df.format(lvScore) + "##"
					+ df.format(refinedSoundexScore);
	}
	
	public static String removeExtraWhiteSpacesAndSpecialChars(String input) {
		if(input!=null && input.trim().length()>0){
			input = input.replaceAll("[^ A-Za-z0-9]", " ");
			input = input.trim().replaceAll(" +", " ");
		}
		return input;
	}
	
	/** This method is to check whether the given input String has any non-english characters
	 * @param input String
	 * @return boolean
	 * 		<br>&emsp;&emsp;true - String has no non-english characters 	
	 * 		<br>&emsp;&emsp;false - String contains non-english characters
	 */
	public boolean isValid(String input) {    
		boolean isValid;
		try{
			CharsetEncoder asciiEncoder = Charset.forName("US-ASCII").newEncoder();
	    	isValid = asciiEncoder.canEncode(input);
	    	asciiEncoder=null;
	    	input=null;
		}catch (Exception e){
			isValid = false;
		}
		return isValid;
	}

	/*public static void main(String[] args) {
		System.out.println(removeExtraWhiteSpacesAndSpecialChars("abc def *a4   bdr"));	
	}*/
}
