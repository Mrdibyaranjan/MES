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

import com.smes.masters.model.LookupMasterModel;

@Entity
@Table(name="TRNS_HM_RECEIPT_DETAILS")
@TableGenerator(name = "HM_RECV_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HmRecvSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class HmReceiveBaseDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "HM_RECV_SEQ_GEN")
	@Column(name="HM_RECV_ID")
	private Integer hmRecvId;
	
	@Version
	@Column(name="RECORD_VERSION")
	private Integer version;
	
	@Column(name="HM_SEQ_NO",updatable=false)
	private Integer hmSeqNo;
	
	@Column(name="RECEIPT_SHIFT")
	private String recShift;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="HM_LADLE_NO",nullable=false)
	private String hmLadleNo;
	
	@Column(name="CAST_NO")
	private String castNo;
	
	@Column(name="HM_LADLE_STATUS")
	private Integer hmLadleStatus;
	
	@Column(name="DATA_SOURCE")
	private Integer dataSource;
	
	@Column(name="HM_LADLE_STAGE_STATUS")
	private Integer hmLadleStageStatus;
	
	@Column(name="HM_SOURCE")
	private Integer hmSource;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HM_LADLE_PROD_DT")
	private Date hmLadleProdDt;
	
	@Column(name="HM_LADLE_TARE_WT")
	private Double hmLadleTareWt;
	
	@Column(name="HM_LADLE_GROSS_WT")
	private Double hmLadleGrossWt;
	
	@Column(name="HM_LADLE_NET_WT")
	private Double hmLadleNetWt;
	
	@Column(name="HM_LADLE_C")
	private Double hmLadleC;
	
	@Column(name="HM_LADLE_MN")
	private Double hmLadleMn;
	
	@Column(name="HM_LADLE_P")
	private Double hmLadleP;
	
	@Column(name="HM_LADLE_S")
	private Double hmLadleS;
	
	@Column(name="HM_LADLE_SI")
	private Double hmLadleSi;
	
	@Column(name="HM_LADLE_TI")
	private Double hmLadleTi;
	
	@Column(name="HM_LADLE_TEMP_BF")
	private Double hmLadleTempBf;
	
	@Column(name="HM_LADLE_TEMP_EOF")
	private Double hmLadleTempEof;
	
	@Column(name="HM_AVLBL_WT")
	private Double hmAvlblWt;
	
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
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HM_RECV_DATE")
	private Date hmRecvDate;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="HM_LADLE_STAGE_STATUS" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupHmStageStatus;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="DATA_SOURCE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupDataSource;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="HM_SOURCE" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupHmSource;
	

	@ManyToOne(optional=true)
	@JoinColumn(name="HM_LADLE_STATUS" ,referencedColumnName="LOOKUP_ID",insertable=false,updatable=false)
	private LookupMasterModel lookupLdlStatus;
	
	@Transient
	private String hmLadleProdDt_s,hmRecvDate_s;
	
	@Transient
	private String hmNextProcess,sourceOfHm;
	
	public String getSourceOfHm() {
		return sourceOfHm;
	}

	public void setSourceOfHm(String sourceOfHm) {
		this.sourceOfHm = sourceOfHm;
	}

	@Transient
	private Double mixqty;
	
	@Column(name="HM_SMS_MEASURED_NET_WT")
	private Double hmSmsMeasuredWt;
	
	@Transient
	private String hm_arry;
	
	public String getHm_arry() {
		return hm_arry;
	}

	public void setHm_arry(String hm_arry) {
		this.hm_arry = hm_arry;
	}

	public Double getMixqty() {
		return mixqty;
	}

	public void setMixqty(Double mixqty) {
		this.mixqty = mixqty;
	}

	public String getHmNextProcess() {
		return hmNextProcess;
	}

	public void setHmNextProcess(String hmNextProcess) {
		this.hmNextProcess = hmNextProcess;
	}

	public String getHmLadleProdDt_s() {
		return hmLadleProdDt_s;
	}

	public void setHmLadleProdDt_s(String hmLadleProdDt_s) {
		this.hmLadleProdDt_s = hmLadleProdDt_s;
	}

	public String getHmRecvDate_s() {
		return hmRecvDate_s;
	}

	public void setHmRecvDate_s(String hmRecvDate_s) {
		this.hmRecvDate_s = hmRecvDate_s;
	}

	public Date getHmRecvDate() {
		return hmRecvDate;
	}

	public void setHmRecvDate(Date hmRecvDate) {
		this.hmRecvDate = hmRecvDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRecShift() {
		return recShift;
	}

	public void setRecShift(String recShift) {
		this.recShift = recShift;
	}

	public String getHmLadleNo() {
		return hmLadleNo;
	}

	public void setHmLadleNo(String hmLadleNo) {
		this.hmLadleNo = hmLadleNo;
	}

	public LookupMasterModel getLookupHmStageStatus() {
		return lookupHmStageStatus;
	}

	public void setLookupHmStageStatus(LookupMasterModel lookupHmStageStatus) {
		this.lookupHmStageStatus = lookupHmStageStatus;
	}

	public LookupMasterModel getLookupDataSource() {
		return lookupDataSource;
	}

	public void setLookupDataSource(LookupMasterModel lookupDataSource) {
		this.lookupDataSource = lookupDataSource;
	}

	public LookupMasterModel getLookupHmSource() {
		return lookupHmSource;
	}

	public void setLookupHmSource(LookupMasterModel lookupHmSource) {
		this.lookupHmSource = lookupHmSource;
	}

	public LookupMasterModel getLookupLdlStatus() {
		return lookupLdlStatus;
	}

	public void setLookupLdlStatus(LookupMasterModel lookupLdlStatus) {
		this.lookupLdlStatus = lookupLdlStatus;
	}

	public Integer getHmRecvId() {
		return hmRecvId;
	}

	public void setHmRecvId(Integer hmRecvId) {
		this.hmRecvId = hmRecvId;
	}

	public Integer getHmSeqNo() {
		return hmSeqNo;
	}

	public void setHmSeqNo(Integer hmSeqNo) {
		this.hmSeqNo = hmSeqNo;
	}

	public String getCastNo() {
		return castNo;
	}

	public void setCastNo(String castNo) {
		this.castNo = castNo;
	}

	public Integer getHmLadleStatus() {
		return hmLadleStatus;
	}

	public void setHmLadleStatus(Integer hmLadleStatus) {
		this.hmLadleStatus = hmLadleStatus;
	}

	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

	public Integer getHmLadleStageStatus() {
		return hmLadleStageStatus;
	}

	public void setHmLadleStageStatus(Integer hmLadleStageStatus) {
		this.hmLadleStageStatus = hmLadleStageStatus;
	}

	public Integer getHmSource() {
		return hmSource;
	}

	public void setHmSource(Integer hmSource) {
		this.hmSource = hmSource;
	}

	public Date getHmLadleProdDt() {
		return hmLadleProdDt;
	}

	public void setHmLadleProdDt(Date hmLadleProdDt) {
		this.hmLadleProdDt = hmLadleProdDt;
	}

	public Double getHmLadleTareWt() {
		return hmLadleTareWt;
	}

	public void setHmLadleTareWt(Double hmLadleTareWt) {
		this.hmLadleTareWt = hmLadleTareWt;
	}

	public Double getHmLadleGrossWt() {
		return hmLadleGrossWt;
	}

	public void setHmLadleGrossWt(Double hmLadleGrossWt) {
		this.hmLadleGrossWt = hmLadleGrossWt;
	}

	public Double getHmLadleNetWt() {
		return hmLadleNetWt;
	}

	public void setHmLadleNetWt(Double hmLadleNetWt) {
		this.hmLadleNetWt = hmLadleNetWt;
	}

	public Double getHmLadleC() {
		return hmLadleC;
	}

	public void setHmLadleC(Double hmLadleC) {
		this.hmLadleC = hmLadleC;
	}

	public Double getHmLadleMn() {
		return hmLadleMn;
	}

	public void setHmLadleMn(Double hmLadleMn) {
		this.hmLadleMn = hmLadleMn;
	}

	public Double getHmLadleP() {
		return hmLadleP;
	}

	public void setHmLadleP(Double hmLadleP) {
		this.hmLadleP = hmLadleP;
	}

	public Double getHmLadleS() {
		return hmLadleS;
	}

	public void setHmLadleS(Double hmLadleS) {
		this.hmLadleS = hmLadleS;
	}



	public Double getHmLadleSi() {
		return hmLadleSi;
	}

	public void setHmLadleSi(Double hmLadleSi) {
		this.hmLadleSi = hmLadleSi;
	}

	public Double getHmLadleTi() {
		return hmLadleTi;
	}

	public void setHmLadleTi(Double hmLadleTi) {
		this.hmLadleTi = hmLadleTi;
	}

	public Double getHmLadleTempBf() {
		return hmLadleTempBf;
	}

	public void setHmLadleTempBf(Double hmLadleTempBf) {
		this.hmLadleTempBf = hmLadleTempBf;
	}

	public Double getHmLadleTempEof() {
		return hmLadleTempEof;
	}

	public void setHmLadleTempEof(Double hmLadleTempEof) {
		this.hmLadleTempEof = hmLadleTempEof;
	}

	public Double getHmAvlblWt() {
		return hmAvlblWt;
	}

	public void setHmAvlblWt(Double hmAvlblWt) {
		this.hmAvlblWt = hmAvlblWt;
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

	public Double getHmSmsMeasuredWt() {
		return hmSmsMeasuredWt;
	}

	public void setHmSmsMeasuredWt(Double hmSmsMeasuredWt) {
		this.hmSmsMeasuredWt = hmSmsMeasuredWt;
	}

	
}
