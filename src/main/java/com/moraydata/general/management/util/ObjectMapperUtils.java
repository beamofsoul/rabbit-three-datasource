package com.moraydata.general.management.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum ObjectMapperUtils {
	
	INSTANCE;
	
	private ObjectMapper objectMapper;
	
	private ObjectMapperUtils() {
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.setObjectMapper(om);
	}
	
	public ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}
	
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	public <T> T convert(Object value, Class<T> clazz) {
		return this.getObjectMapper().convertValue(value, clazz);
	}
}
