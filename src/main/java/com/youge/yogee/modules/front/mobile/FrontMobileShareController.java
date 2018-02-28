/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.modules.front.mobile;

import com.youge.yogee.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站Controller
 */
@Controller
@RequestMapping(value = "${shareMobileFrontPath}")
public class FrontMobileShareController extends BaseController{

	
	/**
	 * anbo,2017-10-24,pc网站首页
	 */
	@RequestMapping
	public String index() {
		//return "modules/share/m/m_index";
		return "modules/share/m/index";
	}

	
}
