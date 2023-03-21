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

import com.smes.masters.model.MtrlProcessConsumableMstrModel;

@Entity
@Table(name="TRNS_EOF_HEAT_CONS_MATERIALS")
@TableGenerator(name = "HEAT_CONS_MTRLS_SEQ_GEN", table = "SEQ_GENERATOR", pkColumnName = "COL_KEY", valueColumnName = "NEXT_VAL", pkColumnValue = "HeatConsMtrlsSeqId", allocationSize = 1)
@DynamicUpdate(value=true)
@OptimisticLocking(type=OptimisticLockType.VERSION)
public class HeatConsMaterialsDetails implements Serializable {
	
	 private static final long serialVersionUID = 1L;
		

		@Id
		@GeneratedValue(strategy = GenerationType.TABLE, generator = "HEAT_CONS_MTRLS_SEQ_GEN")
		@Column(name="MTR_CONS_SI_NO")
		private Integer mtr_cons_si_no;
		
		@Column(name="TRNS_EOF_SI_NO",nullable=false)
		private Integer trns_eof_si_no;
		
		@Column(name="MATERIAL_ID",nullable=false)
		private Integer material_id;
		
		@Column(name="QTY",nullable=false)
		private Double qty;
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name="CONSUMPTION_DATE")
		private Date consumption_date;	
		
		@Column(name="RECORD_STATUS",nullable=false)
		private Integer record_status;	
		
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
		
		@Column(name="SAP_MATL_ID")
		private String sap_matl_id;	

		@Column(name="VALUATION_TYPE")
		private String valuation_type;	
		
		@ManyToOne(optional=true)
		@JoinColumn(name="MATERIAL_ID" ,referencedColumnName="MATERIAL_ID",insertable=false,updatable=false)
		private  MtrlProcessConsumableMstrModel mtrlMstrModel;
		
		@ManyToOne(optional=true)
		@JoinColumn(name="TRNS_EOF_SI_NO" ,referencedColumnName="TRNS_SI_NO",insertable=false,updatable=false)
		private EofHeatDetails eofTrnsModel;
		
		@Transient
		private String mtrlName;

		@Transient
		private String uom;
		
		@Transient
		private String grid_arry;
		
		@Transient
		private Double val_min,val_max,val_aim;

		public Double getVal_min() {
			return val_min;
		}

		public void setVal_min(Double val_min) {
			this.val_min = val_min;
		}

		public Double getVal_max() {
			return val_max;
		}

		public void setVal_max(Double val_max) {
			this.val_max = val_max;
		}

		public Double getVal_aim() {
			return val_aim;
		}

		public void setVal_aim(Double val_aim) {
			this.val_aim = val_aim;
		}

		public Integer getMtr_cons_si_no() {
			return mtr_cons_si_no;
		}

		public void setMtr_cons_si_no(Integer mtr_cons_si_no) {
			this.mtr_cons_si_no = mtr_cons_si_no;
		}

		public Integer getTrns_eof_si_no() {
			return trns_eof_si_no;
		}

		public void setTrns_eof_si_no(Integer trns_eof_si_no) {
			this.trns_eof_si_no = trns_eof_si_no;
		}

		public Integer getMaterial_id() {
			return material_id;
		}

		public void setMaterial_id(Integer material_id) {
			this.material_id = material_id;
		}

		public Double getQty() {
			return qty;
		}

		public void setQty(Double qty) {
			this.qty = qty;
		}

		public Date getConsumption_date() {
			return consumption_date;
		}

		public void setConsumption_date(Date consumption_date) {
			this.consumption_date = consumption_date;
		}
		public Integer getRecord_status() {
			return record_status;
		}

		public void setRecord_status(Integer record_status) {
			this.record_status = record_status;
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

		public MtrlProcessConsumableMstrModel getMtrlMstrModel() {
			return mtrlMstrModel;
		}

		public void setMtrlMstrModel(MtrlProcessConsumableMstrModel mtrlMstrModel) {
			this.mtrlMstrModel = mtrlMstrModel;
		}

		public EofHeatDetails getEofTrnsModel() {
			return eofTrnsModel;
		}

		public void setEofTrnsModel(EofHeatDetails eofTrnsModel) {
			this.eofTrnsModel = eofTrnsModel;
		}

		public String getMtrlName() {
			return mtrlName;
		}

		public void setMtrlName(String mtrlName) {
			this.mtrlName = mtrlName;
		}

		public String getUom() {
			return uom;
		}

		public void setUom(String uom) {
			this.uom = uom;
		}

		public String getGrid_arry() {
			return grid_arry;
		}

		public void setGrid_arry(String grid_arry) {
			this.grid_arry = grid_arry;
		}

		public Integer getVersion() {
			return version;
		}

		public void setVersion(Integer version) {
			this.version = version;
		}
		
		public String getSap_matl_id() {
			return sap_matl_id;
		}

		public void setSap_matl_id(String sap_matl_id) {
			this.sap_matl_id = sap_matl_id;
		}

		public String getValuation_type() {
			return valuation_type;
		}

		public void setValuation_type(String valuation_type) {
			this.valuation_type = valuation_type;
		}		

}
