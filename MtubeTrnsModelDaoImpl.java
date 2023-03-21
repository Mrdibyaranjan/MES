package com.smes.trans.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.smes.trans.model.MtubeTrnsModel;
import com.smes.util.Constants;
import com.smes.util.GenericDaoImpl;

@Repository
public class MtubeTrnsModelDaoImpl extends GenericDaoImpl<MtubeTrnsModel,Long>  implements MtubeTrnsModelDao{

	private static final Logger logger = Logger.getLogger(MtubeTrnsModelDaoImpl.class);
	
	@Transactional
	@Override
	public MtubeTrnsModel getMtubeTrnsByid(Integer trns_id) {
		// TODO Auto-generated method stub
		Session session=getNewSession();
		MtubeTrnsModel lst=null;
		try {

			String hql = "select a from MtubeTrnsModel a where  a.ccm_mtube_trns_id="+trns_id;
			
			lst=(MtubeTrnsModel) session.createQuery(hql).uniqueResult();
//			Criteria cr=session.createCriteria(MtubeTrnsModel.class);
//			cr.add(Restrictions.eq("ccm_mtube_trns_id", trns_id));
			//cr.add(Projections.)
//			cr.setProjection(Projections.projectionList()
//			        .add(Projections.property("ccm_mtube_trns_id"))
//			        .add(Projections.property("ccm_mtube_sl_no"))
//			        .add(Projections.property("mtube_life"))
//			        .add(Projections.property("mtube_status")));
			//lst=(MtubeTrnsModel) cr.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			//logger.error("error in getEofHeatDtlsById........"+e);
			e.printStackTrace();
		}finally{
			close(session);
		}
		return lst;
		
	}
	
	@Transactional
	@Override
	public String saveccmMtubeTrns(MtubeTrnsModel model) {
		String result = Constants.SAVE;
		create(model);
		return result;
	}

	@Transactional
	@Override
	public String updateccmMtubeTrns(MtubeTrnsModel model) {
		String result = Constants.SAVE;
		update(model);
		return result;
	}

}
