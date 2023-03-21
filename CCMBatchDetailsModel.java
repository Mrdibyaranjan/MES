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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smes.masters.model.LookupMasterModel;

@Entity
@Table(name="TRNS_CCM_BATCH_DETAILS")
@TableGenerator(name = "TRNS_CCM_BATCH_DETAILS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmBatchDetSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CCMBatchDetailsModel  implements Serializable{

	private static final long serialVersionUID = 5190634593497351115L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_BATCH_DETAILS_SEQ_GEN")
	@Column(name="BATCH_TRNS_ID")
	private Integer batch_trns_id;
	
	@Column(name="TRNS_SL_NO")
	private Integer trns_sl_no;
	
	@Column(name="BATCHNO")
	private String batch_no;
	
	@Column(name="PRODUCT")
	private Integer product;
	
	@Column(name="PLND_LENGTH")
	private Float plnd_len;
	
	@Column(name="ACT_LENGTH")
	private Float act_len;
	
	@Column(name="ACT_BATCH_WGT")
	private Float act_batch_wgt;
	
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

	@Column(name="HEAT_PLAN_ID")
	private Integer heat_plan_id;
	
	@Column(name="HEAT_PLAN_LINE_NO")
	private Integer heat_plan_line_id;//PROD_DATE
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PROD_DATE")
	private Date prod_date;
	
	@Column(name="IS_SEND_SAP")
	private String is_send_sap;
	
	@Column(name="SAP_UD_CODE")
	private Integer sap_ud_code;
	
	@Transient
	private String section;
	
	@Transient
	private Integer strandNo;
	
	@Transient
	private Double Steel_wgt ;
	
	@Transient
	private Double Tot_batch_wgt ;
	
	public Double getSteel_wgt() {
		return Steel_wgt;
	}

	public void setSteel_wgt(Double steel_wgt) {
		Steel_wgt = steel_wgt;
	}

	public Double getTot_batch_wgt() {
		return Tot_batch_wgt;
	}

	public void setTot_batch_wgt(Double tot_batch_wgt) {
		Tot_batch_wgt = tot_batch_wgt;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name="PRODUCT" ,referencedColumnName="PRD_TRNS_ID",insertable=false,updatable=false)
	private CCMProductDetailsModel productMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SAP_UD_CODE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel sapudcodeLkpModel;
	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public Integer getSap_ud_code() {
		return sap_ud_code;
	}

	public void setSap_ud_code(Integer sap_ud_code) {
		this.sap_ud_code = sap_ud_code;
	}

	public LookupMasterModel getSapudcodeLkpModel() {
		return sapudcodeLkpModel;
	}

	public void setSapudcodeLkpModel(LookupMasterModel sapudcodeLkpModel) {
		this.sapudcodeLkpModel = sapudcodeLkpModel;
	}

	public Integer getStrandNo() {
		return strandNo;
	}

	public void setStrandNo(Integer strandNo) {
		this.strandNo = strandNo;
	}

	public Integer getBatch_trns_id() {
		return batch_trns_id;
	}

	public void setBatch_trns_id(Integer batch_trns_id) {
		this.batch_trns_id = batch_trns_id;
	}

	public Integer getTrns_sl_no() {
		return trns_sl_no;
	}

	public void setTrns_sl_no(Integer trns_sl_no) {
		this.trns_sl_no = trns_sl_no;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Float getPlnd_len() {
		return plnd_len;
	}

	public void setPlnd_len(Float plnd_len) {
		this.plnd_len = plnd_len;
	}

	public Float getAct_len() {
		return act_len;
	}

	public void setAct_len(Float act_len) {
		this.act_len = act_len;
	}

	public Float getAct_batch_wgt() {
		return act_batch_wgt;
	}

	public void setAct_batch_wgt(Float act_batch_wgt) {
		this.act_batch_wgt = act_batch_wgt;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
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
	
	public Date getProd_date() {
		return prod_date;
	}

	public void setProd_date(Date prod_date) {
		this.prod_date = prod_date;
	}

	public String getIs_send_sap() {
		return is_send_sap;
	}

	public void setIs_send_sap(String is_send_sap) {
		this.is_send_sap = is_send_sap;
	}

	public CCMProductDetailsModel getProductMdl() {
		return productMdl;
	}

	public void setProductMdl(CCMProductDetailsModel productMdl) {
		this.productMdl = productMdl;
	}
}
