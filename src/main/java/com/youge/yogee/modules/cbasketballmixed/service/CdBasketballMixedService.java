/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbasketballmixed.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cbasketballmixed.dao.CdBasketballMixedDao;
import com.youge.yogee.modules.cbasketballmixed.entity.CdBasketballMixed;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩蓝球Service
 * @author WeiJinChao
 * @version 2018-01-22
 */
@Component
@Transactional(readOnly = true)
public class CdBasketballMixedService extends BaseService {

	@Autowired
	private CdBasketballMixedDao cdBasketballMixedDao;
	
	public CdBasketballMixed get(String id) {
		return cdBasketballMixedDao.get(id);
	}
	
	public Page<CdBasketballMixed> find(Page<CdBasketballMixed> page, CdBasketballMixed cdBasketballMixed) {
		DetachedCriteria dc = cdBasketballMixedDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdBasketballMixed.getWinningName())) {
			dc.add(Restrictions.or(Restrictions.like("winningName", "%" + cdBasketballMixed.getWinningName() + "%"), Restrictions.like("defeatedName", "%" + cdBasketballMixed.getWinningName() + "%")));

		}
		dc.add(Restrictions.eq(CdBasketballMixed.FIELD_DEL_FLAG, CdBasketballMixed.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("remarks"));
		dc.addOrder(Order.asc("matchId"));
		return cdBasketballMixedDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdBasketballMixed cdBasketballMixed) {

		if(StringUtils.isEmpty(cdBasketballMixed.getId())){
			cdBasketballMixed.setId(IdGen.uuid());
			cdBasketballMixed.setCreateDate(DateUtils.getDateTime());
			cdBasketballMixed.setDelFlag(CdBasketballMixed.DEL_FLAG_NORMAL);
		}
		cdBasketballMixedDao.save(cdBasketballMixed);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdBasketballMixedDao.deleteById(id);
	}



	@Transactional(readOnly = false)
	public List<CdBasketballMixed> getSize(){
		List<CdBasketballMixed> listSize = cdBasketballMixedDao.getSize();
		return listSize;
	}

	@Transactional(readOnly = false)
	public List getRqSize(){
		return cdBasketballMixedDao.findBySql("select distinct remarks from cd_basketball_mixed order by remarks asc");
	}

	/**
	 * wangsong
	 * 20171220
	 * 查询篮球胜平负接口
	 */
	@Transactional(readOnly = false)
	public List<CdBasketballMixed> findListByTime(String remarks) {
		DetachedCriteria dc = cdBasketballMixedDao.createDetachedCriteria();
		dc.add(Restrictions.eq("remarks", remarks));
		dc.add(Restrictions.ne("victoryordefeatOdds", ","));//不等于
		dc.add(Restrictions.eq(CdBasketballMixed.FIELD_DEL_FLAG, CdBasketballMixed.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("matchDate"));
		// 限制条数|分页
		/*Criteria cri = dc.getExecutableCriteria(cdHelpCenterDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));*/
		return cdBasketballMixedDao.find(dc);
	}

	/**
	 * wangsong
	 * 20171220
	 * 查询篮球根据字段名查询数据
	 */
	@Transactional(readOnly = false)
	public List<CdBasketballMixed> findByName(String remarks,String name,String typeValue) {
		DetachedCriteria dc = cdBasketballMixedDao.createDetachedCriteria();
		dc.add(Restrictions.eq("remarks", remarks));
		dc.add(Restrictions.ne(name, typeValue));//不等于
		dc.add(Restrictions.eq(CdBasketballMixed.FIELD_DEL_FLAG, CdBasketballMixed.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("matchDate"));
		// 限制条数|分页
		/*Criteria cri = dc.getExecutableCriteria(cdHelpCenterDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));*/
		return cdBasketballMixedDao.find(dc);
	}



	/**
	 * wangsong
	 * 20171220
	 * 查询篮球让球接口
	 */
	@Transactional(readOnly = false)
	public List<CdBasketballMixed> findDgMixed(String remarks) {
		DetachedCriteria dc = cdBasketballMixedDao.createDetachedCriteria();
		dc.add(Restrictions.eq("remarks", remarks));
		dc.add(Restrictions.eq(CdBasketballMixed.FIELD_DEL_FLAG, CdBasketballMixed.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("matchId"));
		return cdBasketballMixedDao.find(dc);
	}

	/**
	 * wangsong
	 * 根据itemid查询数据
	 * @param itemid
	 * @return
	 */
	public List<CdBasketballMixed> getByItem(String itemid){
		DetachedCriteria dc = cdBasketballMixedDao.createDetachedCriteria();
		dc.add(Restrictions.eq(CdBasketballMixed.FIELD_DEL_FLAG, CdBasketballMixed.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq("itemid", itemid));
		return cdBasketballMixedDao.find(dc);

	}

	/**
	 * 根据期次和场次查询数据
	 * 20180226
	 */
	@Transactional(readOnly = false)
	public CdBasketballMixed findByMatchId(String matchId) {
		DetachedCriteria dc = cdBasketballMixedDao.createDetachedCriteria();
		dc.add(Restrictions.eq("matchId", matchId));
		//dc.add(Restrictions.eq("remarks", remarks));
		dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));

		List<CdBasketballMixed> list = cdBasketballMixedDao.find(dc);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}

	}



	
}
