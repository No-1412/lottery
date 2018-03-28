/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.bm.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.bm.dao.BmPushMessageDao;
import com.youge.yogee.modules.bm.entity.BmPushMessage;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 推送消息Service
 *
 * @author ZhaoYiFeng
 * @version 2018-03-28
 */
@Component
@Transactional(readOnly = true)
public class BmPushMessageService extends BaseService {

    @Autowired
    private BmPushMessageDao bmPushMessageDao;

    public BmPushMessage get(String id) {
        return bmPushMessageDao.get(id);
    }

    public Page<BmPushMessage> find(Page<BmPushMessage> page, BmPushMessage bmPushMessage) {
        DetachedCriteria dc = bmPushMessageDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(bmPushMessage.getName())) {
            dc.add(Restrictions.like("message", "%" + bmPushMessage.getMessage() + "%"));
        }
        dc.add(Restrictions.eq(BmPushMessage.FIELD_DEL_FLAG, BmPushMessage.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return bmPushMessageDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(BmPushMessage bmPushMessage) {

        if (StringUtils.isEmpty(bmPushMessage.getId())) {
            bmPushMessage.setId(IdGen.uuid());
            bmPushMessage.setCreateDate(DateUtils.getDateTime());
            bmPushMessage.setDelFlag(BmPushMessage.DEL_FLAG_NORMAL);
        }
        bmPushMessageDao.save(bmPushMessage);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        bmPushMessageDao.deleteById(id);
    }

}
