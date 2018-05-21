package com.moraydata.general.secondary.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.moraydata.general.primary.entity.BaseAbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data

@Entity
@Table(name = "T_DATABASE")
public class Database extends BaseAbstractEntity {

	private static final long serialVersionUID = 6731734985570707263L;

	@Id
	@GeneratedValue
	protected Long id;
	
	@Column(name = "user_id", columnDefinition = "bigint(20) not null comment '用户编号'")
	private Long userId;
	
	@Column(name = "jdbc_url", columnDefinition = "varchar(256) comment '数据库连接地址'")
	private String jdbcUrl;
	
	@Column(columnDefinition = "varchar(125) comment '数据库登录用户名'")
	private String username;
	
	@Column(columnDefinition = "varchar(125) comment '数据库登录密码'")
	private String password;
}
