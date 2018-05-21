package com.moraydata.general.secondary.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.secondary.entity.Database;
import com.moraydata.general.secondary.repository.DatabaseRepository;
import com.moraydata.general.secondary.service.DatabaseService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("databaseService")
public class DatabaseServiceImpl implements DatabaseService {

	@Autowired
	private DatabaseRepository databaseRepository;

	@Override
	public Database create(Database instance) {
		return databaseRepository.save(instance);
	}

	@Override
	public Database update(Database instance) {
		Database originalDatabase = databaseRepository.findOne(instance.getId());
		BeanUtils.copyProperties(instance, originalDatabase);
		return databaseRepository.save(originalDatabase);
	}

	@Override
	@Transactional
	public long delete(Long... instanceIds) {
		return databaseRepository.deleteByIds(instanceIds);
	}

	@Override
	public Database get(Long instanceId) {
		return databaseRepository.findOne(instanceId);
	}
	

	@Override
	public List<Database> get(Long... instanceIds) {
		return databaseRepository.findByIds(instanceIds);
	}

	@Override
	public List<Database> get() {
		return databaseRepository.findAll();
	}

	@Override
	public Page<Database> get(Pageable pageable) {
		return databaseRepository.findAll(pageable);
	}

	@Override
	public Page<Database> get(Pageable pageable, Predicate predicate) {
		return databaseRepository.findAll(predicate, pageable);
	}
	
	@Override
	public BooleanExpression search(JSONObject conditions) {
		if (conditions == null) return null;
		
//		QDatabase Database = QDatabase.database;
		BooleanExpression exp = null;
		
		return exp;
	}
}
