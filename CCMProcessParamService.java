package com.smes.trans.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smes.masters.model.ProcessParametersMasterModel;
import com.smes.masters.service.impl.ProcessParamsMasterService;
import com.smes.trans.dao.impl.CCMHeatProcessParameterDetailsDao;
import com.smes.trans.model.CCMHeatProcessParameterDetails;
import com.smes.util.Constants;

@Service
public class CCMProcessParamService {

	@Autowired
	CCMHeatProcessParameterDetailsDao ccmProcessparamDao;
	
	@Autowired
	ProcessParamsMasterService mstrProcService;
	
	public List<CCMHeatProcessParameterDetails> getProcessParamForAllStrand(Integer subunit_id, String heatNo, Integer heat_counter){
		List<CCMHeatProcessParameterDetails> addedProcessParms=new ArrayList<>();
		List<ProcessParametersMasterModel> mstrProcParms=mstrProcService.getPParametersBySubUnit(subunit_id);
		//List<ProcessParametersMasterModel> mstrProcParms=mstrProcService.getPParametersBySubUnit(subunit_id,heat_counter);
		
		if( mstrProcParms.size()!=0 )
		{
		mstrProcParms.forEach(mstrParm->{
			
			if(mstrParm.getHeat_parameter() == 0)
			{
								
					List<CCMHeatProcessParameterDetails> ccmProcParams=ccmProcessparamDao.getProcessParamByHeatNoAndParm(heatNo, heat_counter, mstrParm.getParam_sl_no());
					if(!ccmProcParams.isEmpty())
					{
						addedProcessParms.add(ccmProcParams.get(0));
					}
					else 
					{
						CCMHeatProcessParameterDetails obj=new CCMHeatProcessParameterDetails();
						ProcessParametersMasterModel procParaMstrMdl = mstrProcService.getProcessParamsMasterById(mstrParm.getParam_sl_no());
						obj.setProcess_date_time(new Date());
						obj.setProcParaMstrMdl(procParaMstrMdl);
						obj.setParam_id(mstrParm.getParam_sl_no());
						addedProcessParms.add(obj);
					}
				
			}
		
		});
	   }
		if(!addedProcessParms.isEmpty()) {
			Collections.sort(addedProcessParms);
		}
		
		return addedProcessParms;
	}
	
	public String saveOrUpdateProcParam(List<CCMHeatProcessParameterDetails> procparams,String user,String HeatNo) {
		String result="";
		try {
		procparams.forEach(procParam->{
			if(procParam.getProc_param_event_id()==null) {
			    procParam.setCreated_date_time(new Date());
			    procParam.setCreated_by(Integer.parseInt(user));
			    procParam.setHeat_id(HeatNo);
			    procParam.setHeat_counter(1);
			   if(procParam.getStrand1_value()!=null || procParam.getStrand2_value()!=null || procParam.getStrand3_value()!=null
				|| procParam.getStrand4_value()!=null || procParam.getStrand5_value()!=null || procParam.getStrand6_value()!=null) {
			    ccmProcessparamDao.processParamsSave(procParam);
			   }
			}else {
				procParam.setUpdated_date_time(new Date());
				procParam.setUpdated_by(Integer.parseInt(user));
				ccmProcessparamDao.processParamsUpdate(procParam);
			}
		});
		result =Constants.SAVE;
		}catch(Exception ex) {
			ex.printStackTrace();
			result =Constants.SAVE_FAIL;
		}
		return result;
	}
}
