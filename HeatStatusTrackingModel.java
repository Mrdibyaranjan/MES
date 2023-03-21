package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;


@Entity
@Table(name="TRNS_HEAT_TRACKING_STATUS")
@TableGenerator(name = "TRNS_HEAT_TRACKING_STATUS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatTrackingSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class HeatStatusTrackingModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_HEAT_TRACKING_STATUS_SEQ_GEN")
	@Column(name="HEAT_TARCK_ID")
	private Integer heat_track_id; 
	
	@Column(name="HEAT_ID", nullable = false)
	private String heat_id;
	
	@Column(name="HEAT_COUNTER", nullable = false)
	private Integer heat_counter;
	
	@Column(name="MAIN_STATUS")
	private String main_status;
	
	@Column(name="ACT_PROC_PATH")
	private String act_proc_path;
	
	@Column(name="CURRENT_UNIT")
	private String current_unit;
	
	@Column(name="UNIT_PROCESS_STATUS")
	private String unit_process_status;
	
	@Column(name="EOF_STATUS")
	private String eof_status;
	
	@Column(name="LRF_STATUS")
	private String lrf_status;
	
	@Column(name="VD_STATUS")
	private String vd_status;
	
	@Column(name="BLT_CAS_STATUS")
	private String blt_cas_status;
	
	@Column(name="BLM_CAS_STATUS")
	private String blm_cas_status;
	
	@Column(name="TCM_STATUS")
	private String tcm_status;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date createdDateTime;	

	@Column(name="UPDATED_BY",insertable=false)
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updatedDateTime;

	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="HEAT_ID_OLD")
	private String heat_id_old;
	
	@Column(name="LADLE_ID")
	private Integer ladle_id;
	
	@Column(name="LRF_STATUS_AF_VD")
	private String lrf_status_af_vd;
	
	@Column(name="PROD_POSTED_BY")
	private Integer prodPostedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PROD_POSTED_DATE")
	private Date prodPostedDate;
	
	@Column(name="CONSUMPTION_POSTED")
	private String isConsPosted;
	
	@Column(name="CONS_POSTED_BY")
	private Integer consPostedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CONS_POSTED_DATE")
	private Date consPostedDate;
	
	@Column(name="INSPECTION_DONE")
	private String inspection_done;
	
	@Transient
	private Integer inActiveHeatTrackId;
	
	public String getLrf_status_af_vd() {
		return lrf_status_af_vd;
	}

	public void setLrf_status_af_vd(String lrf_status_af_vd) {
		this.lrf_status_af_vd = lrf_status_af_vd;
	}

	public Integer getLadle_id() {
		return ladle_id;
	}

	public void setLadle_id(Integer ladle_id) {
		this.ladle_id = ladle_id;
	}

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public String getMain_status() {
		return main_status;
	}

	public void setMain_status(String main_status) {
		this.main_status = main_status;
	}

	public String getInspection_done() {
		return inspection_done;
	}

	public void setInspection_done(String inspection_done) {
		this.inspection_done = inspection_done;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAct_proc_path() {
		return act_proc_path;
	}

	public void setAct_proc_path(String act_proc_path) {
		this.act_proc_path = act_proc_path;
	}

	public String getCurrent_unit() {
		return current_unit;
	}

	public void setCurrent_unit(String current_unit) {
		this.current_unit = current_unit;
	}

	public String getUnit_process_status() {
		return unit_process_status;
	}

	public void setUnit_process_status(String unit_process_status) {
		this.unit_process_status = unit_process_status;
	}

	public String getEof_status() {
		return eof_status;
	}

	public void setEof_status(String eof_status) {
		this.eof_status = eof_status;
	}

	public String getLrf_status() {
		return lrf_status;
	}

	public void setLrf_status(String lrf_status) {
		this.lrf_status = lrf_status;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public Integer getHeat_track_id() {
		return heat_track_id;
	}

	public void setHeat_track_id(Integer heat_track_id) {
		this.heat_track_id = heat_track_id;
	}

	public String getHeat_id() {
		return heat_id;
	}

	public void setHeat_id(String heat_id) {
		this.heat_id = heat_id;
	}

	public Integer getHeat_counter() {
		return heat_counter;
	}

	public void setHeat_counter(Integer heat_counter) {
		this.heat_counter = heat_counter;
	}

	public String getVd_status() {
		return vd_status;
	}

	public void setVd_status(String vd_status) {
		this.vd_status = vd_status;
	}

	public String getBlt_cas_status() {
		return blt_cas_status;
	}

	public void setBlt_cas_status(String blt_cas_status) {
		this.blt_cas_status = blt_cas_status;
	}

	public String getBlm_cas_status() {
		return blm_cas_status;
	}

	public void setBlm_cas_status(String blm_cas_status) {
		this.blm_cas_status = blm_cas_status;
	}

	public String getTcm_status() {
		return tcm_status;
	}

	public void setTcm_status(String tcm_status) {
		this.tcm_status = tcm_status;
	}

	public String getHeat_id_old() {
		return heat_id_old;
	}

	public void setHeat_id_old(String heat_id_old) {
		this.heat_id_old = heat_id_old;
	}

	public Integer getInActiveHeatTrackId() {
		return inActiveHeatTrackId;
	}

	public void setInActiveHeatTrackId(Integer inActiveHeatTrackId) {
		this.inActiveHeatTrackId = inActiveHeatTrackId;
	}

	public Integer getProdPostedBy() {
		return prodPostedBy;
	}

	public void setProdPostedBy(Integer prodPostedBy) {
		this.prodPostedBy = prodPostedBy;
	}

	public Date getProdPostedDate() {
		return prodPostedDate;
	}

	public void setProdPostedDate(Date prodPostedDate) {
		this.prodPostedDate = prodPostedDate;
	}

	public String getIsConsPosted() {
		return isConsPosted;
	}

	public void setIsConsPosted(String isConsPosted) {
		this.isConsPosted = isConsPosted;
	}

	public Integer getConsPostedBy() {
		return consPostedBy;
	}

	public void setConsPostedBy(Integer consPostedBy) {
		this.consPostedBy = consPostedBy;
	}

	public Date getConsPostedDate() {
		return consPostedDate;
	}

	public void setConsPostedDate(Date consPostedDate) {
		this.consPostedDate = consPostedDate;
	}
}
	
	