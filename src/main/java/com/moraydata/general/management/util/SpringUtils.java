package com.moraydata.general.management.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;
	private static BeanDefinitionRegistry registry = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
		registry = (DefaultListableBeanFactory) SpringUtils.applicationContext.getAutowireCapableBeanFactory();
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}
	
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name,clazz);
	}
	
	public static void removeBean(String name) {
		registry.removeBeanDefinition(name);
	}
	
	public static <T> int countBeans(Class<T> clazz) {
		return getApplicationContext().getBeanNamesForType(clazz).length;
	}
}
