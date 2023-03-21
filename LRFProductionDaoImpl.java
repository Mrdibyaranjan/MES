package com.smes.trans.dao.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.masters.dao.impl.PsnGradeMasterDao;
import com.smes.masters.dao.impl.PsnRouteMasterDao;
import com.smes.masters.model.PsnGradeMasterModel;
import com.smes.reports.model.LRFHeatLogRpt;
import com.smes.trans.model.EOFCrewDetails;
import com.smes.trans.model.EofHeatDetails;
import com.smes.trans.model.HeatChemistryHdrDetails;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatStatusTrackingModel;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.LRFHeatArcingDetailsModel;
import com.smes.trans.model.LRFHeatConsumableLogModel;
import com.smes.trans.model.LRFHeatConsumableModel;
import com.smes.trans.model.LRFHeatDetailsModel;
import com.smes.trans.model.LRFHeatDetailsPsnBkpModel;
import com.smes.trans.model.ReladleProcessDetailsModel;
import com.smes.trans.model.ReladleProcessHdrModel;
import com.smes.trans.model.ReladleTrnsDetailsMdl;
import com.smes.trans.model.StLadleLifeHeatWiseModel;
import com.smes.trans.model.StLdlLifeAtHeat;
import com.smes.trans.model.SteelLadleLifeModel;
import com.smes.trans.model.SteelLadleTrackingModel;
import com.smes.trans.service.impl.HeatPlanDetailsService;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("lrfProductionDao")

public class LRFProductionDaoImpl extends

