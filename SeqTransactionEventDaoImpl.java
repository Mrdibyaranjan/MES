package com.smes.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.SeqTransactionEvent;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class SeqTransactionEventDaoImpl extends GenericDaoImpl<SeqTransactionEvent, Long> implements SeqTransactionEventDao{

	private static final Logger logger = Logger.getLogger(SeqTransactionEventDaoImpl.class);
	@Override
	public List<SeqTransactionEvent> getSeqTrnasEventsByGroupNo(Integer groupNo,Integer sub_unit_id) {
		List<SeqTransactionEvent> list=new ArrayList<>();
		Session session=getNewSession();
		try { 
			String hql = "select a from SeqTransactionEvent a where a.group_seq_no="+groupNo+" and a.sub_unit_id="+sub_unit_id;
			list=(List<SeqTransactionEvent>) session.createQuery(hql).list();
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
	
	@Override
	public String saveCCMSeqEvent(SeqTransactionEvent model) {
		String result=Constants.SAVE;
		create(model);
		return result;
	}
	@Override
	public String updateCCMSeqEvent(SeqTransactionEvent model) {
		String result=Constants.SAVE;
		update(model);
		return result;
	}

	@Override
	public String saveCCMSeqGroupDtls(CCMSeqGroupDetails model) {
		String result = "";
		Session session=getNewSession();	
		try{
			begin(session);
			session.save(model);
			commit(session);
			result=Constants.SAVE;
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
	public String updateCCMSeqGroupDtls(CCMSeqGroupDetails model) {
		String result = "";
		Session session=getNewSession();	
		try{
			begin(session);
			session.saveOrUpdate(model);
			commit(session);
			result=Constants.SAVE;
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
	public CCMSeqGroupDetails getCCMSeqGroupDtlById(Integer seqGroupDtlId) {
		List<CCMSeqGroupDetails> lst=new ArrayList<CCMSeqGroupDetails>();
		Session session=getNewSession();
		try {
			String hql = "select a from CCMSeqGroupDetails a where a.seq_group_dtls_sl_no="+seqGroupDtlId;
			lst=(List<CCMSeqGroupDetails>) session.createQuery(hql).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(session);
		}
		if(lst.isEmpty()) {
			return null;
		}else {
			return lst.get(0);
		}
	}

	@Override
	public CCMSeqGroupDetails getCCMSeqGroupDtlByNoUnit(Integer seqNo, Integer sub_unit) {
		List<CCMSeqGroupDetails> lst=new ArrayList<CCMSeqGroupDetails>();
		Session session=getNewSession();
		try {
			String hql = "select a from CCMSeqGroupDetails a where a.sub_unit_id="+sub_unit+" and a.seq_group_no < "+seqNo+" order by a.seq_group_no desc";
			lst=(List<CCMSeqGroupDetails>) session.createQuery(hql).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(session);
		}
		if(lst.isEmpty()) {
			return null;
		}else {
			return lst.get(0);
		}
	}

	@Override
	public CCMSeqGroupDetails getCCMSeqGroupDtlBySeqNoAndUnit(Integer seqNo, Integer sub_unit) {
		// TODO Auto-generated method stub
		List<CCMSeqGroupDetails> lst=new ArrayList<CCMSeqGroupDetails>();
		Session session=getNewSession();
		try {
			String hql = "select a from CCMSeqGroupDetails a where a.sub_unit_id="+sub_unit+" and a.seq_group_no = "+seqNo;
			lst=(List<CCMSeqGroupDetails>) session.createQuery(hql).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(session);
		}
		if(lst.isEmpty()) {
			return null;
		}else {
			return lst.get(0);
		}
	}
}