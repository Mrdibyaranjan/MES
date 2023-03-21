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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Table(name="TRNS_SCRAP_BUCKET_DTLS_H")
@TableGenerator(name = "SC_BKT_DTLS_H_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ScBktDtlHistSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class ScrapBucketDetailsLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SC_BKT_DTLS_H_SEQ_GEN")
	@Column(name="SC_BKT_DTLS_H_ID")
	private Integer sc_bkt_dtls_log_id;
	
	@Column(name="SCRAP_BKT_DETAIL_ID",nullable= false)
	private Integer scrap_bkt_detail_id;
	
	@Column(name="SCRAP_BKT_HEADER_ID",nullable= false)
	private Integer scrap_bkt_header_id;
	
	@Column(name="MATERIAL_ID",nullable= false)
	private Integer material_id;
	
	@Column(name="MATERIAL_QTY_ORIG",nullable=false)
	private Double materialQtyOrig;
		
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
	
	@Column(name="CREATED_BY_H",updatable=false)
	private Integer createdHistBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_H",updatable=false)
	private Date createdHistDateTime;
	
	public Integer getScrap_bkt_detail_id() {
		return scrap_bkt_detail_id;
	}

	public void setScrap_bkt_detail_id(Integer scrap_bkt_detail_id) {
		this.scrap_bkt_detail_id = scrap_bkt_detail_id;
	}

	public Integer getScrap_bkt_header_id() {
		return scrap_bkt_header_id;
	}

	public void setScrap_bkt_header_id(Integer scrap_bkt_header_id) {
		this.scrap_bkt_header_id = scrap_bkt_header_id;
	}

	public Integer getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(Integer material_id) {
		this.material_id = material_id;
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

	public Integer getSc_bkt_dtls_log_id() {
		return sc_bkt_dtls_log_id;
	}

	public void setSc_bkt_dtls_log_id(Integer sc_bkt_dtls_log_id) {
		this.sc_bkt_dtls_log_id = sc_bkt_dtls_log_id;
	}

	public Double getMaterialQtyOrig() {
		return materialQtyOrig;
	}

	public void setMaterialQtyOrig(Double materialQtyOrig) {
		this.materialQtyOrig = materialQtyOrig;
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
