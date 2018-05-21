package com.moraydata.general.management.database;

public class DataSourceLocal {

	private static final ThreadLocal<Long> dataSourceLocal = new ThreadLocal<Long>();
	
    public static void set(Long dataSourceNumber) {
        dataSourceLocal.set(dataSourceNumber);
    }
    
    public static Long get() {
        return (Long) dataSourceLocal.get();
    }
    
    public static void clear() {
        dataSourceLocal.remove();
    }
}
