package com.moraydata.general.primary.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "T_ROLE_PERMISSION")
public class RolePermission extends BaseAbstractEntity {

	private static final long serialVersionUID = 2066960160095509120L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERMISSION_ID")
	private Permission permission;
	
	public RolePermission(Role role, Permission permission) {
		this.role = role;
		this.permission = permission;
	}
}
