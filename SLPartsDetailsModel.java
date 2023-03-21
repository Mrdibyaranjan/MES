package com.smes.trans.model;

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


@Entity
@Table(name = "SL_PARTS_DTLS")
@TableGenerator(name = "SL_Parts_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "SLPartsMapSeqId", allocationSize = 1)
public class SLPartsDetailsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SL_Parts_SEQ_GEN")
	@Column(name = "PARTS_SI_NO")
	private Integer parts_si_no;

	@Column(name = "PART_ID")
	private Integer part_id;

	@Column(name = "PART_SUPP_ID")
	private Integer part_supp_id;

	@Column(name = "PART_TYPE_ID")
	private Integer part_type_id;

	@Column(name = "PART_LIFE")
	private Integer part_life;

	@Column(name = "PARTS_STATUS")
	private Integer parts_status;

	@Column(name = "CREATED_BY")
	private Integer created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE_TIME")
	private Date created_date_time;

	@Column(name = "UPDATED_BY")
	private Integer updated_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE_TIME")
	private Date updated_date_time;

	@Column(name = "TRNS_STLADLE_TRACK_ID")
	private Integer trns_stladle_track_id;

	@Column(name = "TRNS_STLADLE_LIFE")
	private Integer trns_stladle_life;

	@Column(name = "SHIFT_DTL_ID")
	private Integer shift_dtl_id;

	@Transient
	private String partName;

	@ManyToOne(optional = true)
	@JoinColumn(name = "SHIFT_DTL_ID", referencedColumnName = "TRNS_SHIFT_SI_NO", insertable = false, updatable = false)
	private SLShiftDetailsModel shftDtlsMdl;

	public Integer getParts_si_no() {
		return parts_si_no;
	}

	public void setParts_si_no(Integer parts_si_no) {
		this.parts_si_no = parts_si_no;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public void setPart_id(Integer part_id) {
		this.part_id = part_id;
	}

	public Integer getPart_life() {
		return part_life;
	}

	public void setPart_life(Integer part_life) {
		this.part_life = part_life;
	}

	public Integer getParts_status() {
		return parts_status;
	}

	public void setParts_status(Integer parts_status) {
		this.parts_status = parts_status;
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

	public Integer getTrns_stladle_track_id() {
		return trns_stladle_track_id;
	}

	public void setTrns_stladle_track_id(Integer trns_stladle_track_id) {
		this.trns_stladle_track_id = trns_stladle_track_id;
	}

	public Integer getTrns_stladle_life() {
		return trns_stladle_life;
	}

	public void setTrns_stladle_life(Integer trns_stladle_life) {
		this.trns_stladle_life = trns_stladle_life;
	}

	public Integer getShift_dtl_id() {
		return shift_dtl_id;
	}

	public void setShift_dtl_id(Integer shift_dtl_id) {
		this.shift_dtl_id = shift_dtl_id;
	}

	public Integer getPart_supp_id() {
		return part_supp_id;
	}

	public void setPart_supp_id(Integer part_supp_id) {
		this.part_supp_id = part_supp_id;
	}

	public Integer getPart_type_id() {
		return part_type_id;
	}

	public void setPart_type_id(Integer part_type_id) {
		this.part_type_id = part_type_id;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public SLShiftDetailsModel getShftDtlsMdl() {
		return shftDtlsMdl;
	}

	public void setShftDtlsMdl(SLShiftDetailsModel shftDtlsMdl) {
		this.shftDtlsMdl = shftDtlsMdl;
	}

	
	
/*	@ManyToOne(optional = true)
	@JoinColumn(name = "PART_ID", referencedColumnName = "LOOKUP_ID", insertable = false, updatable = false)
	private LookupMasterModel lkpPartId;

	@ManyToOne(optional = true)
	@JoinColumn(name = "PART_SUPP_ID", referencedColumnName = "LOOKUP_ID", insertable = false, updatable = false)
	private LookupMasterModel lkpSuppId;

	@ManyToOne(optional = true)
	@JoinColumn(name = "PART_TYPE_ID", referencedColumnName = "LOOKUP_ID", insertable = false, updatable = false)
	private LookupMasterModel lkpTypeId;

	public LookupMasterModel getLkpPartId() {
		return lkpPartId;
	}

	public void setLkpPartId(LookupMasterModel lkpPartId) {
		this.lkpPartId = lkpPartId;
	}

	public LookupMasterModel getLkpSuppId() {
		return lkpSuppId;
	}

	public void setLkpSuppId(LookupMasterModel lkpSuppId) {
		this.lkpSuppId = lkpSuppId;
	}

	public LookupMasterModel getLkpTypeId() {
		return lkpTypeId;
	}

	public void setLkpTypeId(LookupMasterModel lkpTypeId) {
		this.lkpTypeId = lkpTypeId;
	}*/

}
