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

@Entity
@Table(name="SO_COMPONENT_T")
@TableGenerator(name = "SO_COMPONENT_T_SEQ", table = "SEQ_GENERATOR", valueColumnName = "NEXT_VAL", allocationSize = 1)
public class SoComponent implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator = "SO_COMPONENT_T_SEQ")
	@Column(name="SO_COMP_ID")
	private Long soCompId;
	
	@Column(name="SO_ID")
	private long soId;
	
	@Column(name="ORDER_NO")
	private String orderNo;
	
	@Column(name="ITEM_NO")
	private String itemNo;
	
	@Column(name="MATERIAL")
	private String material;
	
	@Column(name="ROUTE_IND")
	private String routeInd;
	
	@Column(name="FINISH_ITEM")
	private String finishItem;
	
	@Column(name="SELECTED_ROUTE_FLAG")
	private String selectedRouteFlag;

	@Column(name="SELECTED_ROUTE_POS")
	private Long selectedRoutePos;
	
	@Column(name="BATCH_WGT_MAX")
	private Double batchWgtMax;

	@Column(name="BATCH_WGT_MIN")
	private Double batchWgtMin;
	
	private Double thickness;

	@Column(name="THICKNESS_MAX")
	private Double thicknessMax;

	@Column(name="THICKNESS_MIN")
	private Double thicknessMin;
	
	private Double width;

	@Column(name="WIDTH_MAX")
	private Double widthMax;

	@Column(name="WIDTH_MIN")
	private Double widthMin;
	
	@Column(name="IDM_1")
	private Integer idm1;

	@Column(name="IDM_2")
	private Integer idm2;
	
	@Column(name="grade_id")
	private String gradeId;
	
	@Column(name="EDGE_CONDN")
	private String edgeCondn;
	
	@Column(name="LENGTH_AIM")
	private Integer lengthAim;

	@Column(name="LENGTH_MAX")
	private Integer lengthMax;

	@Column(name="LENGTH_MIN")
	private Integer lengthMin;
	
	@Column(name="BDGT_YIELD")
	private Double bdgtYield;
	
	@Column(name="PRODUCTION_UNIT_P")
	private String productionUnitP;

	@Column(name="PRODUCTION_UNIT_S1")
	private String productionUnitS1;

	@Column(name="PRODUCTION_UNIT_S2")
	private String productionUnitS2;

	@Column(name="PRODUCTION_UNIT_S3")
	private String productionUnitS3;

	@Column(name="PRODUCTION_UNIT_S4")
	private String productionUnitS4;

	@Column(name="PRODUCTION_UNIT_S5")
	private String productionUnitS5;

	@Column(name="PRODUCTION_UNIT_S6")
	private String productionUnitS6;

	@Column(name="PRODUCTION_UNIT_S7")
	private String productionUnitS7;
	
	@Column(name="PARTING_REQ")
	private String partingReq;
	
	@Column(name="NO_OF_PARTS")
	private Integer noOfParts;
	
	@Column(name="ROUTE_IND_NEXT")
	private String routeIndNext;
	
	@Column(name="SURFACE_FINISH")
	private String surfaceFinish;
	
	@Column(name="TRIMMING_REQ_FLAG")
	private String trimmingReqFlag;
	
	@Column(name="PRIORITY_FLAG")
	private String priorityFlag;
	
	@Column(name="CUST_SAMPLE_REQ_FLAG")
	private String custSampleReqFlag;
	
	@Column(name="CUST_SEG")
	private String custSeg;
	
	@Column(name="LABEL_CODE")
	private String labelCode;
	
	@Column(name="LICENCE_NO")
	private String licenceNo;
	
	@Column(name="OILING_FLAG")
	private String oilingFlag;
	
	@Column(name="OILING_CODE")
	private String oilingCode;
	
	@Column(name="OILING_TYPE")
	private String oilingType;
	
	@Column(name="OILING_MG_MAX")
	private Double oilingMgMax;

	@Column(name="OILING_MG_MIN")
	private Double oilingMgMin;

	@Column(name="PACKING_CODE")
	private String packingCode;
	
	@Column(name="ROUGHNESS_CODE")
	private String roughnessCode;
	
	@Column(name="SHAPE_SEVERITY")
	private String shapeSeverity;
	
	@Column(name="SURFACE_SEVERITY")
	private String surfaceSeverity;
	
	@Column(name="SLEEVE_REQD_FLAG")
	private String sleeveReqdFlag;
	
	@Column(name="SPEC_ON_LOGO")
	private String specOnLogo;
	
	@Column(name="UNITISATION_REQ_FLAG")
	private String unitisationReqFlag;
	
	@Column(name="WIDTH_TOL_TYPE")
	private String widthTolType;
	
	@Column(name="RM_GRADE_ID")
	private long rmGradeId;
	
	@Column(name="PLTCM_NECK_MM")
	private Double pltcmNeckMm;
	
	@Column(name="SKINPASS_REQ_FLAG")
	private String sSkinpassReqFlag;
	
	@Column(name="RH_REQ_FLAG")
	private String sRhReqFlag;
	
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
	
	@Column(name="CRNO_DENSITY")
	private Double crnoDensity;
	
	@Column(name="CR_THICK_MM_AIM")
	private Double crThickMmAim;

	@Column(name="CR_THICK_MM_MAX")
	private Double crThickMmMax;

	@Column(name="CR_THICK_MM_MIN")
	private Double crThickMmMin;
	
	@Column(name="INS_COATING_CODE")
	private String insCoatingCode;

	@Column(name="INS_COATING_MICRON_AIM")
	private Double insCoatingMicronAim;

	@Column(name="INS_COATING_MICRON_MAX")
	private Double insCoatingMicronMax;

	@Column(name="INS_COATING_MICRON_MIN")
	private Double insCoatingMicronMin;

	@Column(name="INS_COATING_SUPPLIER")
	private String insCoatingSupplier;
	
	@Column(name="LINER_MARKING_REQ_FLAG")
	private String linerMarkingReqFlag;
	
	@Column(name="ORDER_COMBI_FLAG")
	private String orderCombiFlag;
	
	@Column(name="ZN_COATING_GSM_MIN")
	private Double znCoatingGsmMin;
	
	@Column(name="ANTI_FLUTING_REQD_FLAG")
	private String antiFlutingReqdFlag;
	
	@Column(name="COIL_WINDING")
	private String coilWinding;
	
	@Column(name="COLD_TREAT")
	private String coldTreat;
	
	@Column(name="LFQ_REQD_FLAG")
	private String lfqReqdFlag;
	
	private String passivation;
	
	@Column(name="PROD_LOGO_MARKING_REQ_FLAG")
	private String prodLogoMarkingReqFlag;
	
	@Column(name="SLEEVE_TYPE")
	private String sleeveType;

	@Column(name="SPANGLE_TYPE")
	private String spangleType;
	
	@Column(name="ZN_COATING_BOTTOM_GSM_MAX")
	private Double znCoatingBottomGsmMax;

	@Column(name="ZN_COATING_BOTTOM_GSM_MIN")
	private Double znCoatingBottomGsmMin;
	
	@Column(name="ZN_COATING_GSM_MAX")
	private Double znCoatingGsmMax;
	
	@Column(name="ZN_COATING_TOP_GSM_MAX")
	private Double znCoatingTopGsmMax;

	@Column(name="ZN_COATING_TOP_GSM_MIN")
	private Double znCoatingTopGsmMin;
	
	@Column(name="ZN_COATING_TYPE")
	private String znCoatingType;
	
	@Column(name="GUARD_FILM_REQ_FLAG")
	private String guardFilmReqFlag;
	
	@Column(name="INS_COATING_TYPE")
	private String insCoatingType;
	
	@Column(name="NEXT_UNIT")
	private String nextUnit;

	@Column(name="NEXT_MATERIAL")
	private String nextMaterial;

	@Column(name="NEXT_OPERATION")
	private String nextOperation;
	
	@Column(name="ZN_COATING_GSM")
	private Double znCoatingGsm;

	@Column(name="ZN_COATING_TOP_GSM")
	private Double znCoatingTopGsm;

	@Column(name="ZN_COATING_BOTTOM_GSM")
	private Double znCoatingBottomGsm;
	
	@Column(name="LEG_THICKNESS_A")
	private Double legThicknessA;
	
	@Column(name="LEG_THICKNESS_B")
	private Double legThicknessB;
	
	@Column(name="LEG_LENGTH_A")
	private Double legLengthA;
	
	@Column(name="LEG_LENGTH_B")
	private Double legLengthB;
	
	@Column(name="DIA_AIM")
	private Integer diaAim;
	
	@Column(name="DIA_MIN")
	private Integer diaMin;
	
	@Column(name="DIA_MAX")
	private Integer diaMax;
	
	@Column(name="DIA_UOM")
	private Integer diaUom;
	
	@Column(name="HEIGHT_AIM")
	private Double heightAim;
	
	@Column(name="HEIGHT_MIN")
	private Double heightMin;
	
	@Column(name="HEIGHT_MAX")
	private Double heightMax;
	
	@Column(name="HEIGHT_UOM")
	private Double heightUom;
	
	@Column(name="PCS_PER_BUNDLE_AIM")
	private Double pcsPerBundleAim;
	
	@Column(name="PCS_PER_BUNDLE_MIN")
	private Double pcsPerBundleMin;
	
	@Column(name="PCS_PER_BUNDLE_MAX")
	private Double pcsPerBundleMax;
	
	@Column(name="TEST_CERTIFICATE_TYPE")
	private String testCertificationType;
	
	@Column(name="LBS_WT_MIN")
	private Double lbsWtMin;
	
	@Column(name="LBS_WT_MAX")
	private Double lbsWtMax;
	
	@Column(name="LENGTH_FEET")
	private Double lengthFeet;
	
	@Column(name="DIA_INCH")
	private Double diaInch;
	
	@Column(name="SO_INVOICING_WT_CAT")
	private String soInvoicingWtCat;

	public Long getSoCompId() {
		return soCompId;
	}

	public void setSoCompId(Long soCompId) {
		this.soCompId = soCompId;
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

	public String getRouteInd() {
		return routeInd;
	}

	public void setRouteInd(String routeInd) {
		this.routeInd = routeInd;
	}

	public String getFinishItem() {
		return finishItem;
	}

	public void setFinishItem(String finishItem) {
		this.finishItem = finishItem;
	}

	public String getSelectedRouteFlag() {
		return selectedRouteFlag;
	}

	public void setSelectedRouteFlag(String selectedRouteFlag) {
		this.selectedRouteFlag = selectedRouteFlag;
	}

	public Long getSelectedRoutePos() {
		return selectedRoutePos;
	}

	public void setSelectedRoutePos(Long selectedRoutePos) {
		this.selectedRoutePos = selectedRoutePos;
	}

	public Double getBatchWgtMax() {
		return batchWgtMax;
	}

	public void setBatchWgtMax(Double batchWgtMax) {
		this.batchWgtMax = batchWgtMax;
	}

	public Double getBatchWgtMin() {
		return batchWgtMin;
	}

	public void setBatchWgtMin(Double batchWgtMin) {
		this.batchWgtMin = batchWgtMin;
	}

	public Double getThickness() {
		return thickness;
	}

	public void setThickness(Double thickness) {
		this.thickness = thickness;
	}

	public Double getThicknessMax() {
		return thicknessMax;
	}

	public void setThicknessMax(Double thicknessMax) {
		this.thicknessMax = thicknessMax;
	}

	public Double getThicknessMin() {
		return thicknessMin;
	}

	public void setThicknessMin(Double thicknessMin) {
		this.thicknessMin = thicknessMin;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getWidthMax() {
		return widthMax;
	}

	public void setWidthMax(Double widthMax) {
		this.widthMax = widthMax;
	}

	public Double getWidthMin() {
		return widthMin;
	}

	public void setWidthMin(Double widthMin) {
		this.widthMin = widthMin;
	}

	public Integer getIdm1() {
		return idm1;
	}

	public void setIdm1(Integer idm1) {
		this.idm1 = idm1;
	}

	public Integer getIdm2() {
		return idm2;
	}

	public void setIdm2(Integer idm2) {
		this.idm2 = idm2;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getEdgeCondn() {
		return edgeCondn;
	}

	public void setEdgeCondn(String edgeCondn) {
		this.edgeCondn = edgeCondn;
	}

	public Integer getLengthAim() {
		return lengthAim;
	}

	public void setLengthAim(Integer lengthAim) {
		this.lengthAim = lengthAim;
	}

	public Integer getLengthMax() {
		return lengthMax;
	}

	public void setLengthMax(Integer lengthMax) {
		this.lengthMax = lengthMax;
	}

	public Integer getLengthMin() {
		return lengthMin;
	}

	public void setLengthMin(Integer lengthMin) {
		this.lengthMin = lengthMin;
	}

	public Double getBdgtYield() {
		return bdgtYield;
	}

	public void setBdgtYield(Double bdgtYield) {
		this.bdgtYield = bdgtYield;
	}

	public String getProductionUnitP() {
		return productionUnitP;
	}

	public void setProductionUnitP(String productionUnitP) {
		this.productionUnitP = productionUnitP;
	}

	public String getProductionUnitS1() {
		return productionUnitS1;
	}

	public void setProductionUnitS1(String productionUnitS1) {
		this.productionUnitS1 = productionUnitS1;
	}

	public String getProductionUnitS2() {
		return productionUnitS2;
	}

	public void setProductionUnitS2(String productionUnitS2) {
		this.productionUnitS2 = productionUnitS2;
	}

	public String getProductionUnitS3() {
		return productionUnitS3;
	}

	public void setProductionUnitS3(String productionUnitS3) {
		this.productionUnitS3 = productionUnitS3;
	}

	public String getProductionUnitS4() {
		return productionUnitS4;
	}

	public void setProductionUnitS4(String productionUnitS4) {
		this.productionUnitS4 = productionUnitS4;
	}

	public String getProductionUnitS5() {
		return productionUnitS5;
	}

	public void setProductionUnitS5(String productionUnitS5) {
		this.productionUnitS5 = productionUnitS5;
	}

	public String getProductionUnitS6() {
		return productionUnitS6;
	}

	public void setProductionUnitS6(String productionUnitS6) {
		this.productionUnitS6 = productionUnitS6;
	}

	public String getProductionUnitS7() {
		return productionUnitS7;
	}

	public void setProductionUnitS7(String productionUnitS7) {
		this.productionUnitS7 = productionUnitS7;
	}

	public String getPartingReq() {
		return partingReq;
	}

	public void setPartingReq(String partingReq) {
		this.partingReq = partingReq;
	}

	public Integer getNoOfParts() {
		return noOfParts;
	}

	public void setNoOfParts(Integer noOfParts) {
		this.noOfParts = noOfParts;
	}

	public String getRouteIndNext() {
		return routeIndNext;
	}

	public void setRouteIndNext(String routeIndNext) {
		this.routeIndNext = routeIndNext;
	}

	public String getSurfaceFinish() {
		return surfaceFinish;
	}

	public void setSurfaceFinish(String surfaceFinish) {
		this.surfaceFinish = surfaceFinish;
	}

	public String getTrimmingReqFlag() {
		return trimmingReqFlag;
	}

	public void setTrimmingReqFlag(String trimmingReqFlag) {
		this.trimmingReqFlag = trimmingReqFlag;
	}

	public String getPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(String priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	public String getCustSampleReqFlag() {
		return custSampleReqFlag;
	}

	public void setCustSampleReqFlag(String custSampleReqFlag) {
		this.custSampleReqFlag = custSampleReqFlag;
	}

	public String getCustSeg() {
		return custSeg;
	}

	public void setCustSeg(String custSeg) {
		this.custSeg = custSeg;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getOilingFlag() {
		return oilingFlag;
	}

	public void setOilingFlag(String oilingFlag) {
		this.oilingFlag = oilingFlag;
	}

	public String getOilingCode() {
		return oilingCode;
	}

	public void setOilingCode(String oilingCode) {
		this.oilingCode = oilingCode;
	}

	public String getOilingType() {
		return oilingType;
	}

	public void setOilingType(String oilingType) {
		this.oilingType = oilingType;
	}

	public Double getOilingMgMax() {
		return oilingMgMax;
	}

	public void setOilingMgMax(Double oilingMgMax) {
		this.oilingMgMax = oilingMgMax;
	}

	public Double getOilingMgMin() {
		return oilingMgMin;
	}

	public void setOilingMgMin(Double oilingMgMin) {
		this.oilingMgMin = oilingMgMin;
	}

	public String getPackingCode() {
		return packingCode;
	}

	public void setPackingCode(String packingCode) {
		this.packingCode = packingCode;
	}

	public String getRoughnessCode() {
		return roughnessCode;
	}

	public void setRoughnessCode(String roughnessCode) {
		this.roughnessCode = roughnessCode;
	}

	public String getShapeSeverity() {
		return shapeSeverity;
	}

	public void setShapeSeverity(String shapeSeverity) {
		this.shapeSeverity = shapeSeverity;
	}

	public String getSurfaceSeverity() {
		return surfaceSeverity;
	}

	public void setSurfaceSeverity(String surfaceSeverity) {
		this.surfaceSeverity = surfaceSeverity;
	}

	public String getSleeveReqdFlag() {
		return sleeveReqdFlag;
	}

	public void setSleeveReqdFlag(String sleeveReqdFlag) {
		this.sleeveReqdFlag = sleeveReqdFlag;
	}

	public String getSpecOnLogo() {
		return specOnLogo;
	}

	public void setSpecOnLogo(String specOnLogo) {
		this.specOnLogo = specOnLogo;
	}

	public String getUnitisationReqFlag() {
		return unitisationReqFlag;
	}

	public void setUnitisationReqFlag(String unitisationReqFlag) {
		this.unitisationReqFlag = unitisationReqFlag;
	}

	public String getWidthTolType() {
		return widthTolType;
	}

	public void setWidthTolType(String widthTolType) {
		this.widthTolType = widthTolType;
	}

	public long getRmGradeId() {
		return rmGradeId;
	}

	public void setRmGradeId(long rmGradeId) {
		this.rmGradeId = rmGradeId;
	}

	public Double getPltcmNeckMm() {
		return pltcmNeckMm;
	}

	public void setPltcmNeckMm(Double pltcmNeckMm) {
		this.pltcmNeckMm = pltcmNeckMm;
	}

	public String getsSkinpassReqFlag() {
		return sSkinpassReqFlag;
	}

	public void setsSkinpassReqFlag(String sSkinpassReqFlag) {
		this.sSkinpassReqFlag = sSkinpassReqFlag;
	}

	public String getsRhReqFlag() {
		return sRhReqFlag;
	}

	public void setsRhReqFlag(String sRhReqFlag) {
		this.sRhReqFlag = sRhReqFlag;
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

	public Double getCrnoDensity() {
		return crnoDensity;
	}

	public void setCrnoDensity(Double crnoDensity) {
		this.crnoDensity = crnoDensity;
	}

	public Double getCrThickMmAim() {
		return crThickMmAim;
	}

	public void setCrThickMmAim(Double crThickMmAim) {
		this.crThickMmAim = crThickMmAim;
	}

	public Double getCrThickMmMax() {
		return crThickMmMax;
	}

	public void setCrThickMmMax(Double crThickMmMax) {
		this.crThickMmMax = crThickMmMax;
	}

	public Double getCrThickMmMin() {
		return crThickMmMin;
	}

	public void setCrThickMmMin(Double crThickMmMin) {
		this.crThickMmMin = crThickMmMin;
	}

	public String getInsCoatingCode() {
		return insCoatingCode;
	}

	public void setInsCoatingCode(String insCoatingCode) {
		this.insCoatingCode = insCoatingCode;
	}

	public Double getInsCoatingMicronAim() {
		return insCoatingMicronAim;
	}

	public void setInsCoatingMicronAim(Double insCoatingMicronAim) {
		this.insCoatingMicronAim = insCoatingMicronAim;
	}

	public Double getInsCoatingMicronMax() {
		return insCoatingMicronMax;
	}

	public void setInsCoatingMicronMax(Double insCoatingMicronMax) {
		this.insCoatingMicronMax = insCoatingMicronMax;
	}

	public Double getInsCoatingMicronMin() {
		return insCoatingMicronMin;
	}

	public void setInsCoatingMicronMin(Double insCoatingMicronMin) {
		this.insCoatingMicronMin = insCoatingMicronMin;
	}

	public String getInsCoatingSupplier() {
		return insCoatingSupplier;
	}

	public void setInsCoatingSupplier(String insCoatingSupplier) {
		this.insCoatingSupplier = insCoatingSupplier;
	}

	public String getLinerMarkingReqFlag() {
		return linerMarkingReqFlag;
	}

	public void setLinerMarkingReqFlag(String linerMarkingReqFlag) {
		this.linerMarkingReqFlag = linerMarkingReqFlag;
	}

	public String getOrderCombiFlag() {
		return orderCombiFlag;
	}

	public void setOrderCombiFlag(String orderCombiFlag) {
		this.orderCombiFlag = orderCombiFlag;
	}

	public Double getZnCoatingGsmMin() {
		return znCoatingGsmMin;
	}

	public void setZnCoatingGsmMin(Double znCoatingGsmMin) {
		this.znCoatingGsmMin = znCoatingGsmMin;
	}

	public String getAntiFlutingReqdFlag() {
		return antiFlutingReqdFlag;
	}

	public void setAntiFlutingReqdFlag(String antiFlutingReqdFlag) {
		this.antiFlutingReqdFlag = antiFlutingReqdFlag;
	}

	public String getCoilWinding() {
		return coilWinding;
	}

	public void setCoilWinding(String coilWinding) {
		this.coilWinding = coilWinding;
	}

	public String getColdTreat() {
		return coldTreat;
	}

	public void setColdTreat(String coldTreat) {
		this.coldTreat = coldTreat;
	}

	public String getLfqReqdFlag() {
		return lfqReqdFlag;
	}

	public void setLfqReqdFlag(String lfqReqdFlag) {
		this.lfqReqdFlag = lfqReqdFlag;
	}

	public String getPassivation() {
		return passivation;
	}

	public void setPassivation(String passivation) {
		this.passivation = passivation;
	}

	public String getProdLogoMarkingReqFlag() {
		return prodLogoMarkingReqFlag;
	}

	public void setProdLogoMarkingReqFlag(String prodLogoMarkingReqFlag) {
		this.prodLogoMarkingReqFlag = prodLogoMarkingReqFlag;
	}

	public String getSleeveType() {
		return sleeveType;
	}

	public void setSleeveType(String sleeveType) {
		this.sleeveType = sleeveType;
	}

	public String getSpangleType() {
		return spangleType;
	}

	public void setSpangleType(String spangleType) {
		this.spangleType = spangleType;
	}

	public Double getZnCoatingBottomGsmMax() {
		return znCoatingBottomGsmMax;
	}

	public void setZnCoatingBottomGsmMax(Double znCoatingBottomGsmMax) {
		this.znCoatingBottomGsmMax = znCoatingBottomGsmMax;
	}

	public Double getZnCoatingBottomGsmMin() {
		return znCoatingBottomGsmMin;
	}

	public void setZnCoatingBottomGsmMin(Double znCoatingBottomGsmMin) {
		this.znCoatingBottomGsmMin = znCoatingBottomGsmMin;
	}

	public Double getZnCoatingGsmMax() {
		return znCoatingGsmMax;
	}

	public void setZnCoatingGsmMax(Double znCoatingGsmMax) {
		this.znCoatingGsmMax = znCoatingGsmMax;
	}

	public Double getZnCoatingTopGsmMax() {
		return znCoatingTopGsmMax;
	}

	public void setZnCoatingTopGsmMax(Double znCoatingTopGsmMax) {
		this.znCoatingTopGsmMax = znCoatingTopGsmMax;
	}

	public Double getZnCoatingTopGsmMin() {
		return znCoatingTopGsmMin;
	}

	public void setZnCoatingTopGsmMin(Double znCoatingTopGsmMin) {
		this.znCoatingTopGsmMin = znCoatingTopGsmMin;
	}

	public String getZnCoatingType() {
		return znCoatingType;
	}

	public void setZnCoatingType(String znCoatingType) {
		this.znCoatingType = znCoatingType;
	}

	public String getGuardFilmReqFlag() {
		return guardFilmReqFlag;
	}

	public void setGuardFilmReqFlag(String guardFilmReqFlag) {
		this.guardFilmReqFlag = guardFilmReqFlag;
	}

	public String getInsCoatingType() {
		return insCoatingType;
	}

	public void setInsCoatingType(String insCoatingType) {
		this.insCoatingType = insCoatingType;
	}

	public String getNextUnit() {
		return nextUnit;
	}

	public void setNextUnit(String nextUnit) {
		this.nextUnit = nextUnit;
	}

	public String getNextMaterial() {
		return nextMaterial;
	}

	public void setNextMaterial(String nextMaterial) {
		this.nextMaterial = nextMaterial;
	}

	public String getNextOperation() {
		return nextOperation;
	}

	public void setNextOperation(String nextOperation) {
		this.nextOperation = nextOperation;
	}

	public Double getZnCoatingGsm() {
		return znCoatingGsm;
	}

	public void setZnCoatingGsm(Double znCoatingGsm) {
		this.znCoatingGsm = znCoatingGsm;
	}

	public Double getZnCoatingTopGsm() {
		return znCoatingTopGsm;
	}

	public void setZnCoatingTopGsm(Double znCoatingTopGsm) {
		this.znCoatingTopGsm = znCoatingTopGsm;
	}

	public Double getZnCoatingBottomGsm() {
		return znCoatingBottomGsm;
	}

	public void setZnCoatingBottomGsm(Double znCoatingBottomGsm) {
		this.znCoatingBottomGsm = znCoatingBottomGsm;
	}

	public Double getLegThicknessA() {
		return legThicknessA;
	}

	public void setLegThicknessA(Double legThicknessA) {
		this.legThicknessA = legThicknessA;
	}

	public Double getLegThicknessB() {
		return legThicknessB;
	}

	public void setLegThicknessB(Double legThicknessB) {
		this.legThicknessB = legThicknessB;
	}

	public Double getLegLengthA() {
		return legLengthA;
	}

	public void setLegLengthA(Double legLengthA) {
		this.legLengthA = legLengthA;
	}

	public Double getLegLengthB() {
		return legLengthB;
	}

	public void setLegLengthB(Double legLengthB) {
		this.legLengthB = legLengthB;
	}

	public Integer getDiaAim() {
		return diaAim;
	}

	public void setDiaAim(Integer diaAim) {
		this.diaAim = diaAim;
	}

	public Integer getDiaMin() {
		return diaMin;
	}

	public void setDiaMin(Integer diaMin) {
		this.diaMin = diaMin;
	}

	public Integer getDiaMax() {
		return diaMax;
	}

	public void setDiaMax(Integer diaMax) {
		this.diaMax = diaMax;
	}

	public Integer getDiaUom() {
		return diaUom;
	}

	public void setDiaUom(Integer diaUom) {
		this.diaUom = diaUom;
	}

	public Double getHeightAim() {
		return heightAim;
	}

	public void setHeightAim(Double heightAim) {
		this.heightAim = heightAim;
	}

	public Double getHeightMin() {
		return heightMin;
	}

	public void setHeightMin(Double heightMin) {
		this.heightMin = heightMin;
	}

	public Double getHeightMax() {
		return heightMax;
	}

	public void setHeightMax(Double heightMax) {
		this.heightMax = heightMax;
	}

	public Double getHeightUom() {
		return heightUom;
	}

	public void setHeightUom(Double heightUom) {
		this.heightUom = heightUom;
	}

	public Double getPcsPerBundleAim() {
		return pcsPerBundleAim;
	}

	public void setPcsPerBundleAim(Double pcsPerBundleAim) {
		this.pcsPerBundleAim = pcsPerBundleAim;
	}

	public Double getPcsPerBundleMin() {
		return pcsPerBundleMin;
	}

	public void setPcsPerBundleMin(Double pcsPerBundleMin) {
		this.pcsPerBundleMin = pcsPerBundleMin;
	}

	public Double getPcsPerBundleMax() {
		return pcsPerBundleMax;
	}

	public void setPcsPerBundleMax(Double pcsPerBundleMax) {
		this.pcsPerBundleMax = pcsPerBundleMax;
	}

	public String getTestCertificationType() {
		return testCertificationType;
	}

	public void setTestCertificationType(String testCertificationType) {
		this.testCertificationType = testCertificationType;
	}

	public Double getLbsWtMin() {
		return lbsWtMin;
	}

	public void setLbsWtMin(Double lbsWtMin) {
		this.lbsWtMin = lbsWtMin;
	}

	public Double getLbsWtMax() {
		return lbsWtMax;
	}

	public void setLbsWtMax(Double lbsWtMax) {
		this.lbsWtMax = lbsWtMax;
	}

	public Double getLengthFeet() {
		return lengthFeet;
	}

	public void setLengthFeet(Double lengthFeet) {
		this.lengthFeet = lengthFeet;
	}

	public Double getDiaInch() {
		return diaInch;
	}

	public void setDiaInch(Double diaInch) {
		this.diaInch = diaInch;
	}

	public String getSoInvoicingWtCat() {
		return soInvoicingWtCat;
	}

	public void setSoInvoicingWtCat(String soInvoicingWtCat) {
		this.soInvoicingWtCat = soInvoicingWtCat;
	}
	
	
}
