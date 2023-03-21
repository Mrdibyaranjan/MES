package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.ProductSectionMasterModel;
import com.smes.masters.model.PsnGradeMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.model.SubUnitMasterModel;
import com.smes.masters.model.TundishMasterModel;

@Entity
@Table(name="TRNS_CCM_HEAT_DETAILS")
@TableGenerator(name = "TRNS_CCM_HEAT_DETAILS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmHeatDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)

public class CCMHeatDetailsModel implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_HEAT_DETAILS_SEQ_GEN")
	@Column(name="TRNS_SL_NO")
	private Integer trns_sl_no;
	
	@Column(name="HEAT_NO")
	private String heat_no;

	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;
	
	@Column(name="PRODUCT")
	private Integer product_id;
	
	@Column(name="CS_SIZE")
	private Integer cs_size;
	
	@Column(name="RETURN_QTY")
	private Float return_qty;
	
	@Column(name="RETURN_TYPE")
	private Integer return_type;
	
	@Column(name="RETURN_QTY_ID")
	private Integer return_qty_id;
	
	@Column(name="STEEL_LADLE_NO")
	private Integer steel_ladle_no;
	
	@Column(name="STEEL_LADLE_WGT")
	private Float steel_ladle_wgt;
	
	@Column(name="PSN_NO")
	private Integer psn_no;
	
	@Column(name="CASTING_POWDER")
	private Integer casting_powder;
	
	@Column(name="TUNDISH_NO")
	private Integer tundish_no;
	
	@Column(name="TUNDISH_CHANGE")
	private String tundish_change;
	
	@Column(name="SEQ_BREAK")
	private String seq_break;
	
	@Column(name="SEQ_NO")
	private String seq_no;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PROD_DATE")
	private Date prod_date;
	
	@Column(name="ROUTE")
	private String route;
	
	@Column(name="EOF_WC")
	private Integer eof_wc;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="LADLE_OPEN_TYPE")
	private String ladle_open_type;
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;
	
	@Column(name="HEAT_PLAN")
	private Integer heat_plan_id;
	
	@Column(name="TUNDISH_CAR_NO")
	private Integer tundish_car_no;
	
	@Column(name="LADLE_CAR")
	private Integer ladle_car_no;
	
	@Column(name="GRADE")
	private Integer grade;
	
	@Column(name="HEAT_PLAN_LINE_NO")
	private Integer heat_plan_line_no;
	
