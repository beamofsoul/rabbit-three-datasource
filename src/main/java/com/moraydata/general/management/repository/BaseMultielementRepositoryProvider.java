package com.moraydata.general.management.repository;

/**
 * 
 * @ClassName BaseMultielementRepositoryProvider
 * @Description BaseMultielementRepository实例提供器
 * @author MingshuJian
 * @Date 2017年8月29日 下午3:50:57
 * @version 1.0.0
 */
public interface BaseMultielementRepositoryProvider {
	
	BaseMultielementRepositoryProvider initialize(Object... args);
	BaseMultielementRepository<?, ?> provide();
	BaseMultielementRepository<?, ?> provide(Object... args);
	Class<?> getReopositoryImplementClass();
}