GenericDaoImpl<LRFHeatDetailsModel, Long> implements LRFProductionDao {

	@Autowired

	HeatPlanDetailsService heatPlanDtlsservice;

	@Autowired

	PsnGradeMasterDao psnGradeDao;

	@Autowired

	PsnRouteMasterDao psnRouteDao;

	private static final Logger logger = Logger

			.getLogger(LRFProductionDaoImpl.class);

	@SuppressWarnings("unchecked")

	@Override

	public String saveAll(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if ((LRFHeatDetailsModel) mod_obj.get("LRF_HEAT_DET") != null) {
				LRFHeatDetailsModel lrf_heat_det = (LRFHeatDetailsModel) mod_obj.get("LRF_HEAT_DET");
				session.save(lrf_heat_det);
				if ((ReladleTrnsDetailsMdl) mod_obj.get("LRF_RELADLE_DET") != null) {
					ReladleTrnsDetailsMdl reladle_obj = (ReladleTrnsDetailsMdl) mod_obj.get("LRF_RELADLE_DET");
					reladle_obj.setHeatRefId(lrf_heat_det.getTrns_sl_no());
					session.save(reladle_obj);
				}
			}
			List<EOFCrewDetails> list = (List<EOFCrewDetails>) mod_obj.get("LRF_CREW_DET");
			if (list != null) {
				for (EOFCrewDetails obj : list) {
					session.save(obj);
				}
			}
			if ((HeatStatusTrackingModel) mod_obj.get("LRF_HEAT_STATUS") != null) {
				HeatStatusTrackingModel hstm = (HeatStatusTrackingModel) mod_obj.get("LRF_HEAT_STATUS");
				if (hstm.getHeat_track_id() == null)
					session.save(hstm);
				else
					session.update(hstm);
			}
			if ((HeatStatusTrackingModel) mod_obj.get("LRF_INACTIVE_HEAT_STATUS") != null) {
				HeatStatusTrackingModel prevHstm = (HeatStatusTrackingModel) mod_obj.get("LRF_INACTIVE_HEAT_STATUS");
				if (prevHstm.getHeat_track_id() == null)
					session.save(prevHstm);
				else
					session.update(prevHstm);
			}
			if ((SteelLadleTrackingModel) mod_obj.get("LRF_ST_LALDE_STATUS_UPDATE") != null) {
				SteelLadleTrackingModel trackObj = (SteelLadleTrackingModel) mod_obj.get("LRF_ST_LALDE_STATUS_UPDATE");
				session.update(trackObj);
			}
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(LRFProductionDaoImpl.class + " Inside LRFProductionDaoImpl saveAll Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;
	}

	@Override

	public String updatelrfHeatDetails(LRFHeatDetailsModel lrfHeatDetails) {

		String result = "";

		Session session = getNewSession();

		try {

			begin(session);

			session.update(lrfHeatDetails);

			commit(session);

			result = Constants.SAVE;

		} catch (DataIntegrityViolationException e) {

			logger.error(LRFProductionDaoImpl.class

					+ " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		} catch (ConstraintViolationException e) {

			logger.error(LRFProductionDaoImpl.class

					+ " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		} catch (Exception e) {

			e.printStackTrace();

			rollback(session);

			logger.error(LRFProductionDaoImpl.class

					+ " Inside LRFProductionDaoImpl updateLRFHeatDetails Exception..", e);

			result = Constants.SAVE_FAIL;

		} finally {

			close(session);

		}

		return result;

	}

	@Override
	public String updateLRFHeatDetails(Hashtable<String, Object> mod_obj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if ((LRFHeatDetailsModel) mod_obj.get("LRF_HEAT_DET") != null) {
				session.update((LRFHeatDetailsModel) mod_obj.get("LRF_HEAT_DET"));
			}
			if ((HeatStatusTrackingModel) mod_obj.get("LRF_HEAT_STATUS") != null) {
				HeatStatusTrackingModel hstm = (HeatStatusTrackingModel) mod_obj.get("LRF_HEAT_STATUS");
				session.update(hstm);
			}
			if ((SteelLadleTrackingModel) mod_obj.get("LRF_ST_LALDE_STATUS_UPDATE") != null) {
				session.update((SteelLadleTrackingModel) mod_obj.get("LRF_ST_LALDE_STATUS_UPDATE"));
			}
			if ((HeatChemistryHdrDetails) mod_obj.get("LRF_HEAT_CHEM") != null) {
				session.update((HeatChemistryHdrDetails) mod_obj.get("LRF_HEAT_CHEM"));
			}
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(LRFProductionDaoImpl.class + " Inside LRFProductionDaoImpl updateLRFHeatDetails Exception..",
					e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;
	}

	@Override
	public HeatStatusTrackingModel getHeatStatusObject(Integer integer) {

		// TODO Auto-generated method stub

		HeatStatusTrackingModel obj = null;

		Session session = getNewSession();

		try {

			begin(session);

			obj = (HeatStatusTrackingModel) session.get(

					HeatStatusTrackingModel.class, integer);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			close(session);

		}

		return obj;

	}

	@Override

	public LRFHeatDetailsModel getLRFHeatDtlsFormByID(Integer trns_sl_no) {

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		logger.info("inside .. getLRFHeatDtlsFormByID....."

				+ LRFProductionDaoImpl.class);

		LRFHeatDetailsModel lrfHeatObj = null;

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {

			String hql = " select TO_CHAR (a.production_date, 'DD/MM/YYYY HH:MI'),a.production_shift, a.sub_unit_id, b.sub_unit_name, a.aim_psn,c.psn_no,"

					+ " (select lookup_value from LookupMasterModel where lookup_id=a.target_caster_id), a.prev_unit,(select steel_ladle_no from  SteelLadleMasterModel where steel_ladle_si_no=a.steel_ladle_no) , a.steel_wgt, a.tap_temp,a.heat_plan_id, a.heat_plan_line_no,a.heat_counter,a.heat_id, "

					+ " a.trns_sl_no,(select hc.sample_temp from HeatChemistryHdrDetails hc where hc.heat_id=a.heat_id and hc.heat_counter=a.heat_counter and hc.analysisLookupDtls.lookup_value = '"+Constants.EAF_TAP_CHEM+"' and hc.final_result = 1), "
					+ "htc.heat_track_id,(select st.st_ladle_track_id from SteelLadleTrackingModel st where st.st_ladle_si_no = a.steel_ladle_no) AS ladle_track_id,a.purge_medium,a.process_control,a.vessel_car_id,hpld.plan_cut_length from LRFHeatDetailsModel a, SubUnitMasterModel b,  PSNHdrMasterModel c,HeatStatusTrackingModel htc,HeatPlanLinesDetails hpld where a.sub_unit_id = b.sub_unit_id  "

					+ " and a.aim_psn = c.psn_hdr_sl_no and htc.heat_id=a.heat_id and a.heat_plan_id=hpld.heat_plan_id  and htc.heat_counter=a.heat_counter and c.psn_status = 'APPROVED' and a.trns_sl_no ="

					+ trns_sl_no;

			List ls = (List<EofHeatDetails>) getResultFromNormalQuery(hql);

			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				lrfHeatObj = new LRFHeatDetailsModel();

				lrfHeatObj.setProduction_date(((null == rows[0]) ? null : df.parse(rows[0].toString())));
				lrfHeatObj.setProduction_shift(Integer.parseInt((null == rows[1]) ? null : rows[1].toString()));
				lrfHeatObj.setSub_unit_id(Integer.parseInt((null == rows[2]) ? null : rows[2].toString()));
				lrfHeatObj.setSub_unit_name((null == rows[3]) ? null : rows[3].toString());
				lrfHeatObj.setAim_psn(Integer.parseInt((null == rows[4]) ? null : rows[4].toString()));
				lrfHeatObj.setPsn_no((null == rows[5]) ? null : rows[5].toString());
				lrfHeatObj.setTarget_caster_name((null == rows[6]) ? null : rows[6].toString());
				lrfHeatObj.setPrev_unit((null == rows[7]) ? null : rows[7].toString());
				lrfHeatObj.setSteel_ladle_name((null == rows[8]) ? null : rows[8].toString());
				lrfHeatObj.setSteel_wgt(Double.parseDouble((null == rows[9]) ? null : rows[9].toString()));
				lrfHeatObj.setTap_temp(Integer.parseInt((null == rows[10]) ? null : rows[10].toString()));
				lrfHeatObj.setHeat_plan_id(Integer.parseInt((null == rows[11]) ? null : rows[11].toString()));
				lrfHeatObj.setHeat_plan_line_no(Integer.parseInt((null == rows[12]) ? null : rows[12].toString()));
				lrfHeatObj.setHeat_counter(Integer.parseInt((null == rows[13]) ? null : rows[13].toString()));
				lrfHeatObj.setHeat_id((null == rows[14]) ? null : rows[14].toString());
				lrfHeatObj.setTrns_sl_no(Integer.parseInt((null == rows[15]) ? null : rows[15].toString()));
				lrfHeatObj.setLrf_initial_temp((null == rows[16]) ? null : Double.parseDouble(rows[16].toString()));
				lrfHeatObj.setHeat_track_id((null == rows[17]) ? null : Integer.parseInt(rows[17].toString()));
				lrfHeatObj.setSteel_ladle_no((null == rows[18]) ? null : Integer.parseInt(rows[18].toString()));
				lrfHeatObj.setPurge_medium((null == rows[19]) ? null : Integer.parseInt(rows[19].toString()));
				lrfHeatObj.setProcess_control((null == rows[20]) ? null : Integer.parseInt(rows[20].toString()));
				// lrfHeatObj.setVessel_car_no((null == rows[21]) ? null: rows[21].toString());
				lrfHeatObj.setVessel_car_id((null == rows[21]) ? null : Integer.parseInt(rows[21].toString()));
				

				//				Session session = getNewSession();
				//
				//				try {
				//
				//					begin(session);
				//
				//					HeatPlanHdrDetails heatPlanHdr = (HeatPlanHdrDetails) session.get(
				//
				//							HeatStatusTrackingModel.class, lrfHeatObj.getHeat_plan_id());
				//
				//					lrfHeatObj.setHeatPlanModel(heatPlanHdr);
				//
				//				} catch (Exception e) {
				//
				//					e.printStackTrace();
				//
				//				} finally {
				//
				//					close(session);
				//
				//				}

				lrfHeatObj.setVessel_car_no((null == rows[21]) ? null : rows[21].toString());
				lrfHeatObj.setPlan_cut_length(Double.parseDouble((null== rows[22]) ? null :rows[22].toString()));

			}

		} catch (Exception e) {

			// TODO: handle exception

			logger.error(HeatProceeEventDaoImpl.class

					+ " error in getLrfHeatDtlsById........" + e);

			e.printStackTrace();

		}

		return lrfHeatObj;

	}

	@SuppressWarnings("rawtypes")

	@Override

	public List<LRFHeatConsumableModel> getArcAdditions(String lookup_code, Integer subUnitId) {

		logger.info(LRFProductionDaoImpl.class + " Inside getArcAdditions " + subUnitId);

		List<LRFHeatConsumableModel> additionsList = new ArrayList<LRFHeatConsumableModel>();

		Session session = getNewSession();

		LRFHeatConsumableModel obj;

		try {

			String hql = "select "

					/*
					 * +
					 * "(select hc.cons_sl_no   || '&&'  || hc.consumption_qty  || '&&' || TO_CHAR (hc.addition_date_time, 'DD/MM/YYYY HH:MI')  FROM LRFHeatArcingDetailsModel hd, LRFHeatConsumableModel hc "
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
			List ls = (List<LRFHeatConsumableModel>) getResultFromNormalQuery(hql);
			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new LRFHeatConsumableModel();

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

			logger.error("error in getArcAdditions........" + e);

			e.printStackTrace();

		} finally {

			close(session);

		}

		return additionsList;

	}

	@Override

	public List<LRFHeatConsumableModel> getArcAdditionsBySampleNo(

			Integer arc_sl_no, String heat_id, Integer heat_cnt) {

		logger.info(LRFProductionDaoImpl.class + " Inside getArcAdditionsBySampleNo ");

		List<LRFHeatConsumableModel> additionsList = new ArrayList<LRFHeatConsumableModel>();

		Session session = getNewSession();

		LRFHeatConsumableModel obj;

		try {

			String hql = "select (select hc.cons_sl_no   || '&&'  || hc.consumption_qty  || '&&' || TO_CHAR (hc.addition_date_time, 'DD/MM/YYYY HH:MI')  FROM LRFHeatArcingDetailsModel hd, LRFHeatConsumableModel hc "

					+ "WHERE hd.arc_sl_no = hc.arc_sl_no   AND hd.heat_id = '" + heat_id + "' AND hd.arc_sl_no ="
					+ arc_sl_no + " AND hc.material_id = pcon.material_id "

					+ " AND hd.heat_counter = 1) as col1, pcon.material_id, pcon.material_desc, uomlkp.lookup_value,pcon.material_type from LookupMasterModel pl,LookupMasterModel uomlkp, MtrlProcessConsumableMstrModel pcon, MaterialSubUnitMapMstrModel mu "

					+ " where uomlkp.lookup_id= pcon.uom and pl.lookup_code='LRF_ARC_ADDITIONS' and  uomlkp.lookup_id = pcon.uom and pl.lookup_id = pcon.material_type and pcon.material_id = mu.mtrl_id order by pcon.createdDateTime";

			List ls = (List<LRFHeatConsumableModel>) getResultFromNormalQuery(hql);

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new LRFHeatConsumableModel();

				String s[] = (null == rows[0] ? null
						: rows[0].toString()

						.split("&&"));

				if (s != null) {

					obj.setCons_sl_no(Integer.parseInt((null == s[0]) ? null

							: s[0].toString()));

					obj.setConsumption_qty(Double

							.parseDouble((null == s[1]) ? null : s[1].toString()));

					obj.setAddition_date_time(df.parse(s[2].toString()));

				} else {

					obj.setCons_sl_no(null);

					obj.setConsumption_qty(null);

					obj.setAddition_date_time(new Date());

				}

				obj.setMaterial_id(Integer.parseInt((null == rows[1]) ? null

						: rows[1].toString()));

				obj.setMaterial_name((null == rows[2]) ? null
						: rows[2]

								.toString());

				obj.setUom((null == rows[3]) ? null : rows[3].toString());

				obj.setMaterial_type(Integer.parseInt((null == rows[4]) ? null

						: rows[4].toString()));

				additionsList.add(obj);

			}

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getArcAdditions........" + e);

			e.printStackTrace();

		} finally {

			close(session);

		}

		return additionsList;

	}

	@Override

	public String getNextArcNo(String heat_id, Integer heat_counter) {

		// TODO Auto-generated method stub

		Session session = getNewSession();

		String nextArcNo = "";

		try {

			Query query = session.createQuery(
					"select coalesce((max (ad.arc_no) + 1),1) from LRFHeatArcingDetailsModel ad where  ad.heat_id ='"
							+ heat_id + "' and ad.heat_counter=1");

			List<?> results = query.list();

			Iterator<?> it = results.iterator();

			while (it.hasNext()) {

				nextArcNo = it.next().toString();

			}

		} catch (Exception e)

		{

			e.printStackTrace();

		}

		finally {

			logger.info("close calling ...getNextArcNo");

			close(session);

		}

		return nextArcNo;

	}

	@Override

	public LRFHeatConsumableModel getLRFHeatConsumablesById(Integer cons_sl_no) {

		// TODO Auto-generated method stub

		logger.info("inside .. getLRFHeatConsumablesById.....");

		LRFHeatConsumableModel list = new LRFHeatConsumableModel();

		Session session = getNewSession();

		try {

			list = (LRFHeatConsumableModel) session.get(LRFHeatConsumableModel.class, cons_sl_no);

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getLRFHeatConsumablesById........" + e);

		}

		finally {

			close(session);

		}

		return list;

	}

	@Override

	public String lrfArcAdditionsSaveOrUpdate(Hashtable<String, Object> mod_obj) {

		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		String result = "";

		Session session = getNewSession();

		try {

			begin(session);

			if ((LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD") != null)

			{

				LRFHeatArcingDetailsModel arcDet = (LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD");

				if (arcDet.getArc_sl_no() != null)

				{

					session.update((LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD"));

				} else {

					session.save((LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD"));

				}

			}

			if (Integer.parseInt(mod_obj.get("LRF_ARC_ADD_CONS_CNT").toString()) > 0)

			{

				LRFHeatArcingDetailsModel arcObj = (LRFHeatArcingDetailsModel) mod_obj.get("LRF_ARC_ADD");

				Integer cnt = Integer.parseInt(mod_obj.get("LRF_ARC_ADD_CONS_CNT").toString());

				String key = "LRF_ARC_ADD_CONS";

				for (int i = 0; i <= cnt; i++)

				{

					key = key + i;

					if ((LRFHeatConsumableModel) mod_obj.get(key) != null)

					{

						LRFHeatConsumableModel conObj = (LRFHeatConsumableModel) mod_obj.get(key);

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

			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		}

		catch (ConstraintViolationException e)

		{

			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		}

		catch (Exception e) {

			e.printStackTrace();

			rollback(session);

			logger.error(LRFProductionDaoImpl.class + " Inside lrfArcAdditionsSaveOrUpdate Exception..", e);

			result = Constants.SAVE_FAIL;

		} finally {

			close(session);

		}

		return result;

	}

	@Override

	public LRFHeatArcingDetailsModel getArcDetailsBySlno(Integer arc_sl_no) {

		logger.info("inside .. getArcDetailsBySlno.....");

		LRFHeatArcingDetailsModel list = new LRFHeatArcingDetailsModel();

		Session session = getNewSession();

		try {

			String query = "select a from LRFHeatArcingDetailsModel a where a.arc_sl_no= " + arc_sl_no;

			list = (LRFHeatArcingDetailsModel) session.createQuery(query).uniqueResult();

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

	/*
	 * public static void main(String args[]){
	 * 
	 * LRFProductionDao lrfProductionDao = new LRFProductionDaoImpl();
	 * 
	 * lrfProductionDao.getLRFArcAdditionsTemp();
	 * 
	 * //LRFProductionDaoImpl prod=new LRFProductionDaoImpl();
	 * 
	 * //prod.();
	 * 
	 * }
	 */

	@Override

	public List<Map<String, Object>> getLRFArcAdditionsTemp(String qry) {

		List<HashMap<String, String>> additionsList = new ArrayList<HashMap<String, String>>();

		Session session = getNewSession();

		LRFHeatConsumableModel obj;

		List<Map<String, Object>> aliasToValueMapList = null;

		try {

			/*
			 * String
			 * sqlQry=" SELECT a.BATH_SAMPLE_NO as sample_no, a.arcing_start_date_time as arc_start_dt, a.arcing_end_date_time as arc_end_dt,"
			 * 
			 * +
			 * " a.BATH_TEMPERATURE as bath_temp,a.POWER_CONSUMPTION as kwh,(SELECT b.consumption_qty FROM trns_lrf_heat_cons_lines b"
			 * 
			 * +
			 * " WHERE b.arcing_si_no = a.arc_si_no  AND b.material_id = (SELECT material_id   FROM mstr_process_consumables"
			 * 
			 * +
			 * " WHERE material_id = 1034)) AS fev,(SELECT b.consumption_qty FROM trns_lrf_heat_cons_lines b"
			 * 
			 * +
			 * " WHERE b.arcing_si_no = a.arc_si_no AND b.material_id = (SELECT material_id    FROM mstr_process_consumables"
			 * 
			 * +
			 * " WHERE material_id = 1035)) AS hcfemn,(SELECT b.consumption_qty FROM trns_lrf_heat_cons_lines b"
			 * 
			 * +
			 * " WHERE b.arcing_si_no = a.arc_si_no  AND b.material_id = (SELECT material_id  FROM mstr_process_consumables"
			 * 
			 * +
			 * " WHERE material_id = 1036)) AS lcfemn FROM salem_mes.trns_lrf_heat_arcing_dtls a WHERE a.heat_id = 'HEAT1' AND a.heat_counter = 1"
			 */;

			 /*
			  * String hql =
			  * "SELECT a.sample_no, a.bath_temp,a.arc_start_date_time, a.arc_end_date_time,a.power_consumption,"
			  * 
			  * +
			  * "(SELECT b.consumption_qty    FROM LRFHeatConsumableModel b      WHERE b.arc_sl_no = a.arc_sl_no     AND b.material_id = (SELECT material_id"
			  * 
			  * +
			  * " FROM MtrlProcessConsumableMstrModel    WHERE material_id = 1034)) AS FEV, (SELECT b.consumption_qty   FROM LRFHeatConsumableModel b "
			  * 
			  * +
			  * " WHERE b.arc_sl_no = a.arc_sl_no   AND b.material_id = (SELECT material_id     FROM MtrlProcessConsumableMstrModel     WHERE material_id = 1035)) AS HCFEMN,"
			  * 
			  * +
			  * " (SELECT b.consumption_qty  FROM LRFHeatConsumableModel b     WHERE b.arc_sl_no = a.arc_sl_no   AND b.material_id = (SELECT material_id "
			  * 
			  * +
			  * " FROM MtrlProcessConsumableMstrModel   WHERE material_id = 1036)) AS LCFEMN FROM LRFHeatArcingDetailsModel a  WHERE a.heat_id = 'HEAT1' AND a.heat_counter = 1"
			  * ;
			  */

			 /*
			  * List<LRFHeatConsumableModel> listObj;
			  * 
			  * HashMap<String, String> hm;
			  */

			 Query query = session.createSQLQuery(qry);

			 query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

			 aliasToValueMapList = query.list();

			 /*
			  * List ls = getResultFromNormalQuery(hql);
			  * 
			  * Iterator it = ls.iterator();
			  * 
			  * 
			  * 
			  * while (it.hasNext()) {
			  * 
			  * Object rows[] = (Object[]) it.next();
			  * 
			  * hm = new HashMap<String, String>();
			  * 
			  * //hm.put("SI_NO", (null == rows[0) ? null : rows[6].toString());
			  * 
			  * hm.put("SAMPLE_NO", (null == rows[0]) ? null : rows[0].toString());
			  * 
			  * hm.put("BATH_TEMP", (null == rows[1]) ? null : rows[1].toString());
			  * 
			  * hm.put("ARC_START_DT", (null == rows[2]) ? null : rows[2].toString());
			  * 
			  * hm.put("ARC_END_DT", (null == rows[3]) ? null : rows[3].toString());
			  * 
			  * hm.put("KWH", (null == rows[4]) ? null : rows[4].toString());
			  * 
			  * 
			  * 
			  * hm.put("FEV", (null == rows[5]) ? null : rows[5].toString());
			  * 
			  * hm.put("HCFEMN", (null == rows[6]) ? null : rows[6].toString());
			  * 
			  * hm.put("LCFEMN", (null == rows[7]) ? null : rows[7].toString());
			  * 
			  * additionsList.add(hm);
			  */

			 // hm.put("", value)

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getArcAdditions........" + e);

			e.printStackTrace();

		} finally {

			close(session);

		}

		return aliasToValueMapList;

	}

	@SuppressWarnings("unchecked")

	@Override

	public List<LRFHeatArcingDetailsModel> getArcDetailsByHeatId(

			String heat_id, Integer heat_cnt) {

		// TODO Auto-generated method stub

		logger.info("inside .. getArcDetailsByHeatId.....");

		List<LRFHeatArcingDetailsModel> list = new ArrayList<LRFHeatArcingDetailsModel>();

		Session session = getNewSession();

		try {

			String query = "select a from LRFHeatArcingDetailsModel a where a.heat_id= '" + heat_id
					+ "' and a.heat_counter=" + heat_cnt + " order by a.arc_sl_no desc";

			list = (List<LRFHeatArcingDetailsModel>) session.createQuery(query).list();

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

	public Map<Integer, LRFHeatConsumableModel> getArcAdditionDetailsByArcSiHeat(

			String heat_id, Integer heat_cnt, Integer arc_sl_no) {

		// TODO Auto-generated method stub

		logger.info(LRFProductionDaoImpl.class + " Inside getArcAdditionDetailsByArcSiHeat ");

		Map<Integer, LRFHeatConsumableModel> additionsList = new HashMap<Integer, LRFHeatConsumableModel>();

		Session session = getNewSession();

		LRFHeatConsumableModel obj;

		try {

			String hql = "select a.material_id,a.consumption_qty from LRFHeatConsumableModel a where a.heat_id= '"
					+ heat_id + "' and a.heat_counter=" + heat_cnt + " and arc_sl_no=" + arc_sl_no + "";

			List ls = (List<LRFHeatConsumableModel>) session.createQuery(hql).list();

			// DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new LRFHeatConsumableModel();

				obj.setMaterial_id(Integer.parseInt((null == rows[0]) ? null

						: rows[0].toString()));

				obj.setConsumption_qty(Double.parseDouble((null == rows[1]) ? null

						: rows[1].toString()));

				additionsList.put(Integer.parseInt(rows[0].toString()), obj);

			}

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getArcAdditions........" + e);

			e.printStackTrace();

		} finally {

			close(session);

		}

		return additionsList;

	}

	@Override

	public LRFHeatDetailsModel getLRFHeatObject(Integer id) {

		// TODO Auto-generated method stub

		LRFHeatDetailsModel obj = null;

		Session session = getNewSession();

		try {

			begin(session);

			obj = (LRFHeatDetailsModel) session.get(

					LRFHeatDetailsModel.class, id);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			close(session);

		}

		return obj;

	}

	@Override

	public List<LRFHeatDetailsModel> getHeatsForVDProcess(String cunit,

			String pstatus) {

		logger.info("inside .. getHeatsForVDProcess.....");

		List<LRFHeatDetailsModel> list = new ArrayList<LRFHeatDetailsModel>();

		LRFHeatDetailsModel obj;

		try {

			String hql = "select a.heat_id AS heatid, (select steel_ladle_no from  SteelLadleMasterModel where steel_ladle_si_no=a.steel_ladle_no) ,a.lrf_dispatch_temp,a.steel_wgt,(select lm.lookup_value from LookupMasterModel lm where lm.lookup_type='CASTER_TYPE' and lm.lookup_id=d.caster_type) as casterType,"

					+ "(select sub_unit_name from  SubUnitMasterModel sud  where sud.sub_unit_id = a.sub_unit_id), (select psn_no from PSNHdrMasterModel where psn_hdr_sl_no = a.aim_psn),a.heat_counter,b.act_proc_path,a.steel_ladle_no,a.aim_psn,b.heat_track_id,a.heat_plan_id,a.heat_plan_line_no,a.target_caster_id,(select st.st_ladle_track_id from SteelLadleTrackingModel st where st.st_ladle_si_no = a.steel_ladle_no) AS ladle_track_id from LRFHeatDetailsModel a ,HeatStatusTrackingModel b,HeatPlanDetails c,HeatPlanHdrDetails d where"

					+ " a.heat_id =b.heat_id and a.heat_counter=b.heat_counter and b.current_unit like '" + cunit + "%" // b.current_unit='"+cunit

					+ "' and b.unit_process_status='" + pstatus
					+ "' and a.heat_plan_line_no=c.heat_plan_dtl_id and d.heat_plan_id=c.heat_plan_id";

			List ls = (List<LRFHeatDetailsModel>) getResultFromNormalQuery(hql);

			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new LRFHeatDetailsModel();

				obj.setHeat_id((null == rows[0]) ? null : rows[0].toString());

				obj.setSteel_ladle_name((null == rows[1]) ? null : rows[1].toString());

				obj.setLrf_dispatch_temp((null == rows[2]) ? null : Double.parseDouble(rows[2].toString()));

				obj.setSteel_wgt((null == rows[3]) ? null : Double.parseDouble(rows[3].toString()));

				obj.setTarget_caster_name((null == rows[4]) ? null : rows[4].toString());

				obj.setPrev_unit((null == rows[5]) ? null : rows[5].toString());

				obj.setAim_psn_char((null == rows[6]) ? null : rows[6].toString());

				obj.setHeat_counter((null == rows[7]) ? null : Integer.parseInt(rows[7].toString()));

				obj.setAct_proc_path((null == rows[8]) ? null : rows[8].toString());

				obj.setSteel_ladle_no((null == rows[9]) ? null : Integer.parseInt(rows[9].toString()));

				obj.setAim_psn((null == rows[10]) ? null : Integer.parseInt(rows[10].toString()));

				obj.setHeat_track_id((null == rows[11]) ? null : Integer.parseInt(rows[11].toString()));

				obj.setHeat_plan_id((null == rows[12]) ? null : Integer.parseInt(rows[12].toString()));

				obj.setHeat_plan_line_no((null == rows[13]) ? null : Integer.parseInt(rows[13].toString()));

				obj.setTarget_caster_id((null == rows[14]) ? null : Integer.parseInt(rows[14].toString()));

				obj.setSteel_ladle_no((null == rows[15]) ? null : Integer.parseInt(rows[15].toString()));

				list.add(obj);

			}

		} catch (Exception e) {

			// TODO: handle exception

			e.printStackTrace();

			logger.error("error in getLRFHeatDetailsByStatus........" + e);

		}

		return list;

	}

	@Override

	public List<LRFHeatDetailsModel> getLrfChemDetails(String heat_id, Integer heat_counter) {
		// TODO Auto-generated method stub
		List<LRFHeatDetailsModel> retlist = new ArrayList<LRFHeatDetailsModel>();
		try {
			String hql = "SELECT ch.ELEMENT, ch.aim_value, lkp.lookup_code FROM trns_heat_chemistry_child ch, app_lookups lkp WHERE lkp.lookup_type = 'ELEMENT' AND "
					+ " lkp.lookup_status = 1  AND ch.ELEMENT = lkp.lookup_id  AND ch.sample_si_no = (SELECT max(chd.sample_si_no) FROM "
					+ " trns_heat_chemistry_hdr chd  WHERE chd.heat_id = '" + heat_id + "' and chd.heat_counter="
					+ heat_counter + " AND chd.analysis_type = "
					+ " (SELECT lookup_id  FROM app_lookups lk1 WHERE lk1.lookup_value = 'LRF_LIFT_CHEM' AND lk1.lookup_type = 'CHEM_LEVEL') and chd.final_result = 1)";
			List<?> ls = getResultFromCustomQuery(hql);
			LRFHeatDetailsModel cb;
			Iterator<?> it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				cb = new LRFHeatDetailsModel();
				cb.setElement_id(Integer.parseInt((null == rows[0]) ? null : rows[0].toString()));
				cb.setElement_aim_value(Double.parseDouble((null == rows[1]) ? null : rows[1].toString()));
				cb.setChem_element_name((null == rows[2]) ? null : rows[2].toString());
				if (cb.getElement_id() != null)
					retlist.add(cb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retlist;
	}

	@Override
	public List<LRFHeatDetailsModel> getHeatsForCasterProcess(String cunit, String pstatus) {

		logger.info("inside .. getHeatsForCasterProcess.....");

		List<LRFHeatDetailsModel> list = new ArrayList<LRFHeatDetailsModel>();

		LRFHeatDetailsModel obj;

		try {

			String hql = " select (select steel_ladle_no from  SteelLadleMasterModel where steel_ladle_si_no=a.steel_ladle_no),"

					+ " a.heat_id AS heatid, "

					+ " (select p.psn_no from PSNHdrMasterModel p where  p.psn_hdr_sl_no = a.aim_psn),"

					+ " (select lm.lookup_value from LookupMasterModel lm where lm.lookup_type='CASTER_TYPE' and lm.lookup_id=d.caster_type) as casterType,"

					+ " a.lrf_dispatch_temp,"

					+ " (select sub_unit_name from  SubUnitMasterModel sud  where sud.sub_unit_id = a.sub_unit_id),"

					+ " TO_CHAR (a.lrf_dispatch_date, 'DD/MM/YYYY HH:MI:SS'), "

					+ " a.heat_plan_id," //

					+ " a.heat_plan_line_no,a.steel_ladle_no,a.steel_wgt,a.aim_psn, a.heat_counter"

					+ " from  LRFHeatDetailsModel a ,HeatStatusTrackingModel b,HeatPlanDetails c,HeatPlanHdrDetails d where"

					+ " a.heat_id =b.heat_id and a.heat_counter=b.heat_counter and b.current_unit like '%" + cunit
					+ "%' and b.unit_process_status='" + pstatus
					+ "' and a.heat_plan_line_no=c.heat_plan_dtl_id and d.heat_plan_id=c.heat_plan_id "; //

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			List ls = (List<LRFHeatDetailsModel>) getResultFromNormalQuery(hql);

			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new LRFHeatDetailsModel();

				obj.setSteel_ladle_name((null == rows[0]) ? null : rows[0].toString());

				obj.setHeat_id((null == rows[1]) ? null : rows[1].toString());

				obj.setAim_psn_char((null == rows[2]) ? null : rows[2].toString());

				obj.setTarget_caster_name((null == rows[3]) ? null : rows[3].toString());

				obj.setLrf_dispatch_temp((null == rows[4]) ? null : Double.parseDouble(rows[4].toString()));

				obj.setPrev_unit((null == rows[5]) ? null : rows[5].toString());

				obj.setLrf_dispatch_date(df.parse(rows[6].toString()));

				obj.setHeat_plan_id((null == rows[7]) ? null : Integer.parseInt(rows[7].toString()));

				obj.setHeat_plan_line_no((null == rows[8]) ? null : Integer.parseInt(rows[8].toString()));

				obj.setSteel_ladle_no((null == rows[9]) ? null : Integer.parseInt(rows[9].toString()));

				obj.setSteel_wgt((null == rows[10]) ? null : Double.parseDouble(rows[10].toString()));

				obj.setHeatPlanModel(heatPlanDtlsservice.getHeatPlanHeaderDetailsById(obj.getHeat_plan_id()));

				obj.setAim_psn(null == rows[11] ? null : Integer.parseInt(rows[11].toString()));
				obj.setHeat_counter(null == rows[12] ? null : Integer.parseInt(rows[12].toString()));

				List<PsnGradeMasterModel> grades = psnGradeDao.getPSNGradeByPsnNo(obj.getAim_psn());
				if (!grades.isEmpty()) {
					obj.setPsn_grade(grades.get(0).getPsn_grade());
				}
				obj.setCcm_section_size(getCCMSecSize((null == rows[7]) ? null : Integer.parseInt(rows[7].toString())));

				/*
				 * 
				 * obj.setHeat_counter((null==rows[7])?null:Integer.parseInt(rows[7].toString())
				 * );
				 * 
				 * obj.setAct_proc_path((null==rows[8])?null:rows[8].toString());
				 * 
				 * obj.setSteel_ladle_no((null==rows[9])?null:Integer.parseInt(rows[9].toString(
				 * )));
				 * 
				 * obj.setAim_psn((null==rows[10])?null:Integer.parseInt(rows[10].toString()));
				 * 
				 * obj.setHeat_track_id((null==rows[11])?null:Integer.parseInt(rows[11].toString
				 * ()));
				 * 
				 * 
				 * 
				 * obj.setTarget_caster_id((null==rows[14])?null:Integer.parseInt(rows[14].
				 * toString()));
				 */

				list.add(obj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("error in getHeatsForCasterProcess........" + e);
		}

		return list;
	}

	@Override

	public LRFHeatDetailsModel getLrfPreviousHeatDetailsByHeatNo(Integer trans_si_no) {

		// TODO Auto-generated method stub

		LRFHeatDetailsModel lrfHeatDetails = new LRFHeatDetailsModel();

		Session session = getNewSession();

		try {

			String hql = "select a from LRFHeatDetailsModel a where a.trns_sl_no < (select b.trns_sl_no from LRFHeatDetailsModel b where b.trns_sl_no ="
					+ trans_si_no + ") and rownum <= 1 order by a.trns_sl_no desc";

			Query query = session.createQuery(hql);

			lrfHeatDetails = (LRFHeatDetailsModel) query.uniqueResult();

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getLrfPreviousHeatDetailsByHeatNo........" + e);

			e.printStackTrace();

		}

		finally {

			close(session);

		}

		return lrfHeatDetails;

	}

	@Override

	public LRFHeatDetailsModel getLrfHeatDetailsById(Integer trans_si_no) {

		// TODO Auto-generated method stub

		LRFHeatDetailsModel lrfHeatDetails = new LRFHeatDetailsModel();

		Session session = getNewSession();

		try {

			String hql = "select a from LRFHeatDetailsModel a where a.trns_sl_no=" + trans_si_no;

			Query query = session.createQuery(hql);

			lrfHeatDetails = (LRFHeatDetailsModel) query.uniqueResult();

		} catch (Exception e) {

			// TODO: handle exception

			logger.error("error in getLrfHeatDetailsById........" + e);

			e.printStackTrace();

		}

		finally {

			close(session);

		}

		return lrfHeatDetails;

	}

	@Override

	public LRFHeatDetailsModel getLRFHeatDetailsByHeatNo(String heatNo, Integer heat_counter) {

		// TODO Auto-generated method stub

		logger.info("inside .. getLRFHeatDetailsByHeatNo....." + EofProductionDaoImpl.class);

		LRFHeatDetailsModel obj = new LRFHeatDetailsModel();

		Session session = getNewSession();

		try {

			String hql = "select a from LRFHeatDetailsModel a where heat_id='" + heatNo + "'and heat_counter='"
					+ heat_counter + "' order by a.trns_sl_no desc";

			List<?> ls = session.createQuery(hql).list();

			if (ls.size() > 0)

				obj = (LRFHeatDetailsModel) ls.get(0);

		} catch (IndexOutOfBoundsException e) {

			// TODO: handle exception

			obj = null;

			logger.error("error in getLRFHeatDetailsByHeatNo........" + e);

			// e.printStackTrace();

		}

		finally {

			close(session);

		}

		return obj;

	}

	@Override
	public String updateLRFHeatDetPSN(LRFHeatDetailsModel lrfHeatObj, LRFHeatDetailsPsnBkpModel lrfHeatPsnBkp,
			List<HeatPlanHdrDetails> heatPlanHdrLi, List<HeatPlanDetails> heatPlanDetLi,
			HeatStatusTrackingModel heatTrackObj,
			IfacesmsLpDetailsModel ifaceObj) {
		// TODO Auto-generated method stub

		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			session.update(lrfHeatObj);
			session.save(lrfHeatPsnBkp);
			for (HeatPlanHdrDetails hdrObj : heatPlanHdrLi) {
				if (hdrObj != null) {
					hdrObj.setHeatPlanDtls(null);
					session.update(hdrObj);
				}
			}
			for (HeatPlanDetails detObj : heatPlanDetLi) {
				session.update(detObj);
			}
			session.update(heatTrackObj);
			session.save(ifaceObj);
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(LRFProductionDaoImpl.class + " Inside LRFProductionDaoImpl updateLRFHeatDetPSN Exception..",
					e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}
		return result;

	}

	@Override

	public List<LRFHeatDetailsModel> getHeatsForLadleMix(String cunit,

			String pstatus) {

		// TODO Auto-generated method stub

		logger.info("inside .. getHeatsForLadleMix.....");

		List<LRFHeatDetailsModel> list = new ArrayList<LRFHeatDetailsModel>();

		LRFHeatDetailsModel obj;

		try {

			String hql = "select lhd.trns_si_no, htr.heat_tarck_id, htr.heat_id, htr.current_unit, lhd.prev_unit," +

					" ph.psn_no, lhd.heat_plan_id, (select al.lookup_value from app_lookups al  where al.lookup_type = 'CASTER_TYPE' "
					+

					" and al.lookup_id = lhd.target_caster) AS caster_type, (select ss.steel_ladle_no  from mstr_steel_ladle ss "
					+

					" where ss.steel_ladle_si_no = lhd.steel_ladle_no) AS ladle_no, lhd.steel_wt from trns_lrf_heat_dtls lhd, "
					+

					" trns_heat_tracking_status htr, mstr_psn_hdr ph, trns_heat_plan_header hph where htr.heat_id = lhd.heat_id "
					+

					" and lhd.aim_psn  = ph.psn_hdr_sl_no and lhd.heat_plan_id = hph.heat_plan_id and lhd.dispatch_date is null "
					+

					" and htr.current_unit = '" + cunit + "' and htr.unit_process_status = '" + pstatus
					+ "' and htr.eof_status = 'DISPATCHED' " +

					" and htr.vd_status is null and htr.blm_cas_status is null and htr.blt_cas_status is null";

			List ls = (List<LRFHeatDetailsModel>) getResultFromCustomQuery(hql);

			Iterator it = ls.iterator();

			while (it.hasNext()) {

				Object rows[] = (Object[]) it.next();

				obj = new LRFHeatDetailsModel();

				obj.setTrns_sl_no((null == rows[0]) ? null : Integer.parseInt(rows[0].toString()));

				obj.setHeat_track_id((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));

				obj.setHeat_id((null == rows[2]) ? null : rows[2].toString());

				obj.setSub_unit_name((null == rows[3]) ? null : rows[3].toString());

				obj.setPrev_unit((null == rows[4]) ? null : rows[4].toString());

				obj.setAim_psn_char((null == rows[5]) ? null : rows[5].toString());

				obj.setHeat_plan_id((null == rows[6]) ? null : Integer.parseInt(rows[6].toString()));

				obj.setTarget_caster_name((null == rows[7]) ? null : rows[7].toString());

				obj.setSteel_ladle_name((null == rows[8]) ? null : rows[8].toString());

				obj.setSteel_wgt((null == rows[9]) ? null : Double.parseDouble(rows[9].toString()));

				list.add(obj);

			}

		} catch (Exception e) {

			// TODO: handle exception

			e.printStackTrace();

			logger.error("error in getHeatsForLadleMix........" + e);

		}

		return list;

	}

	@Override

	public String updateLRFHeatDetForLadleMix(LRFHeatDetailsModel lrfHeatObj1,

			LRFHeatDetailsModel lrfHeatObj2,

			HeatStatusTrackingModel heatTrackObj1,

			HeatStatusTrackingModel heatTrackObj2) {

		// TODO Auto-generated method stub

		String result = "";

		Session session = getNewSession();

		try {

			begin(session);

			session.update(lrfHeatObj1);

			session.update(lrfHeatObj2);

			session.update(heatTrackObj1);

			session.update(heatTrackObj2);

			commit(session);

			result = Constants.SAVE;

		} catch (DataIntegrityViolationException e) {

			logger.error(LRFProductionDaoImpl.class

					+ " Inside DataIntegrityViolationException() Exception..");

			result = Constants.DATA_EXIST;

			rollback(session);

		} catch (Exception e) {

			e.printStackTrace();

			rollback(session);

			logger.error(LRFProductionDaoImpl.class

					+ " Inside LRFProductionDaoImpl updateLRFHeatDetForLadleMix Exception..", e);

			result = Constants.SAVE_FAIL;

		} finally {

			close(session);

		}

		return result;

	}

	@Override

	public List<LRFHeatLogRpt> getLrfHeatLogs(String heatId, String LogType) {

		// TODO Auto-generated method stub

		List<LRFHeatLogRpt> retlist = new ArrayList<LRFHeatLogRpt>();

		Session session = getNewSession();

		try {

			String sql = "";

			if (LogType.equals("LRFHeatDtls")) {

				sql = "SELECT pc.material_desc, round(sum(nvl(a.qty,0)),3) as qty FROM trns_eof_heat_cons_materials a, trns_eof_heat_dtls b, "

						+ "mstr_process_consumables pc, trns_eof_heat_cons_scrap_hm ehcs, app_lookups lu "

						+ "WHERE a.trns_eof_si_no = b.trns_si_no AND a.material_id = pc.material_id AND ehcs.trns_eof_si_no = a.trns_eof_si_no "

						+ "AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'FURNACE_ADDITIONS' "

						+ "AND b.heat_id = '" + heatId + "' AND (ehcs.hm_seq_no IS NOT NULL) group by pc.material_desc";

			} else if (LogType.equals("LRFLadleLife")) {

				sql = "select 'LRFLadleLife',nvl(steel_ladle_life,0) from trns_stladle_status_tracking where trns_stladle_track_id in(select max(trns_stladle_track_id)"

						+ " from trns_lrf_heat_dtls a,trns_stladle_status_tracking b where a.heat_id = '" + heatId
						+ "' and a.steel_ladle_no = b.steel_ladle_si_no)";

			} else if (LogType.equals("LRFRinsingTime")) {

				sql = " Select 'Rinsing Time',max(data_value) as data_value from( "
						+ " Select 'Rinsing Time',(round(nvl((end_rinse.date_end - start_rinse.date_start),0)*24*60)) as data_value from "
						+ "			 (Select a.EVENT_DATE_TIME date_start from TRNS_HEAT_PROCESS_EVENTS a,TRNS_LRF_HEAT_DTLS b,mstr_event c,MSTR_SUB_UNIT_DETAILS d "
						+ "						  where b.heat_id = '" + heatId
						+ "'  and a.HEAT_ID = b.heat_id and c.EVENT_SI_NO = a.EVENT_ID and d.SUB_UNIT_ID = c.sub_unit_id and "
						+ "						  b.SUB_UNIT_ID = c.sub_unit_id and event_desc in('POWER_OFF_AT') and "
						+ "                          b.heat_counter in(select max(heat_counter) from trns_lrf_heat_dtls where heat_id =  '"
						+ heatId + "' )) start_rinse, "
						+ "						  (Select a.EVENT_DATE_TIME date_end from TRNS_HEAT_PROCESS_EVENTS a,TRNS_LRF_HEAT_DTLS b,mstr_event c,MSTR_SUB_UNIT_DETAILS d "
						+ "						  where b.heat_id = '" + heatId
						+ "'  and a.HEAT_ID = b.heat_id and c.EVENT_SI_NO = a.EVENT_ID and d.SUB_UNIT_ID = c.sub_unit_id and "
						+ "						  b.SUB_UNIT_ID = c.sub_unit_id and event_desc in('PURGING_STOP') and "
						+ "                          b.heat_counter in(select max(heat_counter) from trns_lrf_heat_dtls where heat_id =  '"
						+ heatId + "' )) end_rinse " + " Union select  'Rinsing Time',0 as data_value from dual) ";

			} else if (LogType.equals("VDHoldingTime")) {

				sql = " Select 'Holding_time',round(max(data_value)) as data_value from( "
						+ " Select 'Holding_time',nvl(((a.event_dt - b.event_dt)*24*60),0) as data_value from "
						+ " (Select 'Holding_time' as param_type,'VD_END_TIME' event_desc,event_date_time event_dt from  trns_vd_heat_dtls vd_hdr,mstr_event "
						+ " mstr_eve,trns_heat_process_events proc_eve where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('VD_END_TIME')"
						+ " and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))) a, "
						+ " (Select 'Holding_time' as param_type,'PUMP_HOLDING_TIME' event_desc,event_date_time event_dt from  trns_vd_heat_dtls vd_hdr,mstr_event mstr_eve,trns_heat_process_events proc_eve "
						+ " where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('PUMP_HOLDING_TIME')"
						+ "and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))) b " + " where a.param_type = b.param_type " + " union "
						+ " Select 'Holding_time',0 as data_value from dual) ";

			}else if (LogType.equals("VDFlowRate")) {

				sql = " Select 'Flow rate ' as flow_rate,max(data_value) data_value from (Select 'Flow rate ' as flow_rate,nvl(PARAM_VALUE_AIM,0) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_VD_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "

						+ " where b.heat_id =  '" + heatId
						+ "'  and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id  "

						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Flow_Rate(614)') "

						+ " UNION "

						+ " Select 'Flow rate ' as flow_rate,0 as data_value from dual)";}
			else if (LogType.equals("VDArgonFlowRate")) {

				sql = " Select 'Argon Flow rate ' as argon_flow_rate,max(data_value) data_value from (Select 'Argon Flow rate' as argon_flow_rate,nvl(PARAM_VALUE_AIM,0) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_VD_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "

							+ " where b.heat_id =  '" + heatId
							+ "'  and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id  "

							+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Argon_Flow_Rate_During_Flotation') "

							+ " UNION "

							+ " Select 'Argon Flow rate ' as argon_flow_rate,0 as data_value from dual)";}

			else if (LogType.equals("VDLiftingTime")) {

				sql = " Select 'PURGING_STOP' as param_type,to_char(event_date_time,'dd/mm/yy hh:mi')  from  trns_vd_heat_dtls vd_hdr,mstr_event mstr_eve,trns_heat_process_events proc_eve "
						+ " where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('PURGING_STOP')"
						+ " and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))" ;}

			else if (LogType.equals("VDFreeBoardLayer")) {

				sql = " Select 'FreeBoardLayer' as col,max(data_value) data_value from( "
						+ " Select 'FreeBoardLayer' as col,(nvl(PARAM_VALUE_AIM,0)) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "'   and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id   "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Free_Board_Layers') "
						+ " and b.heat_counter in ( select max (heat_counter) from trns_vd_heat_dtls  where heat_id in ('"
						+ heatId + "' ))" + " union Select 'FreeBoardLayer',0 as data_value from dual) ";

			}else if (LogType.equals("PorousPlugLife")) {
				sql = " Select 'Porous Plug Life' as col,max(data_value) data_value from( "
						+ " Select 'Porous Plug Life' as col,(nvl(PARAM_VALUE_AIM,0)) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "'   and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id   "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Porous Plug Life') "
						+ " and b.heat_counter in ( select max (heat_counter) from trns_lrf_heat_dtls  where heat_id in ('"
						+ heatId + "' ))" + " union Select 'Porous Plug Life',0 as data_value from dual) ";
			}
			else if (LogType.equals("FlotationTime")) {
				sql = " Select 'Flotation Time' as col,max(data_value) data_value from( "
						+ " Select 'Flotation Time' as col,(nvl(PARAM_VALUE_AIM,0)) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "'   and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id   "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Flotation Time') "
						+ " and b.heat_counter in ( select max (heat_counter) from trns_lrf_heat_dtls  where heat_id in ('"
						+ heatId + "' ))" + " union Select 'Floatation Time',0 as data_value from dual) ";
			}
			else if (LogType.equals("PlateLife")) {
				sql = " Select 'Plate Life' as col,max(data_value) data_value from( "
						+ " Select 'Plate Life' as col,(nvl(PARAM_VALUE_AIM,0)) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "'   and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id   "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Plate Life') "
						+ " and b.heat_counter in ( select max (heat_counter) from trns_lrf_heat_dtls  where heat_id in ('"
						+ heatId + "' ))" + " union Select 'Plate Life',0 as data_value from dual) ";
			}
			else if (LogType.equals("A2N2Pressure")) {

				sql = " Select 'A2N2Pressure' as col,max(data_value) as data_value from ( "
						+ " Select 'A2N2Pressure' as col,nvl(PARAM_VALUE_AIM,0) as data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "'  and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id  "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Flow_Rate(Nm3/Hr)')"
						+ " Union Select 'A2N2Pressure',0 as data_value from dual)";

			} else if (LogType.equals("A2N2FlowRateBVD")) {

				sql = " Select 'A2N2FlowRateBVD' as col,max(data_value) as data_value from ( "
						+ " Select 'A2N2FlowRateBVD' as col,nvl(PARAM_VALUE_AIM,0) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "' and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id  "
						+ " and a.heat_counter in(Select max(vd.heat_counter) from TRNS_VD_HEAT_DTLS vd where heat_id = '"
						+ heatId + "' )" + " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Argon Flow')"
						+ " Union Select 'A2N2FlowRateBVD',0 as data_value from dual)";
			} else if (LogType.equals("A2N2FlowRateAVD")) {
				sql = " Select 'A2N2FlowRateAVD' as col,max(data_value) as data_value from ( "
						+ " Select 'A2N2FlowRateAVD' as col,nvl(PARAM_VALUE_AIM,0) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "' and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id  "
						+ " and a.heat_counter in(Select max(vd.heat_counter)+1 from TRNS_VD_HEAT_DTLS vd where heat_id = '"
						+ heatId + "' )" + " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Argon Flow')"
						+ " Union Select 'A2N2FlowRateBVD',0 as data_value from dual)";
			}

			else if (LogType.equals("LRFPowerConsm")) {

				sql = " Select 'LRFPowerConsm' as col,max(data_value) data_value from (Select 'LRFPowerConsm' as col,round(max(nvl(POWER_CONSUMPTION,0)),3) data_value from TRNS_LRF_HEAT_DTLS a,TRNS_LRF_HEAT_ARCING_DTLS b "

						+ " where b.heat_id =  '" + heatId
						+ "' and a.heat_id = b.heat_id  and a.heat_counter in(select max(heat_counter) from trns_lrf_heat_dtls where heat_id =  '"
						+ heatId + "')"

						+ " Union Select 'LRFPowerConsm' as col,0 as data_value from dual)";

			}

			else if (LogType.equals("LRFA2N2Consm")) {

				sql = " Select 'Total Ar_consumption' as col,max(data_value) data_value from( "
						+ " Select 'Total Ar_consumption' as col,((nvl(PARAM_VALUE_AIM,0))) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "'   and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id   "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Total Ar_Consumption') "
						+ " and b.heat_counter in ( select max (heat_counter) from trns_vd_heat_dtls  where heat_id in ('"
						+ heatId + "' ))" + " union Select 'Total Ar_consumption',0 as data_value from dual) ";
				/*
				 * " Select 'LRFA2N2Consm',max(data_value) data_value from( "
				 * +" Select 'LRFA2N2Consm' as col,nvl(CONSUMPTION_QTY,0) as data_value from TRNS_LRF_HEAT_CONS_LINES a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_CONSUMABLES c "
				 * +" where b.heat_id =  '" + heatId +
				 * "'  and b.heat_counter in(select max(heat_counter) from trns_lrf_heat_dtls where heat_id =  '"
				 * + heatId + "' ) "
				 * +" and a.HEAT_ID = b.heat_id and c.MATERIAL_ID = a.MATERIAL_ID and material_desc in('Ar_Consumption') "
				 * +" union " +" Select 'LRFA2N2Consm' as col,0 as data_value from dual) ";
				 */
			}

			else if (LogType.equals("LRFIncharges")) {
				sql = " Select 'LRFShiftIncharge',a.user_name "
						+ " from app_user_account_details a,mstr_user_role_mapping b,trns_heat_crew_details shift,app_lookups lov "
						+ " where a.app_user_id = b.app_user_id and shift.user_role_id = b.user_role_id and lov.lookup_value = 'LRF_SECTION_INCHARGE' "
						+ " and lov.lookup_id = b.lookup_id and shift.heat_id = '" + heatId
						+ "' and heat_counter in(Select max(heat_counter) from trns_heat_crew_details where heat_id = '"
						+ heatId + "') " + " UNION " + " Select 'LRFSrShiftIncharge',a.user_name "
						+ " from app_user_account_details a,mstr_user_role_mapping b,trns_heat_crew_details shift,app_lookups lov "
						+ " where a.app_user_id = b.app_user_id and shift.user_role_id = b.user_role_id "
						+ " and lov.lookup_value = 'LRF SHIFT INCHARGE' and lov.lookup_id = b.lookup_id and shift.heat_id = '"
						+ heatId + "' "
						+ " and heat_counter in(Select max(heat_counter) from trns_heat_crew_details where heat_id = '"
						+ heatId + "') " + " UNION " + " Select 'LRFTargetCCM',b.lookup_value "
						+ " from TRNS_LRF_HEAT_DTLS a,app_lookups b " + " where heat_id = '" + heatId
						+ "' and a.TARGET_CASTER = b.lookup_id "
						+ " and heat_counter in(Select max(heat_counter) from trns_heat_crew_details where heat_id = '"
						+ heatId + "') ";
			} else if (LogType.equals("LRFArcingTime")) {

				sql = " Select 'LRFArcingTime',sum(data_value) as data_value from( "

						+ " Select 'LRFArcingTime',arcing_end_date_time,arcing_start_date_time,round((nvl(arcing_end_date_time - arcing_start_date_time,0)*24*60),0) as data_value "

						+ " from trns_lrf_heat_dtls lrf_hdr,app_lookups matl_type,app_lookups uom,app_lookups vd_name,mstr_process_consumables matls,mstr_mtrl_unit_map subunit_matl_map, "

						+ " (Select distinct heat_id,heat_counter,arc_si_no, arcing_start_date_time,arcing_end_date_time, power_consumption, bath_temperature, addition_type from trns_lrf_heat_arcing_dtls arc_hdr "

						+ " where arc_hdr.heat_id = '" + heatId + "') arc_hdr "

						+ " where   uom.LOOKUP_ID = matls.UOM and matl_type.LOOKUP_CODE ='LRF_ARC_ADDITIONS' and vd_name.lookup_type = 'LRF_ADDITION_TYPE' and arc_hdr.addition_type = vd_name.lookup_id "

						+ " and lrf_hdr.sub_unit_id = subunit_matl_map.sub_unit_id and uom.lookup_id = matls.uom "

						+ " and matl_type.lookup_id = matls.material_type and matls.material_id = subunit_matl_map.mtrl_id "

						+ " and arc_hdr.heat_id = lrf_hdr.heat_id and arc_hdr.heat_counter = lrf_hdr.heat_counter and arcing_start_date_time is not null "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and get_matl_act_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,matls.material_type,matls.material_id) > 0 "

						+ " group by 'LRFArcingTime',arcing_end_date_time,arcing_start_date_time "

						+ " union "

						+ " Select 'LRFArcingTime',sysdate,sysdate,0 as data_value from dual) ";

			}

			else if (LogType.equals("LRFProcessTime")) {

				sql = " Select 'LRFProcessTime',round(max(data_value)) as data_value from( "
						+ " Select 'LRFProcessTime',nvl(((a.event_dt - b.event_dt)*24*60),0) as data_value from "
						+ " (Select 'LRFProcessTime' as param_type,'PURGING_STOP' event_desc,event_date_time event_dt from  trns_lrf_heat_dtls lrf_hdr,mstr_event "
						+ " mstr_eve,trns_heat_process_events proc_eve where lrf_hdr.heat_id = '" + heatId
						+ "' and lrf_hdr.heat_id = proc_eve.heat_id and  lrf_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('PURGING_STOP')) a, "
						+ " (Select 'LRFProcessTime' as param_type,'PURGING_START' event_desc,event_date_time event_dt from  trns_lrf_heat_dtls lrf_hdr,mstr_event mstr_eve,trns_heat_process_events proc_eve "
						+ " where lrf_hdr.heat_id = '" + heatId
						+ "' and lrf_hdr.heat_id = proc_eve.heat_id and  lrf_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('PURGING_START')) b"
						+ " where a.param_type = b.param_type " + " union "
						+ " Select 'LRFProcessTime',0 as data_value from dual) ";
			} else if (LogType.equals("Liquidus")) {

				sql = " Select  'Liquidus' Liquidus_temp,(case when psn_chem.value_aim is not null and psn_chem.value_aim > 0 then '0' || to_char(psn_chem.value_aim) when psn_chem.value_min is null then '0' "

						+ " else to_char(psn_chem.value_aim) end) data_value from mstr_psn_hdr psn_hdr,mstr_psn_chemistry psn_chem, "

						+ " trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('"+Constants.LRF_LIFT_CHEM+"')  "

						+ " and le.lookup_value in('Liquidus Temperature') and psn_hdr.psn_hdr_sl_no = lrf_hdr.aim_psn and psn_hdr.psn_hdr_sl_no = psn_chem.psn_hdr_sl_no and psn_hdr.psn_status in('APPROVED') "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and lrf_hdr.heat_counter in(Select max(heat_counter) from trns_lrf_heat_dtls  lrf_dtl where heat_id = '"
						+ heatId + "' " + ")  order by le.attribute1   ";

			}

			else if (LogType.equals("LRFElectrode")) {

				sql = " SELECT a.lookup_value, NVL (b.is_added, 'N') is_added, NVL (b.is_adjusted, 'N') is_adjusted "

						+ " FROM (SELECT d.lookup_value lookup_value, '' is_added, '' is_adjusted  FROM app_lookups d "

						+ " WHERE d.lookup_value IN ('E1', 'E2', 'E3') AND d.lookup_type = 'LRF_ELECTRODE') a, "

						+ " (SELECT   '', c.lookup_value lookup_value, NVL (is_added,'N') is_added,NVL (is_adjusted, 'N') is_adjusted "

						+ " FROM trns_lrf_heat_dtls a,trns_lrf_electrodes_usage b,app_lookups c WHERE a.trns_si_no = b.lrf_heat_trns_id "

						+ " AND b.electrode_id(+) = c.lookup_id AND a.heat_id = '" + heatId
						+ "' ORDER BY c.lookup_id) b "

						+ " WHERE a.lookup_value = b.lookup_value(+) ";

			} else if (LogType.equals("HeatConsmAndChemDtls")) {

				sql = "SELECT pc.material_desc, nvl(ehcm.qty,0) FROM trns_eof_heat_cons_materials ehcm, mstr_process_consumables pc, app_lookups lu, "

						+ "trns_eof_heat_dtls ehd WHERE ehcm.material_id = pc.material_id AND pc.record_status = 1 "

						+ "AND pc.material_type = lu.lookup_id AND lu.lookup_code = 'LADLE_ADDITIONS' AND ehd.trns_si_no = ehcm.trns_eof_si_no "

						+ "AND ehd.HEAT_ID = '" + heatId + "' order by pc.order_seq";

			} else if (LogType.equals("EOFQCAChem")) {

				sql = "select nvl(chd.sample_date_time,'NA'), (select nvl(ch.aim_value,0) from trns_heat_chemistry_child ch, app_lookups lkp "

						+ "where ch.element = lkp.lookup_id and ch.sample_si_no = chd.sample_si_no and lkp.lookup_code = 'ATM OXY' "

						+ "and lkp.lookup_type = 'ELEMENT' and lkp.lookup_status = 1 and ch.element = lkp.lookup_id) as atmoxy, "

						+ "(select nvl(ch.aim_value,0) from trns_heat_chemistry_child ch, app_lookups lkp where ch.element = lkp.lookup_id "

						+ "and ch.sample_si_no = chd.sample_si_no and lkp.lookup_code = 'SUB OXY' and lkp.lookup_type = 'ELEMENT' and "

						+ "lkp.lookup_status = 1 and ch.element = lkp.lookup_id) as suboxy, chd.sample_temp, "

						+ "(select nvl(ch.aim_value,0) from trns_heat_chemistry_child ch, app_lookups lkp  where ch.element = lkp.lookup_id and "

						+ "ch.sample_si_no = chd.sample_si_no and lkp.lookup_code = 'C' and lkp.lookup_type = 'ELEMENT' and lkp.lookup_status = 1 "

						+ "and ch.element = lkp.lookup_id) as c from trns_heat_chemistry_hdr chd where chd.heat_id = '"
						+ heatId + "' and chd.final_result = "

						+ "1 AND chd.analysis_type = (select lookup_id  from app_lookups lk1 where lk1.lookup_value = 'EAF_QCA_CHEM' and "

						+ "lk1.lookup_type = 'CHEM_LEVEL')";

			} else if (LogType.equals("HeatEventDelay")) {

				sql = "select adtl.activities,std_cycle_time std_time,nvl(round((case when (select (hdr.ACTIVITY_END_TIME - hdr.ACTIVITY_START_TIME)*24*60 from trns_delay_entry_hdr hdr where  "
						+ " hdr.activity_delay_id = adtl.activity_delay_id and hdr.trns_heat_id = ehd.trns_si_no  ) <= 0 then 0 "
						+ " else (select (hdr.ACTIVITY_END_TIME - hdr.ACTIVITY_START_TIME)*24*60 from trns_delay_entry_hdr hdr where "
						+ " hdr.activity_delay_id = adtl.activity_delay_id and hdr.trns_heat_id = ehd.trns_si_no  ) end),0),0) as act_time, "
						+ " nvl(round((Case when (select deh.tot_delay from trns_delay_entry_hdr deh where deh.activity_delay_id = "
						+ " adtl.activity_delay_id and deh.trns_heat_id = ehd.trns_si_no) <= 0 then 0 else (select deh.tot_delay from trns_delay_entry_hdr deh where deh.activity_delay_id = "
						+ " adtl.activity_delay_id and deh.trns_heat_id = ehd.trns_si_no) end),0),0) tot_delay,  "
						+ " get_delay_reason(adtl.activity_delay_id,ehd.trns_si_no) delay_reason, "
						+ " '['||(select to_char(hdr.ACTIVITY_START_TIME,'dd/mm/yy hh:mi')||' to '||to_char(hdr.ACTIVITY_END_TIME,'dd/mm/yy hh:mi') from trns_delay_entry_hdr hdr where "
						+ " hdr.activity_delay_id = adtl.activity_delay_id and hdr.trns_heat_id = ehd.trns_si_no  )||'] ','['||adtl.DELAY_DETAILS||']' "
						+ " from  mstr_activity_delay_dtls adtl, trns_lrf_heat_dtls ehd where ehd.heat_id = '" + heatId
						+ "' "
						+ " and (adtl.delay_details,ehd.heat_id,heat_counter) in(Select delay_details,heat_id,max(heat_counter) from mstr_activity_delay_dtls a, trns_lrf_heat_dtls b where heat_id = '"
						+ heatId + "' " + " and a.sub_unit_id = b.sub_unit_id group by delay_details,heat_id)"
						+ " and ehd.sub_unit_id = adtl.sub_unit_id order by adtl.activity_seq";

			} else if (LogType.equals("LRFLiftPSNAimChem")) {

				sql = " Select   'AIM' as stage,le.lookup_value element_name,(case when psn_chem.value_aim is not null "

						+ " and psn_chem.value_aim > 0 then '0' || to_char(psn_chem.value_aim) when psn_chem.value_min is null then '0'  "

						+ " else to_char(psn_chem.value_aim) end) data_value, le.attribute1 from mstr_psn_hdr psn_hdr,mstr_psn_chemistry psn_chem, "

						+ " trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and psn_hdr.psn_hdr_sl_no = lrf_hdr.aim_psn and psn_hdr.psn_hdr_sl_no = psn_chem.psn_hdr_sl_no and psn_hdr.psn_status in('APPROVED') "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and lrf_hdr.heat_counter in(Select max(heat_counter) from trns_lrf_heat_dtls  lrf_dtl where heat_id = '"
						+ heatId + "' " + ")  order by le.attribute1   ";

			} else if (LogType.equals("LRFLiftPSNMinChem")) {

				sql = " Select   'MIN' as stage,le.lookup_value element_name,(case when psn_chem.value_min is not null "

						+ " and psn_chem.value_min > 0 then '0' || to_char(psn_chem.value_min) when psn_chem.value_min is null then '0'  "

						+ " else to_char(psn_chem.value_min) end) data_value, le.attribute1 from mstr_psn_hdr psn_hdr,mstr_psn_chemistry psn_chem, "

						+ " trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and psn_hdr.psn_hdr_sl_no = lrf_hdr.aim_psn and psn_hdr.psn_hdr_sl_no = psn_chem.psn_hdr_sl_no and psn_hdr.psn_status in('APPROVED') "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and lrf_hdr.heat_counter in(Select max(heat_counter) from trns_lrf_heat_dtls  lrf_dtl where heat_id = '"
						+ heatId + "' " + ")  order by le.attribute1   ";

			} else if (LogType.equals("LRFLiftPSNMaxChem")) {

				sql = " Select   'MAX' as stage,le.lookup_value element_name,(case when psn_chem.value_max is not null "

						+ " and psn_chem.value_max > 0 then '0' || to_char(psn_chem.value_max) when psn_chem.value_max is null then '0'  "

						+ " else to_char(psn_chem.value_max) end) data_value, le.attribute1 from mstr_psn_hdr psn_hdr,mstr_psn_chemistry psn_chem, "

						+ " trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and psn_hdr.psn_hdr_sl_no = lrf_hdr.aim_psn and psn_hdr.psn_hdr_sl_no = psn_chem.psn_hdr_sl_no and psn_hdr.psn_status in('APPROVED') "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and lrf_hdr.heat_counter in(Select max(heat_counter) from trns_lrf_heat_dtls  lrf_dtl where heat_id = '"
						+ heatId + "' " + ")  order by le.attribute1   ";

			} else if (LogType.equals("EOFMatlList")) {

				sql = " Select stage,material_desc,data_value,material_id from("

						+ " Select 'EOFMName' as stage,'DATE' as material_desc,'DATE' as data_value,0 as material_id from dual "

						+ " UNION "

						+ " select distinct 'EOFMName' as stage,matls.material_desc as material_desc, matls.material_desc||' ('||uom.lookup_value||')' as data_value,matls.material_id  "

						+ " from  trns_eof_heat_dtls eof_hdr,app_lookups matl_type,app_lookups uom,mstr_process_consumables matls,mstr_mtrl_unit_map subunit_matl_map "

						+ " where uom.LOOKUP_ID = matls.UOM  and matl_type.LOOKUP_CODE ='LADLE_ADDITIONS' and eof_hdr.sub_unit_id = subunit_matl_map.sub_unit_id "

						+ " and uom.lookup_id = matls.uom and matl_type.lookup_id = matls.material_type  and matls.material_id = subunit_matl_map.mtrl_id "

						+ " and eof_hdr.heat_id = '" + heatId + "' "

						+ " and get_matl_act_consumption(eof_hdr.heat_id,eof_hdr.heat_counter,matls.material_type,matls.material_id) > 0 "

						+ ") order by material_id ";

			} else if (LogType.equals("EOFMatlConsm")) {
				sql = " SELECT   'EOFMName', TO_CHAR (MIN (consumption_date),'dd/mm/yy hh24:mi') consumption_date, "
						+ " get_matl_consumption (eof_hdr.heat_id, eof_hdr.heat_counter, eof_hdr.trns_si_no, matls.material_type, matls.material_id) AS data_value, "
						+ " NVL (matls.material_id, 0) material_id "
						+ " FROM trns_eof_heat_dtls eof_hdr, app_lookups matl_type,  app_lookups uom, mstr_process_consumables matls, mstr_mtrl_unit_map subunit_matl_map, "
						+ " (SELECT DISTINCT heat_id, heat_counter, trns_si_no, consumption_date "
						+ " FROM trns_eof_heat_cons_materials cons,trns_eof_heat_dtls eof_dtl "
						+ " WHERE eof_dtl.heat_id = '" + heatId
						+ "' and eof_dtl.trns_si_no = cons.trns_eof_si_no) cons_hdr "
						+ " WHERE uom.lookup_id = matls.uom AND matl_type.lookup_code = 'LADLE_ADDITIONS' AND eof_hdr.sub_unit_id = subunit_matl_map.sub_unit_id "
						+ " AND uom.lookup_id = matls.uom AND matl_type.lookup_id = matls.material_type AND matls.material_id = subunit_matl_map.mtrl_id "
						+ " AND cons_hdr.heat_id = eof_hdr.heat_id AND cons_hdr.heat_counter = eof_hdr.heat_counter AND consumption_date IS NOT NULL "
						+ " AND eof_hdr.heat_id = '" + heatId
						+ "' AND get_matl_act_consumption (eof_hdr.heat_id, eof_hdr.heat_counter, matls.material_type, matls.material_id ) > 0 "
						+ " group by 'EOFMName', get_matl_consumption (eof_hdr.heat_id, eof_hdr.heat_counter, eof_hdr.trns_si_no, matls.material_type, matls.material_id), "
						+ " NVL (matls.material_id, 0) ";
			}

			else if (LogType.equals("LRFMatlList")) {

				sql = " Select stage,material_desc,data_value,material_id from("

						+ " Select 'MName' as stage,'ARC_START' as material_desc,'ARC_START' as data_value,-4 as material_id from dual "

						+ " UNION "

						+ " Select 'MName' as stage,'ARC_END' as material_desc,'ARC_END' as data_value,-3 as material_id from dual "

						+ " UNION "

						+ " Select 'MName' as stage,'ARC_TIME' as material_desc,'ARC_TIME' as data_value,-2 as material_id from dual "

						+ " UNION "

						+ " Select 'MName' as stage,'POWER_CONSM' as material_desc,'POWER_CONSM' as data_value,-1 as material_id from dual "

						+ " UNION "

						+ " Select 'MName' as stage,'TEMP' as material_desc,'TEMP' as data_value,0 as material_id from dual "

						+ " UNION "

						+ " select distinct 'MName' as stage,matls.material_desc as material_desc, matls.material_desc||' ('||uom.lookup_value||')' as data_value,matls.material_id  "

						+ " from  trns_lrf_heat_dtls lrf_hdr,app_lookups matl_type,app_lookups uom,mstr_process_consumables matls,mstr_mtrl_unit_map subunit_matl_map "

						+ " where uom.LOOKUP_ID = matls.UOM  and matl_type.LOOKUP_CODE ='LRF_ARC_ADDITIONS' and lrf_hdr.sub_unit_id = subunit_matl_map.sub_unit_id "

						+ " and uom.lookup_id = matls.uom and matl_type.lookup_id = matls.material_type  and matls.material_id = subunit_matl_map.mtrl_id "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and get_matl_act_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,matls.material_type,matls.material_id) > 0 "

						+ ") order by material_id ";

			} else if (LogType.equals("VDMatlList")) {

				sql = " Select material_desc,data_value,material_id from("

						+ " select matls.material_desc as material_desc, matls.material_desc||' ('||uom.lookup_value||')' as data_value,matls.material_id  "

						+ " from  trns_vd_heat_dtls lrf_hdr,app_lookups matl_type,app_lookups uom,mstr_process_consumables matls,mstr_mtrl_unit_map subunit_matl_map "

						+ " where uom.LOOKUP_ID = matls.UOM  and matl_type.LOOKUP_CODE ='VD_ADDITIONS' and lrf_hdr.sub_unit_id = subunit_matl_map.sub_unit_id "

						+ " and uom.lookup_id = matls.uom and matl_type.lookup_id = matls.material_type  and matls.material_id = subunit_matl_map.mtrl_id "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and get_matl_act_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,matls.material_type,matls.material_id) > 0 "

						+ ") order by material_id ";

			}else if (LogType.equals("LRFMatlConsm")) {

				sql = " Select 'MName',to_char(arcing_start_date_time,'dd/mm/yy hh24:mi ') arcing_start_date_time, get_matl_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,arc_hdr.arc_si_no, "

						+ " matls.material_type,matls.material_id) as data_value,nvl(matls.material_id,0) material_id,to_char(arcing_end_date_time,'dd/mm/yy hh24:mi ') arcing_end_date_time,to_char((arcing_end_date_time - arcing_start_date_time )*24*60) as arc_time, power_consumption, bath_temperature,vd_name.attribute1 as addition_type   "

						+ " from trns_lrf_heat_dtls lrf_hdr,app_lookups matl_type,app_lookups uom,app_lookups vd_name,mstr_process_consumables matls,mstr_mtrl_unit_map subunit_matl_map, "

						+ " (Select distinct heat_id,heat_counter,arc_si_no, arcing_start_date_time,arcing_end_date_time, power_consumption, bath_temperature, addition_type from trns_lrf_heat_arcing_dtls arc_hdr "

						+ " where arc_hdr.heat_id = '" + heatId + "') arc_hdr "

						+ " where   uom.LOOKUP_ID = matls.UOM and matl_type.LOOKUP_CODE ='LRF_ARC_ADDITIONS' and vd_name.lookup_type = 'LRF_ADDITION_TYPE' and arc_hdr.addition_type = vd_name.lookup_id "

						+ " and lrf_hdr.sub_unit_id = subunit_matl_map.sub_unit_id and uom.lookup_id = matls.uom "

						+ " and matl_type.lookup_id = matls.material_type and matls.material_id = subunit_matl_map.mtrl_id "

						+ " and arc_hdr.heat_id = lrf_hdr.heat_id and arc_hdr.heat_counter = lrf_hdr.heat_counter and arcing_start_date_time is not null "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and get_matl_act_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,matls.material_type,matls.material_id) > 0 "

						+ " order by arcing_start_date_time,matls.material_id ";

			}else if (LogType.equals("VDMatlConsm")) {

				sql = " Select 'VDMName',(select distinct to_char(addition_date_time,'dd/mm/yy ') consumption_date from trns_vd_heat_cons_lines where heat_id = '" + heatId + "') consumption_date,get_matl_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,arc_hdr.arc_si_no, "

						+ " matls.material_type,matls.material_id) as data_value,nvl(matls.material_id,0) material_id  "

						+ " from trns_vd_heat_dtls lrf_hdr,app_lookups matl_type,app_lookups uom,mstr_process_consumables matls,mstr_mtrl_unit_map subunit_matl_map, "

						+ " (Select distinct heat_id,heat_counter,arc_si_no,addition_type from trns_vd_additions_dtls arc_hdr "

						+ " where arc_hdr.heat_id = '" + heatId + "') arc_hdr "

						+ " where   uom.LOOKUP_ID = matls.UOM and matl_type.LOOKUP_CODE ='VD_ADDITIONS'"

						+ " and lrf_hdr.sub_unit_id = subunit_matl_map.sub_unit_id and uom.lookup_id = matls.uom "

						+ " and matl_type.lookup_id = matls.material_type and matls.material_id = subunit_matl_map.mtrl_id "

						+ " and arc_hdr.heat_id = lrf_hdr.heat_id and arc_hdr.heat_counter = lrf_hdr.heat_counter "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and get_matl_act_consumption(lrf_hdr.heat_id,lrf_hdr.heat_counter,matls.material_type,matls.material_id) > 0 "

						+ " order by matls.material_id ";

			}
			else if (LogType.equals("LRFChemList")) {

				sql = " Select stage,element_name,data_value,attribute1 from( "

						+ " SELECT  'ENAME' AS stage, 'SAMP_DATE' element_name, "

						+ " 'DATE' data_value, '3' as attribute1 FROM DUAL "

						+ " UNION "

						+ " SELECT  'ENAME' AS stage, 'TEMP' element_name, "

						+ " 'TEMP' data_value, '2' as attribute1 "

						+ " FROM DUAL "

						+ " UNION "

						+ " SELECT  'ENAME' AS stage, 'STAGE' element_name, "

						+ " 'STAGE ' data_value, '1' as attribute1 "

						+ " FROM DUAL "

						+ " UNION "

						+ " Select  distinct 'ENAME' as stage,le.lookup_value element_name,le.lookup_value data_value, le.attribute1 from mstr_psn_chemistry psn_chem, "

						+ " trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and lrf_hdr.heat_counter in(Select max(heat_counter) from trns_lrf_heat_dtls  lrf_dtl where heat_id = '"
						+ heatId + "' " + "))  order by attribute1   ";

			}

			else if (LogType.equals("LP0") || LogType.equals("LP1") || LogType.equals("LP2") || LogType.equals("LP3")
					|| LogType.equals("LP4") || LogType.equals("LP5") || LogType.equals("LP6")
					|| LogType.equals("LP7")) {

				sql = " Select stage,element_name,data_value,attribute1 from "

						+ "( Select '" + LogType
						+ "' as stage,'SAMP_DATE' as element_name,to_char(hch.SAMPLE_DATE_TIME,'dd/mm/yy hh:mi ')as data_value, '3' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,'TEMP' as element_name,to_char(hch.SAMPLE_TEMP) as data_value, '2' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,'STAGE' as element_name,to_char(get_sample_stage(lrf_hdr.heat_id,lrf_hdr.heat_counter,'"
						+ LogType + "')) as data_value, '1' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,le.lookup_value element_name,(case when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) is not null and get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) > 0 then '0' || to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter,hch.sample_si_no, le.lookup_id) is null then '0'  "

						+ " else to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) end) data_value, le.attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "')  order by attribute1   ";

			}
			else if (LogType.equals("/LP1") || LogType.equals("/LP2") || LogType.equals("/LP3") || LogType.equals("/LP4")
					|| LogType.equals("/LP5") || LogType.equals("/LP6") || LogType.equals("/LP7")
					|| LogType.equals("/PT1") || LogType.equals("/PT2") || LogType.equals("/PT3") || LogType.equals("/PT4")
					|| LogType.equals("/PT5") || LogType.equals("/PT6") || LogType.equals("/PT7") ) {


				LogType=LogType.replace("/","");
				sql = " Select stage,element_name,data_value,attribute1 from "

						+ "( Select '" + LogType
						+ "' as stage,'SAMP_DATE' as element_name,to_char(hch.SAMPLE_DATE_TIME,'dd/mm/yy hh:mi ')as data_value, '3' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,10,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,'TEMP' as element_name,to_char(hch.SAMPLE_TEMP) as data_value, '2' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,10,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType	+ "' as stage,'STAGE' as element_name,to_char(get_sample_stage(lrf_hdr.heat_id,lrf_hdr.heat_counter,'"
						+  LogType + "')) as data_value, '1' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,10,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,le.lookup_value element_name,(case when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) is not null and get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) > 0 then '0' || to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter,hch.sample_si_no, le.lookup_id) is null then '0'  "

						+ " else to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) end) data_value, le.attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_lrf_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('LRF_LIFT_CHEM')  "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

						+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and substr(hch.SAMPLE_NO,10,3) = '" + LogType

						+ "')  order by attribute1   ";

			}

			else if (LogType.equals("VP0") || LogType.equals("VP1") || LogType.equals("VP2") || LogType.equals("VP3")
					|| LogType.equals("VP4") || LogType.equals("VP5") || LogType.equals("VP6")
					|| LogType.equals("VP7")) {
				sql = " Select stage,element_name,data_value,attribute1 from "

						+ "( Select '" + LogType+ "' as stage,'SAMP_DATE' as element_name,to_char(hch.SAMPLE_DATE_TIME,'dd/mm/yy hh:mi ')as data_value, '3' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,'TEMP' as element_name,to_char(hch.SAMPLE_TEMP) as data_value, '2' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,'STAGE' as element_name, 'AVD' "
						+ " as data_value, '1' as attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

						+ " and lrf_hdr.heat_id = '" + heatId + "' "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "' UNION "

						+ " Select '" + LogType
						+ "' as stage,le.lookup_value element_name,(case when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) is not null and get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) > 0 then '0' || to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

						+ " lrf_hdr.heat_counter,hch.sample_si_no, le.lookup_id) is null then '0'  "

						+ " else to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) end) data_value, le.attribute1 "

						+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl,trns_heat_chemistry_hdr hch  "

						+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

						+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

						+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

						+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"
						+ heatId + "' "

                       + " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') "

						+ " and substr(hch.SAMPLE_NO,length(SAMPLE_NO)-2,3) = '" + LogType

						+ "')  order by attribute1   ";
			}
			else if ( LogType.equals("/VP1") || LogType.equals("/VP2") || LogType.equals("/VP3")
					|| LogType.equals("/VP4") || LogType.equals("/VP5") || LogType.equals("/VP6")
					|| LogType.equals("/VP7")) {

				LogType=LogType.replace("/","");
				sql = " Select stage,element_name,data_value,attribute1 from "

							+ "( Select '" + LogType+ "' as stage,'SAMP_DATE' as element_name,to_char(hch.SAMPLE_DATE_TIME,'dd/mm/yy hh:mi ')as data_value, '3' as attribute1 "

							+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

							+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

							+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

							+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

							+ " and lrf_hdr.heat_id = '" + heatId + "' AND substr(hch.SAMPLE_NO,10,3) = '"+LogType+"' UNION "

							+ " Select '" + LogType	+ "' as stage,'TEMP' as element_name,to_char(hch.SAMPLE_TEMP) as data_value, '2' as attribute1 "

							+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

							+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

							+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

							+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

							+ " and lrf_hdr.heat_id = '" + heatId + "' AND substr(hch.SAMPLE_NO,10,3) = '"+LogType+"' "
							+ " UNION "

							+ " Select '" + LogType	+ "' as stage,'STAGE' as element_name, 'AVD' "
							+ " as data_value, '1' as attribute1 "

							+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups lcl,trns_heat_chemistry_hdr hch  "

							+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

							+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

							+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

							+ " and lrf_hdr.heat_id = '" + heatId + "' AND substr(hch.SAMPLE_NO,10,3) = '"+LogType+"' "

							+ " UNION "

							+ " Select '" +LogType+ "' as stage,le.lookup_value element_name,(case when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

							+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) is not null and get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

							+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id) > 0 then '0' || to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

							+ " lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) when get_heat_chem_sino_ele_val(lrf_hdr.heat_id, "

							+ " lrf_hdr.heat_counter,hch.sample_si_no, le.lookup_id) is null then '0'  "

							+ " else to_char(get_heat_chem_sino_ele_val(lrf_hdr.heat_id, lrf_hdr.heat_counter, hch.sample_si_no, le.lookup_id)) end) data_value, le.attribute1 "

							+ " from mstr_psn_chemistry psn_chem, trns_vd_heat_dtls  lrf_hdr,trns_heat_plan_header plan_hdr, trns_heat_plan_lines plan_lines,app_lookups le,app_lookups lcl,trns_heat_chemistry_hdr hch  "

							+ " where plan_hdr.heat_plan_id = plan_lines.heat_plan_id and plan_lines.aim_psn =  psn_chem.psn_hdr_sl_no "

							+ " and hch.heat_id = lrf_hdr.heat_id and hch.heat_counter = lrf_hdr.heat_counter  and lrf_hdr.heat_plan_id = plan_hdr.heat_plan_id and psn_chem.chem_level = lcl.lookup_id "

							+ " and lcl.lookup_type = 'CHEM_LEVEL' and lcl.lookup_value in('VD_CHEM')  "

							+ " and psn_chem.element_id = le.lookup_id  and le.lookup_type = 'ELEMENT'  and lrf_hdr.heat_id = '"+ heatId + "' AND substr(hch.SAMPLE_NO,10,3) = '"+LogType+"' "

							+ " and le.lookup_value not in('Liq Temp(HOWE Method)','Liquidus Temperature') )  order by attribute1   ";

			}
			else if (LogType.equals("GasConsumption")) {

				sql = "select c.material_desc, nvl(a.qty,0) from trns_eof_heat_cons_materials a, trns_eof_heat_dtls b, mstr_process_consumables c, "

						+ "app_lookups d, app_lookups e where a.trns_eof_si_no = b.trns_si_no and a.material_id = c.material_id and "

						+ "c.uom = d.lookup_id and c.material_type = e.lookup_id and c.record_status = 1 and e.lookup_code = 'PROCESS_CONSUMPTIONS' "

						+ "and b.heat_id = '" + heatId + "'";

			}

			else if (LogType.equals("OtherDataElements")) {

				sql = " Select 'LOW_MBAR_ACHIEVED',(case when substr(to_char(ROUND (MAX (data_value),3)),1,1) = '.' then '0'||to_char(ROUND (MAX (data_value),3)) else to_char(ROUND (MAX (data_value),3)) end) as data_value from( "
						+ " Select 'LOW_MBAR_ACHIEVED',NVL (param_value_aim, 0) AS data_value"
						+ " from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_VD_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "' and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('VD_VACCUM') " + " Union "
						+ " Select 'LOW_MBAR_ACHIEVED',0 as data_value from dual) " + " Union "
						+ " Select 'VDProcessTime',to_char(round(max(data_value))) as data_value from( "
						+ " Select 'VDProcessTime',nvl(((a.event_dt - b.event_dt)*24*60),0) as data_value from "
						+ " (Select 'VDProcessTime' as param_type,'VD_END_TIME' event_desc,event_date_time event_dt from  trns_vd_heat_dtls vd_hdr,mstr_event "
						+ " mstr_eve,trns_heat_process_events proc_eve where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('VD_END_TIME')"
						+ " and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))) a, "
						+ " (Select 'VDProcessTime' as param_type,'VD_START_TIME' event_desc,event_date_time event_dt from  trns_vd_heat_dtls vd_hdr,mstr_event mstr_eve,trns_heat_process_events proc_eve "
						+ " where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('VD_START_TIME')"
						+ "and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))) b " + " where a.param_type = b.param_type " + " union "
						+ " Select 'VDProcessTime',0 as data_value from dual) " + " Union "
						+ " Select 'PLAN_CUT_LEN',to_char(max(data_value)) data_value from ( "
						+ " select 'PLAN_CUT_LEN',nvl(c.PLAN_CUT_LENGTH,0) data_value from trns_lrf_heat_dtls a,trns_heat_plan_header b, trns_heat_plan_lines c "
						+ " where a.heat_plan_id = b.heat_plan_id and a.heat_plan_id = c.heat_plan_id "
						+ " and a.heat_id = '" + heatId + "' and a.AIM_PSN = c.AIM_PSN "
						+ " and a.heat_counter in (select max (heat_counter) from trns_lrf_heat_dtls where heat_id in('"
						+ heatId + "')) " + " union " + " Select 'PLAN_CUT_LEN',0 data_value from dual) " + " UNION "
						+ " SELECT 'PLAN_SECTION',NVL(data_value,'NA') from( "
						+ " select 'PLAN_SECTION',nvl(d.lookup_value,'NA') data_value from trns_lrf_heat_dtls a,trns_heat_plan_header b,mstr_sms_capability c, "
						+ " app_lookups d where a.heat_plan_id = b.heat_plan_id and b.SECTION_PLANNED = c.CAPABILITY_MSTR_ID and c.OUTPUT_SECTION = d.LOOKUP_ID "
						+ " and d.lookup_type = 'OUTPUT_SECTION' and  a.heat_id = '" + heatId + "'  "
						+ " and a.heat_counter in (select max (heat_counter) from trns_lrf_heat_dtls where heat_id in('"
						+ heatId + "'))) " + " UNION "
						+ " Select 'Ar Pressure',to_char(max(data_value)) as data_value from( "
						+ " Select 'Ar Pressure',nvl(PARAM_VALUE_AIM,0) data_value from TRNS_HEAT_PROCESS_PARAMETERS a,TRNS_LRF_HEAT_DTLS b,MSTR_PROCESS_PARAMETERS c,MSTR_SUB_UNIT_DETAILS d "
						+ " where b.heat_id =  '" + heatId
						+ "' and a.HEAT_ID = b.heat_id and c.PARAM_SI_NO = a.PARAM_ID and d.SUB_UNIT_ID = c.sub_unit_id "
						+ " and b.SUB_UNIT_ID = c.sub_unit_id  and param_desc in('Argon_Pressure') "
						+ " and a.heat_counter in (select (case when max(heat_counter)=0 then 1 else max (heat_counter) end) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "')) " + " Union " + " Select 'Ar Pressure',0 as data_value from dual) ";
			}

			else if (LogType.equals("VDElements")) {
				sql = "  Select 'VDUnitName' as col,(b.sub_unit_name) data_value FROM TRNS_VD_HEAT_DTLS a,MSTR_SUB_UNIT_DETAILS b"
						+ " where a.heat_id =  '" + heatId + "' and a.sub_unit_id = b.sub_unit_id  "
						+ " and a.heat_counter in(Select max(heat_counter)-1 from trns_lrf_heat_dtls   where heat_id = '"
						+ heatId + "' " + ")" + " Union "
						+ " Select 'VD_START_TIME' as param_type,to_char(event_date_time,'dd/mm/yy hh:mi')  from  trns_vd_heat_dtls vd_hdr,mstr_event mstr_eve,trns_heat_process_events proc_eve "
						+ " where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('VD_START_TIME')"
						+ " and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))" + " Union "
						+ " Select 'VD_END_TIME' as param_type,to_char(event_date_time,'dd/mm/yy hh:mi')  from  trns_vd_heat_dtls vd_hdr,mstr_event mstr_eve,trns_heat_process_events proc_eve "
						+ " where vd_hdr.heat_id = '" + heatId
						+ "' and vd_hdr.heat_id = proc_eve.heat_id and  vd_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('VD_END_TIME')"
						+ " and vd_hdr.heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls where heat_id in('"
						+ heatId + "'))" + " UNION "
						+ " Select 'LADLE_LIFTING_TIME',to_char((Case when a.event_dt is null then b.event_dt else a.event_dt end),'dd/mm/yy hh:mi') as data_value from "
						+ " (Select 'LADLE_LIFTING_TIME' as param_type,'LADLE_LIFTING_START' event_desc,min(event_date_time) event_dt from  trns_lrf_heat_dtls lrf_hdr,mstr_event "
						+ " mstr_eve,trns_heat_process_events proc_eve where lrf_hdr.heat_id = '" + heatId
						+ "' and lrf_hdr.heat_id = proc_eve.heat_id and  lrf_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('LADLE_LIFTING_START')) a, "
						+ " (Select 'LADLE_LIFTING_TIME' as param_type,'DISPATCH_TO_VD' event_desc,min(event_date_time) event_dt from  trns_lrf_heat_dtls lrf_hdr,mstr_event "
						+ " mstr_eve,trns_heat_process_events proc_eve where lrf_hdr.heat_id = '" + heatId
						+ "' and lrf_hdr.heat_id = proc_eve.heat_id and  lrf_hdr.heat_counter = proc_eve.heat_counter "
						+ " and mstr_eve.event_si_no = proc_eve.event_id and mstr_eve.event_desc in('DISPATCH_TO_VD')) b "
						+ " where a.param_type(+) = b.param_type " + " UNION "
						+ " Select 'VD_REMARKS',(case when a.data_value is null then b.data_value else a.data_value end) data_value from (Select 'VD_REMARKS' ,VD_PROCESS_REMARKS data_value FROM trns_vd_heat_dtls vd_hdr "
						+ " where vd_hdr.heat_id = '" + heatId
						+ "' and heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls vd_dtl "
						+ " where vd_dtl.heat_id = '" + heatId + "')) a, "
						+ " (Select 'VD_REMARKS','NA' data_value  FROM DUAL) b" + " UNION "
						+ " Select 'LRF_REMARKS_BVD',(case when a.data_value is null then b.data_value else a.data_value end) data_value from "
						+ " (Select 'LRF_REMARKS_BVD' ,LRF_PROCESS_REMARKS data_value FROM trns_lrf_heat_dtls lrf_hdr "
						+ " where lrf_hdr.heat_id = '" + heatId
						+ "' and heat_counter in(Select max(heat_counter) from trns_vd_heat_dtls lrf_dtl "
						+ " where lrf_dtl.heat_id = '" + heatId + "')) a, "
						+ " (Select 'LRF_REMARKS_BVD','NA' data_value  FROM DUAL) b " + " UNION "
						+ " Select 'LRF_REMARKS_AVD',(case when a.data_value is null then b.data_value else a.data_value end) data_value from "
						+ " (Select 'LRF_REMARKS_AVD' ,LRF_PROCESS_REMARKS data_value FROM trns_lrf_heat_dtls lrf_hdr "
						+ " where lrf_hdr.heat_id = '" + heatId
						+ "' and heat_counter in(Select max(heat_counter)+1 from trns_vd_heat_dtls lrf_dtl "
						+ " where lrf_dtl.heat_id = '" + heatId + "')) a, "
						+ " (Select 'LRF_REMARKS_AVD','NA' data_value  FROM DUAL) b " + " UNION "
						+ " Select 'LRF_SAMPLE_REMARKS',(case when a.data_value is null then b.data_value else a.data_value end) data_value from "
						+ " (Select 'LRF_SAMPLE_REMARKS' ,get_heat_sample_remarks(lrf_hdr.heat_id,lrf_hdr.heat_counter,'LRF') data_value FROM trns_lrf_heat_dtls lrf_hdr "
						+ " where lrf_hdr.heat_id = '" + heatId
						+ "' and heat_counter in(Select max(heat_counter)+1 from trns_vd_heat_dtls lrf_dtl "
						+ " where lrf_dtl.heat_id = '" + heatId + "')) a, "
						+ " (Select 'LRF_SAMPLE_REMARKS','NA' data_value  FROM DUAL) b ";
			} else if (LogType.equals("HeatMaxCounter")) {

				sql = "select 'HeatMaxCounter',max(heat_counter) heat_counter from trns_lrf_heat_dtls where heat_id = '"
						+ heatId + "'";

			}
			Query query = session.createSQLQuery(sql);

			List ls = null;

			Integer i_list = 0, i_ref = 0, i_matl = 0, i_chem = 0;

			String ls_type = "", ls_curr_arc_start_dt = "", ls_prev_arc_start_dt = "", ls_curr_cons_dt = "",
					ls_prev_cons_dt = "";

			try {

				ls = query.list();

				i_list = query.list().size();

			} catch (Exception e) {

				e.printStackTrace();

			}

			if (i_list > 0) {
				// List ls= getResultFromCustomQuery(hql);
				LRFHeatLogRpt obj;

				LRFHeatLogRpt o1 = new LRFHeatLogRpt();

				LRFHeatLogRpt rptObj = new LRFHeatLogRpt();

				Iterator it = ls.iterator();

				Double tot = 0.0000;

				while (it.hasNext()) {

					Object rows[] = (Object[]) it.next();

					obj = new LRFHeatLogRpt();

					if (LogType.equals("GasConsumption")) {

						obj.setParameter((null == rows[0]) ? null : rows[0].toString());

						if (obj.getParameter().equals("ATM OXYGEN")) {

							o1.setValue1((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("ARGON")) {

							o1.setValue3((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("NITROGEN")) {

							o1.setValue2((null == rows[1]) ? null : rows[1].toString());

						}

					}

					else if (LogType.equals("OtherDataElements")) {

						obj.setParameter((null == rows[0]) ? null : rows[0].toString());

						if (obj.getParameter().equals("LOW_MBAR_ACHIEVED")) {

							o1.setValue1((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("VDProcessTime")) {

							o1.setValue2((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("PLAN_CUT_LEN")) {

							o1.setValue3((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("PLAN_SECTION")) {

							o1.setValue4((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("Ar Pressure")) {

							o1.setValue5((null == rows[1]) ? null : rows[1].toString());

						}

					}

					else if (LogType.equals("LRFIncharges")) {

						obj.setParameter((null == rows[0]) ? null : rows[0].toString());

						if (obj.getParameter().equals("LRFShiftIncharge")) {

							o1.setValue1((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("LRFSrShiftIncharge")) {

							o1.setValue2((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("LRFTargetCCM")) {

							o1.setValue3((null == rows[1]) ? null : rows[1].toString());

						}

					}

					else if (LogType.equals("VDElements")) {

						obj.setParameter((null == rows[0]) ? null : rows[0].toString());

						if (obj.getParameter().equals("VDUnitName")) {

							o1.setValue1((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("VD_START_TIME")) {

							o1.setValue2((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("VD_END_TIME")) {

							o1.setValue3((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("LADLE_LIFTING_TIME")) {

							o1.setValue4((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("VD_REMARKS")) {

							o1.setValue5((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("LRF_REMARKS_BVD")) {

							o1.setValue6((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("LRF_REMARKS_AVD")) {

							o1.setValue7((null == rows[1]) ? null : rows[1].toString());

						} else if (obj.getParameter().equals("LRF_SAMPLE_REMARKS")) {

							o1.setValue8((null == rows[1]) ? null : rows[1].toString());

						}

					} else if (LogType.equals("LRFLadleLife")) {

						obj.setParameter("Ladle Life");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("LRFRinsingTime")) {

						obj.setParameter("Rinsing Time");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("LRFArcingTime")) {

						obj.setParameter("Arcing Time");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("VDHoldingTime")) {

						obj.setParameter("Holding Time");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("HeatMaxCounter")) {

						obj.setParameter("Max Counter");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("VDFlowRate")) {

						obj.setParameter("Flow Rate(Nm3/Hr)");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("VDArgonFlowRate")) {

						obj.setParameter("Ar while flotation(LPM)");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}
					else if (LogType.equals("VDLiftingTime")) {

						obj.setParameter("VD lifting time(Hrs)");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("VDFreeBoardLayer")) {

						obj.setParameter("Free Board Layer");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("A2N2Pressure")) {

						obj.setParameter("Flow Pressure");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}
					else if (LogType.equals("PorousPlugLife")) {

						obj.setParameter("PP life");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}
					else if (LogType.equals("FlotationTime")) {

						obj.setParameter("Flotation time(mins)");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}
					else if (LogType.equals("PlateLife")) {

						obj.setParameter("P life");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("A2N2FlowRateBVD")) {

						obj.setParameter("Flow Rate BVD");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("A2N2FlowRateAVD")) {

						obj.setParameter("Flow Rate AVD");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("LRFPowerConsm")) {

						obj.setParameter("Power Consumption");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("LRFA2N2Consm")) {

						obj.setParameter("A2N2 Consumption");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("LRFProcessTime")) {

						obj.setParameter("Process Time");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					} else if (LogType.equals("Liquidus")) {

						obj.setParameter("Liquidus");

						o1.setValue1((null == rows[1]) ? null : rows[1].toString());

					}

					else if (LogType.equals("LRFElectrode")) {

						obj.setParameter((null == rows[0]) ? null : rows[0].toString());

						if (obj.getParameter().equals("E1"))

						{

							o1.setValue1((null == rows[0]) ? null : rows[0].toString());

							o1.setValue2((null == rows[1]) ? null : rows[1].toString());

							o1.setValue3((null == rows[2]) ? null : rows[2].toString());

						}

						else if (obj.getParameter().equals("E2"))

						{

							o1.setValue4((null == rows[0]) ? null : rows[0].toString());

							o1.setValue5((null == rows[1]) ? null : rows[1].toString());

							o1.setValue6((null == rows[2]) ? null : rows[2].toString());

						}

						else if (obj.getParameter().equals("E3"))

						{

							o1.setValue7((null == rows[0]) ? null : rows[0].toString());

							o1.setValue8((null == rows[1]) ? null : rows[1].toString());

							o1.setValue9((null == rows[2]) ? null : rows[2].toString());

						}

					}

					else if (LogType.equals("ShiftIncharge")) {

						o1.setParameter((null == rows[0]) ? null : rows[0].toString());

					} else if (LogType.equals("LrfMelter")) {

						o1.setParameter((null == rows[0]) ? null : rows[0].toString());
					}

					else if (LogType.equals("LRFLiftPSNAimChem") || LogType.equals("LRFLiftPSNMinChem")
							|| LogType.equals("LRFLiftPSNMaxChem") || LogType.equals("LP0")
							|| LogType.equals("LRFChemList") || LogType.equals("LP1") || LogType.equals("LP2")
							|| LogType.equals("LP3") || LogType.equals("LP4") || LogType.equals("LP5")
							|| LogType.equals("LP6") || LogType.equals("LP7") ||LogType.equals("VP0")
							|| LogType.equals("VP1") || LogType.equals("VP2")
							|| LogType.equals("VP3") || LogType.equals("VP4") || LogType.equals("VP5")
							|| LogType.equals("VP6") || LogType.equals("VP7")
							|| LogType.equals("PT1") || LogType.equals("PT2") || LogType.equals("PT3")
							|| LogType.equals("PT4") || LogType.equals("PT5") || LogType.equals("PT6")
							|| LogType.equals("PT7") ) {

						o1.setParameter((null == rows[0]) ? null : rows[0].toString());

						o1.getAlString().add(i_ref, (null == rows[2]) ? null : rows[2].toString());
						if (LogType.equals("LRFChemList")) {
							o1.setValue2(i_ref.toString());
						}
						i_ref = i_ref + 1;
					}

					else if (LogType.equals("LRFMatlList")) {

						o1.setParameter((null == rows[0]) ? null : rows[0].toString());
						o1.getAlString().add(i_ref, (null == rows[2]) ? null : rows[2].toString());
						if (LogType.equals("LRFMatlList")) {
							o1.setValue2(i_ref.toString());
						}
						i_ref = i_ref + 1;

					}
					else if (LogType.equals("VDMatlList")) {

						o1.setParameter((null == rows[0]) ? null : rows[0].toString());
						o1.getAlString().add(i_ref, (null == rows[1]) ? null : rows[1].toString());
						//o1.getAlString().add(i_ref, (null == rows[2]) ? null : rows[2].toString());
						if (LogType.equals("VDMatlList")) {
							o1.setValue2(i_ref.toString());
						}
						i_ref = i_ref + 1;

					}
					else if (LogType.equals("EOFMatlList")) {

						o1.setParameter((null == rows[0]) ? null : rows[0].toString());
						o1.getAlString().add(i_ref, (null == rows[2]) ? null : rows[2].toString());
						if (LogType.equals("EOFMatlList")) {
							o1.setValue2(i_ref.toString());
						}
						i_ref = i_ref + 1;

					}

					else if (LogType.equals("LRFMatlConsm")) {
						ls_curr_arc_start_dt = (null == rows[1]) ? null : rows[1].toString();
						if (ls_prev_arc_start_dt.equals("")) {
							i_matl = 0;
							i_ref = 0;
							rptObj.setParameter("MName" + i_ref);
							rptObj.getAlString().add(i_matl, (null == rows[8]) ? null : rows[8].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[1]) ? null : rows[1].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[4]) ? null : rows[4].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[5]) ? null : rows[5].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[6]) ? null : rows[6].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[7]) ? null : rows[7].toString());
							ls_prev_arc_start_dt = ls_curr_arc_start_dt;
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[2]) ? null : rows[2].toString());
							i_matl = i_matl + 1;
						} else if (ls_curr_arc_start_dt.equals(ls_prev_arc_start_dt)) {
							rptObj.getAlString().add(i_matl, (null == rows[2]) ? null : rows[2].toString());
							ls_prev_arc_start_dt = ls_curr_arc_start_dt;
							i_matl = i_matl + 1;
						} else {
							retlist.add(i_ref, rptObj);
							rptObj = new LRFHeatLogRpt();
							rptObj.getAlString().clear();
							i_matl = 0;
							i_ref = i_ref + 1;
							ls_prev_arc_start_dt = ls_curr_arc_start_dt;
							rptObj.setParameter("MName" + i_ref);
							rptObj.getAlString().add(i_matl, (null == rows[8]) ? null : rows[8].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[1]) ? null : rows[1].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[4]) ? null : rows[4].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[5]) ? null : rows[5].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[6]) ? null : rows[6].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[7]) ? null : rows[7].toString());
							ls_prev_arc_start_dt = ls_curr_arc_start_dt;
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[2]) ? null : rows[2].toString());
							i_matl = i_matl + 1;
						}
					}

					else if (LogType.equals("EOFMatlConsm")) {
						ls_curr_cons_dt = (null == rows[1]) ? null : rows[1].toString();
						if (ls_prev_cons_dt.equals("")) {
							i_matl = 0;
							i_ref = 0;
							rptObj.setParameter("EOFMName" + i_ref);
							rptObj.getAlString().add(i_matl, (null == rows[1]) ? null : rows[1].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[2]) ? null : rows[2].toString());
							i_matl = i_matl + 1;
							ls_prev_cons_dt = ls_curr_cons_dt;
						} else if (ls_curr_cons_dt.equals(ls_prev_cons_dt)) {
							rptObj.getAlString().add(i_matl, (null == rows[2]) ? null : rows[2].toString());
							ls_prev_cons_dt = ls_curr_cons_dt;
							i_matl = i_matl + 1;
						} else {
							retlist.add(i_ref, rptObj);
							rptObj = new LRFHeatLogRpt();
							rptObj.getAlString().clear();
							i_matl = 0;
							i_ref = i_ref + 1;
							ls_prev_cons_dt = ls_curr_cons_dt;
							rptObj.setParameter("EOFMName" + i_ref);
							rptObj.getAlString().add(i_matl, (null == rows[1]) ? null : rows[1].toString());
							i_matl = i_matl + 1;
							rptObj.getAlString().add(i_matl, (null == rows[2]) ? null : rows[2].toString());
							i_matl = i_matl + 1;
						}
					}
					else if (LogType.equals("VDMatlConsm")) {
						i_matl = 0;
						i_ref = 0;
						rptObj.setParameter("VDMName" + i_ref);
						rptObj.getAlString().add(i_matl,(null == rows[2]) ? null : rows[2].toString());
						i_matl = i_matl + 1;
					}
					else if (LogType.equals("HeatEventDelay")) {
						obj.setParameter((null == rows[0]) ? null : rows[0].toString());
						obj.setValue1((null == rows[1]) ? null : rows[1].toString());
						obj.setValue2((null == rows[2]) ? null : rows[2].toString());
						obj.setValue3((null == rows[3]) ? null : rows[3].toString());
						obj.setValue4((null == rows[4]) ? null : rows[4].toString());
						try {
							obj.setValue5((null == rows[5]) ? null : rows[5].toString());
						} catch (ArrayIndexOutOfBoundsException e) {
							obj.setValue5(null);
						}
						try {
							obj.setValue6((null == rows[6]) ? null : rows[6].toString());
						} catch (ArrayIndexOutOfBoundsException e) {
							obj.setValue6(null);
						}
						if (obj.getValue3() != null)
							tot = tot + Double.parseDouble(obj.getValue3());
						retlist.add(obj);
					}

					else {

						obj.setParameter((null == rows[0]) ? null : rows[0].toString());

						obj.setValue1((null == rows[1]) ? null : rows[1].toString());

						try {

							obj.setValue2((null == rows[2]) ? null : rows[2].toString());

						} catch (ArrayIndexOutOfBoundsException e) {

							obj.setValue2(null);

						}

						if (obj.getValue1() != null)

							tot = tot + Double.parseDouble(obj.getValue1());

						retlist.add(obj);

					}

				}

				if (LogType.equals("HeatEventDelay")) {

					DecimalFormat f = new DecimalFormat("###.000");

					obj = new LRFHeatLogRpt();

					obj.setParameter("Total");

					obj.setValue1((f.format(tot)));

					// retlist.add(obj);

				} else if (LogType.equals("LRFMatlConsm") || LogType.equals("EOFMatlConsm") ||LogType.equals("VDMatlConsm") ) {

					retlist.add(rptObj);
				} else
					retlist.add(o1);

			}

		}

		catch (Exception e) {

			e.printStackTrace();

		} finally {

			close(session);

		}

		return retlist;

	}

	@Override
	public List<ReladleTrnsDetailsMdl> getUnProcessedReturnHeatDtls(Integer p_trns_sl_no) {
		logger.info("inside .. getReturnHeatDetails.....");
		List<ReladleTrnsDetailsMdl> list = new ArrayList<ReladleTrnsDetailsMdl>();
		Session session = getNewSession();
		try {
			String query = "";
			if (p_trns_sl_no == null) {
				query = "select a from ReladleTrnsDetailsMdl a where a.is_processed='N' ";
			} else {
				query = "select a from ReladleTrnsDetailsMdl a where a.is_processed='N' and a.trns_sl_no <> "
						+ p_trns_sl_no;
			}
			list = (List<ReladleTrnsDetailsMdl>) session.createQuery(query).list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getReturnHeatDetails........" + e);
		} finally {
			close(session);
		}

		return list;
	}

	@Override
	public ReladleTrnsDetailsMdl getReturnHeatDetailsById(Integer trns_sl_no) {
		logger.info("inside .. getReturnHeatDetailsById.....");
		ReladleTrnsDetailsMdl model = new ReladleTrnsDetailsMdl();
		Session session = getNewSession();
		try {
			model = (ReladleTrnsDetailsMdl) session.get(ReladleTrnsDetailsMdl.class, trns_sl_no);
		} catch (Exception e) {
			logger.error("error in getReturnHeatDetailsById........" + e);
		} finally {
			close(session);
		}
		return model;
	}

	@Override
	public String saveReladleDetails(List<ReladleProcessHdrModel> reladleHdrList,
			List<ReladleTrnsDetailsMdl> reladleHeatDtlLi, List<HeatStatusTrackingModel> heatTrackLi,
			List<SteelLadleTrackingModel> steelLadleTrackLi, List<SteelLadleLifeModel> stLadlePartsLi,
			List<StLadleLifeHeatWiseModel> heatwiseStLdlPartsLi, StLdlLifeAtHeat heatwiseStLdlObj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if (reladleHdrList != null) {
				for (ReladleProcessHdrModel reladleHdr : reladleHdrList) {
					session.save(reladleHdr);
					for (ReladleProcessDetailsModel dtlObj : reladleHdr.getReladleProcessDtls()) {
						dtlObj.setReladleProcessHdrMdl(null);
						dtlObj.setReladleProcessHdrId(reladleHdr.getReladleProcessId());
						session.persist(dtlObj);
					}
				}
			}
			if (reladleHeatDtlLi != null) {
				for (ReladleTrnsDetailsMdl heatDtlObj : reladleHeatDtlLi) {
					session.update(heatDtlObj);
				}
			}
			if (heatTrackLi != null) {
				for (HeatStatusTrackingModel heatTrackObj : heatTrackLi) {
					if (heatTrackObj.getHeat_track_id() == null)
						session.save(heatTrackObj);
					else
						session.update(heatTrackObj);
				}
			}
			if (steelLadleTrackLi != null) {
				for (SteelLadleTrackingModel steelLadleTrackObj : steelLadleTrackLi) {
					session.update(steelLadleTrackObj);
				}
			}
			if (stLadlePartsLi != null) {
				for (SteelLadleLifeModel stLdlPartsLifeObj : stLadlePartsLi) {
					session.update(stLdlPartsLifeObj);
				}
			}
			if (heatwiseStLdlPartsLi != null) {
				for (StLadleLifeHeatWiseModel heatwiseStLdlPartsObj : heatwiseStLdlPartsLi) {
					session.save(heatwiseStLdlPartsObj);
				}
			}
			if (heatwiseStLdlObj != null) {
				session.save(heatwiseStLdlObj);
			}
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(LRFProductionDaoImpl.class + " Inside LRFProductionDaoImpl saveReladleDetails Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public String saveDispatchToLrfDtls(LRFHeatDetailsModel lrfHeatObj, LRFHeatDetailsModel newLrfHeatObj,
			HeatStatusTrackingModel heatTrackObj, ReladleTrnsDetailsMdl reladleTrnsObj) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if (lrfHeatObj != null) {
				session.update(lrfHeatObj);
			}
			if (newLrfHeatObj != null) {
				session.save(newLrfHeatObj);
			}
			if (heatTrackObj != null) {
				if (heatTrackObj.getHeat_track_id() == null)
					session.save(heatTrackObj);
				else
					session.update(heatTrackObj);
			}
			if (reladleTrnsObj != null) {
				session.save(reladleTrnsObj);
			}
			commit(session);
			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside saveDispatchToLrfDtls() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(LRFProductionDaoImpl.class + " Inside saveDispatchToLrfDtls() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(LRFProductionDaoImpl.class + " Inside LRFProductionDaoImpl saveDispatchToLrfDtls Exception..",
					e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public List<ReladleProcessDetailsModel> getReladleDetails(Integer reladle_heat_id) {
		logger.info("inside .. getReladleDetails.....");
		List<ReladleProcessDetailsModel> list = new ArrayList<ReladleProcessDetailsModel>();
		Session session = getNewSession();
		try {
			String query = "select a from ReladleProcessDetailsModel a where a.reladleHeatDtlId=" + reladle_heat_id;

			list = (List<ReladleProcessDetailsModel>) session.createQuery(query).list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getReladleDetails........" + e);
		} finally {
			close(session);
		}

		return list;
	}

	public String getCCMSecSize(int pln_id) {
		Session session=getNewSession();
		String qry="SELECT lkup.lookup_value " + 
				" FROM app_lookups lkup, " + 
				" mstr_sms_capability sec_mst, " + 
				" trns_heat_plan_header pln_hrd " + 
				" WHERE pln_hrd.heat_plan_id = " + pln_id+
				" AND sec_mst.capability_mstr_id = pln_hrd.section_planned " + 
				" AND lkup.lookup_id = sec_mst.output_section";
		return getResultFromCustomQuery(qry).get(0).toString();

	}

	@Override
	public List<LRFHeatConsumableModel> getLrfAdditionsByHeatNo(String heat_no, String mtrl_type) {
		logger.info("inside .. getLrfAdditionsByHeatNo.....");
		List<LRFHeatConsumableModel> list = new ArrayList<LRFHeatConsumableModel>();
		LRFHeatConsumableModel obj;
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String hql = "select mstr.material_id, mstr.material_desc, mstr.sap_mtl_desc, (select lookup_value from app_lookups where mstr.valuation_type=lookup_id) valuation_type, "+
					" main_qry.total_qty, (select lookup_value from app_lookups where mstr.uom=lookup_id) uom, main_qry.cons_si_no, main_qry.consumption_qty, main_qry.record_version, "+
					" main_qry.arcing_si_no, mstr.material_type, main_qry.heat_id, main_qry.heat_counter, to_char(main_qry.addition_date_time, 'dd/MM/yyyy HH:mi:ss'), main_qry.created_by, to_char(main_qry.created_date_time, 'dd/MM/yyyy HH:mi:ss') from mstr_process_consumables mstr left outer join (select cons.material_id,qry.total_qty, cons.arcing_si_no, cons.cons_si_no, "+ 
					" cons.consumption_qty, cons.record_version, cons.heat_id, cons.heat_counter, cons.addition_date_time, cons.created_by, cons.created_date_time from trns_lrf_heat_cons_lines cons, (select cons.material_id, sum(consumption_qty) total_qty, max(cons.arcing_si_no) arc_si_no "+ 
					" from trns_lrf_heat_arcing_dtls ad, trns_lrf_heat_cons_lines cons where ad.arc_si_no = cons.arcing_si_no and ad.heat_id = '"+heat_no+"' group by cons.material_id) qry "+ 
					" where cons.arcing_si_no = qry.arc_si_no and cons.material_id = qry.material_id) main_qry on mstr.material_id = main_qry.material_id join app_lookups l on "+
					" mstr.material_type = l.lookup_id and l.lookup_code = '"+mtrl_type+"' and mstr.matl_unit = '"+Constants.SUB_UNIT_LRF+"' and mstr.record_status = 1";

			List ls = (List<LRFHeatDetailsModel>) getResultFromCustomQuery(hql);
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new LRFHeatConsumableModel();
				obj.setMaterial_id((null == rows[0]) ? null : Integer.parseInt(rows[0].toString()));
				obj.setMaterial_name((null == rows[1]) ? null : rows[1].toString());
				obj.setSap_matl_id((null == rows[2]) ? null : rows[2].toString());
				obj.setValuation_type((null == rows[3]) ? null : rows[3].toString());
				obj.setTot_qty((null == rows[4]) ? null : Double.parseDouble(rows[4].toString()));
				obj.setUom((null == rows[5]) ? null : rows[5].toString());
				obj.setCons_sl_no((null == rows[6]) ? null : Integer.parseInt(rows[6].toString()));
				obj.setConsumption_qty((null == rows[7]) ? null : Double.parseDouble(rows[7].toString()));
				obj.setRecord_version((null == rows[8]) ? null : Integer.parseInt(rows[8].toString()));
				obj.setArc_sl_no((null == rows[9]) ? null : Integer.parseInt(rows[9].toString()));
				obj.setMaterial_type((null == rows[10]) ? null : Integer.parseInt(rows[10].toString()));
				obj.setHeat_id((null == rows[11]) ? null : rows[11].toString());
				obj.setHeat_counter((null == rows[12]) ? null : Integer.parseInt(rows[12].toString()));
				obj.setAddition_date_time((null == rows[13]) ? null : df.parse(rows[13].toString()));
				obj.setCreated_by((null == rows[14]) ? null : Integer.parseInt(rows[14].toString()));
				obj.setCreated_date_time((null == rows[15]) ? null : df.parse(rows[15].toString()));
				obj.setAct_tot_qty(obj.getTot_qty());

				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in getLrfAdditionsByHeatNo........" + e);
		}

		return list;
	}

	@Override
	public List<LRFHeatConsumableLogModel> getMtrlConsLogByHeatNo(String heatNo) {
		logger.info("inside .. getMtrlConsLogByHeatNo.....");
		List<LRFHeatConsumableLogModel> list = new ArrayList<LRFHeatConsumableLogModel>();
		Session session = getNewSession(); 
		String hql = "Select a from LRFHeatConsumableLogModel a where a.heat_id = '"+heatNo+"'";
		try { 
			list = (List<LRFHeatConsumableLogModel>) session.createQuery(hql).list();
		} catch (Exception e) { 
			logger.error("error in getMtrlConsLogByHeatNo........"+e); 
		} 
		finally{
			close(session); 
		} 
		return list;
	}

	@Override
	public String saveOrUpdateMatrlCons(List<LRFHeatConsumableModel> updLi, List<LRFHeatConsumableLogModel> logLi) {
		// TODO Auto-generated method stub
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			if (updLi != null) {
				for(LRFHeatConsumableModel consObj : updLi) {
					if(consObj.getConsumption_qty() != null) {
						if(consObj.getCons_sl_no() != null)
							session.update(consObj);
						else
							session.save(consObj);
					}
				}
			}

			if (logLi != null) {
				for(LRFHeatConsumableLogModel logObj : logLi) {
					session.save(logObj);
				}
			}

			commit(session);
			result = Constants.SAVE;
		} catch (org.hibernate.StaleObjectStateException s) {
			logger.error(HeatProceeEventDaoImpl.class
					+ " Inside org.hibernate.StaleObjectStateException..furnaceAdditionMtrlsSaveOrUpdate", s);

			result = Constants.CONCURRENT_UPDATE_MSG_FAIL;

			rollback(session);

		} catch (DataIntegrityViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatProceeEventDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatProceeEventDaoImpl.class + " Inside furnaceAdditionMtrlsSaveOrUpdate Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}
	@Override
	public HeatStatusTrackingModel getHeatTrackingObj(Integer heatTrackingId) {
		logger.info("inside .. getHeatTrackingObj.....");
		HeatStatusTrackingModel obj = new HeatStatusTrackingModel();
		List<HeatStatusTrackingModel> list=new ArrayList<HeatStatusTrackingModel>();
		try {
			String hql = "select a from HeatStatusTrackingModel a where a.heat_track_id="+heatTrackingId;			
			list = (List<HeatStatusTrackingModel>) getResultFromNormalQuery(hql);
			if(list.size()>0)
			{
				obj=list.get(0);
			}
			//System.out.println("inside daoimpl"+obj.getAct_proc_path()+" track id"+heatTrackingId);
		} catch (Exception e) {
			logger.error("error in getHeatStatusDetails........" + e);
		}

		return obj;
	}


	@Override
	@Transactional
	public HeatChemistryHdrDetails getHeatDetailsByHeatNo(String heatNo, String logType) {

		HeatChemistryHdrDetails obj = new HeatChemistryHdrDetails();
		List<HeatChemistryHdrDetails> list=new ArrayList<HeatChemistryHdrDetails>();
		try {
			String hql = "select a from HeatChemistryHdrDetails a where a.heat_id='"+heatNo+"' and substr(a.sample_no,10,3)='"+logType+"' ";			
			list = (List<HeatChemistryHdrDetails>) getResultFromNormalQuery(hql);

			if(list.size()>0)
			{
				obj=list.get(0);
			}

		} catch (Exception e) {
			logger.error("error in HeatChemistryHdrDetails model........" + e);
		}

		return obj;
	}		
}