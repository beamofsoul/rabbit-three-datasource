package com.moraydata.general.management.repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.moraydata.general.management.database.DataSourceHolder;
import com.moraydata.general.management.database.DynamicDataSource;
import com.moraydata.general.management.util.SpringUtils;

@ConditionalOnBean({SpringUtils.class, DataSourceHolder.class})
@DependsOn({"secondaryDataSource"}) // ensure SpringUtils has been initialized, before use it
@Configuration
@EnableJpaRepositories(
		basePackages={"com.moraydata.general.tertiary.repository"},
		transactionManagerRef="tertiaryTransactionManager",
		entityManagerFactoryRef="tertiaryEntityManagerFactory",
		repositoryFactoryBeanClass=RepositoryFactoryCreator.class)
@EnableSpringDataWebSupport
public class TertiaryRepositoryConfiguration extends BaseJpaRepositoryConfiguration {

	private static final String JPA_ENTITY_PACKAGE_PATH = "com.moraydata.general.tertiary.entity";
	private static final String SHARED_ENTITY_MANAGER_CREATOR = "org.springframework.orm.jpa.SharedEntityManagerCreator#2";
	
	@Autowired
	private DataSourceHolder holder;
	
	@Bean("tertiaryDataSource")
	public DataSource dataSource() {
		return new DynamicDataSource(holder.targetDataSources, holder.targetDataSources.get(115L));
	}
	
	@Bean("tertiaryEntityManager")
	public EntityManager entityManager() {
		sharedEntityManagerCreatorNameSet.add(SHARED_ENTITY_MANAGER_CREATOR);
		return SpringUtils.getBean(SHARED_ENTITY_MANAGER_CREATOR, EntityManager.class);
	}
	
	@Bean("tertiaryEntityManagerFactory")
	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {		
		return getEntityManagerFactory(dataSource(), JPA_ENTITY_PACKAGE_PATH, "tertiaryPersistenceUnit");
	}
	
	@Bean("tertiaryTransactionManager")
	PlatformTransactionManager transactionManager() {
		return getTransactionManager();
	}
}
