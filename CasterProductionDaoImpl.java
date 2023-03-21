package com.smes.trans.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smes.admin.model.AppUserAccountDetails;
import com.smes.masters.dao.impl.LookupMasterDao;
import com.smes.masters.model.LookupMasterModel;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CCMHeatSeqGenModel;
import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.CCMTundishDetailsModel;
import com.smes.trans.model.CastPlanDetModel;
import com.smes.trans.model.CastRunningStatusModel;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.MtubeTrnsModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.TundishTrnsHistoryModel;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("CasterProductionDao")
public class CasterProductionDaoImpl extends GenericDaoImpl<CastRunningStatusModel, Long> implements CasterProductionDao {

	private static final Logger logger = Logger.getLogger(CasterProductionDaoImpl.class);
	
	@Autowired
	CCMHeatSeqGenModelDao ccmHeatSeqDao;

	@Autowired
	LookupMasterDao lookupmstrDao;
	
	@Autowired
	LRFProductionDao lrfProdDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public CastRunningStatusModel getRunningIdDetByHeatPlanNo(Integer heat_planNo) {
		logger.info("inside CasterProductionDaoImpl ..getRunningIdDetByHeatPlanNo...."+ CasterProductionDaoImpl.class);
		
		String sql="select a.running_id AS runid, a.tot_plan_heats, TO_CHAR (a.cast_start_date, 'DD/MM/YYYY HH:MI') from CastRunningStatusModel a,CastPlanDetModel b where  a.running_id = b.running_id AND b.plan_id = "
				+heat_planNo+ " GROUP BY a.running_id, a.tot_plan_heats, a.cast_start_date ORDER BY a.running_id";
		CastRunningStatusModel obj=null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try{
		
		List ls =(List<CastRunningStatusModel>) getResultFromNormalQuery(sql);
		Iterator it = ls.iterator();
		while(it.hasNext()){
			Object rows[] = (Object[])it.next();
			obj=new CastRunningStatusModel();
			obj.setRunning_id((null==rows[0])?null:Integer.parseInt(rows[0].toString()));
			obj.setTot_plan_heats((null==rows[1])?null:Integer.parseInt(rows[1].toString()));
			obj.setCast_start_date((null == rows[2]) ? null : df.parse(rows[2].toString()));
			
		}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return obj;
	
}

	@Override
	public String saveRunningIdDet(CastRunningStatusModel runObj, Integer userId) {
		String result = Constants.SAVE;
		try {
			logger.info(CasterProductionDaoImpl.class+" Inside saveRunningIdDet ");
			create(runObj);

		} catch (Exception e) {
			logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveAllCastDetails(
			Hashtable<String, Object> htObj) {
		// TODO Auto-generated method stub
		
		String result = "";
		Session session=getNewSession();
		Integer runningId = null;
		try{
			begin(session);
			//saving cast running status parent model  
			if((CastRunningStatusModel)htObj.get("CAST_RUN_DET")!=null)
			{
			session.save((CastRunningStatusModel)htObj.get("CAST_RUN_DET"));
			}
			//saving cast heat plan details child model
			if((List<CastPlanDetModel>)htObj.get("CAST_PLAN_DET")!=null)
			{
				List<CastPlanDetModel> plancastList=(List<CastPlanDetModel>)htObj.get("CAST_PLAN_DET");
				CastRunningStatusModel runcast=(CastRunningStatusModel)htObj.get("CAST_RUN_DET");
				
				for (CastPlanDetModel castPlanDetModel : plancastList) {
					runningId=runcast.getRunning_id();
					castPlanDetModel.setRunning_id(runningId);
					session.save(castPlanDetModel);
				}
				
			
			}
			
			commit(session);
			result =Constants.SAVE+" and New RunId Generated: "+runningId;
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return result;
		
	}

	@Override
	public CastRunningStatusModel getRunIdDetWithRunId(Integer runId) {
		logger.info("inside CasterProductionDaoImpl ..getRunIdDetWithRunId...."+ CasterProductionDaoImpl.class);
		
		String sql="select a.tot_plan_heats, TO_CHAR (a.cast_start_date, 'DD/MM/YYYY HH:MI') from CastRunningStatusModel a,CastPlanDetModel b where  a.running_id = b.running_id AND a.running_id = "+runId ;
		CastRunningStatusModel obj=null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try{
		
		List ls =(List<CastRunningStatusModel>) getResultFromNormalQuery(sql);
		Iterator it = ls.iterator();
		while(it.hasNext()){
			Object rows[] = (Object[])it.next();
			obj=new CastRunningStatusModel();
			obj.setTot_plan_heats((null==rows[0])?null:Integer.parseInt(rows[0].toString()));
			obj.setCast_start_date((null == rows[1]) ? null : df.parse(rows[1].toString()));
			
		}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return obj;
	
}



	@Override
	public String saveAllCastHeatDetails(Hashtable<String, Object> htObj) {
		// TODO Auto-generated method stub
		logger.info("inside CasterProductionDaoImpl ..saveAllCastHeatDetails...."+ CasterProductionDaoImpl.class);
		String result = "";
		Session session=getNewSession();
		Integer caster_return_id = null;
		Integer caster_id = null;
		try{
			begin(session);
						
			if ((HeatStatusTrackingModel) htObj.get("CAST_HEAT_STATUS") != null) {
				HeatStatusTrackingModel hstm = (HeatStatusTrackingModel) htObj.get("CAST_HEAT_STATUS");
				session.update(hstm);
			}
			
			if ((HeatPlanLinesDetails) htObj.get("HEATPLAN_LINE_UPDATE") != null) {
				HeatPlanLinesDetails hpl = (HeatPlanLinesDetails) htObj
						.get("HEATPLAN_LINE_UPDATE");
				session.update(hpl);
			}
			
			//saving cast heat details 
			if((CCMHeatDetailsModel)htObj.get("CAST_HEAT_DET")!=null){
				CCMHeatDetailsModel heat=	(CCMHeatDetailsModel) htObj.get("CAST_HEAT_DET");
				//heat.setCast_return_id(caster_return_id);
				heat.setReturn_qty_id(caster_return_id);
				heat.setReturn_qty_id(caster_return_id);
				caster_id=(Integer) session.save(heat);
			}
			if((CCMHeatSeqGenModel)htObj.get("CAST_SEQ")!=null) {
				
				CCMHeatSeqGenModel seqObj=(CCMHeatSeqGenModel)htObj.get("CAST_SEQ");
//				CCMHeatSeqGenModel seqObjS=ccmHeatSeqDao.getCCMHeatSeqNo();
//				seqObj.setSeq_sl_no(seqObjS.getSeq_sl_no());
				session.merge(seqObj);
				if((CCMHeatDetailsModel)htObj.get("CAST_HEAT_DET")!=null){
					CCMHeatDetailsModel heat=	(CCMHeatDetailsModel) htObj.get("CAST_HEAT_DET");
					if(seqObj.getIs_primary().equals(1)) {
						heat.setSeq_group_no(seqObj.getLast_fly_cast_no());
					}else if(seqObj.getIs_flying().equals(1) ){
						heat.setSeq_group_no(seqObj.getLast_fly_cast_no());
					}
					session.update(heat);
				}
				if((CCMSeqGroupDetails)htObj.get("SEQ_GROUP_DTLS")!=null){
					session.save((CCMSeqGroupDetails)htObj.get("SEQ_GROUP_DTLS"));
				}
			}
			if(htObj.get("CCM_TUNDISH_DET")!=null) {//CCM_TUNDISH_DET
				CCMTundishDetailsModel tMdl=(CCMTundishDetailsModel) htObj.get("CCM_TUNDISH_DET");
				tMdl.setTrns_sl_no(caster_id);
				session.save(tMdl);
			}
			if((TundishTrnsHistoryModel)htObj.get("SEL_PREV_TUND_TRNS")!=null){
				session.update((TundishTrnsHistoryModel)htObj.get("SEL_PREV_TUND_TRNS"));
			}
			if((TundishTrnsHistoryModel)htObj.get("SEL_NEW_TUND_TRNS")!=null){
				session.save((TundishTrnsHistoryModel)htObj.get("SEL_NEW_TUND_TRNS"));
			}
			if((TundishTrnsHistoryModel)htObj.get("PREV_TUND_TRNS")!=null){
				session.update((TundishTrnsHistoryModel)htObj.get("PREV_TUND_TRNS"));
			}
			if((TundishTrnsHistoryModel)htObj.get("PREV_NEW_TUND_TRNS")!=null){
				session.save((TundishTrnsHistoryModel)htObj.get("PREV_NEW_TUND_TRNS"));
			}
			commit(session);
			result =Constants.SAVE;
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return result;
		
	}

	@Override
	public String saveCastDetails(CCMHeatDetailsModel model) {
		logger.info("inside CasterProductionDaoImpl ..saveCastDetails...."+ CasterProductionDaoImpl.class);
		String result = "";
		Session session=getNewSession();
		
		try{
			begin(session);
			
			session.saveOrUpdate(model);
			
			commit(session);
			result =Constants.SAVE;
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return result;
	}

	//@Transactional
	@Override
	public String saveCCMretQty(CCMHeatDetailsModel model,String user,String heat_id) {
		String result="";
		HeatStatusTrackingModel heatTrackObj = null;
		ReladleTrnsDetailsMdl rectMdl=getReladleMdlByCasterId(model.getTrns_sl_no());
		try {
			
		if(rectMdl==null) {//creating new record		
			if(model.getReturn_qty() != null && model.getReturn_qty()>0) {
				List<LookupMasterModel> retTypes = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CAST_RET_TYPE", 1);
				LRFHeatDetailsModel lrfHeatObj = lrfProdDao.getLRFHeatDetailsByHeatNo(heat_id, model.getHeat_counter());
				float retQty = Math.round(model.getReturn_qty());
				float steelwt = Math.round(model.getSteel_ladle_wgt());
				//float half = steelwt / 2;
				
				model.setSteel_wt_orig(steelwt);
				model.setSteel_ladle_wgt(steelwt - retQty);
				saveCastDetails(model);//updating ccm heat dtls 
				ReladleTrnsDetailsMdl retMdl=new ReladleTrnsDetailsMdl();
				retMdl.setTrns_sl_no(null);
				retMdl.setHeat_id(heat_id);
				retMdl.setSub_unit_id(model.getSub_unit_id());
				retMdl.setHeatRefId(model.getTrns_sl_no());
				retMdl.setAim_psn_no(model.getPsn_no());
				retMdl.setAct_qty(retQty);
				retMdl.setSteelLadleNo(model.getSteel_ladle_no());
				retMdl.setIs_processed("N");
				retMdl.setHeat_counter(model.getHeat_counter());
				retMdl.setHeatPlanId(model.getHeat_plan_id());
				retMdl.setHeatPlanLineNo(model.getHeat_plan_line_no());

				if (retQty == steelwt) {
					Integer full = retTypes.stream().filter(p -> p.getLookup_value().equals(Constants.CCM_RETURN_HEAT_FULL))
						.mapToInt(p -> p.getLookup_id()).findFirst().getAsInt();
					retMdl.setReturn_type(full);
				} else if (retQty != steelwt && retQty != 0) {
					Integer partial = retTypes.stream().filter(p -> p.getLookup_value().equals(Constants.CCM_RETURN_HEAT_PARTIAL))
						.mapToInt(p -> p.getLookup_id()).findFirst().getAsInt();
					retMdl.setReturn_type(partial);
				}
				retMdl.setReturn_date(new Date());
				retMdl.setReason(model.getReturn_reason());
				retMdl.setBalance_qty(retQty);
				retMdl.setRecord_status(1);
				retMdl.setCreated_date_time(new Date());
				retMdl.setCreated_by(Integer.parseInt(user));
				retMdl.setDisp_temp(lrfHeatObj.getTap_temp());
				if((steelwt - retQty) == 0.0 || (steelwt - retQty) == 0){
					heatTrackObj = getHeatStatusTrackingModelByHeatIdWithoutStatus(retMdl.getHeat_id(), retMdl.getHeat_counter());
					heatTrackObj.setUnit_process_status(Constants.UNIT_PROCESS_STATUS_FULL_RET);
				}
				saveOrUpdateRetQtyMdlReladleProcess(retMdl, heatTrackObj);
			}
		}else {//updating record
			if(model.getReturn_qty()>0) {
				List<LookupMasterModel> retTypes = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CAST_RET_TYPE", 1);
				
				float retQty = Math.round(model.getReturn_qty());
				float steelwt = Math.round(model.getSteel_ladle_wgt());
				//float half = steelwt / 2;
				
				if(rectMdl.getIs_processed().equals("N")) {
					model.setSteel_ladle_wgt(steelwt - retQty);
				saveCastDetails(model);//updating ccm heat dtls 
				ReladleTrnsDetailsMdl retMdl=rectMdl;
				//retMdl.setTrns_sl_no(rectMdl.getTrns_sl_no());
				retMdl.setHeat_id(heat_id);
				retMdl.setSub_unit_id(model.getSub_unit_id());
				retMdl.setHeatRefId(model.getTrns_sl_no());
				retMdl.setAim_psn_no(model.getPsn_no());
				retMdl.setAct_qty(retQty);
				retMdl.setHeat_counter(model.getHeat_counter());
				retMdl.setSteelLadleNo(model.getSteel_ladle_no());
				if (retQty == steelwt) {
					Integer full = retTypes.stream().filter(p -> p.getLookup_value().equals(Constants.CCM_RETURN_HEAT_FULL))
							.mapToInt(p -> p.getLookup_id()).findFirst().getAsInt();
					retMdl.setReturn_type(full);
				} else if (retQty != steelwt && retQty != 0) {
					Integer partial = retTypes.stream().filter(p -> p.getLookup_value().equals(Constants.CCM_RETURN_HEAT_PARTIAL))
							.mapToInt(p -> p.getLookup_id()).findFirst().getAsInt();
					retMdl.setReturn_type(partial);
				}
				retMdl.setReturn_date(new Date());
				retMdl.setReason(model.getReturn_reason());
				retMdl.setBalance_qty(retQty);
				retMdl.setRecord_status(1);
				retMdl.setUpdated_date_time(new Date());
				retMdl.setUpdated_by(Integer.parseInt(user));
				if((steelwt - retQty) == 0.0 || (steelwt - retQty) == 0){
					heatTrackObj = getHeatStatusTrackingModelByHeatIdWithoutStatus(retMdl.getHeat_id(), retMdl.getHeat_counter());
					heatTrackObj.setUnit_process_status(Constants.UNIT_PROCESS_STATUS_FULL_RET);
				}
				saveOrUpdateRetQtyMdlReladleProcess(retMdl, heatTrackObj);
				}
			}
		}
		result=Constants.SAVE;
		}catch(Exception ex) {
			result=Constants.SAVE_FAIL;
			ex.printStackTrace();
		}
		return result;
	}
	
	public String saveOrUpdateRetQtyMdlReladleProcess( ReladleTrnsDetailsMdl mdl, HeatStatusTrackingModel heatTrackObj){
		String result = "";
		Session session=getNewSession();
		
		try{
			begin(session);
			if(heatTrackObj != null){
				session.update(heatTrackObj);
			}
			session.saveOrUpdate(mdl);
			
			commit(session);
			result =Constants.SAVE;
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return result;
	}
	
	@Transactional
	@Override
	public ReladleTrnsDetailsMdl getReladleMdlByCasterId(Integer  casterID){
		Session session=getNewSession();
		ReladleTrnsDetailsMdl model=null;
		try{
			Criteria cr=session.createCriteria(ReladleTrnsDetailsMdl.class);
			cr.add(Restrictions.eq("heatRefId", casterID));
			model=(ReladleTrnsDetailsMdl) cr.uniqueResult();
		} 
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return model;
	}
	
	@Transactional
	@Override
	public HeatStatusTrackingModel getHeatStatusTrackingModelByHeatId(String heat_id){
		String result = "";
		Session session=getNewSession();
		HeatStatusTrackingModel model=null;
		try{
			Criteria cr=session.createCriteria(HeatStatusTrackingModel.class);
			cr.add(Restrictions.eq("heat_id", heat_id));
			cr.add(Restrictions.eq("unit_process_status","COMPLETED"));
			model=(HeatStatusTrackingModel) cr.uniqueResult();
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return model;
	}
	@Transactional
	@Override
	public HeatStatusTrackingModel getHeatStatusTrackingModelByHeatIdWithoutStatus(String heat_id, Integer heat_counter){
		String result = "";
		Session session=getNewSession();
		HeatStatusTrackingModel model=null;
		try{
			Criteria cr=session.createCriteria(HeatStatusTrackingModel.class);
			cr.add(Restrictions.eq("heat_id", heat_id));
			cr.add(Restrictions.eq("heat_counter", heat_counter));
			//cr.add(Restrictions.eq("unit_process_status","COMPLETED"));
			model=(HeatStatusTrackingModel) cr.uniqueResult();
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			
		}finally {
			close(session);
		}

		return model;
	}
	@Transactional
	@Override
	public String updateHeatStatusTrack(HeatStatusTrackingModel model){
		String result = "";
		Session session=getNewSession();
		String res=null;
		
		try {
			begin(session);
			session.saveOrUpdate(model);
			result=Constants.UPDATE;
			commit(session);
		}catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			result=Constants.UPDATE_FAIL;	
		}finally {
			close(session);
		}
		/*try{
			begin(session);
			session.saveOrUpdate(model);		
			
			res= session.doReturningWork( new ReturningWork<String>(){
				  public String execute(Connection connection) throws SQLException {
					  CallableStatement cstmt = null;
					  try{
						  cstmt = connection.prepareCall ("{call SAP_IFACE_ENPG.send_heat_to_sap (?, ?, ?)}");
						  cstmt.registerOutParameter (3, Types.INTEGER);					    
						  cstmt.setString (1, model.getHeat_id());
						  cstmt.setInt (2, model.getHeat_counter()); 			   
						  cstmt.execute();
						  Integer p_out = cstmt.getInt(3);
						  return p_out.toString();
					  }finally{
						  cstmt.close();
					  }
				  }
				});
			if(res.equals("0"))
				result=Constants.UPDATE_FAIL;
			else
				result=Constants.UPDATE;
			commit(session);
		} 
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			result=Constants.UPDATE_FAIL;	
		}finally {
			close(session);
		}*/

		return result;
	}
	
	@Transactional
	@Override
	public String saveORUpdatetCCMConsMtrl(CCMHeatConsMaterialsDetails model){
		String result = "";
		Session session=getNewSession();
		
		try{
			begin(session);
			session.saveOrUpdate(model);
			commit(session);
			result=Constants.UPDATE;
		} 
	
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			result=Constants.UPDATE_FAIL;
			
		}finally {
			close(session);
		}

		return result;
	}
	@Transactional
	public List<CCMSeqGroupDetails> getGroupSeqNos(Integer unit_id){
		List<CCMSeqGroupDetails> lst=new ArrayList<CCMSeqGroupDetails>();
		
		try{
			String query = "select a from CCMSeqGroupDetails a where a.sub_unit_id="+unit_id+" and a.seq_status = 1 order by a.seq_group_no";
			lst=(List<CCMSeqGroupDetails>) getResultFromNormalQuery(query);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception.. getGroupSeqNos..."+e.getMessage());
		}
		
		return lst;
	}
	
	@Transactional
	public List<CCMHeatDetailsModel> getSeqGroupNosHeats(Integer unit_id,String group_no){	
        List<CCMHeatDetailsModel> list=new ArrayList<CCMHeatDetailsModel>();
		try {
			String sql = "Select a from CCMHeatDetailsModel a where a.seq_group_no='"+group_no+"' and a.sub_unit_id="+unit_id;
			list=(List<CCMHeatDetailsModel>) getResultFromNormalQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getCasterHeatsWithRunId..."+e.getMessage());
		}
		
		return list;
	}

	@Override
	public MtubeTrnsModel getMtubelife(Integer mtube_sl_no) {
		// TODO Auto-generated method stub
		logger.info("inside getMtubelife ..");
		MtubeTrnsModel obj = null;
		try {
			/*
			 * String
			 * sql="select (select sum(mtube_life) from trns_ccm_mtubes mt where mt.ccm_mtube_sl_no = "
			 * +mtube_sl_no+" and mtube_status" +
			 * " in ('READY FOR USE','RUNNING')) cur_life, (select sum(mtube_life) from trns_ccm_mtubes mt where mt.ccm_mtube_sl_no = "
			 * +mtube_sl_no+"" +
			 * " and mt.IS_CLEANED = 'Y') cleaning_life, (select sum(mtube_life) from trns_ccm_mtubes mt where mt.ccm_mtube_sl_no = "
			 * +mtube_sl_no+") total_life from dual ";
			 */
			String sql="select" + 
					"(select sum(mtube_life) from trns_ccm_mtubes mt where mt.ccm_mtube_sl_no = "+mtube_sl_no+" and mtube_status in ('READY FOR USE','RUNNING')) cur_life," + 
					" (select sum(mtube_life) from trns_ccm_mtubes mt where mt.ccm_mtube_sl_no = "+mtube_sl_no+" and mt.IS_CLEANED = 'Y') cleaning_life," + 
					"  (select sum(mtube_life) from trns_ccm_mtubes mt where mt.ccm_mtube_sl_no = "+mtube_sl_no+") total_life" + 
					"  ,(select nvl(mtube_life,0) curr_cleaning_life from mstr_ccm_mtubes mstr,trns_ccm_mtubes mt where mstr.ccm_mtube_sl_no=mt.ccm_mtube_sl_no and mt.ccm_mtube_sl_no="+mtube_sl_no+" and mt.is_cleaned='Y'and mt.ccm_mtube_trns_id in (select max(trns_latest.ccm_mtube_trns_id) from trns_ccm_mtubes trns_latest where trns_latest.ccm_mtube_sl_no = mstr.ccm_mtube_sl_no and trns_latest.is_cleaned='Y'))current_life" + 
					"  from dual";	
			List<MtubeTrnsModel> ls =(List<MtubeTrnsModel>) getResultFromCustomQuery(sql);
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new MtubeTrnsModel();
				obj.setMtube_life((null==rows[0])?null:Integer.parseInt(rows[0].toString()));
				obj.setCleaning_life((null==rows[1])?null:Integer.parseInt(rows[1].toString()));
				obj.setTotal_life((null==rows[2])?null:Integer.parseInt(rows[2].toString()));
				obj.setCurrent_life((null==rows[3])?null:Integer.parseInt(rows[3].toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error in getMtubelife........"+e);
		}
		return obj;
	}
	
	public List<HeatChemistryChildDetails> getLrfLiftChemDetails(String heat_id, Integer heat_counter, Integer aim_psn) {
		// TODO Auto-generated method stub
		logger.info("inside getLrfLiftChemDetails ..");
		List<HeatChemistryChildDetails> retlist = new ArrayList<HeatChemistryChildDetails>();
		try {
			String hql = "select lkp.lookup_code, ps.uom, ps.value_min, ps.value_max, ps.value_aim,ch.aim_value, ch.dtls_si_no from mstr_psn_chemistry ps, "
					+ " trns_heat_chemistry_child ch, app_lookups lkp where ps.element_id = ch.element and ch.element = lkp.lookup_id and ps.psn_hdr_sl_no = "+aim_psn+""
					+ " and ps.chem_level = (select lookup_id  from app_lookups lk1 where lk1.lookup_value = 'LRF_LIFT_CHEM' and lk1.lookup_type = 'CHEM_LEVEL') "
					+ " and ch.sample_si_no = (select max(chd.sample_si_no) from trns_heat_chemistry_hdr chd  where chd.heat_id = '"+heat_id+"' and chd.heat_counter="+heat_counter+""
					+ " and chd.analysis_type = (select lookup_id  from app_lookups lk2 where lk2.lookup_value = 'LRF_LIFT_CHEM' and lk2.lookup_type = 'CHEM_LEVEL') and chd.final_result = 1) order by lkp.attribute1";
			List<?> ls = getResultFromCustomQuery(hql);
			HeatChemistryChildDetails cb;
			Iterator<?> it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				cb = new HeatChemistryChildDetails();
				cb.setElementName((null == rows[0]) ? null : rows[0].toString());
				cb.setUom((null == rows[1]) ? null : rows[1].toString());
				cb.setMin_value((null == rows[2]) ? null : Double.parseDouble(rows[2].toString()));
				cb.setMax_value((null == rows[3]) ? null : Double.parseDouble(rows[3].toString()));
				cb.setPsn_aim_value((null == rows[4]) ? null : Double.parseDouble(rows[4].toString()));
				cb.setAim_value((null == rows[5]) ? null : Double.parseDouble(rows[5].toString()));
				cb.setDtls_si_no((null == rows[6]) ? null : Integer.parseInt(rows[6].toString()));
				
				retlist.add(cb);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getLrfLiftChemDetails........"+e);
		}
		return retlist;
	}

	@Transactional
	public List<CCMSeqGroupDetails> getCloseGroupSeqNos(Integer unit_id) {
		List<CCMSeqGroupDetails> lst=new ArrayList<CCMSeqGroupDetails>();
		try{
			String query = "select a from CCMSeqGroupDetails a where a.sub_unit_id="+unit_id+" and a.seq_status = 0 and a.delay_entry = 'N' order by a.seq_group_no";
			lst=(List<CCMSeqGroupDetails>) getResultFromNormalQuery(query);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception.. getCloseGroupSeqNos..."+e.getMessage());
		}
		return lst;
	}
	
	@Transactional
	public CCMHeatDetailsModel getHeatDtlsByHeatId(String heat_id,Integer heat_counter) {
		logger.info("inside getHeatDtlsByHeatId ..");
		 CCMHeatDetailsModel model =null;
		String sql = "Select a from CCMHeatDetailsModel a where a.heat_no='"+heat_id+"' and a.heat_counter="+heat_counter;
		model=(CCMHeatDetailsModel) getResultFromNormalQuery(sql).get(0);
		
		return model;
	}
	
	
	@Transactional
	@Override
	public void sendFailedHeatData(String heat_no) {
		Session session = getNewSession();
		String heatNotCompletedQuery = "Select LOG_TYPE,LOG_SOURCE,CREATED_USER,CREATED_DATE,ERROR_MSG from INTF_SALEM_LOG where HEAT_ID='"+heat_no+"'"+ " and STATUS='FAILED'";		
		try {
			List<?> failedStatusList = session.createSQLQuery(heatNotCompletedQuery).list();
			Iterator<?> failedStatusIterator = failedStatusList.iterator();
			String msg_str="",header="Error for SAP Post Heat No :"+heat_no;
			msg_str+="heat ID = "+heat_no;
			int rno=0;
			while(failedStatusIterator.hasNext()) {
				Object[] rows=(Object[]) failedStatusIterator.next();
				msg_str+="\nRowNo= "+(++rno);
				msg_str+="\nLOG_TYPE= "+rows[0].toString();
				msg_str+="\nLOG_SOURCE= "+rows[1].toString();
				msg_str+="\nERROR_MSG= "+rows[4].toString();
				msg_str+="\n";				
			}		
			sendDateToMail(msg_str,header);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public void sendDateToMail(String failedHeatData, String header) {
		try {
				 String from=  "salem.mesteam@jsw.in"; 	
				 String to[]= {"guruprasath.r@jsw.in","kathiravan.palanisami@jsw.in","rezon.lambert@jsw.in"}; 					 				 
				 java.util.Properties props = new java.util.Properties(); 
				 props.put("mail.smtp.host",Constants.MAILHOST);  
				 props.put("mail.smtp.port",Constants.MAILPORT); 				 
				 javax.mail.Session mailSession = javax.mail.Session.getDefaultInstance(props); 
				 // Construct the message 
				 Message message = new MimeMessage(mailSession); 
				 message.setFrom(new InternetAddress(from)); 				 
				 InternetAddress[] allInternetAddress = new InternetAddress[to.length];
				 for(int index=0; index < to.length; index++) {
					 allInternetAddress[index] = new InternetAddress(to[index]);
				 }
				 message.setRecipients(Message.RecipientType.TO, allInternetAddress);						
				 message.setSubject(header);  
				 message.setText(failedHeatData);  
				 // Send the message 
				 Transport.send(message); 			
				
		} catch (Exception e) {
			logger.error("error.. while sending email ......"+e);
		}				
	}

	@Transactional
	@Override
	public List<CommonCombo> getheatId(Integer lookup_id) {
		// TODO Auto-generated method stu
		 List<CommonCombo> list = new ArrayList<CommonCombo>();
		 try {
				String sql = "select c.trns_sl_no, c.heat_no from LookupMasterModel a" + 
						" ,ProductSectionMasterModel b,CCMHeatDetailsModel c,HeatChemistryHdrDetails d" + 
						" where a.lookup_id=b.material_id and c.product_id=b.ccm_mat_sec_id and a.lookup_id='"+lookup_id+"' and c.spectro_chem in (1,0)" + 
						" and c.heat_no=d.heat_id and d.approve=0 and c.sub_unit_id=d.sub_unit_id";
			List ls = getResultFromNormalQuery(sql); 
			CommonCombo cb;
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				cb = new CommonCombo();
				cb.setKeyval((null == rows[0]) ? null : rows[0].toString());
				cb.setTxtvalue(((null == rows[1]) ? null : rows[1].toString()));
				list.add(cb);
			}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				logger.error("Exception.. getCasterHeatId's..."+e.getMessage());
			}
			return list;
	}

}