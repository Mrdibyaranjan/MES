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
@Table(name="TRNS_CCM_SEQ_GROUP_DTLS")
@TableGenerator(name = "TRNS_CCM_SEQ_GROUP_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmSeqGroupDtlsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class CCMSeqGroupDetails  implements Serializable{

	private static final long serialVersionUID = -2721052854635227401L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_CCM_SEQ_GROUP_DTLS_SEQ_GEN")
	@Column(name="SEQ_GRP_DTLS_SL_NO")
	private Integer seq_group_dtls_sl_no;
	
	@Column(name="SEQ_GROUP_NO")
	private Integer seq_group_no;
	
	@Column(name="SUB_UNIT_ID")
	private Integer sub_unit_id;
	
	@Column(name="SEQUENCE_STATUS")
	private Integer seq_status;
	
	@Column(name="DELAY_ENTRY")
	private String delay_entry;
	
	@Column(name="CREATED_BY" ,updatable=false)
	private Integer created_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="SEQ_CLOSE_DATE_TIME")
	private Date seq_close_date_time;
	
	@Column(name="SEQ_CLOSE_BY")
	private Integer seq_close_by;
	
	@Column(name="UPDATED_BY")
	private Integer updated_by;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;

	public Integer getSeq_group_dtls_sl_no() {
		return seq_group_dtls_sl_no;
	}

	public String getDelay_entry() {
		return delay_entry;
	}

	public void setDelay_entry(String delay_entry) {
		this.delay_entry = delay_entry;
	}

	public void setSeq_group_dtls_sl_no(Integer seq_group_dtls_sl_no) {
		this.seq_group_dtls_sl_no = seq_group_dtls_sl_no;
	}

	public Integer getSeq_group_no() {
		return seq_group_no;
	}

	public void setSeq_group_no(Integer seq_group_no) {
		this.seq_group_no = seq_group_no;
	}

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
	}

	public Integer getSeq_status() {
		return seq_status;
	}

	public void setSeq_status(Integer seq_status) {
		this.seq_status = seq_status;
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

	public Date getSeq_close_date_time() {
		return seq_close_date_time;
	}

	public void setSeq_close_date_time(Date seq_close_date_time) {
		this.seq_close_date_time = seq_close_date_time;
	}

	public Integer getSeq_close_by() {
		return seq_close_by;
	}

	public void setSeq_close_by(Integer seq_close_by) {
		this.seq_close_by = seq_close_by;
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
	
	
}
