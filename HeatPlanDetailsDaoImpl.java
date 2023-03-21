package com.smes.trans.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.smes.masters.model.PsnCustomerMasterMapModel;
import com.smes.masters.model.PsnGradeMasterModel;
import com.smes.trans.model.HeatPlanDetails;
import com.smes.trans.model.HeatPlanHdrDetails;
import com.smes.trans.model.HeatPlanLinesDetails;
import com.smes.trans.model.IfacesmsLpDetailsModel;
import com.smes.trans.model.SoHeader;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository("heatPlanDetailsDao")
public class HeatPlanDetailsDaoImpl extends GenericDaoImpl<HeatPlanHdrDetails, Long> implements HeatPlanDetailsDao {

	private static final Logger logger = Logger.getLogger(HeatPlanDetailsDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<HeatPlanHdrDetails> getHeatPlanHeaderDetailsByStatus(String ids) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatPlanHeaderDetailsByStatus.....");

		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		List<PsnGradeMasterModel> list1 = new ArrayList<PsnGradeMasterModel>();
		List<PsnCustomerMasterMapModel> list2 = new ArrayList<PsnCustomerMasterMapModel>();
		List<HeatPlanHdrDetails> finalList = new ArrayList<HeatPlanHdrDetails>();
		List<SoHeader> newList = new ArrayList<SoHeader>();
		Session session = getNewSession();
		try {
				String hql = "select a from HeatPlanHdrDetails a where a.main_status_id in (" + ids
					+ ") and to_date(prod_start_date, 'dd/mm/yyyy') = to_date(sysdate, 'dd/mm/yyyy') order by plan_sequence";
            list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
			String Act_psn="";
			Integer psn_grade=0;
			Integer cust_ref=0;
			Integer Soheader=0;
			String salesORDER="";
			for (HeatPlanHdrDetails hdrObj : list) {
				int pending_heats = 0;
				//for (HeatPlanLinesDetails lineObj : hdrObj.getHeatPlanLine()){
				
				for (HeatPlanDetails lineObj : hdrObj.getHeatPlanDtls()) {
					if (lineObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)) 
					{
						pending_heats = pending_heats + 1;
						Act_psn=lineObj.getPsnHdrModel().getPsn_no();
						hdrObj.setPsn_no(Act_psn);
						psn_grade=lineObj.getPsnHdrModel().getPsn_hdr_sl_no();
		                String hql1 = "select distinct a from  PsnGradeMasterModel a where a.psn_hdr_sl_no= "+psn_grade+" ";			
						list1 = (List<PsnGradeMasterModel>) session.createQuery(hql1).list();
						for (PsnGradeMasterModel psnGrd : list1)
						{	
							hdrObj.setGrade(psnGrd.getPsn_grade());
                         for(HeatPlanLinesDetails heatPlanLinesDetails: hdrObj.getHeatPlanLine()) {
						  if (heatPlanLinesDetails.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE))
								{
                                   Soheader=heatPlanLinesDetails.getSoHeaderId();
                                   String hql2="select distinct a from SoHeader a where soId="+Soheader+"";
                           		newList = (List<SoHeader>) session.createQuery(hql2).list();
                                   for(SoHeader soObj : newList) {
                           			
                           			salesORDER = soObj.getOrderNo()+"/"+soObj.getItemNo()+"/"+soObj.getSoiDocTypeRef();
                           			
                           		}
                                    hdrObj.setSales_Order(salesORDER);
									
								}
						  }
						}
					   /* cust_ref=lineObj.getPsnHdrModel().getPsn_hdr_sl_no();
					    System.out.println("psn header number"+cust_ref);
						String hql3 = "select distinct a from  PsnCustomerMasterMapModel a where a.psn_hdr_sl_no= "+cust_ref+" ";			
						list2 = (List<PsnCustomerMasterMapModel>) session.createQuery(hql3).list();
						System.out.println("list2 size cust reference"+list2.size());
						for (PsnCustomerMasterMapModel psncust : list2)
						{
						hdrObj.setCust_psn_ref(psncust.getCust_psn_ref());
						}*/
					}
				}
				hdrObj.setPending_heats(pending_heats);
				hdrObj.setTotal(hdrObj.getHeatPlanLine().size());
				finalList.add(hdrObj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatPlanHeaderDetailsByStatus........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<HeatPlanLinesDetails> getHeatPlanLineDetailsByStatus(Integer heatplanid) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatPlanLineDetailsByStatus.....");
		List<HeatPlanLinesDetails> list = new ArrayList<HeatPlanLinesDetails>();
		List<PsnGradeMasterModel> list1 = new ArrayList<PsnGradeMasterModel>();

		Session session = getNewSession();
		String grade="";
		int psn_grade=0;
		try {
			String hql = "select a from HeatPlanLinesDetails a where a.heatPlanHdrModel.heat_plan_id=" + heatplanid
					+ " and a.record_status=1 order by heat_plan_line_no asc";
			list = (List<HeatPlanLinesDetails>) session.createQuery(hql).list();

			for (HeatPlanLinesDetails hdrObj : list) {
				if (hdrObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)) {
					psn_grade=hdrObj.getPsnHdrModel().getPsn_hdr_sl_no();

					String hql1 = "select distinct a from  PsnGradeMasterModel a where a.psn_hdr_sl_no= "+psn_grade+" ";			
					list1 = (List<PsnGradeMasterModel>) session.createQuery(hql1).list();
					for (PsnGradeMasterModel psnGrd : list1) {	
						hdrObj.setGrade(psnGrd.getPsn_grade());
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatPlanLineDetailsByStatus........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public String heatPlanHeaderSave(HeatPlanHdrDetails heatPlanHdrDetails) {
		// TODO Auto-generated method stub
		logger.info("inside .. heatPlanHeaderSave....." + HeatPlanDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			session.save(heatPlanHdrDetails);

			for (HeatPlanLinesDetails obj : heatPlanHdrDetails.getHeatPlanLine()) {
				obj.setLookupTundishType(null);				
				//	obj.setLookupCastingOrder(null);
				obj.setHeat_plan_id(heatPlanHdrDetails.getHeat_plan_id());
				session.save(obj);
			}
			for (HeatPlanDetails dtlsObj : heatPlanHdrDetails.getHeatPlanDtls()) {
				dtlsObj.setHeat_plan_id(heatPlanHdrDetails.getHeat_plan_id());
				session.save(dtlsObj);
			}
			commit(session);

			result = Constants.SAVE;
		} catch (DataIntegrityViolationException e) {
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (ConstraintViolationException e) {
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside DataIntegrityViolationException() Exception..");
			result = Constants.DATA_EXIST;
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanHeaderSave Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public String heatPlanHeaderUpdate(HeatPlanHdrDetails heatPlanHdrDetails) {
		// TODO Auto-generated method stub
		logger.info("inside .. heatPlanHeaderUpdate....." + HeatPlanDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			session.update(heatPlanHdrDetails);
			for (HeatPlanLinesDetails obj : heatPlanHdrDetails.getHeatPlanLine()) {
				obj.setLookupTundishType(null);
				//obj.setLookupCastingOrder(null);
				if (obj.getHeat_line_id() == null) {
					obj.setHeat_plan_id(heatPlanHdrDetails.getHeat_plan_id());

					session.save(obj);
				} else {

					session.update(obj);
				}
			}

			for (HeatPlanDetails dtlsObj : heatPlanHdrDetails.getHeatPlanDtls()) {
				if (dtlsObj.getHeat_plan_dtl_id() == null) {
					dtlsObj.setHeat_plan_id(heatPlanHdrDetails.getHeat_plan_id());
					session.save(dtlsObj);
				} else {
					if (dtlsObj.getDeleteFlag() != null && dtlsObj.getDeleteFlag().equals("Y")) {
						session.delete(dtlsObj);
					} else
						session.update(dtlsObj);
				}
			}
			commit(session);

			result = Constants.UPDATE;
		} catch (org.hibernate.StaleObjectStateException s) {
			s.printStackTrace();
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanHeaderUpdate Exception..", s);
			result = "<b>PlanHeader--> " + (heatPlanHdrDetails).getHeat_plan_id()
					+ "</b> The Selected item has already been updated by another user. Please get the updated values";
			rollback(session);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanHeaderUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public String heatPlanLinesSave(HeatPlanLinesDetails heatPlanLinesDetails) {
		// TODO Auto-generated method stub
		logger.info("inside .. heatPlanLinesSave....." + HeatPlanDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			session.save(heatPlanLinesDetails);
			commit(session);
			result = Constants.SAVE;
		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanLinesSave Exception..", e);
			result = Constants.SAVE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getMaxHDRHeatPlan_id() {
		// TODO Auto-generated method stub
		logger.info("inside getMaxHDRHeatPlan_id");

		Integer max_id = 0;
		Session session = getNewSession();
		try {
			begin(session);
			String hsql = "select max(a.heat_plan_id) from HeatPlanHdrDetails a";

			Iterator<Integer> itr = (Iterator<Integer>) session.createQuery(hsql).list().iterator();
			max_id = itr.next().intValue();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception.. getMaxHDRHeatPlan_id..." + e.getMessage());
		} finally {
			close(session);
		}
		return max_id;
	}

	@Override
	public String heatPlanLinesUpdate(HeatPlanLinesDetails heatPlanLinesDetails) {
		// TODO Auto-generated method stub
		logger.info("inside .. heatPlanLinesUpdate....." + HeatPlanDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			session.merge(heatPlanLinesDetails);
			commit(session);
			result = Constants.UPDATE;
		} catch (org.hibernate.StaleObjectStateException s) {
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanLinesUpdate Exception..", s);

			// result=Constants.CONCURRENT_UPDATE_MSG_FAIL;
			result = "<b>PlanLineNo --> " + (heatPlanLinesDetails).getHeat_plan_line_no()
					+ " </b> The Selected item has already been updated by another user. Please get the updated values";
			rollback(session);

		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanLinesUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		} finally {
			close(session);
		}

		return result;
	}

	@Override
	public List<HeatPlanHdrDetails> getHeatPlanDetailsForAttachByStatus(String ids) {
		logger.info("inside .. getHeatPlanDetailsForAttachByStatus.....");
		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		Session session = getNewSession();
		try {
			String hql = "select distinct a from HeatPlanHdrDetails a, HeatPlanDetails b where a.heat_plan_id = b.heat_plan_id and"
					+ " b.status in (" + ids + ") order by a.heat_plan_id asc";

			list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatPlanDetailsForAttachByStatus........" + e);
		} finally {
			close(session);
		}

		return list;

	}

	@Override
	public HeatPlanHdrDetails getHeatPlanHeaderDetailsById(Integer heat_plan_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatPlanHeaderDetailsById.....");
		HeatPlanHdrDetails list = new HeatPlanHdrDetails();
		Session session = getNewSession();
		try {
			list = (HeatPlanHdrDetails) session.get(HeatPlanHdrDetails.class, heat_plan_id);
			// String hql = "select a from HeatPlanHdrDetails a where
			// a.heat_plan_id="+heat_plan_id+" and a.record_status=1";
			// list=(HeatPlanHdrDetails) session.createQuery(hql).uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatPlanHeaderDetailsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public HeatPlanLinesDetails getHeatPlanLineDetailsById(Integer heat_plan_line_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatPlanLineDetailsById.....");
		HeatPlanLinesDetails list = new HeatPlanLinesDetails();
		Session session = getNewSession();
		try {
			list = (HeatPlanLinesDetails) session.get(HeatPlanLinesDetails.class, heat_plan_line_id);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatPlanLineDetailsById........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<HeatPlanLinesDetails> getHeatPlanDetWithRunId(Integer runId, Integer lstatusId) {

		logger.info("inside CasterProductionDaoImpl ..getHeatPlanDetWithRunId...." + CasterProductionDaoImpl.class);

		String sql = "select l.heat_plan_id, l.heat_plan_line_no, l.heat_id,(select ms.main_status_desc from MainHeatStatusMasterModel ms where ms.main_status_id = l.line_status),  "
				+ " (select pg.psn_no from PSNHdrMasterModel pg  where pg.psn_hdr_sl_no = l.aim_psn), TO_CHAR (h.plan_release_date, 'DD/MM/YYYY HH:MI') ,l.indent_no,(select lookup_value from LookupMasterModel where lookup_id=l.section_type and lookup_type='SECTION_TYPE'),l.plan_dia,l.heat_line_id,l.aim_psn from CastRunningStatusModel r, "
				+ " CastPlanDetModel c,HeatPlanLinesDetails l,HeatPlanHdrDetails h where r.running_id = c.running_id and c.plan_id = l.heat_plan_id and l.heat_plan_id = h.heat_plan_id "
				+ " and line_status= " + lstatusId + "and r.running_id =" + runId
				+ " group by l.heat_plan_id,l.heat_plan_line_no,l.heat_id,l.line_status,l.aim_psn,h.plan_release_date,l.indent_no,l.heat_line_id,l.section_type,l.plan_dia";
		HeatPlanLinesDetails obj = null;
		List<HeatPlanLinesDetails> hpList = new ArrayList<HeatPlanLinesDetails>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		List<HeatPlanLinesDetails> ls = (List<HeatPlanLinesDetails>) getResultFromNormalQuery(sql);
		try {
			Iterator it = ls.iterator();
			while (it.hasNext()) {
				Object rows[] = (Object[]) it.next();
				obj = new HeatPlanLinesDetails();
				obj.setHeat_plan_id((null == rows[0]) ? null : Integer.parseInt(rows[0].toString()));
				obj.setHeat_plan_line_no((null == rows[1]) ? null : Integer.parseInt(rows[1].toString()));
				obj.setHeat_id((null == rows[2]) ? null : rows[2].toString());
				// obj.setLine_status_desc((null==rows[3])?null:rows[3].toString());
				// obj.setAim_psn_grade((null==rows[4])?null:rows[4].toString());
				// obj.setPlan_release_date(df.parse((null==rows[5])?null:rows[5].toString()));
				// obj.setIndent_no((null==rows[6])?null:rows[6].toString());
				// obj.setSection_type_desc((null==rows[7])?null:rows[7].toString());
				// obj.setPlan_dia((null==rows[8])?null:Double.parseDouble(rows[8].toString()));
				obj.setHeat_line_id((null == rows[9]) ? null : Integer.parseInt(rows[9].toString()));
				// obj.setAim_psn((null==rows[10])?null:Integer.parseInt(rows[10].toString()));
				hpList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return hpList;

	}

	@Override
	public HeatPlanLinesDetails getHeatPlanLineObject(Integer heatplan_linepk) {

		// TODO Auto-generated method stub
		HeatPlanLinesDetails obj = null;
		Session session = getNewSession();
		try {
			begin(session);
			obj = (HeatPlanLinesDetails) session.get(HeatPlanLinesDetails.class, heatplan_linepk);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(session);
		}
		return obj;
	}

	@Override
	public List<HeatPlanHdrDetails> getAllPrevHeatPlanDetails() {
		// TODO Auto-generated method stub
		logger.info("inside .. getAllPrevHeatPlanDetails,.....");

		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		List<PsnGradeMasterModel> list1 = new ArrayList<PsnGradeMasterModel>();
		List<HeatPlanHdrDetails> finalList = new ArrayList<HeatPlanHdrDetails>();
		List<SoHeader> newList = new ArrayList<SoHeader>();
		Session session = getNewSession();
		int pending_heats;
		try {
			String hql = " select distinct a from HeatPlanHdrDetails a, HeatPlanDetails b where a.heat_plan_id = b.heat_plan_id and "
					+ " b.statusMstrModel.main_status_desc = '"
					+ Constants.MAINHEAT_STATUS_RELEASE + "' order by a.heat_plan_id";

			list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
			String Act_psn="";
			Integer psn_grade=0;
			Integer Soheader=0;
			String salesORDER="";
			for (HeatPlanHdrDetails hdrObj : list) {
				pending_heats = 0;
				//for (HeatPlanLinesDetails lineObj : hdrObj.getHeatPlanLine()){
				
				for (HeatPlanDetails lineObj : hdrObj.getHeatPlanDtls()) {
					if (lineObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)) 
					{
						pending_heats = pending_heats + 1;
						Act_psn=lineObj.getPsnHdrModel().getPsn_no();
						hdrObj.setPsn_no(Act_psn);
						psn_grade=lineObj.getPsnHdrModel().getPsn_hdr_sl_no();

						String hql1 = "select distinct a from  PsnGradeMasterModel a where a.psn_hdr_sl_no= "+psn_grade+" ";			
						list1 = (List<PsnGradeMasterModel>) session.createQuery(hql1).list();

						for (PsnGradeMasterModel psnGrd : list1)
						{	
							hdrObj.setGrade(psnGrd.getPsn_grade());
                         for(HeatPlanLinesDetails heatPlanLinesDetails: hdrObj.getHeatPlanLine()) {
						  if (heatPlanLinesDetails.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE))
								{
                                   Soheader=heatPlanLinesDetails.getSoHeaderId();
                                   String hql2="select distinct a from SoHeader a where soId="+Soheader+"";
                           		newList = (List<SoHeader>) session.createQuery(hql2).list();
                                   for(SoHeader soObj : newList) {
                           			
                           			salesORDER = soObj.getOrderNo()+"/"+soObj.getItemNo()+"/"+soObj.getSoiDocTypeRef();
                           			
                           		}
                                    hdrObj.setSales_Order(salesORDER);
									
								}

						}
						}
					}
				}
				hdrObj.setPending_heats(pending_heats);
				hdrObj.setTotal(hdrObj.getHeatPlanLine().size());
				finalList.add(hdrObj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getAllPrevHeatPlanDetails........" + e);
		} finally {
			close(session);
		}
		return finalList;
	}

	@Override
	public List<HeatPlanHdrDetails> getDaywiseHeatPlanDetails() {
		// TODO Auto-generated method stub
		logger.info("inside .. getDaywiseHeatPlanDetails.....");

		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		Session session = getNewSession();
		try {
			String hql = "select a from HeatPlanHdrDetails a where mainHeatStatusMasterModel.main_status_desc in ('"
					+ Constants.MAINHEAT_STATUS_RELEASE + "', '" + Constants.MAINHEAT_STATUS_WIP
					+ "') and to_date(prod_start_date, 'dd/mm/yyyy') = to_date(sysdate, 'dd/mm/yyyy') order by plan_sequence";

			list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getDaywiseHeatPlanDetails........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public HeatPlanDetails getHeatPlanDetailsById(Integer heat_plan_dtl_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatPlanDetailsById.....");
		HeatPlanDetails list = new HeatPlanDetails();
		Session session = getNewSession();

		try {
			list = (HeatPlanDetails) session.get(HeatPlanDetails.class, heat_plan_dtl_id);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getHeatPlanDetailsById........" + e);
		} finally {
			close(session);
		}

		return list;
	}

	@Override
	public List<HeatPlanHdrDetails> getCasterwiseHeatPlanDetails(Integer caster) {
		// TODO Auto-generated method stub
		logger.info("inside .. getCasterwiseHeatPlanDetails.....");

		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		Session session = getNewSession();
		try {
			String hql = "select a from HeatPlanHdrDetails a where mainHeatStatusMasterModel.main_status_desc in ('"
					+ Constants.MAINHEAT_STATUS_RELEASE + "', '" + Constants.MAINHEAT_STATUS_WIP
					+ "') and a.caster_type = " + caster
					+ " and to_date(prod_start_date, 'dd/mm/yyyy') = to_date(sysdate, 'dd/mm/yyyy') order by plan_sequence";

			list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in getCasterwiseHeatPlanDetails........" + e);
		} finally {
			close(session);
		}
		return list;
	}

	@Override
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReport(Integer caster, String report_date) {
		// TODO Auto-generated method stub
		logger.info("inside .. displayHeatPlanDetailReport.....");

		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		List<HeatPlanHdrDetails> finalList = new ArrayList<HeatPlanHdrDetails>();
		Session session = getNewSession();
		int pending_heats;
		try {
			String hql = " select distinct a from HeatPlanHdrDetails a, HeatPlanDetails b where a.heat_plan_id = b.heat_plan_id and"
					+ " a.caster_type = " + caster + " and a.prod_start_date = TO_DATE('" + report_date
					+ "','DD/MM/YYYY') order by plan_sequence";

			list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
			for (HeatPlanHdrDetails hdrObj : list) {
				pending_heats = 0;
				for (HeatPlanDetails lineObj : hdrObj.getHeatPlanDtls()) {
					if (lineObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)) {
						pending_heats = pending_heats + 1;
					}
				}
				hdrObj.setPending_heats(pending_heats);
				finalList.add(hdrObj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in displayHeatPlanDetailReport........" + e);
		} finally {
			close(session);
		}
		return finalList;
	}

	@Override
	public List<String> getlastEOFHeatNo(String evedate) {

		// TODO Auto-generated method stub
		Session session = getNewSession();
		List<String> heatNo = new ArrayList<String>();
		String[] unit = { "EAF1", "EAF2" };
		try {
			for (int i = 0; i < unit.length; i++) {

				String sql = "SELECT   heat.heat_id " + " FROM trns_eof_heat_dtls heat "
						+ " WHERE heat.production_date < TO_DATE ('" + evedate + "', 'DD/MM/YYYY') "
						+ " AND heat.sub_unit_id = (SELECT b.sub_unit_id " + " FROM mstr_sub_unit_details b "
						+ " WHERE b.sub_unit_name = '" + unit[i] + "') "
						+ " ORDER BY heat.production_date DESC FETCH FIRST ROW ONLY";
				List<Object> lst = session.createSQLQuery(sql).list();

				heatNo.add(lst.get(0).toString());

			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in displayHeatPlanDetailReport........" + e);
		} finally {
			close(session);
		}
		return heatNo;
	}

	@Override
	public List<SoHeader> getSalesOrderDetails(PsnGradeMasterModel psnGradeObj, String compo_mtrl) {
		List<SoHeader> lst = new ArrayList<SoHeader>();
		List<SoHeader> lst1 = new ArrayList<SoHeader>();
		List<SoHeader> newList = new ArrayList<SoHeader>();
		Session session = getNewSession();
		try {
			SoHeader newObj;
			String hql = "select a from SoHeader a, SoComponent b where a.soId = b.soId and b.material = '"+compo_mtrl+"' "
					+ " and b.gradeId = '"+psnGradeObj.getEq_spec4()+"' and a.mainStatusModel.main_status_desc = '"+Constants.MAINHEAT_STATUS_OPEN+"' ";
			lst = (List<SoHeader>) session.createQuery(hql).list();
			for(SoHeader soObj : lst) {
				newObj = new SoHeader();
				newObj.setSoId(soObj.getSoId());
				newObj.setOrderNo(soObj.getOrderNo());
				newObj.setItemNo(soObj.getItemNo());
				newObj.setSoHeaderItem(soObj.getOrderNo()+""+soObj.getItemNo()+"/"+soObj.getSoiDocTypeRef());
				newList.add(newObj);
			}
			String hql1 = "select a from SoHeader a, SoComponent b where a.soId = b.soId and a.soiDocTypeRef = '"+Constants.SO_IDOC_REF_ISO+"'"
					+ " and b.material = '"+compo_mtrl+"' and a.mainStatusModel.main_status_desc = '"+Constants.MAINHEAT_STATUS_OPEN+"' ";

			lst1 = (List<SoHeader>) session.createQuery(hql1).list();
			for(SoHeader soObj : lst1) {
				newObj = new SoHeader();
				newObj.setSoId(soObj.getSoId());
				newObj.setOrderNo(soObj.getOrderNo());
				newObj.setItemNo(soObj.getItemNo());
				newObj.setSoHeaderItem(soObj.getOrderNo()+""+soObj.getItemNo()+"/"+soObj.getSoiDocTypeRef());
				newList.add(newObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(session);
		}
		return newList;
	}

	@Override
	public List<HeatPlanHdrDetails> displayHeatPlanDetailReportView(Integer caster, String report_date) {
		// TODO Auto-generated method stub
		logger.info("inside .. displayHeatPlanDetailReport.....");

		List<HeatPlanHdrDetails> list = new ArrayList<HeatPlanHdrDetails>();
		List<HeatPlanHdrDetails> finalList = new ArrayList<HeatPlanHdrDetails>();
		Session session = getNewSession();
		int pending_heats;
		//String plan_remarks;
		try {
			String hql = " select distinct a from HeatPlanHdrDetails a, HeatPlanDetails b where a.heat_plan_id = b.heat_plan_id and"
					+ " a.caster_type = " + caster + " and a.prod_start_date = TO_DATE('" + report_date
					+ "','DD/MM/YYYY') order by plan_sequence";

			list = (List<HeatPlanHdrDetails>) session.createQuery(hql).list();
			for (HeatPlanHdrDetails hdrObj : list) {
				pending_heats = 0;
				for (HeatPlanDetails lineObj : hdrObj.getHeatPlanDtls()) {
					if (lineObj.getStatusMstrModel().getMain_status_desc().equals(Constants.MAINHEAT_STATUS_RELEASE)) {
						pending_heats = pending_heats + 1;
					}

				}
				//hdrObj.setPending_heats(pending_heats);
				for(HeatPlanLinesDetails heatPlanLinesDetails: hdrObj.getHeatPlanLine()) {
					hdrObj.setCut_length(heatPlanLinesDetails.getPlan_cut_length());
					hdrObj.setAlter_cut_length_max(heatPlanLinesDetails.getAlter_cut_length_max());
					hdrObj.setAlter_cut_length_min(heatPlanLinesDetails.getAlter_cut_length_min());
					hdrObj.setProcess_route(heatPlanLinesDetails.getProcess_route());
					HeatPlanHdrDetails heatPlanHdrDetails=heatPlanLinesDetails.getHeatPlanHdrModel();
					hdrObj.setPsn_no(heatPlanHdrDetails.getPsn_no());
				}
				Set<HeatPlanLinesDetails> set=hdrObj.getHeatPlanLine();
				//Set<PSNHdrMasterModel> set1=(Set<PSNHdrMasterModel>) hdrObj.getPsnHdrModel();
				Iterator<HeatPlanLinesDetails> it = set.iterator();
				if(it.hasNext()) {
					HeatPlanLinesDetails heatPlanLinesDetails= it.next();

				}
				//hdrObj.setPsn_no(psnHdrMasterModel.getPsn_no());
				hdrObj.setPending_heats(pending_heats);
				finalList.add(hdrObj);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("error in displayHeatPlanDetailReport........" + e);
			e.printStackTrace();
		} finally {
			close(session);
		}
		return finalList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HeatPlanDetails> getHeatPlanLineByStatus(Integer heat_plan_id) {
		// TODO Auto-generated method stub
		logger.info("inside .. getHeatPlanLineByStatus.....");
		List<HeatPlanDetails> listline=new ArrayList<HeatPlanDetails>();
		
		Session session = getNewSession();
		HeatPlanDetails line=null;
		try {
			String hql = "select  a.heat_plan_id,a.status,b.main_status_desc,a.act_heat_id,d.psn_grade,a.heat_plan_dtl_id,a.indent_no "+
					 " from HeatPlanDetails a,MainHeatStatusMasterModel b,PSNHdrMasterModel c,PsnGradeMasterModel d where a.heat_plan_id="+heat_plan_id+"  " +
					 "AND a.status=b.main_status_id and a.aim_psn=c.psn_hdr_sl_no and c.psn_hdr_sl_no=d.psn_hdr_sl_no  " + 
					 "AND b.status_type = 'PLAN_LINES' AND a.statusMstrModel.main_status_desc in ('"
							+ Constants.MAINHEAT_STATUS_RELEASE + "', '" + Constants.MAINHEAT_STATUS_WIP
							+ "') order by a.indent_no asc";
		
			List ls  = (List<HeatPlanDetails>) session.createQuery(hql).list();
			Iterator it = ls.iterator();
					while (it.hasNext()) {
					Object rows[] = (Object[])it.next();
					line=new HeatPlanDetails();
					line.setHeat_plan_id((null == rows[0]) ? null : Integer.parseInt(rows[0].toString()));
					line.setStatus((null==rows[1])?null:Integer.parseInt(rows[1].toString()));
					line.setMain_status_desc((null == rows[2]) ? null : rows[2].toString());
					line.setAct_heat_id((null==rows[3])?null:rows[3].toString());
					line.setPsn_grade((null == rows[4]) ? null : rows[4].toString());
					line.setHeat_plan_dtl_id((null == rows[5]) ? null : Integer.parseInt(rows[5].toString()));
					line.setIndent_no((null == rows[6]) ? null : Integer.parseInt(rows[6].toString()));
					listline.add(line);
			
					}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			close(session);
		}
		return listline;
	}

	
	@Override
	public String heatPlanDetailsUpdate(HeatPlanDetails heatPlanDetails,IfacesmsLpDetailsModel ifacObj) {
		// TODO Auto-generated method stub
		logger.info("inside .. heatPlanLinesUpdate....." + HeatPlanDetailsDaoImpl.class);
		String result = "";
		Session session = getNewSession();
		try {
			begin(session);
			session.merge(heatPlanDetails);
			session.save(ifacObj);
			commit(session);
			result = Constants.UPDATE;
		} catch (org.hibernate.StaleObjectStateException s) {
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanDetailsUpdate Exception..", s);

			// result=Constants.CONCURRENT_UPDATE_MSG_FAIL;
			result = "<b>PlanLineNo --> " + (heatPlanDetails).getHeat_plan_id()
					+ " </b> The Selected item has already been updated by another user. Please get the updated values";
			rollback(session);

		} catch (Exception e) {
			e.printStackTrace();
			rollback(session);
			logger.error(HeatPlanDetailsDaoImpl.class + " Inside heatPlanDetailsUpdate Exception..", e);
			result = Constants.UPDATE_FAIL;
		} finally {
			close(session);
		}

		return result;
	
	}
}
