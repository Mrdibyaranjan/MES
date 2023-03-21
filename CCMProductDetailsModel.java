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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smes.masters.model.LookupMasterModel;

@Entity
@Table(name="TRNS_CCM_PROD_DETAILS")
@TableGenerator(name = "TRNS_CCM_PROD_DETAILS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmProdDtlsSeq", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CCMProductDetailsModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_PROD_DETAILS_SEQ_GEN")
	@Column(name="PRD_TRNS_ID")
	private Integer prod_trns_id;
	
	@Column(name="TRNS_SL_NO")
	private Integer trns_sl_no;
	
	@Column(name="STRAND_ID")
	private Integer stand_id;
	
	@Column(name="CUT_LENGTH")
	private Float cut_length;
	
	@Column(name="NO_BATCHES")
	private Float no_batches;
	
	@Column(name="CS_SIZE")
	private Integer cs_size;
	
	@Column(name="CS_WGT")
	private Float cs_wgt;
	
	@Column(name="TOT_WGT_BATCHES")
	private Float tot_wgt_batches;
	
	@Column(name="CASTING_SPEED")
	private Float casting_speed;
	
	@Column(name="EMS_CF")
	private Float ems_cf;
	
	@Column(name="MOULD_JACKT_NO")
	private Integer module_jackt_no;
	
	//mould related
	@Column(name="CCM_MTUBE_TRNS_ID")
	private Integer ccm_mtube_trns_id;
	
	@Column(name="MTUBE_LIFE")
	private Integer mtube_life;
	
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
	
	@Column(name="HEAT_PLAN_LINE_NO")
	private Integer heat_plan_line_no;
	
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Transient
	String stand_no,clean,status;
	
	@OneToMany(mappedBy="productMdl",fetch = FetchType.EAGER)
	private List<CCMBatchDetailsModel> ccmBatchDtls;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STRAND_ID" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel standlkpMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="CS_SIZE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel csSizeMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="CCM_MTUBE_TRNS_ID" ,referencedColumnName="CCM_MTUBE_TRNS_ID",insertable=false,updatable=false)
	private MtubeTrnsModel mtubeTrnsMdl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name="TRNS_SL_NO" ,referencedColumnName="TRNS_SL_NO",insertable=false,updatable=false)
	private CCMHeatDetailsModel ccmHeatDtls;

	public List<CCMBatchDetailsModel> getCcmBatchDtls() {
		return ccmBatchDtls;
	}

	public void setCcmBatchDtls(List<CCMBatchDetailsModel> ccmBatchDtls) {
		this.ccmBatchDtls = ccmBatchDtls;
	}

	public Integer getProd_trns_id() {
		return prod_trns_id;
	}

	public Integer getHeat_plan_line_no() {
		return heat_plan_line_no;
	}

	public void setHeat_plan_line_no(Integer heat_plan_line_no) {
		this.heat_plan_line_no = heat_plan_line_no;
	}

	public void setProd_trns_id(Integer prod_trns_id) {
		this.prod_trns_id = prod_trns_id;
	}

	public Integer getTrns_sl_no() {
		return trns_sl_no;
	}

	public void setTrns_sl_no(Integer trns_sl_no) {
		this.trns_sl_no = trns_sl_no;
	}

	public Integer getStand_id() {
		return stand_id;
	}

	public void setStand_id(Integer stand_id) {
		this.stand_id = stand_id;
	}

	public Float getCut_length() {
		return cut_length;
	}

	public void setCut_length(Float cut_length) {
		this.cut_length = cut_length;
	}

	public Float getNo_batches() {
		return no_batches;
	}

	public void setNo_batches(Float no_batches) {
		this.no_batches = no_batches;
	}

	public Integer getCs_size() {
		return cs_size;
	}

	public void setCs_size(Integer cs_size) {
		this.cs_size = cs_size;
	}

	public Float getCs_wgt() {
		return cs_wgt;
	}

	public void setCs_wgt(Float cs_wgt) {
		this.cs_wgt = cs_wgt;
	}

	public Float getTot_wgt_batches() {
		return tot_wgt_batches;
	}

	public void setTot_wgt_batches(Float tot_wgt_batches) {
		this.tot_wgt_batches = tot_wgt_batches;
	}

	public Float getCasting_speed() {
		return casting_speed;
	}

	public void setCasting_speed(Float casting_speed) {
		this.casting_speed = casting_speed;
	}

	public Float getEms_cf() {
		return ems_cf;
	}

	public void setEms_cf(Float ems_cf) {
		this.ems_cf = ems_cf;
	}

	public Integer getModule_jackt_no() {
		return module_jackt_no;
	}

	public void setModule_jackt_no(Integer module_jackt_no) {
		this.module_jackt_no = module_jackt_no;
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
	
	public LookupMasterModel getStandlkpMdl() {
		return standlkpMdl;
	}

	public void setStandlkpMdl(LookupMasterModel standlkpMdl) {
		this.standlkpMdl = standlkpMdl;
	}
	
	public String getStand_no() {
		return stand_no;
	}

	public void setStand_no(String stand_no) {
		this.stand_no = stand_no;
	}
	
	public Integer getCcm_mtube_trns_id() {
		return ccm_mtube_trns_id;
	}

	public void setCcm_mtube_trns_id(Integer ccm_mtube_trns_id) {
		this.ccm_mtube_trns_id = ccm_mtube_trns_id;
	}

	public Integer getMtube_life() {
		return mtube_life;
	}

	public void setMtube_life(Integer mtube_life) {
		this.mtube_life = mtube_life;
	}

	public String getClean() {
		return clean;
	}

	public void setClean(String clean) {
		this.clean = clean;
	}

	public MtubeTrnsModel getMtubeTrnsMdl() {
		return mtubeTrnsMdl;
	}

	public void setMtubeTrnsMdl(MtubeTrnsModel mtubeTrnsMdl) {
		this.mtubeTrnsMdl = mtubeTrnsMdl;
	}

	public LookupMasterModel getCsSizeMdl() {
		return csSizeMdl;
	}

	public void setCsSizeMdl(LookupMasterModel csSizeMdl) {
		this.csSizeMdl = csSizeMdl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CCMHeatDetailsModel getCcmHeatDtls() {
		return ccmHeatDtls;
	}

	public void setCcmHeatDtls(CCMHeatDetailsModel ccmHeatDtls) {
		this.ccmHeatDtls = ccmHeatDtls;
	}
}
