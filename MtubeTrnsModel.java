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

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.MtubeMasterModel;

@Entity
@Table(name="TRNS_CCM_MTUBES")
@TableGenerator(name = "TRNS_CCM_MTUBE_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "MtubeTrnsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)

public class MtubeTrnsModel implements Serializable{
	
private static final long serialVersionUID = 1L;  

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_MTUBE_SEQ_GEN")
	@Column(name="CCM_MTUBE_TRNS_ID",unique=true, nullable=false)
	private Integer ccm_mtube_trns_id;	

	@Column(name="CCM_MTUBE_SL_NO")
	private Integer ccm_mtube_sl_no;	
	
	@Column(name="MTUBE_STATUS")
	private String mtube_status;
		
	@Column(name="MTUBE_LIFE")
	private Integer mtube_life;
	
	@Column(name="JACKET_NO")
	private Integer jacket_no;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ASSEMBLED_DATE")
	private Date assembled_date;
	
	@Column(name="ASSEMBLED_BY")
	private Integer assembled_by;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLEANED_DATE")
	private Date cleaned_date;
	
	@Column(name="CLEANED_BY")
	private Integer cleaned_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;
	
	@Column(name="CREATED_BY")
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Column(name="IS_CLEANED")
	private String is_cleaned;
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="LAST_STRAND_NO")
	private Integer last_strand_no;

	@Transient
	private Integer cleaning_life,total_life,current_life;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="CCM_MTUBE_SL_NO" ,referencedColumnName="CCM_MTUBE_SL_NO",insertable=false,updatable=false)
	private MtubeMasterModel mtubeMasterMdl;
	
//	@ManyToOne(optional=true)
//	@JoinColumn(name="MTUBE_STATUS" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
//	private LookupMasterModel sizeLookupMdl;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="JACKET_NO" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel jcktNoLookupMdl;		

	public Integer getCleaning_life() {
		return cleaning_life;
	}

	public void setCleaning_life(Integer cleaning_life) {
		this.cleaning_life = cleaning_life;
	}

	public Integer getTotal_life() {
		return total_life;
	}

	public void setTotal_life(Integer total_life) {
		this.total_life = total_life;
	}

	public Integer getCcm_mtube_trns_id() {
		return ccm_mtube_trns_id;
	}

	public void setCcm_mtube_trns_id(Integer ccm_mtube_trns_id) {
		this.ccm_mtube_trns_id = ccm_mtube_trns_id;
	}

	public Integer getCcm_mtube_sl_no() {
		return ccm_mtube_sl_no;
	}

	public void setCcm_mtube_sl_no(Integer ccm_mtube_sl_no) {
		this.ccm_mtube_sl_no = ccm_mtube_sl_no;
	}

	public String getMtube_status() {
		return mtube_status;
	}

	public void setMtube_status(String mtube_status) {
		this.mtube_status = mtube_status;
	}

	public Integer getMtube_life() {
		return mtube_life;
	}

	public void setMtube_life(Integer mtube_life) {
		this.mtube_life = mtube_life;
	}

	public Integer getJacket_no() {
		return jacket_no;
	}

	public void setJacket_no(Integer jacket_no) {
		this.jacket_no = jacket_no;
	}

	public Date getAssembled_date() {
		return assembled_date;
	}

	public void setAssembled_date(Date assembled_date) {
		this.assembled_date = assembled_date;
	}

	public Integer getAssembled_by() {
		return assembled_by;
	}

	public void setAssembled_by(Integer assembled_by) {
		this.assembled_by = assembled_by;
	}

	public Date getCleaned_date() {
		return cleaned_date;
	}

	public void setCleaned_date(Date cleaned_date) {
		this.cleaned_date = cleaned_date;
	}

	public Integer getCleaned_by() {
		return cleaned_by;
	}

	public void setCleaned_by(Integer cleaned_by) {
		this.cleaned_by = cleaned_by;
	}

	public Date getCreated_date_time() {
		return created_date_time;
	}

	public void setCreated_date_time(Date created_date_time) {
		this.created_date_time = created_date_time;
	}

	public Integer getCreated_by() {
		return created_by;
	}

	public void setCreated_by(Integer created_by) {
		this.created_by = created_by;
	}

	public Date getUpdated_date_time() {
		return updated_date_time;
	}

	public void setUpdated_date_time(Date updated_date_time) {
		this.updated_date_time = updated_date_time;
	}

	public Integer getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Integer updated_by) {
		this.updated_by = updated_by;
	}

	public String getIs_cleaned() {
		return is_cleaned;
	}

	public void setIs_cleaned(String is_cleaned) {
		this.is_cleaned = is_cleaned;
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

	public MtubeMasterModel getMtubeMasterMdl() {
		return mtubeMasterMdl;
	}

	public void setMtubeMasterMdl(MtubeMasterModel mtubeMasterMdl) {
		this.mtubeMasterMdl = mtubeMasterMdl;
	}

//	public LookupMasterModel getSizeLookupMdl() {
//		return sizeLookupMdl;
//	}
//
//	public void setSizeLookupMdl(LookupMasterModel sizeLookupMdl) {
//		this.sizeLookupMdl = sizeLookupMdl;
//	}

	public LookupMasterModel getJcktNoLookupMdl() {
		return jcktNoLookupMdl;
	}

	public void setJcktNoLookupMdl(LookupMasterModel jcktNoLookupMdl) {
		this.jcktNoLookupMdl = jcktNoLookupMdl;
	}

	public Integer getLast_strand_no() {
		return last_strand_no;
	}

	public void setLast_strand_no(Integer last_strand_no) {
		this.last_strand_no = last_strand_no;
	}

	public Integer getCurrent_life() {
		return current_life;
	}

	public void setCurrent_life(Integer current_life) {
		this.current_life = current_life;
	}

	
	
	
	
}