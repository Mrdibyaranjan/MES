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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;


@Entity
@Table(name="TRNS_LRF_HEAT_DTLS_PSN_BKP")
@TableGenerator(name = "TRNS_LRF_HEAT_DTLS_BKP_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "LrfHeatBkpSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class LRFHeatDetailsPsnBkpModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_LRF_HEAT_DTLS_BKP_SEQ_GEN")
	@Column(name="TRNS_BKP_SI_NO")
	private Integer trns_bkp_sl_no; 
	
	@Column(name="TRNS_SI_NO")
	private Integer trns_sl_no; 

	@Column(name="HEAT_ID")
	private String heat_id;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steel_ladle_no;
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date createdDateTime;	

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updatedDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PRODUCTION_DATE")
	private Date production_date;
	
	@Column(name="PRODUCTION_SHIFT")
	private Integer production_shift;
	
	@Column(name="AIM_PSN")
	private Integer aim_psn;
	
	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="HEAT_PLAN_LINE_NO")
	private Integer heat_plan_line_no;
	
	@Column(name="PREV_UNIT")
	private String prev_unit;
	
	@Column(name="STEEL_WT")
	private Double steel_wgt;

	public Integer getTrns_sl_no() {
		return trns_sl_no;
	}

	public void setTrns_sl_no(Integer trns_sl_no) {
		this.trns_sl_no = trns_sl_no;
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

	public Integer getSteel_ladle_no() {
		return steel_ladle_no;
	}

	public void setSteel_ladle_no(Integer steel_ladle_no) {
		this.steel_ladle_no = steel_ladle_no;
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

	public Date getProduction_date() {
		return production_date;
	}

	public void setProduction_date(Date production_date) {
		this.production_date = production_date;
	}

	public Integer getProduction_shift() {
		return production_shift;
	}

	public void setProduction_shift(Integer production_shift) {
		this.production_shift = production_shift;
	}

	public Integer getAim_psn() {
		return aim_psn;
	}

	public void setAim_psn(Integer aim_psn) {
		this.aim_psn = aim_psn;
	}

	public String getPrev_unit() {
		return prev_unit;
	}

	public void setPrev_unit(String prev_unit) {
		this.prev_unit = prev_unit;
	}

	public Integer getTrns_bkp_sl_no() {
		return trns_bkp_sl_no;
	}

	public void setTrns_bkp_sl_no(Integer trns_bkp_sl_no) {
		this.trns_bkp_sl_no = trns_bkp_sl_no;
	}

	public Double getSteel_wgt() {
		return steel_wgt;
	}

	public void setSteel_wgt(Double steel_wgt) {
		this.steel_wgt = steel_wgt;
	}

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Integer getHeat_plan_line_no() {
		return heat_plan_line_no;
	}

	public void setHeat_plan_line_no(Integer heat_plan_line_no) {
		this.heat_plan_line_no = heat_plan_line_no;
	}
}
