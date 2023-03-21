package com.smes.trans.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;

@Entity
@Table(name="TRNS_RELADLE_PROCESS_HDR")
@TableGenerator(name = "TRNS_RELADLE_PROCESS_HDR_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "trnsReladleProcessHdrSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class ReladleProcessHdrModel {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_RELADLE_PROCESS_HDR_SEQ_GEN")
	@Column(name="RELADLE_PROCESS_ID")
	private Integer reladleProcessId;
	
	@Column(name="HEAT_ID")
	private String heatId;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="STEEL_LADLE_ID")
	private Integer steelLadleId;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PROCESS_DATE")
	private Date processDate;
	
	@Column(name="PROCESS_BY")
	private Integer processBy;
	
	@Column(name="PROCESS_TYPE")
	private String processType;
	
	@Column(name="HEAT_QTY")
	private Double heatQty;
	
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
	private Integer reladleHeatDtlId;
	
	@Transient
	private Double actualQty;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="AIM_PSN",referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrModel;

	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_ID" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel steelLdlMstr;
	
	@OneToMany(mappedBy="reladleProcessHdrMdl",fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<ReladleProcessDetailsModel> reladleProcessDtls;

	public Integer getReladleProcessId() {
		return reladleProcessId;
	}

	public void setReladleProcessId(Integer reladleProcessId) {
		this.reladleProcessId = reladleProcessId;
	}

	public String getHeatId() {
		return heatId;
	}

	public void setHeatId(String heatId) {
		this.heatId = heatId;
	}

	public Integer getSteelLadleId() {
		return steelLadleId;
	}

	public void setSteelLadleId(Integer steelLadleId) {
		this.steelLadleId = steelLadleId;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Integer getProcessBy() {
		return processBy;
	}

	public void setProcessBy(Integer processBy) {
		this.processBy = processBy;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public Double getHeatQty() {
		return heatQty;
	}

	public void setHeatQty(Double heatQty) {
		this.heatQty = heatQty;
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

	public PSNHdrMasterModel getPsnHdrModel() {
		return psnHdrModel;
	}

	public void setPsnHdrModel(PSNHdrMasterModel psnHdrModel) {
		this.psnHdrModel = psnHdrModel;
	}

	public SteelLadleMasterModel getSteelLdlMstr() {
		return steelLdlMstr;
	}

	public void setSteelLdlMstr(SteelLadleMasterModel steelLdlMstr) {
		this.steelLdlMstr = steelLdlMstr;
	}

	public Set<ReladleProcessDetailsModel> getReladleProcessDtls() {
		return reladleProcessDtls;
	}

	public void setReladleProcessDtls(
			Set<ReladleProcessDetailsModel> reladleProcessDtls) {
		this.reladleProcessDtls = reladleProcessDtls;
	}

	public Integer getReladleHeatDtlId() {
		return reladleHeatDtlId;
	}

	public void setReladleHeatDtlId(Integer reladleHeatDtlId) {
		this.reladleHeatDtlId = reladleHeatDtlId;
	}

	public Double getActualQty() {
		return actualQty;
	}

	public void setActualQty(Double actualQty) {
		this.actualQty = actualQty;
	}

	public Integer getHeat_counter() {
		return heat_counter;
	}

	public void setHeat_counter(Integer heat_counter) {
		this.heat_counter = heat_counter;
	}
	
		
}
