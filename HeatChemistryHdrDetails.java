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

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.SubUnitMasterModel;

@Entity
@Table(name="TRNS_HEAT_CHEMISTRY_HDR")
@TableGenerator(name = "HEAT_CHEM_HDR_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatChemHdrSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class HeatChemistryHdrDetails implements Serializable{
	
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_CHEM_HDR_SEQ_GEN")
@Column(name="SAMPLE_SI_NO")
private Integer sample_si_no;

@Column(name="SAMPLE_NO")
private String sample_no;

@Temporal(TemporalType.TIMESTAMP)
@Column(name="SAMPLE_DATE_TIME")
private Date sample_date_time;

@Column(name="SAMPLE_TEMP")
private Double sample_temp;

@Column(name="SUB_UNIT_ID")
private Integer sub_unit_id;

@Column(name="SAMPLE_RESULT")
private Integer sample_result;

@Column(name="ANALYSIS_TYPE")
private Integer analysis_type;

@Column(name="FINAL_RESULT")
private Integer final_result;

@Column(name="REMARKS")
private String remarks;

@Column(name="CREATED_BY",updatable=false)
private Integer createdBy;

@Temporal(TemporalType.TIMESTAMP)
@Column(name="CREATED_DATE_TIME",updatable=false)
private Date createdDateTime;	

@Column(name="UPDATED_BY")
private Integer updatedBy;

@Column(name="RECORD_STATUS")
private Integer record_status;

@Temporal(TemporalType.TIMESTAMP)
@Column(name="UPDATED_DATE_TIME")
private Date updatedDateTime;

@Column(name="HEAT_ID")
private String heat_id;

@Column(name="APPROVE")
private Integer approve;

@Column(name="APPROVED_BY")
private String approved_by;


public String getApproved_by() {
	return approved_by;
}

public void setApproved_by(String approved_by) {
	this.approved_by = approved_by;
}

public Integer getApprove() {
	return approve;
}

public void setApprove(Integer approve) {
	this.approve = approve;
}

@Column(name="HEAT_COUNTER")
private Integer heat_counter;

@Version
@Column(name="RECORD_VERSION")
private Integer version;

@Transient
private String grid_arry;

@Transient
private String analysisType, prev_unit;

@ManyToOne(optional=true)
@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
private SubUnitMasterModel subUnitMasterDtls;

@ManyToOne(optional=true)
@JoinColumn(name="ANALYSIS_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
private LookupMasterModel analysisLookupDtls;

@ManyToOne(optional=true)
@JoinColumn(name="SAMPLE_RESULT" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
private LookupMasterModel sampleResultLookupDtls;

/*@ManyToOne(optional=true)
@JoinColumn(name="HEAT_ID" ,referencedColumnName="HEAT_ID",insertable=false,updatable=false)
private EofHeatDetails eofHeatIdDtls;

@ManyToOne(optional=true)
@JoinColumn(name="HEAT_COUNTER" ,referencedColumnName="HEAT_COUNTER",insertable=false,updatable=false)
private EofHeatDetails eofHeatCounterDtls;*/

public Integer getSample_result() {
	return sample_result;
}

public void setSample_result(Integer sample_result) {
	this.sample_result = sample_result;
}

public LookupMasterModel getSampleResultLookupDtls() {
	return sampleResultLookupDtls;
}

public void setSampleResultLookupDtls(LookupMasterModel sampleResultLookupDtls) {
	this.sampleResultLookupDtls = sampleResultLookupDtls;
}

public Integer getSample_si_no() {
	return sample_si_no;
}

public void setSample_si_no(Integer sample_si_no) {
	this.sample_si_no = sample_si_no;
}

public String getSample_no() {
	return sample_no;
}

public void setSample_no(String sample_no) {
	this.sample_no = sample_no;
}

public Date getSample_date_time() {
	return sample_date_time;
}

public void setSample_date_time(Date sample_date_time) {
	this.sample_date_time = sample_date_time;
}

public Double getSample_temp() {
	return sample_temp;
}

public void setSample_temp(Double sample_temp) {
	this.sample_temp = sample_temp;
}

public Integer getSub_unit_id() {
	return sub_unit_id;
}

public void setSub_unit_id(Integer sub_unit_id) {
	this.sub_unit_id = sub_unit_id;
}


public Integer getAnalysis_type() {
	return analysis_type;
}

public void setAnalysis_type(Integer analysis_type) {
	this.analysis_type = analysis_type;
}

public Integer getFinal_result() {
	return final_result;
}

public void setFinal_result(Integer final_result) {
	this.final_result = final_result;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
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


public SubUnitMasterModel getSubUnitMasterDtls() {
	return subUnitMasterDtls;
}

public void setSubUnitMasterDtls(SubUnitMasterModel subUnitMasterDtls) {
	this.subUnitMasterDtls = subUnitMasterDtls;
}

public LookupMasterModel getAnalysisLookupDtls() {
	return analysisLookupDtls;
}

public void setAnalysisLookupDtls(LookupMasterModel analysisLookupDtls) {
	this.analysisLookupDtls = analysisLookupDtls;
}

public Integer getVersion() {
	return version;
}

public void setVersion(Integer version) {
	this.version = version;
}

public String getGrid_arry() {
	return grid_arry;
}

public void setGrid_arry(String grid_arry) {
	this.grid_arry = grid_arry;
}

public Integer getRecord_status() {
	return record_status;
}

public void setRecord_status(Integer record_status) {
	this.record_status = record_status;
}

/*public EofHeatDetails getEofHeatIdDtls() {
	return eofHeatIdDtls;
}

public void setEofHeatIdDtls(EofHeatDetails eofHeatIdDtls) {
	this.eofHeatIdDtls = eofHeatIdDtls;
}

public EofHeatDetails getEofHeatCounterDtls() {
	return eofHeatCounterDtls;
}

public void setEofHeatCounterDtls(EofHeatDetails eofHeatCounterDtls) {
	this.eofHeatCounterDtls = eofHeatCounterDtls;
}*/

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

public String getAnalysisType() {
	return analysisType;
}

public void setAnalysisType(String analysisType) {
	this.analysisType = analysisType;
}

public String getPrev_unit() {
	return prev_unit;
}

public void setPrev_unit(String prev_unit) {
	this.prev_unit = prev_unit;
}

}
