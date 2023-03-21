package com.smes.trans.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMBatchDetailsBkpModel;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMProductDetailsBkpModel;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Transactional
@Repository
public class CCMBatchDetailsDaoImpl extends GenericDaoImpl<CCMBatchDetailsModel,Long> implements CCMBatchDetailsDao {

	private static final Logger logger = Logger.getLogger(CCMBatchDetailsDaoImpl.class);
	@Override
	public List<CCMBatchDetailsModel> getBatchDetailsByProduct(Integer product_id) {
		List<CCMBatchDetailsModel> list=new ArrayList<CCMBatchDetailsModel>();
		try {
			String sql = "Select a from CCMBatchDetailsModel a where a.product="+product_id+" order by a.batch_no";
			list=(List<CCMBatchDetailsModel>) getResultFromNormalQuery(sql);
		}catch(Exception ex) {
			ex.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getBatchDetailsByProduct..."+ex.getMessage());
		}
		return list;
	}
	@Override
	public String saveProductBatch(CCMBatchDetailsModel model) {
		String result=Constants.SAVE;
		create(model);
		return result;
	}
	@Override
	public String updateProductBatch(CCMBatchDetailsModel model) {
		String result=Constants.SAVE;
		update(model);
		return result;
	}
	@Override
	public String removeProductBatch(CCMBatchDetailsModel model) {
		String result=Constants.DELETE;
		delete(model);
		return result;
	}
	@Override
	public String saveUpdateOrDeleteCCMBatches(Map<String, List<CCMProductDetailsModel>> ccmProdMap,
			Map<String, List<CCMBatchDetailsModel>> ccmBatchMap,  List<CCMProductDetailsBkpModel> ccmProdBkpLi, List<CCMBatchDetailsBkpModel> ccmBatchBkpLi) {
		// TODO Auto-generated method stub
		logger.info("inside .. saveUpdateOrDeleteCCMBatches....." + CCMBatchDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			List<CCMProductDetailsModel> saveUpdateProdLi = ccmProdMap.get("SAVE_CCM_PROD");
			List<CCMProductDetailsModel> removeProdLi = ccmProdMap.get("DELETE_CCM_PROD"); 
			List<CCMBatchDetailsModel> saveUpdateBatchLi = ccmBatchMap.get("SAVE_CCM_BATCH");
			List<CCMBatchDetailsModel> removeBatchLi = ccmBatchMap.get("DELETE_CCM_BATCH");
			
			for(CCMProductDetailsModel prodObj : saveUpdateProdLi) {
				if(prodObj.getProd_trns_id() != null) {
					session.update(prodObj);
				}else {
					session.save(prodObj);
					List<CCMBatchDetailsModel> newCcmBatchObjLi = saveUpdateBatchLi.stream().filter(p -> p.getStrandNo().equals(prodObj.getStand_id())).collect(Collectors.toList());
					for(CCMBatchDetailsModel newBatchObj : newCcmBatchObjLi) {
						if(newBatchObj.getBatch_trns_id() == 0 && newBatchObj.getProduct() == 0) {
							newBatchObj.setProduct(prodObj.getProd_trns_id());
							//session.save(prodObj);
						}
					}
				}
			}
			for(CCMProductDetailsModel prodObj : removeProdLi) {
				session.delete(prodObj);
			}
			for(CCMBatchDetailsModel ccmBatchObj : saveUpdateBatchLi) {
				if(ccmBatchObj.getBatch_trns_id() != null) {
					session.update(ccmBatchObj);
				}else {
					session.save(ccmBatchObj);
				}
			}
			for(CCMBatchDetailsModel ccmBatchObj : removeBatchLi) {
				session.delete(ccmBatchObj);
			}
			
			for(CCMProductDetailsBkpModel prodBkpObj : ccmProdBkpLi) {
				System.out.println("prodBkpObj prod trns id "+prodBkpObj.getProd_trns_id());
				System.out.println("created by "+prodBkpObj.getCreated_by());
				session.save(prodBkpObj);
			}
			for(CCMBatchDetailsBkpModel batchBkpObj : ccmBatchBkpLi) {
				session.save(batchBkpObj);
			}
			
			commit(session);

			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(CCMBatchDetailsDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(CCMBatchDetailsDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(CCMBatchDetailsDaoImpl.class + " Inside ccmBatchObj Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}
	@Override
	public String ccmProductionPosting(HeatStatusTrackingModel heatTrackObj,IfacesmsLpDetailsModel ifacObj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session=getNewSession();
		String res=null;
		
		try {
			begin(session);
			session.update(heatTrackObj);
			session.save(ifacObj);
			res= session.doReturningWork( new ReturningWork<String>(){
			public String execute(Connection connection) throws SQLException {
				CallableStatement cstmt = null;
				try{
					cstmt = connection.prepareCall ("{call SAP_IFACE_ENPG.send_heat_to_sap (?, ?, ?)}");
					cstmt.registerOutParameter (3, Types.INTEGER);					    
					cstmt.setString (1, heatTrackObj.getHeat_id());
					cstmt.setInt (2, heatTrackObj.getHeat_counter()); 			   
					cstmt.execute();
					Integer p_out = cstmt.getInt(3);
					return p_out.toString();
				}finally{
					cstmt.close();
				}
			}});
			if(res.equals("0"))
				result=Constants.SAVE_FAIL;
			else
				result=Constants.PROD_POST;
			commit(session);
		} 
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			result=Constants.SAVE_FAIL;	
		}finally {
			close(session);
		}
		
		return result;
	}
	
	public List<CCMProductDetailsBkpModel> getProdDtlsBkpByHeatTrnsId(Integer heatTransSlNo) {
		List<CCMProductDetailsBkpModel> list=new ArrayList<CCMProductDetailsBkpModel>();
		try {
			String sql = "Select a from CCMProductDetailsBkpModel a where a.trns_sl_no="+heatTransSlNo;
			list=(List<CCMProductDetailsBkpModel>) getResultFromNormalQuery(sql);
		}catch(Exception ex) {
			ex.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getProdDtlsBkpByHeatTrnsId..."+ex.getMessage());
		}
		return list;
	}
	
	public List<CCMBatchDetailsBkpModel> getBatchDtlsBkpByProdTrnsId(Integer ProdTransSlNo) {
		List<CCMBatchDetailsBkpModel> list=new ArrayList<CCMBatchDetailsBkpModel>();
		try {
			String sql = "Select a from CCMBatchDetailsBkpModel a where a.product="+ProdTransSlNo;
			list=(List<CCMBatchDetailsBkpModel>) getResultFromNormalQuery(sql);
		}catch(Exception ex) {
			ex.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getProdDtlsBkpByHeatTrnsId..."+ex.getMessage());
		}
		return list;
	}
	@Override
	public String getSteelQty(Integer trns_sl_no,Integer billet_max_length) {
		// TODO Auto-generated method stub
		String result = null ;
		try {
			String sql = " SELECT " + 
					" CASE WHEN steel_ladle_wgt+"+billet_max_length+" < SUM (act_batch_wgt)   THEN 'TRUE' " + 
					" WHEN steel_ladle_wgt+"+billet_max_length+" > SUM (act_batch_wgt)   THEN 'FALSE' " + 
					" END AS result"+
					"    FROM trns_ccm_heat_details a, " + 
					"         trns_ccm_batch_details b, " + 
					"         trns_ccm_prod_details c " + 
					"     WHERE a.trns_sl_no = c.trns_sl_no " + 
					"     AND b.product = c.prd_trns_id " + 
					"     AND a.TRNS_SL_NO= "+trns_sl_no +" " + 
					" GROUP BY steel_ladle_wgt ";
			result = (String) getResultFromCustomQuery(sql).get(0);
			System.out.println("I AM HERE !@#@$%^&*"+result);
		}catch(Exception ex) {
			ex.printStackTrace();
			logger.error("Exception.. CCMBatchDetailsModel..."+ex.getMessage());
		}
		return result;
}
	@Override
	public String ccmBatchProductionPosting(HeatStatusTrackingModel heatTrackObj,IfacesmsLpDetailsModel ifacObj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session=getNewSession();
		String res=null;
		
		try {
			begin(session);
			session.update(heatTrackObj);
			session.save(ifacObj);
			res= session.doReturningWork( new ReturningWork<String>(){
			public String execute(Connection connection) throws SQLException {
				CallableStatement cstmt = null;
				try{
					cstmt = connection.prepareCall ("{call SAP_IFACE_ENPG.send_heat_insp_details_to_sap (?, ?, ?)}");
					cstmt.registerOutParameter (3, Types.INTEGER);					    
					cstmt.setString (1, heatTrackObj.getHeat_id());
					cstmt.setInt (2, heatTrackObj.getHeat_counter()); 			   
					cstmt.execute();
					Integer p_out = cstmt.getInt(3);
					return p_out.toString();
				}finally{
					cstmt.close();
				}
			}});
			if(res.equals("0"))
				result=Constants.SAVE_FAIL;
			else
				result=Constants.PROD_POST;
			commit(session);
		} 
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			result=Constants.SAVE_FAIL;	
		}finally {
			close(session);
		}
		
		return result;
	}
	
	@Override
	public String saveCCMBatchDetails(List<CCMBatchDetailsModel> batchLi, List<CCMProductDetailsModel> productLi) {
		// TODO Auto-generated method stub
		logger.info("inside .. saveCCMBatchDetails....." + CCMBatchDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			batchLi.forEach(batchObj -> {
				session.saveOrUpdate(batchObj);
			});
			productLi.forEach(prodObj -> {
				session.saveOrUpdate(prodObj);
			});
			commit(session);
			result = Constants.SAVE;
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(CCMBatchDetailsDaoImpl.class + " Inside saveCCMBatchDetails Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}
	
}
