package com.moraydata.general.primary.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

import com.moraydata.general.management.util.QuerydslUtils;
import com.moraydata.general.primary.entity.Permission;
import com.moraydata.general.primary.entity.query.QPermission;
import com.moraydata.general.primary.repository.PermissionRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

@NoRepositoryBean
public class PermissionRepositoryImpl implements PermissionRepositoryCustom<Permission, Long> {
	
	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Permission> getRelational(Long parentId) {
		JPAQuery<Permission> query = QuerydslUtils.newQuery(entityManager);
		QPermission $1 = new QPermission("tp1");
		QPermission $2 = new QPermission("tp2");
		
		List<Tuple> results = query.select(Projections.constructor(Permission.class, 
					$1.id,
					$1.name,
					$1.action,
					$1.resourceType,
					$1.parentId,
					$1.group,
					$1.sort,
					$1.available), JPAExpressions.select($2.id.count().as("countOfChildren")).from($2).where($2.parentId.eq($1.id)))
			.from($1)
			.where($1.parentId.eq(parentId))
			.orderBy($1.sort.asc())
			.fetch();
		
		List<Permission> pList = new ArrayList<>(results.size());
		for (Tuple each : results) {
			Object[] eachArray = each.toArray();
			Permission permission = (Permission) eachArray[0];
			permission.setCountOfChildren((Long) eachArray[1]);
			pList.add(permission);
		}
		return pList;
	}
}
