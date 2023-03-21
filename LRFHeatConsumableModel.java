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

import com.smes.masters.model.MtrlProcessConsumableMstrModel;

@Entity
@Table(name="TRNS_LRF_HEAT_CONS_LINES")
@TableGenerator(name = "TRNS_LRF_HEAT_CONS_LINE_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "LrfHeatConsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class LRFHeatConsumableModel  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_LRF_HEAT_CONS_LINE_SEQ_GEN")
	@Column(name="CONS_SI_NO")
	private Integer cons_sl_no;
	
	@Column(name="ARCING_SI_NO")
	private Integer arc_sl_no;
	
	@Column(name="HEAT_ID")
	private String heat_id;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="MATERIAL_TYPE")
	private Integer material_type;
	
	@Column(name="MATERIAL_ID")
	private Integer material_id;
	
	@Column(name="CONSUMPTION_QTY")
	private Double consumption_qty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ADDITION_DATE_TIME",updatable=false)
	private Date addition_date_time;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;

	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="CREATED_BY")
	private Integer created_by;
	
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Column(name="SAP_MATL_ID")
	private String sap_matl_id;	
	
	@Column(name="VALUATION_TYPE")
	private String valuation_type;	

	@Transient
	private String material_name,uom;
	
	@Transient
	private Double tot_qty, act_tot_qty;

	@ManyToOne(optional=true)
	@JoinColumn(name="MATERIAL_ID" ,referencedColumnName="MATERIAL_ID",insertable=false,updatable=false)
	private  MtrlProcessConsumableMstrModel mtrlMstrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="ARCING_SI_NO" ,referencedColumnName="ARC_SI_NO",insertable=false,updatable=false)
	private LRFHeatArcingDetailsModel heatarcingDeatils;
		
	@Transient
	private String arc_grid_arry;
	
	public Double getTot_qty() {
		return tot_qty;
	}

	public void setTot_qty(Double tot_qty) {
		this.tot_qty = tot_qty;
	}

	public Double getAct_tot_qty() {
		return act_tot_qty;
	}

	public void setAct_tot_qty(Double act_tot_qty) {
		this.act_tot_qty = act_tot_qty;
	}

	public String getArc_grid_arry() {
		return arc_grid_arry;
	}

	public void setArc_grid_arry(String arc_grid_arry) {
		this.arc_grid_arry = arc_grid_arry;
	}
	public LRFHeatArcingDetailsModel getHeatarcingDeatils() {
		return heatarcingDeatils;
	}

	public void setHeatarcingDeatils(LRFHeatArcingDetailsModel heatarcingDeatils) {
		this.heatarcingDeatils = heatarcingDeatils;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public Integer getCons_sl_no() {
		return cons_sl_no;
	}

	public void setCons_sl_no(Integer cons_sl_no) {
		this.cons_sl_no = cons_sl_no;
	}

	public Integer getArc_sl_no() {
		return arc_sl_no;
	}

	public void setArc_sl_no(Integer arc_sl_no) {
		this.arc_sl_no = arc_sl_no;
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

	public Integer getMaterial_type() {
		return material_type;
	}

	public void setMaterial_type(Integer material_type) {
		this.material_type = material_type;
	}

	public Integer getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(Integer material_id) {
		this.material_id = material_id;
	}

	public Double getConsumption_qty() {
		return consumption_qty;
	}

	public void setConsumption_qty(Double consumption_qty) {
		this.consumption_qty = consumption_qty;
	}

	public Date getAddition_date_time() {
		return addition_date_time;
	}

	public void setAddition_date_time(Date addition_date_time) {
		this.addition_date_time = addition_date_time;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
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
	
	public String getSap_matl_id() {
		return sap_matl_id;
	}

	public void setSap_matl_id(String sap_matl_id) {
		this.sap_matl_id = sap_matl_id;
	}
	
	public String getValuation_type() {
		return valuation_type;
	}

	public void setValuation_type(String valuation_type) {
		this.valuation_type = valuation_type;
	}

	public MtrlProcessConsumableMstrModel getMtrlMstrModel() {
		return mtrlMstrModel;
	}

	public void setMtrlMstrModel(MtrlProcessConsumableMstrModel mtrlMstrModel) {
		this.mtrlMstrModel = mtrlMstrModel;
	}
	
	
}