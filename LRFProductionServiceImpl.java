package com.smes.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.ActivityDelayMasterModel;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.MtrlProcessConsumableMstrModel;
import com.smes.masters.model.PSNProductMasterModel;
import com.smes.masters.service.impl.ActivityDelayMasterService;
import com.smes.masters.service.impl.LookupMasterService;
import com.smes.masters.service.impl.PSNProductMasterService;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.reports.model.LRFHeatLogRpt;
import com.smes.trans.dao.impl.EofProductionDao;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.LrfElectrodeTransactionsDao;
import com.smes.trans.dao.impl.SteelLadleMaintnDao;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.LRFHeatDetailsPsnBkpModel;
import com.smes.trans.model.LrfElectrodeTransactions;
import com.smes.trans.model.ReladleProcessDetailsModel;
import com.smes.trans.model.ReladleProcessHdrModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.TransDelayEntryHeader;
import com.smes.util.CommonCombo;
import com.smes.util.CommonPsnChemistry;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.GenericClass;
import com.smes.util.LRFHeatConsumableDisplay;
import com.smes.wrappers.LRFRequestWrapper;

@Service("lrfProductionService")
public class LRFProductionServiceImpl implements LRFProductionService {

	@Autowired
	private LRFProductionDao lrfProductionDao;
	
	@Autowired
	private EofProductionDao eofProductionDao;

	@Autowired
	private CommonService commonService;

	@Autowired
	private LookupMasterService lookupService;

	@Autowired
	private HeatProceeEventDao heatProceeEventDao;

	@Autowired
	private ActivityDelayMasterService activityDlyMstrServie;

	@Autowired
	private HeatProceeEventService heatProcessEventService;

	@Autowired
	private TransDelayEntryHeaderService transDelayEntryHeaderService;

	@Autowired
	private SteelLadleStatusMasterService steelLadleService;

	@Autowired
	LrfElectrodeTransactionsDao lrfElectrodeTransDao;
	
	@Autowired
	SteelLadleMaintnDao  steelLadleMaintnDao;

	@Autowired
	private PSNProductMasterService psnProdMstrService;
	
	@Transactional
	@Override
	public String saveAll(LRFRequestWrapper reqWrapper, Integer userId) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			SteelLadleTrackingModel stTrackObj = new SteelLadleTrackingModel();
			Hashtable<String, Object> mod_obj = null;
			mod_obj = new Hashtable<String, Object>();
			if (!reqWrapper.getLrfHeatDetails().equals(null)) {
				reqWrapper.getLrfHeatDetails().setCreatedBy(userId);
				reqWrapper.getLrfHeatDetails().setCreatedDateTime(new Date());
				reqWrapper.getLrfHeatDetails().setRecord_status(1);
				reqWrapper.getLrfHeatDetails().setProduction_date(new Date());
				//reqWrapper.setLrfHeatDetails(lrfProductionDao.getLRFHeatDetailsByHeatNo(reqWrapper.getLrfHeatDetails().getHeat_id()));
				
				if (reqWrapper.getLrfHeatDetails().getSteel_ladle_no() > 0) {
					stTrackObj = steelLadleService
							.getStLadlleTrackObjById(reqWrapper.getLrfHeatDetails().getSteel_ladle_no());
					reqWrapper.getLrfHeatDetails().setSteel_ladle_no(stTrackObj.getSt_ladle_si_no());

					Integer ladle_status_id;
					if (stTrackObj != null) {
						ladle_status_id = commonService.getLookupIdByQuery(
								"select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status_id="+Constants.LADLE_RCVD_IN_LRF+" and  recordStatus=1");
						stTrackObj.setLadle_status(ladle_status_id);
						stTrackObj.setUpdatedBy(userId);
						stTrackObj.setUpdatedDateTime(new Date());
					}

				}
				//Added on 10/11/2018 for Return Heat
				if(reqWrapper.getLrfHeatDetails().getReladling().equals("Y")){
					ReladleTrnsDetailsMdl reladleObj = new ReladleTrnsDetailsMdl();
					reladleObj.setHeat_id(reqWrapper.getLrfHeatDetails().getHeat_id());
					reladleObj.setHeat_counter(reqWrapper.getLrfHeatDetails().getHeat_counter());
					reladleObj.setSub_unit_id(reqWrapper.getLrfHeatDetails().getSub_unit_id());
					reladleObj.setAim_psn_no(reqWrapper.getLrfHeatDetails().getAim_psn());
					reladleObj.setAct_qty( (reqWrapper.getLrfHeatDetails().getSteel_wgt()).floatValue() );
					reladleObj.setSteelLadleNo(reqWrapper.getLrfHeatDetails().getSteel_ladle_no());
					reladleObj.setReturn_date(reqWrapper.getLrfHeatDetails().getProduction_date());
					reladleObj.setReturn_type(commonService.getLookupIdByQuery(
								"select lookup_id from LookupMasterModel where lookup_type='"+Constants.CAST_RETURN_TYPE+"' and lookup_code = '"+Constants.CAST_RETURN_TYPE_RELADLING+"' and  lookup_status=1"));
					reladleObj.setBalance_qty((reqWrapper.getLrfHeatDetails().getSteel_wgt()).floatValue());
					reladleObj.setRecord_status(reqWrapper.getLrfHeatDetails().getRecord_status());
					reladleObj.setCreated_by(reqWrapper.getLrfHeatDetails().getCreatedBy());
					reladleObj.setCreated_date_time(reqWrapper.getLrfHeatDetails().getCreatedDateTime());
					reladleObj.setHeatPlanId(reqWrapper.getLrfHeatDetails().getHeat_plan_id());
					reladleObj.setHeatPlanLineNo(reqWrapper.getLrfHeatDetails().getHeat_plan_line_no());
					reladleObj.setIs_processed("N");
					reladleObj.setDisp_temp(reqWrapper.getLrfHeatDetails().getTap_temp());
					
					reqWrapper.getLrfHeatDetails().setLrf_dispatch_date(new Date());
					
					mod_obj.put("LRF_RELADLE_DET", reladleObj);
				}
				mod_obj.put("LRF_HEAT_DET", reqWrapper.getLrfHeatDetails());
				mod_obj.put("LRF_ST_LALDE_STATUS_UPDATE", stTrackObj);
			}
			if (reqWrapper.getLrfCrewDetList().size() > 0) {
				reqWrapper.getLrfCrewDetList().stream().forEach(c -> c.setCreated_by(userId));
				reqWrapper.getLrfCrewDetList().stream().forEach(c -> c.setCreated_date_time(new Date()));
				mod_obj.put("LRF_CREW_DET", reqWrapper.getLrfCrewDetList());
			}
			if (!reqWrapper.getLrfHeatStatus().equals(null)) {
				HeatStatusTrackingModel hstm;
				if(reqWrapper.getLrfHeatStatus().getHeat_track_id() == null){
					hstm = new HeatStatusTrackingModel();
					hstm.setHeat_id(reqWrapper.getLrfHeatDetails().getHeat_id());
					hstm.setCreatedBy(userId);
					hstm.setCreatedDateTime(new Date());
					hstm.setHeat_plan_id(reqWrapper.getLrfHeatDetails().getHeat_plan_id());
					hstm.setLadle_id(reqWrapper.getLrfHeatDetails().getSteel_ladle_no());
					hstm.setRecord_status(1);
					
					HeatStatusTrackingModel prevHstm = lrfProductionDao
					.getHeatStatusObject(new Integer(reqWrapper.getLrfHeatStatus().getInActiveHeatTrackId()));
					prevHstm.setUpdatedBy(userId);
					prevHstm.setUpdatedDateTime(new Date());
					prevHstm.setRecord_status(0);
					
					mod_obj.put("LRF_INACTIVE_HEAT_STATUS", prevHstm);
				}else{
					hstm = lrfProductionDao
						.getHeatStatusObject(new Integer(reqWrapper.getLrfHeatStatus().getHeat_track_id()));
					hstm.setUpdatedBy(userId);
					hstm.setUpdatedDateTime(new Date());
				}
				hstm.setHeat_counter(reqWrapper.getLrfHeatStatus().getHeat_counter());
				hstm.setMain_status(reqWrapper.getLrfHeatStatus().getMain_status());
				hstm.setAct_proc_path(reqWrapper.getLrfHeatStatus().getAct_proc_path());
				hstm.setCurrent_unit(reqWrapper.getLrfHeatStatus().getCurrent_unit());
				hstm.setUnit_process_status(reqWrapper.getLrfHeatStatus().getUnit_process_status());
				hstm.setLrf_status(reqWrapper.getLrfHeatStatus().getLrf_status());
				hstm.setLrf_status_af_vd(reqWrapper.getLrfHeatStatus().getLrf_status_af_vd());
				
				mod_obj.put("LRF_HEAT_STATUS", hstm);
			}

