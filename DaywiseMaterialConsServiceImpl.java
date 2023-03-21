package com.smes.trans.service.impl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.directwebremoting.util.SystemOutLoggingOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.dao.impl.CommonDao;
import com.smes.masters.dao.impl.LookupMasterDao;
import com.smes.masters.dao.impl.MaterialMapMasterDao;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.MaterialSubUnitMapMstrModel;
import com.smes.trans.dao.impl.CasterProductionDao;
import com.smes.trans.dao.impl.DaywiseMaterialConsDao;
import com.smes.trans.dao.impl.EofProductionDao;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.dao.impl.HotMetalReceiveDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.MaterialConsumptionDao;
import com.smes.trans.dao.impl.ScrapEntryDao;
import com.smes.trans.dao.impl.VDProductionDao;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatConsMtlsDetailsLog;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.DaywiseMaterialConsModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatConsumableModelLog;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.Constants;
import com.smes.util.MaterialConsumptionDTO;

@Service("DaywiseMaterialConsService")
public class DaywiseMaterialConsServiceImpl implements DaywiseMaterialConsService {

	@Autowired
	private DaywiseMaterialConsDao daywiseMtrlConsDao;
	
	@Autowired
	MaterialConsumptionService mtrlConsServ;
	
	@Autowired
	private MaterialMapMasterDao mtrlMapMstrDao;
	
	@Autowired
	private EofProductionDao eofProdDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private VDProductionDao vdProdDao;
	
	@Autowired
	private CasterProductionDao ccmProdDao;
	
	@Autowired
	private MaterialConsumptionDao mtrlConsDao;
	
	@Autowired
	private LRFProductionDao lrfProdDao;

