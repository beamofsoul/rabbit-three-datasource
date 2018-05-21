package com.moraydata.general.primary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.primary.entity.Login;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface LoginService {
	
	Login create(Login instance);
	Login update(Login instance);
	long delete(Long... instanceIds);
	Login get(Long instanceId);
	List<Login> get(Long... instanceIds);
	Page<Login> get(Pageable pageable);
	Page<Login> get(Pageable pageable, Predicate predicate);
	List<Login> get();
	BooleanExpression search(JSONObject conditions);
	
	long deleteByUserIds(Long... userIds);
}
