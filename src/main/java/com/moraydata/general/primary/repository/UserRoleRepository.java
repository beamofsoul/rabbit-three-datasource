package com.moraydata.general.primary.repository;

import org.springframework.stereotype.Repository;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.primary.entity.UserRole;

@Repository
public interface UserRoleRepository extends BaseMultielementRepository<UserRole,Long>, UserRoleRepositoryCustom {
	
	int deleteByUser_IdAndRole_Id(Long userId, Long roleId);
	
//	@Modifying
//	@Query(value="DELETE FROM t_user WHERE user_id = ?1",nativeQuery=true)
//	int deleteByUserId(Long userId);
//	
//	@Modifying
//	@Query(value="DELETE FROM t_user_role WHERE user_id in ?1",nativeQuery=true)
//	int deleteByUserIds(Long... userIds);
}
