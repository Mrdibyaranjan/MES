package com.smes.trans.service.impl;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.dao.impl.CommonDao;
import com.smes.masters.dao.impl.LookupMasterDao;
import com.smes.masters.dao.impl.MaterialMapMasterDao;
import com.smes.masters.dao.impl.SubUnitMasterDao;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.MaterialSubUnitMapMstrModel;
import com.smes.masters.model.MtrlProcessConsumableMstrModel;
import com.smes.masters.model.SubUnitMasterModel;
import com.smes.trans.dao.impl.CasterProductionDao;
import com.smes.trans.dao.impl.DaywiseMaterialConsDao;
import com.smes.trans.dao.impl.EofProductionDao;
import com.smes.trans.dao.impl.HeatPlanDetailsDao;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.dao.impl.HotMetalReceiveDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.dao.impl.MaterialConsumptionDao;
import com.smes.trans.dao.impl.ScrapEntryDao;
import com.smes.trans.dao.impl.VDProductionDao;
import com.smes.trans.model.CCMBatchDetailsModel;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatConsMtlsDetailsLog;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.CCMProductDetailsModel;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsMaterialsDetailsLog;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.IntfMaterialConsumptionModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.ScrapBucketDetails;
import com.smes.trans.model.ScrapBucketDetailsLog;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatConsumableModelLog;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.Constants;
import com.smes.util.DataConversion;
import com.smes.util.MaterialConsumptionDTO;

@Service("MaterialConsumptionService")
public class MaterialConsumptionServiceImpl implements MaterialConsumptionService {

	@Autowired
	private MaterialConsumptionDao mtrlConsDao;

	@Autowired
	private MaterialMapMasterDao mtrlMapMstrDao;

	@Autowired
	private EofProductionDao eofProdDao;

	@Autowired 
	private HeatProceeEventDao heatProcessDao;

	@Autowired
	private HotMetalReceiveDao hmReceiveDao;

	@Autowired
	private ScrapEntryDao scrapDtlDao;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private ScrapEntryDao scrapHdrDao;

	@Autowired
	private LRFProductionDao lrfProdDao;

	@Autowired
	private VDProductionDao vdProdDao;

	@Autowired
	private CasterProductionDao ccmProdDao;

	@Autowired
	private DaywiseMaterialConsDao daywiseMtrlConsDao;

	@Autowired
	private LookupMasterDao lkpMasterDao;

	@Autowired
	private SubUnitMasterDao subUnitMstrDao;

	@Autowired
	private HeatPlanDetailsDao heatPlanDao;

	@Transactional
	@Override
	public List<HeatStatusTrackingModel> getProdPostedHeats(String cons_date) {
		// TODO Auto-generated method stub
		return mtrlConsDao.getProdPostedHeats(cons_date);
	}

