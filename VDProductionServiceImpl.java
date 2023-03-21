package com.smes.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.ActivityDelayMasterModel;
import com.smes.masters.model.MtrlProcessConsumableMstrModel;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.service.impl.ActivityDelayMasterService;
import com.smes.masters.service.impl.LookupMasterService;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.VDProductionDao;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.CommonPsnChemistry;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.GenericClass;
import com.smes.util.VDHeatConsumableDisplay;
import com.smes.wrappers.LRFRequestWrapper;
import com.smes.wrappers.VDRequestWrapper;

@Service("VDProductionService")
public class VDProductionServiceImpl implements VDProductionService {
	private static final Logger logger = Logger.getLogger(VDProductionServiceImpl.class);
	@Autowired
	private VDProductionDao vdproductionDao;

	@Autowired
	private LRFProductionDao lrfProductionDao;

	@Autowired
	private ActivityDelayMasterService activityDlyMstrServie;

	@Autowired
	private HeatProceeEventService heatProcessEventService;

	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;

	@Autowired
	private SteelLadleStatusMasterService steelLadleService;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HeatProceeEventDao heatProceeEventDao;
	
	@Autowired
	private LookupMasterService lookupService;

	@Transactional
	@Override
	public VDHeatDetailsModel getVDHeatDtlsFormByID(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return vdproductionDao.getVDHeatDtlsFormByID(trns_sl_no);
	}

