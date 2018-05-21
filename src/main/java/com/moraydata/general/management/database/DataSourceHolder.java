package com.moraydata.general.management.database;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.moraydata.general.management.util.CollectionUtils;
import com.moraydata.general.management.util.SpringUtils;
import com.moraydata.general.secondary.entity.Database;
import com.moraydata.general.secondary.service.DatabaseService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
@DependsOn({"springUtils"})
public class DataSourceHolder {
	
	public final Map<Object, Object> targetDataSources = new ConcurrentHashMap<Object, Object>();
	{
		DatabaseService databaseService = SpringUtils.getBean(DatabaseService.class);
		List<Database> databases = databaseService.get();
		if (CollectionUtils.isNotBlank(databases)) {
			databases.forEach(e -> {
				HikariConfig hikariConfig = new HikariConfig();
				hikariConfig.setJdbcUrl(e.getJdbcUrl());
				hikariConfig.setUsername(e.getUsername());
				hikariConfig.setPassword(e.getPassword());
				targetDataSources.put(e.getUserId(), new HikariDataSource(hikariConfig));
			});
		}
	}
}
