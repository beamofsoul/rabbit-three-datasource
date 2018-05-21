package com.moraydata.general.management.util;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import lombok.NonNull;

public class HttpUrlUtils {

	public static String integrate(@NonNull String basicUrl, Map<String, Object> parameters) {
		StringBuilder url = new StringBuilder(basicUrl).append("?");
		if (MapUtils.isNotEmpty(parameters)) {
			parameters.entrySet().stream().forEach(e -> url.append("&").append(e.getKey()).append("=").append(e.getValue()));
		}
		url.deleteCharAt(url.indexOf("?&") + 1);
		return url.toString();
	}
}
