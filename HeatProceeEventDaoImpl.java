package com.smes.trans.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.masters.dao.impl.LookupMasterDao;
import com.smes.masters.model.EventMasterModel;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.PsnChemistryMasterModel;
import com.smes.masters.model.ScrapBucketStatusModel;
import com.smes.masters.model.SpectroLabChemMaster;
import com.smes.masters.service.impl.PsnChemistryMasterService;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.EofDispatchDetails;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatChemistryChildDetailsHistory;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatProcessParameterDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLdlHeatingDtls;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleCampaignModel;
import com.smes.trans.model.SteelLadleLifeModel;
//import com.smes.trans.model.SteelLadleMaintenanceModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;
import com.smes.util.SpectroLabUtil;

import oracle.jdbc.OracleTypes;

@Repository("HeatProceeEventDao")
public class HeatProceeEventDaoImpl extends GenericDaoImpl<HeatProcessEventDetails, Long>
		implements HeatProceeEventDao {

	private static final Logger logger = Logger.getLogger(HeatProceeEventDaoImpl.class);

	@Autowired
	private PsnChemistryMasterService psnChemSerivice;
	
	@Autowired
	private LookupMasterDao lookupMstrDao;
	
	@Override
	public String saveHeatProcessEvent(HeatProcessEventDetails hped) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		try {
			logger.info(HeatProceeEventDaoImpl.class + " Inside saveHeatProcessEvent ");
			create(hped);

		} catch (Exception e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside saveHeatProcessEvent Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;

	}

	@Transactional
	@Override
	public List<CCMHeatConsMaterialsDetails> getCCMMtrlDetailsByType(String mtrlType, Integer ccm_trns_sno, String psn_no, Float heat_qty) {
		String hql = "";
		List<CCMHeatConsMaterialsDetails> heatConsMtrlDtls = new ArrayList<CCMHeatConsMaterialsDetails>();	
		
		if(psn_no.equals("NA")){
			hql="SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty || '&&' || TO_CHAR (a.consumption_date, 'DD/MM/YYYY HH:MI:SS')|| '&&'|| a.record_version ||'&&' || nvl(a.sap_matl_id,'NA') FROM trns_ccm_heat_cons_materials a WHERE a.material_id = c.material_id AND a.trns_ccm_si_no = "
		            + ccm_trns_sno+ "), c.material_id, c.material_desc, d.lookup_value, c.order_seq,nvl(c.sap_mtl_desc,'NA') FROM mstr_process_consumables c, app_lookups d, app_lookups e WHERE c.uom = d.lookup_id  AND c.record_status = 1  AND c.material_type = e.lookup_id "
		            +" AND e.lookup_code = '"+mtrlType+"'";    		
		}
		List ls = (List<CCMHeatConsMaterialsDetails>) getResultFromCustomQuery(hql);
		heatConsMtrlDtls=getDetailsFilledByCCMPSN_NA(ls, heat_qty);
		
		return heatConsMtrlDtls;
	}
	@Override
	public List<HeatConsMaterialsDetails> getMtrlDetailsByType(String mtrlType, Integer eof_trns_sno,String psn_no) {
		logger.info("inside getMtrlDetailsByType" + HeatProceeEventDaoImpl.class);
		EofHeatDetails eofHeatDetails = getEofHeatDtlsById(eof_trns_sno);
		Session session = getNewSession();
		String hql = "";
		List<HeatConsMaterialsDetails> heatConsMtrlDtls = new ArrayList<HeatConsMaterialsDetails>();
		HeatConsMaterialsDetails obj;
		int flag=0;
		try {
			if(psn_no.equals("NA")){
				
				hql="SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty || '&&' || TO_CHAR (a.consumption_date, 'DD/MM/YYYY HH:MI:SS')|| '&&'|| a.record_version || '&&' || nvl(a.sap_matl_id,'NA') || '&&' || nvl(a.valuation_type,'NA') FROM trns_eof_heat_cons_materials a WHERE a.material_id = c.material_id AND a.trns_eof_si_no = "
				+ eof_trns_sno+ "), c.material_id, c.material_desc, d.lookup_value, c.order_seq,nvl(c.sap_mtl_desc,'NA'),nvl(g.lookup_value,'NA') valuation_type FROM mstr_process_consumables c, app_lookups d, app_lookups e, app_lookups g WHERE c.uom = d.lookup_id  AND c.record_status = 1  AND c.material_type = e.lookup_id and g.lookup_id(+) = c.valuation_type AND g.lookup_type(+) = 'MATL_VALUATION_TYPE' "
				+" AND e.lookup_code = '"+mtrlType+"'";
				flag=1;
			}else{
				flag=2;
				hql = "SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty|| '&&' || to_char(a.consumption_date,'DD/MM/YYYY HH:MI:SS') || '&&' ||a.record_version || '&&' || nvl(a.sap_matl_id,'NA') || '&&' || nvl(a.valuation_type,'NA')  FROM TRNS_EOF_HEAT_CONS_MATERIALS a WHERE a.material_id = c.material_id "
						+ "AND a.trns_eof_si_no = " + eof_trns_sno + "),f.record_status, c.material_id, "
						+ "c.material_desc, d.lookup_value,f.value_min,f.value_max,f.value_aim,c.order_seq, nvl(c.sap_mtl_desc,'NA'), nvl(g.lookup_value,'NA') valuation_type  FROM MSTR_PROCESS_CONSUMABLES c, "
						+ "APP_LOOKUPS d, APP_LOOKUPS e,MSTR_PSN_ADDITIONS f,APP_LOOKUPS g WHERE c.uom = d.lookup_id AND c.record_status = 1  and c.historian_mtl_desc='Heat' AND g.lookup_id(+) = c.valuation_type AND g.lookup_type(+) = 'MATL_VALUATION_TYPE' AND "
						+ "c.material_type = e.lookup_id AND e.lookup_code = '" + mtrlType
						+ "' and c.material_id=f.mat_id and f.sub_unit_id=" + eofHeatDetails.getSub_unit_id()
						+" and f.psn_hdr_sl_no =(select d.psn_hdr_sl_no from MSTR_PSN_HDR d where d.psn_no='"+psn_no+"' and  PSN_STATUS='APPROVED')"
						+ " union "
						+" SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty|| '&&' || to_char(a.consumption_date,'DD/MM/YYYY HH:MI:SS') || '&&' ||a.record_version || '&&' || nvl(a.sap_matl_id,'NA') || '&&' || nvl(a.valuation_type,'NA')  FROM TRNS_EOF_HEAT_CONS_MATERIALS a WHERE a.material_id = c.material_id "
						+ "AND a.trns_eof_si_no = " + eof_trns_sno + "),f.record_status, c.material_id, "
						+ "c.material_desc, d.lookup_value,f.VALUE_MIN,f.VALUE_Max,f.VALUE_aim,c.order_seq, nvl(c.sap_mtl_desc,'NA'), nvl(g.lookup_value,'NA') valuation_type  FROM MSTR_PROCESS_CONSUMABLES c, "
						+ "APP_LOOKUPS d, APP_LOOKUPS e,MSTR_MTRL_UNIT_MAP f,APP_LOOKUPS g WHERE c.uom = d.lookup_id AND c.record_status = 1 and c.historian_mtl_desc='Heat' AND g.lookup_id(+) = c.valuation_type AND g.lookup_type(+) = 'MATL_VALUATION_TYPE' AND "
						+ "c.material_type = e.lookup_id AND e.lookup_code = '" + mtrlType
						+ "' and c.material_id=f.mtrl_id and f.sub_unit_id=" + eofHeatDetails.getSub_unit_id()
						+" and f.mtrl_id not in (select ff.mat_id FROM MSTR_PROCESS_CONSUMABLES cc, "
						+ "APP_LOOKUPS dd, APP_LOOKUPS ee,MSTR_PSN_ADDITIONS ff WHERE cc.uom = dd.lookup_id AND cc.record_status = 1 AND "
						+ "c.material_type = e.lookup_id AND e.lookup_code = '" + mtrlType
						+ "' and cc.material_id=ff.mat_id and ff.sub_unit_id=" + eofHeatDetails.getSub_unit_id()
						+" and ff.psn_hdr_sl_no =(select ddd.psn_hdr_sl_no from MSTR_PSN_HDR ddd where ddd.psn_no='"+psn_no+"' and PSN_STATUS='APPROVED'))"
						+ " ORDER BY order_seq";
			}
		
			
		
			List ls = (List<HeatConsMaterialsDetails>) getResultFromCustomQuery(hql);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// Convert from String to Date
			if(flag==2) {
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new HeatConsMaterialsDetails();
				String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));
				
				if (s != null) {
					obj.setMtr_cons_si_no((null == s[0]) ? null : Integer.parseInt(s[0].toString()));
					obj.setQty((null == s[1]) ? null : Double.parseDouble(s[1].toString()));
					obj.setConsumption_date(df.parse(s[2].toString()));
					obj.setVersion((null == s[3]) ? 0 : Integer.parseInt(s[3].toString()));
					obj.setSap_matl_id(("NA" == s[4] || null == s[4]) ? null : (s[4].toString()));
					obj.setValuation_type(("NA" == s[5] || null == s[5]) ? null : (s[5].toString()));
				} else {
					
					obj.setMtr_cons_si_no(null);
					obj.setQty(null);
					obj.setConsumption_date(new Date());
					obj.setVersion(0);
					obj.setSap_matl_id((null == rows[9] || "NA" == rows[9]) ? null : rows[9].toString());
					obj.setValuation_type((null == rows[10]  || "NA" == rows[10]) ? null : rows[10].toString());
				}
				
				obj.setRecord_status((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));
				obj.setMaterial_id((null == rows[2]) ? null : Integer.parseInt(rows[2].toString()));
				obj.setMtrlName((null == rows[3]) ? null : rows[3].toString());
				obj.setUom((null == rows[4]) ? null : rows[4].toString());
				if(!psn_no.equals("NA")){
				obj.setVal_min((null == rows[5]) ? null : Double.parseDouble(rows[5].toString()));
				obj.setVal_max((null == rows[6]) ? null : Double.parseDouble(rows[6].toString()));
				obj.setVal_aim((null == rows[7]) ? null : Double.parseDouble(rows[7].toString()));
			     }
	
				heatConsMtrlDtls.add(obj);
			}
			}else if(flag==1){
				heatConsMtrlDtls=getDetailsFilledByPSN_NA(ls);
			}
			
			//heatConsMtrlDtls=ls;

		} catch (Exception e) {
			logger.error("Exception.. getMtrlDetailsByType..." + e.getMessage());
			e.printStackTrace();
		} finally {
			close(session);
		}

		return heatConsMtrlDtls;
	}
	
	public List<CCMHeatConsMaterialsDetails> getDetailsFilledByCCMPSN_NA(List<CCMHeatConsMaterialsDetails> ls, Float heat_qty){
		List<CCMHeatConsMaterialsDetails> heatConsMtrlDtls = new ArrayList<CCMHeatConsMaterialsDetails>();
		Double calcVal;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// Convert from String to Date
		Iterator it = ls.iterator();
		CCMHeatConsMaterialsDetails obj;
		while (it.hasNext()) {
			try {
			Object rows[] = (Object[]) it.next();
			obj = new CCMHeatConsMaterialsDetails();
			String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));
			
			if (s != null) {
				obj.setMtr_cons_si_no((null == s[0]) ? null : Integer.parseInt(s[0].toString()));
				//obj.setQty((null == s[1]) ? null : Double.parseDouble(s[1].toString()));
				obj.setConsumption_date(df.parse(s[2].toString()));
				obj.setVersion((null == s[3]) ? 0 : Integer.parseInt(s[3].toString()));
			} else {
				
				obj.setMtr_cons_si_no(null);
				//obj.setQty(null);
				obj.setConsumption_date(new Date());
				obj.setVersion(0);
			}
			
			obj.setMaterial_id((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));
			obj.setMtrlName((null == rows[2]) ? null : rows[2].toString());
			if(obj.getMtrlName().equalsIgnoreCase(Constants.CCM_BYPRD_TUND_SKULL)){
				calcVal = heat_qty * Constants.CCM_TUND_SKULL_PERC * 0.001;
			}else if(obj.getMtrlName().equalsIgnoreCase(Constants.CCM_BYPRD_LDL_SKULL)){
				calcVal = heat_qty * Constants.CCM_LDL_SKULL_PERC * 0.001;
			}else if(obj.getMtrlName().equalsIgnoreCase(Constants.CCM_BYPRD_MILL_SCALE)){
				calcVal = heat_qty * Constants.CCM_MILL_SCALE_PERC * 0.001;
			}else{
				calcVal = null;
			}
			if (s != null){
				if(s[1] == null){
					if(calcVal == null)
						obj.setQty(calcVal);
					else
						obj.setQty(Double.parseDouble(new DecimalFormat("#.###").format (calcVal.doubleValue()))); 
				}else{
					obj.setQty(Double.parseDouble(s[1].toString()));
				}
			}else{
				if(calcVal == null)
					obj.setQty(calcVal);
				else
					obj.setQty(Double.parseDouble(new DecimalFormat("#.###").format(calcVal.doubleValue()))); 
			}
			
			obj.setUom((null == rows[3]) ? null : rows[3].toString());
			obj.setSap_matl_id((null == rows[5]) ? null : rows[5].toString());
			heatConsMtrlDtls.add(obj);
			}
			catch(Exception ex) {
				logger.error("error in getDetailsFilledByPSN_NA......." + ex);
				ex.printStackTrace();
		     }		
		}
		return heatConsMtrlDtls;
	}
	
	public List<HeatConsMaterialsDetails> getDetailsFilledByPSN_NA(List<HeatConsMaterialsDetails> ls){
		List<HeatConsMaterialsDetails> heatConsMtrlDtls = new ArrayList<HeatConsMaterialsDetails>();
		//
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// Convert from String to Date
		Iterator it = ls.iterator();
		HeatConsMaterialsDetails obj;
		while (it.hasNext()) {
			try {
			Object rows[] = (Object[]) it.next();
			obj = new HeatConsMaterialsDetails();
			String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));
			
			if (s != null) {
				obj.setMtr_cons_si_no((null == s[0]) ? null : Integer.parseInt(s[0].toString()));
				obj.setQty((null == s[1]) ? null : Double.parseDouble(s[1].toString()));
				obj.setConsumption_date(df.parse(s[2].toString()));
				obj.setVersion((null == s[3]) ? 0 : Integer.parseInt(s[3].toString()));
				obj.setSap_matl_id(("NA" == s[4]) ? null : s[4].toString());
				obj.setValuation_type(("NA" == s[5]) ? null : s[5].toString());
			} else {
				
				obj.setMtr_cons_si_no(null);
				obj.setQty(null);
				obj.setConsumption_date(new Date());
				obj.setVersion(0);
				obj.setSap_matl_id((null == rows[5]) ? null : rows[5].toString());
				obj.setValuation_type((null == rows[6]) ? null : rows[6].toString());
			}
			
			obj.setMaterial_id((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));
			obj.setMtrlName((null == rows[2]) ? null : rows[2].toString());
			obj.setUom((null == rows[3]) ? null : rows[3].toString());
			heatConsMtrlDtls.add(obj);
			}
			catch(Exception ex) {
				logger.error("error in getDetailsFilledByPSN_NA......." + ex);
				ex.printStackTrace();
		     }		
		}
		
		return heatConsMtrlDtls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EofHeatDetails getEofHeatDtlsById(Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEofHeatDtlsById....." + HeatProceeEventDaoImpl.class);
		List<EofHeatDetails> list = new ArrayList<EofHeatDetails>();
		EofHeatDetails eofObj = new EofHeatDetails();
		Session session = getNewSession();
		try {

			String hql = "select a from EofHeatDetails a where a.trns_si_no =" + eof_trns_sno + "";
			list = (List<EofHeatDetails>) session.createQuery(hql).list();
			
			eofObj = list.get(0);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEofHeatDtlsById........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return eofObj;
	}

	@Override
	public EofHeatDetails getEOFHeatDtlsFormByID(Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEOFHeatDtlsFormByID....." + HeatProceeEventDaoImpl.class);
		EofHeatDetails eofHeatObj = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {

			String hql = "select a.trns_si_no,a.heat_counter,a.aim_psn,a.heat_plan_line_id,a.plan_wt,d.hmRecvId,d.hmAvlblWt,"
					+ "a.heat_id,e.psn_no,c.indent_no,d.hmLadleNo,a.hm_wt,a.hm_temp,a.heat_plan_id,a.subUnitMstrMdl.sub_unit_name,a.sub_unit_id,a.version,a.production_shift,"
					+ "TO_CHAR (a.production_date, 'DD/MM/YYYY HH:MI:SS'), (select to_char(f.event_date_time,'DD/MM/YYYY HH:MI:SS') from HeatProcessEventDetails f, EventMasterModel m where a.heat_id = f.heat_id and f.event_id = m.event_si_no and m.event_desc = 'TAP_START_AT')"
							+ ", (select to_char(f.event_date_time,'DD/MM/YYYY HH:MI:SS') from HeatProcessEventDetails f, EventMasterModel m where a.heat_id = f.heat_id and f.event_id = m.event_si_no and m.event_desc = 'TAP_END_AT')"
					+ " from EofHeatDetails a,HeatConsScrapMtrlDetails b,HeatPlanDetails c,HmReceiveBaseDetails d,PSNHdrMasterModel e where a.trns_si_no= "
					+ eof_trns_sno + ""
					+ " and a.trns_si_no=b.trns_eof_si_no and b.hm_seq_no=d.hmRecvId and a.heat_plan_line_id=c.heat_plan_dtl_id and a.aim_psn=e.psn_hdr_sl_no";
			 
			List ls = (List<EofHeatDetails>) getResultFromNormalQuery(hql);
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				eofHeatObj = new EofHeatDetails();

				eofHeatObj.setTrns_si_no(Integer.parseInt((null == rows[0]) ? null : rows[0].toString()));
				eofHeatObj.setHeat_counter(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				eofHeatObj.setAim_psn(Integer.parseInt((null == rows[2]) ? null : rows[2].toString()));
				eofHeatObj.setHeat_plan_line_id(Integer.parseInt((null == rows[3]) ? null : rows[3].toString()));
				//eofHeatObj.setPlan_wt(Double.parseDouble((null == rows[4]) ? null : rows[4].toString()));
				eofHeatObj.setHmRecvId(Integer.parseInt((null == rows[5]) ? null : rows[5].toString()));
				eofHeatObj.setAvailable_hm(Double.parseDouble((null == rows[6]) ? null : rows[6].toString()));

				eofHeatObj.setHeat_id((null == rows[7]) ? null : rows[7].toString());
				eofHeatObj.setPsn_no((null == rows[8]) ? null : rows[8].toString());
				eofHeatObj.setHeat_plan_line_no(Integer.parseInt((null == rows[9]) ? null : rows[9].toString()));
				eofHeatObj.setHm_ladle_no((null == rows[10]) ? null : rows[10].toString());
				if(rows[11]!=null) {
				eofHeatObj.setHm_wt(Double.parseDouble((null == rows[11]) ? null : rows[11].toString()));
				}
				eofHeatObj.setHm_temp(Integer.parseInt((null == rows[12]) ? null : rows[12].toString()));
				eofHeatObj.setHeat_plan_id(Integer.parseInt((null == rows[13]) ? null : rows[13].toString()));
				eofHeatObj.setSub_unit((null == rows[14]) ? null : rows[14].toString());
				eofHeatObj.setSub_unit_id(Integer.parseInt((null == rows[15]) ? null : rows[15].toString()));
				eofHeatObj.setVersion(Integer.parseInt((null == rows[16]) ? null : rows[16].toString()));
				eofHeatObj.setProduction_shift((null == rows[17]) ? null : Integer.parseInt(rows[17].toString()));
				eofHeatObj.setProduction_date(((null == rows[18]) ? null : df.parse(rows[18].toString())));
				eofHeatObj.setTap_start_at(((null == rows[19]) ? null : df.parse(rows[19].toString())));
				eofHeatObj.setTap_close_at(((null == rows[20]) ? null : df.parse(rows[20].toString())));
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(HeatProceeEventDaoImpl.class + " error in getEOFHeatDtlsFormByID........" + e);
			e.printStackTrace();
		}

		return eofHeatObj;
	}

	@Override
	public String ccmAdditionMtrlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if ((HeatProcessEventDetails) mod_obj.get("HEATPROEVNT") != null) {
				session.save((HeatProcessEventDetails) mod_obj.get("HEATPROEVNT"));
			}

			if (Integer.parseInt(mod_obj.get("HEATCONSMTRLCNT").toString()) > 0) {

				Integer cnt = Integer.parseInt(mod_obj.get("HEATCONSMTRLCNT").toString());
				String key = "HEATCONSMTRL";
				for (int i = 0; i <= cnt; i++) {
					key = key + i;
					if ((CCMHeatConsMaterialsDetails) mod_obj.get(key) != null) {
						CCMHeatConsMaterialsDetails heatmtrl_det = (CCMHeatConsMaterialsDetails) mod_obj.get(key);

						if (heatmtrl_det.getMtr_cons_si_no() != null) {
							session.update(heatmtrl_det);
						} else {
							session.save(heatmtrl_det);
						}
						heatmtrl_det = null;
					}
				}
			}
			commit(session);
			result = Constants.SAVE;
		} catch (org.hibernate.StaleObjectStateException s) {
			logger.error(HeatProceeEventDaoImpl.class
					+ " Inside org.hibernate.StaleObjectStateException..furnaceAdditionMtrlsSaveOrUpdate", s);

			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;

			rollback(session);

		} catch (DataIntegrityViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside ccmAdditionMtrlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}
	
	@Override
	public String furnaceAdditionMtrlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if ((HeatProcessEventDetails) mod_obj.get("HEATPROEVNT") != null) {
				session.save((HeatProcessEventDetails) mod_obj.get("HEATPROEVNT"));
			}

			if (Integer.parseInt(mod_obj.get("HEATCONSMTRLCNT").toString()) > 0) {

				Integer cnt = Integer.parseInt(mod_obj.get("HEATCONSMTRLCNT").toString());
				String key = "HEATCONSMTRL";
				for (int i = 0; i <= cnt; i++) {
					key = key + i;
					if ((HeatConsMaterialsDetails) mod_obj.get(key) != null) {
						HeatConsMaterialsDetails heatmtrl_det = (HeatConsMaterialsDetails) mod_obj.get(key);

						if (heatmtrl_det.getMtr_cons_si_no() != null) {
							session.update(heatmtrl_det);
						} else {
							session.save(heatmtrl_det);
						}
						heatmtrl_det = null;
					}
				}
			}

			// SCRAP ADDITION START

			if ((HeatConsScrapMtrlDetails) mod_obj.get("HEATSCRAP") != null) {
				session.save((HeatConsScrapMtrlDetails) mod_obj.get("HEATSCRAP"));
			}

			if ((ScrapBucketStatusModel) mod_obj.get("SCRAPBKTSTATUS") != null) {
				session.update((ScrapBucketStatusModel) mod_obj.get("SCRAPBKTSTATUS"));
			}

			if ((ScrapBucketHdr) mod_obj.get("SCRAPHDRSTATUS") != null) {
				session.update((ScrapBucketHdr) mod_obj.get("SCRAPHDRSTATUS"));
			}

			// SCRAP ADDITION END

			commit(session);
			result = Constants.SAVE;
		} catch (org.hibernate.StaleObjectStateException s) {
			logger.error(HeatProceeEventDaoImpl.class
					+ " Inside org.hibernate.StaleObjectStateException..furnaceAdditionMtrlsSaveOrUpdate", s);

			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;

			rollback(session);

		} catch (DataIntegrityViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside furnaceAdditionMtrlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public HeatConsMaterialsDetails getHeatConsMtrlsDtlsById(Integer Mtrl_Cons_ID) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatConsMtrlsDtlsById.....");
		HeatConsMaterialsDetails list = new HeatConsMaterialsDetails();
		Session session = getNewSession();
		try {
			list = (HeatConsMaterialsDetails) session.get(HeatConsMaterialsDetails.class, Mtrl_Cons_ID);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatConsMtrlsDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public CCMHeatConsMaterialsDetails getccmHeatConsMtrlsDtlsById(Integer Mtrl_Cons_ID) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatConsMtrlsDtlsById.....");
		CCMHeatConsMaterialsDetails list = new CCMHeatConsMaterialsDetails();
		Session session = getNewSession();
		try {
			list = (CCMHeatConsMaterialsDetails) session.get(CCMHeatConsMaterialsDetails.class, Mtrl_Cons_ID);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatConsMtrlsDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id, Integer psn_id) {
		// TODO Auto-generated method stub
		List<HeatChemistryChildDetails> heatChemistryDtls = new ArrayList<HeatChemistryChildDetails>();
		HeatChemistryChildDetails heatChemobj;
		try {
			List<PsnChemistryMasterModel> psnChemDtls = psnChemSerivice.getPsnChemMstrDtlsByPSN(psn_id, analysis_id);
			List<LookupMasterModel> hcc=(List<LookupMasterModel>)psnChemSerivice.getlookUpChem();
			List<LookupMasterModel> newLkpLi = new ArrayList<LookupMasterModel>();
			List<LookupMasterModel> udCodeLi = lookupMstrDao.getLookupDtlsByLkpTypeAndStatus("UOM", 1);
			for(LookupMasterModel lkpObj : hcc) {
				List<PsnChemistryMasterModel> psnLi = psnChemDtls.stream().filter(p -> p.getElement_id().equals(lkpObj.getLookup_id())).collect(Collectors.toList());
				if(psnLi.size() == 0) {
					newLkpLi.add(lkpObj);
				}
			}
			for(PsnChemistryMasterModel psnObj : psnChemDtls) {
				heatChemobj = new HeatChemistryChildDetails();
				 heatChemobj.setElement(psnObj.getElement_id());
					heatChemobj.setUom(psnObj.getUomLkpMstrModel().getLookup_value());
					heatChemobj.setPsn_aim_value(psnObj.getValue_aim());
					heatChemobj.setMin_value(psnObj.getValue_min());
					heatChemobj.setMax_value(psnObj.getValue_max());
					heatChemobj.setElementName(psnObj.getElementLkpMstrModel().getLookup_value());
					heatChemistryDtls.add(heatChemobj);
			}
			for(LookupMasterModel hcc1 : newLkpLi) {
				heatChemobj = new HeatChemistryChildDetails();
                        heatChemobj.setElement(hcc1.getLookup_id());
						heatChemobj.setUom(udCodeLi.get(0).getLookup_value());
						heatChemobj.setElementName(hcc1.getLookup_value());
						heatChemistryDtls.add(heatChemobj);
		}
		}
	 catch (Exception e) {
			logger.error("Exception.. getChemDtlsByAnalysis..." + e.getMessage());
			e.printStackTrace();
		}
		return heatChemistryDtls;
	}
	
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsByAnalysisWithSpectro(Integer analysis_id, Integer psn_id,String actual_sample,String heat_id) {
		List<HeatChemistryChildDetails> heatChemistryDtls = new ArrayList<HeatChemistryChildDetails>();
		HeatChemistryChildDetails heatChemobj;
		List<SpectroLabChemMaster> spectroObj=new ArrayList<SpectroLabChemMaster>();
		try {
			
			List<PsnChemistryMasterModel> psnChemDtls = psnChemSerivice.getPsnChemMstrDtlsByPSN(psn_id, analysis_id);
			if(psnChemDtls.size()>0) {
			for (PsnChemistryMasterModel psn : psnChemDtls) {
				heatChemobj = new HeatChemistryChildDetails();
				heatChemobj.setElement(psn.getElement_id());
				heatChemobj.setUom(psn.getUomLkpMstrModel().getLookup_value());
				heatChemobj.setPsn_aim_value(psn.getValue_aim());
				heatChemobj.setMin_value(psn.getValue_min());
				heatChemobj.setMax_value(psn.getValue_max());
				String elements=psn.getElementLkpMstrModel().getLookup_value();
				heatChemobj.setElementName(elements);	
				spectroObj=SpectroLabUtil.getChemByHeatIdAndSampleNo(heat_id, actual_sample,elements);	
				if(spectroObj!=null && spectroObj.size()>0 && Double.valueOf(spectroObj.get(0).getAvg())>0) {
				heatChemobj.setAim_value(Double.valueOf(spectroObj.get(0).getAvg()));	
				heatChemobj.setRemarks("Spectro Server");
				heatChemobj.setSpectro_flag(1);
				String dateValue = spectroObj.get(0).Time;
				//  String date=stringToDate(dateValue);
			        heatChemobj.setSample_date(dateValue);
				}
				else {
					heatChemobj.setAim_value(null);
					heatChemobj.setRemarks("Manual Entry");	
					heatChemobj.setSpectro_flag(0);
				}
				heatChemistryDtls.add(heatChemobj);
			};
			}
		} catch (Exception e) {
			logger.error("Exception.. getChemDtlsByAnalysisWithSpectro..." + e.getMessage());
			e.printStackTrace();
		}
		return heatChemistryDtls;
	}
	@Override
	public List<HeatChemistryChildDetails> getTundishChem(Integer analysis_id, Integer psn_id,String actual_sample,String heat_id,Integer heat_counter) {
		List<HeatChemistryChildDetails> heatChemistryDtls = new ArrayList<HeatChemistryChildDetails>();
		HeatChemistryChildDetails heatChemobj;
		List<SpectroLabChemMaster> spectroObj=new ArrayList<SpectroLabChemMaster>();
		try {
			
			List<PsnChemistryMasterModel> psnChemDtls = psnChemSerivice.getPsnChemMstrDtlsByPSN(psn_id, analysis_id);
			if(psnChemDtls.size()>0) {
			for (PsnChemistryMasterModel psn : psnChemDtls) {
				heatChemobj = new HeatChemistryChildDetails();
				heatChemobj.setElement(psn.getElement_id());
				heatChemobj.setUom(psn.getUomLkpMstrModel().getLookup_value());
				heatChemobj.setPsn_aim_value(psn.getValue_aim());
				heatChemobj.setMin_value(psn.getValue_min());
				heatChemobj.setMax_value(psn.getValue_max());
				String elements=psn.getElementLkpMstrModel().getLookup_value();
				heatChemobj.setElementName(elements);	
				spectroObj=SpectroLabUtil.getTundishChem(heat_id, actual_sample,elements);	
				if(spectroObj!=null && spectroObj.size()>0 && Double.valueOf(spectroObj.get(0).getAvg())>0) {
				heatChemobj.setAim_value(Double.valueOf(spectroObj.get(0).getAvg()));	
				heatChemobj.setRemarks("Spectro Server");
				heatChemobj.setSpectro_flag(1);
				   String dateValue = spectroObj.get(0).Time;
				//  String date=stringToDate(dateValue);
			        heatChemobj.setSample_date(dateValue);
				}
				else {
					//heatChemobj.setAim_value(0.00);
					heatChemobj.setRemarks("Manual Entry");	
					heatChemobj.setSpectro_flag(0);
				}
				
				heatChemistryDtls.add(heatChemobj);
			}
			}
		} catch (Exception e) {
			logger.error("Exception.. getChemDtlsByAnalysisWithSpectro..." + e.getMessage());
			e.printStackTrace();
		}
		return heatChemistryDtls;
	}

	@Override
	public HeatChemistryHdrDetails getHeatChemistryHdrDtlsById(Integer sample_si_Id, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatChemistryHdrDtlsById.....");
		HeatChemistryHdrDetails list = new HeatChemistryHdrDetails();
		Session session = getNewSession();
		try {
			String query = "select a from HeatChemistryHdrDetails a where a.sample_si_no= " + sample_si_Id;
			list = (HeatChemistryHdrDetails) session.createQuery(query).uniqueResult();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error in getHeatChemistryHdrDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HeatChemistryHdrDetails> getSampleDtlsByAnalysis(Integer sub_unit_id,
			String heat_id, Integer analysis_id, Integer heat_counter) {
		// TODO Auto-generated method stub
		logger.info("inside .. getSampleDtlsByAnalysis.....");
		List<HeatChemistryHdrDetails> list = new ArrayList<HeatChemistryHdrDetails>();
		Session session=getNewSession(); 
		String hql;
		if(analysis_id == 0) //LRF
			hql = "Select a from HeatChemistryHdrDetails a where a.heat_id = '"+heat_id+"'";// and a.heat_counter="+heat_counter*/
		else
			hql = "Select a from HeatChemistryHdrDetails a where a.sub_unit_id = "+sub_unit_id+" and a.heat_id = '"+heat_id+"' and a.analysis_type = "+analysis_id;// and a.heat_counter="+heat_counter; 
		try { 
			list = (List<HeatChemistryHdrDetails>) session.createQuery(hql).list();
		 } catch (Exception e) { 
			// TODO: handle exception
			logger.error("error in getSampleDtlsByAnalysis........"+e); 
		} 
		finally{
		 	close(session); 
		} 
		return list;
	}

	@Override
	public HeatChemistryChildDetails getHeatChemistryChildDtlsById(Integer dtls_si_Id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatChemistryChildDtlsById.....");
		HeatChemistryChildDetails list = new HeatChemistryChildDetails();
		Session session = getNewSession();
		try {
			list = (HeatChemistryChildDetails) session.get(HeatChemistryChildDetails.class, dtls_si_Id);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatChemistryChildDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}
	
	@Override
	public String heatChemistryAllDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if ((HeatProcessEventDetails) mod_obj.get("HEATPROEVNT") != null) {
				session.save((HeatProcessEventDetails) mod_obj.get("HEATPROEVNT"));
			}

			if ((HeatChemistryHdrDetails) mod_obj.get("HEATCHEMHDR") != null) {
				HeatChemistryHdrDetails heatChemHdr = (HeatChemistryHdrDetails) mod_obj.get("HEATCHEMHDR");
				if (heatChemHdr.getSample_si_no() != null) {
					session.update((HeatChemistryHdrDetails) mod_obj.get("HEATCHEMHDR"));
				} else {
					session.save((HeatChemistryHdrDetails) mod_obj.get("HEATCHEMHDR"));
				}
			}
			
			if (Integer.parseInt(mod_obj.get("HEATCHEMCHILDCNT").toString()) > 0) {
				HeatChemistryHdrDetails chemHdr = (HeatChemistryHdrDetails) mod_obj.get("HEATCHEMHDR");
				Integer cnt = Integer.parseInt(mod_obj.get("HEATCHEMCHILDCNT").toString());
				String key = "HEATCHEMCHILD";
				for (int i = 0; i <= cnt; i++) {
					key = key + i;
					HeatChemistryChildDetails heatChemChild = (HeatChemistryChildDetails) mod_obj.get(key);
					if ((HeatChemistryChildDetails) mod_obj.get(key) != null) {
						heatChemChild.setSample_si_no(chemHdr.getSample_si_no());
						if (heatChemChild.getDtls_si_no() != null) {
							session.update(heatChemChild);
						} else {
							session.save(heatChemChild);
						}
						heatChemChild = null;
						
					}//if
					
				} // for
				
			}
			if ((LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD") != null) {
				HeatChemistryHdrDetails chemHdrObj = (HeatChemistryHdrDetails) mod_obj.get("HEATCHEMHDR");
				LRFHeatArcingDetailsModel arcObj = (LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD");
				arcObj.setSample_no(chemHdrObj.getSample_si_no());
				session.update(arcObj);
			}
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside heatChemistryAllDtlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}	

		return result;
	}

	@Override
	public List<HeatProcessEventDetails> getHeatProcessEventDtls(Integer eof_trns_sno,String unit) {
		
		logger.info(HeatProceeEventDaoImpl.class + "inside .. getHeatProcessEventDtls.....");
		 EofHeatDetails eofHeatDetails = getEofHeatDtlsById(eof_trns_sno);
			
		Session session = getNewSession();
		String hql = "";
		List<HeatProcessEventDetails> heatProcessEventDtls = new ArrayList<HeatProcessEventDetails>();
		HeatProcessEventDetails obj;
		try {

			hql = "SELECT (SELECT a.heat_proc_event_id || '&&' || TO_CHAR (a.event_date_time, 'DD/MM/YYYY hh24:mi') FROM HeatProcessEventDetails a"
					+ " WHERE a.event_id = b.event_si_no AND a.heat_id = '" + eofHeatDetails.getHeat_id()
					+ "' AND a.heat_counter = " + eofHeatDetails.getHeat_counter() + "), "
					+ "b.event_si_no, b.event_desc,b.event_unit_seq  FROM EventMasterModel b WHERE b.sub_unit_id ="
					+ eofHeatDetails.getSub_unit_id() + " and b.recordStatus=1 "
					+ " order by event_unit_seq" ;

			List ls = (List<HeatProcessEventDetails>) getResultFromNormalQuery(hql);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// Convert from String to Date

			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new HeatProcessEventDetails();

				String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));

				if (s != null) {
					obj.setHeat_proc_event_id(Integer.parseInt((null == s[0]) ? null : s[0].toString()));
					obj.setEvent_date_time(df.parse(s[1].toString()));

				} else {
					obj.setHeat_proc_event_id(null);
					obj.setEvent_date_time(null);

				}

				obj.setEvent_id(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				obj.setEventName((null == rows[2]) ? null : rows[2].toString());
				obj.setEvent_unit_seq((null == rows[3]) ? null : Float.parseFloat(rows[3].toString()));
				EventMasterModel event = new EventMasterModel();
				event.setSub_unit_id(eofHeatDetails.getSub_unit_id());
				event.setEvent_si_no(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				event.setEvent_unit_seq(((null == rows[3]) ? null : Float.parseFloat(rows[3].toString())));
				event.setEvent_desc((null == rows[2]) ? null : rows[2].toString());
				obj.setEventMstrMdl(event);

				heatProcessEventDtls.add(obj);
			}

		} catch (Exception e) {
			logger.error(HeatProceeEventDaoImpl.class + " Exception.. getHeatProcessEventDtls..." + e.getMessage());
			e.printStackTrace();
		} finally {
			close(session);
		}

		return heatProcessEventDtls;
	}

	@Override
	public List<HeatProcessEventDetails> getHeatProcessEventDtlsByUnit(String heat_id, Integer heat_cnt,
			Integer sub_unit_id) {
		logger.info(HeatProceeEventDaoImpl.class + "inside .. getHeatProcessEventDtlsByUnit.....");
		// EofHeatDetails eofHeatDetails = getEofHeatDtlsById(eof_trns_sno);
			
		Session session = getNewSession();
		String hql = "";
		List<HeatProcessEventDetails> heatProcessEventDtls = new ArrayList<HeatProcessEventDetails>();
		HeatProcessEventDetails obj;
		try {

			hql = "SELECT (SELECT a.heat_proc_event_id || '&&' || TO_CHAR (a.event_date_time, 'DD/MM/YYYY hh24:mi') FROM HeatProcessEventDetails a"
					+ " WHERE a.event_id = b.event_si_no AND a.heat_id = '" + heat_id
					+ "' AND a.heat_counter = " + heat_cnt + "), "
					+ "b.event_si_no, b.event_desc,b.event_unit_seq,b.event_desc  FROM EventMasterModel b WHERE b.sub_unit_id ="
					+ sub_unit_id + "and b.recordStatus=1"
					+ " order by event_unit_seq" ;

			List ls = (List<HeatProcessEventDetails>) getResultFromNormalQuery(hql);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// Convert from String to Date

			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new HeatProcessEventDetails();

				String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));

				if (s != null) {
					obj.setHeat_proc_event_id(Integer.parseInt((null == s[0]) ? null : s[0].toString()));
					obj.setEvent_date_time(df.parse(s[1].toString()));

				} else {
					obj.setHeat_proc_event_id(null);
					obj.setEvent_date_time(null);

				}

				obj.setEvent_id(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				obj.setEventName((null == rows[2]) ? null : rows[2].toString());
				obj.setEvent_unit_seq((null == rows[3]) ? null : Float.parseFloat(rows[3].toString()));
				EventMasterModel event = new EventMasterModel();
				event.setSub_unit_id(sub_unit_id);
				event.setEvent_si_no(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				event.setEvent_unit_seq(((null == rows[3]) ? null : Float.parseFloat(rows[3].toString())));
				event.setEvent_desc(((null == rows[4]) ? null : String.valueOf(rows[4].toString())));
				obj.setEventMstrMdl(event);

				heatProcessEventDtls.add(obj);
			}

		} catch (Exception e) {
			logger.error(HeatProceeEventDaoImpl.class + " Exception.. getHeatProcessEventDtlsByUnit..." + e.getMessage());
			e.printStackTrace();
		} finally {
			close(session);
		}

		return heatProcessEventDtls;
	}

	@Override
	public String processEventDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);

			if (Integer.parseInt(mod_obj.get("HEATPROCESSEVENT_CNT").toString()) > 0) {

				Integer cnt = Integer.parseInt(mod_obj.get("HEATPROCESSEVENT_CNT").toString());
				String key = "HEATPROCESSEVENT";
				for (int i = 0; i <= cnt; i++) {
					key = key + i;
					if ((HeatProcessEventDetails) mod_obj.get(key) != null) {
						HeatProcessEventDetails heatEvent_det = (HeatProcessEventDetails) mod_obj.get(key);

						if (heatEvent_det.getHeat_proc_event_id() != null) {
							session.update(heatEvent_det);
						} else {
							session.save(heatEvent_det);
						}
						heatEvent_det = null;
					}
				}
				
				if ((SteelLadleTrackingModel) mod_obj.get("STLADLE_TRACK") != null) {
					session.update((SteelLadleTrackingModel) mod_obj.get("STLADLE_TRACK"));
				}
			}
			if ((HeatProcessEventDetails) mod_obj.get("HEATPROCESS_LRF_EVENT") != null) {
				session.save((HeatProcessEventDetails) mod_obj.get("HEATPROCESS_LRF_EVENT"));
			}

			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside processEventDtlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public HeatProcessEventDetails getHeatProcessEventDtlsById(Integer heat_proc_eventId) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatProcessEventDtlsById.....");
		HeatProcessEventDetails list = new HeatProcessEventDetails();
		Session session = getNewSession();
		try {
			list = (HeatProcessEventDetails) session.get(HeatProcessEventDetails.class, heat_proc_eventId);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatProcessEventDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public String eofDispatchDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session=getNewSession();
		try{
			begin(session);
			
			
			
			if((List<EofDispatchDetails>)mod_obj.get("EOFDISPATCH")!=null)
			{
				List<EofDispatchDetails> eofDisp = (List<EofDispatchDetails>) mod_obj.get("EOFDISPATCH");
				for (EofDispatchDetails a:eofDisp){
				if(a.getCampaign_id()!=null)
				{
					session.update(a);
					
				}else{
					session.save(a);
					
				}
				}
				
				if((EofHeatDetails)mod_obj.get("EOFHEATCAMPAIGNUPDATE")!=null)
				{
				EofHeatDetails eofHeat =(EofHeatDetails)mod_obj.get("EOFHEATCAMPAIGNUPDATE");
				if(eofDisp.get(0).getCampaign_id()!=null){
				    eofHeat.setEof_campaign_no(eofDisp.get(0).getCampaign_id());

				    session.update((EofHeatDetails)mod_obj.get("EOFHEATCAMPAIGNUPDATE"));
				  }
				}// end if eof heat campaign update
				
				if((EofHeatDetails)mod_obj.get("EOFHEATCAMPAIGNUPDATE")!=null)
				{
				HeatPlanDetails heatLineStatus =(HeatPlanDetails)mod_obj.get("EOFHEATPLANLINESTATUSUPDATE");
				if(heatLineStatus.getHeat_plan_dtl_id()!=null){
					
					session.update((HeatPlanDetails)mod_obj.get("EOFHEATPLANLINESTATUSUPDATE"));
				}
				if((SteelLadleTrackingModel)mod_obj.get("EOFSTEELLADLESTATUSUPDATE")!=null)
				{
									
					session.update((SteelLadleTrackingModel)mod_obj.get("EOFSTEELLADLESTATUSUPDATE"));
				}
				
				}// end if Plan Line status update code
				
				/** heat status Update begin **/
				if((HeatStatusTrackingModel)mod_obj.get("EOF_HEAT_STATUS")!=null){
					HeatStatusTrackingModel mobj=(HeatStatusTrackingModel)mod_obj.get("EOF_HEAT_STATUS");
					if(mobj.getHeat_track_id()!=null){
						session.update(mobj);
					}
					
				}
				/** heat status Update end **/
				
				
				/** Actions at time of Dispatch
				/**Begin close prev ladle campaign**/
				if((SteelLadleCampaignModel)mod_obj.get("CLOSE_PREV_LDL_CAMPAIGN")!=null)
				{
					session.update((SteelLadleCampaignModel)mod_obj.get("CLOSE_PREV_LDL_CAMPAIGN"));
				}
				/**End close prev ladle campaign**/
				
				/**Begin insert new ladle campaign**/
				if((SteelLadleCampaignModel)mod_obj.get("INSERT_LADLE_CAMPAIGN")!=null)
				{
					session.save((SteelLadleCampaignModel)mod_obj.get("INSERT_LADLE_CAMPAIGN"));
				}
				/**End insert new ladle campaign**/
				
				if(mod_obj.get("LADLE_LIFE_DET_CNT") != null) {
				if(Integer.parseInt(mod_obj.get("LADLE_LIFE_DET_CNT").toString())>0){
					
					Integer cnt=Integer.parseInt(mod_obj.get("LADLE_LIFE_DET_CNT").toString());
					String key="LADLE_LIFE_DETAILS";
					for(int i=0;i<=cnt;i++)
					{
						key=key+i;
					if((SteelLadleLifeModel)mod_obj.get(key)!=null)
					{
						SteelLadleLifeModel ldlLifeobj=(SteelLadleLifeModel)mod_obj.get(key);
					if(ldlLifeobj.getLadle_life_sl_no()!=null)
					{
					session.update(ldlLifeobj);
					}else{
					session.save(ldlLifeobj);
					}
					ldlLifeobj=null;
					}
					}
				}
				}
				//Steel Ladle Maintenance
//				if((List<SteelLadleMaintenanceModel>)mod_obj.get("STEEL_LADLE_MAINT")!=null){
//					List<SteelLadleMaintenanceModel> steelMaint = (List<SteelLadleMaintenanceModel>) mod_obj.get("STEEL_LADLE_MAINT");
//					for (SteelLadleMaintenanceModel obj : steelMaint){
//						session.update(obj);
//					}
//				}				
//				if((StLadleStatusTrackHistoryModel)mod_obj.get("STEEL_LADLE_MAINT")!=null)
//				{
//					session.save((StLadleStatusTrackHistoryModel)mod_obj.get("STEEL_LADLE_MAINT"));
//				}
			
				if((List<StLadleLifeHeatWiseModel>)mod_obj.get("STEEL_LADLE_PART_LIFE_HEAT")!=null){
					List<StLadleLifeHeatWiseModel> stldlPartLifeHeat = (List<StLadleLifeHeatWiseModel>) mod_obj.get("STEEL_LADLE_PART_LIFE_HEAT");
					for (StLadleLifeHeatWiseModel obj : stldlPartLifeHeat){
						session.update(obj);
					}
				}

				if((StLdlLifeAtHeat)mod_obj.get("STEEL_LADLE_LIFE_HEAT")!=null)
				{
					session.save((StLdlLifeAtHeat)mod_obj.get("STEEL_LADLE_LIFE_HEAT"));
				}
				
				if((StLdlHeatingDtls)mod_obj.get("STEEL_LADLE_HEATING_INFO")!=null)
				{
					session.save((StLdlHeatingDtls)mod_obj.get("STEEL_LADLE_HEATING_INFO"));
				}
				
			}// end if EOF Dispatch
			
			
			
			
			commit(session);
			result =Constants.SAVE;
		} 
		catch(DataIntegrityViolationException e)
		{
			logger.error(HeatProceeEventDaoImpl.class+" Inside eofDispatchDtlsSaveOrUpdate DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch(ConstraintViolationException e)
		{
			logger.error(HeatProceeEventDaoImpl.class+" Inside eofDispatchDtlsSaveOrUpdate DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class+" Inside eofDispatchDtlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;
	}

	@Override
	public EofDispatchDetails getEofDispatchDetailsById(Integer campaignId) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEofDispatchDetailsById....." + HeatProceeEventDaoImpl.class);
		List<EofDispatchDetails> list = new ArrayList<EofDispatchDetails>();
		EofDispatchDetails eofDispObj = new EofDispatchDetails();
		Session session = getNewSession();
		try {

			String hql = "select a from EofDispatchDetails a where a.campaign_id =" + campaignId + "";
			list = (List<EofDispatchDetails>) session.createQuery(hql).list();
			eofDispObj = list.get(0);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEofDispatchDetailsById........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return eofDispObj;
	}

	@Override
	public EofDispatchDetails getEofDispPreviousDtls(Integer subunitid, String lifeParameter ) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEofDispatchPrevoiusDtls....."+lifeParameter);
		
		EofDispatchDetails eofDispPrevObj=new EofDispatchDetails();
		List<EofDispatchDetails> list = new ArrayList<EofDispatchDetails>();
				
		Session sess=getNewSession();
		try {
			//String hql1 = "Select a from EofDispatchDetails a where a.campaign_id = (select max(b.campaign_id) from EofDispatchDetails b where b.sub_unit_id ="+subunitid+")";
			String hql =  "Select a from EofDispatchDetails a,LookupMasterModel l  where a.sub_unit_id ="+subunitid+ " and l.lookup_id = a.lifeParameter and a.campaign_life_status=1 and l.lookup_code ='"+lifeParameter+"')";
					
			list=(List<EofDispatchDetails>) sess.createQuery(hql).list();
			
			if(list.size()>0){
				eofDispPrevObj = list.get(0);
			}else{
				eofDispPrevObj=null;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error in getEofDispatchPrevoiusDtls........"+e);
		}
		finally{
			close(sess);
		}
		return eofDispPrevObj;
	}

	@Override
	public List<HeatProcessParameterDetails> getHeatProcParamDtls(String heatid, Integer heatcntr, Integer subunitid,
			String psn_no) {
		Session session = getNewSession();
		Criteria crit = session.createCriteria(PSNHdrMasterModel.class);
		crit.add(Restrictions.eq("psn_no", psn_no));
		crit.add(Restrictions.eq("psn_status", "APPROVED"));
		PSNHdrMasterModel psnMstr = (PSNHdrMasterModel) crit.uniqueResult();
		session.close();
		List<HeatProcessParameterDetails> heatProcParam = new ArrayList<HeatProcessParameterDetails>();
		HeatProcessParameterDetails obj;
		try {

		
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append(
					" select  (select   a.proc_param_event_id|| '&&'|| a.param_value_aim||  '&&' || TO_CHAR (a.process_date_time, 'DD/MM/YYYY HH:MI') "
			+ 		" FROM trns_heat_process_parameters a WHERE a.param_id = b.PSN_PROC_PARAM_SL_NO AND a.heat_id = '"
							+ heatid + "' AND a.heat_counter =" + heatcntr + ") as col_1,"
			+" b.PSN_PROC_PARAM_SL_NO, c.param_desc, lookup_value as uom, b.VALUE_MIN,b.VALUE_MAX,b.VALUE_AIM,c.is_mandatory from mstr_psn_process_parameters b, "
			+" mstr_process_parameters c,app_lookups l WHERE b.sub_unit_id ="+ subunitid + " and b.PSN_HDR_SL_NO=" + psnMstr.getPsn_hdr_sl_no()+ ""
			+ " and c.param_si_no = b.param_id and c.heat_parameter=1 and c.record_status=1 and l.lookup_id = c.uom and b.sub_unit_id = c.sub_unit_id "
			+ " union "
			+ " select  (select   a.proc_param_event_id|| '&&'|| a.param_value_aim||  '&&' || TO_CHAR (a.process_date_time, 'DD/MM/YYYY HH:MI')"
			+ " FROM trns_heat_process_parameters a WHERE a.param_id = c.param_si_no AND a.heat_id = '"
			+ heatid + "' AND a.heat_counter =" + heatcntr + ") as col_1,"
			+" c.param_si_no, c.param_desc, lookup_value as uom, c.min_VALUE,c.MAX_value,c.aim_VALUE,c.is_mandatory from  "
			+" mstr_process_parameters c,app_lookups l WHERE c.sub_unit_id ="+ subunitid + ""
			+ " and c.heat_parameter=1 and  c.record_status=1 and c.param_si_no not in (select  bb.PARAM_id from mstr_psn_process_parameters bb WHERE bb.sub_unit_id = c.sub_unit_id AND bb.psn_hdr_sl_no ="
			+  psnMstr.getPsn_hdr_sl_no()+"" 
			+ " ) and l.lookup_id = c.uom ");
			List<HeatProcessParameterDetails> ls = (List<HeatProcessParameterDetails>) getResultFromCustomQuery(queryBuilder.toString());
   
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// Convert from String to Date

			Iterator<?> it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new HeatProcessParameterDetails();
				//System.out.println("ROW LENTGH -> "+rows.length);
				for(int i=0;i<rows.length;i++) {
					System.out.println(null == rows[i] ? 0 : rows[i].toString());
				}
				if (rows[0] != null) {
					String s[] = rows[0].toString().trim().split("&&");

					if (s.length > 0) {

						obj.setProc_param_event_id((null == s[0]) ? null : Integer.parseInt(s[0].toString()));
						obj.setParam_value_actual((null == s[1]) ? null : Double.parseDouble(s[1].toString()));
						obj.setProcess_date_time(df.parse(s[2].toString()));

					} else {
						obj.setProc_param_event_id(null);
						obj.setParam_value_actual(null);
						obj.setProcess_date_time(new Date());

					}
				} else {
					obj.setProc_param_event_id(null);
					obj.setParam_value_actual(null);
					obj.setProcess_date_time(new Date());

				}

				obj.setParam_id(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				obj.setProc_para_name((null == rows[2]) ? null : rows[2].toString());
				obj.setUom((null == rows[3]) ? null : rows[3].toString());
                obj.setParam_value_min((null == rows[4]) ? null : Double.parseDouble(rows[4].toString()));
				obj.setParam_value_max((null == rows[5]) ? null : Double.parseDouble(rows[5].toString()));
				obj.setParam_value_aim((null == rows[6]) ? null : Double.parseDouble(rows[6].toString()));
				obj.setIs_mandatory((null == rows[7]) ? null :rows[7].toString());
				heatProcParam.add(obj);
			}

		} catch (Exception e) {
			logger.error("Exception.. getMtrlDetailsByType..." + e.getMessage());
			e.printStackTrace();
		} finally {
			 close(session);
		}

		return heatProcParam;
	}

	@Override
	public String processParamSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if (Integer.parseInt(mod_obj.get("HEATPROCPARA_CNT").toString()) > 0) {

				Integer cnt = Integer.parseInt(mod_obj.get("HEATPROCPARA_CNT").toString());
				String key = "HEATPROCPARA";
				for (int i = 0; i <= cnt; i++) {
					key = key + i;
					if ((HeatProcessParameterDetails) mod_obj.get(key) != null) {
						HeatProcessParameterDetails heatProcParam = (HeatProcessParameterDetails) mod_obj.get(key);

						if (heatProcParam.getProc_param_event_id() != null) {
							session.update(heatProcParam);
						} else {
							session.save(heatProcParam);
						}
					}
				}
			} // end if

			if ((HeatProcessParameterDetails) mod_obj.get("HEATPROCESSPARAM_BLOW") != null) {
				HeatProcessParameterDetails heatProcParamBlow = (HeatProcessParameterDetails) mod_obj
						.get("HEATPROCESSPARAM_BLOW");
				if (heatProcParamBlow.getProc_param_event_id() != null) {
					session.update(heatProcParamBlow);
				} else {
					session.save(heatProcParamBlow);
				}
			}
			commit(session);
			result = Constants.SAVE;
		}

		catch (DataIntegrityViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside processParamSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public HeatProcessParameterDetails getProcParamDtlsById(Integer proc_param_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getProcParamDtlsById.....");
		HeatProcessParameterDetails list = new HeatProcessParameterDetails();
		Session session = getNewSession();
		try {
			list = (HeatProcessParameterDetails) session.get(HeatProcessParameterDetails.class, proc_param_id);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getProcParamDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public List<EofDispatchDetails> getEOFCampaignLife(Integer subUnit) {
		// TODO Auto-generated method stub
		//String hql = "";
		List<EofDispatchDetails> list = new ArrayList<EofDispatchDetails>(); 
		EofDispatchDetails obj;
		try {
			
		
		
			String qry = "SELECT a.campaign_id, a.sub_unit_id, a.campaign_number, a.total_heats,"+  
	       " l.lookup_code paramname ,l.LOOKUP_id  FROM trns_furnace_campaign_lines a, mstr_sub_unit_details c, APP_LOOKUPS l "+
	       " WHERE  a.sub_unit_id = c.sub_unit_id and a.sub_unit_id = " + subUnit+
	       " and  a.campaign_life_status = 1 and a.life_parameter = l.LOOKUP_ID " +
	       " and l.LOOKUP_STATUS = 1 " +
	       " and l.LOOKUP_CODE in ('"+Constants.LIFE_PARA_WALL_LIFE+"', '"+Constants.LIFE_PARA_BOT_LIFE+"','"+Constants.LIFE_PARA_EBT_LIFE+"',"
	       		+ "'"+Constants.LIFE_PARA_DELTA_LIFE+"','"+Constants.LIFE_PARA_HM_LNDR_LIFE+"')";

			logger.info("inside .getEOFCampaignLife. campaign life....."+qry);
			
			@SuppressWarnings("unchecked")
			List ls=(List<EofDispatchDetails>) getResultFromCustomQuery(qry);
			
			
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new EofDispatchDetails();
				obj.setCampaign_id((null == rows[0]) ? null :Integer.parseInt(rows[0].toString()));
				obj.setSub_unit_id((null == rows[1]) ? null :Integer.parseInt(rows[1].toString()));
				obj.setCampaign_number((null == rows[2]) ? null :Integer.parseInt(rows[2].toString()));
				obj.setTotal_heats((null == rows[3]) ? null :Integer.parseInt(rows[3].toString()));
				obj.setLifeParameterName((null == rows[4]) ? null :rows[4].toString());
				obj.setLifeParameter(null== rows[5] ? null :Integer.parseInt(rows[5].toString()));
				list.add(obj);
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception.. getEOFCampaignLife..."+e.getMessage());
		}
		
		return list;
	}

	@Override
	public Double getTotalFurnaceWeight(String heat_id, Integer sub_unit_id, Integer counter) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHMDetailsbyInterface.....");
		Session session = null;
		String res = null;
		try {
			session = getNewSession();

			res = session.doReturningWork(new ReturningWork<String>() {
				public String execute(Connection connection) throws SQLException {
					CallableStatement call = null;
					try {
						call = connection.prepareCall("{ ? = call gettotalhmweight(?,?,?) }");
						call.registerOutParameter(1, OracleTypes.NVARCHAR);
						call.setString(2, heat_id);
						call.setInt(3, sub_unit_id);
						call.setInt(4, counter);

						call.execute();
						String result = call.getString(1);
						return result;
					} finally {
						call.close();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getHMDetailsbyInterface........" + e);

		} finally {
			close(session);
		}
		return Double.parseDouble(res);
	}

	@Override
	public HeatProcessParameterDetails getProcParamDtlsByParmaID(Integer param_id, String heat_id, Integer heat_cnt) {
		// TODO Auto-generated method stub
		logger.info("inside .. getProcParamDtlsById.....");
		HeatProcessParameterDetails list = new HeatProcessParameterDetails();
		Session session = getNewSession();
		try {
			String hql = "select a from HeatProcessParameterDetails a where a.param_id=" + param_id + " and a.heat_id='"
					+ heat_id + "' and a.heat_counter=" + heat_cnt;
			list = (HeatProcessParameterDetails) session.createQuery(hql).uniqueResult();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getProcParamDtlsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HeatChemistryChildDetails> getHeatChemistryDtls(String heat_id, Integer heat_counter,
			Integer sub_unit_id, Integer hm_analysis_id, Integer result_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatChemistryDtls....." + HeatProceeEventDaoImpl.class);
		List<HeatChemistryChildDetails> list = new ArrayList<HeatChemistryChildDetails>();

		Session session = getNewSession();
		try {

			String hql = "select a from HeatChemistryChildDetails a where a.sample_si_no =(select max(b.sample_si_no) from HeatChemistryHdrDetails b where b.heat_id='"
					+ heat_id + "' " + "and b.heat_counter=" + heat_counter + " and b.analysis_type=" + hm_analysis_id
					+ " and b.sub_unit_id=" + sub_unit_id + " and b.final_result=" + result_id + ")";

			list = (List<HeatChemistryChildDetails>) session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEofHeatDtlsById........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return list;
	}
	
	@Override
	public String getSampleNo(String heat_id, Integer heat_counter, Integer sub_unit_id, Integer analysis_type) {
		// TODO Auto-generated method stub
		logger.info("#inside .. getSampleNo.....");
		Session session = null;
		String res = null,sample_id="";
		try {
			session = getNewSession();

			res = session.doReturningWork(new ReturningWork<String>() {
				public String execute(Connection connection) throws SQLException {
					CallableStatement call = null;
					try {
						call = connection.prepareCall("{ ? = call get_sample_no_fn(?,?,?,?)}");						
						call.registerOutParameter(1, OracleTypes.VARCHAR);
						call.setString(2,heat_id);
						call.setInt(3,heat_counter);
						call.setInt(4,sub_unit_id);
						call.setInt(5,analysis_type);						
						call.execute();
						String result = call.getString(1);
						return result;
					} finally {
						call.close();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getSampleNo........" + e);

		} finally {
			close(session);
		}
		return res.toString();
	}
	@SuppressWarnings("unchecked")
	@Override
	public VDHeatDetailsModel getVdHeatDtlsById(Integer trns_sno) {
		// TODO Auto-generated method stub
		logger.info("inside .. getVdHeatDtlsById....." + HeatProceeEventDaoImpl.class);
		List<VDHeatDetailsModel> list = new ArrayList<VDHeatDetailsModel>();
		VDHeatDetailsModel vdObj = new VDHeatDetailsModel();
		Session session = getNewSession();
		try {

			String hql = "select a from VDHeatDetailsModel a where a.trns_si_no =" + trns_sno + "";
			list = (List<VDHeatDetailsModel>) session.createQuery(hql).list();
			vdObj = list.get(0);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getVdHeatDtlsById........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return vdObj;
	}

	
	@Override
	public LRFHeatDetailsModel getLRFHeatDtlsById(Integer trns_sno) {
		logger.info("inside .. getLRFHeatDtlsById....." + HeatProceeEventDaoImpl.class);
		LRFHeatDetailsModel lrfObj  = new LRFHeatDetailsModel();
		Session session = null;
		
		try {
			session = getNewSession();
			String hql = " from LRFHeatDetailsModel  where trns_sl_no='"+trns_sno+"'";
			lrfObj=(LRFHeatDetailsModel) session.createQuery(hql).list().get(0);
			} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getLRFHeatDtlsById........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return lrfObj;
	}
	@Override
	public CCMHeatDetailsModel getCCMHeatDtlsById(Integer trns_sno) {
		logger.info("inside .. getCCMHeatDtlsById....." + HeatProceeEventDaoImpl.class);
		CCMHeatDetailsModel ccmObj  = new CCMHeatDetailsModel();
		Session session = null;
		
		try {
			session = getNewSession();
			String hql = " from CCMHeatDetailsModel  where trns_sl_no='"+trns_sno+"'";
			ccmObj=(CCMHeatDetailsModel) session.createQuery(hql).list().get(0);
			} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getCCMHeatDtlsById........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return ccmObj;
	}
	
	
	
	
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsBySampleHdrId(
			Integer sample_si_id, Integer psn_id, Integer analysis_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getChemDtlsBySampleHdrId....." + HeatProceeEventDaoImpl.class);
		List<HeatChemistryChildDetails> chem_list = new ArrayList<HeatChemistryChildDetails>();
		List<HeatChemistryChildDetails> psn_li = getChemDtlsByAnalysis(analysis_id, psn_id);
		Session session = getNewSession();
		try {
			 chem_list = new ArrayList<HeatChemistryChildDetails>();
			String hql = "select a from HeatChemistryChildDetails a where a.sample_si_no = "+sample_si_id;
			chem_list = (List<HeatChemistryChildDetails>) session.createQuery(hql).list();
			
			for (HeatChemistryChildDetails data : psn_li){
				for (HeatChemistryChildDetails c_obj : chem_list){
					if (data.getElement().equals(c_obj.getElement())&& data.getAim_value() == null ) {
						//data.setMin_value(c_obj.getMin_value());
						//data.setMax_value(c_obj.getMax_value());
						data.setDtls_si_no(c_obj.getDtls_si_no());
						data.setAim_value(c_obj.getAim_value());
						//break;
					}
				}
			};
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getChemDtlsBySampleHdrId........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return psn_li;
	}
	
	@Override
	public String saveFinalResultiface(HeatChemistryHdrDetails chemHdrObj,IfacesmsLpDetailsModel ifacObj) {
		// TODO Auto-generated method stub   ------------------,IfacesmsLpDetailsModel ifacObj
				logger.info("inside .. saveFinalResult....."+HeatProceeEventDaoImpl.class);
				String result = "";
				Session session=getNewSession();
				try{
					begin(session);	
					session.update(chemHdrObj);
					session.save(ifacObj);
					commit(session);
					//session.flush();
					result =Constants.SAVE;
				}catch (Exception e) {
					e.printStackTrace();
					rollback(session);
					logger.error(HeatProceeEventDaoImpl.class+" Inside saveFinalResult Exception..", e);
					result = Constants.UPDATE_FAIL;
				}finally {
					close(session);
				}

				return result;
	}
	
	@Override
	public String saveFinalResult(HeatChemistryHdrDetails chemHdrObj) {
		// TODO Auto-generated method stub   ------------------,IfacesmsLpDetailsModel ifacObj
				logger.info("inside .. saveFinalResult....."+HeatProceeEventDaoImpl.class);
				String result = "";
				Session session=getNewSession();
				try{
					begin(session);	
					session.update(chemHdrObj);
					commit(session);
					//session.flush();
					result =Constants.SAVE;
				}catch (Exception e) {
					e.printStackTrace();
					rollback(session);
					logger.error(HeatProceeEventDaoImpl.class+" Inside saveFinalResult Exception..", e);
					result = Constants.UPDATE_FAIL;
				}finally {
					close(session);
				}

				return result;
	}
	
	@Override
	public String approvechem(HeatChemistryHdrDetails chemHdrObj) {
		        logger.info("inside .. approvechem....."+HeatProceeEventDaoImpl.class);
				String result = "";
				Session session=getNewSession();
				try{
					begin(session);	
					session.update(chemHdrObj);
					commit(session);
					result =Constants.SAVE;
				}catch (Exception e) {
					e.printStackTrace();
					rollback(session);
					logger.error(HeatProceeEventDaoImpl.class+" Inside saveFinalResult Exception..", e);
					result = Constants.UPDATE_FAIL;
				}finally {
					close(session);
				}

				return result;
	}

	@Override
	public List<HeatChemistryChildDetails> getChemDtlsBySampleHdr(
			Integer sample_si_Id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getChemDtlsBySampleHdr....." + HeatProceeEventDaoImpl.class);
		List<HeatChemistryChildDetails> chem_list = new ArrayList<HeatChemistryChildDetails>();
		Session session = getNewSession();
		try {
			 String hql = "select a from HeatChemistryChildDetails a where a.sample_si_no = "+sample_si_Id;
			 chem_list = (List<HeatChemistryChildDetails>) session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getChemDtlsBySampleHdr........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return chem_list;
	}

	@Override
	public List<HeatProcessEventDetails> getLrfHeatProcessEventDtls(
			String heat_id, Integer heat_cnt, Integer sub_unit_id,
			String prev_unit, String psn_route) {
		logger.info(HeatProceeEventDaoImpl.class + "inside .. getLrfHeatProcessEventDtls.....");
		
		String p_unit = prev_unit.substring(0, prev_unit.length()-1);
		Session session = getNewSession();
		String hql = "",hql_event="", bvd_hql, bvd_hql_1="";
		List<HeatProcessEventDetails> heatProcessEventDtls = new ArrayList<HeatProcessEventDetails>();
		List<HeatProcessEventDetails> finalLi = new ArrayList<HeatProcessEventDetails>();
		List<HeatProcessEventDetails> EventMstrDtls = new ArrayList<HeatProcessEventDetails>();
		List<EventMasterModel> bvd_eventLi = new ArrayList<EventMasterModel>();
		List<EventMasterModel> avd_eventLi = new ArrayList<EventMasterModel>();
		HeatProcessEventDetails obj;
		int eventCount = 0; 
		String disp_event = null;
		try {
			String nextUnit;
			String subStr = psn_route.substring(psn_route.indexOf(Constants.SUB_UNIT_LRF)+Constants.SUB_UNIT_LRF.length()+1);

			if(subStr.indexOf("-") != -1) {
				nextUnit = subStr.substring(0, subStr.indexOf("-"));
			}else {
				nextUnit = subStr;
			}
			
			if(p_unit.substring(0, 2).equalsIgnoreCase(Constants.PREV_UNIT_VD) || nextUnit.equalsIgnoreCase(Constants.SUB_UNIT_VD))
				disp_event = Constants.HEAT_PROCESS_EVENT_VD_DISP;
			else if(nextUnit.equalsIgnoreCase(Constants.SUB_UNIT_CCM))
				disp_event = Constants.HEAT_PROCESS_EVENT_CCM_DISP;
			
			hql_event = "select b from EventMasterModel b where b.sub_unit_id ="+sub_unit_id;	
			EventMstrDtls = (List<HeatProcessEventDetails>) session.createQuery(hql_event).list();
			
			bvd_hql_1 = " or (b.event_desc='"+disp_event+"' and b.sub_unit_id ="+ sub_unit_id + ") ";
			
			if(p_unit.substring(0, 2).equalsIgnoreCase(Constants.PREV_UNIT_VD))
				hql = "select a from HeatProcessEventDetails a where a.heat_id = '"+heat_id+"' and a.eventMstrMdl.subUnitMstrModel.sub_unit_name like 'LRF%' order by eventMstrMdl.event_unit_seq";
			else
				hql = "select a from HeatProcessEventDetails a where a.heat_id = '"+heat_id+"' and a.heat_counter="+heat_cnt+" and a.eventMstrMdl.subUnitMstrModel.sub_unit_name like 'LRF%' order by eventMstrMdl.event_unit_seq";
			heatProcessEventDtls = (List<HeatProcessEventDetails>) session.createQuery(hql).list();
			
			bvd_hql = "select b from EventMasterModel b where b.sub_unit_id ="+ sub_unit_id + " and b.event_unit_seq <"
					+ " (select event_unit_seq from EventMasterModel where event_desc='"+Constants.HEAT_PROCESS_EVENT_VD_DISP
					+ "' and sub_unit_id="+sub_unit_id+")"+bvd_hql_1+" and b.recordStatus=1 order by b.event_unit_seq";
			bvd_eventLi = (List<EventMasterModel>) session.createQuery(bvd_hql).list();
			
			if(heatProcessEventDtls.size() > 0){
				for(EventMasterModel evtObj : bvd_eventLi){
					obj = new HeatProcessEventDetails();
					for (HeatProcessEventDetails heatProcessEvntObj : heatProcessEventDtls){
						if(heatProcessEvntObj.getEvent_id() == evtObj.getEvent_si_no()){
							obj.setHeat_proc_event_id(heatProcessEvntObj.getHeat_proc_event_id());
							obj.setEvent_date_time(heatProcessEvntObj.getEvent_date_time());
							break;
						}
					}
					obj.setEvent_id(evtObj.getEvent_si_no());
					obj.setEventName(evtObj.getEvent_desc());
					obj.setEvent_unit_seq(evtObj.getEvent_unit_seq());
					obj.setEventMstrMdl(evtObj);
					
					finalLi.add(obj);
					//if(heatProcessEvntObj.getEventMstrMdl().getEvent_desc().equals(Constants.HEAT_PROCESS_EVENT_VD_DISP))
						eventCount = finalLi.size();
				}
				if(p_unit.substring(0, 2).equalsIgnoreCase(Constants.PREV_UNIT_VD) && EventMstrDtls.size()-1 > finalLi.size()){
					hql = "select b from EventMasterModel b where b.sub_unit_id ="+ sub_unit_id + " and b.event_unit_seq >"
							+ " (select event_unit_seq from EventMasterModel where event_desc='"+Constants.HEAT_PROCESS_EVENT_VD_DISP
							+ "' and sub_unit_id="+sub_unit_id+") and b.recordStatus=1 order by b.event_unit_seq";
					avd_eventLi = (List<EventMasterModel>) session.createQuery(hql).list();
					for (EventMasterModel eventObj : avd_eventLi){
						obj = new HeatProcessEventDetails();
						for (HeatProcessEventDetails heatProcessEvntObj : heatProcessEventDtls){
							if(heatProcessEvntObj.getEvent_id() == eventObj.getEvent_si_no()){
								obj.setHeat_proc_event_id(heatProcessEvntObj.getHeat_proc_event_id());
								obj.setEvent_date_time(heatProcessEvntObj.getEvent_date_time());
								break;
							}
						}
						obj.setEvent_id(eventObj.getEvent_si_no());
						obj.setEventName(eventObj.getEvent_desc());
						obj.setEvent_unit_seq(eventObj.getEvent_unit_seq());
						obj.setEventMstrMdl(eventObj);
						finalLi.add(obj);
					}
				}
			}else{
				for (EventMasterModel eventObj : bvd_eventLi){
					obj = new HeatProcessEventDetails();
					obj.setHeat_proc_event_id(null);
					obj.setEvent_date_time(null);
					obj.setEvent_id(eventObj.getEvent_si_no());
					obj.setEventName(eventObj.getEvent_desc());
					obj.setEvent_unit_seq(eventObj.getEvent_unit_seq());
					obj.setEventMstrMdl(eventObj);
				
					finalLi.add(obj);
				}
			}
		} catch (Exception e) {
			logger.error(HeatProceeEventDaoImpl.class + " Exception.. getLrfHeatProcessEventDtls..." + e.getMessage());
			e.printStackTrace();
		} finally {
			close(session);
		}

		return finalLi;
	
	}

	@Override
	public List<HeatChemistryHdrDetails> getSampleDtlsByAnalysisType(
			Integer sub_unit_id, String heat_id, Integer heat_counter,
			String analysis_type) {
		// TODO Auto-generated method stub
		logger.info("inside .. getSampleDtlsByAnalysisType.....");
		List<HeatChemistryHdrDetails> list = new ArrayList<HeatChemistryHdrDetails>();
		Session session=getNewSession(); 
		try { 
			String hql = "Select a from HeatChemistryHdrDetails a where a.sub_unit_id = "+sub_unit_id+" and a.heat_id = '"+heat_id+"' "
							+ " and a.heat_counter = "+heat_counter+" and a.analysisLookupDtls.lookup_value = '"+analysis_type+"'";
			list = (List<HeatChemistryHdrDetails>) session.createQuery(hql).list();
		} catch (Exception e) { 
			logger.error("error in getSampleDtlsByAnalysisType........"+e); 
		} 
		finally{
			close(session); 
		} 
		
		return list;
	}
	
	@Transactional
	@Override
	public HeatChemistryHdrDetails getChemHdrDtls(String sampleNo) {
		// TODO Auto-generated method stub
		logger.info("inside .. getChemHdrDtls.....");
		HeatChemistryHdrDetails list = new HeatChemistryHdrDetails();
		Session session=getNewSession(); 
		try { 
			String hql = "Select a from HeatChemistryHdrDetails a where a.sample_no = '"+sampleNo+"'";
							
			list =  (HeatChemistryHdrDetails) session.createQuery(hql).uniqueResult();
		} catch (Exception e) { 
			logger.error("error in getSampleDtlsByAnalysisType........"+e); 
		} 
		finally{
			close(session); 
		} 
		
		return list;
	}
	
	@Transactional
	public String stringToDate(String dateValue) throws ParseException {
		String souredate=dateValue.replaceAll("-", "/");
		Date  sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(souredate);//  04/02/2011 20:27:05
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	    	String strDate= formatter.format(sdf);
	    	 return strDate;
	}
	
	@Transactional
	public Integer checkProdConf(String heatno) {
		int ack=0;
		Session session=getNewSession(); 
		try { 
			String qry = "select COUNT(*) from INTF_SALEM_LOG ilog  where ilog.HEAT_ID='"+heatno+"' AND "
					+ "ilog.STATUS='SUCCESS' AND ilog.ERROR_MSG='OK' AND ilog.LOG_TYPE IN ('PROD_CONF','PROD_CONSM')";			
			ack=Integer.parseInt( session.createSQLQuery(qry).list().get(0).toString());
			
			
		} catch (Exception e) { 
			System.out.println("error in checkProdConf........"+e); 
		} 
		finally{
			close(session); 
		} 
		return ack;
		
	}

	@Override
	public List<HeatConsMaterialsDetails> getHeatConsMtrlsDtlsByTrnsId(Integer trns_id) {
		logger.info("inside .. getHeatConsMtrlsDtlsByTrnsId.....");
		List<HeatConsMaterialsDetails> list = new ArrayList<HeatConsMaterialsDetails>();
		Session session = getNewSession(); 
		String hql = "Select a from HeatConsMaterialsDetails a where a.trns_eof_si_no = "+trns_id;
		try { 
			list = (List<HeatConsMaterialsDetails>) session.createQuery(hql).list();
		 } catch (Exception e) { 
			logger.error("error in getHeatConsMtrlsDtlsByTrnsId........"+e); 
		} 
		finally{
		 	close(session); 
		} 
		return list;
	}

	@Override
	public List<CCMHeatConsMaterialsDetails> getMtrlDetailsByCCMType(String mtrlType, Integer eof_trns_sno, String psn_no) {
		// TODO Auto-generated method stub
		logger.info("inside getMtrlDetailsByCCMType" + HeatProceeEventDaoImpl.class);
	    CCMHeatDetailsModel ccmHeatDetails= getCCMHeatDtlsById(eof_trns_sno);
		Session session = getNewSession();
		String hql = "";
		List<CCMHeatConsMaterialsDetails> heatConsMtrlDtls = new ArrayList<CCMHeatConsMaterialsDetails>();
		CCMHeatConsMaterialsDetails obj;
		int flag=0;
		try {
			if(psn_no.equals("NA")){
				
				hql="SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty || '&&' || TO_CHAR (a.consumption_date, 'DD/MM/YYYY HH:MI:SS')|| '&&'|| a.record_version || '&&' || nvl(a.sap_matl_id,'NA') || '&&' || nvl(a.valuation_type,'NA') FROM trns_ccm_heat_cons_materials a WHERE a.material_id = c.material_id AND a.trns_ccm_si_no = "
				+ eof_trns_sno+ "), c.material_id, c.material_desc, d.lookup_value, c.order_seq,nvl(c.sap_mtl_desc,'NA'),nvl(g.lookup_value,'NA') valuation_type FROM mstr_process_consumables c, app_lookups d, app_lookups e, app_lookups g WHERE c.uom = d.lookup_id  AND c.record_status = 1  AND c.material_type = e.lookup_id and g.lookup_id(+) = c.valuation_type AND g.lookup_type(+) = 'MATL_VALUATION_TYPE' "
				+" AND e.lookup_code = '"+mtrlType+"'";
				flag=1;
			}else{
				flag=2;
				hql = "SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty|| '&&' || to_char(a.consumption_date,'DD/MM/YYYY HH:MI:SS') || '&&' ||a.record_version || '&&' || nvl(a.sap_matl_id,'NA') || '&&' || nvl(a.valuation_type,'NA')  FROM TRNS_CCM_HEAT_CONS_MATERIALS a WHERE a.material_id = c.material_id "
						+ "AND a.trns_ccm_si_no = " + eof_trns_sno + "),f.record_status, c.material_id, "
						+ "c.material_desc, d.lookup_value,f.value_min,f.value_max,f.value_aim,c.order_seq, nvl(c.sap_mtl_desc,'NA'), nvl(g.lookup_value,'NA') valuation_type  FROM MSTR_PROCESS_CONSUMABLES c, "
						+ "APP_LOOKUPS d, APP_LOOKUPS e,MSTR_PSN_ADDITIONS f,APP_LOOKUPS g WHERE c.uom = d.lookup_id AND c.record_status = 1 AND c.HISTORIAN_MTL_DESC='Heat' AND g.lookup_id(+) = c.valuation_type AND g.lookup_type(+) = 'MATL_VALUATION_TYPE' AND "
						+ "c.material_type = e.lookup_id AND e.lookup_code = '" + mtrlType 
						+ "' and c.material_desc <> 'CASTING POWDER' and c.material_id=f.mat_id and f.sub_unit_id=" + ccmHeatDetails.getSub_unit_id()
						+" and f.psn_hdr_sl_no =(select d.psn_hdr_sl_no from MSTR_PSN_HDR d where d.psn_no='"+psn_no+"' and  PSN_STATUS='APPROVED')"
						+ " union "
						+" SELECT (SELECT a.mtr_cons_si_no || '&&' || a.qty|| '&&' || to_char(a.consumption_date,'DD/MM/YYYY HH:MI:SS') || '&&' ||a.record_version || '&&' || nvl(a.sap_matl_id,'NA') || '&&' || nvl(a.valuation_type,'NA')  FROM TRNS_CCM_HEAT_CONS_MATERIALS a WHERE a.material_id = c.material_id "
						+ "AND a.trns_ccm_si_no = " + eof_trns_sno + "),f.record_status, c.material_id, "
						+ "c.material_desc, d.lookup_value,f.VALUE_MIN,f.VALUE_Max,f.VALUE_aim,c.order_seq, nvl(c.sap_mtl_desc,'NA'), nvl(g.lookup_value,'NA') valuation_type  FROM MSTR_PROCESS_CONSUMABLES c, "
						+ "APP_LOOKUPS d, APP_LOOKUPS e,MSTR_MTRL_UNIT_MAP f,APP_LOOKUPS g WHERE c.uom = d.lookup_id AND c.record_status = 1 AND c.HISTORIAN_MTL_DESC='Heat' AND g.lookup_id(+) = c.valuation_type AND g.lookup_type(+) = 'MATL_VALUATION_TYPE' AND "
						+ "c.material_type = e.lookup_id AND e.lookup_code = '" + mtrlType
						+ "'and c.material_desc <> 'CASTING POWDER' and c.material_id=f.mtrl_id and f.sub_unit_id=" +  ccmHeatDetails.getSub_unit_id()
						+" and f.mtrl_id not in (select ff.mat_id FROM MSTR_PROCESS_CONSUMABLES cc, "
						+ "APP_LOOKUPS dd, APP_LOOKUPS ee,MSTR_PSN_ADDITIONS ff WHERE cc.uom = dd.lookup_id AND cc.record_status = 1 AND "
						+ "c.material_type = e.lookup_id AND e.lookup_code = '" + mtrlType
						+ "' and cc.material_id=ff.mat_id and ff.sub_unit_id=" +  ccmHeatDetails.getSub_unit_id()
						+" and ff.psn_hdr_sl_no =(select ddd.psn_hdr_sl_no from MSTR_PSN_HDR ddd where ddd.psn_no='"+psn_no+"' and PSN_STATUS='APPROVED'))"
						+ " ORDER BY order_seq";
			}
		
			List ls = (List<CCMHeatConsMaterialsDetails>) getResultFromCustomQuery(hql);
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// Convert from String to Date
			if(flag==2) {
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new CCMHeatConsMaterialsDetails();
				String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));
				
				if (s != null) {
					obj.setMtr_cons_si_no((null == s[0]) ? null : Integer.parseInt(s[0].toString()));
					obj.setQty((null == s[1]) ? null : Double.parseDouble(s[1].toString()));
					obj.setConsumption_date(df.parse(s[2].toString()));
					obj.setVersion((null == s[3]) ? 0 : Integer.parseInt(s[3].toString()));
					obj.setSap_matl_id(("NA" == s[4] || null == s[4]) ? null : (s[4].toString()));
					obj.setValuation_type(("NA" == s[5] || null == s[5]) ? null : (s[5].toString()));
				} else {
					
					obj.setMtr_cons_si_no(null);
					obj.setQty(null);
					obj.setConsumption_date(new Date());
					obj.setVersion(0);
					obj.setSap_matl_id((null == rows[9] || "NA" == rows[9]) ? null : rows[9].toString());
					obj.setValuation_type((null == rows[10]  || "NA" == rows[10]) ? null : rows[10].toString());
				}
				
				obj.setRecord_status((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));
				obj.setMaterial_id((null == rows[2]) ? null : Integer.parseInt(rows[2].toString()));
				obj.setMtrlName((null == rows[3]) ? null : rows[3].toString());
				obj.setUom((null == rows[4]) ? null : rows[4].toString());
				if(!psn_no.equals("NA")){
				obj.setVal_min((null == rows[5]) ? null : Double.parseDouble(rows[5].toString()));
				obj.setVal_max((null == rows[6]) ? null : Double.parseDouble(rows[6].toString()));
				obj.setVal_aim((null == rows[7]) ? null : Double.parseDouble(rows[7].toString()));
			     }
	
				heatConsMtrlDtls.add(obj);
			}
			}else if(flag==1){
				heatConsMtrlDtls=getDetailsFilledByPSN_NA(ls);
			}
			
			//heatConsMtrlDtls=ls;

		} catch (Exception e) {
			logger.error("Exception.. getMtrlDetailsByType..." + e.getMessage());
			e.printStackTrace();
		} finally {
			close(session);
		}

		return heatConsMtrlDtls;
	}
		



	@Override
	public CCMHeatDetailsModel getCCMHeatDtlsFormById(Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		return null;
	}
}
