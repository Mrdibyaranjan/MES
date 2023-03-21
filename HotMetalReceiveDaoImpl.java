package com.smes.trans.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.intf.wb.dao.impl.WeighmentDetailsDao;
import com.smes.intf.wb.model.WeighmentDetails;
import com.smes.masters.model.HmLadleMasterModel;
import com.smes.masters.model.LookupMasterModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.HotMetalLadleMixDetails;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;
import com.smes.util.JPODDetailsUtil;
@Transactional
@Repository("hotMetalReceiveDao")
public class HotMetalReceiveDaoImpl extends GenericDaoImpl<HmReceiveBaseDetails, Long> implements HotMetalReceiveDao{
	
	@Autowired
	WeighmentDetailsDao weDto;
	
	private static final Logger logger = Logger
			.getLogger(HotMetalReceiveDaoImpl.class);
	


	@SuppressWarnings("unchecked")
	@Override
	public List<HmReceiveBaseDetails> getHMDetailsbyStatus(Integer stage,
			String ladlestatus) {
		logger.info("inside .. getHMDetailsbyStatus.....");
		List<HmReceiveBaseDetails> list=new ArrayList<HmReceiveBaseDetails>();
		//List<HmReceiveBaseDetails> lst=new ArrayList<HmReceiveBaseDetails>();
		try {
			
			String hql = "select a from HmReceiveBaseDetails a where a.hmLadleStatus in ("+ladlestatus+") and hmLadleStageStatus="+stage+"";
				
			list =(List<HmReceiveBaseDetails>) getResultFromNormalQuery(hql);
	
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHMDetailsbyStatus........"+e);
		}
		
		list.forEach(p->{
			List<WeighmentDetails> weighmentDtls=weDto.getWeighmentByLadleNoCastNo(p.getHmLadleNo(), p.getCastNo(), "AFTER_POURING");
			if(!weighmentDtls.isEmpty()) {
				p.setHmLadleTareWt(weighmentDtls.get(0).getWeight());
				Double hmAvl=p.getHmLadleGrossWt()-weighmentDtls.get(0).getWeight();
				DecimalFormat df = new DecimalFormat("#.#####");
				String value = df.format(hmAvl);
					p.setHmAvlblWt(Double.valueOf(value));
				
			}
			//lst.add(p);
		});
		
		return list;
	}


	@Override
	public Integer getHMDetailsbyInterface() {
		// TODO Auto-generated method stub
		logger.info("inside .. getHMDetailsbyInterface.....");
		Integer result=0;
		try{
			
			ProcedureCall call=getSession().createStoredProcedureCall("HOTMETAL_RECEIVED_RECEIPT_DTLS");//createSQLQuery("call HOTMETAL_RECEIVED_RECEIPT_DTLS(?)");
			
			call.registerParameter0("hm_rcvd_op", String.class, ParameterMode.OUT);
			
			ProcedureOutputs outputs = call.getOutputs();
			
			result = Integer.parseInt(outputs.getOutputParameterValue( "hm_rcvd_op" ).toString());
			
			
			
	}catch(Exception e)
	{
		e.printStackTrace();
		logger.error("error in getHMDetailsbyInterface........"+e);
		
	}
		return result;
}


