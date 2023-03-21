package com.smes.trans.service.impl;

import java.util.Hashtable;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.smes.trans.model.HmReceiveBaseDetails;

public interface HotMetalReceiveService {

	public List<HmReceiveBaseDetails> getHMDetailsbyStatus(Integer stageid,
			String ids);

	public Integer getHMDetailsbyInterface();

	public String hotMetalReceiveDetailsSave(HmReceiveBaseDetails hmRecvDetails);
	
	 String hotMetalReceiveDetailsUpdate(HmReceiveBaseDetails hmRecvDetails);

	public List<HmReceiveBaseDetails> getHMDetailsByQuery(String qry);

	public String hotMetalNextProcessUnit(HmReceiveBaseDetails hmdatasave,
			HmReceiveBaseDetails hmdataforupdate);

	public HmReceiveBaseDetails getHmReceiveDetailsById(Integer recvid);

	public String hotMetalMixDetailsSaveAll(HmReceiveBaseDetails hmRecvDetails,String t4n_allids,Integer uid);

	public String hotMetalLadleNextProcessUpdate(
			HmReceiveBaseDetails hmRecvDetails,Integer uid);

	public String saveHMDataArray(HmReceiveBaseDetails hmRecvDetails,
			int userId);
	
	public Double updateHmDetails(Integer hmRevId,Double tareWeight);
	
	public String hotMetalReceiveStatusUpdate(Long hmRecvID);
	
	

	

}
