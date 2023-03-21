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

@Entity
@Table(name="TRNS_EOF_HEAT_CONS_SCRAP_HM")
@TableGenerator(name = "HEAT_CONS_SCRAP_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatConsScrapSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class HeatConsScrapMtrlDetails implements Serializable {

	 private static final long serialVersionUID = 1L;
		

		@Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_CONS_SCRAP_SEQ_GEN")
		@Column(name="CONS_SI_NO")
		private Integer cons_si_no;
		
		@Column(name="TRNS_EOF_SI_NO",nullable=false)
		private Integer trns_eof_si_no;
			
		@Column(name="HM_SEQ_NO")
		private Integer hm_seq_no;
		
		@Column(name="QTY",nullable=false)
		private Double qty;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="CONSUMPTION_DATE")
		private Date consumption_date;	
		
		@Column(name="SCRAP_BUCKET_HEADER_ID")
		private Integer scrap_bkt_header_id;
		
		@Column(name="RECORD_STATUS",nullable=false)
		private Integer record_status;		
		
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
		@JoinColumn(name="SCRAP_BUCKET_HEADER_ID" ,referencedColumnName="SCRAP_BKT_HEADER_ID",insertable=false,updatable=false)
		private ScrapBucketHdr scrapBucketHdrModel;

		@ManyToOne(optional=true)
		@JoinColumn(name="TRNS_EOF_SI_NO" ,referencedColumnName="TRNS_SI_NO",insertable=false,updatable=false)
		private EofHeatDetails eofTrnsModel;
		
		@ManyToOne(optional=true)
		@JoinColumn(name="HM_SEQ_NO" ,referencedColumnName="HM_SEQ_NO",insertable=false,updatable=false)
		private HmReceiveBaseDetails hmReceiveModel;
		
	
		@Version
		@Column(name="RECORD_VERSION")
		private Integer version;
		
		@Transient
		private String mtrlName;


		@Transient
		private String scrap_bkt_no;
		
		public String getScrap_bkt_no() {
			return scrap_bkt_no;
		}

		public void setScrap_bkt_no(String scrap_bkt_no) {
			this.scrap_bkt_no = scrap_bkt_no;
		}

		@Transient
		private String uom;
		

		public Integer getCons_si_no() {
			return cons_si_no;
		}

		public void setCons_si_no(Integer cons_si_no) {
			this.cons_si_no = cons_si_no;
		}
		public Integer getTrns_eof_si_no() {
			return trns_eof_si_no;
		}

		public void setTrns_eof_si_no(Integer trns_eof_si_no) {
			this.trns_eof_si_no = trns_eof_si_no;
		}

		public Integer getHm_seq_no() {
			return hm_seq_no;
		}

		public void setHm_seq_no(Integer hm_seq_no) {
			this.hm_seq_no = hm_seq_no;
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

		public Integer getScrap_bkt_header_id() {
			return scrap_bkt_header_id;
		}

		public void setScrap_bkt_header_id(Integer scrap_bkt_header_id) {
			this.scrap_bkt_header_id = scrap_bkt_header_id;
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

		public ScrapBucketHdr getScrapBucketHdrModel() {
			return scrapBucketHdrModel;
		}

		public void setScrapBucketHdrModel(ScrapBucketHdr scrapBucketHdrModel) {
			this.scrapBucketHdrModel = scrapBucketHdrModel;
		}

		
		public EofHeatDetails getEofTrnsModel() {
			return eofTrnsModel;
		}

		public void setEofTrnsModel(EofHeatDetails eofTrnsModel) {
			this.eofTrnsModel = eofTrnsModel;
		}

		public HmReceiveBaseDetails getHmReceiveModel() {
			return hmReceiveModel;
		}

		public void setHmReceiveModel(HmReceiveBaseDetails hmReceiveModel) {
			this.hmReceiveModel = hmReceiveModel;
		}

		public String getMtrlName() {
			return mtrlName;
		}

		public void setMtrlName(String mtrlName) {
			this.mtrlName = mtrlName;
		}

		public String getUom() {
			return uom;
		}

		public void setUom(String uom) {
			this.uom = uom;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}

		
		
		
	
}
