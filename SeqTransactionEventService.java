package com.smes.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smes.trans.dao.impl.SeqTransactionEventDao;
import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.SeqTransactionEvent;

@Service
public class SeqTransactionEventService {

	@Autowired
	SeqTransactionEventDao seqTrnsEvtDao;
	
	@Transactional
	public List<SeqTransactionEvent> getSeqTrnasEventsByGroupNo(Integer groupNo,Integer sub_unit_id) {
		return seqTrnsEvtDao.getSeqTrnasEventsByGroupNo(groupNo, sub_unit_id);
	}
	
	@Transactional
	public String saveCCMSeqEvent(SeqTransactionEvent model) {
		return seqTrnsEvtDao.saveCCMSeqEvent(model);
	}
	
	@Transactional
	public String updateCCMSeqEvent(SeqTransactionEvent model) {
		return seqTrnsEvtDao.updateCCMSeqEvent(model);
	}
	
	@Transactional
	public String saveCCMSeqGroupDtls(CCMSeqGroupDetails model) {
		return seqTrnsEvtDao.saveCCMSeqGroupDtls(model);
	}
	
	@Transactional
	public String updateCCMSeqGroupDtls(CCMSeqGroupDetails model) {
		return seqTrnsEvtDao.updateCCMSeqGroupDtls(model);
	}
	
	public CCMSeqGroupDetails getCCMSeqGroupDtlById(Integer seqGroupDtlId){
		return seqTrnsEvtDao.getCCMSeqGroupDtlById(seqGroupDtlId);
	}
	public CCMSeqGroupDetails getCCMSeqGroupDtlByNoUnit(Integer seqNo, Integer sub_unit){
		return seqTrnsEvtDao.getCCMSeqGroupDtlByNoUnit(seqNo, sub_unit);
	}
	public CCMSeqGroupDetails getCCMSeqGroupDtlBySeqNoAndUnit(Integer seqNo, Integer sub_unit){
		return seqTrnsEvtDao.getCCMSeqGroupDtlBySeqNoAndUnit(seqNo, sub_unit);
	}
}
