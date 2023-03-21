package com.smes.trans.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Table(name="TRNS_CAST_RUN_STATUS")
@TableGenerator(name = "TRNS_CAST_RUN_STATUS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "CastRunStatusSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CastRunningStatusModel {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CAST_RUN_STATUS_SEQ_GEN")
	@Column(name="RUNNING_ID")
	private Integer running_id;
	
	@Column(name="CAST_START_DATE")
	private Date cast_start_date;
	
	@Column(name="CAST_END_DATE")
	private Date cast_end_date;

	@Column(name="SEQ_FINISHED")
	private String seq_finished;
	
	@Column(name="TOTAL_PLAN_HEATS")
	private Integer tot_plan_heats;
	
	@Column(name="TOTAL_CAST_HEATS")
	private Integer tot_cast_heats;
	
	
	
	@Column(name="RUN_CLOSE_REMARKS")
	private String run_close_remarks;
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;

	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="CREATED_BY")
	private Integer created_by;
	
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Transient
	private Integer heat_plan_id;

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Integer getRunning_id() {
		return running_id;
	}

	public void setRunning_id(Integer running_id) {
		this.running_id = running_id;
	}

	public Date getCast_start_date() {
		return cast_start_date;
	}

	public void setCast_start_date(Date cast_start_date) {
		this.cast_start_date = cast_start_date;
	}

	public Date getCast_end_date() {
		return cast_end_date;
	}

	public void setCast_end_date(Date cast_end_date) {
		this.cast_end_date = cast_end_date;
	}

	public String getSeq_finished() {
		return seq_finished;
	}

	public void setSeq_finished(String seq_finished) {
		this.seq_finished = seq_finished;
	}

	public Integer getTot_plan_heats() {
		return tot_plan_heats;
	}

	public void setTot_plan_heats(Integer tot_plan_heats) {
		this.tot_plan_heats = tot_plan_heats;
	}

	public Integer getTot_cast_heats() {
		return tot_cast_heats;
	}

	public void setTot_cast_heats(Integer tot_cast_heats) {
		this.tot_cast_heats = tot_cast_heats;
	}


	public String getRun_close_remarks() {
		return run_close_remarks;
	}

	public void setRun_close_remarks(String run_close_remarks) {
		this.run_close_remarks = run_close_remarks;
	}

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
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
}

