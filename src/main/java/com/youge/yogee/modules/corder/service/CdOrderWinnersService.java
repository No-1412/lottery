/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.corder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.corder.dao.CdOrderWinnersDao;
import com.youge.yogee.modules.corder.entity.CdOrderWinners;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 中奖订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-14
 */
@Component
@Transactional(readOnly = true)
public class CdOrderWinnersService extends BaseService {

    @Autowired
    private CdOrderWinnersDao cdOrderWinnersDao;

    public CdOrderWinners get(String id) {
        return cdOrderWinnersDao.get(id);
    }

    public Page<CdOrderWinners> find(Page<CdOrderWinners> page, CdOrderWinners cdOrderWinners) {
        DetachedCriteria dc = cdOrderWinnersDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdOrderWinners.getName())) {
            dc.add(Restrictions.eq("winOrderNum", cdOrderWinners.getWinOrderNum()));
        }
        dc.add(Restrictions.eq(CdOrderWinners.FIELD_DEL_FLAG, CdOrderWinners.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdOrderWinnersDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdOrderWinners cdOrderWinners) {

        if (StringUtils.isEmpty(cdOrderWinners.getId())) {
            cdOrderWinners.setId(IdGen.uuid());
            cdOrderWinners.setCreateDate(DateUtils.getDateTime());
            cdOrderWinners.setDelFlag(CdOrderWinners.DEL_FLAG_NORMAL);
        }
        cdOrderWinnersDao.save(cdOrderWinners);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdOrderWinnersDao.deleteById(id);
    }

    public List<CdOrderWinners> findByWallType(String wallType) {
        DetachedCriteria dc = cdOrderWinnersDao.createDetachedCriteria();
        dc.add(Restrictions.eq("wallType", wallType));
        dc.add(Restrictions.eq(CdOrderWinners.FIELD_DEL_FLAG, CdOrderWinners.DEL_FLAG_NORMAL));
        //dc.add(Restrictions.sqlRestriction("1=1 order by CAST(win_price as SIGNED) desc"));
        dc.addOrder(Order.desc("winPrice"));
        dc.addOrder(Order.desc("createDate"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdOrderWinnersDao.getSession());
        cri.setMaxResults(10);
        cri.setFirstResult(0);
        return cdOrderWinnersDao.find(dc);
    }

    public List<CdOrderWinners> findByUid(String uid, String total, String count) {
        DetachedCriteria dc = cdOrderWinnersDao.createDetachedCriteria();
        dc.add(Restrictions.eq("uid", uid));
        dc.add(Restrictions.eq(CdOrderWinners.FIELD_DEL_FLAG, CdOrderWinners.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));

        Criteria cri = dc.getExecutableCriteria(cdOrderWinnersDao.getSession());
        cri.setFirstResult(Integer.parseInt(total));
        cri.setMaxResults(Integer.parseInt(count));
        return cdOrderWinnersDao.find(dc);
    }


    public CdOrderWinners findByOrderNum(String winOrderNum) {
        DetachedCriteria dc = cdOrderWinnersDao.createDetachedCriteria();
        dc.add(Restrictions.eq("winOrderNum", winOrderNum));
        dc.add(Restrictions.eq(CdOrderWinners.FIELD_DEL_FLAG, CdOrderWinners.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));

        List<CdOrderWinners> list = cdOrderWinnersDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
