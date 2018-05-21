package com.moraydata.general.management.repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

/**
 * @ClassName BaseMultielementRepositoryFactory
 * @Description 创建BaseMultielementRepository实现类的BaseMultielementRepositoryProvider实例
 * @author MingshuJian
 * @Date 2017年8月29日 下午4:23:17
 * @version 1.0.0
 */
@Component
public class BaseMultielementRepositoryFactory implements BaseMultielementRepositoryProvider {
	
	private Class<?> domainClass;
	private EntityManager entityManager;
	
	private BaseMultielementRepositoryFactory() {}
	
	@Override
	public BaseMultielementRepositoryProvider initialize(Object... args) {
		if (args != null && args.length > 0) {
			for (Object object : args) {
				if (object instanceof EntityManager) {
					entityManager = (EntityManager) object;
				} else if (object instanceof Class<?>) {
					domainClass = (Class<?>) object;
				}
			}
		}
		return this;
	}
	
	private BaseMultielementRepository<?, ?> doProvide() {
		BaseMultielementRepository<?, ?> implementation = null;
		try {
			Constructor<?> constructor = this.getReopositoryImplementClass().getConstructor(Class.class, EntityManager.class);
			implementation = (BaseMultielementRepository<?, ?>) constructor.newInstance(domainClass, entityManager);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Exception occurs during implementing multielement repository for " + domainClass.getName(), e);
		}
		return implementation;
	}

	@Override
	public BaseMultielementRepository<?, ?> provide() {
		if (domainClass == null || entityManager == null) {
			throw new RuntimeException("DomainClass or entityManager must not be null");
		}
		return doProvide();
	}
	
	@Override
	public BaseMultielementRepository<?, ?> provide(Object... args) {
		return this.initialize(args).provide();
	}
	
	@Override
	public Class<?> getReopositoryImplementClass() {
		return BaseMultielementRepositoryImpl.class;
	}
}
