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
@Table(name="IFACE_SMS_LP_TB")
@TableGenerator(name = "IFACE_SMS_LP_TB_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL",pkColumnValue = "IfaceSeqId",allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class IfacesmsLpDetailsModel implements Serializable{
	
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "IFACE_SMS_LP_TB_SEQ_GEN")
		@Column(name="MSG_ID")
		private Integer msg_id;
		
		@Column(name="SCH_ID",nullable=true)
		private Integer sch_id;
		
		@Column(name="PLANNED_HEAT_ID")
		private String planned_heat_id;
		
		@Column(name="ACTUAL_HEAT_ID") 
		private String actual_heat_id;
		
		@Column(name="PREV_SCH_ID")
		private Integer prev_sch_id;
		
		@Column(name="PREV_PLANNED_HEAT_ID")
		private String prev_planned_heat_id;
		
		@Column(name="GRADE")
		private String grade;
		
		@Column(name="EVENT_CODE")
		private	String event_code;
		
		@Column(name="INTERFACE_STATUS",nullable=false)
		private Integer interface_status;
		
		@Column(name="ERROR_MSG")
		private String error_msg;
		
		@Column(name="CREATED_BY",nullable=false)
		private Integer createdBy;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="CREATED_DATE",nullable=false)
		private Date created_Date;
		
	    public Date getCreated_Date() {
			return created_Date;
		}

		public void setCreated_Date(Date created_Date) {
			this.created_Date = created_Date;
		}

		@Column(name="MODIFIED_BY")
		private Integer modified_by;
		
		@Column(name="MODIFIED_DATE")
		private Date modified_date;
		
		public Integer getMsg_id() {
			return msg_id;
		}

		public void setMsg_id(Integer msg_id) {
			this.msg_id = msg_id;
		}

	
		public Integer getSch_id() {
			return sch_id;
		}

		public void setSch_id(Integer sch_id) {
			this.sch_id = sch_id;
		}

		public String getPlanned_heat_id() {
			return planned_heat_id;
		}

		public void setPlanned_heat_id(String planned_heat_id) {
			this.planned_heat_id = planned_heat_id;
		}

		public String getActual_heat_id() {
			return actual_heat_id;
		}

		public void setActual_heat_id(String actual_heat_id) {
			this.actual_heat_id = actual_heat_id;
		}

		public Integer getPrev_sch_id() {
			return prev_sch_id;
		}

		public void setPrev_sch_id(Integer prev_sch_id) {
			this.prev_sch_id = prev_sch_id;
		}

		public String getPrev_planned_heat_id() {
			return prev_planned_heat_id;
		}

		public void setPrev_planned_heat_id(String prev_planned_heat_id) {
			this.prev_planned_heat_id = prev_planned_heat_id;
		}

		public String getGrade() {
			return grade;
		}

		public void setGrade(String grade) {
			this.grade = grade;
		}

		public String getEvent_code() {
			return event_code;
		}

		public void setEvent_code(String event_code) {
			this.event_code = event_code;
		}

		public Integer getInterface_status() {
			return interface_status;
		}

		public void setInterface_status(Integer interface_status) {
			this.interface_status = interface_status;
		}

		public String getError_msg() {
			return error_msg;
		}

		public void setError_msg(String error_msg) {
			this.error_msg = error_msg;
		}

		public Integer getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(Integer createdBy) {
			this.createdBy = createdBy;
		}

		

		public Integer getModified_by() {
			return modified_by;
		}

		public void setModified_by(Integer modified_by) {
			this.modified_by = modified_by;
		}

		public Date getModified_date() {
			return modified_date;
		}

		public void setModified_date(Date modified_date) {
			this.modified_date = modified_date;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		

		
}
		