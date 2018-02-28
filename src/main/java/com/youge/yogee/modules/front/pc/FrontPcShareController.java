/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.modules.front.pc;

import com.youge.yogee.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站Controller
 */
@Controller
@RequestMapping(value = "${sharePcFrontPath}")
public class FrontPcShareController extends BaseController{

	
	/**
	 * anbo,2017-10-24,pc网站首页
	 */
	/*@RequestMapping
	public String index() {
		return "modules/share/pc/index";
	}*/

	@RequestMapping
	public String index() {
		return "modules/pc/index";
	}
	

	
}
