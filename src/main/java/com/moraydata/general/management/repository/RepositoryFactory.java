package com.moraydata.general.management.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

import lombok.NonNull;

public class RepositoryFactory extends JpaRepositoryFactory {

	private BaseMultielementRepositoryProvider provider;
	
	public RepositoryFactory(@NonNull EntityManager entityManager) {
		super(entityManager);
		provider = BaseMultielementRepositoryConfigurer.getProvider();
	}

	@SuppressWarnings("unchecked")
	protected <T, ID extends Serializable> JpaRepository<T, ID> getTargetRepository(RepositoryMetadata metadata, EntityManager entityManager) {
		return (JpaRepository<T, ID>) provider.provide((Class<T>) metadata.getDomainType(), entityManager);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T, ID extends Serializable> SimpleJpaRepository<T, ID> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
		return (SimpleJpaRepository<T, ID>) provider.provide((Class<T>) information.getDomainType(), entityManager);
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return provider.getReopositoryImplementClass();
	}
}
