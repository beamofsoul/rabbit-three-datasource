package com.moraydata.general.secondary.repository;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.secondary.entity.Database;

@Repository
public interface DatabaseRepository extends BaseMultielementRepository<Database, Long> {

}
