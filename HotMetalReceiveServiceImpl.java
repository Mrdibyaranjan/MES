package com.smes.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.HmLadleMasterModel;
import com.smes.trans.dao.impl.HotMetalReceiveDao;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.HotMetalLadleMixDetails;
import com.smes.util.GenericClass;

@Service("hotMetalReceiveService")
public class HotMetalReceiveServiceImpl implements HotMetalReceiveService{

	@Autowired
	private HotMetalReceiveDao hotMetalReceiveDao;
	
	
	@Autowired
	private CommonService commonService;
	
	@Override
	@Transactional
	public List<HmReceiveBaseDetails> getHMDetailsbyStatus(Integer stage,
			String ladlestatus) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.getHMDetailsbyStatus(stage,ladlestatus);
	}

	@Override
	@Transactional
	public Integer getHMDetailsbyInterface() {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.getHMDetailsbyInterface();
	}

	@Override
	@Transactional
	public String hotMetalReceiveDetailsSave(HmReceiveBaseDetails hmRecvDetails) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.hotMetalReceiveDetailsSave(hmRecvDetails);
	}


	@Override
	public List<HmReceiveBaseDetails> getHMDetailsByQuery(String qry) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.getHMDetailsByQuery(qry);
	}

	@Override
	@Transactional
	public String hotMetalNextProcessUnit(HmReceiveBaseDetails hmdatasave,
			HmReceiveBaseDetails hmdataforupdate) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.hotMetalNextProcessUnit(hmdatasave,hmdataforupdate);
	}
	
	@Override
	@Transactional
	public String hotMetalReceiveStatusUpdate(Long hmRecvID) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.hotMetalReceiveStatusUpdate(hmRecvID);
	}
	
	@Override
	@Transactional
	public String hotMetalReceiveDetailsUpdate(HmReceiveBaseDetails hmRecvDetails) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.hotMetalReceiveDetailsUpdate(hmRecvDetails);
	}

	@Override
	@Transactional
	public HmReceiveBaseDetails getHmReceiveDetailsById(Integer recvid) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.getHmReceiveDetailsById(recvid);
	}

	
	public String hotMetalMixDetailsSaveAll(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.hotMetalMixDetailsSaveAll(mod_obj);
	}

	public String hotMetalMixDetailsSaveAll(HmReceiveBaseDetails hmRecvDetails,String t4n_allids,Integer uid) {
		// TODO Auto-generated method stub
		String result = "";
		
		
		Hashtable<String, Object> mod_obj=new Hashtable<String, Object>();
		
		//Registering Mixed Ladle entry TRNS_HM_RECEIPT_DETAILS

		try{
		hmRecvDetails.setHmRecvDate(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hmRecvDetails.getHmRecvDate_s()));
		hmRecvDetails.setHmLadleProdDt(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hmRecvDetails.getHmLadleProdDt_s()));
		hmRecvDetails.setHmRecvId(null);
		hmRecvDetails.setCreatedBy(uid);
		hmRecvDetails.setCreatedDateTime(new Date());
		hmRecvDetails.setHmSmsMeasuredWt(hmRecvDetails.getHmLadleGrossWt());
		
		Integer stageid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='AVAILABLE' and lookup_status=1");
		Integer ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='SEEOF' and lookup_status=1");
		Integer datasourceid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='DATA_SOURCE_TYPE' and lookup_code='MANUAL' and lookup_status=1");
		Integer hmsourceid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_SOURCE' and lookup_code='MIX' and lookup_status=1");
	
		hmRecvDetails.setHmSource(hmsourceid);
		hmRecvDetails.setHmLadleStageStatus(stageid);
		hmRecvDetails.setHmLadleStatus(ladlestatusid);
		hmRecvDetails.setDataSource(datasourceid);
		
		//result = hotMetalReceiveService.hotMetalReceiveDetailsSave(hmRecvDetails);
		
		mod_obj.put("HM_RECV_SAVE", hmRecvDetails);
		
		//updating ladle status and make it not available
		
		//commonService.updateRecordByQuery("update HmLadleMasterModel set hm_ladle_status='NOT_AVAILABLE',updated_date_time=sysdate,updated_by="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hm_ladle_no='"+hmRecvDetails.getHmLadleNo()+"'");		
	
		HmLadleMasterModel hml=new HmLadleMasterModel();
		
		hml.setHm_ladle_no(hmRecvDetails.getHmLadleNo());
		hml.setHm_ladle_status("NOT_AVAILABLE");
		hml.setUpdated_by(uid);
		hml.setUpdated_date_time(new Date());
		
		mod_obj.put("HM_MSTR_UPDATE", hml);
		
		//Consuming wt and updating status of mix ladles
		
		
			String ids[]=t4n_allids.split("IDS");
			int cnt=0,hmreckey=0,hmldlsts=0;
			String key1="HM_RECV_UPDATE",key2="HM_MSTR_LDL",key3="HM_MIX_INSERT";
			for(int k=0;k<ids.length;k++)
			{
				cnt=cnt+1;
				
			String dats[]=ids[k].split("@");
			
			Integer recvid=Integer.parseInt(dats[0]);
			Double avlwt=Double.parseDouble(dats[1]);
			Double mixqty=Double.parseDouble(dats[2]);
			String ladleno=dats[3];
			Double balwt=avlwt-mixqty;
			
			if(balwt>0)
			{
				Integer lsid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='PARTC' and lookup_status=1");
			
				//commonService.updateRecordByQuery("update HmReceiveBaseDetails set hmLadleStatus="+lsid+",hmAvlblWt="+balwt+",updatedDateTime=sysdate,updatedBy="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hmRecvId="+recvid+"");
				
				HmReceiveBaseDetails hmDetails=getHmReceiveDetailsById(recvid);
				
				hmDetails.setHmLadleStatus(lsid);
				hmDetails.setHmAvlblWt(balwt);
				hmDetails.setUpdatedBy(uid);
				hmDetails.setUpdatedDateTime(new Date());
				hmreckey=hmreckey+1;
				mod_obj.put(key1+k, hmDetails);
				hmDetails=null;
			}else{
				hmreckey=hmreckey+1;
				Integer lsid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='CHRG' and lookup_status=1");
				
				Integer stid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='CONSUMED' and lookup_status=1");
				
				//commonService.updateRecordByQuery("update HmReceiveBaseDetails set hmLadleStatus="+lsid+",hmAvlblWt=0,hmLadleStageStatus="+stid+",updatedDateTime=sysdate,updatedBy="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hmRecvId="+recvid+"");
		
				
				HmReceiveBaseDetails hmDetails=getHmReceiveDetailsById(recvid);
				
				hmDetails.setHmLadleStatus(lsid);
				hmDetails.setHmAvlblWt(0.0);
				hmDetails.setHmLadleStageStatus(stid);
				hmDetails.setUpdatedBy(uid);
				hmDetails.setUpdatedDateTime(new Date());
				
				mod_obj.put(key1+k, hmDetails);
				hmDetails=null;
				
				//updating ladle status and make it available
				
				//commonService.updateRecordByQuery("update HmLadleMasterModel set hm_ladle_status='AVAILABLE',updated_date_time=sysdate,updated_by="+Integer.parseInt(session.getAttribute("USER_APP_ID").toString())+" where hm_ladle_no='"+ladleno+"'");		
				
				if(!hmRecvDetails.getHmLadleNo().equalsIgnoreCase(ladleno))
				{
				hmldlsts=hmldlsts+1;
				HmLadleMasterModel hml1=new HmLadleMasterModel();
				
				hml1.setHm_ladle_no(ladleno);
				hml1.setHm_ladle_status("AVAILABLE");
				hml1.setUpdated_by(uid);
				hml1.setUpdated_date_time(new Date());
				
				mod_obj.put(key2+k, hml1);
				hml1=null;
				}
			}
			
			//inserting mixing ladles into TRNS_HM_LADLE_MIX_MAPPING
			
			HotMetalLadleMixDetails hm=new HotMetalLadleMixDetails();
			
			hm.setCast_no(hmRecvDetails.getCastNo());
			hm.setHm_recv_id(recvid);
			hm.setHm_ladle_wt(mixqty);
			hm.setCreatedBy(uid);
			hm.setCreatedDateTime(new Date());
			hm.setVersion(0);
			//result = hmMixService.HmLadleMixDetailsSave(hm);
			mod_obj.put(key3+k, hm);
			hm=null;
			
			}
			mod_obj.put("HMMIXCNT", cnt);
			mod_obj.put("HMRECVCNT", hmreckey);
			mod_obj.put("HMLDLCNT", hmldlsts);
					
			if(mod_obj!=null)
			{
			//result =eofProductionService.eofHeatProductionSaveAll(mod_obj);
				result=hotMetalMixDetailsSaveAll(mod_obj);
				commonService.updateRecordByQuery("update SeqIdDetails set next_val= next_val+1 where col_key='MIXSeqId'");	
			}
		
		}catch(Exception e)
		{
			e.printStackTrace();
			//logger.error(EOFProductionController.class+" Inside EOFProductionSaveOrUpdate Exception..", e);
			return null;//new RestResponse("FAILURE", e.getMessage());
		}
			
	return result;
	}
	
	public String hotMetalLadleNextProcessUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return hotMetalReceiveDao.hotMetalLadleNextProcessUpdate(mod_obj);
	}

	
	public String hotMetalLadleNextProcessUpdate(
			HmReceiveBaseDetails hmRecvDetails,Integer uid) {
		// TODO Auto-generated method stub
		String result = "";
		Hashtable<String, Object> mod_obj=null;
		try{
		if((hmRecvDetails.getHmRecvId())>0)
		{
			
			String qry="select a from HmReceiveBaseDetails a where a.hmRecvId="+hmRecvDetails.getHmRecvId()+"";
			
			List<HmReceiveBaseDetails> hmDetails=getHMDetailsByQuery(qry);
			
			if(hmDetails.size()>0)
			{
				
		mod_obj=new Hashtable<String, Object>();
		
		HmReceiveBaseDetails hmdataforupdate=new HmReceiveBaseDetails();
		/*This data used to update ladle stage as consumed*/
		Integer con_stageid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='CONSUMED' and lookup_status=1");
		 hmdataforupdate=hmDetails.get(0);
		//hmdataforupdate.setHmRecvId(hmRecvDetails.getHmRecvId());
		
		
		//hmdataforupdate.setHmNextProcess(String.valueOf(con_stageid));//(con_stageid);
		hmdataforupdate.setHmLadleStageStatus(con_stageid);
		hmdataforupdate.setHmLadleStatus(hmDetails.get(0).getHmLadleStatus());
		hmdataforupdate.setUpdatedBy(uid);
		hmdataforupdate.setUpdatedDateTime(new Date());
		
		/*insert new process and update old status*/
		//result = hotMetalReceiveService.hotMetalReceiveDetailsUpdate(hmdataforupdate);
		
		mod_obj.put("UPDATE", hmdataforupdate);
		
		hmdataforupdate=null;
			/* This data used to inserting next process details */
			HmReceiveBaseDetails hmdatasave=new HmReceiveBaseDetails();
			
			List<HmReceiveBaseDetails> hmDetails1=getHMDetailsByQuery(qry);
			
			
			 hmdatasave=hmDetails1.get(0);
			
			Integer stageid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='AVAILABLE' and lookup_status=1");
			
			Integer ladlestatusid=null;
			
			if(hmRecvDetails.getHmNextProcess().equalsIgnoreCase("EAF"))
			{
			ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='SEEOF' and lookup_status=1");
			}else if(hmRecvDetails.getHmNextProcess().equalsIgnoreCase("PCM")){
			ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='PCM' and lookup_status=1");
			}
			
			Integer datasourceid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='DATA_SOURCE_TYPE' and lookup_code='MANUAL' and lookup_status=1");
		
			hmdatasave.setHmLadleStageStatus(stageid);
			hmdatasave.setHmLadleStatus(ladlestatusid);
			hmdatasave.setDataSource(datasourceid);
			hmdatasave.setHmRecvId(null);
			hmdatasave.setHmSeqNo(null);
			hmdatasave.setRemarks("Received From Screen");
			hmdatasave.setCreatedBy(uid);
			hmdatasave.setCreatedDateTime(new Date());
			hmdatasave.setHmRecvDate(new Date());
			//hmdatasave.setUpdatedBy(null);
			//hmdatasave.setUpdatedDateTime(null);
			hmdatasave.setVersion(0);
			//result =hotMetalReceiveService.hotMetalReceiveDetailsSave(hmdatasave);hmRecvDetails
			hmdatasave.setHmSmsMeasuredWt(hmRecvDetails.getHmSmsMeasuredWt());  //change done 
			hmdatasave.setHmLadleTempEof(hmRecvDetails.getHmLadleTempEof());
			mod_obj.put("SAVE", hmdatasave);
			hmdatasave=null;
			
			if(mod_obj!=null)
			{
			//result =eofProductionService.eofHeatProductionSaveAll(mod_obj);
				result =hotMetalLadleNextProcessUpdate(mod_obj);
			}
			}
		}
		}catch(Exception e)
		{
			e.printStackTrace();
			//logger.error(EOFProductionController.class+" Inside EOFProductionSaveOrUpdate Exception..", e);
			return null;//new RestResponse("FAILURE", e.getMessage());
		}
			
	return result;
		
	}
	@Transactional
	@Override
	public String saveHMDataArray(HmReceiveBaseDetails hmRecvDetails, int userId) {
		// TODO Auto-generated method stub
		List<HmReceiveBaseDetails> hmDataList=null;

		try{
			 if(hmRecvDetails.getHm_arry() != null && hmRecvDetails.getHm_arry().toString() != ""&& !(hmRecvDetails.getHm_arry().isEmpty())){
					String row[]=hmRecvDetails.getHm_arry().split("SIDS");
					HmReceiveBaseDetails hmDataObj = null;
					hmDataList=new ArrayList<HmReceiveBaseDetails>();
					
					for(int i=0;i<row.length;i++){
						String id[] = row[i].split("@");
						hmDataObj =new HmReceiveBaseDetails();
						hmDataObj.setHmRecvId(null);
					
						hmDataObj.setCastNo(id[0].toString());
						hmDataObj.setHmLadleTareWt((id[1].equalsIgnoreCase("null") || id[1].isEmpty()) ? null : Double.parseDouble(id[1].toString()));
						hmDataObj.setHmLadleGrossWt((id[2].equalsIgnoreCase("null") || id[2].isEmpty()) ? null : Double.parseDouble(id[2].toString()));
						hmDataObj.setHmLadleNetWt((id[3].equalsIgnoreCase("null") || id[3].isEmpty()) ? null : Double.parseDouble(id[3].toString()));
						hmDataObj.setHmLadleC((id[4].equalsIgnoreCase("null") || id[4].isEmpty()) ? null : Double.parseDouble(id[4].toString()));
						hmDataObj.setHmLadleMn((id[5].equalsIgnoreCase("null") || id[5].isEmpty()) ? null : Double.parseDouble(id[5].toString()));
						hmDataObj.setHmLadleS((id[6].equalsIgnoreCase("null") || id[6].isEmpty()) ? null : Double.parseDouble(id[6].toString()));
						hmDataObj.setHmLadleP((id[7].equalsIgnoreCase("null") || id[7].isEmpty()) ? null : Double.parseDouble(id[7].toString()));
						hmDataObj.setHmLadleSi((id[8].equalsIgnoreCase("null") || id[8].isEmpty()) ? null : Double.parseDouble(id[8].toString()));	
						hmDataObj.setHmLadleTi((id[9].equalsIgnoreCase("null") || id[9].isEmpty()) ? null : Double.parseDouble(id[9].toString()));
						
						hmDataObj.setHmLadleTempBf((id[10].equalsIgnoreCase("null") || id[10].isEmpty()) ? null : Double.parseDouble(id[10].toString()));
						//PSNdtls.setApproved_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm:ss", PSNdtls.getS_approved_date_time()));
						//GenericClass.getFormattedDate(df.parse(id[11].toString()),"dd/MMM/yyyy");
						//heatConsObj.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						hmDataObj.setHmLadleProdDt(GenericClass.getDateObject("YYYY/MM/dd HH:mm", id[11].toString()));
						hmDataObj.setHmAvlblWt((id[12].equalsIgnoreCase("null") || id[12].isEmpty()) ? null : Double.parseDouble(id[12].toString()));
						hmDataObj.setHmLadleNo(id[13].toString());
						Integer hmsourceid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_SOURCE' and lookup_code='"+id[14].toString()+"' and lookup_status=1");
						hmDataObj.setHmSource(hmsourceid);
						hmDataObj.setRemarks("Received from Jpod");
						hmDataObj.setHmSeqNo((id[16].equalsIgnoreCase("null") || id[16].isEmpty()) ? null : Integer.parseInt(id[16].toString()));
						
						Integer stageid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='AVAILABLE' and lookup_status=1");
						Integer ladlestatusid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='RECVD' and lookup_status=1");
						Integer datasourceid=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='DATA_SOURCE_TYPE' and lookup_code='INTERFACE' and lookup_status=1");
					
						hmDataObj.setHmLadleStageStatus(stageid);
						hmDataObj.setHmLadleStatus(ladlestatusid);
						hmDataObj.setDataSource(datasourceid);
						
						hmDataObj.setCreatedBy(userId);	
						hmDataObj.setCreatedDateTime(new Date());
						hmDataObj.setHmRecvDate(new Date());
						hmDataObj.setVersion(0);
						hmDataList.add(hmDataObj);
							
					 }// end out for
					
					
			 }else{
				 System.out.println("in else block");
			 }
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		
		return hotMetalReceiveDao.saveHMDataArray(hmDataList);
	}

	@Override
	public Double updateHmDetails(Integer hmRevId, Double tareWeight) {
		// TODO Auto-generated method stub
		
		return hotMetalReceiveDao.updateHmDetails( hmRevId,  tareWeight);
	}


}
