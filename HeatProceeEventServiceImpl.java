package com.smes.trans.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.model.LookupMasterModel;
import com.smes.masters.model.ScrapBucketStatusModel;
import com.smes.masters.service.impl.EventMasterService;
import com.smes.masters.service.impl.LookupMasterService;
import com.smes.masters.service.impl.ProcessParamsMasterService;
import com.smes.masters.service.impl.ScrapBucketStatusMasterService;
import com.smes.masters.service.impl.SteelLadleStatusMasterService;
import com.smes.trans.dao.impl.HeatPlanDetailsDao;
import com.smes.trans.dao.impl.HeatProceeEventDao;
import com.smes.trans.dao.impl.LRFProductionDao;
import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.trans.model.EofDispatchDetails;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatChemistryChildDetails;
import com.smes.trans.model.HeatChemistryChildDetailsHistory;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatConsMaterialsDetails;
import com.smes.trans.model.HeatConsScrapMtrlDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatProcessParameterDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.HmReceiveBaseDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.ScrapBucketHdr;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLadleMaintStatusModel;
import com.smes.trans.model.StLadlePartsMaintLogModel;
import com.smes.trans.model.StLdlHeatingDtls;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleCampaignModel;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericClass;
import com.smes.util.SpectroLabUtil;
import com.smes.wrappers.EOFRequestWrapper;

@Service("HeatProceeEventService")
public class HeatProceeEventServiceImpl implements HeatProceeEventService{
	
	@Autowired
	private HeatProceeEventDao heatProceeEventDao;
	
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ScrapEntryService scrapEntryService;
	
	@Autowired
	private ScrapBucketStatusMasterService scrapBucketStatusMasterService;
	
	@Autowired
	private HeatPlanDetailsDao heatPlanDetailsDao;
	
	@Autowired
	private SteelLadleStatusMasterService steelLadleService;
	
	@Autowired
	private HotMetalReceiveService hotMetalRecvService;
	
	@Autowired
	private EventMasterService eventMstrService;
	
	@Autowired
	private ProcessParamsMasterService processParamService;
	
	@Autowired
	private LRFProductionDao lrfProdDao;
	
	@Autowired
	private SteelLadleMaintnService stLdlMaintService;
	
	@Autowired
	SpectroLabUtil spectroLabUtil;
	
