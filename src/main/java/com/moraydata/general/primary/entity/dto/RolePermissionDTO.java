package com.moraydata.general.primary.entity.dto;

import com.moraydata.general.primary.entity.Permission;
import com.moraydata.general.primary.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class RolePermissionDTO {

	private Long roleId;
	private String roleName;
	private Long permissionId;
	private String permissionName;
	private String permissionAction;
	private String permissionResourceType;
	private Long permissionParentId;
	private String permissionGroup;
	private Long permissionSort;
	private Boolean permissionAvailable;

	public RolePermissionDTO(Long roleId, String roleName, Long permissionId, String permissionName,
			Boolean permissionAvailable, Long permissionParentId, String permissionResourceType,
			String permissionAction, Long permissionSort) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.permissionId = permissionId;
		this.permissionName = permissionName;
		this.permissionAvailable = permissionAvailable;
		this.permissionParentId = permissionParentId;
		this.permissionResourceType = permissionResourceType;
		this.permissionAction = permissionAction;
		this.permissionSort = permissionSort;
	}

	public Role convertToRole() {
		return new Role(roleId, roleName);
	}

	public Permission convertToPermission() {
		return new Permission(permissionId, permissionName, permissionAction, permissionResourceType,
				permissionParentId, permissionGroup, permissionSort, permissionAvailable);
	}
}
