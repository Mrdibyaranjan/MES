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

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.SubUnitMasterModel;

@Entity
@Table(name="TRNS_EOF_HEAT_DTLS")
@TableGenerator(name = "TRNS_EOF_HEAT_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "EofHeatSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class EofHeatDetails implements Serializable{
	
		private static final long serialVersionUID = 1L;
			
		@Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_EOF_HEAT_DTLS_SEQ_GEN")
		@Column(name="TRNS_SI_NO")
		private Integer trns_si_no;
		
		@Column(name="HEAT_ID",nullable=false)
		private String heat_id;
		
		@Column(name="HEAT_COUNTER",nullable=false)
		private Integer heat_counter;
		
		@Column(name="AIM_PSN",nullable=false)
		private Integer aim_psn;
		
		@Column(name="PRODUCTION_SHIFT")
		private Integer production_shift;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="PRODUCTION_DATE")
		private Date production_date;
		
		@Column(name="PLAN_WT")
		private Double plan_wt;
		
		@Column(name="HEAT_PLAN_ID",nullable=false)
		private Integer heat_plan_id;
		
		
		@Column(name="HEAT_PLAN_LINE_ID")
		private Integer heat_plan_line_id;
		
		@Column(name="STEEL_LADLE_NO")
		private Integer steel_ladle_no;
		
		@Column(name="ST_LADLE_RUN_LIFE")
		private Integer st_ladle_run_life;
		
		@Column(name="ST_LADLE_RUN_CONDITION")
		private Integer st_ladle_run_cond;
		
		@Column(name="BS_LIFE")
		private Integer bs_life;
		
		@Column(name="LAUNDER_LIFE")
		private Integer launder_life;
		
		@Column(name="TAP_WT")
		private Double tap_wt;
		
		@Column(name="HM_WT")
		private Double hm_wt;
		
		@Column(name="HM_TEMP")
		private Integer hm_temp;
		
		@Column(name="TAP_TEMP")
		private Integer tap_temp;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="DISPATCH_DATE")
		private Date dispatch_date;

		@Column(name="DISPATCH_TO_UNIT")
		private Integer dispatch_to_unit;
		
		@Column(name="EOF_CAMPAIGN_NO")
		private Integer eof_campaign_no;
		
		@Column(name="SUB_UNIT_ID")
		private Integer sub_unit_id;
		
		@Column(name="RECORD_STATUS")
		private Integer record_status;
		
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
		
		@Version
		@Column(name="RECORD_VERSION")
		private Integer version;
		
		@Column(name="HM_RECEIPT_ID")
		private Integer hm_receipt_id;
		
		@Column(name="CONTAINER_TRNS_ID")
		private Integer container_trns_id;
		
		@Column(name="CONTAINER_LIFE")
		private Integer container_life;
		
		@Column(name="FURNACE_LIFE")
		private Integer furnace_life;
		
		@Column(name="ELECTRODE_START_TIME")
		private Date electrodeStartTime;	

		@Column(name="ELECTRODE_END_TIME")
		private Date electrodeEndTime;
		
		@ManyToOne(optional=true)
		@JoinColumn(name="HM_RECEIPT_ID" ,referencedColumnName="HM_RECV_ID" ,insertable=false,updatable=false)
		private HmReceiveBaseDetails hmReceiveBaseDetails;
		
		@ManyToOne(optional=true)
		@JoinColumn(name="AIM_PSN" ,referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
		private PSNHdrMasterModel psnHdrMstrMdl;
		
//		@ManyToOne(optional=true)
//		@JoinColumn(name="HEAT_PLAN_LINE_ID" ,referencedColumnName="HEAT_LINE_ID",insertable=false,updatable=false)
//		private HeatPlanLinesDetails heatPlanLineDtls;
		
		@ManyToOne(optional=true)
		@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
		private SubUnitMasterModel subUnitMstrMdl;
		
		
		@ManyToOne(optional=true)
		@JoinColumn(name="PRODUCTION_SHIFT" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
		private LookupMasterModel lookupMdl;
		
		@Transient
		private String ladle_no,sub_unit_name,aim_psn_char,act_proc_path,unit_process_status,ccm_sec_size, psn_route;

		@Transient
		private Date hm_charge_at,scrap_charge_at,tap_start_at,tap_close_at;
		
		
		@Transient
		private Double available_hm;
		
		@Transient
		private Integer element_id, lrf_entry;
		
		@Transient
		private Double element_aim_value;
		
		@Transient
		private String steel_ladle_name;
		
		public Integer getLrf_entry() {
			return lrf_entry;
		}

		public void setLrf_entry(Integer lrf_entry) {
			this.lrf_entry = lrf_entry;
		}

		public String getSteel_ladle_name() {
			return steel_ladle_name;
		}

		public void setSteel_ladle_name(String steel_ladle_name) {
			this.steel_ladle_name = steel_ladle_name;
		}

		@Transient
		private Double eof_C,eof_MN,eof_P,eof_S,eof_Si,eof_Ti;
		
		/*@Column(name="SMS_MEASURED_HM_WT")
		private Double sms_measured_hm_wt;*/
	
		public Double getEof_Si() {
			return eof_Si;
		}

		public void setEof_Si(Double eof_Si) {
			this.eof_Si = eof_Si;
		}

		public Double getEof_Ti() {
			return eof_Ti;
		}

		public void setEof_Ti(Double eof_Ti) {
			this.eof_Ti = eof_Ti;
		}

		public Double getEof_C() {
			return eof_C;
		}

		public void setEof_C(Double eof_C) {
			this.eof_C = eof_C;
		}

		public Double getEof_MN() {
			return eof_MN;
		}

		public void setEof_MN(Double eof_MN) {
			this.eof_MN = eof_MN;
		}

		public Double getEof_P() {
			return eof_P;
		}

		public void setEof_P(Double eof_P) {
			this.eof_P = eof_P;
		}

		public Double getEof_S() {
			return eof_S;
		}

		public void setEof_S(Double eof_S) {
			this.eof_S = eof_S;
		}

		

		public Integer getElement_id() {
			return element_id;
		}

		public void setElement_id(Integer element_id) {
			this.element_id = element_id;
		}

		public Double getElement_aim_value() {
			return element_aim_value;
		}

		public void setElement_aim_value(Double element_aim_value) {
			this.element_aim_value = element_aim_value;
		}

		@Transient
		private Integer hmRecvId,heat_plan_line_no,caster_id,heat_track_id;
		

		@Transient
		private String psn_no,hm_ladle_no,sub_unit,caster_type,check,dispatch_to_unit_name;
		
		@Transient
		private String validationChar;
		@Transient
		private Integer value;
		
		public String getValidationChar() {
			return validationChar;
		}

		public void setValidationChar(String validationChar) {
			this.validationChar = validationChar;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public Integer getHeat_track_id() {
			return heat_track_id;
		}

		public void setHeat_track_id(Integer heat_track_id) {
			this.heat_track_id = heat_track_id;
		}

		public Integer getCaster_id() {
			return caster_id;
		}

		public void setCaster_id(Integer caster_id) {
			this.caster_id = caster_id;
		}
		public String getDispatch_to_unit_name() {
			return dispatch_to_unit_name;
		}

		public void setDispatch_to_unit_name(String dispatch_to_unit_name) {
			this.dispatch_to_unit_name = dispatch_to_unit_name;
		}

		public String getCheck() {
			return check;
		}

		public void setCheck(String check) {
			this.check = check;
		}

		public String getCaster_type() {
			return caster_type;
		}

		public void setCaster_type(String caster_type) {
			this.caster_type = caster_type;
		}

		
		public String getAct_proc_path() {
			return act_proc_path;
		}

		public void setAct_proc_path(String act_proc_path) {
			this.act_proc_path = act_proc_path;
		}

		public String getAim_psn_char() {
			return aim_psn_char;
		}

		public void setAim_psn_char(String aim_psn_char) {
			this.aim_psn_char = aim_psn_char;
		}

		public LookupMasterModel getLookupMdl() {
			return lookupMdl;
		}

		public void setLookupMdl(LookupMasterModel lookupMdl) {
			this.lookupMdl = lookupMdl;
		}

		public String getSub_unit_name() {
			return sub_unit_name;
		}

		public void setSub_unit_name(String sub_unit_name) {
			this.sub_unit_name = sub_unit_name;
		}

		public Integer getHmRecvId() {
			return hmRecvId;
		}

		public void setHmRecvId(Integer hmRecvId) {
			this.hmRecvId = hmRecvId;
		}

		public Double getAvailable_hm() {
			return available_hm;
		}

		public void setAvailable_hm(Double available_hm) {
			this.available_hm = available_hm;
		}

		public Date getHm_charge_at() {
			return hm_charge_at;
		}

		public void setHm_charge_at(Date hm_charge_at) {
			this.hm_charge_at = hm_charge_at;
		}

		public Date getScrap_charge_at() {
			return scrap_charge_at;
		}

		public void setScrap_charge_at(Date scrap_charge_at) {
			this.scrap_charge_at = scrap_charge_at;
		}

		public Date getTap_start_at() {
			return tap_start_at;
		}

		public void setTap_start_at(Date tap_start_at) {
			this.tap_start_at = tap_start_at;
		}

		public Date getTap_close_at() {
			return tap_close_at;
		}

		public void setTap_close_at(Date tap_close_at) {
			this.tap_close_at = tap_close_at;
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

		public Integer getAim_psn() {
			return aim_psn;
		}

		public void setAim_psn(Integer aim_psn) {
			this.aim_psn = aim_psn;
		}

		public Integer getProduction_shift() {
			return production_shift;
		}

		public void setProduction_shift(Integer production_shift) {
			this.production_shift = production_shift;
		}

		public Date getProduction_date() {
			return production_date;
		}

		public void setProduction_date(Date production_date) {
			this.production_date = production_date;
		}

		public Double getPlan_wt() {
			return plan_wt;
		}

		public void setPlan_wt(Double plan_wt) {
			this.plan_wt = plan_wt;
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

		public Integer getSteel_ladle_no() {
			return steel_ladle_no;
		}

		public void setSteel_ladle_no(Integer steel_ladle_no) {
			this.steel_ladle_no = steel_ladle_no;
		}

		public Integer getSt_ladle_run_life() {
			return st_ladle_run_life;
		}

		public void setSt_ladle_run_life(Integer st_ladle_run_life) {
			this.st_ladle_run_life = st_ladle_run_life;
		}

		public Integer getSt_ladle_run_cond() {
			return st_ladle_run_cond;
		}

		public void setSt_ladle_run_cond(Integer st_ladle_run_cond) {
			this.st_ladle_run_cond = st_ladle_run_cond;
		}

		public Integer getBs_life() {
			return bs_life;
		}

		public void setBs_life(Integer bs_life) {
			this.bs_life = bs_life;
		}

		public Integer getLaunder_life() {
			return launder_life;
		}

		public void setLaunder_life(Integer launder_life) {
			this.launder_life = launder_life;
		}

		public Double getTap_wt() {
			return tap_wt;
		}

		public void setTap_wt(Double tap_wt) {
			this.tap_wt = tap_wt;
		}

		public Double getHm_wt() {
			return hm_wt;
		}

		public void setHm_wt(Double hm_wt) {
			this.hm_wt = hm_wt;
		}

		public Integer getHm_temp() {
			return hm_temp;
		}

		public void setHm_temp(Integer hm_temp) {
			this.hm_temp = hm_temp;
		}

		public Integer getTap_temp() {
			return tap_temp;
		}

		public void setTap_temp(Integer tap_temp) {
			this.tap_temp = tap_temp;
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

		public Integer getEof_campaign_no() {
			return eof_campaign_no;
		}

		public void setEof_campaign_no(Integer eof_campaign_no) {
			this.eof_campaign_no = eof_campaign_no;
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

		public PSNHdrMasterModel getPsnHdrMstrMdl() {
			return psnHdrMstrMdl;
		}

		public void setPsnHdrMstrMdl(PSNHdrMasterModel psnHdrMstrMdl) {
			this.psnHdrMstrMdl = psnHdrMstrMdl;
		}

//		public HeatPlanLinesDetails getHeatPlanLineDtls() {
//			return heatPlanLineDtls;
//		}
//
//		public void setHeatPlanLineDtls(HeatPlanLinesDetails heatPlanLineDtls) {
//			this.heatPlanLineDtls = heatPlanLineDtls;
//		}

		public String getLadle_no() {
			return ladle_no;
		}

		public void setLadle_no(String ladle_no) {
			this.ladle_no = ladle_no;
		}

		public String getPsn_no() {
			return psn_no;
		}

		public void setPsn_no(String psn_no) {
			this.psn_no = psn_no;
		}

		public String getHm_ladle_no() {
			return hm_ladle_no;
		}

		public void setHm_ladle_no(String hm_ladle_no) {
			this.hm_ladle_no = hm_ladle_no;
		}

		public Integer getHeat_plan_line_no() {
			return heat_plan_line_no;
		}

		public void setHeat_plan_line_no(Integer heat_plan_line_no) {
			this.heat_plan_line_no = heat_plan_line_no;
		}

		
		
		public SubUnitMasterModel getSubUnitMstrMdl() {
			return subUnitMstrMdl;
		}

		public void setSubUnitMstrMdl(SubUnitMasterModel subUnitMstrMdl) {
			this.subUnitMstrMdl = subUnitMstrMdl;
		}

		public String getSub_unit() {
			return sub_unit;
		}

		public void setSub_unit(String sub_unit) {
			this.sub_unit = sub_unit;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}
		public String getUnit_process_status() {
			return unit_process_status;
		}

		public void setUnit_process_status(String unit_process_status) {
			this.unit_process_status = unit_process_status;
		}

		public Integer getHm_receipt_id() {
			return hm_receipt_id;
		}

		public void setHm_receipt_id(Integer hm_receipt_id) {
			this.hm_receipt_id = hm_receipt_id;
		}
		
		

		public Integer getContainer_trns_id() {
			return container_trns_id;
		}

		public void setContainer_trns_id(Integer container_trns_id) {
			this.container_trns_id = container_trns_id;
		}

		
		public Integer getContainer_life() {
			return container_life;
		}

		public void setContainer_life(Integer container_life) {
			this.container_life = container_life;
		}
		
		

		public Integer getFurnace_life() {
			return furnace_life;
		}

		public void setFurnace_life(Integer furnace_life) {
			this.furnace_life = furnace_life;
		}

		public HmReceiveBaseDetails getHmReceiveBaseDetails() {
			return hmReceiveBaseDetails;
		}

		public void setHmReceiveBaseDetails(HmReceiveBaseDetails hmReceiveBaseDetails) {
			this.hmReceiveBaseDetails = hmReceiveBaseDetails;
		}

		public String getCcm_sec_size() {
			return ccm_sec_size;
		}

		public void setCcm_sec_size(String ccm_sec_size) {
			this.ccm_sec_size = ccm_sec_size;
		}

		public Date getElectrodeStartTime() {
			return electrodeStartTime;
		}

		public void setElectrodeStartTime(Date electrodeStartTime) {
			this.electrodeStartTime = electrodeStartTime;
		}

		public Date getElectrodeEndTime() {
			return electrodeEndTime;
		}

		public void setElectrodeEndTime(Date electrodeEndTime) {
			this.electrodeEndTime = electrodeEndTime;
		}

		public String getPsn_route() {
			return psn_route;
		}

		public void setPsn_route(String psn_route) {
			this.psn_route = psn_route;
		}
		
}