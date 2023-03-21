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

import com.smes.masters.model.MtrlProcessConsumableMstrModel;


@Entity
@Table(name="TRNS_SCRAP_BUCKET_DTLS")
@TableGenerator(name = "SCRAP_BKT_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ScrapBktDTLSSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class ScrapBucketDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SCRAP_BKT_DTLS_SEQ_GEN")
	@Column(name="SCRAP_BKT_DETAIL_ID")
	private Integer scrap_bkt_detail_id;
	
	@Column(name="SCRAP_BKT_HEADER_ID",nullable= false)
	private Integer scrap_bkt_header_id;
	
	@Column(name="MATERIAL_ID",nullable= false)
	private Integer material_id;
	
	@Column(name="MATERIAL_QTY",nullable=false)
	private Double material_qty;
	
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
	
	@ManyToOne(optional=true)
	@JoinColumn(name="MATERIAL_ID" ,referencedColumnName="MATERIAL_ID",insertable=false,updatable=false)
	private MtrlProcessConsumableMstrModel mtrlProcessConsumableMstrModel;
	
	@Transient
	private String mtrlName;
	
	@Transient
	private String sapMtlid;
	
	public String getSapMtlid() {
		return sapMtlid;
	}

	public void setSapMtlid(String sapMtlid) {
		this.sapMtlid = sapMtlid;
	}

	@Transient
	private Double matPercent;
	
	@Transient
	private Double bckHeaderQty;
	
	@Transient
	private Integer scrap_pattern_id;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
	}

	@Transient
	private String uom;
	
	@Transient
	private String grid_arry;
	

	
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

	public Double getMaterial_qty() {
		return material_qty;
	}

	public void setMaterial_qty(Double material_qty) {
		this.material_qty = material_qty;
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

	public MtrlProcessConsumableMstrModel getMtrlProcessConsumableMstrModel() {
		return mtrlProcessConsumableMstrModel;
	}

	public void setMtrlProcessConsumableMstrModel(
			MtrlProcessConsumableMstrModel mtrlProcessConsumableMstrModel) {
		this.mtrlProcessConsumableMstrModel = mtrlProcessConsumableMstrModel;
	}

	public String getMtrlName() {
		return mtrlName;
	}

	public void setMtrlName(String mtrlName) {
		this.mtrlName = mtrlName;
	}

	public Double getMatPercent() {
		return matPercent;
	}

	public void setMatPercent(Double matPercent) {
		this.matPercent = matPercent;
	}
	
	public Double getBckHeaderQty() {
		return bckHeaderQty;
	}

	public void setBckHeaderQty(Double bckHeaderQty) {
		this.bckHeaderQty = bckHeaderQty;
	}

	public Integer getScrap_pattern_id() {
		return scrap_pattern_id;
	}

	public void setScrap_pattern_id(Integer scrap_pattern_id) {
		this.scrap_pattern_id = scrap_pattern_id;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getGrid_arry() {
		return grid_arry;
	}

	public void setGrid_arry(String grid_arry) {
		this.grid_arry = grid_arry;
	}

	

}
