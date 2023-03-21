package com.smes.trans.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.EOFCrewDetails;
import com.smes.trans.model.HeatProcessEventDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.model.VDHeatConsumableModel;
import com.smes.trans.model.VDHeatDetailsModel;
import com.smes.trans.model.VdAdditionsDetailsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("VDProductionDao")
public class VDProductionDaoImpl extends
								GenericDaoImpl<VDHeatDetailsModel, Long> implements VDProductionDao {
	private static final Logger logger = Logger
			.getLogger(VDProductionDaoImpl.class);

	@Override
	public VDHeatDetailsModel getVDHeatDtlsFormByID(Integer trns_sl_no) {
		logger.info("inside .. getVDHeatDtlsFormByID....."
				+ LRFProductionDaoImpl.class);
		VDHeatDetailsModel vdHeatObj = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {

			String hql = " select TO_CHAR (a.production_date, 'DD/MM/YYYY HH:MI'),a.production_shift, a.sub_unit_id, b.sub_unit_name, a.aim_psn,c.psn_no,"
					+ " (select lookup_value from LookupMasterModel where lookup_id=a.target_caster), a.prev_unit,(select steel_ladle_no from  SteelLadleMasterModel where steel_ladle_si_no=a.steel_ladle_no) , a.steel_wgt, a.dispatch_temp,a.heat_plan_id, a.heat_plan_line_id,a.heat_counter,a.heat_id, "
					+ " a.trns_si_no,htc.heat_track_id,a.target_caster,(select st.st_ladle_track_id from SteelLadleTrackingModel st where st.st_ladle_si_no = a.steel_ladle_no) AS ladle_track_id,a.lowest_m_bar,a.holding_time from VDHeatDetailsModel a, SubUnitMasterModel b,  PSNHdrMasterModel c,HeatStatusTrackingModel htc where a.sub_unit_id = b.sub_unit_id  "
					+ " and a.aim_psn = c.psn_hdr_sl_no and htc.heat_id=a.heat_id  and htc.heat_counter=a.heat_counter and a.trns_si_no ="
					+ trns_sl_no;
			List ls = (List<VDHeatDetailsModel>) getResultFromNormalQuery(hql);
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				vdHeatObj = new VDHeatDetailsModel();
				vdHeatObj.setProduction_date(((null == rows[0]) ? null : df.parse(rows[0].toString())));
				vdHeatObj.setProduction_shift((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));
				vdHeatObj.setSub_unit_id((null == rows[2]) ? null : Integer.parseInt(rows[2].toString()));
				vdHeatObj.setSub_unit_name((null == rows[3]) ? null : rows[3].toString());
				vdHeatObj.setAim_psn((null == rows[4]) ? null: Integer.parseInt(rows[4].toString()));
				vdHeatObj.setPsn_no((null == rows[5]) ? null : rows[5].toString());
				vdHeatObj.setTarget_caster_name((null == rows[6]) ? null: rows[6].toString());
				vdHeatObj.setPrev_unit((null == rows[7]) ? null : rows[7].toString());
				vdHeatObj.setSteel_ladle_name((null == rows[8]) ? null : rows[8].toString());
				vdHeatObj.setSteel_wgt((null == rows[9]) ? null:Float.parseFloat( rows[9].toString()));
				vdHeatObj.setDispatch_temp((null == rows[10]) ? null : Integer.parseInt(rows[10].toString()));
				vdHeatObj.setHeat_plan_id((null == rows[11]) ? null : Integer.parseInt(rows[11].toString()));
				vdHeatObj.setHeat_plan_line_id((null == rows[12]) ? null : Integer.parseInt(rows[12].toString()));
				vdHeatObj.setHeat_counter((null == rows[13]) ? null : Integer.parseInt(rows[13].toString()));
				vdHeatObj.setHeat_id((null == rows[14]) ? null : rows[14].toString());
				vdHeatObj.setTrns_si_no((null == rows[15]) ? null : Integer.parseInt(rows[15].toString()));
				vdHeatObj.setHeat_trackingId((null == rows[16]) ? null: Integer.parseInt(rows[16].toString()));
				vdHeatObj.setTarget_caster((null == rows[17]) ? null: Integer.parseInt(rows[17].toString()));
				vdHeatObj.setSteel_ladle_no((null == rows[18]) ? null: Integer.parseInt(rows[18].toString()));
				vdHeatObj.setLowest_m_bar((null == rows[19]) ? null: Integer.parseInt(rows[19].toString()));
				vdHeatObj.setHolding_time((null == rows[20]) ? null: Integer.parseInt(rows[20].toString()));
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(VDProductionDaoImpl.class
					+ " error in getVDHeatDtlsFormByID........" + e);
			e.printStackTrace();
		}

		return vdHeatObj;

	
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveAll(Hashtable<String, Object> mod_obj) {
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if ((VDHeatDetailsModel) mod_obj.get("VD_HEAT_DET") != null) {
				session.save((VDHeatDetailsModel) mod_obj.get("VD_HEAT_DET"));
				}
			
			if ((List<EOFCrewDetails>) mod_obj.get("VD_CREW_DET") != null) {
				
				List<EOFCrewDetails> list = (List<EOFCrewDetails>) mod_obj
						.get("VD_CREW_DET");
					for (EOFCrewDetails obj : list) {
						session.save(obj);
				}
			}
			
			if ((HeatStatusTrackingModel) mod_obj.get("VD_HEAT_STATUS") != null) {
				HeatStatusTrackingModel hstm = (HeatStatusTrackingModel) mod_obj
						.get("VD_HEAT_STATUS");
						session.update(hstm);
							}
			if ((SteelLadleTrackingModel) mod_obj.get("VD_ST_LALDE_STATUS_UPDATE") != null) {
				SteelLadleTrackingModel trackObj = (SteelLadleTrackingModel) mod_obj
						.get("VD_ST_LALDE_STATUS_UPDATE");
				session.update(trackObj);

			}
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(VDProductionDaoImpl.class
					+ " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(VDProductionDaoImpl.class
					+ " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(VDProductionDaoImpl.class
					+ " Inside VDProductionDaoImpl saveAll Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	
	}
	
	@Override
	public String updateVDHeatDetails(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
		if ((VDHeatDetailsModel) mod_obj.get("VD_HEAT_DET") != null) {
			session.update((VDHeatDetailsModel) mod_obj.get("VD_HEAT_DET"));
				}
	
		if ((HeatStatusTrackingModel) mod_obj.get("VD_HEAT_STATUS") != null) {
		HeatStatusTrackingModel hstm = (HeatStatusTrackingModel) mod_obj
				.get("VD_HEAT_STATUS");
				session.update(hstm);
					}
			commit(session);
			result = Constants.SAVE;
			} catch (DataIntegrityViolationException e) {
			logger.error(VDProductionDaoImpl.class+ " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
			} catch (ConstraintViolationException e) {
				logger.error(VDProductionDaoImpl.class+ " Inside DataIntegrityViolationException() Exception..");
				result = Constants.DATA_EXIST;
				rollback(session);
			} catch (Exception e) {
				e.printStackTrace();
				rollback(session);
				logger.error(VDProductionDaoImpl.class	+ " Inside VDProductionDaoImpl updateVDHeatDetails Exception..", e);
				result = Constants.SAVE_FAIL;
			} finally {
				close(session);
			}
			return result;

	}

	@Override
	public String processEventDtlsSaveOrUpdate(Hashtable<String, Object> mod_obj) {

		String result = "";
		Session session=getNewSession();
		try{
			begin(session);
			
			
			if(Integer.parseInt(mod_obj.get("HEATPROCESSEVENT_CNT").toString())>0)
			{
				
				Integer cnt=Integer.parseInt(mod_obj.get("HEATPROCESSEVENT_CNT").toString());
				String key="HEATPROCESSEVENT";
				for(int i=0;i<=cnt;i++)
				{
					key=key+i;
				if((HeatProcessEventDetails)mod_obj.get(key)!=null)
				{
					HeatProcessEventDetails heatEvent_det=(HeatProcessEventDetails)mod_obj.get(key);
					
				if(heatEvent_det.getHeat_proc_event_id()!=null)
				{
				session.update(heatEvent_det);
				}else{
				session.save(heatEvent_det);
				}
				heatEvent_det=null;
				}
				}
			}
			if((HeatProcessEventDetails)mod_obj.get("HEATPROCESS_VD_EVENT")!=null)
			{
					session.save((HeatProcessEventDetails)mod_obj.get("HEATPROCESS_VD_EVENT"));
			}
			
			
			commit(session);
			result =Constants.SAVE;
		} 
		catch(DataIntegrityViolationException e)
		{
			logger.error(VDProductionDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch(ConstraintViolationException e)
		{
			logger.error(VDProductionDaoImpl.class+" Inside DataIntegrityViolationException() Exception..");
			result =Constants.DATA_EXIST;
			rollback(session);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(VDProductionDaoImpl.class+" Inside processEventDtlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		}finally {
			close(session);
		}

		return result;
	
	}

	@Override
	public VDHeatDetailsModel getVdHeatObject(Integer trns_si_no) {

		// TODO Auto-generated method stub
		VDHeatDetailsModel obj = null;
		Session session = getNewSession();
		try {
			begin(session);
			obj = (VDHeatDetailsModel) session.get(
					VDHeatDetailsModel.class, trns_si_no);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(session);
		}
		return obj;
	
	}

	@Override
	public VDHeatDetailsModel getVDPreviousHeatDetailsByHeatNo(Integer trans_si_no) {
		VDHeatDetailsModel VdHeatDetails=new VDHeatDetailsModel();
		Session session=getNewSession();
		try {
			String hql = "select a from VDHeatDetailsModel a where a.trns_si_no < (select b.trns_si_no from VDHeatDetailsModel b where b.trns_si_no ="+trans_si_no+") and rownum <= 1 order by a.trns_si_no desc";
		Query query=session.createQuery(hql);
		VdHeatDetails=(VDHeatDetailsModel) query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getVDPreviousHeatDetailsByHeatNo........"+e);
			e.printStackTrace();
		}
		finally{
			close(session);
		}
		
		return VdHeatDetails;
	
	}

	@Override
	public VDHeatDetailsModel getVDHeatDetailsByHeatNo(String heatNo) {
		// TODO Auto-generated method stub
				logger.info("inside .. getEOFHeatDetailsByHeatNo....."+EofProductionDaoImpl.class);
				VDHeatDetailsModel obj = new VDHeatDetailsModel();
				Session session=getNewSession();
				try {

					String hql = "select a from VDHeatDetailsModel a where heat_id='" +heatNo+"'";
					List<VDHeatDetailsModel> li = session.createQuery(hql).list();
					if(li.size() > 0)
						obj= li.get(0);
					
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("error in getEOFHeatDetailsByHeatNo........"+e);
					e.printStackTrace();
				}
				finally{
					close(session);
				}
				
				return obj;
	}
	
	@SuppressWarnings("rawtypes")

	@Override

	public List<VDHeatConsumableModel> getVdAdditions(String lookup_code, Integer subUnitId) {

		logger.info(VDProductionDaoImpl.class + " Inside getVdAdditions " + subUnitId);

		List<VDHeatConsumableModel> additionsList = new ArrayList<VDHeatConsumableModel>();

		Session session = getNewSession();

		VDHeatConsumableModel obj;

		try {

			String hql = "select "

					/*
					 * +
					 * "(select hc.cons_sl_no   || '&&'  || hc.consumption_qty  || '&&' || TO_CHAR (hc.addition_date_time, 'DD/MM/YYYY HH:MI')  FROM LRFHeatArcingDetailsModel hd, VDHeatConsumableModel hc "
					 * 
					 * +
					 * "WHERE hd.arc_sl_no = hc.arc_sl_no   AND hc.material_id = pcon.material_id "
					 * 
					 * + " ) as col1,"
					 */

					+ " pcon.material_id, pcon.material_desc, uomlkp.lookup_value,pcon.material_type,pcon.sap_matl_desc, "
					+ " (select lookup_value from LookupMasterModel where lookup_id = pcon.valuation_type ) from LookupMasterModel pl,LookupMasterModel uomlkp, MtrlProcessConsumableMstrModel pcon, MaterialSubUnitMapMstrModel mu "

					+ " where uomlkp.lookup_id= pcon.uom and pl.lookup_code='"

					+ lookup_code

					+ "'and mu.sub_unit_id=" + subUnitId
					+ " and pcon.record_status = 1 and uomlkp.lookup_id = pcon.uom and pl.lookup_id = pcon.material_type and pcon.material_id = mu.mtrl_id order by pcon.createdDateTime";
			List ls = (List<VDHeatConsumableModel>) getResultFromNormalQuery(hql);
			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new VDHeatConsumableModel();

				/*
				 * String s[] = (null == rows[0] ? null : rows[0].toString()
				 * 
				 * .split("&&"));
				 * 
				 * 
				 * 
				 * if (s != null) {
				 * 
				 * obj.setCons_sl_no(Integer.parseInt((null == s[0]) ? null
				 * 
				 * : s[0].toString()));
				 * 
				 * obj.setConsumption_qty(Double.parseDouble((null == s[1]) ? null :
				 * s[1].toString()));
				 * 
				 * obj.setAddition_date_time(df.parse(s[2].toString()));
				 * 
				 *
				 * 
				 * + obj.getAddition_date_time());
				 * 
				 * } else {
				 * 
				 * obj.setCons_sl_no(null);
				 * 
				 * obj.setConsumption_qty(null);
				 * 
				 * obj.setAddition_date_time(new Date());
				 * 
				 * 
				 * 
				 * }
				 */

				obj.setCons_sl_no(null);

				obj.setConsumption_qty(null);

				obj.setAddition_date_time(new Date());

				obj.setMaterial_id(Integer.parseInt((null == rows[0]) ? null

						: rows[0].toString()));

				obj.setMaterial_name((null == rows[1]) ? null
						: rows[1]

								.toString());

				obj.setUom((null == rows[2]) ? null : rows[2].toString());

				obj.setMaterial_type(Integer.parseInt((null == rows[3]) ? null

						: rows[3].toString()));
				obj.setSap_matl_id((null == rows[4]) ? null : rows[4].toString());

				obj.setValuation_type((null == rows[5]) ? null : rows[5].toString());

				additionsList.add(obj);

			}

		} catch (Exception e) {

			logger.error("error in getVdAdditions........" + e);

			e.printStackTrace();

		} finally {

			close(session);

		}

		return additionsList;

	}
	
	@Override

	public VdAdditionsDetailsModel getAddDetailsBySlno(Integer arc_sl_no) {

		logger.info("inside .. getArcDetailsBySlno.....");

		VdAdditionsDetailsModel list = new VdAdditionsDetailsModel();

		Session session = getNewSession();

		try {

			String query = "select a from VdAdditionsDetailsModel a where a.arc_sl_no= " + arc_sl_no;

			list = (VdAdditionsDetailsModel) session.createQuery(query).uniqueResult();

		} catch (Exception e) {

			// TODO: handle exception

			e.printStackTrace();

			logger.error("error in getArcDetailsBySlno........" + e);

		}

		finally {

			close(session);

		}

		return list;

	}
	
	@Override

	public VDHeatConsumableModel getVDHeatConsumablesById(Integer cons_sl_no) {

		// TODO Auto-generated method stub

		logger.info("inside .. getVDHeatConsumablesById.....");

		VDHeatConsumableModel list = new VDHeatConsumableModel();

		Session session = getNewSession();

		try {

			list = (VDHeatConsumableModel) session.get(VDHeatConsumableModel.class, cons_sl_no);

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getVDHeatConsumablesById........" + e);

		}

		finally {

			close(session);

		}

		return list;

	}
	
	@Override

	public String getNextAddNo(String heat_id, Integer heat_counter) {

		// TODO Auto-generated method stub

		Session session = getNewSession();

		String nextAddNo = "";

		try {

			Query query = session.createQuery(
					"select coalesce((max (ad.arc_no) + 1),1) from VdAdditionsDetailsModel ad where  ad.heat_id ='"
							+ heat_id + "' and ad.heat_counter=1");

			List<?> results = query.list();

			Iterator<?> it = results.iterator();

			while (it.hasNext()) {

				nextAddNo = it.next().toString();

			}

		} catch (Exception e)

		{

			e.printStackTrace();

		}

		finally {

			logger.info("close calling ...getVDNextAddNo");

			close(session);

		}

		return nextAddNo;

	}
	
	@Override

	public String VDAddSaveOrUpdate(Hashtable<String, Object> mod_obj) {

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String result = "";

		Session session = getNewSession();

		try {

			begin(session);

			if ((VdAdditionsDetailsModel) mod_obj.get("LRF_ARC_ADD") != null)

			{

				VdAdditionsDetailsModel arcDet = (VdAdditionsDetailsModel) mod_obj.get("LRF_ARC_ADD");

				if (arcDet.getArc_sl_no() != null)

				{

					session.update((VdAdditionsDetailsModel) mod_obj.get("LRF_ARC_ADD"));

				} else {

					session.save((VdAdditionsDetailsModel) mod_obj.get("LRF_ARC_ADD"));

				}

			}

			if (Integer.parseInt(mod_obj.get("LRF_ARC_ADD_CONS_CNT").toString()) > 0)

			{

				VdAdditionsDetailsModel arcObj = (VdAdditionsDetailsModel) mod_obj.get("LRF_ARC_ADD");

				Integer cnt = Integer.parseInt(mod_obj.get("LRF_ARC_ADD_CONS_CNT").toString());

				String key = "LRF_ARC_ADD_CONS";

				for (int i = 0; i <= cnt; i++)

				{

					key = key + i;

					if ((VDHeatConsumableModel) mod_obj.get(key) != null)

					{

						VDHeatConsumableModel conObj = (VDHeatConsumableModel) mod_obj.get(key);

						conObj.setArc_sl_no(arcObj.getArc_sl_no());

						if (conObj.getCons_sl_no() != null)

						{

							session.update(conObj);

						} else {

							session.save(conObj);

						}

						conObj = null;

					} // if

				} // for

			}

			commit(session);

			result = Constants.SAVE;

		}

		catch (DataIntegrityViolationException e)

		{

			logger.error(VDProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		}

		catch (ConstraintViolationException e)

		{

			logger.error(VDProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		}

		catch (Exception e) {

			e.printStackTrace();

			rollback(session);

			logger.error(VDProductionDaoImpl.class + " Inside lrfArcAdditionsSaveOrUpdate Exception..", e);

			result = Constants.SAVE_FAIL;

		} finally {

			close(session);

		}

		return result;

	}
	
	
	@Override

	public List<Map<String, Object>> getVDAdditionsTemp(String qry) {

		List<HashMap<String, String>> additionsList = new ArrayList<HashMap<String, String>>();

		Session session = getNewSession();

		VDHeatConsumableModel obj;

		List<Map<String, Object>> aliasToValueMapList = null;

		try {

			Query query = session.createSQLQuery(qry);

			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

			aliasToValueMapList = query.list();


		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getVDAdditions........" + e);

			e.printStackTrace();

		} finally {

			close(session);

		}

		return aliasToValueMapList;

	}
	
	
}
