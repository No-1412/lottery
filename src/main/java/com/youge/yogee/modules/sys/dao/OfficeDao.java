/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.modules.sys.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.youge.yogee.common.persistence.BaseDao;
import com.youge.yogee.common.persistence.Parameter;
import com.youge.yogee.modules.sys.entity.Office;

/**
 * 机构DAO接口
 */
@Repository
public class OfficeDao extends BaseDao<Office> {
	
	public List<Office> findByParentIdsLike(String parentIds){
		return find("from Office where parentIds like :p1", new Parameter(parentIds));
	}
	
//	@Query("from Office where (id=?1 or parent.id=?1 or parentIds like ?2) and delFlag='" + Office.DEL_FLAG_NORMAL + "' order by code")
//	public List<Office> findAllChild(Long parentId, String likeParentIds);
	
}
