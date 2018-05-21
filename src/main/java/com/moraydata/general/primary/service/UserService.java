package com.moraydata.general.primary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.primary.entity.User;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface UserService {
	
	User get(Long instanceId);
	List<User> get(Long... instanceIds);
	Page<User> get(Pageable pageable);
	Page<User> get(Pageable pageable, Predicate predicate);
	List<User> get();
	BooleanExpression search(JSONObject conditions);
	User get(String username);
	
	User getByOpenId(String openId) throws Exception;
	List<User> getByParentId(Long userId) throws Exception;
}
