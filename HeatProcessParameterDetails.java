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

import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.PSNProcessParametersModel;
import com.smes.masters.model.ProcessParametersMasterModel;

@Entity
@Table(name="TRNS_HEAT_PROCESS_PARAMETERS")
@TableGenerator(name = "HEAT_PROCESS_PARAM_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "EofProcessParamSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class HeatProcessParameterDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_PROCESS_PARAM_SEQ_GEN")
	@Column(name="PROC_PARAM_EVENT_ID")
	private Integer proc_param_event_id;

	@Column(name="PARAM_ID")
	private Integer param_id;

	@Column(name="PARAM_VALUE_AIM")
	//private Double param_value_aim;
	private Double param_value_actual;

	public Double getParam_value_actual() {
		return param_value_actual;
	}

	public void setParam_value_actual(Double param_value_actual) {
		this.param_value_actual = param_value_actual;
	}

	@Column(name="PARAM_VALUE_MIN")
	private Double param_value_min;

	@Column(name="PARAM_VALUE_MAX")
	private Double param_value_max;

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

	@Transient
	private String proc_para_name;
	
	/*
*/
	@Transient
	private Double param_value_aim;

	@Transient
	private String uom;
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	private String grid_arry; 
	
	@Transient
	private Integer trns_eof_si_no;
	
	//Changed By Nabi on 13 Sep 2018
	@ManyToOne(optional=true)
	@JoinColumn(name="PARAM_ID" ,referencedColumnName="PARAM_SI_NO",insertable=false,updatable=false)
	private ProcessParametersMasterModel psnProcParaMstrMdl;//procParaMstrMdl;
	
	public Integer getProc_param_event_id() {
		return proc_param_event_id;
	}

	public ProcessParametersMasterModel getPsnProcParaMstrMdl() {
		return psnProcParaMstrMdl;
	}

	public void setPsnProcParaMstrMdl(ProcessParametersMasterModel psnProcParaMstrMdl) {
		this.psnProcParaMstrMdl = psnProcParaMstrMdl;
	}

	public void setProc_param_event_id(Integer proc_param_event_id) {
		this.proc_param_event_id = proc_param_event_id;
	}
	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}
	public Integer getParam_id() {
		return param_id;
	}

	public void setParam_id(Integer param_id) {
		this.param_id = param_id;
	}

	public Date getProcess_date_time() {
		return process_date_time;
	}

	public void setProcess_date_time(Date process_date_time) {
		this.process_date_time = process_date_time;
	}

	public Double getParam_value_aim() {
		return param_value_aim;
	}

	public void setParam_value_aim(Double param_value_aim) {
		this.param_value_aim = param_value_aim;
	}

	public Double getParam_value_min() {
		return param_value_min;
	}

	public void setParam_value_min(Double param_value_min) {
		this.param_value_min = param_value_min;
	}

	public Double getParam_value_max() {
		return param_value_max;
	}

	public void setParam_value_max(Double param_value_max) {
		this.param_value_max = param_value_max;
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

	public String getProc_para_name() {
		return proc_para_name;
	}

	public void setProc_para_name(String proc_para_name) {
		this.proc_para_name = proc_para_name;
	}

//	public ProcessParametersMasterModel getProcParaMstrMdl() {
//		return procParaMstrMdl;
//	}
//
//	public void setProcParaMstrMdl(ProcessParametersMasterModel procParaMstrMdl) {
//		this.procParaMstrMdl = procParaMstrMdl;
//	}
	
	

	public String getGrid_arry() {
		return grid_arry;
	}

	public void setGrid_arry(String grid_arry) {
		this.grid_arry = grid_arry;
	}

	public Integer getTrns_eof_si_no() {
		return trns_eof_si_no;
	}

	public void setTrns_eof_si_no(Integer trns_eof_si_no) {
		this.trns_eof_si_no = trns_eof_si_no;
	}

	public void setIs_mandatory(Object object) {
		// TODO Auto-generated method stub
		
	}

	
}
