package com.moraydata.general.primary.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.primary.entity.RolePermission;

@Repository
public interface RolePermissionRepository extends BaseMultielementRepository<RolePermission,Long>, RolePermissionRepositoryCustom<RolePermission, Long> {

//	@Query("SELECT new com.beamofsoul.bip.entity.dto.RolePermissionDTO(r.id AS roleId,r.name AS roleName,p.id AS permissionId,p.name AS permissionName,p.available AS permissionAvailable,p.parentId AS permissionParentId,p.resourceType AS permissionResourceType,p.url AS permissionUrl,p.action AS permissionAction) FROM RolePermission rp RIGHT JOIN rp.role r LEFT JOIN rp.permission p WHERE 1 = 1 ORDER BY roleId, permissionParentId ASC")
//	public List<RolePermissionDTO> findAllRolePermissionMapping();
	
	List<RolePermission> findByRole_Id(Long roleId);
}
