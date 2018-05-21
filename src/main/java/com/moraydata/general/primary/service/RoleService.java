package com.moraydata.general.primary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.primary.entity.Role;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface RoleService {
	
	Role get(Long instanceId);
	List<Role> get(Long... instanceIds);
	Page<Role> get(Pageable pageable);
	Page<Role> get(Pageable pageable, Predicate predicate);
	List<Role> get();
	List<Role> getAllAvailable();
	BooleanExpression search(JSONObject conditions);
	Role get(String name);
}
