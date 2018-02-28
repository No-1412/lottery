/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.cmessage.service;

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
import com.youge.yogee.modules.cmessage.entity.CdMessage;
import com.youge.yogee.modules.cmessage.dao.CdMessageDao;

import java.util.List;

/**
 * 短信信息表Service
 * @author WeiJinChao
 * @version 2017-12-08
 */
@Component
@Transactional(readOnly = true)
public class CdMessageService extends BaseService {

	@Autowired
	private CdMessageDao cdMessageDao;
	
	public CdMessage get(String id) {
		return cdMessageDao.get(id);
	}
	
	public Page<CdMessage> find(Page<CdMessage> page, CdMessage cdMessage) {
		DetachedCriteria dc = cdMessageDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(cdMessage.getName())){
			dc.add(Restrictions.like("name", "%"+cdMessage.getName()+"%"));
		}
		dc.add(Restrictions.eq(CdMessage.FIELD_DEL_FLAG, CdMessage.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return cdMessageDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(CdMessage cdMessage) {

		if(StringUtils.isEmpty(cdMessage.getId())){
			cdMessage.setId(IdGen.uuid());
			cdMessage.setCreateDate(DateUtils.getDateTime());
			cdMessage.setDelFlag(CdMessage.DEL_FLAG_NORMAL);
		}
		cdMessageDao.save(cdMessage);
	}
	/**
	 * 查询短信验证码
	 * @param mobile
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<CdMessage> getMessage(String mobile){
		DetachedCriteria dc = cdMessageDao.createDetachedCriteria();
		dc.add(Restrictions.eq("checkZt","0"));
		dc.add(Restrictions.eq("phone",mobile));
		dc.add(Restrictions.eq(CdMessage.FIELD_DEL_FLAG, CdMessage.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("createDate"));
		return cdMessageDao.find(dc);
	}
	/**
	 * 查询条数
	 */
	@Transactional(readOnly = false)
	public List getCount(String mobile,String date){
		List list = cdMessageDao.findmessageCount(mobile,date);
		return list;
	}
	@Transactional(readOnly = false)
	public void delete(String id) {
		cdMessageDao.deleteById(id);
	}

	/**
	 * 获取时间和验证码
	 * @param phone
	 * @return
	 */
	public List findDateAndCode(String phone) {

		List codeList = cdMessageDao.findDateAndCode(phone);

		return codeList;
	}

}
