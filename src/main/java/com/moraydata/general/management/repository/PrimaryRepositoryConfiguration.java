package com.moraydata.general.management.repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.moraydata.general.management.util.SpringUtils;

@Configuration
@EnableJpaRepositories(
		basePackages={"com.moraydata.general.primary.repository"},
		transactionManagerRef="primaryTransactionManager",
		entityManagerFactoryRef="primaryEntityManagerFactory",
		repositoryFactoryBeanClass=RepositoryFactoryCreator.class)
@EnableSpringDataWebSupport
public class PrimaryRepositoryConfiguration extends BaseJpaRepositoryConfiguration {

	private static final String JPA_ENTITY_PACKAGE_PATH = "com.moraydata.general.primary.entity";
	private static final String SHARED_ENTITY_MANAGER_CREATOR = "org.springframework.orm.jpa.SharedEntityManagerCreator#0";
	
	@Bean("primaryDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.primary")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean("primaryEntityManager")
	@DependsOn({"springUtils"}) // ensure SpringUtils has been initialized, before use it
	@Primary
	public EntityManager entityManager() {
		sharedEntityManagerCreatorNameSet.add(SHARED_ENTITY_MANAGER_CREATOR);
		return SpringUtils.getBean(SHARED_ENTITY_MANAGER_CREATOR, EntityManager.class);
	}
	
	@Bean("primaryEntityManagerFactory")
	@Primary
	@Override
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {		
		return getEntityManagerFactory(dataSource(), JPA_ENTITY_PACKAGE_PATH, "primaryPersistenceUnit");
	}
	
	@Bean("primaryTransactionManager")
	@Primary
	PlatformTransactionManager transactionManager() {
		return getTransactionManager();
	}
}
