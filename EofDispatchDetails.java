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
@Table(name="TRNS_FURNACE_CAMPAIGN_LINES")
@TableGenerator(name = "EOF_DISPATCH_CAMPAIGN_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "EofDispatchSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class EofDispatchDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "EOF_DISPATCH_CAMPAIGN_SEQ_GEN")
	@Column(name="CAMPAIGN_ID")
	private Integer campaign_id;
	
	@Column(name="SUB_UNIT_ID",nullable=false)
	private Integer sub_unit_id;
	
	@Column(name="CAMPAIGN_NUMBER",nullable=false)
	private Integer campaign_number;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAMPAIGN_START_DATE")
	private Date campaign_start_date;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAMPAIGN_END_DATE")
	private Date campaign_end_date;
	
	@Column(name="START_HEAT_ID",nullable=false)
	private String start_heat_id;
	
	@Column(name="END_HEAT_ID")
	private String end_heat_id;
	
	@Column(name="TOTAL_HEATS",nullable=false)
	private Integer total_heats;
	
	@Column(name="CAMPAIGN_REMARKS")
	private String campaign_remarks;
	
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
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstrMdl;
	
	@Transient
	private String sub_unit_name,campaign_status,lifeParameterName;
	
	@Transient
	private Integer eof_trns_sno;
	
	@Column(name="life_parameter",nullable=false)
	private Integer lifeParameter;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="LIFE_PARAMETER" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lifeParameterLookupDtls;
	
	@Column(name="CAMPAIGN_LIFE_STATUS")
	private Integer campaign_life_status;
	
	

	public String getCampaign_status() {
		return campaign_status;
	}

	public void setCampaign_status(String campaign_status) {
		this.campaign_status = campaign_status;
	}

	public Integer getEof_trns_sno() {
		return eof_trns_sno;
	}

	public void setEof_trns_sno(Integer eof_trns_sno) {
		this.eof_trns_sno = eof_trns_sno;
	}

	public Integer getCampaign_id() {
		return campaign_id;
	}

	public void setCampaign_id(Integer campaign_id) {
		this.campaign_id = campaign_id;
	}

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public Integer getCampaign_number() {
		return campaign_number;
	}

	public void setCampaign_number(Integer campaign_number) {
		this.campaign_number = campaign_number;
	}

	public Date getCampaign_start_date() {
		return campaign_start_date;
	}

	public void setCampaign_start_date(Date campaign_start_date) {
		this.campaign_start_date = campaign_start_date;
	}

	public Date getCampaign_end_date() {
		return campaign_end_date;
	}

	public void setCampaign_end_date(Date campaign_end_date) {
		this.campaign_end_date = campaign_end_date;
	}

	public String getStart_heat_id() {
		return start_heat_id;
	}

	public void setStart_heat_id(String start_heat_id) {
		this.start_heat_id = start_heat_id;
	}

	public String getEnd_heat_id() {
		return end_heat_id;
	}

	public void setEnd_heat_id(String end_heat_id) {
		this.end_heat_id = end_heat_id;
	}

	public Integer getTotal_heats() {
		return total_heats;
	}

	public void setTotal_heats(Integer total_heats) {
		this.total_heats = total_heats;
	}

	public String getCampaign_remarks() {
		return campaign_remarks;
	}

	public void setCampaign_remarks(String campaign_remarks) {
		this.campaign_remarks = campaign_remarks;
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

	public SubUnitMasterModel getSubUnitMstrMdl() {
		return subUnitMstrMdl;
	}

	public void setSubUnitMstrMdl(SubUnitMasterModel subUnitMstrMdl) {
		this.subUnitMstrMdl = subUnitMstrMdl;
	}

	public String getSub_unit_name() {
		return sub_unit_name;
	}

	public void setSub_unit_name(String sub_unit_name) {
		this.sub_unit_name = sub_unit_name;
	}

	

	public Integer getCampaign_life_status() {
		return campaign_life_status;
	}

	public void setCampaign_life_status(Integer campaign_life_status) {
		this.campaign_life_status = campaign_life_status;
	}

	public Integer getLifeParameter() {
		return lifeParameter;
	}

	public void setLifeParameter(Integer lifeParameter) {
		this.lifeParameter = lifeParameter;
	}

	public LookupMasterModel getLifeParameterLookupDtls() {
		return lifeParameterLookupDtls;
	}

	public void setLifeParameterLookupDtls(LookupMasterModel lifeParameterLookupDtls) {
		this.lifeParameterLookupDtls = lifeParameterLookupDtls;
	}

	public String getLifeParameterName() {
		return lifeParameterName;
	}

	public void setLifeParameterName(String lifeParameterName) {
		this.lifeParameterName = lifeParameterName;
	}
	
	

}
