package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.ScrapBucketStatusModel;
import com.smes.masters.model.ScrapPatternMasterModel;


@Entity
@Table(name="TRNS_SCRAP_BUCKET_HDR")
@TableGenerator(name = "SCRAP_BKT_HDR_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ScrapBktHDRSeqId", allocationSize = 1)
@DynamicUpdate(value=true)

public class ScrapBucketHdr implements Serializable{

	
   private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SCRAP_BKT_HDR_SEQ_GEN")
	@Column(name="SCRAP_BKT_HEADER_ID")
	private Integer scrap_bkt_header_id;
	
	@Column(name="SCRAP_BKT_ID",nullable=false,updatable=false)
	private Integer scrap_bkt_id;
	
	@Column(name="SCRAP_BKT_QTY",nullable=false)
	private Double scrap_bkt_qty;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SCRAP_PROD_DATE")
	private Date scrap_prod_date;	
	
	@Column(name="SCRAP_BKT_LOAD_STATUS",nullable=false)
	private Integer scrap_bkt_load_status;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
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
	
	@Column(name="SCRAP_PATTERN_ID")
	private Integer scrap_pattern_id;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SCRAP_BKT_ID" ,referencedColumnName="SCRAP_BUCKET_ID",insertable=false,updatable=false)
	private ScrapBucketStatusModel scrapBucketStatusModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SCRAP_PATTERN_ID" ,referencedColumnName="SCRAP_PATTERN_ID",insertable=false,updatable=false)
	private ScrapPatternMasterModel scrapPatternModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="SCRAP_BKT_LOAD_STATUS" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel scrapBucketLoadedStatusModel;

	@Transient
	private String scrap_prod_dt;
	
	@Transient
	private String scrap_bckt_no;
	
	@Transient
	private String scrap_patrn_no;
	
	@Transient
	List<ScrapBucketDetails> scrapBktDtls;
		
	public List<ScrapBucketDetails> getScrapBktDtls() {
		return scrapBktDtls;
	}

	public void setScrapBktDtls(List<ScrapBucketDetails> scrapBktDtls) {
		this.scrapBktDtls = scrapBktDtls;
	}

	public Integer getScrap_bkt_header_id() {
		return scrap_bkt_header_id;
	}

	public void setScrap_bkt_header_id(Integer scrap_bkt_header_id) {
		this.scrap_bkt_header_id = scrap_bkt_header_id;
	}

	public Integer getScrap_bkt_id() {
		return scrap_bkt_id;
	}

	public void setScrap_bkt_id(Integer scrap_bkt_id) {
		this.scrap_bkt_id = scrap_bkt_id;
	}

	public Double getScrap_bkt_qty() {
		return scrap_bkt_qty;
	}	

	public void setScrap_bkt_qty(Double scrap_bkt_qty) {
		this.scrap_bkt_qty = scrap_bkt_qty;
	}	
	public Date getScrap_prod_date() {
		return scrap_prod_date;
	}

	public void setScrap_prod_date(Date scrap_prod_date) {
		this.scrap_prod_date = scrap_prod_date;
	}

	
	public Integer getScrap_bkt_load_status() {
		return scrap_bkt_load_status;
	}

	public void setScrap_bkt_load_status(Integer scrap_bkt_load_status) {
		this.scrap_bkt_load_status = scrap_bkt_load_status;
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

	public Integer getScrap_pattern_id() {
		return scrap_pattern_id;
	}

	public void setScrap_pattern_id(Integer scrap_pattern_id) {
		this.scrap_pattern_id = scrap_pattern_id;
	}

	public ScrapBucketStatusModel getScrapBucketStatusModel() {
		return scrapBucketStatusModel;
	}

	public void setScrapBucketStatusModel(
			ScrapBucketStatusModel scrapBucketStatusModel) {
		this.scrapBucketStatusModel = scrapBucketStatusModel;
	}

	public ScrapPatternMasterModel getScrapPatternModel() {
		return scrapPatternModel;
	}

	public void setScrapPatternModel(ScrapPatternMasterModel scrapPatternModel) {
		this.scrapPatternModel = scrapPatternModel;
	}

	public LookupMasterModel getScrapBucketLoadedStatusModel() {
		return scrapBucketLoadedStatusModel;
	}

	public void setScrapBucketLoadedStatusModel(
			LookupMasterModel scrapBucketLoadedStatusModel) {
		this.scrapBucketLoadedStatusModel = scrapBucketLoadedStatusModel;
	}

	public String getScrap_prod_dt() {
		return scrap_prod_dt;
	}

	public void setScrap_prod_dt(String scrap_prod_dt) {
		this.scrap_prod_dt = scrap_prod_dt;
	}

	public String getScrap_bckt_no() {
		return scrap_bckt_no;
	}

	public void setScrap_bckt_no(String scrap_bckt_no) {
		this.scrap_bckt_no = scrap_bckt_no;
	}

	public String getScrap_patrn_no() {
		return scrap_patrn_no;
	}

	public void setScrap_patrn_no(String scrap_patrn_no) {
		this.scrap_patrn_no = scrap_patrn_no;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	
	
}
