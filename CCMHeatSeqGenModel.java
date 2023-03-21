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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Entity
@Table(name="TRNS_CCM_SEQ_NO_TRACK_STATUS")
@TableGenerator(name = "TRNS_CCM_SEQ_NO_TRACK_STATUS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmSeqNoSeq", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CCMHeatSeqGenModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_SEQ_NO_TRACK_STATUS_SEQ_GEN")
	@Column(name="SEQ_SL_NO")
	private Integer seq_sl_no;
//	@Column(name="TRNS_HEAT_ID")
//	private String heat_no;
	
	@Column(name="P_SEQ")
	private Integer primary_seq;
	
	@Column(name="F_SEQ")
	private Integer fly_seq;
	
	@Column(name="SEQ_NO")
	private String seq_no;
	
	@Column(name="IS_PRIMARY")
	private Integer is_primary;
	
	@Column(name="IS_FLYING")
	private Integer is_flying;
	
	@Column(name="TUNDISH_CHANGE")
	private String tundish_change;
	
	@Column(name="SEQ_BREAK")
	private String seq_brk;
	
	@Column(name="CREATED_BY" ,updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE",updatable=false)
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	@Temporal(TemporalType.TIMESTAMP)
	
	@Column(name="UPDATED_DATE")
	private Date updated_date_time;//
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	@Column(name="SEQ_TYPE")
	private String seq_type;
	
	@Column(name="F_SEQ_SI")
	private Integer fly_seq_si;
	
	@Column(name="RECORD_VERSION")
	private Integer record_version;
	
	@Column(name="UNIT_ID")
	private Integer unit_id;
	
	@Column(name="SEQ_GROUP_NO")
	private Integer last_fly_cast_no;
	
	@Column(name="TUNDISH_NO")
	private Integer tundish_no;
	
	@Column(name="TUNDISH_CAR_NO")
	private Integer tundish_car_no;
	
	@Column(name="SHROUD_MAKE")
	private Integer shroud_make;
		
//	@Column(name="LAST_PRIMARY_CAST_NO")
//	private Integer last_primary_cast_no;
	
	@Transient
	private Integer seq_group_status;
	
	public Integer getSeq_group_status() {
		return seq_group_status;
	}

	public void setSeq_group_status(Integer seq_group_status) {
		this.seq_group_status = seq_group_status;
	}

	public Integer getShroud_make() {
		return shroud_make;
	}

	public void setShroud_make(Integer shroud_make) {
		this.shroud_make = shroud_make;
	}

	public Integer getTundish_no() {
		return tundish_no;
	}

	public void setTundish_no(Integer tundish_no) {
		this.tundish_no = tundish_no;
	}

	public Integer getTundish_car_no() {
		return tundish_car_no;
	}

	public void setTundish_car_no(Integer tundish_car_no) {
		this.tundish_car_no = tundish_car_no;
	}

	public Integer getSeq_sl_no() {
		return seq_sl_no;
	}

	public void setSeq_sl_no(Integer seq_sl_no) {
		this.seq_sl_no = seq_sl_no;
	}


	public Integer getPrimary_seq() {
		return primary_seq;
	}

	public void setPrimary_seq(Integer primary_seq) {
		this.primary_seq = primary_seq;
	}

	public Integer getFly_seq() {
		return fly_seq;
	}

	public void setFly_seq(Integer fly_seq) {
		this.fly_seq = fly_seq;
	}

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}

	public Integer getIs_primary() {
		return is_primary;
	}

	public void setIs_primary(Integer is_primary) {
		this.is_primary = is_primary;
	}

	public Integer getIs_flying() {
		return is_flying;
	}

	public void setIs_flying(Integer is_flying) {
		this.is_flying = is_flying;
	}

	public String getTundish_change() {
		return tundish_change;
	}

	public void setTundish_change(String tundish_change) {
		this.tundish_change = tundish_change;
	}

	public String getSeq_brk() {
		return seq_brk;
	}

	public void setSeq_brk(String seq_brk) {
		this.seq_brk = seq_brk;
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

//	public String getHeat_no() {
//		return heat_no;
//	}
//
//	public void setHeat_no(String heat_no) {
//		this.heat_no = heat_no;
//	}

	public String getSeq_type() {
		return seq_type;
	}

	public void setSeq_type(String seq_type) {
		this.seq_type = seq_type;
	}

	public Integer getFly_seq_si() {
		return fly_seq_si;
	}

	public void setFly_seq_si(Integer fly_seq_si) {
		this.fly_seq_si = fly_seq_si;
	}
	

	public Integer getUnit_id() {
		return unit_id;
	}

	public void setUnit_id(Integer unit_id) {
		this.unit_id = unit_id;
	}
	

	public Integer getLast_fly_cast_no() {
		return last_fly_cast_no;
	}

	public void setLast_fly_cast_no(Integer last_fly_cast_no) {
		this.last_fly_cast_no = last_fly_cast_no;
	}
//
//	public Integer getLast_primary_cast_no() {
//		return last_primary_cast_no;
//	}
//
//	public void setLast_primary_cast_no(Integer last_primary_cast_no) {
//		this.last_primary_cast_no = last_primary_cast_no;
//	}

	@Override
	public String toString() {
		return "CCMHeatSeqGenModel [seq_sl_no=" + seq_sl_no + ", primary_seq=" + primary_seq + ", fly_seq=" + fly_seq
				+ ", seq_no=" + seq_no + ", is_primary=" + is_primary + ", is_flying=" + is_flying + ", tundish_change="
				+ tundish_change + ", seq_brk=" + seq_brk + ", created_by=" + created_by + ", created_date_time="
				+ created_date_time + ", updated_by=" + updated_by + ", updated_date_time=" + updated_date_time
				+ ", record_status=" + record_status + ", seq_type=" + seq_type + ", fly_seq_si=" + fly_seq_si
				+ ", record_version=" + record_version + ", unit_id=" + unit_id + "]";
	}


	
	
	

}
