package com.moraydata.general.primary.repository;

import java.util.List;

import com.moraydata.general.primary.entity.dto.RolePermissionDTO;

public interface RolePermissionRepositoryCustom<T,ID> {

	List<RolePermissionDTO> findAllRolePermissionMapping();
}
