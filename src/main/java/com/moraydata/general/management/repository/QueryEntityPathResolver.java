package com.moraydata.general.management.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.querydsl.core.types.EntityPath;

/**
 * @ClassName QueryEntityPathResolver
 * @Description A copy of {@link org.springframework.data.querydsl.SimpleEntityPathResovler} and method {@link #getQueryClassName(Class)} has been modified to adapt current query class paths which are under *.entity.query package.
 * @author MingshuJian
 * @Date 2017年9月21日 下午3:27:40
 * @version 1.0.0
 */
public enum QueryEntityPathResolver implements EntityPathResolver {

	INSTANCE;

	private static final String NO_CLASS_FOUND_TEMPLATE = "Did not find a query class %s for domain class %s!";
	private static final String NO_FIELD_FOUND_TEMPLATE = "Did not find a static field of the same type in %s!";

	/**
	 * Creates an {@link EntityPath} instance for the given domain class. Tries to lookup a class matching the naming
	 * convention (prepend Q to the simple name of the class, same package) and find a static field of the same type in
	 * it.
	 * 
	 * @param domainClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> EntityPath<T> createPath(Class<T> domainClass) {

		String pathClassName = getQueryClassName(domainClass);

		try {

			Class<?> pathClass = ClassUtils.forName(pathClassName, domainClass.getClassLoader());

			return getStaticFieldOfType(pathClass)//
					.map(it -> (EntityPath<T>) ReflectionUtils.getField(it, null))//
					.orElseThrow(() -> new IllegalStateException(String.format(NO_FIELD_FOUND_TEMPLATE, pathClass)));

		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(String.format(NO_CLASS_FOUND_TEMPLATE, pathClassName, domainClass.getName()),
					e);
		}
	}

	/**
	 * Returns the first static field of the given type inside the given type.
	 * 
	 * @param type
	 * @return
	 */
	private Optional<Field> getStaticFieldOfType(Class<?> type) {

		for (Field field : type.getDeclaredFields()) {

			boolean isStatic = Modifier.isStatic(field.getModifiers());
			boolean hasSameType = type.equals(field.getType());

			if (isStatic && hasSameType) {
				return Optional.of(field);
			}
		}

		Class<?> superclass = type.getSuperclass();
		return Object.class.equals(superclass) ? Optional.empty() : getStaticFieldOfType(superclass);
	}

	/**
	 * Returns the name of the query class for the given domain class.
	 * <p>Modified: Query class paths are under *.entity.query package for now.</p>
	 * @param domainClass
	 * @return
	 */
	private String getQueryClassName(Class<?> domainClass) {

		String simpleClassName = ClassUtils.getShortName(domainClass);
		return String.format("%s.query.Q%s%s", domainClass.getPackage().getName(), getClassBase(simpleClassName),
				domainClass.getSimpleName());
	}

	/**
	 * Analyzes the short class name and potentially returns the outer class.
	 * 
	 * @param shortName
	 * @return
	 */
	private String getClassBase(String shortName) {

		String[] parts = shortName.split("\\.");

		if (parts.length < 2) {
			return "";
		}

		return parts[0] + "_";
	}
}