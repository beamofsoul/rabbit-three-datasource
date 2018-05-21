package com.moraydata.general.management.repository;

import static com.moraydata.general.management.util.QuerydslUtils.doDelete;
import static com.moraydata.general.management.util.QuerydslUtils.doUpdate;
import static com.moraydata.general.management.util.QuerydslUtils.initQuery;
import static com.moraydata.general.management.util.QuerydslUtils.newQuery;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.moraydata.general.management.util.Constants;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.Getter;

/**
 * 
 * @ClassName BaseMultielementRepositoryImpl
 * @Description 默认的基于泛型的持久层通用实现类
 * @author MingshuJian
 * @Date 2017年8月29日 下午3:36:58
 * @version 1.0.0
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings("unchecked")
@NoRepositoryBean
public class BaseMultielementRepositoryImpl<T,ID extends Serializable> extends QuerydslJpaRepository<T, ID> implements BaseMultielementRepository<T, ID> {

    private final EntityManager entityManager;
    @Getter
    private final PathBuilder<T> entityPath;
    @Getter
    private final Path<ID> idPath;
    
	public BaseMultielementRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager, QueryEntityPathResolver.INSTANCE);
		this.entityManager = entityManager;
		this.entityPath = new PathBuilder<T>(entityInformation.getJavaType(), entityInformation.getEntityName());
		this.idPath = (Path<ID>) entityPath.getNumber(Constants.ENTITY.DEFAULT_PRIMARY_KEY, Long.class);
	}
	
	public BaseMultielementRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		this((JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager),
				entityManager);
	}
	
	/**
	 * @Title: findByIds  
	 * @Description: 根据业务实体类主键数组获取所有相关的业务实体类实例列表
	 * @param ids 业务实体类主键数组
	 * @return List<T> 查询到的业务实体类实例列表
	 */
	@Override
	public List<T> findByIds(ID... ids) {
		return queryByPredicate(((SimpleExpression<ID>) idPath).in(ids));
	}
	
	/**
	 * @Title: findByPredicate  
	 * @Description: 根据业务实体类查询条件获取符合条件的业务实体类实例列表
	 * @param predicate 业务实体类的查询条件对象
	 * @return List<T> 查询到的业务实体类实例列表
	 */
	@Override
	public List<T> findByPredicate(Predicate predicate) {
		return queryByPredicate(predicate);
	}
	
	/**
	 * @Title: findByPredicate  
	 * @Description: 根据业务实体类查询条件获取符合条件的业务实体类实例列表
	 * @param predicate 业务实体类的查询条件对象
	 * @param limit 最多返回多少条记录
	 * @return List<T> 查询到的业务实体类实例列表
	 */
	@Override
	public List<T> findByPredicate(Predicate predicate, Long limit) {
		return queryByPredicate(predicate, limit);
	}
	
	/**
	 * @Title: findByPredicate  
	 * @Description: 根据业务实体类查询条件获取符合条件的业务实体类实例列表
	 * @param predicate 业务实体类的查询条件对象
	 * @param limit 最多返回多少条记录
	 * @param sort 排序条件
	 * @return List<T> 查询到的业务实体类实例列表
	 */
	@Override
	public List<T> findByPredicate(Predicate predicate, Long limit, Sort sort) {
		return queryByPredicate(predicate, limit, sort);
	}
	
	/**
	 * @Title: findOneByPredicate  
	 * @Description: 根据业务实体类查询条件获取符合条件的业务实体类实例
	 * @param predicate 业务实体类的查询条件对象
	 * @return T 查询到的业务实体类实例
	 */
	@Override
	public T findOneByPredicate(Predicate predicate) {
		return queryOneByPredicate(predicate);
	}
	
	/**
	 * @Title: findOne  
	 * @Description: 根据业务实体类主键获取符合条件的业务实体类实例
	 * @param id 业务实体类的主键
	 * @return T 查询到的业务实体类实例
	 */
	@Override
	public T findOne(ID id) {
		return queryOneByPredicate(((SimpleExpression<ID>)idPath).eq(id));
	}
	
	/**
	 * @Title: findByPredicateAndSort  
	 * @Description: 根据业务实体类查询条件和排序条件获取符合条件的业务实体类实例列表
	 * @param predicate 业务实体类的查询条件对象
	 * @param sort 业务实体类的排序条件对象
	 * @return List<T> 查询到的业务实体类实例列表
	 */
	@Override
	public List<T> findByPredicateAndSort(Predicate predicate, Sort sort) {
		return queryByPredicateAndSort(predicate, sort);
	}
	
	/**
	 * @Title: findPageableIds  
	 * @Description: 根据分页对象的值查询当前泛型业务实体类某一页所有的主键ID
	 * @param pageable 分页对象，保存了当前页码，每页数量和一个排序对象(Sort)
	 * @return QueryResults<ID> 查询到的业务实体类主键ID列表
	 */
	@Override
	public QueryResults<ID> findPageableIds(Pageable pageable) {
		return findPageableIds(pageable, null);
	}
	
	/**
	 * @Title: findPageableIds  
	 * @Description: 根据分页对象的值查询当前泛型业务实体类某一页所有的主键ID
	 * @param pageable 分页对象，保存了当前页码，每页数量和一个排序对象(Sort)
	 * @param predicate 断言对象，保存了查询的条件
	 * @return QueryResults<ID> 查询到的业务实体类主键ID列表
	 */
	@Override
	public QueryResults<ID> findPageableIds(Pageable pageable, Predicate predicate) {
		JPAQuery<T> query = initQuery(entityManager, entityPath, pageable, predicate);
		query.select(idPath);
		return (QueryResults<ID>) query.fetchResults();
	}
	
	/**
	 * @Title: findAllIds  
	 * @Description: 查询当前泛型业务实体类所有的主键ID
	 * @return QueryResults<ID> 查询到的业务实体类主键ID列表
	 */
	@Override
	public QueryResults<ID> findAllIds() {
		JPAQuery<T> query = newQuery(entityManager, entityPath);
		query.select(idPath);
		return (QueryResults<ID>) query.fetchResults();
	}

   /**
	 * @Title: findSpecificDataByPredicate
	 * @Description: 根据查询需要的字段查询当前泛型业务实体类的特定结果集
	 * @param predicate 断言对象，保存了查询的条件
     * @param selects 查询需要的字段
	 * @return QueryResults<?> 查询到的业务实体类的特定结果集
	 */
	@Override
	public QueryResults<?> findSpecificData(Predicate predicate, Expression<?>... selects) {
		JPAQuery<T> query = newQuery(entityManager, entityPath, predicate, null, selects);
		return query.fetchResults();
	}
	
   /**
	 * @Title: findSpecificDataByPredicate
	 * @Description: 根据查询需要的字段查询当前泛型业务实体类的特定结果集
	 * @param predicate 断言对象，保存了查询的条件
	 * @param sort 排序对象，保存了排序的条件
     * @param selects 查询需要的字段
	 * @return QueryResults<?> 查询到的业务实体类的特定结果集
	 */
	@Override
	public QueryResults<?> findSpecificData(Predicate predicate, Sort sort, Expression<?>... selects) {
		JPAQuery<T> query = newQuery(entityManager, entityPath, predicate, sort, selects);
		return query.fetchResults();
	}

	/**
	 * @Title: deleteByIds  
	 * @Description: 根据业务实体类主键ID删除相对应的业务记录
	 * @param ids 业务实体类主键ID数组
	 * @return long 删除多少条业务记录的数量
	 */
	@Override
	public long deleteByIds(ID... ids) {
		return deleteByPredicate(((SimpleExpression<ID>) idPath).in(ids));
	}
	
	/**
	 * @Title: update  
	 * @Description: 根据path与value值批量修改数据库表中的记录
	 * @param path 需要修改的字段
	 * @param value 修改后的值
	 * @return long 修改了多少条记录
	 */
	@Override
	public <S> long update(Path<S> path, S value) {
		return doUpdate(entityManager, entityPath, path, value);
	}
	
	/**
	 * @Title: update  
	 * @Description: 根据path与value值批量修改数据库表中的记录
	 * @param path 需要修改的字段
	 * @param value 修改后的值
	 * @param predicate 修改的条件
	 * @return long 修改了多少条记录
	 */
	@Override
	public <S> long update(Path<S> path, S value, Predicate predicate) {
		return doUpdate(entityManager, entityPath, path, value, predicate);
	}
	
	/**
	 * @Title: update  
	 * @Description: 根据path与value值批量修改数据库表中的记录
	 * @param paths 需要修改的字段列表
	 * @param values 修改后的值列表
	 * @return long 修改了多少条记录
	 */
	@Override
	public long update(List<? extends Path<?>> paths, List<?> values) {
		return doUpdate(entityManager, entityPath, paths, values);
	} 
	
	/**
	 * @Title: update  
	 * @Description: 根据path与value值批量修改数据库表中的记录
	 * @param paths 需要修改的字段列表
	 * @param values 修改后的值列表
	 * @param predicate 修改的条件
	 * @return long 修改了多少条记录
	 */
	@Override
	public long update(List<? extends Path<?>> paths, List<?> values, Predicate predicate) {
		return doUpdate(entityManager, entityPath, paths, values, predicate);
	}
	
	/**
	 * @Title: update
	 * @Description: 根据path与expression值批量修改数据库表中的记录
	 * @param path 需要修改的字段
	 * @param exp 修改后的表达式
	 * @param predicate 修改的条件
	 * @return long 修改了多少条记录
	 */
	@Override
	public <S> long update(Path<S> path, Expression<S> exp, Predicate predicate) {
		return doUpdate(entityManager, entityPath, path, exp, predicate);
	}
	
	/**
	 * @Title: deleteByPredicate  
	 * @Description: 根据条件删除业务记录
	 * @param predicate 删除的条件
	 * @return Long 删除了多少条记录
	 */
	@Override
	public Long deleteByPredicate(Predicate predicate) {
		return doDelete(entityManager, entityPath, predicate);
	}
	
	/**
	 * @Title: newQueryByPredicate  
	 * @Description: 根据查询条件创建查询对象
	 * @param predicate 查询的条件
	 * @return JPAQuery<T> 创建好的查询对象
	 */
	public JPAQuery<T> newQueryByPredicate(Predicate predicate) {
		return newQuery(entityManager, entityPath, predicate);
	}
	
	/**
	 * @Title: newQueryByPredicateAndSort  
	 * @Description: 根据查询条件和排序条件创建查询对象
	 * @param predicate 查询的条件
	 * @param sort 排序的条件
	 * @return JPAQuery<T> 创建好的查询对象
	 */
	public JPAQuery<T> newQueryByPredicateAndSort(Predicate predicate, Sort sort) {
		return newQuery(entityManager, entityPath, predicate, sort);
	}
	
	/**
	 * @Title: queryByPredicate  
	 * @Description: 根据查询条件执行查询
	 * @param predicate 查询的条件
	 * @return List<T> 执行查询后的返回值
	 */
	public List<T> queryByPredicate(Predicate predicate) {
		return newQueryByPredicate(predicate).fetch();
	}
	
	/**
	 * @Title: queryByPredicate  
	 * @Description: 根据查询条件执行查询
	 * @param predicate 查询的条件
	 * @param limit 最大多少条记录
	 * @return List<T> 执行查询后的返回值
	 */
	public List<T> queryByPredicate(Predicate predicate, Long limit) {
		return newQueryByPredicate(predicate).limit(limit).fetch();
	}
	
	/**
	 * @Title: queryByPredicate  
	 * @Description: 根据查询条件执行查询
	 * @param predicate 查询的条件
	 * @param limit 最大多少条记录
	 * @param sort 排序方法
	 * @return List<T> 执行查询后的返回值
	 */
	public List<T> queryByPredicate(Predicate predicate, Long limit, Sort sort) {
		JPAQuery<T> query = newQuery(entityManager, entityPath, predicate, sort);
		return query.limit(limit).fetch();
	}
	
	/**
	 * @Title: queryOneByPredicate  
	 * @Description: 根据查询条件执行查询
	 * @param predicate 查询的条件
	 * @return T 执行查询后的单一返回值
	 */
	public T queryOneByPredicate(Predicate predicate) {
		return newQueryByPredicate(predicate).fetchOne();
	}
	
	/**
	 * @Title: queryByPredicateAndSort  
	 * @Description: 根据查询条件和排序条件执行查询
	 * @param predicate 查询的条件
	 * @param sort 排序的条件
	 * @return List<T> 执行查询后的返回值
	 */
	public List<T> queryByPredicateAndSort(Predicate predicate, Sort sort) {
		return newQueryByPredicateAndSort(predicate, sort).fetch();
	}
}
