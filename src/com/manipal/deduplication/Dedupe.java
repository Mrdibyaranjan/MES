package com.manipal.deduplication;

import static main.CFRTAdapter.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoderComparator;
import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.commons.codec.language.Soundex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import oracle.net.aso.e;

//import worldCheckList.WorldCheckListUtility;


/**
 * @author Rajasekaran Raghavan
 * @version v5.1
 * @lastUpdated 10-06-2016
 * @description 
 * 				This class contains methods to parse input xml, apply de-duplication algorithm
 * 				for the xml values against OFAC and UN list values
 * 				This class generates an output xml response with screening results and also inserts
 * 				screening summary to the database
 */
public class Dedupe {
	public static final int splitSize = ConstantConfiguration.splitSizeForNoOfThreads;
	public final String ALIAS_TAG = "ALIAS_NAME";
	String ofac_xml="",un_xml="",locallist_xml="",pep_xml="",wc_xml;
	public DecimalFormat df = new DecimalFormat("0.00");
	//double levenshtein_score=0;
	//double soundex_score=0;
	//int levenshtein_distance_for_final=0;
	//double	levinsteianScore1=0;
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> ofac_individual_details;
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> un_individual_details;
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> locallist_individual_details;
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> pep_individual_details;
	public static Vector<HashMap<Integer, HashMap<String,ArrayList<String>>>> worldchecklist_individual_details;
	public static Vector<HashMap<Integer, HashMap<String,ArrayList<String>>>> worldchecklist_individual_details_delta;
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> hmWorldchecklist_individual_details;
	public static HashMap<Integer, HashMap<String,ArrayList<String>>> hmWorldchecklist_individual_details_delta;
	public  HashMap<Integer, ArrayList<String>> whiteWatchlistData = null;
    public ArrayList<HashMap<String,String>> matches = null;
	Logger log = Logger.getLogger(Dedupe.class);
	SimpleDateFormat sdf =  new SimpleDateFormat("hhmmss");
	
