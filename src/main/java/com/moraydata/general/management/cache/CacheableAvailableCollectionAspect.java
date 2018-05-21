package com.moraydata.general.management.cache;

import static com.moraydata.general.management.system.AnnotationRepositoryNameMapping.REPOSITORY_MAP;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Component;

import com.moraydata.general.management.repository.BaseMultielementRepository;
import com.moraydata.general.management.repository.BaseMultielementRepositoryProvider;
import com.moraydata.general.management.util.CacheUtils;
import com.moraydata.general.management.util.CollectionUtils;
import com.moraydata.general.management.util.Constants;
import com.moraydata.general.management.util.SpringUtils;
import com.moraydata.general.primary.entity.BaseAbstractEntity;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Path;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 从数据库中获取所有属性available为true的业务对象
 * 并同步缓存与数据库中这些数据的内容
 * @author Mingshu Jian
 */
@Slf4j
@Aspect
@Component
public class CacheableAvailableCollectionAspect {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Cache cache;
	
	@Autowired
	private BaseMultielementRepositoryProvider repositoryProvider;
	
	@Pointcut(value="@annotation(cacheableAvailableCollection)")
	public void locateAnnotation(CacheableAvailableCollection cacheableAvailableCollection) {}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Around("locateAnnotation(cacheableAvailableCollection)")
	public Object doAround(ProceedingJoinPoint joinPoint, CacheableAvailableCollection cacheableAvailableCollection) {
		//获取注解参数中的缓存名称、业务实体类类型
		String[] cacheNames = cacheableAvailableCollection.value();
		Class<? extends Serializable> entityClass = cacheableAvailableCollection.entity();
		
		//获取被注解的方法的返回参数的泛型
		try {
			if (entityClass == null || entityClass.equals(BaseAbstractEntity.class)) {
				entityClass = (Class<? extends Serializable>) getMethodGenericReturnType(joinPoint);
			}
		} catch (Exception e) {
			log.warn("failed to get generic return type of target method automatically, to fix this needs to input entity class as annotation's parameter",e);
			return null;
		}
		
		try {
			Field availableField = entityClass.getDeclaredField("available");
			availableField.setAccessible(true);
		} catch (Exception e) {
			log.warn("failed to get required field ['available'] for input entity class, to fix this needs to ensure that input entity class has a field named 'available'", e);
			return null;
		}
		
		//根据注解参数，如果注解参数中不包含缓存名称，就去目标方法所在的类的CacheConfig注解中上面的参数找缓存名称
		//如果CacheConfig注解不存在或没有配置缓存名称，那么不向下执行其他代码并提示没有设置方法名
		if (cacheNames == null || cacheNames.length == 0 || cacheNames[0].equals("")) {
			CacheConfig config = joinPoint.getTarget().getClass().getDeclaredAnnotation(CacheConfig.class);
			if (config == null || config.cacheNames() == null || config.cacheNames().length == 0) {
				log.warn("no cache names found in annotation CacheableBasedPageableCollection");
				return null;
			} else {
				cacheNames = config.cacheNames();
			}
		}
		
		try {
			//在Spring容器中直接获取注解参数entityClass所对应的Repository实例，如果不存在，则通过工厂创建一个
			String entityName = StringUtils.uncapitalize(entityClass.getSimpleName());
			BaseMultielementRepository<?, Long> reps = REPOSITORY_MAP.containsKey(entityName) ?
					SpringUtils.getBean(REPOSITORY_MAP.get(entityName), BaseMultielementRepository.class) :
					(BaseMultielementRepository<?, Long>) repositoryProvider.initialize(entityClass, entityManager).provide();
			
			
			//根据具体业务实体类类型和分页对象获取分页后的业务实体类对象主键id列表
			Path<Long> idPath = reps.getIdPath();
			QueryResults<?> queryResults = reps.findSpecificData(reps.getEntityPath().get("available", Boolean.class).eq(true), idPath);
			List<?> listResults = queryResults.getResults();
			if (listResults == null) {
				listResults = new ArrayList<>(0);
			}
			List<Long> ids = listResults.stream().map(e -> ((Tuple) e).get(idPath)).collect(Collectors.toList());
			List<Long> sortedIds = new ArrayList<Long>(ids.size());
			for (Long id : ids)
				sortedIds.add(id);
			
			//根据主键id列表去相应的缓存中查询是否有相应的缓存记录
			//将id在缓存中有的对象存入返回结果集，将id在缓存中不存在的对象id存入一个列表中
			List<Long> cachedIds = new ArrayList<Long>(ids.size());
			for (String name : cacheNames) {
				cache = CacheUtils.getCache(name);
				if (cache != null) {
					for (Long id : ids)
						if (cache.get(id, entityClass) != null)
							cachedIds.add(id);
				}
				if (CollectionUtils.isNotBlank(ids)) {
					ids.removeAll(cachedIds);
				}
				cachedIds.clear();
			}
			
			//当未缓存对象id列表中至少有一个id记录时，去数据库查询该id对应的记录
			//将结果通过其主键id存入缓存中，且将结果存入返回结果集中
			if (ids.size() > 0) {
				List uncachedEntities = reps.findByIds(ids.toArray(new Long[]{}));
				for (String cacheName : cacheNames) {
					cache = CacheUtils.getCache(cacheName);
					if (cache != null) {
						for (Object object : uncachedEntities) {
							cache.put(getEntityPrimaryKeyValue(object), object);
						}
						break;
					}
				}
			}
			
			//2017-03-10 解决缓存数据与从数据库查出来数据合并时的乱序问题，现将所有为缓存记录存入缓存，再遵循顺序一次查出
			List entityList = new ArrayList(sortedIds.size());
			for (Long id : sortedIds)
				entityList.add(cache.get(id, entityClass));
			
			//将返回结果集中的对象封装成页面展示需要的分页对象并进行返回
			return new ArrayList<>(entityList);
		} catch (Exception e) {
			log.warn("failed to cache data using annotation CacheableAvailableCollection",e);
			return null;
		}
	}

	public static Type getMethodGenericReturnType(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		MethodSignature ms = (MethodSignature) joinPoint.getSignature();
		Method method = joinPoint.getTarget().getClass().getMethod(ms.getName(), ms.getParameterTypes());
		Type genericReturnType = method.getGenericReturnType();
		return ((ParameterizedType)genericReturnType).getActualTypeArguments()[0];
	}
	
	public static Long getEntityPrimaryKeyValue(@NonNull Object obj) throws NoSuchFieldException, SecurityException, NumberFormatException, IllegalArgumentException, IllegalAccessException {
		Field pk = obj.getClass().getDeclaredField(Constants.ENTITY.DEFAULT_PRIMARY_KEY);
		pk.setAccessible(true);
		return Long.valueOf(String.valueOf(pk.get(obj)));
	}
}