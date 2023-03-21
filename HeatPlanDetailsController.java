package com.smes.trans.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.smes.admin.service.impl.CommonService;
import com.smes.masters.dao.impl.PsnGradeMasterDaoImpl;
import com.smes.masters.model.SMSCapabilityMasterModel;
import com.smes.masters.service.impl.LookupMasterServiceImpl;
import com.smes.masters.service.impl.SMSCapabilityMasterService;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.SoHeader;
import com.smes.trans.service.impl.HeatPlanDetailsService;
import com.smes.util.Constants;
import com.smes.util.GenericClass;
import com.smes.util.RestResponse;

@Controller
@RequestMapping("HeatPlan")
public class HeatPlanDetailsController {

	private static final Logger logger = Logger
			.getLogger(HeatPlanDetailsController.class);

	@Autowired
	private HeatPlanDetailsService heatPlanDetailsService;

	@Autowired
	private CommonService commonService;

	@Autowired
	private SMSCapabilityMasterService capabMstrService;

	@Autowired
	private LookupMasterServiceImpl lookupmasterservice;

	@Autowired
	private PsnGradeMasterDaoImpl psnGradeMasterDaoImpl;
	
	@RequestMapping("/HeatPlanView")
	public ModelAndView getHeatPlanDetails() {
		logger.info(HeatPlanDetailsController.class + "...getHeatPlanDetails()");		

		ModelAndView model = new ModelAndView("transaction/HeatPlanDetails");
		return model;
	}
	
	@RequestMapping("/PendingHeatLineEdit")
	public ModelAndView getPendingHeatLineEdit() {
		logger.info(HeatPlanDetailsController.class + "...getHeatPlanDetails()");		

		ModelAndView model = new ModelAndView("transaction/EditHeatLinePending");
		return model;
	}

	//@Developed for displaying the heat line per a heat plan.
		@RequestMapping(value = "/getHeatPlanLineByStatus", method = RequestMethod.GET, produces = "application/json")
		public @ResponseBody List<HeatPlanDetails> getHeatPlanLineByStatus(
				@RequestParam("HEAT_PLAN_ID") Integer heat_plan_id,
				HttpSession session)
				
		{
			logger.info(HeatPlanHdrDetails.class+ "...getHeatPlanLinePerHeatByStatus()");
			
			session.setAttribute("heat_plan_id", heat_plan_id);
			return heatPlanDetailsService.getHeatPlanLineByStatus(heat_plan_id);

		}
	
	@RequestMapping(value = "/getHeatPlanHeaderDetailsByStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByStatus(
			@RequestParam("PLAN_STATUS") String planstatus) {
		logger.info(HeatPlanDetailsController.class
				+ "...getHeatPlanHeaderDetailsByStatus()");

		String planstatusiddata[] = planstatus.split(",");
		String ids = "";

		for (Integer i = 0; i < planstatusiddata.length; i++) {
			Integer stageid = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ planstatusiddata[i]
									+ "' and status_type='PLAN_HEADER'");
			ids = stageid + "," + ids;
		}

		ids = ids.substring(0, ids.length() - 1);

