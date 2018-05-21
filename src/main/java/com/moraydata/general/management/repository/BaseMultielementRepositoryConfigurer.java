package com.moraydata.general.management.repository;

import java.lang.reflect.Constructor;

import org.springframework.stereotype.Component;

import com.moraydata.general.management.system.ConfigurationReader;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName DefaultJpaRepositoryConfiguration
 * @Description 设置BaseMultielementRepositoryProvider的一些参数，如使用哪个实现类
 * @author MingshuJian
 * @Date 2017年8月29日 下午3:52:14
 * @version 1.0.0
 */
@Slf4j
@Component
public class BaseMultielementRepositoryConfigurer {

	public static final String DEFAULT_PROVIDER_INSTANCE_PATH = "project.base.repository.provider";
	public static final String DEFAULT_PROVIDER_INSTANCE = "com.moraydata.general.management.repository.BaseMultielementRepositoryFactory";
	
	/**
	 * 暴露出来的provider对象实例
	 * 当有其他类需要在该配置类注册(向Spring容器)provider之前获取provider实例
	 * 该实例句柄将会指向Spring容器中provider对象实例
	 */
	public static BaseMultielementRepositoryProvider provider;
	
	/**
	 * @Title: getProvider  
	 * @Description: 根据配置文件中的参数或默认的provider instance全路径实例化特定provider实例  
	 * @return BaseMultielementRepositoryProvider 返回类型  
	 */
	@SuppressWarnings("static-access")
	protected static BaseMultielementRepositoryProvider getProvider() {
		/**
		 * 为了保持单例，如果已经存在provider实例，则不再进行重新创建实例
		 */
		if (provider != null) return provider;
		/**
		 * 实例化provider，如未配置则取默认全路径进行实例化
		 */
		try {
			String providerImplementationClassPath = getProviderImplementationClassPath();
			Class<?> clazz = BaseMultielementRepositoryConfigurer.class.forName(providerImplementationClassPath);
			Constructor<?> noArgsConstructor = clazz.getDeclaredConstructor(new Class[]{});
			noArgsConstructor.setAccessible(true);
			provider = (BaseMultielementRepositoryProvider) noArgsConstructor.newInstance();
		} catch (Exception e) {
			log.error("Unrecognized base multielement repository provider settings", e);
		}
		return provider;
	}

	/**
	 * application.yml配置文件中配置的BaseMultielementRepositoryProvider的全路径
	 * 如未设置则获取默认的BaseMultielementRepositoryProvider实现类全路径
	 */
	private static String getProviderImplementationClassPath() {
		String implementation = ConfigurationReader.getValue(DEFAULT_PROVIDER_INSTANCE_PATH, String.class);
		if (implementation == null) {
			implementation = DEFAULT_PROVIDER_INSTANCE;
		}
		return implementation;
	}
}
