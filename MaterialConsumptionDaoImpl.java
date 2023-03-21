package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatConsMtlsDetailsLog;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.IntfMaterialConsumptionModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatConsumableModelLog;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;
import com.smes.util.MaterialConsumptionDTO;


@Repository("MaterialConsumptionDao")
public class MaterialConsumptionDaoImpl extends GenericDaoImpl<MaterialConsumptionDTO, Long> implements MaterialConsumptionDao{

	private static final Logger logger = Logger.getLogger(MaterialConsumptionDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<HeatStatusTrackingModel> getProdPostedHeats(String cons_date) {
		logger.info(MaterialConsumptionDaoImpl.class);
		List<HeatStatusTrackingModel> list = new ArrayList<HeatStatusTrackingModel>();
		try {
			String sql="select a from HeatStatusTrackingModel a where to_char(a.prodPostedDate, 'dd/mm/yyyy')='"+cons_date+"' "
					+ " and a.main_status = '"+Constants.HEAT_TRACK_STATUS_PRD_POST+"' and (a.isConsPosted='N' or a.isConsPosted is null) order by a.heat_id";

			list=(List<HeatStatusTrackingModel>) getResultFromNormalQuery(sql);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getProdPostedHeats..."+e.getMessage());
		}
		return list;
	}

	@Transactional
	@Override	
	public List<LRFHeatConsumableModel> getLrfMtrlConsumptions(String heatNo, Integer heatCounter) {
		logger.info(MaterialConsumptionDaoImpl.class);
		List<LRFHeatConsumableModel> list = new ArrayList<LRFHeatConsumableModel>();
		try {
			/*String sql=" select a from LRFHeatConsumableModel a, (select heat_id, heat_counter from LRFHeatDetailsModel where heat_id='"+heatNo+"' "
					+ " and sub_unit_id="+subUnitId+") qry where a.heat_id = qry.heat_id and a.heat_counter = qry.heat_counter order by a.heat_id, a.heat_counter";*/
			String sql=" select a from LRFHeatConsumableModel a where a.heat_id = '"+heatNo+"' and a.heat_counter = "+heatCounter;

			list=(List<LRFHeatConsumableModel>) getResultFromNormalQuery(sql);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getLrfMtrlConsumptions..."+e.getMessage());
		}
		return list;
	}

	@Override
	public HeatConsScrapMtrlDetails getHeatScrapConsumptionHMRecvId(Integer trns_si_no, Integer hmRecvId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<HeatConsScrapMtrlDetails> list=new ArrayList<HeatConsScrapMtrlDetails>();
		HeatConsScrapMtrlDetails retObj = null;
		try {
			String sql = "Select a from HeatConsScrapMtrlDetails a where a.trns_eof_si_no="+trns_si_no+" and a.hm_seq_no ="+hmRecvId;
			list=(List<HeatConsScrapMtrlDetails>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getHeatScrapConsumptionHMRecvId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public List<LRFHeatDetailsModel> getLrfHeatDetailObj(String heatNo, Integer subUnitId) {
		logger.info(MaterialConsumptionDaoImpl.class);
		List<LRFHeatDetailsModel> list = new ArrayList<LRFHeatDetailsModel>();
		try {
			String sql="select a from LRFHeatDetailsModel a where a.heat_id='"+heatNo+"' and a.sub_unit_id="+subUnitId+" order by a.heat_id, a.heat_counter";

			list=(List<LRFHeatDetailsModel>) getResultFromNormalQuery(sql);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getLrfHeatDetailObj..."+e.getMessage());
		}
		return list;
	}

	@Override
	public List<VDHeatConsumableModel> getVDMtrlConsumptions(String heatNo) {
		logger.info(MaterialConsumptionDaoImpl.class);
		List<VDHeatConsumableModel> list = new ArrayList<VDHeatConsumableModel>();
		try {
			String sql=" select a from VDHeatConsumableModel a where a.heat_id = '"+heatNo+"'";

			list=(List<VDHeatConsumableModel>) getResultFromNormalQuery(sql);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getVDMtrlConsumptions..."+e.getMessage());
		}
		return list;
	}

	@Override
	public List<CCMHeatConsMaterialsDetails> getCCMMtrlConsumptions(Integer trnsSlNo) {
		logger.info(MaterialConsumptionDaoImpl.class);
		List<CCMHeatConsMaterialsDetails> list = new ArrayList<CCMHeatConsMaterialsDetails>();
		try {
			String sql=" select a from CCMHeatConsMaterialsDetails a where a.trns_ccm_si_no = "+trnsSlNo;

			list=(List<CCMHeatConsMaterialsDetails>) getResultFromNormalQuery(sql);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getCCMMtrlConsumptions..."+e.getMessage());
		}
		return list;
	}

	@Override
	public String saveOrUpdateMtrlConsumptions(Map<String, List> saveUpdateLi) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);

			ScrapBucketHdr scrapHdrSaveObj = null;
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
				hmScrapCons = li_7.get(0);
			List<HeatConsScrapMtrlDetails> li_8 = saveUpdateLi.get("UPDATE_HMSCRAP_CONS"); 
			List<HeatConsMaterialsDetails> li_9 =  saveUpdateLi.get("SAVE_EOFMTRL_CONS");
			List<HeatConsMaterialsDetails> li_10 = saveUpdateLi.get("UPDATE_EOFMTRL_CONS");
			//List<LRFHeatArcingDetailsModel> li_11 = saveUpdateLi.get("SAVE_LRF_ARCING");

			/*if(li_11 != null && li_11.size() > 0)
				lrfArcingSave = li_11.get(0);*/
			List<LRFHeatConsumableModel> li_12 = saveUpdateLi.get("SAVE_LRFMTRL_CONS");
			List<LRFHeatConsumableModel> li_13 = saveUpdateLi.get("UPDATE_LRFMTRL_CONS");
			List<VdAdditionsDetailsModel> li_14 = saveUpdateLi.get("SAVE_VD_ADDITION");
			if(li_14 != null && li_14.size() > 0)
				vdAdditionSave = li_14.get(0);
			List<VDHeatConsumableModel> li_15 = saveUpdateLi.get("SAVE_VDMTRL_CONS");
			List<VDHeatConsumableModel> li_16 = saveUpdateLi.get("UPDATE_VDMTRL_CONS");
			List<CCMHeatConsMaterialsDetails> li_17 = saveUpdateLi.get("SAVE_CCMMTRL_CONS");
			List<CCMHeatConsMaterialsDetails> li_18 = saveUpdateLi.get("UPDATE_CCMMTRL_CONS");
			List<ScrapBucketDetailsLog> li_19 = saveUpdateLi.get("SAVE_SCRAP_DTL_LOG");
			List<HeatConsMaterialsDetailsLog> li_20 = saveUpdateLi.get("SAVE_EOF_CONS_LOG");
			List<LRFHeatConsumableLogModel> li_21 = saveUpdateLi.get("SAVE_LRF_CONS_LOG");
			List<VDHeatConsumableModelLog> li_22 = saveUpdateLi.get("SAVE_VD_CONS_LOG");
			List<CCMHeatConsMtlsDetailsLog> li_23 = saveUpdateLi.get("SAVE_CCM_CONS_LOG");

			/*if (li_1 != null && li_1.size() > 0)//UPDATE_EOF_HEAT
				session.update(li_1.get(0));*/
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
			}
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
					if(vdConsSave.getArc_sl_no() == null)
						vdConsSave.setArc_sl_no(vdAdditionSave.getArc_sl_no());
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
					//System.out.println("ccmConsSave");
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
			if (li_19 != null) { //SAVE_SCRAP_DTL_LOG
				for(ScrapBucketDetailsLog scrapDtlLogObj : li_19) {
					session.save(scrapDtlLogObj);
				}
			}
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

	@Transactional
	@Override
	public String postMtrlConsumptions(List<IntfMaterialConsumptionModel> intfMtrlConsLi,
			HeatStatusTrackingModel heatTrackingObj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			
			for(IntfMaterialConsumptionModel intfConsObj : intfMtrlConsLi) {
				if(intfConsObj.getComponent() != null)
				session.save(intfConsObj);
			}
			session.update(heatTrackingObj);
			commit(session);
			result = Constants.SAVE;
		} catch (org.hibernate.StaleObjectStateException s) {
			logger.error(HeatProceeEventDaoImpl.class
					+ " Inside org.hibernate.StaleObjectStateException..saveOrUpdateMtrlConsumptions", s);
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
			logger.error(HeatProceeEventDaoImpl.class + " Inside saveOrUpdateMtrlConsumptions Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Transactional
	@Override
	public HeatConsMaterialsDetails getEofMtrlConsByMtrlId(Integer trnsSlNo, Integer mtrlId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<HeatConsMaterialsDetails> list=new ArrayList<HeatConsMaterialsDetails>();
		HeatConsMaterialsDetails retObj = null;
		try {
			String sql = "Select a from HeatConsMaterialsDetails a where a.trns_eof_si_no="+trnsSlNo+" and a.material_id ="+mtrlId;
			list=(List<HeatConsMaterialsDetails>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getEofMtrlConsByMtrlId..."+e.getMessage());
		}

		return retObj;
	}

	@Override
	public LRFHeatConsumableModel getLrfMtrlConsByMtrlId(String heatNo, Integer mtrlId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<LRFHeatConsumableModel> list=new ArrayList<LRFHeatConsumableModel>();
		LRFHeatConsumableModel retObj = null;
		try {
			String sql = "Select a from LRFHeatConsumableModel a where a.heat_id='"+heatNo+"' and a.material_id ="+mtrlId;
			list=(List<LRFHeatConsumableModel>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getLrfMtrlConsByMtrlId..."+e.getMessage());
		}

		return retObj;
	}
	@Transactional
	@Override

	public List<LRFHeatConsumableModel>  getLrfMtrlConsByCtrlMtrlId(String heatNo,Integer heatcounter, Integer mtrlId)
	{
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<LRFHeatConsumableModel> list=new ArrayList<LRFHeatConsumableModel>();
		List<LRFHeatConsumableModel> retObj = null;
		try {
			String sql = "Select a from LRFHeatConsumableModel a where a.heat_id='"+heatNo+"'and a.heat_counter="+heatcounter+" and a.material_id ="+mtrlId;
			list=(List<LRFHeatConsumableModel>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getLrfMtrlConsByMtrlId..."+e.getMessage());
		}

		return retObj;
	}

	@Override
	public VDHeatConsumableModel getVdMtrlConsByMtrlId(String heatNo, Integer mtrlId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<VDHeatConsumableModel> list=new ArrayList<VDHeatConsumableModel>();
		VDHeatConsumableModel retObj = null;
		try {
			String sql = "Select a from VDHeatConsumableModel a where a.heat_id='"+heatNo+"' and a.material_id ="+mtrlId;
			list=(List<VDHeatConsumableModel>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getVdMtrlConsByMtrlId..."+e.getMessage());
		}

		return retObj;
	}
	@Transactional
	@Override
	public CCMHeatConsMaterialsDetails getCcmMtrlConsByMtrlId(Integer trnsSlNo, Integer mtrlId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<CCMHeatConsMaterialsDetails> list=new ArrayList<CCMHeatConsMaterialsDetails>();
		CCMHeatConsMaterialsDetails retObj = null;
		try {
			String sql = "Select a from CCMHeatConsMaterialsDetails a where a.trns_ccm_si_no="+trnsSlNo+" and a.material_id ="+mtrlId;
			list=(List<CCMHeatConsMaterialsDetails>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getCcmMtrlConsByMtrlId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public ScrapBucketDetailsLog getScrapBucketDetLogByConsId(Integer mtrlConsId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<ScrapBucketDetailsLog> list=new ArrayList<ScrapBucketDetailsLog>();
		ScrapBucketDetailsLog retObj = null;
		try {
			String sql = "Select a from ScrapBucketDetailsLog a where a.scrap_bkt_detail_id="+mtrlConsId;
			list=(List<ScrapBucketDetailsLog>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getScrapBucketDetLogByConsId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public HeatConsMaterialsDetailsLog getEofHeatConsLogByConsId(Integer mtrlConsId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<HeatConsMaterialsDetailsLog> list=new ArrayList<HeatConsMaterialsDetailsLog>();
		HeatConsMaterialsDetailsLog retObj = null;
		try {
			String sql = "Select a from HeatConsMaterialsDetailsLog a where a.mtr_cons_si_no="+mtrlConsId;
			list=(List<HeatConsMaterialsDetailsLog>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getEofHeatConsLogByConsId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public LRFHeatConsumableLogModel getLrfHeatConsLogByConsId(Integer mtrlConsId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<LRFHeatConsumableLogModel> list=new ArrayList<LRFHeatConsumableLogModel>();
		LRFHeatConsumableLogModel retObj = null;
		try {
			String sql = "Select a from LRFHeatConsumableLogModel a where a.cons_sl_no="+mtrlConsId;
			list=(List<LRFHeatConsumableLogModel>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getLrfHeatConsLogByConsId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public VDHeatConsumableModelLog getVdHeatConsLogByConsId(Integer mtrlConsId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<VDHeatConsumableModelLog> list=new ArrayList<VDHeatConsumableModelLog>();
		VDHeatConsumableModelLog retObj = null;
		try {
			String sql = "Select a from VDHeatConsumableModelLog a where a.cons_sl_no="+mtrlConsId;
			list=(List<VDHeatConsumableModelLog>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getVdHeatConsLogByConsId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public CCMHeatConsMtlsDetailsLog getCcmHeatConsLogByConsId(Integer mtrlConsId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<CCMHeatConsMtlsDetailsLog> list=new ArrayList<CCMHeatConsMtlsDetailsLog>();
		CCMHeatConsMtlsDetailsLog retObj = null;
		try {
			String sql = "Select a from CCMHeatConsMtlsDetailsLog a where a.mtr_cons_si_no="+mtrlConsId;
			list=(List<CCMHeatConsMtlsDetailsLog>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getCcmHeatConsLogByConsId..."+e.getMessage());
		}

		return retObj;
	}

	@Transactional
	@Override
	public LRFHeatConsumableModel getLrfHeatConsByConsId(Integer mtrlConsId) {
		// TODO Auto-generated method stub
		logger.info(MaterialConsumptionDaoImpl.class);
		List<LRFHeatConsumableModel> list=new ArrayList<LRFHeatConsumableModel>();
		LRFHeatConsumableModel retObj = null;
		try {
			String sql = "Select a from LRFHeatConsumableModel a where a.cons_sl_no="+mtrlConsId;
			list=(List<LRFHeatConsumableModel>) getResultFromNormalQuery(sql);
			if(list.size() > 0)
				retObj = list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getLrfHeatConsLogByConsId..."+e.getMessage());
		}

		return retObj;
	}

	@Override
	public HeatConsMaterialsDetails getEOFMtrlConsById(Integer mtrlConsSiNo) {
		// TODO Auto-generated method stub
		HeatConsMaterialsDetails obj = new HeatConsMaterialsDetails();
		Session session=getNewSession();
		try {
			String hql = "select a from HeatConsMaterialsDetails a where a.mtr_cons_si_no="+mtrlConsSiNo;
			Query query=session.createQuery(hql);
			obj=(HeatConsMaterialsDetails) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEOFMtrlConsById........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		return obj;
	}

}
