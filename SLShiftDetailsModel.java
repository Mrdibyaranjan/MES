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
@Table(name="SL_SHIFT_DTLS")
@TableGenerator(name = "SL_Shift_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "SLShiftDtlsSeqId", allocationSize = 1)
public class SLShiftDetailsModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SL_Shift_SEQ_GEN")
	@Column(name="TRNS_SHIFT_SI_NO")
	private Integer trns_shift_si_no;

	@Column(name="SHIFT_DATE_TIME")
	private Date shift_date_time;
	
	@Column(name="SHIFT_TYPE")
	private String shift_type;

	@Column(name="LADLE_SHIFT_MANAGER")
	private Integer ladle_shift_manager;
	
	@Column(name="SENIOR_SHIFT_INCHARGE")
	private Integer senior_shift_inchage;
	
	@Column(name="LADLE_MAN_SMS1")
	private Integer ladle_man_sms1;
	
	@Column(name="LADLE_MAN_SMS2")
	private Integer ladle_man_sms2;
	
	@Column(name="LADLE_MAN_ASST_SMS1")
	private Integer ladle_man_asst_sms1;
	
	@Column(name="LADLE_MAN_ASST_SMS2")
	private Integer ladle_man_asst_sms2;
	
	@Column(name="TEAMER_MAN_CCM1")
	private Integer teamer_man_ccm1;
	
	@Column(name="TEAMER_MAN_CCM2")
	private Integer teamer_man_ccm2;
	
	@Column(name="TEAMER_MAN_CCM3")
	private Integer teamer_man_ccm3;
	
	@Column(name="TEAMER_MAN_ASST_CCM1")
	private Integer teamer_man_asst_ccm1;
	
	@Column(name="TEAMER_MAN_ASST_CCM2")
	private Integer teamer_man_asst_ccm2;
	
	@Column(name="TEAMER_MAN_ASST_CCM3")
	private Integer teamer_man_asst_ccm3;
	
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
	
	@Column(name="PARTS_LADLE_MAN")
	private Integer parts_ladle_man;
	
	@Column(name="PARTS_LADLE_MAN_ASST")
	private Integer parts_ladle_man_asst;
	
	
	public Integer getTrns_shift_si_no() {
		return trns_shift_si_no;
	}

	public void setTrns_shift_si_no(Integer trns_shift_si_no) {
		this.trns_shift_si_no = trns_shift_si_no;
	}


	public Date getShift_date_time() {
		return shift_date_time;
	}

	public void setShift_date_time(Date shift_date_time) {
		this.shift_date_time = shift_date_time;
	}

	public String getShift_type() {
		return shift_type;
	}

	public void setShift_type(String shift_type) {
		this.shift_type = shift_type;
	}

	public Integer getLadle_shift_manager() {
		return ladle_shift_manager;
	}

	public void setLadle_shift_manager(Integer ladle_shift_manager) {
		this.ladle_shift_manager = ladle_shift_manager;
	}

	public Integer getSenior_shift_inchage() {
		return senior_shift_inchage;
	}

	public void setSenior_shift_inchage(Integer senior_shift_inchage) {
		this.senior_shift_inchage = senior_shift_inchage;
	}

	public Integer getLadle_man_sms1() {
		return ladle_man_sms1;
	}

	public void setLadle_man_sms1(Integer ladle_man_sms1) {
		this.ladle_man_sms1 = ladle_man_sms1;
	}

	public Integer getLadle_man_sms2() {
		return ladle_man_sms2;
	}

	public void setLadle_man_sms2(Integer ladle_man_sms2) {
		this.ladle_man_sms2 = ladle_man_sms2;
	}

	public Integer getLadle_man_asst_sms1() {
		return ladle_man_asst_sms1;
	}

	public void setLadle_man_asst_sms1(Integer ladle_man_asst_sms1) {
		this.ladle_man_asst_sms1 = ladle_man_asst_sms1;
	}

	public Integer getLadle_man_asst_sms2() {
		return ladle_man_asst_sms2;
	}

	public void setLadle_man_asst_sms2(Integer ladle_man_asst_sms2) {
		this.ladle_man_asst_sms2 = ladle_man_asst_sms2;
	}

	public Integer getTeamer_man_ccm1() {
		return teamer_man_ccm1;
	}

	public void setTeamer_man_ccm1(Integer teamer_man_ccm1) {
		this.teamer_man_ccm1 = teamer_man_ccm1;
	}

	public Integer getTeamer_man_ccm2() {
		return teamer_man_ccm2;
	}

	public void setTeamer_man_ccm2(Integer teamer_man_ccm2) {
		this.teamer_man_ccm2 = teamer_man_ccm2;
	}

	public Integer getTeamer_man_ccm3() {
		return teamer_man_ccm3;
	}

	public void setTeamer_man_ccm3(Integer teamer_man_ccm3) {
		this.teamer_man_ccm3 = teamer_man_ccm3;
	}

	public Integer getTeamer_man_asst_ccm1() {
		return teamer_man_asst_ccm1;
	}

	public void setTeamer_man_asst_ccm1(Integer teamer_man_asst_ccm1) {
		this.teamer_man_asst_ccm1 = teamer_man_asst_ccm1;
	}

	public Integer getTeamer_man_asst_ccm2() {
		return teamer_man_asst_ccm2;
	}

	public void setTeamer_man_asst_ccm2(Integer teamer_man_asst_ccm2) {
		this.teamer_man_asst_ccm2 = teamer_man_asst_ccm2;
	}

	public Integer getTeamer_man_asst_ccm3() {
		return teamer_man_asst_ccm3;
	}

	public void setTeamer_man_asst_ccm3(Integer teamer_man_asst_ccm3) {
		this.teamer_man_asst_ccm3 = teamer_man_asst_ccm3;
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

	public Integer getParts_ladle_man() {
		return parts_ladle_man;
	}

	public void setParts_ladle_man(Integer parts_ladle_man) {
		this.parts_ladle_man = parts_ladle_man;
	}

	public Integer getParts_ladle_man_asst() {
		return parts_ladle_man_asst;
	}

	public void setParts_ladle_man_asst(Integer parts_ladle_man_asst) {
		this.parts_ladle_man_asst = parts_ladle_man_asst;
	}

	@Override
	public String toString() {
		return "SLShiftDetailsModel [trns_shift_si_no=" + trns_shift_si_no + ", shift_date_time=" + shift_date_time
				+ ", shift_type=" + shift_type + ", ladle_shift_manager=" + ladle_shift_manager
				+ ", senior_shift_inchage=" + senior_shift_inchage + ", ladle_man_sms1=" + ladle_man_sms1
				+ ", ladle_man_sms2=" + ladle_man_sms2 + ", ladle_man_asst_sms1=" + ladle_man_asst_sms1
				+ ", ladle_man_asst_sms2=" + ladle_man_asst_sms2 + ", teamer_man_ccm1=" + teamer_man_ccm1
				+ ", teamer_man_ccm2=" + teamer_man_ccm2 + ", teamer_man_ccm3=" + teamer_man_ccm3
				+ ", teamer_man_asst_ccm1=" + teamer_man_asst_ccm1 + ", teamer_man_asst_ccm2=" + teamer_man_asst_ccm2
				+ ", teamer_man_asst_ccm3=" + teamer_man_asst_ccm3 + ", created_by=" + created_by
				+ ", created_date_time=" + created_date_time + ", updated_by=" + updated_by + ", updated_date_time="
				+ updated_date_time + ", trns_stladle_track_id=" + trns_stladle_track_id + ", trns_stladle_life="
				+ trns_stladle_life + ", parts_ladle_man=" + parts_ladle_man + ", parts_ladle_man_asst="
				+ parts_ladle_man_asst + "]";
	}


	



	
	
	
}
