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
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.EquipmentMasterModel;
import com.smes.masters.model.PartsMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;


@Entity
@Table(name="TRNS_STEEL_LADLE_LIFE")
@TableGenerator(name = "STEEL_LADLE_LIFE_TRNS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "steelLadleLifeTrnsSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class SteelLadleLifeModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STEEL_LADLE_LIFE_TRNS_SEQ_GEN")
	@Column(name="LADLE_LIFE_SL_NO",unique=true, nullable=false)
	private Integer ladle_life_sl_no;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steel_ladle_no;
	
	@Column(name="EQUIPMENT_ID")
	private Integer equipment_id;
	
	@Column(name="PART_ID")
	private Integer part_id;
	
	@Column(name="TRNS_LIFE")
	private Integer trns_life;
	
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

	@Transient
	private String part_name;
	
	@Transient
	private Integer max_life_value;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
	}
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

	@Transient
	private String grid1_arry;
	
	@Transient
	private String ladleCampStatus;
	
	@Transient
	private String ladleCampRemarks;
	
	public String getLadleCampRemarks() {
		return ladleCampRemarks;
	}

	public void setLadleCampRemarks(String ladleCampRemarks) {
		this.ladleCampRemarks = ladleCampRemarks;
	}

	public String getLadleCampStatus() {
		return ladleCampStatus;
	}

	public void setLadleCampStatus(String ladleCampStatus) {
		this.ladleCampStatus = ladleCampStatus;
	}

	public String getGrid1_arry() {
		return grid1_arry;
	}

	public void setGrid1_arry(String grid1_arry) {
		this.grid1_arry = grid1_arry;
	}

	public Integer getMax_life_value() {
		return max_life_value;
	}

	public void setMax_life_value(Integer max_life_value) {
		this.max_life_value = max_life_value;
	}

	public Integer getTrns_life() {
		return trns_life;
	}

	public void setTrns_life(Integer trns_life) {
		this.trns_life = trns_life;
	}

	public String getPart_name() {
		return part_name;
	}

	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}

	public Integer getLadle_life_sl_no() {
		return ladle_life_sl_no;
	}

	public void setLadle_life_sl_no(Integer ladle_life_sl_no) {
		this.ladle_life_sl_no = ladle_life_sl_no;
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
