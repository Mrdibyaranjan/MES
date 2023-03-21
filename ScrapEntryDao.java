package com.smes.trans.dao.impl;

import java.util.Hashtable;
import java.util.List;

import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.ScrapWeightDetails;

public interface ScrapEntryDao {

	/*List<ScrapBucketDetails> getScrapDtlsEntryDetails(String scrap_prod_date,Integer scrap_bkt_id);
	public String scrapBktHdrSave(ScrapBucketHdr scrapBktHdrSave);
	public String scrapBktHdrUpdate(ScrapBucketHdr scrapBktHdrUpdate);
	
	public String scrapBktDtlsSave(ScrapBucketDetails scrapBktDetailsSave);
	public String scrapBktDtlsUpdate(ScrapBucketDetails scrapBktDetailsUpdate);
	
	List<ScrapBucketHdr> getScrapHdrEntryDetails(String scrap_prod_dt, Integer scrap_bkt_id);
	public Integer getMaxHDRScrap_bkt_header_id(Integer bktid);*/
	
	List<ScrapBucketHdr> getLoadedScrapBktNos();
	
	ScrapBucketHdr getLoadedScrapBktsByHeaderId(Integer scrap_bkt_hrd_id);
	List<ScrapBucketDetails> getScrapEntryDetails(Integer scrap_bucket_id, Integer scrap_pattern_id, Integer bucket_header_id);
	String scrapBktDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj);
	ScrapBucketDetails getScrapBucketDetailsById(int scrap_det_id);
	List<ScrapBucketDetails> getScrapBktDetList(Integer scrap_bkt_header_id);
	List<ScrapBucketDetails> getAllScrapBktDetList(String sbktIds);
	List<ScrapWeightDetails> getScrapDetailsFrmIntf(String work_center);
	String updateIntfScrapEntries(Integer trans_no);
	Integer validateScrapEntry(Integer scrap_bkt_id,Integer load_statusId);
	List<ScrapBucketDetailsLog> getScrapBktDetLogList(Integer scrap_bkt_header_id);
	String saveOrUpdateScrapCons(ScrapBucketHdr scrapHdrObj, List<ScrapBucketDetailsLog> logLi);
}