	@Transactional
	@Override
	public List<MaterialConsumptionDTO> getDaywiseMtrlConsumption(String cons_date,String cons_type) {  
	    List<MaterialConsumptionDTO> retLi = new ArrayList<MaterialConsumptionDTO>();
	    try {
	    	Double retQty=null;
	        List<HeatConsMaterialsDetails> dayEofConsLi =daywiseMtrlConsDao.getEofConsQty(cons_date, cons_type);
		    List<LRFHeatConsumableModel> dayLrfConsLi=daywiseMtrlConsDao.getLrfConsQty(cons_date, cons_type);
		    List<CCMHeatConsMaterialsDetails> dayCcmConsLi=daywiseMtrlConsDao.getCcmConsQty(cons_date, cons_type);
	        List<MaterialSubUnitMapMstrModel> mtrlMstrLi = mtrlMapMstrDao.getMatlDetailsBySubUnitAndMtrlType(cons_type, null);
	    for(MaterialSubUnitMapMstrModel mtrlMstrObj : mtrlMstrLi) {
		    MaterialConsumptionDTO mtrlConsDto=new MaterialConsumptionDTO();
	        String subUnit1 = mtrlMstrObj.getSubUnitMstrodel().getSub_unit_name();
			if(subUnit1.substring(0, 3).equals(Constants.SUB_UNIT_EOF)){
			retQty = dayEofConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrl_id())).mapToDouble(o -> o.getQty()).sum();
			}else if(subUnit1.substring(0, 3).equals(Constants.SUB_UNIT_LRF)){
				for(LRFHeatConsumableModel lrfObj:dayLrfConsLi)
			    {
			     LRFHeatDetailsModel lrfHeatObj=lrfProdDao.getLRFHeatDetailsByHeatNo(lrfObj.getHeat_id(), lrfObj.getHeat_counter());
		         retQty = dayLrfConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrl_id())).mapToDouble(o -> o.getConsumption_qty()).sum();
			    }
			}else if(subUnit1.substring(0, 3).equals(Constants.SUB_UNIT_CCM)){
				retQty = dayCcmConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrl_id())).mapToDouble(o -> o.getQty()).sum();
			}
			    mtrlConsDto.setMtrlType(mtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
				mtrlConsDto.setMtrlName(mtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
				mtrlConsDto.setMtrlId(mtrlMstrObj.getMtrl_id());
				mtrlConsDto.setSapMtrlId(mtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
				mtrlConsDto.setUom(mtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
		        if(retQty>0) {
				mtrlConsDto.setConsQty(retQty);
				}
				mtrlConsDto.setValuationType(mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
				mtrlConsDto.setSubUnit(mtrlMstrObj.getSubUnitMstrodel().getUnitDetailsMstrMdl().getUnit_name());
				mtrlConsDto.setSubUnitId(mtrlMstrObj.getSub_unit_id());
			
			mtrlConsDto.setUpdateCounter(0);
			 
			retLi.add(mtrlConsDto);
			}
	  } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
		
	return retLi;
}
	
	@Override
	public String saveOrUpdateDayConsumptions(List<MaterialConsumptionDTO> mtrlConsDtoLi,String consDate,Integer totalHeatsProduced,Integer userId) {
		// TODO Auto-generated method stub
		Date dt = null;
		try {
			dt=new SimpleDateFormat("dd/MM/yyyy").parse(consDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//DateTimeFormatter df = DateTimeFormatter.ofPattern("MMM d yyyy");
		DecimalFormat formatter = new DecimalFormat("####.00");
		Map<String, List> saveUpdLi = new HashMap<String, List>();
		List<HeatStatusTrackingModel> heatLi=mtrlConsServ.getProdPostedHeats(consDate);
		List<EofHeatDetails> eofheatObjLi = null;
		List<HeatConsMaterialsDetails> eofMtrlConsSaveLi = null, eofMtrlConsUpdateLi = null;
		LRFHeatConsumableModel lrfMtrlConsSaveObj = null;
		List<LRFHeatConsumableModel> lrfMtrlConsSaveLi = null, lrfMtrlConsUpdateLi = null;
		List<CCMHeatConsMaterialsDetails> ccmMtrlConsSaveLi = null, ccmMtrlConsUpdateLi = null;
		ccmMtrlConsSaveLi = new ArrayList<CCMHeatConsMaterialsDetails>();
		ccmMtrlConsUpdateLi = new ArrayList<CCMHeatConsMaterialsDetails>();
		CCMHeatConsMaterialsDetails ccmMtrlConsSaveObj = null;
		CCMHeatConsMaterialsDetails ccmMtrlConsUpdateObj = null;
		List<CCMHeatConsMtlsDetailsLog> ccmConsLogLi = new ArrayList<CCMHeatConsMtlsDetailsLog>();
		eofMtrlConsSaveLi = new ArrayList<HeatConsMaterialsDetails>();
		eofMtrlConsUpdateLi = new ArrayList<HeatConsMaterialsDetails>();
		// HeatConsMaterialsDetails eofMtrlConsUpdateLi=null;
		lrfMtrlConsSaveLi = new ArrayList<LRFHeatConsumableModel>();
		lrfMtrlConsUpdateLi = new ArrayList<LRFHeatConsumableModel>();
		VDHeatConsumableModel vdMtrlConsSaveObj = null;
		List<VDHeatConsumableModel> vdMtrlConsSaveLi = null, vdMtrlConsUpdateLi = null;
		VDHeatConsumableModel vdMtrlConsupdateObj = null;
		vdMtrlConsSaveLi = new ArrayList<VDHeatConsumableModel>();
		vdMtrlConsUpdateLi = new ArrayList<VDHeatConsumableModel>();
		for(MaterialConsumptionDTO matl:mtrlConsDtoLi)
		{
			String subUnit = matl.getSubUnit();
			if(subUnit.substring(0, 3).equals(Constants.SUB_UNIT_EOF)){
				eofheatObjLi = new ArrayList<EofHeatDetails>();			 
				HeatConsMaterialsDetails eofMtrlConsSaveObj = null;
				HeatConsMaterialsDetails eofMtrlConsupdateObj=null;
				List<HeatConsMaterialsDetailsLog> eofConsLogLi = new ArrayList<HeatConsMaterialsDetailsLog>();
				if(matl.getUom().contains("EA"))
				{   
					double loopcount = 0;
					//Double qty=new Double(0);
					Double val1= (Double) ((null == matl.getConsQty()) ? 0 : matl.getConsQty());
					Double val2= (double) totalHeatsProduced;
					if(val1<=val2)
					{
						loopcount=1;
					}
					else if(val1>val2) {
						loopcount=val1/val2;
						loopcount=Math.ceil(loopcount);
					}
					Map<Map<String,Integer>,Long> mapObj = new HashMap<Map<String,Integer>, Long>();
					Double consNos = val1;
					for(int j=0;j<loopcount;j++)
					{
						//long heatvalue=0;
						for(HeatStatusTrackingModel heat:heatLi)
						{	 						  
							if(consNos.doubleValue() > 0)
							{
								Map<String,Integer> heatObj = new HashMap<String, Integer>();
								heatObj.put(heat.getHeat_id(), heat.getHeat_counter());
								if((mapObj.get(heatObj)) != null)
								{
									mapObj.put(heatObj,mapObj.get(heatObj) + (long) 1);
									consNos = consNos - 1;
								}
								else
								{
									mapObj.put(heatObj,(long) 1);
									consNos = consNos - 1;
								}
							}
						}					  

					}

					for(HeatStatusTrackingModel heat:heatLi)
					{
						Map<String,Integer> heatObj = new HashMap<String, Integer>();
						EofHeatDetails  eofHeatObj = eofProdDao.getEOFHeatDetailsByHeatNo(heat.getHeat_id());
						heatObj.put(heat.getHeat_id(), heat.getHeat_counter());
						if(ccmMtrlConsUpdateObj == null ) {
							if(mapObj.get(heatObj) != null) {
								if(eofMtrlConsupdateObj == null) {
									eofMtrlConsSaveObj = new HeatConsMaterialsDetails();
									eofMtrlConsSaveObj.setTrns_eof_si_no(eofHeatObj.getTrns_si_no());
									eofMtrlConsSaveObj.setMaterial_id(matl.getMtrlId());
									eofMtrlConsSaveObj.setQty(new Double(mapObj.get(heatObj)));
									eofMtrlConsSaveObj.setConsumption_date(dt);
									eofMtrlConsSaveObj.setCreatedBy(userId);
									eofMtrlConsSaveObj.setCreatedDateTime(new Date());
									eofMtrlConsSaveObj.setRecord_status(1);
									eofMtrlConsSaveLi.add(eofMtrlConsSaveObj);
								}
							}
							else {
								eofMtrlConsupdateObj.setQty(new Double(mapObj.get(heatObj)));
								eofMtrlConsupdateObj.setUpdatedBy(userId);
								eofMtrlConsupdateObj.setConsumption_date(dt);
								eofMtrlConsupdateObj.setUpdatedDateTime(new Date());
								eofMtrlConsUpdateLi.add(eofMtrlConsupdateObj);
							}
						}
					}	 
				}
				else {  
					for(HeatStatusTrackingModel heat:heatLi)
					{	
						EofHeatDetails  eofHeatObj = eofProdDao.getEOFHeatDetailsByHeatNo(heat.getHeat_id());	
						if(eofHeatObj.equals(null))
						{
							totalHeatsProduced = totalHeatsProduced - 1;
						}
						if(eofHeatObj != null)
						{
							eofheatObjLi.add(eofHeatObj);
							try {
								Double qty=new Double(0);
								Double val1= (Double) ((null == matl.getConsQty()) ? 0 : matl.getConsQty());
								Double val2= (double) totalHeatsProduced;
								if(val1 > 0 && val2 > 0) {
									qty=(double) val1/val2;
									qty=new Double(formatter.format(qty));
									eofMtrlConsupdateObj = mtrlConsDao.getEofMtrlConsByMtrlId(eofHeatObj.getTrns_si_no(), matl.getMtrlId());
									if(eofMtrlConsupdateObj == null) {
										eofMtrlConsSaveObj = new HeatConsMaterialsDetails();
										eofMtrlConsSaveObj.setTrns_eof_si_no(eofHeatObj.getTrns_si_no());
										eofMtrlConsSaveObj.setMaterial_id(matl.getMtrlId());
										eofMtrlConsSaveObj.setQty(qty);
										eofMtrlConsSaveObj.setConsumption_date(dt);
										eofMtrlConsSaveObj.setCreatedBy(userId);
										eofMtrlConsSaveObj.setCreatedDateTime(new Date());
										eofMtrlConsSaveObj.setRecord_status(1);
										eofMtrlConsSaveLi.add(eofMtrlConsSaveObj);
									}else {
										eofMtrlConsupdateObj.setQty(qty);
										eofMtrlConsupdateObj.setUpdatedBy(userId);
										eofMtrlConsupdateObj.setConsumption_date(dt);
										eofMtrlConsupdateObj.setUpdatedDateTime(new Date());
										eofMtrlConsUpdateLi.add(eofMtrlConsupdateObj);

									}
								}
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				//saveUpdLi.put("UPDATE_EOF_HEAT", eofheatObjLi);
				saveUpdLi.put("SAVE_EOFMTRL_CONS", eofMtrlConsSaveLi);
				saveUpdLi.put("UPDATE_EOFMTRL_CONS", eofMtrlConsUpdateLi);
				saveUpdLi.put("SAVE_EOF_CONS_LOG", eofConsLogLi);

			}
			else if(subUnit.substring(0, 3).equals(Constants.SUB_UNIT_LRF)) {			
				List<LRFHeatConsumableLogModel> lrfConsLogLi = new ArrayList<LRFHeatConsumableLogModel>();
				List<LRFHeatConsumableModel> lrfMtrlConsLi = new ArrayList<LRFHeatConsumableModel>();
				List<LRFHeatArcingDetailsModel> lrfArcingSaveLi = new ArrayList<LRFHeatArcingDetailsModel>();
				Double qtyHeat=new Double(0);
				Double matlQty= (Double) ((null == matl.getConsQty()) ? 0 : matl.getConsQty());
				Double totalHeatsProducedLRF= (double) totalHeatsProduced;
				if(matlQty > 0 && totalHeatsProducedLRF > 0) {
					qtyHeat= (double) matlQty/ totalHeatsProducedLRF;
					qtyHeat=new Double(formatter.format(qtyHeat));
					for(HeatStatusTrackingModel heat:heatLi)
					{
						try {		
							List<LRFHeatConsumableModel> lrfConsLi = mtrlConsDao.getLrfMtrlConsumptions(heat.getHeat_id(), heat.getHeat_counter());
							Integer totalArchingProducedCount = 0;
							Integer lrfArcingId = 0;
							Integer heatCounter = 0;
							Integer lrfConsLiSize = 0;
							if(!lrfConsLi.isEmpty())
							{
								totalArchingProducedCount = new Integer(lrfConsLi.size());
								lrfArcingId = ((null == lrfConsLi.get(0).getArc_sl_no()) ? 0 : lrfConsLi.get(0).getArc_sl_no());
								heatCounter = ((null == lrfConsLi.get(0).getHeat_counter()) ? 0 : lrfConsLi.get(0).getHeat_counter());
							}
							List<LRFHeatConsumableModel> lrfConsLiMtrl = new ArrayList<LRFHeatConsumableModel>();
							lrfConsLiMtrl =	mtrlConsDao.getLrfMtrlConsByCtrlMtrlId(heat.getHeat_id(),heat.getHeat_counter(),matl.getMtrlId());
							int consMtrlCount = 0;
							try
							{
								for (Iterator<LRFHeatConsumableModel> iterator = lrfConsLiMtrl.iterator(); iterator.hasNext();) {
									LRFHeatConsumableModel cnsMtrl = iterator.next();
									consMtrlCount = consMtrlCount + 1;
								}
							}
							catch(NullPointerException e)
							{
								consMtrlCount = 0;
							}

							if(consMtrlCount > 0)
							{
								totalArchingProducedCount = new Integer(lrfConsLiMtrl.size());
								Double qtyArc=new Double(0);
								Double matlArcQty= (Double) ((null == matlQty) ? 0 : matlQty);
								Double totalArcingProducedLrf= (double) totalArchingProducedCount;
								if(matlQty > 0 && totalHeatsProducedLRF > 0) {
									qtyArc = (double) qtyHeat/totalArcingProducedLrf;
									qtyArc=new Double(formatter.format(qtyArc));
								}
								for(LRFHeatConsumableModel consObj : lrfConsLiMtrl) {
									qtyArc = ((null == qtyArc) ? 0 : qtyArc);
									if(qtyArc > 0)
									{
										consObj.setConsumption_qty(qtyArc);
										consObj.setUpdated_by(userId);
										consObj.setUpdated_date_time(new Date());
									}
									lrfMtrlConsUpdateLi.add(consObj);
									LRFHeatConsumableLogModel lrfConsLogObj = mtrlConsDao.getLrfHeatConsLogByConsId(consObj.getCons_sl_no());
									if(lrfConsLogObj == null) {
										lrfConsLogObj = new LRFHeatConsumableLogModel();
										lrfConsLogObj.setCons_sl_no(consObj.getCons_sl_no());
										lrfConsLogObj.setArc_sl_no(consObj.getArc_sl_no());
										lrfConsLogObj.setHeat_id(consObj.getHeat_id());
										lrfConsLogObj.setHeat_counter(consObj.getHeat_counter());
										lrfConsLogObj.setMaterial_type(consObj.getMaterial_type());
										lrfConsLogObj.setMaterial_id(consObj.getMaterial_id());
										lrfConsLogObj.setConsumption_qty(consObj.getConsumption_qty());
										lrfConsLogObj.setAddition_date_time(dt);
										lrfConsLogObj.setCreated_by(consObj.getCreated_by());
										lrfConsLogObj.setCreated_date_time(consObj.getCreated_date_time());
										lrfConsLogObj.setSap_matl_id(consObj.getSap_matl_id());
										lrfConsLogObj.setValuation_type(consObj.getValuation_type());
										lrfConsLogObj.setUpdated_by(consObj.getUpdated_by());
										lrfConsLogObj.setUpdated_date_time(consObj.getUpdated_date_time());
										lrfConsLogObj.setCreatedHistBy(userId);
										lrfConsLogObj.setCreatedHistDateTime(new Date());
										lrfConsLogLi.add(lrfConsLogObj);
									}
									lrfMtrlConsLi.add(consObj);
								}

							}
							else
							{
								LRFHeatConsumableModel consObj = new LRFHeatConsumableModel();
								consObj.setCons_sl_no(null);
								consObj.setHeat_id(heat.getHeat_id());
								consObj.setArc_sl_no(lrfArcingId);
								consObj.setHeat_counter(heat.getHeat_counter());
								consObj.setMaterial_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.LRF_ARC_ADDITIONS+"' and lookup_status=1"));
								consObj.setMaterial_id(matl.getMtrlId());
								consObj.setConsumption_qty(qtyHeat);
								consObj.setCreated_by(userId);
								consObj.setSap_matl_id(matl.getSapMtrlId());
								consObj.setValuation_type(matl.getValuationType());
								consObj.setAddition_date_time(dt);
								consObj.setCreated_date_time(new Date());
								consObj.setRecord_status(1);
								consObj.setUpdated_by(userId);
								consObj.setUpdated_date_time(new Date());
								lrfMtrlConsSaveLi.add(consObj);
							}			   
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						saveUpdLi.put("SAVE_LRFMTRL_CONS", lrfMtrlConsSaveLi);
						saveUpdLi.put("UPDATE_LRFMTRL_CONS", lrfMtrlConsLi);
						//saveUpdLi.put("SAVE_LRF_ARCING", lrfArcingSaveLi);
						saveUpdLi.put("SAVE_LRF_CONS_LOG", lrfConsLogLi);
					}
				}
			}

			else if(subUnit.substring(0, 2).equals(Constants.SUB_UNIT_VD)) {
				String vdConsHdrSave = "N", heatId = null;
				Integer heatCounter = null;
				VdAdditionsDetailsModel vdConsHdrSaveObj;
				for(HeatStatusTrackingModel heat:heatLi)
				{	
					List<VdAdditionsDetailsModel> vdConsHdrSaveLi = new ArrayList<VdAdditionsDetailsModel>();
					VDHeatDetailsModel vdHeatObj = vdProdDao.getVDHeatDetailsByHeatNo(heat.getHeat_id());
					List<VDHeatConsumableModelLog> vdConsLogLi = new ArrayList<VDHeatConsumableModelLog>();
					try {
						Double qty=new Double(0);
						Double val1= (Double) ((null == matl.getConsQty()) ? 0 : matl.getConsQty());
						Double val2= (double) totalHeatsProduced;
						if(val1 > 0 && val2 > 0) {
							System.out.println("val1 = "+val1+" and val2="+ val2);
							qty=(double) val1/val2;
							qty=new Double(formatter.format(qty));
							vdMtrlConsupdateObj = mtrlConsDao.getVdMtrlConsByMtrlId(vdHeatObj.getHeat_id(), matl.getMtrlId());
							if(vdMtrlConsupdateObj == null) {
								vdMtrlConsSaveObj = new VDHeatConsumableModel();

								vdMtrlConsSaveObj.setArc_sl_no(vdHeatObj.getTrns_si_no());
								vdMtrlConsSaveObj.setHeat_id(heatId);
								vdMtrlConsSaveObj.setHeat_counter(heatCounter);
								vdMtrlConsSaveObj.setMaterial_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.VD_ADDITIONS+"' and lookup_status=1"));
								vdMtrlConsSaveObj.setMaterial_id(matl.getMtrlId());
								vdMtrlConsSaveObj.setConsumption_qty(qty);
								vdMtrlConsSaveObj.setAddition_date_time(dt);
								vdMtrlConsSaveObj.setRecord_status(1);
								vdMtrlConsSaveObj.setCreated_by(userId);
								vdMtrlConsSaveObj.setCreated_date_time(new Date());
								//vdMtrlConsSaveObj.setSap_matl_id(dayConsDtoObj.getSapMtrlId());
								//vdMtrlConsSaveObj.setValuation_type(dayConsDtoObj.getValuationType().toString());

								vdMtrlConsSaveLi.add(vdMtrlConsSaveObj);
							}else {
								vdMtrlConsupdateObj.setConsumption_qty(qty);
								vdMtrlConsupdateObj.setUpdated_by(userId);
								vdMtrlConsupdateObj.setUpdated_date_time(new Date());

								vdMtrlConsUpdateLi.add(vdMtrlConsupdateObj);
							}
						}
					}

					//}
					catch(Exception e) {
						e.printStackTrace();
					}
				}

				saveUpdLi.put("SAVE_VDMTRL_CONS", vdMtrlConsSaveLi);
				saveUpdLi.put("UPDATE_VDMTRL_CONS", vdMtrlConsUpdateLi);
				//saveUpdLi.put("SAVE_VD_ADDITION", vdConsHdrSaveLi);
				//saveUpdLi.put("SAVE_VD_CONS_LOG", vdConsLogLi);
			}

			if(subUnit.substring(0, 3).equals(Constants.SUB_UNIT_CCM)) {
				if(matl.getUom().contains("EA"))
				{   
					double loopcount = 0;
					Double qty=new Double(0);
					Double val1= (Double) ((null == matl.getConsQty()) ? 0 : matl.getConsQty());
					Double val2= (double) totalHeatsProduced;
					if(val1<=val2)
					{
						loopcount=1;
					}
					else if(val1>val2) {
						loopcount=val1/val2;
						loopcount=Math.ceil(loopcount);
					}
					Map<Map<String,Integer>,Long> mapObj = new HashMap<Map<String,Integer>, Long>();
					Double consNos = val1;
					for(int j=0;j<loopcount;j++)
					{
						long heatvalue=0;
						for(HeatStatusTrackingModel heat:heatLi)
						{	 						  
							if(consNos.doubleValue() > 0)
							{
								Map<String,Integer> heatObj = new HashMap<String, Integer>();
								heatObj.put(heat.getHeat_id(), heat.getHeat_counter());
								if((mapObj.get(heatObj)) != null)
								{
									mapObj.put(heatObj,mapObj.get(heatObj) + (long) 1);
									consNos = consNos - 1;
								}
								else
								{
									mapObj.put(heatObj,(long) 1);
									consNos = consNos - 1;
								}
							}
						}					  

					}

					for(HeatStatusTrackingModel heat:heatLi)
					{
						Map<String,Integer> heatObj = new HashMap<String, Integer>();
						CCMHeatDetailsModel ccmHeatObj = ccmProdDao.getHeatDtlsByHeatId(heat.getHeat_id(),heat.getHeat_counter());
						ccmMtrlConsUpdateObj =	mtrlConsDao.getCcmMtrlConsByMtrlId(ccmHeatObj.getTrns_sl_no(), matl.getMtrlId());
						heatObj.put(heat.getHeat_id(), heat.getHeat_counter());
						if(ccmMtrlConsUpdateObj == null ) {
							if(mapObj.get(heatObj) != null) {
								ccmMtrlConsSaveObj = new CCMHeatConsMaterialsDetails();
								ccmMtrlConsSaveObj.setTrns_ccm_si_no(ccmHeatObj.getTrns_sl_no());
								ccmMtrlConsSaveObj.setMaterial_id(matl.getMtrlId());
								ccmMtrlConsSaveObj.setQty(new Double(mapObj.get(heatObj)));
								ccmMtrlConsSaveObj.setConsumption_date(dt);
								ccmMtrlConsSaveObj.setCreatedBy(userId);
								ccmMtrlConsSaveObj.setCreatedDateTime(new Date());
								ccmMtrlConsSaveObj.setRecord_status(1);
								ccmMtrlConsSaveLi.add(ccmMtrlConsSaveObj);
							}
						}
						else {
							if(mapObj.get(heatObj) != null) {
								ccmMtrlConsUpdateObj.setQty(new Double(mapObj.get(heatObj)));
								ccmMtrlConsUpdateObj.setUpdatedBy(userId);
								ccmMtrlConsUpdateObj.setConsumption_date(dt);
								ccmMtrlConsUpdateObj.setUpdatedDateTime(new Date());
								ccmMtrlConsUpdateLi.add(ccmMtrlConsUpdateObj);
							}
						}
					}

				}
				else {
					for(HeatStatusTrackingModel heat:heatLi)
					{	
						CCMHeatDetailsModel ccmHeatObj = ccmProdDao.getHeatDtlsByHeatId(heat.getHeat_id(),heat.getHeat_counter());
						try {
							Double qty=new Double(0);
							Double val1= (Double) ((null == matl.getConsQty()) ? 0 : matl.getConsQty());
							Double val2= (double) totalHeatsProduced;
							if(val1 > 0 && val2 > 0) {
								qty= (double) val1/val2;
								qty=new Double(formatter.format(qty));
								ccmMtrlConsUpdateObj = new CCMHeatConsMaterialsDetails();
								ccmMtrlConsUpdateObj =	mtrlConsDao.getCcmMtrlConsByMtrlId(ccmHeatObj.getTrns_sl_no(), matl.getMtrlId());
								if(ccmMtrlConsUpdateObj == null) {
									ccmMtrlConsSaveObj = new CCMHeatConsMaterialsDetails();
									ccmMtrlConsSaveObj.setTrns_ccm_si_no(ccmHeatObj.getTrns_sl_no());
									ccmMtrlConsSaveObj.setMaterial_id(matl.getMtrlId());
									ccmMtrlConsSaveObj.setQty(qty);
									ccmMtrlConsSaveObj.setConsumption_date(dt);
									ccmMtrlConsSaveObj.setCreatedBy(userId);
									ccmMtrlConsSaveObj.setCreatedDateTime(new Date());
									ccmMtrlConsSaveObj.setRecord_status(1);
									ccmMtrlConsSaveLi.add(ccmMtrlConsSaveObj);
								}
								else {
									ccmMtrlConsUpdateObj.setQty(qty);
									ccmMtrlConsUpdateObj.setUpdatedBy(userId);
									ccmMtrlConsUpdateObj.setConsumption_date(dt);
									ccmMtrlConsUpdateObj.setUpdatedDateTime(new Date());
									ccmMtrlConsUpdateLi.add(ccmMtrlConsUpdateObj);
								}
							}

						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				saveUpdLi.put("SAVE_CCMMTRL_CONS", ccmMtrlConsSaveLi);
				saveUpdLi.put("UPDATE_CCMMTRL_CONS", ccmMtrlConsUpdateLi);
				saveUpdLi.put("SAVE_CCM_CONS_LOG", ccmConsLogLi);
			}
		}
		return daywiseMtrlConsDao.saveOrUpdateMtrlConsumptions(saveUpdLi);
	}

	@Transactional
	@Override
	public boolean isConsumptionPosted(String cons_date) {
		// TODO Auto-generated method stub
		return daywiseMtrlConsDao.isConsumptionPosted(cons_date);
	}
}