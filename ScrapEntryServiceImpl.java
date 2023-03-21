package com.smes.trans.service.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.ScrapBucketStatusModel;
import com.smes.masters.service.impl.ScrapBucketStatusMasterService;
import com.smes.trans.dao.impl.ScrapEntryDao;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.ScrapWeightDetails;
import com.smes.util.Constants;
import com.smes.util.GenericClass;

@Service("scrapEntryService")
public class ScrapEntryServiceImpl implements ScrapEntryService{

	@Autowired
	private ScrapEntryDao scrapEntryDao;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ScrapBucketStatusMasterService scrapBucketStatusMasterService;
	
	@Override
	public List<ScrapBucketHdr> getLoadedScrapBktNos() {
		// TODO Auto-generated method stub
		return scrapEntryDao.getLoadedScrapBktNos();
	}
	
	//@Transactional
	@Override
	public ScrapBucketHdr getLoadedScrapBktsByHeaderId(Integer scrap_bkt_hrd_id) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getLoadedScrapBktsByHeaderId(scrap_bkt_hrd_id);
	}

	@Transactional
	@Override
	public List<ScrapBucketDetails> getScrapEntryDetails(Integer scrap_bucket_id, Integer scrap_pattern_id, Integer bucket_header_id) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getScrapEntryDetails(scrap_bucket_id, scrap_pattern_id, bucket_header_id);
	}
	
	public String scrapBktDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return scrapEntryDao.scrapBktDtlsSaveOrUpdate(mod_obj);
	}
	
	public String scrapBktDtlsSaveOrUpdate(ScrapBucketDetails scrapBktDetails,Double totQty,Integer scrap_bkt_id,String scrap_prod_dt,Integer uid, Integer scrap_patrn_id) {
		// TODO Auto-generated method stub
		String result="";
		
		//insert operation
		Hashtable<String, Object> mod_obj = null;
		
		
		mod_obj=new Hashtable<String, Object>();
			
		Integer load_statusId =commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = '"+Constants.SCRAP_BKT_STATUS+"' and lookup_code='"+Constants.SCRAP_BKT_LOAD_STATUS+"'");
		
		ScrapBucketHdr scrapBktHdr=new ScrapBucketHdr();
			
		Integer validateEntry=validateScrapEntry(scrap_bkt_id,load_statusId);
		
		if(validateEntry>0){
			result = "Scrap Bucket is Loaded and is in use. Please unload the bucket.";
			return result;
		}
		
		if(scrapBktDetails.getScrap_bkt_header_id()<1)
		{
			
		scrapBktHdr.setScrap_bkt_header_id(null);
		scrapBktHdr.setCreatedBy(uid);
		scrapBktHdr.setCreatedDateTime(new Date());
		//scrapBktHdr.setVersion(0);
		scrapBktHdr.setScrap_bkt_load_status(load_statusId);
		scrapBktHdr.setScrap_bkt_id(scrap_bkt_id);
		scrapBktHdr.setScrap_pattern_id(scrap_patrn_id);
		}else{
			
			scrapBktHdr=getLoadedScrapBktsByHeaderId(scrapBktDetails.getScrap_bkt_header_id());
			scrapBktHdr.setUpdatedBy(uid);
			scrapBktHdr.setUpdatedDateTime(new Date());
			
		}
		scrapBktHdr.setScrap_prod_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", scrap_prod_dt));
		
		scrapBktHdr.setScrap_bkt_qty(totQty);
		
		mod_obj.put("SCRAPHDR", scrapBktHdr);
		
		String row[]=scrapBktDetails.getGrid_arry().split("&&");
		ScrapBucketDetails buk=null;
		Integer cnt=0;String key="SCRAPBK";
		for(int i=0;i<row.length;i++){
			key=key+i;
			String id[] = row[i].split("@");
			buk=new ScrapBucketDetails();
			if(!id[2].equals("null")){
			
			buk=getScrapBucketDetailsById(Integer.parseInt(id[2]));
			buk.setMaterial_qty(Double.parseDouble(id[1].toString()));
			
			buk.setUpdatedBy(uid);
			buk.setUpdatedDateTime(new Date());
			buk.setRecord_version(Integer.parseInt(id[3]));
			
			}else{
			
			buk.setScrap_bkt_detail_id(null);
			
			buk.setScrap_bkt_header_id(null);
		
			buk.setMaterial_id(Integer.parseInt(id[0]));
				
			buk.setMaterial_qty(Double.parseDouble(id[1].toString()));
				
			buk.setCreatedBy(uid);
			buk.setCreatedDateTime(new Date());
			//buk.setRecord_version(Integer.parseInt(id[3]));
			
			}
			mod_obj.put(key, buk);
			cnt=cnt+1;
			buk=null;
		}
		
		mod_obj.put("SCRAPBKCNT", cnt);
		
		if(scrapBktDetails.getScrap_bkt_header_id()<1)
		{
			ScrapBucketStatusModel scrapBucketStatusModel = scrapBucketStatusMasterService.getScrapBucketMasterByBktId(scrap_bkt_id);
			
			scrapBucketStatusModel.setScrap_bucket_status(load_statusId);
			scrapBucketStatusModel.setUpdated_by(uid);
			scrapBucketStatusModel.setUpdated_date_time(new Date());
			
			mod_obj.put("SCRAPBKSTS", scrapBucketStatusModel);
		}
		if(mod_obj!=null)
		{
			result =  scrapBktDtlsSaveOrUpdate(mod_obj);
		}
		
		return result;
	}

	@Override
	public ScrapBucketDetails getScrapBucketDetailsById(int scrap_det_id) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getScrapBucketDetailsById(scrap_det_id);
	}
	
	@Transactional
	@Override
	public List<ScrapBucketDetails> getScrapBktDetList( Integer scrap_bkt_header_id) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getScrapBktDetList(scrap_bkt_header_id);
	}

	@Transactional
	@Override
	public List<ScrapBucketDetails> getAllScrapBktDetList(String sbktIds) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getAllScrapBktDetList(sbktIds);
	}

	@Transactional
	@Override
	public List<ScrapWeightDetails> getScrapDetailsFrmIntf(String work_center) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getScrapDetailsFrmIntf(work_center);
	}

	@Transactional
	@Override
	public String updateIntfScrapEntry(Integer trans_no) {
		// TODO Auto-generated method stub
		return scrapEntryDao.updateIntfScrapEntries(trans_no);
	}
	
	@Transactional
	@Override
	public Integer validateScrapEntry(Integer scrap_bkt_id,Integer load_statusId) {
		// TODO Auto-generated method stub
		return scrapEntryDao.validateScrapEntry(scrap_bkt_id, load_statusId);
	}

	@Transactional
	@Override
	public List<ScrapBucketDetailsLog> getScrapBktDetLogList(Integer scrap_bkt_header_id) {
		// TODO Auto-generated method stub
		return scrapEntryDao.getScrapBktDetLogList(scrap_bkt_header_id);
	}

	@Override
	public String saveOrUpdateScrapCons(ScrapBucketHdr scrapHdrObj, List<ScrapBucketDetailsLog> logLi) {
		// TODO Auto-generated method stub
		return scrapEntryDao.saveOrUpdateScrapCons(scrapHdrObj, logLi);
	}
}