package com.smes.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.masters.dao.impl.TundishMaintanaceLogModelDao;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.TundishMaintanaceLogModel;
import com.smes.masters.service.impl.LookupMasterService;
import com.smes.trans.dao.impl.TundishTrnsHistoryDao;
import com.smes.trans.model.TundishTrnsHistoryModel;
import com.smes.util.Constants;
import com.smes.util.TundishTransactionDTO;

@Service
public class TundishTrnsHistoryService {

	@Autowired
	TundishTrnsHistoryDao trnsHistoryDao;
	
	@Autowired
	LookupMasterService lkpmstrService;
	
	@Autowired
	TundishMaintanaceLogModelDao tundishMaintanaceLogModelDao;
	
	@Autowired
	LookupMasterService lkpMstrService;
	
	public TundishTrnsHistoryModel getTundishStatusHistoryByTundishId(Integer TundishId){
		List<TundishTrnsHistoryModel> lst=new ArrayList<>();
		lst=trnsHistoryDao.getTundishStatusHistoryByTundishId(TundishId);
		if(!lst.isEmpty()) {
		return trnsHistoryDao.getTundishStatusHistoryByTundishId(TundishId).get(0);
		}else {
			return new TundishTrnsHistoryModel();
		}
	}
	public List<TundishMaintanaceLogModel> getTundishEventsByTnsId(Integer trnsId) {
		List<LookupMasterModel> lst=lkpmstrService.getLookupDtlsByLkpTypeAndStatus("TUNDISH_EVENT", 1);
		List<TundishMaintanaceLogModel> rlst=new ArrayList<>();
		lst.forEach(lkp->{
			List<TundishMaintanaceLogModel> mdl=trnsHistoryDao.getTundishEventsByTnsIdAndEventId(trnsId, lkp.getLookup_id());
			if(!mdl.isEmpty()) {
				rlst.add(mdl.get(0));
				
			}else {
				TundishMaintanaceLogModel obj=new TundishMaintanaceLogModel();
				obj.setTundishEventMdl(lkp);
				obj.setTundishEventId(lkp.getLookup_id());
//				obj.setSen_heat_start(new Date());
//				obj.setSen_heat_end(new Date());
//				obj.setStartTime(new Date());
//				obj.setEndTime(new Date());
				rlst.add(obj);
			}
		});
		return rlst;
	}
	
	public String saveTundishHistoryandEvents(TundishTransactionDTO htryEvtDTO,String user) {
		String result="";
		try {
		List<TundishTrnsHistoryModel> hstry=trnsHistoryDao.getTundishStatusHistoryByTundishId(htryEvtDTO.getHstryMdl().getTundish_id());
		if(hstry.isEmpty()) {//update other entries record status =0 and create new entry
			TundishTrnsHistoryModel model=htryEvtDTO.getHstryMdl();
			model.setCreatedBy(Integer.parseInt(user));
			model.setCreatedDateTime(new Date());
			model.setRecordStatus(1);
			model.setTundish_life(0);
			result=trnsHistoryDao.hstrySave(model);
			saveEvtDetials(htryEvtDTO,user);
			
		}else {//create new entry
			TundishTrnsHistoryModel model=htryEvtDTO.getHstryMdl();
			if(htryEvtDTO.getPrev_status_id()!=model.getTundish_status()) { //change in tundish status
				hstry.forEach(htry->{
					htry.setRecordStatus(0);
					htry.setUpdated_by(Integer.parseInt(user));
					htry.setUpdated_date_time(new Date());
					trnsHistoryDao.hstryUpdate(htry);
				  }
	              );
				LookupMasterModel lkp=lkpMstrService.getLookUpRowById(model.getTundish_status());
				if(lkp.getLookup_value().equals("READY")) {
					model.setTundish_life(0);
					}else {
						model.setTundish_life(hstry.get(0).getTundish_life());
					}
				model.setTrns_id(null);
				model.setCreatedBy(Integer.parseInt(user));
				model.setCreatedDateTime(new Date());
				model.setRecordStatus(1);
				trnsHistoryDao.hstrySave(model);
				saveEvtDetials(htryEvtDTO,user);
				
			}
			
			
		}
		result=Constants.SAVE;
		}catch(Exception ex) {
			ex.printStackTrace();
			result=Constants.SAVE_FAIL;
		}
		return result;
	}
	
	public String saveEvtDetials(TundishTransactionDTO htryEvtDTO,String user) {
		String result="";
		List<TundishTrnsHistoryModel> savedHstry=trnsHistoryDao.getTundishStatusHistoryByTundishId(htryEvtDTO.getHstryMdl().getTundish_id());
		if(!savedHstry.isEmpty()) {
			List<TundishMaintanaceLogModel> lst=htryEvtDTO.getTrnsEventsLog();
			lst.forEach(evt->{
				if(evt.getStartTime()!=null ||evt.getSen_heat_start()!=null || evt.getEndTime()!=null || evt.getSen_heat_end()!=null) {
				evt.setTrns_tundish_id(savedHstry.get(0).getTrns_id());
				evt.setRecordStatus(1);
				evt.setCreatedBy(Integer.parseInt(user));
				evt.setCreatedDateTime(new Date());
				tundishMaintanaceLogModelDao.Save(evt);
				}
			});
			
			
		}
		return result;
	}
	
	public String saveTundishHistry(TundishTrnsHistoryModel model) {
		String result="";
		result=trnsHistoryDao.hstryUpdate(model);
		return result;
	}
	
	
	
	public List<TundishTrnsHistoryModel> getAllhistory(Integer thundishId){
		List<TundishTrnsHistoryModel> hstry=trnsHistoryDao.getTundishStatusHistoryByTundishId(thundishId,1);
		return hstry;
	}
}
