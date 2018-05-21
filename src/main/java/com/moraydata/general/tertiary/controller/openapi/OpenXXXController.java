package com.moraydata.general.tertiary.controller.openapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moraydata.general.management.util.ResponseEntity;
import com.moraydata.general.secondary.service.DatabaseService;
import com.moraydata.general.tertiary.service.DataStreamService;

@RequestMapping("/open/xxx")
@RestController
public class OpenXXXController {
	
	@Autowired
	private DatabaseService databaseService;
	
	@Autowired
	private DataStreamService dataStreamService;
	
	@GetMapping("/{param}")
	public ResponseEntity testResource(@PathVariable String param) {
		try {
			System.out.println(databaseService.get(1L));
			System.out.println(dataStreamService.get(1L));
			return ResponseEntity.of(StringUtils.isNotBlank(param) ? "测试成功，已接收到测试参数" : "测试失败，未接收到任何测试参数", param);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.UNKNOWN_ERROR;
		}
	}
}
