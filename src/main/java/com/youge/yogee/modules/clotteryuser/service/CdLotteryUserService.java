/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.clotteryuser.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.interfaces.util.HttpResultUtil;
import com.youge.yogee.modules.clotteryuser.dao.CdLotteryUserDao;
import com.youge.yogee.modules.clotteryuser.entity.CdLotteryUser;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户注册Service
 *
 * @author WeiJinChao
 * @version 2017-12-07
 */
@Component
@Transactional(readOnly = true)
public class CdLotteryUserService extends BaseService {

    @Autowired
    private CdLotteryUserDao cdLotteryUserDao;

    public CdLotteryUser get(String id) {
        return cdLotteryUserDao.get(id);
    }

    public Page<CdLotteryUser> find(Page<CdLotteryUser> page, CdLotteryUser cdLotteryUser) {
        DetachedCriteria dc = cdLotteryUserDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdLotteryUser.getName())) {
            dc.add(Restrictions.like("name", "%" + cdLotteryUser.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdLotteryUser.FIELD_DEL_FLAG, CdLotteryUser.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdLotteryUserDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdLotteryUser cdLotteryUser) {

        if (StringUtils.isEmpty(cdLotteryUser.getId())) {
            cdLotteryUser.setId(IdGen.uuid());
            cdLotteryUser.setCreateDate(DateUtils.getDateTime());
            cdLotteryUser.setDelFlag(CdLotteryUser.DEL_FLAG_NORMAL);
        }
        cdLotteryUserDao.save(cdLotteryUser);
    }
    //判断昵称是否已被使用
    public int findByName(String name) {
        DetachedCriteria dc = cdLotteryUserDao.createDetachedCriteria();
        dc.add(Restrictions.eq("name", name));
        dc.add(Restrictions.eq(CdLotteryUser.FIELD_DEL_FLAG, CdLotteryUser.DEL_FLAG_NORMAL));
        List<CdLotteryUser> list = cdLotteryUserDao.find(dc);
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }
    //根据手机号获取用户信息
    public CdLotteryUser findByPhone(String phone) {
        DetachedCriteria dc = cdLotteryUserDao.createDetachedCriteria();
        dc.add(Restrictions.eq("mobile", phone));
        dc.add(Restrictions.eq(CdLotteryUser.FIELD_DEL_FLAG, CdLotteryUser.DEL_FLAG_NORMAL));
        List<CdLotteryUser> list = cdLotteryUserDao.find(dc);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdLotteryUserDao.deleteById(id);
    }

    //判断手机号是否注册
    public Long getCountByPhone(String phone) {
        DetachedCriteria dc = cdLotteryUserDao.createDetachedCriteria();
        dc.add(Restrictions.eq("mobile", phone));
        dc.add(Restrictions.eq(CdLotteryUser.FIELD_DEL_FLAG, CdLotteryUser.DEL_FLAG_NORMAL));
        return cdLotteryUserDao.count(dc);
    }

    //2017-12-01,判断用户信息
    public String checkUser(CdLotteryUser user, Integer i) {

        if (user == null) {
            logger.error("当前用户不存在!");
            return HttpResultUtil.errorJson("当前用户不存在!");
        }
        if (user != null) {
            if (user.getIsBlock().equals("1")) {
                logger.error("当前用户已锁定，不允许操作!");
                return HttpResultUtil.errorJson("当前用户已锁定，不允许操作!");
            }
            if (user.getIsRegisterCharge().equals("0")) {
                logger.error("当前用户未充值入会费!");
                return HttpResultUtil.errorJson("当前用户未充值入会费!");
            }
            if (i == 2) {
                if (user.getMemberType().equals("0")) {
                    logger.error("当前用户不是永久会员!");
                    return HttpResultUtil.errorJson("当前用户不是永久会员!");
                }
            }
        }

        return null;
    }


    //根据手机号获取用户信息
    public List<CdLotteryUser> findCatchTimes() {
        DetachedCriteria dc = cdLotteryUserDao.createDetachedCriteria();
        dc.add(Restrictions.ne("catchTimes", "3"));
        dc.add(Restrictions.eq(CdLotteryUser.FIELD_DEL_FLAG, CdLotteryUser.DEL_FLAG_NORMAL));

        return cdLotteryUserDao.find(dc);

    }

}
