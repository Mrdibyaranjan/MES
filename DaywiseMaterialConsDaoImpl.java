package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatConsMtlsDetailsLog;
import com.smes.trans.model.DaywiseMaterialConsModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatConsumableModelLog;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("DaywiseMaterialConsDao")
public class DaywiseMaterialConsDaoImpl extends GenericDaoImpl<DaywiseMaterialConsModel, Long> implements DaywiseMaterialConsDao{

	private static final Logger logger = Logger.getLogger(DaywiseMaterialConsDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DaywiseMaterialConsModel> getDaywiseMtrlConsumption(String cons_date) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		List<DaywiseMaterialConsModel> list=new ArrayList<DaywiseMaterialConsModel>();
		try {
			String sql="select a from DaywiseMaterialConsModel a where to_char(a.consumption_date, 'dd/mm/yyyy')='"+cons_date+"' order by a.mtr_cons_si_no";
			
			list=(List<DaywiseMaterialConsModel>) getResultFromNormalQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getDaywiseMtrlConsumption..."+e.getMessage());
		}
		return list;
	}

	@Override
	public List<HeatConsMaterialsDetails> getEofConsSlNo(String cons_date) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		List<HeatConsMaterialsDetails> list=new ArrayList<HeatConsMaterialsDetails>();
		try {
			String sql="select a from HeatConsMaterialsDetails a where to_char(a.consumption_date, 'dd/mm/yyyy')='"+cons_date+"' order by a.mtr_cons_si_no";
			
			list=(List<HeatConsMaterialsDetails>) getResultFromNormalQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getDaywiseMtrlConsumption..."+e.getMessage());
		}
		return list;
	}
	
	@Override
	public List<HeatConsMaterialsDetails> getEofConsQty(String cons_date,String cons_type) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		List<HeatConsMaterialsDetails> list=new ArrayList<HeatConsMaterialsDetails>();
		try {
			String sql="select a from HeatConsMaterialsDetails a where to_char(a.consumption_date, 'dd/mm/yyyy')='"+cons_date+"' and a.mtrlMstrModel.histor_matl_desc='"+cons_type+"' ";
		    list=(List<HeatConsMaterialsDetails>) getResultFromNormalQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getDaywiseMtrlConsumption..."+e.getMessage());
		}
		return list;
	}
	
	@Override
	public List<LRFHeatConsumableModel> getLrfConsQty(String cons_date,String cons_type) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		List<LRFHeatConsumableModel> list=new ArrayList<LRFHeatConsumableModel>();
		try {
			String sql="select a from LRFHeatConsumableModel a where to_char(a.addition_date_time, 'dd/mm/yyyy')='"+cons_date+"' and a.mtrlMstrModel.histor_matl_desc='"+cons_type+"'";
		    list=(List<LRFHeatConsumableModel>) getResultFromNormalQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getDaywiseMtrlConsumption..."+e.getMessage());
		}
		return list;
	}
	
	@Override
	public List<CCMHeatConsMaterialsDetails> getCcmConsQty(String cons_date,String cons_type) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		List<CCMHeatConsMaterialsDetails> list=new ArrayList<CCMHeatConsMaterialsDetails>();
		try {
			String sql="select a from CCMHeatConsMaterialsDetails a where to_char(a.consumption_date, 'dd/mm/yyyy')='"+cons_date+"' and a.mtrlMstrModel.histor_matl_desc='"+cons_type+"'";
			list=(List<CCMHeatConsMaterialsDetails>) getResultFromNormalQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getDaywiseMtrlConsumption..."+e.getMessage());
		}
		return list;
	}
	
	/*@Override
	public String saveOrUpdateDayConsumptions(List<DaywiseMaterialConsModel> saveUpdLi) {
		// TODO Auto-generated method stub
		logger.info("inside .. saveOrUpdateDayConsumptions....." + DaywiseMaterialConsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			for(DaywiseMaterialConsModel mtrlConsObj : saveUpdLi) {
				if(mtrlConsObj.getMtr_cons_si_no() != null && mtrlConsObj.getUpdateCounter() > 0) {
					session.update(mtrlConsObj);
				}else {
					session.save(mtrlConsObj);
				}
			}
			commit(session);

			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(DaywiseMaterialConsDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(DaywiseMaterialConsDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(DaywiseMaterialConsDaoImpl.class + " Inside mtrlConsObj Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}*/

	@Override
	public DaywiseMaterialConsModel getDaywiseMaterialConsById(Integer mtrlConsTrnsSlNo) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		Session session = getNewSession();
		DaywiseMaterialConsModel retObj = new DaywiseMaterialConsModel();
		try {
			retObj = (DaywiseMaterialConsModel) session.get(DaywiseMaterialConsModel.class, mtrlConsTrnsSlNo);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(session);
		}
		
		return retObj;
	}

	@Override
	public boolean isConsumptionPosted(String cons_date) {
		logger.info(DaywiseMaterialConsDaoImpl.class);
		List<HeatStatusTrackingModel> list = new ArrayList<HeatStatusTrackingModel>();
		boolean retFlag = false;
		try {
			String sql="select a from HeatStatusTrackingModel a where to_char(a.prodPostedDate, 'dd/mm/yyyy')='"+cons_date+"' "
					+ " and a.isConsPosted='Y' ";
			
			list=(List<HeatStatusTrackingModel>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retFlag = true;
			else
				retFlag = false;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. isConsumptionPosted..."+e.getMessage());
		}
		return retFlag;
	}
	
	@Override
	public String saveOrUpdateMtrlConsumptions(Map<String, List> saveUpdateLi) {
		// TODO Auto-generated method stub
		String result = "";
		int i=0;
		Session session = getNewSession();
		try {
			begin(session);
			VdAdditionsDetailsModel vdAdditionSave = null;
			/*ScrapBucketHdr scrapHdrSaveObj = null;
			HeatConsScrapMtrlDetails hmScrapCons = null;
			LRFHeatArcingDetailsModel lrfArcingSave = null;
			VdAdditionsDetailsModel vdAdditionSave = null;
			List<EofHeatDetails> li_1 = saveUpdateLi.get("UPDATE_EOF_HEAT");
			List<HmReceiveBaseDetails> li_2 = saveUpdateLi.get("UPDATE_HM");
			List<ScrapBucketHdr> li_3 = saveUpdateLi.get("SAVE_SCRAP_HDR");
			if(li_3 != null && li_3.size() > 0)
				scrapHdrSaveObj = li_3.get(0);
			List<ScrapBucketHdr> li_4 = saveUpdateLi.get("UPDATE_SCRAP_HDR");
			List<ScrapBucketDetails> li_5 = saveUpdateLi.get("UPDATE_SCRAP_DTL");
			List<ScrapBucketDetails> li_6 = saveUpdateLi.get("SAVE_SCRAP_DTL");
			List<HeatConsScrapMtrlDetails> li_7 = saveUpdateLi.get("SAVE_HMSCRAP_CONS");
			if(li_7 != null && li_7.size() > 0)
				hmScrapCons = li_7.get(0);*/
			List<HeatConsScrapMtrlDetails> li_8 = saveUpdateLi.get("UPDATE_HMSCRAP_CONS"); 
			List<HeatConsMaterialsDetails> li_9 =  saveUpdateLi.get("SAVE_EOFMTRL_CONS");
			List<HeatConsMaterialsDetails> li_10 = saveUpdateLi.get("UPDATE_EOFMTRL_CONS");
			//List<LRFHeatArcingDetailsModel> li_11 = saveUpdateLi.get("SAVE_LRF_ARCING");
			
			/*if(li_11 != null && li_11.size() > 0)
				lrfArcingSave = li_11.get(0);*/
			List<LRFHeatConsumableModel> li_12 = saveUpdateLi.get("SAVE_LRFMTRL_CONS");
			
			//System.out.println("size = " +li_12.size());
			/*for(i=0;i<li_12.size();i++)
			{
				System.out.println("for loop id "+ i + ": heat number = " +li_12.get(i).getHeat_id());
			}*/
			
			List<LRFHeatConsumableModel> li_13 = saveUpdateLi.get("UPDATE_LRFMTRL_CONS");
			List<VdAdditionsDetailsModel> li_14 = saveUpdateLi.get("SAVE_VD_ADDITION");
			if(li_14 != null && li_14.size() > 0)
				vdAdditionSave = li_14.get(0);
			List<VDHeatConsumableModel> li_15 = saveUpdateLi.get("SAVE_VDMTRL_CONS");
			List<VDHeatConsumableModel> li_16 = saveUpdateLi.get("UPDATE_VDMTRL_CONS");
			List<CCMHeatConsMaterialsDetails> li_17 = saveUpdateLi.get("SAVE_CCMMTRL_CONS");
			List<CCMHeatConsMaterialsDetails> li_18 = saveUpdateLi.get("UPDATE_CCMMTRL_CONS");
			/*List<ScrapBucketDetailsLog> li_19 = saveUpdateLi.get("SAVE_SCRAP_DTL_LOG");*/
			List<HeatConsMaterialsDetailsLog> li_20 = saveUpdateLi.get("SAVE_EOF_CONS_LOG");
			List<LRFHeatConsumableLogModel> li_21 = saveUpdateLi.get("SAVE_LRF_CONS_LOG");
			List<VDHeatConsumableModelLog> li_22 = saveUpdateLi.get("SAVE_VD_CONS_LOG");
			List<CCMHeatConsMtlsDetailsLog> li_23 = saveUpdateLi.get("SAVE_CCM_CONS_LOG");
			
			/*if (li_1 != null && li_1.size() > 0)//UPDATE_EOF_HEAT
				session.update(li_1.get(0));
			if (li_2 != null && li_2.size() > 0)  //UPDATE_HM
				session.update(li_2.get(0));
			if (scrapHdrSaveObj != null) { //SAVE_SCRAP_HDR
				session.save(scrapHdrSaveObj);
			}
			if (li_4 != null && li_4.size() > 0) { //UPDATE_SCRAP_HDR
				session.update(li_4.get(0));
			}
			if (li_5 != null) { //UPDATE_SCRAP_DTL
				for(ScrapBucketDetails scrapDetUpdate : li_5) {
					session.update(scrapDetUpdate);
				}
			}
			if (li_6 != null) { //SAVE_SCRAP_DTL
				for(ScrapBucketDetails scrapDetSave : li_5) {
					if(scrapDetSave.getScrap_bkt_header_id() == null)
						scrapDetSave.setScrap_bkt_header_id(scrapHdrSaveObj.getScrap_bkt_header_id());
					session.save(scrapDetSave);
				}
			}
			if (hmScrapCons != null) { //SAVE_HMSCRAP_CONS
				hmScrapCons.setScrap_bkt_header_id(scrapHdrSaveObj.getScrap_bkt_header_id());
				session.save(hmScrapCons);
			}
			if (li_8 != null) { //UPDATE_HMSCRAP_CONS
				for(HeatConsScrapMtrlDetails hmScrapConsObj: li_8) {
					session.update(hmScrapConsObj);
				}
			}*/
			if (li_9 != null) { //SAVE_EOFMTRL_CONS
				for(HeatConsMaterialsDetails eofConsSave : li_9) {
					session.saveOrUpdate(eofConsSave);
				}
			}
			if (li_10 != null) { //UPDATE_EOFMTRL_CONS
			   for(HeatConsMaterialsDetails eofConsUpdate : li_10) {
					session.update(eofConsUpdate);
				}
			}
			/*if (lrfArcingSave != null) { //SAVE_LRF_ARCING
				session.save(lrfArcingSave);
			}*/
			if (li_12 != null) { //SAVE_LRFMTRL_CONS
				for(LRFHeatConsumableModel lrfConsSave : li_12) {
					session.saveOrUpdate(lrfConsSave);
				}
			}
			if (li_13 != null) { //UPDATE_LRFMTRL_CONS
				for(LRFHeatConsumableModel lrfConsUpdate : li_13) {
					session.update(lrfConsUpdate);
				}
			}
			if (vdAdditionSave != null) { //SAVE_VD_ADDITION
				session.save(vdAdditionSave);
			}
			if (li_15 != null) { //SAVE_VDMTRL_CONS
				for(VDHeatConsumableModel vdConsSave : li_15) {
					session.save(vdConsSave);
				}
			}
			if (li_16 != null) { //UPDATE_VDMTRL_CONS
				for(VDHeatConsumableModel vdConsUpdate : li_16) {
					session.update(vdConsUpdate);
				}
			}
			if (li_17 != null) { //SAVE_CCMMTRL_CONS
				for(CCMHeatConsMaterialsDetails ccmConsSave : li_17) {		
					session.saveOrUpdate(ccmConsSave);
				}
			}
			if (li_18 != null) { //UPDATE_CCMMTRL_CONS
				for(CCMHeatConsMaterialsDetails ccmConsUpdate : li_18) {
					System.out.println("ccmConsUpdate "+ccmConsUpdate.getMtr_cons_si_no());
					System.out.println("ccmConsUpdate "+ccmConsUpdate.getMaterial_id());
					System.out.println("ccmConsUpdate "+ccmConsUpdate.getQty());
					session.update(ccmConsUpdate);
				}
			}
			/*if (li_19 != null) { //SAVE_SCRAP_DTL_LOG
				for(ScrapBucketDetailsLog scrapDtlLogObj : li_19) {
					session.save(scrapDtlLogObj);
				}
			}*/
			if (li_20 != null) { //SAVE_EOF_CONS_LOG
				for(HeatConsMaterialsDetailsLog eofConsLogObj : li_20) {
					session.save(eofConsLogObj);
				}
			}
			if(li_21 != null) { //SAVE_LRF_CONS_LOG
				for(LRFHeatConsumableLogModel lrfConsLogObj : li_21) {
					session.save(lrfConsLogObj);
				}
			}
			if(li_22 != null) { //SAVE_VD_CONS_LOG
				for(VDHeatConsumableModelLog vdConsLogObj : li_22) {
					session.save(vdConsLogObj);
				}
			}
			if(li_23 != null) { //SAVE_CCM_CONS_LOG
				for(CCMHeatConsMtlsDetailsLog ccmConsLogObj : li_23) {
					session.save(ccmConsLogObj);
				}
			}
			commit(session);
			result = Constants.SAVE;
		} catch (org.hibernate.StaleObjectStateException s) {
			s.printStackTrace();
			logger.error(HeatProceeEventDaoImpl.class
					+ " Inside org.hibernate.StaleObjectStateException..saveOrUpdateMtrlConsumptions", s);
			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;
			rollback(session);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside saveOrUpdateMtrlConsumptions Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}
}
