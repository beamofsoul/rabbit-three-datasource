//package com.moraydata.general.management.system;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.core.GrantedAuthority;
//
//import com.moraydata.general.primary.entity.Permission;
//import com.moraydata.general.primary.entity.dto.RolePermissionDTO;
//
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public final class RolePermissionsMapping {
//	
//	private final static Map<String,List<Permission>> ROLE_PERMISSIONS_MAP =
//			new ConcurrentHashMap<String, List<Permission>>();
//	
//	private final static String SYSTEM_ACTION_PREFIX = "sys";
//	private final static String ACTION_DELIMITER = ":";
//	
//	public static boolean contains(Collection<? extends GrantedAuthority> roles, @NonNull Object permissionAction) {
//		String requiredSystemAction = getSystemAction(permissionAction);
//		Set<Permission> currentUserAllPermissions = getCurrentUserAllPermissions(roles);
//		for (Permission permission : currentUserAllPermissions) {
//			if (permission.getAction().equals(requiredSystemAction) || permission.getAction().equals(permissionAction) )
//				return true;
//		}
//		return false;
//	}
//	
//	private static Set<Permission> getCurrentUserAllPermissions(Collection<? extends GrantedAuthority> roles) {
//		Set<Permission> allPermissionsCurrentUserHas = new HashSet<Permission>();
//		List<Permission> permissionList = null;
//		for (GrantedAuthority role : roles) {
//			permissionList = ROLE_PERMISSIONS_MAP.get(role.getAuthority().split("_")[1].toUpperCase());
//			if (permissionList != null) {
//				allPermissionsCurrentUserHas.addAll(permissionList);
//				permissionList = null;
//			}
//		}		
//		return allPermissionsCurrentUserHas;
//	}
//	
//	public static void fill(List<RolePermissionDTO> rps) {
//		log.debug("开始加载角色权限映射信息...");
//		for (RolePermissionDTO rp : rps) {
//			String roleName = StringUtils.upperCase(rp.getRoleName());
//			if (!ROLE_PERMISSIONS_MAP.containsKey(roleName))
//				ROLE_PERMISSIONS_MAP.put(roleName, new ArrayList<Permission>());
//			ROLE_PERMISSIONS_MAP.get(roleName).add(rp.convertToPermission());
//		}
//		log.debug("角色权限映射信息加载完毕...");
//	}
//	
//	public static void refill(List<RolePermissionDTO> rps) {
//		ROLE_PERMISSIONS_MAP.clear();
//		fill(rps);
//	}
//	
//	private static String getPureOperation(Object permissionAction) {
//		return permissionAction.toString().split(ACTION_DELIMITER)[1];
//	}
//	
//	private static String getSystemOperation(String pureAction) {
//		return SYSTEM_ACTION_PREFIX + ACTION_DELIMITER + pureAction;
//	}
//	
//	private static String getSystemAction(Object permissionAction) {
//		return getSystemOperation(getPureOperation(permissionAction));
//	}
//}