	@Transactional
	@Override 
	//public List<List<MaterialConsumptionDTO>> getProdUnitwiseMtrlCons(MaterialConsumptionDTO ipParamDTO, String cons_date) {
	public List<List<MaterialConsumptionDTO>> getProdUnitwiseMtrlCons(String heatNo, String subUnitName, Integer subUnitId, Integer heatTrackId, Double heatQty, Integer totalHeats, String cons_date) {
		// TODO Auto-generated method stub
		MaterialConsumptionDTO mtrlConsDto = null;
		List<MaterialConsumptionDTO> retLi = new ArrayList<MaterialConsumptionDTO>();
		List<MaterialConsumptionDTO> formulaLi = new ArrayList<MaterialConsumptionDTO>();
		List<List<MaterialConsumptionDTO>> finalLi = new ArrayList<List<MaterialConsumptionDTO>>();
		try {
			List<MaterialSubUnitMapMstrModel> unitwiseMtrlMstrLi = mtrlMapMstrDao.getMatlDetailsBySubUnitAndMtrlType(Constants.MTRL_CONS_HEAT, subUnitId);
			//List<MaterialSubUnitMapMstrModel> daymtrlMstrLi = mtrlMapMstrDao.getMatlDetailsBySubUnitAndMtrlType(Constants.MTRL_CONS_DAY,subUnitId);
			String unitname="";
			unitname=mtrlMapMstrDao.getunitName(subUnitId);
			List<MaterialSubUnitMapMstrModel> daymtrlMstrLi = mtrlMapMstrDao.getMatlDetailsByMtrlType(Constants.MTRL_CONS_DAY,unitname);
			DecimalFormat df= new DecimalFormat("#.###");
			df.setRoundingMode(RoundingMode.CEILING);

			if(subUnitName.substring(0, 3).equals(Constants.SUB_UNIT_EOF)){
				List<MtrlProcessConsumableMstrModel> scrapMtrlMstrLi = commonDao.getProcConsumablesByTypeAndCode(Constants.MATERIAL_TYPE, Constants.MATERIAL_TYPE_SCRAP);
				EofHeatDetails  eofHeatObj = eofProdDao.getEOFHeatDetailsByHeatNo(heatNo);
				List<HeatConsMaterialsDetails> eofMtrlConsLi = heatProcessDao.getHeatConsMtrlsDtlsByTrnsId(eofHeatObj.getTrns_si_no());
				List<HeatConsScrapMtrlDetails> eofScrapConsLi = eofProdDao.getHeatScrapConsumptionByEofId(eofHeatObj.getTrns_si_no());
				HmReceiveBaseDetails hmReceieptObj = hmReceiveDao.getHmReceiveDetailsById(eofHeatObj.getHm_receipt_id());
				List<ScrapBucketDetails> scrapBucketDetLi = null;
				if(eofScrapConsLi.size() > 0) {
					scrapBucketDetLi = scrapDtlDao.getScrapBktDetList(eofScrapConsLi.get(0).getScrap_bkt_header_id());
				}

				List<MtrlProcessConsumableMstrModel> hmMtrlMstrLi = commonDao.getProcConsumablesByTypeAndCode(Constants.MATERIAL_TYPE, Constants.MATERIAL_TYPE_HM);
				MtrlProcessConsumableMstrModel hmMstrObj = hmMtrlMstrLi.get(0);
				mtrlConsDto = new MaterialConsumptionDTO();
				mtrlConsDto.setMtrlType(hmMstrObj.getMtrlTypeLkpModel().getLookup_code());
				mtrlConsDto.setMtrlName(hmMstrObj.getMaterial_desc());
				mtrlConsDto.setMtrlId(hmMstrObj.getMaterial_id());
				mtrlConsDto.setSapMtrlId(hmMstrObj.getSap_matl_desc());
				mtrlConsDto.setUom(hmMstrObj.getUomLkpModel().getLookup_code());
				if(hmMstrObj.getSapuomLkpModel() != null)
				{
					mtrlConsDto.setSapuom(hmMstrObj.getSapuomLkpModel().getLookup_code());
				}

				mtrlConsDto.setConsQty(hmReceieptObj.getHmLadleGrossWt());
				mtrlConsDto.setMtrlConsSlNo(hmReceieptObj.getHmRecvId());
				mtrlConsDto.setValuationType((null == hmMstrObj.getValTypeLkpModel()) ? null : hmMstrObj.getValTypeLkpModel().getLookup_code());
				mtrlConsDto.setUpdateCounter(0);
				mtrlConsDto.setTrnsSlNo(eofHeatObj.getTrns_si_no());
				retLi.add(mtrlConsDto);
				for(MtrlProcessConsumableMstrModel scrapMtrlMstrObj : scrapMtrlMstrLi) {
					ScrapBucketDetails scrapConsObj = null;
					List<ScrapBucketDetails> scrapConsObjLi = null;
					if(scrapBucketDetLi != null)
					{
						scrapConsObjLi = scrapBucketDetLi.stream().filter(p -> p.getMaterial_id().equals(scrapMtrlMstrObj.getMaterial_id())).collect(Collectors.toList());
						if(scrapConsObjLi.size() > 0)
						scrapConsObj = scrapConsObjLi.get(0);
					}
					
					mtrlConsDto = new MaterialConsumptionDTO();
					if(scrapConsObj != null) {
						mtrlConsDto.setConsQty(scrapConsObj.getMaterial_qty());
						mtrlConsDto.setMtrlConsSlNo(scrapConsObj.getScrap_bkt_detail_id());
					}else {
						mtrlConsDto.setConsQty(null);
						mtrlConsDto.setMtrlConsSlNo(null);
					}
					mtrlConsDto.setMtrlType(scrapMtrlMstrObj.getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(scrapMtrlMstrObj.getMaterial_desc());
					mtrlConsDto.setMtrlId(scrapMtrlMstrObj.getMaterial_id());
					mtrlConsDto.setSapMtrlId(scrapMtrlMstrObj.getSap_matl_desc());
					mtrlConsDto.setUom(scrapMtrlMstrObj.getUomLkpModel().getLookup_code());
					if(scrapMtrlMstrObj.getSapuomLkpModel() != null)
					{
						mtrlConsDto.setSapuom(scrapMtrlMstrObj.getSapuomLkpModel().getLookup_code());
					}
					mtrlConsDto.setValuationType((null == scrapMtrlMstrObj.getValTypeLkpModel()) ? null : scrapMtrlMstrObj.getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);
					mtrlConsDto.setTrnsSlNo(eofHeatObj.getTrns_si_no());
					retLi.add(mtrlConsDto);
				}
				for(MaterialSubUnitMapMstrModel mtrlMstrObj : unitwiseMtrlMstrLi) {
					HeatConsMaterialsDetails eofConsObj = null;
					List<HeatConsMaterialsDetails> eofConsObjLi = null;
					eofConsObjLi = eofMtrlConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrlMstrodel().getMaterial_id())).collect(Collectors.toList());
					if(eofConsObjLi.size() > 0)
						eofConsObj = eofConsObjLi.get(0);

					mtrlConsDto = new MaterialConsumptionDTO();
					if(eofConsObj != null) {
						mtrlConsDto.setConsQty(eofConsObj.getQty());
						mtrlConsDto.setMtrlConsSlNo(eofConsObj.getMtr_cons_si_no());
					}else {
						mtrlConsDto.setConsQty(null);
						mtrlConsDto.setMtrlConsSlNo(null);
					}
					mtrlConsDto.setMtrlType(mtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(mtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
					mtrlConsDto.setMtrlId(mtrlMstrObj.getMtrlMstrodel().getMaterial_id());
					mtrlConsDto.setSapMtrlId(mtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
					mtrlConsDto.setUom(mtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					if(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel() != null)
					{
						mtrlConsDto.setSapuom(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
					}

					mtrlConsDto.setValuationType((null == mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);
					mtrlConsDto.setTrnsSlNo(eofHeatObj.getTrns_si_no());
					retLi.add(mtrlConsDto);
				}
				finalLi.add(retLi);

				/*for(MaterialSubUnitMapMstrModel dayMtrlObj : dayMtrlMstrLi) {
					HeatConsMaterialsDetails eofConsObj = null;
					mtrlConsDto = new MaterialConsumptionDTO();
					mtrlConsDto.setConsQty(Double.parseDouble(df.format(dayMtrlObj.getMtrlMstrodel().getHistor_matl_id() * heatQty)));
					mtrlConsDto.setMtrlConsSlNo(null);
					mtrlConsDto.setMtrlType(dayMtrlObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(dayMtrlObj.getMtrlMstrodel().getMaterial_desc());
					mtrlConsDto.setMtrlId(dayMtrlObj.getMtrlMstrodel().getMaterial_id());
					mtrlConsDto.setSapMtrlId(dayMtrlObj.getMtrlMstrodel().getSap_matl_desc());
					mtrlConsDto.setUom(dayMtrlObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					mtrlConsDto.setValuationType((null == dayMtrlObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : dayMtrlObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);
					mtrlConsDto.setTrnsSlNo(eofHeatObj.getTrns_si_no());
				    formulaLi.add(mtrlConsDto);
				}*/
				for(MaterialSubUnitMapMstrModel dayMtrlMstrObj : daymtrlMstrLi) {
					MaterialConsumptionDTO mtrlConsDto1=new MaterialConsumptionDTO();
					List<HeatConsMaterialsDetails> EofQtyLi=null;
					List<HeatConsMaterialsDetails> dayEofConsLi =daywiseMtrlConsDao.getEofConsQty(cons_date, Constants.MTRL_CONS_DAY);
					EofQtyLi = dayEofConsLi.stream().filter(p -> p.getMaterial_id().equals(dayMtrlMstrObj.getMtrl_id())).collect(Collectors.toList());
					if(dayMtrlMstrObj != null) {
						for(HeatConsMaterialsDetails eofConsObj : EofQtyLi) {
							mtrlConsDto1.setConsQty(eofConsObj.getQty());
						}
						mtrlConsDto1.setMtrlConsSlNo(null);
						mtrlConsDto1.setMtrlType(dayMtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
						mtrlConsDto1.setMtrlName(dayMtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
						mtrlConsDto1.setMtrlId(dayMtrlMstrObj.getMtrlMstrodel().getMaterial_id());
						mtrlConsDto1.setSapMtrlId(dayMtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
						mtrlConsDto1.setUom(dayMtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
						mtrlConsDto1.setValuationType((null == dayMtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : dayMtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
						mtrlConsDto1.setUpdateCounter(0);
						mtrlConsDto1.setTrnsSlNo(eofHeatObj.getTrns_si_no());
						formulaLi.add(mtrlConsDto1);
					}
				}
				finalLi.add(formulaLi);
			}else if(subUnitName.substring(0, 3).equals(Constants.SUB_UNIT_LRF)) {
				List<LRFHeatDetailsModel> lrfHeatObjLi = mtrlConsDao.getLrfHeatDetailObj(heatNo, subUnitId);
				List<LRFHeatConsumableModel> lrfMtrlConsLi = new ArrayList<LRFHeatConsumableModel>();
				for(LRFHeatDetailsModel lrfHeatObj : lrfHeatObjLi) {
					List<LRFHeatConsumableModel> lrfConsLi = mtrlConsDao.getLrfMtrlConsumptions(lrfHeatObj.getHeat_id(), lrfHeatObj.getHeat_counter());
					for(LRFHeatConsumableModel consObj : lrfConsLi) {
						lrfMtrlConsLi.add(consObj);
					}
				}
				Integer lrfArcingId = null, heatCounter = null;
				if(lrfMtrlConsLi.size() > 0) {
					lrfArcingId = lrfMtrlConsLi.get(0).getArc_sl_no();
					heatCounter = lrfMtrlConsLi.get(0).getHeat_counter();
				}
				for(MaterialSubUnitMapMstrModel mtrlMstrObj : unitwiseMtrlMstrLi) {
					Double totalConsumedQty = null;
					List<LRFHeatConsumableModel> lrfConsObjLi = null;
					lrfConsObjLi = lrfMtrlConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrlMstrodel().getMaterial_id())).collect(Collectors.toList());
					if(lrfConsObjLi.size() > 0) {
						totalConsumedQty = lrfConsObjLi.stream().mapToDouble(o -> o.getConsumption_qty()).sum();
						for(LRFHeatConsumableModel lrfConsObj : lrfConsObjLi) {
							mtrlConsDto = new MaterialConsumptionDTO();
							mtrlConsDto.setConsQty(totalConsumedQty);
							mtrlConsDto.setMtrlConsSlNo(lrfConsObj.getCons_sl_no());
							mtrlConsDto.setTrnsSlNo(lrfConsObj.getArc_sl_no());
							mtrlConsDto.setMtrlType(mtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
							mtrlConsDto.setMtrlName(mtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
							mtrlConsDto.setMtrlId(mtrlMstrObj.getMtrlMstrodel().getMaterial_id());
							mtrlConsDto.setSapMtrlId(mtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
							mtrlConsDto.setUom(mtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());

							if(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel() != null)
							{
								mtrlConsDto.setSapuom(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
							}

							mtrlConsDto.setValuationType((null == mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
							mtrlConsDto.setUpdateCounter(0);

							retLi.add(mtrlConsDto);
						}
					}else {
						mtrlConsDto = new MaterialConsumptionDTO();
						mtrlConsDto.setConsQty(null);
						mtrlConsDto.setMtrlConsSlNo(null);
						mtrlConsDto.setHeatNo(heatNo);
						mtrlConsDto.setHeatCounter(heatCounter);
						mtrlConsDto.setTrnsSlNo(lrfArcingId);
						mtrlConsDto.setMtrlType(mtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
						mtrlConsDto.setMtrlName(mtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
						mtrlConsDto.setMtrlId(mtrlMstrObj.getMtrlMstrodel().getMaterial_id());
						mtrlConsDto.setSapMtrlId(mtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
						mtrlConsDto.setUom(mtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
						if(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel() != null)
						{
							mtrlConsDto.setSapuom(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
						}

						mtrlConsDto.setValuationType((null == mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
						mtrlConsDto.setUpdateCounter(0);

						retLi.add(mtrlConsDto);
					}
				}
				finalLi.add(retLi);
				/*for(MaterialSubUnitMapMstrModel dayMtrlObj : dayMtrlMstrLi) {
					DaywiseMaterialConsModel dayConsObj = null;
					List<DaywiseMaterialConsModel> dayConsObjLi = null;
					dayConsObjLi = dayConsLi.stream().filter(p -> p.getMaterial_id().equals(dayMtrlObj.getMtrlMstrodel().getMaterial_id())).collect(Collectors.toList());
					if(dayConsObjLi.size() > 0)
						dayConsObj = dayConsObjLi.get(0);

					mtrlConsDto = new MaterialConsumptionDTO();
					if(dayConsObj != null && dayConsObj.getQty() != null && !dayConsObj.equals(0)) {
						mtrlConsDto.setConsQty(dayConsObj.getQty() / totalHeats);
					}else {
						mtrlConsDto.setConsQty(null);
					}
					mtrlConsDto.setMtrlConsSlNo(null);
					mtrlConsDto.setMtrlType(dayMtrlObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(dayMtrlObj.getMtrlMstrodel().getMaterial_desc());
					mtrlConsDto.setMtrlId(dayMtrlObj.getMtrlMstrodel().getMaterial_id());
					mtrlConsDto.setSapMtrlId(dayMtrlObj.getMtrlMstrodel().getSap_matl_desc());
					mtrlConsDto.setUom(dayMtrlObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					mtrlConsDto.setValuationType((null == dayMtrlObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : dayMtrlObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);
					mtrlConsDto.setTrnsSlNo(lrfArcingId);
					mtrlConsDto.setHeatNo(heatNo);
					mtrlConsDto.setHeatCounter(heatCounter);

					formulaLi.add(mtrlConsDto);
				}*/
				for(MaterialSubUnitMapMstrModel dayMtrlMstrObj : daymtrlMstrLi) {
					List<LRFHeatConsumableModel> LrfQtyLi=null;
					List<LRFHeatConsumableModel> dayLrfConsLi=daywiseMtrlConsDao.getLrfConsQty(cons_date,Constants.MTRL_CONS_DAY);
					MaterialConsumptionDTO mtrlConsDto1=new MaterialConsumptionDTO();
					LrfQtyLi=dayLrfConsLi.stream().filter(p -> p.getMaterial_id().equals(dayMtrlMstrObj.getMtrl_id())).collect(Collectors.toList());
					if(dayMtrlMstrObj != null) {
						mtrlConsDto1.setMtrlConsSlNo(null);
						mtrlConsDto1.setMtrlType(dayMtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
						mtrlConsDto1.setMtrlName(dayMtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
						mtrlConsDto1.setMtrlId(dayMtrlMstrObj.getMtrlMstrodel().getMaterial_id());
						mtrlConsDto1.setSapMtrlId(dayMtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
						mtrlConsDto1.setUom(dayMtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
						mtrlConsDto1.setValuationType((null == dayMtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : dayMtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
						for(LRFHeatConsumableModel LrfConsobj : LrfQtyLi) {
							mtrlConsDto1.setConsQty(LrfConsobj.getConsumption_qty());
						}
						mtrlConsDto1.setUpdateCounter(0);
						mtrlConsDto1.setTrnsSlNo(lrfArcingId);
						mtrlConsDto1.setHeatNo(heatNo);
						mtrlConsDto1.setHeatCounter(heatCounter);
						formulaLi.add(mtrlConsDto1);
					}
				}
				finalLi.add(formulaLi);
			}else if(subUnitName.substring(0, 2).equals(Constants.SUB_UNIT_VD)) {
				List<VDHeatConsumableModel> vdConsLi = mtrlConsDao.getVDMtrlConsumptions(heatNo);
				Integer vdArcingId = null;
				if(vdConsLi.size() > 0)
					vdArcingId = vdConsLi.get(0).getArc_sl_no();
				for(MaterialSubUnitMapMstrModel mtrlMstrObj : unitwiseMtrlMstrLi) {
					VDHeatConsumableModel vdConsObj = null;
					List<VDHeatConsumableModel> vdConsObjLi = null;
					mtrlConsDto = new MaterialConsumptionDTO();
					vdConsObjLi = vdConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrlMstrodel().getMaterial_id())).collect(Collectors.toList());
					if(vdConsObjLi.size() > 0)
						vdConsObj = vdConsObjLi.get(0);
					mtrlConsDto = new MaterialConsumptionDTO();
					if(vdConsObj != null) {
						mtrlConsDto.setConsQty(vdConsObj.getConsumption_qty());
						mtrlConsDto.setMtrlConsSlNo(vdConsObj.getCons_sl_no());
						mtrlConsDto.setTrnsSlNo(vdConsObj.getArc_sl_no());
					}else {
						mtrlConsDto.setConsQty(null);
						mtrlConsDto.setMtrlConsSlNo(null);
						mtrlConsDto.setTrnsSlNo(vdArcingId);
					}
					mtrlConsDto.setMtrlType(mtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(mtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
					mtrlConsDto.setMtrlId(mtrlMstrObj.getMtrlMstrodel().getMaterial_id());
					mtrlConsDto.setSapMtrlId(mtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
					mtrlConsDto.setUom(mtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					if(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel() != null)
					{
						mtrlConsDto.setSapuom(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());	
					}

					mtrlConsDto.setValuationType((null == mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);

					retLi.add(mtrlConsDto);
				}
				finalLi.add(retLi);
			}else if(subUnitName.substring(0, 3).equals(Constants.SUB_UNIT_CCM)) {
				HeatStatusTrackingModel heatTrackObj = lrfProdDao.getHeatStatusObject(heatTrackId);
				CCMHeatDetailsModel ccmHeatObj = ccmProdDao.getHeatDtlsByHeatId(heatTrackObj.getHeat_id(), heatTrackObj.getHeat_counter());
				List<CCMHeatConsMaterialsDetails> ccmConsLi = mtrlConsDao.getCCMMtrlConsumptions(ccmHeatObj.getTrns_sl_no());
				List<MaterialSubUnitMapMstrModel> unitwiseMtrlMstrNewLi = unitwiseMtrlMstrLi.stream().filter(p -> !(p.getMtrlMstrodel().getMaterial_desc().equals(Constants.CCM_MTRL_CASTING_POWDER))).collect(Collectors.toList());
				for(MaterialSubUnitMapMstrModel mtrlMstrObj : unitwiseMtrlMstrNewLi) {
					CCMHeatConsMaterialsDetails ccmConsObj = null;
					List<CCMHeatConsMaterialsDetails> ccmConsObjLi = null;
					mtrlConsDto = new MaterialConsumptionDTO();
					ccmConsObjLi = ccmConsLi.stream().filter(p -> p.getMaterial_id().equals(mtrlMstrObj.getMtrlMstrodel().getMaterial_id())).collect(Collectors.toList());
					if(ccmConsObjLi.size() > 0)
						ccmConsObj = ccmConsObjLi.get(0);
					mtrlConsDto = new MaterialConsumptionDTO();
					if(ccmConsObj != null) {
						mtrlConsDto.setConsQty(ccmConsObj.getQty());
						mtrlConsDto.setMtrlConsSlNo(ccmConsObj.getMtr_cons_si_no());
					}else {
						mtrlConsDto.setConsQty(null);
						mtrlConsDto.setMtrlConsSlNo(null);
					}
					mtrlConsDto.setMtrlType(mtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(mtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
					mtrlConsDto.setMtrlId(mtrlMstrObj.getMtrlMstrodel().getMaterial_id());
					mtrlConsDto.setSapMtrlId(mtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
					mtrlConsDto.setUom(mtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					if(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel() != null)
					{
						mtrlConsDto.setSapuom(mtrlMstrObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
					}

					mtrlConsDto.setValuationType((null == mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : mtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);
					mtrlConsDto.setTrnsSlNo(ccmHeatObj.getTrns_sl_no());

					retLi.add(mtrlConsDto);
				}
				finalLi.add(retLi);

				/*for(MaterialSubUnitMapMstrModel dayMtrlObj : dayMtrlMstrLi) {
					DaywiseMaterialConsModel dayConsObj = null;
					List<DaywiseMaterialConsModel> dayConsObjLi = null;
					dayConsObjLi = dayConsLi.stream().filter(p -> p.getMaterial_id().equals(dayMtrlObj.getMtrlMstrodel().getMaterial_id())).collect(Collectors.toList());
					if(dayConsObjLi.size() > 0)
						dayConsObj = dayConsObjLi.get(0);

					mtrlConsDto = new MaterialConsumptionDTO();
					if(dayConsObj != null && dayConsObj.getQty() != null && !dayConsObj.equals(0)) {
						mtrlConsDto.setConsQty( dayConsObj.getQty() / totalHeats);
					}else {
						mtrlConsDto.setConsQty(null);
					}
					mtrlConsDto.setMtrlConsSlNo(null);
					mtrlConsDto.setMtrlType(dayMtrlObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
					mtrlConsDto.setMtrlName(dayMtrlObj.getMtrlMstrodel().getMaterial_desc());
					mtrlConsDto.setMtrlId(dayMtrlObj.getMtrlMstrodel().getMaterial_id());
					mtrlConsDto.setSapMtrlId(dayMtrlObj.getMtrlMstrodel().getSap_matl_desc());
					mtrlConsDto.setUom(dayMtrlObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					mtrlConsDto.setValuationType((null==dayMtrlObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : dayMtrlObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
					mtrlConsDto.setUpdateCounter(0);
					mtrlConsDto.setTrnsSlNo(ccmHeatObj.getTrns_sl_no());

					formulaLi.add(mtrlConsDto);
				}*/
				for(MaterialSubUnitMapMstrModel dayMtrlMstrObj : daymtrlMstrLi) {
					MaterialConsumptionDTO mtrlConsDto1=new MaterialConsumptionDTO();
					List<CCMHeatConsMaterialsDetails> CcmQtyLi=null;
					CCMHeatDetailsModel ccmHeatObj1 = ccmProdDao.getHeatDtlsByHeatId(heatTrackObj.getHeat_id(), heatTrackObj.getHeat_counter());
					List<CCMHeatConsMaterialsDetails> dayCcmConsLi=daywiseMtrlConsDao.getCcmConsQty(cons_date,Constants.MTRL_CONS_DAY);
					Double consQty = null;
					CcmQtyLi=dayCcmConsLi.stream().filter(p -> p.getTrns_ccm_si_no().equals(ccmHeatObj1.getTrns_sl_no()) && p.getMaterial_id().equals(dayMtrlMstrObj.getMtrl_id())).collect(Collectors.toList());
					if(CcmQtyLi.size() > 0)
						consQty = CcmQtyLi.get(0).getQty();
					if(dayMtrlMstrObj != null) {
						mtrlConsDto1.setMtrlConsSlNo(null);
						mtrlConsDto1.setMtrlType(dayMtrlMstrObj.getMtrlMstrodel().getMtrlTypeLkpModel().getLookup_code());
						mtrlConsDto1.setMtrlName(dayMtrlMstrObj.getMtrlMstrodel().getMaterial_desc());
						mtrlConsDto1.setMtrlId(dayMtrlMstrObj.getMtrlMstrodel().getMaterial_id());
						mtrlConsDto1.setSapMtrlId(dayMtrlMstrObj.getMtrlMstrodel().getSap_matl_desc());
						mtrlConsDto1.setConsQty(consQty);
						mtrlConsDto1.setUom(dayMtrlMstrObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
						mtrlConsDto1.setValuationType((null==dayMtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel()) ? null : dayMtrlMstrObj.getMtrlMstrodel().getValTypeLkpModel().getLookup_code());
						mtrlConsDto1.setUpdateCounter(0);
						mtrlConsDto1.setTrnsSlNo(ccmHeatObj.getTrns_sl_no());

						formulaLi.add(mtrlConsDto1);
					}

				}

				if(ccmHeatObj.getCasting_powder() != null) {
					List<LookupMasterModel> castingPowderPercLkp = lkpMasterDao.getLookupDtlsByLkpTypeAndStatus(Constants.LKP_TYPE_CAST_POWDER_PERC, 1);
					MaterialSubUnitMapMstrModel unitMtrlMapObj  = unitwiseMtrlMstrLi.stream().filter(p -> p.getMtrlMstrodel().getMaterial_desc().equals(Constants.CCM_MTRL_CASTING_POWDER)).collect(Collectors.toList()).get(0);
					MaterialConsumptionDTO mtrlConsDto1=new MaterialConsumptionDTO();
					LookupMasterModel valuationTypeLkpObj = lkpMasterDao.getLookUpRowById(Integer.parseInt(ccmHeatObj.getLkpCastinPowerMdl().getAttribute4()));
					mtrlConsDto1.setConsQty( heatQty * Double.parseDouble(castingPowderPercLkp.get(0).getLookup_value()));
					mtrlConsDto1.setMtrlConsSlNo(null);
					mtrlConsDto1.setMtrlType(Constants.CCM_ADDITIONS);
					mtrlConsDto1.setMtrlName(ccmHeatObj.getLkpCastinPowerMdl().getLookup_code());
					mtrlConsDto1.setMtrlId(unitMtrlMapObj.getMtrl_id());
					mtrlConsDto1.setSapMtrlId(ccmHeatObj.getLkpCastinPowerMdl().getAttribute1());
					mtrlConsDto1.setUom(unitMtrlMapObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
					if(unitMtrlMapObj.getMtrlMstrodel().getSapuomLkpModel() != null)
					{
						mtrlConsDto1.setSapuom(unitMtrlMapObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
					}


					mtrlConsDto1.setValuationType((null == valuationTypeLkpObj) ? null : valuationTypeLkpObj.getLookup_code());
					mtrlConsDto1.setUpdateCounter(0);
					mtrlConsDto1.setTrnsSlNo(ccmHeatObj.getTrns_sl_no());

					formulaLi.add(mtrlConsDto1);

				}
				finalLi.add(formulaLi);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalLi;
	}

	@Override
	public String saveOrUpdateMtrlConsumptions(MaterialConsumptionDTO mtrlConsDto, String consDate, Integer userId) {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Map<String, List> saveUpdLi = new HashMap<String, List>();
		List<MaterialConsumptionDTO> mtrlConsDtoLi = mtrlConsDto.getMtrlConsDtoLi();
		List<MaterialConsumptionDTO> dayConsDtoLi = mtrlConsDto.getDayConsDtoLi();
		String subUnit = mtrlConsDto.getSubUnit();

		if(subUnit.substring(0, 3).equals(Constants.SUB_UNIT_EOF)){
			List<EofHeatDetails> eofheatObjLi = null;
			List<HmReceiveBaseDetails> hmSaveLi = null;
			List<ScrapBucketDetails> scrapSaveLi = null, scrapUpdateLi = null;
			List<ScrapBucketHdr> scrapHdrSaveLi = null, scrapHdrUpdateLi = null;
			List<HeatConsScrapMtrlDetails> hmScrapConsSaveLi = null, hmScrapConsUpdateLi = null;
			List<HeatConsMaterialsDetails> eofMtrlConsSaveLi = null, eofMtrlConsUpdateLi = null;
			EofHeatDetails  eofHeatObj = eofProdDao.getEOFHeatDetailsById(mtrlConsDtoLi.get(0).getTrnsSlNo());
			eofheatObjLi = new ArrayList<EofHeatDetails>();
			hmSaveLi = new ArrayList<HmReceiveBaseDetails>();
			scrapSaveLi = new ArrayList<ScrapBucketDetails>();
			scrapUpdateLi = new ArrayList<ScrapBucketDetails>();
			scrapHdrSaveLi = new ArrayList<ScrapBucketHdr>();
			scrapHdrUpdateLi = new ArrayList<ScrapBucketHdr>();
			hmScrapConsSaveLi = new ArrayList<HeatConsScrapMtrlDetails>();
			hmScrapConsUpdateLi = new ArrayList<HeatConsScrapMtrlDetails>();
			eofMtrlConsSaveLi = new ArrayList<HeatConsMaterialsDetails>();
			eofMtrlConsUpdateLi = new ArrayList<HeatConsMaterialsDetails>();
			HmReceiveBaseDetails hmSaveObj;
			ScrapBucketDetails scrapSaveObj;
			ScrapBucketHdr scrapHdrSaveObj;
			HeatConsScrapMtrlDetails hmScrapConsSaveObj;
			HeatConsMaterialsDetails eofMtrlConsSaveObj = null;
			Integer scrapHeaderId = null;
			List<HeatConsScrapMtrlDetails> eofScrapConsLi = eofProdDao.getHeatScrapConsumptionByEofId(eofHeatObj.getTrns_si_no());
			if(eofScrapConsLi.size() > 0)
				scrapHeaderId = eofScrapConsLi.get(0).getScrap_bkt_header_id();
			String scrapHdrSave = "N", scrapHdrUpdate = "N";
			Double totalScrapQty = 0.0;
			List<ScrapBucketDetailsLog> scrapDtlLogLi = new ArrayList<ScrapBucketDetailsLog>();
			List<HeatConsMaterialsDetailsLog> eofConsLogLi = new ArrayList<HeatConsMaterialsDetailsLog>();

			try {
				for(MaterialConsumptionDTO dtoObj : mtrlConsDtoLi) {
					if(dtoObj.getMtrlType().equals(Constants.MATERIAL_TYPE_HM)) {
						if(dtoObj.getMtrlConsSlNo() != null && dtoObj.getUpdateCounter() > 0) {
							hmSaveObj = hmReceiveDao.getHmReceiveDetailsById(dtoObj.getMtrlConsSlNo());

							hmSaveObj.setHmLadleGrossWt(dtoObj.getConsQty());
							hmSaveObj.setUpdatedBy(userId);
							hmSaveObj.setUpdatedDateTime(new Date());

							hmSaveLi.add(hmSaveObj);

							hmScrapConsSaveObj = mtrlConsDao.getHeatScrapConsumptionHMRecvId(eofHeatObj.getTrns_si_no(), dtoObj.getMtrlConsSlNo());
							hmScrapConsSaveObj.setQty(dtoObj.getConsQty());
							hmScrapConsSaveObj.setUpdatedBy(userId);
							hmScrapConsSaveObj.setUpdatedDateTime(new Date());

							hmScrapConsUpdateLi.add(hmScrapConsSaveObj);

							eofHeatObj.setHm_wt(dtoObj.getConsQty());
							eofHeatObj.setUpdatedBy(userId);
							eofHeatObj.setUpdatedDateTime(new Date());

							eofheatObjLi.add(eofHeatObj);
						}
					}else if(dtoObj.getMtrlType().equals(Constants.MATERIAL_TYPE_SCRAP)) {
						if(dtoObj.getConsQty() != null)
							totalScrapQty = totalScrapQty + dtoObj.getConsQty();
						if(dtoObj.getMtrlConsSlNo() != null && dtoObj.getUpdateCounter() > 0) {
							scrapHdrUpdate = "Y";
							scrapSaveObj = scrapDtlDao.getScrapBucketDetailsById(dtoObj.getMtrlConsSlNo());

							scrapSaveObj.setMaterial_qty(dtoObj.getConsQty());
							scrapSaveObj.setUpdatedBy(userId);
							scrapSaveObj.setUpdatedDateTime(new Date());
							scrapUpdateLi.add(scrapSaveObj);

							ScrapBucketDetailsLog scrapDetlLogObj = mtrlConsDao.getScrapBucketDetLogByConsId(scrapSaveObj.getScrap_bkt_detail_id());
							if(scrapDetlLogObj == null) {
								scrapDetlLogObj = new ScrapBucketDetailsLog();
								scrapDetlLogObj.setScrap_bkt_detail_id(scrapSaveObj.getScrap_bkt_detail_id());
								scrapDetlLogObj.setScrap_bkt_header_id(scrapSaveObj.getScrap_bkt_header_id());
								scrapDetlLogObj.setMaterial_id(scrapSaveObj.getMaterial_id());
								scrapDetlLogObj.setMaterialQtyOrig(scrapSaveObj.getMaterial_qty());
								scrapDetlLogObj.setCreatedBy(scrapSaveObj.getCreatedBy());
								scrapDetlLogObj.setCreatedDateTime(scrapSaveObj.getCreatedDateTime());
								scrapDetlLogObj.setUpdatedBy(scrapSaveObj.getUpdatedBy());
								scrapDetlLogObj.setUpdatedDateTime(scrapSaveObj.getUpdatedDateTime());
								scrapDetlLogObj.setCreatedHistBy(userId);
								scrapDetlLogObj.setCreatedHistDateTime(new Date());

								scrapDtlLogLi.add(scrapDetlLogObj);
							}
						}else if(dtoObj.getMtrlConsSlNo() == null && dtoObj.getConsQty() != null){

							//if((eofScrapConsLi.size() == 0 || eofScrapConsLi.get(0).getScrap_bkt_header_id() == null) && scrapHdrSave.equals("N"))
							if(eofScrapConsLi.size() == 0 && scrapHdrSave.equals("N"))
								scrapHdrSave = "Y";
							else 
								scrapHdrUpdate = "Y";

							scrapSaveObj = new ScrapBucketDetails();
							scrapSaveObj.setScrap_bkt_header_id(scrapHeaderId);
							scrapSaveObj.setMaterial_id(dtoObj.getMtrlId());
							scrapSaveObj.setMaterial_qty(dtoObj.getConsQty());
							scrapSaveObj.setCreatedBy(userId);
							scrapSaveObj.setCreatedDateTime(new Date());

							scrapSaveLi.add(scrapSaveObj);
						}
					}else {
						if(dtoObj.getMtrlConsSlNo() != null && dtoObj.getUpdateCounter() > 0) {
							eofMtrlConsSaveObj = mtrlConsDao.getEOFMtrlConsById(dtoObj.getMtrlConsSlNo());

							eofMtrlConsSaveObj.setQty(dtoObj.getConsQty());
							eofMtrlConsSaveObj.setUpdatedBy(userId);
							eofMtrlConsSaveObj.setUpdatedDateTime(new Date());
							eofMtrlConsUpdateLi.add(eofMtrlConsSaveObj);

							HeatConsMaterialsDetailsLog eofConsLogObj = mtrlConsDao.getEofHeatConsLogByConsId(eofMtrlConsSaveObj.getMtr_cons_si_no());
							if(eofConsLogObj == null) {
								eofConsLogObj = new HeatConsMaterialsDetailsLog();

								eofConsLogObj.setMtr_cons_si_no(eofMtrlConsSaveObj.getMtr_cons_si_no());
								eofConsLogObj.setTrns_eof_si_no(eofMtrlConsSaveObj.getTrns_eof_si_no());
								eofConsLogObj.setMaterial_id(eofMtrlConsSaveObj.getMaterial_id());
								eofConsLogObj.setQty(eofMtrlConsSaveObj.getQty());
								eofConsLogObj.setConsumption_date(eofMtrlConsSaveObj.getConsumption_date());
								eofConsLogObj.setCreatedBy(eofMtrlConsSaveObj.getCreatedBy());
								eofConsLogObj.setCreatedDateTime(eofMtrlConsSaveObj.getCreatedDateTime());
								eofConsLogObj.setUpdatedBy(eofMtrlConsSaveObj.getUpdatedBy());
								eofConsLogObj.setUpdatedDateTime(eofMtrlConsSaveObj.getUpdatedDateTime());
								eofConsLogObj.setCreatedHistBy(userId);
								eofConsLogObj.setCreatedHistDateTime(new Date());

								eofConsLogLi.add(eofConsLogObj);
							}
						}else if(dtoObj.getMtrlConsSlNo() == null && dtoObj.getConsQty() != null){
							eofMtrlConsSaveObj = new HeatConsMaterialsDetails();

							eofMtrlConsSaveObj.setTrns_eof_si_no(eofHeatObj.getTrns_si_no());
							eofMtrlConsSaveObj.setMaterial_id(dtoObj.getMtrlId());
							eofMtrlConsSaveObj.setQty(dtoObj.getConsQty());
							eofMtrlConsSaveObj.setConsumption_date(df.parse(consDate));
							eofMtrlConsSaveObj.setCreatedBy(userId);
							eofMtrlConsSaveObj.setCreatedDateTime(new Date());
							eofMtrlConsSaveObj.setRecord_status(1);

							eofMtrlConsSaveLi.add(eofMtrlConsSaveObj);
						}
					}	
				}
				if(scrapHdrSave.equals("Y")){
					scrapHdrSaveObj = new ScrapBucketHdr();
					scrapHdrSaveObj.setScrap_bkt_id(1);	
					scrapHdrSaveObj.setScrap_bkt_qty(totalScrapQty);
					scrapHdrSaveObj.setScrap_prod_date(df.parse(consDate));
					scrapHdrSaveObj.setScrap_bkt_load_status(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.SCRAP_BKT_STATUS+"' and lookup_code='"+Constants.SCRAP_BKT_CONSUMED+"' and lookup_status=1"));
					scrapHdrSaveObj.setCreatedBy(userId);
					scrapHdrSaveObj.setCreatedDateTime(new Date());
					scrapHdrSaveObj.setScrap_pattern_id(1);
					scrapHdrSaveLi.add(scrapHdrSaveObj);

					hmScrapConsSaveObj = new HeatConsScrapMtrlDetails();
					hmScrapConsSaveObj.setTrns_eof_si_no(eofHeatObj.getTrns_si_no());
					hmScrapConsSaveObj.setHm_seq_no(null);
					hmScrapConsSaveObj.setQty(totalScrapQty);
					hmScrapConsSaveObj.setCreatedBy(userId);
					hmScrapConsSaveObj.setCreatedDateTime(new Date());
					hmScrapConsSaveObj.setRecord_status(1);
					hmScrapConsSaveObj.setConsumption_date(df.parse(consDate));
					hmScrapConsSaveLi.add(hmScrapConsSaveObj);
				}
				if(scrapHdrUpdate.equals("Y")){
					scrapHdrSaveObj = scrapHdrDao.getLoadedScrapBktsByHeaderId(scrapHeaderId);
					scrapHdrSaveObj.setScrap_bkt_qty(totalScrapQty);
					scrapHdrSaveObj.setUpdatedBy(userId);
					scrapHdrSaveObj.setUpdatedDateTime(new Date());
					scrapHdrUpdateLi.add(scrapHdrSaveObj);

					hmScrapConsSaveObj = eofScrapConsLi.get(0);
					hmScrapConsSaveObj.setQty(totalScrapQty);
					hmScrapConsSaveObj.setUpdatedBy(userId);
					hmScrapConsSaveObj.setUpdatedDateTime(new Date());
					hmScrapConsUpdateLi.add(hmScrapConsSaveObj);
				}
				for(MaterialConsumptionDTO dayConsDtoObj : dayConsDtoLi) {
					if(dayConsDtoObj.getConsQty() != null && !dayConsDtoObj.getConsQty().equals(0)) {
						HeatConsMaterialsDetails eofDayConsObj = mtrlConsDao.getEofMtrlConsByMtrlId(eofHeatObj.getTrns_si_no(), dayConsDtoObj.getMtrlId());
						if(eofDayConsObj == null) {
							eofMtrlConsSaveObj = new HeatConsMaterialsDetails();

							eofMtrlConsSaveObj.setTrns_eof_si_no(eofHeatObj.getTrns_si_no());
							eofMtrlConsSaveObj.setMaterial_id(dayConsDtoObj.getMtrlId());
							eofMtrlConsSaveObj.setQty(dayConsDtoObj.getConsQty());
							eofMtrlConsSaveObj.setConsumption_date(df.parse(consDate));
							eofMtrlConsSaveObj.setCreatedBy(userId);
							eofMtrlConsSaveObj.setCreatedDateTime(new Date());
							eofMtrlConsSaveObj.setRecord_status(1);

							eofMtrlConsSaveLi.add(eofMtrlConsSaveObj);
						}else {
							eofDayConsObj.setQty(dayConsDtoObj.getConsQty());
							eofDayConsObj.setUpdatedBy(userId);
							eofDayConsObj.setUpdatedDateTime(new Date());

							eofMtrlConsUpdateLi.add(eofDayConsObj);
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}

			saveUpdLi.put("UPDATE_EOF_HEAT", eofheatObjLi);
			saveUpdLi.put("UPDATE_HM", hmSaveLi);
			saveUpdLi.put("SAVE_SCRAP_HDR", scrapHdrSaveLi);
			saveUpdLi.put("UPDATE_SCRAP_HDR", scrapHdrUpdateLi);
			saveUpdLi.put("SAVE_SCRAP_DTL", scrapSaveLi);
			saveUpdLi.put("UPDATE_SCRAP_DTL", scrapUpdateLi);
			saveUpdLi.put("SAVE_HMSCRAP_CONS", hmScrapConsSaveLi);
			saveUpdLi.put("UPDATE_HMSCRAP_CONS", hmScrapConsUpdateLi);
			saveUpdLi.put("SAVE_EOFMTRL_CONS", eofMtrlConsSaveLi);
			saveUpdLi.put("UPDATE_EOFMTRL_CONS", eofMtrlConsUpdateLi);
			saveUpdLi.put("SAVE_SCRAP_DTL_LOG", scrapDtlLogLi);
			saveUpdLi.put("SAVE_EOF_CONS_LOG", eofConsLogLi);
		}else if(subUnit.substring(0, 3).equals(Constants.SUB_UNIT_LRF)) {
			String lrfArcHdrSave = "N", heatId = null;
			Integer heatCounter = null;
			LRFHeatConsumableModel lrfMtrlConsSaveObj = null;
			List<LRFHeatConsumableModel> lrfMtrlConsSaveLi = null, lrfMtrlConsUpdateLi = null;
			lrfMtrlConsSaveLi = new ArrayList<LRFHeatConsumableModel>();
			lrfMtrlConsUpdateLi = new ArrayList<LRFHeatConsumableModel>();
			LRFHeatArcingDetailsModel lrfArcSaveObj;
			List<LRFHeatArcingDetailsModel> lrfArcingSaveLi = new ArrayList<LRFHeatArcingDetailsModel>();
			List<LRFHeatDetailsModel> lrfHeatObjLi = mtrlConsDao.getLrfHeatDetailObj(mtrlConsDto.getHeatNo(), mtrlConsDto.getSubUnitId());
			List<LRFHeatConsumableLogModel> lrfConsLogLi = new ArrayList<LRFHeatConsumableLogModel>();
			try {
				LRFHeatArcingDetailsModel lrfArcingObj = null;
				for(MaterialConsumptionDTO dtoObj : mtrlConsDtoLi) {
					if(dtoObj.getMtrlConsSlNo() != null && dtoObj.getUpdateCounter() > 0) {
						lrfMtrlConsSaveObj = lrfProdDao.getLRFHeatConsumablesById(dtoObj.getMtrlConsSlNo());
						lrfMtrlConsSaveObj.setConsumption_qty(dtoObj.getConsQty());
						lrfMtrlConsSaveObj.setUpdated_by(userId);
						lrfMtrlConsSaveObj.setUpdated_date_time(new Date());
						lrfMtrlConsUpdateLi.add(lrfMtrlConsSaveObj);

						LRFHeatConsumableLogModel lrfConsLogObj = mtrlConsDao.getLrfHeatConsLogByConsId(lrfMtrlConsSaveObj.getCons_sl_no());
						if(lrfConsLogObj == null) {
							lrfConsLogObj = new LRFHeatConsumableLogModel();
							lrfConsLogObj.setCons_sl_no(lrfMtrlConsSaveObj.getCons_sl_no());
							lrfConsLogObj.setArc_sl_no(lrfMtrlConsSaveObj.getArc_sl_no());
							lrfConsLogObj.setHeat_id(lrfMtrlConsSaveObj.getHeat_id());
							lrfConsLogObj.setHeat_counter(lrfMtrlConsSaveObj.getHeat_counter());
							lrfConsLogObj.setMaterial_type(lrfMtrlConsSaveObj.getMaterial_type());
							lrfConsLogObj.setMaterial_id(lrfMtrlConsSaveObj.getMaterial_id());
							lrfConsLogObj.setConsumption_qty(lrfMtrlConsSaveObj.getConsumption_qty());
							lrfConsLogObj.setAddition_date_time(lrfMtrlConsSaveObj.getAddition_date_time());
							lrfConsLogObj.setCreated_by(lrfMtrlConsSaveObj.getCreated_by());
							lrfConsLogObj.setCreated_date_time(lrfMtrlConsSaveObj.getCreated_date_time());
							lrfConsLogObj.setSap_matl_id(lrfMtrlConsSaveObj.getSap_matl_id());
							lrfConsLogObj.setValuation_type(lrfMtrlConsSaveObj.getValuation_type());
							lrfConsLogObj.setUpdated_by(lrfMtrlConsSaveObj.getUpdated_by());
							lrfConsLogObj.setUpdated_date_time(lrfMtrlConsSaveObj.getUpdated_date_time());
							lrfConsLogObj.setCreatedHistBy(userId);
							lrfConsLogObj.setCreatedHistDateTime(new Date());
							lrfConsLogLi.add(lrfConsLogObj);
						}
					}else if(dtoObj.getMtrlConsSlNo() == null && dtoObj.getConsQty() != null){
						if(lrfArcingObj == null) {
							lrfArcingObj = lrfProdDao.getArcDetailsBySlno(dtoObj.getTrnsSlNo());
							heatId = lrfArcingObj.getHeat_id();
							heatCounter = lrfArcingObj.getHeat_counter();
						}
						if(lrfArcingObj == null && lrfArcHdrSave.equals("N"))
							lrfArcHdrSave = "Y";

						lrfMtrlConsSaveObj = new LRFHeatConsumableModel();

						lrfMtrlConsSaveObj.setArc_sl_no(dtoObj.getTrnsSlNo());
						lrfMtrlConsSaveObj.setHeat_id(heatId);
						lrfMtrlConsSaveObj.setHeat_counter(heatCounter);
						lrfMtrlConsSaveObj.setMaterial_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.LRF_ARC_ADDITIONS+"' and lookup_status=1"));
						lrfMtrlConsSaveObj.setMaterial_id(dtoObj.getMtrlId());
						lrfMtrlConsSaveObj.setConsumption_qty(dtoObj.getConsQty());
						lrfMtrlConsSaveObj.setAddition_date_time(new Date());
						lrfMtrlConsSaveObj.setRecord_status(1);
						lrfMtrlConsSaveObj.setCreated_by(userId);
						lrfMtrlConsSaveObj.setCreated_date_time(new Date());
						//lrfMtrlConsSaveObj.setSap_matl_id(dtoObj.getSapMtrlId());
						//lrfMtrlConsSaveObj.setValuation_type(dtoObj.getValuationType().toString());
						lrfMtrlConsSaveLi.add(lrfMtrlConsSaveObj);
					}
				}
				if(lrfArcHdrSave.equals("Y")){
					lrfArcSaveObj = new LRFHeatArcingDetailsModel();
					lrfArcSaveObj.setHeat_id(lrfHeatObjLi.get(0).getHeat_id());
					lrfArcSaveObj.setHeat_counter(lrfHeatObjLi.get(0).getHeat_counter());
					lrfArcSaveObj.setAddition_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.LRF_ADDITION_TYPE+"' and lookup_code='"+Constants.LRF_ADDITION_BVD+"' and lookup_status=1"));
					lrfArcSaveObj.setArc_no(1);
					lrfArcSaveObj.setArc_start_date_time(lrfHeatObjLi.get(0).getProduction_date());
					lrfArcSaveObj.setArc_end_date_time(lrfHeatObjLi.get(0).getProduction_date());
					lrfArcSaveObj.setCreated_by(userId);
					lrfArcSaveObj.setCreated_date_time(new Date());

					lrfArcingSaveLi.add(lrfArcSaveObj);
				}

				/*for(MaterialConsumptionDTO dayConsDtoObj : dayConsDtoLi) {
					if(dayConsDtoObj.getConsQty() != null && !dayConsDtoObj.getConsQty().equals(0)) {
						LRFHeatConsumableModel lrfDayConsObj =mtrlConsDao.getLrfMtrlConsByMtrlId(heatId, dayConsDtoObj.getMtrlId());
						if(lrfDayConsObj == null) {
						lrfMtrlConsSaveObj = new LRFHeatConsumableModel();
						lrfMtrlConsSaveObj.setArc_sl_no(dayConsDtoObj.getTrnsSlNo());
						lrfMtrlConsSaveObj.setHeat_id(heatId);
						lrfMtrlConsSaveObj.setHeat_counter(heatCounter);
						lrfMtrlConsSaveObj.setMaterial_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.LRF_ARC_ADDITIONS+"' and lookup_status=1"));
						lrfMtrlConsSaveObj.setMaterial_id(dayConsDtoObj.getMtrlId());
						lrfMtrlConsSaveObj.setConsumption_qty(dayConsDtoObj.getConsQty());
						lrfMtrlConsSaveObj.setAddition_date_time(new Date());
						lrfMtrlConsSaveObj.setRecord_status(1);
						lrfMtrlConsSaveObj.setCreated_by(userId);
						lrfMtrlConsSaveObj.setCreated_date_time(new Date());
						//lrfMtrlConsSaveObj.setSap_matl_id(dayConsDtoObj.getSapMtrlId());
						//lrfMtrlConsSaveObj.setValuation_type(dayConsDtoObj.getValuationType().toString());

						lrfMtrlConsSaveLi.add(lrfMtrlConsSaveObj);
						}else {
							lrfDayConsObj.setConsumption_qty(dayConsDtoObj.getConsQty());
							lrfDayConsObj.setUpdated_by(userId);
							lrfDayConsObj.setUpdated_date_time(new Date());

							lrfMtrlConsUpdateLi.add(lrfDayConsObj);
						}
					}
				}*/
			}catch(Exception e) {
				e.printStackTrace();
			}

			saveUpdLi.put("SAVE_LRFMTRL_CONS", lrfMtrlConsSaveLi);
			saveUpdLi.put("UPDATE_LRFMTRL_CONS", lrfMtrlConsUpdateLi);
			saveUpdLi.put("SAVE_LRF_ARCING", lrfArcingSaveLi);
			saveUpdLi.put("SAVE_LRF_CONS_LOG", lrfConsLogLi);
		}else if(subUnit.substring(0, 2).equals(Constants.SUB_UNIT_VD)) {
			String vdConsHdrSave = "N", heatId = null;
			Integer heatCounter = null;
			VDHeatConsumableModel vdMtrlConsSaveObj = null;
			List<VDHeatConsumableModel> vdMtrlConsSaveLi = null, vdMtrlConsUpdateLi = null;
			vdMtrlConsSaveLi = new ArrayList<VDHeatConsumableModel>();
			vdMtrlConsUpdateLi = new ArrayList<VDHeatConsumableModel>();
			VdAdditionsDetailsModel vdConsHdrSaveObj;
			List<VdAdditionsDetailsModel> vdConsHdrSaveLi = new ArrayList<VdAdditionsDetailsModel>();
			VDHeatDetailsModel vdHeatObj = vdProdDao.getVDHeatDetailsByHeatNo(mtrlConsDto.getHeatNo());
			List<VDHeatConsumableModelLog> vdConsLogLi = new ArrayList<VDHeatConsumableModelLog>();
			try {
				VdAdditionsDetailsModel vdConsHdrObj = null;
				for(MaterialConsumptionDTO dtoObj : mtrlConsDtoLi) {
					if(dtoObj.getMtrlConsSlNo() != null && dtoObj.getUpdateCounter() > 0) {
						vdMtrlConsSaveObj = vdProdDao.getVDHeatConsumablesById(dtoObj.getMtrlConsSlNo());
						vdMtrlConsSaveObj.setConsumption_qty(dtoObj.getConsQty());
						vdMtrlConsSaveObj.setUpdated_by(userId);
						vdMtrlConsSaveObj.setUpdated_date_time(new Date());
						vdMtrlConsUpdateLi.add(vdMtrlConsSaveObj);

						VDHeatConsumableModelLog vdConsLogObj = mtrlConsDao.getVdHeatConsLogByConsId(vdMtrlConsSaveObj.getCons_sl_no());
						if(vdConsLogObj == null) {
							vdConsLogObj = new VDHeatConsumableModelLog();
							vdConsLogObj.setCons_sl_no(vdMtrlConsSaveObj.getCons_sl_no());
							vdConsLogObj.setArc_sl_no(vdMtrlConsSaveObj.getArc_sl_no());
							vdConsLogObj.setHeat_id(vdMtrlConsSaveObj.getHeat_id());
							vdConsLogObj.setHeat_counter(vdMtrlConsSaveObj.getHeat_counter());
							vdConsLogObj.setMaterial_type(vdMtrlConsSaveObj.getMaterial_type());
							vdConsLogObj.setMaterial_id(vdMtrlConsSaveObj.getMaterial_id());
							vdConsLogObj.setConsumption_qty(vdMtrlConsSaveObj.getConsumption_qty());
							vdConsLogObj.setAddition_date_time(vdMtrlConsSaveObj.getAddition_date_time());
							vdConsLogObj.setRecord_status(vdMtrlConsSaveObj.getRecord_status());
							vdConsLogObj.setCreated_by(vdMtrlConsSaveObj.getCreated_by());
							vdConsLogObj.setCreated_date_time(vdMtrlConsSaveObj.getCreated_date_time());
							vdConsLogObj.setSap_matl_id(vdMtrlConsSaveObj.getSap_matl_id());
							vdConsLogObj.setValuation_type(vdMtrlConsSaveObj.getValuation_type());
							vdConsLogObj.setUpdated_by(vdMtrlConsSaveObj.getUpdated_by());
							vdConsLogObj.setUpdated_date_time(vdMtrlConsSaveObj.getUpdated_date_time());
							vdConsLogObj.setCreatedHistBy(userId);
							vdConsLogObj.setCreatedHistDateTime(new Date());

							vdConsLogLi.add(vdConsLogObj);
						}
					}else if(dtoObj.getMtrlConsSlNo() == null && dtoObj.getConsQty() != null){
						if(vdConsHdrObj == null) {
							vdConsHdrObj = vdProdDao.getAddDetailsBySlno(dtoObj.getTrnsSlNo());
							heatId = vdConsHdrObj.getHeat_id();
							heatCounter = vdConsHdrObj.getHeat_counter();
						}
						if(vdConsHdrObj == null && vdConsHdrSave.equals("N"))
							vdConsHdrSave = "Y";

						vdMtrlConsSaveObj = new VDHeatConsumableModel();

						vdMtrlConsSaveObj.setArc_sl_no(dtoObj.getTrnsSlNo());
						vdMtrlConsSaveObj.setHeat_id(heatId);
						vdMtrlConsSaveObj.setHeat_counter(heatCounter);
						vdMtrlConsSaveObj.setMaterial_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.VD_ADDITIONS+"' and lookup_status=1"));
						vdMtrlConsSaveObj.setMaterial_id(dtoObj.getMtrlId());
						vdMtrlConsSaveObj.setConsumption_qty(dtoObj.getConsQty());
						vdMtrlConsSaveObj.setAddition_date_time(new Date());
						vdMtrlConsSaveObj.setRecord_status(1);
						vdMtrlConsSaveObj.setCreated_by(userId);
						vdMtrlConsSaveObj.setCreated_date_time(new Date());
						//vdMtrlConsSaveObj.setSap_matl_id(dtoObj.getSapMtrlId());
						//vdMtrlConsSaveObj.setValuation_type(dtoObj.getValuationType().toString());

						vdMtrlConsSaveLi.add(vdMtrlConsSaveObj);
					}
				}
				if(vdConsHdrSave.equals("Y")){
					vdConsHdrSaveObj = new VdAdditionsDetailsModel();
					vdConsHdrSaveObj.setHeat_id(vdHeatObj.getHeat_id());
					vdConsHdrSaveObj.setHeat_counter(vdHeatObj.getHeat_counter());
					vdConsHdrSaveObj.setArc_no(1);
					vdConsHdrSaveObj.setCreated_by(userId);
					vdConsHdrSaveObj.setCreated_date_time(new Date());

					vdConsHdrSaveLi.add(vdConsHdrSaveObj);
				}
				for(MaterialConsumptionDTO dayConsDtoObj : dayConsDtoLi) {
					if(dayConsDtoObj.getConsQty() != null && !dayConsDtoObj.getConsQty().equals(0)) {
						VDHeatConsumableModel vdDayConsObj = mtrlConsDao.getVdMtrlConsByMtrlId(heatId, dayConsDtoObj.getMtrlId());
						if(vdDayConsObj == null) {
							vdMtrlConsSaveObj = new VDHeatConsumableModel();
							vdMtrlConsSaveObj.setArc_sl_no(dayConsDtoObj.getTrnsSlNo());
							vdMtrlConsSaveObj.setHeat_id(heatId);
							vdMtrlConsSaveObj.setHeat_counter(heatCounter);
							vdMtrlConsSaveObj.setMaterial_type(commonDao.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type='"+Constants.MATERIAL_TYPE+"' and lookup_code='"+Constants.VD_ADDITIONS+"' and lookup_status=1"));
							vdMtrlConsSaveObj.setMaterial_id(dayConsDtoObj.getMtrlId());
							vdMtrlConsSaveObj.setConsumption_qty(dayConsDtoObj.getConsQty());
							vdMtrlConsSaveObj.setAddition_date_time(new Date());
							vdMtrlConsSaveObj.setRecord_status(1);
							vdMtrlConsSaveObj.setCreated_by(userId);
							vdMtrlConsSaveObj.setCreated_date_time(new Date());
							//vdMtrlConsSaveObj.setSap_matl_id(dayConsDtoObj.getSapMtrlId());
							//vdMtrlConsSaveObj.setValuation_type(dayConsDtoObj.getValuationType().toString());

							vdMtrlConsSaveLi.add(vdMtrlConsSaveObj);
						}else {
							vdDayConsObj.setConsumption_qty(dayConsDtoObj.getConsQty());
							vdDayConsObj.setUpdated_by(userId);
							vdDayConsObj.setUpdated_date_time(new Date());

							vdMtrlConsUpdateLi.add(vdDayConsObj);
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}

			saveUpdLi.put("SAVE_VDMTRL_CONS", vdMtrlConsSaveLi);
			saveUpdLi.put("UPDATE_VDMTRL_CONS", vdMtrlConsUpdateLi);
			saveUpdLi.put("SAVE_VD_ADDITION", vdConsHdrSaveLi);
			saveUpdLi.put("SAVE_VD_CONS_LOG", vdConsLogLi);
		}else if(subUnit.substring(0,3).equals(Constants.SUB_UNIT_CCM)) {

			List<CCMHeatConsMaterialsDetails> ccmMtrlConsSaveLi = null, ccmMtrlConsUpdateLi = null;
			ccmMtrlConsSaveLi = new ArrayList<CCMHeatConsMaterialsDetails>();
			ccmMtrlConsUpdateLi = new ArrayList<CCMHeatConsMaterialsDetails>();
			CCMHeatConsMaterialsDetails ccmMtrlConsSaveObj = null;
			List<CCMHeatConsMtlsDetailsLog> ccmConsLogLi = new ArrayList<CCMHeatConsMtlsDetailsLog>();
			try {
				for(MaterialConsumptionDTO dtoObj : mtrlConsDtoLi) {
					if(dtoObj.getMtrlConsSlNo() != null && dtoObj.getUpdateCounter() > 0) {
						ccmMtrlConsSaveObj = heatProcessDao.getccmHeatConsMtrlsDtlsById(dtoObj.getMtrlConsSlNo());
						ccmMtrlConsSaveObj.setQty(dtoObj.getConsQty());
						ccmMtrlConsSaveObj.setUpdatedBy(userId);
						ccmMtrlConsSaveObj.setUpdatedDateTime(new Date());
						ccmMtrlConsUpdateLi.add(ccmMtrlConsSaveObj);

						CCMHeatConsMtlsDetailsLog ccmConsLogObj = mtrlConsDao.getCcmHeatConsLogByConsId(ccmMtrlConsSaveObj.getMtr_cons_si_no());
						if(ccmConsLogObj == null) {
							ccmConsLogObj = new CCMHeatConsMtlsDetailsLog();
							ccmConsLogObj.setMtr_cons_si_no(ccmMtrlConsSaveObj.getMtr_cons_si_no());
							ccmConsLogObj.setTrns_ccm_si_no(ccmMtrlConsSaveObj.getTrns_ccm_si_no());
							ccmConsLogObj.setMaterial_id(ccmMtrlConsSaveObj.getMaterial_id());
							ccmConsLogObj.setQty(ccmMtrlConsSaveObj.getQty());
							ccmConsLogObj.setConsumption_date(ccmMtrlConsSaveObj.getConsumption_date());
							ccmConsLogObj.setCreatedBy(ccmMtrlConsSaveObj.getCreatedBy());
							ccmConsLogObj.setCreatedDateTime(ccmMtrlConsSaveObj.getCreatedDateTime());
							ccmConsLogObj.setRecord_status(ccmMtrlConsSaveObj.getRecord_status());
							ccmConsLogObj.setUpdatedBy(ccmMtrlConsSaveObj.getUpdatedBy());
							ccmConsLogObj.setUpdatedDateTime(ccmMtrlConsSaveObj.getUpdatedDateTime());
							ccmConsLogObj.setCreatedHistBy(userId);
							ccmConsLogObj.setCreatedHistDateTime(new Date());

							ccmConsLogLi.add(ccmConsLogObj);
						}
					}else if(dtoObj.getMtrlConsSlNo() == null && dtoObj.getConsQty() != null){
						ccmMtrlConsSaveObj = new CCMHeatConsMaterialsDetails();

						ccmMtrlConsSaveObj.setTrns_ccm_si_no(dtoObj.getTrnsSlNo());
						ccmMtrlConsSaveObj.setMaterial_id(dtoObj.getMtrlId());
						ccmMtrlConsSaveObj.setQty(dtoObj.getConsQty());
						ccmMtrlConsSaveObj.setConsumption_date(df.parse(consDate));
						ccmMtrlConsSaveObj.setCreatedBy(userId);
						ccmMtrlConsSaveObj.setCreatedDateTime(new Date());
						ccmMtrlConsSaveObj.setRecord_status(1);

						ccmMtrlConsSaveLi.add(ccmMtrlConsSaveObj);
					}
				}
				for(MaterialConsumptionDTO dayConsDtoObj : dayConsDtoLi) {
					if(dayConsDtoObj.getConsQty() != null && !dayConsDtoObj.getConsQty().equals(0)) {
						CCMHeatConsMaterialsDetails ccmDayConsObj = mtrlConsDao.getCcmMtrlConsByMtrlId(dayConsDtoObj.getTrnsSlNo(), dayConsDtoObj.getMtrlId());
						if(ccmDayConsObj == null) {
							ccmMtrlConsSaveObj = new CCMHeatConsMaterialsDetails();
							ccmMtrlConsSaveObj.setTrns_ccm_si_no(dayConsDtoObj.getTrnsSlNo());
							ccmMtrlConsSaveObj.setMaterial_id(dayConsDtoObj.getMtrlId());
							ccmMtrlConsSaveObj.setQty(dayConsDtoObj.getConsQty());
							ccmMtrlConsSaveObj.setConsumption_date(df.parse(consDate));
							ccmMtrlConsSaveObj.setCreatedBy(userId);
							ccmMtrlConsSaveObj.setCreatedDateTime(new Date());
							ccmMtrlConsSaveObj.setRecord_status(1);
							ccmMtrlConsSaveLi.add(ccmMtrlConsSaveObj);
						}else {
							ccmDayConsObj.setQty(dayConsDtoObj.getConsQty());
							ccmDayConsObj.setUpdatedBy(userId);
							ccmDayConsObj.setUpdatedDateTime(new Date());

							ccmMtrlConsUpdateLi.add(ccmDayConsObj);
						}
					}
					List<MaterialSubUnitMapMstrModel> unitwiseMtrlMstrLi = mtrlMapMstrDao.getMatlDetailsBySubUnitAndMtrlType(Constants.MTRL_CONS_HEAT, dayConsDtoObj.getSubUnitId());
					HeatStatusTrackingModel heatTrackObj = lrfProdDao.getHeatStatusObject(dayConsDtoObj.getHeatTrackId());
					CCMHeatDetailsModel ccmHeatObj = ccmProdDao.getHeatDtlsByHeatId(heatTrackObj.getHeat_id(), heatTrackObj.getHeat_counter());
					if(ccmHeatObj.getCasting_powder() != null) {
						List<LookupMasterModel> castingPowderPercLkp = lkpMasterDao.getLookupDtlsByLkpTypeAndStatus(Constants.LKP_TYPE_CAST_POWDER_PERC, 1);
						MaterialSubUnitMapMstrModel unitMtrlMapObj  = unitwiseMtrlMstrLi.stream().filter(p -> p.getMtrlMstrodel().getMaterial_desc().equals(Constants.CCM_MTRL_CASTING_POWDER)).collect(Collectors.toList()).get(0);
						MaterialConsumptionDTO mtrlConsDto1=new MaterialConsumptionDTO();
						LookupMasterModel valuationTypeLkpObj = lkpMasterDao.getLookUpRowById(Integer.parseInt(ccmHeatObj.getLkpCastinPowerMdl().getAttribute4()));
						//mtrlConsDto1 = new MaterialConsumptionDTO();
						mtrlConsDto1.setConsQty( dayConsDtoObj.getHeatQty() * Double.parseDouble(castingPowderPercLkp.get(0).getLookup_value()));
						mtrlConsDto1.setMtrlConsSlNo(null);
						mtrlConsDto1.setMtrlType(Constants.CCM_ADDITIONS);
						mtrlConsDto1.setMtrlName(ccmHeatObj.getLkpCastinPowerMdl().getLookup_code());
						mtrlConsDto1.setMtrlId(unitMtrlMapObj.getMtrl_id());
						mtrlConsDto1.setSapMtrlId(ccmHeatObj.getLkpCastinPowerMdl().getAttribute1());
						mtrlConsDto1.setUom(unitMtrlMapObj.getMtrlMstrodel().getUomLkpModel().getLookup_code());
						mtrlConsDto1.setValuationType((null == valuationTypeLkpObj) ? null : valuationTypeLkpObj.getLookup_code());
						mtrlConsDto1.setUpdateCounter(0);
						mtrlConsDto1.setTrnsSlNo(ccmHeatObj.getTrns_sl_no());

					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			saveUpdLi.put("SAVE_CCMMTRL_CONS", ccmMtrlConsSaveLi);
			saveUpdLi.put("UPDATE_CCMMTRL_CONS", ccmMtrlConsUpdateLi);
			saveUpdLi.put("SAVE_CCM_CONS_LOG", ccmConsLogLi);
		}

		return mtrlConsDao.saveOrUpdateMtrlConsumptions(saveUpdLi);
	}

	@Transactional
	@Override
	public List<LRFHeatDetailsModel> getLrfHeatDetailObj(String heatNo, Integer subUnitId) {
		// TODO Auto-generated method stub
		return mtrlConsDao.getLrfHeatDetailObj(heatNo, subUnitId);
	}

	//@Modified by Santosh Kr Biswal on 30 th Nov 2020 
	//Modification of quantity to SAP by keeping in mind the SAP unit of measuremnt.
	//This was changed and will move to SAP.
	@Transactional
	@Override
	public List<IntfMaterialConsumptionModel> getHeatwiseMtrlCons(String heatNo, Integer heatTrackId, String cons_date) {
		// TODO Auto-generated method stub
		List<IntfMaterialConsumptionModel> retLi = new ArrayList<IntfMaterialConsumptionModel>();
		IntfMaterialConsumptionModel intfMtrlConsObj;
		String salesOrder, salesOrderItem;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		List<ScrapBucketDetails> scrapBucketDetLi=null;
		String from_uom="",to_uom="";
		Double vValue=0.0D;
		DataConversion dataConver =new DataConversion();
		try {
			List<MtrlProcessConsumableMstrModel> hmMtrlMstrLi = commonDao.getProcConsumablesByTypeAndCode(Constants.MATERIAL_TYPE, Constants.MATERIAL_TYPE_HM);
			MtrlProcessConsumableMstrModel hmMstrObj = hmMtrlMstrLi.get(0);
			EofHeatDetails  eofHeatObj = eofProdDao.getEOFHeatDetailsByHeatNo(heatNo);

			List<HeatConsScrapMtrlDetails> eofScrapConsLi = eofProdDao.getHeatScrapConsumptionByEofId(eofHeatObj.getTrns_si_no());
			if(eofScrapConsLi.size() > 0)
			{
				scrapBucketDetLi = scrapDtlDao.getScrapBktDetList(eofScrapConsLi.get(0).getScrap_bkt_header_id());
			}

			List<HeatConsMaterialsDetails> eofMtrlConsLi = heatProcessDao.getHeatConsMtrlsDtlsByTrnsId(eofHeatObj.getTrns_si_no());
			HeatStatusTrackingModel heatTrackObj = lrfProdDao.getHeatStatusObject(heatTrackId);
			CCMHeatDetailsModel ccmHeatObj = ccmProdDao.getHeatDtlsByHeatId(heatTrackObj.getHeat_id(), heatTrackObj.getHeat_counter());
			HeatPlanLinesDetails heatPlanLineObj = heatPlanDao.getHeatPlanLineDetailsById(ccmHeatObj.getCcmProdHeatDtls().get(0).getHeat_plan_line_no());
			salesOrder = heatPlanLineObj.getSoHeaderModel().getOrderNo();
			salesOrderItem = heatPlanLineObj.getSoHeaderModel().getItemNo();

			intfMtrlConsObj = new IntfMaterialConsumptionModel();
			intfMtrlConsObj.setMtrlType(hmMstrObj.getMtrlTypeLkpModel().getLookup_code());
			intfMtrlConsObj.setMtrlName(hmMstrObj.getMaterial_desc());
			intfMtrlConsObj.setActHeatNo(heatNo);
			intfMtrlConsObj.setPostingDate(df.parse(cons_date));
			intfMtrlConsObj.setWorkCenter(eofHeatObj.getSubUnitMstrMdl().getAttribute3());
			intfMtrlConsObj.setComponent(hmMstrObj.getSap_matl_desc());
			intfMtrlConsObj.setComponentType(hmMstrObj.getParam_type());
			intfMtrlConsObj.setUom(hmMstrObj.getUomLkpModel().getLookup_code());
            if(hmMstrObj.getSapuomLkpModel() != null)
			{
				intfMtrlConsObj.setSapuom(hmMstrObj.getSapuomLkpModel().getLookup_code());
				to_uom = hmMstrObj.getSapuomLkpModel().getLookup_code();
			}
			vValue=Double.parseDouble(eofHeatObj.getHm_wt().toString());
			intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,hmMstrObj.getUomLkpModel().getLookup_code(),to_uom));
		    intfMtrlConsObj.setStatusFlag(0);
			intfMtrlConsObj.setDateCreated(new Date());
			intfMtrlConsObj.setValuationType((null == hmMstrObj.getValTypeLkpModel()) ? null : hmMstrObj.getValTypeLkpModel().getLookup_code());
			intfMtrlConsObj.setStorageLocation(hmMstrObj.getStorage_loc_1());
			intfMtrlConsObj.setSalesOrder(salesOrder);
			intfMtrlConsObj.setSalesOrderItem(salesOrderItem);

			retLi.add(intfMtrlConsObj);
			if(eofScrapConsLi.size() > 0) {
			for(ScrapBucketDetails scrapConsObj : scrapBucketDetLi) {
				if(scrapConsObj.getMaterial_qty() != null && scrapConsObj.getMaterial_qty() > 0) {
					intfMtrlConsObj = new IntfMaterialConsumptionModel();
					intfMtrlConsObj.setMtrlType(scrapConsObj.getMtrlProcessConsumableMstrModel().getMtrlTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setMtrlName(scrapConsObj.getMtrlProcessConsumableMstrModel().getMaterial_desc());
					intfMtrlConsObj.setActHeatNo(heatNo);
					intfMtrlConsObj.setPostingDate(df.parse(cons_date));
					intfMtrlConsObj.setWorkCenter(eofHeatObj.getSubUnitMstrMdl().getAttribute3());
					intfMtrlConsObj.setComponent(scrapConsObj.getMtrlProcessConsumableMstrModel().getSap_matl_desc());
					intfMtrlConsObj.setComponentType(scrapConsObj.getMtrlProcessConsumableMstrModel().getParam_type());
					intfMtrlConsObj.setQty(scrapConsObj.getMaterial_qty());
					intfMtrlConsObj.setUom(scrapConsObj.getMtrlProcessConsumableMstrModel().getUomLkpModel().getLookup_code());

					/*if(scrapConsObj.getMtrlProcessConsumableMstrModel().getSapuomLkpModel() != null)
					{
						intfMtrlConsObj.setSapuom(scrapConsObj.getMtrlProcessConsumableMstrModel().getSapuomLkpModel().getLookup_code());
						to_uom = scrapConsObj.getMtrlProcessConsumableMstrModel().getSapuomLkpModel().getLookup_code();
					}

					vValue=Double.parseDouble(scrapConsObj.getMaterial_qty().toString());
					intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,scrapConsObj.getMtrlProcessConsumableMstrModel().getUomLkpModel().getLookup_code(),to_uom));
*/                  intfMtrlConsObj.setStatusFlag(0);
					intfMtrlConsObj.setDateCreated(new Date());
					intfMtrlConsObj.setValuationType((null == scrapConsObj.getMtrlProcessConsumableMstrModel().getValTypeLkpModel()) ? null : scrapConsObj.getMtrlProcessConsumableMstrModel().getValTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setStorageLocation(scrapConsObj.getMtrlProcessConsumableMstrModel().getStorage_loc_1());
					intfMtrlConsObj.setSalesOrder(salesOrder);
					intfMtrlConsObj.setSalesOrderItem(salesOrderItem);

					retLi.add(intfMtrlConsObj);
				}
			}
			}
			for(HeatConsMaterialsDetails eofConsObj : eofMtrlConsLi) {
				Double totalConsumedQty = 0.0;
				Integer mtrlCount = 0;
				List<HeatConsMaterialsDetails> li = null;
				List<HeatConsMaterialsDetails> notNullConsList = eofMtrlConsLi.stream().filter(o->!o.getMtrlMstrModel().getSap_matl_desc().isEmpty()).collect(Collectors.toList());
				List<IntfMaterialConsumptionModel> notNullIntfList = retLi.stream().filter(o->!o.getComponent().isEmpty()).collect(Collectors.toList());

				if(eofConsObj.getMtrlMstrModel().getSap_matl_desc() != null){
					li = notNullConsList.stream().filter(o->o.getMtrlMstrModel().getSap_matl_desc().equals(eofConsObj.getMtrlMstrModel().getSap_matl_desc())).collect(Collectors.toList());
					mtrlCount = notNullIntfList.stream().filter(o->o.getComponent().contains(eofConsObj.getMtrlMstrModel().getSap_matl_desc())).collect(Collectors.toList()).size();
				}
				if(li != null && li.size() > 1)
					totalConsumedQty = li.stream().mapToDouble(o -> o.getQty()).sum();
				else
					totalConsumedQty = eofConsObj.getQty();

				//        	if(eofConsObj.getQty() != null && eofConsObj.getQty() > 0 ) 
				if( mtrlCount == 0 && totalConsumedQty > 0){
					intfMtrlConsObj = new IntfMaterialConsumptionModel();
					intfMtrlConsObj.setMtrlType(eofConsObj.getMtrlMstrModel().getMtrlTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setMtrlName(eofConsObj.getMtrlMstrModel().getMaterial_desc());
					intfMtrlConsObj.setActHeatNo(heatNo);
					intfMtrlConsObj.setPostingDate(df.parse(cons_date));
					intfMtrlConsObj.setWorkCenter(eofHeatObj.getSubUnitMstrMdl().getAttribute3());
					intfMtrlConsObj.setComponent(eofConsObj.getMtrlMstrModel().getSap_matl_desc());
					intfMtrlConsObj.setComponentType(eofConsObj.getMtrlMstrModel().getParam_type());
					
                    if(eofConsObj.getMtrlMstrModel().getSapuomLkpModel() != null)
					{
                    	intfMtrlConsObj.setUom(eofConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
						intfMtrlConsObj.setSapuom(eofConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());	
						to_uom = eofConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code();
					}
					vValue=Double.parseDouble(totalConsumedQty.toString());
					intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,eofConsObj.getMtrlMstrModel().getUomLkpModel().getLookup_code(),to_uom));
                    intfMtrlConsObj.setStatusFlag(0);
					intfMtrlConsObj.setDateCreated(new Date());
					intfMtrlConsObj.setValuationType((null == eofConsObj.getMtrlMstrModel().getValTypeLkpModel()) ? null : eofConsObj.getMtrlMstrModel().getValTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setStorageLocation(eofConsObj.getMtrlMstrModel().getStorage_loc_1());
					intfMtrlConsObj.setSalesOrder(salesOrder);
					intfMtrlConsObj.setSalesOrderItem(salesOrderItem);

					retLi.add(intfMtrlConsObj);
				}
			}

			List<SubUnitMasterModel> subUnitLi = subUnitMstrDao.getSubUnitMasterDetails("1");
			List<SubUnitMasterModel> lrfSubUnitLi = subUnitLi.stream().filter(p -> p.getUnitDetailsMstrMdl().getUnit_name().equals(Constants.SUB_UNIT_LRF)).collect(Collectors.toList());
			for(SubUnitMasterModel subUnitObj : lrfSubUnitLi) {
				List<LRFHeatDetailsModel> lrfHeatObjLi = mtrlConsDao.getLrfHeatDetailObj(heatNo, subUnitObj.getSub_unit_id());
				for(LRFHeatDetailsModel lrfHeatObj : lrfHeatObjLi) {
					List<LRFHeatConsumableModel> lrfConsLi = mtrlConsDao.getLrfMtrlConsumptions(lrfHeatObj.getHeat_id(), lrfHeatObj.getHeat_counter());
					List<LRFHeatConsumableModel> mtrlGrpLi = lrfConsLi.stream().filter(distinctByKey(p -> p.getMaterial_id())).collect(Collectors.toList());
					//List<LRFHeatConsumableModel> mtrlGrpLi = lrfConsLi.stream().filter(p -> p.getMaterial_id().equals(p.getMaterial_id())).collect(Collectors.toList());

					for(LRFHeatConsumableModel lrfGrpObj : mtrlGrpLi) {
						//Double totalConsumedQty = mtrlGrpLi.stream().mapToDouble(o -> o.getConsumption_qty()).sum();
						Double totalConsumedQty = 0.0;
						totalConsumedQty = lrfConsLi.stream().filter(o -> o.getMaterial_id().equals(lrfGrpObj.getMaterial_id())).mapToDouble(o -> o.getConsumption_qty()).sum();
						if(totalConsumedQty > 0) {
							intfMtrlConsObj = new IntfMaterialConsumptionModel();
							intfMtrlConsObj.setMtrlType(lrfGrpObj.getMtrlMstrModel().getMtrlTypeLkpModel().getLookup_code());
							intfMtrlConsObj.setMtrlName(lrfGrpObj.getMtrlMstrModel().getMaterial_desc());
							intfMtrlConsObj.setActHeatNo(heatNo);
							intfMtrlConsObj.setPostingDate(df.parse(cons_date));
							intfMtrlConsObj.setWorkCenter(lrfHeatObj.getSubUnitMstrMdl().getAttribute3());
							intfMtrlConsObj.setComponent(lrfGrpObj.getMtrlMstrModel().getSap_matl_desc());
							intfMtrlConsObj.setComponentType(lrfGrpObj.getMtrlMstrModel().getParam_type());
							//intfMtrlConsObj.setQty(totalConsumedQty);

							
							if(lrfGrpObj.getMtrlMstrModel().getSapuomLkpModel() != null)
							{
								intfMtrlConsObj.setUom(lrfGrpObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
								intfMtrlConsObj.setSapuom(lrfGrpObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
								to_uom = lrfGrpObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code();
							}
							vValue=Double.parseDouble(totalConsumedQty.toString());
							intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,lrfGrpObj.getMtrlMstrModel().getUomLkpModel().getLookup_code(),to_uom));
                            intfMtrlConsObj.setStatusFlag(0);
							intfMtrlConsObj.setDateCreated(new Date());
							intfMtrlConsObj.setValuationType((null == lrfGrpObj.getMtrlMstrModel().getValTypeLkpModel()) ? null : lrfGrpObj.getMtrlMstrModel().getValTypeLkpModel().getLookup_code());
							intfMtrlConsObj.setStorageLocation(lrfGrpObj.getMtrlMstrModel().getStorage_loc_1());
							intfMtrlConsObj.setSalesOrder(salesOrder);
							intfMtrlConsObj.setSalesOrderItem(salesOrderItem);

							retLi.add(intfMtrlConsObj);
						}
					}
				}
			}
			VDHeatDetailsModel vdHeatObj = vdProdDao.getVDHeatDetailsByHeatNo(heatNo);
			List<VDHeatConsumableModel> vdConsLi = mtrlConsDao.getVDMtrlConsumptions(heatNo);
			for(VDHeatConsumableModel vdConsObj : vdConsLi) {
				if(vdConsObj.getConsumption_qty() != null && vdConsObj.getConsumption_qty() > 0) {
					intfMtrlConsObj = new IntfMaterialConsumptionModel();
					intfMtrlConsObj.setMtrlType(vdConsObj.getMtrlMstrModel().getMtrlTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setMtrlName(vdConsObj.getMtrlMstrModel().getMaterial_desc());
					intfMtrlConsObj.setActHeatNo(heatNo);
					intfMtrlConsObj.setPostingDate(df.parse(cons_date));
					intfMtrlConsObj.setWorkCenter(vdHeatObj.getSubUnitMstrMdl().getAttribute3());
					intfMtrlConsObj.setComponent(vdConsObj.getMtrlMstrModel().getSap_matl_desc());
					intfMtrlConsObj.setComponentType(vdConsObj.getMtrlMstrModel().getParam_type());
					//intfMtrlConsObj.setQty(vdConsObj.getConsumption_qty());

					
					if(vdConsObj.getMtrlMstrModel().getSapuomLkpModel() != null)
					{
						intfMtrlConsObj.setUom(vdConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
						intfMtrlConsObj.setSapuom(vdConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
						to_uom=vdConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code();
					}
					vValue=Double.parseDouble(vdConsObj.getConsumption_qty().toString());
					intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,vdConsObj.getMtrlMstrModel().getUomLkpModel().getLookup_code(),to_uom));


					intfMtrlConsObj.setStatusFlag(0);
					intfMtrlConsObj.setDateCreated(new Date());
					intfMtrlConsObj.setValuationType((null == vdConsObj.getMtrlMstrModel().getValTypeLkpModel()) ? null : vdConsObj.getMtrlMstrModel().getValTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setStorageLocation(vdConsObj.getMtrlMstrModel().getStorage_loc_1());
					intfMtrlConsObj.setSalesOrder(salesOrder);
					intfMtrlConsObj.setSalesOrderItem(salesOrderItem);

					retLi.add(intfMtrlConsObj);
				}
			}

			List<CCMHeatConsMaterialsDetails> ccmConsLi = mtrlConsDao.getCCMMtrlConsumptions(ccmHeatObj.getTrns_sl_no());
			List<MaterialSubUnitMapMstrModel> unitwiseMtrlMstrLi = mtrlMapMstrDao.getMatlDetailsBySubUnitAndMtrlType(Constants.MTRL_CONS_HEAT, ccmHeatObj.getSub_unit_id());
			boolean casterPowderInsert=true;
			for(CCMHeatConsMaterialsDetails ccmConsObj : ccmConsLi) {
				if(ccmConsObj.getQty() != null && ccmConsObj.getQty() > 0) {
					intfMtrlConsObj = new IntfMaterialConsumptionModel();
					intfMtrlConsObj.setMtrlType(ccmConsObj.getMtrlMstrModel().getMtrlTypeLkpModel().getLookup_code());
					intfMtrlConsObj.setMtrlName(ccmConsObj.getMtrlMstrModel().getMaterial_desc());
					intfMtrlConsObj.setActHeatNo(heatNo);
					intfMtrlConsObj.setPostingDate(df.parse(cons_date));
					intfMtrlConsObj.setWorkCenter(ccmHeatObj.getSubUnitMstrMdl().getAttribute3());
					intfMtrlConsObj.setComponentType(ccmConsObj.getMtrlMstrModel().getParam_type());
					//intfMtrlConsObj.setQty(ccmConsObj.getQty());

					

					if(ccmConsObj.getMtrlMstrModel().getSapuomLkpModel() != null)
					{
						intfMtrlConsObj.setUom(ccmConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
						intfMtrlConsObj.setSapuom(ccmConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code());
						to_uom=ccmConsObj.getMtrlMstrModel().getSapuomLkpModel().getLookup_code();
					}
					vValue=Double.parseDouble(ccmConsObj.getQty().toString());
					intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,ccmConsObj.getMtrlMstrModel().getUomLkpModel().getLookup_code(),to_uom));

					intfMtrlConsObj.setStatusFlag(0);
					intfMtrlConsObj.setDateCreated(new Date());
					intfMtrlConsObj.setSalesOrder(salesOrder);
					intfMtrlConsObj.setSalesOrderItem(salesOrderItem);
					if(ccmConsObj.getMtrlMstrModel().getMaterial_desc().equals(Constants.CCM_MTRL_CASTING_POWDER) ) {
						intfMtrlConsObj.setComponent(ccmHeatObj.getLkpCastinPowerMdl().getAttribute1());
						intfMtrlConsObj.setStorageLocation(ccmHeatObj.getLkpCastinPowerMdl().getAttribute3());
						LookupMasterModel valuationTypeLkpObj = lkpMasterDao.getLookUpRowById(Integer.parseInt(ccmHeatObj.getLkpCastinPowerMdl().getAttribute4()));
						intfMtrlConsObj.setValuationType((null == valuationTypeLkpObj) ? null : valuationTypeLkpObj.getLookup_code());
						casterPowderInsert=false;
					}else {
						intfMtrlConsObj.setComponent(ccmConsObj.getMtrlMstrModel().getSap_matl_desc());
						intfMtrlConsObj.setStorageLocation(ccmConsObj.getMtrlMstrModel().getStorage_loc_1());
						intfMtrlConsObj.setValuationType((null == ccmConsObj.getMtrlMstrModel().getValTypeLkpModel()) ? null : ccmConsObj.getMtrlMstrModel().getValTypeLkpModel().getLookup_code());
					}
					retLi.add(intfMtrlConsObj);
				}
			}
			if(casterPowderInsert)
			{
				List<MaterialSubUnitMapMstrModel> unitwiseMtrlMstrLiCaPowder = mtrlMapMstrDao.getMatlDetailsBySubUnitAndMtrlType(Constants.MTRL_CONS_HEAT, ccmHeatObj.getSub_unit_id());
				List<MaterialSubUnitMapMstrModel> unitwiseMtrlMstrNewLi = unitwiseMtrlMstrLiCaPowder.stream().filter(p -> !(p.getMtrlMstrodel().getMaterial_desc().contains(Constants.CCM_MTRL_CASTING_POWDER))).collect(Collectors.toList());
				List<LookupMasterModel> castingPowderPercLkp = lkpMasterDao.getLookupDtlsByLkpTypeAndStatus(Constants.LKP_TYPE_CAST_POWDER_PERC, 1);
				MaterialSubUnitMapMstrModel unitMtrlMapObj  = unitwiseMtrlMstrLiCaPowder.stream().filter(p -> p.getMtrlMstrodel().getMaterial_desc().equals(Constants.CCM_MTRL_CASTING_POWDER)).collect(Collectors.toList()).get(0);
				for(MaterialSubUnitMapMstrModel unitobj:unitwiseMtrlMstrLiCaPowder) {
					if(unitobj.getMtrlMstrodel().getMaterial_desc().contains(Constants.CCM_MTRL_CASTING_POWDER)) {
						intfMtrlConsObj = new IntfMaterialConsumptionModel();
						intfMtrlConsObj.setMtrlType(Constants.CCM_MTRL_CASTING_POWDER);
						intfMtrlConsObj.setMtrlName(ccmHeatObj.getLkpCastinPowerMdl().getLookup_code());
						intfMtrlConsObj.setActHeatNo(heatNo);
						intfMtrlConsObj.setPostingDate(df.parse(cons_date));
						intfMtrlConsObj.setWorkCenter(ccmHeatObj.getSubUnitMstrMdl().getAttribute3());
						intfMtrlConsObj.setComponent(ccmHeatObj.getLkpCastinPowerMdl().getAttribute1());
						intfMtrlConsObj.setComponentType(unitMtrlMapObj.getMtrlMstrodel().getParam_type());
						Double totalBatchQty = 0.0D;
						List<CCMProductDetailsModel>  ccmProdLi = ccmHeatObj.getCcmProdHeatDtls();
						for(CCMProductDetailsModel ccmProdObj : ccmProdLi) {
							List<CCMBatchDetailsModel> ccmBatchLi = ccmProdObj.getCcmBatchDtls();
							totalBatchQty = totalBatchQty + ccmBatchLi.stream().mapToDouble(o -> o.getAct_batch_wgt()).sum();
						}
						//intfMtrlConsObj.setQty( totalBatchQty * Double.parseDouble(castingPowderPercLkp.get(0).getLookup_value()));

						
						if(unitMtrlMapObj.getMtrlMstrodel().getSapuomLkpModel() != null)
						{
							intfMtrlConsObj.setUom(unitMtrlMapObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
							intfMtrlConsObj.setSapuom(unitMtrlMapObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code());
							to_uom=unitMtrlMapObj.getMtrlMstrodel().getSapuomLkpModel().getLookup_code();
						}
						vValue= totalBatchQty * Double.parseDouble(castingPowderPercLkp.get(0).getLookup_value());
						intfMtrlConsObj.setQty(dataConver.dataConversion(vValue,unitMtrlMapObj.getMtrlMstrodel().getUomLkpModel().getLookup_code(),to_uom));


						intfMtrlConsObj.setStatusFlag(0);
						intfMtrlConsObj.setDateCreated(new Date());
						intfMtrlConsObj.setSalesOrder(salesOrder);
						intfMtrlConsObj.setSalesOrderItem(salesOrderItem);
						intfMtrlConsObj.setStorageLocation(ccmHeatObj.getLkpCastinPowerMdl().getAttribute3());
						LookupMasterModel valuationTypeLkpObj = lkpMasterDao.getLookUpRowById(Integer.parseInt(ccmHeatObj.getLkpCastinPowerMdl().getAttribute4()));
						intfMtrlConsObj.setValuationType((null == valuationTypeLkpObj) ? null : valuationTypeLkpObj.getLookup_code());
						/*if(ccmConsObj.getMtrlMstrModel().getMaterial_desc().equals(Constants.CCM_MTRL_CASTING_POWDER) ) {
				intfMtrlConsObj.setComponent(ccmHeatObj.getLkpCastinPowerMdl().getAttribute1());
				intfMtrlConsObj.setStorageLocation(ccmHeatObj.getLkpCastinPowerMdl().getAttribute3());
				LookupMasterModel valuationTypeLkpObj = lkpMasterDao.getLookUpRowById(Integer.parseInt(ccmHeatObj.getLkpCastinPowerMdl().getAttribute4()));
				intfMtrlConsObj.setValuationType((null == valuationTypeLkpObj) ? null : valuationTypeLkpObj.getLookup_code());
				casterPowderInsert=false;
			}else {
				intfMtrlConsObj.setComponent(ccmConsObj.getMtrlMstrModel().getSap_matl_desc());
				intfMtrlConsObj.setStorageLocation(ccmConsObj.getMtrlMstrModel().getStorage_loc_1());
				intfMtrlConsObj.setValuationType((null == ccmConsObj.getMtrlMstrModel().getValTypeLkpModel()) ? null : ccmConsObj.getMtrlMstrModel().getValTypeLkpModel().getLookup_code());
			}*/
					}
				}
				retLi.add(intfMtrlConsObj);
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return retLi;
	}
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> key) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(key.apply(t), Boolean.TRUE) == null;
	}

	@Transactional
	@Override
	public String postMtrlConsumptions(IntfMaterialConsumptionModel intfMtrlConsObj, Integer userId) {
		// TODO Auto-generated method stub
		List<IntfMaterialConsumptionModel> saveIntfConsLi = new ArrayList<IntfMaterialConsumptionModel>();
		HeatStatusTrackingModel heatTrackObj = lrfProdDao.getHeatStatusObject(intfMtrlConsObj.getHeatTrackId());
		heatTrackObj.setConsPostedBy(userId);
		heatTrackObj.setConsPostedDate(new Date());
		heatTrackObj.setIsConsPosted("Y");

		List<IntfMaterialConsumptionModel> uniqueMtrlconsLi = intfMtrlConsObj.getMtrlConsLi().stream().filter(distinctByKey(p -> p.getComponent())).collect(Collectors.toList());
		for(IntfMaterialConsumptionModel intfConsObj : uniqueMtrlconsLi) {
			Double totalConsumedQty = 0.0;
			totalConsumedQty = uniqueMtrlconsLi.stream().filter(o -> o.getComponent().equals(intfConsObj.getComponent())).mapToDouble(o -> o.getQty()).sum();
			intfConsObj.setQty(totalConsumedQty);
			saveIntfConsLi.add(intfConsObj);
		}
		
		return mtrlConsDao.postMtrlConsumptions(saveIntfConsLi, heatTrackObj);
	}
}