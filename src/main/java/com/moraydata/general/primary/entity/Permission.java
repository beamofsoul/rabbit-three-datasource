package com.moraydata.general.primary.entity;

import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "T_PERMISSION")
public class Permission extends BaseAbstractRelationalEntity {

	private static final long serialVersionUID = 3651456443720778568L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(columnDefinition = "varchar(20) unique not null comment '权限名称'")
	private String name;
	
	@Column(columnDefinition = "varchar(20) unique not null comment '权限行为'")
	private String action;
	
	@Column(columnDefinition = "enum('menu','button') not null comment '资源类型'")
	private String resourceType;
	
	@Column(columnDefinition = "bigint not null default 0 comment '父节点id'")
	private Long parentId;
	
	@Column(name = "[group]", columnDefinition = "varchar(20) not null comment '所在分组'")
	private String group;
	
	@Column(columnDefinition = "bigint not null default 0 comment '所在排序'")
	private Long sort;
	
	@Column(columnDefinition = "bit default 1 comment '是否可用'")
	private Boolean available = Boolean.FALSE;
	
	@RequiredArgsConstructor(access=AccessLevel.PROTECTED)
	public static enum ResourceType {
		BUTTON("button"),MENU("menu");
		@Getter private final String value;
		private static HashMap<String, ResourceType> codeValueMap = new HashMap<>(3);
		static {
			for (ResourceType resourceType : ResourceType.values()) {
				codeValueMap.put(resourceType.value, resourceType);
			}
		}
		public static ResourceType getInstance(String code) {
			return codeValueMap.get(code);
		}
		public static boolean exists(String code) {
			return codeValueMap.containsKey(code);
		}
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", action=" + action + ", resourceType=" + resourceType
				+ ", parentId=" + parentId + ", group=" + group + ", sort=" + sort + ", available=" + available + "]";
	}
}
