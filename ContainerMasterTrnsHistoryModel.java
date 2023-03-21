package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.smes.masters.model.ContainerMasterModel;

@Entity
@Table(name = "TRNS_LOG_EOF_CONTAINER")
@TableGenerator(name = "TRNS_CONTAINER_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ContainerTrnsHistorySeqId", allocationSize = 1)
public class ContainerMasterTrnsHistoryModel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CONTAINER_SEQ_GEN")
	@Column(name = "TRNS_SI_NO", unique = true, nullable = false)
	private Integer trns_si_no;

	@Column(name = "CONTAINER_ID")
	private Integer container_id;

	@Column(name = "CONTAINER_NAME")
	private String container_name;

	@Column(name = "SUPPLIER")
	private String supplier;

	@Column(name = "CONTAINER_LIFE")
	private Integer container_life;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updated_date;

	@Column(name = "UPDATED_BY")
	private Integer updated_by;

	@Column(name = "FROM_STATUS")
	private String from_status;

	@Column(name = "TO_STATUS")
	private String to_status;

	@Column(name = "UNIT")
	private Integer unit;

	@Column(name = "RECORD_STATUS")
	private Integer record_status;
	
	@Transient
	private ContainerMasterModel ContainerMstrMdl;

	public Integer getTrns_si_no() {
		return trns_si_no;
	}

	public void setTrns_si_no(Integer trns_si_no) {
		this.trns_si_no = trns_si_no;
	}

	public Integer getContainer_id() {
		return container_id;
	}

	public void setContainer_id(Integer container_id) {
		this.container_id = container_id;
	}

	public String getContainer_name() {
		return container_name;
	}

	public void setContainer_name(String container_name) {
		this.container_name = container_name;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String string) {
		this.supplier = string;
	}

	public Integer getContainer_life() {
		return container_life;
	}

	public void setContainer_life(Integer container_life) {
		this.container_life = container_life;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	public String getFrom_status() {
		return from_status;
	}

	public void setFrom_status(String from_status) {
		this.from_status = from_status;
	}

	public String getTo_status() {
		return to_status;
	}

	public void setTo_status(String to_status) {
		this.to_status = to_status;
	}

	public Integer getUnit() {
		return unit;
	}

	public void setUnit(Integer unit) {
		this.unit = unit;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	public ContainerMasterModel getContainerMstrMdl() {
		return ContainerMstrMdl;
	}

	public void setContainerMstrMdl(ContainerMasterModel containerMstrMdl) {
		ContainerMstrMdl = containerMstrMdl;
	}

	@Override
	public String toString() {
		return "ContainerMasterTrnsHistoryModel [trns_si_no=" + trns_si_no + ", container_id=" + container_id
				+ ", container_name=" + container_name + ", supplier_id=" + supplier + ", container_life="
				+ container_life + ", updated_date=" + updated_date + ", updated_by=" + updated_by + ", from_status="
				+ from_status + ", to_status=" + to_status + ", unit=" + unit + ", record_status=" + record_status
				+ "]";
	}

}
