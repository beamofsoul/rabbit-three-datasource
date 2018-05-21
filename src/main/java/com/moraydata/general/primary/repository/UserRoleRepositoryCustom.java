package com.moraydata.general.primary.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.moraydata.general.primary.entity.dto.UserRoleDTO;

public interface UserRoleRepositoryCustom {
	
	Page<UserRoleDTO> findAllUserRoleMapping(Pageable pageable);
	Page<UserRoleDTO> findUserRoleMappingByCondition(Pageable pageable, Object condition);
}
