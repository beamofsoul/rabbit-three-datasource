package com.moraydata.general.management.util;

import static com.moraydata.general.management.util.Constants.RESPONSE_ENTITY.ERROR;
import static com.moraydata.general.management.util.Constants.RESPONSE_ENTITY.OPERATION_FAILURE_CAUSED_BY_UNKNOWN_ERROR;
import static com.moraydata.general.management.util.Constants.RESPONSE_ENTITY.SUCCESS;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JSON format of http response entity which includes code, message, and data.
 * @author Mingshu Jian
 * @date 2018-04-05
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity {
	
	private String code;
	private String message;
	private Object data;
	
	public static final ResponseEntity UNKNOWN_ERROR = ResponseEntity.builder().code(ERROR).message(OPERATION_FAILURE_CAUSED_BY_UNKNOWN_ERROR).build();
	
	public static ResponseEntity error(String message) {
		return ResponseEntity
				.builder()
				.code(ERROR)
				.message(message)
				.build();
	}
	
	public static ResponseEntity success(String message, Object data) {
		return ResponseEntity
				.builder()
				.code(SUCCESS)
				.message(message)
				.data(data)
				.build();
	}
	
	public static ResponseEntity of(String message, Object data) {
		return data == null ? error(message) : success(message, data);
	}
}
