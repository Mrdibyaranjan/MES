package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smes.admin.model.AppUserAccountDetails;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.MainHeatStatusMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.SMSCapabilityMasterModel;
import com.smes.masters.model.SubUnitMasterModel;


@Entity
@Table(name="TRNS_HEAT_PLAN_HEADER")
@TableGenerator(name = "HEAT_PLAN_NEW_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatPlanNewSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)

public class HeatPlanHdrDetails implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_PLAN_NEW_SEQ_GEN")
	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="CASTER_TYPE",nullable=false)
	private Integer caster_type;
	
	@Column(name="SECTION_TYPE",nullable=false)
	private Integer section_type;
	
	@Column(name="SECTION_PLANNED",nullable=false)
	private Integer section_planned;
		
	@Column(name="TARGET_EOF",nullable=false)
	private Integer target_eof;
	
	
	/*@Column(name="AIM_PSN_ID",nullable=false)
	private Integer aim_psn_id;

	@Column(name="TUNDISH_TYPE",nullable=false)
	private Integer tundish_type;*/
	
	@Column(name="NO_OF_HEATS_PLANNED",nullable=false)
	private Integer no_of_heats_planned;
	
	@Column(name="PLANNED_QTY")
	private Double planned_qty;
	
	@Column(name="PLAN_SEQUENCE",nullable=false)
	private Integer plan_sequence;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PLAN_CREATE_DATE")
	private Date plan_create_date;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PROD_START_DATE")
	private Date prod_start_date;

	@Column(name="PLAN_REMARKS")
	private String plan_remarks;
	
	@Column(name="MAIN_STATUS_ID",nullable=false)
	private Integer main_status_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PLAN_RELEASE_DATE")
	private Date plan_release_date;
	
	@Column(name="PLAN_RELEASE_BY")
	private Integer plan_release_by;
	
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
	
	@Column(name="RECORD_STATUS",nullable=false)
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@Transient
	private Integer pending_heats;
	
	@Column(name="LP_SCHD_ID")
	private Integer lp_schd_id;
	/*@Transient
	private String process_route;
	
	@Transient
	private String grade;*/
	
	public Integer getLp_schd_id() {
		return lp_schd_id;
	}

	public void setLp_schd_id(Integer lp_schd_id) {
		this.lp_schd_id = lp_schd_id;
	}

	@Transient
	private Integer line_no;
	
	@Transient
	private Double alter_cut_length_max;
	
	@Transient
	private Double alter_cut_length_min;
	
	@Transient
	private String product;

	@Transient
	private String grade; 
	
	@Transient
	private String cust_psn_ref; 
	
	public String getCust_psn_ref() {
		return cust_psn_ref;
	}

    public void setCust_psn_ref(String cust_psn_ref) {
		this.cust_psn_ref = cust_psn_ref;
	}

	@Transient
	private String process_route; 
	
	
	@Transient
	private Integer line_id;
	
	@Transient
	private Double cut_length;
	
	@Transient
	private Double plan_qty;
	
	@Transient
	private String line_status;
	
	@Transient
	private Integer line_version;
	
	@Transient
	private Integer total;
	
	@Transient
	private Integer aim_psn_id;
	
	@Transient
	private String psn_no;
	

	@Transient
	private String area;
	
	@Transient
	private String section;

	@Transient
	private Integer tundish; 
	
	@Transient
	private String remarks; 

	public Integer getTundish() {
		return tundish;
	}



	public void setTundish(Integer tundish) {
		this.tundish = tundish;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getSection() {
		return section;
	}
	
	

	public void setSection(String section) {
		this.section = section;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Transient
	private PSNHdrMasterModel psnHdrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="HEAT_PLAN_ID" ,referencedColumnName="HEAT_PLAN_ID",insertable=false,updatable=false)
	private HeatPlanLinesDetails heatPlanLinesDetails;
	
    @OneToMany(mappedBy="heatPlanHdrModel",fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<HeatPlanLinesDetails> heatPlanLine;
	
	public HeatPlanLinesDetails getHeatPlanLinesDetails() {
		return heatPlanLinesDetails;
	}

    public void setHeatPlanLinesDetails(HeatPlanLinesDetails heatPlanLinesDetails) {
		this.heatPlanLinesDetails = heatPlanLinesDetails;
	}

	@OneToMany(mappedBy="planHdrModel",fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<HeatPlanDetails> heatPlanDtls;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="CASTER_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupCasterType;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SECTION_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupSectionType;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SECTION_PLANNED" ,referencedColumnName="CAPABILITY_MSTR_ID",insertable=false,updatable=false)
	private SMSCapabilityMasterModel smsCapabilityMstrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TARGET_EOF" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitTargetEof;
	
	//@ManyToOne(optional=true)
	//@JoinColumn(name="AIM_PSN_ID" ,referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	//private PSNHdrMasterModel psnHdrModel;
	
//	@ManyToOne(optional=true)
//	@JoinColumn(name="TUNDISH_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
//	private LookupMasterModel lookupTundishType;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="MAIN_STATUS_ID" ,referencedColumnName="MAIN_STATUS_ID",insertable=false,updatable=false)
	private MainHeatStatusMasterModel mainHeatStatusMasterModel;
	
	@ManyToOne
	@JoinColumn(name="PLAN_RELEASE_BY" ,referencedColumnName="APP_USER_ID",insertable=false,updatable=false)
	private AppUserAccountDetails userDtlModel;
	
	
	@Transient
	private String sales_Order;
	
	public String getSales_Order() {
		return sales_Order;
	}



	public void setSales_Order(String sales_Order) {
		this.sales_Order = sales_Order;
	}



	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getAlter_cut_length_max() {
		return alter_cut_length_max;
	}

	public void setAlter_cut_length_max(Double alter_cut_length_max) {
		this.alter_cut_length_max = alter_cut_length_max;
	}

	public Double getAlter_cut_length_min() {
		return alter_cut_length_min;
	}

	public void setAlter_cut_length_min(Double alter_cut_length_min) {
		this.alter_cut_length_min = alter_cut_length_min;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getProcess_route() {
		return process_route;
	}

	public void setProcess_route(String process_route) {
		this.process_route = process_route;
	}

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Integer getCaster_type() {
		return caster_type;
	}

	public void setCaster_type(Integer caster_type) {
		this.caster_type = caster_type;
	}

	public Integer getSection_type() {
		return section_type;
	}

	public void setSection_type(Integer section_type) {
		this.section_type = section_type;
	}

	public Integer getSection_planned() {
		return section_planned;
	}

	public void setSection_planned(Integer section_planned) {
		this.section_planned = section_planned;
	}

	public Integer getTarget_eof() {
		return target_eof;
	}

	public void setTarget_eof(Integer target_eof) {
		this.target_eof = target_eof;
	}

	public Integer getAim_psn_id() {
		return aim_psn_id;
	}

	public void setAim_psn_id(Integer aim_psn_id) {
		this.aim_psn_id = aim_psn_id;
	}
    
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	/*public Integer getTundish_type() {
		return tundish_type;
	}

	public void setTundish_type(Integer tundish_type) {
		this.tundish_type = tundish_type;
	}
	*/
	public Integer getNo_of_heats_planned() {
		return no_of_heats_planned;
	}

	public void setNo_of_heats_planned(Integer no_of_heats_planned) {
		this.no_of_heats_planned = no_of_heats_planned;
	}

	public Integer getPlan_sequence() {
		return plan_sequence;
	}

	public void setPlan_sequence(Integer plan_sequence) {
		this.plan_sequence = plan_sequence;
	}

	public Date getPlan_create_date() {
		return plan_create_date;
	}

	public void setPlan_create_date(Date plan_create_date) {
		this.plan_create_date = plan_create_date;
	}

	public Date getProd_start_date() {
		return prod_start_date;
	}

	public void setProd_start_date(Date prod_start_date) {
		this.prod_start_date = prod_start_date;
	}

	public String getPlan_remarks() {
		return plan_remarks;
	}

	public void setPlan_remarks(String plan_remarks) {
		this.plan_remarks = plan_remarks;
	}

	public Integer getMain_status_id() {
		return main_status_id;
	}

	public void setMain_status_id(Integer main_status_id) {
		this.main_status_id = main_status_id;
	}

	public Date getPlan_release_date() {
		return plan_release_date;
	}

	public void setPlan_release_date(Date plan_release_date) {
		this.plan_release_date = plan_release_date;
	}

	public Integer getPlan_release_by() {
		return plan_release_by;
	}

	public void setPlan_release_by(Integer plan_release_by) {
		this.plan_release_by = plan_release_by;
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

	public String getPsn_no() {
		return psn_no;
	}

	public void setPsn_no(String psn_no) {
		this.psn_no = psn_no;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public LookupMasterModel getLookupCasterType() {
		return lookupCasterType;
	}

	public void setLookupCasterType(LookupMasterModel lookupCasterType) {
		this.lookupCasterType = lookupCasterType;
	}

	public LookupMasterModel getLookupSectionType() {
		return lookupSectionType;
	}

	public void setLookupSectionType(LookupMasterModel lookupSectionType) {
		this.lookupSectionType = lookupSectionType;
	}

	public SMSCapabilityMasterModel getSmsCapabilityMstrModel() {
		return smsCapabilityMstrModel;
	}

	public void setSmsCapabilityMstrModel(
			SMSCapabilityMasterModel smsCapabilityMstrModel) {
		this.smsCapabilityMstrModel = smsCapabilityMstrModel;
	}

	public SubUnitMasterModel getSubUnitTargetEof() {
		return subUnitTargetEof;
	}

	public void setSubUnitTargetEof(SubUnitMasterModel subUnitTargetEof) {
		this.subUnitTargetEof = subUnitTargetEof;
	}

	public PSNHdrMasterModel getPsnHdrModel() {
		return psnHdrModel;
	}

	public void setPsnHdrModel(PSNHdrMasterModel psnHdrModel) {
		this.psnHdrModel = psnHdrModel;
	}

	public MainHeatStatusMasterModel getMainHeatStatusMasterModel() {
		return mainHeatStatusMasterModel;
	}

	public void setMainHeatStatusMasterModel(
			MainHeatStatusMasterModel mainHeatStatusMasterModel) {
		this.mainHeatStatusMasterModel = mainHeatStatusMasterModel;
	}

	public AppUserAccountDetails getUserDtlModel() {
		return userDtlModel;
	}

	public void setUserDtlModel(AppUserAccountDetails userDtlModel) {
		this.userDtlModel = userDtlModel;
	}
	
	public Set<HeatPlanLinesDetails> getHeatPlanLine() {
		return heatPlanLine;
	}

	public void setHeatPlanLine(Set<HeatPlanLinesDetails> heatPlanLine) {
		this.heatPlanLine = heatPlanLine;
	}

//	public LookupMasterModel getLookupTundishType() {
//		return lookupTundishType;
//	}
//
//	public void setLookupTundishType(LookupMasterModel lookupTundishType) {
//		this.lookupTundishType = lookupTundishType;
//	}
	
	public Integer getPending_heats() {
		return pending_heats;
	}

	public void setPending_heats(Integer pending_heats) {
		this.pending_heats = pending_heats;
	}
	/*
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
	*/
	public Integer getLine_no() {
		return line_no;
	}

	public void setLine_no(Integer line_no) {
		this.line_no = line_no;
	}

	public Double getCut_length() {
		return cut_length;
	}

	public void setCut_length(Double cut_length) {
		this.cut_length = cut_length;
	}

	public Double getPlan_qty() {
		return plan_qty;
	}

	public void setPlan_qty(Double plan_qty) {
		this.plan_qty = plan_qty;
	}

	public String getLine_status() {
		return line_status;
	}

	public void setLine_status(String line_status) {
		this.line_status = line_status;
	}

	public Integer getLine_version() {
		return line_version;
	}

	public void setLine_version(Integer line_version) {
		this.line_version = line_version;
	}
	public Integer getLine_id() {
		return line_id;
	}

	public void setLine_id(Integer line_id) {
		this.line_id = line_id;
	}
	
	public Double getPlanned_qty() {
		return planned_qty;
	}

	public void setPlanned_qty(Double planned_qty) {
		this.planned_qty = planned_qty;
	}

	public Set<HeatPlanDetails> getHeatPlanDtls() {
		return heatPlanDtls;
	}

	public void setHeatPlanDtls(Set<HeatPlanDetails> heatPlanDtls) {
		this.heatPlanDtls = heatPlanDtls;
	}

}
