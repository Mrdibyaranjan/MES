package com.smes.trans.model;

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

import com.smes.masters.model.SLMasterModel;

@Entity
@Table(name="TRNS_SL_TRACKING")
@TableGenerator(name = "SL_Status_MSTR_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "SLStatusMstrSeqId", allocationSize = 1)
public class SLStatusTrackingModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SL_Status_MSTR_SEQ_GEN")
	@Column(name="TRNS_STLADLE_TRACK_ID")
	private Integer trns_stladle_track_id;

	@Column(name="STEEL_LADLE_SI_NO")
	private Integer steel_ladle_si_no;
	
	@Column(name="STEEL_LADLE_LIFE")
	private Integer steel_ladle_life;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATED_BY")
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	  @ManyToOne(optional=true)
	  
	  @JoinColumn(name="STEEL_LADLE_SI_NO",referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	  private SLMasterModel slMasterObj;
	 

	public Integer getTrns_stladle_track_id() {
		return trns_stladle_track_id;
	}

	public void setTrns_stladle_track_id(Integer trns_stladle_track_id) {
		this.trns_stladle_track_id = trns_stladle_track_id;
	}

	public Integer getSteel_ladle_si_no() {
		return steel_ladle_si_no;
	}

	public void setSteel_ladle_si_no(Integer steel_ladle_si_no) {
		this.steel_ladle_si_no = steel_ladle_si_no;
	}

	public Integer getSteel_ladle_life() {
		return steel_ladle_life;
	}

	public void setSteel_ladle_life(Integer steel_ladle_life) {
		this.steel_ladle_life = steel_ladle_life;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	  public SLMasterModel getSlMasterObj() { return slMasterObj; }
	  
	  public void setSlMasterObj(SLMasterModel slMasterObj) { this.slMasterObj = slMasterObj; }

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

	@Override
	public String toString() {
		return "SLStatusTrackingModel [trns_stladle_track_id=" + trns_stladle_track_id + ", steel_ladle_si_no="
				+ steel_ladle_si_no + ", steel_ladle_life=" + steel_ladle_life + ", status=" + status + ", created_by="
				+ created_by + ", created_date_time=" + created_date_time + ", updated_by=" + updated_by
				+ ", updated_date_time=" + updated_date_time + ", record_status=" + record_status + ", slMasterObj="
				+ slMasterObj + "]";
	}
	  
	
	 
	
	
	
	
	
}