	public Dedupe(){
		
	}
	public void insertToSLS(String reqid, String sfdcfieldmatched,
			String sfdccontentmatched, String fieldmatched, String listname,
			String contentmatched, String score, String version,
			String sanctionID, String watchListMatch, int screeningType,
			String requestDateTime, String textScore, String soundexScore,
			String customerType) {
		
		DatabaseAdaptor adapter_obj = new DatabaseAdaptor();
		if(matches == null){
			matches = new ArrayList<HashMap<String,String>>();
		}
		HashMap<String,String> matchedRecord = new HashMap<String,String>();
		try {
			if(requestDateTime.contains("IST")&&requestDateTime.trim().length()>0){
				requestDateTime = adapter_obj.convertDateTOETCFormate(requestDateTime);
			}
		} catch (ParseException e) {
			logger.error("", e);
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
		//logger.trace("putting to matches");
		//synchronized (matches) {
			matches.add(matchedRecord);
		//}
		//	System.out.print(" -- "+matches.size());
	}
	public Dedupe(boolean init)
	{
		
		DatabaseAdaptor adapter_obj = new DatabaseAdaptor();
		/*Initialising ArrayLists which will be loaded with required values and used for lookup*/
		//adapter_obj = new DatabaseAdaptor();
		//ofac_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		//un_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		
		/* Nagaraj -- do not reinitialize for every message
		 * locallist_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		 
		
		ofac_xml = adapter_obj.getofacsanctionslist();
		un_xml = adapter_obj.getUNlistxml();
		locallist_xml = adapter_obj.getlistxml("Local List");
		//To read required values from OFAC_XML and UN_XML files and load them into ArrayList for lookup
		ofac_individual_details = adapter_obj.prepareLookUpList(ofac_xml,"OFAC");
		un_individual_details = adapter_obj.prepareLookUpList(un_xml,"UN List");
		//logger.trace("PArsing the Local list -- "+locallist_xml);
		locallist_individual_details = adapter_obj.prepareLookUpList(locallist_xml,"UN List"); //temporarily get from ofac list
		*/ //end of comment
		/*To fetch last added OFAC and UN list from SANCTIONSLIST table*/
		if(ConstantConfiguration.ofacScreening&&ofac_individual_details==null )
		{
			ofac_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this 
			ofac_xml = adapter_obj.getSanctionsListDetails("OFAC");
			ofac_individual_details = adapter_obj.prepareLookUpList(ofac_xml,"OFAC",null);
			logger.info("ofac_individual_details==online="+ofac_individual_details.size());
		}
		if(ConstantConfiguration.unScreening&&un_individual_details==null)
		{
			un_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this
			un_xml = adapter_obj.getSanctionsListDetails("UN List");
			un_individual_details = adapter_obj.prepareLookUpList(un_xml,"UN List",null);
			logger.info("un_individual_details==online="+un_individual_details.size());
		}
		
	/*	
	 * Removed Local list for JFS new requirement. 
	 */
		/*if(ConstantConfiguration.localScreening&&locallist_individual_details == null)
		{
			locallist_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this
			locallist_xml = adapter_obj.getSanctionsListDetails("Local List");
			locallist_individual_details = adapter_obj.prepareLookUpList(locallist_xml,"Local List",null);
			logger.info("locallist_individual_details==online="+locallist_individual_details.size());
		}*/
		
		if(ConstantConfiguration.pepScreening&&pep_individual_details == null)
		{
			pep_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this
			pep_xml = adapter_obj.getSanctionsListDetails("PEP List");
			pep_individual_details = adapter_obj.prepareLookUpList(pep_xml,"PEP List",null);
			logger.info("pep_individual_details==online="+pep_individual_details.size());
		}
		
		if(ConstantConfiguration.wclScreening&&worldchecklist_individual_details == null)
		{
			WorldCheckListUtility wcl_obj=new WorldCheckListUtility();
			
			/*worldchecklist_individual_details=null;
			String filepath=System.getProperty("user.dir")+"\\WCL\\world-check-day.xml";
			filepath = "D:\\Builds\\WATCHLIST\\WCL\\world-check-day.xml";
			worldchecklist_individual_details_delta =wcl_obj.parseWorldCheckXML(filepath, null);
			worldchecklist_individual_details = worldchecklist_individual_details_delta; 
			*/
			
			//read from selialized hashmap and initialize
			worldchecklist_individual_details=splitHashMap((HashMap<Integer, HashMap<String,ArrayList<String>>>)wcl_obj.byte2Object(wcl_obj.fetchFeedFromTable()));
			
			/*if(worldchecklist_individual_details!=null){
				logger.info("Online 275==="+worldchecklist_individual_details.get(275));
				logger.info("Online 277==="+worldchecklist_individual_details.get(277));
			}*/
			if(worldchecklist_individual_details!=null)
				logger.info("Online Full List Screen No Of Sub Hashmap Worldchecklist_individual_details--offline--"+worldchecklist_individual_details.size());
			
		}
	/*need to close database connection. Pratyusha 26/2/2019*/
		if(adapter_obj.conn!=null)
			try {
				adapter_obj.conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error("", e);
			}
	}
	
	
	public static Vector<HashMap<Integer, HashMap<String, ArrayList<String>>>> splitHashMap(HashMap<Integer, HashMap<String, ArrayList<String>>> bigMap){
		Vector<HashMap<Integer, HashMap<String, ArrayList<String>>>> hashMapSplits = null;
		HashMap<Integer, HashMap<String, ArrayList<String>>> smallMap = null;
		if(bigMap != null && bigMap.size() > 0){
			hashMapSplits = new Vector<HashMap<Integer,HashMap<String,ArrayList<String>>>>();
			smallMap = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
			int counter = 0;
					
			for (Entry<Integer, HashMap<String, ArrayList<String>>> hashMap : bigMap.entrySet()) {
				smallMap.put(hashMap.getKey(), hashMap.getValue());
				counter++;
				if(counter % splitSize == 0){
					hashMapSplits.add(smallMap);
					smallMap = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
				}
			}
			if(smallMap != null && smallMap.size() > 0){
				hashMapSplits.add(smallMap);
				smallMap = null;
			}
		}
		return hashMapSplits;
	}
	public Dedupe(boolean isOFACUpdated, boolean isUNUpdated, boolean islocalListUpdated, boolean isPEPListUpdated,boolean isWCListUpdated)
	{
		
		/*Initialising ArrayLists which will be loaded with required values and used for lookup*/
		DatabaseAdaptor adapter_obj = new DatabaseAdaptor();
		//ofac_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		//un_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		//locallist_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
		/*To fetch last added OFAC and UN list from SANCTIONSLIST table*/
		/*To read required values from OFAC_XML and UN_XML files and load them into ArrayList for lookup*/
		
		
		if(ConstantConfiguration.ofacScreening&&isOFACUpdated && ofac_individual_details==null )
		{  
			ofac_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this 
			ofac_xml = adapter_obj.getofacsanctionslist();
			if(ofac_xml !=null && ofac_xml.indexOf("##") > -1 && ofac_xml.split("##")[1] !=null){
				ofac_individual_details = adapter_obj.prepareLookUpList(ofac_xml.split("##")[0],"OFAC", adapter_obj.prepareLookUpList(ofac_xml.split("##")[1],"OFAC", null));//0 - old and 1 - new
			}else{
				ofac_individual_details = adapter_obj.prepareLookUpList(ofac_xml,"OFAC",null);
			}
			logger.info("ofac_individual_details--offline--"+ofac_individual_details.size());
		}
		
		if(ConstantConfiguration.unScreening&&isUNUpdated && un_individual_details==null)
		{
			un_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this
			un_xml = adapter_obj.getUNlistxml();
			if(un_xml !=null && un_xml.indexOf("##") > -1 && un_xml.split("##")[1] !=null){
				un_individual_details = adapter_obj.prepareLookUpList(un_xml.split("##")[0],"UN List",adapter_obj.prepareLookUpList(un_xml.split("##")[1],"UN List", null));
			}else{
				un_individual_details = adapter_obj.prepareLookUpList(un_xml,"UN List",null);
			}
			logger.info("un_individual_details--offline--"+un_individual_details.size());
		}
		
		if(ConstantConfiguration.localScreening&&islocalListUpdated && locallist_individual_details == null)
		{
			locallist_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this
			locallist_xml = adapter_obj.getlistxml("Local List");
			
			if(locallist_xml !=null && locallist_xml.indexOf("##") > -1 && locallist_xml.split("##")[1] !=null){
				locallist_individual_details = adapter_obj.prepareLookUpList(locallist_xml.split("##")[0],"Local List",adapter_obj.prepareLookUpList(locallist_xml.split("##")[1],"Local List", null)); //temporarily get from ofac list
			}else{
				locallist_individual_details = adapter_obj.prepareLookUpList(locallist_xml,"Local List",null);
			}
			logger.info("locallist_individual_details---offline-"+locallist_individual_details.size());
		}
		
		if(ConstantConfiguration.pepScreening&&isPEPListUpdated && pep_individual_details == null)
		{
			pep_individual_details = new HashMap<Integer, HashMap<String,ArrayList<String>>>();//Nagaraj if it already exists we do not want another thread to update this
			pep_xml = adapter_obj.getlistxml("PEP List");
				
			if(pep_xml !=null && pep_xml.indexOf("##") > -1 && pep_xml.split("##")[1] !=null){
				pep_individual_details = adapter_obj.prepareLookUpList(pep_xml.split("##")[0],"PEP List",adapter_obj.prepareLookUpList(pep_xml.split("##")[1],"PEP List", null)); //temporarily get from ofac list
			}else{
				pep_individual_details = adapter_obj.prepareLookUpList(pep_xml,"PEP List",null);
			}
			logger.info("pep_individual_details--offline--"+pep_individual_details.size());
		}
		
		if(ConstantConfiguration.wclScreening&&isWCListUpdated && ((ConstantConfiguration.offlineCompleteListScreening==false 
				&& worldchecklist_individual_details_delta == null)||(ConstantConfiguration.feedType == 1 && worldchecklist_individual_details == null)))
		{
			
			//if Delta Screening then use the daily Added/updated file and screening existing customers against that list.
			//Delta added/updated hashmap should be merged to global hashmap for online screening and store in DB selialized hashmap
			//For Offline if complete list has to be screened then use latest online hashmap.
			//Delete the daily deleted entry from global hashmap.
			
			WorldCheckListUtility wcl_obj=new WorldCheckListUtility();
			if(ConstantConfiguration.feedType == 1){//Initial Complete file loading
				
				String filepath="";
				//filepath=System.getProperty("user.dir")+"\\WCL\\world-check-day.xml";
				//filepath = "D:\\Builds\\WATCHLIST\\WCL\\world-check-day.xml";
				filepath=ConstantConfiguration.intialFeedFile;
				
				hmWorldchecklist_individual_details=wcl_obj.parseWorldCheckXML(filepath, null,false);
				
				if(hmWorldchecklist_individual_details!=null)
					logger.info("Full List Screen hmWorldchecklist_individual_details--offline--"+hmWorldchecklist_individual_details.size());
				
				worldchecklist_individual_details =splitHashMap(hmWorldchecklist_individual_details);
				wcl_obj.storeFeedToTable(wcl_obj.object2Byte((Object)hmWorldchecklist_individual_details));
				
				if(worldchecklist_individual_details!=null)
					logger.info("Full List Screen No Of Sub Hashmap Worldchecklist_individual_details--offline--"+worldchecklist_individual_details.size());
				
				hmWorldchecklist_individual_details =null;
				filepath=null;
				//if(worldchecklist_individual_details_delta==null) 
					//worldchecklist_individual_details_delta=worldchecklist_individual_details;
				
				if(!ConstantConfiguration.offlineScreening){
					logger.info("Initial World Check list loading Done!, Closing Offline...");
					System.exit(0);
				}
				
			}else{//Incremental feedType == 0
				
				hmWorldchecklist_individual_details_delta = new HashMap<Integer, HashMap<String,ArrayList<String>>>();
				
				//getMemoryStatus("1 Before Read From Table:");
				int noOfdailyIncrementalAddtionFiles=wcl_obj.getAddModifyIncrementalFiles(hmWorldchecklist_individual_details_delta);
				
				hmWorldchecklist_individual_details =(HashMap<Integer, HashMap<String,ArrayList<String>>>)wcl_obj.byte2Object(wcl_obj.fetchFeedFromTable());
			
				if(hmWorldchecklist_individual_details!=null)
					logger.info("Delta List Screen Full List Size Before Delta Addition Merge hmWorldchecklist_individual_details--offline--"+hmWorldchecklist_individual_details.size());

				
				if(hmWorldchecklist_individual_details_delta!=null){
					if(hmWorldchecklist_individual_details==null)
						hmWorldchecklist_individual_details=new HashMap<Integer, HashMap<String,ArrayList<String>>>();
					
					hmWorldchecklist_individual_details.putAll(hmWorldchecklist_individual_details_delta);
				}
				
				if(hmWorldchecklist_individual_details!=null)
					logger.info("Full Size After Delta Addition Merge hmWorldchecklist_individual_details--offline--"+hmWorldchecklist_individual_details.size());
				if(hmWorldchecklist_individual_details_delta!=null)
					logger.info("Delta Size After Delta Addition Merge hmWorldchecklist_individual_details_delta--offline--"+hmWorldchecklist_individual_details_delta.size());

				
				int noOfdailyIncrementalDeletionFiles=wcl_obj.getDeletedIncrementalFiles(hmWorldchecklist_individual_details,hmWorldchecklist_individual_details_delta);
				
				//worldchecklist_individual_details=splitHashMap(hmWorldchecklist_individual_details);
				worldchecklist_individual_details_delta=splitHashMap(hmWorldchecklist_individual_details_delta);
				
				//logger.trace("B4 Put all worldchecklist_individual_details==="+worldchecklist_individual_details.size());
				/*if(worldchecklist_individual_details!=null){
					logger.trace("Offline 275==="+worldchecklist_individual_details.get(275));
					logger.trace("Offline 277==="+worldchecklist_individual_details.get(277));
				}*/
				
				//logger.trace("worldchecklist_individual_details==="+worldchecklist_individual_details.size());
				//logger.trace("worldchecklist_individual_details_delta==="+worldchecklist_individual_details_delta.size());
				//logger.trace("totalMemory111---"+Runtime.getRuntime().totalMemory());
				//logger.trace("freeMemory111---"+Runtime.getRuntime().freeMemory());
				//getMemoryStatus("After Read From Table:");
				logger.info("noOfdailyIncrementalAddtionFiles--"+noOfdailyIncrementalAddtionFiles);
				logger.info("noOfdailyIncrementalDeletionFiles--"+noOfdailyIncrementalDeletionFiles);
				if(noOfdailyIncrementalAddtionFiles>0 || noOfdailyIncrementalDeletionFiles>0){
					wcl_obj.storeFeedToTable(wcl_obj.object2Byte((Object)hmWorldchecklist_individual_details));
					
					if(ConstantConfiguration.restartOnlineScreening){
					
					try {
						String line;
						String ckeckOnlineNameScreeningProcess = "";
						boolean isOnlineNameScreeningRunning = true;

						
						logger.info("Stopping Online Screening.. Please wait for a Minute...");
						Runtime.getRuntime().exec("taskkill /F /IM OnlineNameScreening.exe");
						Thread.sleep(30000);
						
						while(isOnlineNameScreeningRunning){
						 Process p = Runtime.getRuntime().exec("tasklist /nh /fi \"imagename eq OnlineNameScreening.exe\"");
					     BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
					      while ((line = input.readLine()) != null) {
					    	  ckeckOnlineNameScreeningProcess+=line;
					      }
					      ckeckOnlineNameScreeningProcess=ckeckOnlineNameScreeningProcess.toLowerCase();
					      if(ckeckOnlineNameScreeningProcess.contains("onlinenamescreening.exe")){
					    	  Runtime.getRuntime().exec("taskkill /F /IM OnlineNameScreening.exe");
					    	  isOnlineNameScreeningRunning=true;
					      }
					      else
					    	  isOnlineNameScreeningRunning=false;
					      
					      Thread.sleep(30000);
					      }
					      
						logger.info("Starting Online Screening...");
						logger.trace("Starting Online Screening...");
						String command = "cmd /C start " + ConstantConfiguration.onlineRunningPath;
						Runtime.getRuntime().exec(command);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error("", e);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						logger.error("", e);
					}
					
				}
				}
				
				if(hmWorldchecklist_individual_details!=null)
					logger.info("Full Size After Delta Deletion Merge hmWorldchecklist_individual_details--offline--"+hmWorldchecklist_individual_details.size());
				if(hmWorldchecklist_individual_details_delta!=null)
					logger.info("Delta Size After Delta Deletion Merge hmWorldchecklist_individual_details_delta--offline--"+hmWorldchecklist_individual_details_delta.size());
				if(worldchecklist_individual_details_delta!=null)
					logger.info("Delta List Screen No Of Sub Hashmap worldchecklist_individual_details_delta--offline--"+worldchecklist_individual_details_delta.size());

				hmWorldchecklist_individual_details=null;
				hmWorldchecklist_individual_details_delta=null;	
				
				if(!ConstantConfiguration.offlineScreening){
					logger.info("Incremental Addition/Deletion Merge Done!, Closing Offline...");
					System.exit(0);
				}
				
				//getMemoryStatus("After set null Hashmap From Table:");
				//logger.trace("totalMemory222---"+Runtime.getRuntime().totalMemory());
				//logger.trace("freeMemory222----"+Runtime.getRuntime().freeMemory());
			}
		}else if(ConstantConfiguration.wclScreening&&ConstantConfiguration.offlineCompleteListScreening && worldchecklist_individual_details== null){
			WorldCheckListUtility wcl_obj=new WorldCheckListUtility();
			if(ConstantConfiguration.feedType == 1){//Initial Complete file loading
				
				String filepath="";
				//filepath=System.getProperty("user.dir")+"\\WCL\\world-check-day.xml";
				//filepath = "D:\\Builds\\WATCHLIST\\WCL\\world-check-day.xml";
				filepath=ConstantConfiguration.intialFeedFile;
				
				hmWorldchecklist_individual_details=wcl_obj.parseWorldCheckXML(filepath, null,false);
				worldchecklist_individual_details =splitHashMap(hmWorldchecklist_individual_details);
				wcl_obj.storeFeedToTable(wcl_obj.object2Byte((Object)hmWorldchecklist_individual_details));
				
				if(hmWorldchecklist_individual_details!=null)
					logger.info("Full List Screen hmWorldchecklist_individual_details--offline--"+hmWorldchecklist_individual_details.size());
				if(worldchecklist_individual_details!=null)
					logger.info("Full List Screen No Of Sub Hashmap Worldchecklist_individual_details--offline--"+worldchecklist_individual_details.size());
				
				
				hmWorldchecklist_individual_details =null;
				filepath=null;
				//if(worldchecklist_individual_details_delta==null)
					//worldchecklist_individual_details_delta=worldchecklist_individual_details;
				
				if(!ConstantConfiguration.offlineScreening){
					logger.info("Initial World Check list loading Done!, Closing Offline...");
					System.exit(0);
				}
				
			}else{
				hmWorldchecklist_individual_details =(HashMap<Integer, HashMap<String,ArrayList<String>>>)wcl_obj.byte2Object(wcl_obj.fetchFeedFromTable());
			/*	 logger.info("Start Iterat Here");
		         int entity=0;
		         int individuals=0;
		         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : hmWorldchecklist_individual_details.entrySet()) {
		        	  HashMap<String, ArrayList<String>> wclIndividual = entry.getValue();
		        	 if(wclIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))
		        	 	entity++;
		        	 else
		        		 individuals++;
		        		 
		         }
		         logger.info("End Iterat Here");
		         logger.info("Entity Count="+entity);
		         logger.info("Individuals Count="+individuals);*/
				
				worldchecklist_individual_details=splitHashMap(hmWorldchecklist_individual_details);
				
				if(hmWorldchecklist_individual_details!=null)
					logger.info("Full List Screen hmWorldchecklist_individual_details--offline--"+hmWorldchecklist_individual_details.size());
				if(worldchecklist_individual_details!=null)
					logger.info("Full List Screen No Of Sub Hashmap Worldchecklist_individual_details--offline--"+worldchecklist_individual_details.size());

					hmWorldchecklist_individual_details =null;
					
					if(!ConstantConfiguration.offlineScreening){
						logger.info("Initial World Check list loading Done!, Closing Offline...");
						System.exit(0);
					}
			}
		}
		
		
				
	}
	
	public void getMemoryStatus(String s){
		int mb = 1024*1024;
		//Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();
		//Print used memory
		logger.trace(s+"Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);
		//Print free memory
		logger.trace(s+"Free Memory:" + runtime.freeMemory() / mb);
		//Print total available memory
		logger.trace(s+"Total Memory:" + runtime.totalMemory() / mb);
		//Print Maximum available memory
		logger.trace(s+"Max Memory:" + runtime.maxMemory() / mb);
	}
	
	public String deduplication(String xmlreq)
	{
		return parsexmlreq(xmlreq);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String parsexmlreq(String xmlRequest)
	{
		
			String firstname = xmlRequest.split("</First_Name>")[0];
					if((firstname.split("<First_Name>")).length > 1){
						firstname = firstname.split("<First_Name>")[1];
						firstname = xmlEscapeText(firstname);
						xmlRequest = xmlRequest.split("<First_Name>")[0]+"<First_Name>"+firstname+"</First_Name>"+xmlRequest.split("</First_Name>")[1];
					}
					
					String middlename = xmlRequest.split("</Middle_Name>")[0];
					if((middlename.split("<Middle_Name>")).length > 1){
						middlename = middlename.split("<Middle_Name>")[1];
						middlename = xmlEscapeText(middlename);
						xmlRequest = xmlRequest.split("<Middle_Name>")[0]+"<Middle_Name>"+middlename+"</Middle_Name>"+xmlRequest.split("</Middle_Name>")[1];
					}
					
					String lastname = xmlRequest.split("</Last_Name>")[0];
					if((lastname.split("<Last_Name>")).length > 1){
						lastname = lastname.split("<Last_Name>")[1];
						lastname = xmlEscapeText(lastname);
						xmlRequest = xmlRequest.split("<Last_Name>")[0]+"<Last_Name>"+lastname+"</Last_Name>"+xmlRequest.split("</Last_Name>")[1];
					}
		
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		Document doc = null;
		File file = null;
		try 
		{
			/*Write the XML Request to a file for processing*/
			double x = Math.random(); //Generate a random number to append with the filename
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			file = new File(ConstantConfiguration.FlmsDemoPath+"Configuration/xmlreq"+x+".xml");
			//logger.trace("FilePAth==="+file);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(xmlRequest);
            output.close();
            
            String validationResult = "";
            
            try{//Parse the XML file
				doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();
            }catch(SAXParseException e){
            	validationResult = "false~"+e.getMessage();
            }
			
			/*Validate XML Request against the Request Schema File*/ 
			DatabaseAdaptor obj = new DatabaseAdaptor();
			validationResult = obj.validateXMLSchema(
					new File(ConstantConfiguration.FlmsDemoPath+"Configuration/"+ConstantConfiguration.requestSchemaFile), file);
			
			/*If XML Request validation succeeds, then apply deduplication algorithm*/
			if(validationResult.trim().equalsIgnoreCase("true")){
				String reqid = ((Element)((NodeList) doc.getElementsByTagName("Request")).item(0)).getAttribute("id");
				
				/*Checking whether the values in Request XML matches with values in UN List or OFAC List
				and returns a hashmap with list of matched content and score*/
				String firstName = doc.getElementsByTagName("First_Name").item(0).getTextContent().trim();
				String middleName = doc.getElementsByTagName("Middle_Name").item(0).getTextContent().trim();
				String lastName = doc.getElementsByTagName("Last_Name").item(0).getTextContent().trim();
				String streetName = doc.getElementsByTagName("Address1").item(0).getTextContent().trim(); //Street Name
				String doorNo = doc.getElementsByTagName("Address2").item(0).getTextContent().trim(); // Door No.
				String city = doc.getElementsByTagName("City").item(0).getTextContent().trim();
				String state = doc.getElementsByTagName("State").item(0).getTextContent().trim();
				String country = doc.getElementsByTagName("Country").item(0).getTextContent().trim();
				String zipCode = doc.getElementsByTagName("Zipcode").item(0).getTextContent().trim();
				String passportNumber = doc.getElementsByTagName("Passport_Number").item(0).getTextContent().trim().replaceAll("\\s+",""); // Passport No.
				String dateOfBirth = doc.getElementsByTagName("Date_of_Birth").item(0).getTextContent().trim();
				String requestDateTime=doc.getElementsByTagName("Date_time_of_request").item(0).getTextContent().trim();
				String entityType= "1";
				if(doc.getElementsByTagName("Entity_type")!=null){
					try{
					entityType = doc.getElementsByTagName("Entity_type").item(0).getTextContent().trim();
					}catch(Exception e){
						logger.error("", e);
					}
				}
				//String SFDCRecordId=doc.getElementsByTagName("SFDCRecordId").item(0).getTextContent().trim();
				//String IntegrationId=doc.getElementsByTagName("IntegrationId").item(0).getTextContent().trim();
				/* Added Aadhar and Pan number strings NAgaraj 16/11/2016 */
				String aadharNumber = null;
				String panNumber = null;
				if(doc.getElementsByTagName("AADHAR") != null && doc.getElementsByTagName("AADHAR").getLength() > 0)aadharNumber=doc.getElementsByTagName("AADHAR").item(0).getTextContent().trim().replaceAll("\\s+","");
				if(doc.getElementsByTagName("PAN") != null && doc.getElementsByTagName("PAN").getLength()>0) panNumber = doc.getElementsByTagName("PAN").item(0).getTextContent().trim();
				//logger.trace("Input AADHAR Number -- "+aadharNumber);
				// Calling getScreeningInfo() method to do screening against the customer info in request XML
				HashMap<Integer, HashMap<String,String>> sanctionsInfo = 
						getScreeningInfo(firstName,middleName, lastName, streetName, doorNo, city, state, 
								country, zipCode, passportNumber, aadharNumber,panNumber,
								dateOfBirth, reqid, 0,requestDateTime,entityType); //0 - Online
				
				
				//Online WCL Screening Starts
				try{
					if(ConstantConfiguration.wclScreening){
					int counter = 1;
					ArrayList<WorldCheckScreening> wcThreads = new ArrayList<WorldCheckScreening>();
					
					
					for (HashMap<Integer, HashMap<String, ArrayList<String>>> wcDetails : Dedupe.worldchecklist_individual_details) {
						WorldCheckScreening wcScreening = new WorldCheckScreening(firstName,middleName, lastName, streetName, doorNo, city, state, 
								country, zipCode, passportNumber, aadharNumber, panNumber,
								dateOfBirth, reqid, 1, requestDateTime, entityType, wcDetails);
						wcScreening.setName(reqid+"--"+"Thread_"+counter);
						wcScreening.start();
						wcThreads.add(wcScreening);
						counter++;
					}
					for (WorldCheckScreening worldcheckScreening : wcThreads) {
						worldcheckScreening.join();
						
						if(sanctionsInfo==null){
							sanctionsInfo= new	HashMap<Integer, HashMap<String,String>>();
						}
						sanctionsInfo.putAll(worldcheckScreening.getWCSanctionsInfo());
						
						if(matches == null){
							matches = new ArrayList<HashMap<String,String>>();
						}
						//logger.trace("dedupeObj.matches=="+dedupeObj.matches);
						//logger.trace("worldcheckScreening.getMatches()=="+worldcheckScreening.g.etMatches());
						if(worldcheckScreening.getMatches() != null){
							matches.addAll(worldcheckScreening.getMatches());
						}
					}
					}
				}catch(Exception e){
					//NameScreening.printMessage(NameScreening.getStackTrace(e), 1);
					logger.error("", e);
				}
				//Online WCL Screening ends
				
				// Getting the Max.Score
				//double maxScore = getFinalScoreForHashMap(sanctionsInfo, "SCORE");
				Map<Integer, HashMap<String, String>> sortedSanctionsInfo = getOnlyTopNMatches(sanctionsInfo, ConstantConfiguration.topNMatches);
				sanctionsInfo = calculateFinalScore(reqid, 0, requestDateTime, entityType, sanctionsInfo);
				HashMap<String, String> result = getFinalScoreForHashMapFinal(sanctionsInfo, "SCORE");
				double maxScore = 0.0;
				
				if(result!=null&&result.get("MAXSCORE")!=null)
					maxScore=Double.parseDouble(result.get("MAXSCORE"));
				
				
				NodeList nodes = doc.getElementsByTagName("Customer_Info");
				
				// Adding Final_Score to the response
				Element E = doc.createElement("Final_Score");
		        Text textE = doc.createTextNode(df.format(maxScore));
		        E.appendChild(textE);
	        	
		        // Adding Watchlist_Match to the response
	        	Element F = doc.createElement("Watchlist_Match");
	        	Text textF = doc.createTextNode("No Match");
	        	
	        	
	        	if(entityType.equalsIgnoreCase("1"))
	        	{
		        	if(maxScore > ConstantConfiguration.FinalScoreThresholdIndividual){
		        		textF = doc.createTextNode("Match");
		        	}
	        	}
	        	else
	        	{
	        		if(maxScore > ConstantConfiguration.FinalScoreThresholdEntity){
		        		textF = doc.createTextNode("Match");
		        	}
	        	}
		        F.appendChild(textF);
	        	
		    	nodes.item(0).getParentNode().insertBefore(E, nodes.item(0));
		    	nodes.item(0).getParentNode().insertBefore(F, nodes.item(0));
		    	
	         /*for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : ofac_individual_details.entrySet()) {
        	 dataID = entry.getKey();
		      HashMap<String, ArrayList<String>> ofacIndividual = entry.getValue();*/
					      
				// Iterating through the scoring HashMap's and form XML response along with matched content and scores
				/*Set keySet1 = sanctionsInfo.entrySet();
				Iterator iter1 = keySet1.iterator();*/
				for (Entry<Integer, HashMap<String, String>> entry1 : sortedSanctionsInfo.entrySet()) { //Iterating through the list of individuals
					String sanctionID =entry1.getKey().toString();
					
					Element parent = doc.createElement("Sanction_Info");
					Element id = doc.createElement("Sanction_ID");
			        Text textid = doc.createTextNode(sanctionID);
			        id.appendChild(textid);
			        parent.appendChild(id);
			        
			        Element A = doc.createElement("Score");
		        	Text textA = doc.createTextNode(((HashMap<String, String>) entry1.getValue()).get("SCORE"));
		        	A.appendChild(textA);
		        	Element B = doc.createElement("Sanctions_List_Type");
			        Text textB = doc.createTextNode(((HashMap<String, String>) entry1.getValue()).get("LIST_TYPE"));
			        B.appendChild(textB);
			        
			        parent.appendChild(B);
			        parent.appendChild(A);
			        
			        for (Entry<String, String> entry2 : entry1.getValue().entrySet()) {
						//logger.trace("entry2===="+entry2);
						String matchedColumn = entry2.getKey().toString();
						String matchedContent = entry2.getValue().toString();
						//logger.trace("matchedContent==="+matchedContent);
						
						if(!matchedColumn.equalsIgnoreCase("SCORE") && !matchedColumn.equalsIgnoreCase("LIST_TYPE")){
							Element C = doc.createElement("Matched_Column");
					        Text textC = doc.createTextNode(matchedColumn);
					        C.appendChild(textC);
					        Element D = doc.createElement("Matched_Content");
					        Text textD = doc.createTextNode(matchedContent);
					        D.appendChild(textD);
					        
					        parent.appendChild(D);
					        parent.appendChild(C);
						}
					}
					nodes.item(0).getParentNode().insertBefore(parent, nodes.item(0));
				}
			}else{// If schema validation fails, appending error message to the response
		        
				String failureMsg = "Request format not supported";
				if(validationResult.indexOf("~")>-1){
					failureMsg = validationResult.split(":")[1].trim();
					failureMsg = failureMsg.replace("'", "\"");
					failureMsg = failureMsg.replace("{", "");
					failureMsg = failureMsg.replace("}", "");
				}
				if(doc!=null){
					Element A = doc.createElement("Final_Score");
			        Text textA = doc.createTextNode("0.0");
			        A.appendChild(textA);
			        NodeList nodes = doc.getElementsByTagName("Customer_Info");
			        nodes.item(0).getParentNode().insertBefore(A, nodes.item(0));
			        
			        Element B = doc.createElement("Error_Message");
			        Text textB = doc.createTextNode(failureMsg);
			        B.appendChild(textB);
			        nodes.item(0).getParentNode().insertBefore(B, A);
			        
			        Element C = doc.createElement("Error_Code");
			        Text textC = doc.createTextNode("001");
			        C.appendChild(textC);
			        nodes.item(0).getParentNode().insertBefore(C, B);
			        
				}else{// If the XML request is not parsable, will use the following format of response and append to the XML request
					String resp = xmlRequest+"\n<Response>"+
							"\n<Response_Type>Failure</Response_Type>"+
							"\n<Error_Code>001</Error_Code>"+
							"\n<Error_Message>Request could not be parsed. XML format not proper.</Error_Message>"+
							"\n<Final_Score>0.0</Final_Score>"+
							"\n</Response>\n";
					if(file!=null){
						file.delete();
					}
					return resp;//Return the formed response ignoring the addition of other response elements
				}
			}
			
			/*To rename the "Request" node in XML Request to "Response"*/
			NodeList n = doc.getElementsByTagName("Request");
			for (int i = 0; i < n.getLength(); i++) {
			  doc.renameNode(n.item(i), null, "Response");
			//  doc.renameNode(n.item(i+1), null, "Correlation");
			}
	//		 n=doc.getElementsByTagName("APPSOURCE");
	//		 doc.getElementsByTagName("APPSOURCE").item(0).getTextContent();
			 
			/*n=doc.getElementsByTagName(MQReaderStandAlone.CorrelationId);
			logger.info(n);*/
			/*Adding "Response_Type"  Element with 'Success' or 'Failure' message*/
			n = doc.getElementsByTagName("Request_type");
			for (int i = 0; i < n.getLength(); i++) {
			  doc.renameNode(n.item(i), null, "Response_Type");
			  if(validationResult.trim().equalsIgnoreCase("true")){
				  n.item(0).setTextContent("Success");
				  Element C = doc.createElement("Error_Code");
		          Text textC = doc.createTextNode("000");
			      C.appendChild(textC);
			      doc.getElementsByTagName("Final_Score").item(0).getParentNode().insertBefore(C, 
			    		  doc.getElementsByTagName("Final_Score").item(0));
			  }else{
				  n.item(0).setTextContent("Failure");
			  }
			}
			n = doc.getElementsByTagName("Entity_type");
			for (int i = 0; i < n.getLength(); i++) {
				  n.item(0).getTextContent();
				}
			
			
			 /*n = doc.getElementsByTagName("SFDCRecordId");
			 for (int i = 0; i < n.getLength(); i++) {
					  n.item(0).getTextContent();
				}
			 n = doc.getElementsByTagName("IntegrationId");
			 for (int i = 0; i < n.getLength(); i++) {
				 n.item(0).getTextContent();
			}*/
			
			/*Renaming the node "Date_time_of_request" in XML Request to "Date_time_of_response"
			and adding the current date and time as the response time for the processed XML response*/
			n = doc.getElementsByTagName("Date_time_of_request");
			for (int i = 0; i < n.getLength(); i++) {
			  doc.renameNode(n.item(i), null, "Date_time_of_response");
			  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			  SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
			  long currentDateTime = System.currentTimeMillis();
			  String responseDateTime = dateFormat.format(new Date(currentDateTime))+"T"+timeFormat.format(new Date(currentDateTime));
			  n.item(0).setTextContent(responseDateTime);
			}
			
			/*Removing Customer Information from the Response*/
			//Comment the following IF block if customer information is required in XML Response
			if(doc.getElementsByTagName("Customer_Info").getLength()>0){
				Node n1 = doc.getElementsByTagName("Customer_Info").item(0); 
				n1.getParentNode().removeChild(n1);
			}
			
			/*Removing Bank Branch Information from the Response*/
			//Comment the following IF block if Bank Branch information is required in XML Response
			if(doc.getElementsByTagName("Bank_Branch_ID").getLength()>0){
				Node n1 = doc.getElementsByTagName("Bank_Branch_ID").item(0); 
				n1.getParentNode().removeChild(n1);
			}
			
    	   	/*Converting the XML Response String to XML File Format with Indention and writing it to output stream*/
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			xmlRequest = result.getWriter().toString();
			//logger.trace("xmlRequest==="+xmlRequest);
			//logger.info("xmlRequest==="+xmlRequest);
		} 
		catch (ParserConfigurationException e) {
			logger.error("", e);
		} catch (SAXException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} catch (Exception e) {
			logger.error("", e);
		}finally{
			if(file!=null){
				file.delete();//To delete the XML Request file in case of exceptions while processing
			}
		}
		
		/*DatabaseAdaptor adapterObj = new DatabaseAdaptor();
		adapterObj.updateResultsToDB(0);*/
		
		return xmlRequest;
	}
	
	/**
	 * Iterates through the input hashmap and returns the maximum score
	 * @param sanctionsInfo
	 * @return maxScore(double)
	 */
	public double getFinalScoreForHashMap(HashMap<Integer, HashMap<String, String>> sanctionsInfo, String scoreElement){
		double maxScore = 0;
		for (Entry<Integer, HashMap<String, String>> entry : sanctionsInfo.entrySet()) {
			HashMap<String, String> tmp = entry.getValue();
			double score;
			if(tmp.get(scoreElement)!=null){
				score = Double.parseDouble(tmp.get(scoreElement));
				if(score>maxScore){
					maxScore = score;
					
				}
			}
		}
		return maxScore;
	}
	
	public HashMap<String, String>  getFinalScoreForHashMapFinal(HashMap<Integer, HashMap<String, String>> sanctionsInfo, String scoreElement){
		double maxScore = 0;
		String finaldataID="";
		String listType="";
		 HashMap<String, String> individualSanctionInfoFinal=new HashMap<String, String>();
		 for (Entry<Integer, HashMap<String, String>> entry : sanctionsInfo.entrySet()) {
			HashMap<String, String> tmp = (HashMap<String, String>) entry.getValue();
			double score;
			//individualSanctionInfoFinal = new HashMap<String, String>();
			if(tmp.get(scoreElement)!=null){
				score = Double.parseDouble(tmp.get(scoreElement));
				if(score>maxScore){
					maxScore = score;
					finaldataID=entry.getKey().toString();
					listType=tmp.get("LIST_TYPE");
					individualSanctionInfoFinal.put("MAXSCORE",maxScore+"");
					individualSanctionInfoFinal.put("LISTTYPE",listType);
					individualSanctionInfoFinal.put("FINALDATAID",finaldataID);
				}
				
			}
		}
	 return individualSanctionInfoFinal;
	}
	
	/**
	 * This methods return a HashMap which has highest score of individual list from the screeningResult Map
	 * @param screeningResult
	 * @return
	 */
	public HashMap<String, Double> getFinalScoreFromIndividualList(
			HashMap<Integer, HashMap<String, String>> screeningResult) {
		List<Double> ofacScoreList = new ArrayList<>();
		List<Double> unScoreList = new ArrayList<>();
		List<Double> pepScoreList = new ArrayList<>();
		HashMap<String, String> tempMap = null;
		HashMap<String, Double> resultMap = new HashMap<>();

		for (int i : screeningResult.keySet()) {
			tempMap = screeningResult.get(i);
			double tempScore = 0;
			for (String key : tempMap.keySet()) {
				if (key.equalsIgnoreCase("SCORE"))
					tempScore = Double.parseDouble(tempMap.get(key));
				if (key.equalsIgnoreCase("LIST_TYPE")) {
					if (tempMap.get(key).equalsIgnoreCase("OFAC LIST")) {
						ofacScoreList.add(tempScore);
					} else if (tempMap.get(key).equalsIgnoreCase("UN LIST")) {
						unScoreList.add(tempScore);
					} else if (tempMap.get(key).equalsIgnoreCase("PEP LIST"))
						pepScoreList.add(tempScore);
				}
			}
		}

		if (ofacScoreList.size() > 0)
			resultMap.put("OFAC", Collections.max(ofacScoreList));
		if (unScoreList.size() > 0)
			resultMap.put("UN", Collections.max(unScoreList));
		if (pepScoreList.size() > 0)
			resultMap.put("PEP", Collections.max(pepScoreList));

		return resultMap;
	}
	
	
	public static Map<Integer, HashMap<String, String>> getOnlyTopNMatches(HashMap<Integer, HashMap<String, String>> sanctionsInfo, 
			int n) {
		Map<Integer, HashMap<String, String>> sortedSanctionsInfo = new LinkedHashMap<Integer, HashMap<String, String>>();
		double maxScore = 0;
		Map<Double, Integer> scoreMap = new TreeMap<Double, Integer>(Collections.reverseOrder());
		if (sanctionsInfo != null && sanctionsInfo.size() > 0) {
			for (Entry<Integer, HashMap<String, String>> sanctionInfo : sanctionsInfo.entrySet()) {
				HashMap<String, String> info = (HashMap<String, String>) sanctionInfo.getValue();
				if (info.get("SCORE") != null) {
					maxScore = Double.parseDouble(info.get("SCORE"));
					scoreMap.put(maxScore, sanctionInfo.getKey());
				}
			}
		}
		Set set = scoreMap.entrySet();
		Iterator<e> iter = set.iterator();
		int counter = 0;
		while (iter.hasNext()) {
			Map.Entry me = (Map.Entry) iter.next();
			if (counter < n) {
				sortedSanctionsInfo.put(Integer.parseInt(me.getValue().toString()), sanctionsInfo.get(me.getValue()));
				counter++;
			} else {
				break;
			}
		}
		
		/*logger.trace(scoreMap.size()+" SIZES "+sortedSanctionsInfo.size()); 
		for(Map.Entry<Integer, HashMap<String, String>> object : sortedSanctionsInfo.entrySet()) {
			logger.trace("KEY1 -- "+object.getKey()); 
			for(Map.Entry<String, String> object2 : object.getValue().entrySet()) {
				logger.trace("KEY2 -- "+object2.getKey()+" -- VALUE -- "+object2.getValue()); 
			} 
		}*/
		 
		return sortedSanctionsInfo;
	}
	
	public boolean containsCaseInsensitive(ArrayList<String> l, String s){
	     for (String string : l){
	        if (string.equalsIgnoreCase(s)){
	            return true;
	         }
	     }
	    return false;
	}
	
	public String getMatchedContent(ArrayList<String> l, String s){
		String matchedContent = null;
	     for (String string : l){
	        if (string.equalsIgnoreCase(s)){
	        	matchedContent = string;
	         }
	     }
	    return matchedContent;
	}
	public void matchEntityWithLocalList(String firstName,String middleName, String lastName,
			String streetName, String doorNo, String city, String state, String country, String zipCode,
			String passportNumber, String aadharNumber, String panNumber, String dateOfBirth,
			String requestID, int screeningMode,String reuestDateTime,String entityType,HashMap<Integer, HashMap<String, String>> sanctionsInfo)
	{  
		DatabaseAdaptor adapter_obj = new DatabaseAdaptor();
		//logger.trace("MAtching entity with locallist");
		String LIST_TYPE_STR = "LOCAL LIST";
		String versionNo="";
	   int dataID=0;
       HashMap<String, String> individualSanctionInfo=null;
       Utilities utilityObj = new Utilities();
       
		if(locallist_individual_details!=null){
	         double maxFirstnameScore=0;
	         double maxMiddlenameScore=0;
	         double maxLastnameScore=0;
	         double maxAliasnameScore=0;
	         
	         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
	         
	         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : locallist_individual_details.entrySet()) {
	        	 dataID = entry.getKey();
	   	      HashMap<String, ArrayList<String>> ofacIndividual = entry.getValue();
	          double f1score = 0, f3score = 0, m1score = 0, m3score = 0,l1score = 0;
	          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0;
	          double f1lvScore=0,f1SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0;
	          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
	          String f1 = null, f3 = null, m1  = null, m3 = null,l1 = null, l3=null,addr1 = null;
	          String addr2 = null,dob = null,otherID = null;
	          individualSanctionInfo = new HashMap<String, String>();
	          String matchedPassport = null, matchedAadhar = null, matchedPan = null; //added for aadhar,pan Nagaraj 16/11/2016
	          //changes for new name screening algo Nagaraj 2016
		        if(ofacIndividual.get("ENTITY_TYPE") == null || (!ofacIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))) continue;
		        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(ofacIndividual);
		       
				if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
				NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
				
				//if( matchedName == null) continue; //this is a bug. if no name positive it doesn't go to matching other fields NAgaraj 16/11/2016
				
				//end of changes for new name screening algo Nagaraj 2016
	        //end of code changes for new screening Nagaraj 10/11/2016
	          
	          if(streetName!=null && streetName.trim()!="" && ofacIndividual.get("STREETEntity") != null){
		          if(country!=null && country.trim()!="" && ofacIndividual.get("COUNTRYEntity") != null){
		        	  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("COUNTRYEntity"), country).split("##")[0]) > 0.8){
			        	  if(state!=null && state.trim()!="" && ofacIndividual.get("STATE_PROVINCEEntity") != null){ 
			        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("STATE_PROVINCEEntity"), state).split("##")[0]) > 0.8){
				        		  if(city!=null && city.trim()!="" && ofacIndividual.get("CITYEntity") != null){
			    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("CITYEntity"), city).split("##")[0]) > 0.8){
					        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
					        				  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
					        			      String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
					        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
					        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
					        				 
					        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
					        				  addr1Score = 0.75;
					        			  }
					        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
					        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
					        		          String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
					        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
					        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
					        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
					            			  addr2Score = 0.25;
					            		  }
			    	        		  }
				        		  }else{
				        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
				        				  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
				        				  String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
				        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
				        				  addr1=addr1+","+st2+" \n"+st3;
				        				  addr1Score = 0.75/2;
				        			  }
				        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
				        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
				        				  String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
				        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
				        				  addr2=addr2+","+st2+" \n"+st3;
				        				  addr2Score = 0.25/2;
				            		  }
				        		  }
			        		  }
			        	  }else{
			        		  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
			        			  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
			        			  String st2=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
			        			  addr1=addr1+","+st2;
		        				  addr1Score = 0.75/3;
		        			  }
		        			  if(doorNo!=null && doorNo.trim()!="" && 
		        					  containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
		        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
		        				  String st2=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
		        				  addr2=addr2+","+st2;
		            			  addr2Score = 0.25/3;
		            		  }
			        	  }
		        	  }
		          }else{
		        	  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
		        		  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
		        		  addr1Score = 0.75/4;
        			  }
        			  if(doorNo!=null && doorNo.trim()!="" && 
        					  containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
        				  addr2Score = 0.25/4;
            		  }
		          }
	          }
	          if(isValidDOBCheck){
		          if(ofacIndividual.get("DATE_OF_BIRTHEntity") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
		        		  containsCaseInsensitive(ofacIndividual.get("DATE_OF_BIRTHEntity"), dateOfBirth)){
		        	  dob = getMatchedContent(ofacIndividual.get("DATE_OF_BIRTHEntity"), dateOfBirth);
		        	  dobScore = 1;
		          }
	          }
	          if(ofacIndividual.get("PASSPORT_NUMBEREntity") != null && passportNumber != null && passportNumber.trim() != "" && 
	        		  containsCaseInsensitive(ofacIndividual.get("PASSPORT_NUMBEREntity"), passportNumber)){
	        	  matchedPassport = getMatchedContent(ofacIndividual.get("PASSPORT_NUMBEREntity"), passportNumber);
	        	  otherIDScore += 1;
	          }
	          ///logger.trace("AADHAR Number -- "+ofacIndividual.get("AADHAR"));
	          if(ofacIndividual.get("AADHAR") != null && aadharNumber != null && aadharNumber.trim() != "" && 
	        		  containsCaseInsensitive(ofacIndividual.get("AADHAR"), aadharNumber)){
		          //logger.trace("AADHAR Number Matched -- ");

	        	  matchedAadhar = getMatchedContent(ofacIndividual.get("AADHAR"), aadharNumber);
	        	  otherIDScore += 1;
	          }
	          if(ofacIndividual.get("PAN") != null && panNumber != null && panNumber.trim() != "" && 
	        		  containsCaseInsensitive(ofacIndividual.get("PAN"), panNumber)){
	        	  matchedPan = getMatchedContent(ofacIndividual.get("PAN"), panNumber);
	        	  otherIDScore += 1;
	          }	
	          
	        //Start: Changes  for new name screening algo Nagaraj 2016
	          if(matchedName != null)
	          {
				for(NameScore partNameScore : matchedName.getMatch_scores())
				{
					double weightedScore = 0.0;
					if(partNameScore.getField_name().equalsIgnoreCase("FIRST_NAME"))
					{
						f1score = partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
		                f1 = partNameScore.getScreened_entity_value();
		                f1lvScore=partNameScore.getMatch_score();
		                f1SoundexScore=partNameScore.getMatch_score();
		                weightedScore=partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
					}
					else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
					{
						l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
		          		l3lvScore=partNameScore.getMatch_score();
		          		l3SoundexScore=partNameScore.getMatch_score();
		          		l1 = partNameScore.getScreened_entity_value();
		          		weightedScore=partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
					} 
					else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
					{
						m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
		          		m3lvScore=partNameScore.getMatch_score();
		          		m3SoundexScore=partNameScore.getMatch_score();
		          		m1 = partNameScore.getScreened_entity_value();
		          		weightedScore=partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
					}
					//logger.info("Found part name Match --- "+requestID+
					//		"Field "+partNameScore.getField_name()+ " "+
					//		 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
					individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
					
					if(weightedScore>ConstantConfiguration.FNThreshold)
					{
						insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , LIST_TYPE_STR, partNameScore.sanctioned_entity_value, 
								df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","1");
					}
									}
	          }
				// End changes for new name screening algo Nagaraj 2016
                //following 2 lines added to ensure a null in last or first name means this part of the names positive
				//if(firstName == null || firstName == "") { f1score = 1;f3score=1;}
		        //if(lastName == null || lastName == "") { l1score = 1;l3score=1;}
		            
		          
   		  f1score= f1score*ConstantConfiguration.FFWeightage;
   		  f3score= f3score*ConstantConfiguration.FLWeightage;
          m1score= m1score*ConstantConfiguration.MFWeightage;
          m3score= m3score*ConstantConfiguration.MLWeightage;
          l1score= l1score*ConstantConfiguration.LFWeightage;
          l3score= l3score*ConstantConfiguration.LLWeightage;
          
          //logger.trace("Name positive Scores are ---"+f1score+" "+m1score+" "+l1score);
          
            maxFirstnameScore=Math.max(f1score,f3score);
            maxMiddlenameScore=Math.max(m1score,m3score);
            maxLastnameScore=Math.max(l1score,l3score);
          //  maxAliasnameScore=Math.max(alfScore, Math.max(almScore,allScore));
  	        
            if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
  	       {
  	        	
  	        	if(firstName!=null && middleName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("") && maxAliasnameScore<=0.7 && dateOfBirth.equalsIgnoreCase("") && passportNumber.equalsIgnoreCase("") 
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
           // logger.trace("Final score after First Name consideration -- "+finalScore);
            		
            if(maxMiddlenameScore>ConstantConfiguration.MNThreshold)
	          {
	  	    	  
	  	    	if(middleName!=null && firstName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("")  && dateOfBirth.equalsIgnoreCase("") && passportNumber.equalsIgnoreCase("") 
	        			&& streetName.equalsIgnoreCase("") && doorNo.equalsIgnoreCase(""))       
	        	{
	        		if(maxMiddlenameScore==1 || maxMiddlenameScore==0.9 )
	        		{
	        		 finalScore = (maxMiddlenameScore * ConstantConfiguration.MNWeightage);	
	        		 finalScore = finalScore+(1 * ConstantConfiguration.MLWeightage);
	        		}
	        		else
	        		{
	        		 finalScore = (maxMiddlenameScore * ConstantConfiguration.MNWeightage);
	        		}
	        	}
	        	else 
	        	{ 
	        		finalScore = (maxMiddlenameScore * ConstantConfiguration.MNWeightage);
	        	}
	          }
  	      
        
  	    if(maxLastnameScore>ConstantConfiguration.LNThreshold)
        {
  	    	 
     	   if( lastName!=null && middleName.equalsIgnoreCase("") && firstName.equalsIgnoreCase("")  &&  maxAliasnameScore<=0.7 && dateOfBirth.equalsIgnoreCase("") && passportNumber.equalsIgnoreCase("")
     			  && streetName.equalsIgnoreCase("") && doorNo.equalsIgnoreCase(""))           
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
        
  	  //logger.trace("Final score after Last Name consideration -- "+finalScore);
  	 
  //	finalScore= Math.max( finalScore, Math.max( alfScore * ConstantConfiguration.AliasWeightage, Math.max( almScore * ConstantConfiguration.AliasWeightage, allScore * ConstantConfiguration.AliasWeightage) ) );		    
  	   
  	  finalScore= Math.max( finalScore, aliasScore );	 
  	//logger.trace("Final score after full name-- "+finalScore); 
	          if((addr1Score+addr2Score) > ConstantConfiguration.AddressThreshold){
	        	  if(addr1Score>0){
	        		  individualSanctionInfo.put("ADDRESS1", addr1);
	        		  insertToSLS(requestID,"address1",addr1,  "ADDRESS1", LIST_TYPE_STR, addr1, 
								df.format(addr1Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
	        	  }
	        	  if(addr2Score>0){
		        	  individualSanctionInfo.put("ADDRESS2", addr2);
		        	  insertToSLS(requestID,"address2",addr2,"ADDRESS2", LIST_TYPE_STR, addr2, 
								df.format(addr2Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
	        	  }
	        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
	          }
	          if(dobScore > ConstantConfiguration.DOBThreshold){
	        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
	        	  finalScore = finalScore + (dobScore * ConstantConfiguration.DOBWeightage);
	        	  
	        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", LIST_TYPE_STR, dob, 
							df.format(dobScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
	          }
	          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
	        	  if(matchedPassport != null)
	        	  {
		        	  individualSanctionInfo.put("PASSPORT_NUMBER", matchedPassport);
		        	  finalScore = finalScore + (1 * ConstantConfiguration.OtherIDWeightage);
		        	  
		        	  insertToSLS(requestID, "Passport_Number",passportNumber, "PASSPORT_NUMBER", LIST_TYPE_STR, otherID, 
								df.format(1.0),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
	        	  }
	          
		          if(matchedAadhar != null)
	        	  {
		        	  individualSanctionInfo.put("AADHAR", matchedAadhar);
		        	  finalScore = finalScore + (1 * ConstantConfiguration.OtherIDWeightage);
		        	  
		        	  insertToSLS(requestID, "AADHAR",aadharNumber, "AADHAR", "Local List", matchedAadhar, 
								df.format(1.0),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
	        	  }
	        	  if(matchedPan != null)
	        	  {
		        	  individualSanctionInfo.put("PAN", matchedPan);
		        	  finalScore = finalScore + (1 * ConstantConfiguration.OtherIDWeightage);
		        	  
		        	  insertToSLS(requestID, "PAN",panNumber, "PAN", "Local List", matchedPan, 
								df.format(1.0),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
	        	  }
          }
	         
	          if(finalScore > 0){
		          individualSanctionInfo.put("SCORE", finalScore+"");
		          individualSanctionInfo.put("LIST_TYPE", LIST_TYPE_STR);
		          sanctionsInfo.put(dataID, individualSanctionInfo);
	          }
	        }
		}
	}//end of matchEntityWithLocalList
	
	//Start of matchEntityWithWCList
	public void matchEntityWithWCList(String firstName,String middleName, String lastName,
			String streetName, String doorNo, String city, String state, String country, String zipCode,
			String passportNumber, String aadharNumber, String panNumber, String dateOfBirth,
			String requestID, int screeningMode,String reuestDateTime,String entityType,HashMap<Integer, HashMap<String, String>> sanctionsInfo)
	{  
        
		HashMap<Integer, HashMap<String,ArrayList<String>>> worldchecklist=null;
        
        //0-- online
        //1--Ofline
      /*  if(screeningMode==0){
        		worldchecklist=worldchecklist_individual_details;
        }else{
        	if(ConstantConfiguration.offlineCompleteScreening)
        		worldchecklist=worldchecklist_individual_details;
        	else
        		worldchecklist=worldchecklist_individual_details_delta;
        	
        	 //logger.info("offlineCompleteScreening===Screening"+worldchecklist.size());
        }*/
        
        //logger.info("screeningMode===Screening"+screeningMode);
        //logger.info("worldchecklist===Screening"+worldchecklist.size());
        
        
        if(worldchecklist!=null){
        String versionNo="";
 	   int dataID=0;
        HashMap<String, String> individualSanctionInfo=null;
        Utilities utilityObj = new Utilities();
 		
        
         double maxFirstnameScore=0;
         double maxMiddlenameScore=0;
         double maxLastnameScore=0;
         double maxAliasnameScore=0;
         
         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
         
        for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : worldchecklist.entrySet()) {
       	 dataID = entry.getKey();
		      HashMap<String, ArrayList<String>> wclIndividual = entry.getValue();
          individualSanctionInfo = new HashMap<String, String>();
         double f1score = 0,f2score=0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0,aliasDobscore=0;
         double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0,alfscore=0,almscore=0,allscore=0;
         String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null,aliasTypefirst = null;
         String addr2 = null,dob = null,otherID = null,alf=null,alm=null,all=null,aliasDob=null,aliasTypemiddle = null,aliasTypelast = null;     
         double f1lvScore=0,f1SoundexScore=0,f2lvScore=0,f2SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0,m2lvScore=0,m2SoundexScore=0;
         double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l2lvScore=0,l2SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
         double alflvScore=0,alfSoundexScore=0,almlvScore=0,almSoundexScore=0,alllvScore=0,allSoundexScore=0,maxaliasScore=0,alf1Score=0,all1Score=0,alm1Score=0;
       
       //changes for new name screening algo Nagaraj 2016
	     
	       // logger.info("un entity=="+unIndividual);       
         
         if(entityType.equalsIgnoreCase("1"))//For Individuals  No Need to screen with Entity List
 		{
        	 if( wclIndividual.get("ENTITY_TYPE") == null || (wclIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))) continue;
 		}else{//For Non Individuals No Need to screen with Individuals List
	         if( wclIndividual.get("ENTITY_TYPE") == null || (!wclIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))) continue;
 		}
	        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(wclIndividual);
	        
	       // logger.info("Corporate sanctionedEntityNameList for UN==="+sanctionedEntityNameList);
	       
	        /*logger.trace("firstName==="+firstName);
	        logger.trace("middleName==="+middleName);
	        logger.trace("lastName==="+lastName);
	        logger.trace("Account==="+requestID);*/
	        
			if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
			NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
			
		     // logger.info("matchedName UN==="+matchedName);
			
			//if( matchedName == null) continue; //bug. Nagaraj 16/11/2016 
			
			//end of changes for new name screening algo Nagaraj 2016
			
	         /* logger.trace("streetName==================="+streetName);
	          logger.trace("country==================="+country);
	          logger.trace("state==================="+state);
	          logger.trace("city==================="+city);*/
        		          
			if(streetName!=null && streetName.trim()!="" && wclIndividual.get("ADDRESS") != null){
		          if(country!=null && country.trim()!="" && wclIndividual.get("COUNTRY") != null){
		        	  if(Double.parseDouble(refinedSoundexCheckForAddress(wclIndividual.get("COUNTRY"), country).split("##")[0]) > 0.8){
			        	  if(state!=null && state.trim()!="" && wclIndividual.get("STATE_PROVINCE") != null){ 
			        		  if(Double.parseDouble(refinedSoundexCheckForAddress(wclIndividual.get("STATE_PROVINCE"), state).split("##")[0]) > 0.8){
				        		  if(city!=null && city.trim()!="" && wclIndividual.get("CITY") != null){
			    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(wclIndividual.get("CITY"), city).split("##")[0]) > 0.8){
					        			  if(containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
					        		     	  addr1 = getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
					        			      String st2=getMatchedContent(wclIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
					        				  String st4=getMatchedContent(wclIndividual.get("COUNTRY"), country);
					        				 
					        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
					        				  addr1Score = 0.75;
					        			  }
					        			  if(containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
					        				  addr2 = getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
					        		          String st2=getMatchedContent(wclIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
					        				  String st4=getMatchedContent(wclIndividual.get("COUNTRY"), country);
					        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
					            			  addr2Score = 0.25;
					            		  }
			    	        		  }
				        		  }else{
				        			  if(containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
				        				  addr1 = getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
				        				  String st2=getMatchedContent(wclIndividual.get("CITY"), city);
				        				  String st3=getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
				        				  addr1=addr1+","+st2+" \n"+st3;
				        				  addr1Score = 0.75/2;
				        			  }
				        			  if(containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
				        				  addr2 = getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
				        				  String st2=getMatchedContent(wclIndividual.get("CITY"), city);
				        				  String st3=getMatchedContent(wclIndividual.get("STATE_PROVINCE"), state);
				        				  addr2=addr2+","+st2+" \n"+st3;
				        				  addr2Score = 0.25/2;
				            		  }
				        		  }
			        		  }
			        	  }else{
			        		  if(containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
			        			  addr1 = getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
			        			  String st2=getMatchedContent(wclIndividual.get("COUNTRY"), country);
			        			  addr1=addr1+","+st2;
			        			  addr1Score = 0.75/3;
		        			  }
		        			  if(doorNo!=null && doorNo.trim()!="" && 
		        					  containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
		        				  addr2 = getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
		        				  String st2=getMatchedContent(wclIndividual.get("COUNTRY"), country);
		        				  addr2=addr2+","+st2;
		            			  addr2Score = 0.25/3;
		            		  }
			        	  }
		        	  }
		          }else{
		        	  if(containsCaseInsensitive(wclIndividual.get("ADDRESS"), streetName)){
		        		  addr1 = getMatchedContent(wclIndividual.get("ADDRESS"), streetName);
		        		  addr1Score = 0.75/4;
       			  }
       			  if(doorNo!=null && doorNo.trim()!="" && 
       					  containsCaseInsensitive(wclIndividual.get("ADDRESS"), doorNo)){
       				  addr2 = getMatchedContent(wclIndividual.get("ADDRESS"), doorNo);
       				  addr2Score = 0.25/4;
           		  }
		          }
	          }
         if(isValidDOBCheck){
	          if(wclIndividual.get("DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
	        		  containsCaseInsensitive(wclIndividual.get("DATE_OF_BIRTH"), dateOfBirth)){
	        	  dob = getMatchedContent(wclIndividual.get("DATE_OF_BIRTH"), dateOfBirth);
	        	  dobScore = 1;
	        	  versionNo="-";
	          }
	          if(wclIndividual.get("ALIAS_DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
	        		  containsCaseInsensitive(wclIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth)){
	        	  aliasDob = getMatchedContent(wclIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth);
	        	  aliasDobscore = 1;
	        	  versionNo="-";
	          }
         }
         if(wclIndividual.get("PASSPORT_NUMBER") != null && passportNumber != null && passportNumber.trim() != "" && 
       		  containsCaseInsensitive(wclIndividual.get("PASSPORT_NUMBER"), passportNumber)){
       	  otherID = getMatchedContent(wclIndividual.get("PASSPORT_NUMBER"), passportNumber);
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
	                f1 = partNameScore.getScreened_entity_value();
	                f1lvScore=partNameScore.getMatch_score();
	                f1SoundexScore=partNameScore.getMatch_score();
	                weightedScore = f1score;
				}
				else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
				{
					l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
	          		l3lvScore=partNameScore.getMatch_score();
	          		l3SoundexScore=partNameScore.getMatch_score();
	          		l1 = partNameScore.getScreened_entity_value();
	          		 weightedScore = l1score;
				}
				else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
				{
					m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
	          		m3lvScore=partNameScore.getMatch_score();
	          		m3SoundexScore=partNameScore.getMatch_score();
	          		m1 = partNameScore.getScreened_entity_value();
	          		 weightedScore = m1score;
				}
				//logger.info("Found part name Match --- "+requestID+
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
       }
      }
	}//end of matchEntityWithWCList
	
	
	public HashMap<Integer, HashMap<String, String>> calculateFinalScore(String requestID, int screeningMode, String reuestDateTime, String entityType,
			HashMap<Integer, HashMap<String, String>> sanctionsInfo) {
		double maxScore = getFinalScoreForHashMap(sanctionsInfo, "SCORE");
		HashMap<String, String> result = getFinalScoreForHashMapFinal(
				sanctionsInfo, "SCORE");
		String Listtype = "";
		String watchListMatch = "No Match";
		
		double finalThresholdScore=0.0;
		if(entityType.equalsIgnoreCase("1"))
				finalThresholdScore=ConstantConfiguration.FinalScoreThresholdIndividual;
			else
				finalThresholdScore=ConstantConfiguration.FinalScoreThresholdEntity;
		
		if (maxScore > finalThresholdScore) {
			watchListMatch = "Match";
		} else {
			watchListMatch = "No Match";
		}

		if (result.get("LISTTYPE") != null
				&& !result.get("LISTTYPE").equalsIgnoreCase("")) {
			Listtype = result.get("LISTTYPE");
		} else {
			Listtype = "-";
		}
		String FINALDATAID = "";
		if (result.get("FINALDATAID") != null
				&& !result.get("FINALDATAID").equalsIgnoreCase("")) {
			FINALDATAID = result.get("FINALDATAID");
		} else {
			FINALDATAID = "-";
		}
		// Code for only Score
		/*
		 * DatabaseAdaptor.insertToSLS(requestID,"-","-", "-",Listtype, "-",
		 * df.format(maxScore),"-",FINALDATAID+"", watchListMatch,
		 * screeningMode,reuestDateTime,"-","-","1",finalMaxScore);
		 */

		// end ofCode for only Score*

		insertToSLS(requestID, "-", "-", "-", Listtype, "-",df.format(maxScore),
				"-", FINALDATAID + "", watchListMatch, screeningMode,
				reuestDateTime, "-", "-", entityType);
		
		/*logger.trace("BEFORE "+matches.size());
		logger.trace("INSERTING FINAL SCORE ................. \n"+requestID+" *** "+"-"+" *** "+"-"+" *** "+"-"+" *** "+
				Listtype+" *** "+"-"+" *** "+finalMaxScore+" *** "+
				"-"+" *** "+FINALDATAID + ""+" *** "+watchListMatch+" *** "+screeningMode+" *** "+
				reuestDateTime+" *** "+"-"+" *** "+"-"+" *** "+entityType+" *** "+ "");
		logger.trace("AFTER "+matches.size());*/
		return sanctionsInfo;

	}
	
	/**
	 * Compares the input parameters against the values of UN and OFAC lists. 
	 * Generate scores based on redefined soundex algorithm and inserts data 
	 * into the summary table if there is a positive
	 * 
	 * @param firstName
	 * @param lastName
	 * @param streetName
	 * @param doorNo
	 * @param city
	 * @param state
	 * @param country
	 * @param zipCode
	 * @param passportNumber
	 * @param dateOfBirth
	 * @param requestID
	 * @param screeningMode
	 * 			<br>&emsp;0 - Online 	
	 * 			<br>&emsp;1 - Offline
	 * @return HashMap<Integer, HashMap<String, String>>
	 * 			HashMap will be used to prepare the xml response for online screening
	 */
	/* Added arguments and code for aadhar and pan numbers for local list*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<Integer, HashMap<String, String>> getScreeningInfo(String firstName,String middleName, String lastName,
			String streetName, String doorNo, String city, String state, String country, String zipCode,
			String passportNumber, String aadharNumber, String panNumber,String dateOfBirth,
			String requestID, int screeningMode,String reuestDateTime,String entityType) {
		   String versionNo="";
		   int dataID=0;
	          HashMap<String, String> individualSanctionInfo=null;
	          Utilities utilityObj = new Utilities();
		HashMap<Integer, HashMap<String, String>> sanctionsInfo = new HashMap<Integer, HashMap<String, String>>();
		
		 //logger.trace("Customer Full Name -- "+firstName +" -- "+ middleName+" -- "+lastName+"\n\n");
		
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
		
		if(entityType.equalsIgnoreCase("p") || entityType.equalsIgnoreCase("i"))
		{
			//individual
			if(ofac_individual_details!=null){
		         double maxFirstnameScore=0;
		         double maxMiddlenameScore=0;
		         double maxLastnameScore=0;
		         
		         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
		         
		         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : ofac_individual_details.entrySet()) {
		        	 dataID = entry.getKey();
				      HashMap<String, ArrayList<String>> ofacIndividual = entry.getValue();
		           
		        //   logger.info("Matching ofac list entity no. -- "+sanctionedEntityCount);
		           
		          double f1score = 0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0;
		          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0;
		          double alfScore=0,almScore=0,allScore=0,f1lvScore=0,f1SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0;
		          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
		          String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null;
		          String addr2 = null,dob = null,otherID = null,al=null,alf=null,alm=null,all=null,aliasString=null;
		          double alf1Score = 0,alf2Score = 0,alm1Score = 0,alm2Score = 0,all1Score = 0,all2Score = 0,alf1wScore = 0,alf2wScore = 0;
		          String alf1 = null,alf2 = null,alm1 = null,alm2 = null,all1 = null,all2 = null,alfw1 = null,alfw2 = null;
		        individualSanctionInfo = new HashMap<String, String>();
		        
		      
		       	
		        //changes for new name screening algo Nagaraj 2016
		        if(ofacIndividual.get("ENTITY_TYPE") != null && ofacIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY")) continue;
		        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(ofacIndividual);
		       
				if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
				
				NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
				
				//if( matchedName == null) continue; //bug. NAgaraj 16/11/2016
				
				//end of changes for new name screening algo Nagaraj 2016
				
				
		          if(streetName!=null && streetName.trim()!="" && ofacIndividual.get("STREET") != null){
			          if(country!=null && country.trim()!="" && ofacIndividual.get("COUNTRY") != null){
			        	  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("COUNTRY"), country).split("##")[0]) > 0.8){
				        	  if(state!=null && state.trim()!="" && ofacIndividual.get("STATE_PROVINCE") != null){ 
				        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("STATE_PROVINCE"), state).split("##")[0]) > 0.8){
					        		  if(city!=null && city.trim()!="" && ofacIndividual.get("CITY") != null){
				    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("CITY"), city).split("##")[0]) > 0.8){
						        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
						        				  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
						        			      String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
						        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
						        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
						        				 
						        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
						        				  addr1Score = 0.75;
						        			  }
						        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
						        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
						        		          String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
						        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
						        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
						        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
						            			  addr2Score = 0.25;
						            		  }
				    	        		  }
					        		  }else{
					        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
					        				  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
					        				  String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
					        				  addr1=addr1+","+st2+" \n"+st3;
					        				  addr1Score = 0.75/2;
					        			  }
					        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
					        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
					        				  String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
					        				  addr2=addr2+","+st2+" \n"+st3;
					        				  addr2Score = 0.25/2;
					            		  }
					        		  }
				        		  }
				        	  }else{
				        		  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
				        			  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
				        			  String st2=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
				        			  addr1=addr1+","+st2;
			        				  addr1Score = 0.75/3;
			        			  }
			        			  if(doorNo!=null && doorNo.trim()!="" && 
			        					  containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
			        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
			        				  String st2=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
			        				  addr2=addr2+","+st2;
			            			  addr2Score = 0.25/3;
			            		  }
				        	  }
			        	  }
			          }else{
			        	  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
			        		  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
			        		  addr1Score = 0.75/4;
	        			  }
	        			  if(doorNo!=null && doorNo.trim()!="" && 
	        					  containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
	        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
	        				  addr2Score = 0.25/4;
	            		  }
			          }
		          }
		          if(isValidDOBCheck){
			          if(ofacIndividual.get("DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
			        		  containsCaseInsensitive(ofacIndividual.get("DATE_OF_BIRTH"), dateOfBirth)){
			        	  dob = getMatchedContent(ofacIndividual.get("DATE_OF_BIRTH"), dateOfBirth);
			        	  dobScore = 1;
			          }
		          }
		          if(ofacIndividual.get("PASSPORT_NUMBER") != null && passportNumber != null && passportNumber.trim() != "" && 
		        		  containsCaseInsensitive(ofacIndividual.get("PASSPORT_NUMBER"), passportNumber)){
		        	  otherID = getMatchedContent(ofacIndividual.get("PASSPORT_NUMBER"), passportNumber);
		        	  otherIDScore = 1;
		          }
		          
		          
	  
		          
		          //Final Score Calculation
		          //Start: Changes  for new name screening algo Nagaraj 2016
		          if( matchedName != null)
		          {
					for(NameScore partNameScore : matchedName.getMatch_scores())
					{
						double weightedScore = 0.0;
						if(partNameScore.getField_name().equalsIgnoreCase("FIRST_NAME"))
						{
							f1score = partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
			                f1 = partNameScore.getScreened_entity_value();
			                f1lvScore=partNameScore.getMatch_score();
			                f1SoundexScore=partNameScore.getMatch_score();
			                weightedScore = f1score;
						}
						else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
						{
							l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
			          		l3lvScore=partNameScore.getMatch_score();
			          		l3SoundexScore=partNameScore.getMatch_score();
			          		l1 = partNameScore.getScreened_entity_value();
			          		weightedScore = l1score;
						}
						else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
						{
							m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
			          		m3lvScore=partNameScore.getMatch_score();
			          		m3SoundexScore=partNameScore.getMatch_score();
			          		m1 = partNameScore.getScreened_entity_value();
			          		weightedScore = m1score;
						}
						//logger.info("Found part name Match --- "+requestID+
						//		"Field "+partNameScore.getField_name()+ " "+
						//		 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
						individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
						if(weightedScore>ConstantConfiguration.FNThreshold)
						{
						insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "OFAC List", partNameScore.sanctioned_entity_value, 
								df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","1");
						}
						}
		          }
					// End changes for new name screening algo Nagaraj 2016
					
		      //end of commented out code - NAgaraj 11/10/2016
	   		  f1score= f1score*ConstantConfiguration.FFWeightage;
	   		  f3score= f3score*ConstantConfiguration.FLWeightage;
	          m1score= m1score*ConstantConfiguration.MFWeightage;
	          m3score= m3score*ConstantConfiguration.MLWeightage;
	          l1score= l1score*ConstantConfiguration.LFWeightage;
	          l3score= l3score*ConstantConfiguration.LLWeightage;
	         
	            maxFirstnameScore=Math.max(f1score,f3score);
	            maxMiddlenameScore=Math.max(m1score,m3score);
	            maxLastnameScore=Math.max(l1score,l3score);
	            
	         
	            
	            
	            
	            
	          //  maxAliasnameScore=Math.max(alfScore, Math.max(almScore,allScore));
	  	        
	            if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
	  	       {
	  	        	/**
	  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
	  	        	 */
	  	        	if(firstName!=null && middleName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("") )       
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
	  	      if(maxMiddlenameScore>ConstantConfiguration.MNThreshold)
	          {
	  	    	  /**
	  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
	  	        	 */
	  	    	  
	  	    	if(middleName!=null && firstName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("")  )       
  	        	{
  	        		if(maxMiddlenameScore==1 || maxMiddlenameScore==0.9 )
  	        		{
  	        		 finalScore = (maxMiddlenameScore * ConstantConfiguration.MNWeightage);	
  	        		 finalScore = finalScore+(1 * ConstantConfiguration.MLWeightage);
  	        		}
  	        		else
  	        		{
  	        		 finalScore = finalScore+(maxMiddlenameScore * ConstantConfiguration.MNWeightage);
  	        		}
  	        	}
  	        	else 
  	        	{ 
  	        		finalScore = finalScore+(maxMiddlenameScore * ConstantConfiguration.MNWeightage);
  	        	}
	          }
	        
	  	    if(maxLastnameScore>ConstantConfiguration.LNThreshold)
	        {
	  	    	/**
  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
  	        	 */
	  	    	 
	     	   if( lastName!=null && middleName.equalsIgnoreCase("") && firstName.equalsIgnoreCase("") )           
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
	  		
	  //	finalScore= Math.max( finalScore, Math.max( alfScore * ConstantConfiguration.AliasWeightage, Math.max( almScore * ConstantConfiguration.AliasWeightage, allScore * ConstantConfiguration.AliasWeightage) ) );		    
	  	   
	  	  finalScore= Math.max( finalScore, aliasScore );	 
	  	    
		          if((addr1Score+addr2Score) > ConstantConfiguration.AddressThreshold){
		        	  if(addr1Score>0){
		        		  individualSanctionInfo.put("ADDRESS1", addr1);
		        		  insertToSLS(requestID,"address1",addr1,  "ADDRESS1", "OFAC List", addr1, 
									df.format(addr1Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		        	  }
		        	  if(addr2Score>0){
			        	  individualSanctionInfo.put("ADDRESS2", addr2);
			        	  insertToSLS(requestID,"address2",addr2,"ADDRESS2", "OFAC List", addr2, 
									df.format(addr2Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		        	  }
		        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
		          }
		          if(dobScore > ConstantConfiguration.DOBThreshold){
		        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
		        	  finalScore = finalScore + (dobScore * ConstantConfiguration.DOBWeightage);
		        	  
		        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "OFAC List", dob, 
								df.format(dobScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		          }
		          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
		        	  individualSanctionInfo.put("PASSPORT_NUMBER", otherID);
		        	  finalScore = finalScore + (otherIDScore * ConstantConfiguration.OtherIDWeightage);
		        	  
		        	  insertToSLS(requestID, "Passport_Number",passportNumber, "PASSPORT_NUMBER", "OFAC List", otherID, 
								df.format(otherIDScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		          }
		         
		          if(finalScore > 0){
			          individualSanctionInfo.put("SCORE", finalScore+"");
			          individualSanctionInfo.put("LIST_TYPE", "OFAC LIST");
			          sanctionsInfo.put(dataID, individualSanctionInfo);
		          }
		        }
			}
	
			if(locallist_individual_details!=null){
			         double maxFirstnameScore=0;
			         double maxMiddlenameScore=0;
			         double maxLastnameScore=0;
			         //logger.trace("Screening against local list -- ");
			         
			         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
			         
			         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : locallist_individual_details.entrySet()) {
			        	 dataID = entry.getKey();
					      HashMap<String, ArrayList<String>> ofacIndividual = entry.getValue();
			          double f1score = 0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0;
			          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0;
			          double alfScore=0,almScore=0,allScore=0,f1lvScore=0,f1SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0;
			          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
			          String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null;
			          String addr2 = null,dob = null,otherID = null,al=null,alf=null,alm=null,all=null,aliasString=null;
			          double alf1Score = 0,alf2Score = 0,alm1Score = 0,alm2Score = 0,all1Score = 0,all2Score = 0,alf1wScore = 0,alf2wScore = 0;
			          String alf1 = null,alf2 = null,alm1 = null,alm2 = null,all1 = null,all2 = null,alfw1 = null,alfw2 = null;
			        individualSanctionInfo = new HashMap<String, String>();
			        String matchedPassport = null, matchedAadhar = null, matchedPan = null;
			          

			            	
			        //changes for new name screening algo Nagaraj 2016
			        if(ofacIndividual.get("ENTITY_TYPE") != null && ofacIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY")) continue;
			        //logger.trace("Screening against local list Individual -- "+ofacIndividual.get("AADHAR"));
			        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(ofacIndividual);
			       
					if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
					NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
					
					//if( matchedName == null) continue;
					
					//end of changes for new name screening algo Nagaraj 2016
					
					
			          if(streetName!=null && streetName.trim()!="" && ofacIndividual.get("STREET") != null){
				          if(country!=null && country.trim()!="" && ofacIndividual.get("COUNTRY") != null){
				        	  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("COUNTRY"), country).split("##")[0]) > 0.8){
					        	  if(state!=null && state.trim()!="" && ofacIndividual.get("STATE_PROVINCE") != null){ 
					        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("STATE_PROVINCE"), state).split("##")[0]) > 0.8){
						        		  if(city!=null && city.trim()!="" && ofacIndividual.get("CITY") != null){
					    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("CITY"), city).split("##")[0]) > 0.8){
							        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
							        				  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
							        			      String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
							        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
							        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
							        				 
							        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
							        				  addr1Score = 0.75;
							        			  }
							        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
							        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
							        		          String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
							        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
							        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
							        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
							            			  addr2Score = 0.25;
							            		  }
					    	        		  }
						        		  }else{
						        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
						        				  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
						        				  String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
						        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
						        				  addr1=addr1+","+st2+" \n"+st3;
						        				  addr1Score = 0.75/2;
						        			  }
						        			  if(containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
						        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
						        				  String st2=getMatchedContent(ofacIndividual.get("CITY"), city);
						        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCE"), state);
						        				  addr2=addr2+","+st2+" \n"+st3;
						        				  addr2Score = 0.25/2;
						            		  }
						        		  }
					        		  }
					        	  }else{
					        		  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
					        			  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
					        			  String st2=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
					        			  addr1=addr1+","+st2;
				        				  addr1Score = 0.75/3;
				        			  }
				        			  if(doorNo!=null && doorNo.trim()!="" && 
				        					  containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
				        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
				        				  String st2=getMatchedContent(ofacIndividual.get("COUNTRY"), country);
				        				  addr2=addr2+","+st2;
				            			  addr2Score = 0.25/3;
				            		  }
					        	  }
				        	  }
				          }else{
				        	  if(containsCaseInsensitive(ofacIndividual.get("STREET"), streetName)){
				        		  addr1 = getMatchedContent(ofacIndividual.get("STREET"), streetName);
				        		  addr1Score = 0.75/4;
		        			  }
		        			  if(doorNo!=null && doorNo.trim()!="" && 
		        					  containsCaseInsensitive(ofacIndividual.get("STREET"), doorNo)){
		        				  addr2 = getMatchedContent(ofacIndividual.get("STREET"), doorNo);
		        				  addr2Score = 0.25/4;
		            		  }
				          }
			          }
			          if(isValidDOBCheck){
				          if(ofacIndividual.get("DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
				        		  containsCaseInsensitive(ofacIndividual.get("DATE_OF_BIRTH"), dateOfBirth)){
				        	  dob = getMatchedContent(ofacIndividual.get("DATE_OF_BIRTH"), dateOfBirth);
				        	  dobScore = 1;
				          }
			          }
			          //logger.trace("PassPort Number 11-- "+ofacIndividual.get("PASSPORT_NUMBER"));
			          if(ofacIndividual.get("PASSPORT_NUMBER") != null && passportNumber != null && passportNumber.trim() != "" && 
			        		  containsCaseInsensitive(ofacIndividual.get("PASSPORT_NUMBER"), passportNumber)){
			        	  matchedPassport = getMatchedContent(ofacIndividual.get("PASSPORT_NUMBER"), passportNumber);
			        	  otherIDScore += 1;
			          }
			          //logger.trace("AADHAR Number -- "+ofacIndividual.get("AADHAR"));
			          if(ofacIndividual.get("AADHAR") != null && aadharNumber != null && aadharNumber.trim() != "" && 
			        		  containsCaseInsensitive(ofacIndividual.get("AADHAR"), aadharNumber)){
				          //logger.trace("AADHAR Number Matched -- ");

			        	  matchedAadhar = getMatchedContent(ofacIndividual.get("AADHAR"), aadharNumber);
			        	  otherIDScore += 1;
			          }
			          if(ofacIndividual.get("PAN") != null && panNumber != null && panNumber.trim() != "" && 
			        		  containsCaseInsensitive(ofacIndividual.get("PAN"), panNumber)){
			        	  matchedPan = getMatchedContent(ofacIndividual.get("PAN"), panNumber);
			        	  otherIDScore += 1;
			          }	          
			          //Final Score Calculation
			          //Start: Changes  for new name screening algo Nagaraj 2016
			          if(matchedName != null) //added condition NAgaraj 16/11/2016
			          {
						for(NameScore partNameScore : matchedName.getMatch_scores())
						{
							double weightedScore = 0.0;
							if(partNameScore.getField_name().equalsIgnoreCase("FIRST_NAME"))
							{
								f1score = partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
				                f1 = partNameScore.getScreened_entity_value();
				                f1lvScore=partNameScore.getMatch_score();
				                f1SoundexScore=partNameScore.getMatch_score();
				                weightedScore = f1score;
							}
							else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
							{
								l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
				          		l3lvScore=partNameScore.getMatch_score();
				          		l3SoundexScore=partNameScore.getMatch_score();
				          		l1 = partNameScore.getScreened_entity_value();
				          		weightedScore = l1score;
							}
							else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
							{
								m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
				          		m3lvScore=partNameScore.getMatch_score();
				          		m3SoundexScore=partNameScore.getMatch_score();
				          		m1 = partNameScore.getScreened_entity_value();
				          		weightedScore = m1score;
							}
							//logger.info("Found part name Match --- "+requestID+
								//	"Field "+partNameScore.getField_name()+ " "+
								//	 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
							individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
							
							if(weightedScore>ConstantConfiguration.FNThreshold)
							{
								insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "Local List", partNameScore.sanctioned_entity_value, 
										df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","1");

							}				}
			          }	
						// End changes for new name screening algo Nagaraj 2016
						
			      //end of commented out code - NAgaraj 11/10/2016
		   		  f1score= f1score*ConstantConfiguration.FFWeightage;
		   		  f3score= f3score*ConstantConfiguration.FLWeightage;
		          m1score= m1score*ConstantConfiguration.MFWeightage;
		          m3score= m3score*ConstantConfiguration.MLWeightage;
		          l1score= l1score*ConstantConfiguration.LFWeightage;
		          l3score= l3score*ConstantConfiguration.LLWeightage;
		         
		            maxFirstnameScore=Math.max(f1score,f3score);
		            maxMiddlenameScore=Math.max(m1score,m3score);
		            maxLastnameScore=Math.max(l1score,l3score);
		          //  maxAliasnameScore=Math.max(alfScore, Math.max(almScore,allScore));
		  	        
		            if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
		  	       {  /**
		  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
		  	        	 */
		  	        	
		  	        	if(firstName!=null && middleName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("")  )       
		  	        	{
		  	        		if(maxFirstnameScore==1 || maxFirstnameScore==0.9 )
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
	  	        		 finalScore = finalScore+(maxMiddlenameScore * ConstantConfiguration.MNWeightage);
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
		  	    	 
		     	   if( lastName!=null && middleName.equalsIgnoreCase("") && firstName.equalsIgnoreCase("")  )           
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
		        
		           

		  	 
		  //	finalScore= Math.max( finalScore, Math.max( alfScore * ConstantConfiguration.AliasWeightage, Math.max( almScore * ConstantConfiguration.AliasWeightage, allScore * ConstantConfiguration.AliasWeightage) ) );		    
		  	   
		  	  finalScore= Math.max( finalScore, aliasScore );	 
	          // logger.trace("Final Score after full name --"+finalScore); 

			          if((addr1Score+addr2Score) > ConstantConfiguration.AddressThreshold){
			        	  if(addr1Score>0){
			        		  individualSanctionInfo.put("ADDRESS1", addr1);
			        		  insertToSLS(requestID,"address1",addr1,  "ADDRESS1", "Local List", addr1, 
										df.format(addr1Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
			        	  }
			        	  if(addr2Score>0){
				        	  individualSanctionInfo.put("ADDRESS2", addr2);
				        	  insertToSLS(requestID,"address2",addr2,"ADDRESS2", "Local List", addr2, 
										df.format(addr2Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
			        	  }
			        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
			          }
			          if(dobScore > ConstantConfiguration.DOBThreshold){
			        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
			        	  finalScore = finalScore + (dobScore * ConstantConfiguration.DOBWeightage);
			        	  
			        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "Local List", dob, 
									df.format(dobScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
			          }
			          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
			        	  //logger.trace("Passport_LOcal==="+matchedPassport);
			        	  if(matchedPassport != null)
			        	  {
				        	  individualSanctionInfo.put("PASSPORT_NUMBER", otherID);
				        	  finalScore = finalScore + (otherIDScore * ConstantConfiguration.OtherIDWeightage);
				        	  
				        	  insertToSLS(requestID, "Passport_Number",passportNumber, "PASSPORT_NUMBER", "Local List", otherID, 
										df.format(otherIDScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
			        	  }
			        	  //logger.trace("PassportNumber_local===="+passportNumber);
			        	  if(matchedAadhar != null)
			        	  {
				        	  individualSanctionInfo.put("AADHAR", matchedAadhar);
				        	  finalScore = finalScore + (1 * ConstantConfiguration.OtherIDWeightage);
				        	  
				        	  insertToSLS(requestID, "AADHAR",aadharNumber, "AADHAR", "Local List", matchedAadhar, 
										df.format(1.0),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
			        	  }
			        	  if(matchedPan != null)
			        	  {
				        	  individualSanctionInfo.put("PAN", matchedPan);
				        	  finalScore = finalScore + (1 * ConstantConfiguration.OtherIDWeightage);
				        	  
				        	  insertToSLS(requestID, "PAN",panNumber, "PAN", "Local List", matchedPan, 
										df.format(1.0),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
			        	  }
			          }
			         
			          if(finalScore > 0){
				          individualSanctionInfo.put("SCORE", finalScore+"");
				          individualSanctionInfo.put("LIST_TYPE", "LOCAL LIST");
				          sanctionsInfo.put(dataID, individualSanctionInfo);
			          }
			        }
				}//end of local list processing
			
		if(un_individual_details != null){
	        double maxFirstnameScore=0;
	         double maxMiddlenameScore=0;
	         double maxLastnameScore=0;
	         double maxAliasnameScore=0;
	         
	         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
	         
	         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : un_individual_details.entrySet()) {
	        	 dataID = entry.getKey();
			      HashMap<String, ArrayList<String>> unIndividual = entry.getValue();
	           individualSanctionInfo = new HashMap<String, String>();
	          double f1score = 0,f2score=0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0,aliasDobscore=0;
	          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0,alfscore=0,almscore=0,allscore=0;
	          String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null,aliasTypefirst = null;
	          String addr2 = null,dob = null,otherID = null,alf=null,alm=null,all=null,aliasDob=null,aliasTypemiddle = null,aliasTypelast = null;     
	          double f1lvScore=0,f1SoundexScore=0,f2lvScore=0,f2SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0,m2lvScore=0,m2SoundexScore=0;
	          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l2lvScore=0,l2SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
	          double alflvScore=0,alfSoundexScore=0,almlvScore=0,almSoundexScore=0,alllvScore=0,allSoundexScore=0,maxaliasScore=0,alf1Score=0,all1Score=0,alm1Score=0;
	          //changes for new screening NAgaraj 10/11/2016
	        //changes for new name screening algo Nagaraj 2016
		        
		        if(unIndividual.get("ENTITY_TYPE") != null && unIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY")) continue;
		              ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(unIndividual);
		        
//		        logger.info("sanctionedEntityNameList for UN==="+sanctionedEntityNameList);
		       
				if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
				NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
				
			     // logger.info("matchedName UN==="+matchedName);
				
				//if( matchedName == null) continue; //bug. NAgaraj 16/11/2016
				
				//end of changes for new name screening algo Nagaraj 2016
								  
	          if(streetName!=null && streetName.trim()!="" && unIndividual.get("STREET") != null){
		          if(country!=null && country.trim()!="" && unIndividual.get("COUNTRY") != null){
		        	  if(Double.parseDouble(refinedSoundexCheckForAddress(unIndividual.get("COUNTRY"), country).split("##")[0]) > 0.8){
			        	  if(state!=null && state.trim()!="" && unIndividual.get("STATE_PROVINCE") != null){ 
			        		  if(Double.parseDouble(refinedSoundexCheckForAddress(unIndividual.get("STATE_PROVINCE"), state).split("##")[0]) > 0.8){
				        		  if(city!=null && city.trim()!="" && unIndividual.get("CITY") != null){
			    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(unIndividual.get("CITY"), city).split("##")[0]) > 0.8){
					        			  if(containsCaseInsensitive(unIndividual.get("STREET"), streetName)){
					        		     	  addr1 = getMatchedContent(unIndividual.get("STREET"), streetName);
					        			      String st2=getMatchedContent(unIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCE"), state);
					        				  String st4=getMatchedContent(unIndividual.get("COUNTRY"), country);
					        				 
					        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
					        				  addr1Score = 0.75;
					        			  }
					        			  if(containsCaseInsensitive(unIndividual.get("STREET"), doorNo)){
					        				  addr2 = getMatchedContent(unIndividual.get("STREET"), doorNo);
					        		          String st2=getMatchedContent(unIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCE"), state);
					        				  String st4=getMatchedContent(unIndividual.get("COUNTRY"), country);
					        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
					            			  addr2Score = 0.25;
					            		  }
			    	        		  }
				        		  }else{
				        			  if(containsCaseInsensitive(unIndividual.get("STREET"), streetName)){
				        				  addr1 = getMatchedContent(unIndividual.get("STREET"), streetName);
				        				  String st2=getMatchedContent(unIndividual.get("CITY"), city);
				        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCE"), state);
				        				  addr1=addr1+","+st2+" \n"+st3;
				        				  addr1Score = 0.75/2;
				        			  }
				        			  if(containsCaseInsensitive(unIndividual.get("STREET"), doorNo)){
				        				  addr2 = getMatchedContent(unIndividual.get("STREET"), doorNo);
				        				  String st2=getMatchedContent(unIndividual.get("CITY"), city);
				        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCE"), state);
				        				  addr2=addr2+","+st2+" \n"+st3;
				        				  addr2Score = 0.25/2;
				            		  }
				        		  }
			        		  }
			        	  }else{
			        		  if(containsCaseInsensitive(unIndividual.get("STREET"), streetName)){
			        			  addr1 = getMatchedContent(unIndividual.get("STREET"), streetName);
			        			  String st2=getMatchedContent(unIndividual.get("COUNTRY"), country);
			        			  addr1=addr1+","+st2;
			        			  addr1Score = 0.75/3;
		        			  }
		        			  if(doorNo!=null && doorNo.trim()!="" && 
		        					  containsCaseInsensitive(unIndividual.get("STREET"), doorNo)){
		        				  addr2 = getMatchedContent(unIndividual.get("STREET"), doorNo);
		        				  String st2=getMatchedContent(unIndividual.get("COUNTRY"), country);
		        				  addr2=addr2+","+st2;
		            			  addr2Score = 0.25/3;
		            		  }
			        	  }
		        	  }
		          }else{
		        	  if(containsCaseInsensitive(unIndividual.get("STREET"), streetName)){
		        		  addr1 = getMatchedContent(unIndividual.get("STREET"), streetName);
		        		  addr1Score = 0.75/4;
        			  }
        			  if(doorNo!=null && doorNo.trim()!="" && 
        					  containsCaseInsensitive(unIndividual.get("STREET"), doorNo)){
        				  addr2 = getMatchedContent(unIndividual.get("STREET"), doorNo);
        				  addr2Score = 0.25/4;
            		  }
		          }
	          }
	          if(isValidDOBCheck){
		          if(unIndividual.get("DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
		        		  containsCaseInsensitive(unIndividual.get("DATE_OF_BIRTH"), dateOfBirth)){
		        	  dob = getMatchedContent(unIndividual.get("DATE_OF_BIRTH"), dateOfBirth);
		        	  dobScore = 1;
		        	  versionNo=unIndividual.get("VERSIONNUM").toString().replaceAll("\\[", "").replaceAll("\\]","");
		          }
		          if(unIndividual.get("ALIAS_DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
		        		  containsCaseInsensitive(unIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth)){
		        	  aliasDob = getMatchedContent(unIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth);
		        	  aliasDobscore = 1;
		        	  versionNo=unIndividual.get("VERSIONNUM").toString().replaceAll("\\[", "").replaceAll("\\]","");
		          }
	          }
	          if(unIndividual.get("PASSPORT_NUMBER") != null && passportNumber != null && passportNumber.trim() != "" && 
	        		  containsCaseInsensitive(unIndividual.get("PASSPORT_NUMBER"), passportNumber)){
	        	  otherID = getMatchedContent(unIndividual.get("PASSPORT_NUMBER"), passportNumber);
	        	  otherIDScore = 1;
	        	  versionNo=unIndividual.get("VERSIONNUM").toString().replaceAll("\\[", "").replaceAll("\\]","");
	          }
	        
	          
		       
			  //first name       
	        //Start: Changes  for new name screening algo Nagaraj 2016
	          if( matchedName != null)
	          {
				for(NameScore partNameScore : matchedName.getMatch_scores())
				{
					double weightedScore = 0.0;
					if(partNameScore.getField_name().equalsIgnoreCase("FIRST_NAME"))
					{
						f1score = partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
		                f1 = partNameScore.getScreened_entity_value();
		                f1lvScore=partNameScore.getMatch_score();
		                f1SoundexScore=partNameScore.getMatch_score();
		                weightedScore = f1score;
					}
					else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
					{
						l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
		          		l3lvScore=partNameScore.getMatch_score();
		          		l3SoundexScore=partNameScore.getMatch_score();
		          		l1 = partNameScore.getScreened_entity_value();
		          		 weightedScore = l1score;
					}
					else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
					{
						m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
		          		m3lvScore=partNameScore.getMatch_score();
		          		m3SoundexScore=partNameScore.getMatch_score();
		          		m1 = partNameScore.getScreened_entity_value();
		          		 weightedScore = m1score;
					}
					individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
					
					//logger.info("Found part name Match --- "+requestID+
					//		"Field "+partNameScore.getField_name()+ " "+
					//		 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
					if(weightedScore>ConstantConfiguration.FNThreshold)
					{
					insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "UN List", partNameScore.sanctioned_entity_value, 
							df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","1");
					}
					}
	          }
				// End changes for new name screening algo Nagaraj 2016
				
	       //end of changes for new screening NAgaraj 10//12016
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
		         maxaliasScore=Math.max( alf1Score , Math.max( alm1Score , all1Score) );
		         
		  	        if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
		  	       {/**
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
		  	    	if(middleName!=null && firstName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("")  )       
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
		          
		         
		           if(maxaliasScore >=ConstantConfiguration.ALWeightage)
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
		        		  insertToSLS(requestID,"address1",addr1, "ADDRESS1", "UN List", addr1, 
									df.format(addr1Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		        	  }
		        	  if(addr2Score>0){
			        	  individualSanctionInfo.put("ADDRESS2", addr2);
			        	  insertToSLS(requestID,"address2",addr2,  "ADDRESS2", "UN List", addr2, 
									df.format(addr2Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		        	  }
		        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
		          }
		          if(dobScore > ConstantConfiguration.DOBThreshold){
		        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
		        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "UN List", dob, 
								df.format(dobScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		          }
		          if(aliasDobscore > ConstantConfiguration.DOBThreshold){
		        	  individualSanctionInfo.put("DATE_OF_BIRTH",aliasDob);
		        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "Alias_Date_of_Birth", "UN List", aliasDob, 
								df.format(aliasDobscore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		          }
		          
		          dobScore= Math.max( dobScore * ConstantConfiguration.DOBWeightage ,aliasDobscore * ConstantConfiguration.DOBWeightage);   
		          finalScore = finalScore + dobScore;
		          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
		        	  individualSanctionInfo.put("PASSPORT_NUMBER", otherID);
		        	  finalScore = finalScore + (otherIDScore * ConstantConfiguration.OtherIDWeightage);
		        	  
		        	  insertToSLS(requestID,"Passport_Number",passportNumber, "PASSPORT_NUMBER", "UN List", otherID, 
								df.format(otherIDScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		          }
	          if(finalScore > 0){
		          individualSanctionInfo.put("SCORE", finalScore+"");
		          individualSanctionInfo.put("LIST_TYPE", "UN LIST");
		          sanctionsInfo.put(dataID, individualSanctionInfo);
	          }
	        }
		}
		
		if(pep_individual_details != null){

	        double maxFirstnameScore=0;
	         double maxMiddlenameScore=0;
	         double maxLastnameScore=0;
	         double maxAliasnameScore=0;
	         
	         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
	         
	         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : pep_individual_details.entrySet()) {
	        	 dataID = entry.getKey();
			      HashMap<String, ArrayList<String>> pepIndividual = entry.getValue();
	           individualSanctionInfo = new HashMap<String, String>();
	          double f1score = 0,f2score=0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0,aliasDobscore=0;
	          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0,alfscore=0,almscore=0,allscore=0;
	          String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null,aliasTypefirst = null;
	          String addr2 = null,dob = null,otherID = null,alf=null,alm=null,all=null,aliasDob=null,aliasTypemiddle = null,aliasTypelast = null;     
	          double f1lvScore=0,f1SoundexScore=0,f2lvScore=0,f2SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0,m2lvScore=0,m2SoundexScore=0;
	          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l2lvScore=0,l2SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
	          double alflvScore=0,alfSoundexScore=0,almlvScore=0,almSoundexScore=0,alllvScore=0,allSoundexScore=0,maxaliasScore=0,alf1Score=0,all1Score=0,alm1Score=0;
	          //changes for new screening NAgaraj 10/11/2016
	        //changes for new name screening algo Nagaraj 2016
		        
		        if(pepIndividual.get("ENTITY_TYPE") != null && pepIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY")) continue;
		              ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(pepIndividual);
		        
//		        logger.info("sanctionedEntityNameList for UN==="+sanctionedEntityNameList);
		       
				if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
				NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
				
			     // logger.info("matchedName UN==="+matchedName);
				
				//if( matchedName == null) continue; //bug. NAgaraj 16/11/2016
				
				//end of changes for new name screening algo Nagaraj 2016
								  
	          if(streetName!=null && streetName.trim()!="" && pepIndividual.get("STREET") != null){
		          if(country!=null && country.trim()!="" && pepIndividual.get("COUNTRY") != null){
		        	  if(Double.parseDouble(refinedSoundexCheckForAddress(pepIndividual.get("COUNTRY"), country).split("##")[0]) > 0.8){
			        	  if(state!=null && state.trim()!="" && pepIndividual.get("STATE_PROVINCE") != null){ 
			        		  if(Double.parseDouble(refinedSoundexCheckForAddress(pepIndividual.get("STATE_PROVINCE"), state).split("##")[0]) > 0.8){
				        		  if(city!=null && city.trim()!="" && pepIndividual.get("CITY") != null){
			    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(pepIndividual.get("CITY"), city).split("##")[0]) > 0.8){
					        			  if(containsCaseInsensitive(pepIndividual.get("STREET"), streetName)){
					        		     	  addr1 = getMatchedContent(pepIndividual.get("STREET"), streetName);
					        			      String st2=getMatchedContent(pepIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(pepIndividual.get("STATE_PROVINCE"), state);
					        				  String st4=getMatchedContent(pepIndividual.get("COUNTRY"), country);
					        				 
					        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
					        				  addr1Score = 0.75;
					        			  }
					        			  if(containsCaseInsensitive(pepIndividual.get("STREET"), doorNo)){
					        				  addr2 = getMatchedContent(pepIndividual.get("STREET"), doorNo);
					        		          String st2=getMatchedContent(pepIndividual.get("CITY"), city);
					        				  String st3=getMatchedContent(pepIndividual.get("STATE_PROVINCE"), state);
					        				  String st4=getMatchedContent(pepIndividual.get("COUNTRY"), country);
					        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
					            			  addr2Score = 0.25;
					            		  }
			    	        		  }
				        		  }else{
				        			  if(containsCaseInsensitive(pepIndividual.get("STREET"), streetName)){
				        				  addr1 = getMatchedContent(pepIndividual.get("STREET"), streetName);
				        				  String st2=getMatchedContent(pepIndividual.get("CITY"), city);
				        				  String st3=getMatchedContent(pepIndividual.get("STATE_PROVINCE"), state);
				        				  addr1=addr1+","+st2+" \n"+st3;
				        				  addr1Score = 0.75/2;
				        			  }
				        			  if(containsCaseInsensitive(pepIndividual.get("STREET"), doorNo)){
				        				  addr2 = getMatchedContent(pepIndividual.get("STREET"), doorNo);
				        				  String st2=getMatchedContent(pepIndividual.get("CITY"), city);
				        				  String st3=getMatchedContent(pepIndividual.get("STATE_PROVINCE"), state);
				        				  addr2=addr2+","+st2+" \n"+st3;
				        				  addr2Score = 0.25/2;
				            		  }
				        		  }
			        		  }
			        	  }else{
			        		  if(containsCaseInsensitive(pepIndividual.get("STREET"), streetName)){
			        			  addr1 = getMatchedContent(pepIndividual.get("STREET"), streetName);
			        			  String st2=getMatchedContent(pepIndividual.get("COUNTRY"), country);
			        			  addr1=addr1+","+st2;
			        			  addr1Score = 0.75/3;
		        			  }
		        			  if(doorNo!=null && doorNo.trim()!="" && 
		        					  containsCaseInsensitive(pepIndividual.get("STREET"), doorNo)){
		        				  addr2 = getMatchedContent(pepIndividual.get("STREET"), doorNo);
		        				  String st2=getMatchedContent(pepIndividual.get("COUNTRY"), country);
		        				  addr2=addr2+","+st2;
		            			  addr2Score = 0.25/3;
		            		  }
			        	  }
		        	  }
		          }else{
		        	  if(containsCaseInsensitive(pepIndividual.get("STREET"), streetName)){
		        		  addr1 = getMatchedContent(pepIndividual.get("STREET"), streetName);
		        		  addr1Score = 0.75/4;
        			  }
        			  if(doorNo!=null && doorNo.trim()!="" && 
        					  containsCaseInsensitive(pepIndividual.get("STREET"), doorNo)){
        				  addr2 = getMatchedContent(pepIndividual.get("STREET"), doorNo);
        				  addr2Score = 0.25/4;
            		  }
		          }
	          }
	          if(isValidDOBCheck){
		          if(pepIndividual.get("DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
		        		  containsCaseInsensitive(pepIndividual.get("DATE_OF_BIRTH"), dateOfBirth)){
		        	  dob = getMatchedContent(pepIndividual.get("DATE_OF_BIRTH"), dateOfBirth);
		        	  dobScore = 1;
		          }
	          }
	        
			  //first name       
	        //Start: Changes  for new name screening algo Nagaraj 2016
	          if( matchedName != null)
	          {
				for(NameScore partNameScore : matchedName.getMatch_scores())
				{
					double weightedScore = 0.0;
					if(partNameScore.getField_name().equalsIgnoreCase("FIRST_NAME"))
					{
						f1score = partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
		                f1 = partNameScore.getScreened_entity_value();
		                f1lvScore=partNameScore.getMatch_score();
		                f1SoundexScore=partNameScore.getMatch_score();
		                weightedScore = f1score;
					}
					else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
					{
						l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
		          		l3lvScore=partNameScore.getMatch_score();
		          		l3SoundexScore=partNameScore.getMatch_score();
		          		l1 = partNameScore.getScreened_entity_value();
		          		 weightedScore = l1score;
					}
					else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
					{
						m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
		          		m3lvScore=partNameScore.getMatch_score();
		          		m3SoundexScore=partNameScore.getMatch_score();
		          		m1 = partNameScore.getScreened_entity_value();
		          		 weightedScore = m1score;
					}
					individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
					
					//logger.info("Found part name Match --- "+requestID+
					//		"Field "+partNameScore.getField_name()+ " "+
					//		 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
					if(weightedScore>ConstantConfiguration.FNThreshold)
					{
					insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "PEP List", partNameScore.sanctioned_entity_value, 
							df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","1");
					}
					}
	          }
				// End changes for new name screening algo Nagaraj 2016
				
	       //end of changes for new screening NAgaraj 10//12016
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
		         maxaliasScore=Math.max( alf1Score , Math.max( alm1Score , all1Score) );
		         
		  	        if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
		  	       {/**
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
		  	        		finalScore =finalScore+ (maxFirstnameScore * ConstantConfiguration.FNWeightage);
		  	        	}
		  	        	
		  	       }
		  	         
		  	      if(maxMiddlenameScore>ConstantConfiguration.MNThreshold)
		          {/**
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
	  	        		 finalScore = finalScore+(maxMiddlenameScore * ConstantConfiguration.MNWeightage);
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
		          
		         
		           if(maxaliasScore >=ConstantConfiguration.ALWeightage)
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
		        		  insertToSLS(requestID,"address1",addr1, "ADDRESS1", "PEP List", addr1, 
									df.format(addr1Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		        	  }
		        	  if(addr2Score>0){
			        	  individualSanctionInfo.put("ADDRESS2", addr2);
			        	  insertToSLS(requestID,"address2",addr2,  "ADDRESS2", "PEP List", addr2, 
									df.format(addr2Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		        	  }
		        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
		          }
		          if(dobScore > ConstantConfiguration.DOBThreshold){
		        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
		        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "PEP List", dob, 
								df.format(dobScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","1");
		          }
		          
		          dobScore= Math.max( dobScore * ConstantConfiguration.DOBWeightage ,aliasDobscore * ConstantConfiguration.DOBWeightage);   
		          finalScore = finalScore + dobScore;
	          if(finalScore > 0){
		          individualSanctionInfo.put("SCORE", finalScore+"");
		          individualSanctionInfo.put("LIST_TYPE", "PEP List");
		          sanctionsInfo.put(dataID, individualSanctionInfo);
	          }
	        }
		
		}
		
		
		//if(worldchecklist_individual_details_delta != null){
			
			/*matchEntityWithWCList( firstName, middleName,  lastName,
					 streetName,  doorNo,  city,  state,  country,  zipCode,
					 passportNumber, aadharNumber,panNumber, dateOfBirth,
					 requestID,  screeningMode, reuestDateTime, entityType, sanctionsInfo);*/
		//}
		
		
		
	/*		double maxScore = getFinalScoreForHashMap(sanctionsInfo, "SCORE");
			String watchListMatch = "No Match";
			if(maxScore > ConstantConfiguration.FinalScoreThresholdIndividual){
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
		    
		    insertToSLS(requestID,"-","-", "-",Listtype, "-", 
					df.format(maxScore),"-",FINALDATAID+"", watchListMatch, screeningMode,reuestDateTime,"-","-","1");
		   // logger.info("Finished Inserting to SLS final score "+requestID);
		*/
		
	}
		
	//Screening corporate customers
		else
			{
			
			//ofac entity
				if(ofac_individual_details!=null){
			         double maxFirstnameScore=0;
			         double maxMiddlenameScore=0;
			         double maxLastnameScore=0;
			         double maxAliasnameScore=0;
			         
			         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
			         
			         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : ofac_individual_details.entrySet()) {
			        	 dataID = entry.getKey();
					      HashMap<String, ArrayList<String>> ofacIndividual = entry.getValue();
			          double f1score = 0, f3score = 0, m1score = 0, m3score = 0,l1score = 0;
			          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0;
			          double f1lvScore=0,f1SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0;
			          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
			          String f1 = null, f3 = null, m1  = null, m3 = null,l1 = null, l3=null,addr1 = null;
			          String addr2 = null,dob = null,otherID = null;
			          individualSanctionInfo = new HashMap<String, String>();
			          //changes for new name screening algo Nagaraj 2016
				        if(ofacIndividual.get("ENTITY_TYPE") == null || (!ofacIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))) continue;
				        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(ofacIndividual);
				       
						if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
						NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
						
						//if( matchedName == null) continue; //bug. Nagaraj 16/11/2016
						
						//end of changes for new name screening algo Nagaraj 2016
			        //end of code changes for new screening Nagaraj 10/11/2016
			          
			          if(streetName!=null && streetName.trim()!="" && ofacIndividual.get("STREETEntity") != null){
				          if(country!=null && country.trim()!="" && ofacIndividual.get("COUNTRYEntity") != null){
				        	  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("COUNTRYEntity"), country).split("##")[0]) > 0.8){
					        	  if(state!=null && state.trim()!="" && ofacIndividual.get("STATE_PROVINCEEntity") != null){ 
					        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("STATE_PROVINCEEntity"), state).split("##")[0]) > 0.8){
						        		  if(city!=null && city.trim()!="" && ofacIndividual.get("CITYEntity") != null){
					    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(ofacIndividual.get("CITYEntity"), city).split("##")[0]) > 0.8){
							        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
							        				  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
							        			      String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
							        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
							        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
							        				 
							        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
							        				  addr1Score = 0.75;
							        			  }
							        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
							        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
							        		          String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
							        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
							        				  String st4=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
							        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
							            			  addr2Score = 0.25;
							            		  }
					    	        		  }
						        		  }else{
						        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
						        				  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
						        				  String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
						        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
						        				  addr1=addr1+","+st2+" \n"+st3;
						        				  addr1Score = 0.75/2;
						        			  }
						        			  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
						        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
						        				  String st2=getMatchedContent(ofacIndividual.get("CITYEntity"), city);
						        				  String st3=getMatchedContent(ofacIndividual.get("STATE_PROVINCEEntity"), state);
						        				  addr2=addr2+","+st2+" \n"+st3;
						        				  addr2Score = 0.25/2;
						            		  }
						        		  }
					        		  }
					        	  }else{
					        		  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
					        			  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
					        			  String st2=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
					        			  addr1=addr1+","+st2;
				        				  addr1Score = 0.75/3;
				        			  }
				        			  if(doorNo!=null && doorNo.trim()!="" && 
				        					  containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
				        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
				        				  String st2=getMatchedContent(ofacIndividual.get("COUNTRYEntity"), country);
				        				  addr2=addr2+","+st2;
				            			  addr2Score = 0.25/3;
				            		  }
					        	  }
				        	  }
				          }else{
				        	  if(containsCaseInsensitive(ofacIndividual.get("STREETEntity"), streetName)){
				        		  addr1 = getMatchedContent(ofacIndividual.get("STREETEntity"), streetName);
				        		  addr1Score = 0.75/4;
		        			  }
		        			  if(doorNo!=null && doorNo.trim()!="" && 
		        					  containsCaseInsensitive(ofacIndividual.get("STREETEntity"), doorNo)){
		        				  addr2 = getMatchedContent(ofacIndividual.get("STREETEntity"), doorNo);
		        				  addr2Score = 0.25/4;
		            		  }
				          }
			          }
			          if(isValidDOBCheck){
				          if(ofacIndividual.get("DATE_OF_BIRTHEntity") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
				        		  containsCaseInsensitive(ofacIndividual.get("DATE_OF_BIRTHEntity"), dateOfBirth)){
				        	  dob = getMatchedContent(ofacIndividual.get("DATE_OF_BIRTHEntity"), dateOfBirth);
				        	  dobScore = 1;
				          }
			          }
			          if(ofacIndividual.get("PASSPORT_NUMBEREntity") != null && passportNumber != null && passportNumber.trim() != "" && 
			        		  containsCaseInsensitive(ofacIndividual.get("PASSPORT_NUMBEREntity"), passportNumber)){
			        	  otherID = getMatchedContent(ofacIndividual.get("PASSPORT_NUMBEREntity"), passportNumber);
			        	  otherIDScore = 1;
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
				                f1 = partNameScore.getScreened_entity_value();
				                f1lvScore=partNameScore.getMatch_score();
				                f1SoundexScore=partNameScore.getMatch_score();
				                weightedScore=partNameScore.getMatch_score()*ConstantConfiguration.FFWeightage;
							}
							else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
							{
								l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
				          		l3lvScore=partNameScore.getMatch_score();
				          		l3SoundexScore=partNameScore.getMatch_score();
				          		l1 = partNameScore.getScreened_entity_value();
				          		weightedScore=partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
							}
							else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
							{
								m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
				          		m3lvScore=partNameScore.getMatch_score();
				          		m3SoundexScore=partNameScore.getMatch_score();
				          		m1 = partNameScore.getScreened_entity_value();
				          		weightedScore=partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
							}
							//logger.info("Found part name Match --- "+requestID+
							//		"Field "+partNameScore.getField_name()+ " "+
							//		 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
							individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
							if(weightedScore>ConstantConfiguration.FNThreshold)
							{
							
							insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "OFAC List", partNameScore.sanctioned_entity_value, 
									df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","2");
							}
							}
			          }
						// End changes for new name screening algo Nagaraj 2016
		  
			          
		   		  f1score= f1score*ConstantConfiguration.FFWeightage;
		   		  f3score= f3score*ConstantConfiguration.FLWeightage;
		          m1score= m1score*ConstantConfiguration.MFWeightage;
		          m3score= m3score*ConstantConfiguration.MLWeightage;
		          l1score= l1score*ConstantConfiguration.LFWeightage;
		          l3score= l3score*ConstantConfiguration.LLWeightage;
		         

		            maxFirstnameScore=Math.max(f1score,f3score);
		            maxMiddlenameScore=Math.max(m1score,m3score);
		            maxLastnameScore=Math.max(l1score,l3score);
		          //  maxAliasnameScore=Math.max(alfScore, Math.max(almScore,allScore));
		  	        
		            if(maxFirstnameScore>ConstantConfiguration.FNThreshold)
		  	       {
		            	/**
		  	        	 * For Name Matches will get Match fix removed address Check on 03 May 17
		  	        	 */
		  	        	if(firstName!=null && middleName.equalsIgnoreCase("") && lastName.equalsIgnoreCase("") && maxAliasnameScore<=0.7 )       
		  	        	{
		  	        		if(maxFirstnameScore==1 || maxFirstnameScore==0.9 )
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
		  	        		 finalScore = finalScore+(maxMiddlenameScore * ConstantConfiguration.MNWeightage);
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
		  	    	 
		     	   if( lastName!=null && middleName.equalsIgnoreCase("") && firstName.equalsIgnoreCase("")  &&  maxAliasnameScore<=0.7 )           
			        	{
			        		if(maxLastnameScore==1 || maxLastnameScore==0.9)
			        		{
			        		  finalScore = (maxLastnameScore * ConstantConfiguration.LNWeightage);	 
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
		        
		  		
		  	 
		  //	finalScore= Math.max( finalScore, Math.max( alfScore * ConstantConfiguration.AliasWeightage, Math.max( almScore * ConstantConfiguration.AliasWeightage, allScore * ConstantConfiguration.AliasWeightage) ) );		    
		  	   
		  	  finalScore= Math.max( finalScore, aliasScore );	 
		  	    
			          if((addr1Score+addr2Score) > ConstantConfiguration.AddressThreshold){
			        	  if(addr1Score>0){
			        		  individualSanctionInfo.put("ADDRESS1", addr1);
			        		  insertToSLS(requestID,"address1",addr1,  "ADDRESS1", "OFAC List", addr1, 
										df.format(addr1Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			        	  }
			        	  if(addr2Score>0){
				        	  individualSanctionInfo.put("ADDRESS2", addr2);
				        	  insertToSLS(requestID,"address2",addr2,"ADDRESS2", "OFAC List", addr2, 
										df.format(addr2Score),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			        	  }
			        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
			          }
			          if(dobScore > ConstantConfiguration.DOBThreshold){
			        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
			        	  finalScore = finalScore + (dobScore * ConstantConfiguration.DOBWeightage);
			        	  
			        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "OFAC List", dob, 
									df.format(dobScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			          }
			          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
			        	  individualSanctionInfo.put("PASSPORT_NUMBER", otherID);
			        	  finalScore = finalScore + (otherIDScore * ConstantConfiguration.OtherIDWeightage);
			        	  
			        	  insertToSLS(requestID, "Passport_Number",passportNumber, "PASSPORT_NUMBER", "OFAC List", otherID, 
									df.format(otherIDScore),"-", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			          }
			         
			          if(finalScore > 0){
				          individualSanctionInfo.put("SCORE", finalScore+"");
				          individualSanctionInfo.put("LIST_TYPE", "OFAC LIST");
				          sanctionsInfo.put(dataID, individualSanctionInfo);
			          }
			        }
				}
	//local list entity	
				if(locallist_individual_details != null){
					matchEntityWithLocalList( firstName, middleName,  lastName,
							 streetName,  doorNo,  city,  state,  country,  zipCode,
							 passportNumber, aadharNumber,panNumber, dateOfBirth,
							 requestID,  screeningMode, reuestDateTime, entityType, sanctionsInfo);
				}
				
	//UN Entity
				
			if(un_individual_details != null){
				
		        double maxFirstnameScore=0;
		         double maxMiddlenameScore=0;
		         double maxLastnameScore=0;
		         double maxAliasnameScore=0;
		         
		         boolean isValidDOBCheck = isValidDOB(dateOfBirth);
		         
		         for (Map.Entry<Integer, HashMap<String, ArrayList<String>>> entry : un_individual_details.entrySet()) {
		        	 dataID = entry.getKey();
				      HashMap<String, ArrayList<String>> unIndividual = entry.getValue();
		           individualSanctionInfo = new HashMap<String, String>();
		          double f1score = 0,f2score=0, f3score = 0, m1score = 0, m2score = 0, m3score = 0,l1score = 0, l2score = 0,aliasDobscore=0;
		          double l3score = 0,addr1Score = 0, addr2Score = 0, dobScore = 0,otherIDScore = 0,aliasScore=0, finalScore = 0,alfscore=0,almscore=0,allscore=0;
		          String f1 = null, f2=null, f3 = null, m1  = null, m2 = null,m3 = null,l1 = null,l2=null,l3=null,addr1 = null,aliasTypefirst = null;
		          String addr2 = null,dob = null,otherID = null,alf=null,alm=null,all=null,aliasDob=null,aliasTypemiddle = null,aliasTypelast = null;     
		          double f1lvScore=0,f1SoundexScore=0,f2lvScore=0,f2SoundexScore=0,f3lvScore=0,f3SoundexScore=0,m1lvScore=0,m2lvScore=0,m2SoundexScore=0;
		          double m1SoundexScore=0,m3lvScore=0,m3SoundexScore=0,l1lvScore=0,l1SoundexScore=0,l2lvScore=0,l2SoundexScore=0,l3lvScore=0,l3SoundexScore=0;
		          double alflvScore=0,alfSoundexScore=0,almlvScore=0,almSoundexScore=0,alllvScore=0,allSoundexScore=0,maxaliasScore=0,alf1Score=0,all1Score=0,alm1Score=0;
		        
		        //changes for new name screening algo Nagaraj 2016
			     
			       // logger.info("un entity=="+unIndividual);       
		          
			        if( unIndividual.get("ENTITY_TYPE") == null || (!unIndividual.get("ENTITY_TYPE").get(0).equalsIgnoreCase("ENTITY"))) continue;
			       
			        ArrayList<CFName> sanctionedEntityNameList = utilityObj.getEntityNames(unIndividual);
			        
			       // logger.info("Corporate sanctionedEntityNameList for UN==="+sanctionedEntityNameList);
			       
					if(sanctionedEntityNameList == null || sanctionedEntityNameList.size() == 0) continue;
					NameMatch matchedName = utilityObj.matchNames(firstName,middleName,lastName, sanctionedEntityNameList,entityType);
					
				     // logger.info("matchedName UN==="+matchedName);
					
					//if( matchedName == null) continue; //bug. Nagaraj 16/11/2016 
					
					//end of changes for new name screening algo Nagaraj 2016
		         		          
		          if(streetName!=null && streetName.trim()!="" && unIndividual.get("STREETEntity") != null){
			          if(country!=null && country.trim()!="" && unIndividual.get("COUNTRYEntity") != null){
			        	  if(Double.parseDouble(refinedSoundexCheckForAddress(unIndividual.get("COUNTRYEntity"), country).split("##")[0]) > 0.8){
				        	  if(state!=null && state.trim()!="" && unIndividual.get("STATE_PROVINCEEntity") != null){ 
				        		  if(Double.parseDouble(refinedSoundexCheckForAddress(unIndividual.get("STATE_PROVINCEEntity"), state).split("##")[0]) > 0.8){
					        		  if(city!=null && city.trim()!="" && unIndividual.get("CITYENtity") != null){
				    	        		  if(Double.parseDouble(refinedSoundexCheckForAddress(unIndividual.get("CITYENtity"), city).split("##")[0]) > 0.8){
						        			  if(containsCaseInsensitive(unIndividual.get("STREETEntity"), streetName)){
						        		     	  addr1 = getMatchedContent(unIndividual.get("STREETEntity"), streetName);
						        			      String st2=getMatchedContent(unIndividual.get("CITYENtity"), city);
						        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCEEntity"), state);
						        				  String st4=getMatchedContent(unIndividual.get("COUNTRYEntity"), country);
						        				 
						        				  addr1=addr1+","+st2+" \n"+st3+" \n"+st4;
						        				  addr1Score = 0.75;
						        			  }
						        			  if(containsCaseInsensitive(unIndividual.get("STREETEntity"), doorNo)){
						        				  addr2 = getMatchedContent(unIndividual.get("STREETEntity"), doorNo);
						        		          String st2=getMatchedContent(unIndividual.get("CITYENtity"), city);
						        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCEEntity"), state);
						        				  String st4=getMatchedContent(unIndividual.get("COUNTRYEntity"), country);
						        				  addr2=addr2+","+st2+" \n"+st3+" \n"+st4;
						            			  addr2Score = 0.25;
						            		  }
				    	        		  }
					        		  }else{
					        			  if(containsCaseInsensitive(unIndividual.get("STREETEntity"), streetName)){
					        				  addr1 = getMatchedContent(unIndividual.get("STREETEntity"), streetName);
					        				  String st2=getMatchedContent(unIndividual.get("CITYENtity"), city);
					        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCEEntity"), state);
					        				  addr1=addr1+","+st2+" \n"+st3;
					        				  addr1Score = 0.75/2;
					        			  }
					        			  if(containsCaseInsensitive(unIndividual.get("STREETEntity"), doorNo)){
					        				  addr2 = getMatchedContent(unIndividual.get("STREETEntity"), doorNo);
					        				  String st2=getMatchedContent(unIndividual.get("CITYENtity"), city);
					        				  String st3=getMatchedContent(unIndividual.get("STATE_PROVINCEEntity"), state);
					        				  addr2=addr2+","+st2+" \n"+st3;
					        				  addr2Score = 0.25/2;
					            		  }
					        		  }
				        		  }
				        	  }else{
				        		  if(containsCaseInsensitive(unIndividual.get("STREETEntity"), streetName)){
				        			  addr1 = getMatchedContent(unIndividual.get("STREETEntity"), streetName);
				        			  String st2=getMatchedContent(unIndividual.get("COUNTRYEntity"), country);
				        			  addr1=addr1+","+st2;
				        			  addr1Score = 0.75/3;
			        			  }
			        			  if(doorNo!=null && doorNo.trim()!="" && 
			        					  containsCaseInsensitive(unIndividual.get("STREETEntity"), doorNo)){
			        				  addr2 = getMatchedContent(unIndividual.get("STREETEntity"), doorNo);
			        				  String st2=getMatchedContent(unIndividual.get("COUNTRYEntity"), country);
			        				  addr2=addr2+","+st2;
			            			  addr2Score = 0.25/3;
			            		  }
				        	  }
			        	  }
			          }else{
			        	  if(containsCaseInsensitive(unIndividual.get("STREETEntity"), streetName)){
			        		  addr1 = getMatchedContent(unIndividual.get("STREETEntity"), streetName);
			        		  addr1Score = 0.75/4;
	        			  }
	        			  if(doorNo!=null && doorNo.trim()!="" && 
	        					  containsCaseInsensitive(unIndividual.get("STREETEntity"), doorNo)){
	        				  addr2 = getMatchedContent(unIndividual.get("STREETEntity"), doorNo);
	        				  addr2Score = 0.25/4;
	            		  }
			          }
		          }
		          if(isValidDOBCheck){
			          if(unIndividual.get("DATE_OF_BIRTHEntity") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
			        		  containsCaseInsensitive(unIndividual.get("DATE_OF_BIRTHEntity"), dateOfBirth)){
			        	  dob = getMatchedContent(unIndividual.get("DATE_OF_BIRTHEntity"), dateOfBirth);
			        	  dobScore = 1;
			        	  versionNo=unIndividual.get("VERSIONNUMEntity").toString().replaceAll("\\[", "").replaceAll("\\]","");
			          }
			          if(unIndividual.get("ALIAS_DATE_OF_BIRTH") != null && dateOfBirth != null && dateOfBirth.trim() != "" &&
			        		  containsCaseInsensitive(unIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth)){
			        	  aliasDob = getMatchedContent(unIndividual.get("ALIAS_DATE_OF_BIRTH"), dateOfBirth);
			        	  aliasDobscore = 1;
			        	  versionNo=unIndividual.get("VERSIONNUMEntity").toString().replaceAll("\\[", "").replaceAll("\\]","");
			          }
		          }
		          if(unIndividual.get("PASSPORT_NUMBEREntity") != null && passportNumber != null && passportNumber.trim() != "" && 
		        		  containsCaseInsensitive(unIndividual.get("PASSPORT_NUMBEREntity"), passportNumber)){
		        	  otherID = getMatchedContent(unIndividual.get("PASSPORT_NUMBEREntity"), passportNumber);
		        	  otherIDScore = 1;
		        	  versionNo=unIndividual.get("VERSIONNUMEntity").toString().replaceAll("\\[", "").replaceAll("\\]","");
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
			                f1 = partNameScore.getScreened_entity_value();
			                f1lvScore=partNameScore.getMatch_score();
			                f1SoundexScore=partNameScore.getMatch_score();
			                weightedScore = f1score;
						}
						else if(partNameScore.getField_name().equalsIgnoreCase("THIRD_NAME"))
						{
							l1score = partNameScore.getMatch_score()*ConstantConfiguration.LLWeightage;
			          		l3lvScore=partNameScore.getMatch_score();
			          		l3SoundexScore=partNameScore.getMatch_score();
			          		l1 = partNameScore.getScreened_entity_value();
			          		 weightedScore = l1score;
						}
						else if(partNameScore.getField_name().equalsIgnoreCase("SECOND_NAME"))
						{
							m1score = partNameScore.getMatch_score()*ConstantConfiguration.MMWeightage;
			          		m3lvScore=partNameScore.getMatch_score();
			          		m3SoundexScore=partNameScore.getMatch_score();
			          		m1 = partNameScore.getScreened_entity_value();
			          		 weightedScore = m1score;
						}
						//logger.info("Found part name Match --- "+requestID+
							//	"Field "+partNameScore.getField_name()+ " "+
							//	 partNameScore.getScreened_entity_value()+" "+partNameScore.sanctioned_entity_value);//temporary Nagaraj
						individualSanctionInfo.put(partNameScore.getField_name(),partNameScore.sanctioned_entity_value );
						if(weightedScore>ConstantConfiguration.FNThreshold)
						{
						insertToSLS(requestID,partNameScore.getField_name(),partNameScore.getScreened_entity_value(),partNameScore.sanctioned_entity_field_name , "UN List", partNameScore.sanctioned_entity_value, 
								df.format(weightedScore),"-", dataID+"", "-", screeningMode,reuestDateTime,partNameScore.getMatch_score()+"",partNameScore.getMatch_score()+"","2");
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
			        		  insertToSLS(requestID,"address1",addr1, "ADDRESS1", "UN List", addr1, 
										df.format(addr1Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			        	  }
			        	  if(addr2Score>0){
				        	  individualSanctionInfo.put("ADDRESS2", addr2);
				        	  insertToSLS(requestID,"address2",addr2,  "ADDRESS2", "UN List", addr2, 
										df.format(addr2Score),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			        	  }
			        	  finalScore = finalScore + ((addr1Score+addr2Score) * ConstantConfiguration.AddressWeightage);
			          }
			          if(dobScore > ConstantConfiguration.DOBThreshold){
			        	  individualSanctionInfo.put("DATE_OF_BIRTH",dob);
			        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "DATE_OF_BIRTH", "UN List", dob, 
									df.format(dobScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			          }
			          if(aliasDobscore > ConstantConfiguration.DOBThreshold){
			        	  individualSanctionInfo.put("DATE_OF_BIRTH",aliasDob);
			        	  insertToSLS(requestID,"Date_of_Birth",dateOfBirth, "Alias_Date_of_Birth", "UN List", aliasDob, 
									df.format(aliasDobscore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			          }
			          
			          dobScore= Math.max( dobScore * ConstantConfiguration.DOBWeightage ,aliasDobscore * ConstantConfiguration.DOBWeightage);   
			          finalScore = finalScore + dobScore;
			          if(otherIDScore > ConstantConfiguration.OtherIDThreshold){
			        	  individualSanctionInfo.put("PASSPORT_NUMBER", otherID);
			        	  finalScore = finalScore + (otherIDScore * ConstantConfiguration.OtherIDWeightage);
			        	  
			        	  insertToSLS(requestID,"Passport_Number",passportNumber, "PASSPORT_NUMBER", "UN List", otherID, 
									df.format(otherIDScore),versionNo+"", dataID+"", "-", screeningMode,reuestDateTime,"-","-","2");
			          }
		          if(finalScore > 0){
			          individualSanctionInfo.put("SCORE", finalScore+"");
			          individualSanctionInfo.put("LIST_TYPE", "UN LIST");
			          sanctionsInfo.put(dataID, individualSanctionInfo);
		          }
		        }
			}
			
			///WCL START
			//if(worldchecklist_individual_details_delta != null){
				
				/*matchEntityWithWCList( firstName, middleName,  lastName,
						 streetName,  doorNo,  city,  state,  country,  zipCode,
						 passportNumber, aadharNumber,panNumber, dateOfBirth,
						 requestID,  screeningMode, reuestDateTime, entityType, sanctionsInfo);*/
			//}
			
			//WCL END
			
			/*double maxScore = getFinalScoreForHashMap(sanctionsInfo, "SCORE");
			String watchListMatch = "No Match";
			if(maxScore > ConstantConfiguration.FinalScoreThresholdEntity){
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
		    insertToSLS(requestID,"-","-", "-",Listtype, "-", 
					df.format(maxScore),"-",FINALDATAID+"", watchListMatch, screeningMode,reuestDateTime,"-","-",entityType);
			*/
				
		}
		
		return sanctionsInfo;
	}
	
	/**
	 * @param s1
	 * @param s2
	 * @return soundexScore(double) by comparing String s1 and String s2 using Soundex Algorithm
	 */
	public double soundexCheck(String s1, String s2){
		//logger.trace("SOUNDEX");
		Soundex soundex = new Soundex();
		StringEncoderComparator comparator = new StringEncoderComparator(soundex);
		double soundexScore = 0;
		try {
			//logger.trace(soundexScore = ((double)soundex.difference(s1, s2)/s2.length()));
			if(comparator.compare(s1, s2)==0){// If sound matches
				soundexScore = ((double)soundex.difference(s1, s2)/4);
			}
		} catch (EncoderException e) {
			logger.error("", e);
		}
		return soundexScore;
	}
	
	/**
	 * @param s1
	 * @param s2
	 * @return soundexScore(double) by comparing String s1 and String s2 using Metaphone Algorithm
	 */
	public double metaphoneCheck(String s1, String s2){
		//logger.trace("METAPHONE");
		Metaphone metaphone = new Metaphone();
		double metaphoneScore = 0;
		/*StringEncoderComparator comparator = new StringEncoderComparator(metaphone);
		logger.trace("COMPARATOR METAPHONE -- "+comparator.compare(s1, s2));*/
		for (int i = s2.length(); i > 1 ; i--) {
			metaphone.setMaxCodeLen(i);
			if(metaphone.isMetaphoneEqual(s1, s2)){// If string matches
				//logger.trace(metaphone.getMaxCodeLen());
				metaphoneScore = ((double)metaphone.getMaxCodeLen()/s2.length());
				break;
			}
		}
		return metaphoneScore;
	}
	
	/**
	 * @param s1
	 * @param s2
	 * @return soundexScore(double) by comparing String s1 and String s2 using DoubleMetaphone Algorithm
	 */
	public double doubleMetaphoneCheck(String s1, String s2){
		//logger.trace("DOUBLE METAPHONE");
		DoubleMetaphone doubleMetaphone = new DoubleMetaphone();
		double doubleMetaphoneScore = 0;
		StringEncoderComparator comparator = new StringEncoderComparator(doubleMetaphone);
		//logger.trace("COMPARATOR DOUBLEMETAPHONE -- "+comparator.compare(s1, s2));
		String dms1 = doubleMetaphone.doubleMetaphone(s1);
		String dms2 = doubleMetaphone.doubleMetaphone(s2);
		String dmAlts1 =  doubleMetaphone.doubleMetaphone(s1,true);
		String dmAlts2 = doubleMetaphone.doubleMetaphone(s2,true);
		/*logger.trace(dms1);
		logger.trace(dms2);
		logger.trace(dmAlts1);
		logger.trace(dmAlts2);*/
		if(comparator.compare(dms1,dms2)==0 || comparator.compare(dms1,dmAlts2)==0
				|| comparator.compare(dmAlts1,dms2)==0 || comparator.compare(dms1,dmAlts2)==0){
			doubleMetaphoneScore = 1;
		}else{
			for (int i = s2.length(); i > 1 ; i--) {
				doubleMetaphone.setMaxCodeLen(i);
				if(doubleMetaphone.isDoubleMetaphoneEqual(s1, s2)){// If string matches
					doubleMetaphoneScore = ((double)i/s2.length());
					break;
				}
			}
		}
		return doubleMetaphoneScore;
	}
	
	/**
	 * @param s1
	 * @param s2
	 * @return soundexScore(double) by comparing String s1 and String s2 using RefinedSoundex Algorithm
	 */
	public String refinedSoundexCheckForAddress(ArrayList<String> listOfStrings, String s2){
		//logger.trace("REFINED SOUNDEX");
		RefinedSoundex refinedSoundex = new RefinedSoundex();
		StringEncoderComparator comparator = new StringEncoderComparator(refinedSoundex);
		double refinedSoundexScore = 0;
		String highestMatch = null;
		try {
			if(listOfStrings != null){
				for (String s1 : listOfStrings) {
					String s31="",s32="";
					s31=s1;
					s32=s2;
					s1=removalOfSpecialCharacter(s31);
					s2=removalOfSpecialCharacter(s32);
					if(isValid(s1) && isValid(s2)){
						int encS1 = refinedSoundex.encode(s1).length();
						int encS2 = refinedSoundex.encode(s2).length();
						/*logger.trace(refinedSoundex.encode(s1)+" - "+refinedSoundex.encode(s2));
						logger.trace("REFINED Difference -- "+((double)refinedSoundex.difference(s1, s2)));
						logger.trace("REFINED comparator -- "+comparator.compare(s1, s2));
						logger.trace("REFSOUNDEX S1 - "+refinedSoundex.soundex(s1));
						logger.trace("REFSOUNDEX S2 - "+refinedSoundex.soundex(s2));*/
						//if(comparator.compare(s1, s2)==0){// If sound matches
							if(encS1<encS2){//To divide by the length of the shortest encoded string
								double score = ((double)refinedSoundex.difference(s1, s2)/encS2);
								if(score>refinedSoundexScore){
									refinedSoundexScore = score;
									highestMatch = s31;
								}
							}
							else{
								double score = ((double)refinedSoundex.difference(s1, s2)/encS1);
								if(score>refinedSoundexScore){
									refinedSoundexScore = score;
									highestMatch = s31;
								}
							}
						//}
					}
				}
			}
		} catch (EncoderException e) {
			logger.error("", e);
		}
		return refinedSoundexScore+"##"+highestMatch;
	}
	
	public String getMatchScore(String s1, String s2){
		RefinedSoundex refinedSoundex = new RefinedSoundex();
		//double matchScore = 0;
		//double levenshteinScore = 0;
		double lvScore = 0;
		double soundexScore = 0;
		double finalMatchedScore = 0;
		try{
			String s3="",s4="";
			s1 = s1.toUpperCase();
			s2 = s2.toUpperCase();
			s3=s1;
			s4=s2;
			
			//removal of Special character from Sanctioned entities
			s1=removalOfSpecialCharacter(s3);
			
			/*s11=s3.replaceAll("[`~!@#$%^&*+=/?.,;':\\*\\- ]", "");
	    	s12=s11.replaceAll("\\(.*\\)", "");
	    	s13=s12.replaceAll("\\[.*\\]", "");
	    	s14=s13.replaceAll("\\{.*\\}", "");
	    	s1=s14.replaceAll("\\<.*\\>", "");*/
			
	    	//removal of Special character from Screened entities
			s2=removalOfSpecialCharacter(s4);
			
	    	/*s21=s4.replaceAll("[`~!@#$%^&*+=/?.;:\\*\\- ]", "");
	    	s22=s21.replaceAll("\\(.*\\)", "");
	    	s23=s22.replaceAll("\\[.*\\]", "");
	    	s24=s23.replaceAll("\\{.*\\}", "");
	    	s2=s24.replaceAll("\\<.*\\>", "");*/
	    	
	    //	logger.info("s1==="+s1); 
	    	
			if (isValid(s1) && isValid(s2)){
				int minLength = (s1.length() <= s2.length() ? s1.length()
						: s2.length());
				int levenshteinDistance = StringUtils
						.getLevenshteinDistance(s1, s2);
				double levenshteinScore = 1.0 - (double) levenshteinDistance
						/ minLength;
				
				if (Double.isNaN(levenshteinScore)
						|| (Double.compare(levenshteinScore, 0.0) < 0)) {
					levenshteinScore = 0;
				}
				else if( minLength == 1 &&   s1.charAt(0) == s2.charAt(0)) 
				{ //for handling initials	
					levenshteinScore = 0.8;
				}
				else if(levenshteinDistance < 2 && minLength > 1 && minLength < 5 && s1.charAt(0) == s2.charAt(0) && levenshteinScore < 0.8 ) 
				{	
					levenshteinScore = 0.8;
				}
				if(levenshteinScore>lvScore)
				{
					lvScore=levenshteinScore;
				}
			   if (lvScore > 0.5) {
						int encS1 = refinedSoundex.encode(s1).length();
						int encS2 = refinedSoundex.encode(s2).length();
						if (encS1 < encS2) {
						double	matchScore = ((double) refinedSoundex
									.difference(s1, s2) / encS2);
						if(matchScore>soundexScore)
						{
							soundexScore=matchScore;
						}
						} else {
						double	matchScore = ((double) refinedSoundex
									.difference(s1, s2) / encS1);
						if(matchScore>soundexScore)
						{
							soundexScore=matchScore;
						}
						}
					 finalMatchedScore=Math.max(lvScore, soundexScore);		
				} 
			   else {
					if ((lvScore > finalMatchedScore)) {
						finalMatchedScore = levenshteinScore;
					}
				}
			
			}	
			
		} catch (Exception e) {
			logger.error("", e);
		}
		return finalMatchedScore+"##"+df.format(lvScore)+"##"+df.format(soundexScore);
	}

	
	public String refinedSoundexCheck(ArrayList<String> listOfStrings, String s2) {
		RefinedSoundex refinedSoundex = new RefinedSoundex();
		double refinedSoundexScore = 0;
		//double levenshteinScore = 0;
		double lvScore = 0;
		double finalMatchedScore = 0;
		String highestMatch = null;
		String type=""; 
		String category="";
		try {
			if (listOfStrings != null) {
				for (String s1 : listOfStrings) {
					String s3="",s4="";
					if(s1.indexOf("##")>-1)
					{
						category=s1.split("##")[1].trim();
						s1=s1.split("##")[0].trim();
					}
					
					//logger.info("category==="+category); 
					s1 = s1.toUpperCase();
					s2 = s2.toUpperCase();
					
					
					s3=s1;
					s4=s2;
					
					
					//removal of Special character from Sanctioned entities
					s1=removalOfSpecialCharacter(s3);
							
			
					/*s11=s3.replaceAll("[`~!@#$%^&*+=/?.',;:\\*\\- ]", "");
			    	s12=s11.replaceAll("\\(.*\\)", "");
			    	s13=s12.replaceAll("\\[.*\\]", "");
			    	s14=s13.replaceAll("\\{.*\\}", "");
			    	s1=s14.replaceAll("\\<.*\\>", "");*/
			    	
			    	
			    	//removal of Special character from Screened entities
			    	s2=removalOfSpecialCharacter(s4);
			    	
			    	
			    	/*s21=s4.replaceAll("[`~!@#$%^&*+=/?.;:\\*\\- ]", "");
			    	s22=s21.replaceAll("\\(.*\\)", "");
			    	s23=s22.replaceAll("\\[.*\\]", "");
			    	s24=s23.replaceAll("\\{.*\\}", "");
			    	s2=s24.replaceAll("\\<.*\\>", "");*/
					
			    //	logger.info("s1=nm=="+s1); 
			    	
					if (isValid(s1) && isValid(s2)) {
					int minLength = (s1.length() <= s2.length() ? s1.length()
							: s2.length());
					int levenshteinDistance = StringUtils
							.getLevenshteinDistance(s1, s2);
				double	levenshteinScore = 1.0 - (double) levenshteinDistance
							/ minLength;
					
					if (Double.isNaN(levenshteinScore)
							|| (Double.compare(levenshteinScore, 0.0) < 0)) {
						levenshteinScore = 0;
					}
					else if( minLength == 1 &&   s1.charAt(0) == s2.charAt(0)) 
					{ //for handling initials	
						levenshteinScore = 0.8;
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
							int encS1 = refinedSoundex.encode(s1).length();
							int encS2 = refinedSoundex.encode(s2).length();
							if (encS1 < encS2) {// To divide by the length of
												// the shortest encoded string
								double score = ((double) refinedSoundex
										.difference(s1, s2) / encS2);
								if (score > refinedSoundexScore) {
									refinedSoundexScore = score;
									highestMatch = s3;
									type=category;
								} 
							} else {
								double score = ((double) refinedSoundex
										.difference(s1, s2) / encS1);
								if (score > refinedSoundexScore) {
									refinedSoundexScore = score;
									highestMatch = s3;
									type=category;
								} 
							}
						 finalMatchedScore=Math.max(lvScore, refinedSoundexScore);	
					} 
			       
			        else {
			        	if ((lvScore > finalMatchedScore)||(highestMatch==null)) {
			        		finalMatchedScore = lvScore;
							highestMatch = s3;
							type=category;
						}
					}
				}
				}
			}
			
		} catch (EncoderException e) {
			logger.error("", e);
		}
		if(!type.equalsIgnoreCase("")&& !type.equalsIgnoreCase("null") )
		{
		  return type+"&&"+finalMatchedScore+"##"+type+"&&"+highestMatch+"##"+df.format(lvScore)+"&&"+df.format(refinedSoundexScore);
		}
		else
			return finalMatchedScore+"##"+highestMatch+"##"+df.format(lvScore)+"##"+df.format(refinedSoundexScore);
	}
	
	private String removalOfSpecialCharacter(String s3) {
		String s11="",s12="",s13="",s14="",s="";
		s11=s3.replaceAll("[`~!@#$%^&*+=/?.',;:\\*\\- ]", "");
    	s12=s11.replaceAll("\\(.*\\)", "");
    	s13=s12.replaceAll("\\[.*\\]", "");
    	s14=s13.replaceAll("\\{.*\\}", "");
    	s=s14.replaceAll("\\<.*\\>", "");
		// TODO Auto-generated method stub
		return s;
	}

	public String refinedSoundexCheckForFullName(ArrayList<String> listOfStrings, String fName, String mName, String lName, 
			int dataID, String reqID, String reqDT, int mode, String entType ) {
		String highestMatch = null;
		double refinedSoundexScore = 0;
		String matchedf2f= null,matchedf2m= null,matchedf2l= null,matchedl2f= null,matchedl2m= null,matchedl2l= null,aliasType=null;
		double f2flvScore = 0,f2mlvScore = 0,f2llvScore=0,l2flvScore=0,l2mlvScore=0,l2llvScore=0;
		double f2fsoundexScore = 0,f2msoundexScore = 0,f2lsoundexScore=0,l2fsoundexScore=0,l2msoundexScore=0,l2lsoundexScore=0;
		
		DatabaseAdaptor adapter_obj = new DatabaseAdaptor();
		
		try {
			if (listOfStrings != null) {
				if(entType.equals("1"))
					{
					  for(String s1 : listOfStrings) {
						double f2f = 0,f2m = 0,f2l=0,l2f=0,l2m=0,l2l=0;
						String category = null;
						String firstName = null;
						String lastName = null;
						if(s1.indexOf("##")>-1)
						{
							firstName=s1.split("##")[0].trim();
							lastName=s1.split("##")[1].trim();
							category=s1.split("##")[2].trim();
							
						}
		                
						firstName = firstName.toUpperCase();
						lastName =  lastName.toUpperCase();
						fName = fName.toUpperCase();
						mName = mName.toUpperCase();
						lName = lName.toUpperCase();
						
						if(!firstName.equalsIgnoreCase("1")){
							aliasType="ALIAS_NAME_FIRST_"+category;	
							boolean exactMatch=false;
						matchedf2f = getMatchScore(firstName, fName);
						f2f=Double.parseDouble(matchedf2f.split("##")[0]);
						f2flvScore=Double.parseDouble(matchedf2f.split("##")[1]);
						f2fsoundexScore=Double.parseDouble(matchedf2f.split("##")[2]);
						
						
							if(f2f > ConstantConfiguration.ALFThreshold){
					        	  insertToSLS(reqID,"First_name",fName,aliasType, "OFAC List", firstName, 
											df.format(f2f),"-", dataID+"", "-", mode,reqDT,f2flvScore+"",f2fsoundexScore+"","1");
					          }
							
								matchedf2m = getMatchScore(firstName, mName);
								f2m=Double.parseDouble(matchedf2m.split("##")[0]);
								f2mlvScore=Double.parseDouble(matchedf2m.split("##")[1]);
								f2msoundexScore=Double.parseDouble(matchedf2m.split("##")[2]);
								
								if(f2m > ConstantConfiguration.ALMThreshold){
						        	  insertToSLS(reqID,"Middle_name",mName,aliasType, "OFAC List", firstName, 
												df.format(f2m),"-", dataID+"", "-", mode,reqDT,f2mlvScore+"",f2msoundexScore+"","1");
						          }
								
								matchedf2l = getMatchScore(firstName, lName);
								f2l=Double.parseDouble(matchedf2l.split("##")[0]);
								f2llvScore=Double.parseDouble(matchedf2l.split("##")[1]);
								f2lsoundexScore=Double.parseDouble(matchedf2l.split("##")[2]);
								if(f2l > ConstantConfiguration.ALMThreshold){
						        	  insertToSLS(reqID,"Last_name",lName, aliasType, "OFAC List", firstName, 
												df.format(f2l),"-", dataID+"", "-", mode,reqDT,f2llvScore+"",f2lsoundexScore+"","1");
						          }
							
							
							s1 = firstName;
						}
						if(!lastName.equalsIgnoreCase("2")){
							aliasType="ALIAS_NAME_LAST_"+category;	
							boolean exactMatch=false;
							//l2l = getMatchScore(lastName, lName);
							matchedl2l = getMatchScore(lastName, lName);
							l2l=Double.parseDouble(matchedl2l.split("##")[0]);
							l2llvScore=Double.parseDouble(matchedl2l.split("##")[1]);
							l2lsoundexScore=Double.parseDouble(matchedl2l.split("##")[2]);
							
							if(l2l > ConstantConfiguration.ALFThreshold){
					        	  insertToSLS(reqID,"Last_name",lName, aliasType, "OFAC List", lastName, 
											df.format(l2l),"-", dataID+"", "-", mode,reqDT,l2llvScore+"",l2lsoundexScore+"","1");
					          }
							
						
								//l2f = getMatchScore(lastName, fName);
								matchedl2f = getMatchScore(lastName, fName);
								l2f=Double.parseDouble(matchedl2f.split("##")[0]);
								l2flvScore=Double.parseDouble(matchedl2f.split("##")[1]);
								l2fsoundexScore=Double.parseDouble(matchedl2f.split("##")[2]);
								
								if(l2f > ConstantConfiguration.ALFThreshold){
						        	  insertToSLS(reqID,"First_name",fName, aliasType, "OFAC List", lastName, 
												df.format(l2f),"-", dataID+"", "-", mode,reqDT,l2flvScore+"",l2fsoundexScore+"","1");
						          }
								//l2m = getMatchScore(lastName, mName);
								matchedl2m = getMatchScore(lastName, mName);
								l2m=Double.parseDouble(matchedl2m.split("##")[0]);
								l2mlvScore=Double.parseDouble(matchedl2m.split("##")[1]);
								l2msoundexScore=Double.parseDouble(matchedl2m.split("##")[2]);
								
								
								if(l2m > ConstantConfiguration.ALFThreshold){
						        	  insertToSLS(reqID,"Middle_name",mName, aliasType, "OFAC List", lastName, 
												df.format(l2m),"-", dataID+"", "-", mode,reqDT,l2mlvScore+"",l2msoundexScore+"","1");
						          }
							
							
							if(s1.length()>1){
								s1 = s1+" "+lastName;
							}else{
								s1 = lastName;
							}
						}
						double firstNameScore = Math.max(f2f, l2f);
						if(fName!=null && lName.equalsIgnoreCase("") && !fName.equalsIgnoreCase("") && lName.equalsIgnoreCase(""))
						{
							
							if(firstNameScore==1.0)
							{
								if(firstNameScore>ConstantConfiguration.ALFThreshold)
								{
									firstNameScore= ConstantConfiguration.AliasThreshold + firstNameScore* ConstantConfiguration.ALFWeightage;
								}
								else
								{
									firstNameScore=0.0;
								}
							}
							else
							{
								if(firstNameScore>ConstantConfiguration.ALFThreshold)
								{
									firstNameScore= firstNameScore* ConstantConfiguration.ALFWeightage;
								}
								else
								{
									firstNameScore=0.0;
								}
							}
							
						}
						else
						{  
							if(firstNameScore>ConstantConfiguration.ALFThreshold)
							{
								firstNameScore= firstNameScore* ConstantConfiguration.ALFWeightage;
							}
							else
							{
								firstNameScore=0.0;
							}
						}
						
						double middleNameScore = Math.max(f2m, l2m);
						if(middleNameScore>ConstantConfiguration.ALMThreshold)
						{
							middleNameScore= middleNameScore* ConstantConfiguration.ALFWeightage;
						}
						else
						{
							middleNameScore=0.0;
						}
						
						//double lastNameScore = Math.max(f2l, l2l) * ConstantConfiguration.ALLWeightage;
						double lastNameScore = Math.max(f2l, l2l);
						if(lName!=null && fName.equalsIgnoreCase("") && !lName.equalsIgnoreCase("") && fName.equalsIgnoreCase(""))
						{
							if(lastNameScore==1)
							{
								if(lastNameScore>ConstantConfiguration.ALMThreshold)
								{
									lastNameScore=ConstantConfiguration.AliasThreshold +lastNameScore* ConstantConfiguration.ALLWeightage;
								}
								else
								{
									lastNameScore=0.0;
								}
							}
							else
						  {
								if(lastNameScore>ConstantConfiguration.ALMThreshold)
								{
									lastNameScore= lastNameScore* ConstantConfiguration.ALLWeightage;
								}
								else
								{
									lastNameScore=0.0;
								}
								
							}
							
						}
						else
						{
							if(lastNameScore>ConstantConfiguration.ALMThreshold)
							{
								lastNameScore= lastNameScore* ConstantConfiguration.ALLWeightage;
							}
							else
							{
								lastNameScore=0.0;
							}
						}
						
						double score = (firstNameScore + middleNameScore + lastNameScore);
						if(category!=null && category.equalsIgnoreCase("weak")){
							score = score * 1.0;
						}
						if(score > refinedSoundexScore){
							refinedSoundexScore = score;
							highestMatch = s1.replaceAll("##", " ");
						}
						
						
					}
					
				}
				
				else
				{
					for (String s1 : listOfStrings) {
						double f2f = 0,f2m = 0,f2l=0,l2f=0,l2m=0,l2l=0;
						String category = null;
						String firstName = null;
						String lastName = null;
						if(s1.indexOf("##")>-1)
						{
							firstName=s1.split("##")[0].trim();
							lastName=s1.split("##")[1].trim();
							category=s1.split("##")[2].trim();
						}
		                
						firstName = firstName.toUpperCase();
						lastName =  lastName.toUpperCase();
						fName = fName.toUpperCase();
						mName = mName.toUpperCase();
						lName = lName.toUpperCase();
						
						/*if(!firstName.equalsIgnoreCase("1")){
							aliasType="ALIAS_NAME_FIRST_"+category;	
							boolean exactMatch=false;
						matchedf2f = getMatchScore(firstName, fName);
						f2f=Double.parseDouble(matchedf2f.split("##")[0]);
						f2flvScore=Double.parseDouble(matchedf2f.split("##")[1]);
						f2fsoundexScore=Double.parseDouble(matchedf2f.split("##")[2]);
						
						
							if(f2f > ConstantConfiguration.ALFThreshold){
					        	  objadapter.insertToSLS(reqID,"First_name",fName,aliasType, "OFAC List", firstName, 
											df.format(f2f),"-", dataID+"", "-", mode,reqDT,f2flvScore+"",f2fsoundexScore+"","2");
					          }
							
								matchedf2m = getMatchScore(firstName, mName);
								f2m=Double.parseDouble(matchedf2m.split("##")[0]);
								f2mlvScore=Double.parseDouble(matchedf2m.split("##")[1]);
								f2msoundexScore=Double.parseDouble(matchedf2m.split("##")[2]);
								
								if(f2m > ConstantConfiguration.ALMThreshold){
						        	  objadapter.insertToSLS(reqID,"Middle_name",mName,aliasType, "OFAC List", firstName, 
												df.format(f2m),"-", dataID+"", "-", mode,reqDT,f2mlvScore+"",f2msoundexScore+"","2");
						          }
								
								matchedf2l = getMatchScore(firstName, lName);
								f2l=Double.parseDouble(matchedf2l.split("##")[0]);
								f2llvScore=Double.parseDouble(matchedf2l.split("##")[1]);
								f2lsoundexScore=Double.parseDouble(matchedf2l.split("##")[2]);
								if(f2l > ConstantConfiguration.ALMThreshold){
						        	  objadapter.insertToSLS(reqID,"Last_name",lName, aliasType, "OFAC List", firstName, 
												df.format(f2l),"-", dataID+"", "-", mode,reqDT,f2llvScore+"",f2lsoundexScore+"","2");
						          }
							
							
							s1 = firstName;
						}*/
						if(!lastName.equalsIgnoreCase("2")){
							aliasType="ALIAS_NAME_LAST_"+category;	
							boolean exactMatch=false;
							matchedl2l = getMatchScore(lastName, lName);
							l2l=Double.parseDouble(matchedl2l.split("##")[0]);
							l2llvScore=Double.parseDouble(matchedl2l.split("##")[1]);
							l2lsoundexScore=Double.parseDouble(matchedl2l.split("##")[2]);
							
							if(l2l > ConstantConfiguration.ALFThreshold){
					        	  insertToSLS(reqID,"Last_name",lName, aliasType, "OFAC List", lastName, 
											df.format(l2l),"-", dataID+"", "-", mode,reqDT,l2llvScore+"",l2lsoundexScore+"","2");
					          }
							
						
								matchedl2f = getMatchScore(lastName, fName);
								l2f=Double.parseDouble(matchedl2f.split("##")[0]);
								l2flvScore=Double.parseDouble(matchedl2f.split("##")[1]);
								l2fsoundexScore=Double.parseDouble(matchedl2f.split("##")[2]);
								
								if(l2f > ConstantConfiguration.ALFThreshold){
						        	  insertToSLS(reqID,"First_name",fName, aliasType, "OFAC List", lastName, 
												df.format(l2f),"-", dataID+"", "-", mode,reqDT,l2flvScore+"",l2fsoundexScore+"","2");
						          }
								matchedl2m = getMatchScore(lastName, mName);
								l2m=Double.parseDouble(matchedl2m.split("##")[0]);
								l2mlvScore=Double.parseDouble(matchedl2m.split("##")[1]);
								l2msoundexScore=Double.parseDouble(matchedl2m.split("##")[2]);
								
								
								if(l2m > ConstantConfiguration.ALFThreshold){
						        	  insertToSLS(reqID,"Middle_name",mName, aliasType, "OFAC List", lastName, 
												df.format(l2m),"-", dataID+"", "-", mode,reqDT,l2mlvScore+"",l2msoundexScore+"","2");
						          }
							
							
							if(s1.length()>1){
								s1 = s1+" "+lastName;
							}else{
								s1 = lastName;
							}
						}
						
						/*double firstNameScore = Math.max(f2f, l2f);
						if(firstNameScore>ConstantConfiguration.ALFThreshold)
						{
							firstNameScore= firstNameScore* ConstantConfiguration.ALFWeightage;
						}
						else
						{
							firstNameScore=0.0;
						}
						//double middleNameScore = Math.max(f2m, l2m) * ConstantConfiguration.ALMWeightage;
						double middleNameScore = Math.max(f2m, l2m);
						if(middleNameScore>ConstantConfiguration.ALMThreshold)
						{
							middleNameScore= middleNameScore* ConstantConfiguration.ALFWeightage;
						}
						else
						{
							middleNameScore=0.0;
						}
						
						//double lastNameScore = Math.max(f2l, l2l) * ConstantConfiguration.ALLWeightage;
						double lastNameScore = Math.max(f2l, l2l);
						if(lastNameScore>ConstantConfiguration.ALMThreshold)
						{
							lastNameScore= lastNameScore* ConstantConfiguration.ALLWeightage;
						}
						else
						{
							lastNameScore=0.0;
						}*/
						
						double aliasEntityScore=0;
						double score=0;
						aliasEntityScore= Math.max(l2f, Math.max(l2m, l2l));
						if(aliasEntityScore>ConstantConfiguration.AliasThreshold)
						{
						  score = aliasEntityScore*ConstantConfiguration.AliasEntityWeightage;
						}
						if(category!=null && category.equalsIgnoreCase("weak")){
							score = score * 1.0;
						}
						if(score > refinedSoundexScore){
							refinedSoundexScore = score;
							highestMatch = s1.replaceAll("##", " ");
						}
					}
				}
				
				
				
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
			return refinedSoundexScore+"##"+highestMatch;
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
		}catch (Exception e){
			isValid = false;
		}
		return isValid;
	}
	
	 /**
	 * @param dateString
	 * @return boolean
	 * <br>&emsp;&emsp;true - Valid Date
	 * <br>&emsp;&emsp;false - UnParsable Date
	 */
	public boolean isValidDOB(String dateString){
		boolean isValid = false;
		try {
			if(dateString != null && dateString.trim().length() > 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date formattedDate;
				formattedDate = sdf.parse(dateString);
				if(formattedDate!=null)
				isValid = true;
				
				sdf=null;//Anand Added 11-OCT-2018
				formattedDate=null; //Anand Added 11-OCT-2018
				
			}
		} catch (ParseException e) {
			isValid = false;
		}
		dateString=null;//Anand Added 11-OCT-2018
		
		
		return isValid;
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
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Date formattedDate; 
			formattedDate = sdf.parse(dateString);
			if(formattedDate!=null)
			isValid = true;
		} catch (ParseException e) {
			isValid = false;
		}
		return isValid;
	}
	
	public static String removeExtraWhiteSpacesAndSpecialChars(String input) {
		if(input!=null && input.trim().length()>0){
			input = input.trim().replaceAll(" +", " ");
			input = input.replaceAll("[^A-Za-z0-9]", "");
		}
		return input;
	}
	
	public static ArrayList<String> getSubPartNames(String name){
		ArrayList<String> partNames = new ArrayList<String>();
		if(name != null && name.trim().length()>0){
			partNames = new ArrayList<String>(Arrays.asList(name.split(" ")));
		}
		return partNames;
	}
	public static String xmlEscapeText(String t) {
		   StringBuilder sb = new StringBuilder();
		   for(int i = 0; i < t.length(); i++){
		      char c = t.charAt(i);
		      switch(c){
		      case '<': sb.append("&lt;"); break;
		      case '>': sb.append("&gt;"); break;
		      case '\"': sb.append("&quot;"); break;
		      case '&': sb.append("&amp;"); break;
		      case '\'': sb.append("&apos;"); break;
		      default:
		         if(c>0x7e) {
		            sb.append("&#"+((int)c)+";");
		         }else
		            sb.append(c);
		      }
		   }
		   return sb.toString();
		}
}
