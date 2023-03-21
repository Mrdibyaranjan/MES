package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.masters.model.SteelLadleMaintnModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLadleMaintStatusModel;
import com.smes.trans.model.StLadlePartsMaintLogModel;
import com.smes.trans.model.StLadleStatusTrackHistoryModel;
import com.smes.trans.model.StLdlHeatingDtls;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleMaintenanceModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("steelLadleMaintnDao")
public class SteelLadleMaintnDaoImpl extends
		GenericDaoImpl<SteelLadleMasterModel, Long> implements
		SteelLadleMaintnDao {
	private static final Logger logger = Logger
			.getLogger(SteelLadleMaintnDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public SteelLadleMasterModel getChangeStatusByStLadle(Integer st_ladle_no_id) {
		logger.info("inside .. getChangeStatusByStLadle.."
				+ SteelLadleMaintnDaoImpl.class);
		List<SteelLadleMasterModel> list = new ArrayList<SteelLadleMasterModel>();
		SteelLadleMasterModel ladleObj = new SteelLadleMasterModel();
		Session session = getNewSession();
		try {

			String hql = "select a from SteelLadleMasterModel a where a.steel_ladle_si_no ="
					+ st_ladle_no_id + "";
			list = session.createQuery(hql)
					.list();
			ladleObj = list.get(0);
			
		} catch (Exception e) {
			logger.error("error in getChangeStatusByStLadle.." + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return ladleObj;
	}
	
	@Override
	public List<SteelLadleTrackingModel> getAvailableLadleStatus(){
		
		Session session = getNewSession();
		List<SteelLadleTrackingModel> list=null;
		try {

			String hql = "select a from SteelLadleTrackingModel a where a.ladle_status =1";
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			logger.error("error in getAvaiableLadle........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		
		return list;
	}
	
	@Override
	public SteelLadleTrackingModel getAvailableLadleByLadleId(Integer ladleId){
		
		Session session = getNewSession();
		SteelLadleTrackingModel list=null;
		try {

			String hql = "select a from SteelLadleTrackingModel a where a.st_ladle_si_no ="+ladleId;
			list = (SteelLadleTrackingModel) session.createQuery(hql).list().get(0);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getAvaiableLadle........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		
		return list;
	}
	
	
	@Transactional
	@Override
	public String  updateSteelLadle(SteelLadleTrackingModel model){
		String result = Constants.UPDATE;
		Session session = getNewSession();
		List<SteelLadleTrackingModel> list=null;
		try {
			logger.info("inside updatesteel ladle"+model.getSt_ladle_si_no());
			session.update(model);
			session.flush();
			
		} catch (Exception e) {
			result = Constants.SAVE_FAIL;
			logger.error("error in updating steel ladle......." + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		
		return result;
	}
	
	

	@Override
	public String stLadleSaveAndInsertHist(Hashtable<String, Object> mod_obj) {
		String result = Constants.SAVE;
		Session session = getNewSession();
		try {
			logger.info(SteelLadleMaintnDaoImpl.class
					+ " Inside stLadleSaveAndInsertHist ");
			begin(session);
			if ((SteelLadleMasterModel) mod_obj.get("UPDATE_ST_LADLE_MSTR") != null) {
				SteelLadleMasterModel obj = (SteelLadleMasterModel) mod_obj
						.get("UPDATE_ST_LADLE_MSTR");
				session.update(mod_obj
						.get("UPDATE_ST_LADLE_MSTR"));

			}
			if ((SteelLadleMaintnModel) mod_obj.get("INSERT_ST_LADLE_HIST") != null) {
				session.save(mod_obj
						.get("INSERT_ST_LADLE_HIST"));
			}
			if ((SteelLadleMaintnModel) mod_obj.get("UPDATE_ST_LADLE_HIST") != null) {
				session.update(mod_obj
						.get("UPDATE_ST_LADLE_HIST"));
			}

			commit(session);

		} catch (Exception e) {
			logger.error(SteelLadleMaintnDaoImpl.class
					+ " Inside stLadleSaveAndInsertHist Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;
	}

	@Override
	public SteelLadleMaintnModel getMaxLadle_sl_no(Integer steel_ladle_si_no) {
		logger.info("inside .. getMaxLadle_sl_no.."
				+ SteelLadleMaintnDaoImpl.class);
		List<SteelLadleMaintnModel> list = new ArrayList<SteelLadleMaintnModel>();
		SteelLadleMaintnModel ladlehistObj = new SteelLadleMaintnModel();
		Session session = getNewSession();
		try {
			String hql = "select a from SteelLadleMaintnModel a where a.st_ladle_sl_no=(select max(st_ladle_sl_no) from SteelLadleMaintnModel where steel_ladle_no ="
					+ steel_ladle_si_no + ")";
			list = session.createQuery(hql).list();
			ladlehistObj = list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getMaxLadle_sl_no.." + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return ladlehistObj;

	}

	@Override
	public SteelLadleTrackingModel getSteelLadleTracking(Integer ladle_id) {
		logger.info("inside .. getSteelLadleTracking....."
				+ SteelLadleMaintnDaoImpl.class);
		List<SteelLadleTrackingModel> list = new ArrayList<SteelLadleTrackingModel>();
		SteelLadleTrackingModel trackObj = new SteelLadleTrackingModel();
		Session session = getNewSession();
		try {
			String hql = "select a from SteelLadleTrackingModel a where a.st_ladle_si_no ="+ladle_id;
			list = session.createQuery(hql).list();
			if (list.size() > 0) {
				trackObj = list.get(0);
			}
		} catch (Exception e) {
			logger.error("error in getSteelLadleTracking........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}

		return trackObj;
	}

	@Override
	public String saveSteelLadleMaint(
			SteelLadleMaintenanceModel stlLdlMaintModel, SteelLadleMaintenanceModel prevStlLdlMaintModel,
			SteelLadleLifeModel stLdlLifeObj, SteelLadleTrackingModel stLdlTrackingObj) {
		String result = Constants.SAVE;
		Session session = getNewSession();
		try {
			logger.info(SteelLadleMaintnDaoImpl.class
					+ " Inside saveSteelLadleMaint ");
			begin(session);
			if(prevStlLdlMaintModel != null)
				session.update(prevStlLdlMaintModel);
			if(stLdlLifeObj != null)
				session.update(stLdlLifeObj);
			if(stLdlTrackingObj != null)
				session.update(stLdlTrackingObj);
			session.save(stlLdlMaintModel);
			commit(session);

		} catch (Exception e) {
			logger.error(SteelLadleMaintnDaoImpl.class
					+ " Inside saveSteelLadleMaint Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;
	}

	@Override
	public HeatStatusTrackingModel getHeatDtlsByStLadle(Integer st_ladle_id) {
		logger.info("inside .. getHeatDtlsByStLadle....."
				+ SteelLadleMaintnDaoImpl.class);
		List<HeatStatusTrackingModel> list = new ArrayList<HeatStatusTrackingModel>();
		HeatStatusTrackingModel trackObj = new HeatStatusTrackingModel();
		Session session = getNewSession();
		try {
			String hql = "select a from HeatStatusTrackingModel a where a.ladle_id ="+st_ladle_id+" and rownum < 2 order by a.heat_track_id desc";
			list = session.createQuery(hql).list();
			if (list.size() > 0 )  
				trackObj = list.get(0);
			else
				trackObj = null;
		} catch (Exception e) {
			logger.error("error in getHeatDtlsByStLadle........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return trackObj;
	}

	@Override
	public SteelLadleMaintenanceModel getPrevSteelLadleMaintByPart(Integer ladle_id,
			Integer part_id) {
		logger.info("inside .. getPrevSteelLadleMaintByPart....."
				+ SteelLadleMaintnDaoImpl.class);
		List<SteelLadleMaintenanceModel> list = new ArrayList<SteelLadleMaintenanceModel>();
		SteelLadleMaintenanceModel ladleMaintObj = new SteelLadleMaintenanceModel();
		Session session = getNewSession();
		try {
			String hql = "select a from SteelLadleMaintenanceModel a where a.ladle_id = "+ladle_id+" and a.part_id ="+part_id+" and a.last_heat is null and rownum < 2 order by a.ladle_maint_id desc";
			list = session.createQuery(hql).list();
			if(list.size() > 0)
				ladleMaintObj = list.get(0);
			else
				ladleMaintObj = null;
		} catch (Exception e) {
			logger.error("error in getPrevSteelLadleMaintByPart........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return ladleMaintObj;
	}

	@Override
	public List<SteelLadleMaintenanceModel> getStLdlMaintAfterPartChange(
			Integer ladle_id) {
		logger.info("inside .. getStLdlMaintAfterPartChange"
				+ SteelLadleMaintnDaoImpl.class);
		List<SteelLadleMaintenanceModel> list = new ArrayList<SteelLadleMaintenanceModel>();
		SteelLadleMaintenanceModel ladleMaintObj = new SteelLadleMaintenanceModel();
		Session session = getNewSession();
		try {
			String hql = "select a from SteelLadleMaintenanceModel a where a.ladle_id = "+ladle_id+" and (a.first_heat is null or a.start_date is null) order by a.ladle_maint_id desc";
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getPrevSteelLadleMaintByPart........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public List<SteelLadleMaintenanceModel> getStLdlLifeDtlsForStatusChange(
			Integer ladle_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getStLdlLifeDtlsForStatusChange....."
						+ SteelLadleMaintnDaoImpl.class);
		
		Session session=null;
		SteelLadleMaintenanceModel obj;
		List<SteelLadleMaintenanceModel> resultList = new ArrayList<SteelLadleMaintenanceModel>();
				
		String hql="select ht.heat_id, lt.st_ladle_life, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Inner Nozzle' and ll.heat_id = ht.heat_id),0) as InnerNozzle, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Outer Nozzle'and ll.heat_id = ht.heat_id),0) as OuterNozzle, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Random Plug' and ll.heat_id = ht.heat_id),0) as RandomPlug, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Directional Plug' and ll.heat_id = ht.heat_id),0) as DirectionalPlug, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Hybrid Plug' and ll.heat_id = ht.heat_id),0) as HybridPlug, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Fixed Plate' and ll.heat_id = ht.heat_id),0) as FixedPlate, "
				+ "nvl((select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id "
				+ "and ll.partsModel.part_name ='Movable Plate' and ll.heat_id = ht.heat_id),0) as MovablePlate from HeatStatusTrackingModel ht, SteelLadleTrackingModel lt "
				+ "where ht.ladle_id = lt.st_ladle_si_no and ht.ladle_id = "+ladle_id;
		
		/*hql="select ht.heat_id, lt.st_ladle_life, "
				+ " (select ll.trns_life from StLadleLifeHeatWiseModel ll where ll.steel_ladle_no = ht.ladle_id and ll.partsModel.part_name ='"+Constants.STEEL_LADLE_NOZZLE_WBC+"'),"
				+ " (select ll.trns_life from SteelLadleLifeModel ll where ll.steel_ladle_no = ht.ladle_id and ll.partsModel.part_name ='"+Constants.STEEL_LADLE_INNER_NOZZLE+"'),"
				+ " (select ll.trns_life from SteelLadleLifeModel ll where ll.steel_ladle_no = ht.ladle_id and ll.partsModel.part_name ='"+Constants.STEEL_LADLE_OUTER_NOZZLE+"'),"
				+ " (select ll.trns_life from SteelLadleLifeModel ll where ll.steel_ladle_no = ht.ladle_id and ll.partsModel.part_name ='"+Constants.STEEL_LADLE_PLUG_WELL_BLOCK+"'),"
				+ " (select ll.trns_life from SteelLadleLifeModel ll where ll.steel_ladle_no = ht.ladle_id and ll.partsModel.part_name ='"+Constants.STEEL_LADLE_PLUG+"')"
				+ " from HeatStatusTrackingModel ht, SteelLadleTrackingModel lt where ht.ladle_id = lt.st_ladle_si_no and ht.ladle_id = "+ladle_id;*/
		
		try{
			session=getNewSession();
			Query query = session.createQuery(hql);
			List ls=query.list();
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new SteelLadleMaintenanceModel();
				obj.setHeat_id((null==rows[0])?null:rows[0].toString());
				obj.setLadle_life((null==rows[1])?null:Integer.parseInt(rows[1].toString()));
				obj.setInner_nozzle((null==rows[2])?null:Integer.parseInt(rows[2].toString()));
				obj.setOuter_nozzle((null==rows[3])?null:Integer.parseInt(rows[3].toString()));
				obj.setRandom_plug((null==rows[4])?null:Integer.parseInt(rows[4].toString()));
				obj.setDirectional_plug((null==rows[5])?null:Integer.parseInt(rows[5].toString()));
				obj.setHybrid_plug((null==rows[6])?null:Integer.parseInt(rows[6].toString()));
				obj.setFixed_plate((null==rows[7])?null:Integer.parseInt(rows[7].toString()));
				obj.setMovable_plate((null==rows[8])?null:Integer.parseInt(rows[8].toString()));
				
				/*obj.setNozzle_wbc_life((null==rows[9])?null:Integer.parseInt(rows[2].toString()));
				obj.setPlug_wbc_life((null==rows[12])?null:Integer.parseInt(rows[5].toString()));
				obj.setPlug_life((null==rows[13])?null:Integer.parseInt(rows[6].toString()));*/
						
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
	public StLadleMaintStatusModel getPrevStladleMaintSts(Integer maint_sts_id, Integer ladle_id) {
		logger.info("inside .. getPrevStladleMaintSts....." + SteelLadleMaintnDaoImpl.class);
		List<StLadleMaintStatusModel> list = new ArrayList<StLadleMaintStatusModel>();
		StLadleMaintStatusModel ladleMaintObj = new StLadleMaintStatusModel();
		Session session = getNewSession();
		try {
			String hql = "select a from StLadleMaintStatusModel a where a.stladle_maint_status_id = "+maint_sts_id+" and a.stladle_id ="+ladle_id+" order by a.stladle_maint_status_id desc";
			list = session.createQuery(hql).list();
			if(list.size() > 0)
				ladleMaintObj = list.get(0);
			else
				ladleMaintObj = null;
		} catch (Exception e) {
			logger.error("error in getPrevStladleMaintSts........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return ladleMaintObj;
	}
	
	@Override
	public StLadleStatusTrackHistoryModel getStlLadleStsTrackHist(Integer st_ladle_track_id) {
		logger.info("inside .. getPrevStladleMaintSts....." + SteelLadleMaintnDaoImpl.class);
		List<StLadleStatusTrackHistoryModel> list = new ArrayList<StLadleStatusTrackHistoryModel>();
		StLadleStatusTrackHistoryModel ladleStsTrackObj = new StLadleStatusTrackHistoryModel();
		Session session = getNewSession();
		try {
			String hql = "select a from StLadleStatusTrackHistoryModel a where a.trns_stladle_track_id = "+st_ladle_track_id+" order by a.stladle_status_hist_id desc";
			list = session.createQuery(hql).list();
			if(list.size() > 0)
				ladleStsTrackObj = list.get(0);
			else
				ladleStsTrackObj = null;
		} catch (Exception e) {
			logger.error("error in getStlLadleStsTrackHist........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return ladleStsTrackObj;
	}
	
	//Scrap
	@Override
	public String saveSteelLadleMaintSts(StLadleMaintStatusModel prevStLdlMaintSts, StLadleMaintStatusModel stlLdlMaintStsModel,
			StLadlePartsMaintLogModel stlLdlPartMaintLogModel) {
			String result = Constants.SAVE;
			Session session = getNewSession();
			try {
				logger.info(SteelLadleMaintnDaoImpl.class
						+ " Inside saveSteelLadleMaint ");
				begin(session);
				if(prevStLdlMaintSts != null) { //&& prevStLdlMaintSts.getStladle_maint_status_id() > 0
					session.update(prevStLdlMaintSts);
				} else {
					session.save(stlLdlMaintStsModel);
				}
				commit(session);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(SteelLadleMaintnDaoImpl.class
						+ " Inside saveSteelLadleMaint Exception..", e);
				result = Constants.SAVE_FAIL;
			} finally {
				close(session);
			}
			return result;
		}
	
	//@SuppressWarnings("unchecked")
	//@Override
	public List<StLadleLifeHeatWiseModel> getStLdlPartsHeatWiseDtls(Integer StLadleId){
		logger.error("in getStLdlPartsHeatWiseDtls..");
		Session session = getNewSession();
		List<StLadleLifeHeatWiseModel> list= new ArrayList<StLadleLifeHeatWiseModel>();
		try {
			String hql = "select a from StLadleLifeHeatWiseModel a where a.steel_ladle_no = "+StLadleId+" "
					+ "and record_status=1";
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			logger.error("error in getStLdlPartsHeatWiseDtls.." + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StLadlePartsMaintLogModel> getStLdlPartsDtls(Integer StLadleId){
		logger.error("in getStLdlPartsDtls..");
		Session session = getNewSession();
		List<StLadlePartsMaintLogModel> list= new ArrayList<StLadlePartsMaintLogModel>();
		try {
			String hql = "select a from StLadlePartsMaintLogModel a where a.stLdlMainStstModel.stladle_id = "+StLadleId+" "
					+ "and a.part_id != null and a.part_id > 0 and a.record_status = 1";
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			logger.error("error in getStLdlPartsDtls.." + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		
		return list;
	}
	
	@Override
	public String saveStLadleMaintainance(Hashtable<String, Object> modobj)
			/*StLadleMaintStatusModel stlLdlMaintStsModel, 
			SteelLadleTrackingModel stLdlTrackingObj, List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi, 
			List<SteelLadleLifeModel> stLdlLifeLi, StLadleStatusTrackHistoryModel stLdlTrackHist)*/ 
		{
			String result = Constants.SAVE;
			Session session = getNewSession();
			try {
				logger.info(SteelLadleMaintnDaoImpl.class + " Inside saveSteelLadleMaint ");
				begin(session);				
				if((StLadleMaintStatusModel)modobj.get("MAINT_STATUS")!=null) {
					StLadleMaintStatusModel stlLdlMaintStsModel = (StLadleMaintStatusModel)modobj.get("MAINT_STATUS");
					if(stlLdlMaintStsModel.getStladle_maint_status_id() != null) {						
						session.update((StLadleMaintStatusModel)modobj.get("MAINT_STATUS"));
					} else {
						StLadleMaintStatusModel maintStsObj = (StLadleMaintStatusModel)modobj.get("MAINT_STATUS");
						StLadleMaintStatusModel obj = getSteelLadleMaintStatus(maintStsObj.getStladle_id());
						List<StLadlePartsMaintLogModel> partli = new ArrayList<StLadlePartsMaintLogModel>();
						List<StLadleLifeHeatWiseModel> stLdlHeatLi = new ArrayList<StLadleLifeHeatWiseModel>();
						
						if (obj != null) {
							
							obj.setRecord_status(0);
							obj.setUpdated_by(stlLdlMaintStsModel.getUpdated_by());
							obj.setUpdated_date_time(stlLdlMaintStsModel.getUpdated_date_time());
							
							partli = getStLdlPartsDtls(stlLdlMaintStsModel.getStladle_id());
							
							if (partli != null && partli.size() > 0) {
								for (StLadlePartsMaintLogModel partObj : partli){
									partObj.setRecord_status(0);
									partObj.setUpdated_by(stlLdlMaintStsModel.getUpdated_by());
									partObj.setUpdated_date_time(stlLdlMaintStsModel.getUpdated_date_time());
									
									session.update(partObj);
								}
							}
							
							stLdlHeatLi = getStLdlPartsHeatWiseDtls(stlLdlMaintStsModel.getStladle_id());							
							if (stLdlHeatLi != null && stLdlHeatLi.size() > 0) {
								for (StLadlePartsMaintLogModel stLdlHeatObj : partli){
									stLdlHeatObj.setRecord_status(0);
									stLdlHeatObj.setUpdated_by(stlLdlMaintStsModel.getUpdated_by());
									stLdlHeatObj.setUpdated_date_time(stlLdlMaintStsModel.getUpdated_date_time());
									
									session.update(stLdlHeatObj);
								}
							}							
							session.update(obj);
						}						
						session.save(stlLdlMaintStsModel);
					}
					
					if (stlLdlMaintStsModel.getStLdlPartMaintLog() != null) {
						if (stlLdlMaintStsModel.getStLdlPartMaintLog().size() > 0) {
							for (StLadlePartsMaintLogModel obj : stlLdlMaintStsModel.getStLdlPartMaintLog()){
				                obj.setLkpSupplier(null);
								obj.setPartsMstrModel(null);	
								obj.setStladle_maint_status_id(stlLdlMaintStsModel.getStladle_maint_status_id());
				                if (obj.getStladle_parts_maint_log_id() != null && obj.getStladle_parts_maint_log_id() > 0) {
				                	session.update(obj);
				                } else {
				                	session.save(obj);
				                }
				            }
						}
					}
				}
				
				if((SteelLadleTrackingModel)modobj.get("STLDL_TRACK")!=null) {
					SteelLadleTrackingModel stLdlTrackingObj = (SteelLadleTrackingModel)modobj.get("STLDL_TRACK");
					if (stLdlTrackingObj.getSt_ladle_track_id() != null && stLdlTrackingObj.getSt_ladle_track_id() > 0) {
						session.update(stLdlTrackingObj);
					}
				}
				
				/*if((StLadleStatusTrackHistoryModel)modobj.get("STLDL_TRACK_HIST")!=null) {
					StLadleStatusTrackHistoryModel stLdlTrackHist = (StLadleStatusTrackHistoryModel)modobj.get("STLDL_TRACK_HIST");
					if (stLdlTrackHist.getSt_ladle_track_id() != null && stLdlTrackHist.getSt_ladle_track_id() > 0) {
						session.update(stLdlTrackHist);
					}
				}*/
				
				if((List<StLadleLifeHeatWiseModel>)modobj.get("STLDL_HEAT_PART_LIFE")!=null){
					List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi = (List<StLadleLifeHeatWiseModel>) modobj.get("STLDL_HEAT_PART_LIFE");
					for (StLadleLifeHeatWiseModel obj : stlLdlHeatPartLifeLi){
						session.save(obj);
					}
				}
				
				if((List<SteelLadleLifeModel>)modobj.get("STLDL_PART_LIFE")!=null){
					List<SteelLadleLifeModel> stLdlLifeLi = (List<SteelLadleLifeModel>) modobj.get("STLDL_PART_LIFE");
					for (SteelLadleLifeModel obj : stLdlLifeLi){
						if (obj.getLadle_life_sl_no() != null && obj.getLadle_life_sl_no() > 0) {
							session.update(obj);
						} else {
							session.save(obj);
						}
					}
				}
				
				if((StLdlHeatingDtls)modobj.get("STLDL_HEATING_INFO")!=null) {
					StLdlHeatingDtls stLdlHeatingBkpObj = (StLdlHeatingDtls)modobj.get("STLDL_HEATING_INFO");
					session.save(stLdlHeatingBkpObj);
				}
				
				commit(session);
	            
	            result =Constants.SAVE;
			} catch(DataIntegrityViolationException e)
        	{
                logger.error(SteelLadleMaintnDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
                result =Constants.DATA_EXIST;
                rollback(session);
            }
            catch(ConstraintViolationException e)
            {
                logger.error(SteelLadleMaintnDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
                result =Constants.DATA_EXIST;
                rollback(session);
            }
            catch (Exception e) {
                e.printStackTrace();
                rollback(session);
                logger.error(SteelLadleMaintnDaoImpl.class+" Inside saveStLadleMaintainance Exception..", e);
                result = Constants.SAVE_FAIL;
            }finally {
                close(session);
            }

            return result;
	}
		
	@Override
	public String backupSteelLadlePartLife(List<StLadleLifeHeatWiseModel> stlLdlHeatPartLifeLi, List<SteelLadleLifeModel> stLdlLifeLi) {
			String result = Constants.SAVE;
			Session session = getNewSession();
			try {
				logger.info(SteelLadleMaintnDaoImpl.class + " Inside backupSteelLadlePartLife ");
				begin(session);
				Integer i=0;
				for (StLadleLifeHeatWiseModel obj : stlLdlHeatPartLifeLi){
					session.save(obj);
	            }
				
				for (SteelLadleLifeModel slpl : stLdlLifeLi){
					session.update(slpl);
	            }
				
				commit(session);
	            
	            result = Constants.SAVE;
			} catch (Exception e) {
                e.printStackTrace();
                rollback(session);
                logger.error(SteelLadleMaintnDaoImpl.class+" Inside backupSteelLadlePartLife Exception..", e);
                result = Constants.SAVE_FAIL;
            }finally {
                close(session);
            }

            return result;
	}
	
	@Override
	public StLadleMaintStatusModel getSteelLadleMaintStatus(Integer stLdlId) {
		logger.info(SteelLadleMaintnDaoImpl.class);
		StLadleMaintStatusModel obj=new StLadleMaintStatusModel();
		Session session=getNewSession();
		
		String hql = "Select a from StLadleMaintStatusModel a where a.stladle_id='"+stLdlId+"' and a.record_status=1";
		try {
			List<?> ls = session.createQuery(hql).list(); 
			if (ls.size() > 0) { 
				obj=(StLadleMaintStatusModel) ls.get(0);
			} else {
				obj = null;
			}
		} catch (Exception e) {
			obj = null;
			e.printStackTrace();
		}finally {
            close(session);
        }
					
		return obj;
	}
	
	@Override
	public StLadlePartsMaintLogModel getStlLadleMaintPartsLog(Integer stLdlMaintStsId, Integer partId) {
		logger.info(SteelLadleMaintnDaoImpl.class+" getStlLadleMaintPartsLog");
		StLadlePartsMaintLogModel obj=new StLadlePartsMaintLogModel();
		Session session=getNewSession();
		String hql;
		if (partId != null && partId > 0) {
			hql = "Select a from StLadlePartsMaintLogModel a where a.stladle_maint_status_id ="+stLdlMaintStsId
					+ " and a.partsMstrModel.part_id = '"+partId+"' and a.record_status = 1";
		} else {
			hql = "Select a from StLadlePartsMaintLogModel a where a.stladle_maint_status_id ="+stLdlMaintStsId
					+ " and a.partsMstrModel.part_id is null and a.record_status = 1";
		}
		
		try {
			List<?> ls = session.createQuery(hql).list(); 
			if (ls.size() > 0) { 
				obj=(StLadlePartsMaintLogModel) ls.get(0);
			} else {
				obj = null;
			}
		} catch (Exception e) {
			obj = null;
			e.printStackTrace();
		}finally {
            close(session);
        }
					
		return obj;
	}

	@Override
	public List<SteelLadleLifeModel> getSteelLadlePartsByLadleId(Integer stLdlId) {	
		Session session = getNewSession();
		List<SteelLadleLifeModel> list = null;
		try {
			String hql = "select a from SteelLadleLifeModel a where a.steel_ladle_no = "+stLdlId+" and a.eqpModel.equipment_name='"+Constants.EQUIPMENT_STEEL_LADLE+"'";
			list = session.createQuery(hql).list();
		} catch (Exception e) {
			logger.error("error in getSteelLadlePartsByLadleId........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		
		return list;
	}
}
