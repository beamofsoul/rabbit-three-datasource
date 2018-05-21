package com.moraydata.general.primary.repository;


import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.primary.entity.Role;

@Repository
public interface RoleRepository extends BaseMultielementRepository<Role,Long> {
	
	Role findByName(String name);
}
