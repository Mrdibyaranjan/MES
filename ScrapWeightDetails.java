package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INTF_SCRAP_WGT_DET")
public class ScrapWeightDetails implements Serializable{

	
	     
	@Id
	@Column(name="TRANS_YEAR")
	Integer trans_year;
	
	@Id
	@Column(name="TRANS_NO")
	Integer trans_no;
	
//	 @Id
//	    @AttributeOverrides({
//	    @AttributeOverride(name = "trans_year",
//	    column = @Column(name="TRANS_YEAR")),
//	    @AttributeOverride(name = "trans_no",
//	    column = @Column(name="TRANS_NO"))
//	    })
	 
	
	@Column(name="WB_TYPE") //weighbridge type
	String wb_type; 
	
	@Column(name="TRANS_DATE")
	Date  trans_date;
	
	@Column(name="WORK_CENTER")
	String work_center; //MATERIAL_DESC
	
	@Column(name="MATERIAL_DESC")
	String material_desc; 
	
	@Column(name="NET_WEIGHT")
	Float net_weight;
	
	@Column(name="RECORD_STATUS")
	Integer record_status;
	
	
	@Column(name="CREATED_TIME")
	Date created_time;
	
	
	@Column(name="UPDATED_TIME")
	Date updated_time;


	public Integer getTrans_year() {
		return trans_year;
	}


	public void setTrans_year(Integer trans_year) {
		this.trans_year = trans_year;
	}


	public Integer getTrans_no() {
		return trans_no;
	}


	public void setTrans_no(Integer trans_no) {
		this.trans_no = trans_no;
	}


	public String getWb_type() {
		return wb_type;
	}


	public void setWb_type(String wb_type) {
		this.wb_type = wb_type;
	}


	public Date getTrans_date() {
		return trans_date;
	}


	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}


	public String getWork_center() {
		return work_center;
	}


	public void setWork_center(String work_center) {
		this.work_center = work_center;
	}


	public String getMaterial_desc() {
		return material_desc;
	}


	public void setMaterial_desc(String material_desc) {
		this.material_desc = material_desc;
	}


	public Float getNet_weight() {
		return net_weight;
	}


	public void setNet_weight(Float net_weight) {
		this.net_weight = net_weight;
	}


	




	public Integer getRecord_status() {
		return record_status;
	}


	public void setRecord_status(Integer record_status) {
		this.record_status = record_status;
	}


	public Date getCreated_time() {
		return created_time;
	}


	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}


	public Date getUpdated_time() {
		return updated_time;
	}


	public void setUpdated_time(Date updated_time) {
		this.updated_time = updated_time;
	}
	
	
	
}
