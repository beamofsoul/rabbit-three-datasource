package com.moraydata.general.management.cache;

import static com.moraydata.general.management.system.AnnotationRepositoryNameMapping.REPOSITORY_MAP;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.management.repository.BaseMultielementRepositoryProvider;
import com.moraydata.general.management.util.CacheUtils;
import com.moraydata.general.management.util.CollectionUtils;
import com.moraydata.general.management.util.SpringUtils;
import com.moraydata.general.primary.entity.BaseAbstractEntity;
import com.querydsl.core.QueryResults;

import lombok.extern.slf4j.Slf4j;

/**
 * 根据输入的业务对象实例Id数组(如果有)从缓存和数据库中获取需要的业务对象列表
 * 并同步缓存与数据库中这些数据的内容
 * @author MingshuJian
 */
@Slf4j
@Aspect
@Component
public class CacheableCommonCollectionAspect {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Cache cache;
	
	@Autowired
	private BaseMultielementRepositoryProvider repositoryProvider;
	
	@Pointcut(value="@annotation(cacheableCommonCollection)")
	public void locateAnnotation(CacheableCommonCollection cacheableCommonCollection) {}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Around("locateAnnotation(cacheableCommonCollection)")
	public Object doAround(ProceedingJoinPoint joinPoint, CacheableCommonCollection cacheableCommonCollection) {
		//获取注解参数中的缓存名称、业务实体类类型和目标方法输入参数(可以没有输入参数,如果有,则第一个必须为数组)
		String[] cacheNames = cacheableCommonCollection.value();
		Class<? extends Serializable> entityClass = cacheableCommonCollection.entity();
		
		//获取被注解的方法的返回参数的泛型
		try {
			if (entityClass == null || entityClass.equals(BaseAbstractEntity.class)) {
				entityClass = (Class<? extends Serializable>) CacheableBasedPageableCollectionAspect.getMethodGenericReturnType(joinPoint);
			}
		} catch (Exception e) {
			log.warn("failed to get generic return type of target method automatically, to fix this needs to input entity class as annotation's parameter",e);
			return null;
		}
		
		Object[] params = joinPoint.getArgs();
		Object primaryKeyIdArray = null;
		
		//根据注解参数，如果注解参数中不包含缓存名称，就去目标方法所在的类的CacheConfig注解中上面的参数找缓存名称
		//如果CacheConfig注解不存在或没有配置缓存名称，那么不向下执行其他代码并提示没有设置方法名
		if (cacheNames == null || cacheNames.length == 0 || cacheNames[0].equals("")) {
			CacheConfig config = joinPoint.getTarget().getClass().getDeclaredAnnotation(CacheConfig.class);
			if (config == null || config.cacheNames() == null || config.cacheNames().length == 0) {
				log.warn("no cache names found in annotation CacheableCommonCollection");
				return null;
			} else {
				cacheNames = config.cacheNames();
			}
		}
		
		//判断目标方法存在输入参数且第一个输入参数是业务对象主键Id数组
		//如果目标方法没有任何输入参数,则默认认为该目标方法为获取所有业务对象的数据列表
		if (params != null && params.length > 0) {
			primaryKeyIdArray = params[0];
			if (!(primaryKeyIdArray instanceof Long[]) && !(primaryKeyIdArray instanceof long[])) {
				log.warn("unrecognized input params found in annotation CacheableCommonCollection");
				return null;
			}
		}
		
		try {
			//在Spring容器中直接获取注解参数entityClass所对应的Repository实例，如果不存在，则通过工厂创建一个
			String entityName = StringUtils.uncapitalize(entityClass.getSimpleName());
			BaseMultielementRepository<?, Long> reps = REPOSITORY_MAP.containsKey(entityName) ?
					SpringUtils.getBean(REPOSITORY_MAP.get(entityName), BaseMultielementRepository.class) :
					(BaseMultielementRepository<?, Long>) repositoryProvider.initialize(entityClass, entityManager).provide();
			
			//获取业务实体类对象主键id列表
			List<Long> sortedIds = null;
			if (primaryKeyIdArray == null) {
				QueryResults<Long> queryResults = reps.findAllIds();
				sortedIds = queryResults.getResults();
			} else {
				int length = Array.getLength(primaryKeyIdArray);
				Long[] idLongArray = new Long[length];
				for (int i = 0; i < idLongArray.length; i++) {
					idLongArray[i] = (Long) Array.get(primaryKeyIdArray, i);
				}
				sortedIds = Arrays.asList(idLongArray);
			}
			//Arrays$ArrayList和ArrayList都继承自AbstractList，其中的remove(),add()等方法在AbstractList中是默认抛出上面的异常的并且不做任何处理
			//为了解决Array$ArrayList没有重写父类的如removeAll等方法,会在使用过程中抛出java.lang.UnsupportedOperationException异常的问题
			//现将Arrays$ArrayList转为ArrayList,因为ArrayList重写了这些方法,所有在使用中不会有任何问题存在
			sortedIds = new ArrayList(sortedIds);
			Collections.sort(sortedIds);
			final List<Long> unchangedIdList = CollectionUtils.deepCopy(sortedIds);
			
			//根据主键id列表去相应的缓存中查询是否有相应的缓存记录
			//将id在缓存中有的对象存入返回结果集，将id在缓存中不存在的对象id存入一个列表中
			List<Long> cachedIds = new ArrayList<Long>(sortedIds.size());
			for (String name : cacheNames) {
				cache = CacheUtils.getCache(name);
				if (cache != null) {
					for (Long id : sortedIds)
						if (cache.get(id, entityClass) != null)
							cachedIds.add(id);
				}
				if (CollectionUtils.isNotBlank(sortedIds)) {
					sortedIds.removeAll(cachedIds);
				}
				cachedIds.clear();
			}
			
			//当未缓存对象id列表中至少有一个id记录时，去数据库查询该id对应的记录
			//将结果通过其主键id存入缓存中，且将结果存入返回结果集中
			if (sortedIds.size() > 0) {
				List uncachedEntities = reps.findByIds(sortedIds.toArray(new Long[]{}));
				for (String cacheName : cacheNames) {
					cache = CacheUtils.getCache(cacheName);
					if (cache != null) {
						for (Object object : uncachedEntities) {
							cache.put(CacheableBasedPageableCollectionAspect.getEntityPrimaryKeyValue(object), object);
						}
						break;
					}
				}
			}
			
			//2017-03-10 解决缓存数据与从数据库查出来数据合并时的乱序问题，现将所有为缓存记录存入缓存，再遵循顺序一次查出
			List entityList = new ArrayList(unchangedIdList.size());
			for (Long id : unchangedIdList)
				entityList.add(cache.get(id, entityClass));
			
			//将对象并进行返回
			return entityList;
		} catch (Exception e) {
			log.warn("failed to cache data using annotation CacheableCommonCollection",e);
			return null;
		}
	}
}