			if (mod_obj != null) {
				result = LRFHeatProductionSaveAll(mod_obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	
	@Override
	public List<CommonCombo> getAllAvbleLadle() {
		List<SteelLadleTrackingModel> lst=steelLadleMaintnDao.getAvailableLadleStatus();
		List<CommonCombo> comboList=new ArrayList<>();
		lst.forEach(track->{
			CommonCombo combo=new CommonCombo();
			combo.setKeyval(track.getSteelLadleObj().getSteel_ladle_si_no()+"");
			combo.setTxtvalue(track.getSteelLadleObj().getSteel_ladle_no());
			comboList.add(combo);
		});
		return comboList;
	}
	
	
	private String LRFHeatProductionSaveAll(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return lrfProductionDao.saveAll(mod_obj);
	}

	@Transactional
	@Override
	public LRFHeatDetailsModel getLRFHeatDtlsFormByID(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getLRFHeatDtlsFormByID(trns_sl_no);
	}

	@Transactional
	@Override
	public List<LRFHeatConsumableModel> getArcAdditions(String lookup_code,Integer sub_unit_id) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getArcAdditions(lookup_code,sub_unit_id);
	}

	@Transactional
	@Override
	public List<LRFHeatConsumableModel> getArcAdditionsBySampleNo(Integer arc_sl_no, String heat_id, Integer heat_cnt) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getArcAdditionsBySampleNo(arc_sl_no, heat_id, heat_cnt);
	}

	@Transactional
	@Override
	public String lrfArcAdditionsSaveOrUpdate(LRFRequestWrapper reqwrapper, String arc_start_date, String arc_end_date,
			int userId) {

		String result = "";
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		LRFHeatArcingDetailsModel arcDet = null;
		String arc_no = null;
		arcDet = new LRFHeatArcingDetailsModel();
		if (reqwrapper.getArcDetails().getArc_sl_no() < 1) {
			// insert operation
			arcDet.setArc_sl_no(null);
			arcDet.setSample_no(reqwrapper.getArcDetails().getSample_no());
			arcDet.setRecord_status(1);
			arcDet.setRecord_version(0);
			arcDet.setCreated_by(userId);
			arcDet.setCreated_date_time(new Date());
			arc_no = lrfProductionDao.getNextArcNo(reqwrapper.getArcDetails().getHeat_id(),
					reqwrapper.getArcDetails().getHeat_counter());
			arcDet.setArc_no(Integer.parseInt(arc_no));
		} else {
			arcDet = getArcDetailsBySlno(reqwrapper.getArcDetails().getArc_sl_no());
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
			LRFHeatConsumableModel conObj = null;
			for (int i = 0; i < row.length; i++) {

				String id[] = row[i].split("@");
				key = key + i;
				conObj = new LRFHeatConsumableModel();
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
					conObj = getLRFHeatConsumablesById(Integer.parseInt(id[5]));
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
		result = lrfArcAdditionsSaveOrUpdate(mod_obj);
		return result;

	}

	public String lrfArcAdditionsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return lrfProductionDao.lrfArcAdditionsSaveOrUpdate(mod_obj);
	}

	@Transactional
	@Override
	public LRFHeatConsumableModel getLRFHeatConsumablesById(Integer cons_sl_no) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getLRFHeatConsumablesById(cons_sl_no);
	}

