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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.EquipmentMasterModel;
import com.smes.masters.model.PartsMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;


@Entity
@Table(name="TRNS_HEATWISE_STEEL_LADLE_LIFE")
@TableGenerator(name = "STLADLE_LIFE_HEAT_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "stLadleLifeHeatTrnsSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)

public class StLadleLifeHeatWiseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STLADLE_LIFE_HEAT_SEQ_GEN")
	@Column(name="STEEL_LADLE_LIFE_ID",unique=true, nullable=false)
	private Integer steel_ladle_life_id;
	
	@Column(name="HEAT_ID")
	private String heat_id;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steel_ladle_no;
	
	@Column(name="EQUIPMENT_ID")
	private Integer equipment_id;
	
	@Column(name="PART_ID")
	private Integer part_id;
	
	@Column(name="TRNS_LIFE")
	private Integer trns_life;
	
	@Column(name="PART_CHANGE_DATE")
	private Date part_change_date;
	
	@Column(name="PART_CHNG_LDL_STS")
	private Integer part_chng_ldl_sts;
	
	@Column(name="LDL_DOWN_DATE")
	private Date ldl_down_date;
	
	@Column(name="LDL_CIRC_RESERVED_DATE") //Reserved under circulation
	private Date ldl_circ_reserved_date;
	
	@Column(name="LDL_DOWN_HEATING_DATE") //Heating When Down before Making available
	private Date ldl_down_heating_date;
	
	@Column(name="LDL_HEAT_CHANGE_DATE") //Heat Completed for Ladle
	private Date ldl_heat_change_date;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="EQUIPMENT_ID" ,referencedColumnName="EQUIPMENT_ID",insertable=false,updatable=false)
	private EquipmentMasterModel eqpModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PART_ID" ,referencedColumnName="PART_ID",insertable=false,updatable=false)
	private PartsMasterModel partsModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_NO" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel stlLdlMstrModel;
		
	public PartsMasterModel getPartsModel() {
		return partsModel;
	}

	public void setPartsModel(PartsMasterModel partsModel) {
		this.partsModel = partsModel;
	}

	public EquipmentMasterModel getEqpModel() {
		return eqpModel;
	}

	public void setEqpModel(EquipmentMasterModel eqpModel) {
		this.eqpModel = eqpModel;
	}

	public SteelLadleMasterModel getStlLdlMstrModel() {
		return stlLdlMstrModel;
	}

	public void setStlLdlMstrModel(SteelLadleMasterModel stlLdlMstrModel) {
		this.stlLdlMstrModel = stlLdlMstrModel;
	}
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;	
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;

	/*@Transient
	private String part_name;
	
	@Transient
	private Integer max_life_value;*/
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
	}

	public Integer getSteel_ladle_life_id() {
		return steel_ladle_life_id;
	}

	public void setSteel_ladle_life_id(Integer steel_ladle_life_id) {
		this.steel_ladle_life_id = steel_ladle_life_id;
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

	public Integer getSteel_ladle_no() {
		return steel_ladle_no;
	}

	public void setSteel_ladle_no(Integer steel_ladle_no) {
		this.steel_ladle_no = steel_ladle_no;
	}

	public Integer getEquipment_id() {
		return equipment_id;
	}

	public void setEquipment_id(Integer equipment_id) {
		this.equipment_id = equipment_id;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public void setPart_id(Integer part_id) {
		this.part_id = part_id;
	}

	public Integer getTrns_life() {
		return trns_life;
	}

	public void setTrns_life(Integer trns_life) {
		this.trns_life = trns_life;
	}

	public Date getPart_change_date() {
		return part_change_date;
	}

	public void setPart_change_date(Date part_change_date) {
		this.part_change_date = part_change_date;
	}

	public Integer getPart_chng_ldl_sts() {
		return part_chng_ldl_sts;
	}

	public void setPart_chng_ldl_sts(Integer part_chng_ldl_sts) {
		this.part_chng_ldl_sts = part_chng_ldl_sts;
	}

	public Date getLdl_down_date() {
		return ldl_down_date;
	}

	public void setLdl_down_date(Date ldl_down_date) {
		this.ldl_down_date = ldl_down_date;
	}

	public Date getLdl_circ_reserved_date() {
		return ldl_circ_reserved_date;
	}

	public void setLdl_circ_reserved_date(Date ldl_circ_reserved_date) {
		this.ldl_circ_reserved_date = ldl_circ_reserved_date;
	}

	public Date getLdl_down_heating_date() {
		return ldl_down_heating_date;
	}

	public void setLdl_down_heating_date(Date ldl_down_heating_date) {
		this.ldl_down_heating_date = ldl_down_heating_date;
	}

	public Date getLdl_heat_change_date() {
		return ldl_heat_change_date;
	}

	public void setLdl_heat_change_date(Date ldl_heat_change_date) {
		this.ldl_heat_change_date = ldl_heat_change_date;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}

	public Date getCreated_date_time() {
		return created_date_time;
	}

	public void setCreated_date_time(Date created_date_time) {
		this.created_date_time = created_date_time;
	}

	public Integer getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public Date getUpdated_date_time() {
		return updated_date_time;
	}

	public void setUpdated_date_time(Date updated_date_time) {
		this.updated_date_time = updated_date_time;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}
	
}
