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




import com.smes.admin.model.AppUserAccountDetails;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.model.SubUnitMasterModel;



@Entity

@Table(name="TRNS_LRF_HEAT_DTLS")

@TableGenerator(name = "TRNS_LRF_HEAT_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "LrfHeatSeqId", allocationSize = 1)

@DynamicUpdate(value=true)

@OptimisticLocking(type=OptimisticLockType.VERSION)

public class LRFHeatDetailsModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_LRF_HEAT_DTLS_SEQ_GEN")
	@Column(name="TRNS_SI_NO")
	private Integer trns_sl_no; 



	@Column(name="HEAT_ID")

	private String heat_id;

	

	@Column(name="HEAT_COUNTER")

	private Integer heat_counter;

	

	@Column(name="STEEL_WT")

	private Double steel_wgt;

	

	@Column(name="STEEL_LADLE_NO")

	private Integer steel_ladle_no;

	

	@Column(name="HEAT_PLAN_ID")

	private Integer heat_plan_id;

	

	@Column(name="HEAT_PLAN_LINE_NO")

	private Integer heat_plan_line_no;

	

	@Column(name="TARGET_CASTER")

	private Integer target_caster_id;



	@Column(name="SUB_UNIT_ID")

	private Integer sub_unit_id;

	

	@Column(name="RECORD_STATUS")

	private Integer record_status;

	

	@Column(name="LRF_PROCESS_REMARKS")

	private String lrf_process_remarks;

	

	@Version

	@Column(name="RECORD_VERSION")

	private Integer record_version;

	

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

	



	@Temporal(TemporalType.TIMESTAMP)

	@Column(name="PRODUCTION_DATE")

	private Date production_date;

	

	@Column(name="PRODUCTION_SHIFT")

	private Integer production_shift;

	

	@Column(name="AIM_PSN")

	private Integer aim_psn;

	

	@Column(name="TAP_TEMP")

	private Integer tap_temp;

	

	

	@Column(name="PURGE_MEDIUM")

	private Integer purge_medium;

	

	@Column(name="PROCESS_CONTROL")

	private Integer process_control;

	

	@Column(name="PREV_UNIT")

	private String prev_unit;

	

	@Column(name="LRF_INITIAL_TEMP")

	private Double lrf_initial_temp;

	

	@Column(name="DISPATCH_TEMP")

	private Double lrf_dispatch_temp;

	

	



	@Column(name="DISPATCH_TO_UNIT")

	private Integer lrf_dispatch_unit;

	

	@Column(name="DISPATCH_DATE")

	private Date lrf_dispatch_date;





	@Column(name="LRF_DISPATCH_WGT")

	private Double lrf_dispatch_wgt;

	

	@Column(name="STEEL_WT_OLD")

	private Double steel_wgt_old;

	

	@Column(name="HEAT_ID_OLD")

	private String heat_id_old;

	

	@Column(name="STEEL_LADLE_NO_OLD")

	private Integer steel_ladle_no_old;

	

	

	@Column(name="VESSEL_CAR_NO")

	private String vessel_car_no;

	

	@Column(name="AR_N2_CONSUMPTION")

	private Float AR_N2_CONSUMPTION;

	
	@Column(name="ELECTRODE_START_TIME")

	private Date electrodeStartTime;

	

	@Column(name="ELECTRODE_END_TIME")

	private Date electrodeEndTime;
	
	@Column(name="VESSEL_CAR_ID")
	private Integer vessel_car_id;
	
	@Column(name="RELADLING")
	private String reladling;
	
	@Column(name="RETURN_REMARKS")
	private String returnRemarks;

	@Transient
	private String sub_unit_name,target_caster_name,psn_no,steel_ladle_name,aim_psn_char,act_proc_path,psn_grade,chem_details, chem_element_name,ccm_section_size;

	@Transient
	private Double mix_qty;

	@Transient
	private Integer heat_track_id;

	@Transient
	private Integer element_id;

	@Transient
	private Double element_aim_value;

	@Transient
	private Double lrf_C,lrf_MN,lrf_P,lrf_S,lrf_Si,lrf_Ti,plan_cut_length;

	public Double getPlan_cut_length() {
		return plan_cut_length;
	}


	public void setPlan_cut_length(Double plan_cut_length) {
		this.plan_cut_length = plan_cut_length;
	}


	@ManyToOne(optional=true)
	@JoinColumn(name="AIM_PSN" ,referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrMstrMdl;

	@ManyToOne(optional=true)
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstrMdl;

	@ManyToOne(optional=true)
	@JoinColumn(name="PRODUCTION_SHIFT" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupMdl;	

	@ManyToOne(optional=true)
	@JoinColumn(name="CREATED_BY" ,referencedColumnName="APP_USER_ID",insertable=false,updatable=false)
	private AppUserAccountDetails appUserModel;	

	@ManyToOne(optional=true)
	@JoinColumn(name="PURGE_MEDIUM" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookUpMdl_pm;	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="VESSEL_CAR_ID" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookUpMdlVid;	
	
	@ManyToOne(optional=true)
	@JoinColumn(name="HEAT_PLAN_ID" ,referencedColumnName="HEAT_PLAN_ID",insertable=false,updatable=false)
	private HeatPlanHdrDetails heatPlanModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_NO" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel ladleMstr;
	
	public String getChem_element_name() {
		return chem_element_name;
	}


	public void setChem_element_name(String chem_element_name) {
		this.chem_element_name = chem_element_name;
	}

	public Double getLrf_dispatch_wgt() {
		return lrf_dispatch_wgt;
	}

	public Integer getElement_id() {
		return element_id;
	}

	public void setElement_id(Integer element_id) {
		this.element_id = element_id;
	}

	public String getAct_proc_path() {
		return act_proc_path;
	}

	public void setAct_proc_path(String act_proc_path) {
		this.act_proc_path = act_proc_path;
	}

	public Double getElement_aim_value() {
		return element_aim_value;
	}

	public void setElement_aim_value(Double element_aim_value) {
		this.element_aim_value = element_aim_value;
	}

	public PSNHdrMasterModel getPsnHdrMstrMdl() {
		return psnHdrMstrMdl;
	}

	public void setPsnHdrMstrMdl(PSNHdrMasterModel psnHdrMstrMdl) {
		this.psnHdrMstrMdl = psnHdrMstrMdl;
	}

	public String getReladling() {
		return reladling;
	}

	public void setReladling(String reladling) {
		this.reladling = reladling;
	}

	public SubUnitMasterModel getSubUnitMstrMdl() {

		return subUnitMstrMdl;

	}



	public void setSubUnitMstrMdl(SubUnitMasterModel subUnitMstrMdl) {

		this.subUnitMstrMdl = subUnitMstrMdl;

	}



	public LookupMasterModel getLookupMdl() {

		return lookupMdl;

	}



	public void setLookupMdl(LookupMasterModel lookupMdl) {

		this.lookupMdl = lookupMdl;

	}



	public void setLrf_dispatch_wgt(Double lrf_dispatch_wgt) {

		this.lrf_dispatch_wgt = lrf_dispatch_wgt;

	}

	



	public Integer getHeat_track_id() {

		return heat_track_id;

	}



	public void setHeat_track_id(Integer heat_track_id) {

		this.heat_track_id = heat_track_id;

	}



	public String getLrf_process_remarks() {

		return lrf_process_remarks;

	}



	public void setLrf_process_remarks(String lrf_process_remarks) {

		this.lrf_process_remarks = lrf_process_remarks;

	}



	public Double getLrf_dispatch_temp() {

		return lrf_dispatch_temp;

	}



	public void setLrf_dispatch_temp(Double lrf_dispatch_temp) {

		this.lrf_dispatch_temp = lrf_dispatch_temp;

	}



	public Integer getLrf_dispatch_unit() {

		return lrf_dispatch_unit;

	}



	public void setLrf_dispatch_unit(Integer lrf_dispatch_unit) {

		this.lrf_dispatch_unit = lrf_dispatch_unit;

	}



	public Date getLrf_dispatch_date() {

		return lrf_dispatch_date;

	}



	public void setLrf_dispatch_date(Date lrf_dispatch_date) {

		this.lrf_dispatch_date = lrf_dispatch_date;

	}

	public Integer getSteel_ladle_no() {

		return steel_ladle_no;

	}



	public void setSteel_ladle_no(Integer steel_ladle_no) {

		this.steel_ladle_no = steel_ladle_no;

	}

	

	public String getSteel_ladle_name() {

		return steel_ladle_name;

	}



	public void setSteel_ladle_name(String steel_ladle_name) {

		this.steel_ladle_name = steel_ladle_name;

	}



	public String getPsn_no() {

		return psn_no;

	}



	public void setPsn_no(String psn_no) {

		this.psn_no = psn_no;

	}

	

	public Double getLrf_initial_temp() {

		return lrf_initial_temp;

	}



	public void setLrf_initial_temp(Double lrf_initial_temp) {

		this.lrf_initial_temp = lrf_initial_temp;

	}



	public Date getProduction_date() {

		return production_date;

	}



	public void setProduction_date(Date production_date) {

		this.production_date = production_date;

	}



	public Integer getProduction_shift() {

		return production_shift;

	}



	public void setProduction_shift(Integer production_shift) {

		this.production_shift = production_shift;

	}



	public Integer getAim_psn() {

		return aim_psn;

	}



	public void setAim_psn(Integer aim_psn) {

		this.aim_psn = aim_psn;

	}



	public Integer getTap_temp() {

		return tap_temp;

	}



	public void setTap_temp(Integer tap_temp) {

		this.tap_temp = tap_temp;

	}



	public String getPrev_unit() {

		return prev_unit;

	}



	public void setPrev_unit(String prev_unit) {

		this.prev_unit = prev_unit;

	}





	public Integer getTrns_sl_no() {

		return trns_sl_no;

	}



	public void setTrns_sl_no(Integer trns_sl_no) {

		this.trns_sl_no = trns_sl_no;

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



	public Double getSteel_wgt() {

		return steel_wgt;

	}



	public void setSteel_wgt(Double steel_wgt) {

		this.steel_wgt = steel_wgt;

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



	public Integer getTarget_caster_id() {

		return target_caster_id;

	}



	public void setTarget_caster_id(Integer target_caster_id) {

		this.target_caster_id = target_caster_id;

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



	public String getSub_unit_name() {

		return sub_unit_name;

	}



	public void setSub_unit_name(String sub_unit_name) {

		this.sub_unit_name = sub_unit_name;

	}



	public String getTarget_caster_name() {

		return target_caster_name;

	}



	public void setTarget_caster_name(String target_caster_name) {

		this.target_caster_name = target_caster_name;

	}



	public Integer getRecord_version() {

		return record_version;

	}



	public void setRecord_version(Integer record_version) {

		this.record_version = record_version;

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



	public String getAim_psn_char() {

		return aim_psn_char;

	}



	public void setAim_psn_char(String aim_psn_char) {

		this.aim_psn_char = aim_psn_char;

	}



	public Double getLrf_C() {

		return lrf_C;

	}



	public void setLrf_C(Double lrf_C) {

		this.lrf_C = lrf_C;

	}



	public Double getLrf_MN() {

		return lrf_MN;

	}



	public void setLrf_MN(Double lrf_MN) {

		this.lrf_MN = lrf_MN;

	}



	public Double getLrf_P() {

		return lrf_P;

	}



	public void setLrf_P(Double lrf_P) {

		this.lrf_P = lrf_P;

	}



	public Double getLrf_S() {

		return lrf_S;

	}



	public void setLrf_S(Double lrf_S) {

		this.lrf_S = lrf_S;

	}



	public Double getLrf_Si() {

		return lrf_Si;

	}



	public void setLrf_Si(Double lrf_Si) {

		this.lrf_Si = lrf_Si;

	}



	public Double getLrf_Ti() {

		return lrf_Ti;

	}



	public void setLrf_Ti(Double lrf_Ti) {

		this.lrf_Ti = lrf_Ti;

	}



	public Double getMix_qty() {

		return mix_qty;

	}



	public void setMix_qty(Double mix_qty) {

		this.mix_qty = mix_qty;

	}



	public String getHeat_id_old() {

		return heat_id_old;

	}



	public void setHeat_id_old(String heat_id_old) {

		this.heat_id_old = heat_id_old;

	}



	public Integer getSteel_ladle_no_old() {

		return steel_ladle_no_old;

	}



	public void setSteel_ladle_no_old(Integer steel_ladle_no_old) {

		this.steel_ladle_no_old = steel_ladle_no_old;

	}



	public Double getSteel_wgt_old() {

		return steel_wgt_old;

	}



	public void setSteel_wgt_old(Double steel_wgt_old) {

		this.steel_wgt_old = steel_wgt_old;

	}



	public Integer getPurge_medium() {

		return purge_medium;

	}



	public void setPurge_medium(Integer purge_medium) {

		this.purge_medium = purge_medium;

	}



	public Integer getProcess_control() {

		return process_control;

	}



	public void setProcess_control(Integer process_control) {

		this.process_control = process_control;

	}



	

	public String getVessel_car_no() {

		return vessel_car_no;

	}



	public void setVessel_car_no(String vessel_car_no) {

		this.vessel_car_no = vessel_car_no;

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



	public AppUserAccountDetails getAppUserModel() {

		return appUserModel;

	}



	public void setAppUserModel(AppUserAccountDetails appUserModel) {

		this.appUserModel = appUserModel;

	}



//	public SubUnitMasterModel getSubUnitMstrMdlVssl() {

//		return subUnitMstrMdlVssl;

//	}

//

//	public void setSubUnitMstrMdlVssl(SubUnitMasterModel subUnitMstrMdlVssl) {

//		this.subUnitMstrMdlVssl = subUnitMstrMdlVssl;

//	}

	

	public LookupMasterModel getLookUpMdl_pm() {

		return lookUpMdl_pm;

	}



	public void setLookUpMdl_pm(LookupMasterModel lookUpMdl_pm) {

		this.lookUpMdl_pm = lookUpMdl_pm;

	}



	public Float getAR_N2_CONSUMPTION() {

		return AR_N2_CONSUMPTION;

	}



	public void setAR_N2_CONSUMPTION(Float aR_N2_CONSUMPTION) {

		AR_N2_CONSUMPTION = aR_N2_CONSUMPTION;

	}



	public HeatPlanHdrDetails getHeatPlanModel() {

		return heatPlanModel;

	}



	public void setHeatPlanModel(HeatPlanHdrDetails heatPlanModel) {

		this.heatPlanModel = heatPlanModel;

	}



	public String getPsn_grade() {

		return psn_grade;

	}



	public void setPsn_grade(String psn_grade) {

		this.psn_grade = psn_grade;

	}



	public Integer getVessel_car_id() {
		return vessel_car_id;
	}

	public void setVessel_car_id(Integer vessel_car_id) {
		this.vessel_car_id = vessel_car_id;
	}

	public LookupMasterModel getLookUpMdl_vid() {
		return lookUpMdlVid;
	}

	public void setLookUpMdl_vid(LookupMasterModel lookUpMdl_vid) {
		this.lookUpMdlVid = lookUpMdl_vid;
	}

	public String getChem_details() {
		return chem_details;
	}

	public void setChem_details(String chem_details) {
		this.chem_details = chem_details;
	}

	public String getReturnRemarks() {
		return returnRemarks;
	}

	public void setReturnRemarks(String returnRemarks) {
		this.returnRemarks = returnRemarks;
	}

	public SteelLadleMasterModel getLadleMstr() {
		return ladleMstr;
	}


	public void setLadleMstr(SteelLadleMasterModel ladleMstr) {
		this.ladleMstr = ladleMstr;
	}


	public String getCcm_section_size() {
		return ccm_section_size;
	}


	public void setCcm_section_size(String ccm_section_size) {
		this.ccm_section_size = ccm_section_size;
	}



}

