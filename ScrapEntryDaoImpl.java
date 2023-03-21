package com.smes.trans.dao.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.masters.model.ScrapBucketStatusModel;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.ScrapWeightDetails;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("scrapEntryDao")
public class ScrapEntryDaoImpl extends GenericDaoImpl<ScrapBucketDetails, Long> implements ScrapEntryDao{

	private static final Logger logger = Logger.getLogger(ScrapEntryDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ScrapBucketHdr> getLoadedScrapBktNos() {
		// TODO Auto-generated method stub
		logger.info("inside .. getLoadedScrapBktNos....."+ScrapEntryDaoImpl.class);
		List<ScrapBucketHdr> list=new ArrayList<ScrapBucketHdr>();
		Session session=getNewSession();
		try { 
			String hql = "select a "//, to_char(scrap_prod_date,'dd/mm/yyyy hh24:ss') as scrap_prod_dt "
					+ "from ScrapBucketHdr a, ScrapBucketStatusModel b " //, ScrapPatternMasterModel c "
					+ "where b.scrap_bucket_id = a.scrap_bkt_id "
					+ "and a.scrapBucketLoadedStatusModel.lookup_type = 'SCRAP_BUCKET_STATUS' "
					+ "and a.scrapBucketLoadedStatusModel.lookup_code in ('"+Constants.SCRAP_BKT_LOAD_STATUS+"')) "
					+ "and a.scrapBucketStatusModel.scrap_bucket_status= a.scrap_bkt_load_status "
					+ "and a.scrapBucketStatusModel.record_status=1";
			list=(List<ScrapBucketHdr>) session.createQuery(hql).list();
			//hdrObj = list;
		} catch (Exception e) {
			// TODO: handle exception
			list = null;
			logger.error("error in getLoadedScrapBktNos........"+e);
		}
		finally{
			close(session);
		}		
		return list;		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ScrapBucketHdr getLoadedScrapBktsByHeaderId(Integer scrap_bkt_hrd_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getLoadedScrapBktsByHeaderId....."+ScrapEntryDaoImpl.class);
		List<ScrapBucketHdr> list=new ArrayList<ScrapBucketHdr>();
		ScrapBucketHdr hdrObj = new ScrapBucketHdr();
		Session session=getNewSession();
		try {

			String hql = "select a from ScrapBucketHdr a where a.scrap_bkt_header_id ="+scrap_bkt_hrd_id+"";
			list=(List<ScrapBucketHdr>) session.createQuery(hql).list();
			hdrObj = list.get(0);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getLoadedScrapBktsByHeaderId........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return hdrObj;
		
	}

	public ScrapBucketHdr getScrapBktHdr(Integer scrap_bkt_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getScrapBktHdr....."+ScrapEntryDaoImpl.class);
		List<ScrapBucketHdr> list=new ArrayList<ScrapBucketHdr>();
		ScrapBucketHdr hdrObj = null;
		Session session=getNewSession();
		try { 
			String hql = "select a from ScrapBucketHdr a where a.scrap_bkt_id ="+scrap_bkt_id+" and a.scrap_bkt_load_status in "
					+ "(select lu.lookup_id from LookupMasterModel lu where lu.lookup_type = 'SCRAP_BUCKET_STATUS' "
					+ "and lu.lookup_code in ('"+Constants.SCRAP_BKT_LOAD_STATUS+"')) and a.scrapBucketStatusModel.scrap_bucket_status= a.scrap_bkt_load_status "
					+ "and a.scrapBucketStatusModel.record_status=1"; 
			
			list=(List<ScrapBucketHdr>) session.createQuery(hql).list();
			if(list.size() > 0)
				hdrObj = list.get(0);

		} catch (Exception e) {
			// TODO: handle exception
			hdrObj = null;
			logger.error("error in getScrapBktHdr........"+e);
			//e.printStackTrace();
		}
		finally{
			close(session);
		}
		return hdrObj;	
	}
	
	@Override
	public List<ScrapBucketDetails> getScrapEntryDetails(Integer scrap_bucket_id, Integer scrap_pattern_id, Integer bucket_header_id) {
		// TODO Auto-generated method stub
		logger.info("inside getScrapDtlsEntryDetails"+ScrapEntryDaoImpl.class);
		
		String hql = ""; Integer scrap_header_id = 0;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		List<ScrapBucketDetails> scrapBucketDtls = new ArrayList<ScrapBucketDetails>(); 
		ScrapBucketDetails obj;
		if (bucket_header_id == null || bucket_header_id == 0) {
			ScrapBucketHdr hdrObj = new ScrapBucketHdr();
			if (scrap_bucket_id != 0) {
				hdrObj = getScrapBktHdr(scrap_bucket_id);
			} else {
				hdrObj = null;
			}
			
			if (hdrObj != null) {
				scrap_header_id = hdrObj.getScrap_bkt_header_id();
			}
		} else {
			scrap_header_id = bucket_header_id;
		}
		
		try {
			if (scrap_header_id > 0) {
				hql="SELECT (SELECT a.scrap_bkt_detail_id || '&&' || a.material_qty ||  '&&' || a.record_version FROM ScrapBucketDetails a "
						+ "WHERE a.material_id = c.material_id AND a.scrap_bkt_header_id ='"+scrap_header_id+"'), c.material_id, c.material_desc,"
						+ "d.lookup_value, h.scrap_bkt_qty, h.scrap_pattern_id, (select spd.scrap_percent from ScrapPatternMaterialMaster spd "
						+ "where spd.scrap_pattern_id=h.scrap_pattern_id and spd.scrap_mat=c.material_id) as scrap_percent,'' as tot_wgt ,'' as scrap_pattern_dtl_id,c.sap_matl_desc "
						+ "FROM MtrlProcessConsumableMstrModel c, LookupMasterModel d, "
						+ "LookupMasterModel e, ScrapBucketDetails f, ScrapBucketHdr h WHERE f.material_id = c.material_id "
						+ "AND h.scrap_bkt_header_id = f.scrap_bkt_header_id AND h.scrap_bkt_header_id = '"+scrap_header_id+"' "
			    		+ "AND c.uom = d.lookup_id AND c.record_status = 1 AND c.material_type = e.lookup_id AND e.lookup_code = 'SCRAP' "
			    		+ "ORDER BY c.order_seq asc";				
			} else {
				hql="select '' as bucket_header_id, c.material_id, c.material_desc, d.lookup_value, 0 as scrap_bkt_qty, sp.scrap_pattern_id, "
						+ "spd.scrap_percent, sp.tot_wgt, spd.scrap_pattern_dtl_id, c.sap_matl_desc "
						+ "from ScrapPatternMasterModel sp, ScrapPatternMaterialMaster spd, MtrlProcessConsumableMstrModel c, LookupMasterModel d "
						+ "where sp.scrap_pattern_id = spd.scrap_pattern_id "
						+ "and c.material_id = spd.scrap_mat and c.uom = d.lookup_id "
						+ "and c.record_status = 1"
						+ "and sp.scrap_pattern_id = "+scrap_pattern_id +" ORDER BY c.order_seq asc ";
			}
		List ls=(List<ScrapBucketDetails>) getResultFromNormalQuery(hql);
				
		Iterator it = ls.iterator();
		while(it.hasNext()){
			Object rows[] = (Object[])it.next();
			obj=new ScrapBucketDetails();
			
			if(rows[0]!=null && rows[0].toString().trim().length()>0){
				String s[] = (null == rows[0] ? null : rows[0].toString().split("&&"));
				
				obj.setScrap_bkt_detail_id(Integer.parseInt((null==s[0])?null:s[0].toString()));
				obj.setMaterial_qty(Double.parseDouble((null==s[1])?null:s[1].toString()));
				obj.setRecord_version(Integer.parseInt((null==s[2])?null:s[2].toString()));
				
			}else{
				obj.setScrap_bkt_detail_id(null);
				obj.setMaterial_qty(null);
				obj.setRecord_version(null);
				obj.setSapMtlid(null);
			}
			
			obj.setMaterial_id(Integer.parseInt((null==rows[1])?null:rows[1].toString()));
			obj.setMtrlName((null==rows[2])?null:rows[2].toString());
			obj.setUom((null==rows[3])?null:rows[3].toString());
			obj.setBckHeaderQty(Double.valueOf(twoDForm.format(Double.parseDouble((null==rows[4])?null:rows[4].toString()))));
			//obj.setRecord_version(Integer.parseInt((null==rows[4])?null:rows[4].toString()));
			obj.setScrap_pattern_id(Integer.parseInt((null==rows[5])?"0":rows[5].toString()));
			obj.setMatPercent(Double.parseDouble((null==rows[6])?null:rows[6].toString()));
			obj.setSapMtlid((null==rows[9])?null:rows[9].toString());
			
			scrapBucketDtls.add(obj);
		}
		
		}catch(Exception e){
			logger.error("Exception.. getScrapEntryDetails..."+ e.getMessage());
			e.printStackTrace();
		}
		
		return scrapBucketDtls;
	}

	@Override
	public String scrapBktDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session=getNewSession();
		try{
			begin(session);
			if((ScrapBucketHdr)mod_obj.get("SCRAPHDR")!=null)
			{
				ScrapBucketHdr ScrapBHdr=(ScrapBucketHdr)mod_obj.get("SCRAPHDR");
				if(ScrapBHdr.getScrap_bkt_header_id()!=null)
				{
					session.update((ScrapBucketHdr)mod_obj.get("SCRAPHDR"));
				}else{
					session.save((ScrapBucketHdr)mod_obj.get("SCRAPHDR"));
				}
			}
			
			if((ScrapBucketStatusModel)mod_obj.get("SCRAPBKSTS")!=null)
			{
			session.update((ScrapBucketStatusModel)mod_obj.get("SCRAPBKSTS"));
			}
			
			if(Integer.parseInt(mod_obj.get("SCRAPBKCNT").toString())>0)
			{
				ScrapBucketHdr scrphdr=(ScrapBucketHdr)mod_obj.get("SCRAPHDR");
				
				Integer cnt=Integer.parseInt(mod_obj.get("SCRAPBKCNT").toString());
				String key="SCRAPBK";
				for(int i=0;i<=cnt;i++)
				{
					key=key+i;
				if((ScrapBucketDetails)mod_obj.get(key)!=null)
				{
				ScrapBucketDetails scrap_det=(ScrapBucketDetails)mod_obj.get(key);
				scrap_det.setScrap_bkt_header_id(scrphdr.getScrap_bkt_header_id());
				if(scrap_det.getScrap_bkt_detail_id()!=null)
				{
				session.update(scrap_det);
				}else{
				session.save(scrap_det);
				}
				scrap_det=null;
				}
				}
			}
			
			commit(session);
			result =Constants.SAVE;
		} catch(org.hibernate.StaleObjectStateException s)
		{
			logger.error(ScrapEntryDaoImpl.class+" Inside scrapBktDtlsSaveOrUpdate Exception..", s);
			if(s.getEntityName().contains("ScrapBucketDetails"))
			{
			result ="This scrap bucket has already been updated by another user. Please refresh and try again.";
				//result ="The Selected item has already been updated by another user. Please get the updated values";
			}else{
				result=Constants.CONCURRENT_UPDATE_MSG_FAIL;
			}
			rollback(session);
			
		} 
		catch(DataIntegrityViolationException e)
		{
			logger.error(ScrapEntryDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch(ConstraintViolationException e)
		{
			logger.error(ScrapEntryDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(ScrapEntryDaoImpl.class+" Inside scrapBktDtlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;
		
	}

	@Override
	public ScrapBucketDetails getScrapBucketDetailsById(int scrap_det_id) {
		// TODO Auto-generated method stub
				logger.info("inside .. getScrapBucketDetailsById....."+ScrapEntryDaoImpl.class);
				
				ScrapBucketDetails bukobj = new ScrapBucketDetails();
				Session session=getNewSession();
				try {
					bukobj=(ScrapBucketDetails) session.get(ScrapBucketDetails.class, scrap_det_id);

				} catch (Exception e) {
					// TODO: handle exception
					logger.error("error in getScrapBucketDetailsById........"+e);
					e.printStackTrace();
				}
				finally{
					close(session);
				}
				
				return bukobj;
				
			}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScrapBucketDetails> getScrapBktDetList(
			Integer scrap_bkt_header_id) {
		// TODO Auto-generated method stub
		List<ScrapBucketDetails> list=new ArrayList<ScrapBucketDetails>();
		try {
			String sql = "Select a from ScrapBucketDetails a where a.scrap_bkt_header_id="+scrap_bkt_header_id+" ";
			list=(List<ScrapBucketDetails>) getResultFromNormalQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getScrapBktDetList..."+e.getMessage());
		}
		
		return list;
	}

	@Override
	public List<ScrapBucketDetails> getAllScrapBktDetList(String sbktIds) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		List<ScrapBucketDetails> sbktList=new ArrayList<ScrapBucketDetails>();
		ScrapBucketDetails obj;
		try {
			String sql="Select material_id,SUM (material_qty) from ScrapBucketDetails a where a.scrap_bkt_header_id in ("+sbktIds+") group by material_id";
			List ls=(List<ScrapBucketDetails>) getResultFromNormalQuery(sql);
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[] = (Object[])it.next();
				obj=new ScrapBucketDetails();
				
				obj.setMaterial_id(Integer.parseInt((null==rows[0])?null:rows[0].toString()));
				obj.setMaterial_qty(Double.parseDouble((null==rows[1])?null:rows[1].toString()));
				sbktList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getScrapBktDetList..."+e.getMessage());
		}
		
		return sbktList;
	}

	@Override
	public List<ScrapWeightDetails> getScrapDetailsFrmIntf(String work_center) {
		List<ScrapWeightDetails> scrapWeights=new ArrayList<ScrapWeightDetails>();
		Session session=getNewSession();
		try {
			String sql="select a from ScrapWeightDetails  a where trunc(a.trans_date) =trunc(SYSDATE) and a.record_status=0 and a.work_center='"+work_center+"' order by a.trans_date desc";
			//scrapWeights=(List<ScrapWeightDetails>) getResultFromNormalQuery(sql);
			scrapWeights=session.createQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception.. getScrapBktDetList..."+e.getMessage());
		}
		finally{
			close(session);
		}
		
		return scrapWeights;
	}

	@Override
	public String updateIntfScrapEntries(Integer trans_no) {
		String response="";
		Session session=getNewSession();
		try { 
			Calendar calendar=Calendar.getInstance();
		
			
			Query query = session.createQuery("update ScrapWeightDetails set record_status=1 where trans_year="+calendar.get(Calendar.YEAR)+" and trans_no="+trans_no);
			int updatedRow=query.executeUpdate();
//			list=(List<ScrapBucketHdr>) session.createQuery(hql).list();
//			hdrObj = list.get(0);
			
			response="SUCCESS";

		} catch (Exception e) {
			// TODO: handle exception
//			hdrObj = null;
			logger.error("error in getScrapBktHdr........"+e);
			response="ERROR";
			//e.printStackTrace();
		}
		finally{
			close(session);
		}
		return response;
	}

	@Override
	public Integer validateScrapEntry(Integer scrap_bkt_id,Integer load_statusId) {
		Integer count = 0;
		Session session=getNewSession();
		
		try { 
			count = ((Long)session.createQuery("select count(*) from ScrapBucketStatusModel where scrap_bucket_id="+scrap_bkt_id+" and lookupMstrMdl.lookup_value = 'LOADED' and lookupMstrMdl.lookup_id="+load_statusId).uniqueResult()).intValue();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getScrapBktHdr........"+e);
			count=0;
		}
		finally{
			close(session);
		}
		return count;
	}

	@Override
	public List<ScrapBucketDetailsLog> getScrapBktDetLogList(Integer scrap_bkt_header_id) {
		// TODO Auto-generated method stub
		List<ScrapBucketDetailsLog> list=new ArrayList<ScrapBucketDetailsLog>();
		try {
			String sql = "Select a from ScrapBucketDetailsLog a where a.scrap_bkt_header_id="+scrap_bkt_header_id+" ";
			list=(List<ScrapBucketDetailsLog>) getResultFromNormalQuery(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			logger.error("Exception.. getScrapBktDetLogList..."+e.getMessage());
		}
		
		return list;
	}

	@Override
	public String saveOrUpdateScrapCons(ScrapBucketHdr scrapHdrObj, List<ScrapBucketDetailsLog> logLi) {
		// TODO Auto-generated method stub
		String result = "";
		Session session=getNewSession();
		try{
			begin(session);
			if(scrapHdrObj != null){
				session.update(scrapHdrObj);
				for(ScrapBucketDetails d_obj : scrapHdrObj.getScrapBktDtls()) {
					session.update(d_obj);
				}
			}
			if(logLi != null){
				for(ScrapBucketDetailsLog l_obj : logLi) {
					session.save(l_obj);
				}
			}
			commit(session);
			result =Constants.SAVE;
		}catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(ScrapEntryDaoImpl.class+" Inside saveOrUpdateScrapCons Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;
	}
}