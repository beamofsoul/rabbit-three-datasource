package com.moraydata.general.primary.repository;

import java.util.List;

import com.moraydata.general.primary.entity.Permission;

public interface PermissionRepositoryCustom<T,ID> {

	List<Permission> getRelational(Long parentId);
}
