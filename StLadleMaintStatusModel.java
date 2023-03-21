package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.SteelLadleMasterModel;
import com.smes.masters.model.SteelLadleStatusMasterModel;

@Entity
@Table(name="TRNS_STEEL_LADLE_MAINT_STATUS")
@TableGenerator(name = "STLADLE_STS_MAINT_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "stLadleStsMaintSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class StLadleMaintStatusModel implements Serializable {
	

	public StLadleMaintStatusModel() {
	}
	
	public StLadleMaintStatusModel(StLadleMaintStatusModel obj) {
		obj.stladle_maint_status_id = stladle_maint_status_id;
		obj.stladle_id = stladle_id;
		obj.stladle_status_id = stladle_status_id;
		obj.sr_shift_incharge = sr_shift_incharge;
		obj.shift_incharge = shift_incharge;
		obj.ladle_manager = ladle_manager;
		obj.record_status = record_status;
		obj.created_by = created_by;
		obj.created_date_time = created_date_time;
		obj.updated_by = updated_by;
		obj.updated_date_time = updated_date_time;
		obj.lining_type = lining_type;
		obj.lining_supplier = lining_supplier;
		obj.record_version = record_version;
		obj.lkpLiningType = lkpLiningType;
		obj.lkpLiningSupp = lkpLiningSupp;
		obj.stLdlMstrModel = stLdlMstrModel;
		obj.stLdlStsMstrModel = stLdlStsMstrModel;
		obj.stLdlPartMaintLog = stLdlPartMaintLog;
		obj.stLdlTrackModel = stLdlTrackModel;
		obj.part_id = part_id;
		obj.part_supplier = part_supplier;
		obj.burner_no = burner_no;
		obj.remarks = remarks;
		obj.steel_ladle_status = steel_ladle_status;
		obj.trans_date = trans_date;
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "STLADLE_STS_MAINT_SEQ_GEN")
	@Column(name="STLADLE_MAINT_STATUS_ID", unique=true, nullable=false)
	private Integer stladle_maint_status_id;

	@Column(name="STLADLE_ID")
	private Integer stladle_id;

	@Column(name="STLADLE_STATUS_ID")
	private Integer stladle_status_id;

	@Column(name="SR_SHIFT_INCHARGE") 
	private Integer sr_shift_incharge;

	@Column(name="SHIFT_INCHARGE")
	private Integer shift_incharge;

	@Column(name="LADLE_MANAGER")
	private Integer ladle_manager;

	@Column(name="RECORD_STATUS")
	private Integer record_status;

	@Column(name="CREATED_BY") 
	private Integer created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME")
	private Date created_date_time;

	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME") 
	private Date updated_date_time;

	@Column(name="LINING_TYPE")
	private Integer lining_type;
	
	@Column(name="LINING_SUPPLIER")
	private Integer lining_supplier;

	@Version
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRANS_DATE") 
	private Date trans_date;

	@ManyToOne(optional=true)
	@JoinColumn(name="LINING_TYPE",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpLiningType;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="LINING_SUPPLIER",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpLiningSupp;
	
	/*@ManyToOne(optional=true)
	@JoinColumn(name="PART_SUPPLIER",referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lkpSupplier;*/
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STLADLE_ID",referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleMasterModel stLdlMstrModel;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STLADLE_STATUS_ID",referencedColumnName="STATUS_ID",insertable=false,updatable=false)
	private SteelLadleStatusMasterModel stLdlStsMstrModel;
	
	@OneToMany(mappedBy="stLdlMainStstModel",fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<StLadlePartsMaintLogModel> stLdlPartMaintLog;
	
	public Set<StLadlePartsMaintLogModel> getStLdlPartMaintLog() {
		return stLdlPartMaintLog;
	}

	public void setStLdlPartMaintLog(Set<StLadlePartsMaintLogModel> stLdlPartMaintLog) {
		this.stLdlPartMaintLog = stLdlPartMaintLog;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STLADLE_ID",referencedColumnName="STEEL_LADLE_SI_NO",insertable=false,updatable=false)
	private SteelLadleTrackingModel stLdlTrackModel;
	
	public SteelLadleTrackingModel getStLdlTrackModel() {
		return stLdlTrackModel;
	}

	public void setStLdlTrackModel(SteelLadleTrackingModel stLdlTrackModel) {
		this.stLdlTrackModel = stLdlTrackModel;
	}

	@Transient
	private Integer part_id;	
	@Transient
	private Integer part_supplier;
	@Transient
	private Integer burner_no;
	@Transient
	private String remarks;
	
	@Transient
	private String steel_ladle_status;
	
	public String getSteel_ladle_status() {
		return steel_ladle_status;
	}

	public void setSteel_ladle_status(String steel_ladle_status) {
		this.steel_ladle_status = steel_ladle_status;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public void setPart_id(Integer part_id) {
		this.part_id = part_id;
	}

	public Integer getPart_supplier() {
		return part_supplier;
	}

	public void setPart_supplier(Integer part_supplier) {
		this.part_supplier = part_supplier;
	}

	public Integer getBurner_no() {
		return burner_no;
	}

	public void setBurner_no(Integer burner_no) {
		this.burner_no = burner_no;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/*public void add(StLadlePartsMaintLogModel stLdlPartLog) {
		if(stLdlPartMaintLog == null) stLdlPartMaintLog = new HashSet<StLadlePartsMaintLogModel>();
		stLdlPartLog.setStLdlMainStstModel(this);
		stLdlPartMaintLog.add(stLdlPartLog);
    }*/
	
	public Integer getStladle_maint_status_id() {
		return stladle_maint_status_id;
	}

	public void setStladle_maint_status_id(Integer stladle_maint_status_id) {
		this.stladle_maint_status_id = stladle_maint_status_id;
	}

	public Integer getStladle_id() {
		return stladle_id;
	}

	public void setStladle_id(Integer stladle_id) {
		this.stladle_id = stladle_id;
	}

	public Integer getStladle_status_id() {
		return stladle_status_id;
	}

	public void setStladle_status_id(Integer stladle_status_id) {
		this.stladle_status_id = stladle_status_id;
	}

	public Integer getSr_shift_incharge() {
		return sr_shift_incharge;
	}

	public void setSr_shift_incharge(Integer sr_shift_incharge) {
		this.sr_shift_incharge = sr_shift_incharge;
	}

	public Integer getShift_incharge() {
		return shift_incharge;
	}

	public void setShift_incharge(Integer shift_incharge) {
		this.shift_incharge = shift_incharge;
	}

	public Integer getLadle_manager() {
		return ladle_manager;
	}

	public void setLadle_manager(Integer ladle_manager) {
		this.ladle_manager = ladle_manager;
	}

	public Integer getRecord_status() {
		return record_status;
	}

	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
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

	public Integer getLining_type() {
		return lining_type;
	}

	public void setLining_type(Integer lining_type) {
		this.lining_type = lining_type;
	}

	public Integer getLining_supplier() {
		return lining_supplier;
	}

	public void setLining_supplier(Integer lining_supplier) {
		this.lining_supplier = lining_supplier;
	}

	public Integer getRecord_version() {
		return record_version;
	}

	public void setRecord_version(Integer record_version) {
		this.record_version = record_version;
	}

	public LookupMasterModel getLkpLiningType() {
		return lkpLiningType;
	}

	public void setLkpLiningType(LookupMasterModel lkpLiningType) {
		this.lkpLiningType = lkpLiningType;
	}

	public LookupMasterModel getLkpLiningSupp() {
		return lkpLiningSupp;
	}

	public void setLkpLiningSupp(LookupMasterModel lkpLiningSupp) {
		this.lkpLiningSupp = lkpLiningSupp;
	}

	/*public LookupMasterModel getLkpSupplier() {
		return lkpSupplier;
	}

	public void setLkpSupplier(LookupMasterModel lkpSupplier) {
		this.lkpSupplier = lkpSupplier;
	}*/

	public SteelLadleMasterModel getStLdlMstrModel() {
		return stLdlMstrModel;
	}

	public void setStLdlMstrModel(SteelLadleMasterModel stLdlMstrModel) {
		this.stLdlMstrModel = stLdlMstrModel;
	}

	public SteelLadleStatusMasterModel getStLdlStsMstrModel() {
		return stLdlStsMstrModel;
	}

	public void setStLdlStsMstrModel(SteelLadleStatusMasterModel stLdlStsMstrModel) {
		this.stLdlStsMstrModel = stLdlStsMstrModel;
	}

	public Date getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}
	
}
