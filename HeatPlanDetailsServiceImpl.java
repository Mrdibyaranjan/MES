package com.smes.trans.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.masters.dao.impl.PSNProductMasterDao;
import com.smes.masters.dao.impl.PsnGradeMasterDao;
import com.smes.masters.model.PSNHdrMasterModel;
import com.smes.masters.model.PSNProductMasterModel;
import com.smes.masters.model.PsnGradeMasterModel;
import com.smes.trans.dao.impl.HeatPlanDetailsDao;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.SoHeader;
import com.smes.util.Constants;

@Service("heatPlanDetailsService")
public class HeatPlanDetailsServiceImpl implements HeatPlanDetailsService{

	@Autowired
	private HeatPlanDetailsDao heatPlanDetailsDao;

	@Autowired
	private PSNProductMasterDao psnProductDao;
	
	@Autowired
	private PsnGradeMasterDao psnGradeDao;
	
	@Transactional
	@Override
	public List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByStatus(String ids) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanHeaderDetailsByStatus(ids);
	}

	//@Transactional
	@Override
	public List<HeatPlanLinesDetails> getHeatPlanLineDetailsByStatus(
			Integer heatplanid) {
		// TODO Auto-generated method stub
		 List<HeatPlanLinesDetails> list = heatPlanDetailsDao.getHeatPlanLineDetailsByStatus(heatplanid);
		 SoHeader soObj;
		 for(HeatPlanLinesDetails obj : list) {
			 soObj = new SoHeader();
			 soObj.setSoId(obj.getSoHeaderModel().getSoId());
			 soObj.setOrderNo(obj.getSoHeaderModel().getOrderNo());
			 soObj.setItemNo(obj.getSoHeaderModel().getItemNo());
			 soObj.setSoiDocTypeRef(obj.getSoHeaderModel().getSoiDocTypeRef());
			 soObj.setSoHeaderItem(obj.getSoHeaderModel().getOrderNo()+""+obj.getSoHeaderModel().getItemNo()+"/"+obj.getSoHeaderModel().getSoiDocTypeRef());
			 obj.setSoHeaderModel(soObj);
		 }
		return list;
	}

	//@Transactional
	@Override
	public String heatPlanHeaderSave(HeatPlanHdrDetails heatPlanHdrDetails) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.heatPlanHeaderSave(heatPlanHdrDetails);
	}

	//@Transactional
	@Override
	public String heatPlanLinesSave(HeatPlanLinesDetails heatPlanLinesDetails) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.heatPlanLinesSave(heatPlanLinesDetails);
	}

	//@Transactional
	@Override
	public Integer getMaxHDRHeatPlan_id() {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getMaxHDRHeatPlan_id();
	}

	//@Transactional
	@Override
	public String heatPlanLinesUpdate(HeatPlanLinesDetails heatPlanLinesDetails) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.heatPlanLinesUpdate(heatPlanLinesDetails);
	}

	@Override
	public String heatPlanHeaderUpdate(HeatPlanHdrDetails heatPlanHdrDetails) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.heatPlanHeaderUpdate(heatPlanHdrDetails);
	}
	
	@Override
	public String heatPlanDetailsUpdate(HeatPlanDetails heatPlanDetails,IfacesmsLpDetailsModel ifacObj) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.heatPlanDetailsUpdate(heatPlanDetails,ifacObj);
	}

	@Override
	public List<HeatPlanHdrDetails> getHeatPlanDetailsForAttachByStatus(
			String ids) {
		// TODO Auto-generated method stub
		
		return heatPlanDetailsDao.getHeatPlanDetailsForAttachByStatus(ids);
	}

	@Override
	public HeatPlanHdrDetails getHeatPlanHeaderDetailsById(Integer heat_plan_id) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanHeaderDetailsById(heat_plan_id);
	}
	
	@Override
	public HeatPlanLinesDetails getHeatPlanLineDetailsById(
			Integer heat_plan_line_id) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanLineDetailsById(heat_plan_line_id);
	}
	
	@Transactional
	@Override
	public List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId,Integer lstatusId) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanDetWithRunId(runId,lstatusId);
	}

	@Override
	public HeatPlanLinesDetails getHeatPlanLineObject(Integer heatplan_linepk) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanLineObject(heatplan_linepk);
	}

	@Transactional
	@Override
	public List<HeatPlanHdrDetails> getAllPrevHeatPlanDetails() {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getAllPrevHeatPlanDetails();
	}

	@Transactional
	@Override
	public List<HeatPlanHdrDetails> getDaywiseHeatPlanDetails() {
		// TODO Auto-generated method stub
		List<HeatPlanHdrDetails> list = heatPlanDetailsDao.getDaywiseHeatPlanDetails();
		
		return getHeatPlanGradeRoute(list);
	}
	
	
	private List<HeatPlanHdrDetails> getHeatPlanGradeRoute(List<HeatPlanHdrDetails> list){
		List<PSNProductMasterModel> psnProductLi = null; 
		List<PsnGradeMasterModel> psnGradeLi = null;
		int sec_pln, out_sec = 0, counter = 1;
		PSNHdrMasterModel psnHdrMasterModel = null;
		String grade;
		String process_route;
		for(HeatPlanHdrDetails obj : list) {
			for(HeatPlanLinesDetails l : obj.getHeatPlanLine()){
				psnProductLi = psnProductDao.getPSNProductMstrDtls(l.getAim_psn()); 
				psnGradeLi = psnGradeDao.getPSNGradeByPsnNo(l.getAim_psn());  
				grade = "";
				psnHdrMasterModel = l.getPsnHdrModel();
				obj.setPsn_no(psnHdrMasterModel.getPsn_no());
				for(PSNProductMasterModel p : psnProductLi){
					sec_pln = obj.getSmsCapabilityMstrModel().getSection();
					if (p.getOutput_section() != null){
						out_sec = p.getOutput_section();
					}else{
						out_sec = 0;
					}
				
					if(sec_pln == out_sec){
						l.setProcess_route(p.getLkpProcRoutMstrMdl().getLookup_value());
					}
				}
				for(PsnGradeMasterModel g: psnGradeLi){
					if(grade.equals(""))
						grade = g.getPsn_grade();
					else
						grade = grade + "," + g.getPsn_grade();
				}
				l.setGrade(grade);
				l.setSlNo(counter);
				obj.setGrade(grade);
				obj.setProcess_route(l.getProcess_route());
				obj.setTundish(l.getTundish_type());
	            obj.setRemarks(l.getLine_remarks());
				counter = counter + 1;
			}
			obj.setArea(obj.getLookupCasterType().getLookup_value());
			obj.setProduct(obj.getLookupSectionType().getLookup_value());
			obj.setSection(obj.getSmsCapabilityMstrModel().getLookupOutputSection().getLookup_value());
		
		}

		for(HeatPlanHdrDetails hdrObj : list){
			hdrObj.setTotal(counter - 1);
		}
		return list;
	}

	@Override
	public HeatPlanDetails getHeatPlanDetailsById(
			Integer heat_plan_dtl_id) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanDetailsById(heat_plan_dtl_id) ;
	}

	@Override
	public List<HeatPlanHdrDetails> getCasterwiseHeatPlanDetails(Integer caster_type) {
		// TODO Auto-generated method stub
		
		return getHeatPlanGradeRoute(heatPlanDetailsDao.getCasterwiseHeatPlanDetails(caster_type));
	}

	@Override
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReport(Integer caster,
			String report_date) {
		// TODO Auto-generated method stub
		return getHeatPlanGradeRoute(heatPlanDetailsDao.displayHeatPlanDetailReport(caster, report_date));
	}

	@Override
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReportView(Integer caster,
			String report_date) {
		// TODO Auto-generated method stub
		return getHeatPlanGradeRoute(heatPlanDetailsDao.displayHeatPlanDetailReportView(caster, report_date));
	}
	
	@Override
	public List<String> getlastEOFHeatNo(String date) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getlastEOFHeatNo(date);
	}
	
	

	@Override
	public List<SoHeader> getSalesOrderDetails(Integer psn_hdr_id, String section_type) {
		// TODO Auto-generated method stub
		List<PsnGradeMasterModel> psnGradeList = psnGradeDao.getPSNGradeMstrDtls(psn_hdr_id);
		String compo_mtrl = null;
		if(section_type.equalsIgnoreCase(Constants.SEC_TYPE_BLT)) {
			compo_mtrl = Constants.SO_COMPO_MTRL_BLT;
		}else if(section_type.equalsIgnoreCase(Constants.SEC_TYPE_BLM)) {
			compo_mtrl = Constants.SO_COMPO_MTRL_BLM;
		}else if(section_type.equalsIgnoreCase(Constants.SEC_TYPE_RND)) {
			compo_mtrl = Constants.SO_COMPO_MTRL_RND;
		}else if(section_type.equalsIgnoreCase(Constants.SEC_TYPE_SLB)) {
			compo_mtrl=Constants.SO_COMPO_MTRL_SLB;
		}
		
		return heatPlanDetailsDao.getSalesOrderDetails(psnGradeList.get(0), compo_mtrl);
	}

	@Override
	public List<HeatPlanDetails> getHeatPlanLineByStatus(Integer heat_plan_id) {
		// TODO Auto-generated method stub
		return heatPlanDetailsDao.getHeatPlanLineByStatus(heat_plan_id);
	}

	
}
