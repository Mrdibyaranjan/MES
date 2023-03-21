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

import com.smes.masters.model.TundishMasterModel;

@Entity
@Table(name="TRNS_CCM_TUNDISH_DETAILS")
@TableGenerator(name = "TRNS_CCM_TUNDISH_DETAILS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmTundishDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CCMTundishDetailsModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_TUNDISH_DETAILS_SEQ_GEN")
	@Column(name="TUN_TRNS_ID")
	private Integer tun_trns_id;
	
	@Column(name="TRNS_SL_NO")
	private Integer trns_sl_no;
	
	@Column(name="TUN_ID")
	private Integer tun_id;
	
	@Column(name="TUN_LOSS")
	private Float tun_loss;
	
	@Column(name="TUN_TEMP1")
	private Float tun_temp1;
	
	@Column(name="TUN_TEMP2")
	private Float tun_temp2;
	
	@Column(name="TUN_TEMP3")
	private Float tun_temp3;
	
	@Column(name="CREATED_BY" ,updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updated_date_time;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="LIQ_TEMP")
	private Integer liq_temp;
	
	@Column(name="SUPER_HEAT1")
	private Float super_heat1;
	
	@Column(name="SUPER_HEAT2")
	private Float super_heat2;
	
	@Column(name="SUPER_HEAT3")
	private Float super_heat3;
	
	@Column(name="TUNDISH_LIFE")
	private Integer tundish_life;

	@ManyToOne(optional=true)
	@JoinColumn(name="TUN_ID" ,referencedColumnName="TUNDISH_SL_NO",insertable=false,updatable=false)
	private TundishMasterModel tundMstrModel;
	
	@Transient
	private Integer prev_tund_id;
	
	public Integer getTundish_life() {
		return tundish_life;
	}

	public void setTundish_life(Integer tundish_life) {
		this.tundish_life = tundish_life;
	}

	public Float getSuper_heat1() {
		return super_heat1;
	}

	public void setSuper_heat1(Float super_heat1) {
		this.super_heat1 = super_heat1;
	}

	public Float getSuper_heat2() {
		return super_heat2;
	}

	public void setSuper_heat2(Float super_heat2) {
		this.super_heat2 = super_heat2;
	}

	public Float getSuper_heat3() {
		return super_heat3;
	}

	public void setSuper_heat3(Float super_heat3) {
		this.super_heat3 = super_heat3;
	}
	
	public TundishMasterModel getTundMstrModel() {
		return tundMstrModel;
	}

	public void setTundMstrModel(TundishMasterModel tundMstrModel) {
		this.tundMstrModel = tundMstrModel;
	}

	public Integer getTun_trns_id() {
		return tun_trns_id;
	}

	public void setTun_trns_id(Integer tun_trns_id) {
		this.tun_trns_id = tun_trns_id;
	}

	public Integer getTrns_sl_no() {
		return trns_sl_no;
	}

	public void setTrns_sl_no(Integer trns_sl_no) {
		this.trns_sl_no = trns_sl_no;
	}

	public Integer getTun_id() {
		return tun_id;
	}

	public void setTun_id(Integer tun_id) {
		this.tun_id = tun_id;
	}

	public Float getTun_loss() {
		return tun_loss;
	}

	public void setTun_loss(Float tun_loss) {
		this.tun_loss = tun_loss;
	}

	public Float getTun_temp1() {
		return tun_temp1;
	}

	public void setTun_temp1(Float tun_temp1) {
		this.tun_temp1 = tun_temp1;
	}

	public Float getTun_temp2() {
		return tun_temp2;
	}

	public void setTun_temp2(Float tun_temp2) {
		this.tun_temp2 = tun_temp2;
	}

	public Float getTun_temp3() {
		return tun_temp3;
	}

	public void setTun_temp3(Float tun_temp3) {
		this.tun_temp3 = tun_temp3;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getLiq_temp() {
		return liq_temp;
	}

	public void setLiq_temp(Integer liq_temp) {
		this.liq_temp = liq_temp;
	}

	public Integer getPrev_tund_id() {
		return prev_tund_id;
	}

	public void setPrev_tund_id(Integer prev_tund_id) {
		this.prev_tund_id = prev_tund_id;
	}
}