package com.moraydata.general.primary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data

@Entity
@Table(name = "T_LOGIN")
public class Login extends BaseAbstractEntity {

	private static final long serialVersionUID = -4737041655325184278L;
	
	@Id
	@GeneratedValue
	protected Long id;
	
	@Column(columnDefinition = "bigint(20) not null comment '登录用户ID'")
	private Long userId;
	
	@Column(columnDefinition = "varchar(20) comment 'IP地址'")
	private String ipAddress;
	
	@Column(columnDefinition = "varchar(50) comment '设备品牌'")
	private String brand;
	
	@Column(columnDefinition = "varchar(50) comment '设备型号'")
	private String model;
	
	@Column(columnDefinition = "varchar(20) comment '屏幕尺寸'")
	private String screenSize;
	
	@Column(columnDefinition = "varchar(50) comment '设备操作系统'")
	private String operatingSystem;
	
	@Column(columnDefinition = "varchar(50) comment '所用浏览器'")
	private String browser;
}
