package com.moraydata.general.management.system;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.util.SpringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AnnotationRepositoryClassMapping
 * @Description 注解'@Repository'的持久化类映射工具类
 * @author MingshuJian
 * @Date 2017年4月7日 下午2:37:33
 * @version 1.0.0
 */
@Slf4j
public class AnnotationRepositoryNameMapping {

	public static final Map<String, String> REPOSITORY_MAP = new HashMap<>();
	
	public static void loadRepositoryMap() {
		log.debug("开始加载持久化注解名称映射信息...");
		if (REPOSITORY_MAP.size() > 0) REPOSITORY_MAP.clear(); 
		Map<String, Object> beansWithAnnotation = SpringUtils.getApplicationContext().getBeansWithAnnotation(Repository.class);
		for (String key : beansWithAnnotation.keySet()) REPOSITORY_MAP.put(key.replace("Repository", "").toLowerCase(), key);
		log.debug("持久化注解名称映射信息加载完毕...");
	}
}
