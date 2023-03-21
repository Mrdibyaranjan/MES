package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMTundishDetailsModel;
import com.smes.trans.model.MtubeTrnsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class CCMTundishDetailsDaoImpl extends GenericDaoImpl<CCMTundishDetailsModel, Long> implements CCMTundishDetailsDao {
	@Transactional
	@Override
	public List<CCMTundishDetailsModel> getccmTundishDetailsByTrnsSlno(Integer trns_sl_no) {
		List<CCMTundishDetailsModel> lst= new ArrayList<>();
		Session session=getNewSession();
		try {
			String hql = "select a from CCMTundishDetailsModel a where a.trns_sl_no="+trns_sl_no+" and a.record_status =1";	
			lst=(List<CCMTundishDetailsModel>) session.createQuery(hql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(session);
		}
		return lst;
	}
	
	@Transactional
	@Override
	public String saveccmTundishDetails(CCMTundishDetailsModel model) {
		// TODO Auto-generated method stub
		String result=Constants.SAVE;
		create(model);
		return result;
	}

	@Transactional
	@Override
	public String updateccmTundishDetails(CCMTundishDetailsModel model) {
		// TODO Auto-generated method stub
		String result=Constants.SAVE;
		update(model);
		return result;
	}

	@Override
	public MtubeTrnsModel mtubeTrns(Integer mtube_Trans_id) {
		List<MtubeTrnsModel> lst= new ArrayList<>();
		MtubeTrnsModel mtube=new MtubeTrnsModel();
		Session session=getNewSession();
		try 
		{
			String hql = "select a from MtubeTrnsModel a where a.ccm_mtube_trns_id="+mtube_Trans_id+" ";	
			lst = session.createQuery(hql).list();
			
			if(lst.size()>0)
			{
				mtube =lst.get(0);	
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			close(session);
		}
		return mtube;
	}

	

}
