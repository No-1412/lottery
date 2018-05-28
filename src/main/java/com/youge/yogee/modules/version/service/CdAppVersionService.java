/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.version.service;

import com.youge.yogee.common.persistence.Parameter;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.modules.version.entity.CdAppVersion;
import com.youge.yogee.modules.version.dao.CdAppVersionDao;

import java.util.List;

/**
 * 版本信息Service
 *
 * @author ZhaoYiFeng
 * @version 2018-05-28
 */
@Component
@Transactional(readOnly = true)
public class CdAppVersionService extends BaseService {

    @Autowired
    private CdAppVersionDao cdAppVersionDao;

    public CdAppVersion get(String id) {
        return cdAppVersionDao.get(id);
    }

    public CdAppVersion getByVersionCode(String versionCode) {
        DetachedCriteria dc = cdAppVersionDao.createDetachedCriteria();
        dc.add(Restrictions.eq(CdAppVersion.FIELD_DEL_FLAG, CdAppVersion.DEL_FLAG_NORMAL));
        dc.add(Restrictions.eq("versionCode", versionCode));
        List<CdAppVersion> list = cdAppVersionDao.find(dc);
        if(list.size()>0){
            return  list.get(0);
        }else {
            return null;
        }
    }

    public Page<CdAppVersion> find(Page<CdAppVersion> page, CdAppVersion cdAppVersion) {
        DetachedCriteria dc = cdAppVersionDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdAppVersion.getVersionName())) {
            dc.add(Restrictions.like("versionName", "%" + cdAppVersion.getVersionName() + "%"));
        }
        if (StringUtils.isNotEmpty(cdAppVersion.getVersionCode())) {
            dc.add(Restrictions.eq("versionCode", cdAppVersion.getVersionCode()));
        }
        if (StringUtils.isNotEmpty(cdAppVersion.getVersionChannel())) {
            dc.add(Restrictions.eq("versionChannel", cdAppVersion.getVersionChannel()));
        }
        dc.add(Restrictions.eq(CdAppVersion.FIELD_DEL_FLAG, CdAppVersion.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdAppVersionDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdAppVersion cdAppVersion) {

        if (StringUtils.isEmpty(cdAppVersion.getId())) {
            cdAppVersion.setId(IdGen.uuid());
            cdAppVersion.setCreateDate(DateUtils.getDateTime());
            cdAppVersion.setDelFlag(CdAppVersion.DEL_FLAG_NORMAL);
        }
        cdAppVersion.setUpdateDate(DateUtils.getDateTime());
        cdAppVersionDao.save(cdAppVersion);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdAppVersionDao.deleteById(id);
    }

}
