package com.moraydata.general.secondary.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.secondary.entity.Database;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

public interface DatabaseService {
	
	Database create(Database instance);
	Database update(Database instance);
	long delete(Long... instanceIds);
	Database get(Long instanceId);
	List<Database> get(Long... instanceIds);
	Page<Database> get(Pageable pageable);
	Page<Database> get(Pageable pageable, Predicate predicate);
	List<Database> get();
	BooleanExpression search(JSONObject conditions);
}
