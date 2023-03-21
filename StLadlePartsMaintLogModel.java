package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.PartsMasterModel;

@Entity
@Table(name="TRNS_STLADLE_PARTS_MAINT_LOG")
@TableGenerator(name = "STLADLE_PARTS_MAINT_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "stLadlePartsMaintLogSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class StLadlePartsMaintLogModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STLADLE_PARTS_MAINT_SEQ_GEN")
	@Column(name="STLADLE_PARTS_MAINT_LOG_ID", unique=true, nullable=false)
	private Integer stladle_parts_maint_log_id;

	@Column(name="STLADLE_MAINT_STATUS_ID")
	private Integer stladle_maint_status_id;

	@Column(name="PART_ID")
	private Integer part_id;

	@Column(name="PART_TYPE")
	private Integer part_type;

	@Column(name="PART_SUPPLIER")
	private Integer part_supplier;

	//@JsonFormat
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CHANGE_DATE")
	private Date change_date;
	
	@Column(name="BURNER_NO")
	private Integer burner_no;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HEAT_START_DATE") 
	private Date heat_start_date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HEAT_END_DATE")
	private Date heat_end_date;

	@Column(name="REMARKS")
	private String remarks;

	@Column(name="CREATED_BY") 
	private Integer created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;

	@Column(name="UPDATED_BY")
	private Integer updated_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME") 
	private Date updated_date_time;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="STLADLE_MAINT_STATUS_ID", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	private StLadleMaintStatusModel stLdlMainStstModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PART_ID",insertable=false,updatable=false)
	private PartsMasterModel partsMstrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PART_SUPPLIER",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpSupplier;
	
	@Transient
	private String cdate;
	@Transient
	private String deleteFlag;
	
	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public Integer getStladle_parts_maint_log_id() {
		return stladle_parts_maint_log_id;
	}

	public void setStladle_parts_maint_log_id(Integer stladle_parts_maint_log_id) {
		this.stladle_parts_maint_log_id = stladle_parts_maint_log_id;
	}

	public Integer getStladle_maint_status_id() {
		return stladle_maint_status_id;
	}

	public void setStladle_maint_status_id(Integer stladle_maint_status_id) {
		this.stladle_maint_status_id = stladle_maint_status_id;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public void setPart_id(Integer part_id) {
		this.part_id = part_id;
	}

	public Integer getPart_type() {
		return part_type;
	}

	public void setPart_type(Integer part_type) {
		this.part_type = part_type;
	}

	public Integer getPart_supplier() {
		return part_supplier;
	}

	public void setPart_supplier(Integer part_supplier) {
		this.part_supplier = part_supplier;
	}

	public Date getChange_date() {
		return change_date;
	}

	public void setChange_date(Date change_date) {
		this.change_date = change_date;
	}

	public Integer getBurner_no() {
		return burner_no;
	}

	public void setBurner_no(Integer burner_no) {
		this.burner_no = burner_no;
	}

	public Date getHeat_start_date() {
		return heat_start_date;
	}

	public void setHeat_start_date(Date heat_start_date) {
		this.heat_start_date = heat_start_date;
	}

	public Date getHeat_end_date() {
		return heat_end_date;
	}

	public void setHeat_end_date(Date heat_end_date) {
		this.heat_end_date = heat_end_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public StLadleMaintStatusModel getStLdlMainStstModel() {
		return stLdlMainStstModel;
	}

	public void setStLdlMainStstModel(StLadleMaintStatusModel stLdlMainStstModel) {
		this.stLdlMainStstModel = stLdlMainStstModel;
	}

	public PartsMasterModel getPartsMstrModel() {
		return partsMstrModel;
	}

	public void setPartsMstrModel(PartsMasterModel partsMstrModel) {
		this.partsMstrModel = partsMstrModel;
	}

	public LookupMasterModel getLkpSupplier() {
		return lkpSupplier;
	}

	public void setLkpSupplier(LookupMasterModel lkpSupplier) {
		this.lkpSupplier = lkpSupplier;
	}
}
