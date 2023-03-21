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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.LookupMasterModel;

@Entity
@Table(name="TRNS_LADLE_PARTS_MAINT_LOG")
@TableGenerator(name = "STEEL_LADLE_MAINT_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "steelLadleMaintSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class SteelLadleMaintenanceModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STEEL_LADLE_MAINT_SEQ_GEN")
	@Column(name="PARTS_MAINT_LOG_ID",unique=true, nullable=false)
	private Integer ladle_maint_id;
	
	@Column(name="LADLE_ID")
	private Integer ladle_id;
	
	@Column(name="STATUS_ID")
	private Integer status_id;
	
	@Column(name="PART_ID")
	private Integer part_id;
	
	@Column(name="PART_TYPE")
	private String part_type;
	
	@Column(name="PART_SUPPLIER")
	private Integer part_supplier;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CHANGED_DATE")
	private Date change_date;
	
	@Column(name="PLUG_POSITION")
	private Integer plug_position;
	
	@Column(name="FIRST_HEAT")
	private String first_heat;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HEAT_START_DATE")
	private Date start_date;
	
	@Column(name="LAST_HEAT")
	private String last_heat;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="END_DATE")
	private Date end_date;
	
	@Column(name="REMARKS")
	private String remarks;
	
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

	@Transient
	private String part_name, ladle_status, heat_id;
	
	@Transient
	private Integer ladle_life, nozzle_wbc_life, inner_nozzle, outer_nozzle, plug_wbc_life, plug_life, steel_ldl_track_id,
	random_plug, directional_plug, hybrid_plug, fixed_plate, movable_plate;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PART_SUPPLIER",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel supplierLkpModel;
		
	public LookupMasterModel getSupplierLkpModel() {
		return supplierLkpModel;
	}

	public void setSupplierLkpModel(LookupMasterModel supplierLkpModel) {
		this.supplierLkpModel = supplierLkpModel;
	}

	public Integer getSteel_ldl_track_id() {
		return steel_ldl_track_id;
	}

	public void setSteel_ldl_track_id(Integer steel_ldl_track_id) {
		this.steel_ldl_track_id = steel_ldl_track_id;
	}

	public Integer getRandom_plug() {
		return random_plug;
	}

	public void setRandom_plug(Integer random_plug) {
		this.random_plug = random_plug;
	}

	public Integer getDirectional_plug() {
		return directional_plug;
	}

	public void setDirectional_plug(Integer directional_plug) {
		this.directional_plug = directional_plug;
	}

	public Integer getHybrid_plug() {
		return hybrid_plug;
	}

	public void setHybrid_plug(Integer hybrid_plug) {
		this.hybrid_plug = hybrid_plug;
	}

	public Integer getFixed_plate() {
		return fixed_plate;
	}

	public void setFixed_plate(Integer fixed_plate) {
		this.fixed_plate = fixed_plate;
	}

	public Integer getMovable_plate() {
		return movable_plate;
	}

	public void setMovable_plate(Integer movable_plate) {
		this.movable_plate = movable_plate;
	}

	public String getHeat_id() {
		return heat_id;
	}

	public void setHeat_id(String heat_id) {
		this.heat_id = heat_id;
	}

	public Integer getLadle_life() {
		return ladle_life;
	}

	public void setLadle_life(Integer ladle_life) {
		this.ladle_life = ladle_life;
	}

	public Integer getNozzle_wbc_life() {
		return nozzle_wbc_life;
	}

	public void setNozzle_wbc_life(Integer nozzle_wbc_life) {
		this.nozzle_wbc_life = nozzle_wbc_life;
	}

	public Integer getInner_nozzle() {
		return inner_nozzle;
	}

	public void setInner_nozzle(Integer inner_nozzle) {
		this.inner_nozzle = inner_nozzle;
	}

	public Integer getOuter_nozzle() {
		return outer_nozzle;
	}

	public void setOuter_nozzle(Integer outer_nozzle) {
		this.outer_nozzle = outer_nozzle;
	}

	public Integer getPlug_wbc_life() {
		return plug_wbc_life;
	}

	public void setPlug_wbc_life(Integer plug_wbc_life) {
		this.plug_wbc_life = plug_wbc_life;
	}

	public Integer getPlug_life() {
		return plug_life;
	}

	public void setPlug_life(Integer plug_life) {
		this.plug_life = plug_life;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public String getLadle_status() {
		return ladle_status;
	}

	public void setLadle_status(String ladle_status) {
		this.ladle_status = ladle_status;
	}

	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}

	public Integer getLadle_maint_id() {
		return ladle_maint_id;
	}

	public void setLadle_maint_id(Integer ladle_maint_id) {
		this.ladle_maint_id = ladle_maint_id;
	}

	public Integer getLadle_id() {
		return ladle_id;
	}

	public void setLadle_id(Integer ladle_id) {
		this.ladle_id = ladle_id;
	}

	public Integer getStatus_id() {
		return status_id;
	}

	public void setStatus_id(Integer status_id) {
		this.status_id = status_id;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public void setPart_id(Integer part_id) {
		this.part_id = part_id;
	}

	public String getPart_type() {
		return part_type;
	}

	public void setPart_type(String part_type) {
		this.part_type = part_type;
	}

	public Integer getPart_supplier() {
		return part_supplier;
	}

	public void setPart_supplier(Integer part_supplier) {
		this.part_supplier = part_supplier;
	}

	public Date getChange_date() {
		return change_date;
	}

	public void setChange_date(Date change_date) {
		this.change_date = change_date;
	}

	public Integer getPlug_position() {
		return plug_position;
	}

	public void setPlug_position(Integer plug_position) {
		this.plug_position = plug_position;
	}

	public String getFirst_heat() {
		return first_heat;
	}

	public void setFirst_heat(String first_heat) {
		this.first_heat = first_heat;
	}

	public String getLast_heat() {
		return last_heat;
	}

	public void setLast_heat(String last_heat) {
		this.last_heat = last_heat;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
