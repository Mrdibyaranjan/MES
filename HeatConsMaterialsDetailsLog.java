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
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.MtrlProcessConsumableMstrModel;

@Entity
@Table(name = "TRNS_EOF_HEAT_CONS_MAT_H")
@TableGenerator(name = "MTRL_CONS_H_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "MtrlConsHistSeqId", allocationSize = 1)
@DynamicUpdate(value = true)
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class HeatConsMaterialsDetailsLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MTRL_CONS_H_SEQ_GEN")
	@Column(name = "MTR_CONS_H_SI_NO")
	private Integer mtr_cons_log_si_no;
	
	@Column(name = "MTR_CONS_SI_NO", nullable = false)
	private Integer mtr_cons_si_no;

	@Column(name = "TRNS_EOF_SI_NO", nullable = false)
	private Integer trns_eof_si_no;

	@Column(name = "MATERIAL_ID", nullable = false)
	private Integer material_id;

	@Column(name = "QTY", nullable = false)
	private Double qty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONSUMPTION_DATE")
	private Date consumption_date;

	@Column(name = "CREATED_BY", updatable = false)
	private Integer createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE_TIME", updatable = false)
	private Date createdDateTime;

	@Column(name = "UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE_TIME")
	private Date updatedDateTime;

	@Column(name = "SAP_MATL_ID")
	private String sap_matl_id;

	@Column(name = "VALUATION_TYPE")
	private String valuation_type;

	@Column(name = "CREATED_BY_H", updatable = false)
	private Integer createdHistBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE_H", updatable = false)
	private Date createdHistDateTime;
	
	public Integer getMtr_cons_si_no() {
		return mtr_cons_si_no;
	}

	public void setMtr_cons_si_no(Integer mtr_cons_si_no) {
		this.mtr_cons_si_no = mtr_cons_si_no;
	}

	public Integer getTrns_eof_si_no() {
		return trns_eof_si_no;
	}

	public void setTrns_eof_si_no(Integer trns_eof_si_no) {
		this.trns_eof_si_no = trns_eof_si_no;
	}

	public Integer getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(Integer material_id) {
		this.material_id = material_id;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Date getConsumption_date() {
		return consumption_date;
	}

	public void setConsumption_date(Date consumption_date) {
		this.consumption_date = consumption_date;
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

	public String getSap_matl_id() {
		return sap_matl_id;
	}

	public void setSap_matl_id(String sap_matl_id) {
		this.sap_matl_id = sap_matl_id;
	}

	public String getValuation_type() {
		return valuation_type;
	}

	public void setValuation_type(String valuation_type) {
		this.valuation_type = valuation_type;
	}

	public Integer getMtr_cons_log_si_no() {
		return mtr_cons_log_si_no;
	}

	public void setMtr_cons_log_si_no(Integer mtr_cons_log_si_no) {
		this.mtr_cons_log_si_no = mtr_cons_log_si_no;
	}

	public Integer getCreatedHistBy() {
		return createdHistBy;
	}

	public void setCreatedHistBy(Integer createdHistBy) {
		this.createdHistBy = createdHistBy;
	}

	public Date getCreatedHistDateTime() {
		return createdHistDateTime;
	}

	public void setCreatedHistDateTime(Date createdHistDateTime) {
		this.createdHistDateTime = createdHistDateTime;
	}
	
}
