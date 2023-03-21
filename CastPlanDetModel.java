package com.smes.trans.model;

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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Table(name="TRNS_CAST_PLAN_DETAILS")
@TableGenerator(name = "TRNS_CAST_PLAN_DET_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "CastPlanDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CastPlanDetModel {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CAST_PLAN_DET_SEQ_GEN")
	@Column(name="PLAN_SI_NO")
	private Integer plan_sl_no;
	
	@Column(name="RUNNING_ID")
	private Integer running_id;
	
	@Column(name="PLAN_ID")
	private Integer plan_id;
	
	@Column(name="LINE_ID")
	private Integer line_id;
	
	@Column(name="CREATED_BY")
	private Integer created_by;
	
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="RUNNING_ID" ,referencedColumnName="RUNNING_ID",insertable=false,updatable=false)
	private CastRunningStatusModel castRunStatusModel;
	public CastRunningStatusModel getCastRunStatusModel() {
		return castRunStatusModel;
	}

	public void setCastRunStatusModel(CastRunningStatusModel castRunStatusModel) {
		this.castRunStatusModel = castRunStatusModel;
	}

	public Integer getPlan_sl_no() {
		return plan_sl_no;
	}

	public void setPlan_sl_no(Integer plan_sl_no) {
		this.plan_sl_no = plan_sl_no;
	}

	public Integer getRunning_id() {
		return running_id;
	}

	public void setRunning_id(Integer running_id) {
		this.running_id = running_id;
	}

	public Integer getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(Integer plan_id) {
		this.plan_id = plan_id;
	}

	public Integer getLine_id() {
		return line_id;
	}

	public void setLine_id(Integer line_id) {
		this.line_id = line_id;
	}

	public Integer getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date_time() {
		return created_date_time;
	}

	public void setCreated_date_time(Date created_date_time) {
		this.created_date_time = created_date_time;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_date_time() {
		return updated_date_time;
	}

	public void setUpdated_date_time(Date updated_date_time) {
		this.updated_date_time = updated_date_time;
	}

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
	}
	
	
}
