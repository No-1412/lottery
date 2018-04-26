/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cbannerimg.service;

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
import com.youge.yogee.modules.cbannerimg.entity.CdBannerImg;
import com.youge.yogee.modules.cbannerimg.dao.CdBannerImgDao;

import java.util.List;

/**
 * 轮播图片Service
 *
 * @author WeiJinChao
 * @version 2017-12-15
 */
@Component
@Transactional(readOnly = true)
public class CdBannerImgService extends BaseService {

    @Autowired
    private CdBannerImgDao cdBannerImgDao;

    public CdBannerImg get(String id) {
        return cdBannerImgDao.get(id);
    }

    public Page<CdBannerImg> find(Page<CdBannerImg> page, CdBannerImg cdBannerImg) {
        DetachedCriteria dc = cdBannerImgDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdBannerImg.getName())) {
            dc.add(Restrictions.like("name", "%" + cdBannerImg.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdBannerImg.FIELD_DEL_FLAG, CdBannerImg.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdBannerImgDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdBannerImg cdBannerImg) {

        if (StringUtils.isEmpty(cdBannerImg.getId())) {
            cdBannerImg.setId(IdGen.uuid());
            cdBannerImg.setCreateDate(DateUtils.getDateTime());
            cdBannerImg.setDelFlag(CdBannerImg.DEL_FLAG_NORMAL);
        }
        cdBannerImgDao.save(cdBannerImg);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdBannerImgDao.deleteById(id);
    }

    //轮播图片
    public List<CdBannerImg> getBannerImg() {
        DetachedCriteria dc = cdBannerImgDao.createDetachedCriteria();
        dc.add(Restrictions.eq("isUse", "1"));
        dc.add(Restrictions.eq(CdBannerImg.FIELD_DEL_FLAG, CdBannerImg.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        return cdBannerImgDao.find(dc);
    }
}
