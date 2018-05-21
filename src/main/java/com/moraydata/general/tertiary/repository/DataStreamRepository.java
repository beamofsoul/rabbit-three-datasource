package com.moraydata.general.tertiary.repository;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.tertiary.entity.DataStream;

@Repository
public interface DataStreamRepository extends BaseMultielementRepository<DataStream, Long> {

}
