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

import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.SubUnitMasterModel;

@Entity
@Table(name="TRNS_VD_HEAT_DTLS")
@TableGenerator(name = "TRNS_VD_HEAT_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "VdHeatSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class VDHeatDetailsModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_VD_HEAT_DTLS_SEQ_GEN")
	@Column(name="TRNS_SI_NO")
	private Integer trns_si_no;
	
	@Column(name="HEAT_ID",nullable=false)
	private String heat_id;
	
	@Column(name="HEAT_COUNTER",nullable=false)
	private Integer heat_counter;
	
	@Column(name="STEEL_WT")
	private Float steel_wgt;

	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="HEAT_PLAN_LINE_ID")
	private Integer heat_plan_line_id;

	@Column(name="TARGET_CASTER")
	private Integer target_caster;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DISPATCH_DATE")
	private Date dispatch_date;

	@Column(name="DISPATCH_TO_UNIT")
	private Integer dispatch_to_unit;
	
	@Column(name="DISPATCH_TEMP")
	private Integer dispatch_temp;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steel_ladle_no;
	
	@Column(name="STEEL_LADLE_CAR_NO")
	private Integer steel_ladle_car_no;
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;	

	@Column(name="UPDATED_BY")
	private Integer updated_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Column(name="VD_START_DATE_TIME",updatable=false)
	private Date vd_start_date_time;
	
	@Column(name="VD_END_DATE_TIME",updatable=false)
	private Date vd_end_date_time;	
	
	@Column(name="LOWEST_M_BAR")
	private Integer lowest_m_bar;
	
	@Column(name="HOLDING_TIME")
	private Integer holding_time;
	
	@Column(name="POWER_CONS")
	private Integer power_consumption;
	
	@Column(name="BEFORE_VD_TEMP")
	private Integer before_vd_temp;
	
	@Column(name="AFTER_VD_TEMP")
	private Integer after_vd_temp;
	
	@Column(name="TOTAL_VD_TIME")
	private Integer total_vd_time;

	@Column(name="PRODUCTION_SHIFT")
	private Integer production_shift;
	
	@Column(name="AIM_PSN")
	private Integer aim_psn;
	
	@Column(name="PREV_UNIT")
	private String prev_unit;

	public Date getProduction_date() {
		return production_date;
	}

	public void setProduction_date(Date production_date) {
		this.production_date = production_date;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PRODUCTION_DATE")
	private Date production_date;

	@Column(name="VD_PROCESS_REMARKS")
	private String vd_process_remarks;
	
	@Column(name="VD_DISPATCH_WGT")
	private Integer vd_dispatch_wgt;
	
	public Integer getVd_dispatch_wgt() {
		return vd_dispatch_wgt;
	}

	public void setVd_dispatch_wgt(Integer vd_dispatch_wgt) {
		this.vd_dispatch_wgt = vd_dispatch_wgt;
	}

	public String getVd_process_remarks() {
		return vd_process_remarks;
	}

	public void setVd_process_remarks(String vd_process_remarks) {
		this.vd_process_remarks = vd_process_remarks;
	}

	public String getPrev_unit() {
		return prev_unit;
	}

	public void setPrev_unit(String prev_unit) {
		this.prev_unit = prev_unit;
	}

	@Transient
	private String sub_unit_name,psn_no,target_caster_name,steel_ladle_name;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="AIM_PSN" ,referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrMstrMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstrMdl;
	
	
	public PSNHdrMasterModel getPsnHdrMstrMdl() {
		return psnHdrMstrMdl;
	}

	public void setPsnHdrMstrMdl(PSNHdrMasterModel psnHdrMstrMdl) {
		this.psnHdrMstrMdl = psnHdrMstrMdl;
	}

	@Transient
	private Integer heat_trackingId;
	
	public Integer getHeat_trackingId() {
		return heat_trackingId;
	}

	public void setHeat_trackingId(Integer heat_trackingId) {
		this.heat_trackingId = heat_trackingId;
	}

	public String getSteel_ladle_name() {
		return steel_ladle_name;
	}

	public void setSteel_ladle_name(String steel_ladle_name) {
		this.steel_ladle_name = steel_ladle_name;
	}

	public String getTarget_caster_name() {
		return target_caster_name;
	}

	public void setTarget_caster_name(String target_caster_name) {
		this.target_caster_name = target_caster_name;
	}

	public Integer getAim_psn() {
		return aim_psn;
	}

	public void setAim_psn(Integer aim_psn) {
		this.aim_psn = aim_psn;
	}
	public String getSub_unit_name() {
		return sub_unit_name;
	}

	public void setSub_unit_name(String sub_unit_name) {
		this.sub_unit_name = sub_unit_name;
	}

	public Integer getProduction_shift() {
		return production_shift;
	}

	public void setProduction_shift(Integer production_shift) {
		this.production_shift = production_shift;
	}

	public Integer getTrns_si_no() {
		return trns_si_no;
	}

	public void setTrns_si_no(Integer trns_si_no) {
		this.trns_si_no = trns_si_no;
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



	public Float getSteel_wgt() {
		return steel_wgt;
	}

	public void setSteel_wgt(Float steel_wgt) {
		this.steel_wgt = steel_wgt;
	}

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Integer getHeat_plan_line_id() {
		return heat_plan_line_id;
	}

	public void setHeat_plan_line_id(Integer heat_plan_line_id) {
		this.heat_plan_line_id = heat_plan_line_id;
	}

	public Integer getTarget_caster() {
		return target_caster;
	}

	public void setTarget_caster(Integer target_caster) {
		this.target_caster = target_caster;
	}

	public Date getDispatch_date() {
		return dispatch_date;
	}

	public void setDispatch_date(Date dispatch_date) {
		this.dispatch_date = dispatch_date;
	}

	public Integer getDispatch_to_unit() {
		return dispatch_to_unit;
	}

	public void setDispatch_to_unit(Integer dispatch_to_unit) {
		this.dispatch_to_unit = dispatch_to_unit;
	}

	public Integer getDispatch_temp() {
		return dispatch_temp;
	}

	public void setDispatch_temp(Integer dispatch_temp) {
		this.dispatch_temp = dispatch_temp;
	}

	public Integer getSteel_ladle_no() {
		return steel_ladle_no;
	}

	public void setSteel_ladle_no(Integer steel_ladle_no) {
		this.steel_ladle_no = steel_ladle_no;
	}

	public Integer getSteel_ladle_car_no() {
		return steel_ladle_car_no;
	}

	public void setSteel_ladle_car_no(Integer steel_ladle_car_no) {
		this.steel_ladle_car_no = steel_ladle_car_no;
	}

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public Date getVd_start_date_time() {
		return vd_start_date_time;
	}

	public void setVd_start_date_time(Date vd_start_date_time) {
		this.vd_start_date_time = vd_start_date_time;
	}

	public Date getVd_end_date_time() {
		return vd_end_date_time;
	}

	public void setVd_end_date_time(Date vd_end_date_time) {
		this.vd_end_date_time = vd_end_date_time;
	}

	public Integer getLowest_m_bar() {
		return lowest_m_bar;
	}

	public void setLowest_m_bar(Integer lowest_m_bar) {
		this.lowest_m_bar = lowest_m_bar;
	}

	public Integer getHolding_time() {
		return holding_time;
	}

	public void setHolding_time(Integer holding_time) {
		this.holding_time = holding_time;
	}

	public Integer getPower_consumption() {
		return power_consumption;
	}

	public void setPower_consumption(Integer power_consumption) {
		this.power_consumption = power_consumption;
	}

	public Integer getBefore_vd_temp() {
		return before_vd_temp;
	}

	public void setBefore_vd_temp(Integer before_vd_temp) {
		this.before_vd_temp = before_vd_temp;
	}

	public Integer getAfter_vd_temp() {
		return after_vd_temp;
	}

	public void setAfter_vd_temp(Integer after_vd_temp) {
		this.after_vd_temp = after_vd_temp;
	}

	public Integer getTotal_vd_time() {
		return total_vd_time;
	}

	public void setTotal_vd_time(Integer total_vd_time) {
		this.total_vd_time = total_vd_time;
	}

	public String getPsn_no() {
		return psn_no;
	}

	public void setPsn_no(String psn_no) {
		this.psn_no = psn_no;
	}

	public SubUnitMasterModel getSubUnitMstrMdl() {
		return subUnitMstrMdl;
	}

	public void setSubUnitMstrMdl(SubUnitMasterModel subUnitMstrMdl) {
		this.subUnitMstrMdl = subUnitMstrMdl;
	}
	
	
	
	
	
}
