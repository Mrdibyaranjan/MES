package com.smes.trans.dao.impl;


import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.admin.model.SeqIdDetails;
import com.smes.masters.model.MtrlProcessConsumableMstrModel;
import com.smes.reports.model.GraphReportModel;
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
import com.smes.reports.model.EOFHeatLogRpt;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.DelayEntryDTO;
import com.smes.util.GenericClass;
import com.smes.util.GenericDaoImpl;
import com.smes.util.ValidationResultsModel;

@Repository("eofProductionDao")
public class EofProductionDaoImpl extends GenericDaoImpl<EofHeatDetails, Long> implements EofProductionDao {

	private static final Logger logger = Logger.getLogger(EofProductionDaoImpl.class);
	
	@Override
	public String eofHeatProductionSave(EofHeatDetails eofHeatDetails) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofHeatProductionSave ");
			create(eofHeatDetails);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;
		
	}

	@Override
	public String eofHeatProductionUpdate(EofHeatDetails eofHeatDetails) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofHeatProductionUpdate ");
			update(eofHeatDetails);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		}

		return result;
	}
	
  @Override
	public String eofreceivehmStatusUpdate(Long hmRecvID) {
		// TODO Auto-generated method stub
		String result = Constants.DELETE;
		Session session=getNewSession();
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofreceivehmStatusUpdate ");
			String hql = "update HmReceiveBaseDetails set hmLadleStatus=(select a.lookup_id from LookupMasterModel a where a.lookup_type='HM_LADLE_STATUS' and a.lookup_code='DELETED')"
					+ ", hmLadleStageStatus=(select a.lookup_id from LookupMasterModel a where a.lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and a.lookup_code='DELETED') where hmRecvId="+hmRecvID;
			
			Query query=session.createQuery(hql);
			int rows=query.executeUpdate();
		}catch(org.hibernate.StaleObjectStateException s)
		{
			logger.error(EofProductionDaoImpl.class+" Inside eofreceivehmStatusUpdate Exception..", s);
			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;
		}
		catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofreceivehmStatusUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		}

		return result;
	}

	@Override
	public String eofCrewDetailsSave(List<EOFCrewDetails> eofCrewDetails) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		Session session=getNewSession();
		try {
			begin(session);
			logger.info(EofProductionDaoImpl.class+" Inside eofCrewDetailsSave ");
			for(EOFCrewDetails eof:eofCrewDetails)
			{
				
				session.save(eof);
			}
			commit(session);

		} catch (Exception e) {
			rollback(session);
			logger.error(EofProductionDaoImpl.class+" Inside eofCrewDetailsSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}
		finally{
			close(session);
		}

		return result;
	}

	@Override
	public String eofCrewDetailsUpdate(EOFCrewDetails eofCrewDetails) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
		Session session=getNewSession();
		try {
			logger.info(EofProductionDaoImpl.class+" Inside eofCrewDetailsUpdate ");
			begin(session);
			session.update(eofCrewDetails);
			commit(session);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofCrewDetailsUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		}
		finally{
			close(session);
		}
		return result;
	}

	@Override
	public List<EOFCrewDetails> getCrewDetailsByHeatNo(String heat_no,Integer unit_id,Integer heat_counter) {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside getCrewDetailsByHeatNo ");
		List<EOFCrewDetails> list=new ArrayList<EOFCrewDetails>();
		Session session=getNewSession();
		try {

			String hql = "select a from EOFCrewDetails a where a.heat_id ='"+heat_no+"' and a.heat_counter="+heat_counter+" and a.sub_unit_id="+unit_id;
			
			list=(List<EOFCrewDetails>) session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		return list;
	}

	@Override
	public String eofHeatProductionSaveAll(Hashtable<String, Object> mod_obj) {	
		String result = "";
		Session session=getNewSession();
		try{
			begin(session);
			
			if((EofHeatDetails)mod_obj.get("EOF")!=null){
				session.save((EofHeatDetails)mod_obj.get("EOF"));
			}
			
			if((SeqIdDetails)mod_obj.get("UPD_HEAT_NO_GEN_SEQ")!=null){
				session.update((SeqIdDetails)mod_obj.get("UPD_HEAT_NO_GEN_SEQ"));
			}
				
			if((HeatProcessEventDetails)mod_obj.get("EOF_HM_CHRG")!=null){
				session.save((HeatProcessEventDetails)mod_obj.get("EOF_HM_CHRG"));
			}
				
			if((HeatProcessEventDetails)mod_obj.get("EOF_SCRAP_CHRG")!=null){
				session.save((HeatProcessEventDetails)mod_obj.get("EOF_SCRAP_CHRG"));
			}
				
			if((HeatProcessEventDetails)mod_obj.get("EOF_TAP_START")!=null){
				session.save((HeatProcessEventDetails)mod_obj.get("EOF_TAP_START"));
			}
				
			if((HeatProcessEventDetails)mod_obj.get("EOF_TAP_END")!=null){
				session.save((HeatProcessEventDetails)mod_obj.get("EOF_TAP_END"));
			}
				
			if((HmReceiveBaseDetails)mod_obj.get("HM_UPDATE")!=null){
				session.update((HmReceiveBaseDetails)mod_obj.get("HM_UPDATE"));
			}
				
			if((HeatConsScrapMtrlDetails)mod_obj.get("HM_CONS")!=null){
				HeatConsScrapMtrlDetails heat_det=(HeatConsScrapMtrlDetails)mod_obj.get("HM_CONS");
				EofHeatDetails eof=(EofHeatDetails)mod_obj.get("EOF");
				heat_det.setTrns_eof_si_no(eof.getTrns_si_no());
			    session.save(heat_det);
			}
				
			if((HeatPlanHdrDetails)mod_obj.get("HEAT_HDR_UPDATE")!=null){
				session.update((HeatPlanHdrDetails)mod_obj.get("HEAT_HDR_UPDATE"));
			}
				
			if((HeatPlanDetails)mod_obj.get("HEAT_LINE_UPDATE")!=null){
				session.update((HeatPlanDetails)mod_obj.get("HEAT_LINE_UPDATE"));
			}			
				
			if(mod_obj.get("EVENTS_CNT").toString()!=null && Integer.parseInt(mod_obj.get("EVENTS_CNT").toString())>0){				
				Integer cnt=Integer.parseInt(mod_obj.get("EVENTS_CNT").toString());
				String key="EVENTS_UPDATE";
				for(int i=0;i<=cnt;i++){
					key=key+i;
					if((HeatProcessEventDetails)mod_obj.get(key)!=null){
						HeatProcessEventDetails scrap_det=(HeatProcessEventDetails)mod_obj.get(key);
						if(scrap_det.getHeat_proc_event_id()!=null){
							session.update(scrap_det);
						}else{
							session.save(scrap_det);
						}
						scrap_det=null;
					}
				}
			}
				
			if((HeatStatusTrackingModel)mod_obj.get("HEAT_TRACK_STATUS_INSERT")!=null){
				session.save((HeatStatusTrackingModel)mod_obj.get("HEAT_TRACK_STATUS_INSERT"));
			}
			if((IfacesmsLpDetailsModel)mod_obj.get("IFACE_SMS_LP_TB_INSERT")!=null){
				session.save((IfacesmsLpDetailsModel)mod_obj.get("IFACE_SMS_LP_TB_INSERT"));
			}
				
			commit(session);
			result =Constants.SAVE;
		} 
		catch(org.hibernate.StaleObjectStateException s){
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside hotMetalReceiveDetailsUpdate Exception..", s);
			
			if(s.getEntityName().contains("HmReceiveBaseDetails")){
				result ="<b>Ladle --> "+((HmReceiveBaseDetails)mod_obj.get("HM_UPDATE")).getHmLadleNo()+"</b> The Selected item has already been updated by another user. Please get the updated values";
			}else if(s.getEntityName().contains("HeatPlanHdrDetails")){
				result ="<b>PlanHeader--> "+((HeatPlanHdrDetails)mod_obj.get("HEAT_HDR_UPDATE")).getHeat_plan_id()+"</b> The Selected item has already been updated by another user. Please get the updated values";
			}else if(s.getEntityName().contains("HeatPlanDetails")){
				result ="<b>PlanLineNo --> "+((HeatPlanDetails)mod_obj.get("HEAT_LINE_UPDATE")).getIndent_no()+" </b> The Selected item has already been updated by another user. Please get the updated values";
			}else{
				result=Constants.CONCURRENT_UPDATE_MSG_FAIL;
			}
			rollback(session);
		} 
		catch(DataIntegrityViolationException e){
			logger.error(EofProductionDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			e.printStackTrace();
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch(ConstraintViolationException e){
			logger.error(EofProductionDaoImpl.class+" Inside ConstraintViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch (Exception e) {
			rollback(session);
			logger.error(EofProductionDaoImpl.class+" Inside hotMetalReceiveDetailsSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;			
	}


	@SuppressWarnings("rawtypes")
	@Override
	public List<EofHeatDetails> getLRFHeatDetailsByStatus(String cunit, String pstatus) {
		// TODO Auto-generated method stub

		logger.info("inside .. Eof ProductionDaoimpl----getLRFHeatDetailsByStatus() .");
		List<EofHeatDetails> list=new ArrayList<EofHeatDetails>();
		EofHeatDetails obj;
		try {
			
//			String hql = "select (select steel_ladle_no from  SteelLadleMasterModel where steel_ladle_si_no=a.steel_ladle_no) ,a.tap_temp,a.heat_id, (select psn_no from PSNHdrMasterModel where psn_hdr_sl_no = a.aim_psn),a.tap_wt,a.heat_plan_id,a.heat_plan_line_id,"
//					+ "(select lm.lookup_value from LookupMasterModel lm where lm.lookup_type='CASTER_TYPE' and lm.lookup_id=d.caster_type) as casterType,(select sub_unit_name from  SubUnitMasterModel sud  where sud.sub_unit_id = a.sub_unit_id),a.heat_counter,d.caster_type as caster_id,a.sub_unit_id as subUnitId,b.act_proc_path,b.heat_track_id,a.aim_psn,a.steel_ladle_no from EofHeatDetails a ,HeatStatusTrackingModel b,HeatPlanLinesDetails c,HeatPlanHdrDetails d where"
//						+" a.heat_id =b.heat_id and a.heat_counter=b.heat_counter and b.current_unit='"+cunit
//						+ "' and b.unit_process_status='"+pstatus+"' and a.heat_plan_line_id=c.heat_line_id and d.heat_plan_id=c.heat_plan_id "
//						+ " union "
//						+ "select a.heat_id AS heatid, (select steel_ladle_no from  SteelLadleMasterModel where steel_ladle_si_no=a.steel_ladle_no) ,a.lrf_dispatch_temp,a.steel_wgt,(select lm.lookup_value from LookupMasterModel lm where lm.lookup_type='CASTER_TYPE' and lm.lookup_id=d.caster_type) as casterType,"
//					+ "(select sub_unit_name from  SubUnitMasterModel sud  where sud.sub_unit_id = a.sub_unit_id), (select psn_no from PSNHdrMasterModel where psn_hdr_sl_no = a.aim_psn),a.heat_counter,b.act_proc_path,a.steel_ladle_no,a.aim_psn,b.heat_track_id,a.heat_plan_id,a.heat_plan_line_no,a.target_caster_id from  VDHeatDetailsModel a ,HeatStatusTrackingModel b,HeatPlanLinesDetails c,HeatPlanHdrDetails d where"
//						+" a.heat_id =b.heat_id and a.heat_counter=b.heat_counter and b.current_unit like 'LRF%' "
//						+ " and b.unit_process_status= 'WAITING FOR DISPATCH' and a.heat_plan_line_no=c.heat_line_id and d.heat_plan_id=c.heat_plan_id ";
			
			/*String sql=" select (select ss.steel_ladle_no  from  mstr_steel_ladle ss  where ss.steel_ladle_si_no = eof.steel_ladle_no) AS ladle_no, (select st.trns_stladle_track_id from trns_stladle_status_tracking st where st.steel_ladle_si_no = eof.steel_ladle_no) AS ladle_track_id,eof.tap_temp, eof.heat_id,(select psnhdrmast5_.psn_no  from mstr_psn_hdr psnhdrmast5_  where psnhdrmast5_.psn_hdr_sl_no = eof.aim_psn) AS psn_no,eof.tap_wt, eof.heat_plan_id, eof.heat_plan_line_id,(select al.lookup_value from app_lookups al  where al.lookup_type = 'CASTER_TYPE' and al.lookup_id = hph.caster_type) AS caster_type,"
					+" (select su.sub_unit_name  from mstr_sub_unit_details su  where su.sub_unit_id = eof.sub_unit_id) AS sub_unit,eof.heat_counter, hph.caster_type as caster_id, eof.sub_unit_id, hts.act_proc_path,hts.heat_tarck_id, eof.aim_psn,hts.unit_process_status,hts.current_unit,0 from trns_eof_heat_dtls eof,trns_heat_tracking_status hts,trns_heat_plan_lines hpl,"
					+ "trns_heat_plan_header hph where eof.heat_id = hts.heat_id and eof.heat_counter = hts.heat_counter and hts.current_unit = 'LRF' and hts.unit_process_status = 'WAITING FOR PROCESSING' and eof.heat_plan_line_id = hpl.heat_line_id and hph.heat_plan_id = hpl.heat_plan_id UNION"
					+ " select (select steel_ladle_no from mstr_steel_ladle where steel_ladle_si_no = a.steel_ladle_no) AS ladle_no, (select st.trns_stladle_track_id from trns_stladle_status_tracking st where st.steel_ladle_si_no = a.steel_ladle_no) AS ladle_track_id,a.dispatch_temp, a.heat_id, (select psn_no from mstr_psn_hdr  where psn_hdr_sl_no = a.aim_psn),a.steel_wt, a.heat_plan_id, a.heat_plan_line_id,(select lm.lookup_value from app_lookups lm where lm.lookup_type = 'CASTER_TYPE' and lm.lookup_id = d.caster_type) AS castertype,"
					+ " (select sub_unit_name  from mstr_sub_unit_details sud  where sud.sub_unit_id = a.sub_unit_id), a.heat_counter,d.caster_type AS caster_id, a.sub_unit_id AS subunitid,b.act_proc_path, b.heat_tarck_id, a.aim_psn, b.unit_process_status,b.current_unit,a.trns_si_no from trns_vd_heat_dtls a,trns_heat_tracking_status b,trns_heat_plan_lines c,       trns_heat_plan_header d where a.heat_id = b.heat_id and a.heat_counter = b.heat_counter"
					+ " and b.current_unit LIKE 'LRF%' and b.unit_process_status = 'WAITING FOR DISPATCH'  and a.heat_plan_line_id = c.heat_line_id and d.heat_plan_id = c.heat_plan_id";
			*/
			String sql=" select (select ss.steel_ladle_no  from  mstr_steel_ladle ss  where ss.steel_ladle_si_no = eof.steel_ladle_no) AS ladle_no, (select st.trns_stladle_track_id from trns_stladle_status_tracking st where st.steel_ladle_si_no = eof.steel_ladle_no) AS ladle_track_id,eof.tap_temp, eof.heat_id,(select psnhdrmast5_.psn_no  from mstr_psn_hdr psnhdrmast5_  where psnhdrmast5_.psn_hdr_sl_no = eof.aim_psn) AS psn_no,eof.tap_wt, eof.heat_plan_id, eof.heat_plan_line_id,(select al.lookup_value from app_lookups al  where al.lookup_type = 'CASTER_TYPE' and al.lookup_id = hph.caster_type) AS caster_type,"
					+" (select su.sub_unit_name  from mstr_sub_unit_details su  where su.sub_unit_id = eof.sub_unit_id) AS sub_unit,eof.heat_counter, hph.caster_type as caster_id, eof.sub_unit_id, hts.act_proc_path,hts.heat_tarck_id, eof.aim_psn,hts.unit_process_status,hts.current_unit, 0 as trns_si_no, 1 as lrf_entry,TO_CHAR (eof.DISPATCH_DATE , 'DD/MM/YYYY HH:MI:SS')from trns_eof_heat_dtls eof,trns_heat_tracking_status hts,trns_heat_plan_dtls hpl,"
					+" trns_heat_plan_header hph where eof.heat_id = hts.heat_id and eof.heat_counter = hts.heat_counter and hts.current_unit = 'LRF' and hts.unit_process_status = 'WAITING FOR PROCESSING' and eof.heat_plan_line_id = hpl.heat_plan_dtl_id  and hph.heat_plan_id = hpl.heat_plan_id UNION"
					+" select (select steel_ladle_no from mstr_steel_ladle where steel_ladle_si_no = a.steel_ladle_no) AS ladle_no, (select st.trns_stladle_track_id from trns_stladle_status_tracking st where st.steel_ladle_si_no = a.steel_ladle_no) AS ladle_track_id,a.dispatch_temp, a.heat_id, (select psn_no from mstr_psn_hdr  where psn_hdr_sl_no = a.aim_psn),a.steel_wt, a.heat_plan_id, a.heat_plan_line_id,(select lm.lookup_value from app_lookups lm where lm.lookup_type = 'CASTER_TYPE' and lm.lookup_id = d.caster_type) AS castertype,"
					+" (select sub_unit_name  from mstr_sub_unit_details sud  where sud.sub_unit_id = a.sub_unit_id), a.heat_counter,d.caster_type AS caster_id, a.sub_unit_id AS subunitid,b.act_proc_path, b.heat_tarck_id, a.aim_psn, b.unit_process_status,b.current_unit,a.trns_si_no, 1 as lrf_entry,TO_CHAR (a.DISPATCH_DATE , 'DD/MM/YYYY HH:MI:SS')  from trns_vd_heat_dtls a,trns_heat_tracking_status b,trns_heat_plan_dtls c,       trns_heat_plan_header d where a.heat_id = b.heat_id and a.heat_counter = b.heat_counter"
					+" and b.current_unit LIKE 'LRF%' and b.unit_process_status = 'WAITING FOR DISPATCH'  and a.heat_plan_line_id = c.heat_plan_dtl_id  and d.heat_plan_id = c.heat_plan_id  and b.lrf_status_af_vd is null"
					+" UNION select (select steel_ladle_no from mstr_steel_ladle where steel_ladle_si_no = a.steel_ladle_no) AS ladle_no, (select st.trns_stladle_track_id from trns_stladle_status_tracking st where st.steel_ladle_si_no = a.steel_ladle_no) AS ladle_track_id, a.dispatch_temp, a.heat_id, (select psn_no from mstr_psn_hdr  where psn_hdr_sl_no = a.aim_psn), a.balance_qty, a.heat_plan_id, a.heat_plan_line_no,"
					+" (select lm.lookup_value from app_lookups lm where lm.lookup_type = 'CASTER_TYPE' and lm.lookup_id = d.caster_type) AS castertype, (select sub_unit_name  from mstr_sub_unit_details sud  where sud.sub_unit_id = a.sub_unit_id), a.heat_counter, d.caster_type AS caster_id, a.sub_unit_id AS subunitid, b.act_proc_path, b.heat_tarck_id, a.aim_psn, b.unit_process_status,b.current_unit,a.trns_sl_no, "
					+" (select count(lh.trns_si_no) from trns_lrf_heat_dtls lh where lh.heat_id = a.heat_id and lh.heat_counter = a.heat_counter) as lrf_entry,TO_CHAR (a.UPDATED_DATE_TIME , 'DD/MM/YYYY HH:MI:SS') " 
					+" from trns_reladle_heat_dtls a, trns_heat_tracking_status b,trns_heat_plan_dtls c, trns_heat_plan_header d where a.heat_id = b.heat_id and a.heat_counter = b.heat_counter and b.current_unit LIKE 'LRF%' and b.unit_process_status in ('WAITING FOR PROCESSING', 'RELADLING', 'FULL RETURN') and a.heat_plan_line_no = c.heat_plan_dtl_id  and d.heat_plan_id = c.heat_plan_id  and a.is_processed = 'Y' and b.record_status=1";		
			
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			@SuppressWarnings("unchecked")
			List<EofHeatDetails> ls =(List<EofHeatDetails>) getResultFromCustomQuery(sql);
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new EofHeatDetails();
				obj.setSteel_ladle_name((null==rows[0])?null:rows[0].toString());
				obj.setSteel_ladle_no((null==rows[1])?null:Integer.parseInt(rows[1].toString()));
				obj.setTap_temp((null==rows[2])?null:Integer.parseInt(rows[2].toString()));
				obj.setHeat_id((null==rows[3])?null:rows[3].toString());
				obj.setAim_psn_char((null==rows[4])?null:rows[4].toString());
				obj.setTap_wt((null==rows[5])?null:Double.parseDouble(rows[5].toString()));
				obj.setHeat_plan_id((null==rows[6])?null:Integer.parseInt(rows[6].toString()));
				obj.setHeat_plan_line_id((null==rows[7])?null:Integer.parseInt(rows[7].toString()));
				obj.setCaster_type((null==rows[8])?null:rows[8].toString());
				obj.setSub_unit_name((null==rows[9])?null:rows[9].toString());
				obj.setHeat_counter(Integer.parseInt((null==rows[10])?null:rows[10].toString()));
				obj.setCaster_id((null==rows[11])?null:Integer.parseInt(rows[11].toString()));
				obj.setSub_unit_id((null==rows[12])?null:Integer.parseInt(rows[12].toString()));
				obj.setAct_proc_path((null==rows[13])?null:rows[13].toString());
				obj.setHeat_track_id((null==rows[14])?null:Integer.parseInt(rows[14].toString()));
				obj.setAim_psn((null==rows[15])?null:Integer.parseInt(rows[15].toString()));
				//obj.setSteel_ladle_no((null==rows[16])?null:Integer.parseInt(rows[16].toString()));
				obj.setUnit_process_status((null==rows[16])?null:rows[16].toString());
				obj.setTrns_si_no(Integer.parseInt((null==rows[18])?null:rows[18].toString()));
				obj.setLrf_entry(Integer.parseInt((null==rows[19])?null:rows[19].toString()));
				
				obj.setDispatch_date(df.parse(rows[20].toString()));
				
				obj.setCcm_sec_size(getCCMSecSize((null==rows[6])?null:Integer.parseInt(rows[6].toString())));
				list.add(obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error in getLRFHeatDetailsByStatus........"+e);
		}
		
		return list;
	
	}
	
	public String getCCMSecSize(int pln_id) {
		Session session=getNewSession();
		String qry="SELECT lkup.lookup_value " + 
				" FROM app_lookups lkup, " + 
				" mstr_sms_capability sec_mst, " + 
				" trns_heat_plan_header pln_hrd " + 
				" WHERE pln_hrd.heat_plan_id = " + pln_id+
				" AND sec_mst.capability_mstr_id = pln_hrd.section_planned " + 
				" AND lkup.lookup_id = sec_mst.output_section";
		return getResultFromCustomQuery(qry).get(0).toString();
		
	}

	@Override
	public List<CommonCombo> getConsumedBuckets(Integer heat_transId) {
		// TODO Auto-generated method stub
				List<CommonCombo> retlist = new ArrayList<CommonCombo>();
				try {
					MtrlProcessConsumableMstrModel obj;

				String hql="select  bh.scrap_bkt_header_id, bs.scrap_bucket_no from HeatConsScrapMtrlDetails hm,ScrapBucketHdr bh,ScrapBucketStatusModel bs "
						+ " where hm.scrap_bkt_header_id = bh.scrap_bkt_header_id and bh.scrap_bkt_id = bs.scrap_bucket_id and hm.trns_eof_si_no ="
						+ heat_transId+" and hm.scrap_bkt_header_id  is not null";
				List ls= getResultFromNormalQuery(hql);
				CommonCombo cb;
				Iterator it = ls.iterator();
				while(it.hasNext()){
					Object rows[]=(Object[]) it.next();
					cb = new CommonCombo();
					cb.setKeyval((null == rows[0]) ? null : rows[0].toString());
					cb.setTxtvalue(((null == rows[1]) ? null : rows[1].toString()));
					retlist.add(cb);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				return retlist;
			}

	@Override
	public List<HeatConsScrapMtrlDetails> getConsumedBucketsDtls(
			Integer heat_transId) {
		// TODO Auto-generated method stub
		List<HeatConsScrapMtrlDetails> retlist = new ArrayList<HeatConsScrapMtrlDetails>();
		try {
		 	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		String hql="select  bs.scrap_bucket_no,hm.qty,to_char(hm.consumption_date,'DD/MM/YYYY HH:MI:SS'), hm.scrap_bkt_header_id "
				+ "from HeatConsScrapMtrlDetails hm,ScrapBucketHdr bh,ScrapBucketStatusModel bs "
				+ "where hm.scrap_bkt_header_id = bh.scrap_bkt_header_id and bh.scrap_bkt_id = bs.scrap_bucket_id and hm.trns_eof_si_no ="
				+ heat_transId+" and hm.scrap_bkt_header_id  is not null";
		List ls= getResultFromNormalQuery(hql);
		HeatConsScrapMtrlDetails cb;
		Iterator it = ls.iterator();
		while(it.hasNext()){
			Object rows[]=(Object[]) it.next();
			cb = new HeatConsScrapMtrlDetails();
			cb.setScrap_bkt_no((null == rows[0]) ? null : rows[0].toString());
			cb.setQty((null == rows[1]) ? null :Double.parseDouble(rows[1].toString()) );
			cb.setConsumption_date(df.parse(rows[2].toString()));
			cb.setScrap_bkt_header_id(Integer.parseInt((null == rows[3]) ? "0" : rows[3].toString()));
			//cb.setKeyval((null == rows[0]) ? null : rows[0].toString());
			//cb.setTxtvalue(((null == rows[1]) ? null : rows[1].toString()));
			retlist.add(cb);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}

	@Override
	public Integer checkScrapEntryByTransId(Integer transId) {
		// TODO Auto-generated method stub
		Integer result;
		Long lg;
		Session session = null ;
		try{
		session=getNewSession();
		Query query = session
		            .createQuery("select count(hc.scrap_bkt_header_id) from HeatConsScrapMtrlDetails hc where hc.trns_eof_si_no="+transId+"and hc.scrap_bkt_header_id is not null");
		 
		lg=(Long) query.uniqueResult();
		result= lg.intValue();
		return result;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally {
				close(session);
		}
	}

	@Override
	public List<ValidationResultsModel> checkEOFValidations(Integer eof_tranId) {
		// TODO Auto-generated method stub
		Session session = null ;
		List<ValidationResultsModel> retlist = new ArrayList<ValidationResultsModel>();
		try{
		session=getNewSession();
		
		session.doReturningWork( new ReturningWork<ValidationResultsModel >()
				    {
				        @Override
				        public ValidationResultsModel execute( Connection aConnection ) throws SQLException
				        {
				            CallableStatement callstm = null;
				           
				            try
				            {
				                String functionCall = "{call  eof_checkheatvalidations(?,?)}";
				                callstm = aConnection.prepareCall( functionCall );
				                callstm.registerOutParameter(1, OracleTypes.CURSOR );
				                callstm.setInt(2, eof_tranId );
				                callstm.execute();
				                
				                //ResultSet res = callstm.getResultSet();
				                ResultSet res = (ResultSet) callstm.getObject(1);
				                
				                while (res.next()) {
				                	ValidationResultsModel pt=new ValidationResultsModel();
				                	
				                	pt.setValidation_compo(res.getString("compo"));
				                	pt.setValidation_result((int)res.getInt("result"));
				                	retlist.add(pt);
				                }
				               
				            }
				            finally
				            {
				              callstm.close();
				            }
							return null;
				        }
				    } );
		 
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(session);
		}
		return retlist;
	}

	@Override
	public List<SteelLadleLifeModel> getLaddleLifeDet(Integer steelLadleNo) {
		Session session=null;
		SteelLadleLifeModel obj;
		List<SteelLadleLifeModel> resultList=new ArrayList<SteelLadleLifeModel>();
		
		String hql="select (select sl.ladle_life_sl_no || '&&' || sl.equipment_id || '&&' || sl.part_id  || '&&'  || sl.trns_life from SteelLadleLifeModel sl where sl.steel_ladle_no = "+steelLadleNo+" and "
				+ " sl.part_id = ep.part_id), pm.part_name, ep.max_value,em.equipment_id,pm.part_id FROM EquipmentPartsMapMaster ep, PartsMasterModel pm ,EquipmentMasterModel em"
				+ " where ep.part_id = pm.part_id and ep.equipment_id = em.equipment_id and em.equipment_name = 'STEEL_LADLE'";
		
		try{
			session=getNewSession();
			Query query = session.createQuery(hql);
			List ls=query.list();
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new SteelLadleLifeModel();

				String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));
				if(s!=null){
					
					obj.setLadle_life_sl_no((null==s[0])?null:Integer.parseInt(s[0].toString()));
					obj.setEquipment_id((null==s[1])?null:Integer.parseInt(s[1].toString()));
					obj.setPart_id((null==s[2])?null:Integer.parseInt(s[2].toString()));
					obj.setTrns_life((null==s[3])?null:Integer.parseInt(s[3].toString()));
				}else{
					obj.setLadle_life_sl_no(null);
					obj.setEquipment_id((null==rows[3])?null:Integer.parseInt(rows[3].toString().trim()));
					obj.setPart_id((null==rows[4])?null:Integer.parseInt(rows[4].toString().trim()));
					obj.setTrns_life(1);
				}
				obj.setPart_name((null==rows[1])?null:rows[1].toString());
				obj.setMax_life_value((null==rows[2])?null:Integer.parseInt(rows[2].toString().trim()));
				resultList.add(obj);
			}
		
			}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(session);
		}
		return resultList;
		
	}
	
	@Override
	public String getSampleNoforHmChem(String heat_id) {
		// TODO Auto-generated method stub
		Session session = null ;
		//String result=null;
		String res=null;
		try{
		session=getNewSession();
		
		res= session.doReturningWork( new ReturningWork<String>(){
			  public String execute(Connection connection) throws SQLException {
				  CallableStatement call=null;
				 try{
			    call = connection.prepareCall("{ ? = call eof_chem_sample_no_gen(?) }");
			    call.registerOutParameter( 1,OracleTypes.NVARCHAR  ); 
			    call.setString(2,heat_id );
			   
			    call.execute();
			    String result = call.getString(1);
			    return result;
			  }finally{
				  call.close();
			  }
			  }
			});
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
			close(session);
			
		}
		return res;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<EofHeatDetails> getEOFHeatDetailsByDate(String fdate,
			String tdate,Integer subunit) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEOFHeatDetailsByDate....."+EofProductionDaoImpl.class);
		List<EofHeatDetails> list=new ArrayList<EofHeatDetails>();
		Session session=getNewSession();
		try {

			//date input format is dd/MM/yyyy
			String fdate1=GenericClass.getFormattedDate(fdate,"dd/MMM/yyyy");
			String tdate1=GenericClass.getFormattedDate(tdate,"dd/MMM/yyyy");
			
			//String hql = "select a from EofHeatDetails a where trunc(a.production_date) between '01/Dec/2016' and '07/Jan/2017' and a.sub_unit_id="+subunit+" order by a.production_date";
			String hql = "select a from EofHeatDetails a where trunc(a.production_date) between '"+fdate1+"' and '"+tdate1+"' and a.sub_unit_id="+subunit+" order by a.production_date";
			
			list=(List<EofHeatDetails>) session.createQuery(hql).list();
			

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEOFHeatDetailsByDate........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<HeatConsScrapMtrlDetails> getHeatScrapConsumptionByEofId(
			Integer trns_si_no) {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class);
		List<HeatConsScrapMtrlDetails> list=new ArrayList<HeatConsScrapMtrlDetails>();
		try {
			String sql = "Select a from HeatConsScrapMtrlDetails a where a.trns_eof_si_no="+trns_si_no+" and a.hm_seq_no is null";
			list=(List<HeatConsScrapMtrlDetails>) getResultFromNormalQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getHeatScrapConsumptionByEofId..."+e.getMessage());
		}
		
		return list;
	
	}

	@Override
	public List<GraphReportModel> getHeatsWithInDates(String fdate,
			String tdate, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatsWithInDates....."+EofProductionDaoImpl.class);
		List<GraphReportModel> resultList=new ArrayList<GraphReportModel>();
		Session session=getNewSession();
		try {

			String fdate1=GenericClass.getFormattedDate(fdate,"dd/MMM/yyyy");
			String tdate1=GenericClass.getFormattedDate(tdate,"dd/MMM/yyyy");
			String sqlquery="SELECT prod_date,(SELECT COUNT (production_date)FROM trns_eof_heat_dtls WHERE TO_CHAR (production_date, 'DD/MM/YYYY') = prod_date AND sub_unit_id ="+sub_unit_id+" ) heatcnt "
					+ "  FROM (SELECT TO_CHAR (TO_DATE ('"+fdate1+"', 'dd-mon-yyyy') + ROWNUM - 1, 'DD/MM/YYYY') prod_date FROM all_objects  WHERE ROWNUM <=TO_DATE ('"+tdate1+"', 'dd-mon-yyyy')- TO_DATE ('"+fdate1+"', 'dd-mon-yyyy')   + 1)";
			Query query = session.createSQLQuery(sqlquery);
			List ls=query.list();
			Iterator it = ls.iterator();
			GraphReportModel obj=null;
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new GraphReportModel();
				obj.setLabel((null==rows[0])?null:rows[0].toString());
				obj.setHeat_cnt((null==rows[1])?null:Integer.parseInt(rows[1].toString().trim()));
				resultList.add(obj);
				obj=null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatsWithInDates........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return resultList;
	}
	@Override
	public List<EofHeatDetails> getEofChemDetails(String heat_id,
			Integer heat_counter) {
		// TODO Auto-generated method stub
		List<EofHeatDetails> retlist = new ArrayList<EofHeatDetails>();
		try {
		  
		String hql="SELECT ch.ELEMENT, ch.aim_value FROM trns_heat_chemistry_child ch, app_lookups lkp WHERE lkp.lookup_type = 'ELEMENT' AND lkp.lookup_status = 1  AND ch.ELEMENT = lkp.lookup_id  AND ch.sample_si_no = "
				+ " (SELECT max(chd.sample_si_no)   FROM trns_heat_chemistry_hdr chd  WHERE chd.heat_id = '"+heat_id+"' AND chd.heat_counter = "+heat_counter+" AND chd.analysis_type = (SELECT lookup_id  FROM app_lookups lk1"
				+ " WHERE lk1.lookup_value = 'EAF_TAP_CHEM' AND lk1.lookup_type = 'CHEM_LEVEL') and chd.final_result = 1)";
		
		
		List ls= getResultFromCustomQuery(hql);
		EofHeatDetails cb;
		Iterator it = ls.iterator();
		while(it.hasNext()){
			Object rows[]=(Object[]) it.next();
			cb = new EofHeatDetails();
			cb.setElement_id(Integer.parseInt((null==rows[0])?null:rows[0].toString()));
			cb.setElement_aim_value(Double.parseDouble((null==rows[1])?null:rows[1].toString()));
			retlist.add(cb);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}
	
	@Override
	public List<SeqIdDetails> getSeqIdByQuery(String sub_unit) {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside getSeqIdByQuery ");
		List<SeqIdDetails> list=new ArrayList<SeqIdDetails>();
		Session session=getNewSession();
		try {
			String hql = null;
			if(sub_unit.equals(Constants.SUB_UNIT_EAF1))
				hql = "select a from SeqIdDetails a where a.col_key in ('"+Constants.HEAT_NO_GEN_SEQ_1+"') order by a.col_key desc";
			else if (sub_unit.equals(Constants.SUB_UNIT_EAF2)) 
				hql = "select a from SeqIdDetails a where a.col_key in ('"+Constants.HEAT_NO_GEN_SEQ_2+"') order by a.col_key desc";
			
			list=(List<SeqIdDetails>) session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getSeqIdByQuery........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public EofHeatDetails getEOFHeatDetailsByHeatNo(String heatNo) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEOFHeatDetailsByHeatNo....."+EofProductionDaoImpl.class);
		EofHeatDetails obj = new EofHeatDetails();
		Session session=getNewSession();
		try {

			String hql = "select a from EofHeatDetails a where heat_id= UPPER('" +heatNo+"')";
			if(hql!=null) {
				obj=(EofHeatDetails) session.createQuery(hql).list().get(0);
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEOFHeatDetailsByHeatNo........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return obj;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Integer getHMRecptIdByHeatNo(String heatNo) {
		// TODO Auto-generated method stub
		logger.info("inside .. getEOFHeatDetailsByHeatNo....."+EofProductionDaoImpl.class);
		EofHeatDetails obj = new EofHeatDetails();
		Session session=getNewSession(); Integer hmRecId=0;
		try {

			String sql = "select ehcs.hm_seq_no from trns_eof_heat_dtls ehd, trns_eof_heat_cons_scrap_hm ehcs "
			+ "where ehd.trns_si_no = ehcs.trns_eof_si_no and ehd.heat_id = '"+heatNo+"' and ehcs.hm_seq_no > 0";

			Query query =session.createSQLQuery(sql);
			List<?> results =query.list();
			Iterator<?> it = results.iterator();
			while(it.hasNext())
			{
				hmRecId = Integer.parseInt(it.next().toString());
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHMRecptIdByHeatNo........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return hmRecId;
	}

	@Override
	public DelayEntryDTO getEofDelayEntriesBySubUnitAndHeat(Integer sub_unit_id, Integer trans_heat_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EofHeatDetails getEOFPreviousHeatDetailsByHeatNo(Integer trans_si_no) {
		logger.info("inside .. getEOFHeatDetailsByHeatNo....."+EofProductionDaoImpl.class);
		EofHeatDetails obj = null;
		Session session=getNewSession();
		try {
			String sql="select  TRNS_SI_NO from TRNS_EOF_HEAT_DTLS where TRNS_SI_NO <(" + 
					"select TRNS_SI_NO from TRNS_EOF_HEAT_DTLS where TRNS_SI_NO="+trans_si_no+") and rownum <=1  order by TRNS_SI_NO desc ";
			SQLQuery query=session.createSQLQuery(sql);
			BigDecimal id=(BigDecimal) query.uniqueResult();
			Criteria crt=session.createCriteria(EofHeatDetails.class);
			if(id != null) {
			crt.add(Restrictions.eq("trns_si_no",id.intValue()));
			obj = new EofHeatDetails();
			obj=(EofHeatDetails) crt.uniqueResult();
			}
			//obj=(EofHeatDetails) session.createQuery("from EofHeatDetails where trns_si_no="+id).uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEOFHeatDetailsByHeatNo........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return obj;
		
	}

	@Override
	public EofHeatDetails getEOFHeatDetailsById(Integer trans_si_no) {
		// TODO Auto-generated method stub
		EofHeatDetails obj = new EofHeatDetails();
		Session session=getNewSession();
		try {
		String hql = "select a from EofHeatDetails a where a.trns_si_no="+trans_si_no;
		Query query=session.createQuery(hql);
		obj=(EofHeatDetails) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEOFHeatDetailsById........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		return obj;
	}
	
	@Override
    public String getCrewRoleUser(String heatId, String crewUserRole) {
        // TODO Auto-generated method stub
        List<EOFHeatLogRpt> retlist = new ArrayList<EOFHeatLogRpt>();
        Session session=getNewSession();
        String res = "";
        try {
             	String sql = "select ad.USER_NAME as shift_incharge from trns_heat_crew_details hcd, mstr_user_role_mapping rm,  app_lookups lk, "
             			+ "app_user_account_details ad where hcd.USER_ROLE_ID = rm.USER_ROLE_ID and ad.APP_USER_ID = rm.APP_USER_ID "
             			+ "and lk.LOOKUP_ID = rm.LOOKUP_ID and lk.LOOKUP_CODE = '"+crewUserRole+"' and hcd.HEAT_ID = UPPER('"+heatId+"')";
             
             	Query query = session.createSQLQuery(sql);
                Object ls = null;
                try {
                	ls=query.uniqueResult();
                	res = ls.toString();
                } catch (Exception e) {
                	e.printStackTrace();
                }
	        res = ls.toString();
        } catch (Exception e) {
        	e.printStackTrace();
        }finally {
			close(session);
		}
        
        return res;
	}
	
	@Override
    public List<EOFHeatLogRpt> getEofHeatLogs(String heatId, String LogType) {
        // TODO Auto-generated method stub
        List<EOFHeatLogRpt> retlist = new ArrayList<EOFHeatLogRpt>();
        Session session=getNewSession();
        try {
            String sql = "";
        if (LogType.equals("ScrapChanges") ) {
            sql="SELECT pc.material_desc, sum(sbd.material_qty ) as qty "
                    + "FROM trns_eof_heat_cons_scrap_hm ehcs, trns_eof_heat_dtls ehd, trns_scrap_bucket_dtls sbd, mstr_process_consumables pc, app_lookups lu "
                    + "WHERE ehcs.trns_eof_si_no = ehd.trns_si_no AND sbd.scrap_bkt_header_id = ehcs.scrap_bucket_header_id "
                    + "AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'SCRAP' AND sbd.material_id = pc.material_id "
                    + "AND ehd.heat_id = '"+heatId+"' AND (ehcs.scrap_bucket_header_id IS NOT NULL) group by pc.material_desc";
        } else if (LogType.equals("FurnaceAdditions")) {
            sql = "SELECT pc.material_desc, sum(a.qty) as qty FROM trns_eof_heat_cons_materials a, trns_eof_heat_dtls b, "
                    + "mstr_process_consumables pc, trns_eof_heat_cons_scrap_hm ehcs, app_lookups lu "
                    + "WHERE a.trns_eof_si_no = b.trns_si_no AND a.material_id = pc.material_id AND ehcs.trns_eof_si_no = a.trns_eof_si_no "
                    + "AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'FURNACE_ADDITIONS' "
                    + "AND b.heat_id = '"+heatId+"' AND (ehcs.hm_seq_no IS NOT NULL) group by pc.material_desc";
        } else if (LogType.equals("LadleAdditions")) {
            sql = "SELECT pc.material_desc, ehcm.qty FROM trns_eof_heat_cons_materials ehcm, mstr_process_consumables pc, app_lookups lu, "
                    + "trns_eof_heat_dtls ehd WHERE ehcm.material_id = pc.material_id AND pc.record_status = 1 "
                    + "AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'LADLE_ADDITIONS' AND ehd.trns_si_no = ehcm.trns_eof_si_no "
                    + "AND ehd.HEAT_ID = '"+heatId+"' order by pc.order_seq";
        } else if (LogType.equals("EOFQCAChem")) {
            /*sql = "SELECT lkp.LOOKUP_CODE, ch.aim_value FROM trns_heat_chemistry_child ch, app_lookups lkp "
                    + " WHERE lkp.lookup_type = 'ELEMENT' AND lkp.lookup_status = 1  AND ch.ELEMENT = lkp.lookup_id  AND ch.sample_si_no = "
                    + " (SELECT max(chd.sample_si_no)   FROM trns_heat_chemistry_hdr chd  WHERE chd.heat_id = '"+heatId+"' "
                    + " AND chd.analysis_type = (SELECT lookup_id  FROM app_lookups lk1 WHERE lk1.lookup_value = 'EAF_QCA_CHEM' "
                    + " AND lk1.lookup_type = 'CHEM_LEVEL') and chd.final_result = 1)";*/
        	sql = "select chd.sample_date_time, (select ch.aim_value from trns_heat_chemistry_child ch, app_lookups lkp "
    			+ "where ch.element = lkp.lookup_id and ch.sample_si_no = chd.sample_si_no and lkp.lookup_code = 'ATM OXY' "
    			+ "and lkp.lookup_type = 'ELEMENT' and lkp.lookup_status = 1 and ch.element = lkp.lookup_id) as atmoxy, "
    			+ "(select ch.aim_value from trns_heat_chemistry_child ch, app_lookups lkp where ch.element = lkp.lookup_id "
    			+ "and ch.sample_si_no = chd.sample_si_no and lkp.lookup_code = 'SUB OXY' and lkp.lookup_type = 'ELEMENT' and "
    			+ "lkp.lookup_status = 1 and ch.element = lkp.lookup_id) as suboxy, chd.sample_temp, "
    			+ "(select ch.aim_value from trns_heat_chemistry_child ch, app_lookups lkp  where ch.element = lkp.lookup_id and "
    			+ "ch.sample_si_no = chd.sample_si_no and lkp.lookup_code = 'C' and lkp.lookup_type = 'ELEMENT' and lkp.lookup_status = 1 "
    			+ "and ch.element = lkp.lookup_id) as c from trns_heat_chemistry_hdr chd where chd.heat_id = '"+heatId+"' and chd.final_result = "
    			+ "1 AND chd.analysis_type in (select lookup_id  from app_lookups lk1 where lk1.lookup_value in('EAF_QCA_CHEM') and "
    			+ "lk1.lookup_type = 'CHEM_LEVEL')  order by chd.sample_date_time";
        } else if (LogType.equals("ProcessConsumptions")) {
        	sql = "SELECT pc.material_desc, nvl(ehcm.qty,0),decode(lu.lookup_value,null,' ','('||luom.lookup_value||')') as uom FROM trns_eof_heat_cons_materials ehcm, mstr_process_consumables pc, app_lookups lu,app_lookups luom, "
                    + " trns_eof_heat_dtls ehd WHERE ehcm.material_id = pc.material_id AND pc.record_status = 1 "
                    + " AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'PROCESS_CONSUMPTIONS' AND ehd.trns_si_no = ehcm.trns_eof_si_no "
                    + " AND luom.lookup_type = 'UOM' AND pc.uom = luom.lookup_id"
                    + " AND ehd.HEAT_ID = '"+heatId+"' order by pc.order_seq ";	
        } else if (LogType.equals("HeatEventDelay")) {
            /*sql = "select e.EVENT_DESC, pe.EVENT_DATE_TIME from trns_heat_process_events pe, mstr_event e "
                    + "where pe.event_id = e.event_si_no and pe.HEAT_ID = '"+heatId+"' order by e.EVENT_UNIT_SEQ";
        	sql = "select adtl.activities, deh.tot_delay, adtl.delay_details from trns_delay_entry_hdr deh, "
        			+ "mstr_activity_delay_dtls adtl, trns_eof_heat_dtls ehd where deh.activity_delay_id = adtl.activity_delay_id "
        			+ "and ehd.trns_si_no = deh.trns_heat_id and ehd.HEAT_ID = '"+heatId+"'";*/
        	sql = "select adtl.delay_details,std_cycle_time std_time,round((case when (select (hdr.ACTIVITY_END_TIME - hdr.ACTIVITY_START_TIME)*24*60 from trns_delay_entry_hdr hdr where  "
        		+" hdr.activity_delay_id = adtl.activity_delay_id and hdr.trns_heat_id = ehd.trns_si_no  ) <= 0 then 0 "
        		+" else (select (hdr.ACTIVITY_END_TIME - hdr.ACTIVITY_START_TIME)*24*60 from trns_delay_entry_hdr hdr where "  
        		+" hdr.activity_delay_id = adtl.activity_delay_id and hdr.trns_heat_id = ehd.trns_si_no  ) end),0) as act_time, "
        		+" round((Case when (select deh.tot_delay from trns_delay_entry_hdr deh where deh.activity_delay_id = " 
        		+" adtl.activity_delay_id and deh.trns_heat_id = ehd.trns_si_no) <= 0 then 0 else (select deh.tot_delay from trns_delay_entry_hdr deh where deh.activity_delay_id = " 
        		+" adtl.activity_delay_id and deh.trns_heat_id = ehd.trns_si_no) end),0) tot_delay,  "
        		+" get_delay_reason(adtl.activity_delay_id,ehd.trns_si_no) delay_reason, "
    			+" '['||(select to_char(hdr.ACTIVITY_START_TIME,'dd/mm/yy hh:mi')||' to '||to_char(hdr.ACTIVITY_END_TIME,'dd/mm/yy hh:mi') from trns_delay_entry_hdr hdr where " 
    	        +" hdr.activity_delay_id = adtl.activity_delay_id and hdr.trns_heat_id = ehd.trns_si_no  )||'] ' "
    			+" from  mstr_activity_delay_dtls adtl, trns_eof_heat_dtls ehd where ehd.heat_id = '"+heatId+"' "
    			+" and ehd.sub_unit_id = adtl.sub_unit_id order by adtl.activity_seq";
        } else if (LogType.equals("EOFTapChem")) {
        	sql = "select le.lookup_value, hcc.aim_value  from trns_heat_chemistry_child hcc, trns_heat_chemistry_hdr hch, "
        			+ "app_lookups le, app_lookups lcl where hch.sample_si_no = hcc.sample_si_no and hch.final_result = 1 and "
        			+ "hch.analysis_type = lcl.lookup_id and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value = 'EAF_TAP_CHEM' "
        			+ "and hcc.element = le.lookup_id and le.lookup_type = 'ELEMENT' and hch.heat_id = '"+heatId+"' order by le.attribute1";
        } else if (LogType.equals("EOFTapPSNAimChem")) {
        	sql = "select le.LOOKUP_VALUE, pc.VALUE_AIM from mstr_psn_chemistry pc, trns_eof_heat_dtls ehd, mstr_psn_hdr ph, "
    			+ "app_lookups le, app_lookups lcl where pc.PSN_HDR_SL_NO = ph.PSN_HDR_SL_NO and pc.ELEMENT_ID = le.LOOKUP_ID "
    			+ "and pc.CHEM_LEVEL = lcl.LOOKUP_ID and lcl.LOOKUP_TYPE = 'CHEM_LEVEL' and lcl.LOOKUP_VALUE = 'EAF_TAP_CHEM' "
    			+ "and le.LOOKUP_TYPE = 'ELEMENT' and ph.PSN_HDR_SL_NO = ehd.AIM_PSN and ehd.HEAT_ID = '"+heatId+"' order by le.attribute1";
        } else if (LogType.equals("LRFInitChem")) {
        	sql = "select le.lookup_value, hcc.aim_value  from trns_heat_chemistry_child hcc, trns_heat_chemistry_hdr hch, "
        			+ "app_lookups le, app_lookups lcl where hch.sample_si_no = hcc.sample_si_no and hch.final_result = 1 and "
        			+ "hch.analysis_type = lcl.lookup_id and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value = 'LRF_INITIAL_CHEM' "
        			+ "and hcc.element = le.lookup_id and le.lookup_type = 'ELEMENT' and hch.heat_id = '"+heatId+"' order by le.attribute1";
        } else if (LogType.equals("GasElements")) {
        	sql =    " SELECT  (case when pc.material_desc like('Oxygen%') or pc.material_desc like('OXYGEN%') or pc.material_desc like('O2%') then 'OXYGEN' "
        			 +" when pc.material_desc like('Nitrogen%') or pc.material_desc like('NITROGEN%') then 'NITROGEN' "
        			 +" when pc.material_desc like('Ar_Consumption%') or pc.material_desc like('Ar_CONSUMPTION%') then 'Ar_CONSUMPTION' else pc.material_desc end) gas_material_desc, sum(ehcm.qty) qty " 
        			 +" FROM trns_eof_heat_cons_materials ehcm, mstr_process_consumables pc, app_lookups lu, trns_eof_heat_dtls ehd "
        			 +" WHERE ehcm.material_id = pc.material_id AND pc.record_status = 1 AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'PROCESS_CONSUMPTIONS'  "
        			 +" AND ehd.trns_si_no = ehcm.trns_eof_si_no AND (pc.material_desc like('Oxygen%') or pc.material_desc like('O2%') or pc.material_desc like('OXYGEN%')or pc.material_desc in('NITROGEN','Nitrogen','Ar_Consumption','Ar_CONSUMPTION')) " 
        			 +" AND ehd.HEAT_ID = '"+heatId+"' group by (case when pc.material_desc like('Oxygen%') or pc.material_desc like('OXYGEN%') or pc.material_desc like('O2%') then 'OXYGEN' "
        			 +" when pc.material_desc like('Nitrogen%') or pc.material_desc like('NITROGEN%') then 'NITROGEN' "
        			 +" when pc.material_desc like('Ar_Consumption%') or pc.material_desc like('Ar_CONSUMPTION%') then 'Ar_CONSUMPTION'else pc.material_desc end) ";
        } else if (LogType.equals("InletPressure")) {
        	sql =  "select c.param_desc, a.param_value_aim from trns_heat_process_parameters a, mstr_psn_process_parameters b, " 
        			+" mstr_process_parameters c,trns_eof_heat_dtls d where a.heat_id = d.heat_id and "
                    +" a.param_id = b.psn_proc_param_sl_no(+) and c.param_si_no = a.param_id and c.param_desc in('O2_PRESSURE','N2_PRESSURE','ARGON_PRESSURE') "
       			    +" and c.record_status = 1 and a.heat_id = '"+heatId+"' and d.SUB_UNIT_ID = c.SUB_UNIT_ID";
        			/*"select c.param_desc, a.param_value_aim from trns_heat_process_parameters a, mstr_psn_process_parameters b, "
    			+ "mstr_process_parameters c where a.param_id = b.psn_proc_param_sl_no and c.param_si_no = b.param_id "
    			+ "and c.record_status = 1 and ";*/        	
        } else if (LogType.equals("OtherDataElements")) {
        sql =  /*" SELECT  'Furnace_Life',sum(a.total_heats) furnace_life FROM trns_furnace_campaign_lines a, mstr_sub_unit_details c, app_lookups l "   
               +" WHERE  a.sub_unit_id = c.sub_unit_id  and a.life_parameter = l.lookup_id and l.lookup_status = 1 "
               +" and l.lookup_code in ('FURNACE LIFE') and a.sub_unit_id in(Select sub_unit_id from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"' and "
               +" heat_counter in(Select max(heat_counter) from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"')) "*/
	           " Select 'Furnace_Life',max(furnace_life) furnace_life from (Select 'Furnace_Life',furnace_life  from TRNS_EOF_HEAT_DTLS where  heat_id = '"+heatId+"'  and "
               +" heat_counter in(Select max(heat_counter) from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"') "
               +" UNION "
			   +" Select 'Furnace_Life',0 as furnace_life from dual) a where furnace_life is not null "
               +" UNION "
               +" Select 'Heat_Count',count(*) no_of_heats from TRNS_EOF_HEAT_DTLS "
               +" where (to_date(production_date),sub_unit_id) in(Select to_date(production_date),sub_unit_id from TRNS_EOF_HEAT_DTLS "
               +" where heat_id = '"+heatId+"' and heat_counter in ( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"'))"
               +" and production_date <= (Select (production_date) from TRNS_EOF_HEAT_DTLS "
               +" where heat_id = '"+heatId+"' and heat_counter in ( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"'))"
               +" UNION "
               +" Select 'Heat_Month_Count',count(*) no_of_heats from TRNS_EOF_HEAT_DTLS "
               +" where to_date(production_date) >= (Select to_date(trunc(production_date,'MM')) from TRNS_EOF_HEAT_DTLS  "
               +" where heat_id = '"+heatId+"' and heat_counter in ( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"'))   and to_date(production_date) <=(Select to_date(production_date) from TRNS_EOF_HEAT_DTLS " 
               +" where heat_id = '"+heatId+"' and heat_counter in ( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"'))"
	        	+" and production_date <= (Select (production_date) from TRNS_EOF_HEAT_DTLS "
	        	+" where heat_id = '"+heatId+"' and heat_counter in ( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"'))"
	        	+" and sub_unit_id in(Select (sub_unit_id) from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"' and heat_counter in ( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"'))" 
	        	+" UNION "
	        	+" Select 'VD_NVD',max(vd_flag) from (select 'VD_NVD',count(*) vd_flag from trns_eof_heat_dtls e,mstr_psn_route a,app_lookups b " 
	        	+" where a.route_id = b.lookup_id and e.HEAT_ID = '"+heatId+"' and e.aim_psn = a.psn_hdr_sl_no and lookup_value like'%VD%' "
	        	+" and heat_counter in( Select max(heat_counter) from TRNS_eof_HEAT_DTLS where heat_id = '"+heatId+"') group by psn_hdr_sl_no "
	        	+" union "
	        	+" select 'VD_NVD',0 vd_flag from dual) where vd_flag is not null "
				+" UNION "
				+" Select 'CONTAINER_LIFE',max(container_life) container_life from (Select 'CONTAINER_LIFE',container_life  from TRNS_EOF_HEAT_DTLS where  heat_id = '"+heatId+"'  and "
				+" heat_counter in(Select max(heat_counter) from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"') "
				+" UNION "
				+" Select 'CONTAINER_LIFE',0 as container_life from dual) a where container_life is not null "
	        	+" UNION "
	        	+" SELECT  'CAMPAIGN_NO', campaign_number FROM trns_furnace_campaign_lines a, mstr_sub_unit_details c, app_lookups l   "
	        	+" WHERE  a.sub_unit_id = c.sub_unit_id  and a.life_parameter = l.lookup_id and l.lookup_status = 1  and l.lookup_code in ('FURNACE LIFE') "
	        	+" and (a.sub_unit_id,campaign_id) in(Select sub_unit_id,eof_campaign_no from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"' and "
	            +" heat_counter in(Select max(heat_counter) from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"'))"
	            +" UNION "
	            +" Select 'STEEL_LDL_GROSS_WT',(nvl(tap_wt,0)+nvl(tare_wt,0)) gross_wgt from TRNS_EOF_HEAT_DTLS a,MSTR_STEEL_LADLE b where heat_id = '"+heatId+"' "
	            + "and b.STEEL_LADLE_SI_NO=a.STEEL_LADLE_NO and heat_counter in(Select max(heat_counter) from TRNS_EOF_HEAT_DTLS where heat_id = '"+heatId+"')";
		      }
        Query query = session.createSQLQuery(sql);
        List ls = null;
        try { 
        	ls=query.list();
        } catch (Exception e) {
        	//e.printStackTrace();
        }
            //List ls= getResultFromCustomQuery(hql);
            EOFHeatLogRpt obj; 
            EOFHeatLogRpt o1 = new EOFHeatLogRpt();
            Iterator it = ls.iterator();
            Double tot = 0.0;
            while(it.hasNext()){
                Object rows[]=(Object[]) it.next();
                obj = new EOFHeatLogRpt();
                
                if (LogType.equals("EOFQCAChem")) {
                	obj.setParameter((null == rows[0]) ? null : GenericClass.getDateTimeFormatted("dd/MM/yyyy hh:mm aa", (Date) rows[0]));
                    obj.setValue1((null == rows[1]) ? null :rows[1].toString());
                    obj.setValue2((null == rows[2]) ? null :rows[2].toString());
                	obj.setValue3((null == rows[3]) ? null :rows[3].toString());
                	obj.setValue4((null == rows[4]) ? null :rows[4].toString());
                	retlist.add(obj);
                } else if (LogType.equals("EOFTapPSNAimChem")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                	if (obj.getParameter().equals("C")) {
                		o1.setValue1((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Si")) {
                		o1.setValue2((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Mn")) {
                		o1.setValue3((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("P")) {
                		o1.setValue4((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("S")) {
                		o1.setValue5((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Cr")) {
                		o1.setValue6((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Al")) {
                		o1.setValue7((null == rows[1]) ? null :rows[1].toString());
                	}                	
                } else if (LogType.equals("EOFTapChem")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                	if (obj.getParameter().equals("C")) {
                		o1.setValue1((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Si")) {
                		o1.setValue2((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Mn")) {
                		o1.setValue3((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("P")) {
                		o1.setValue4((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("S")) {
                		o1.setValue5((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Cr")) {
                		o1.setValue6((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Al")) {
                		o1.setValue7((null == rows[1]) ? null :rows[1].toString());
                	}                	
                } 
                else if (LogType.equals("LRFInitChem")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                	if (obj.getParameter().equals("C")) {
                		o1.setValue1((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Si")) {
                		o1.setValue2((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Mn")) {
                		o1.setValue3((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("P")) {
                		o1.setValue4((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("S")) {
                		o1.setValue5((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Cr")) {
                		o1.setValue6((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("Al")) {
                		o1.setValue7((null == rows[1]) ? null :rows[1].toString());
                	}                	
                } 
                else if (LogType.equals("GasElements")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                	if (obj.getParameter().equals("OXYGEN")) {
                		o1.setValue1((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("NITROGEN")) {
                		o1.setValue2((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("Ar_CONSUMPTION")) {
                		o1.setValue3((null == rows[1]) ? null :rows[1].toString());
                	}
                } else if (LogType.equals("InletPressure")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                	if (obj.getParameter().equals("O2_PRESSURE")) {
                		o1.setValue1((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("ARGON_PRESSURE")) {
                		o1.setValue3((null == rows[1]) ? null :rows[1].toString());
                	} else if (obj.getParameter().equals("N2_PRESSURE")) {
                		o1.setValue2((null == rows[1]) ? null :rows[1].toString());
                	}
                } else if (LogType.equals("ShiftIncharge")) {
                	o1.setParameter((null == rows[0]) ? null : rows[0].toString());
                } else if (LogType.equals("EofController")) {
                	o1.setParameter((null == rows[0]) ? null : rows[0].toString());
                } else if (LogType.equals("OtherDataElements")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                	if (obj.getParameter().equals("Furnace_Life")) {
                		o1.setValue1((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("Heat_Count")) {
                		o1.setValue2((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("Heat_Month_Count")) {
                		o1.setValue3((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("VD_NVD")) {
                		o1.setValue4((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("CONTAINER_LIFE")) {
                		o1.setValue5((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("CAMPAIGN_NO")) {
                		o1.setValue6((null == rows[1]) ? null :rows[1].toString());
                	}else if (obj.getParameter().equals("STEEL_LDL_GROSS_WT")) {
                		o1.setValue7((null == rows[1]) ? null :rows[1].toString());
                	}
                }
                else if(LogType.equals("HeatEventDelay")) {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                    obj.setValue1((null == rows[1]) ? null :rows[1].toString());
                    obj.setValue2((null == rows[2]) ? null :rows[2].toString());
                    obj.setValue3((null == rows[3]) ? null :rows[3].toString());
                    obj.setValue4((null == rows[4]) ? null :rows[4].toString());
                	try {                    	
                    	obj.setValue5((null == rows[5]) ? null :rows[5].toString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                    	obj.setValue5(null);
                    }
                    if (obj.getValue3() != null)
                    	tot = tot + Double.parseDouble(obj.getValue3());                    
                    retlist.add(obj);
                }  
                else {
                	obj.setParameter((null == rows[0]) ? null : rows[0].toString());
                    obj.setValue1((null == rows[1]) ? null :rows[1].toString());
                	try {
                    	obj.setValue2((null == rows[2]) ? null :rows[2].toString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                    	obj.setValue2(null);
                    }
                    if (obj.getValue1() != null)
                    	tot = tot + Double.parseDouble(obj.getValue1());                    
                    retlist.add(obj);
                }                
            }  
            if (LogType.equals("ScrapChanges") || LogType.equals("FurnaceAdditions") || LogType.equals("LadleAdditions")) {
            	DecimalFormat f = new DecimalFormat("##.00");
            	obj = new EOFHeatLogRpt();
                obj.setParameter("Total");
                obj.setValue1((f.format(tot)));
                retlist.add(obj);
            }else if (LogType.equals("HeatEventDelay")) {
            	DecimalFormat f = new DecimalFormat("##.00");
            	obj = new EOFHeatLogRpt();
                obj.setParameter("Total");
                obj.setValue3((f.format(tot)));
                retlist.add(obj);
            }
            else 
            	retlist.add(o1);
          //if (LogType.equals("EOFTapPSNAimChem") || LogType.equals("EOFTapChem") || LogType.equals("GasConsumption") || LogType.equals("InletPressure")) 
        }catch(Exception e){
			e.printStackTrace();
		}finally{
			close(session);
		}
		return retlist;
	}

	@Override
	public List<EOFHeatLogRpt> getEofHMScrapDayConsumption() {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside getEofHMScrapDayConsumption ");
		List<EOFHeatLogRpt> retlist = new ArrayList<EOFHeatLogRpt>();
		try {
			String hql="select ehd.trns_si_no, ifc.heat_number, ifc.psn, ifc.grade, ehd.hm_wt, (select sbh.scrap_bkt_qty from "
					+ " trns_eof_heat_cons_scrap_hm hm, trns_scrap_bucket_hdr sbh where hm.scrap_bucket_header_id = sbh.scrap_bkt_header_id "
					+ " and hm.trns_eof_si_no = ehd.trns_si_no) as scrap_qty, ifc.total_wt, (select sbh.scrap_bkt_header_id from "
					+ " trns_eof_heat_cons_scrap_hm hm, trns_scrap_bucket_hdr sbh where hm.scrap_bucket_header_id = sbh.scrap_bkt_header_id "
					+ " and hm.trns_eof_si_no = ehd.trns_si_no) as scrap_hdr_id from intf_sms_prod_conf_heat ifc,trns_eof_heat_dtls ehd "
					+ " where ifc.heat_number = ehd.heat_id and to_date(ifc.production_date) = to_date(sysdate-1)";
		
			List ls= getResultFromCustomQuery(hql);
			EOFHeatLogRpt retObj;
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[]=(Object[]) it.next();
				retObj = new EOFHeatLogRpt();
				retObj.setTrns_si_no((null==rows[0])?null:Integer.parseInt(rows[0].toString()));
				retObj.setHeat_no((null==rows[1])?null:rows[1].toString());
				retObj.setAim_psn((null==rows[2])?null:rows[2].toString());
				retObj.setGrade((null==rows[3])?null:rows[3].toString());
				retObj.setHm_wt((null==rows[4])?null:Double.parseDouble(rows[4].toString()));
				retObj.setTotal_scrap((null==rows[5])?null:Double.parseDouble(rows[5].toString()));
				retObj.setOp_wt((null==rows[6])?null:Double.parseDouble(rows[6].toString()));
				retObj.setScrap_hdr_id((null==rows[7])?null:Integer.parseInt(rows[7].toString()));
				retlist.add(retObj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retlist;
	}

	@Override
	public Integer isMaterialConsumptionPosted() {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside isMaterialConsumptionPosted ");
		Integer retVal = 0;
		try {
			String hql="select ch.consumption_date  from intf_sms_prod_consm_heat ch where to_date(ch.consumption_date) = to_date(sysdate-1)";
		
			List ls= getResultFromCustomQuery(hql);
			if(ls.size() > 0)
				retVal = 1;
			else
				retVal = 0;
		}catch(Exception e){
			e.printStackTrace();
		}
		return retVal;
	}

	@Override
	@Transactional
	public List<String> getConsumedMaterialList(String sub_unit) {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside getConsumedMaterialList ");
		List<String> ls = null;
		String hql = null;
		try {
				if(sub_unit.equals(Constants.SUB_UNIT_EOF)) {
					hql="select matl.material_desc from mstr_process_consumables matl, (select cons.material_id from intf_sms_prod_conf_heat ifc,trns_eof_heat_dtls ehd, " + 
						" trns_eof_heat_cons_materials cons where ifc.heat_number = ehd.heat_id and ehd.trns_si_no = cons.trns_eof_si_no and " + 
						" to_date(ifc.production_date) = to_date(sysdate-1) group by cons.material_id) mat where matl.material_id = mat.material_id "+
						" and matl.material_type in (select lookup_id from app_lookups l where l.lookup_type = '"+Constants.MATERIAL_TYPE+"' and l.lookup_code in "+
						" ('"+Constants.LADLE_ADDITIONS+"', '"+Constants.FURNACE_ADDITIONS+"')) order by matl.material_desc";
				}else if(sub_unit.equals(Constants.SUB_UNIT_LRF)) {
					hql ="select matl.material_desc from mstr_process_consumables matl, (select cons.material_id from intf_sms_prod_conf_heat ifc, " +
						 " trns_lrf_heat_arcing_dtls ad, trns_lrf_heat_cons_lines cons where ifc.heat_number = ad.heat_id and ad.arc_si_no = cons.arcing_si_no " +
						 " and to_date(ifc.production_date) = to_date(sysdate-1) group by cons.material_id) mat where matl.material_id = mat.material_id order by matl.material_desc";
				}
		
			ls = (List<String>) getResultFromCustomQuery(hql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	@Transactional
	public List<EOFHeatLogRpt> getHeatPostedList(String sub_unit) {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside getConsumedMaterialList ");
		List<EOFHeatLogRpt> retLi = new ArrayList<EOFHeatLogRpt>();
		EOFHeatLogRpt obj;
		String hql = null;
		try {
			if(sub_unit.equals(Constants.SUB_UNIT_EOF)) {
				hql="select ehd.heat_id, ifc.psn, ifc.grade, ehd.trns_si_no, ifc.work_centre_heat from intf_sms_prod_conf_heat ifc, "
					+ " trns_eof_heat_dtls ehd where ifc.heat_number = ehd.heat_id and to_date(ifc.production_date) = to_date(sysdate-1)";
			}else if(sub_unit.equals(Constants.SUB_UNIT_LRF)) {
				hql="select main_q.heat_id, intf.psn, intf.grade, main_q.trns_no, (select s.sub_unit_name from trns_lrf_heat_dtls l, mstr_sub_unit_details s "+
					" where l.trns_si_no = main_q.trns_no and l.sub_unit_id = s.sub_unit_id) sub_unit from intf_sms_prod_conf_heat intf, "+
					" (select lhd.heat_id, max(lhd.trns_si_no) trns_no from intf_sms_prod_conf_heat ifc, trns_lrf_heat_dtls lhd where ifc.heat_number = lhd.heat_id "+ 
					" and to_date(ifc.production_date) = to_date(sysdate-1) group by lhd.heat_id ) main_q where main_q.heat_id = intf.heat_number";
			}
			List ls= getResultFromCustomQuery(hql);
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[]=(Object[]) it.next();
				obj = new EOFHeatLogRpt();
				obj.setHeat_no((null==rows[0])?null:rows[0].toString());
				obj.setAim_psn((null==rows[1])?null:rows[1].toString());
				obj.setGrade((null==rows[2])?null:rows[2].toString());
				obj.setTrns_si_no((null==rows[3])?null:Integer.parseInt(rows[3].toString()));
				obj.setUnit((null==rows[4])?null:rows[4].toString());
				
				retLi.add(obj);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retLi;
	}

	@Override
	@Transactional
	public List<String> getHeatwiseActualMaterialConsumed(Integer transSiNo, String sub_unit, String heat_number) {
		// TODO Auto-generated method stub
		logger.info(EofProductionDaoImpl.class+" Inside getConsumedMaterialList ");
		List<String> retLi = new ArrayList<String>();
		String hql = null;
		try {
			if(sub_unit.equals(Constants.SUB_UNIT_EOF)) {
				hql="select mstr.material_desc, main_qry.qty from mstr_process_consumables mstr, (select mat.material_id , cons.qty from "
					+ " trns_eof_heat_cons_materials cons right outer join (select cons.material_id from intf_sms_prod_conf_heat ifc, trns_eof_heat_dtls ehd, "
					+ " trns_eof_heat_cons_materials cons where ifc.heat_number = ehd.heat_id and ehd.trns_si_no = cons.trns_eof_si_no and to_date(ifc.production_date) "
					+ " = to_date(sysdate-1) group by cons.material_id) mat on mat.material_id = cons.material_id and cons.trns_eof_si_no = "+transSiNo+") main_qry "
					+ " where mstr.material_id = main_qry.material_id and mstr.material_type in (select lookup_id from app_lookups l where l.lookup_type = '"+Constants.MATERIAL_TYPE+"' "
					+ " and l.lookup_code in ('"+Constants.LADLE_ADDITIONS+"', '"+Constants.FURNACE_ADDITIONS+"')) order by mstr.material_desc";
			}else if(sub_unit.equals(Constants.SUB_UNIT_LRF)) {
				hql="select mstr.material_desc, main_qry.qty from mstr_process_consumables mstr, (select mat.material_id, sum(cons.consumption_qty) qty "+
					" from trns_lrf_heat_arcing_dtls ad join trns_lrf_heat_cons_lines cons on ad.arc_si_no = cons.arcing_si_no right outer join "+
					" (select cons.material_id from intf_sms_prod_conf_heat ifc, trns_lrf_heat_arcing_dtls ad, trns_lrf_heat_cons_lines cons where ifc.heat_number = ad.heat_id "+
					" and ad.arc_si_no = cons.arcing_si_no  and to_date(ifc.production_date) = to_date(sysdate-1) group by cons.material_id) mat on mat.material_id = cons.material_id "+
					" and ad.heat_id = '"+heat_number+"' group by mat.material_id) main_qry where mstr.material_id = main_qry.material_id order by mstr.material_desc";
			}
			List ls= getResultFromCustomQuery(hql);
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[]=(Object[]) it.next();
				retLi.add((null==rows[1])?"":rows[1].toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retLi;
	}

	@Override
	public List<HeatConsMaterialsDetailsLog> getMtrlConsLogByTrnsId(Integer trns_id) {
		logger.info("inside .. getMtrlConsLogByTrnsId.....");
		List<HeatConsMaterialsDetailsLog> list = new ArrayList<HeatConsMaterialsDetailsLog>();
		Session session = getNewSession(); 
		String hql = "Select a from HeatConsMaterialsDetailsLog a where a.trns_eof_si_no = "+trns_id;
		try { 
			list = (List<HeatConsMaterialsDetailsLog>) session.createQuery(hql).list();
		 } catch (Exception e) { 
			logger.error("error in getMtrlConsLogByTrnsId........"+e); 
		} 
		finally{
		 	close(session); 
		} 
		return list;
	}

	@Override
	public String saveOrUpdateMatrlCons(List<HeatConsMaterialsDetails> updLi, List<HeatConsMaterialsDetailsLog> logLi) {
		// TODO Auto-generated method stub
		logger.info("inside .. saveOrUpdateMatrlCons.....");
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if (updLi != null) {
				for(HeatConsMaterialsDetails consObj : updLi) {
					if(consObj.getQty() != null) {
						if(consObj.getMtr_cons_si_no() != null)
							session.update(consObj);
						else
							session.save(consObj);
					}
				}
			}

			if (logLi != null) {
				for(HeatConsMaterialsDetailsLog logObj : logLi) {
					session.save(logObj);
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
			logger.error(HeatProceeEventDaoImpl.class + " Inside furnaceAdditionMtrlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Transactional
	@Override
	public String eofElectrodeUsageTrnsSaveOrUpdate(EofElectrodeUsageModel obj) {
		logger.info("inside .. eofElectrodeUsageTrnsSaveOrUpdate.....");
		String result="";
		Session session = getNewSession();
		try {
			begin(session);
			if(obj.getElectrodeTransId()!=null) {
				session.update(obj);
			}else {
				session.save(obj);
			}
			
			commit(session);
			result = Constants.SAVE;
		} catch (ConstraintViolationException e) {	
			logger.error(HeatProceeEventDaoImpl.class + " Inside eofElectrodeUsageTrnsSaveOrUpdate Exception..", e);
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside eofElectrodeUsageTrnsSaveOrUpdate Exception..", e);
			e.printStackTrace();
			rollback(session);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;
	}

	@Transactional
	@Override
	public EofElectrodeUsageModel eofElectrodeUsageTrnsById(Integer id) {
		logger.info("inside .. eofElectrodeUsageTrnsById.....");
		EofElectrodeUsageModel ls = null;
		try {
			String sql="select a from EofElectrodeUsageModel a where electrodeTransId="+id ;
			List lst =  getResultFromNormalQuery(sql);
			if(!lst.isEmpty()) {
				ls =(EofElectrodeUsageModel) getResultFromNormalQuery(sql).get(0);
			}
		}catch (Exception e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside eofElectrodeUsageTrnsById Exception..", e);
			e.printStackTrace();
		}
		return ls;
	}

	@Transactional
	@Override
	public EofElectrodeUsageModel getElectrodeStatusByUnitAndLkp(Integer sub_unit_id, Integer electrodeId,
			Integer trans_si_no) {
		// TODO Auto-generated method stub
		EofElectrodeUsageModel ls=null;
		try {
			String sql="select a from EofElectrodeUsageModel a where subUintId="+sub_unit_id +" and electrodeId ="+electrodeId +" and eofHeatTransId="+trans_si_no;
			List lst =  getResultFromNormalQuery(sql);
			if(!lst.isEmpty()) {
			ls =(EofElectrodeUsageModel) getResultFromNormalQuery(sql).get(0);
			}
		}catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
		}
		return ls;
	}
	
	@Transactional
	@Override
	public EofHeatDetails getEofTrnsNo(String heat_no) {
		EofHeatDetails trns_sl_no = null;
		String sql="select a from EofHeatDetails a where heat_id='"+heat_no+"'";
		List lst =  getResultFromNormalQuery(sql);
		if(!lst.isEmpty()) {
			trns_sl_no =  (EofHeatDetails) getResultFromNormalQuery(sql).get(0);
			
			return trns_sl_no;			
		}
		return null;
	} 
	
}