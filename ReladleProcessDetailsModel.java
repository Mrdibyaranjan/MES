package com.smes.trans.model;

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
import com.smes.masters.model.SteelLadleMasterModel;

@Entity
@Table(name="TRNS_RELADLE_PROCESS_DTLS")
@TableGenerator(name = "TRNS_RELADLE_PROCESS_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "trnsReladleProcessDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class ReladleProcessDetailsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_RELADLE_PROCESS_DTLS_SEQ_GEN")
	@Column(name="RELADLE_PROCESS_DTLS_ID")
	private Integer reladleProcessDtlId;
	
	@Column(name="RELADLE_PROCESS_HDR_ID")
	private Integer reladleProcessHdrId;
	
	@Column(name="RELADLE_HEAT_DTLS_ID")
	private Integer reladleHeatDtlId;
	
	@Column(name="STEEL_LADLE_ID")
	private Integer steelLadleId;
	
	@Column(name="ACT_QTY")
	private Double actQty;
	
	@Column(name="POUR_QTY")
	private Double pourQty;
	
	@Column(name="BALANCE_QTY")
	private Double balanceQty;
	
	@Column(name="AIM_PSN")
	private Integer aimPsn;
	
	@Column(name="CREATED_BY" ,updatable=false)
	private Integer createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date createdDate;
	
	@Column(name="UPDATED_BY")
	private Integer updatedBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;

	@Transient
	private String heat_id;
	
	@Transient
	private Integer heat_counter;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="RELADLE_PROCESS_HDR_ID",nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	private ReladleProcessHdrModel reladleProcessHdrMdl;

	@ManyToOne(optional=true)
	@JoinColumn(name="RELADLE_HEAT_DTLS_ID" ,referencedColumnName="TRNS_SL_NO",insertable=false,updatable=false)
	private ReladleTrnsDetailsMdl reladleHeatDetailMdl;

	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_ID" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel steelLdlMstr;

	public Integer getReladleProcessDtlId() {
		return reladleProcessDtlId;
	}

	public void setReladleProcessDtlId(Integer reladleProcessDtlId) {
		this.reladleProcessDtlId = reladleProcessDtlId;
	}

	public Integer getReladleProcessHdrId() {
		return reladleProcessHdrId;
	}

	public void setReladleProcessHdrId(Integer reladleProcessHdrId) {
		this.reladleProcessHdrId = reladleProcessHdrId;
	}

	public Integer getReladleHeatDtlId() {
		return reladleHeatDtlId;
	}

	public void setReladleHeatDtlId(Integer reladleHeatDtlId) {
		this.reladleHeatDtlId = reladleHeatDtlId;
	}

	public Integer getSteelLadleId() {
		return steelLadleId;
	}

	public void setSteelLadleId(Integer steelLadleId) {
		this.steelLadleId = steelLadleId;
	}

	public Double getActQty() {
		return actQty;
	}

	public void setActQty(Double actQty) {
		this.actQty = actQty;
	}

	public Double getPourQty() {
		return pourQty;
	}

	public void setPourQty(Double pourQty) {
		this.pourQty = pourQty;
	}

	public Integer getAimPsn() {
		return aimPsn;
	}

	public void setAimPsn(Integer aimPsn) {
		this.aimPsn = aimPsn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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

	public ReladleProcessHdrModel getReladleProcessHdrMdl() {
		return reladleProcessHdrMdl;
	}

	public void setReladleProcessHdrMdl(ReladleProcessHdrModel reladleProcessHdrMdl) {
		this.reladleProcessHdrMdl = reladleProcessHdrMdl;
	}

	public ReladleTrnsDetailsMdl getReladleHeatDetailMdl() {
		return reladleHeatDetailMdl;
	}

	public void setReladleHeatDetailMdl(ReladleTrnsDetailsMdl reladleHeatDetailMdl) {
		this.reladleHeatDetailMdl = reladleHeatDetailMdl;
	}

	public SteelLadleMasterModel getSteelLdlMstr() {
		return steelLdlMstr;
	}

	public void setSteelLdlMstr(SteelLadleMasterModel steelLdlMstr) {
		this.steelLdlMstr = steelLdlMstr;
	}

	public Double getBalanceQty() {
		return balanceQty;
	}

	public void setBalanceQty(Double balanceQty) {
		this.balanceQty = balanceQty;
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
	
}