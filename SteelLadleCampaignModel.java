package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Table(name="TRNS_STEEL_LADLE_CAMPAIGN")
@TableGenerator(name = "STEEL_LADLE_CAMPAIGN_TRNS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ladleCampaignTrnsSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class SteelLadleCampaignModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STEEL_LADLE_CAMPAIGN_TRNS_SEQ_GEN")
	@Column(name="LC_SL_NO",unique=true, nullable=false)
	private Integer lc_sl_no;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steel_ladle_no;
	
	@Column(name="CAMPAIGN_NO")
	private Integer campaign_no;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAMPAIGN_START_DATE")
	private Date campaign_start_date;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CAMPAIGN_END_DATE")
	private Date campaign_end_date;
	
	@Column(name="CAMPAIGN_REMARKS")
	private String campaign_remarks;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
	}
	
	public Integer getLc_sl_no() {
		return lc_sl_no;
	}

	public void setLc_sl_no(Integer lc_sl_no) {
		this.lc_sl_no = lc_sl_no;
	}

	public Integer getSteel_ladle_no() {
		return steel_ladle_no;
	}

	public void setSteel_ladle_no(Integer steel_ladle_no) {
		this.steel_ladle_no = steel_ladle_no;
	}

	public Integer getCampaign_no() {
		return campaign_no;
	}

	public void setCampaign_no(Integer campaign_no) {
		this.campaign_no = campaign_no;
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

	public String getCampaign_remarks() {
		return campaign_remarks;
	}

	public void setCampaign_remarks(String campaign_remarks) {
		this.campaign_remarks = campaign_remarks;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
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

	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	
	
	
	
	
	
}
