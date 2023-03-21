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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.ActivityDelayMasterModel;

@Entity
@Table(name="TRNS_DELAY_ENTRY_HDR")
@TableGenerator(name = "TRNS_DELAY_ENTRY_HDR_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "TransDelayEntryHdrSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class TransDelayEntryHeader implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_DELAY_ENTRY_HDR_SEQ_GEN")
	@Column(name="TRNS_DELAY_ENTRY_HDR_ID" ,nullable=false)
	private Integer trns_delay_entry_hdr_id;
	
	@Column(name="TRNS_HEAT_ID")
	private Integer trans_heat_id;

	@Column(name="ACTIVITY_DELAY_ID")
	private Integer activity_delay_id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ACTIVITY_START_TIME")
	private Date activity_start_time;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ACTIVITY_END_TIME")
	private Date activity_end_time;
	
	@Column(name="ACTIVITY_DUR")
	private Integer activity_duration;
	
	@Column(name="TOT_DELAY")
	private Integer total_delay;
	
	@Column(name="CORRECTIVE_ACTION")
	private String corrective_action;
	
	
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date createdDateTime;	

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDateTime;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="activity_delay_id" ,referencedColumnName="activity_delay_id",insertable=false,updatable=false)
	ActivityDelayMasterModel activityDelayMstrMdl;

	

	public Integer getTrans_heat_id() {
		return trans_heat_id;
	}

	public void setTrans_heat_id(Integer trans_heat_id) {
		this.trans_heat_id = trans_heat_id;
	}

	public Integer getActivity_delay_id() {
		return activity_delay_id;
	}

	public void setActivity_delay_id(Integer activity_delay_id) {
		this.activity_delay_id = activity_delay_id;
	}

	public Date getActivity_start_time() {
		return activity_start_time;
	}

	public void setActivity_start_time(Date activity_start_time) {
		this.activity_start_time = activity_start_time;
	}

	public Date getActivity_end_time() {
		return activity_end_time;
	}

	public void setActivity_end_time(Date activity_end_time) {
		this.activity_end_time = activity_end_time;
	}

	public Integer getActivity_duration() {
		return activity_duration;
	}

	public void setActivity_duration(Integer activity_duration) {
		this.activity_duration = activity_duration;
	}

	public Integer getTotal_delay() {
		return total_delay;
	}

	public void setTotal_delay(Integer total_delay) {
		this.total_delay = total_delay;
	}

	public String getCorrective_action() {
		return corrective_action;
	}

	public void setCorrective_action(String corrective_action) {
		this.corrective_action = corrective_action;
	}

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
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

	public ActivityDelayMasterModel getActivityDelayMstrMdl() {
		return activityDelayMstrMdl;
	}

	public void setActivityDelayMstrMdl(ActivityDelayMasterModel activityDelayMstrMdl) {
		this.activityDelayMstrMdl = activityDelayMstrMdl;
	}

	public Integer getTrns_delay_entry_hdr_id() {
		return trns_delay_entry_hdr_id;
	}

	public void setTrns_delay_entry_hdr_id(Integer trns_delay_entry_hdr_id) {
		this.trns_delay_entry_hdr_id = trns_delay_entry_hdr_id;
	}
	
	
	
	
	
	
	
}