	@Override
	public String hotMetalReceiveDetailsSave(HmReceiveBaseDetails hmRecvDetails) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		try {
			logger.info(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveDetailsSave ");
			create(hmRecvDetails);

		} catch (Exception e) {
			logger.error(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveDetailsSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}

		return result;
		
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<HmReceiveBaseDetails> getHMDetailsByQuery(String qry) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHMDetailsByQuery.....");
		List<HmReceiveBaseDetails> list=new ArrayList<HmReceiveBaseDetails>();
		Session session=getNewSession();
		try {
			
			list =(List<HmReceiveBaseDetails>)session.createQuery(qry).list();//(List<HmReceiveBaseDetails>) getResultFromNormalQuery(qry);
	
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHMDetailsByQuery........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return list;
	}


	@Override
	public String hotMetalNextProcessUnit(HmReceiveBaseDetails hmdatasave,
			HmReceiveBaseDetails hmdataforupdate) {
		// TODO Auto-generated method stub
		String result = Constants.SAVE;
		//Session s=getSessionFactory().openSession();
		try{
			
			update(hmdataforupdate);
			create(hmdatasave);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.error("error in hotMetalNextProcessUnit........");
			result = Constants.SAVE_FAIL;
		}finally{
			logger.info("close calling ...getComboList");
			//s.close();
		}
		
		return result;
	}

	@Override
	public String hotMetalReceiveDetailsUpdate(HmReceiveBaseDetails hmRecvDetails) {
		// TODO Auto-generated method stub
		String result = Constants.UPDATE;
		try {
			logger.info(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveDetailsUpdate ");
			update(hmRecvDetails);

		}catch(org.hibernate.StaleObjectStateException s)
		{
			logger.error(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveDetailsUpdate Exception..", s);
			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;
		}
		catch (Exception e) {
			logger.error(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveDetailsUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		}

		return result;
	}
	
	@Override
	public String hotMetalReceiveStatusUpdate(Long hmRecvID) {
		// TODO Auto-generated method stub
		String result = Constants.DELETE;
		try {
			logger.info(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveStatusUpdate ");
			String hql = "update HmReceiveBaseDetails set hmLadleStatus=(select a.lookup_id from LookupMasterModel a where a.lookup_type='HM_LADLE_STATUS' and a.lookup_code='DELETED')"
					+ ", hmLadleStageStatus=(select a.lookup_id from LookupMasterModel a where a.lookup_type='HM_LADLE_STAGE_STATUS_TYPE' and a.lookup_code='DELETED') where hmRecvId="+hmRecvID;
			Session session=getSession();
			Query query=session.createQuery(hql);
			int rows=query.executeUpdate();
		}catch(org.hibernate.StaleObjectStateException s)
		{
			logger.error(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveStatusUpdate Exception..", s);
			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;
		}
		catch (Exception e) {
			logger.error(HotMetalReceiveDaoImpl.class+" Inside hotMetalReceiveStatusUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		}

		return result;
	}

	@Override
	public HmReceiveBaseDetails getHmReceiveDetailsById(Integer recvid) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHmReceiveDetailsById.....");
		HmReceiveBaseDetails list=new HmReceiveBaseDetails();
		Session session=getNewSession();
		try {
			list=(HmReceiveBaseDetails) session.get(HmReceiveBaseDetails.class, recvid);
			//String hql = "select a from HeatPlanHdrDetails a where a.heat_plan_id="+heat_plan_id+" and a.record_status=1";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHmReceiveDetailsById........"+e);
		}
		finally{
			close(session);
		}
		return list;
	}


	@Override
	public String hotMetalMixDetailsSaveAll(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session=getNewSession();
		try{
			begin(session);
			
			if((HmReceiveBaseDetails)mod_obj.get("HM_RECV_SAVE")!=null)
			{
			session.save((HmReceiveBaseDetails)mod_obj.get("HM_RECV_SAVE"));
			}
			
			if((HmLadleMasterModel)mod_obj.get("HM_MSTR_UPDATE")!=null)
			{
			session.update((HmLadleMasterModel)mod_obj.get("HM_MSTR_UPDATE"));
			}
			
			if(Integer.parseInt(mod_obj.get("HMRECVCNT").toString())>0)
			{
					
				Integer cnt=Integer.parseInt(mod_obj.get("HMRECVCNT").toString());
				String key="HM_RECV_UPDATE";
				
				for(int i=0;i<=cnt;i++)
				{
				if((HmReceiveBaseDetails)mod_obj.get(key+i)!=null)
				{
					
					session.update((HmReceiveBaseDetails)mod_obj.get(key+i));
				
				
				}
				
				}
			}
			
			
			if(Integer.parseInt(mod_obj.get("HMLDLCNT").toString())>0)
			{
					
				Integer cnt=Integer.parseInt(mod_obj.get("HMLDLCNT").toString());
				String key="HM_MSTR_LDL";
				
				for(int i=0;i<=cnt;i++)
				{
					
					if((HmLadleMasterModel)mod_obj.get(key+i)!=null)
					{
						
						session.update((HmLadleMasterModel)mod_obj.get(key+i));					
					}
				
				}
			}
			
			
			
			
			if(Integer.parseInt(mod_obj.get("HMMIXCNT").toString())>0)
			{
			
				Integer cnt=Integer.parseInt(mod_obj.get("HMMIXCNT").toString());
				String key1="HM_MIX_INSERT";
				
				for(int i=0;i<=cnt;i++)
				{
					
					
				if((HotMetalLadleMixDetails)mod_obj.get(key1+i)!=null)
				{
					
					session.save((HotMetalLadleMixDetails)mod_obj.get(key1+i));
					
				
				}
				
				}
				
			}
			
			commit(session);
			result =Constants.SAVE;
		} 
		catch(DataIntegrityViolationException e)
		{
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch(ConstraintViolationException e)
		{
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside hotMetalReceiveDetailsSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;
		
	}


	@Override
	public String hotMetalLadleNextProcessUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		
		Session session=getNewSession();
		try{
			
			
			begin(session);
			
			if((HmReceiveBaseDetails)mod_obj.get("UPDATE")!=null)
			{
				
				session.update((HmReceiveBaseDetails)mod_obj.get("UPDATE"));
			}
			
			if((HmReceiveBaseDetails)mod_obj.get("SAVE")!=null)
			{
				session.save((HmReceiveBaseDetails)mod_obj.get("SAVE"));
			}
			
			
			commit(session);
			result =Constants.SAVE;
		} 
		catch(DataIntegrityViolationException e)
		{
			//e.printStackTrace();
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch(ConstraintViolationException e)
		{
			//e.printStackTrace();
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside ConstraintViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch (Exception e) {
			//e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class+" Inside hotMetalReceiveDetailsSave Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;
		
	}



	  @Override
	  public String saveHMDataArray(List<HmReceiveBaseDetails> hmRecvDetList) {

	    // TODO Auto-generated method stub
	    String result = Constants.SAVE;
	    Connection jpodconn=null;
	    Session session=getNewSession();
	    try {
	      begin(session);
	      logger.info(HotMetalReceiveDaoImpl.class+" Inside HM data List save ");
	      for(HmReceiveBaseDetails hmDetails:hmRecvDetList)
	      {
	        session.save(hmDetails);
	      }
	      //update jpod records in jpod database
	      
	     jpodconn = JPODDetailsUtil.getJPODConnection();
	     updateJpodHmRecords(hmRecvDetList,jpodconn);
	      
	      commit(session);
	      jpodconn.commit();

	    } catch (Exception e) {
	    	e.printStackTrace();
	      rollback(session);
	         if (jpodconn != null) {
	                try {
	                    System.err.print("Transaction is being rolled back");
	                    jpodconn.rollback();
	                } catch(SQLException excep) {
	                  excep.printStackTrace();
	                }
	      logger.error(HotMetalReceiveDaoImpl.class+" Inside HM data List Save Exception..", e);
	      result = Constants.SAVE_FAIL;
	    }
	    }finally{
	      close(session);
	      if (jpodconn != null) {
              try {
            	  jpodconn.close();
              } catch(Exception excep) {
                excep.printStackTrace();
              }
	    }
	    
	    }
	    return result;
	  
	  }
	  
	  public void updateJpodHmRecords(List<HmReceiveBaseDetails> HmDetList,Connection jpodConn ) throws SQLException {
	       PreparedStatement hdrStmt = null;
	       //PreparedStatement detailsStmt=null;
	       
	       String updateHdrSql= " update intf_hm_receive_hdr h SET h.record_status = 1,h.updated_by = 101,  h.updated_date_time = SYSDATE WHERE h.record_status = 0 "
	          +" and h.cast_no =? AND h.hm_ladle_no = ? and h.hm_seq_no=?";
	       
	      /* String updateDetailsSql=" update intf_hm_receive_dtls d SET d.record_status = 1, d.updated_by = 101,d.updated_date_time = SYSDATE"
	              +" WHERE d.record_status =0 AND d.hm_seq_no = ?";*/
	      try{
	       jpodConn.setAutoCommit(false);
	        hdrStmt = jpodConn.prepareStatement(updateHdrSql);
	       // detailsStmt = jpodConn.prepareStatement(updateDetailsSql);
	        
	        for (HmReceiveBaseDetails hmd: HmDetList) {
	          
	          hdrStmt.setString(1,hmd.getCastNo());
	          hdrStmt.setString(2, hmd.getHmLadleNo());
	          hdrStmt.setInt(3, hmd.getHmSeqNo());
	         // hdrStmt.executeUpdate();
	          hdrStmt.addBatch();
	          
	          //detailsStmt.setInt(1, hmd.getHmSeqNo());
	          //detailsStmt.addBatch();
	          }
	         hdrStmt.executeBatch();
	        //detailsStmt.executeBatch();
	      }finally{
	        if (hdrStmt != null) {
	          hdrStmt.close();
	            }
	           /* if (detailsStmt != null) {
	              detailsStmt.close();
	            }*/
	       
	      }
	    
	    
	  }
	  
	public HmReceiveBaseDetails getHmReceiveBaseDetails(Integer hmRevId) {
		Session session=getNewSession();
		HmReceiveBaseDetails obj=null;
		try {

			String hql = "select a from HmReceiveBaseDetails a where hmRecvId= "+hmRevId;
			
			obj=(HmReceiveBaseDetails) session.createQuery(hql).uniqueResult();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		return obj;
	  }



	@Override
	public Double updateHmDetails(Integer hmRevId, Double tareWeight) {
		Session session=getSession();
		HmReceiveBaseDetails hmReceieveDetails=null;
	
		Double hmNet = null;
			String result = Constants.UPDATE;
			try {
				
				hmReceieveDetails=getHmReceiveBaseDetails(hmRevId);
				
				Double hmSmsMeasuredWt =hmReceieveDetails.getHmSmsMeasuredWt();			
				 hmNet=hmSmsMeasuredWt-tareWeight;
				
				Query query=session.createQuery("update HmReceiveBaseDetails set hmAvlblWt ="+hmNet+", hmLadleTareWt="+tareWeight+"  where hmRecvId ="+hmRevId );				
				int rows=query.executeUpdate();
				//hmReceieveDetails=(HmReceiveBaseDetails) sqlQ.uniqueResult();
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EofProductionDaoImpl.class+" Inside eofHeatProductionUpdate Exception..", e);
				result = Constants.UPDATE_FAIL;
			}
			
			//hmReceieveDetails=getHmReceiveBaseDetails(hmRevId);
			
		
		
		return hmNet;
	}

	

}