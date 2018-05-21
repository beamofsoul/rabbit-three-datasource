package com.moraydata.general.primary.service.impl;

import static com.moraydata.general.management.util.BooleanExpressionUtils.addExpression;
import static com.moraydata.general.management.util.BooleanExpressionUtils.like;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toLong;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.management.util.PageUtils;
import com.moraydata.general.primary.entity.UserRoleCombineRole;
import com.moraydata.general.primary.entity.query.QUserRoleCombineRole;
import com.moraydata.general.primary.repository.UserRoleCombineRoleRepository;
import com.moraydata.general.primary.service.UserRoleService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("userRoleService")
public class UserRoleServiceImpl extends BaseAbstractService implements UserRoleService {
	
	@Autowired
	private UserRoleCombineRoleRepository userRoleCombineRoleRepository;
	
	@Override
	public Page<UserRoleCombineRole> get(Pageable pageable) {
		pageable = PageUtils.setPageableSort(pageable, new Sort(Direction.ASC, QUserRoleCombineRole.userRoleCombineRole.userId.getMetadata().getName()));
		return userRoleCombineRoleRepository.findAll(pageable);
	}
	
	@Override
	public Page<UserRoleCombineRole> get(Pageable pageable, JSONObject conditions) {
		if (conditions != null) {
			QUserRoleCombineRole $ = QUserRoleCombineRole.userRoleCombineRole;
			BooleanExpression exp = null;
			
			String userId = conditions.getString($.userId.getMetadata().getName());
			exp = addExpression(userId, exp, $.userId.eq(toLong(userId)));
			
			String nickname = conditions.getString($.nickname.getMetadata().getName());
			exp = addExpression(nickname, exp, $.nickname.like(like(nickname)));
			
			String username = conditions.getString($.username.getMetadata().getName());
			exp = addExpression(username, exp, $.username.like(like(username)));
			
			String roleName = conditions.getString($.roleName.getMetadata().getName());
			if (StringUtils.isNotBlank(roleName))
				exp = addExpression(roleName, exp, $.roleName.stringValue().contains((roleName)));
			
			String roleId = conditions.getString($.roleId.getMetadata().getName());
			if (StringUtils.isNotBlank(roleId))
				exp = addExpression(roleId, exp, $.roleId.stringValue().contains(roleId));
			
			return userRoleCombineRoleRepository.findAll(exp, pageable);
		} else {
			return userRoleCombineRoleRepository.findAll(pageable);
		}
	}

	@Override
	public UserRoleCombineRole get(Long userId) {
		Predicate predicate = QUserRoleCombineRole.userRoleCombineRole.userId.eq(userId);
		return userRoleCombineRoleRepository.findOne(predicate).get();
	}
}
