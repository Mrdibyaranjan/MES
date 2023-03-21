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
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.SteelLadleMasterModel;


@Entity
@Table(name="TRNS_STLADLE_HEATING_INFO")
@TableGenerator(name = "STLADLE_HEATING_INFO_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "StLadleHeatingInfoSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)

public class StLdlHeatingDtls implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STLADLE_HEATING_INFO_SEQ_GEN")
	@Column(name="STLADLE_HEAT_LIFE_HIST_ID",unique=true, nullable=false)
	private Integer stladle_heat_life_hist_id;

	@Column(name="HIST_ENTRY_TIME")
	private Date hist_entry_time;
	
	@Column(name="STEEL_LADLE_SI_NO")
	private Integer steel_ladle_si_no;
	
	@Column(name="HEAT_ID")
	private String heat_id;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="HEATING_START_DT")
	private Date heating_start_dt;
	
	@Column(name="HEATING_END_DT")
	private Date heating_end_dt;
	
	@Column(name="BURNER_NO")
	private Integer burner_no;
	
	@Column(name="STLADLE_STATUS")
	private Integer stladle_status;
	
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
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_NO" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel stlLdlMstrModel;
	
	public Integer getStladle_heat_life_hist_id() {
		return stladle_heat_life_hist_id;
	}

	public void setStladle_heat_life_hist_id(Integer stladle_heat_life_hist_id) {
		this.stladle_heat_life_hist_id = stladle_heat_life_hist_id;
	}

	public Date getHist_entry_time() {
		return hist_entry_time;
	}

	public void setHist_entry_time(Date hist_entry_time) {
		this.hist_entry_time = hist_entry_time;
	}

	public Integer getSteel_ladle_si_no() {
		return steel_ladle_si_no;
	}

	public void setSteel_ladle_si_no(Integer steel_ladle_si_no) {
		this.steel_ladle_si_no = steel_ladle_si_no;
	}

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

	public Date getHeating_start_dt() {
		return heating_start_dt;
	}

	public void setHeating_start_dt(Date heating_start_dt) {
		this.heating_start_dt = heating_start_dt;
	}

	public Date getHeating_end_dt() {
		return heating_end_dt;
	}

	public void setHeating_end_dt(Date heating_end_dt) {
		this.heating_end_dt = heating_end_dt;
	}

	public Integer getBurner_no() {
		return burner_no;
	}

	public void setBurner_no(Integer burner_no) {
		this.burner_no = burner_no;
	}

	public Integer getStladle_status() {
		return stladle_status;
	}

	public void setStladle_status(Integer stladle_status) {
		this.stladle_status = stladle_status;
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

	public SteelLadleMasterModel getStlLdlMstrModel() {
		return stlLdlMstrModel;
	}

	public void setStlLdlMstrModel(SteelLadleMasterModel stlLdlMstrModel) {
		this.stlLdlMstrModel = stlLdlMstrModel;
	}
}
