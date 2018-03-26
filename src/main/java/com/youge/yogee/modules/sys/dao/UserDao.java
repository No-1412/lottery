/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.modules.sys.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.sys.entity.User;

/**
 * 用户DAO接口
 */
@Repository
public class UserDao extends BaseDao<User> {
	
	public List<User> findAllList() {
		return find("from User where delFlag=:p1 order by id", new Parameter(User.DEL_FLAG_NORMAL));
	}
	
	public User findByLoginName(String loginName){
		return getByHql("from User where loginName = :p1 and delFlag = :p2", new Parameter(loginName, User.DEL_FLAG_NORMAL));
	}

	public int updatePasswordById(String newPassword, String id){
		return update("update User set password=:p1 where id = :p2", new Parameter(newPassword, id));
	}
	
	public int updateLoginInfo(String loginIp, Date loginDate, String id){
		return update("update User set loginIp=:p1, loginDate=:p2 where id = :p3", new Parameter(loginIp, loginDate, id));
	}

	//充值轮播墙
	public List<Map<String, String>> findRechargeList(String count) {
		String sql = "SELECT su.NAME AS salename,clu.name AS uname,cr.recharge_money AS money,cr.create_date AS createdate,cr.user_id as userid,clu.sale_id as saleid " +
				" FROM cd_recharge cr" +
				" LEFT JOIN cd_lottery_user clu ON cr.user_id = clu.id " +
				" LEFT JOIN sys_user su ON su.id = clu.sale_id " +
				" LIMIT 0," + count;
		return findBySql(sql);
	}

	//开户轮播墙
	public List<Map<String, String>> findRegisterList(String count) {
		String sql = "SELECT su.name as salename, clu.name as uname,clu.create_date as createdate " +
				" FROM cd_lottery_user clu LEFT JOIN sys_user su on clu.sale_id = su.id " +
				" ORDER BY createdate DESC LIMIT 0," + count;
		return findBySql(sql);
	}
	
}
