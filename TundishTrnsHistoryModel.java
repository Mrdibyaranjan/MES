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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.TundishMasterModel;

@Entity
@Table(name="TRNS_TUNDISH_DET")
@TableGenerator(name = "TRNS_TUNDISH_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "TundishTrnsHistorySeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class TundishTrnsHistoryModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_TUNDISH_SEQ_GEN")
	@Column(name="TRNS_ID",unique=true, nullable=false)
	private Integer trns_id;
	
	@Column(name="TUNDISH_ID")
	private Integer tundish_id;
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)//pattern = "dd/MM/yyyy HH:mm:ss"
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRNS_DATE")
	private Date trns_date;
	
	@Column(name="SAFETY_LINING_MAKE")
	private Integer safty_lining_make;
	
	@Column(name="WORKING_LINING_MAKE")
	private Integer working_lining_make;
	
	@Column(name="SPRAY_MASS")
	private Integer spray_mass;
	
	@Column(name="SEN_TYPE")
	private Integer sen_type;
	
	@Column(name="SEN_DIA")
	private Integer sen_dia;
	
	@Column(name="SEN_MAKE")
	private Integer sen_make;
	
	@Column(name="MBS_MAKE")
	private Integer mbs_make;
	
	@Column(name="PATCHING_QTY")
	private Double patching_qty;
	
	@Column(name="REMRKS")
	private String remarks;
	
	@Column(name="TUNDISH_STATUS")
	private Integer tundish_status;
	
	@Column(name="TUNDISH_LIFE")
	private Integer tundish_life;
	
	@Column(name="RECORD_STATUS")
	private Integer recordStatus;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date createdDateTime;

	@Column(name="UPDATED_BY",insertable=false)
	private Integer updated_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE",insertable=false)
	private Date updated_date_time;

	public Integer getTrns_id() {
		return trns_id;
	}

	public void setTrns_id(Integer trns_id) {
		this.trns_id = trns_id;
	}

	public Integer getTundish_id() {
		return tundish_id;
	}

	public void setTundish_id(Integer tundish_id) {
		this.tundish_id = tundish_id;
	}

	public Date getTrns_date() {
		return trns_date;
	}

	public void setTrns_date(Date trns_date) {
		this.trns_date = trns_date;
	}

	public Integer getSafty_lining_make() {
		return safty_lining_make;
	}

	public void setSafty_lining_make(Integer safty_lining_make) {
		this.safty_lining_make = safty_lining_make;
	}

	public Integer getWorking_lining_make() {
		return working_lining_make;
	}

	public void setWorking_lining_make(Integer working_lining_make) {
		this.working_lining_make = working_lining_make;
	}

	public Integer getSpray_mass() {
		return spray_mass;
	}

	public void setSpray_mass(Integer spray_mass) {
		this.spray_mass = spray_mass;
	}

	public Integer getSen_type() {
		return sen_type;
	}

	public void setSen_type(Integer sen_type) {
		this.sen_type = sen_type;
	}

	public Integer getSen_dia() {
		return sen_dia;
	}

	public void setSen_dia(Integer sen_dia) {
		this.sen_dia = sen_dia;
	}

	public Integer getSen_make() {
		return sen_make;
	}

	public void setSen_make(Integer sen_make) {
		this.sen_make = sen_make;
	}

	public Integer getMbs_make() {
		return mbs_make;
	}

	public void setMbs_make(Integer mbs_make) {
		this.mbs_make = mbs_make;
	}

	public Double getPatching_qty() {
		return patching_qty;
	}

	public void setPatching_qty(Double patching_qty) {
		this.patching_qty = patching_qty;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
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
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TUNDISH_ID" ,referencedColumnName="TUNDISH_SL_NO",insertable=false,updatable=false)
	private TundishMasterModel tundishMstrMdl;

	public TundishMasterModel getTundishMstrMdl() {
		return tundishMstrMdl;
	}

	public void setTundishMstrMdl(TundishMasterModel tundishMstrMdl) {
		this.tundishMstrMdl = tundishMstrMdl;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TUNDISH_STATUS" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel tundishStautsMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SPRAY_MASS" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel sprayMassMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SAFETY_LINING_MAKE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel saftyLinkingMakeMdl;
	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="WORKING_LINING_MAKE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel workingLiningMakeMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SEN_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel senTypeMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SEN_MAKE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel senMakeMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SEN_DIA" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel senDiaMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="MBS_MAKE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel mbsMakeMdl;
	
	
	public Integer getTundish_status() {
		return tundish_status;
	}

	public void setTundish_status(Integer tundish_status) {
		this.tundish_status = tundish_status;
	}

	public LookupMasterModel getTundishStautsMdl() {
		return tundishStautsMdl;
	}

	public void setTundishStautsMdl(LookupMasterModel tundishStautsMdl) {
		this.tundishStautsMdl = tundishStautsMdl;
	}

	public Integer getTundish_life() {
		return tundish_life;
	}

	public void setTundish_life(Integer tundish_life) {
		this.tundish_life = tundish_life;
	}

	public LookupMasterModel getSprayMassMdl() {
		return sprayMassMdl;
	}

	public void setSprayMassMdl(LookupMasterModel sprayMassMdl) {
		this.sprayMassMdl = sprayMassMdl;
	}

	public LookupMasterModel getSaftyLinkingMakeMdl() {
		return saftyLinkingMakeMdl;
	}

	public void setSaftyLinkingMakeMdl(LookupMasterModel saftyLinkingMakeMdl) {
		this.saftyLinkingMakeMdl = saftyLinkingMakeMdl;
	}

	public LookupMasterModel getWorkingLiningMakeMdl() {
		return workingLiningMakeMdl;
	}

	public void setWorkingLiningMakeMdl(LookupMasterModel workingLiningMakeMdl) {
		this.workingLiningMakeMdl = workingLiningMakeMdl;
	}

	public LookupMasterModel getSenTypeMdl() {
		return senTypeMdl;
	}

	public void setSenTypeMdl(LookupMasterModel senTypeMdl) {
		this.senTypeMdl = senTypeMdl;
	}

	public LookupMasterModel getSenMakeMdl() {
		return senMakeMdl;
	}

	public void setSenMakeMdl(LookupMasterModel senMakeMdl) {
		this.senMakeMdl = senMakeMdl;
	}

	public LookupMasterModel getSenDiaMdl() {
		return senDiaMdl;
	}

	public void setSenDiaMdl(LookupMasterModel senDiaMdl) {
		this.senDiaMdl = senDiaMdl;
	}

	public LookupMasterModel getMbsMakeMdl() {
		return mbsMakeMdl;
	}

	public void setMbsMakeMdl(LookupMasterModel mbsMakeMdl) {
		this.mbsMakeMdl = mbsMakeMdl;
	}
	
	
	
	
	
	
	
}
