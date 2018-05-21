package com.moraydata.general.primary.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

//@JsonIgnoreProperties({"permissions"})

@Entity
@Table(name = "T_ROLE")
public class Role extends BaseAbstractEntity {

	private static final long serialVersionUID = -8061467633030908642L;
	private static final int MAX_PRIORITY = 99;

	@Id
	@GeneratedValue
	private Long id;

	@Column(columnDefinition = "varchar(20) unique not null comment '角色名称'")
	private String name;

	@Column(columnDefinition = "int default 99 comment '角色优先级 - 优先级数值越低,代表优先级越大,角色normal的优先级为99'")
	private int priority;
	
	@Column(columnDefinition = "bit default 1 comment '是否可用'")
	private boolean available;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JoinTable(name = "T_ROLE_PERMISSION", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permission_id") })
	private Set<Permission> permissions = new HashSet<Permission>(0);

	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.priority = MAX_PRIORITY;
		this.available = true;
	}
	
	public Role(Long id, String name, int priority) {
		super();
		this.id = id;
		this.name = name;
		this.priority = priority;
		this.available = true;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", priority=" + priority + ", available=" + available + "]";
	}
}