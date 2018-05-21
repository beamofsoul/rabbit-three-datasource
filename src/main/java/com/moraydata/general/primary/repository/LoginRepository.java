package com.moraydata.general.primary.repository;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.primary.entity.Login;

@Repository
public interface LoginRepository extends BaseMultielementRepository<Login, Long> {

}
