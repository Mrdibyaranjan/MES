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
@Table(name="TRNS_STLADLE_STATUS_HIST")
@TableGenerator(name = "STLADLE_STS_HIST_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "StLadleHistSeqId", allocationSize = 1)
@DynamicUpdate
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class StLadleStatusTrackHistoryModel implements Serializable{
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "STLADLE_STS_HIST_SEQ_GEN")
		@Column(name="STLADLE_STATUS_HIST_ID",unique=true, nullable=false)
		private Integer stladle_status_hist_id;
		
		@Column(name="HIST_ENTRY_TIME")
		private Date hist_entry_time;
		
		@Column(name="TRNS_STLADLE_TRACK_ID")
		private Integer trns_stladle_track_id;
		
		@Column(name="STEEL_LADLE_SI_NO")
		private Integer steel_ladle_si_no;
		
		@Column(name="STEEL_LADLE_LIFE")
		private Integer steel_ladle_life;
		
		@Column(name="FIRST_HEAT")
		private String first_heat;
		
		@Column(name="LAST_HEAT")
		private String last_heat;
		
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

		public Integer getStladle_status_hist_id() {
			return stladle_status_hist_id;
		}

		public void setStladle_status_hist_id(Integer stladle_status_hist_id) {
			this.stladle_status_hist_id = stladle_status_hist_id;
		}

		public Date getHist_entry_time() {
			return hist_entry_time;
		}

		public void setHist_entry_time(Date hist_entry_time) {
			this.hist_entry_time = hist_entry_time;
		}

		public Integer getTrns_stladle_track_id() {
			return trns_stladle_track_id;
		}

		public void setTrns_stladle_track_id(Integer trns_stladle_track_id) {
			this.trns_stladle_track_id = trns_stladle_track_id;
		}

		public Integer getSteel_ladle_si_no() {
			return steel_ladle_si_no;
		}

		public void setSteel_ladle_si_no(Integer steel_ladle_si_no) {
			this.steel_ladle_si_no = steel_ladle_si_no;
		}

		public Integer getSteel_ladle_life() {
			return steel_ladle_life;
		}

		public void setSteel_ladle_life(Integer steel_ladle_life) {
			this.steel_ladle_life = steel_ladle_life;
		}

		public String getFirst_heat() {
			return first_heat;
		}

		public void setFirst_heat(String first_heat) {
			this.first_heat = first_heat;
		}

		public String getLast_heat() {
			return last_heat;
		}

		public void setLast_heat(String last_heat) {
			this.last_heat = last_heat;
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

		
}
