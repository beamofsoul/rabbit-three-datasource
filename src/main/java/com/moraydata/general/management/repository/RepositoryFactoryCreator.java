package com.moraydata.general.management.repository;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @ClassName DefaultJpaRepositoryFactoryBean
 * @Description TODO(用一句话描述这个类的作用)
 * @author MingshuJian
 * @Date 2017年8月29日 下午3:46:37
 * @version 1.0.0
 * @param <T>
 * @param <S>
 */
public class RepositoryFactoryCreator<T extends Repository<S, ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID> {

	public RepositoryFactoryCreator(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
	}
	
	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new RepositoryFactory(entityManager);
	}

}
