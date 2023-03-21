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

@Entity
@Table(name="TRNS_HEAT_CHEMISTRY_CHILD")
@TableGenerator(name = "HEAT_CHEM_CHLD_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatChemChldSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class HeatChemistryChildDetails implements Serializable{
	
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_CHEM_CHLD_SEQ_GEN")
@Column(name="DTLS_SI_NO")
private Integer dtls_si_no;

@Column(name="SAMPLE_SI_NO")
private Integer sample_si_no;

@Column(name="ELEMENT")
private Integer element;

@Column(name="AIM_VALUE")
private Double aim_value;

@Column(name="MIN_VALUE")
private Double min_value;

@Column(name="MAX_VALUE")
private Double max_value;

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

@Version
@Column(name="RECORD_VERSION")
private Integer version;

@ManyToOne(optional=true)
@JoinColumn(name="ELEMENT" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
private LookupMasterModel elementMstrLookupDtls;

@ManyToOne(optional=true)
@JoinColumn(name="SAMPLE_SI_NO" ,referencedColumnName="SAMPLE_SI_NO",insertable=false,updatable=false)
private HeatChemistryHdrDetails heatChemistryHdrDtls;


@Transient
private String elementName;

@Transient
private String uom;

@Transient
private Double psn_aim_value;

@Transient
private String remarks;

@Transient
private Integer spectro_flag;

@Transient
private String sample_date;


public Integer getDtls_si_no() {
	return dtls_si_no;
}

public void setDtls_si_no(Integer dtls_si_no) {
	this.dtls_si_no = dtls_si_no;
}

public Integer getSample_si_no() {
	return sample_si_no;
}

public void setSample_si_no(Integer sample_si_no) {
	this.sample_si_no = sample_si_no;
}

public Integer getElement() {
	return element;
}

public void setElement(Integer element) {
	this.element = element;
}

public Double getAim_value() {
	return aim_value;
}

public void setAim_value(Double aim_value) {
	this.aim_value = aim_value;
}

public Double getMin_value() {
	return min_value;
}

public void setMin_value(Double min_value) {
	this.min_value = min_value;
}

public Double getMax_value() {
	return max_value;
}

public void setMax_value(Double max_value) {
	this.max_value = max_value;
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

public LookupMasterModel getElementMstrLookupDtls() {
	return elementMstrLookupDtls;
}

public void setElementMstrLookupDtls(LookupMasterModel elementMstrLookupDtls) {
	this.elementMstrLookupDtls = elementMstrLookupDtls;
}

public HeatChemistryHdrDetails getHeatChemistryHdrDtls() {
	return heatChemistryHdrDtls;
}

public void setHeatChemistryHdrDtls(HeatChemistryHdrDetails heatChemistryHdrDtls) {
	this.heatChemistryHdrDtls = heatChemistryHdrDtls;
}

public String getElementName() {
	return elementName;
}

public void setElementName(String elementName) {
	this.elementName = elementName;
}

public String getUom() {
	return uom;
}

public void setUom(String uom) {
	this.uom = uom;
}

public Integer getVersion() {
	return version;
}

public void setVersion(Integer version) {
	this.version = version;
}

public Double getPsn_aim_value() {
	return psn_aim_value;
}

public void setPsn_aim_value(Double psn_aim_value) {
	this.psn_aim_value = psn_aim_value;
}

public String getRemarks() {
	return remarks;
}

public void setRemarks(String remarks) {
	this.remarks = remarks;
}

public Integer getSpectro_flag() {
	return spectro_flag;
}

public void setSpectro_flag(Integer spectro_flag) {
	this.spectro_flag = spectro_flag;
}

public String getSample_date() {
	return sample_date;
}

public void setSample_date(String sample_date) {
	this.sample_date = sample_date;
}





}
