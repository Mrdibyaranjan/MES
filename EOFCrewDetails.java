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

import com.smes.masters.model.SubUnitMasterModel;
import com.smes.masters.model.UserRoleMapMasterModel;

@Entity
@Table(name="TRNS_HEAT_CREW_DETAILS")
@TableGenerator(name = "TRNS_EOF_CREW_DTLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "EofCrewDtlsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
public class EOFCrewDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TRNS_EOF_CREW_DTLS_SEQ_GEN")
	@Column(name="CREW_SI_NO")
	private Integer crew_si_no;
	
	@Column(name="HEAT_ID",nullable=false)
	private String heat_id;
	
	@Column(name="HEAT_COUNTER",nullable=false)
	private Integer heat_counter;
	
	@Column(name="USER_ROLE_ID",nullable=false)
	private Integer user_role_id;

	@Column(name="SUB_UNIT_ID",nullable=false)
	private Integer sub_unit_id;

		
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
	@JoinColumn(name="USER_ROLE_ID" ,referencedColumnName="USER_ROLE_ID",insertable=false,updatable=false)
	private UserRoleMapMasterModel userRoleMapMstrMdl;

	@ManyToOne(optional=true)
	@JoinColumn(name="SUB_UNIT_ID" ,referencedColumnName="SUB_UNIT_ID",insertable=false,updatable=false)
	private SubUnitMasterModel subUnitMstrModl;
	
	public Integer getCrew_si_no() {
		return crew_si_no;
	}

	public void setCrew_si_no(Integer crew_si_no) {
		this.crew_si_no = crew_si_no;
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

	public Integer getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(Integer user_role_id) {
		this.user_role_id = user_role_id;
	}

	public Integer getSub_unit_id() {
		return sub_unit_id;
	}

	public void setSub_unit_id(Integer sub_unit_id) {
		this.sub_unit_id = sub_unit_id;
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

	public UserRoleMapMasterModel getUserRoleMapMstrMdl() {
		return userRoleMapMstrMdl;
	}

	public void setUserRoleMapMstrMdl(UserRoleMapMasterModel userRoleMapMstrMdl) {
		this.userRoleMapMstrMdl = userRoleMapMstrMdl;
	}

	public SubUnitMasterModel getSubUnitMstrModl() {
		return subUnitMstrModl;
	}

	public void setSubUnitMstrModl(SubUnitMasterModel subUnitMstrModl) {
		this.subUnitMstrModl = subUnitMstrModl;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
	
	
}
