package com.manipal.deduplication;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;

import main.CFRTAdapter;

/**
 * 
 * This class is used to define constants and does the file reading operation.
 * 
 * @author Saraansh
 * @version v3.3
 * @lastUpdated 16-05-2016
 */
public class ConstantConfiguration {
	public static Logger watchlistLogger=null;
	public static String AdminQueue = "";
	public static String IBMMQ = "";
	public static String MQCHANNEL; 
	public static String MQPORT; 
	public static String MQCCSID;
	public static String INPUTQUEUE;
	public static String OUTPUTQUEUE;
	public static String MQMANAGER;
	public static String str = null;
	public static String MQReadMode = "";
	public static String FlmsDemoPath = "";
	public static String MQUserId = "";
	public static double FinalScoreThreshold = 0.0;
	public static double FinalScoreThresholdIndividual = 0.0;
	public static double FinalScoreThresholdEntity = 0.0;
	public static double FNThreshold = 0.0;
	public static double MNThreshold = 0.0;
	public static double LNThreshold = 0.0;
	public static double AddressThreshold = 0.0;
	public static double OtherIDThreshold = 0.0;
	public static double DOBThreshold = 0.0;
	public static double FNWeightage = 0.0;
	public static double MNWeightage = 0.0;
	public static double LNWeightage = 0.0;
	public static double AddressWeightage = 0.0;
	public static double OtherIDWeightage = 0.0;
	public static double DOBWeightage = 0.0;
	public static String url="",user="",password="",driver="";
	public static long waitingTime=0;
	public static String vizUserName="";
	public static String entUserName="";
	public static String requestSchemaFile="";
	public static int noOfOfflineScreeningThreads=0;
	public static int noOfOnlineScreeningThreads=0;
	public static int timeToStartOfflineScreening=0;
	public static double FFThreshold = 0.0;
	public static double FMThreshold = 0.0;
	public static double FLThreshold = 0.0;
	public static double MFThreshold = 0.0;
	public static double MMThreshold = 0.0;
	public static double MLThreshold = 0.0;
	public static double LFThreshold = 0.0;
	public static double LMThreshold = 0.0;
	public static double LLThreshold = 0.0; 
	public static double LFNWeightage = 0.0;
	public static double FLNWeightage = 0.0;
	
	public static double FFWeightage = 0.0;
	public static double FMWeightage = 0.0;
	public static double FLWeightage = 0.0;
	public static double MFWeightage = 0.0;
	public static double MMWeightage = 0.0;
	public static double MLWeightage = 0.0;
	public static double LFWeightage = 0.0;
	public static double LMWeightage = 0.0;
	public static double LLWeightage = 0.0; 
	
	public static double ALFThreshold = 0.0;
	public static double ALMThreshold = 0.0;
	public static double ALLThreshold = 0.0; 
	
	public static double ALFWeightage = 0.0;
	public static double ALMWeightage = 0.0;
	public static double ALLWeightage = 0.0;
	
	
	public static double AliasThreshold = 0.0;
	public static double AliasWeightage = 0.0;
	
	public static double ASWeightage=0.0;
	public static double AWWeightage=0.0; 
	public static double AHWeightage=0.0;
	public static double ALWeightage=0.0; 
	
	public static double AliasEntityWeightage=0.0;
	
	public static String versionNum = "";
	
	
	public static int feedType = 0;
	
	public static boolean ofacScreening =false;
	public static boolean unScreening =false;
	public static boolean localScreening =false;
	public static boolean pepScreening =false;
	public static boolean wclScreening =true;
	
	public static boolean offlineCompleteListScreening=false;
	public static String intialFeedFile = "";
	public static boolean testingMode=false;
	public static String accountIds="'0'";
	public static int splitSizeForNoOfThreads=200000;
	public static boolean dataBaseInsert=false;
	public static int accountProcessedPrintCount=10;
	public static int topNMatches = 10;		
	public static boolean skipFNLNNotMatch=false;
	public static boolean aliasNameLoad=false;
	public static boolean alternativeSpellingLoad=false;
	public static boolean offlineScreening=false;
	public static boolean restartOnlineScreening=false;
	public static String onlineRunningPath="";
	public static String dbType = "";
	public static int scrollType = 0;
	
	public ConstantConfiguration() {
		
	}

