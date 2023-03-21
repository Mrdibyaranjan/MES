package com.smes.trans.dao.impl;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.stereotype.Repository;

import com.smes.trans.model.CCMHeatConsMaterialsDetails;
import com.smes.trans.model.CCMHeatDetailsModel;
import com.smes.util.CommonCombo;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class CCMHeatDetailsDaoImpl  extends GenericDaoImpl<CCMHeatDetailsModel, Long> implements CCMHeatDetailsDao {

	@Transactional
	@Override
	public List<CCMHeatDetailsModel> getCCMheats(Integer sub_unit_id) {
		List<CCMHeatDetailsModel> lst=null;
		Session session=getNewSession();
		try {

			String hql = "select a from CCMHeatDetailsModel a, HeatStatusTrackingModel b where a.heat_no = b.heat_id and a.heat_counter = b.heat_counter "
					+ " and a.sub_unit_id="+sub_unit_id+" and b.unit_process_status not in ('"+Constants.UNIT_PROCESS_STATUS_FULL_RET +"', '"+Constants.HEAT_TRACK_STATUS_COMPLETED +"') "
					+ " and a.record_status =1";
			System.out.println("inside daoimpl subunit id"+sub_unit_id);
			lst=(List<CCMHeatDetailsModel>) session.createQuery(hql).list();

		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		return lst;
	}

	@Override
	public CCMHeatDetailsModel getCCMheatByid(Integer trns_sl_no) {
		
		List<CCMHeatDetailsModel> lst=new ArrayList<>();
		Session session=getNewSession();
		try {

		String hql = "select a from CCMHeatDetailsModel a where a.trns_sl_no="+trns_sl_no+"";
		lst=(List<CCMHeatDetailsModel>) session.createQuery(hql).list();
		}
		catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		if(lst.isEmpty()) {
			return null;
		}else {
			return lst.get(0);
		}
		
	}

	@Override
	public List<CCMHeatConsMaterialsDetails> getCCMByProducts(Integer trns_sl_no, String mtrlType) {
		List<CCMHeatConsMaterialsDetails> lst=new ArrayList<CCMHeatConsMaterialsDetails>();
		Session session=getNewSession();
		try {
		String hql = "select a from CCMHeatConsMaterialsDetails a where a.trns_ccm_si_no="+trns_sl_no+" and a.mtrlMstrModel.mtrlTypeLkpModel.lookup_code='"+mtrlType+"'";
		lst=(List<CCMHeatConsMaterialsDetails>) session.createQuery(hql).list();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(session);
		}
		
		return lst;
	} 
	
	@Transactional
	public Integer sendChemToSap(String heatno,String heatcounter) throws SQLException, IOException {
	/*Connection dbConnection = null;
		CallableStatement callableStatement = null;
		  try 
		    {
		String getDBUSERByUserIdSql = "{call  SAP_IFACE_ENPG.SEND_PROD_CHEM(?,?,?)}";
		dbConnection = CommonJDBCConnection.getJDBCConnection();
		
		callableStatement=dbConnection.prepareCall(getDBUSERByUserIdSql);
		callableStatement.setString(1, heatno);
		callableStatement.setInt(2,Integer.valueOf(heatcounter));
		callableStatement.registerOutParameter(3, OracleTypes.INTEGER);
		callableStatement.execute();
		
		int resultSet = callableStatement.getInt(3);
		return resultSet;
		    }catch (Exception e) 
		    {
		        e.printStackTrace();
		    }
		  finally {

				if (callableStatement != null) {
					callableStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
		return null;
		*/

		// TODO Auto-generated method stub
		Integer result = null;
		Session session=getNewSession();
		String res=null;
		
		try {
			begin(session);
			
			res= session.doReturningWork( new ReturningWork<String>(){
			public String execute(Connection connection) throws SQLException {
				CallableStatement cstmt = null;
				try{
					cstmt = connection.prepareCall ("{call  SAP_IFACE_ENPG.SEND_PROD_CHEM(?,?,?)}");
					cstmt.registerOutParameter (3, Types.INTEGER);					    
					cstmt.setString (1, heatno);
					cstmt.setInt (2, Integer.valueOf(heatcounter)); 			   
					cstmt.execute();
					Integer p_out = cstmt.getInt(3);
					return p_out.toString();
				}finally{
					cstmt.close();
				}
			}});
			result = Integer.valueOf(res);
			commit(session);
		} 
		catch (Exception e) {
			e.printStackTrace();
			rollback(session);
		}finally {
			close(session);
		}
		
		return result;
	}

	@Transactional
	@Override
	public List<CommonCombo> getCCMCompletedHeats(Integer sub_unit_id) {
		// TODO Auto-generated method stub
		List<CommonCombo> retlist = new ArrayList<CommonCombo>();
		CommonCombo cb;
		try {
			String hql="select a.trns_sl_no, a.heat_no from CCMHeatDetailsModel a, HeatStatusTrackingModel b where a.heat_no = b.heat_id and a.heat_counter = b.heat_counter and a.sub_unit_id="+sub_unit_id+" "
			+ " and b.unit_process_status = '"+Constants.HEAT_TRACK_STATUS_COMPLETED +"' and b.main_status = '"+Constants.HEAT_TRACK_STATUS_PRD_POST+"' and b.inspection_done='N' and a.record_status =1";
			
			List ls= getResultFromNormalQuery(hql);
			Iterator it = ls.iterator();
			while(it.hasNext()){
				Object rows[]=(Object[]) it.next();
				cb = new CommonCombo();
				cb.setKeyval((null == rows[0]) ? null : rows[0].toString());
				cb.setTxtvalue(((null == rows[1]) ? null : rows[1].toString()));
				retlist.add(cb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return retlist;	
	}

}