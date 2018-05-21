package com.moraydata.general.primary.service.impl;

import static com.moraydata.general.management.util.BooleanExpressionUtils.addExpression;
import static com.moraydata.general.management.util.BooleanExpressionUtils.like;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toBoolean;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toInteger;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toLocalDateTime;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toLong;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.primary.entity.Role;
import com.moraydata.general.primary.entity.query.QRole;
import com.moraydata.general.primary.repository.RoleRepository;
import com.moraydata.general.primary.service.RoleService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("roleService")
public class RoleServiceImpl extends BaseAbstractService implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role get(Long instanceId) {
		return roleRepository.findOne(instanceId); 
	}

	@Override
	public List<Role> get(Long... instanceIds) {
		return roleRepository.findByIds(instanceIds);
	}

	@Override
	public Page<Role> get(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@Override
	public Page<Role> get(Pageable pageable, Predicate predicate) {
		return roleRepository.findAll(predicate, pageable);
	}

	@Override
	public List<Role> get() {
		return roleRepository.findAll();
	}
	
	@Override
	public List<Role> getAllAvailable() {
		QRole role = new QRole("Role");
		return roleRepository.findByPredicateAndSort(role.available.eq(true), 
				new Sort(Direction.ASC, 
						Arrays.asList(role.priority.getMetadata().getName(),
								role.id.getMetadata().getName())));
	}

	@Override
	public BooleanExpression search(JSONObject conditions) {
		if (conditions == null) return null;

		QRole role = QRole.role;
		BooleanExpression exp = null;
		
		String id = conditions.getString(role.id.getMetadata().getName());
		exp = addExpression(id, exp, role.id.eq(toLong(id)));
		
		String name = conditions.getString(role.name.getMetadata().getName());
		exp = addExpression(name, exp, role.name.like(like(name)));
		
		String priority = conditions.getString(role.priority.getMetadata().getName());
		exp = addExpression(priority, exp, role.priority.eq(toInteger(priority)));
		
		
		String available = conditions.getString(role.available.getMetadata().getName());
		exp = addExpression(available, exp, role.available.eq(toBoolean(available)));
		
		String createdDate = conditions.getString(role.createdDate.getMetadata().getName());
		exp = addExpression(createdDate, exp, role.createdDate.before(toLocalDateTime(createdDate)));
		
		return exp;
	}
	
	/**
	 * Get unique role instance by name, otherwise return a null value.
	 * @see AuthenticationUserDetailsService#getUser0
	 * @param name - name used to get unique role instance.
	 * @return an instance of role have gotten or a null value. 
	 */
	@Override
	public Role get(String name) {
		return roleRepository.findByName(name);
	}
}
