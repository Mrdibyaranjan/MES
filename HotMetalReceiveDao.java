package com.smes.trans.dao.impl;

import java.util.Hashtable;
import java.util.List;

import com.smes.trans.model.HmReceiveBaseDetails;

public interface HotMetalReceiveDao {

	List<HmReceiveBaseDetails> getHMDetailsbyStatus(Integer stage,
			String ladlestatus);

	Integer getHMDetailsbyInterface();

	String hotMetalReceiveDetailsSave(HmReceiveBaseDetails hmRecvDetails);
	
	String hotMetalReceiveDetailsUpdate(HmReceiveBaseDetails hmRecvDetails);
	
	String hotMetalReceiveStatusUpdate(Long hmRecvID);
	
	List<HmReceiveBaseDetails> getHMDetailsByQuery(String qry);

	String hotMetalNextProcessUnit(HmReceiveBaseDetails hmdatasave,
			HmReceiveBaseDetails hmdataforupdate);

	HmReceiveBaseDetails getHmReceiveDetailsById(Integer recvid);

	String hotMetalMixDetailsSaveAll(Hashtable<String, Object> mod_obj);

	String hotMetalLadleNextProcessUpdate(Hashtable<String, Object> mod_obj);

	String saveHMDataArray(List<HmReceiveBaseDetails> hmdetList);
	
	public Double updateHmDetails(Integer hmRevId,Double tareWeight);
	
   

	


}
