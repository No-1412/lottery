/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cchoosenine.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cchoosenine.dao.CdChooseNineDao;
import com.youge.yogee.modules.cchoosenine.entity.CdChooseNine;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 任选九开奖Service
 *
 * @author RenHaipeng
 * @version 2018-01-05
 */
@Component
@Transactional(readOnly = true)
public class CdChooseNineService extends BaseService {

    @Autowired
    private CdChooseNineDao cdChooseNineDao;

    public CdChooseNine get(String id) {
        return cdChooseNineDao.get(id);
    }

    public Page<CdChooseNine> find(Page<CdChooseNine> page, CdChooseNine cdChooseNine) {
        DetachedCriteria dc = cdChooseNineDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdChooseNine.getName())) {
            dc.add(Restrictions.like("name", "%" + cdChooseNine.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdChooseNine.FIELD_DEL_FLAG, CdChooseNine.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdChooseNineDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdChooseNine cdChooseNine) {

        if (StringUtils.isEmpty(cdChooseNine.getId())) {
            cdChooseNine.setId(IdGen.uuid());
            cdChooseNine.setCreateDate(DateUtils.getDateTime());
            cdChooseNine.setDelFlag(CdChooseNine.DEL_FLAG_NORMAL);
        }
        cdChooseNineDao.save(cdChooseNine);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdChooseNineDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public void deleteChooseNine() {
        cdChooseNineDao.update("delete from CdChooseNine");
    }

    @Transactional(readOnly = false)
    public List<CdChooseNine> getChooseNine(){
        DetachedCriteria dc = cdChooseNineDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdChooseNine.FIELD_DEL_FLAG, CdChooseNine.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdChooseNineDao.find(dc);
    }

    public CdChooseNine findByWeekday(String weekday){
        DetachedCriteria dc = cdChooseNineDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdChooseNine.FIELD_DEL_FLAG, CdChooseNine.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("matchId", "第"+weekday+"期"));
        dc.addOrder(Order.desc("createDate"));
        List<CdChooseNine> list =  cdChooseNineDao.find(dc);
        if(list.size() == 0){
            return null;
        }else {
            return list.get(0);
        }
    }
}
