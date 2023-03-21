package com.smes.trans.dao.impl;

import java.util.List;

import com.smes.trans.model.CCMSeqGroupDetails;
import com.smes.trans.model.SeqTransactionEvent;

public interface SeqTransactionEventDao {

	List<SeqTransactionEvent> getSeqTrnasEventsByGroupNo(Integer groupNo,Integer sub_unit_id);
	
	public String saveCCMSeqEvent(SeqTransactionEvent model);

	public String updateCCMSeqEvent(SeqTransactionEvent model);
	
	public String saveCCMSeqGroupDtls(CCMSeqGroupDetails model);
	
	public String updateCCMSeqGroupDtls(CCMSeqGroupDetails model);
	
	public CCMSeqGroupDetails getCCMSeqGroupDtlById(Integer seqGroupDtlId);
	
	public CCMSeqGroupDetails getCCMSeqGroupDtlByNoUnit(Integer seqNo, Integer sub_unit);
	public CCMSeqGroupDetails getCCMSeqGroupDtlBySeqNoAndUnit(Integer seqNo, Integer sub_unit);
}