	@Autowired
	LookupMasterService lookupmstrDao;
	
	
	@Override
	public String saveHeatProcessEvent(HeatProcessEventDetails hped) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.saveHeatProcessEvent(hped);
	}

	@Transactional
	@Override
	public List<HeatConsMaterialsDetails> getMtrlDetailsByType(String mtrlType,Integer eof_trns_sno,String psn_no) {
		// TODO Auto-generated method stub
		List<HeatConsMaterialsDetails> list=heatProceeEventDao.getMtrlDetailsByType(mtrlType,eof_trns_sno,psn_no);
		

		Iterator<HeatConsMaterialsDetails> iterator = list.iterator();
        while (iterator.hasNext()) {
        	HeatConsMaterialsDetails data = iterator.next();
        	Integer sl_no=data.getMtr_cons_si_no();
        	Optional<Integer> sc = Optional.ofNullable(sl_no); 
//            if (!sc.isPresent() && data.getRecord_status()==0) {
//                iterator.remove();
//            }
        }
	
		return list;
	}
	@Transactional
	@Override
	public List<CCMHeatConsMaterialsDetails> getMtrlDetailsByCCMType(String mtrlType,Integer eof_trns_sno,String psn_no) {
		// TODO Auto-generated method stub
		List<CCMHeatConsMaterialsDetails> list=heatProceeEventDao.getMtrlDetailsByCCMType(mtrlType,eof_trns_sno,psn_no);
		

		Iterator<CCMHeatConsMaterialsDetails> iterator = list.iterator();
        while (iterator.hasNext()) {
        	CCMHeatConsMaterialsDetails data = iterator.next();
        	Integer sl_no=data.getMtr_cons_si_no();
        	Optional<Integer> sc = Optional.ofNullable(sl_no); 
//            if (!sc.isPresent() && data.getRecord_status()==0) {
//                iterator.remove();
//            }
        }
	
		return list;
	}
	
	@Override
	public EofHeatDetails getEofHeatDtlsById(Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getEofHeatDtlsById(eof_trns_sno);
	}

	@Transactional
	@Override
	public EofHeatDetails getEOFHeatDtlsFormByID(Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getEOFHeatDtlsFormByID(eof_trns_sno);
	}
	
	

	@Transactional
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsByAnalysis(
			Integer analysis_id,String heat_id, Integer heat_counter,Integer sub_unit_id,String sample_no,Integer psn_id,Integer hm_recv_id,String copy_chem) {
		// TODO Auto-generated method stub
		List<HeatChemistryChildDetails> hcc= heatProceeEventDao
					.getChemDtlsByAnalysis(analysis_id, psn_id);
		
		
		String qry = "select a from HmReceiveBaseDetails a where a.hmRecvId="
				+ hm_recv_id;
		List<HmReceiveBaseDetails> hmRecvDetails = hotMetalRecvService
				.getHMDetailsByQuery(qry);
		HmReceiveBaseDetails hmReceiveDtls = hmRecvDetails.get(0);

		if (copy_chem.equalsIgnoreCase("Y")) {
			hcc.forEach((data) -> {
				if (data.getElementName().equalsIgnoreCase("C")) {
					data.setAim_value(hmReceiveDtls.getHmLadleC());
				}
				if (data.getElementName().equalsIgnoreCase("Mn")) {
					data.setAim_value(hmReceiveDtls.getHmLadleMn());
				}
				if (data.getElementName().equalsIgnoreCase("S")) {
					data.setAim_value(hmReceiveDtls.getHmLadleS());
				}
				if (data.getElementName().equalsIgnoreCase("P")) {
					data.setAim_value(hmReceiveDtls.getHmLadleP());
				}
				if (data.getElementName().equalsIgnoreCase("Si")) {
					data.setAim_value(hmReceiveDtls.getHmLadleSi());
				}
				if (data.getElementName().equalsIgnoreCase("Ti")) {
					data.setAim_value(hmReceiveDtls.getHmLadleTi());
				}
			});
		}
		return hcc;
	}
	
	@Transactional
	@Override
	public List<HeatChemistryChildDetails> getChemDtlsBySpectro(
			Integer analysis_id,String heat_id, Integer heat_counter,Integer sub_unit_id,String sample_no,Integer psn_id,Integer hm_recv_id,String copy_chem,String actual_sample) throws IOException {
		// TODO Auto-generated method stub
		
		Boolean activeConnect=spectroLabUtil.testConnection();
		List<HeatChemistryChildDetails> hcc =new ArrayList<>();
		if(activeConnect==true) {
		hcc = heatProceeEventDao
					.getChemDtlsByAnalysisWithSpectro(analysis_id, psn_id,actual_sample,heat_id);
		}
		else {
			 hcc= heatProceeEventDao
					.getChemDtlsByAnalysis(analysis_id, psn_id);
		}
		
		/*List<HeatChemistryChildDetails> hcc = heatProceeEventDao
				.getChemDtlsByAnalysisWithSpectro(analysis_id, psn_id,actual_sample,heat_id);*/
		
		String qry = "select a from HmReceiveBaseDetails a where a.hmRecvId="
				+ hm_recv_id;
		List<HmReceiveBaseDetails> hmRecvDetails = hotMetalRecvService
				.getHMDetailsByQuery(qry);
		HmReceiveBaseDetails hmReceiveDtls = hmRecvDetails.get(0);

		if (copy_chem.equalsIgnoreCase("Y")) {
			hcc.forEach((data) -> {
				if (data.getElementName().equalsIgnoreCase("C")) {
					data.setAim_value(hmReceiveDtls.getHmLadleC());
				}
				if (data.getElementName().equalsIgnoreCase("Mn")) {
					data.setAim_value(hmReceiveDtls.getHmLadleMn());
				}
				if (data.getElementName().equalsIgnoreCase("S")) {
					data.setAim_value(hmReceiveDtls.getHmLadleS());
				}
				if (data.getElementName().equalsIgnoreCase("P")) {
					data.setAim_value(hmReceiveDtls.getHmLadleP());
				}
				if (data.getElementName().equalsIgnoreCase("Si")) {
					data.setAim_value(hmReceiveDtls.getHmLadleSi());
				}
				if (data.getElementName().equalsIgnoreCase("Ti")) {
					data.setAim_value(hmReceiveDtls.getHmLadleTi());
				}
			});
		}
		return hcc;
	}

	/*@Transactional
	@Override
	public HeatChemistryHdrDetails getChemHdrDtlsByAnalysis(
			Integer analysis_id, Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getChemHdrDtlsByAnalysis(analysis_id, eof_trns_sno);
	}*/
	
	@Transactional
	@Override
	public HeatConsMaterialsDetails getHeatConsMtrlsDtlsById(
			Integer Mtrl_Cons_ID) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatConsMtrlsDtlsById(Mtrl_Cons_ID);
	}
	
	@Override
	public CCMHeatConsMaterialsDetails getccmHeatConsMtrlsDtlsById(
			Integer Mtrl_Cons_ID) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getccmHeatConsMtrlsDtlsById(Mtrl_Cons_ID);
	}
	@Override
	public List<HeatChemistryHdrDetails> getSampleDtlsByAnalysis(
			Integer sub_unit_id, String heat_id, Integer analysis_id, Integer heat_counter) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getSampleDtlsByAnalysis(sub_unit_id, heat_id,
				analysis_id, heat_counter);
	}

	@Transactional
	@Override
	public HeatChemistryHdrDetails getHeatChemistryHdrDtlsById(
			Integer sample_si_Id, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatChemistryHdrDtlsById(sample_si_Id,sub_unit_id);
	}

	@Transactional
	@Override
	public HeatChemistryChildDetails getHeatChemistryChildDtlsById(Integer dtls_si_Id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatChemistryChildDtlsById(dtls_si_Id);
	}
	
	public String furnaceAdditionMtrlsSaveOrUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		
		return heatProceeEventDao.furnaceAdditionMtrlsSaveOrUpdate(mod_obj);
	}
	public String ccmAdditionMtrlsSaveOrUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		
		return heatProceeEventDao.ccmAdditionMtrlsSaveOrUpdate(mod_obj);
	}
	public String heatConsMtrlsSaveOrUpdate(
			HeatConsMaterialsDetails heatConsMtrls, Integer sub_unit,
			String eventname, Integer userid) {
		// TODO Auto-generated method stub

		String result="";
		
		Hashtable<String, Object> mod_obj = null;
		
		mod_obj=new Hashtable<String, Object>();
		if(heatConsMtrls.getTrns_eof_si_no() >0 && heatConsMtrls.getTrns_eof_si_no() !=null){
		
			/*Integer eventId = 0;
		EofHeatDetails eofHeatDtls = new EofHeatDetails();
		eofHeatDtls = getEofHeatDtlsById(heatConsMtrls.getTrns_eof_si_no());
		
		if(sub_unit != null && sub_unit>0){
		 eventId =commonService.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "+sub_unit+" and event_desc = '"+eventname+"'and recordStatus=1");
		}
		
		HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
		
		processEvnt.setHeat_proc_event_id(null);
		processEvnt.setHeat_id(eofHeatDtls.getHeat_id());
		processEvnt.setHeat_counter(eofHeatDtls.getHeat_counter());
		processEvnt.setEvent_id(eventId);
		processEvnt.setEvent_date_time(new Date());
		processEvnt.setCreatedBy(userid);
		processEvnt.setCreatedDateTime(new Date());
		
		mod_obj.put("HEATPROEVNT", processEvnt);*/
		
		if(heatConsMtrls.getGrid_arry() != null && heatConsMtrls.getGrid_arry().toString() != ""&& !(heatConsMtrls.getGrid_arry().isEmpty())){
			
			String row[]=heatConsMtrls.getGrid_arry().split("SIDS");
			Integer cnt=0;String key="HEATCONSMTRL";
			HeatConsMaterialsDetails heatConsObj = null;
			
			
			for(int i=0;i<row.length;i++){
				
				String id[] = row[i].split("@");
				
				key=key+i;
				heatConsObj =new HeatConsMaterialsDetails();
				
					if(id[3].equalsIgnoreCase("null")){
						//insert operation
						heatConsObj.setMtr_cons_si_no(null);
						heatConsObj.setTrns_eof_si_no(heatConsMtrls.getTrns_eof_si_no());
						heatConsObj.setCreatedBy(userid);
						heatConsObj.setCreatedDateTime(new Date());
						heatConsObj.setVersion(0);
						heatConsObj.setRecord_status(1);
						heatConsObj.setMaterial_id(Integer.parseInt(id[0]));
						heatConsObj.setQty(Double.parseDouble(id[1].toString()));
						heatConsObj.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						if(eventname.equals("FURNACE_ADDITION") || eventname.equals("LADLE_ADDITIONS"))
						{
						heatConsObj.setSap_matl_id(("NA" == id[5].toString() || (null == id[5].toString())) ? "" : id[5].toString());
						heatConsObj.setValuation_type(("NA" == id[6].toString() || (null == id[6].toString())) ? "" : id[6].toString());
						}
					}else{
						//Update operation
						 heatConsObj= getHeatConsMtrlsDtlsById(Integer.parseInt(id[3]));
						 heatConsObj.setMaterial_id(Integer.parseInt(id[0]));
						 heatConsObj.setQty(Double.parseDouble(id[1].toString()));
						 heatConsObj.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						 heatConsObj.setUpdatedBy(userid);
						 heatConsObj.setUpdatedDateTime(new Date());
						 heatConsObj.setVersion(Integer.parseInt(id[4]));
						 if(eventname.equals("FURNACE_ADDITION") || eventname.equals("LADLE_ADDITIONS"))
						 {
						  heatConsObj.setSap_matl_id(("NA" == id[5].toString() || (null == id[5].toString())) ? "" : id[5].toString());
						  heatConsObj.setValuation_type(("NA" == id[6].toString() || (null == id[6].toString())) ? "" : id[6].toString());
						 }
						}

					mod_obj.put(key, heatConsObj);
					cnt=cnt+1;
					heatConsObj=null;
					
			 }// end out for
			
			mod_obj.put("HEATCONSMTRLCNT", cnt);
			
		}// end if grid array
		else{
			mod_obj.put("HEATCONSMTRLCNT", 0);
		}//end else grid array
			
			result = furnaceAdditionMtrlsSaveOrUpdate(mod_obj);
		
		}// end if
		
		
		
		return result;
	}

	
	public String ccmheatConsMtrlsSaveOrUpdate(
			CCMHeatConsMaterialsDetails ccmheatConsMtrls, Integer sub_unit,
			String eventname, Integer userid) {
		// TODO Auto-generated method stub

		String result="";
		
		Hashtable<String, Object> mod_obj = null;
		
		mod_obj=new Hashtable<String, Object>();
		if(ccmheatConsMtrls.getTrns_ccm_si_no() >0 && ccmheatConsMtrls.getTrns_ccm_si_no() !=null){
		
		if(ccmheatConsMtrls.getGrid_arry() != null && ccmheatConsMtrls.getGrid_arry().toString() != ""&& !(ccmheatConsMtrls.getGrid_arry().isEmpty())){
			
			String row[]=ccmheatConsMtrls.getGrid_arry().split("SIDS");
			Integer cnt=0;String key="HEATCONSMTRL";
			CCMHeatConsMaterialsDetails heatConsObj = null;
			
			
			for(int i=0;i<row.length;i++){
				
				String id[] = row[i].split("@");
				
				key=key+i;
				heatConsObj =new CCMHeatConsMaterialsDetails();
				
					if(id[3].equalsIgnoreCase("null")){
						//insert operation
						heatConsObj.setMtr_cons_si_no(null);
						heatConsObj.setTrns_ccm_si_no(ccmheatConsMtrls.getTrns_ccm_si_no() );
						heatConsObj.setCreatedBy(userid);
						heatConsObj.setCreatedDateTime(new Date());
						heatConsObj.setVersion(0);
						heatConsObj.setRecord_status(1);
						heatConsObj.setMaterial_id(Integer.parseInt(id[0]));
						heatConsObj.setQty(Double.parseDouble(id[1].toString()));
						heatConsObj.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						if(eventname.equals("CCM_ADDITION"))
						{
						heatConsObj.setSap_matl_id(("NA" == id[5].toString() || (null == id[5].toString())) ? "" : id[5].toString());
						heatConsObj.setValuation_type(("NA" == id[6].toString() || (null == id[6].toString())) ? "" : id[6].toString());
						}
					}else{
						//Update operation
						 heatConsObj= getccmHeatConsMtrlsDtlsById(Integer.parseInt(id[3]));
						 heatConsObj.setMaterial_id(Integer.parseInt(id[0]));
						 heatConsObj.setQty(Double.parseDouble(id[1].toString()));
						 heatConsObj.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						 heatConsObj.setUpdatedBy(userid);
						 heatConsObj.setUpdatedDateTime(new Date());
						 heatConsObj.setVersion(Integer.parseInt(id[4]));
						 if(eventname.equals("CCM_ADDITIONS"))
						 {
						  heatConsObj.setSap_matl_id(("NA" == id[5].toString() || (null == id[5].toString())) ? "" : id[5].toString());
						  heatConsObj.setValuation_type(("NA" == id[6].toString() || (null == id[6].toString())) ? "" : id[6].toString());
						 }
						}

					mod_obj.put(key, heatConsObj);
					cnt=cnt+1;
					heatConsObj=null;
					
			 }// end out for
			
			mod_obj.put("HEATCONSMTRLCNT", cnt);
			
		}// end if grid array
		else{
			mod_obj.put("HEATCONSMTRLCNT", 0);
		}//end else grid array
			
			result = ccmAdditionMtrlsSaveOrUpdate(mod_obj);
		
		}// end if
		
		
		
		return result;
	}

	
	
	
	public String heatConsScrapMtrlsSaveOrUpdate(
			HeatConsScrapMtrlDetails heatConsScrapMtrls, String cons_date,
			Integer sub_unit, String eventname, Integer userid) {
		// TODO Auto-generated method stub
		String result="";

		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		
		if(heatConsScrapMtrls.getTrns_eof_si_no() >0 && heatConsScrapMtrls.getTrns_eof_si_no() !=null){
		//insert operation
	/*	Integer eventId = 0;
		EofHeatDetails eofHeatDtls = new EofHeatDetails();
		eofHeatDtls = getEofHeatDtlsById(heatConsScrapMtrls.getTrns_eof_si_no());
		
		if(sub_unit != null && sub_unit>0){
		 eventId =commonService.getLookupIdByQuery("select event_si_no from EventMasterModel where sub_unit_id = "+sub_unit+" and event_desc = '"+eventname+"'and recordStatus=1");
		}

		HeatProcessEventDetails processEvnt = new HeatProcessEventDetails();
		
		processEvnt.setHeat_proc_event_id(null);
		processEvnt.setHeat_id(eofHeatDtls.getHeat_id());
		processEvnt.setHeat_counter(eofHeatDtls.getHeat_counter());
		processEvnt.setEvent_id(eventId);
		processEvnt.setEvent_date_time(new Date());
		processEvnt.setCreatedBy(userid);
		processEvnt.setCreatedDateTime(new Date());
		
		mod_obj.put("HEATPROEVNT", processEvnt);*/
		mod_obj.put("HEATCONSMTRLCNT", 0);// added for scrap iteration 
		   
		    heatConsScrapMtrls.setCons_si_no(null);
		    heatConsScrapMtrls.setHm_seq_no(heatConsScrapMtrls.getHm_seq_no());
			heatConsScrapMtrls.setQty(heatConsScrapMtrls.getQty());
			heatConsScrapMtrls.setConsumption_date(GenericClass.getDateObject("dd/MM/yyyy HH:mm", cons_date));
			heatConsScrapMtrls.setRecord_status(1);
			heatConsScrapMtrls.setCreatedBy(userid);
			heatConsScrapMtrls.setCreatedDateTime(new Date());
			heatConsScrapMtrls.setVersion(0);
			
			mod_obj.put("HEATSCRAP", heatConsScrapMtrls);
		
		}// end if
		
		
		Integer empty_statusId =commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = '"+Constants.SCRAP_BKT_STATUS+"' and lookup_code='"+Constants.SCRAP_BKT_EMPTY+"'");
		ScrapBucketHdr  scrapBkthdr = new ScrapBucketHdr();
		scrapBkthdr = scrapEntryService.getLoadedScrapBktsByHeaderId(heatConsScrapMtrls.getScrap_bkt_header_id());
		
		//scrap master update with bkt load status
		ScrapBucketStatusModel scrapBucketStatusModel = scrapBucketStatusMasterService.getScrapBucketMasterByBktId(scrapBkthdr.getScrap_bkt_id());
		scrapBucketStatusModel.setScrap_bucket_status(empty_statusId);
		scrapBucketStatusModel.setUpdated_by(userid);
		scrapBucketStatusModel.setUpdated_date_time(new Date());
		
		mod_obj.put("SCRAPBKTSTATUS", scrapBucketStatusModel);
		
		Integer load_statusId =commonService.getLookupIdByQuery("select lookup_id from LookupMasterModel where lookup_type = '"+Constants.SCRAP_BKT_STATUS+"' and lookup_code='"+Constants.SCRAP_BKT_CONSUMED+"'");
		
		//scrap hdr update with bkt load status
		scrapBkthdr.setScrap_bkt_load_status(load_statusId);
		scrapBkthdr.setUpdatedBy(userid);
		scrapBkthdr.setUpdatedDateTime(new Date());
		
		mod_obj.put("SCRAPHDRSTATUS", scrapBkthdr);
		
		result = furnaceAdditionMtrlsSaveOrUpdate(mod_obj);
		
		return result;
	}

	public String heatChemistryAllDtlsSaveOrUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.heatChemistryAllDtlsSaveOrUpdate(mod_obj);
	}
	
	public String chemistryDtlsSaveOrUpdate(
			HeatChemistryHdrDetails heatChemHdrDtls, String eventname,
			String sample_date,Integer userid) {
		// TODO Auto-generated method stub
		String result="";
		
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		
		List<LookupMasterModel> chemresLi = lookupmstrDao.getLookupDtlsByLkpTypeAndStatus("CHEM_TEST_RESULT", 1);
		LookupMasterModel model = chemresLi.stream().filter(p -> p.getLookup_code().equals("OK"))
				.collect(Collectors.toList()).get(0);
		
	if(heatChemHdrDtls.getHeat_id() !="" && heatChemHdrDtls.getHeat_id() !=null){
		String validateChem = "P";		
		HeatChemistryHdrDetails heatChemHdrObj = null;
		
		//LookupMasterModel lkp = lookupService.getLookUpRowById(heatChemHdrDtls.getAnalysis_type());
		String chem_type = heatChemHdrDtls.getAnalysisType();
	
		if(heatChemHdrDtls.getSample_si_no()<1)
		{
			//insert operation
			heatChemHdrObj = new HeatChemistryHdrDetails();
			
			heatChemHdrObj.setSample_si_no(null);
			heatChemHdrObj.setSample_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", sample_date));
			
			heatChemHdrObj.setCreatedBy(userid);
			heatChemHdrObj.setCreatedDateTime(new Date());
			heatChemHdrObj.setVersion(0);
	
			if( chem_type.equalsIgnoreCase(Constants.LRF_CHEM) || chem_type.equalsIgnoreCase(Constants.LRF_LIFT_CHEM)){
				List<LRFHeatArcingDetailsModel> arcLi = lrfProdDao.getArcDetailsByHeatId(heatChemHdrDtls.getHeat_id(), heatChemHdrDtls.getHeat_counter());
				
				if(arcLi.size()> 0){
				LRFHeatArcingDetailsModel arcObj = arcLi.get(0);
				if (arcObj.getSample_no() == null){
					arcObj.setUpdated_by(userid);
					arcObj.setUpdated_date_time(new Date());
					
					mod_obj.put("LRF_ARC_ADD", arcObj);
				}
				mod_obj.put("LRF_ARC_ADD_CONS_CNT", 0);
				}
			}else if(chem_type.equalsIgnoreCase(Constants.LRF_AVD_CHEM)){
				List<LRFHeatArcingDetailsModel> arcLi = lrfProdDao.getArcDetailsByHeatId(heatChemHdrDtls.getHeat_id(), heatChemHdrDtls.getHeat_counter());
				if(arcLi.size()> 0){
				LRFHeatArcingDetailsModel arcObj = arcLi.get(0);
				if ((arcObj.getSample_no() == null) && arcObj.getLookupMdl().getLookup_code().equalsIgnoreCase("AFTER_VD") ){
					arcObj.setUpdated_by(userid);
					arcObj.setUpdated_date_time(new Date());
					mod_obj.put("LRF_ARC_ADD", arcObj);
				}
				mod_obj.put("LRF_ARC_ADD_CONS_CNT", 0);
				}
			}
		}else{
			heatChemHdrObj = new HeatChemistryHdrDetails();
			
			heatChemHdrObj = getHeatChemistryHdrDtlsById(heatChemHdrDtls.getSample_si_no(),heatChemHdrDtls.getSub_unit_id());
			
			heatChemHdrObj.setSample_si_no(heatChemHdrDtls.getSample_si_no());
			heatChemHdrObj.setUpdatedBy(userid);
			heatChemHdrObj.setUpdatedDateTime(new Date());
		}
		
		heatChemHdrObj.setSample_no(heatChemHdrDtls.getSample_no());				
		heatChemHdrObj.setSample_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", sample_date));
		heatChemHdrObj.setSample_temp(heatChemHdrDtls.getSample_temp());
		heatChemHdrObj.setSub_unit_id(heatChemHdrDtls.getSub_unit_id());
		heatChemHdrObj.setHeat_id(heatChemHdrDtls.getHeat_id());
		heatChemHdrObj.setHeat_counter(heatChemHdrDtls.getHeat_counter());
		heatChemHdrObj.setAnalysis_type(heatChemHdrDtls.getAnalysis_type());
		heatChemHdrObj.setSample_result(heatChemHdrDtls.getSample_result());
		heatChemHdrObj.setRemarks(heatChemHdrDtls.getRemarks());
		heatChemHdrObj.setRecord_status(1);
		heatChemHdrObj.setFinal_result(0);
		if (heatChemHdrDtls.getSample_result() == model.getLookup_id()) {
			heatChemHdrObj.setApprove(1);
		}else {
			heatChemHdrObj.setApprove(0);
		}
		
		 if(heatChemHdrDtls.getGrid_arry() != null && heatChemHdrDtls.getGrid_arry().toString() != ""&& !(heatChemHdrDtls.getGrid_arry().isEmpty())){	
		String row[]=heatChemHdrDtls.getGrid_arry().split("SIDS");
		
		Integer cnt=0;String key="HEATCHEMCHILD";
		HeatChemistryChildDetails heatChemChildObj = null;
		
		for(int i=0;i<row.length;i++){
			String id[] = row[i].split("@");
			key=key+i;
			
			heatChemChildObj =new HeatChemistryChildDetails();
			
				if(id[4].equalsIgnoreCase("null")){
					System.out.println(" inisde child insert" );
					heatChemChildObj.setDtls_si_no(null);
					heatChemChildObj.setSample_si_no(null);
					heatChemChildObj.setElement(Integer.parseInt(id[0]));
					heatChemChildObj.setAim_value(Double.parseDouble(id[1].toString()));
					heatChemChildObj.setMin_value((id[2].equalsIgnoreCase("null") || id[2].isEmpty()) ? null : Double.parseDouble(id[2].toString()));
					heatChemChildObj.setMax_value((id[3].equalsIgnoreCase("null") || id[3].isEmpty()) ? null : Double.parseDouble(id[3].toString()));
					
					heatChemChildObj.setCreatedBy(userid);
					heatChemChildObj.setCreatedDateTime(new Date());
					heatChemChildObj.setVersion(0);
				}else{
					//Update operation
					heatChemChildObj = getHeatChemistryChildDtlsById(Integer.parseInt(id[4]));
					
					heatChemChildObj.setElement(Integer.parseInt(id[0]));
					heatChemChildObj.setAim_value(Double.parseDouble(id[1].toString()));
					heatChemChildObj.setMin_value((id[2].equalsIgnoreCase("null") || id[2].isEmpty()) ? null : Double.parseDouble(id[2].toString()));
					heatChemChildObj.setMax_value((id[3].equalsIgnoreCase("null") || id[3].isEmpty()) ? null : Double.parseDouble(id[3].toString()));
					heatChemChildObj.setUpdatedBy(userid);
					heatChemChildObj.setUpdatedDateTime(new Date());
				}
				
				mod_obj.put(key, heatChemChildObj);
				cnt=cnt+1;
				
				if(chem_type.equalsIgnoreCase(Constants.LRF_INITIAL_CHEM) || chem_type.equalsIgnoreCase(Constants.LRF_LIFT_CHEM)){
					if(heatChemChildObj.getAim_value() != null && heatChemChildObj.getAim_value().toString() != "" && heatChemChildObj.getMax_value() != null && heatChemChildObj.getMax_value().toString() != ""){
					if(heatChemChildObj.getAim_value() > heatChemChildObj.getMax_value()){
						validateChem = "F";
					}
					}
				}
				
				heatChemChildObj=null;
		 }// end out for
		
		mod_obj.put("HEATCHEMCHILDCNT", cnt);
		}// end if grid array
		else{
			mod_obj.put("HEATCHEMCHILDCNT", 0);
		}//end else grid array
		
		mod_obj.put("HEATCHEMHDR", heatChemHdrObj);
		result = heatChemistryAllDtlsSaveOrUpdate(mod_obj);
		
		}// END IF
		
		return result;
	}

	@Transactional
	@Override
	public List<HeatProcessEventDetails> getHeatProcessEventDtls(Integer eof_trns_sno,String unit) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatProcessEventDtls(eof_trns_sno,unit) ;//eof
	}

	
	
	@Transactional
	@Override
	public List<HeatProcessEventDetails> getHeatProcessEventDtlsByUnit(
			String heat_id, Integer heat_cnt, Integer sub_unit_id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatProcessEventDtlsByUnit(heat_id, heat_cnt, sub_unit_id);
	}

	
	public HeatProcessEventDetails getHeatProcessEventDtlsById(Integer heat_proc_eventId) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatProcessEventDtlsById(heat_proc_eventId);
	}
	
	
	public String processEventDtlsSaveOrUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.processEventDtlsSaveOrUpdate(mod_obj);
	}
	
	public String heatProcessEventSaveOrUpdate(
			HeatProcessEventDetails heatProcessEventDtls, Integer trns_sno,String unit,
			Integer userid) {
		// TODO Auto-generated method stub
		String result="";
		String heatId="";
		Integer heatCounter=0;
		Integer subUnit=0;
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		if(trns_sno >0 && trns_sno !=null){
			if (unit.equalsIgnoreCase("EAF")){
			EofHeatDetails eofHeatDtls = new EofHeatDetails();
			eofHeatDtls = getEofHeatDtlsById(trns_sno);
			heatId = eofHeatDtls.getHeat_id();
			heatCounter = eofHeatDtls.getHeat_counter();
			subUnit = eofHeatDtls.getSub_unit_id();
			}
			else if (unit.equalsIgnoreCase("VD")){
				VDHeatDetailsModel vdHeatDtls = new VDHeatDetailsModel();
				vdHeatDtls = getVdHeatDtlsById(trns_sno);
				heatId = vdHeatDtls.getHeat_id();
				heatCounter = vdHeatDtls.getHeat_counter();
				subUnit = vdHeatDtls.getSub_unit_id();
				}
			else if (unit.equalsIgnoreCase("LRF")){
				LRFHeatDetailsModel lrfHeatDtls = new LRFHeatDetailsModel();
				
				lrfHeatDtls = getLRFHeatDtlsById(trns_sno);
				heatId = //"AMHT123";//
						lrfHeatDtls.getHeat_id();
				
				heatCounter = //1;//
						lrfHeatDtls.getHeat_counter();
				subUnit = // 3;//
						lrfHeatDtls.getSub_unit_id();
						
				}
			else if(unit.equalsIgnoreCase("CCM")) {
				CCMHeatDetailsModel ccmHeatDtls=new CCMHeatDetailsModel();
				ccmHeatDtls=getCCMHeatDtlsById(trns_sno);
				heatId=ccmHeatDtls.getHeat_no();
				heatCounter=ccmHeatDtls.getHeat_counter();
				subUnit=ccmHeatDtls.getSub_unit_id();
				SteelLadleTrackingModel stLdlTrackObj = stLdlMaintService.getSteelLadleTracking(ccmHeatDtls.getSteel_ladle_no());
				stLdlTrackObj.setLadle_status(commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status='"+ Constants.ST_LADLE_AVAILABLE + "'"));
				stLdlTrackObj.setUpdatedBy(userid);
				stLdlTrackObj.setUpdatedDateTime(new Date());
				mod_obj.put("STLADLE_TRACK", stLdlTrackObj);
			}
			if(heatProcessEventDtls.getGrid_arry() != null && heatProcessEventDtls.getGrid_arry().toString() != ""&& !(heatProcessEventDtls.getGrid_arry().isEmpty())){
			String row[]=heatProcessEventDtls.getGrid_arry().split("SIDS");
			Integer cnt=0;String key="HEATPROCESSEVENT";
			HeatProcessEventDetails eventObj = null;
			for(int i=0;i<row.length;i++){
				String id[] = row[i].split("@");
				key=key+i;
				eventObj =new HeatProcessEventDetails();
				if(id[1].equalsIgnoreCase("null") || id[1] == "null" || id[1] ==""){
					System.out.println("event date is null");
				}
				else{
					if(id[2].equalsIgnoreCase("null")){
						//insert operation
						eventObj.setHeat_proc_event_id(null);
						eventObj.setHeat_id(heatId);
						eventObj.setHeat_counter(heatCounter);
						eventObj.setEvent_id(Integer.parseInt(id[0].toString()));
						eventObj.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[1].toString()));
						eventObj.setCreatedBy(userid);
						eventObj.setCreatedDateTime(new Date());
						eventObj.setVersion(0);
					}else{
						//Update operation
						eventObj.setHeat_id(heatId);
						   eventObj = getHeatProcessEventDtlsById(Integer.parseInt(id[2]));
						  eventObj.setHeat_proc_event_id(Integer.parseInt(id[2]));
						  eventObj.setEvent_id(Integer.parseInt(id[0].toString()));
						  eventObj.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[1].toString()));
						  eventObj.setUpdatedBy(userid);
						  eventObj.setUpdatedDateTime(new Date());
					}
				
					mod_obj.put(key, eventObj);
					cnt=cnt+1;
				}
					eventObj=null;
					
			 }// end out for
			mod_obj.put("HEATPROCESSEVENT_CNT", cnt);		
		}// end if grid array
		else{
			mod_obj.put("HEATPROCESSEVENT_CNT", 0);
		}//end else grid array			
			result = processEventDtlsSaveOrUpdate(mod_obj);
		if(result.equals(Constants.SAVE)){
			if (unit == "EAF"){
			calcBlowStartEndTime( heatId,subUnit,heatCounter,userid);
			}
		}
		}// end if
		
		
		
		return result;
	}
	
	@Override
	public void calcBlowStartEndTime(String heat_id, Integer sub_unit_id,
			Integer counter,Integer uid) {
		// TODO Auto-generated method stub
		
		
		List<HeatProcessEventDetails> eventDtlsList= new ArrayList<HeatProcessEventDetails>();
		eventDtlsList= getHeatProcessEventDtlsByUnit(heat_id, counter, sub_unit_id);
		HeatProcessParameterDetails heatProcessParam= null;
		Integer param_event_id=0;
		Integer blow_start=eventMstrService.getEventMstrDtlsById("O2_BLOW_START", sub_unit_id);
		Integer blow_end= eventMstrService.getEventMstrDtlsById("O2_BLOW_END", sub_unit_id);
		
		Date blow_start_date=null, blow_end_date=null;
		Long diff_date=0l;
		
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		
		for (HeatProcessEventDetails EventDetails : eventDtlsList) {
			if(EventDetails.getEvent_id()==blow_start){
				blow_start_date=EventDetails.getEvent_date_time();
			}
			if(EventDetails.getEvent_id()==blow_end){
				blow_end_date=EventDetails.getEvent_date_time();
			}
			
			
		}
		if(blow_start_date!=null && blow_end_date!=null){
		diff_date=(blow_end_date.getTime()- blow_start_date.getTime())/(1000*60);
		
		Integer procParamId=processParamService.getProcessParamMstrDtlsById("BLOW DURATION", sub_unit_id);
		heatProcessParam= getProcParamDtlsByParmaID(procParamId, heat_id, counter);
		
		if(heatProcessParam!=null){
			param_event_id=heatProcessParam.getProc_param_event_id();
		}else
		{
			heatProcessParam=new HeatProcessParameterDetails();   // Please check the code
		}
		if(diff_date>0){
			if(param_event_id>0){
				heatProcessParam.setParam_value_actual((double)(diff_date));
				heatProcessParam.setUpdated_by(uid);
				heatProcessParam.setUpdated_date_time(new Date());
			
			}else{
				heatProcessParam.setProc_param_event_id(null);
				heatProcessParam.setParam_id(procParamId);
				heatProcessParam.setParam_value_actual((double)(diff_date));
				heatProcessParam.setProcess_date_time(new Date());
				heatProcessParam.setHeat_id(heat_id);
				heatProcessParam.setHeat_counter(counter);
				heatProcessParam.setCreated_by(uid);
				heatProcessParam.setCreated_date_time(new Date());
				heatProcessParam.setVersion(0);
			}
		}
		
		mod_obj.put("HEATPROCESSPARAM_BLOW", heatProcessParam);
		mod_obj.put("HEATPROCPARA_CNT", 0);
		processParamSaveOrUpdate(mod_obj);
		}
	}
	
	public EofDispatchDetails getEofDispatchDetailsById(Integer campaignId) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getEofDispatchDetailsById(campaignId);
	}
	
	
	
	public String eofDispatchDtlsSaveOrUpdate(
			Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.eofDispatchDtlsSaveOrUpdate(mod_obj);
	}
	
	
	public HeatPlanLinesDetails getHeatPlanLineDetailsById(Integer eof_trns_sno) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanLineDetailsById(eof_trns_sno);
	}
	@Override
	public String eofDispatchSave(EOFRequestWrapper reqWrapper, Integer userid) {
		// TODO Auto-generated method stub
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		String result = "";
		Integer ladle_status_id;
		EofDispatchDetails dispatchObj,dispatchObjPrev = null;
		List<EofDispatchDetails> dispList = new ArrayList<EofDispatchDetails>();
		
		EofHeatDetails eofHeatDtls = new EofHeatDetails();
		SteelLadleTrackingModel stTrackObj=new SteelLadleTrackingModel();
		
		//eofHeatDtls = getEofHeatDtlsById(eof_trns_sno);
		EofDispatchDetails dispatch  = reqWrapper.getEofDispatchList().get(0);
		eofHeatDtls = getEofHeatDtlsById(dispatch.getEof_trns_sno());
			HeatPlanDetails heatPlanLineDtls = new HeatPlanDetails();
			try{
				//if(campaign_status.equalsIgnoreCase("YES")){
			if(dispatch.getCampaign_status().equalsIgnoreCase("YES")){
					
				
					//dispatchObj=insertNewCampaign(eofDispatchDetails, eofHeatDtls, userid);
				for (EofDispatchDetails a: reqWrapper.getEofDispatchList()) {
				dispatchObj=insertNewCampaign(a, eofHeatDtls, userid);
				dispatchObj.setLifeParameterName(a.getLifeParameterName());
				dispList.add(dispatchObj);
				dispatchObjPrev = new EofDispatchDetails();							
				dispatchObjPrev = getEofDispPreviousDtls(eofHeatDtls.getSub_unit_id(),dispatchObj.getLifeParameterName());
				if (dispatchObjPrev!= null){
				dispatchObjPrev.setUpdatedBy(userid);
				dispatchObjPrev.setUpdatedDateTime(new Date());
				dispatchObjPrev.setCampaign_life_status(0);
				dispList.add(dispatchObjPrev);
				}
				}
				
				}else{
					
					//if campaign status is NO
					
					for (EofDispatchDetails a: reqWrapper.getEofDispatchList()) {
						if (a.getLifeParameterName().equalsIgnoreCase("FURNACE LIFE")) {
							//update campaign for furnace life
							dispatchObj = new EofDispatchDetails();							
							dispatchObj = getEofDispPreviousDtls(eofHeatDtls.getSub_unit_id(),a.getLifeParameterName());							
							if(dispatchObj!=null)
							{			
							dispatchObj.setTotal_heats(dispatchObj.getTotal_heats()+1);
							dispatchObj.setEnd_heat_id(eofHeatDtls.getHeat_id());
							//dispatchObj.setCampaign_remarks(eofDispatchDetails.getCampaign_remarks());
							dispatchObj.setCampaign_remarks(dispatch.getCampaign_remarks());
							dispatchObj.setUpdatedBy(userid);
							dispatchObj.setUpdatedDateTime(new Date());
							dispatchObj.setCampaign_end_date(eofHeatDtls.getProduction_date());
							}else{
								//insert new Campaign for first time
								dispatchObj=insertNewCampaign(dispatch, eofHeatDtls, userid);
								//dispatchObjPrev = new EofDispatchDetails();							
								//dispatchObjPrev = getEofDispPreviousDtls(eofHeatDtls.getSub_unit_id(),dispatchObj.getLifeParameterName());
								dispatchObj.setUpdatedBy(userid);
								dispatchObj.setUpdatedDateTime(new Date());
								//dispatchObjPrev.setCampaign_life_status(0);
								//dispList.add(dispatchObj);
							}
							dispList.add(dispatchObj);
						}
						else {
							if(a.getCampaign_status().equalsIgnoreCase("YES")){
						dispatchObj=insertNewCampaign(a, eofHeatDtls, userid);
						dispatchObj.setLifeParameterName(a.getLifeParameterName());
						dispatchObjPrev = new EofDispatchDetails();							
						dispatchObjPrev = getEofDispPreviousDtls(eofHeatDtls.getSub_unit_id(),a.getLifeParameterName());
						if(dispatchObj!=null)
						{
						dispatchObjPrev.setUpdatedBy(userid);
						dispatchObjPrev.setUpdatedDateTime(new Date());
						dispatchObjPrev.setCampaign_life_status(0);
						dispList.add(dispatchObjPrev);
						}
							}
							else {
								//update campaign other than furnace life
								dispatchObj = new EofDispatchDetails();							
								dispatchObj = getEofDispPreviousDtls(eofHeatDtls.getSub_unit_id(),a.getLifeParameterName());							
								if(dispatchObj!=null)
								{
								dispatchObj.setTotal_heats(dispatchObj.getTotal_heats()+1);
								dispatchObj.setEnd_heat_id(eofHeatDtls.getHeat_id());
								//dispatchObj.setCampaign_remarks(eofDispatchDetails.getCampaign_remarks());
								dispatchObj.setCampaign_remarks(dispatch.getCampaign_remarks());
								dispatchObj.setUpdatedBy(userid);
								dispatchObj.setUpdatedDateTime(new Date());
								dispatchObj.setCampaign_end_date(eofHeatDtls.getProduction_date());
								}else{
									//insert new Campaign for first time
									dispatchObj=insertNewCampaign(a, eofHeatDtls, userid);
									//dispatchObjPrev = new EofDispatchDetails();							
									//dispatchObjPrev = getEofDispPreviousDtls(eofHeatDtls.getSub_unit_id(),dispatchObj.getLifeParameterName());
									dispatchObj.setUpdatedBy(userid);
									dispatchObj.setUpdatedDateTime(new Date());
									//dispatchObjPrev.setCampaign_life_status(0);
									//dispList.add(dispatchObjPrev);
								}
								
							}
							dispList.add(dispatchObj);
						}
							
					}
					
				}
				
				//Plan Line status update code
				//heatPlanLineDtls = getHeatPlanLineDetailsById(eofHeatDtls.getHeat_plan_line_id());
				heatPlanLineDtls = heatPlanDetailsDao.getHeatPlanDetailsById(eofHeatDtls.getHeat_plan_line_id());
				
				heatPlanLineDtls.setAct_prod_heat_qty(reqWrapper.getEofHeatDetails().getTap_wt());
				heatPlanLineDtls.setUpdatedBy(userid);
				heatPlanLineDtls.setUpdatedDateTime(new Date());
				
				
				mod_obj.put("EOFHEATPLANLINESTATUSUPDATE", heatPlanLineDtls);
							
				stTrackObj=steelLadleService.getStLadlleTrackObjById(reqWrapper.getEofHeatDetails().getSteel_ladle_no());
			
				eofHeatDtls.setSteel_ladle_no(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
				eofHeatDtls.setTap_wt(reqWrapper.getEofHeatDetails().getTap_wt());
				eofHeatDtls.setTap_temp(reqWrapper.getEofHeatDetails().getTap_temp());
				eofHeatDtls.setDispatch_to_unit(reqWrapper.getEofHeatDetails().getDispatch_to_unit());
				eofHeatDtls.setDispatch_date(new Date());
				eofHeatDtls.setUpdatedBy(userid);
				eofHeatDtls.setUpdatedDateTime(new Date());
				//eofHeatDtls.setTap_wt(reqWrapper.getEofHeatDetails().getHm_wt());
				//eofHeatDtls.setLadle_no(reqWrapper.getEofHeatDetails().getSteel_ladle_no());
				mod_obj.put("EOFHEATCAMPAIGNUPDATE", eofHeatDtls);
				
				eofHeatDtls.setSteel_ladle_no(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
				//SteelLadleTrackingModel
				if(stTrackObj!=null){
				ladle_status_id=commonService.getLookupIdByQuery("select steel_ladle_status_id from SteelLadleStatusMasterModel where steel_ladle_status_id="+Constants.LADLE_DISP_FRM_EOF+" and  recordStatus=1");
				stTrackObj.setLadle_status(ladle_status_id);
				stTrackObj.setSt_ladle_life(stTrackObj.getSt_ladle_life()+1);
				stTrackObj.setUpdatedBy(userid);
				stTrackObj.setUpdatedDateTime(new Date());
				}
				
				mod_obj.put("EOFSTEELLADLESTATUSUPDATE", stTrackObj);
				
			   // mod_obj.put("EOFDISPATCH", dispatchObj);
			    mod_obj.put("EOFDISPATCH", dispList);
			    
			    /**Updating Heat Status Begin **/
			    HeatStatusTrackingModel hstmObj= new HeatStatusTrackingModel();
			    hstmObj=commonService.getHeatStatusObject(reqWrapper.getEofHeatDetails().getHeat_id(),reqWrapper.getEofHeatDetails().getHeat_counter());
			    
			    hstmObj.setMain_status("WIP");
			    hstmObj.setAct_proc_path(reqWrapper.getEofHeatDetails().getSub_unit_name());
			    hstmObj.setLadle_id(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
			    if(reqWrapper.getEofHeatDetails().getDispatch_to_unit_name().equalsIgnoreCase("DRY PIT")){
			    	  hstmObj.setCurrent_unit("EAF");
			    	  hstmObj.setUnit_process_status("ABORTED");
			    	  hstmObj.setEof_status("ABORTED");
			    }else{
			    	  hstmObj.setUnit_process_status("WAITING FOR PROCESSING");
					   hstmObj.setEof_status("DISPATCHED");
					   hstmObj.setCurrent_unit("LRF");
					  
			    }
			  
			    hstmObj.setUpdatedBy(userid);
			    hstmObj.setUpdatedDateTime(new Date());
			    mod_obj.put("EOF_HEAT_STATUS", hstmObj);
			    
			    /**Updating Heat Status End **/
			    
			    /** Begin  checking new ladle campaign **/
			    
			    SteelLadleCampaignModel ladleCampObj = null;
			    if(reqWrapper.getLadleLifeDetails().getLadleCampStatus().equalsIgnoreCase("YES")){
					//insert new Campaign
			    	ladleCampObj=new SteelLadleCampaignModel();
			    	ladleCampObj = steelLadleService.getLadlePrevCampaignObj(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
			    	if(ladleCampObj!=null)
					{
						
				    	ladleCampObj.setCampaign_end_date(new Date());
				    	ladleCampObj.setUpdated_by(userid);
				    	ladleCampObj.setUpdated_date_time(new Date());
						mod_obj.put("CLOSE_PREV_LDL_CAMPAIGN",ladleCampObj);
					}else{
						//insert new Campaign for first time
						ladleCampObj=insertNewLadleCampaign(reqWrapper.getLadleLifeDetails(),userid);
						mod_obj.put("INSERT_LADLE_CAMPAIGN", ladleCampObj);
						
					}
				
				}
		
				
			    /** End  checking new ladle campaign **/
			    
			    
			    
			    /** Insertion/Updating of ladle Life Details Begin **/
			    SteelLadleLifeModel lifeObj;
			    Integer cnt1=0;
			    String key="LADLE_LIFE_DETAILS";
			    String row[]=reqWrapper.getLadleLifeDetails().getGrid1_arry().split("SIDS");
			    
			    for(int i=0;i<row.length;i++){
				String id[] = row[i].split("@");
				key=key+i;
				lifeObj =new SteelLadleLifeModel();
				
					if(id[0].equalsIgnoreCase("null")){
						//insert operation
						lifeObj.setLadle_life_sl_no(null);
						lifeObj.setSteel_ladle_no(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
						lifeObj.setEquipment_id(Integer.parseInt(id[2].toString()));
						lifeObj.setPart_id(Integer.parseInt(id[3].toString()));
						//lifeObj.setTrns_life(Integer.parseInt(id[4].toString()));
						lifeObj.setTrns_life((null==id[4])?1:Integer.parseInt(id[4].toString()));
						lifeObj.setRecord_status(1);
						lifeObj.setCreated_by(userid);
						lifeObj.setCreated_date_time(new Date());
						
					}else{
						//Update operation
						lifeObj = steelLadleService.getLadleLifeDetailsById(Integer.parseInt(id[0]));
						lifeObj.setEquipment_id(Integer.parseInt(id[2].toString()));
						lifeObj.setPart_id(Integer.parseInt(id[3].toString()));
						lifeObj.setTrns_life(Integer.parseInt(id[4].toString()));
						lifeObj.setUpdated_by(userid);
						lifeObj.setUpdated_date_time(new Date());
					}
					mod_obj.put(key, lifeObj);
					cnt1=cnt1+1;
					lifeObj=null;
					
			 }// end out for

			 	mod_obj.put("LADLE_LIFE_DET_CNT", cnt1);
			 	
			 	//update Steel Ladle status
			 	
			 	//update TRNS_LADLE_PARTS_MAINT_LOG table
			 	/*List<SteelLadleMaintenanceModel> maintLi = stLdlMaintService.getStLdlMaintAfterPartChange(stTrackObj.getSteelLadleObj().getSteel_ladle_si_no());
			 	for(SteelLadleMaintenanceModel maintObj : maintLi){
			 		maintObj.setFirst_heat(eofHeatDtls.getHeat_id());
			 		maintObj.setStart_date(new Date());
			 		maintObj.setUpdated_by(userid);
			 		maintObj.setUpdated_date_time(new Date());
			 	}
			 	mod_obj.put("STEEL_LADLE_MAINT", maintLi);

		 		StLadleStatusTrackHistoryModel stldlHist = new StLadleStatusTrackHistoryModel();
		 		
			 	if(dispatch.getCampaign_status().equalsIgnoreCase("YES")){
			 		if(stTrackObj!=null){
				 		stldlHist = stLdlMaintService.getStlLadleStsTrackHist(stTrackObj.getSt_ladle_track_id());
				 	}
				 	
				 	if (stldlHist.getStladle_status_hist_id() != null && stldlHist.getStladle_status_hist_id() > 0 && stldlHist.getFirst_heat() == null) {
				 		stldlHist.setFirst_heat(eofHeatDtls.getHeat_id());
					 	stldlHist.setSteel_ladle_life(0);
					 	stldlHist.setUpdated_by(userid);
					 	stldlHist.setUpdated_date_time(new Date());
				 	} else {
					 	stldlHist.setSteel_ladle_si_no(stTrackObj.getSt_ladle_si_no());
					 	stldlHist.setHist_entry_time(new Date());
					 	stldlHist.setTrns_stladle_track_id(stTrackObj.getSt_ladle_track_id());
					 	stldlHist.setFirst_heat(eofHeatDtls.getHeat_id());
					 	stldlHist.setSteel_ladle_life(0);
					 	stldlHist.setCreated_by(userid);
					 	stldlHist.setCreated_date_time(new Date());
				 	}
			 	}			 	
			 	mod_obj.put("STEEL_LADLE_MAINT", stldlHist);*/
			 	
			 	List<StLadlePartsMaintLogModel> ldlPartList = new ArrayList<StLadlePartsMaintLogModel>();			 	
			 	ldlPartList = stLdlMaintService.getStLdlPartsDtls(stTrackObj.getSt_ladle_si_no());
			 	
			 	SteelLadleLifeModel stLdlLifeObj = new SteelLadleLifeModel();
			 				 	
			 	List<StLadleLifeHeatWiseModel> heatWisePartsLifeLi = new ArrayList<StLadleLifeHeatWiseModel>();			 	
			 	if (ldlPartList != null && ldlPartList.size() > 0) {
					for (StLadlePartsMaintLogModel partObj : ldlPartList){
						
						StLadleLifeHeatWiseModel partlifeheatobj = new StLadleLifeHeatWiseModel();
						
						if (stTrackObj != null) {
							try {
								stLdlLifeObj = steelLadleService.getLadleLifeByParts(stTrackObj.getSt_ladle_si_no(), partObj.getPartsMstrModel().getPart_name(), Constants.EQUIPMENT_STEEL_LADLE);
							} catch (Exception e) {
								stLdlLifeObj = null;
							}
						}
						
						partlifeheatobj.setHeat_id(eofHeatDtls.getHeat_id());
						partlifeheatobj.setHeat_counter(eofHeatDtls.getHeat_counter());
						partlifeheatobj.setSteel_ladle_no(stTrackObj.getSt_ladle_si_no());
						partlifeheatobj.setEquipment_id(stLdlLifeObj.getEquipment_id());
						partlifeheatobj.setPart_id(partObj.getPart_id());
						partlifeheatobj.setTrns_life(stLdlLifeObj.getTrns_life());
						partlifeheatobj.setRecord_status(stLdlLifeObj.getRecord_status());
						partlifeheatobj.setCreated_by(stLdlLifeObj.getCreated_by());
						partlifeheatobj.setCreated_date_time(stLdlLifeObj.getCreated_date_time());
						partlifeheatobj.setUpdated_by(stLdlLifeObj.getUpdated_by());
						partlifeheatobj.setUpdated_date_time(stLdlLifeObj.getUpdated_date_time());
						partlifeheatobj.setRecord_version(stLdlLifeObj.getRecord_version());
						
						partlifeheatobj.setPart_chng_ldl_sts(stTrackObj.getLadle_status());
						partlifeheatobj.setPart_change_date(null);
						partlifeheatobj.setLdl_down_date(null);
						partlifeheatobj.setLdl_down_heating_date(null);
						partlifeheatobj.setLdl_circ_reserved_date(null);
						partlifeheatobj.setLdl_heat_change_date(new Date());
						
						heatWisePartsLifeLi.add(partlifeheatobj);
					}
					
					mod_obj.put("STEEL_LADLE_PART_LIFE_HEAT", heatWisePartsLifeLi);
			 	}
			 	
			 	if (stTrackObj != null) {
				 	StLdlLifeAtHeat ldlLifeHeatObj = new StLdlLifeAtHeat();
				 	ldlLifeHeatObj.setHist_entry_time(new Date());
				 	ldlLifeHeatObj.setSteel_ladle_si_no(stTrackObj.getSt_ladle_si_no());
				 	ldlLifeHeatObj.setHeat_id(eofHeatDtls.getHeat_id());
				 	ldlLifeHeatObj.setHeat_counter(eofHeatDtls.getHeat_counter());
				 	ldlLifeHeatObj.setStladle_life(stTrackObj.getSt_ladle_life());
				 	ldlLifeHeatObj.setStladle_status(stTrackObj.getLadle_status());
				 	
				 	mod_obj.put("STEEL_LADLE_LIFE_HEAT", ldlLifeHeatObj);
			 	}
			 	
			 	StLadleMaintStatusModel stLdlStsMaint= stLdlMaintService.getSteelLadleMaintStatus(stTrackObj.getSt_ladle_si_no());
			 	if (stLdlStsMaint != null) {
			 		StLadlePartsMaintLogModel heatPartIdObj = stLdlMaintService.getStlLadleMaintPartsLog(stLdlStsMaint.getStladle_maint_status_id(), null);
			 		
			 		StLdlHeatingDtls ldlHeatingObj = new StLdlHeatingDtls();
				 	ldlHeatingObj.setHist_entry_time(new Date());
				 	ldlHeatingObj.setSteel_ladle_si_no(stTrackObj.getSt_ladle_si_no());
				 	ldlHeatingObj.setHeat_id(eofHeatDtls.getHeat_id());
				 	ldlHeatingObj.setHeat_counter(eofHeatDtls.getHeat_counter());
				 	ldlHeatingObj.setHeating_start_dt(heatPartIdObj.getHeat_start_date());
				 	ldlHeatingObj.setHeating_end_dt(heatPartIdObj.getHeat_end_date());
				 	ldlHeatingObj.setBurner_no(heatPartIdObj.getBurner_no());
				 	ldlHeatingObj.setStladle_status(stTrackObj.getLadle_status());
				 	ldlHeatingObj.setCreated_by(heatPartIdObj.getCreated_by());
				 	ldlHeatingObj.setCreated_date_time(heatPartIdObj.getCreated_date_time());
				 	ldlHeatingObj.setUpdated_by(heatPartIdObj.getUpdated_by());
				 	ldlHeatingObj.setUpdated_date_time(heatPartIdObj.getUpdated_date_time());
				 	
				 	mod_obj.put("STEEL_LADLE_HEATING_INFO", ldlHeatingObj);
			 	}
			 	
			 	//update TRNS_LADLE_PARTS_MAINT_LOG table END
			}catch(Exception e){
				e.printStackTrace();	
			}
			result = eofDispatchDtlsSaveOrUpdate(mod_obj);
		
		return result;
	}
	
public SteelLadleCampaignModel insertNewLadleCampaign(SteelLadleLifeModel lifeObj,Integer userId){
	Integer campaignSeqId=1;

	
	SteelLadleCampaignModel stObj = new SteelLadleCampaignModel();
	
		
	campaignSeqId = commonService.getLookupIdByQuery("select max(a.campaign_no) from SteelLadleCampaignModel a where a.steel_ladle_no="+lifeObj.getSteel_ladle_no());
	
	if((campaignSeqId==0))
	{
		campaignSeqId=1;
	}else{
		campaignSeqId=campaignSeqId+1;
	}
	stObj.setLc_sl_no(null);
	stObj.setSteel_ladle_no(lifeObj.getSteel_ladle_no());
	stObj.setCampaign_no(campaignSeqId);
	stObj.setCampaign_start_date(new Date());
	stObj.setCampaign_remarks(lifeObj.getLadleCampRemarks());
	stObj.setCreated_by(userId);
	stObj.setCreated_date_time(new Date());
	stObj.setRecord_status(1);
	return stObj;
}
public EofDispatchDetails insertNewCampaign(EofDispatchDetails eofDispatchDetails,EofHeatDetails eofHeatDtls,Integer userid)
{
	Integer campaignSeqId=1;
	Integer lifeParameterId;
	
	int totHeat;
	
	EofDispatchDetails dispatchObj = new EofDispatchDetails();
	totHeat =1;
	
	lifeParameterId = commonService.getLookupIdByQuery("select l.lookup_id from LookupMasterModel l "
			+ " where l.lookup_type='LIFE_PARAMETER' and  l.lookup_code ='" +eofDispatchDetails.getLifeParameterName()+ "'");
		
	campaignSeqId = commonService.getLookupIdByQuery("select d.campaign_number from EofDispatchDetails d "
			+ " where d.sub_unit_id="+eofHeatDtls.getSub_unit_id()+" and d.campaign_life_status = 1  and d.lifeParameter = "+ lifeParameterId 
			+ "");
	
	if((campaignSeqId==0))
	{
		campaignSeqId=1;
	}else{
		campaignSeqId=campaignSeqId+1;
	}
	dispatchObj.setCampaign_id(null);
	dispatchObj.setCampaign_number(campaignSeqId);
	dispatchObj.setTotal_heats(totHeat);		
	dispatchObj.setStart_heat_id(eofHeatDtls.getHeat_id());		
	dispatchObj.setCampaign_start_date(eofHeatDtls.getProduction_date());
	dispatchObj.setCampaign_end_date(new Date());
	dispatchObj.setEnd_heat_id(eofHeatDtls.getHeat_id());
	dispatchObj.setCampaign_remarks(eofDispatchDetails.getCampaign_remarks());
	dispatchObj.setCreatedBy(userid);
	dispatchObj.setCreatedDateTime(new Date());
	dispatchObj.setVersion(0);
	dispatchObj.setCampaign_life_status(1);
	dispatchObj.setSub_unit_id(eofHeatDtls.getSub_unit_id());
	dispatchObj.setLifeParameter(lifeParameterId);
	
	return dispatchObj;
}

	@Override
	public EofDispatchDetails getEofDispPreviousDtls(Integer subunitid, String lifeParameter ) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getEofDispPreviousDtls(subunitid,lifeParameter);
	}


	@Override
	@Transactional
	public List<HeatProcessParameterDetails> getHeatProcParamDtls(
			String heatid, Integer heatcntr, Integer subunitid,String psn_no) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatProcParamDtls( heatid,heatcntr,subunitid,psn_no);
	}

	
	public String processParamSaveOrUpdate(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.processParamSaveOrUpdate(mod_obj);
	}

	
	public String processParamSaveOrUpdate(HeatProcessParameterDetails procParamDtls,Integer uid) {
		// TODO Auto-generated method stub
		String result="";
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		
		if(procParamDtls.getGrid_arry() != null && procParamDtls.getGrid_arry().toString() != ""&& !(procParamDtls.getGrid_arry().isEmpty())){	
			
			String row[]=procParamDtls.getGrid_arry().split("SIDS");
					
			Integer cnt=0;String key="HEATPROCPARA";
			HeatProcessParameterDetails heatProcParamObj = null;
			for(int i=0;i<row.length;i++){
				
				String id[] = row[i].split("@");
				key=key+i;
				heatProcParamObj =new HeatProcessParameterDetails();
					if(id[3].equalsIgnoreCase("null")){
						//insert operation
						heatProcParamObj.setProc_param_event_id(null);
						heatProcParamObj.setParam_id(Integer.parseInt(id[0].toString()));
						heatProcParamObj.setParam_value_actual(Double.parseDouble(id[1].toString()));
				
						heatProcParamObj.setProcess_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						heatProcParamObj.setHeat_id(procParamDtls.getHeat_id());
						heatProcParamObj.setHeat_counter(procParamDtls.getHeat_counter());
						heatProcParamObj.setCreated_by(uid);
						heatProcParamObj.setCreated_date_time(new Date());
						heatProcParamObj.setVersion(0);
						
						
					}else{
						//Update operation
						 						
						heatProcParamObj = getProcParamDtlsById(Integer.parseInt(id[3]));
						
						heatProcParamObj.setProc_param_event_id(Integer.parseInt(id[3]));
						heatProcParamObj.setParam_id(Integer.parseInt(id[0].toString()));
						//heatProcParamObj.setParam_value_min(Double.parseDouble(id[1].toString()));
						//heatProcParamObj.setParam_value_max(Double.parseDouble(id[2].toString()));
						heatProcParamObj.setParam_value_actual(Double.parseDouble(id[1].toString()));
						heatProcParamObj.setProcess_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", id[2].toString()));
						
						heatProcParamObj.setUpdated_by(uid);
						heatProcParamObj.setUpdated_date_time(new Date());
						
					}

					mod_obj.put(key, heatProcParamObj);
					cnt=cnt+1;
					heatProcParamObj=null;
						
				}
			mod_obj.put("HEATPROCPARA_CNT", cnt);
			}// end if grid array
			else{
				mod_obj.put("HEATPROCPARA_CNT", 0);
			}//end else grid array
		result= processParamSaveOrUpdate(mod_obj);
		
		
		return result;
	}



	@Override
	public HeatProcessParameterDetails getProcParamDtlsById(
			Integer proc_param_id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getProcParamDtlsById(proc_param_id);
	}
	@Transactional
	@Override
	public List<EofDispatchDetails> getEOFCampaignLife(Integer subUnit) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getEOFCampaignLife(subUnit);
	}


	@Override
	public String heatProcessEventSaveOrUpdate(
			HeatProcessEventDetails heatProcessEventDtls, Integer userid) {
		// TODO Auto-generated method stub
		String result="";
		
		Hashtable<String, Object> mod_obj = new Hashtable<String, Object>();
		if(heatProcessEventDtls.getHeat_id()!=null && heatProcessEventDtls.getHeat_id()!="" && heatProcessEventDtls.getHeat_proc_event_id()==0){
			heatProcessEventDtls.setHeat_proc_event_id(null);
			heatProcessEventDtls.setHeat_id(heatProcessEventDtls.getHeat_id());
			heatProcessEventDtls.setHeat_counter(heatProcessEventDtls.getHeat_counter());
			heatProcessEventDtls.setEvent_id(heatProcessEventDtls.getEvent_id());
			heatProcessEventDtls.setEvent_date_time(GenericClass.getDateObject("dd/MM/yyyy HH:mm", heatProcessEventDtls.getEvent_date()));
			heatProcessEventDtls.setCreatedBy(userid);
			heatProcessEventDtls.setCreatedDateTime(new Date());
			heatProcessEventDtls.setVersion(0);
		}
		
					mod_obj.put("HEATPROCESS_LRF_EVENT", heatProcessEventDtls);
					
					mod_obj.put("HEATPROCESSEVENT_CNT", 0);
					result = processEventDtlsSaveOrUpdate(mod_obj);	
		
		return result;
	}

	@Override
	public Double getTotalFurnaceWeight(String heat_id, Integer sub_unit_id,
			Integer counter) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getTotalFurnaceWeight(heat_id, sub_unit_id, counter);
	}

	@Override
	public HeatProcessParameterDetails getProcParamDtlsByParmaID(
			Integer param_id, String heat_id, Integer heat_cnt) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getProcParamDtlsByParmaID(param_id, heat_id, heat_cnt);
	}

	@Override
	public List<HeatChemistryChildDetails> getHeatChemistryDtls(String heat_id,
			Integer heat_counter, Integer sub_unit_id, Integer hm_analysis_id,
			Integer result_id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatChemistryDtls(heat_id,heat_counter,sub_unit_id,hm_analysis_id,result_id);
	}

	
	@Override
	public String getSampleNo(String heat_id, Integer heat_counter, Integer sub_unit_id, Integer analysis_type) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getSampleNo(heat_id, heat_counter, sub_unit_id, analysis_type);
	}
	@Override
	public VDHeatDetailsModel getVdHeatDtlsById(Integer trns_sno) {
		return heatProceeEventDao.getVdHeatDtlsById(trns_sno);
		 
	}
	
	@Override
	public LRFHeatDetailsModel getLRFHeatDtlsById(Integer trns_sno) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getLRFHeatDtlsById(trns_sno);
	}
	
	@Override
	public CCMHeatDetailsModel getCCMHeatDtlsById(Integer trns_sno) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getCCMHeatDtlsById(trns_sno);
	}

	@Override
	public List<HeatChemistryChildDetails> getChemDtlsBySampleHdrId(
			Integer sample_si_id, Integer psn_id, Integer analysis_id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getChemDtlsBySampleHdrId(sample_si_id, psn_id, analysis_id);
	}
	
	@Override
	public String saveFinalResultiface(HeatChemistryHdrDetails chemHdrObj,IfacesmsLpDetailsModel ifacObj) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.saveFinalResultiface(chemHdrObj,ifacObj);
	}
	
	@Override 
	public String approvechem(HeatChemistryHdrDetails chemHdrObj) {
		
		return heatProceeEventDao.approvechem(chemHdrObj);
	}
	
	@Override
	public String saveFinalResult(HeatChemistryHdrDetails chemHdrObj) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.saveFinalResult(chemHdrObj);
	}

	@Override
	public List<HeatChemistryChildDetails> getChemDtlsBySampleHdr(
			Integer sample_si_Id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getChemDtlsBySampleHdr(sample_si_Id);
	}

	@Override
	public List<HeatProcessEventDetails> getLrfHeatProcessEventDtls(
			String heat_id, Integer heat_cnt, Integer sub_unit_id,
			String prev_unit, String psn_route) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getLrfHeatProcessEventDtls(heat_id, heat_cnt, sub_unit_id, prev_unit, psn_route) ;
	}

	@Override
	public List<HeatChemistryHdrDetails> getSampleDtlsByAnalysisType(
			Integer sub_unit_id, String heat_id, Integer heat_counter,
			String analysis_type) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getSampleDtlsByAnalysisType(sub_unit_id, heat_id, heat_counter, analysis_type);
	}

	@Override
	public List<HeatChemistryChildDetails> getChemDtlsByAnalysis(Integer analysis_id, Integer psn_id) {
		
		return heatProceeEventDao.getChemDtlsByAnalysis(analysis_id, psn_id);
	}

	@Override
	public List<HeatChemistryChildDetails> getTundishChem(Integer analysis_id, Integer psn_id, String actual_sample,
			String heat_id,Integer heat_counter) throws IOException {
		// TODO Auto-generated method stub
		Boolean activeConnect=spectroLabUtil.testConnection();
		List<HeatChemistryChildDetails> hcc =new ArrayList<>();
		if(activeConnect==true) {
		hcc = heatProceeEventDao
					.getTundishChem(analysis_id, psn_id,actual_sample,heat_id,heat_counter);
		}
		else {
			 hcc= heatProceeEventDao.getChemDtlsByAnalysis(analysis_id, psn_id);
		}
		return hcc;
	}


	@Override
	public List<HeatConsMaterialsDetails> getHeatConsMtrlsDtlsByTrnsId(Integer trns_id) {
		// TODO Auto-generated method stub
		return heatProceeEventDao.getHeatConsMtrlsDtlsByTrnsId(trns_id) ;
	}


	@Override
	public Integer checkProdConf(String heatno) {
		return heatProceeEventDao.checkProdConf(heatno);
	}

	@Override
	public CCMHeatDetailsModel getCCMHeatDtlsFormById(Integer trns_sno) {
		// TODO Auto-generated method stub
		return null;
	}


}