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
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import com.smes.masters.model.EventMasterModel;

@Entity
@Table(name="TRNS_HEAT_PROCESS_EVENTS")
@TableGenerator(name = "HEAT_PROCESS_EVENTS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "heatProcessEvntSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class HeatProcessEventDetails implements Serializable{

	
	   private static final long serialVersionUID = 1L;
	   
	    @Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_PROCESS_EVENTS_SEQ_GEN")
		@Column(name="HEAT_PROC_EVENT_ID")
		private Integer heat_proc_event_id;
	    
	    @Column(name="HEAT_ID",nullable=false,updatable=false)
		private String heat_id;
	    
	    @Column(name="HEAT_COUNTER",nullable=false,updatable=false)
		private Integer heat_counter;
	    
	    @Column(name="EVENT_ID",nullable=false,updatable=false)
		private Integer event_id;
	    
	    @Temporal(TemporalType.TIMESTAMP)
		@Column(name="EVENT_DATE_TIME")
		private Date event_date_time;	
	    
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
		
		@Version
		@Column(name="RECORD_VERSION")
		private Integer version;
		
		
		@Transient
		private String eventName,event_date;
		
		@Transient
		private String grid_arry;
		
		@Transient
		private Float event_unit_seq;

		@ManyToOne(optional=true)
		@JoinColumn(name="EVENT_ID" ,referencedColumnName="EVENT_SI_NO",insertable=false,updatable=false)
		private EventMasterModel eventMstrMdl;
		
		public Integer getHeat_proc_event_id() {
			return heat_proc_event_id;
		}

		public void setHeat_proc_event_id(Integer heat_proc_event_id) {
			this.heat_proc_event_id = heat_proc_event_id;
		}

		public String getHeat_id() {
			return heat_id;
		}

		public void setHeat_id(String heat_id) {
			this.heat_id = heat_id;
		}

		public Integer getHeat_counter() {
			return heat_counter;
		}

		public void setHeat_counter(Integer heat_counter) {
			this.heat_counter = heat_counter;
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
		
		public String getEventName() {
			return eventName;
		}

		public void setEventName(String eventName) {
			this.eventName = eventName;
		}

		public String getGrid_arry() {
			return grid_arry;
		}

		public void setGrid_arry(String grid_arry) {
			this.grid_arry = grid_arry;
		}

		public String getEvent_date() {
			return event_date;
		}

		public void setEvent_date(String event_date) {
			this.event_date = event_date;
		}

		public EventMasterModel getEventMstrMdl() {
			return eventMstrMdl;
		}

		public void setEventMstrMdl(EventMasterModel eventMstrMdl) {
			this.eventMstrMdl = eventMstrMdl;
		}

		public Float getEvent_unit_seq() {
			return event_unit_seq;
		}

		public void setEvent_unit_seq(Float event_unit_seq) {
			this.event_unit_seq = event_unit_seq;
		}
		
		
		
	    

}
