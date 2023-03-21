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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.MtrlProcessConsumableMstrModel;
import com.smes.masters.model.SubUnitMasterModel;

@Entity
@Table(name = "TRNS_DAYWISE_CONS_MATERIALS")
@TableGenerator(name = "DAYWISE_MTR_CONS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "DaywiseConsMtrlsSeqId", allocationSize = 1)
@DynamicUpdate(value = true)
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class DaywiseMaterialConsModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DAYWISE_MTR_CONS_SEQ_GEN")
	@Column(name = "MTR_CONS_SI_NO")
	private Integer mtr_cons_si_no;

	@Column(name = "SUB_UNIT", nullable = false)
	private Integer sub_unit;

	@Column(name = "MATERIAL_ID", nullable = false)
	private Integer material_id;

	@Column(name = "QTY", nullable = false)
	private Double qty;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CONSUMPTION_DATE")
	private Date consumption_date;

	@Column(name = "RECORD_STATUS", nullable = false)
	private Integer record_status;

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
	
	@Transient
	private Integer updateCounter;

	@ManyToOne(optional = true)
	@JoinColumn(name = "MATERIAL_ID", referencedColumnName = "MATERIAL_ID", insertable = false, updatable = false)
	private MtrlProcessConsumableMstrModel mtrlMstrModel;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "SUB_UNIT", referencedColumnName = "SUB_UNIT_ID", insertable = false, updatable = false)
	private SubUnitMasterModel subUnitMstrodel;

	public Integer getMtr_cons_si_no() {
		return mtr_cons_si_no;
	}

	public void setMtr_cons_si_no(Integer mtr_cons_si_no) {
		this.mtr_cons_si_no = mtr_cons_si_no;
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

	public MtrlProcessConsumableMstrModel getMtrlMstrModel() {
		return mtrlMstrModel;
	}

	public void setMtrlMstrModel(MtrlProcessConsumableMstrModel mtrlMstrModel) {
		this.mtrlMstrModel = mtrlMstrModel;
	}

	public Integer getSub_unit() {
		return sub_unit;
	}

	public void setSub_unit(Integer sub_unit) {
		this.sub_unit = sub_unit;
	}

	public SubUnitMasterModel getSubUnitMstrodel() {
		return subUnitMstrodel;
	}

	public void setSubUnitMstrodel(SubUnitMasterModel subUnitMstrodel) {
		this.subUnitMstrodel = subUnitMstrodel;
	}

	public Integer getUpdateCounter() {
		return updateCounter;
	}

	public void setUpdateCounter(Integer updateCounter) {
		this.updateCounter = updateCounter;
	}
	
	
	
}
