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

import com.smes.masters.model.MainHeatStatusMasterModel;

@Entity
@Table(name="SO_HEADER_T")
@TableGenerator(name = "SO_HEADER_T_SEQ", table = "SEQ_GENERATOR", valueColumnName = "NEXT_VAL", allocationSize = 1)
public class SoHeader implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator = "SO_HEADER_T_SEQ")
	@Column(name="SO_ID")
	private long soId;
	
	@Column(name="ORDER_NO")
	private String orderNo;
	
	@Column(name="ITEM_NO")
	private String itemNo;
	
	private String material;
	
	@Column(name="site_id")
	private long siteId;
	
	@Column(name="PLANNED_PATH")
	private String plannedPath;
	
	private Double quantity;
	
	@Column(name="QUANTITY_OVERDEL_TOL")
	private Double quantityOverdelTol;

	@Column(name="QUANTITY_UNDERDEL_TOL")
	private Double quantityUnderdelTol;
	
	@Column(name="SOLD_TO_PARTY_NAME")
	private String soldToPartyName;
	
	@Column(name="SHIP_TO_PARTY_NAME")
	private String shipToPartyName;
	
	@Column(name="DISTRIBUTION_CHANNEL")
	private String distributionChannel;
	
	@Column(name="DESTINATION_CITY")
	private String destinationCity;
	
	@Column(name="MODE_OF_TRANSPORT")
	private String modeOfTransport;
	
	@Column(name="UNLOADING_POINT")
	private String unloadingPoint;
	
	@Column(name="RECEIVING_POINT")
	private String receivingPoint;
	
	@Column(name="MATERIAL_TREE")
	private String materialTree;
	
	@Column(name="PROCESS_PATH")
	private String processPath;
	
	@Column(name="ROUTE_TREE")
	private String routeTree;
	
	@Temporal(TemporalType.DATE)
	@Column(name="PROPOSED_DELIVERY_DATE")
	private Date proposedDeliveryDate;
	
	@Column(name="COMMITTED_DATE")
	private Date committedDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ORDER_CREATION_DATE")
	private Date orderCreationDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="REQUIRED_DATE")
	private Date requiredDate;
	
	@Column(name="STATUS_ID")
	private long statusId;
	
	@Column(name="CREATED_BY")
	private long createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_BY")
	private long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	private String attribute1;

	private String attribute2;

	private String attribute3;

	private String attribute4;

	private String attribute5;
	
	@Column(name="END_APPLICATION")
	private String endApplication;

	@Column(name="CUST_KEY")
	private String custKey;

	@Column(name="CUST_NAME_REFER")
	private String custNameRefer;

	@Column(name="CUST_ORDER_CAT")
	private String custOrderCat;
	
	@Column(name="EQ_SPEC")
	private String eqSpec;

	@Column(name="EQ_SPEC_GRP")
	private String eqSpecGrp;

	@Column(name="EQ_SUB_SPEC")
	private String eqSubSpec;
	
	@Column(name="SOI_DOC_TYPE_REF")
	private String soiDocTypeRef;
	
	@Column(name="TDC_GROUP")
	private String tdcGroup;
	
	@Column(name="ALT_EQ_SPEC")
	private String altEqSpec;

	@Column(name="ALT_EQ_SPEC_GRP")
	private String altEqSpecGrp;

	@Column(name="ALT_EQ_SUB_SPEC")
	private String altEqSubSpec;
	
	@Column(name="END_APP_GROUP")
	private String endAppGroup;
	
	@Column(name="GRADE_CATEGORY")
	private String gradeCategory;
	
	@Column(name="SALES_ORG")
	private String salesOrg;
	
	@Column(name="QUALITY_CODE")
	private String qualityCode;
	
	@Column(name="SUB_CON_REQ_FLAG")
	private String subConReqFlag;
	
	@Column(name="PREV_STATUS")
	private long prevStatus;
	
	@Column(name="COLOR_CODE")
	private String colorCode;
	
	@Column(name="WORK_ORDER")
	private String workOrder;

	@Column(name="ISO_FLAG")
	private String isoFlag;

	@Transient
	private String soHeaderItem;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="STATUS_ID" ,referencedColumnName="MAIN_STATUS_ID",insertable=false,updatable=false)
	private MainHeatStatusMasterModel mainStatusModel;
	
	public MainHeatStatusMasterModel getMainStatusModel() {
		return mainStatusModel;
	}

	public void setMainStatusModel(MainHeatStatusMasterModel mainStatusModel) {
		this.mainStatusModel = mainStatusModel;
	}

	public String getSoHeaderItem() {
		return soHeaderItem;
	}

	public void setSoHeaderItem(String soHeaderItem) {
		this.soHeaderItem = soHeaderItem;
	}

	public long getSoId() {
		return soId;
	}

	public void setSoId(long soId) {
		this.soId = soId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getPlannedPath() {
		return plannedPath;
	}

	public void setPlannedPath(String plannedPath) {
		this.plannedPath = plannedPath;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getQuantityOverdelTol() {
		return quantityOverdelTol;
	}

	public void setQuantityOverdelTol(Double quantityOverdelTol) {
		this.quantityOverdelTol = quantityOverdelTol;
	}

	public Double getQuantityUnderdelTol() {
		return quantityUnderdelTol;
	}

	public void setQuantityUnderdelTol(Double quantityUnderdelTol) {
		this.quantityUnderdelTol = quantityUnderdelTol;
	}

	public String getSoldToPartyName() {
		return soldToPartyName;
	}

	public void setSoldToPartyName(String soldToPartyName) {
		this.soldToPartyName = soldToPartyName;
	}

	public String getShipToPartyName() {
		return shipToPartyName;
	}

	public void setShipToPartyName(String shipToPartyName) {
		this.shipToPartyName = shipToPartyName;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}

	public String getModeOfTransport() {
		return modeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}

	public String getUnloadingPoint() {
		return unloadingPoint;
	}

	public void setUnloadingPoint(String unloadingPoint) {
		this.unloadingPoint = unloadingPoint;
	}

	public String getReceivingPoint() {
		return receivingPoint;
	}

	public void setReceivingPoint(String receivingPoint) {
		this.receivingPoint = receivingPoint;
	}

	public String getMaterialTree() {
		return materialTree;
	}

	public void setMaterialTree(String materialTree) {
		this.materialTree = materialTree;
	}

	public String getProcessPath() {
		return processPath;
	}

	public void setProcessPath(String processPath) {
		this.processPath = processPath;
	}

	public String getRouteTree() {
		return routeTree;
	}

	public void setRouteTree(String routeTree) {
		this.routeTree = routeTree;
	}

	public Date getProposedDeliveryDate() {
		return proposedDeliveryDate;
	}

	public void setProposedDeliveryDate(Date proposedDeliveryDate) {
		this.proposedDeliveryDate = proposedDeliveryDate;
	}

	public Date getCommittedDate() {
		return committedDate;
	}

	public void setCommittedDate(Date committedDate) {
		this.committedDate = committedDate;
	}

	public Date getOrderCreationDate() {
		return orderCreationDate;
	}

	public void setOrderCreationDate(Date orderCreationDate) {
		this.orderCreationDate = orderCreationDate;
	}

	public Date getRequiredDate() {
		return requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

	public String getAttribute3() {
		return attribute3;
	}

	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}

	public String getAttribute4() {
		return attribute4;
	}

	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}

	public String getAttribute5() {
		return attribute5;
	}

	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}

	public String getEndApplication() {
		return endApplication;
	}

	public void setEndApplication(String endApplication) {
		this.endApplication = endApplication;
	}

	public String getCustKey() {
		return custKey;
	}

	public void setCustKey(String custKey) {
		this.custKey = custKey;
	}

	public String getCustNameRefer() {
		return custNameRefer;
	}

	public void setCustNameRefer(String custNameRefer) {
		this.custNameRefer = custNameRefer;
	}

	public String getCustOrderCat() {
		return custOrderCat;
	}

	public void setCustOrderCat(String custOrderCat) {
		this.custOrderCat = custOrderCat;
	}

	public String getEqSpec() {
		return eqSpec;
	}

	public void setEqSpec(String eqSpec) {
		this.eqSpec = eqSpec;
	}

	public String getEqSpecGrp() {
		return eqSpecGrp;
	}

	public void setEqSpecGrp(String eqSpecGrp) {
		this.eqSpecGrp = eqSpecGrp;
	}

	public String getEqSubSpec() {
		return eqSubSpec;
	}

	public void setEqSubSpec(String eqSubSpec) {
		this.eqSubSpec = eqSubSpec;
	}

	public String getSoiDocTypeRef() {
		return soiDocTypeRef;
	}

	public void setSoiDocTypeRef(String soiDocTypeRef) {
		this.soiDocTypeRef = soiDocTypeRef;
	}

	public String getTdcGroup() {
		return tdcGroup;
	}

	public void setTdcGroup(String tdcGroup) {
		this.tdcGroup = tdcGroup;
	}

	public String getAltEqSpec() {
		return altEqSpec;
	}

	public void setAltEqSpec(String altEqSpec) {
		this.altEqSpec = altEqSpec;
	}

	public String getAltEqSpecGrp() {
		return altEqSpecGrp;
	}

	public void setAltEqSpecGrp(String altEqSpecGrp) {
		this.altEqSpecGrp = altEqSpecGrp;
	}

	public String getAltEqSubSpec() {
		return altEqSubSpec;
	}

	public void setAltEqSubSpec(String altEqSubSpec) {
		this.altEqSubSpec = altEqSubSpec;
	}

	public String getEndAppGroup() {
		return endAppGroup;
	}

	public void setEndAppGroup(String endAppGroup) {
		this.endAppGroup = endAppGroup;
	}

	public String getGradeCategory() {
		return gradeCategory;
	}

	public void setGradeCategory(String gradeCategory) {
		this.gradeCategory = gradeCategory;
	}

	public String getSalesOrg() {
		return salesOrg;
	}

	public void setSalesOrg(String salesOrg) {
		this.salesOrg = salesOrg;
	}

	public String getQualityCode() {
		return qualityCode;
	}

	public void setQualityCode(String qualityCode) {
		this.qualityCode = qualityCode;
	}

	public String getSubConReqFlag() {
		return subConReqFlag;
	}

	public void setSubConReqFlag(String subConReqFlag) {
		this.subConReqFlag = subConReqFlag;
	}

	public long getPrevStatus() {
		return prevStatus;
	}

	public void setPrevStatus(long prevStatus) {
		this.prevStatus = prevStatus;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public String getIsoFlag() {
		return isoFlag;
	}

	public void setIsoFlag(String isoFlag) {
		this.isoFlag = isoFlag;
	}

}
