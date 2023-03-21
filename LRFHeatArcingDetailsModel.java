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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.LookupMasterModel;

@Entity
@Table(name="TRNS_LRF_HEAT_ARCING_DTLS")
@TableGenerator(name = "TRNS_LRF_HEAT_ARC_DET_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "LrfHeatArcDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class LRFHeatArcingDetailsModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_LRF_HEAT_ARC_DET_SEQ_GEN")
	@Column(name="ARC_SI_NO")
	private Integer arc_sl_no; 
	
	@Column(name="HEAT_ID")
	private String heat_id; 
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ARCING_START_DATE_TIME",updatable=false)
	private Date arc_start_date_time;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ARCING_END_DATE_TIME",updatable=false)
	private Date arc_end_date_time;
	
	@Column(name="POWER_CONSUMPTION")
	private Integer power_consumption; 
	
	@Column(name="BATH_TEMPERATURE")
	private Integer bath_temp; 
	
	@Column(name="BATH_SAMPLE_NO")
	private Integer sample_no; 
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	private Integer created_by;
	
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Column(name="ADDITION_TYPE")
	private Integer addition_type;
	
	@Column(name="ARC_NO")
	private Integer arc_no;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="ADDITION_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupMdl;
	
	@Transient
	private String prev_unit;
	
	public LookupMasterModel getLookupMdl() {
		return lookupMdl;
	}

	public void setLookupMdl(LookupMasterModel lookupMdl) {
		this.lookupMdl = lookupMdl;
	}

	public Integer getArc_sl_no() {
		return arc_sl_no;
	}

	public void setArc_sl_no(Integer arc_sl_no) {
		this.arc_sl_no = arc_sl_no;
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

	public Date getArc_start_date_time() {
		return arc_start_date_time;
	}

	public void setArc_start_date_time(Date arc_start_date_time) {
		this.arc_start_date_time = arc_start_date_time;
	}

	public Date getArc_end_date_time() {
		return arc_end_date_time;
	}

	public void setArc_end_date_time(Date arc_end_date_time) {
		this.arc_end_date_time = arc_end_date_time;
	}

	public Integer getPower_consumption() {
		return power_consumption;
	}

	public void setPower_consumption(Integer power_consumption) {
		this.power_consumption = power_consumption;
	}

	public Integer getBath_temp() {
		return bath_temp;
	}

	public void setBath_temp(Integer bath_temp) {
		this.bath_temp = bath_temp;
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

	public Integer getAddition_type() {
		return addition_type;
	}

	public void setAddition_type(Integer addition_type) {
		this.addition_type = addition_type;
	}

	public Integer getArc_no() {
		return arc_no;
	}

	public void setArc_no(Integer arc_no) {
		this.arc_no = arc_no;
	}

	public Integer getSample_no() {
		return sample_no;
	}

	public void setSample_no(Integer sample_no) {
		this.sample_no = sample_no;
	}

	public String getPrev_unit() {
		return prev_unit;
	}

	public void setPrev_unit(String prev_unit) {
		this.prev_unit = prev_unit;
	}
	
}
