package com.moraydata.general.primary.service.impl;

import static com.moraydata.general.management.util.BooleanExpressionUtils.addExpression;
import static com.moraydata.general.management.util.BooleanExpressionUtils.like;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toBoolean;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toLong;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.management.cache.CacheableAvailableCollection;
import com.moraydata.general.management.cache.CacheableBasedPageableCollection;
import com.moraydata.general.management.cache.CacheableCommonCollection;
import com.moraydata.general.primary.entity.Permission;
import com.moraydata.general.primary.entity.query.QPermission;
import com.moraydata.general.primary.repository.PermissionRepository;
import com.moraydata.general.primary.service.PermissionService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("permissionService")
@CacheConfig(cacheNames = PermissionServiceImpl.CACHE_NAME)
public class PermissionServiceImpl extends BaseAbstractService implements PermissionService {
	
	public static final String CACHE_NAME = "permissionCache";

	@Autowired
	private PermissionRepository permissionRepository;
	
	@Override
	@Cacheable(key="#result.id", condition="#result ne null")
	public Permission get(Long instanceId) {
		return permissionRepository.findOne(instanceId);
	}
	
	@CacheableCommonCollection
	@Override
	public List<Permission> get(Long... instanceIds) {
		return null;
	}

	@CacheableBasedPageableCollection
	@Transactional(readOnly=true)
	@Override
	public Page<Permission> get(Pageable pageable) {
		return null;
	}
	
	@CacheableBasedPageableCollection
	@Transactional(readOnly=true)
	@Override
	public Page<Permission> get(Pageable pageable, Predicate predicate) {
		return null;
	}

	@CacheableCommonCollection
	@Override
	public List<Permission> get() {
		return permissionRepository.findAll();
	}
	
	@Override
	public BooleanExpression search(JSONObject conditions) {
		if (conditions == null) return null;
		
//		QPermission permission = QPermission.permission;
		QPermission permission = new QPermission("Permission");
		BooleanExpression exp = null;
		
		String name = conditions.getString(permission.name.getMetadata().getName());
		exp = addExpression(name, exp, permission.name.like(like(name)));
		
		String id = conditions.getString(permission.id.getMetadata().getName());
		exp = addExpression(id, exp, permission.id.eq(toLong(id)));
		
		String action = conditions.getString(permission.action.getMetadata().getName());
		exp = addExpression(action, exp, permission.action.like(like(action)));
		
		String group = conditions.getString(permission.group.getMetadata().getName());
		exp = addExpression(group, exp, permission.group.like(like(group)));
		
		String parentId = conditions.getString(permission.parentId.getMetadata().getName());
		exp = addExpression(parentId, exp, permission.parentId.eq(toLong(parentId)));
		
		String resourceType = conditions.getString(permission.resourceType.getMetadata().getName());
		if (StringUtils.isNotBlank(resourceType))
			exp = addExpression(resourceType, exp, permission.resourceType.eq(resourceType));
		
		String available = conditions.getString(permission.available.getMetadata().getName());
		exp = addExpression(available, exp, permission.available.eq(toBoolean(available)));
		
		return exp;
	}
	
	@CacheableAvailableCollection
	@Override
	public List<Permission> getAllAvailable() {
//		QPermission permission = new QPermission("Permission");
//		return permissionRepository.findByPredicateAndSort(permission.available.eq(true), 
//				new Sort(Direction.ASC, 
//						Arrays.asList(permission.sort.getMetadata().getName(),
//								permission.group.getMetadata().getName(),
//								permission.id.getMetadata().getName())));
		return null;
	}
	
	@Override
	@CachePut(key="#result.id", condition="#result ne null")
	public Permission get(String name) {
		return permissionRepository.findByName(name);
	}
}
