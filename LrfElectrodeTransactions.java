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

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.SubUnitMasterModel;

@Entity
@Table(name = "TRNS_LRF_ELECTRODES_USAGE")
@TableGenerator(name = "TRNS_LRF_ELECTRODES_USAGE_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "transLfrElectrodesSeqId", allocationSize = 1)
@DynamicUpdate(value = true)
public class LrfElectrodeTransactions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_LRF_ELECTRODES_USAGE_SEQ_GEN")
	@Column(name = "ELECTRODE_TRANS_ID")
	private Integer electrodeTransId;
	
	@Column(name="SUB_UNIT_ID")
	private Integer subUintId;
	
	@Column(name="ELECTRODE_ID")
	private Integer electrodeId;
	
	@Column(name="IS_ADDED")
	private String isAdded;
	
	@Column(name="IS_ADJUSTED")
	private String isAdjusted;
	
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

	
	@Column(name="RECORD_STATUS")
	private Integer recordStatus;
	
	@Column(name="LRF_HEAT_TRNS_ID")
	private Integer lrfHeatTransId;
	

	public Integer getElectrodeTransId() {
		return electrodeTransId;
	}

	public void setElectrodeTransId(Integer electrodeTransId) {
		this.electrodeTransId = electrodeTransId;
	}

	public Integer getSubUintId() {
		return subUintId;
	}

	public void setSubUintId(Integer subUintId) {
		this.subUintId = subUintId;
	}

	public Integer getElectrodeId() {
		return electrodeId;
	}

	public void setElectrodeId(Integer electrodeId) {
		this.electrodeId = electrodeId;
	}

	public String getIsAdded() {
		return isAdded;
	}

	public void setIsAdded(String isAdded) {
		this.isAdded = isAdded;
	}

	public String getIsAdjusted() {
		return isAdjusted;
	}

	public void setIsAdjusted(String isAdjusted) {
		this.isAdjusted = isAdjusted;
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

	public Integer getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(Integer recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstrModl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="ELECTRODE_ID" ,referencedColumnName="lookup_id",insertable=false,updatable=false)
	private LookupMasterModel electrodeLkpMstrModel;
	
//	@ManyToOne(optional=true)
//	@JoinColumn(name="LRF_HEAT_TRNS_ID" ,referencedColumnName="trns_sl_no",insertable=false,updatable=false)
//	private LRFHeatDetailsModel lrfHEatDetailsModel;

	public SubUnitMasterModel getSubUnitMstrModl() {
		return subUnitMstrModl;
	}

	public void setSubUnitMstrModl(SubUnitMasterModel subUnitMstrModl) {
		this.subUnitMstrModl = subUnitMstrModl;
	}

	public LookupMasterModel getElectrodeLkpMstrModel() {
		return electrodeLkpMstrModel;
	}

	public void setElectrodeLkpMstrModel(LookupMasterModel electrodeLkpMstrModel) {
		this.electrodeLkpMstrModel = electrodeLkpMstrModel;
	}

	public Integer getLrfHeatTransId() {
		return lrfHeatTransId;
	}

	public void setLrfHeatTransId(Integer lrfHeatTransId) {
		this.lrfHeatTransId = lrfHeatTransId;
	}



//	public LRFHeatDetailsModel getLrfHEatDetailsModel() {
//		return lrfHEatDetailsModel;
//	}
//
//	public void setLrfHEatDetailsModel(LRFHeatDetailsModel lrfHEatDetailsModel) {
//		this.lrfHEatDetailsModel = lrfHEatDetailsModel;
//	}
	
	
	
	
	


}
