/**
 * There are <a href="https://github.com/thinkgem/jeesite">JeeSite</a> code generation
 */
package com.youge.yogee.modules.lom.service;

import com.youge.yogee.common.persistence.Page;
import com.youge.yogee.common.service.BaseService;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.IdGen;
import com.youge.yogee.common.utils.StringUtils;
import com.youge.yogee.modules.lom.dao.LomCodeDao;
import com.youge.yogee.modules.lom.entity.LomCode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 验证码记录Service
 * @author RenHaipeng
 * @version 2017-03-10
 */
@Component
@Transactional(readOnly = true)
public class LomCodeService extends BaseService {

	@Autowired
	private LomCodeDao lomCodeDao;
	
	public LomCode get(String id) {
		return lomCodeDao.get(id);
	}
	
	public Page<LomCode> find(Page<LomCode> page, LomCode lomCode) {
		DetachedCriteria dc = lomCodeDao.createDetachedCriteria();
//		if (StringUtils.isNotEmpty(lomCode.getName())){
//			dc.add(Restrictions.like("name", "%"+lomCode.getName()+"%"));
//		}
		dc.add(Restrictions.eq(LomCode.FIELD_DEL_FLAG, LomCode.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return lomCodeDao.find(page, dc);
	}
	
	@Transactional(readOnly = false)
	public void save(LomCode lomCode) {

		if(StringUtils.isEmpty(lomCode.getId())){
			lomCode.setId(IdGen.uuid());
			lomCode.setCreateDate(DateUtils.getDateTime());
			lomCode.setInvalidDate(DateUtils.formatDate(DateUtils.addSeconds(new Date(), 300),"yyyy-MM-dd HH:mm:ss"));
			lomCode.setDelFlag(LomCode.DEL_FLAG_NORMAL);
		}
		lomCodeDao.save(lomCode);
	}
	
	@Transactional(readOnly = false)
	public void delete(String id) {
		lomCodeDao.deleteById(id);
	}


	/******************************************************************************************************************/
	/**
	 *验证手机号和验证码
	 * */
	public String checkPhoneCode(String phone, String type){
		String num = "";
		List count = lomCodeDao.getPhoneCode(phone, type);
		if(count.size() > 0){
			num = count.get(0).toString();
		}
		return num;
	}
}
