/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cfootballmixed.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cfootballmixed.dao.CdFootballMixedDao;
import com.youge.yogee.modules.cfootballmixed.entity.CdFootballMixed;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞彩足球Service
 *
 * @author WeiJinChao
 * @version 2018-01-08
 */
@Component
@Transactional(readOnly = true)
public class CdFootballMixedService extends BaseService {

    @Autowired
    private CdFootballMixedDao cdFootballMixedDao;

    public CdFootballMixed get(String id) {
        return cdFootballMixedDao.get(id);
    }

    public Page<CdFootballMixed> find(Page<CdFootballMixed> page, CdFootballMixed cdFootballMixed) {
        DetachedCriteria dc = cdFootballMixedDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdFootballMixed.getName())) {
            dc.add(Restrictions.like("name", "%" + cdFootballMixed.getName() + "%"));
        }
        if (StringUtils.isNotEmpty(cdFootballMixed.getWinningName())) {
            dc.add(Restrictions.or(Restrictions.like("winningName", "%" + cdFootballMixed.getWinningName() + "%"), Restrictions.like("defeatedName", "%" + cdFootballMixed.getWinningName() + "%")));

        }
        dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("remarks"));
        dc.addOrder(Order.asc("matchId"));
//		dc.add(Restrictions.sqlRestriction("1=1 order by remarks,match_id"));
        return cdFootballMixedDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdFootballMixed cdFootballMixed) {

        if (StringUtils.isEmpty(cdFootballMixed.getId())) {
            cdFootballMixed.setId(IdGen.uuid());
            cdFootballMixed.setCreateDate(DateUtils.getDateTime());
            cdFootballMixed.setDelFlag(CdFootballMixed.DEL_FLAG_NORMAL);
        }
        cdFootballMixedDao.save(cdFootballMixed);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdFootballMixedDao.deleteById(id);
    }


    /**
     * wangsong
     * 20171220
     * 查询帮助中心接口
     */
    public List<CdFootballMixed> findFootballMixedList() {
        DetachedCriteria dc = cdFootballMixedDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("matchDate"));
        // 限制条数|分页
		/*Criteria cri = dc.getExecutableCriteria(cdHelpCenterDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));*/
        return cdFootballMixedDao.find(dc);
    }

    @Transactional(readOnly = false)
    public List<CdFootballMixed> getSize() {
        List<CdFootballMixed> listSize = cdFootballMixedDao.getSize();
        return listSize;
    }

    @Transactional(readOnly = false)
    public List getRqSize() {
        return cdFootballMixedDao.findBySql("select distinct remarks from cd_football_mixed order by remarks asc");
    }


    @Transactional(readOnly = false)
    public List getByItem(String matchDate) {
        DetachedCriteria dc = cdFootballMixedDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("matchDate", matchDate));
        return cdFootballMixedDao.find(dc);

    }


    /**
     * wangsong
     * 20171220
     * 查询足球根据字段名查询数据
     */
    @Transactional(readOnly = false)
    public List<CdFootballMixed> findByName(String remarks, String name, String typeValue) {
        DetachedCriteria dc = cdFootballMixedDao.createDetachedCriteria();
        dc.add(Restrictions.eq("remarks", remarks));
        dc.add(Restrictions.ne(name, typeValue));//不等于
        dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("matchDate"));
        // 限制条数|分页
		/*Criteria cri = dc.getExecutableCriteria(cdHelpCenterDao.getSession());
		cri.setMaxResults(Integer.parseInt(count));
		cri.setFirstResult(Integer.parseInt(total));*/
        return cdFootballMixedDao.find(dc);
    }


    /**
     * wangsong
     * 20180105
     * 查询全部
     */
    @Transactional(readOnly = false)
    public List<CdFootballMixed> findByALL(String remarks) {
        DetachedCriteria dc = cdFootballMixedDao.createDetachedCriteria();
        dc.add(Restrictions.eq("remarks", remarks));
        dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));
        dc.addOrder(Order.asc("matchDate"));
        return cdFootballMixedDao.find(dc);
    }


    /**
     * 根据期次和场次查询数据
     * 20180224
     */
    @Transactional(readOnly = false)
    public CdFootballMixed findByMatchId(String matchId) {
        DetachedCriteria dc = cdFootballMixedDao.createDetachedCriteria();
        dc.add(Restrictions.eq("matchId", matchId));
        //dc.add(Restrictions.eq("remarks", remarks));
        dc.add(Restrictions.eq(CdFootballMixed.FIELD_DEL_FLAG, CdFootballMixed.DEL_FLAG_NORMAL));

        List<CdFootballMixed> list = cdFootballMixedDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }


}
