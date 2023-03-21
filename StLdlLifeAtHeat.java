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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.SteelLadleMasterModel;

@Entity
@Table(name="TRNS_STLADLE_HEAT_LIFE_HIST")
@TableGenerator(name = "STLADLE_HEAT_LIFE_H_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "StLadleLifeHeatHistSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)

public class StLdlLifeAtHeat implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STLADLE_HEAT_LIFE_H_SEQ_GEN")
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
	
	@Column(name="STLADLE_LIFE")
	private Integer stladle_life;
	
	@Column(name="STLADLE_STATUS")
	private Integer stladle_status;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_SI_NO" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
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

	public Integer getStladle_life() {
		return stladle_life;
	}

	public void setStladle_life(Integer stladle_life) {
		this.stladle_life = stladle_life;
	}

	public Integer getStladle_status() {
		return stladle_status;
	}

	public void setStladle_status(Integer stladle_status) {
		this.stladle_status = stladle_status;
	}

	public SteelLadleMasterModel getStlLdlMstrModel() {
		return stlLdlMstrModel;
	}

	public void setStlLdlMstrModel(SteelLadleMasterModel stlLdlMstrModel) {
		this.stlLdlMstrModel = stlLdlMstrModel;
	}
	
}
