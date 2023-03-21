package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.DelayAgencyMasterModel;
import com.smes.masters.model.DelayReasonMasterModel;

@Entity
@Table(name="TRNS_DELAY_ENTRY_DTLS")
@TableGenerator(name = "TRNS_DELAY_ENTRY_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "TransDelayEntryDtlsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class TransDelayDetailsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_DELAY_ENTRY_DTLS_SEQ_GEN")
	@Column(name="TRNS_ENTRY_DTL_ID" ,nullable=false)
	private Integer trans_delay_dtl_id;
	
	@Column(name="DELAY_ENTRY_HDR_ID" ,nullable=false)
	private Integer delay_entry_hdr_id;
	
	@Column(name="DELAY_REASON" ,nullable=false)
	private Integer delay_reason;
	
	@Column(name="DELAY_AGENCY" ,nullable=false)
	private Integer delay_agency;
	
	@Column(name="DELAY_DTL_DUR" ,nullable=false)
	private Integer delay_dtl_duration;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date createdDateTime;	

	@Column(name="UPDATED_BY")
	private Integer updatedBy;
	
	@Column(name="DELAY_COMMENTS")
	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDateTime;
	
//	@ManyToOne(optional=true)
//	@JoinColumn(name="delay_entry_hdr_id" ,referencedColumnName="trns_delay_entry_hdr_id",insertable=false,updatable=false)
//	TransDelayEntryHeader transDelayEntryHdr;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="delay_reason" ,referencedColumnName="delay_reason_id",insertable=false,updatable=false)
	DelayReasonMasterModel delayResonMstrMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="delay_agency" ,referencedColumnName="delay_agency_id",insertable=false,updatable=false)
	DelayAgencyMasterModel delayAgencyMstrMdl;

	@Transient
	private String activities;
	
	@Transient
	private String delay_details;
	
	public Integer getTrans_delay_dtl_id() {
		return trans_delay_dtl_id;
	}

	public void setTrans_delay_dtl_id(Integer trans_delay_dtl_id) {
		this.trans_delay_dtl_id = trans_delay_dtl_id;
	}

	public Integer getDelay_entry_hdr_id() {
		return delay_entry_hdr_id;
	}

	public void setDelay_entry_hdr_id(Integer delay_entry_hdr_id) {
		this.delay_entry_hdr_id = delay_entry_hdr_id;
	}

	public Integer getDelay_reason() {
		return delay_reason;
	}

	public void setDelay_reason(Integer delay_reason) {
		this.delay_reason = delay_reason;
	}

	public Integer getDelay_agency() {
		return delay_agency;
	}

	public void setDelay_agency(Integer delay_agency) {
		this.delay_agency = delay_agency;
	}

	public Integer getDelay_dtl_duration() {
		return delay_dtl_duration;
	}

	public void setDelay_dtl_duration(Integer delay_dtl_duration) {
		this.delay_dtl_duration = delay_dtl_duration;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

//	public TransDelayEntryHeader getTransDelayEntryHdr() {
//		return transDelayEntryHdr;
//	}
//
//	public void setTransDelayEntryHdr(TransDelayEntryHeader transDelayEntryHdr) {
//		this.transDelayEntryHdr = transDelayEntryHdr;
//	}

	public DelayReasonMasterModel getDelayResonMstrMdl() {
		return delayResonMstrMdl;
	}

	public void setDelayResonMstrMdl(DelayReasonMasterModel delayResonMstrMdl) {
		this.delayResonMstrMdl = delayResonMstrMdl;
	}

	public DelayAgencyMasterModel getDelayAgencyMstrMdl() {
		return delayAgencyMstrMdl;
	}

	public void setDelayAgencyMstrMdl(DelayAgencyMasterModel delayAgencyMstrMdl) {
		this.delayAgencyMstrMdl = delayAgencyMstrMdl;
	}

	public String getActivities() {
		return activities;
	}

	public void setActivities(String activities) {
		this.activities = activities;
	}

	public String getDelay_details() {
		return delay_details;
	}

	public void setDelay_details(String delay_details) {
		this.delay_details = delay_details;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
	
	
	
	
}
