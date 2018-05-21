package com.moraydata.general.tertiary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moraydata.general.tertiary.entity.DataStream;
import com.moraydata.general.tertiary.entity.query.QDataStream;
import com.moraydata.general.tertiary.repository.DataStreamRepository;
import com.moraydata.general.tertiary.service.DataStreamService;

@Service("dataStreamService")
public class DataStreamServiceImpl implements DataStreamService {

	@Autowired
	private DataStreamRepository dataStreamRepository;
	
	@Override
	public DataStream get(Long id) {
		QDataStream $ = new QDataStream("DataStream");
		return dataStreamRepository.findOneByPredicate($.id.eq(id));		
	}
}
