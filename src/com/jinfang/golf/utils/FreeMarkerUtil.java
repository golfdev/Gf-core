package com.jinfang.golf.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;


import freemarker.cache.MruCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerUtil {

	private static Configuration configuration= null;
	
	private static final Logger log= Logger.getLogger(FreeMarkerUtil.class);

	static{
		configuration = new Configuration();
		configuration.setClassForTemplateLoading(FreeMarkerUtil.class, "/template/");
		configuration.setDefaultEncoding("UTF-8");
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		configuration.setNumberFormat("0");//数字按字符输出
		configuration.setCacheStorage(new MruCacheStorage(20, 250));
	}
	
	public static String processTemplate(String templatePath, Map<String, Object> templateData) {
		try {
			Template template = configuration.getTemplate(templatePath);
			// 1K buffer
			StringWriter sw = new StringWriter(1024 * 1);
			template.process(templateData, sw);
			if (log.isDebugEnabled()) {
				log.debug("模板解析结果：" + sw.toString());
			}	
			return sw.toString();
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}
	
}
