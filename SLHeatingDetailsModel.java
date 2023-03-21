package com.smes.trans.model;

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

@Entity
@Table(name="SL_HEATING_DTLS")
@TableGenerator(name = "SL_HEATING_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "SLHeatingSeqId", allocationSize = 1)
public class SLHeatingDetailsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SL_HEATING_SEQ_GEN")
	@Column(name="HEATING_SI_NO")
	private Integer heating_si_no;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HEATING_START_TIME")
	private Date heating_start_time;
	
	@Column(name="HEATING_BURNER_NO")
	private Integer heating_burner_no;
	
	@Column(name="HEATING_START_LADLE_SM")
	private Integer heating_start_ladle_sm;
	
	@Column(name="HEATING_START_LADLE_MAN")
	private Integer heating_start_ladle_man;
	
	@Column(name="HEATING_START_LADLE_ASST")
	private Integer heating_start_ladle_asst;
	
			
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HEATING_END_TIME")
	private Date heating_end_time;
	
	@Column(name="HEATING_END_LADLE_SM")
	private Integer heating_end_ladle_sm;
	
	@Column(name="HEATING_END_LADLE_MAN")
	private Integer heating_end_ladle_man;
	
	@Column(name="HEATING_END_LADLE_ASST")
	private Integer heating_end_ladle_asst;
	
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
	
	@Column(name="TRNS_STLADLE_TRACK_ID")
	private Integer trns_stladle_track_id;
	
	@Column(name="TRNS_STLADLE_LIFE")
	private Integer trns_stladle_life;
	
	@Column(name="HEATING_STATUS")
	private Integer heating_status;
	
	@Column(name="HEATING_REMARKS")
	private String heating_remarks;
	

	public Integer getHeating_si_no() {
		return heating_si_no;
	}

	public void setHeating_si_no(Integer heating_si_no) {
		this.heating_si_no = heating_si_no;
	}

	public Date getHeating_start_time() {
		return heating_start_time;
	}

	public void setHeating_start_time(Date heating_start_time) {
		this.heating_start_time = heating_start_time;
	}

	public Integer getHeating_burner_no() {
		return heating_burner_no;
	}

	public void setHeating_burner_no(Integer heating_burner_no) {
		this.heating_burner_no = heating_burner_no;
	}

	public Integer getHeating_start_ladle_sm() {
		return heating_start_ladle_sm;
	}

	public void setHeating_start_ladle_sm(Integer heating_start_ladle_sm) {
		this.heating_start_ladle_sm = heating_start_ladle_sm;
	}

	public Integer getHeating_start_ladle_man() {
		return heating_start_ladle_man;
	}

	public void setHeating_start_ladle_man(Integer heating_start_ladle_man) {
		this.heating_start_ladle_man = heating_start_ladle_man;
	}

	public Integer getHeating_start_ladle_asst() {
		return heating_start_ladle_asst;
	}

	public void setHeating_start_ladle_asst(Integer heating_start_ladle_asst) {
		this.heating_start_ladle_asst = heating_start_ladle_asst;
	}

	public Date getHeating_end_time() {
		return heating_end_time;
	}

	public void setHeating_end_time(Date heating_end_time) {
		this.heating_end_time = heating_end_time;
	}

	public Integer getHeating_end_ladle_sm() {
		return heating_end_ladle_sm;
	}

	public void setHeating_end_ladle_sm(Integer heating_end_ladle_sm) {
		this.heating_end_ladle_sm = heating_end_ladle_sm;
	}

	public Integer getHeating_end_ladle_man() {
		return heating_end_ladle_man;
	}

	public void setHeating_end_ladle_man(Integer heating_end_ladle_man) {
		this.heating_end_ladle_man = heating_end_ladle_man;
	}

	public Integer getHeating_end_ladle_asst() {
		return heating_end_ladle_asst;
	}

	public void setHeating_end_ladle_asst(Integer heating_end_ladle_asst) {
		this.heating_end_ladle_asst = heating_end_ladle_asst;
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

	public Integer getTrns_stladle_track_id() {
		return trns_stladle_track_id;
	}

	public void setTrns_stladle_track_id(Integer trns_stladle_track_id) {
		this.trns_stladle_track_id = trns_stladle_track_id;
	}

	public Integer getTrns_stladle_life() {
		return trns_stladle_life;
	}

	public void setTrns_stladle_life(Integer trns_stladle_life) {
		this.trns_stladle_life = trns_stladle_life;
	}

	public Integer getHeating_status() {
		return heating_status;
	}

	public void setHeating_status(Integer heating_status) {
		this.heating_status = heating_status;
	}

	public String getHeating_remarks() {
		return heating_remarks;
	}

	public void setHeating_remarks(String heating_remarks) {
		this.heating_remarks = heating_remarks;
	}

	@Override
	public String toString() {
		return "SLHeatingDetailsModel [heating_si_no=" + heating_si_no + ", heating_start_time=" + heating_start_time
				+ ", heating_burner_no=" + heating_burner_no + ", heating_start_ladle_sm=" + heating_start_ladle_sm
				+ ", heating_start_ladle_man=" + heating_start_ladle_man + ", heating_start_ladle_asst="
				+ heating_start_ladle_asst + ", heating_end_time=" + heating_end_time + ", heating_end_ladle_sm="
				+ heating_end_ladle_sm + ", heating_end_ladle_man=" + heating_end_ladle_man
				+ ", heating_end_ladle_asst=" + heating_end_ladle_asst + ", created_by=" + created_by
				+ ", created_date_time=" + created_date_time + ", updated_by=" + updated_by + ", updated_date_time="
				+ updated_date_time + ", trns_stladle_track_id=" + trns_stladle_track_id + ", trns_stladle_life="
				+ trns_stladle_life + ", heating_status=" + heating_status + ", heating_remarks=" + heating_remarks
				+ "]";
	}

	
	
	
	

}
