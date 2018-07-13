/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.erp.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.erp.dao.ErpOrderRankDao;
import com.youge.yogee.modules.erp.entity.ErpOrderRank;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 排行Service
 *
 * @author RenHaipeng
 * @version 2018-03-08
 */
@Component
@Transactional(readOnly = true)
public class ErpOrderRankService extends BaseService {

    @Autowired
    private ErpOrderRankDao erpOrderRankDao;

    public ErpOrderRank get(String id) {
        return erpOrderRankDao.get(id);
    }

    public Page<ErpOrderRank> find(Page<ErpOrderRank> page, ErpOrderRank erpOrderRank) {
        DetachedCriteria dc = erpOrderRankDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(erpOrderRank.getName())) {
            dc.add(Restrictions.like("name", "%" + erpOrderRank.getName() + "%"));
        }
        dc.add(Restrictions.eq(ErpOrderRank.FIELD_DEL_FLAG, ErpOrderRank.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return erpOrderRankDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(ErpOrderRank erpOrderRank) {

        if (StringUtils.isEmpty(erpOrderRank.getId())) {
            erpOrderRank.setId(IdGen.uuid());
            erpOrderRank.setCreateDate(DateUtils.getDateTime());
            erpOrderRank.setDelFlag(ErpOrderRank.DEL_FLAG_NORMAL);
        }
        erpOrderRankDao.save(erpOrderRank);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        erpOrderRankDao.deleteById(id);
    }

    public void deleteAll() {
        erpOrderRankDao.deleteAll();
    }


    public List<Object> rank(Page<ErpOrderRank> page) {

        return erpOrderRankDao.rank(page);
    }

    public Page<Map<String, String>> findRank(Page<Map<String, String>> page, Map<String, Object> paramMap) {
        Date beginDate = DateUtils.parseDate(paramMap.get("beginDate"));
        if (beginDate == null) {
            beginDate = DateUtils.setDays(new Date(), 1);
            paramMap.put("beginDate", DateUtils.formatDate(beginDate, "yyyy-MM-dd"));
        }
        Date endDate = DateUtils.parseDate(paramMap.get("endDate"));
        if (endDate == null) {
            endDate = DateUtils.addDays(DateUtils.addMonths(beginDate, 1), -1);
            paramMap.put("endDate", DateUtils.formatDate(endDate, "yyyy-MM-dd"));
        }
        String bDate = DateUtils.formatDate(beginDate, "yyyy-MM-dd");
        String eDate = DateUtils.formatDate(endDate, "yyyy-MM-dd");
        Page<Map<String, String>> lists = new Page<>();
        System.out.println(page.getPageNo());
        int index = Integer.valueOf(page.getPageNo());
        Page datas = erpOrderRankDao.findRank(page, bDate, eDate);
        List<Map<String, String>> list = new ArrayList();
        for (int i = 0; i < datas.getList().size(); i++) {
            Object[] objs = (Object[]) datas.getList().get(i);
            Map data = new HashMap();
            data.put("saleid", objs[0] == null ? "0" : objs[0].toString());
            data.put("salename", objs[1] == null ? "0" : objs[1].toString());
            data.put("moneywithoutgendan", objs[2] == null ? "0" : objs[2].toString());
            data.put("moneygendan", objs[3] == null ? "0" : objs[3].toString());
            data.put("moneytotal", objs[4] == null ? "0" : objs[4].toString());
            data.put("num", objs[5] == null ? "0" : objs[5].toString());
            data.put("createdate", objs[6] == null ? "0" : objs[6].toString());
            data.put("role_name", objs[7] == null ? "0" : objs[7].toString());
            data.put("rowNo", (index - 1) * 10 + i+1);
            list.add(data);
        }
        datas.setList(list);
        return datas;

    }
}
