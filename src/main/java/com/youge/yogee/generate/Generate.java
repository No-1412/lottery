/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.generate;

import com.google.common.collect.Maps;
import com.youge.yogee.common.config.Global;
import com.youge.yogee.common.utils.DateUtils;
import com.youge.yogee.common.utils.FileUtils;
import com.youge.yogee.common.utils.FreeMarkers;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 * @author ThinkGem
 * @version 2013-06-21
 */
public class Generate {
	
	private static Logger logger = LoggerFactory.getLogger(Generate.class);

	private static String DRIVER = Global.getConfig("jdbc.driver");
	private static String USER = Global.getConfig("jdbc.username");
	private static String PWD = Global.getConfig("jdbc.password");
	private static String URL = Global.getConfig("jdbc.url");

	public static void main(String[] args) throws Exception {

		// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

		/**
		 * 单个生成
		 * **/
		createOne("cbbnotfinsh","CdBbNotFinishCollection","cd_bb_notfinish_collection","篮球比赛收藏");

		/**
		 * 批量生成
		 * **/
		//createAuto("sys_");


	}

	/**
	 * 创建单个Entity
	 * @param moduleName 所属模块
	 * @param className	实体类名
	 * @param tableName	关联表名
	 * @param functionName 业务名
	 * @throws Exception
	 */
	public static void createOne(String moduleName, String className, String tableName, String functionName) throws Exception {
		createAuto(moduleName,className,tableName,functionName);
	}

	/**
	 * 批量创建Entity
	 * @param without 排除的模块
	 * @throws Exception
	 */
	public static void createAuto(String without) throws Exception {

		//获取链接
		Connection conn = DB4Mysql.getConnection(URL,USER,PWD);

		List<String> tables = DB4Mysql.getAllTableName(conn);
		System.out.println(tables);

		List<String> comments = DB4Mysql.getCommentListByTableName(tables, conn);
		System.out.println(comments);

		for(int i=0; i<tables.size();i++){

			String moduleName = tables.get(i).split("_")[0];
			String className = getHump(tables.get(i));

			DB4Mysql db4Mysql = new DB4Mysql();
			if(!tables.get(i).startsWith(without)){
				createAuto(moduleName, className, tables.get(i), comments.get(i));
				db4Mysql.tableToEntity(tables.get(i),conn);
			}

		}

		//关闭链接
		conn.close();
	}


	/**
	 * 自动生成文件的方法
	 * @user RenHaipeng
	 */
	public static void createAuto(String moduleName, String className, String tableName, String functionName) throws Exception {
		// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================

		// 主要提供基本功能模块代码生成。
		// 目录生成结构：{packageName}/{moduleName}/{dao,entity,service,web}/{subModuleName}/{className}

		// packageName 包名，这里如果更改包名，请在applicationContext.xml和srping-mvc.xml中配置base-package、packagesToScan属性，来指定多个（共4处需要修改）。
		String packageName = "com.youge.yogee.modules";

//		String moduleName = "unused";			// 模块名，例：sys
		String subModuleName = "";				// 子模块名（可选）
//		String tableName = "unused_message";			// 模块名，例：sys
//		String className = "unusedMessage";			// 类名，例：user
		String classAuthor = "ZhaoYiFeng";		// 类作者，例：ThinkGem
//		String functionName = "闲置留言";		// 功能名，例：用户

		// 是否启用生成工具
		Boolean isEnable = true;

		// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================

		if (!isEnable){
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}

		if (StringUtils.isBlank(moduleName) || StringUtils.isBlank(moduleName)
				|| StringUtils.isBlank(className) || StringUtils.isBlank(functionName)){
			logger.error("参数设置错误：包名、模块名、类名、功能名不能为空。");
			return;
		}

		// 获取文件分隔符
		String separator = File.separator;

		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"main").exists()){
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);

		// 模板文件路径
		String tplPath = StringUtils.replace(projectPath+"/src/main/java/com/youge/yogee/generate/template", "/", separator);
		logger.info("Template Path: {}", tplPath);

		// Java文件路径
		String javaPath = StringUtils.replaceEach(projectPath+"/src/main/java/"+StringUtils.lowerCase(packageName),
				new String[]{"/", "."}, new String[]{separator, separator});
		logger.info("Java Path: {}", javaPath);

		// 视图文件路径
		String viewPath = StringUtils.replace(projectPath+"/src/main/webapp/WEB-INF/views", "/", separator);
		logger.info("View Path: {}", viewPath);

		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(tplPath));

		// 定义模板变量
		Map<String, String> model = Maps.newHashMap();
		model.put("packageName", StringUtils.lowerCase(packageName));
		model.put("moduleName", StringUtils.lowerCase(moduleName));
		model.put("subModuleName", StringUtils.isNotBlank(subModuleName)?"."+StringUtils.lowerCase(subModuleName):"");
		model.put("className", StringUtils.uncapitalize(className));
		model.put("ClassName", StringUtils.capitalize(className));
		model.put("classAuthor", StringUtils.isNotBlank(classAuthor)?classAuthor:"ZhaoYiFeng");
		model.put("classVersion", DateUtils.getDate());
		model.put("functionName", functionName);
		model.put("tableName", StringUtils.lowerCase(tableName));
