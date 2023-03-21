package com.smes.trans.service.impl;

import java.util.List;

import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.ScrapWeightDetails;


public interface ScrapEntryService {
	
	/*List<ScrapBucketDetails> getScrapDtlsEntryDetails(String scrap_prod_date,Integer scrap_bkt_id);
	public String scrapBktHdrSave(ScrapBucketHdr scrapBktHdrSave);
	public String scrapBktHdrUpdate(ScrapBucketHdr scrapBktHdrUpdate);
	
	public String scrapBktDtlsSave(ScrapBucketDetails scrapBktDetailsSave);
	public String scrapBktDtlsUpdate(ScrapBucketDetails scrapBktDetailsUpdate);
	List<ScrapBucketHdr> getScrapHdrEntryDetails(String scrap_prod_dt, Integer scrap_bkt_id);
	public Integer getMaxHDRScrap_bkt_header_id(Integer scrap_bkt_id);*/
	
	List<ScrapBucketHdr> getLoadedScrapBktNos();	
	ScrapBucketHdr getLoadedScrapBktsByHeaderId(Integer scrap_bkt_hrd_id);
	List<ScrapBucketDetails> getScrapEntryDetails(Integer scrap_bucket_id, Integer scrap_pattern_id, Integer bucket_header_id);
	String scrapBktDtlsSaveOrUpdate(ScrapBucketDetails scrapBktDetails,Double totQty,Integer scrap_bkt_id,String scrap_prod_dt,Integer uid, Integer scrap_patrn_id);
	ScrapBucketDetails getScrapBucketDetailsById(int scrap_det_id);
	List<ScrapBucketDetails> getScrapBktDetList(Integer scrap_bkt_header_id);
	List<ScrapBucketDetails> getAllScrapBktDetList(String sbktIds);
	List<ScrapWeightDetails> getScrapDetailsFrmIntf(String work_center);
	String updateIntfScrapEntry(Integer trans_no);
	Integer validateScrapEntry(Integer scrap_bkt_id,Integer load_statusId);
	List<ScrapBucketDetailsLog> getScrapBktDetLogList(Integer scrap_bkt_header_id);
	String saveOrUpdateScrapCons(ScrapBucketHdr scrapHdrObj, List<ScrapBucketDetailsLog> logLi);
}
