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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.model.SteelLadleStatusMasterModel;
@Entity
@Table(name="TRNS_STLADLE_STATUS_TRACKING")
@TableGenerator(name = "TRNS_ST_LADLE_TRACK_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "StLadleTrackSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class SteelLadleTrackingModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_ST_LADLE_TRACK_SEQ_GEN")
	@Column(name="TRNS_STLADLE_TRACK_ID")
	private Integer st_ladle_track_id;
	
	@Column(name="STEEL_LADLE_SI_NO")
	private Integer st_ladle_si_no;

	@Column(name="STATUS")
	private Integer ladle_status;
	
	@Column(name="STEEL_LADLE_LIFE")
	private Integer st_ladle_life;
	
	@Column(name="RECORD_STATUS")
	private Integer recordStatus;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date createdDateTime;	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_SI_NO" ,referencedColumnName="STEEL_LADLE_SI_NO" ,insertable=false,updatable=false)
	private SteelLadleMasterModel steelLadleObj;

	public SteelLadleStatusMasterModel getLadleStatusObj() {
		return ladleStatusObj;
	}

	public void setLadleStatusObj(SteelLadleStatusMasterModel ladleStatusObj) {
		this.ladleStatusObj = ladleStatusObj;
	}

	@ManyToOne(optional=true)
	@JoinColumn(name="STATUS" ,referencedColumnName="STATUS_ID" ,insertable=false,updatable=false)
	private SteelLadleStatusMasterModel ladleStatusObj;
	
	public SteelLadleMasterModel getSteelLadleObj() {
		return steelLadleObj;
	}

	public void setSteelLadleObj(SteelLadleMasterModel steelLadleObj) {
		this.steelLadleObj = steelLadleObj;
	}

	public Integer getSt_ladle_track_id() {
		return st_ladle_track_id;
	}

	public void setSt_ladle_track_id(Integer st_ladle_track_id) {
		this.st_ladle_track_id = st_ladle_track_id;
	}

	public Integer getSt_ladle_si_no() {
		return st_ladle_si_no;
	}

	public void setSt_ladle_si_no(Integer st_ladle_si_no) {
		this.st_ladle_si_no = st_ladle_si_no;
	}

	public Integer getLadle_status() {
		return ladle_status;
	}

	public void setLadle_status(Integer ladle_status) {
		this.ladle_status = ladle_status;
	}

	public Integer getSt_ladle_life() {
		return st_ladle_life;
	}

	public void setSt_ladle_life(Integer st_ladle_life) {
		this.st_ladle_life = st_ladle_life;
	}

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecord_status(Integer recordStatus) {
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updatedDateTime;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
}
