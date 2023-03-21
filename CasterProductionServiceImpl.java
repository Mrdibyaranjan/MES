package com.smes.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.dao.impl.LookupMasterDao;
import com.smes.masters.model.ActivityDelayMasterModel;
import com.smes.masters.model.TundishMaintanaceLogModel;
import com.smes.masters.service.impl.ActivityDelayMasterService;
import com.smes.trans.dao.impl.CCMHeatSeqGenModelDao;
import com.smes.trans.dao.impl.CCMTundishDetailsDao;
import com.smes.trans.dao.impl.CasterProductionDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.SeqTransactionEventDao;
import com.smes.trans.dao.impl.TundishTrnsHistoryDao;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CCMHeatSeqGenModel;
import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.CCMTundishDetailsModel;
import com.smes.trans.model.CastPlanDetModel;
import com.smes.trans.model.CastRunningStatusModel;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.MtubeTrnsModel;
import com.smes.trans.model.SeqTransactionEvent;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.trans.model.TundishTrnsHistoryModel;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.GenericClass;
import com.smes.wrappers.CasterRequestWrapper;

@Service("CasterProductionService")
public class CasterProductionServiceImpl implements CasterProductionService {
	private static final Logger logger = Logger.getLogger(CasterProductionServiceImpl.class);

	@Autowired
	CasterProductionDao casterProdDao;

	@Autowired
	private HeatPlanDetailsService heatPlanDetailsService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ActivityDelayMasterService activityDlyMstrService;

	@Autowired
	private LRFProductionDao lrfProductionDao;
	
	@Autowired
	private SeqTransactionEventDao seqEventDao;
	
	@Autowired
	private CCMTundishDetailsDao ccmTundishDao;
	
	@Autowired
	private TundishTrnsHistoryDao tundishTrnsDao;
	
	@Autowired
	private TundishTrnsHistoryDao tundishTrnsHistDao;

	@Autowired
	LookupMasterDao lookupmstrDao;

	@Autowired
	CCMHeatSeqGenModelDao ccmHeatSeqGenDao;
	
	@Autowired
	TundishTrnsHistoryDao tundTrnsDao;
	
	@Autowired
	private HeatProceeEventService heatProcessEventService;
	
	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;

	@Transactional
	@Override
	public CastRunningStatusModel getRunningIdDetByHeatPlanNo(Integer heat_planno) {
		// TODO Auto-generated method stub
		return casterProdDao.getRunningIdDetByHeatPlanNo(heat_planno);
	}

	@Transactional
	@Override
	public String saveRunningIdDetails(CastRunningStatusModel runObj, String cast_start_dt, String cast_close_dt,
			int userId) {
		runObj.setCreated_by(userId);
		runObj.setCreated_date_time(new Date());
		runObj.setCast_start_date((GenericClass.getDateObject("dd/MM/yyyy HH:mm", cast_start_dt)));
		runObj.setCast_end_date((GenericClass.getDateObject("dd/MM/yyyy HH:mm", cast_close_dt)));
		return casterProdDao.saveRunningIdDet(runObj, userId);
	}

