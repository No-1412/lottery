package com.youge.yogee.modules.cdoptionpass.service;

import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.modules.cdoptionpass.dao.CdOptionPassDao;
import com.youge.yogee.modules.cdoptionpass.entity.CdOptionPassVo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 彩票打印service
 * Created by ab on 2018/4/4.
 */
@Component
@Transactional(readOnly = true)
public class CdOptionPassService extends BaseService{

    @Autowired
    private CdOptionPassDao cdOptionPassDao;

    /**
     * 根据彩票模板编号查询模板相关信息
     * @param number
     * @return
     */
    public CdOptionPassVo findByNumber(String number){
        DetachedCriteria dc = cdOptionPassDao.createDetachedCriteria();
        dc.add(Restrictions.eq("number",number));
        CdOptionPassVo cdOptionPassVo = cdOptionPassDao.find(dc).get(0);

        return cdOptionPassVo;
    }
}
