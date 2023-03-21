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
import com.smes.masters.model.MainHeatStatusMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;

@Entity
@Table(name="TRNS_HEAT_PLAN_DTLS")
@TableGenerator(name = "HEAT_PLAN_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatPlanDtlsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class HeatPlanDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_PLAN_DTLS_SEQ_GEN")
	@Column(name="HEAT_PLAN_DTL_ID",nullable=false)
	private Integer heat_plan_dtl_id;
		
	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="PLAN_HEAT_ID",nullable=false)
	private String plan_heat_id;
	
	@Column(name="PLAN_HEAT_QTY",nullable=false)
	private Double plan_heat_qty;
	
	@Column(name="STATUS",nullable=false)
	private Integer status;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ACT_PROD_HEAT_QTY")
	private Double act_prod_heat_qty;
	
	@Column(name="ACT_HEAT_ID")
	private String act_heat_id;
	
	@Column(name="INDENT_NO",nullable=false)
	private Integer indent_no;
	
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
			
	@Column(name="RECORD_STATUS")
	private Integer record_status;

	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@Column(name="AIM_PSN")
	private Integer aim_psn;
		
	@Column(name="LP_SCHD_ID")
	private Integer lp_schd_id;
	
	@Column(name="LP_PLAN_HEAT_ID")
	private String lp_plan_heat_id;
	
	@Column(name="HEAT_PLAN_LINES_ID")
	private Integer heat_plan_lines_id;
	
	public Integer getLp_schd_id() {
		return lp_schd_id;
	}

	public void setLp_schd_id(Integer lp_schd_id) {
		this.lp_schd_id = lp_schd_id;
	}

	public String getLp_plan_heat_id() {
		return lp_plan_heat_id;
	}

	public void setLp_plan_heat_id(String lp_plan_heat_id) {
		this.lp_plan_heat_id = lp_plan_heat_id;
	}

	public Integer getHeat_plan_lines_id() {
		return heat_plan_lines_id;
	}

	public void setHeat_plan_lines_id(Integer heat_plan_lines_id) {
		this.heat_plan_lines_id = heat_plan_lines_id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="HEAT_PLAN_ID", nullable=false,insertable=false,updatable=false)
	@JsonBackReference
	private HeatPlanHdrDetails planHdrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STATUS",referencedColumnName="MAIN_STATUS_ID",insertable=false,updatable=false)
	private MainHeatStatusMasterModel statusMstrModel;

	@ManyToOne(optional=true)
	@JoinColumn(name="AIM_PSN",referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrModel;
	
	@Transient
	private String deleteFlag, target_caster, section_type, section,main_status_desc,psn_grade;
	
	@Transient
	private String grid_arry;
	

	public String getGrid_arry() {
		return grid_arry;
	}

	public void setGrid_arry(String grid_arry) {
		this.grid_arry = grid_arry;
	}

	public String getPsn_grade() {
		return psn_grade;
	}

	public void setPsn_grade(String psn_grade) {
		this.psn_grade = psn_grade;
	}

	public String getMain_status_desc() {
		return main_status_desc;
	}

	public void setMain_status_desc(String main_status_desc) {
		this.main_status_desc = main_status_desc;
	}

	public String getTarget_caster() {
		return target_caster;
	}

	public void setTarget_caster(String target_caster) {
		this.target_caster = target_caster;
	}

	public String getSection_type() {
		return section_type;
	}

	public void setSection_type(String section_type) {
		this.section_type = section_type;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getIndent_no() {
		return indent_no;
	}

	public void setIndent_no(Integer indent_no) {
		this.indent_no = indent_no;
	}

	public Integer getHeat_plan_dtl_id() {
		return heat_plan_dtl_id;
	}

	public void setHeat_plan_dtl_id(Integer heat_plan_dtl_id) {
		this.heat_plan_dtl_id = heat_plan_dtl_id;
	}

	public String getPlan_heat_id() {
		return plan_heat_id;
	}

	public void setPlan_heat_id(String plan_heat_id) {
		this.plan_heat_id = plan_heat_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getAct_prod_heat_qty() {
		return act_prod_heat_qty;
	}

	public void setAct_prod_heat_qty(Double act_prod_heat_qty) {
		this.act_prod_heat_qty = act_prod_heat_qty;
	}

	public String getAct_heat_id() {
		return act_heat_id;
	}

	public void setAct_heat_id(String act_heat_id) {
		this.act_heat_id = act_heat_id;
	}

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Double getPlan_heat_qty() {
		return plan_heat_qty;
	}

	public void setPlan_heat_qty(Double plan_heat_qty) {
		this.plan_heat_qty = plan_heat_qty;
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

	public HeatPlanHdrDetails getPlanHdrModel() {
		return planHdrModel;
	}

	public void setPlanHdrModel(HeatPlanHdrDetails planHdrModel) {
		this.planHdrModel = planHdrModel;
	}

	public MainHeatStatusMasterModel getStatusMstrModel() {
		return statusMstrModel;
	}

	public void setStatusMstrModel(MainHeatStatusMasterModel statusMstrModel) {
		this.statusMstrModel = statusMstrModel;
	}

	public Integer getAim_psn() {
		return aim_psn;
	}

	public void setAim_psn(Integer aim_psn) {
		this.aim_psn = aim_psn;
	}

	public PSNHdrMasterModel getPsnHdrModel() {
		return psnHdrModel;
	}

	public void setPsnHdrModel(PSNHdrMasterModel psnHdrModel) {
		this.psnHdrModel = psnHdrModel;
	}
	
}