		return heatPlanDetailsService.getHeatPlanHeaderDetailsByStatus(ids);

	}

	@RequestMapping(value = "/getHeatPlanLineDetailsByStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanLinesDetails> getHeatPlanLineDetailsByStatus(
			@RequestParam("HEAT_PLAN_ID") Integer heatplanid,
			HttpSession session) {
		logger.info(HeatPlanDetailsController.class
				+ "...getHeatPlanHeaderDetailsByStatus()");
		session.setAttribute("HeatPlanId", null);
		session.setAttribute("HeatPlanId", heatplanid);// USED IN EDIT MODE OF
		// HEAT PLAN
		return heatPlanDetailsService
				.getHeatPlanLineDetailsByStatus(heatplanid);

	}

	@RequestMapping(value = "/getAllPrevHeatPlanDetails", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanHdrDetails> getAllPrevHeatPlanDetails() {
		logger.info(HeatPlanDetailsController.class
				+ "...getAllPrevHeatPlanDetails()");

		return heatPlanDetailsService.getAllPrevHeatPlanDetails();
	}

	@RequestMapping(value = "/getHeatPlanSequence", method = RequestMethod.GET)
	public @ResponseBody Integer getHeatPlanSequence(
			HttpSession session, @RequestParam String prod_start_date, @RequestParam Integer caster) {
		try {
			logger.info(HeatPlanDetailsController.class
					+ " getHeatPlanSequence");
			//Date prodStartDate = GenericClass.getDateObject("dd/MM/yyyy", prod_start_date);
			Integer result = 0;

			//String qry = "select max(plan_sequence) from HeatPlanHdrDetails where to_date(prod_start_date)=to_date('"+prod_start_date+"', 'dd/MM/yyyy')";			
			String qry = "select max(plan_sequence) from HeatPlanHdrDetails where to_date(prod_start_date)='"+prod_start_date+"' and caster_type="+caster;
			result = commonService.getLookupIdByQuery(qry);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside getHeatPlanSequence Exception..", e);
			return 0;
		}
	}

	@RequestMapping(value = "/getSmsCapabilityDtls", method = RequestMethod.GET)
	public @ResponseBody SMSCapabilityMasterModel getSmsCapabilityDtls(
			HttpSession session, @RequestParam("master_id") Integer capab_id) {
		logger.info(HeatPlanDetailsController.class
				+ " getSmsCapabilityDtls");
		SMSCapabilityMasterModel obj = null;

		try {
			obj = capabMstrService.getSmsCapabilityMstrById(capab_id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside getSmsCapabilityDtls Exception..", e);
		}
		return obj;
	}

	@RequestMapping(value = "/LineDelete", method = RequestMethod.GET)
	public @ResponseBody RestResponse heatPlanLinesDelete(HttpSession session,
			@RequestParam("HEAT_LINE_ID") Integer heatlineid,
			@RequestParam("HEAT_PLAN_ID") Integer heatplanid,
			@RequestParam("NO_OF_HEATS_PLANNED") Integer noofheats,
			@RequestParam Double heat_size, @RequestParam Double heat_qty) {
		try {
			logger.info(HeatPlanDetailsController.class + "heatPlanLinesDelete");
			Integer result = 0;

			result = commonService
					.deleteRecordByQuery("delete from HeatPlanLinesDetails where heat_line_id="
							+ heatlineid + "");
			if (result > 0) {
				try {
					String res ="";
					HeatPlanHdrDetails planhdr = heatPlanDetailsService
							.getHeatPlanHeaderDetailsById(heatplanid);
					planhdr.setUpdatedBy(Integer.parseInt(session
							.getAttribute("USER_APP_ID").toString()));
					planhdr.setUpdatedDateTime(new Date());
					planhdr.setNo_of_heats_planned(noofheats);
					planhdr.setPlan_qty(heat_qty);

					Set<HeatPlanDetails> dtlsLi = planhdr.getHeatPlanDtls();
					int count;
					if(planhdr.getNo_of_heats_planned() < dtlsLi.size()){
						count = 0;
						for (HeatPlanDetails dtlObj : dtlsLi){
							count = count + 1;
							if(planhdr.getNo_of_heats_planned() < count){
								dtlObj.setDeleteFlag("Y");
							}else if(planhdr.getNo_of_heats_planned() == count){
								dtlObj.setPlan_heat_qty(heat_qty);
								dtlObj.setUpdatedBy(Integer.parseInt(session
										.getAttribute("USER_APP_ID").toString()));
								dtlObj.setUpdatedDateTime(new Date());
							}else{
								if(dtlObj.getPlan_heat_qty() != heat_size){
									dtlObj.setPlan_heat_qty(heat_size);
									dtlObj.setUpdatedBy(Integer.parseInt(session
											.getAttribute("USER_APP_ID").toString()));
									dtlObj.setUpdatedDateTime(new Date());
								}
							}
							heat_qty = heat_qty - heat_size;
						}
					}

					res = heatPlanDetailsService
							.heatPlanHeaderUpdate(planhdr);

					if (res.equals(Constants.UPDATE)) {
						return new RestResponse("SUCCESS", Constants.UPDATE);
					} else {
						return new RestResponse("FAILURE", Constants.UPDATE_FAIL);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(HeatPlanDetailsController.class
							+ " Inside heatPlanLinesDelete Exception..", e);
					return new RestResponse("FAILURE", e.getMessage());
				}
			} else {
				return new RestResponse("FAILURE", Constants.UPDATE_FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside heatPlanLinesDelete Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/SaveHeatPlanDetails", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse SaveHeatPlanDetails(
			@RequestBody HeatPlanHdrDetails heatPlanHdrDetails,
			@RequestParam String prod_start_date, @RequestParam Double heat_size, HttpSession session) {
		String result = "", result_close = "";
		HeatPlanDetails heatPlanDtlsObj;
		Double heat_qty;
		Set<HeatPlanDetails> heatPlanDtlLi = new HashSet<HeatPlanDetails>();
		try {
			logger.info(HeatPlanDetailsController.class);
			Integer hdr_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_RELEASE
							+ "' and status_type='PLAN_HEADER'");
			//heatPlanHdrDetails.setHeat_plan_id(null);
			heatPlanHdrDetails.setMain_status_id(hdr_status_id);
			heatPlanHdrDetails.setProd_start_date(GenericClass.getDateObject(
					"dd/MM/yyyy", prod_start_date));
			heatPlanHdrDetails.setRecord_status(1);
			heatPlanHdrDetails.setPlan_create_date(new Date());
			heatPlanHdrDetails.setCreatedBy(Integer.parseInt(session
					.getAttribute("USER_APP_ID").toString()));
			heatPlanHdrDetails.setCreatedDateTime(new Date());

			Integer line_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_RELEASE
							+ "' and status_type='PLAN_LINES'");
			for (HeatPlanLinesDetails obj : heatPlanHdrDetails.getHeatPlanLine()){
				obj.setHeatPlanHdrModel(heatPlanHdrDetails);
				obj.setRecord_status(1);
				obj.setLine_status(line_status_id);
				obj.setCreatedDateTime(new Date());
				obj.setCreatedBy(Integer.parseInt(session
						.getAttribute("USER_APP_ID").toString()));			
			}

			Double psnQty; Integer psnHeats;
			Integer psn_id;
			Hashtable<Integer, Double> psn_map = new Hashtable<Integer, Double>();
			boolean insertFlag = false;
			for (HeatPlanLinesDetails obj1 : heatPlanHdrDetails.getHeatPlanLine()){
				psn_id = obj1.getAim_psn();
				psnQty = 0.0; psnHeats = 0;

				if(psn_map == null) {
					insertFlag = true;
				}else if (psn_map != null && psn_map.get(psn_id) == null){
					insertFlag = true;
				}else {
					insertFlag = false;
				}

				if(insertFlag) {
					for (HeatPlanLinesDetails obj2 : heatPlanHdrDetails.getHeatPlanLine()){
						if(psn_id.equals(obj2.getAim_psn())){
							psnQty = psnQty + obj2.getPlan_heat_qty();
						}
					}
					psnHeats = (int) Math.round((psnQty / heat_size));

					for(int j=1; j<=psnHeats; j++){
						heatPlanDtlsObj = new HeatPlanDetails();
						heatPlanDtlsObj.setHeat_plan_dtl_id(null);
						heatPlanDtlsObj.setPlan_heat_id("H"+j);
						heatPlanDtlsObj.setIndent_no((j*10));
						heatPlanDtlsObj.setAim_psn(psn_id);

						if(psnQty >= heat_size){
							if(j != psnHeats)
								heatPlanDtlsObj.setPlan_heat_qty(heat_size);
							else
								heatPlanDtlsObj.setPlan_heat_qty(psnQty);
						}else if(psnQty < heat_size)
							heatPlanDtlsObj.setPlan_heat_qty(psnQty);
						psnQty = psnQty - heat_size;
						heatPlanDtlsObj.setStatus(line_status_id);
						heatPlanDtlsObj.setRecord_status(1);
						heatPlanDtlsObj.setCreatedDateTime(new Date());
						heatPlanDtlsObj.setCreatedBy(Integer.parseInt(session
								.getAttribute("USER_APP_ID").toString()));	
						heatPlanDtlLi.add(heatPlanDtlsObj);
					}
					psn_map.put(psn_id, psnQty);
				}
			}
			heatPlanHdrDetails.setHeatPlanDtls(heatPlanDtlLi);
			/*heat_qty = heatPlanHdrDetails.getPlanned_qty();
			for(int j=1; j<=heatPlanHdrDetails.getNo_of_heats_planned(); j++){
				heatPlanDtlsObj = new HeatPlanDetails();
				heatPlanDtlsObj.setHeat_plan_dtl_id(null);
				heatPlanDtlsObj.setPlan_heat_id("H"+j);
				heatPlanDtlsObj.setIndent_no((j*10));
				if(heat_qty >= heat_size){
					if(j != heatPlanHdrDetails.getNo_of_heats_planned())
						heatPlanDtlsObj.setPlan_heat_qty(heat_size);
					else
						heatPlanDtlsObj.setPlan_heat_qty(heat_qty);
				}else if(heat_qty < heat_size)
					heatPlanDtlsObj.setPlan_heat_qty(heat_qty);
				heat_qty = heat_qty - heat_size;
				heatPlanDtlsObj.setStatus(line_status_id);
				heatPlanDtlsObj.setRecord_status(1);
				heatPlanDtlsObj.setCreatedDateTime(new Date());
				heatPlanDtlsObj.setCreatedBy(Integer.parseInt(session
					.getAttribute("USER_APP_ID").toString()));	
				heatPlanDtlLi.add(heatPlanDtlsObj);
			}
			heatPlanHdrDetails.setHeatPlanDtls(heatPlanDtlLi);
			 */

			result = heatPlanDetailsService
					.heatPlanHeaderSave(heatPlanHdrDetails);


			//Close all old released plans

			//SimpleDateFormat formatter = new SimpleDateFormat("HH");
			//String curTime = formatter.format(new Date());

			/*if(Integer.parseInt(curTime) >= Constants.HEAT_PLAN_PREV_DAY_CLOSE){
				//if(heatPlanHdrDetails.getPlan_sequence().equals(1)){
				List<HeatPlanHdrDetails> prevPlanLi = heatPlanDetailsService.getAllPrevHeatPlanDetails();
				//Set<HeatPlanLinesDetails> updLineLi;
				Set<HeatPlanDetails> updLineLi;
				//List<HeatPlanHdrDetails> updPrevPlanLi = new ArrayList<HeatPlanHdrDetails>();
				
				Integer pln_hdr_status_id = commonService.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
						+ Constants.MAINHEAT_STATUS_CLOSE+ "' and status_type='PLAN_HEADER'");
				Integer pln_line_status_id = commonService.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
						+ Constants.MAINHEAT_STATUS_CLOSE+ "' and status_type='PLAN_LINES'");
				for (HeatPlanHdrDetails hdrObj : prevPlanLi){
					if(hdrObj.getMainHeatStatusMasterModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)){
						hdrObj.setMain_status_id(pln_hdr_status_id);
					}
					updLineLi = new HashSet<HeatPlanLinesDetails>();
					for(HeatPlanLinesDetails lineObj : hdrObj.getHeatPlanLine()){
						if(lineObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)){
							lineObj.setLine_status(pln_line_status_id);
							updLineLi.add(lineObj);
						}
					}
					hdrObj.setHeatPlanLine(updLineLi);
					updLineLi = new HashSet<HeatPlanDetails>();
					for(HeatPlanDetails lineObj : hdrObj.getHeatPlanDtls()){
						if(lineObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)){
							lineObj.setStatus(pln_line_status_id);
							updLineLi.add(lineObj);
						}
					}
					hdrObj.setHeatPlanDtls(updLineLi);
					updPrevPlanLi.add(hdrObj);
				}
				for(HeatPlanHdrDetails updObj : updPrevPlanLi){
					result_close = heatPlanDetailsService.heatPlanHeaderUpdate(updObj);
				}
			}*/
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside SaveHeatPlanDetails Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}

	@RequestMapping(value = "/UpdateHeatPlanDetails", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse UpdateHeatPlanDetails(
			@RequestBody HeatPlanHdrDetails heatPlanHdr, HttpSession session,@RequestParam Integer no_of_heats_planned, @RequestParam Double planned_qty,
			@RequestParam Double heat_size) {
		try {
			logger.info(HeatPlanDetailsController.class);
			String result = "";
			Double heat_qty=0.0D;
			HeatPlanDetails heatPlanDtlsObj;
			HeatPlanHdrDetails heatPlanHdrDetails = heatPlanDetailsService.getHeatPlanHeaderDetailsById(heatPlanHdr.getHeat_plan_id());
			heatPlanHdrDetails.setHeatPlanLine(heatPlanHdr.getHeatPlanLine());
			heatPlanHdrDetails.setNo_of_heats_planned(no_of_heats_planned);
			heatPlanHdrDetails.setPlanned_qty(planned_qty);
			heatPlanHdrDetails.setUpdatedBy(Integer.parseInt(session.getAttribute(
					"USER_APP_ID").toString()));
			heatPlanHdrDetails.setUpdatedDateTime(new Date());
			Integer line_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_RELEASE
							+ "' and status_type='PLAN_LINES'");
			for (HeatPlanLinesDetails obj : heatPlanHdrDetails.getHeatPlanLine()){
				obj.setHeat_plan_id(heatPlanHdrDetails.getHeat_plan_id());
				obj.setHeatPlanHdrModel(heatPlanHdrDetails);
				if(obj.getHeat_line_id() == null){
					obj.setRecord_status(1);
					obj.setLine_status(line_status_id);
					obj.setCreatedDateTime(new Date());
					obj.setCreatedBy(Integer.parseInt(session
							.getAttribute("USER_APP_ID").toString()));
				}else{
					obj.setUpdatedBy(Integer.parseInt(session
							.getAttribute("USER_APP_ID").toString()));
					obj.setUpdatedDateTime(new Date());
				}
			}
			Hashtable<Integer, Double> psn_map = new Hashtable<Integer, Double>();
			boolean insertFlag = false;
			Double psnQty; Integer psnHeats;
			Integer psn_id;
			List<HeatPlanDetails> dtlsLi;
			Set<HeatPlanDetails> planDtlLi = heatPlanHdrDetails.getHeatPlanDtls();
			for (HeatPlanLinesDetails obj1 : heatPlanHdrDetails.getHeatPlanLine()){
				psn_id = obj1.getAim_psn();
				psnQty = 0.0; psnHeats = 0; 
				dtlsLi = new ArrayList<HeatPlanDetails>();
				if(psn_map == null) {
					insertFlag = true;
				}else if (psn_map != null && psn_map.get(psn_id) == null){
					insertFlag = true;
				}else {
					insertFlag = false;
				}

				if(insertFlag) {
					for (HeatPlanLinesDetails obj2 : heatPlanHdrDetails.getHeatPlanLine()){
						if(psn_id.equals(obj2.getAim_psn())){
							psnQty = psnQty + obj2.getPlan_heat_qty();
						}
					}
					psnHeats = (int) Math.round((psnQty / heat_size));
					for (HeatPlanDetails obj3 : heatPlanHdrDetails.getHeatPlanDtls()){
						if(psn_id.equals(obj3.getAim_psn())){
							dtlsLi.add(obj3);
						}
					}
					psn_map.put(psn_id, psnQty);
					int count = 0;
					if(psnHeats == dtlsLi.size()){
						count = 0;
						HeatPlanDetails updObj = null;
						for (HeatPlanDetails dtlObj : dtlsLi){
							count = count + 1;
							if((dtlObj.getPlan_heat_qty()).compareTo(heat_size)  == 0){
								if(dtlsLi.size() == count)
									updObj = dtlObj;
								else
									psnQty = psnQty - heat_size;
							}else{
								updObj = dtlObj;
							}

						}
						updObj.setPlan_heat_qty(psnQty);
						updObj.setUpdatedBy(Integer.parseInt(session
								.getAttribute("USER_APP_ID").toString()));
						updObj.setUpdatedDateTime(new Date());
					}else if(psnHeats > dtlsLi.size()){
						for (HeatPlanDetails dtlObj : dtlsLi){
							if((dtlObj.getPlan_heat_qty() != heat_size) && (psnQty > heat_size)){
								dtlObj.setPlan_heat_qty(heat_size);
								dtlObj.setUpdatedBy(Integer.parseInt(session
										.getAttribute("USER_APP_ID").toString()));
								dtlObj.setUpdatedDateTime(new Date());
							}
							psnQty = psnQty - heat_size;
						}
						for(int j=(dtlsLi.size()+1); j<=psnHeats; j++){
							heatPlanDtlsObj = new HeatPlanDetails();
							heatPlanDtlsObj.setPlan_heat_id("H"+j);
							heatPlanDtlsObj.setIndent_no((j*10));
							heatPlanDtlsObj.setAim_psn(psn_id);
							if(psnQty >= heat_size){
								if(j != psnHeats)
									heatPlanDtlsObj.setPlan_heat_qty(heat_size);
								else
									heatPlanDtlsObj.setPlan_heat_qty(psnQty);
							}else if(psnQty < heat_size) 
								heatPlanDtlsObj.setPlan_heat_qty(psnQty);
							psnQty = psnQty - heat_size;
							heatPlanDtlsObj.setStatus(line_status_id);
							heatPlanDtlsObj.setRecord_status(1);
							heatPlanDtlsObj.setCreatedDateTime(new Date());
							heatPlanDtlsObj.setCreatedBy(Integer.parseInt(session
									.getAttribute("USER_APP_ID").toString()));
							planDtlLi.add(heatPlanDtlsObj);
							dtlsLi.add(heatPlanDtlsObj);
						}
					}else if(psnHeats < dtlsLi.size()){
						count = 0;
						for (HeatPlanDetails dtlObj : dtlsLi){
							count = count + 1;
							if(psnHeats < count){
								dtlObj.setDeleteFlag("Y");
							}else if(psnHeats == count){
								dtlObj.setPlan_heat_qty(psnQty);
								dtlObj.setUpdatedBy(Integer.parseInt(session
										.getAttribute("USER_APP_ID").toString()));
								dtlObj.setUpdatedDateTime(new Date());
							}else{
								if(dtlObj.getPlan_heat_qty() != heat_size){
									dtlObj.setPlan_heat_qty(heat_size);
									dtlObj.setUpdatedBy(Integer.parseInt(session
											.getAttribute("USER_APP_ID").toString()));
									dtlObj.setUpdatedDateTime(new Date());
								}
							}
							psnQty = psnQty - heat_size;
						}
					}//end if;
				}
			}// end for loop

			/*
			Set<HeatPlanDetails> dtlsLi = heatPlanHdrDetails.getHeatPlanDtls();
			heat_qty = heatPlanHdrDetails.getPlanned_qty();
			int count = 0;
			if(heatPlanHdrDetails.getNo_of_heats_planned() == dtlsLi.size()){
				count = 0;
				HeatPlanDetails updObj = null;
				for (HeatPlanDetails dtlObj : dtlsLi){
					count = count + 1;
					if((dtlObj.getPlan_heat_qty()).compareTo(heat_size)  == 0){
						if(dtlsLi.size() == count)
							updObj = dtlObj;
						else
							heat_qty = heat_qty - heat_size;
					}else{
						updObj = dtlObj;
					}

				}
				updObj.setPlan_heat_qty(heat_qty);
				updObj.setUpdatedBy(Integer.parseInt(session
						.getAttribute("USER_APP_ID").toString()));
				updObj.setUpdatedDateTime(new Date());
			}else if(heatPlanHdrDetails.getNo_of_heats_planned() > dtlsLi.size()){
				for (HeatPlanDetails dtlObj : dtlsLi){
					if((dtlObj.getPlan_heat_qty() != heat_size) && (heat_qty > heat_size)){
						dtlObj.setPlan_heat_qty(heat_size);
						dtlObj.setUpdatedBy(Integer.parseInt(session
								.getAttribute("USER_APP_ID").toString()));
						dtlObj.setUpdatedDateTime(new Date());
					}
					heat_qty = heat_qty - heat_size;
				}
				for(int j=(dtlsLi.size()+1); j<=heatPlanHdrDetails.getNo_of_heats_planned(); j++){
					heatPlanDtlsObj = new HeatPlanDetails();
					heatPlanDtlsObj.setPlan_heat_id("H"+j);
					heatPlanDtlsObj.setIndent_no((j*10));
					if(heat_qty >= heat_size){
						if(j != heatPlanHdrDetails.getNo_of_heats_planned())
							heatPlanDtlsObj.setPlan_heat_qty(heat_size);
						else
							heatPlanDtlsObj.setPlan_heat_qty(heat_qty);
					}else if(heat_qty < heat_size) 
						heatPlanDtlsObj.setPlan_heat_qty(heat_qty);
					heat_qty = heat_qty - heat_size;
					heatPlanDtlsObj.setStatus(line_status_id);
					heatPlanDtlsObj.setRecord_status(1);
					heatPlanDtlsObj.setCreatedDateTime(new Date());
					heatPlanDtlsObj.setCreatedBy(Integer.parseInt(session
						.getAttribute("USER_APP_ID").toString()));	
					dtlsLi.add(heatPlanDtlsObj);
				}
			}else if(heatPlanHdrDetails.getNo_of_heats_planned() < dtlsLi.size()){
				count = 0;
				for (HeatPlanDetails dtlObj : dtlsLi){
					count = count + 1;
					if(heatPlanHdrDetails.getNo_of_heats_planned() < count){
						dtlObj.setDeleteFlag("Y");
					}else if(heatPlanHdrDetails.getNo_of_heats_planned() == count){
						dtlObj.setPlan_heat_qty(heat_qty);
						dtlObj.setUpdatedBy(Integer.parseInt(session
								.getAttribute("USER_APP_ID").toString()));
						dtlObj.setUpdatedDateTime(new Date());
					}else{
						if(dtlObj.getPlan_heat_qty() != heat_size){
							dtlObj.setPlan_heat_qty(heat_size);
							dtlObj.setUpdatedBy(Integer.parseInt(session
									.getAttribute("USER_APP_ID").toString()));
							dtlObj.setUpdatedDateTime(new Date());
						}
					}
					heat_qty = heat_qty - heat_size;
				}
			}
			 */
			heatPlanHdrDetails.setHeatPlanDtls(planDtlLi);

			result = heatPlanDetailsService.heatPlanHeaderUpdate(heatPlanHdrDetails);

			if (result.equals(Constants.UPDATE)) {
				return new RestResponse("SUCCESS", Constants.UPDATE);

			} else {
				return new RestResponse("FAILURE", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside UpdateHeatPlanDetails Exception..", e);
			return new RestResponse("FAILURE", e.getMessage());
		}
	}

	@RequestMapping(value = "/exportDailyHeatPlan", method = RequestMethod.GET)
	public ModelAndView exportDailyHeatPlan(HttpServletRequest req, Model md) {
		logger.info("in exportDailyHeatPlan method");
		List<HeatPlanHdrDetails> dayHeatPlan=new ArrayList<HeatPlanHdrDetails>();

		dayHeatPlan=heatPlanDetailsService.getDaywiseHeatPlanDetails();

		ModelAndView model = new ModelAndView("reports/DaywiseHeatPlanExportExcel","dayHeatPlan",dayHeatPlan);

		return model;
	}

	@RequestMapping(value = "/displayDailyHeatPlan", method = RequestMethod.GET)
	public ModelAndView displayDailyHeatPlanReport(HttpServletRequest req, @RequestParam Integer caster_type) {
		logger.info("in displayDailyHeatPlanReport method");
		List<HeatPlanHdrDetails> dayHeatPlan=new ArrayList<HeatPlanHdrDetails>();

		dayHeatPlan=heatPlanDetailsService.getCasterwiseHeatPlanDetails(caster_type);

		ModelAndView model = new ModelAndView("reports/DaywiseHeatPlanExportExcel","dayHeatPlan",dayHeatPlan);

		return model;
	}

	@RequestMapping(value = "/displayHeatPlanDetailRpt", method = RequestMethod.GET)
	public ModelAndView displayHeatPlanDetailReport(HttpServletRequest req, @RequestParam Integer caster_type, @RequestParam String report_date) {
		logger.info("in displayHeatPlanDetailReport method");
		HashMap<String,Object> dailyRpt=new HashMap<>();
		List<HeatPlanHdrDetails> heatPlanDet=new ArrayList<HeatPlanHdrDetails>();

		heatPlanDet=heatPlanDetailsService.displayHeatPlanDetailReport(caster_type, report_date);
		dailyRpt.put("dailyHeatPlanDtl", heatPlanDet);
		List<String> heat=heatPlanDetailsService.getlastEOFHeatNo(report_date);
		if(heat!=null && heat.size()!=0 ){
			dailyRpt.put("lstEOF1",heat.get(0));
			dailyRpt.put("lstEOF2",heat.get(1));
			dailyRpt.put("PLANT_LOCATION", lookupmasterservice.getLookupDtlsByLkpTypeAndStatus(Constants.LKP_TYPE_PLANT_LOCATION, 1).get(0).getLookup_value());
		}
		ModelAndView model = new ModelAndView("reports/DaywiseHeatPlanExportExcel","dayHeatPlan",dailyRpt);
		model.addObject("report_date", report_date);
		return model;
	}
	@RequestMapping(value = "/dispHeatPlanDtlRptScreen", method = RequestMethod.GET)
	public ModelAndView displayHeatPlanDetailRptScreen(HttpServletRequest req) {
		logger.info("in displayHeatPlanDetailRptView method");
		ModelAndView model = new ModelAndView("reports/HeatPlanRpt");	
		return model;
	}
	@RequestMapping(value = "/getHeatPlanDtlRpt", method = RequestMethod.GET)
	@ResponseBody
	public List<HeatPlanHdrDetails> getHeatPlanDetailsReport(HttpServletRequest req, @RequestParam Integer caster_type, @RequestParam String report_date) {
		logger.info("in displayHeatPlanDetailRptView method");
		HashMap<String,Object> dailyRpt=new HashMap<>();
		List<HeatPlanHdrDetails> heatPlanDet=new ArrayList<HeatPlanHdrDetails>();

		heatPlanDet=heatPlanDetailsService.displayHeatPlanDetailReportView(caster_type, report_date);
		dailyRpt.put("dailyHeatPlanDtl", heatPlanDet);
		List<String> heat=heatPlanDetailsService.getlastEOFHeatNo(report_date);
		if(heat!=null && heat.size()!=0 ){
			dailyRpt.put("lstEOF1",heat.get(0));
			dailyRpt.put("lstEOF2",heat.get(1));
		}
		return heatPlanDet;
	}

	@RequestMapping(value = "/exportHeatPlanReport", method = RequestMethod.GET)
	public ModelAndView exportHeatPlanReport(@RequestParam("caster_type") Integer caster_type,@RequestParam("report_date") String report_date) {
		logger.info("in exportHeatPlanReport method");
		List<HeatPlanHdrDetails> list=new ArrayList<HeatPlanHdrDetails>();
		list=heatPlanDetailsService.displayHeatPlanDetailReportView(caster_type, report_date);
		ModelAndView model = new ModelAndView("reports/HeatPlanReportExportExcel","list",list);
		return model;
	}

	/*@RequestMapping(value = "/displayDailyHeatPlan", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanHdrDetails> displayDailyHeatPlanReport(HttpSession session, @RequestParam Integer caster_type ) {
		logger.info("in displayDailyHeatPlanReport method");
		List<HeatPlanHdrDetails> dayHeatPlan=new ArrayList<HeatPlanHdrDetails>();

		dayHeatPlan=heatPlanDetailsService.getCasterwiseHeatPlanDetails(caster_type);

		return dayHeatPlan;
	}*/

	@RequestMapping(value = "/getHeatPlanDetailsForAttachByStatus", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<HeatPlanHdrDetails> getHeatPlanDetailsForAttachByStatus(
			@RequestParam("PLAN_STATUS") String planstatus, HttpSession session) {
		logger.info(HeatPlanDetailsController.class
				+ "...getHeatPlanDetailsForAttachByStatus()");
		String planstatusiddata[] = planstatus.split(",");
		Integer psn_Grade=0;
		String ids = "";

		for (Integer i = 0; i < planstatusiddata.length; i++) {
			Integer stageid = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ planstatusiddata[i]
									+ "' and status_type='PLAN_LINES'");
			ids = stageid + "," + ids;
		}

		ids = ids.substring(0, ids.length() - 1);
		List<HeatPlanHdrDetails> list = heatPlanDetailsService.getHeatPlanDetailsForAttachByStatus(ids);
		List<HeatPlanHdrDetails> finalLi = new ArrayList<HeatPlanHdrDetails>();
		List<HeatPlanDetails> lineDetLi;
		HeatPlanHdrDetails hdrObj;
		for (HeatPlanHdrDetails h_obj : list){
			lineDetLi = new ArrayList<HeatPlanDetails>();
			for (HeatPlanDetails obj : h_obj.getHeatPlanDtls())
			{
				if(obj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)){
					lineDetLi.add(obj);
				}
			}
			lineDetLi.sort((HeatPlanDetails s1, HeatPlanDetails s2)->s1.getIndent_no() -s2.getIndent_no()); 
			
			for (HeatPlanDetails l_obj : lineDetLi){
				hdrObj = new HeatPlanHdrDetails();

				psn_Grade = l_obj.getPsnHdrModel().getPsn_hdr_sl_no();
				hdrObj.setHeat_plan_id(h_obj.getHeat_plan_id());
				hdrObj.setPlan_sequence(h_obj.getPlan_sequence());
				hdrObj.setProd_start_date(h_obj.getProd_start_date());
				//hdrObj.setAim_psn_id(h_obj.getAim_psn_id());
				//hdrObj.setPsnHdrModel(h_obj.getPsnHdrModel());

				hdrObj.setAim_psn_id(l_obj.getAim_psn());  //new change
				hdrObj.setPsnHdrModel(l_obj.getPsnHdrModel()); //new change
				hdrObj.setLookupSectionType(h_obj.getLookupSectionType());
				hdrObj.setSmsCapabilityMstrModel(h_obj.getSmsCapabilityMstrModel());

				hdrObj.setGrade(psnGradeMasterDaoImpl.getPSNGradeOfPsnNo(l_obj.getPsnHdrModel().getPsn_hdr_sl_no()).getPsn_grade());

				hdrObj.getSmsCapabilityMstrModel().setLookupOutputSection(h_obj.getSmsCapabilityMstrModel().getLookupOutputSection());
				hdrObj.setCaster_type(h_obj.getCaster_type());
				hdrObj.setLookupCasterType(h_obj.getLookupCasterType());
				hdrObj.setSection_type(h_obj.getSection_type());
				hdrObj.setSection_planned(h_obj.getSection_planned());
				hdrObj.setLine_no(l_obj.getIndent_no());

				hdrObj.setLine_id(l_obj.getHeat_plan_dtl_id());

				//hdrObj.setCut_length(l_obj.getPlan_cut_length());
				hdrObj.setPlan_qty(l_obj.getPlan_heat_qty());
				hdrObj.setLine_status(l_obj.getStatusMstrModel().getMain_status_desc());
				hdrObj.setLine_version(l_obj.getVersion());
				hdrObj.setCut_length(h_obj.getHeatPlanLinesDetails().getPlan_cut_length());
				finalLi.add(hdrObj);
			}
		}

		return finalLi;
	}

	@RequestMapping(value = "/getHeatPlanHdrById", method = RequestMethod.GET, produces = "application/json")
	public  @ResponseBody HeatPlanHdrDetails getHeatPlanHdrById(@RequestParam("plan_id") Integer plan_id, HttpSession session) {

		return heatPlanDetailsService.getHeatPlanHeaderDetailsById(plan_id);
	}

	@RequestMapping(value = "/CancelHeatPlanDetails", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse CancelHeatPlanDetails(
			@RequestParam("plan_hdr_id") Integer plan_id, HttpSession session) {
		String result = "";
		HeatPlanHdrDetails heatPlanHdr;
		try {
			logger.info(HeatPlanDetailsController.class);
			heatPlanHdr = heatPlanDetailsService.getHeatPlanHeaderDetailsById(plan_id);
			Integer hdr_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_CANCELLED
							+ "' and status_type='PLAN_HEADER'");

			heatPlanHdr.setMain_status_id(hdr_status_id);
			heatPlanHdr.setRecord_status(0);
			heatPlanHdr.setUpdatedBy(Integer.parseInt(session.getAttribute(
					"USER_APP_ID").toString()));
			heatPlanHdr.setUpdatedDateTime(new Date());

			Integer line_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_CANCELLED
							+ "' and status_type='PLAN_LINES'");

			for (HeatPlanLinesDetails lineObj : heatPlanHdr.getHeatPlanLine()){
				lineObj.setRecord_status(0);
				lineObj.setLine_status(line_status_id);
				lineObj.setUpdatedBy(Integer.parseInt(session.getAttribute(
						"USER_APP_ID").toString()));
				lineObj.setUpdatedDateTime(new Date());
			}

			for (HeatPlanDetails dtlObj : heatPlanHdr.getHeatPlanDtls()){
				dtlObj.setRecord_status(0);
				dtlObj.setStatus(line_status_id);
				dtlObj.setUpdatedBy(Integer.parseInt(session.getAttribute(
						"USER_APP_ID").toString()));
				dtlObj.setUpdatedDateTime(new Date());
			}

			result = heatPlanDetailsService.heatPlanHeaderUpdate(heatPlanHdr);				
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside CancelHeatPlanDetails Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
	}

	@RequestMapping(value="/closeHeatPlanDetails", method=RequestMethod.POST,headers = { "Content-type=application/json" })
	public @ResponseBody RestResponse closeHeatPlanDetails(
			
			@RequestParam Integer heat_plan_id, 
			@RequestBody HeatPlanDetails heatpldtls,
		    HttpSession session) {
		String result = "";
		HeatPlanDetails heatPlandtls=null;
		String row[]=heatpldtls.getGrid_arry().split("SIDS");
		try {
			logger.info(HeatPlanDetailsController.class);
			
               for(int i=0;i<row.length;i++){
				String id[] = row[i].split("@");
                heatPlandtls = heatPlanDetailsService.getHeatPlanDetailsById(Integer.parseInt(id[0]));
            Integer hdr_status_id = commonService
					.getLookupIdByQuery("select main_status_id from MainHeatStatusMasterModel where main_status_desc='"
							+ Constants.MAINHEAT_STATUS_CLOSE
							+ "' and status_type='PLAN_LINES'");
			heatPlandtls.setStatus(hdr_status_id);
			heatPlandtls.setRecord_status(0);
			heatPlandtls.setUpdatedBy(Integer.parseInt(session.getAttribute(
					"USER_APP_ID").toString()));
			heatPlandtls.setUpdatedDateTime(new Date());
			HeatPlanHdrDetails heatPlanHdrDetails = heatPlanDetailsService.getHeatPlanHeaderDetailsById(heatPlandtls.getHeat_plan_id());
			IfacesmsLpDetailsModel ifacObj= new IfacesmsLpDetailsModel();
			ifacObj.setMsg_id(null);
			ifacObj.setSch_id(heatPlanHdrDetails.getLp_schd_id());
			ifacObj.setPlanned_heat_id(heatPlandtls.getLp_plan_heat_id());
			ifacObj.setActual_heat_id(null);
			ifacObj.setPrev_sch_id(null);
			ifacObj.setPrev_planned_heat_id(null);
			ifacObj.setGrade(null);
			ifacObj.setEvent_code("HEATREJ");
			ifacObj.setInterface_status(0);
			ifacObj.setError_msg(null);
			ifacObj.setCreatedBy(Integer.parseInt(session.getAttribute(
					"USER_APP_ID").toString()));
			ifacObj.setCreated_Date(new Date());
			ifacObj.setModified_by(null);
			ifacObj.setModified_date(null);
			
			result = heatPlanDetailsService.heatPlanDetailsUpdate(heatPlandtls,ifacObj);
              }
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(HeatPlanDetailsController.class
					+ " Inside closeHeatPlanDetails Exception..", e);
		}
		if(result.equals(Constants.SAVE) || result.equals(Constants.UPDATE)){
			return new RestResponse("SUCCESS", result);
		}else{
			return new RestResponse("FAILURE", result);
		}
		
				
		
	}
	
	@RequestMapping(value = "/getSalesOrderList", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<SoHeader> getSalesOrderList(@RequestParam("psn_hdr_id") Integer psn_hdr_id, String section_type) {

		return heatPlanDetailsService.getSalesOrderDetails(psn_hdr_id, section_type);
	}

}