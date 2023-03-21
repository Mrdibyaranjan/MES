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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.LookupMasterModel;

@Entity
@Table(name="TRNS_CCM_SEQ_EVENTS")
@TableGenerator(name = "TRNS_CCM_SEQ_EVENTS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "trnsSeqEventSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class SeqTransactionEvent  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2721052854635227401L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_SEQ_EVENTS_SEQ_GEN")
	@Column(name="EVENT_SL_NO")
	private Integer event_sl_no;
	
	@Column(name="EVENT_ID")
	private Integer event_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EVENT_DATE_TIME")
	private Date event_date_time;
	
	@Column(name="HEAT_ID")
	private Integer heat_id;
	
	@Column(name="GROUP_SEQ_NO")
	private Integer group_seq_no;
	
	@Column(name="CREATED_BY" ,updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;//
	
	@Column(name="RECORD_STATUS")
	private Integer record_status;
	
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;

	public Integer getEvent_sl_no() {
		return event_sl_no;
	}

	public void setEvent_sl_no(Integer event_sl_no) {
		this.event_sl_no = event_sl_no;
	}

	public Integer getEvent_id() {
		return event_id;
	}

	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}


	public Date getEvent_date_time() {
		return event_date_time;
	}

	public void setEvent_date_time(Date event_date_time) {
		this.event_date_time = event_date_time;
	}

	public Integer getHeat_id() {
		return heat_id;
	}

	public void setHeat_id(Integer heat_id) {
		this.heat_id = heat_id;
	}

	public Integer getGroup_seq_no() {
		return group_seq_no;
	}

	public void setGroup_seq_no(Integer group_seq_no) {
		this.group_seq_no = group_seq_no;
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
	
	@ManyToOne(optional=true)  //CCM_SEQ_EVENTS
	@JoinColumn(name="EVENT_ID" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel eventLkpMdl;

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public LookupMasterModel getEventLkpMdl() {
		return eventLkpMdl;
	}

	public void setEventLkpMdl(LookupMasterModel eventLkpMdl) {
		this.eventLkpMdl = eventLkpMdl;
	}
	
	
	
	
}
