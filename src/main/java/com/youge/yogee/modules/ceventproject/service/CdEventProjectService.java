/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.ceventproject.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.ceventproject.dao.CdEventProjectDao;
import com.youge.yogee.modules.ceventproject.entity.CdEventProject;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 赛事专题Service
 *
 * @author WeiJinChao
 * @version 2017-12-18
 */
@Component
@Transactional(readOnly = true)
public class CdEventProjectService extends BaseService {

    @Autowired
    private CdEventProjectDao cdEventProjectDao;

    public CdEventProject get(String id) {
        return cdEventProjectDao.get(id);
    }

    public Page<CdEventProject> find(Page<CdEventProject> page, CdEventProject cdEventProject) {
        DetachedCriteria dc = cdEventProjectDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdEventProject.getName())) {
            dc.add(Restrictions.like("name", "%" + cdEventProject.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdEventProject.FIELD_DEL_FLAG, CdEventProject.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdEventProjectDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdEventProject cdEventProject) {

        if (StringUtils.isEmpty(cdEventProject.getId())) {
            cdEventProject.setId(IdGen.uuid());
            cdEventProject.setCreateDate(DateUtils.getDateTime());
            cdEventProject.setDelFlag(CdEventProject.DEL_FLAG_NORMAL);
        }
        cdEventProjectDao.save(cdEventProject);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdEventProjectDao.deleteById(id);
    }

    @Transactional(readOnly = false)
    public List<CdEventProject> getEvent() {
        DetachedCriteria dc = cdEventProjectDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdEventProject.FIELD_DEL_FLAG, CdEventProject.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdEventProjectDao.find(dc);
    }
}