//	@Column(name="PSN_NO")
//	private String psn_no;

	@Column(name="CREATED_BY" ,updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	@Temporal(TemporalType.TIMESTAMP)
	
	@Column(name="UPDATED_DATE")
	private Date updated_date_time;//
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="SEQ_GROUP_NO")
	private Integer seq_group_no;
	
	@Column(name="SHROUD_MAKE")
	private Integer shroud_make;
	
	@Column(name="SHROUD_CHANGE")
	private String shroud_change;
	
	@Column(name="SPECTRO_CHEM")
	private Integer spectro_chem;
	
	@Column(name="PRODUCTION_SHIFT")
	private Integer production_shift;
	
	@Column(name="STEEL_WT_ORIG")
	private Float steel_wt_orig;
	
	@Column(name="NO_OF_PIPES")
	private Integer no_of_pipes;
	
	@Transient
	String return_reason, is_processed, prev_unit, chem_details;
	
	@Transient
	private Double lrf_C,lrf_MN,lrf_P,eof_S,lrf_Si,lrf_Ti, lift_temp, totBatchWeight;
	
	@Transient
	private Integer totNoOfBatches, liftChemId;
	
	@OneToMany(mappedBy="ccmHeatDtls",fetch = FetchType.EAGER)
	private List<CCMProductDetailsModel> ccmProdHeatDtls;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstrMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TUNDISH_NO" ,referencedColumnName="TUNDISH_SL_NO",insertable=false,updatable=false)
	private TundishMasterModel tundishMstrObj;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PSN_NO" ,referencedColumnName="PSN_HDR_SL_NO",insertable=false,updatable=false)
	private PSNHdrMasterModel psnHdrMstrMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="HEAT_PLAN" ,referencedColumnName="heat_plan_id",insertable=false,updatable=false)
	private HeatPlanHdrDetails heatPlanMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STEEL_LADLE_NO" ,referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel steelLadleObj;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="TUNDISH_CAR_NO" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel tundishCarLkpMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="LADLE_CAR" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel ladleCarLkpMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PRODUCT" ,referencedColumnName="CCM_MAT_SEC_ID",insertable=false,updatable=false)
	private ProductSectionMasterModel productMasterMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="CS_SIZE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel sectionLookupModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="RETURN_TYPE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpReturnType;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="GRADE" ,referencedColumnName="PSN_GRADE_SL_NO",insertable=false,updatable=false)
	private PsnGradeMasterModel psnGradeMasterMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SHROUD_MAKE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpShrMakeMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="CASTING_POWDER" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpCastinPowerMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PRODUCTION_SHIFT" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpProductShiftMdl;
	
	public List<CCMProductDetailsModel> getCcmProdHeatDtls() {
		return ccmProdHeatDtls;
	}

	public void setCcmProdHeatDtls(List<CCMProductDetailsModel> ccmProdHeatDtls) {
		this.ccmProdHeatDtls = ccmProdHeatDtls;
	}

	public Integer getLiftChemId() {
		return liftChemId;
	}

	public void setLiftChemId(Integer liftChemId) {
		this.liftChemId = liftChemId;
	}

	public Double getTotBatchWeight() {
		return totBatchWeight;
	}

	public void setTotBatchWeight(Double totBatchWeight) {
		this.totBatchWeight = totBatchWeight;
	}

	public Integer getTotNoOfBatches() {
		return totNoOfBatches;
	}

	public void setTotNoOfBatches(Integer totNoOfBatches) {
		this.totNoOfBatches = totNoOfBatches;
	}

	public Float getSteel_wt_orig() {
		return steel_wt_orig;
	}

	public void setSteel_wt_orig(Float steel_wt_orig) {
		this.steel_wt_orig = steel_wt_orig;
	}

	public Integer getShroud_make() {
		return shroud_make;
	}

	public void setShroud_make(Integer shroud_make) {
		this.shroud_make = shroud_make;
	}

	public String getShroud_change() {
		return shroud_change;
	}

	public void setShroud_change(String shroud_change) {
		this.shroud_change = shroud_change;
	}

	public String getChem_details() {
		return chem_details;
	}

	public void setChem_details(String chem_details) {
		this.chem_details = chem_details;
	}

	public Double getLift_temp() {
		return lift_temp;
	}

	public void setLift_temp(Double lift_temp) {
		this.lift_temp = lift_temp;
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

	public String getHeat_no() {
		return heat_no;
	}

	public void setHeat_no(String heat_no) {
		this.heat_no = heat_no;
	}

	public Integer getHeat_counter() {
		return heat_counter;
	}

	public void setHeat_counter(Integer heat_counter) {
		this.heat_counter = heat_counter;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public Integer getCs_size() {
		return cs_size;
	}

	public void setCs_size(Integer cs_size) {
		this.cs_size = cs_size;
	}

	public Float getReturn_qty() {
		return return_qty;
	}

	public void setReturn_qty(Float return_qty) {
		this.return_qty = return_qty;
	}

	public Integer getReturn_type() {
		return return_type;
	}

	public void setReturn_type(Integer return_type) {
		this.return_type = return_type;
	}

	public Integer getReturn_qty_id() {
		return return_qty_id;
	}

	public void setReturn_qty_id(Integer return_qty_id) {
		this.return_qty_id = return_qty_id;
	}

	public Integer getSteel_ladle_no() {
		return steel_ladle_no;
	}

	public void setSteel_ladle_no(Integer steel_ladle_no) {
		this.steel_ladle_no = steel_ladle_no;
	}

	public Float getSteel_ladle_wgt() {
		return steel_ladle_wgt;
	}

	public void setSteel_ladle_wgt(Float steel_ladle_wgt) {
		this.steel_ladle_wgt = steel_ladle_wgt;
	}

	public Integer getPsn_no() {
		return psn_no;
	}

	public void setPsn_no(Integer psn_no) {
		this.psn_no = psn_no;
	}

	public Integer getCasting_powder() {
		return casting_powder;
	}

	public void setCasting_powder(Integer casting_powder) {
		this.casting_powder = casting_powder;
	}

	public Integer getTundish_no() {
		return tundish_no;
	}

	public void setTundish_no(Integer tundish_no) {
		this.tundish_no = tundish_no;
	}

	public String getTundish_change() {
		return tundish_change;
	}

	public void setTundish_change(String tundish_change) {
		this.tundish_change = tundish_change;
	}

	public String getSeq_break() {
		return seq_break;
	}

	public void setSeq_break(String seq_break) {
		this.seq_break = seq_break;
	}

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}

	public Date getProd_date() {
		return prod_date;
	}

	public void setProd_date(Date prod_date) {
		this.prod_date = prod_date;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Integer getEof_wc() {
		return eof_wc;
	}

	public void setEof_wc(Integer eof_wc) {
		this.eof_wc = eof_wc;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public Integer getHeat_plan_id() {
		return heat_plan_id;
	}

	public void setHeat_plan_id(Integer heat_plan_id) {
		this.heat_plan_id = heat_plan_id;
	}

	public Integer getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	public Date getCreated_date_time() {
		return created_date_time;
	}

	public void setCreated_date_time(Date created_date_time) {
		this.created_date_time = created_date_time;
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
	
	public LookupMasterModel getLkpProductShiftMdl() {
		return lkpProductShiftMdl;
	}

	public void setLkpProductShiftMdl(LookupMasterModel lkpProductShiftMdl) {
		this.lkpProductShiftMdl = lkpProductShiftMdl;
	}

	public LookupMasterModel getLkpShrMakeMdl() {
		return lkpShrMakeMdl;
	}

	public void setLkpShrMakeMdl(LookupMasterModel lkpShrMakeMdl) {
		this.lkpShrMakeMdl = lkpShrMakeMdl;
	}
	
	public LookupMasterModel getLkpCastinPowerMdl() {
		return lkpCastinPowerMdl;
	}

	public void setLkpCastinPowerMdl(LookupMasterModel lkpCastinPowerMdl) {
		this.lkpCastinPowerMdl = lkpCastinPowerMdl;
	}
	
	public LookupMasterModel getSectionLookupModel() {
		return sectionLookupModel;
	}

	public void setSectionLookupModel(LookupMasterModel sectionLookupModel) {
		this.sectionLookupModel = sectionLookupModel;
	}

	public PsnGradeMasterModel getPsnGradeMasterMdl() {
		return psnGradeMasterMdl;
	}

	public void setPsnGradeMasterMdl(PsnGradeMasterModel psnGradeMasterMdl) {
		this.psnGradeMasterMdl = psnGradeMasterMdl;
	}

	public SubUnitMasterModel getSubUnitMstrMdl() {
		return subUnitMstrMdl;
	}

	public void setSubUnitMstrMdl(SubUnitMasterModel subUnitMstrMdl) {
		this.subUnitMstrMdl = subUnitMstrMdl;
	}

	public TundishMasterModel getTundishMstrObj() {
		return tundishMstrObj;
	}

	public void setTundishMstrObj(TundishMasterModel tundishMstrObj) {
		this.tundishMstrObj = tundishMstrObj;
	}

	public PSNHdrMasterModel getPsnHdrMstrMdl() {
		return psnHdrMstrMdl;
	}

	public void setPsnHdrMstrMdl(PSNHdrMasterModel psnHdrMstrMdl) {
		this.psnHdrMstrMdl = psnHdrMstrMdl;
	}

	public HeatPlanHdrDetails getHeatPlanMdl() {
		return heatPlanMdl;
	}

	public void setHeatPlanMdl(HeatPlanHdrDetails heatPlanMdl) {
		this.heatPlanMdl = heatPlanMdl;
	}

	public SteelLadleMasterModel getSteelLadleObj() {
		return steelLadleObj;
	}

	public void setSteelLadleObj(SteelLadleMasterModel steelLadleObj) {
		this.steelLadleObj = steelLadleObj;
	}
	
	public LookupMasterModel getLkpReturnType() {
		return lkpReturnType;
	}

	public void setLkpReturnType(LookupMasterModel lkpReturnType) {
		this.lkpReturnType = lkpReturnType;
	}
	
	public ProductSectionMasterModel getProductMasterMdl() {
		return productMasterMdl;
	}

	public void setProductMasterMdl(ProductSectionMasterModel productMasterMdl) {
		this.productMasterMdl = productMasterMdl;
	}
	
	public Integer getTundish_car_no() {
		return tundish_car_no;
	}

	public void setTundish_car_no(Integer tundish_car_no) {
		this.tundish_car_no = tundish_car_no;
	}

	public LookupMasterModel getTundishCarLkpMdl() {
		return tundishCarLkpMdl;
	}

	public void setTundishCarLkpMdl(LookupMasterModel tundishCarLkpMdl) {
		this.tundishCarLkpMdl = tundishCarLkpMdl;
	}

	public String getReturn_reason() {
		return return_reason;
	}

	public void setReturn_reason(String return_reason) {
		this.return_reason = return_reason;
	}

	public String getIs_processed() {
		return is_processed;
	}

	public void setIs_processed(String is_processed) {
		this.is_processed = is_processed;
	}

	public Integer getSeq_group_no() {
		return seq_group_no;
	}

	public void setSeq_group_no(Integer seq_group_no) {
		this.seq_group_no = seq_group_no;
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

	public Double getEof_S() {
		return eof_S;
	}

	public void setEof_S(Double eof_S) {
		this.eof_S = eof_S;
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

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getHeat_plan_line_no() {
		return heat_plan_line_no;
	}

	public void setHeat_plan_line_no(Integer heat_plan_line_no) {
		this.heat_plan_line_no = heat_plan_line_no;
	}

	public Integer getLadle_car_no() {
		return ladle_car_no;
	}

	public void setLadle_car_no(Integer ladle_car_no) {
		this.ladle_car_no = ladle_car_no;
	}

	public LookupMasterModel getLadleCarLkpMdl() {
		return ladleCarLkpMdl;
	}

	public void setLadleCarLkpMdl(LookupMasterModel ladleCarLkpMdl) {
		this.ladleCarLkpMdl = ladleCarLkpMdl;
	}

	public Integer getSpectro_chem() {
		return spectro_chem;
	}

	public void setSpectro_chem(Integer spectro_chem) {
		this.spectro_chem = spectro_chem;
	}

	public Integer getProduction_shift() {
		return production_shift;
	}

	public void setProduction_shift(Integer production_shift) {
		this.production_shift = production_shift;
	}

	public String getLadle_open_type() {
		return ladle_open_type;
	}

	public void setLadle_open_type(String ladle_open_type) {
		this.ladle_open_type = ladle_open_type;
	}

	public Integer getNo_of_pipes() {
		return no_of_pipes;
	}

	public void setNo_of_pipes(Integer no_of_pipes) {
		this.no_of_pipes = no_of_pipes;
	}			
}
