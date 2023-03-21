package com.smes.trans.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@Table(name = "INTF_SMS_PROD_CONSM_HEAT")
@TableGenerator(name = "INTF_CONS_HEAT_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "MtrlConsIntfSeqId", allocationSize = 1)
@DynamicUpdate(value = true)
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class IntfMaterialConsumptionModel implements Serializable {
	public String getSapuom() {
		return sapuom;
	}

	public void setSapuom(String sapuom) {
		this.sapuom = sapuom;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "INTF_CONS_HEAT_SEQ_GEN")
	@Column(name="INTF_ID" ,nullable=false)
	private Integer intf_id;
	
	@Column(name = "ACTUAL_HEAT_NO", nullable = false)
	private String actHeatNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "POSTING_DATE", nullable = false)
	private Date postingDate;
	
	@Column(name = "WORK_CENTER", nullable = false)
	private String workCenter;
	
	@Column(name = "STORAGE_LOCATION")
	private String storageLocation;
	
	@Column(name = "COMPONENT", nullable = false)
	private String component;
	
	@Column(name = "COMPONENT_TYPE", nullable = false)
	private String componentType;

	@Column(name = "QUANTITY")
	private Double qty;

	@Column(name = "UOM")
	private String uom;

	@Column(name = "RECEIVED_FLAG")
	private String receivedFlag;
	
	@Column(name = "CONS_FLAG")
	private String consFlag;
	
	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "STATUS_FLAG", nullable = false)
	private Integer statusFlag;

	@Column(name = "ERROR_DESCRIPTION")
	private String errorDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_CREATED")
	private Date dateCreated;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_MODIFIED")
	private Date dateModified;	
	
	@Column(name = "VALUATION_TYPE")
	private String valuationType;
	
	@Column(name = "SALES_ORDER")
	private String salesOrder;
	
	@Column(name = "SALES_ORDER_ITEM")
	private String salesOrderItem;
	
	@Transient
	String mtrlType, mtrlName;
	
	@Transient
	List<IntfMaterialConsumptionModel> mtrlConsLi;
	
	@Transient
	Integer heatTrackId;
	
	@Transient
	String sapuom;

	public String getActHeatNo() {
		return actHeatNo;
	}

	public void setActHeatNo(String actHeatNo) {
		this.actHeatNo = actHeatNo;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getReceivedFlag() {
		return receivedFlag;
	}

	public void setReceivedFlag(String receivedFlag) {
		this.receivedFlag = receivedFlag;
	}

	public String getConsFlag() {
		return consFlag;
	}

	public void setConsFlag(String consFlag) {
		this.consFlag = consFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public String getValuationType() {
		return valuationType;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getSalesOrderItem() {
		return salesOrderItem;
	}

	public void setSalesOrderItem(String salesOrderItem) {
		this.salesOrderItem = salesOrderItem;
	}

	public Integer getIntf_id() {
		return intf_id;
	}

	public void setIntf_id(Integer intf_id) {
		this.intf_id = intf_id;
	}

	public String getMtrlType() {
		return mtrlType;
	}

	public void setMtrlType(String mtrlType) {
		this.mtrlType = mtrlType;
	}

	public String getMtrlName() {
		return mtrlName;
	}

	public void setMtrlName(String mtrlName) {
		this.mtrlName = mtrlName;
	}

	public List<IntfMaterialConsumptionModel> getMtrlConsLi() {
		return mtrlConsLi;
	}

	public void setMtrlConsLi(List<IntfMaterialConsumptionModel> mtrlConsLi) {
		this.mtrlConsLi = mtrlConsLi;
	}

	public Integer getHeatTrackId() {
		return heatTrackId;
	}

	public void setHeatTrackId(Integer heatTrackId) {
		this.heatTrackId = heatTrackId;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	
}
