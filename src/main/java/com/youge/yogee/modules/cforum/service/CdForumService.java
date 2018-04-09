/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cforum.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.cforum.dao.CdForumDao;
import com.youge.yogee.modules.cforum.entity.CdForum;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 论坛Service
 *
 * @author WeiJinChao
 * @version 2017-12-14
 */
@Component
@Transactional(readOnly = true)
public class CdForumService extends BaseService {

    @Autowired
    private CdForumDao cdForumDao;

    public CdForum get(String id) {
        return cdForumDao.get(id);
    }

    public Page<CdForum> find(Page<CdForum> page, CdForum cdForum) {
        DetachedCriteria dc = cdForumDao.createDetachedCriteria();
        if (StringUtils.isNotEmpty(cdForum.getName())) {
            dc.add(Restrictions.like("name", "%" + cdForum.getName() + "%"));
        }
        dc.add(Restrictions.eq(CdForum.FIELD_DEL_FLAG, CdForum.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("id"));
        return cdForumDao.find(page, dc);
    }

    @Transactional(readOnly = false)
    public void save(CdForum cdForum) {
        if (StringUtils.isEmpty(cdForum.getId())) {
            cdForum.setId(IdGen.uuid());
            cdForum.setCreateDate(DateUtils.getDateTime());
            cdForum.setDelFlag(CdForum.DEL_FLAG_NORMAL);
        }
        cdForumDao.save(cdForum);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        cdForumDao.deleteById(id);
    }

    /**
     * 查询论坛帖子
     *
     * @return
     */
    @Transactional(readOnly = false)
    public List<CdForum> getForum(String forumType, String total, String count) {
        DetachedCriteria dc = cdForumDao.createDetachedCriteria();
        dc.add(Restrictions.eq("isPosts", "1")); //1代表帖子
        dc.add(Restrictions.eq("forumType", forumType)); //球爆单0竞彩足球1竞彩篮球2
        dc.add(Restrictions.eq(CdForum.FIELD_DEL_FLAG, CdForum.DEL_FLAG_NORMAL));
        dc.addOrder(Order.desc("createDate"));
        // 限制条数|分页
        Criteria cri = dc.getExecutableCriteria(cdForumDao.getSession());
        cri.setMaxResults(Integer.parseInt(count));
        cri.setFirstResult(Integer.parseInt(total));
        return cdForumDao.find(dc);
    }

    /**
     * 查询下边的所有回复内容
     *
     * @return
     */
    @Transactional(readOnly = false)
    public List getForumPing(String ids) {
        return cdForumDao.findBySql("select user_name,parents_user,content,id,dianzan_count,user_id from cd_forum where parents_id like '%" + ids + "%' Order by create_date asc");
    }

    /**
     * 查询所有帖子
     */
    @Transactional(readOnly = false)
    public List getForumTie(String ids) {
        return cdForumDao.findBySql("select user_name,parents_user,content,id,dianzan_count from cd_forum where parents_id like '%" + ids + "%' ");
    }
}