	@Transactional
	@Override
	public LRFHeatArcingDetailsModel getArcDetailsBySlno(Integer arc_sl_no) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getArcDetailsBySlno(arc_sl_no);
	}

	@Override
	public LRFHeatConsumableDisplay getLRFArcAdditionsByHeat(String heat_id, Integer heat_cnt, Integer unit_id) {
		// TODO Auto-generated method stub
		LRFHeatConsumableDisplay header = new LRFHeatConsumableDisplay();
		try {
			// header.setAr_si_no("SI_NO");
			header.setBath_sample_no("SAMPLE_NO");
			header.setBath_temp("BATH_TEMP");
			header.setArc_start_date_time("ARC_START_DT");
			header.setArc_end_date_time("ARC_END_DT");
			header.setPower_consumption("KWH");
			ArrayList<String> arc_additions = new ArrayList<String>();

			Integer mat_type_id=commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.LRF_ARC_ADDITIONS+"' and lookup_status=1");
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
	public List<Map<String, Object>> getLRFArcAdditionsTemp(Integer unit_id, String heatId, Integer heatCnt) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		try {
			Integer mtrl_type = commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code = '"+Constants.LRF_ARC_ADDITIONS+"' and lookup_status=1");
			List<MtrlProcessConsumableMstrModel> hlist = commonService.getDistinctProcessConsumablesByTypeId(mtrl_type, unit_id);
			String qry1 = "", qry3 = "", firstsqlQuery = "", finalSqlQry = "", lastsqlQuery = "";
			firstsqlQuery = "SELECT (select sample_no from trns_heat_chemistry_hdr where sample_si_no = a.BATH_SAMPLE_NO) as sample_no, a.arcing_start_date_time as arc_start_dt, a.arcing_end_date_time as arc_end_dt,"
					+ " a.BATH_TEMPERATURE as bath_temp,a.POWER_CONSUMPTION as kwh ";
			lastsqlQuery = " FROM trns_lrf_heat_arcing_dtls a WHERE a.heat_id = '" + heatId+"'";
					//+ "' AND a.heat_counter =" + heatCnt + ""; commented on 18/12/2018 suma

			for (MtrlProcessConsumableMstrModel obj : hlist) {

				qry1 = ",(SELECT b.consumption_qty FROM trns_lrf_heat_cons_lines b"
						+ " WHERE b.arcing_si_no = a.arc_si_no  AND b.material_id = (SELECT material_id   FROM mstr_process_consumables"
						+ " WHERE material_id =" + obj.getMaterial_id() + ")) AS \""+obj.getMaterial_desc()+"\"";
				qry3 = qry3.concat(qry1);
			}

			firstsqlQuery = firstsqlQuery.concat(qry3);
			finalSqlQry = firstsqlQuery.concat(lastsqlQuery);
			ls = lrfProductionDao.getLRFArcAdditionsTemp(finalSqlQry);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
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
	
	@Transactional
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsByAnalysisWithSpectro(Integer analysis_id, String heat_id,
			Integer heat_counter, Integer sub_unit_id, String sample_no, Integer psn_id,String actual_sample_no,String actual_heat) {
		// TODO Auto-generated method stub
		//List<HeatChemistryChildDetails> hcc = heatProceeEventDao.getChemDtlsByAnalysis(analysis_id, psn_id);
		List<HeatChemistryChildDetails> hcc = heatProceeEventDao.getChemDtlsByAnalysisWithSpectro(analysis_id, psn_id, actual_sample_no, actual_heat);

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
	public String saveLrfDispatchDet(LRFRequestWrapper reqWrapper, int userId) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String result = "";
		try {
			Hashtable<String, Object> mod_obj = null;
			SteelLadleTrackingModel stTrackObj = new SteelLadleTrackingModel();
			mod_obj = new Hashtable<String, Object>();
			LRFHeatDetailsModel lrfModel = null;
			if (!reqWrapper.getLrfHeatDetails().equals(null)) {

				if (!reqWrapper.getLrfHeatDetails().getTrns_sl_no().equals(null)
						&& !reqWrapper.getLrfHeatDetails().getLrf_process_remarks().equals(null)
						&& !reqWrapper.getLrfHeatDetails().getLrf_dispatch_wgt().equals(null)
						&& !reqWrapper.getLrfHeatDetails().getLrf_dispatch_unit().equals(null)
						&& !reqWrapper.getLrfHeatDetails().getLrf_dispatch_temp().equals(null)) {
					
					 lrfModel = lrfProductionDao
							.getLrfHeatDetailsById(reqWrapper.getLrfHeatDetails().getTrns_sl_no());
					lrfModel.setLrf_process_remarks(reqWrapper.getLrfHeatDetails().getLrf_process_remarks());
					lrfModel.setLrf_dispatch_temp(reqWrapper.getLrfHeatDetails().getLrf_dispatch_temp());
					lrfModel.setLrf_dispatch_unit(reqWrapper.getLrfHeatDetails().getLrf_dispatch_unit());
					lrfModel.setLrf_dispatch_wgt(reqWrapper.getLrfHeatDetails().getLrf_dispatch_wgt());
					lrfModel.setLrf_dispatch_date(new Date());
					mod_obj.put("LRF_HEAT_DET", lrfModel);
				}
				if (reqWrapper.getLrfHeatDetails().getSteel_ladle_no() > 0) {
					stTrackObj = steelLadleService
							.getStLadlleTrackObjById(reqWrapper.getLrfHeatDetails().getSteel_ladle_no());
					Integer ladle_status_id;
					if (stTrackObj != null) {
						ladle_status_id = commonService.getLookupIdByQuery(
								"select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status_id="+Constants.LADLE_DISP_FRM_LRF+" and  recordStatus=1");
						stTrackObj.setLadle_status(ladle_status_id);
						// stTrackObj.setSt_ladle_life(stTrackObj.getSt_ladle_life()+1);
						stTrackObj.setUpdatedBy(userId);
						stTrackObj.setUpdatedDateTime(new Date());
					}
					mod_obj.put("LRF_ST_LALDE_STATUS_UPDATE", stTrackObj);

				}
			}
			if (!reqWrapper.getLrfHeatStatus().equals(null)) {
				HeatStatusTrackingModel hstm = lrfProductionDao
						.getHeatStatusObject(new Integer(reqWrapper.getLrfHeatStatus().getHeat_track_id()));
				hstm.setMain_status(reqWrapper.getLrfHeatStatus().getMain_status());
				// hstm.setAct_proc_path(hstm.getAct_proc_path()+"-"+reqWrapper.getLrfHeatStatus().getAct_proc_path());
				hstm.setCurrent_unit(reqWrapper.getLrfHeatStatus().getCurrent_unit());
				hstm.setUnit_process_status(reqWrapper.getLrfHeatStatus().getUnit_process_status());
				hstm.setLrf_status(reqWrapper.getLrfHeatStatus().getLrf_status());
				hstm.setEof_status(reqWrapper.getLrfHeatStatus().getEof_status());
				hstm.setVd_status(reqWrapper.getLrfHeatStatus().getVd_status());
				hstm.setBlt_cas_status(reqWrapper.getLrfHeatStatus().getBlt_cas_status());
				hstm.setBlm_cas_status(reqWrapper.getLrfHeatStatus().getBlm_cas_status());
				hstm.setUpdatedBy(userId);
				hstm.setUpdatedDateTime(new Date());
				mod_obj.put("LRF_HEAT_STATUS", hstm);
			}
			
			if(reqWrapper.getLrfHeatStatus().getCurrent_unit().substring(0, 2).equals(Constants.SUB_UNIT_VD)){
				List<HeatChemistryHdrDetails> heatChemHdrLi = heatProceeEventDao.getSampleDtlsByAnalysisType(lrfModel.getSub_unit_id(), lrfModel.getHeat_id(), lrfModel.getHeat_counter(), Constants.LRF_LIFT_CHEM);
				if(heatChemHdrLi.size() > 0){
					HeatChemistryHdrDetails heatChemHdrObj = heatChemHdrLi.get(0);
					heatChemHdrObj.setAnalysis_type(commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = 'CHEM_LEVEL' and lookup_status = 1 and lookup_value='"+Constants.LRF_CHEM+"'"));
					heatChemHdrObj.setFinal_result(0);
					heatChemHdrObj.setUpdatedBy(userId);
					heatChemHdrObj.setUpdatedDateTime(new Date());
					
					mod_obj.put("LRF_HEAT_CHEM", heatChemHdrObj);
				}
			}
			if (mod_obj != null) {
				result = updateLRFHeatDetails(mod_obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;

	}

	private String updateLRFHeatDetails(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return lrfProductionDao.updateLRFHeatDetails(mod_obj);
	}

	@Transactional
	@Override
	public HashSet<LRFHeatDetailsModel> getHeatsForVDProcess(String cunit, String pstatus) {
		HashSet<LRFHeatDetailsModel> vdHeatDetails = new HashSet<LRFHeatDetailsModel>();
		List<LRFHeatDetailsModel> list = lrfProductionDao.getHeatsForVDProcess(cunit, pstatus);
		//List<LookupMasterModel> lookupList = lookupService.getLookupDtlsByLkpTypeAndStatus("ELEMENT", 1);
		try {
			list.forEach((data) -> {
				List<LRFHeatDetailsModel> lrfChemDet = lrfProductionDao.getLrfChemDetails(data.getHeat_id(), data.getHeat_counter());
				if (lrfChemDet != null && lrfChemDet.isEmpty()) {
					lrfChemDet.forEach((chemVal) -> {
						//lookupList.forEach((lkpMdl) -> {
							if (chemVal.getChem_element_name().equalsIgnoreCase("C")) {
								data.setLrf_C(chemVal.getElement_aim_value());
							}else if (chemVal.getChem_element_name().equalsIgnoreCase("Ti")) {
								data.setLrf_Ti(chemVal.getElement_aim_value());
							}else if (chemVal.getChem_element_name().equalsIgnoreCase("Mn")) {
								data.setLrf_MN(chemVal.getElement_aim_value());
							}else if (chemVal.getChem_element_name().equalsIgnoreCase("S")) {
								data.setLrf_S(chemVal.getElement_aim_value());
							}else if (chemVal.getChem_element_name().equalsIgnoreCase("P")) {
								data.setLrf_P(chemVal.getElement_aim_value());
							}else if (chemVal.getChem_element_name().equalsIgnoreCase("Si")) {
								data.setLrf_Si(chemVal.getElement_aim_value());
							}
						//});
					});
				}
				vdHeatDetails.add(data);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return vdHeatDetails;
	}

	@Transactional
	@Override
	public HashSet<LRFHeatDetailsModel> getHeatsForCasterProcess(String cunit, String pstatus) {
		// TODO Auto-generated method stub
		HashSet<LRFHeatDetailsModel> lrfHeatDetails = new HashSet<LRFHeatDetailsModel>();
		List<LRFHeatDetailsModel> lst=lrfProductionDao.getHeatsForCasterProcess(cunit, pstatus);
		//List<LookupMasterModel> lookupList = lookupService.getLookupDtlsByLkpTypeAndStatus("ELEMENT", 1);
		StringBuilder chemDetails=new StringBuilder("");
		lst.forEach((data) -> {
			List<LRFHeatDetailsModel> lrfChemDet =lrfProductionDao.getLrfChemDetails(data.getHeat_id(), data.getHeat_counter());
			lrfChemDet.forEach(chemVal->{
				//Optional<LookupMasterModel> lkpmstrmdl=lookupList.stream().filter(lkp->lkp.getLookup_id().equals(chemVal.getElement_id())).findFirst();
				//if(lkpmstrmdl.isPresent()) {
				if(chemVal.getChem_element_name().equalsIgnoreCase("C") || chemVal.getChem_element_name().equalsIgnoreCase("Ti") || chemVal.getChem_element_name().equalsIgnoreCase("Mn")
					|| chemVal.getChem_element_name().equalsIgnoreCase("S") || chemVal.getChem_element_name().equalsIgnoreCase("P") || chemVal.getChem_element_name().equalsIgnoreCase("Si")){
					chemDetails.append(" | "+chemVal.getChem_element_name() +"("+chemVal.getElement_aim_value()+")");
				}
				//}
				
				//lookupList.forEach((lkpMdl) -> {
					if(chemVal.getChem_element_name().equalsIgnoreCase("C")) {
						data.setLrf_C(chemVal.getElement_aim_value());
					}else if(chemVal.getChem_element_name().equalsIgnoreCase("Ti")) {
						data.setLrf_Ti(chemVal.getElement_aim_value());
					}else if(chemVal.getChem_element_name().equalsIgnoreCase("Mn")) {
						data.setLrf_MN(chemVal.getElement_aim_value());
					}else if(chemVal.getChem_element_name().equalsIgnoreCase("S")) {
						data.setLrf_S(chemVal.getElement_aim_value());
					}else if(chemVal.getChem_element_name().equalsIgnoreCase("P")) {
						data.setLrf_P(chemVal.getElement_aim_value());
					}else if(chemVal.getChem_element_name().equalsIgnoreCase("Si")) {
						data.setLrf_Si(chemVal.getElement_aim_value());
					}
				//});
			});
			data.setChem_details(chemDetails.toString());
			lrfHeatDetails.add(data);
		});
		return lrfHeatDetails;
	}

	@Override
	public List<DelayEntryDTO> getLrfDelayEntriesBySubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id,
			Integer heat_counter, String prev_unit) {
		List<ActivityDelayMasterModel> delayEntries = activityDlyMstrServie
				.getAllActivityDetalMasterBySubUnit(sub_unit_id);

		List<DelayEntryDTO> lrfDelay = new ArrayList<>();

		LRFHeatDetailsModel prevHeatDetails = lrfProductionDao.getLrfPreviousHeatDetailsByHeatNo(trans_heat_id);
		LRFHeatDetailsModel passedHeatinfo = lrfProductionDao.getLrfHeatDetailsById(trans_heat_id);
		EofHeatDetails eofHeatInfo=eofProductionDao.getEOFHeatDetailsByHeatNo(passedHeatinfo.getHeat_id());
		List<HeatProcessEventDetails> prevHeatevents = new ArrayList<HeatProcessEventDetails>();
		
		List<PSNProductMasterModel> prevHeatPsnProd = psnProdMstrService.getPSNProductMstrDtls(prevHeatDetails.getAim_psn());
		List<PSNProductMasterModel> curHeatPsnProd = psnProdMstrService.getPSNProductMstrDtls(passedHeatinfo.getAim_psn());
		
		if(prevHeatDetails!=null) {
		prevHeatevents=heatProcessEventService
				.getLrfHeatProcessEventDtls(prevHeatDetails.getHeat_id(), heat_counter, sub_unit_id, prev_unit, prevHeatPsnProd.get(0).getLkpProcRoutMstrMdl().getLookup_value());// prev
		}
		String psn_route = curHeatPsnProd.get(0).getLkpProcRoutMstrMdl().getLookup_value();
		List<HeatProcessEventDetails> currentHeatevents = heatProcessEventService
				.getLrfHeatProcessEventDtls(passedHeatinfo.getHeat_id(), heat_counter, sub_unit_id, prev_unit, psn_route);// current
		Map<String, HeatProcessEventDetails> preHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		Map<String, HeatProcessEventDetails> currentHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		
		Map<String, HeatProcessEventDetails> eofHeatEvts = new HashMap<String, HeatProcessEventDetails>();
		
		for (HeatProcessEventDetails prevHeatevent : prevHeatevents) {
			preHeatEvts.put(prevHeatevent.getEventMstrMdl().getEvent_desc().trim(), prevHeatevent);
		}
		for (HeatProcessEventDetails heatProcessEventDetail : currentHeatevents) {
			currentHeatEvts.put(heatProcessEventDetail.getEventMstrMdl().getEvent_desc().trim(), heatProcessEventDetail);
		}
		List<HeatProcessEventDetails> eofHeatEvents = heatProcessEventService.getHeatProcessEventDtls(eofHeatInfo.getTrns_si_no(),
				"");// current
		for (HeatProcessEventDetails heatProcessEventDetail : eofHeatEvents) {
			eofHeatEvts.put(heatProcessEventDetail.getEventMstrMdl().getEvent_desc(), heatProcessEventDetail);
		}
		
		String nextUnit, start_event = null, end_event = null;
		String subStr = psn_route.substring(psn_route.indexOf(Constants.SUB_UNIT_LRF)+Constants.SUB_UNIT_LRF.length()+1);
		if(subStr.indexOf("-") != -1) {
			nextUnit = subStr.substring(0, subStr.indexOf("-"));
		}else {
			nextUnit = subStr;
		}
		if(nextUnit.equalsIgnoreCase(Constants.SUB_UNIT_VD)) {
			start_event = Constants.LRF_BVD_POWER_ON;
			end_event = Constants.HEAT_PROCESS_EVENT_VD_DISP;
		}else if(nextUnit.equalsIgnoreCase(Constants.SUB_UNIT_CCM)) {
			if(prev_unit.substring(0, 2).equalsIgnoreCase(Constants.PREV_UNIT_VD)) 
				start_event = Constants.LRF_AVD_POWER_ON;
			else
				start_event = Constants.LRF_BVD_POWER_ON;
				
			end_event = Constants.HEAT_PROCESS_EVENT_CCM_DISP;
		}
		for (ActivityDelayMasterModel activityDelayMasterModel : delayEntries) {
			TransDelayEntryHeader transDelayHdr = transDelayEntryHeaderService.getTransDelayHeaderByHeatAndActivity(
					activityDelayMasterModel.getActivity_delay_id(), passedHeatinfo.getTrns_sl_no());
			DelayEntryDTO transDTO = new DelayEntryDTO();
			transDTO.setActivity_master(activityDelayMasterModel);
			long diff;
			long diffMinutes;
			if (transDelayHdr == null) {
				switch (activityDelayMasterModel.getActivity_seq()) {
				case 1:// Purging Start - Tap End@EOF
					//EofHeatDetails eofHeatDetails=eofProductionDao.getEOFHeatDetailsById(trans_heat_id);
					HeatProcessEventDetails eofTapEnd = eofHeatEvts.get(Constants.EAF_TAP_END);//preHeatEvts.get("PURGING_START");
					HeatProcessEventDetails lrfPurgeStart = currentHeatEvts.get(Constants.LRF_PURG_START);
					if(lrfPurgeStart!=null &&eofTapEnd!=null ) {
					if (lrfPurgeStart.getEvent_date_time() != null && eofTapEnd.getEvent_date_time() != null) {
						Date lrfPurgeStartTime = lrfPurgeStart.getEvent_date_time();
						Date eafTapEndTime = eofTapEnd.getEvent_date_time();
						transDTO.setStart_time(eafTapEndTime);
						transDTO.setEnd_time(lrfPurgeStartTime);
						diff = lrfPurgeStartTime.getTime() - eafTapEndTime.getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration(diffMinutes);
						if(activityDelayMasterModel.getStd_cycle_time() > diffMinutes) {
							diffMinutes = 0;
							transDTO.setDelay(diffMinutes);
						}
						else {
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						}
					}
					}
					break;
				case 2:// BVD_POWER_OFF_AT to BVD_POWER_ON_AT
					HeatProcessEventDetails bvd_power_on = currentHeatEvts.get(Constants.LRF_BVD_POWER_ON);
					HeatProcessEventDetails bvd_power_off = currentHeatEvts.get(Constants.LRF_BVD_POWER_OFF);

					if(bvd_power_off!=null && bvd_power_on!=null) {
					if (bvd_power_off.getEvent_date_time() != null
							&& bvd_power_on.getEvent_date_time() != null) {
						Date bvdPowerOffDate = bvd_power_off.getEvent_date_time();
						Date bvdPowerOnDate = bvd_power_on.getEvent_date_time();
						transDTO.setStart_time(bvdPowerOnDate);
						transDTO.setEnd_time(bvdPowerOffDate);
						
						diff = bvdPowerOffDate.getTime() - bvdPowerOnDate.getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration(diffMinutes);
						if(activityDelayMasterModel.getStd_cycle_time() > diffMinutes) {
							diffMinutes = 0;
							transDTO.setDelay(diffMinutes);
						}
						else {
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						}
					}
					}
					break;
				case 3:// AVD_POWER_ON_AT to AVD_POWER_OFF_AT

					HeatProcessEventDetails avd_power_on = currentHeatEvts.get(Constants.LRF_AVD_POWER_ON);
					HeatProcessEventDetails avd_power_off = currentHeatEvts.get(Constants.LRF_AVD_POWER_OFF);
					
					if(avd_power_off!=null && avd_power_on!=null) {
					if (avd_power_off.getEvent_date_time() != null && avd_power_on.getEvent_date_time() != null) {
						transDTO.setStart_time(avd_power_on.getEvent_date_time());
						transDTO.setEnd_time(avd_power_off.getEvent_date_time());
						diff = avd_power_off.getEvent_date_time().getTime()
								- avd_power_on.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration(diffMinutes);
						if(activityDelayMasterModel.getStd_cycle_time() > diffMinutes) {
							diffMinutes = 0;
							transDTO.setDelay(diffMinutes);
						}
						else {
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						}
					}
					}
					break;
				case 4:// Purging Start - Tap End@EOF

					HeatProcessEventDetails lrf_rinse_start = currentHeatEvts.get(Constants.LRF_RINSE_START);
					HeatProcessEventDetails lrf_rinse_end = currentHeatEvts.get(Constants.LRF_RINSE_END);
					if(lrf_rinse_start!=null && lrf_rinse_end!=null ) {
					if (lrf_rinse_end.getEvent_date_time() != null && lrf_rinse_start.getEvent_date_time() != null) {
						transDTO.setStart_time(lrf_rinse_start.getEvent_date_time());
						transDTO.setEnd_time(lrf_rinse_end.getEvent_date_time());
						diff = lrf_rinse_end.getEvent_date_time().getTime() - lrf_rinse_start.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration(diffMinutes);
						if(activityDelayMasterModel.getStd_cycle_time() > diffMinutes) {
							diffMinutes = 0;
							transDTO.setDelay(diffMinutes);
						}
						else {
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						}
					}
					}
					break;
				case 5://Ladle Lifting Start - BVD_Power On
					HeatProcessEventDetails lrf_bvd_power_on = currentHeatEvts.get(start_event);
					HeatProcessEventDetails lrf_ldl_lift = currentHeatEvts.get(end_event);
					if(lrf_ldl_lift!=null && lrf_bvd_power_on!=null) {
					if (lrf_ldl_lift.getEvent_date_time() != null && lrf_bvd_power_on.getEvent_date_time() != null) {
						transDTO.setStart_time(lrf_bvd_power_on.getEvent_date_time());
						transDTO.setEnd_time(lrf_ldl_lift.getEvent_date_time());
						diff = lrf_ldl_lift.getEvent_date_time().getTime() - lrf_bvd_power_on.getEvent_date_time().getTime();
						diffMinutes = diff / (60 * 1000);
						transDTO.setDuration(diffMinutes);
						if(activityDelayMasterModel.getStd_cycle_time() > diffMinutes) {
							diffMinutes = 0;
							transDTO.setDelay(diffMinutes);
						}
						else {
						transDTO.setDelay(diffMinutes - activityDelayMasterModel.getStd_cycle_time());
						}
					}
					}
					break;
				}
			} else {
				transDTO.setStart_time(transDelayHdr.getActivity_start_time());
				transDTO.setEnd_time(transDelayHdr.getActivity_end_time());
				transDTO.setDuration((long) transDelayHdr.getActivity_duration());
				if(transDelayHdr.getTotal_delay()!=null) {
				transDTO.setDelay((long) transDelayHdr.getTotal_delay());
				}
				transDTO.setTransDelayEntryhdr(transDelayHdr);
				transDTO.setCorrective_action(transDelayHdr.getCorrective_action());
			}
			lrfDelay.add(transDTO);
		}

		return lrfDelay;
	}

	@Override
	public List<TransDelayEntryHeader> getDelayDetailsHdrWithSubUnitAndHeat(Integer sub_unit_id,
			Integer trans_heat_id) {
		List<ActivityDelayMasterModel> delayEntries = activityDlyMstrServie
				.getAllActivityDetalMasterBySubUnit(sub_unit_id);
		LRFHeatDetailsModel passedHeatinfo = lrfProductionDao.getLrfHeatDetailsById(trans_heat_id);

		List<TransDelayEntryHeader> delayHdrLst = new ArrayList<TransDelayEntryHeader>();
		for (ActivityDelayMasterModel activityDelayMasterModel : delayEntries) {
			TransDelayEntryHeader transDelayHdr = transDelayEntryHeaderService.getTransDelayHeaderByHeatAndActivity(
					activityDelayMasterModel.getActivity_delay_id(), passedHeatinfo.getTrns_sl_no());
			if (transDelayHdr != null) {
				delayHdrLst.add(transDelayHdr);
			}
		}
		return delayHdrLst;
	}

	@Override
	public LRFHeatDetailsModel getLRFHeatDetailsByHeatNo(String heatNo,Integer heat_counter) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getLRFHeatDetailsByHeatNo(heatNo,heat_counter);
	}

	@Transactional
	@Override
	public List<LRFHeatDetailsModel> getHeatsForLadleMix(String cunit, String pstatus) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getHeatsForLadleMix(cunit, pstatus);
	}

	@Override
	public HeatStatusTrackingModel getHeatStatusObject(Integer integer) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getHeatStatusObject(integer);
	}

	@Override
	public String updateLRFHeatDetForLadleMix(LRFHeatDetailsModel lrfHeatObj1, LRFHeatDetailsModel lrfHeatObj2,
			HeatStatusTrackingModel heatTrackObj1, HeatStatusTrackingModel heatTrackObj2) {
		// TODO Auto-generated method stub
		return lrfProductionDao.updateLRFHeatDetForLadleMix(lrfHeatObj1, lrfHeatObj2, heatTrackObj1, heatTrackObj2);
	}

	@Override
	public LRFHeatDetailsModel getLRFHeatObject(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getLRFHeatObject(trns_sl_no);
	}

	@Override
	public List<LRFHeatLogRpt> getLrfHeatLogs(String heatId, String LogType) {
		// TODO Auto-generated method stub
		List<LRFHeatLogRpt> list = lrfProductionDao.getLrfHeatLogs(heatId, LogType);
		return list;//
	}

	@Override
	public List<LrfElectrodeTransactions> getAllElectrodeUsageTrnsByUnit(Integer sub_unit_id, Integer trans_si_no) {
		// TODO Auto-generated method stub
		List<LookupMasterModel> types = lookupService.getLookupDtlsByLkpTypeAndStatus("LRF_ELECTRODE", 1);
		List<LrfElectrodeTransactions> crossVerifiedLst = new ArrayList<>();
		types.forEach((lkp) -> {
			LrfElectrodeTransactions lrfE = null;
			lrfE = lrfElectrodeTransDao.getElectrodeStatusByUnitAndLkp(sub_unit_id, lkp.getLookup_id(), trans_si_no);
			if (lrfE == null) {
				lrfE = new LrfElectrodeTransactions();
				lrfE.setElectrodeTransId(0);
				lrfE.setSubUintId(sub_unit_id);
				lrfE.setElectrodeId(lkp.getLookup_id());
				lrfE.setElectrodeLkpMstrModel(lkp);

			}
			crossVerifiedLst.add(lrfE);

		});
		return crossVerifiedLst; // lrfElectrodeTransDao.getAllElectrodeUsageTrnsByUnit(sub_unit_id);
	}

	@Override
	public String lrfElectrodeUsageTrnsSaveOrUpdate(LrfElectrodeTransactions obj) {
		// TODO Auto-generated method stub
		return lrfElectrodeTransDao.lrfElectrodeUsageTrnsSaveOrUpdate(obj);
	}

	@Override
	public LrfElectrodeTransactions lrfElectrodeUsageTrnsById(Integer id) {
		// TODO Auto-generated method stub
		return lrfElectrodeTransDao.lrfElectrodeUsageTrnsById(id);
	}

	@Override
	public String updateLrfHeatDetails(LRFHeatDetailsModel model) {
		String str=lrfProductionDao.updatelrfHeatDetails(model);
		return str;
	}


	@Override
	public String updateLRFHeatDetPSN(LRFHeatDetailsModel lrfHeatObj,
			LRFHeatDetailsPsnBkpModel lrfHeatPsnBkp,
			List<HeatPlanHdrDetails> heatPlanHdrLi,
			List<HeatPlanDetails> heatPlanDetLi,
			HeatStatusTrackingModel heatTrackObj,
			IfacesmsLpDetailsModel ifaceObj) {
		// TODO Auto-generated method stub
		return lrfProductionDao.updateLRFHeatDetPSN(lrfHeatObj, lrfHeatPsnBkp, heatPlanHdrLi, heatPlanDetLi, heatTrackObj,ifaceObj);
	}


	@Override
	public List<ReladleTrnsDetailsMdl> getUnProcessedReturnHeatDtls(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getUnProcessedReturnHeatDtls(trns_sl_no) ;
	}


	@Override
	public ReladleTrnsDetailsMdl getReturnHeatDetailsById(Integer trns_sl_no) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getReturnHeatDetailsById(trns_sl_no);
	}


	@Override
	public String saveReladleDetails(List<ReladleProcessHdrModel> reladleHdrList,
			List<ReladleTrnsDetailsMdl> reladleHeatDtlLi,
			List<HeatStatusTrackingModel> heatTrackLi,
			List<SteelLadleTrackingModel> steelLadleTrackLi,
			List<SteelLadleLifeModel> stLadlePartsLi, 
			List<StLadleLifeHeatWiseModel> heatwiseStLdlPartsLi, StLdlLifeAtHeat heatwiseStLdlObj) {
		// TODO Auto-generated method stub
		return  lrfProductionDao.saveReladleDetails(reladleHdrList, reladleHeatDtlLi, heatTrackLi, steelLadleTrackLi, stLadlePartsLi, heatwiseStLdlPartsLi, heatwiseStLdlObj);
	}


	@Override
	public String saveDispatchToLrfDtls(LRFHeatDetailsModel lrfHeatObj,
			LRFHeatDetailsModel newLrfHeatObj,
			HeatStatusTrackingModel heatTrackObj, ReladleTrnsDetailsMdl reladleTrnsObj) {
		// TODO Auto-generated method stub
		return lrfProductionDao.saveDispatchToLrfDtls(lrfHeatObj, newLrfHeatObj, heatTrackObj, reladleTrnsObj);
	}

	@Transactional
	@Override
	public List<LRFHeatDetailsModel> getLrfChemDetails(String heat_id,
			Integer heat_counter) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getLrfChemDetails(heat_id, heat_counter);
	}


	@Override
	public List<ReladleProcessDetailsModel> getReladleDetails(Integer reladle_heat_id) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getReladleDetails(reladle_heat_id);
	}

	@Transactional
	@Override
	public List<LRFHeatConsumableModel> getLrfAdditionsByHeatNo(String heat_no, String mtrl_type) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getLrfAdditionsByHeatNo(heat_no, mtrl_type) ;
	}


	@Override
	public String saveOrUpdateMatrlCons(List<LRFHeatConsumableModel> updLi, List<LRFHeatConsumableLogModel> logLi) {
		// TODO Auto-generated method stub
		return lrfProductionDao.saveOrUpdateMatrlCons(updLi, logLi);
	}
	
	@Override
	@Transactional
	public HeatStatusTrackingModel getHeatTrackingObj(Integer heatTrackingId) {
		return lrfProductionDao.getHeatTrackingObj(heatTrackingId);
	}
	
	@Override
	public List<Object> getActualPathUnits(String act_proc_path)
	{
		String LineString = act_proc_path;
		List<Object> lst=new ArrayList<Object>();
	    LineString = LineString.replaceFirst("-", " ");
	    String[] pathValues=LineString.split("\\s+");    
	    for (String s : pathValues) {
	    	lst.add(s);
		}
		return lst;
	}


	@Override
	@Transactional
	public HeatChemistryHdrDetails getHeatDetailsByHeatNo(String heatNo, String LogType) {
		// TODO Auto-generated method stub
		return lrfProductionDao.getHeatDetailsByHeatNo(heatNo,LogType);
	}


	/*@Override
	public List<LrfElectrodeTransactions> getAllLRFParameterUsageTrnsByUnit(Integer sub_unit_id, Integer trans_si_no) {
		// TODO Auto-generated method stub
		return null;
	}*/


}
