package com.smes.trans.model;

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
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.model.SubUnitMasterModel;

@Entity
@Table(name="TRNS_RELADLE_HEAT_DTLS")
@TableGenerator(name = "TRNS_RELADLE_HEAT_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "trnsReladleDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class ReladleTrnsDetailsMdl {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_RELADLE_HEAT_SEQ_GEN")
	@Column(name="TRNS_SL_NO")
	private Integer trns_sl_no;
	
	@Column(name="HEAT_ID")
	private String heat_id;
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;
	
	@Column(name="HEAT_REF_ID")
	private Integer heatRefId;
	
	@Column(name="AIM_PSN")
	private Integer aim_psn_no;
	
	@Column(name="ACT_QTY")
	private Float act_qty;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steelLadleNo;
	
	@Column(name="RETURN_TYPE")
	private Integer return_type;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RETURN_DATE")
	private Date return_date;//
	
	@Column(name="REASON")
	private String reason;
	
	@Column(name="BALANCE_QTY")
	private Float balance_qty;
	
	@Column(name="IS_PROCESSED")
	private String is_processed;
	
	@Column(name="RE_LADLE_NO")
	private Integer reladleNo;
	
	@Column(name="HEAT_PLAN_ID")
	private Integer heatPlanId;
	
	@Column(name="HEAT_PLAN_LINE_NO")
	private Integer heatPlanLineNo;
	
	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="HEAT_ID_H")
	private String heat_id_h;
	
	@Column(name="DISPATCH_TEMP")
	private Integer disp_temp;
	
	@Column(name="HEAT_COUNTER_H")
	private Integer heat_counter_h;
	
	@Column(name="CREATED_BY" ,updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	@Temporal(TemporalType.TIMESTAMP)
	
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;//
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Transient
	private String new_heat_id, psn_route;
	
	@Transient
	private Double pour_qty;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="AIM_PSN",referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrModel;

	@ManyToOne(optional=true)
	@JoinColumn(name="RETURN_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel returnTypeMdl;

	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_NO" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel steelLdlMstr;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstr;
	
	public String getNew_heat_id() {
		return new_heat_id;
	}

	public void setNew_heat_id(String new_heat_id) {
		this.new_heat_id = new_heat_id;
	}

	public Integer getHeat_counter() {
		return heat_counter;
	}

	public void setHeat_counter(Integer heat_counter) {
		this.heat_counter = heat_counter;
	}
	
	public SteelLadleMasterModel getSteelLdlMstr() {
		return steelLdlMstr;
	}

	public void setSteelLdlMstr(SteelLadleMasterModel steelLdlMstr) {
		this.steelLdlMstr = steelLdlMstr;
	}

	public SubUnitMasterModel getSubUnitMstr() {
		return subUnitMstr;
	}

	public void setSubUnitMstr(SubUnitMasterModel subUnitMstr) {
		this.subUnitMstr = subUnitMstr;
	}

	public PSNHdrMasterModel getPsnHdrModel() {
		return psnHdrModel;
	}

	public void setPsnHdrModel(PSNHdrMasterModel psnHdrModel) {
		this.psnHdrModel = psnHdrModel;
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

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public Integer getHeatRefId() {
		return heatRefId;
	}

	public void setHeatRefId(Integer heatRefId) {
		this.heatRefId = heatRefId;
	}

	public Integer getAim_psn_no() {
		return aim_psn_no;
	}

	public void setAim_psn_no(Integer aim_psn_no) {
		this.aim_psn_no = aim_psn_no;
	}


	public Float getAct_qty() {
		return act_qty;
	}

	public void setAct_qty(Float act_qty) {
		this.act_qty = act_qty;
	}

	public Integer getSteelLadleNo() {
		return steelLadleNo;
	}

	public void setSteelLadleNo(Integer steelLadleNo) {
		this.steelLadleNo = steelLadleNo;
	}

	public Integer getReturn_type() {
		return return_type;
	}

	public void setReturn_type(Integer return_type) {
		this.return_type = return_type;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Float getBalance_qty() {
		return balance_qty;
	}

	public void setBalance_qty(Float balance_qty) {
		this.balance_qty = balance_qty;
	}

	public String getIs_processed() {
		return is_processed;
	}

	public void setIs_processed(String is_processed) {
		this.is_processed = is_processed;
	}

	public Integer getReladleNo() {
		return reladleNo;
	}

	public void setReladleNo(Integer reladleNo) {
		this.reladleNo = reladleNo;
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

	public LookupMasterModel getReturnTypeMdl() {
		return returnTypeMdl;
	}

	public void setReturnTypeMdl(LookupMasterModel returnTypeMdl) {
		this.returnTypeMdl = returnTypeMdl;
	}

	public Double getPour_qty() {
		return pour_qty;
	}

	public void setPour_qty(Double pour_qty) {
		this.pour_qty = pour_qty;
	}

	public Integer getHeatPlanId() {
		return heatPlanId;
	}

	public void setHeatPlanId(Integer heatPlanId) {
		this.heatPlanId = heatPlanId;
	}

	public Integer getHeatPlanLineNo() {
		return heatPlanLineNo;
	}

	public void setHeatPlanLineNo(Integer heatPlanLineNo) {
		this.heatPlanLineNo = heatPlanLineNo;
	}

	public String getHeat_id_h() {
		return heat_id_h;
	}

	public void setHeat_id_h(String heat_id_h) {
		this.heat_id_h = heat_id_h;
	}

	public Integer getHeat_counter_h() {
		return heat_counter_h;
	}

	public void setHeat_counter_h(Integer heat_counter_h) {
		this.heat_counter_h = heat_counter_h;
	}

	public Integer getDisp_temp() {
		return disp_temp;
	}

	public void setDisp_temp(Integer disp_temp) {
		this.disp_temp = disp_temp;
	}

	public String getPsn_route() {
		return psn_route;
	}

	public void setPsn_route(String psn_route) {
		this.psn_route = psn_route;
	}	
}