	/**
	 * Description This method is used to read the constant configuration file.
	 * 
	 * @param none.
	 * 
	 * @return none.
	 * 
	 * @exception UnknownHostException,IOException.
	 * 
	 */
	public static void readConstantConfiguration() {
		try {
			FileReader fr = null;
			try {
				fr = new FileReader(ConstantConfiguration.class.getClassLoader()
						.getResource("sanctionlist-config.ini").getPath());
				BufferedReader in = new BufferedReader(fr);
				while ((str = in.readLine()) != null) {
					ConnectionRelated();
					statisticThresholds();
					worldCheckRelated();
				}
				in.close();
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Description This method is used to read the thread related delay
	 * constants from constant configuration file.
	 * 
	 * @param none.
	 * 
	 * @return none.
	 * 
	 * @exception none.
	 * 
	 */
public static void ConnectionRelated(){
	if (str.startsWith("url=")) {
		url = str.substring(str.indexOf("=") + 1);
	}
	else if (str.startsWith("user=")) {
		user = str.substring(str.indexOf("=") + 1);
	}
	else if (str.startsWith("password=")) {
		password = str.substring(str.indexOf("=") + 1);
	}
	else if (str.startsWith("driverName=")) {
		driver = str.substring(str.indexOf("=") + 1);
	}
	else if(str.startsWith("vizUserName=")){
		vizUserName = str.substring(str.indexOf("=")+1);
	}
	else if(str.startsWith("entUserName=")){
		entUserName = str.substring(str.indexOf("=")+1);
	}else if (str.startsWith("DBTYPE=")) {
		  dbType = str.substring(str.indexOf("=")+1);
		  if(dbType.equalsIgnoreCase("oracle")){
			  scrollType = 1005; //ResultSet.TYPE_SCROLL_SENSITIVE
		  }else if(dbType.equalsIgnoreCase("db2")){
			  scrollType = 1004; //ResultSet.TYPE_SCROLL_INSENSITIVE 
		  }
			
}
	
}

	/**
	 * Description This method is used to read the statistical thresholds from
	 * constant configuration file.
	 * 
	 * @param none.
	 * 
	 * @return none.
	 * 
	 * @exception none.
	 * 
	 */
	public static void statisticThresholds() {
		if (str.startsWith("AdminQueue=")) {
			AdminQueue = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("IBMMQ=")) {
			IBMMQ = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("MQCHANNEL=")) {
			MQCHANNEL = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("MQPORT=")) {
			MQPORT = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("MQCCSID=")) {
			MQCCSID = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("INPUTQUEUE=")) {
			INPUTQUEUE = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("OUTPUTQUEUE=")) {
			OUTPUTQUEUE = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("MQUSERID=")) {
			MQUserId = new String(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("MQMANAGER=")) {
			MQMANAGER = new String(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MQReadMode=")){
			MQReadMode = new String(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FlmsDemoPath=")){
			FlmsDemoPath = new String(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FinalScoreThreshold=")){
			FinalScoreThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
	    else if(str.startsWith("FinalScoreThresholdIndividual=")){
			FinalScoreThresholdIndividual = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FinalScoreThresholdEntity=")){
			FinalScoreThresholdEntity = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FirstNameThreshold=")){
			FNThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MiddleNameThreshold=")){
			MNThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LastNameThreshold=")){
			LNThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AddressThreshold=")){
			AddressThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("OtherIDThreshold=")){
			OtherIDThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("DOBThreshold=")){
			DOBThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FirstNameWeightage=")){
			FNWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MiddleNameWeightage=")){
			MNWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LastNameWeightage=")){
			LNWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AddressWeightage=")){
			AddressWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("OtherIDWeightage=")){
			OtherIDWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("DOBWeightage=")){
			DOBWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if (str.startsWith("Request_Schema=")) {
			requestSchemaFile =str.substring(str.indexOf("=")+1);
		}
		else if(str.startsWith("NoOfOfflineThreads=")){
			noOfOfflineScreeningThreads = Integer.parseInt(str.substring(str.indexOf("=")+1));
		}
		else if(str.startsWith("NoOfOnlineThreads=")){
			noOfOnlineScreeningThreads = Integer.parseInt(str.substring(str.indexOf("=")+1));
		}
		else if (str.startsWith("OfflineScreeningTime=")) {
			timeToStartOfflineScreening = Integer.parseInt(str.substring(str.indexOf("=")+1));
		}
		else if (str.startsWith("MQWaitingTimeInSecs=")) {
			waitingTime = Integer.parseInt(str.substring(str.indexOf("=")+1));
		}
		else if(str.startsWith("FFThreshold=")){
			FFThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FMThreshold=")){
			FMThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FLThreshold=")){
			FLThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MFThreshold=")){
			MFThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MMThreshold=")){
			MMThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MLThreshold=")){
			MLThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LFThreshold=")){
			LFThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LMThreshold=")){
			LMThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LLThreshold=")){
			LLThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LFNWeightage=")){
			LFNWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FLNWeightage=")){
			FLNWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FFWeightage=")){
			FFWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FMWeightage=")){
			FMWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("FLWeightage=")){
			FLWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MFWeightage=")){
			MFWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MMWeightage=")){
			MMWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("MLWeightage=")){
			MLWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LFWeightage=")){
			LFWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LMWeightage=")){
			LMWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("LLWeightage=")){
			LLWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALFThreshold=")){
			ALFThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALMThreshold=")){
			ALMThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALLThreshold=")){
			ALLThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALFWeightage=")){
			ALFWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALMWeightage=")){
			ALMWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALLWeightage=")){
			ALLWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AliasThreshold=")){
			AliasThreshold = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AliasWeightage=")){
			AliasWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		
		else if(str.startsWith("ASWeightage=")){
			ASWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AWWeightage=")){
			AWWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AHWeightage=")){
			AHWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("ALWeightage=")){
			ALWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("AliasEntityWeightage=")){
			AliasEntityWeightage = Double.parseDouble(str.substring(str.indexOf("=") + 1));
		}
		else if(str.startsWith("VersionNumber=")){
			versionNum = str.substring(str.indexOf("=") + 1);
		}
	}

	/*public static void  LoadLog4jProperties(){
		try {
		    Properties logProperties = new Properties();
	    	logProperties.load(new FileInputStream("CONFIGURATION\\log4j.properties"));
	    	PropertyConfigurator.configure(logProperties);
		    watchlistLogger=Logger.getLogger("WATCHLIST_LOG");
	    	PatternLayout objPatternLayout=(PatternLayout)watchlistLogger.getAppender("WATCHLIST_LOG").getLayout();
	    	String filePath="CONFIGURATION\\log\\WatchList_Log_";
	    	java.sql.Date dd=new Date(System.currentTimeMillis());
	    	FileAppender objAppender=new FileAppender(objPatternLayout,filePath+dd.toString()+".log");
	    	watchlistLogger.addAppender(objAppender);
		}catch(Exception e){
		      watchlistLogger.error(e);
	    }
	}*/
	public static void  LoadLog4jProperties(){
		try {
		    Properties logProperties = new Properties();
	    	logProperties.load(new FileInputStream("CONFIGURATION\\log4j.properties"));     
	    	PropertyConfigurator.configure(logProperties);
		    watchlistLogger=Logger.getLogger("WATCHLIST_LOG");
	    	PatternLayout objPatternLayout=(PatternLayout)watchlistLogger.getAppender("WATCHLIST_LOG").getLayout();
	    	String filePath="CONFIGURATION\\log\\WatchList_Log.log";
	    	
	    	/*What:Daily log files generation changes 
	    	 *When:10-19-2016
	    	 *Prabhanjan Desai 
	    	 */
	    	DailyRollingFileAppender objAppender = new DailyRollingFileAppender(objPatternLayout,filePath, "'.'yyyy-MM-dd");
	    	
	    	watchlistLogger.addAppender(objAppender);
		}catch(Exception e){
			e.printStackTrace();
			watchlistLogger.error(e);
	    }
	}
	
	
	//World Checklisr Related
		public static void worldCheckRelated(){
			if(str.startsWith("FeedType=")){
				feedType = Integer.parseInt(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("OFACListScreening=")){
				ofacScreening	 = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("UNListScreening=")){
				unScreening	 = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("LOCALListScreening=")){
				localScreening	 = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("PEPListScreening=")){
				pepScreening	 = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("WCListScreening=")){
				wclScreening	 = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("OfflineCompleteListScreening=")){
				offlineCompleteListScreening = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("IntialFeedFile=")){
				intialFeedFile = str.substring(str.indexOf("=") + 1);
			}else if(str.startsWith("TestingMode=")){
				testingMode = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("AccountIds=")){
				accountIds = str.substring(str.indexOf("=") + 1);
			}else if(str.startsWith("SplitSizeForNoOfThreads=")){
				splitSizeForNoOfThreads = Integer.parseInt(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("DataBaseInsert=")){
				dataBaseInsert = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("AccountProcessedPrintCount=")){
				accountProcessedPrintCount = Integer.parseInt(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("TopNMatches=")){
				topNMatches = Integer.parseInt(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("SkipFNLNNotMatch=")){
				skipFNLNNotMatch = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("AliasNameLoad=")){
				aliasNameLoad = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("AlternativeSpellingLoad=")){
				alternativeSpellingLoad = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("OfflineScreening=")){
				offlineScreening = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("RestartOnlineScreening=")){
				restartOnlineScreening = Boolean.parseBoolean(str.substring(str.indexOf("=") + 1));
			}else if(str.startsWith("OnlineRunningPath=")){
				onlineRunningPath = str.substring(str.indexOf("=") + 1);
			}	
		}
	
}
