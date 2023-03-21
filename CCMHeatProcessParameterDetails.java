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
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smes.masters.model.ProcessParametersMasterModel;

@Entity
@Table(name="TRNS_CCM_HEAT_PROCESS_PARAM")
@TableGenerator(name = "CCM_HEAT_PROCESS_PARAM_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "ccmProcessParamSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class CCMHeatProcessParameterDetails  implements Serializable,Comparable<CCMHeatProcessParameterDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CCM_HEAT_PROCESS_PARAM_SEQ_GEN")
	@Column(name="PROC_PARAM_EVENT_ID")
	private Integer proc_param_event_id;
	
	@Column(name="PARAM_ID")
	private Integer param_id;
	
	@Column(name="STRAND1_VALUE")
	private Double strand1_value;
	
	@Column(name="STRAND2_VALUE")
	private Double strand2_value;
	
	@Column(name="STRAND3_VALUE")
	private Double strand3_value;
	
	@Column(name="STRAND4_VALUE")
	private Double strand4_value;
	
	@Column(name="STRAND5_VALUE")
	private Double strand5_value;
	
	@Column(name="STRAND6_VALUE")
	private Double strand6_value;
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)//pattern = "dd/MM/yyyy HH:mm:ss"
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PROCESS_DATE_TIME")
	private Date process_date_time;
	
	@Column(name="HEAT_ID")
	private String heat_id;

	@Column(name="HEAT_COUNTER")
	private Integer heat_counter;

	
	@Column(name="CREATED_BY",updatable=false)
	private Integer created_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE_TIME",updatable=false)
	private Date created_date_time;	

	@Column(name="UPDATED_BY")
	private Integer updated_by;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE_TIME")
	private Date updated_date_time;

	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="PARAM_ID" ,referencedColumnName="PARAM_SI_NO",insertable=false,updatable=false)
	private ProcessParametersMasterModel procParaMstrMdl;//procParaMstrMdl;

	public Integer getProc_param_event_id() {
		return proc_param_event_id;
	}

	public void setProc_param_event_id(Integer proc_param_event_id) {
		this.proc_param_event_id = proc_param_event_id;
	}

	public Integer getParam_id() {
		return param_id;
	}

	public void setParam_id(Integer param_id) {
		this.param_id = param_id;
	}

	public Double getStrand1_value() {
		return strand1_value;
	}

	public void setStrand1_value(Double strand1_value) {
		this.strand1_value = strand1_value;
	}

	public Double getStrand2_value() {
		return strand2_value;
	}

	public void setStrand2_value(Double strand2_value) {
		this.strand2_value = strand2_value;
	}

	public Double getStrand3_value() {
		return strand3_value;
	}

	public void setStrand3_value(Double strand3_value) {
		this.strand3_value = strand3_value;
	}
	
	public Double getStrand4_value() {
		return strand4_value;
	}

	public void setStrand4_value(Double strand4_value) {
		this.strand4_value = strand4_value;
	}

	public Double getStrand5_value() {
		return strand5_value;
	}

	public void setStrand5_value(Double strand5_value) {
		this.strand5_value = strand5_value;
	}

	public Double getStrand6_value() {
		return strand6_value;
	}

	public void setStrand6_value(Double strand6_value) {
		this.strand6_value = strand6_value;
	}

	public Date getProcess_date_time() {
		return process_date_time;
	}

	public void setProcess_date_time(Date process_date_time) {
		this.process_date_time = process_date_time;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ProcessParametersMasterModel getProcParaMstrMdl() {
		return procParaMstrMdl;
	}

	public void setProcParaMstrMdl(ProcessParametersMasterModel procParaMstrMdl) {
		this.procParaMstrMdl = procParaMstrMdl;
	}

	@Override
	public int compareTo(CCMHeatProcessParameterDetails o) {
		if (getCreated_date_time() == null || o.getCreated_date_time() == null) {
		      return 0;
		    }
		    return getCreated_date_time().compareTo(o.getCreated_date_time());
	}
	
	
	
	
	
}
