package com.moraydata.general.primary.service.impl;

import static com.moraydata.general.management.util.BooleanExpressionUtils.addExpression;
import static com.moraydata.general.management.util.BooleanExpressionUtils.like;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toInteger;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toLocalDateTime;
import static com.moraydata.general.management.util.BooleanExpressionUtils.toLong;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.moraydata.general.primary.entity.User;
import com.moraydata.general.primary.entity.query.QUser;
import com.moraydata.general.primary.repository.UserRepository;
import com.moraydata.general.primary.service.UserService;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public User get(Long instanceId) {
		User one = userRepository.findOne(instanceId);
		if (one != null) {
			Hibernate.initialize(one.getRoles());
		}
		return one;
	}

	@Override
	public List<User> get(Long... instanceIds) {
		return userRepository.findByIds(instanceIds);
	}

	@Override
	public Page<User> get(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<User> get(Pageable pageable, Predicate predicate) {
		return userRepository.findAll(predicate, pageable);
	}

	@Override
	public List<User> get() {
		return userRepository.findAll();
	}

	@Override
	public BooleanExpression search(JSONObject conditions) {
		if (conditions == null) return null;

		QUser user = QUser.user;
		BooleanExpression exp = null;
		
		String id = conditions.getString(user.id.getMetadata().getName());
		exp = addExpression(id, exp, user.id.eq(toLong(id)));
		
		String nickname = conditions.getString(user.nickname.getMetadata().getName());
		exp = addExpression(nickname, exp, user.nickname.like(like(nickname)));
		
		String username = conditions.getString(user.username.getMetadata().getName());
		exp = addExpression(username, exp, user.username.like(like(username)));
		
		String password = conditions.getString(user.password.getMetadata().getName());
		exp = addExpression(password, exp, user.password.like(like(password)));
		
		String email = conditions.getString(user.email.getMetadata().getName());
		exp = addExpression(email, exp, user.email.like(like(email)));
		
		String phone = conditions.getString(user.phone.getMetadata().getName());
		exp = addExpression(phone, exp, user.phone.like(like(phone)));
		
		String status = conditions.getString(user.status.getMetadata().getName());
		exp = addExpression(status, exp, user.status.eq(toInteger(status)));
		
		String createdDate = conditions.getString(user.createdDate.getMetadata().getName());
		exp = addExpression(createdDate, exp, user.createdDate.before(toLocalDateTime(createdDate)));
		
		return exp;
	}

	/**
	 * Get unique user instance by user-name, otherwise return a null value.
	 * The method is used when user login. 
	 * @see AuthenticationUserDetailsService#getUser0
	 * @param username - user-name used to get unique user instance.
	 * @return an instance of user have gotten or a null value. 
	 */
	@Transactional
	@Override
	public User get(String username) {
		User currentUser = userRepository.findByUsername(username);
		if (currentUser != null) {
			Hibernate.initialize(currentUser.getRoles());
		}
		return currentUser;
	}
	
	/**
	 * For Open API -> Wechat
	 * @param openId
	 * @return User
	 * @throws Exception
	 */
	@Transactional
	@Override
	public User getByOpenId(String openId) throws Exception {
		User user = userRepository.findByOpenId(openId);
		if (user != null) {
			Hibernate.initialize(user.getRoles());
		}
		return user;
	}
	
	/**
	 * For Open API
	 * @param userId
	 * @throws Exception
	 */
	@Override
	public List<User> getByParentId(Long userId) throws Exception {
		QUser $ = new QUser("User");
		return userRepository.findByPredicate($.parentId.eq(userId));
	}
}
