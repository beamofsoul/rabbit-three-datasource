package com.moraydata.general.management.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;

@SuppressWarnings("unchecked")
@NoRepositoryBean
public interface BaseMultielementRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,JpaSpecificationExecutor<T>,QuerydslPredicateExecutor<T> {

	List<T> findByIds(ID... ids);
//	List<T> findByAttr(String name, Object value);
//	List<T> findByAttrIn(String name, Object... values);
	List<T> findByPredicate(Predicate predicate);
	List<T> findByPredicate(Predicate predicate, Long limit);
	List<T> findByPredicate(Predicate predicate, Long limit, Sort sort);
	T findOneByPredicate(Predicate predicate);
	T findOne(ID id);
	List<T> findByPredicateAndSort(Predicate predicate, Sort sort);
	long deleteByIds(ID... ids);
//	Collection<T> bulkSave(Collection<T> entities);
	<S> long update(Path<S> path, S value);
	<S> long update(Path<S> path, S value, Predicate predicate);
	long update(List<? extends Path<?>> paths, List<?> values);
	long update(List<? extends Path<?>> paths, List<?> values, Predicate predicate);
	<S> long update(Path<S> path, Expression<S> exp, Predicate predicate);
	Long deleteByPredicate(Predicate predicate);
	QueryResults<ID> findPageableIds(Pageable pageable);
	QueryResults<ID> findPageableIds(Pageable pageable, Predicate predicate);
	QueryResults<ID> findAllIds();
    QueryResults<?> findSpecificData(Predicate predicate, Expression<?>... selects);
    QueryResults<?> findSpecificData(Predicate predicate, Sort sort, Expression<?>... selects);
	PathBuilder<T> getEntityPath();
	Path<ID> getIdPath();
}
