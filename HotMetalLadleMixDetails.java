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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="TRNS_HM_LADLE_MIX_MAPPING")
@TableGenerator(name = "TRNS_HM_LADLE_MIX_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HmMixSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class HotMetalLadleMixDetails implements Serializable{

	 private static final long serialVersionUID = 1L;
	   
	    @Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_HM_LADLE_MIX_SEQ_GEN")
		@Column(name="HM_LADLE_MIX_SI_NO")
		private Integer hm_mix_sino;
	    
	    @Column(name="CAST_NO",nullable=false,updatable=false)
		private String cast_no;
	    
	    @Column(name="HM_RECV_ID",nullable=false,updatable=false)
		private Integer hm_recv_id;
	    
	    @Column(name="HM_LADLE_NET_WT",nullable=false,updatable=false)
		private Double hm_ladle_wt;
	    
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

		public Integer getHm_mix_sino() {
			return hm_mix_sino;
		}

		public void setHm_mix_sino(Integer hm_mix_sino) {
			this.hm_mix_sino = hm_mix_sino;
		}

		public String getCast_no() {
			return cast_no;
		}

		public void setCast_no(String cast_no) {
			this.cast_no = cast_no;
		}

		public Integer getHm_recv_id() {
			return hm_recv_id;
		}

		public void setHm_recv_id(Integer hm_recv_id) {
			this.hm_recv_id = hm_recv_id;
		}

		public Double getHm_ladle_wt() {
			return hm_ladle_wt;
		}

		public void setHm_ladle_wt(Double hm_ladle_wt) {
			this.hm_ladle_wt = hm_ladle_wt;
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

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}
	
		
}
