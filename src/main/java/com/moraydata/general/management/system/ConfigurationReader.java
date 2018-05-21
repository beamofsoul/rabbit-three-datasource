package com.moraydata.general.management.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;

import lombok.NonNull;

public class ConfigurationReader {
	
	private static final Map<String, Object> CONFIG_MAP = new HashMap<String, Object>();
	private static final String DEFAULT_CONFIGURATION_FILE_PATH = "application.yml";
	
	static {
		getConfigurations(DEFAULT_CONFIGURATION_FILE_PATH);
	}
	
	public static Object getValue(@NonNull String property) {
		return getValue(null, property);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getValue(@NonNull String property, Class<T> clazz) {
		Object value = getValue(property);
		return (T) ConvertUtils.convert(value == null ? "" : value, clazz); 
	}
	
	public static Object getValue(String configurationFilePath, @NonNull String property) {
		return getConfigurations(configurationFilePath).get(property);
	}
	
	@SuppressWarnings("unchecked")
	private static Map<String, Object> getConfigurations(String configurationFilePath) {
		if (StringUtils.isNoneBlank(configurationFilePath)) {
			List<PropertySource<?>> propertySources = null;
			try {
				propertySources = new YamlPropertySourceLoader().load(configurationFilePath, new DefaultResourceLoader().getResource(configurationFilePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (propertySources != null && propertySources.size() > 0) {
				CONFIG_MAP.clear();
				propertySources.stream().filter(e -> e != null).forEach(e -> CONFIG_MAP.putAll((Map<String, Object>) e.getSource()));
			}
		}
		return CONFIG_MAP;
	}
}