package com.moraydata.general.management.database;

import java.util.Map;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	public DynamicDataSource(Map<Object, Object> targetDataSources, Object defaultTargetDataSource) {
		this.setTargetDataSources(targetDataSources);
		this.setDefaultTargetDataSource(defaultTargetDataSource);
	}

	@Override
    protected Object determineCurrentLookupKey() {
        return DataSourceLocal.get();
    }
}
