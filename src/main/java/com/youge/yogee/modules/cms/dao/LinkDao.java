/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.modules.cms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.cms.entity.Link;

/**
 * 链接DAO接口
 */
@Repository
public class LinkDao extends BaseDao<Link> {
	
	public List<Link> findByIdIn(Long[] ids){
		return find("front Like where id in (:p1)", new Parameter(new Object[]{ids}));
	}
	
	public int updateExpiredWeight(){
		return update("update Link set weight=0 where weight > 0 and weightDate < current_timestamp()");
	}
}
