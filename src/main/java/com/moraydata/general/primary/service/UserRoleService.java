package com.moraydata.general.primary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.primary.entity.UserRoleCombineRole;

public interface UserRoleService {

	Page<UserRoleCombineRole> get(Pageable pageable);
	Page<UserRoleCombineRole> get(Pageable pageable, JSONObject conditions);
	UserRoleCombineRole get(Long userId);
}