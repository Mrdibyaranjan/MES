package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.MainHeatStatusMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;

@Entity
@Table(name="TRNS_HEAT_PLAN_LINES")
@TableGenerator(name = "HEAT_PLAN_LINE_NEW_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatPlanLineNewSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class HeatPlanLinesDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_PLAN_LINE_NEW_SEQ_GEN")
	@Column(name="HEAT_LINE_ID",nullable=false)
	private Integer heat_line_id;
		
	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="HEAT_PLAN_LINE_NO",nullable=false)
	private Integer heat_plan_line_no;
	
	@Column(name="INDENT_NO")
	private String indent_no;
	
	@Column(name="PLAN_CUT_LENGTH",nullable=false)
	private Double plan_cut_length;
	
	@Column(name="ALTERNATIVE_CUT_LEN_MIN")
	private Double alter_cut_length_min;
	
	@Column(name="ALTERNATIVE_CUT_LEN_MAX")
	private Double alter_cut_length_max;
	
	@Column(name="PLAN_HEAT_QTY")
	private Double plan_heat_qty;
	
	@Column(name="LINE_REMARKS")
	private String line_remarks;
	
	@Column(name="LINE_STATUS")
	private Integer line_status;
	
	@Column(name="CREATED_BY",updatable=false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date createdDateTime;	

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updatedDateTime;
		
	@Column(name="PLAN_CUSTOMER")
	private String plan_customer;
	
	@Column(name="UNIT_STATUS_ID")
	private String unit_status_id;
	
	@Column(name="HEAT_ID")
	private String heat_id;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;

	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@Column(name="AIM_PSN")
	private Integer aim_psn;

	@Column(name="TUNDISH_TYPE")
	private Integer tundish_type;
	
	@Column(name="CASTING_ORDER_REQ")
	private String casting_order_req;
	
	@Column(name="SO_HEADER_ID")
	private Integer soHeaderId;
	
	@Transient
	private Integer slNo;
	
	@Transient
	private String process_route;
	
	@Transient
	private String grade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="HEAT_PLAN_ID", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	private HeatPlanHdrDetails heatPlanHdrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="LINE_STATUS",referencedColumnName="MAIN_STATUS_ID",insertable=false,updatable=false)
	private MainHeatStatusMasterModel statusMstrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="AIM_PSN",referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TUNDISH_TYPE",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupTundishType;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SO_HEADER_ID",referencedColumnName="SO_ID",insertable=false,updatable=false)
	private SoHeader soHeaderModel;
	
	/*@ManyToOne(optional=true)
	@JoinColumn(name="CASTING_ORDER_REQ",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupCastingOrder;
	*/
	
	public Integer getHeat_line_id() {
		return heat_line_id;
	}

	public void setHeat_line_id(Integer heat_line_id) {
		this.heat_line_id = heat_line_id;
	}
	
	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Integer getHeat_plan_line_no() {
		return heat_plan_line_no;
	}

	public void setHeat_plan_line_no(Integer heat_plan_line_no) {
		this.heat_plan_line_no = heat_plan_line_no;
	}

	public String getIndent_no() {
		return indent_no;
	}

	public void setIndent_no(String indent_no) {
		this.indent_no = indent_no;
	}

	public Double getPlan_cut_length() {
		return plan_cut_length;
	}

	public void setPlan_cut_length(Double plan_cut_length) {
		this.plan_cut_length = plan_cut_length;
	}

	public Double getAlter_cut_length_min() {
		return alter_cut_length_min;
	}

	public void setAlter_cut_length_min(Double alter_cut_length_min) {
		this.alter_cut_length_min = alter_cut_length_min;
	}

	public Double getAlter_cut_length_max() {
		return alter_cut_length_max;
	}

	public void setAlter_cut_length_max(Double alter_cut_length_max) {
		this.alter_cut_length_max = alter_cut_length_max;
	}

	public Double getPlan_heat_qty() {
		return plan_heat_qty;
	}

	public void setPlan_heat_qty(Double plan_heat_qty) {
		this.plan_heat_qty = plan_heat_qty;
	}

	public String getLine_remarks() {
		return line_remarks;
	}

	public void setLine_remarks(String line_remarks) {
		this.line_remarks = line_remarks;
	}

	public Integer getLine_status() {
		return line_status;
	}

	public void setLine_status(Integer line_status) {
		this.line_status = line_status;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getPlan_customer() {
		return plan_customer;
	}

	public void setPlan_customer(String plan_customer) {
		this.plan_customer = plan_customer;
	}

	public String getUnit_status_id() {
		return unit_status_id;
	}

	public void setUnit_status_id(String unit_status_id) {
		this.unit_status_id = unit_status_id;
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

	public HeatPlanHdrDetails getHeatPlanHdrModel() {
		return heatPlanHdrModel;
	}

	public void setHeatPlanHdrModel(HeatPlanHdrDetails heatPlanHdrModel) {
		this.heatPlanHdrModel = heatPlanHdrModel;
	}

	public MainHeatStatusMasterModel getStatusMstrModel() {
		return statusMstrModel;
	}

	public void setStatusMstrModel(MainHeatStatusMasterModel statusMstrModel) {
		this.statusMstrModel = statusMstrModel;
	}
	public Integer getSlNo() {
		return slNo;
	}

	public void setSlNo(Integer slNo) {
		this.slNo = slNo;
	}

	public Integer getAim_psn() {
		return aim_psn;
	}

	public void setAim_psn(Integer aim_psn) {
		this.aim_psn = aim_psn;
	}

	public Integer getTundish_type() {
		return tundish_type;
	}

	public void setTundish_type(Integer tundish_type) {
		this.tundish_type = tundish_type;
	}

	public PSNHdrMasterModel getPsnHdrModel() {
		return psnHdrModel;
	}

	public void setPsnHdrModel(PSNHdrMasterModel psnHdrModel) {
		this.psnHdrModel = psnHdrModel;
	}

	public LookupMasterModel getLookupTundishType() {
		return lookupTundishType;
	}

	public void setLookupTundishType(LookupMasterModel lookupTundishType) {
		this.lookupTundishType = lookupTundishType;
	}

	public String getProcess_route() {
		return process_route;
	}

	public void setProcess_route(String process_route) {
		this.process_route = process_route;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCasting_order_req() {
		return casting_order_req;
	}

	public void setCasting_order_req(String casting_order_req) {
		this.casting_order_req = casting_order_req;
	}

	public Integer getSoHeaderId() {
		return soHeaderId;
	}

	public void setSoHeaderId(Integer soHeaderId) {
		this.soHeaderId = soHeaderId;
	}

	public SoHeader getSoHeaderModel() {
		return soHeaderModel;
	}

	public void setSoHeaderModel(SoHeader soHeaderModel) {
		this.soHeaderModel = soHeaderModel;
	}
	
/*
	public LookupMasterModel getLookupCastingOrder() {
		return lookupCastingOrder;
	}
	public void setLookupCastingOrder(LookupMasterModel lookupCastingOrder) {
		this.lookupCastingOrder = lookupCastingOrder;
	}
*/
	
	
}
