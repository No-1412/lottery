/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmagicorder.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cmagicorder.dao.CdMagicOrderDao;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicFollowOrder;
import com.youge.yogee.modules.cmagicorder.entity.CdMagicOrder;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 神单订单Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-05
 */
@Component
@Transactional(readOnly = true)
public class CdMagicOrderService extends BaseService {

    @Autowired
    private CdMagicOrderDao cdMagicOrderDao;

    public CdMagicOrder get(String id) {
        return cdMagicOrderDao.get(id);
    }

    public Page<CdMagicOrder> find(Page<CdMagicOrder> page, CdMagicOrder cdMagicOrder) {
        DetachedCriteria dc = cdMagicOrderDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdMagicOrder.getOrderNum())) {
            dc.add(Restrictions.eq("orderNum", cdMagicOrder.getOrderNum()));
        }
        dc.add(Restrictions.eq(CdMagicOrder.FIELD_DEL_FLAG, CdMagicOrder.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdMagicOrderDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdMagicOrder cdMagicOrder) {

        if (StringUtils.isEmpty(cdMagicOrder.getId())) {
            cdMagicOrder.setId(IdGen.uuid());
            cdMagicOrder.setCreateDate(DateUtils.getDateTime());
            cdMagicOrder.setDelFlag(CdMagicOrder.DEL_FLAG_NORMAL);
        }
        cdMagicOrderDao.save(cdMagicOrder);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdMagicOrderDao.deleteById(id);
    }


    public List<CdMagicOrder> getMagicOrder(String total, String count) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = df.format(day);

        DetachedCriteria dc = cdMagicOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdMagicOrder.FIELD_DEL_FLAG, CdMagicOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.gt("shutDownTime", today));
        dc.add(Restrictions.sqlRestriction("1=1 order by CAST(price as SIGNED) desc"));

        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdMagicOrderDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdMagicOrderDao.find(dc);
    }

    public BigDecimal findJoinFoByNumber(String orderNumber) {
        return cdMagicOrderDao.findJoinFoByNumber(orderNumber);
    }

    public CdMagicOrder findOrderByNumber(String orderNumber) {
        DetachedCriteria dc = cdMagicOrderDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdMagicOrder.FIELD_DEL_FLAG, CdMagicOrder.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("orderNum", orderNumber));
        List<CdMagicOrder> list = cdMagicOrderDao.find(dc);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

}
