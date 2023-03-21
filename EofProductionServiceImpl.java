package com.smes.trans.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.model.SeqIdDetails;
import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.ActivityDelayMasterModel;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.service.impl.ActivityDelayMasterService;
import com.smes.masters.service.impl.LookupMasterService;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.reports.model.GraphReportModel;
import com.smes.trans.dao.impl.EofProductionDao;
import com.smes.trans.model.EOFCrewDetails;
import com.smes.trans.model.EofElectrodeUsageModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.util.CommonCombo;
import com.smes.util.CommonProcessEvent;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.GenericClass;
import com.smes.util.ValidationResultsModel;
import com.smes.reports.model.EOFHeatLogRpt;

@Service("eofProductionService")
public class EofProductionServiceImpl implements EofProductionService {

	@Autowired
	private EofProductionDao eofProductionDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private HotMetalReceiveService hotMetalReceiveService;

	@Autowired
	private HeatPlanDetailsService heatPlanDetailsService;

	@Autowired
	private LookupMasterService lookupService;

	@Autowired
	private ActivityDelayMasterService activityDlyMstrServie;

	@Autowired
	private HeatProceeEventService heatProcessEventService;

	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;
	
	@Autowired
	private SteelLadleStatusMasterService steelLadleService;
	
	@Transactional
	@Override
	public String eofHeatProductionSave(EofHeatDetails eofHeatDetails) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofHeatProductionSave(eofHeatDetails);
	}

	@Transactional
	@Override
	public String eofHeatProductionUpdate(EofHeatDetails eofHeatDetails) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofHeatProductionUpdate(eofHeatDetails);
	}
	
	public String eofreceivehmStatusUpdate(Long hmRecvID) {
		// TODO Auto-generated method stub
		return  eofProductionDao.eofreceivehmStatusUpdate(hmRecvID);
	}

	public String eofCrewDetailsSave(List<EOFCrewDetails> eofCrewDetails) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofCrewDetailsSave(eofCrewDetails);
	}

	@Transactional
	@Override
	public String eofCrewDetailsUpdate(EOFCrewDetails eofCrewDetails) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofCrewDetailsUpdate(eofCrewDetails);
	}

	@Transactional
	@Override
	public List<EOFCrewDetails> getCrewDetailsByHeatNo(String heat_no, Integer unit_id, Integer heat_counter) {
		// TODO Auto-generated method stub
		return eofProductionDao.getCrewDetailsByHeatNo(heat_no, unit_id, heat_counter);
	}

	public String eofHeatProductionSaveAll(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofHeatProductionSaveAll(mod_obj);
	}

	public String eofCrewDetailsSave(EOFCrewDetails eofCrewDetails, String user_role_id, Integer uid) {
		String result = "";
		try {

			List<EOFCrewDetails> eofCrewList = new ArrayList<EOFCrewDetails>();
			List<EOFCrewDetails> eofCrewdetails = null;

			eofCrewList = getCrewDetailsByHeatNo(eofCrewDetails.getHeat_id(), eofCrewDetails.getSub_unit_id(),
					eofCrewDetails.getHeat_counter());
			String[] id = user_role_id.split("@");

			if (eofCrewList.size() > 0) {

			} else {
				eofCrewdetails = new ArrayList<EOFCrewDetails>();
				EOFCrewDetails eofCrewDetails1 = null;
				for (int i = 0; i < id.length; i++) {
					if (id[i] != null && id[i] != "") {
						eofCrewDetails1 = new EOFCrewDetails();
						eofCrewDetails1.setHeat_counter(eofCrewDetails.getHeat_counter());
						eofCrewDetails1.setHeat_id(eofCrewDetails.getHeat_id());
						eofCrewDetails1.setSub_unit_id(eofCrewDetails.getSub_unit_id());
						eofCrewDetails1.setCrew_si_no(null);
						eofCrewDetails1.setUser_role_id(Integer.parseInt(id[i]));
						eofCrewDetails1.setCreated_by(uid);
						eofCrewDetails1.setCreated_date_time(new Date());

						eofCrewdetails.add(eofCrewDetails1);

						eofCrewDetails1 = null;
					}
				}

				result = eofCrewDetailsSave(eofCrewdetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public String eofHeatProductionSaveAll(EofHeatDetails eofHeatDetails, String production_date, String hm_charge_at,
			String scrap_charge_at, String tap_start_at, String tap_close_at, Integer uid, Integer hm_ldl_version,
			Integer plan_line_version) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			// logger.info(EOFProductionController.class);
			Hashtable<String, Object> mod_obj = null;

			if (eofHeatDetails.getTrns_si_no() > 0) {

				mod_obj = new Hashtable<String, Object>();

				eofHeatDetails.setProduction_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", production_date));
				eofHeatDetails.setUpdatedBy(uid);
				eofHeatDetails.setUpdatedDateTime(new Date());
				eofHeatDetails.setRecord_status(1);

				// getting exiting events againest heat and counter
				List<CommonProcessEvent> cpe_result = commonService.getEventDetails(eofHeatDetails.getHeat_id(),
						eofHeatDetails.getSub_unit_id(), eofHeatDetails.getHeat_counter());

				if (cpe_result.size() > 0) {
					// adding 4 events into new list from avilable with existing saved data...if
					// events not avilable then manually contruct 4 events.
					List<CommonProcessEvent> cpe_result_new = new ArrayList<CommonProcessEvent>();

					String Keysval = "EVENTS_UPDATE";

					String[] evnts1 = { Constants.EAF_HM_CHRG_AT, Constants.EAF_SCRP_CHRG_AT, Constants.EAF_TAP_START, Constants.EAF_TAP_END };
					String[] evnts_val = { hm_charge_at, scrap_charge_at, tap_start_at, tap_close_at };

					for (int v = 0; v < evnts1.length; v++) {
						boolean res = false;
						mid: for (CommonProcessEvent cp1 : cpe_result) {
							if (evnts1[v].equalsIgnoreCase(cp1.getEvent_desc())) {

								cpe_result_new.add(cp1);
								res = true;
								break mid;
							}
						}

						if (!res) {
							CommonProcessEvent n = new CommonProcessEvent();
							n.setEvent_desc(evnts1[v]);
							n.setHeat_proc_event_id(0);
							cpe_result_new.add(n);
						}

					}

					int i = 0;

					for (CommonProcessEvent cp : cpe_result_new) {
						if ((cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_HM_CHRG_AT))
								|| (cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_SCRP_CHRG_AT))
								|| (cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_TAP_START))
								|| (cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_TAP_END))) {
							Keysval = Keysval + i;
							boolean res = false;
							// process event update..

							for (int v = 0; v < evnts1.length; v++) {

								if (cp.getEvent_desc().equalsIgnoreCase(evnts1[v]) && cp.getHeat_proc_event_id() > 0) {
									res = true;
									HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
									processEvnt.setHeat_proc_event_id(cp.getHeat_proc_event_id());
									processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
									processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
									
									processEvnt.setUpdatedBy(uid);
									processEvnt.setUpdatedDateTime(new Date());
									processEvnt.setEvent_date_time(
											GenericClass.getDateObject("dd/MM/yyyy HH:mm", evnts_val[v]));
									processEvnt.setVersion(cp.getRecord_version());
									mod_obj.put(Keysval, processEvnt);
								}

							}
							if ((!res)) {
								Integer eventId = commonService.getLookupIdByQuery(
										"select event_si_no from EventMasterModel where sub_unit_id = "
												+ eofHeatDetails.getSub_unit_id() + " and event_desc = '"
												+ cp.getEvent_desc() + "' and recordStatus=1");
								HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
								processEvnt.setHeat_proc_event_id(null);
								processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
								processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
								processEvnt.setCreatedBy(uid);
								processEvnt.setCreatedDateTime(new Date());
								processEvnt.setVersion(0);
								if ((cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_HM_CHRG_AT))
										&& (!hm_charge_at.equalsIgnoreCase(""))) {
									processEvnt.setEvent_date_time(
											GenericClass.getDateObject("dd/MM/yyyy HH:mm", hm_charge_at));
									processEvnt.setEvent_id(eventId);
									mod_obj.put(Keysval, processEvnt);
								} else if ((cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_SCRP_CHRG_AT))
										&& (!scrap_charge_at.equalsIgnoreCase(""))) {
									processEvnt.setEvent_date_time(
											GenericClass.getDateObject("dd/MM/yyyy HH:mm", scrap_charge_at));
									processEvnt.setEvent_id(eventId);
									mod_obj.put(Keysval, processEvnt);
								} else if ((cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_TAP_START))
										&& (!tap_start_at.equalsIgnoreCase(""))) {
									processEvnt.setEvent_date_time(
											GenericClass.getDateObject("dd/MM/yyyy HH:mm", tap_start_at));
									processEvnt.setEvent_id(eventId);
									mod_obj.put(Keysval, processEvnt);
								} else if ((cp.getEvent_desc().equalsIgnoreCase(Constants.EAF_TAP_END))
										&& (!tap_close_at.equalsIgnoreCase(""))) {
									processEvnt.setEvent_date_time(
											GenericClass.getDateObject("dd/MM/yyyy HH:mm", tap_close_at));
									processEvnt.setEvent_id(eventId);
									mod_obj.put(Keysval, processEvnt);
								}

							}

							i++;

						}
						mod_obj.put("EVENTS_CNT", i);

					}
				} else {

					/* insert new records for those events... */

					// process event register..

					if (hm_charge_at != null && hm_charge_at.trim().length() > 0) {
						Integer eventId = commonService
								.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
										+ eofHeatDetails.getSub_unit_id()
										+ " and event_desc = '"+Constants.EAF_HM_CHRG_AT+"' and recordStatus=1");
						HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
						processEvnt.setHeat_proc_event_id(null);
						processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
						processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
						processEvnt.setCreatedBy(uid);
						processEvnt.setCreatedDateTime(new Date());
						processEvnt.setEvent_id(eventId);
						processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hm_charge_at));
						mod_obj.put("EOF_HM_CHRG", processEvnt);
					}

					if (scrap_charge_at != null && scrap_charge_at.trim().length() > 0) {
						HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
						processEvnt.setHeat_proc_event_id(null);
						processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
						processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
						processEvnt.setCreatedBy(uid);
						processEvnt.setCreatedDateTime(new Date());
						Integer eventId = commonService
								.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
										+ eofHeatDetails.getSub_unit_id()
										+ " and event_desc = '"+Constants.EAF_SCRP_CHRG_AT+"' and recordStatus=1");
						processEvnt.setEvent_id(eventId);
						processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", scrap_charge_at));

						mod_obj.put("EOF_SCRAP_CHRG", processEvnt);
					}

					if (tap_start_at != null && tap_start_at.trim().length() > 0) {
						HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
						processEvnt.setHeat_proc_event_id(null);
						processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
						processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
						processEvnt.setCreatedBy(uid);
						processEvnt.setCreatedDateTime(new Date());
						Integer eventId = commonService
								.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
										+ eofHeatDetails.getSub_unit_id()
										+ " and event_desc = '"+Constants.EAF_TAP_START+"' and recordStatus=1");

						processEvnt.setEvent_id(eventId);
						processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", tap_start_at));

						mod_obj.put("EOF_TAP_START", processEvnt);
					}

					if (tap_close_at != null && tap_close_at.trim().length() > 0) {
						HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
						processEvnt.setHeat_proc_event_id(null);
						processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
						processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
						processEvnt.setCreatedBy(uid);
						processEvnt.setCreatedDateTime(new Date());
						Integer eventId = commonService
								.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
										+ eofHeatDetails.getSub_unit_id()
										+ " and event_desc = '"+Constants.EAF_TAP_END+"' and recordStatus=1");

						processEvnt.setEvent_id(eventId);
						processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", tap_close_at));

						mod_obj.put("EOF_TAP_END", processEvnt);
					}
					mod_obj.put("EVENTS_CNT", 0);
				}
			} else {

				mod_obj = new Hashtable<String, Object>();
				mod_obj.put("EVENTS_CNT", 0);
				eofHeatDetails.setTrns_si_no(null);

				eofHeatDetails.setProduction_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", production_date));
				eofHeatDetails.setRecord_status(1);
				eofHeatDetails.setAim_psn(eofHeatDetails.getAim_psn());
				eofHeatDetails.setCreatedBy(uid);
				eofHeatDetails.setCreatedDateTime(new Date());

				mod_obj.put("EOF", eofHeatDetails);

				// process event register..

				if (hm_charge_at != null && hm_charge_at.trim().length() > 0) {
					Integer eventId = commonService
							.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
									+ eofHeatDetails.getSub_unit_id()
									+ " and event_desc = '"+Constants.EAF_HM_CHRG_AT+"' and recordStatus=1");
					HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
					processEvnt.setHeat_proc_event_id(null);
					processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
					processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
					processEvnt.setCreatedBy(uid);
					processEvnt.setCreatedDateTime(new Date());
					processEvnt.setEvent_id(eventId);
					processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", hm_charge_at));
					mod_obj.put("EOF_HM_CHRG", processEvnt);
				}

				if (scrap_charge_at != null && scrap_charge_at.trim().length() > 0) {
					HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
					processEvnt.setHeat_proc_event_id(null);
					processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
					processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
					processEvnt.setCreatedBy(uid);
					processEvnt.setCreatedDateTime(new Date());
					Integer eventId = commonService
							.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
									+ eofHeatDetails.getSub_unit_id()
									+ " and event_desc = '"+Constants.EAF_SCRP_CHRG_AT+"' and recordStatus=1");
					processEvnt.setEvent_id(eventId);
					processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", scrap_charge_at));

					mod_obj.put("EOF_SCRAP_CHRG", processEvnt);
				}

				if (tap_start_at != null && tap_start_at.trim().length() > 0) {
					HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
					processEvnt.setHeat_proc_event_id(null);
					processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
					processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
					processEvnt.setCreatedBy(uid);
					processEvnt.setCreatedDateTime(new Date());
					Integer eventId = commonService
							.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
									+ eofHeatDetails.getSub_unit_id()
									+ " and event_desc = '"+Constants.EAF_TAP_START+"' and recordStatus=1");

					processEvnt.setEvent_id(eventId);
					processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", tap_start_at));

					mod_obj.put("EOF_TAP_START", processEvnt);
				}

				if (tap_close_at != null && tap_close_at.trim().length() > 0) {
					HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
					processEvnt.setHeat_proc_event_id(null);
					processEvnt.setHeat_id(eofHeatDetails.getHeat_id());
					processEvnt.setHeat_counter(eofHeatDetails.getHeat_counter());
					processEvnt.setCreatedBy(uid);
					processEvnt.setCreatedDateTime(new Date());
					Integer eventId = commonService
							.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "
									+ eofHeatDetails.getSub_unit_id()
									+ " and event_desc = '"+Constants.EAF_TAP_END+"' and recordStatus=1");

					processEvnt.setEvent_id(eventId);
					processEvnt.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", tap_close_at));

					mod_obj.put("EOF_TAP_END", processEvnt);
				}

				Double availableHm=eofHeatDetails.getAvailable_hm();
				Double hmWeight=eofHeatDetails.getHm_wt();
				if(availableHm==null) {
					availableHm=0.0;
				}
				if(hmWeight==null) {
					hmWeight=0.0;	
				}
				
				Double avl_wt =availableHm-hmWeight; //eofHeatDetails.getAvailable_hm() - eofHeatDetails.getHm_wt();

				String qry = "select a from HmReceiveBaseDetails a where a.hmRecvId=" + eofHeatDetails.getHmRecvId()
						+ "";

				List<HmReceiveBaseDetails> hmDetails = hotMetalReceiveService.getHMDetailsByQuery(qry);

				if (avl_wt > 0) {
					Integer ladlestatusid = commonService.getLookupIdByQuery(
							"select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='PARTC' and lookup_status=1");

					if (hmDetails.size() > 0) {
						HmReceiveBaseDetails hmobj = hmDetails.get(0);
						hmobj.setHmLadleStatus(ladlestatusid);
						hmobj.setHmAvlblWt(avl_wt);
						
						hmobj.setUpdatedBy(uid);
						hmobj.setUpdatedDateTime(new Date());
						hmobj.setVersion(hm_ldl_version);
						mod_obj.put("HM_UPDATE", hmobj);

					}

				} else {

					Integer ladlestatusid = commonService.getLookupIdByQuery(
							"select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STATUS' and lookup_code='CHRG' and lookup_status=1");
					Integer stageid = commonService.getLookupIdByQuery(
							"select lookup_id from LookupMasterModel where lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and lookup_code='CONSUMED' and lookup_status=1");

					if (hmDetails.size() > 0) {
						HmReceiveBaseDetails hmobj = hmDetails.get(0);
						hmobj.setHmLadleStatus(ladlestatusid);
						hmobj.setHmAvlblWt(0.0);
						hmobj.setHmLadleStageStatus(stageid);
						hmobj.setUpdatedBy(uid);
						hmobj.setUpdatedDateTime(new Date());
						hmobj.setVersion(hm_ldl_version);
						mod_obj.put("HM_UPDATE", hmobj);
					}
				}

				// HM Consumption entry

				HeatConsScrapMtrlDetails heatConsScrapMtrls = new HeatConsScrapMtrlDetails();
				heatConsScrapMtrls.setCons_si_no(null);
				heatConsScrapMtrls.setHm_seq_no(eofHeatDetails.getHmRecvId());
				heatConsScrapMtrls.setTrns_eof_si_no(null);
				heatConsScrapMtrls.setQty(hmWeight);//change done
				heatConsScrapMtrls.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", production_date));
				heatConsScrapMtrls.setRecord_status(1);
				heatConsScrapMtrls.setCreatedBy(uid);
				heatConsScrapMtrls.setCreatedDateTime(new Date());

				mod_obj.put("HM_CONS", heatConsScrapMtrls);

				// updating main heat status

				Integer mainstatusid = commonService.getLookupIdByQuery(
						"select main_status_id from MainHeatStatusMasterModel where main_status_desc='WIP' and status_type='PLAN_HEADER'");
				HeatPlanHdrDetails heatplanhdr = heatPlanDetailsService
						.getHeatPlanHeaderDetailsById(eofHeatDetails.getHeat_plan_id());

				if (heatplanhdr != null) {
					HeatPlanHdrDetails heatplanobj = heatplanhdr;
					heatplanobj.setMain_status_id(mainstatusid);
					heatplanobj.setUpdatedBy(uid);
					heatplanobj.setUpdatedDateTime(new Date());

					mod_obj.put("HEAT_HDR_UPDATE", heatplanobj);

				}
				//HeatPlanLinesDetails heatplanline = heatPlanDetailsService.getHeatPlanLineDetailsById(eofHeatDetails.getHeat_plan_line_id());
				HeatPlanDetails plandtls = heatPlanDetailsService.getHeatPlanDetailsById(eofHeatDetails.getHeat_plan_line_id());
				Integer linestatusid = null;

				linestatusid = commonService.getLookupIdByQuery(
						"select main_status_id from MainHeatStatusMasterModel where main_status_desc='WIP' and status_type='PLAN_LINES'");
				
				if (plandtls != null) {
					HeatPlanDetails heatplandtlobj = plandtls;
					heatplandtlobj.setStatus(linestatusid);
					heatplandtlobj.setUpdatedBy(uid);
					heatplandtlobj.setUpdatedDateTime(new Date());
					heatplandtlobj.setVersion(plan_line_version);
					heatplandtlobj.setAct_heat_id(eofHeatDetails.getHeat_id());

					mod_obj.put("HEAT_LINE_UPDATE", heatplandtlobj);
				}
				
				
				/* Heat Tracking status insert... */

				HeatStatusTrackingModel hstm = new HeatStatusTrackingModel();

				hstm.setHeat_track_id(null);

				hstm.setHeat_id(eofHeatDetails.getHeat_id());

				hstm.setHeat_counter(eofHeatDetails.getHeat_counter());

				hstm.setMain_status("WIP");

				hstm.setAct_proc_path(eofHeatDetails.getSub_unit_name());

				hstm.setCurrent_unit("EAF");

				hstm.setUnit_process_status("PROCESSING");

				hstm.setEof_status("PROCESSING");

				hstm.setRecord_status(1);

				hstm.setVersion(0);

				hstm.setCreatedBy(uid);

				hstm.setCreatedDateTime(new Date());

				hstm.setHeat_plan_id(eofHeatDetails.getHeat_plan_id());
				
				mod_obj.put("HEAT_TRACK_STATUS_INSERT", hstm);
				
				//IFACE_SMS_LP_DTL data insert
				IfacesmsLpDetailsModel Ifobj = new IfacesmsLpDetailsModel();
				
			    Ifobj.setMsg_id(null);
				
				Ifobj.setSch_id(plandtls.getLp_schd_id());
				
				Ifobj.setPlanned_heat_id(plandtls.getLp_plan_heat_id());
				
				Ifobj.setActual_heat_id(eofHeatDetails.getHeat_id());
				
				Ifobj.setPrev_sch_id(null);
				
				Ifobj.setPrev_planned_heat_id(null);
				
				Ifobj.setGrade(null);
				
				Ifobj.setEvent_code("HEAF");
				
				Ifobj.setInterface_status(0);
				
				Ifobj.setError_msg(null);
				
				Ifobj.setCreatedBy(uid);
				
			    Ifobj.setCreated_Date(new Date());			
			    
			    Ifobj.setModified_by(null);
				
				Ifobj.setModified_date(null);
				
	            mod_obj.put("IFACE_SMS_LP_TB_INSERT", Ifobj);
				
				SeqIdDetails seqObj = new SeqIdDetails();
				Integer seqId;String seqStr;
				String sub_unit_name;
				
				//HeatNo Generation Sequence Increment - Start
				if(eofHeatDetails.getSub_unit_name() != null)
					sub_unit_name = eofHeatDetails.getSub_unit_name();
				else
					sub_unit_name = eofHeatDetails.getSubUnitMstrMdl().getSub_unit_name();
			
				List<SeqIdDetails> seqLi = eofProductionDao.getSeqIdByQuery(sub_unit_name);
				seqId = seqLi.get(0).getNext_val();
				seqStr = seqLi.get(0).getTable_name();
				
				//seqObj.setCol_key(Constants.HEAT_NO_GEN_SEQ);
				seqObj.setCol_key(seqLi.get(0).getCol_key());
				seqObj.setNext_val(seqId+1);
							
				if(seqId >= Constants.HEAT_NO_GEN_SEQ_MAX || seqId.equals(Constants.HEAT_NO_GEN_SEQ_MAX)) {
					seqStr = GenericClass.getNextSeqChar(seqStr);
					seqObj.setNext_val(1);
				}
				seqObj.setTable_name(seqStr);
				mod_obj.put("UPD_HEAT_NO_GEN_SEQ", seqObj);
				
				//HeatNo Generation Sequence Increment - End
			}
			
			if (mod_obj != null) {
				result = eofHeatProductionSaveAll(mod_obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	@Transactional
	@Override
	public HashSet<EofHeatDetails> getLRFHeatDetailsByStatus(String cunit, String pstatus) {
		// TODO Auto-generated method stub
		HashSet<EofHeatDetails> lrfHeatDetails = new HashSet<EofHeatDetails>();
		List<EofHeatDetails> list = eofProductionDao.getLRFHeatDetailsByStatus(cunit, pstatus);
		List<LookupMasterModel> lookupList = lookupService.getLookupDtlsByLkpTypeAndStatus("ELEMENT", 1);

		// for (EofHeatDetails eof : list) {
		try {
			list.forEach((data) -> {
				List<EofHeatDetails> eofChemDet = eofProductionDao.getEofChemDetails(data.getHeat_id(),
						data.getHeat_counter());
				eofChemDet.forEach((chemVal) -> {
					lookupList.forEach((lkpMdl) -> {
						if (chemVal.getElement_id().equals(lkpMdl.getLookup_id())
								&& lkpMdl.getLookup_code().equalsIgnoreCase("C")) {
							data.setEof_C(chemVal.getElement_aim_value());
						}
						if (chemVal.getElement_id().equals(lkpMdl.getLookup_id())
								&& lkpMdl.getLookup_code().trim().equalsIgnoreCase("Ti")) {
							data.setEof_Ti(chemVal.getElement_aim_value());
						}
						if (chemVal.getElement_id().equals(lkpMdl.getLookup_id())
								&& lkpMdl.getLookup_code().equalsIgnoreCase("Mn")) {
							data.setEof_MN(chemVal.getElement_aim_value());
						}

						if (chemVal.getElement_id().equals(lkpMdl.getLookup_id())
								&& lkpMdl.getLookup_code().equalsIgnoreCase("S")) {
							data.setEof_S(chemVal.getElement_aim_value());
						}
						if (chemVal.getElement_id().equals(lkpMdl.getLookup_id())
								&& lkpMdl.getLookup_code().equalsIgnoreCase("P")) {
							data.setEof_P(chemVal.getElement_aim_value());
						}

						if (chemVal.getElement_id().equals(lkpMdl.getLookup_id())
								&& lkpMdl.getLookup_code().equalsIgnoreCase("Si")) {
							data.setEof_Si(chemVal.getElement_aim_value());
						}
					});

				});

				lrfHeatDetails.add(data);

			});

			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

		return lrfHeatDetails;

		/*
		 * for (int i = 0; i < list.size(); i++) { if(list.get(i).getHeat_id()!=null &&
		 * list.get(i).getHeat_counter()!=null){ List<EofHeatDetails>
		 * eofChemDet=eofProductionDao.getEofChemDetails(list.get(i).getHeat_id(),list.
		 * get(i).getHeat_counter()); eofChemDet.forEach((data)->{
		 * 
		 * for (LookupMasterModel lkpMdl : lookupList) { if(data.getElement_id()==
		 * lkpMdl.getLookup_id() && lkpMdl.getLookup_code().equalsIgnoreCase("C")){
		 * .setElement_aim_value(0.0); }
		 * 
		 * } }); } } return list;
		 */

	}

	// return lrfHeatDetails;

	/*
	 * for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	 * EofHeatDetails eofHeatDetails = (EofHeatDetails) iterator.next();
	 * if(eofHeatDetails.getHeat_id()!=null &&
	 * eofHeatDetails.getHeat_counter()!=null){ List<EofHeatDetails>
	 * eofChemDet=eofProductionDao.getEofChemDetails(eofHeatDetails.getHeat_id(),
	 * eofHeatDetails.getHeat_counter());
	 * 
	 * //if(psnchem.get(data.getElement())!=null) //{
	 * //data.setMin_value(psnchem.get(data.getElement()).getValue_min());
	 * //data.setMax_value(psnchem.get(data.getElement()).getValue_max());
	 * //data.setPsn_aim_value(psnchem.get(data.getElement()).getValue_aim());
	 * 
	 * 
	 * //} }
	 */

	@Transactional
	@Override
	public List<CommonCombo> getConsumedBuckets(Integer heat_transId) {
		// TODO Auto-generated method stub
		return eofProductionDao.getConsumedBuckets(heat_transId);
	}

	@Transactional
	@Override
	public List<HeatConsScrapMtrlDetails> getConsumedBucketsDtls(Integer heat_transId) {
		// TODO Auto-generated method stub
		return eofProductionDao.getConsumedBucketsDtls(heat_transId);
	}

	@Override
	public boolean checkScrapEntryByTransId(Integer transId) {
		// TODO Auto-generated method stub
		Integer result;
		result = eofProductionDao.checkScrapEntryByTransId(transId);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ValidationResultsModel> checkEOFValidations(Integer eof_tranId) {
		// TODO Auto-generated method stub
		return eofProductionDao.checkEOFValidations(eof_tranId);
	}

	@Override
	public List<SteelLadleLifeModel> getLaddleLifeDet(Integer steelLadleNo) {
		// TODO Auto-generated method stub
		SteelLadleTrackingModel stTrackObj = steelLadleService.getStLadlleTrackObjById(steelLadleNo);
		return eofProductionDao.getLaddleLifeDet(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
	}

	@Override
	public String getSampleNoforHmChem(String heat_id) {
		// TODO Auto-generated method stub
		return eofProductionDao.getSampleNoforHmChem(heat_id);
	}

	@Override
	public List<EofHeatDetails> getEOFHeatDetailsByDate(String fdate, String tdate, Integer subunit) {
		// TODO Auto-generated method stub
		return eofProductionDao.getEOFHeatDetailsByDate(fdate, tdate, subunit);
	}

	@Override
	public Integer updatePartLifeBySiNo(Integer ladleLifeSiNo, Integer life_cntr, Integer uid) {
		// TODO Auto-generated method stub
		Integer res = commonService.updateRecordByQuery("update SteelLadleLifeModel set trns_life=" + life_cntr
				+ ",updated_date_time=sysdate,updated_by=" + uid + " where ladle_life_sl_no=" + ladleLifeSiNo + "");
		return res;
	}

	@Override
	public List<HeatConsScrapMtrlDetails> getHeatScrapConsumptionByEofId(Integer trns_si_no) {
		// TODO Auto-generated method stub
		return eofProductionDao.getHeatScrapConsumptionByEofId(trns_si_no);
	}

	@Override
	public List<GraphReportModel> getHeatsWithInDates(String fdate, String tdate, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		return eofProductionDao.getHeatsWithInDates(fdate, tdate, sub_unit_id);
	}

	@Override
	public List<SeqIdDetails> getSeqIdByQuery(String sub_unit) {
		// TODO Auto-generated method stub
		return eofProductionDao.getSeqIdByQuery(sub_unit);
	}

	@Override
	public EofHeatDetails getEOFHeatDetailsByHeatNo(String heatNo) {
		// TODO Auto-generated method stub
		return eofProductionDao.getEOFHeatDetailsByHeatNo(heatNo);
	}

	@Override
	public Integer getHMRecptIdByHeatNo(String heatNo) {
		// TODO Auto-generated method stub
		return eofProductionDao.getHMRecptIdByHeatNo(heatNo);
	}

	@Override
	public List<DelayEntryDTO> getEofDelayEntriesBySubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id) {
		// TODO Auto-generated method stub
		
		List<ActivityDelayMasterModel> delayEntries = activityDlyMstrServie
				.getAllActivityDetalMasterBySubUnit(sub_unit_id);
		
		List<DelayEntryDTO> eofDelay = new ArrayList<DelayEntryDTO>();
		EofHeatDetails prevHeatDetails = eofProductionDao.getEOFPreviousHeatDetailsByHeatNo(trans_heat_id);
		EofHeatDetails passedHeatinfo = eofProductionDao.getEOFHeatDetailsById(trans_heat_id);
		List<HeatProcessEventDetails> prevHeatevents = new ArrayList<HeatProcessEventDetails>();
		if(prevHeatDetails != null) {
			prevHeatevents = heatProcessEventService
				.getHeatProcessEventDtls(prevHeatDetails.getTrns_si_no(), ""); // previous
		}
		List<HeatProcessEventDetails> currentHeatevents = heatProcessEventService.getHeatProcessEventDtls(trans_heat_id,
				"");// current
		Map<String, HeatProcessEventDetails> preHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		Map<String, HeatProcessEventDetails> currentHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		for (HeatProcessEventDetails prevHeatevent : prevHeatevents) {
			preHeatEvts.put(prevHeatevent.getEventMstrMdl().getEvent_desc(), prevHeatevent);
		}
		for (HeatProcessEventDetails heatProcessEventDetail : currentHeatevents) {
			currentHeatEvts.put(heatProcessEventDetail.getEventMstrMdl().getEvent_desc(), heatProcessEventDetail);
		}

		for (ActivityDelayMasterModel activityDelayMasterModel : delayEntries) {
			TransDelayEntryHeader transDelayHdr = transDelayEntryHeaderService.getTransDelayHeaderByHeatAndActivity(
					activityDelayMasterModel.getActivity_delay_id(), passedHeatinfo.getTrns_si_no());
			DelayEntryDTO transDTO = new DelayEntryDTO();
			transDTO.setActivity_master(activityDelayMasterModel);
			long diff;
			long diffMinutes=0;
			if (transDelayHdr == null) {//
				switch (activityDelayMasterModel.getActivity_seq()) {
				case 1: // Prv heat tap end to current heat scrap charge start
					HeatProcessEventDetails prevHeat = preHeatEvts.get(Constants.EAF_TAP_END);
					HeatProcessEventDetails current = currentHeatEvts.get(Constants.EAF_SCRP_CHRG_AT);
					if(prevHeat!=null && current!=null) {
					Date scrapChargeTime = current.getEvent_date_time();
					Date prevHeatTapEnd = prevHeat.getEvent_date_time();
					transDTO.setStart_time(prevHeatTapEnd);
					transDTO.setEnd_time(scrapChargeTime);
					if (current.getEvent_date_time() != null && prevHeat.getEvent_date_time() != null) {
						diff = scrapChargeTime.getTime() - prevHeatTapEnd.getTime();
						diffMinutes = diff / (60 * 1000);
						//transDTO.setDuration((long) diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						
						
					}
					}
					break;
				case 2:// Blowing Start - HM Charge At
					HeatProcessEventDetails scrapchargeStart = currentHeatEvts.get(Constants.EAF_HM_CHRG_AT);
					HeatProcessEventDetails scrapchargeEnd = currentHeatEvts.get(Constants.EAF_02_BLOW_START);
					if(scrapchargeStart!=null && scrapchargeEnd!=null) {
					transDTO.setStart_time(scrapchargeStart.getEvent_date_time());
					transDTO.setEnd_time(scrapchargeEnd.getEvent_date_time());
					if (scrapchargeEnd.getEvent_date_time() != null && scrapchargeStart.getEvent_date_time() != null) {
						
						
						diff = scrapchargeEnd.getEvent_date_time().getTime()
								- scrapchargeStart.getEvent_date_time().getTime();
						
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
					
					}
					}
					break;
				case 3: // Blowing End - Blowing Start
					HeatProcessEventDetails scrapchargeEndH = currentHeatEvts.get(Constants.EAF_02_BLOW_START);
					HeatProcessEventDetails HMchargeStart = currentHeatEvts.get(Constants.EAF_02_BLOW_END);
					if(scrapchargeEndH!=null &&HMchargeStart!=null ) {
					transDTO.setStart_time(scrapchargeEndH.getEvent_date_time());
					transDTO.setEnd_time(HMchargeStart.getEvent_date_time());
					if (HMchargeStart.getEvent_date_time() != null && scrapchargeEndH.getEvent_date_time() != null) {
						
						diff = HMchargeStart.getEvent_date_time().getTime()
								- scrapchargeEndH.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						
					}
					}
					break;
				case 4:// Tapping End - Tapping Start
					HeatProcessEventDetails HMchargeDStart = currentHeatEvts.get(Constants.EAF_TAP_START);
					HeatProcessEventDetails HMchargeDend = currentHeatEvts.get(Constants.EAF_TAP_END);
					if(HMchargeDStart!=null &&HMchargeDend!=null ) {
					transDTO.setStart_time(HMchargeDStart.getEvent_date_time());
					transDTO.setEnd_time(HMchargeDend.getEvent_date_time());
					if (HMchargeDend.getEvent_date_time() != null && HMchargeDStart.getEvent_date_time() != null) {
						
						diff = HMchargeDend.getEvent_date_time().getTime()
								- HMchargeDStart.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						
					}
					}
					break;
				case 5:// HM charging end to O2 blow start
					HeatProcessEventDetails HMchargeO2end = currentHeatEvts.get(Constants.EAF_HM_CHRG_END);
					HeatProcessEventDetails O2BlowStart = currentHeatEvts.get(Constants.EAF_02_BLOW_START);
					if(HMchargeO2end!=null &&O2BlowStart!=null ) {
					transDTO.setStart_time(O2BlowStart.getEvent_date_time());
					transDTO.setEnd_time(HMchargeO2end.getEvent_date_time());
					if (O2BlowStart.getEvent_date_time() != null && HMchargeO2end.getEvent_date_time() != null) {
						
						diff =HMchargeO2end.getEvent_date_time().getTime() - O2BlowStart.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						
						
					}
					}
					break;
				case 6: // O2 blow duration
					HeatProcessEventDetails O2BlowDStart = currentHeatEvts.get(Constants.EAF_02_BLOW_START);
					HeatProcessEventDetails O2BlowDEnd = currentHeatEvts.get(Constants.EAF_02_BLOW_END);
					if(O2BlowDStart!=null && O2BlowDEnd!=null) {
					transDTO.setStart_time(O2BlowDStart.getEvent_date_time());
					transDTO.setEnd_time(O2BlowDEnd.getEvent_date_time());
					if (O2BlowDEnd.getEvent_date_time() != null && O2BlowDStart.getEvent_date_time() != null) {
						
						diff = O2BlowDEnd.getEvent_date_time().getTime() - O2BlowDStart.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						
						
					}
					}
					break;
				case 7:// O2 blow end to tapping start

					HeatProcessEventDetails O2BlowTEnd = currentHeatEvts.get(Constants.EAF_02_BLOW_END);
					HeatProcessEventDetails TapStart = currentHeatEvts.get(Constants.EAF_TAP_START);
					if(O2BlowTEnd!=null &&TapStart!=null  ) {
					transDTO.setStart_time(O2BlowTEnd.getEvent_date_time());
					transDTO.setEnd_time(TapStart.getEvent_date_time());
					if (TapStart.getEvent_date_time() != null && O2BlowTEnd.getEvent_date_time() != null) {
						
						diff = TapStart.getEvent_date_time().getTime() - O2BlowTEnd.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration((long) diffMinutes);
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						
						
						
					}
					}
					break;

				case 8:// Tapping duration
					HeatProcessEventDetails TapDStart = currentHeatEvts.get(Constants.EAF_TAP_START);
					HeatProcessEventDetails TapDEnd = currentHeatEvts.get(Constants.EAF_TAP_END);
					if (TapDEnd != null && TapDStart != null) {
						transDTO.setStart_time(TapDStart.getEvent_date_time());
						transDTO.setEnd_time(TapDEnd.getEvent_date_time());
						if (TapDEnd.getEvent_date_time() != null && TapDStart.getEvent_date_time() != null) {
							
							diff = TapDEnd.getEvent_date_time().getTime() - TapDStart.getEvent_date_time().getTime();
							diffMinutes = diff / (60 * 1000);
							transDTO.setDuration((long) diffMinutes);
							transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
							
							
							
						}
					}
					break;

				default:
					break;
				}
			} else {
				transDTO.setStart_time(transDelayHdr.getActivity_start_time());
				transDTO.setEnd_time(transDelayHdr.getActivity_end_time());
				if(transDelayHdr.getActivity_duration()!=null && transDelayHdr.getTotal_delay()!=null) {
				transDTO.setDuration((long) transDelayHdr.getActivity_duration());
				transDTO.setDelay((long) transDelayHdr.getTotal_delay());
				}
				
				transDTO.setTransDelayEntryhdr(transDelayHdr);
				transDTO.setCorrective_action(transDelayHdr.getCorrective_action());
			}
			eofDelay.add(transDTO);
		}
		return eofDelay; // eofProductionDao.getEofDelayEntriesBySubUnitAndHeat(sub_unit_id,trans_heat_id);
	}
	@Override
	public List<TransDelayEntryHeader> getDelayDetailsHdrWithSubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id){
		 List<ActivityDelayMasterModel> delayEntries = activityDlyMstrServie
					.getAllActivityDetalMasterBySubUnit(sub_unit_id);
		 EofHeatDetails passedHeatinfo = eofProductionDao.getEOFHeatDetailsById(trans_heat_id);
		 
		 List<TransDelayEntryHeader> delayHdrLst=new ArrayList<TransDelayEntryHeader>();
		 for (ActivityDelayMasterModel activityDelayMasterModel : delayEntries) {
			 try {
				TransDelayEntryHeader transDelayHdr = transDelayEntryHeaderService.getTransDelayHeaderByHeatAndActivity(
						activityDelayMasterModel.getActivity_delay_id(), passedHeatinfo.getTrns_si_no());
				if(transDelayHdr!=null) {
				delayHdrLst.add(transDelayHdr);
				}
			 }catch(Exception ex) {
				 
			 }
		 }
		 return delayHdrLst;
	}

	@Override
    public List<EOFHeatLogRpt> getEofHeatLogs(String heatId, String LogType) {
        // TODO Auto-generated method stub
		
        return eofProductionDao.getEofHeatLogs(heatId, LogType);		
	}
	
	
	
	@Override
	public String getCrewRoleUser(String heatId, String crewUserRole) {
		return eofProductionDao.getCrewRoleUser(heatId, crewUserRole);
	}

	@Override
	public EofHeatDetails getById(Integer trans_Si_No) {
		// TODO Auto-generated method stub
		return eofProductionDao.getEOFHeatDetailsById(trans_Si_No);
	}

	@Override
	public Map<String,Double> getLiquidSteelValue(Integer trans_Si_No) {
		List<HeatConsScrapMtrlDetails> scrap=eofProductionDao.getHeatScrapConsumptionByEofId(trans_Si_No);
		EofHeatDetails heatEntry=eofProductionDao.getEOFHeatDetailsById(trans_Si_No);
		Double scrapSum=0.0;
		Map<String,Double> json=new HashMap<>();
		List<LookupMasterModel> lookups=lookupService.getLookupDtlsByLkpTypeAndStatus("EAF_DEFAULTS", 1);
		for (HeatConsScrapMtrlDetails heatConsScrapMtrlDetails2 : scrap) {
			scrapSum=+heatConsScrapMtrlDetails2.getQty();
		}
		if(heatEntry.getHm_wt()!=null) {
		scrapSum=scrapSum+heatEntry.getHm_wt();
		}
		scrapSum=scrapSum*0.9;
		List<String> EOF_sludge= lookups.stream().filter(lookup->lookup.getLookup_code().equals("SLUDGE")).map(lookup->lookup.getLookup_value()).collect(Collectors.toList());;
		List<String> EOF_slag=lookups.stream().filter(lookup->lookup.getLookup_code().equals("SLAG")).map(lookup->lookup.getLookup_value()).collect(Collectors.toList());;
		
		if(!EOF_sludge.isEmpty() && !EOF_slag.isEmpty()) {
			double sludge=scrapSum*Double.parseDouble(EOF_sludge.get(0))/1000;
			double slag=scrapSum*Double.parseDouble(EOF_slag.get(0))/1000;
			try {
				DecimalFormat two = new DecimalFormat("0.000");
				json.put("SLAG",  Double.valueOf(two.format(slag)));
				json.put("SLUDGE", Double.valueOf(two.format(sludge)));
				json.put("LIGUID_STEEL", scrapSum);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}else {
			json.put("STATUS", 0.0);
		}
		return json;
	}

	@Transactional
	@Override
	public List<EOFHeatLogRpt> getEofHMScrapDayConsumption() {
		// TODO Auto-generated method stub
		Integer consPostingFlag = eofProductionDao.isMaterialConsumptionPosted();
		List<EOFHeatLogRpt> li = eofProductionDao.getEofHMScrapDayConsumption();
		Double yield;
		DecimalFormat df = new DecimalFormat("#.##");	
		for(EOFHeatLogRpt obj : li) {
			yield = (obj.getOp_wt()/(obj.getHm_wt()+obj.getTotal_scrap()))*100;
			obj.setYield(Double.parseDouble(df.format(yield)));
			obj.setIsConsPosted(consPostingFlag);
		}
		return li;
	}

	@Override
	public List<String> getEOFLRFMaterialConsumptionHdr(String sub_unit) {
		// TODO Auto-generated method stub
		List<String> hdrLi = new ArrayList<String>();
		
		hdrLi.add("Heat No");
		hdrLi.add("Unit");
		hdrLi.add("PSN");
		hdrLi.add("Grade");
		for(String m : eofProductionDao.getConsumedMaterialList(sub_unit)) {
			hdrLi.add(m);
		}
		
		return hdrLi;
	}

	@Override
	public List<Map<String, Object>> getEOFLRFMaterialConsumptionDtls(String sub_unit) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> outerLi = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> innerDetMap;
		
		for(EOFHeatLogRpt obj : eofProductionDao.getHeatPostedList(sub_unit)) {
			innerDetMap = new HashMap<String, Object>();
			innerDetMap.put("col0", obj.getHeat_no());
			innerDetMap.put("col1", obj.getUnit());
			innerDetMap.put("col2", obj.getAim_psn());
			innerDetMap.put("col3", obj.getGrade());
			innerDetMap.put("trns_si_no", obj.getTrns_si_no());
			
			int col_counter = 4;
			for(String q : eofProductionDao.getHeatwiseActualMaterialConsumed(obj.getTrns_si_no(), sub_unit, obj.getHeat_no())) {
				innerDetMap.put("col"+col_counter, q);
				col_counter = col_counter + 1;
			}
			outerLi.add(innerDetMap);
		}
		return outerLi;
	}

	@Override
	public List<HeatConsMaterialsDetailsLog> getMtrlConsLogByTrnsId(Integer trns_id) {
		// TODO Auto-generated method stub
		return eofProductionDao.getMtrlConsLogByTrnsId(trns_id);
	}

	@Override
	public String saveOrUpdateMatrlCons(List<HeatConsMaterialsDetails> updLi, List<HeatConsMaterialsDetailsLog> logLi) {
		// TODO Auto-generated method stub
		return eofProductionDao.saveOrUpdateMatrlCons(updLi, logLi);
	}

	@Transactional
	@Override
	public Integer isMaterialConsumptionPosted() {
		// TODO Auto-generated method stub
		return eofProductionDao.isMaterialConsumptionPosted();
	}

	@Override
	public String eofElectrodeUsageTrnsSaveOrUpdate(EofElectrodeUsageModel obj) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofElectrodeUsageTrnsSaveOrUpdate(obj) ;
	}

	@Override
	public EofElectrodeUsageModel eofElectrodeUsageTrnsById(Integer id) {
		// TODO Auto-generated method stub
		return eofProductionDao.eofElectrodeUsageTrnsById(id);
	}

	@Override
	public List<EofElectrodeUsageModel> getAllElectrodeUsageTrnsByUnit(Integer sub_unit_id, Integer trans_no) {
		// TODO Auto-generated method stub
		List<LookupMasterModel> types = lookupService.getLookupDtlsByLkpTypeAndStatus("LRF_ELECTRODE", 1);
		List<EofElectrodeUsageModel> li = new ArrayList<EofElectrodeUsageModel>();
		types.forEach((lkp) -> {
			EofElectrodeUsageModel electrodeObj = null;
			electrodeObj = eofProductionDao.getElectrodeStatusByUnitAndLkp(sub_unit_id, lkp.getLookup_id(), trans_no);
			if (electrodeObj == null) {
				electrodeObj = new EofElectrodeUsageModel();
				electrodeObj.setElectrodeTransId(0);
				electrodeObj.setSubUintId(sub_unit_id);
				electrodeObj.setElectrodeId(lkp.getLookup_id());
				electrodeObj.setElectrodeLkpMstrModel(lkp);
			}
			li.add(electrodeObj);
		});
		return li;
	}
	
	@Override
	public EofHeatDetails getEofTrnsNo(String heat_no) {
		EofHeatDetails trns_sl_no;
		trns_sl_no=eofProductionDao.getEofTrnsNo(heat_no);
		
		return trns_sl_no;
		
	}
}
