package com.moraydata.general.primary.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@MappedSuperclass
public class BaseAbstractRelationalEntity extends BaseAbstractEntity {

	private static final long serialVersionUID = -5824855325045563971L;
	
	@Transient
	private Long countOfChildren;

	@Transient
	private Boolean isParent;
	
	public BaseAbstractRelationalEntity() {
		super();
	}
	
	public BaseAbstractRelationalEntity(Long countOfChildren) {
		super();
		setter(countOfChildren);
	}
	
	public void setCountOfChildren(Long countOfChildren) {
		setter(countOfChildren);
	}
	
	private void setter(Long countOfChildren) {
		if (countOfChildren == null) countOfChildren = 0L;
		this.countOfChildren = countOfChildren;
		this.isParent = countOfChildren > 0;
	}
}