//		model.put("tableName", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
//				?"_"+StringUtils.lowerCase(subModuleName):"")+"_"+model.get("className"));
		model.put("urlPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
				?"/"+StringUtils.lowerCase(subModuleName):"")+"/"+model.get("className"));
		model.put("viewPrefix", //StringUtils.substringAfterLast(model.get("packageName"),".")+"/"+
				model.get("urlPrefix"));
		model.put("permissionPrefix", model.get("moduleName")+(StringUtils.isNotBlank(subModuleName)
				?":"+StringUtils.lowerCase(subModuleName):"")+":"+model.get("className"));

		// 生成 Entity
		Template template = cfg.getTemplate("entity.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath+separator+model.get("moduleName")+separator+"entity"
				+separator+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+".java";
		writeFile(content, filePath);
		logger.info("Entity: {}", filePath);

		// 生成 Dao
		template = cfg.getTemplate("dao.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("moduleName")+separator+"dao"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Dao.java";
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);

		// 生成 Service
		template = cfg.getTemplate("service.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("moduleName")+separator+"service"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Service.java";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);

		// 生成 Controller
		template = cfg.getTemplate("controller.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+model.get("moduleName")+separator+"web"+separator
				+StringUtils.lowerCase(subModuleName)+separator+model.get("ClassName")+"Controller.java";
		writeFile(content, filePath);
		logger.info("Controller: {}", filePath);

		// 生成 ViewForm
		template = cfg.getTemplate("viewForm.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName"),".")
				+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
				+separator+model.get("className")+"Form.jsp";
		writeFile(content, filePath);
		logger.info("ViewForm: {}", filePath);

		// 生成 ViewList
		template = cfg.getTemplate("viewList.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = viewPath+separator+StringUtils.substringAfterLast(model.get("packageName"),".")
				+separator+model.get("moduleName")+separator+StringUtils.lowerCase(subModuleName)
				+separator+model.get("className")+"List.jsp";
		writeFile(content, filePath);
		logger.info("ViewList: {}", filePath);

		logger.info("Generate Success.");
	}

	/**
	 * 获取驼峰样式表名
	 * 如果表名中含有_将后面的第一个字母大写，目前只测试一级驼峰好用。
	 *
	 * @user RenHaipeng
	 */
	public static
	String getHump(String normal){
		char[] ch = normal.toCharArray();
		char c = 'a';
		for (int j = 0; j < ch.length; j++) {
			c = ch[j];
			if (c == '_') {
				if (ch[j + 1] >= 'a' && ch[j + 1] <= 'z') {
					ch[j + 1] = (char) (ch[j + 1] - 32);
				}
			}
		}
		String hump = new String(ch).replace("_","");
		return hump;
	}
	
	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	public static void writeFile(String content, String filePath) {
		try {
			if (FileUtils.createFile(filePath)){
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
