package com.moraydata.general.primary.repository;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.primary.entity.Permission;

@Repository
public interface PermissionRepository extends BaseMultielementRepository<Permission,Long>, PermissionRepositoryCustom<Permission, Long> {

	Permission findByName(String name);
}