	@Override
	public List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByCaster(String planstatus, String casterType) {
		// TODO Auto-generated method stub
		List<HeatPlanHdrDetails> hplanList = new ArrayList<HeatPlanHdrDetails>();
		// List<HeatPlanHdrDetails> finalList=new ArrayList<HeatPlanHdrDetails>();
		String planstatusiddata[] = planstatus.split(",");
		String ids = "";
		for (Integer i = 0; i < planstatusiddata.length; i++) {
			Integer stageid = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ planstatus + "' and status_type='PLAN_HEADER'");
			ids = stageid + "," + ids;
		}
		ids = ids.substring(0, ids.length() - 1);
		hplanList = heatPlanDetailsService.getHeatPlanHeaderDetailsByStatus(ids);
		Integer casterId = commonService.getLookupIdByQuery(
				"select lookup_id from LookupMasterModel where lookup_type='CASTER_TYPE' and lookup_value='"
						+ casterType + "' and lookup_status=1");
		for (Iterator it = hplanList.iterator(); it.hasNext();) {
			HeatPlanHdrDetails heatPlanHdrDetails = (HeatPlanHdrDetails) it.next();
			if (!heatPlanHdrDetails.getCaster_type().equals(casterId)) {
				it.remove();
			}

		}
		return hplanList;
	}

	@Override
	public String attachHeatAndGenerateRunId(CastRunningStatusModel crsModel, String start_date, Integer userId) {
		// TODO Auto-generated method stub
		Hashtable<String, Object> htObj = new Hashtable<String, Object>();
		crsModel.setRunning_id(null);
		crsModel.setCast_start_date((GenericClass.getDateObject("dd/MM/yyyy HH:mm", start_date)));
		crsModel.setCreated_by(userId);
		crsModel.setCreated_date_time(new Date());
		crsModel.setRecord_status(1);
		crsModel.setRecord_version(0);
		htObj.put("CAST_RUN_DET", crsModel);

		if (!crsModel.getHeat_plan_id().equals(null)) {
			List<HeatPlanLinesDetails> linesList;
			List<CastPlanDetModel> lineObj = new ArrayList<CastPlanDetModel>();
			// new ArrayList<HeatPlanLinesDetails>();
			linesList = heatPlanDetailsService.getHeatPlanLineDetailsByStatus(crsModel.getHeat_plan_id());
			List<CastPlanDetModel> planList = null;
			CastPlanDetModel cplan = null;

			planList = new ArrayList<CastPlanDetModel>();
			for (HeatPlanLinesDetails heatPlanLinesDetails : linesList) {

				cplan = new CastPlanDetModel();
				cplan.setPlan_sl_no(null);
				Integer heatplanId = crsModel.getHeat_plan_id();
				cplan.setPlan_id(heatplanId);
				cplan.setLine_id(heatPlanLinesDetails.getHeat_line_id());
				cplan.setCreated_by(userId);
				cplan.setCreated_date_time(new Date());
				planList.add(cplan);

			}
			htObj.put("CAST_PLAN_DET", planList);

		}
		return saveAllCastDetails(htObj);
	}

	private String saveAllCastDetails(Hashtable<String, Object> htObj) {
		// TODO Auto-generated method stub
		return casterProdDao.saveAllCastDetails(htObj);
	}

	@Override
	public List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId) {
		// TODO Auto-generated method stub
		// return casterProdDao.getHeatPlanDetWithRunId(runId);
		Integer linestatusid = null;
		linestatusid = commonService.getLookupIdByQuery(
				"select main_status_id from MainHeatStatusMasterModel where main_status_desc='WIP' and status_type='PLAN_LINES'");
		return heatPlanDetailsService.getHeatPlanDetWithRunId(runId, linestatusid);
	}

	@Transactional
	@Override
	public CastRunningStatusModel getRunIdDetWithRunId(Integer runId) {
		// TODO Auto-generated method stub
		return casterProdDao.getRunIdDetWithRunId(runId);
	}

	@Override
	public String saveAll(CasterRequestWrapper casterreq, int userId) {
		logger.info("inside CasterProductionServiceImpl ..saveAll...." + CasterProductionServiceImpl.class);

		Hashtable<String, Object> htObj = new Hashtable<String, Object>();
		CCMHeatDetailsModel castObj = new CCMHeatDetailsModel();
		castObj.setTrns_sl_no(null);
		castObj.setSteel_ladle_no(casterreq.getCasterHeatDetails().getSteel_ladle_no());
		castObj.setHeat_no(casterreq.getCasterHeatDetails().getHeat_no());
		//castObj.setHeat_counter(1);
		castObj.setHeat_counter(casterreq.getCasterHeatDetails().getHeat_counter());
		castObj.setSteel_ladle_wgt(casterreq.getCasterHeatDetails().getSteel_ladle_wgt());
		castObj.setCs_size(casterreq.getCasterHeatDetails().getCs_size());
		castObj.setReturn_qty_id(casterreq.getCasterHeatDetails().getReturn_qty_id());
		castObj.setReturn_type(casterreq.getCasterHeatDetails().getReturn_type());
		castObj.setReturn_qty(casterreq.getCasterHeatDetails().getReturn_qty());
		castObj.setSeq_break(casterreq.getCasterHeatDetails().getSeq_break());
		castObj.setHeat_plan_id(casterreq.getCasterHeatDetails().getHeat_plan_id());
		castObj.setTundish_change(casterreq.getCasterHeatDetails().getTundish_change());
		castObj.setPsn_no(casterreq.getCasterHeatDetails().getPsn_no());
		castObj.setRecord_version(0);
		castObj.setTundish_no(casterreq.getCasterHeatDetails().getTundish_no());
		castObj.setSub_unit_id(casterreq.getCasterHeatDetails().getSub_unit_id());
		castObj.setEof_wc(casterreq.getCasterHeatDetails().getEof_wc());
		castObj.setRoute(casterreq.getCasterHeatDetails().getRoute());
		castObj.setHeat_plan_id(casterreq.getCasterHeatDetails().getHeat_plan_id());
		castObj.setCreated_by(userId);
		castObj.setCreated_date_time(new Date());
		castObj.setRecord_status(1);
		castObj.setProduct_id(casterreq.getCasterHeatDetails().getProduct_id());
		castObj.setSeq_no(casterreq.getCasterHeatDetails().getSeq_no());
		castObj.setTundish_car_no(casterreq.getCasterHeatDetails().getTundish_car_no());
		castObj.setGrade(casterreq.getCasterHeatDetails().getGrade());
		castObj.setHeat_plan_line_no(casterreq.getCasterHeatDetails().getHeat_plan_line_no());
		castObj.setProd_date(casterreq.getCasterHeatDetails().getProd_date());
		castObj.setCasting_powder(casterreq.getCasterHeatDetails().getCasting_powder());
		castObj.setShroud_change(casterreq.getCasterHeatDetails().getShroud_change());
		castObj.setShroud_make(casterreq.getCasterHeatDetails().getShroud_make());
		castObj.setSpectro_chem(0);
		castObj.setProduction_shift(casterreq.getCasterHeatDetails().getProduction_shift());
		castObj.setLadle_car_no(casterreq.getCasterHeatDetails().getLadle_car_no());
		castObj.setLadle_open_type(casterreq.getCasterHeatDetails().getLadle_open_type());
		castObj.setNo_of_pipes(casterreq.getCasterHeatDetails().getNo_of_pipes());
		
		htObj.put("CAST_HEAT_DET", castObj);
		Integer heat_trackId = commonService
				.getLookupIdByQuery("select heat_track_id from HeatStatusTrackingModel where heat_id='"
						+ casterreq.getCasterHeatDetails().getHeat_no() + "'");
		HeatStatusTrackingModel hstm = lrfProductionDao.getHeatStatusObject(heat_trackId);

		hstm.setMain_status(casterreq.getCasterHeatStatus().getMain_status());
		hstm.setAct_proc_path(hstm.getAct_proc_path() + "-" + casterreq.getCasterHeatStatus().getCurrent_unit());
		hstm.setCurrent_unit(casterreq.getCasterHeatStatus().getCurrent_unit());
		hstm.setUnit_process_status(casterreq.getCasterHeatStatus().getUnit_process_status());
		hstm.setBlt_cas_status(casterreq.getCasterHeatStatus().getBlt_cas_status());
		hstm.setUpdatedBy(userId);
		hstm.setUpdatedDateTime(new Date());
		htObj.put("CAST_HEAT_STATUS", hstm);

		List<TundishTrnsHistoryModel> tundTrnsLi = tundTrnsDao.getTundishStatusHistoryByTundishId(casterreq.getCcmTundish().getTun_id());
		TundishTrnsHistoryModel tundTrnsObj = tundTrnsLi.get(0);
		TundishTrnsHistoryModel prevTundTrnsObj =  null;
		TundishTrnsHistoryModel newTundTrnsObj = null;
		TundishTrnsHistoryModel prevNewTundTrnsObj = null;
		Integer tund_life = tundTrnsObj.getTundish_life();
		Integer tund_status;
		
		CCMHeatSeqGenModel seqNoMdl = casterreq.getCcmHeatSeq();
		CCMHeatSeqGenModel prevSeqNoMdl = ccmHeatSeqGenDao
				.getCCMHeatSeqNoByUnit(casterreq.getCasterHeatDetails().getSub_unit_id());
		
		if(seqNoMdl.getSeq_type().equals("primary") || seqNoMdl.getSeq_type().equals("fly")){
			List<TundishTrnsHistoryModel> prevTundTrnsLi = tundTrnsDao.getTundishStatusHistoryByTundishId(casterreq.getCcmTundish().getPrev_tund_id());
			if(prevTundTrnsLi != null && prevTundTrnsLi.size() > 0)
				prevTundTrnsObj = prevTundTrnsLi.get(0);
			
			if(prevTundTrnsObj != null) {
			tund_status = commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='TUNDISH_STATUS' and lookup_value='"+Constants.TUND_STATUS_PREPARATION+"'");
			prevNewTundTrnsObj = setTundishTrnsObject(prevTundTrnsObj, tund_status, 0, userId);
			prevTundTrnsObj.setRecordStatus(0);
			prevTundTrnsObj.setUpdated_by(userId);
			prevTundTrnsObj.setUpdated_date_time(new Date());
			}
			tund_life = tund_life + 1;
			if(tundTrnsObj.getTundishStautsMdl().getLookup_value().equalsIgnoreCase(Constants.TUND_STATUS_READY)){
				tund_status = commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='TUNDISH_STATUS' and lookup_value='"+Constants.TUND_STATUS_RUNNING+"'");
				newTundTrnsObj = setTundishTrnsObject(tundTrnsObj, tund_status, tund_life, userId);
				tundTrnsObj.setRecordStatus(0);
			}
		}else if(seqNoMdl.getSeq_type().equals("continue") || seqNoMdl.getSeq_type().equals("last_heat")){
			tund_life = tund_life + 1;
			if(tundTrnsObj.getTundishStautsMdl().getLookup_value().equalsIgnoreCase(Constants.TUND_STATUS_READY)){
				tund_status = commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='TUNDISH_STATUS' and lookup_value='"+Constants.TUND_STATUS_RUNNING+"'");
				newTundTrnsObj = setTundishTrnsObject(tundTrnsObj, tund_status, tund_life, userId);
				tundTrnsObj.setRecordStatus(0);			
			}else if(tundTrnsObj.getTundishStautsMdl().getLookup_value().equalsIgnoreCase(Constants.TUND_STATUS_RUNNING)){
				tundTrnsObj.setTundish_life(tund_life);
			}
		}
		tundTrnsObj.setUpdated_by(userId);
		tundTrnsObj.setUpdated_date_time(new Date());
		htObj.put("SEL_PREV_TUND_TRNS", tundTrnsObj);
		if(newTundTrnsObj != null){
			htObj.put("SEL_NEW_TUND_TRNS", newTundTrnsObj);
		}
		if(prevTundTrnsObj != null){
			htObj.put("PREV_TUND_TRNS", prevTundTrnsObj);
		}
		if(prevNewTundTrnsObj != null){
			htObj.put("PREV_NEW_TUND_TRNS", prevNewTundTrnsObj);
		}
		if (casterreq.getCcmTundish() != null) {
			casterreq.getCcmTundish().setCreated_by(userId);
			casterreq.getCcmTundish().setCreated_date_time(new Date());
			casterreq.getCcmTundish().setRecord_status(1);
			casterreq.getCcmTundish().setTundish_life(tund_life);
			htObj.put("CCM_TUNDISH_DET", casterreq.getCcmTundish());
		}
		
		seqNoMdl.setSeq_sl_no(prevSeqNoMdl.getSeq_sl_no());
		seqNoMdl.setRecord_status(1);
		seqNoMdl.setUpdated_date_time(new Date());
		seqNoMdl.setUpdated_by(userId);
		seqNoMdl.setUnit_id(prevSeqNoMdl.getUnit_id());
		
		if (seqNoMdl.getSeq_type().equals("continue") || seqNoMdl.getSeq_type().equals("last_heat")) {
			if (prevSeqNoMdl.getIs_primary().equals(1)) {
				seqNoMdl.setPrimary_seq(prevSeqNoMdl.getPrimary_seq() + 1);
			}
			if (prevSeqNoMdl.getIs_flying().equals(1)) {
				seqNoMdl.setFly_seq(prevSeqNoMdl.getFly_seq() + 1);
			}
			
			seqNoMdl.setIs_flying(prevSeqNoMdl.getIs_flying());
			seqNoMdl.setIs_primary(prevSeqNoMdl.getIs_primary());
			seqNoMdl.setFly_seq_si(prevSeqNoMdl.getFly_seq_si());
			seqNoMdl.setLast_fly_cast_no(prevSeqNoMdl.getLast_fly_cast_no());// grouping cast nos		
		} else if (seqNoMdl.getSeq_type().equals("primary")) {
			seqNoMdl.setPrimary_seq(0);
			seqNoMdl.setLast_fly_cast_no(prevSeqNoMdl.getLast_fly_cast_no() + 1);// grouping cast nos
			
			seqNoMdl.setIs_flying(0);
			seqNoMdl.setIs_primary(1);
			seqNoMdl.setFly_seq_si(0);
			seqNoMdl.setFly_seq(0);
			
			CCMSeqGroupDetails seqGroupDtlsObj = new CCMSeqGroupDetails();
			seqGroupDtlsObj.setSeq_group_no(prevSeqNoMdl.getLast_fly_cast_no() + 1);
			seqGroupDtlsObj.setSub_unit_id(casterreq.getCasterHeatDetails().getSub_unit_id());
			seqGroupDtlsObj.setSeq_status(1);
			seqGroupDtlsObj.setDelay_entry("N");
			seqGroupDtlsObj.setCreated_by(userId);
			seqGroupDtlsObj.setCreated_date_time(new Date());
			htObj.put("SEQ_GROUP_DTLS", seqGroupDtlsObj);
		} else if (seqNoMdl.getSeq_type().equals("fly")) {
			seqNoMdl.setFly_seq(0);
			seqNoMdl.setFly_seq_si(prevSeqNoMdl.getFly_seq_si() + 1);
			seqNoMdl.setLast_fly_cast_no(prevSeqNoMdl.getLast_fly_cast_no());	
			
			seqNoMdl.setPrimary_seq(prevSeqNoMdl.getPrimary_seq());		
			seqNoMdl.setIs_flying(1);
			seqNoMdl.setIs_primary(0);
		}		
		htObj.put("CAST_SEQ", seqNoMdl);
						
		return saveAllCastHeatDetails(htObj);
	}
	
	private TundishTrnsHistoryModel setTundishTrnsObject(TundishTrnsHistoryModel tundTrnsObj, Integer tund_status, Integer tund_life, Integer userId){
		TundishTrnsHistoryModel pTundTrnsObj = new TundishTrnsHistoryModel();
		pTundTrnsObj.setTundish_status(tund_status);
		pTundTrnsObj.setTundish_life(tund_life);
		pTundTrnsObj.setTundish_id(tundTrnsObj.getTundish_id());
		pTundTrnsObj.setTrns_date(new Date());
		pTundTrnsObj.setSafty_lining_make(tundTrnsObj.getSafty_lining_make());
		pTundTrnsObj.setWorking_lining_make(tundTrnsObj.getWorking_lining_make());
		pTundTrnsObj.setSpray_mass(tundTrnsObj.getSpray_mass());
		pTundTrnsObj.setSen_type(tundTrnsObj.getSen_type());
		pTundTrnsObj.setSen_dia(tundTrnsObj.getSen_dia());
		pTundTrnsObj.setSen_make(tundTrnsObj.getSen_make());
		pTundTrnsObj.setMbs_make(tundTrnsObj.getMbs_make());
		pTundTrnsObj.setPatching_qty(tundTrnsObj.getPatching_qty());
		pTundTrnsObj.setRecordStatus(1);
		pTundTrnsObj.setCreatedBy(userId);
		pTundTrnsObj.setCreatedDateTime(new Date());
				
		return pTundTrnsObj;
	}

	private String saveAllCastHeatDetails(Hashtable<String, Object> htObj) {
		// TODO Auto-generated method stub
		return casterProdDao.saveAllCastHeatDetails(htObj);
	}

	@Override
	public String saveCastDetails(CCMHeatDetailsModel model) {
		// TODO Auto-generated method stub
		return casterProdDao.saveCastDetails(model);
	}

	@Transactional
	@Override
	public MtubeTrnsModel getMtubelife(Integer mtube_sl_no) {
		// TODO Auto-generated method stub
		return casterProdDao.getMtubelife(mtube_sl_no);
	}

	@Transactional
	@Override
	public List<HeatChemistryChildDetails> getLrfLiftChemDetails(
			String heat_id, Integer heat_counter, Integer aim_psn) {
		// TODO Auto-generated method stub
		return casterProdDao.getLrfLiftChemDetails(heat_id, heat_counter, aim_psn);
	}
	
	@Transactional
	@Override
	public List<CommonCombo> getheatId(Integer lookup_id) {
		// TODO Auto-generated method stub
		return casterProdDao.getheatId(lookup_id);
	}

	@Override
	public List<DelayEntryDTO> getDelayEntriesBySubUnitAndHeat(
			Integer sub_unit_id, Integer trns_si_no, String heat_no, Integer heat_counter) {
		List<ActivityDelayMasterModel> delays = activityDlyMstrService.getAllActivityDetalMasterBySubUnit(sub_unit_id);
		List<ActivityDelayMasterModel> seq_delays = new ArrayList<ActivityDelayMasterModel>();
		List<ActivityDelayMasterModel> heat_delays = new ArrayList<ActivityDelayMasterModel>();
		List<ActivityDelayMasterModel> delayEntries;
		List<DelayEntryDTO> ccmDelay = new ArrayList<DelayEntryDTO>();
		Map<String, HeatProcessEventDetails> heatEventsMap = null;
		Date minCastStart = null, maxCastStop = null, prevMaxCastStop = null;;
		for(ActivityDelayMasterModel d_obj : delays){
			if(d_obj.getAttribute2() != null && d_obj.getAttribute2().equals("SEQ"))
				seq_delays.add(d_obj);
			else
				heat_delays.add(d_obj);
		}
		if(heat_no.equals("0")){
			delayEntries = seq_delays;
			List<SeqTransactionEvent> seqEvents = seqEventDao.getSeqTrnasEventsByGroupNo(trns_si_no, sub_unit_id);
			for(SeqTransactionEvent seq_event_obj : seqEvents){
				if(seq_event_obj.getEventLkpMdl().getLookup_value().contains("CAST START")){ 
					if(minCastStart == null)
						minCastStart = seq_event_obj.getEvent_date_time();
					else if(seq_event_obj.getEvent_date_time().before(minCastStart))
						minCastStart = seq_event_obj.getEvent_date_time();
				}else if(seq_event_obj.getEventLkpMdl().getLookup_value().contains("CAST STOP")){
					if(maxCastStop == null)
						maxCastStop = seq_event_obj.getEvent_date_time();
					else if(seq_event_obj.getEvent_date_time().after(maxCastStop))
						maxCastStop = seq_event_obj.getEvent_date_time();
				}
			}
		}else{
			delayEntries = heat_delays;
			List<HeatProcessEventDetails> heatEvents = heatProcessEventService.getHeatProcessEventDtlsByUnit(heat_no, heat_counter, sub_unit_id);
			heatEventsMap = new HashMap<String, HeatProcessEventDetails>();
			for (HeatProcessEventDetails eventObj : heatEvents) {
				heatEventsMap.put(eventObj.getEventMstrMdl().getEvent_desc().trim(), eventObj);
			}
		}
		for (ActivityDelayMasterModel activityDelayMasterModel : delayEntries) {
			TransDelayEntryHeader transDelayHdr = transDelayEntryHeaderService.getTransDelayHeaderByHeatAndActivity(
					activityDelayMasterModel.getActivity_delay_id(), trns_si_no);
			DelayEntryDTO transDTO = new DelayEntryDTO();
			transDTO.setActivity_master(activityDelayMasterModel);
			long diff;
			long diffMinutes;
			Date startEvent = null, endEvent = null;
			
			if (transDelayHdr == null) {
				if(activityDelayMasterModel.getActivity_seq() == 1) {
					if(heatEventsMap.get(Constants.CCM_LDL_OPEN) != null && heatEventsMap.get(Constants.CCM_LDL_CLOSE) != null){
						startEvent = heatEventsMap.get(Constants.CCM_LDL_OPEN).getEvent_date_time();
						endEvent = heatEventsMap.get(Constants.CCM_LDL_CLOSE).getEvent_date_time();
					}
				}else if(activityDelayMasterModel.getActivity_seq() == 2){
					List<CCMTundishDetailsModel> ccmTundishLi =  ccmTundishDao.getccmTundishDetailsByTrnsSlno(trns_si_no);
					List<TundishTrnsHistoryModel> trnsTundishLi = tundishTrnsDao.getTundishStatusHistoryByTundishId(ccmTundishLi.get(0).getTun_id());
					List<TundishMaintanaceLogModel> tundMaintLogLi = tundishTrnsHistDao.getTundishEventsByTnsIdAndEventId(trnsTundishLi.get(0).getTrns_id(),
							commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='TUNDISH_EVENT' and lookup_value='"
											+Constants.CCM_TUNDISH_HEATING+"' and lookup_status=1"));
					if(tundMaintLogLi.size() != 0 && heatEventsMap.get(Constants.CCM_LDL_OPEN) != null){
						startEvent = tundMaintLogLi.get(0).getEndTime();
						endEvent = heatEventsMap.get(Constants.CCM_LDL_OPEN).getEvent_date_time();
					}
				}else if(activityDelayMasterModel.getActivity_seq() == 3){
					if(heatEventsMap.get(Constants.CCM_LDL_ARM_POS) != null && heatEventsMap.get(Constants.CCM_LDL_OPEN) != null){
						startEvent = heatEventsMap.get(Constants.CCM_LDL_ARM_POS).getEvent_date_time();
						endEvent = heatEventsMap.get(Constants.CCM_LDL_OPEN).getEvent_date_time();
					}
				}else if(activityDelayMasterModel.getActivity_seq() == 4){				
					CCMSeqGroupDetails seqGroupObj = seqEventDao.getCCMSeqGroupDtlById(trns_si_no);
					CCMSeqGroupDetails prevSeqGroupObj = seqEventDao.getCCMSeqGroupDtlByNoUnit(seqGroupObj.getSeq_group_no(), sub_unit_id);
					if(prevSeqGroupObj != null){
						List<SeqTransactionEvent> prevSeqEvents = seqEventDao.getSeqTrnasEventsByGroupNo(prevSeqGroupObj.getSeq_group_dtls_sl_no(), sub_unit_id);
						for(SeqTransactionEvent seq_event_obj : prevSeqEvents){
							if(seq_event_obj.getEventLkpMdl().getLookup_value().contains("CAST STOP")){
								if(prevMaxCastStop == null)
									prevMaxCastStop = seq_event_obj.getEvent_date_time();
								else if(seq_event_obj.getEvent_date_time().after(prevMaxCastStop))
									prevMaxCastStop = seq_event_obj.getEvent_date_time();
							}
						}
					}
					startEvent = prevMaxCastStop;
					endEvent = minCastStart;
				}else if(activityDelayMasterModel.getActivity_seq() == 5){
					startEvent = minCastStart;
					endEvent = maxCastStop;
				}
				if (startEvent != null && endEvent != null) {
					transDTO.setStart_time(startEvent);
					transDTO.setEnd_time(endEvent);
					diff = endEvent.getTime() - startEvent.getTime();
					diffMinutes = diff / (60 * 1000);
					transDTO.setDuration(diffMinutes);
					if(activityDelayMasterModel.getStd_cycle_time() > diffMinutes) {
						diffMinutes = 0;
						transDTO.setDelay(diffMinutes);
					}else {
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
					}
				}
			}else {
				transDTO.setStart_time(transDelayHdr.getActivity_start_time());
				transDTO.setEnd_time(transDelayHdr.getActivity_end_time());
				transDTO.setDuration((long) transDelayHdr.getActivity_duration());
				if(transDelayHdr.getTotal_delay()!=null) {
					transDTO.setDelay((long) transDelayHdr.getTotal_delay());
				}
				transDTO.setTransDelayEntryhdr(transDelayHdr);
				transDTO.setCorrective_action(transDelayHdr.getCorrective_action());
			}
			ccmDelay.add(transDTO);
		}
		return ccmDelay;
	}

	@Override
	public MtubeTrnsModel getMouldDetails(Integer mtube_Trans_id) {
		
		return ccmTundishDao.mtubeTrns(mtube_Trans_id);
	}
}