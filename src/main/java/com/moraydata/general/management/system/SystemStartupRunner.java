package com.moraydata.general.management.system;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.moraydata.general.management.repository.BaseJpaRepositoryConfiguration;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SystemStartupRunner implements CommandLineRunner {
	
	@Override
	public void run(String... args) throws Exception {
		log.info("服务启动开始,执行加载数据等操作...");
		BaseJpaRepositoryConfiguration.removeGeneratedSharedEntityManagerCreatorBeanDefinitions();
		DatabaseTableNameContainer.loadDatabaseTableNames();
		DatabaseTableEntityMapping.initTableEntityMap();
		AnnotationServiceNameMapping.loadServiceMap();
		AnnotationRepositoryNameMapping.loadRepositoryMap();
		log.info("服务启动完毕...");
	}
}