	@Override
	public String saveAll(VDRequestWrapper reqWrapper, int userId) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String result = "";
		SteelLadleTrackingModel stTrackObj = new SteelLadleTrackingModel();
		try {
			Hashtable<String, Object> mod_obj = null;
			mod_obj = new Hashtable<String, Object>();
			if (!reqWrapper.getVdHeatDetails().equals(null)) {
				reqWrapper.getVdHeatDetails().setCreated_by(userId);
				reqWrapper.getVdHeatDetails().setProduction_date(new Date());
				reqWrapper.getVdHeatDetails().setRecord_status(1);
				reqWrapper.getVdHeatDetails().setCreated_date_time(new Date());
				reqWrapper.getVdHeatDetails().setVd_start_date_time(new Date());

				if (reqWrapper.getVdHeatDetails().getSteel_ladle_no() > 0) {
					stTrackObj = steelLadleService
							.getStLadlleTrackObjById(reqWrapper.getVdHeatDetails().getSteel_ladle_no());
					reqWrapper.getVdHeatDetails().setSteel_ladle_no(stTrackObj.getSt_ladle_si_no());

					Integer ladle_status_id;
					if (stTrackObj != null) {
						ladle_status_id = commonService.getLookupIdByQuery(
								"select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status_id="
										+ Constants.LADLE_RCVD_IN_VD + " and  recordStatus=1");
						stTrackObj.setLadle_status(ladle_status_id);
						stTrackObj.setUpdatedBy(userId);
						stTrackObj.setUpdatedDateTime(new Date());
					}

				}
				mod_obj.put("VD_HEAT_DET", reqWrapper.getVdHeatDetails());
				mod_obj.put("VD_ST_LALDE_STATUS_UPDATE", stTrackObj);

			}

			if (reqWrapper.getVdCrewDetList().size() > 0) {
				reqWrapper.getVdCrewDetList().stream().forEach(c -> c.setCreated_by(userId));
				reqWrapper.getVdCrewDetList().stream().forEach(c -> c.setCreated_date_time(new Date()));

				mod_obj.put("VD_CREW_DET", reqWrapper.getVdCrewDetList());
			}
			if (!reqWrapper.getVdHeatStatus().equals(null)) {
				HeatStatusTrackingModel hstm = lrfProductionDao
						.getHeatStatusObject(new Integer(reqWrapper.getVdHeatStatus().getHeat_track_id()));
				hstm.setMain_status(reqWrapper.getVdHeatStatus().getMain_status());
				hstm.setAct_proc_path(reqWrapper.getVdHeatStatus().getAct_proc_path());
				hstm.setCurrent_unit(reqWrapper.getVdHeatStatus().getCurrent_unit());
				hstm.setUnit_process_status(reqWrapper.getVdHeatStatus().getUnit_process_status());
				hstm.setVd_status(reqWrapper.getVdHeatStatus().getVd_status());
				hstm.setUpdatedBy(userId);
				hstm.setUpdatedDateTime(new Date());
				mod_obj.put("VD_HEAT_STATUS", hstm);
			}
			if (mod_obj != null) {
				result = VDHeatProductionSaveAll(mod_obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	private String VDHeatProductionSaveAll(Hashtable<String, Object> mod_obj) {
		return vdproductionDao.saveAll(mod_obj);
	}

	@Override
	public String heatProcessEventSaveOrUpdate(HeatProcessEventDetails heatProcessEventDtls, int userId) {

		String result = "";

		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		if (heatProcessEventDtls.getHeat_id() != null && heatProcessEventDtls.getHeat_id() != ""
				&& heatProcessEventDtls.getHeat_proc_event_id() == 0) {
			heatProcessEventDtls.setHeat_proc_event_id(null);
			heatProcessEventDtls.setHeat_id(heatProcessEventDtls.getHeat_id());
			heatProcessEventDtls.setHeat_counter(heatProcessEventDtls.getHeat_counter());
			heatProcessEventDtls.setEvent_id(heatProcessEventDtls.getEvent_id());
			heatProcessEventDtls.setEvent_date_time(
					GenericClass.getDateObject("dd/MM/yyyy HH:mm", heatProcessEventDtls.getEvent_date()));
			heatProcessEventDtls.setCreatedBy(userId);
			heatProcessEventDtls.setCreatedDateTime(new Date());
			heatProcessEventDtls.setVersion(0);
		}

		mod_obj.put("HEATPROCESS_VD_EVENT", heatProcessEventDtls);
		mod_obj.put("HEATPROCESSEVENT_CNT", 0);
		result = processEventDtlsSaveOrUpdate(mod_obj);

		return result;

	}

	public String processEventDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return vdproductionDao.processEventDtlsSaveOrUpdate(mod_obj);
	}

	@Override
	public String saveVdDispatchDet(VDRequestWrapper reqWrapper, int userId) {
		String result = "";
		try {
			Hashtable<String, Object> mod_obj = null;
			mod_obj = new Hashtable<String, Object>();
			if (!reqWrapper.getVdHeatDetails().equals(null)) {
				if (!reqWrapper.getVdHeatDetails().getTrns_si_no().equals(null)
						&& !reqWrapper.getVdHeatDetails().getVd_process_remarks().equals(null)
						&& !reqWrapper.getVdHeatDetails().getVd_dispatch_wgt().equals(null)
						&& !reqWrapper.getVdHeatDetails().getDispatch_to_unit().equals(null)
						&& !reqWrapper.getVdHeatDetails().getDispatch_temp().equals(null)) {

					VDHeatDetailsModel vdModel = vdproductionDao
							.getVdHeatObject(reqWrapper.getVdHeatDetails().getTrns_si_no());
					vdModel.setVd_process_remarks(reqWrapper.getVdHeatDetails().getVd_process_remarks());
					vdModel.setBefore_vd_temp(reqWrapper.getVdHeatDetails().getBefore_vd_temp());
					vdModel.setDispatch_temp(reqWrapper.getVdHeatDetails().getDispatch_temp());
					vdModel.setDispatch_to_unit(reqWrapper.getVdHeatDetails().getDispatch_to_unit());
					vdModel.setVd_dispatch_wgt(reqWrapper.getVdHeatDetails().getVd_dispatch_wgt());
					vdModel.setVd_end_date_time(new Date());
					vdModel.setDispatch_date(new Date());
					vdModel.setUpdated_by(userId);
					vdModel.setUpdated_date_time(new Date());
					mod_obj.put("VD_HEAT_DET", vdModel);
				}
			}
			if (!reqWrapper.getVdHeatStatus().equals(null)) {
				HeatStatusTrackingModel hstm = lrfProductionDao
						.getHeatStatusObject(new Integer(reqWrapper.getVdHeatStatus().getHeat_track_id()));
				hstm.setMain_status(reqWrapper.getVdHeatStatus().getMain_status());
				// hstm.setAct_proc_path(hstm.getAct_proc_path()+"-"+reqWrapper.getLrfHeatStatus().getAct_proc_path());
				hstm.setCurrent_unit(reqWrapper.getVdHeatStatus().getCurrent_unit());
				hstm.setUnit_process_status(reqWrapper.getVdHeatStatus().getUnit_process_status());
				hstm.setLrf_status(reqWrapper.getVdHeatStatus().getLrf_status());
				hstm.setEof_status(reqWrapper.getVdHeatStatus().getEof_status());
				hstm.setVd_status(reqWrapper.getVdHeatStatus().getVd_status());
				hstm.setBlt_cas_status(reqWrapper.getVdHeatStatus().getBlt_cas_status());
				hstm.setBlm_cas_status(reqWrapper.getVdHeatStatus().getBlm_cas_status());
				hstm.setUpdatedBy(userId);
				hstm.setUpdatedDateTime(new Date());
				mod_obj.put("VD_HEAT_STATUS", hstm);
			}
			if (mod_obj != null) {
				result = updateVDHeatDetails(mod_obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	private String updateVDHeatDetails(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return vdproductionDao.updateVDHeatDetails(mod_obj);
	}

	@Override
	public List<DelayEntryDTO> getVDDelayEntriesBySubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id,
			Integer heat_counter) {

		logger.info("Inside getVDDelayEntriesBySubUnitAndHeat");

		List<ActivityDelayMasterModel> delayEntries = activityDlyMstrServie
				.getAllActivityDetalMasterBySubUnit(sub_unit_id);// Get Delay Event Header with Cycle Time

		VDHeatDetailsModel passedHeatinfo = vdproductionDao.getVdHeatObject(trans_heat_id);
		List<HeatProcessEventDetails> currentHeatevents = heatProcessEventService
				.getHeatProcessEventDtlsByUnit(passedHeatinfo.getHeat_id(), heat_counter, sub_unit_id);// Current Heat
																										// Process Event
		Map<String, HeatProcessEventDetails> currentHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		for (HeatProcessEventDetails heatProcessEventDetail : currentHeatevents) {
			currentHeatEvts.put(heatProcessEventDetail.getEventMstrMdl().getEvent_desc().trim(), heatProcessEventDetail);
		}

		LRFHeatDetailsModel lrfObj = lrfProductionDao.getLRFHeatDetailsByHeatNo(passedHeatinfo.getHeat_id(), passedHeatinfo.getHeat_counter());
		List<HeatProcessEventDetails> lrfHeatevents = heatProcessEventService
				.getHeatProcessEventDtlsByUnit(lrfObj.getHeat_id(), lrfObj.getHeat_counter(), lrfObj.getSub_unit_id());
		Map<String, HeatProcessEventDetails> lrfHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		for (HeatProcessEventDetails lrfEventObj : lrfHeatevents) {
			lrfHeatEvts.put(lrfEventObj.getEventMstrMdl().getEvent_desc().trim(), lrfEventObj);
		}
		
		List<DelayEntryDTO> vdDelay = new ArrayList<DelayEntryDTO>();

		for (ActivityDelayMasterModel activityDelayMasterModel : delayEntries) {
			DelayEntryDTO transDTO = new DelayEntryDTO();
			transDTO.setActivity_master(activityDelayMasterModel);
			long diff;
			long diffMinutes;
			TransDelayEntryHeader transDelayHdr = transDelayEntryHeaderService.getTransDelayHeaderByHeatAndActivity(
					activityDelayMasterModel.getActivity_delay_id(), passedHeatinfo.getTrns_si_no());// Check Already
																										// Exist or Not
			if (transDelayHdr == null) {
				switch (activityDelayMasterModel.getActivity_seq()) {

				case 1:// Heat Dispatch from LRF to VD Start
					HeatProcessEventDetails vdStart1 = currentHeatEvts.get(Constants.HEAT_PROCESS_EVENT_VD_DISP);					
					HeatProcessEventDetails vdEnd1 = currentHeatEvts.get(Constants.VD_START_TIME);
					
					if (vdStart1 != null && vdEnd1 != null) {
						if (vdStart1.getEvent_date_time() != null && vdEnd1 != null) {
							transDTO.setStart_time(vdStart1.getEvent_date_time());
							transDTO.setEnd_time(vdEnd1.getEvent_date_time());
							diff = vdEnd1.getEvent_date_time().getTime() - vdStart1.getEvent_date_time().getTime();
							diffMinutes = diff / (60 * 1000);
							transDTO.setDuration((long) diffMinutes);
							transDTO.setDelay(checkNegative(diffMinutes - activityDelayMasterModel.getStd_cycle_time()));
						}
					}
					break;

				case 2:  //VD start to Holding Start 
					HeatProcessEventDetails vdStart2 = currentHeatEvts.get(Constants.VD_START_TIME);
					HeatProcessEventDetails vdEnd2 = currentHeatEvts.get(Constants.VD_PUMP_HOLD_TIME);
					if (vdStart2 != null && vdEnd2 != null) {
						if (vdStart2.getEvent_date_time() != null && vdEnd2.getEvent_date_time() != null) {
							transDTO.setStart_time(vdStart2.getEvent_date_time());
							transDTO.setEnd_time(vdEnd2.getEvent_date_time());
							diff = vdEnd2.getEvent_date_time().getTime() - vdStart2.getEvent_date_time().getTime();
							diffMinutes = diff / (60 * 1000);
							transDTO.setDuration((long) diffMinutes);
							transDTO.setDelay(checkNegative(diffMinutes - activityDelayMasterModel.getStd_cycle_time()));
						}
					}
					break;
				case 3:// Time between Holding start and VD/Holding End
					HeatProcessEventDetails vdStart3 = currentHeatEvts.get(Constants.VD_PUMP_HOLD_TIME);
					HeatProcessEventDetails vdEnd3 = currentHeatEvts.get(Constants.VD_END_TIME);
					if (vdStart3 != null && vdEnd3 != null) {
						if (vdStart3.getEvent_date_time() != null && vdEnd3.getEvent_date_time() != null) {
							transDTO.setStart_time(vdStart3.getEvent_date_time());
							transDTO.setEnd_time(vdEnd3.getEvent_date_time());
							diff = vdEnd3.getEvent_date_time().getTime() - vdStart3.getEvent_date_time().getTime();
							diffMinutes = diff / (60 * 1000);
							transDTO.setDuration((long) diffMinutes);
							transDTO.setDelay(checkNegative(diffMinutes - activityDelayMasterModel.getStd_cycle_time()));
						}
					}
					break;

				case 4:// Total VD Time->VD Start to VD End
					HeatProcessEventDetails vdStart4 = currentHeatEvts.get(Constants.VD_START_TIME);
					HeatProcessEventDetails vdEnd4 = currentHeatEvts.get(Constants.VD_END_TIME);
					if (vdStart4 != null && vdEnd4 != null) {
						if (vdStart4.getEvent_date_time() != null && vdEnd4.getEvent_date_time() != null) {
							transDTO.setStart_time(vdStart4.getEvent_date_time());
							transDTO.setEnd_time(vdEnd4.getEvent_date_time());
							diff = vdEnd4.getEvent_date_time().getTime() - vdStart4.getEvent_date_time().getTime();
							diffMinutes = diff / (60 * 1000);
							transDTO.setDuration((long) diffMinutes);
							transDTO.setDelay(checkNegative(diffMinutes - activityDelayMasterModel.getStd_cycle_time()));
						}
					}
					break;
				}

			} else {
				if (transDelayHdr.getTotal_delay() != null && transDelayHdr.getTotal_delay() != ' ') {
					transDTO.setDelay((long) transDelayHdr.getTotal_delay());
				} else {
					transDTO.setDelay((long) 0);
				}
				transDTO.setStart_time(transDelayHdr.getActivity_start_time());
				transDTO.setEnd_time(transDelayHdr.getActivity_end_time());
				transDTO.setDuration((long) transDelayHdr.getActivity_duration());
				transDTO.setTransDelayEntryhdr(transDelayHdr);
				transDTO.setCorrective_action(transDelayHdr.getCorrective_action());
			}

			vdDelay.add(transDTO);

		}

		return vdDelay;
	}
	
	
	@Transactional
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id, String heat_id,
			Integer heat_counter, Integer sub_unit_id, String sample_no, Integer psn_id) {
		// TODO Auto-generated method stub

		List<HeatChemistryChildDetails> hcc = heatProceeEventDao.getChemDtlsByAnalysis(analysis_id, psn_id);

		LookupMasterModel lkp = lookupService.getLookUpRowById(analysis_id);
		String p_lookup_type = "CHEM_LEVEL", p_lookup_code = null;

		if (lkp != null) {

			if (lkp.getLookup_code().equalsIgnoreCase("HM_CHEMISTRY")) {
				p_lookup_code = "HM_CHEM";

			} else if (lkp.getLookup_code().equalsIgnoreCase("EAF_TAP_CHEM")) {
				p_lookup_code = "EAF_TAPPING";
			} else if (lkp.getLookup_code().equalsIgnoreCase("LRF_CHEM")) {
				p_lookup_code = "LRF_TAPPING";
			} else if (lkp.getLookup_code().equalsIgnoreCase("CAST_CHEM")) {
				p_lookup_code = "CAST_PRD";
			}
		}

		if (p_lookup_code != null) {
			Map<Integer, CommonPsnChemistry> psnchem = commonService.getPsnChemistryByTypeAndCode(p_lookup_type,
					p_lookup_code, psn_id);

			hcc.forEach((data) -> {
				if (psnchem.get(data.getElement()) != null) {
					data.setMin_value(psnchem.get(data.getElement()).getValue_min());
					data.setMax_value(psnchem.get(data.getElement()).getValue_max());
					data.setPsn_aim_value(psnchem.get(data.getElement()).getValue_aim());
				}
			});

		}

		return hcc;
	}
	

	@Override
	public VDHeatDetailsModel getVDHeatDetailsByHeatNo(String heatNo) {
		// TODO Auto-generated method stub
		return vdproductionDao.getVDHeatDetailsByHeatNo(heatNo);
	}
	public Long checkNegative(Long val) {
		return (val>0)? val:0;
	}
	
	@Transactional
	@Override
	public List<VDHeatConsumableModel> getVdAdditions(String lookup_code,Integer sub_unit_id) {
		// TODO Auto-generated method stub
		return vdproductionDao.getVdAdditions(lookup_code,sub_unit_id);
	}
	
	@Transactional
	@Override
	public VdAdditionsDetailsModel getAddDetailsBySlno(Integer arc_sl_no) {
		// TODO Auto-generated method stub
		return vdproductionDao.getAddDetailsBySlno(arc_sl_no);
	}
	
	@Transactional
	@Override
	public VDHeatConsumableModel getVDHeatConsumablesById(Integer cons_sl_no) {
		// TODO Auto-generated method stub
		return vdproductionDao.getVDHeatConsumablesById(cons_sl_no);
	}

	
	@Override
	@Transactional
	public String VDAdditionsSaveOrUpdate(VDRequestWrapper reqwrapper, String arc_start_date, String arc_end_date,
			int userId) {

		String result = "";
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		VdAdditionsDetailsModel arcDet = null;
		String arc_no = null;
		arcDet = new VdAdditionsDetailsModel();
		if (reqwrapper.getArcDetails().getArc_sl_no() < 1) {
			// insert operation
			arcDet.setArc_sl_no(null);
			arcDet.setSample_no(reqwrapper.getArcDetails().getSample_no());
			arcDet.setRecord_status(1);
			arcDet.setRecord_version(0);
			arcDet.setCreated_by(userId);
			arcDet.setCreated_date_time(new Date());
			arc_no = vdproductionDao.getNextAddNo(reqwrapper.getArcDetails().getHeat_id(),
					reqwrapper.getArcDetails().getHeat_counter());
			arcDet.setArc_no(Integer.parseInt(arc_no));
		} else {
			arcDet = getAddDetailsBySlno(reqwrapper.getArcDetails().getArc_sl_no());
			arcDet.setUpdated_by(userId);
			arcDet.setUpdated_date_time(new Date());
		}

		arcDet.setHeat_id(reqwrapper.getArcDetails().getHeat_id());
		arcDet.setHeat_counter(reqwrapper.getArcDetails().getHeat_counter());
		arcDet.setArc_start_date_time((GenericClass.getDateObject("dd/MM/yyyy HH:mm", arc_start_date)));
		arcDet.setArc_end_date_time((GenericClass.getDateObject("dd/MM/yyyy HH:mm", arc_end_date)));
		arcDet.setPower_consumption(reqwrapper.getArcDetails().getPower_consumption());
		arcDet.setBath_temp(reqwrapper.getArcDetails().getBath_temp());
		arcDet.setAddition_type(reqwrapper.getArcDetails().getAddition_type());

		mod_obj.put("LRF_ARC_ADD", arcDet);
		if (reqwrapper.getConDetails().getArc_grid_arry() != null
				&& reqwrapper.getConDetails().getArc_grid_arry().toString() != ""
				&& !(reqwrapper.getConDetails().getArc_grid_arry().isEmpty())) {

			String row[] = reqwrapper.getConDetails().getArc_grid_arry().split("SIDS");
			Integer cnt = 0;
			String key = "LRF_ARC_ADD_CONS";
			VDHeatConsumableModel conObj = null;
			for (int i = 0; i < row.length; i++) {

				String id[] = row[i].split("@");
				key = key + i;
				conObj = new VDHeatConsumableModel();
				if (id[5].equalsIgnoreCase("null")) {
					// insert operation
					conObj.setCons_sl_no(null);
					conObj.setHeat_id(reqwrapper.getConDetails().getHeat_id());
					conObj.setHeat_counter(reqwrapper.getConDetails().getHeat_counter());
					conObj.setArc_sl_no(null);
					conObj.setMaterial_id(Integer.parseInt(id[0]));
					conObj.setConsumption_qty(Double.parseDouble(id[3].toString()));
					conObj.setAddition_date_time(new Date());
					conObj.setMaterial_type(Integer.parseInt(id[6]));
					conObj.setCreated_by(userId);
					conObj.setCreated_date_time(new Date());
					conObj.setRecord_version(0);
					conObj.setRecord_status(1);
					conObj.setSap_matl_id(id[1]);
					conObj.setValuation_type(id[2]);

				} else {
					// Update operation
					conObj = getVDHeatConsumablesById(Integer.parseInt(id[5]));
					conObj.setMaterial_id(Integer.parseInt(id[0]));
					conObj.setConsumption_qty(Double.parseDouble(id[3].toString()));
					conObj.setSap_matl_id(id[1]);
					conObj.setValuation_type(id[2]);
					conObj.setAddition_date_time(new Date());
					conObj.setMaterial_type(Integer.parseInt(id[6]));
					conObj.setUpdated_by(userId);
					conObj.setUpdated_date_time(new Date());
				}
				mod_obj.put(key, conObj);

				cnt = cnt + 1;
				conObj = null;
			}
			mod_obj.put("LRF_ARC_ADD_CONS_CNT", cnt);
		} else {
			mod_obj.put("LRF_ARC_ADD_CONS_CNT", 0);
		}
		result = VDAdditionsSaveOrUpdate(mod_obj);
		return result;

	}
	
	public String VDAdditionsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return vdproductionDao.VDAddSaveOrUpdate(mod_obj);
	}
	
	@Override
	public VDHeatConsumableDisplay getVDAdditionsByHeat(String heat_id, Integer heat_cnt, Integer unit_id) {
		// TODO Auto-generated method stub
		VDHeatConsumableDisplay header = new VDHeatConsumableDisplay();
		try {
			header.setAr_si_no("ARC_NO");
			//header.setBath_sample_no("SAMPLE_NO");
			header.setBath_temp("BATH_TEMP");
			header.setArc_start_date_time("ARC_START_DT");
			header.setArc_end_date_time("ARC_END_DT");
			header.setPower_consumption("KWH");
			ArrayList<String> arc_additions = new ArrayList<String>();

			Integer mat_type_id=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='VD_ADDITIONS' and lookup_status=1");
			List<MtrlProcessConsumableMstrModel> hlist = commonService.getProcessConsumablesByTypeId(mat_type_id, unit_id);

			hlist.forEach((data) -> {
				arc_additions.add(data.getMaterial_desc() + "(" + data.getUomLkpModel().getLookup_value() + ")");
			});

			header.setHeaderdis(arc_additions);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return header;
	}
	
	@Transactional
	@Override
	public List<Map<String, Object>> getVDAdditionsTemp(Integer unit_id, String heatId, Integer heatCnt) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		try {
			Integer mtrl_type = commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code = 'VD_ADDITIONS' and lookup_status=1");
			List<MtrlProcessConsumableMstrModel> hlist = commonService.getDistinctProcessConsumablesByTypeId(mtrl_type, unit_id);
			String qry1 = "", qry3 = "", firstsqlQuery = "", finalSqlQry = "", lastsqlQuery = "";
			firstsqlQuery = "SELECT (select sample_no from trns_heat_chemistry_hdr where sample_si_no = a.BATH_SAMPLE_NO) as sample_no, a.arcing_start_date_time as arc_start_dt, a.arcing_end_date_time as arc_end_dt,"
					+ " a.BATH_TEMPERATURE as bath_temp,a.POWER_CONSUMPTION as kwh,a.arc_si_no as arc_no ";
			lastsqlQuery = " FROM trns_vd_additions_dtls a WHERE a.heat_id = '" + heatId+"'";
					//+ "' AND a.heat_counter =" + heatCnt + ""; commented on 18/12/2018 suma

			for (MtrlProcessConsumableMstrModel obj : hlist) {

				qry1 = ",(SELECT b.consumption_qty FROM trns_vd_heat_cons_lines b"
						+ " WHERE b.arcing_si_no = a.arc_si_no  AND b.material_id = (SELECT material_id   FROM mstr_process_consumables"
						+ " WHERE material_id =" + obj.getMaterial_id() + ")) AS \""+obj.getMaterial_desc()+"\"";
				qry3 = qry3.concat(qry1);
			}

			firstsqlQuery = firstsqlQuery.concat(qry3);
			finalSqlQry = firstsqlQuery.concat(lastsqlQuery);
			ls = vdproductionDao.getVDAdditionsTemp(finalSqlQry);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}



}
