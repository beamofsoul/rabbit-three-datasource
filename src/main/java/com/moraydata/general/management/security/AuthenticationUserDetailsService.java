package com.moraydata.general.management.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.moraydata.general.management.util.Constants;
import com.moraydata.general.primary.entity.Role;
import com.moraydata.general.primary.entity.User;
import com.moraydata.general.primary.entity.dto.UserExtension;
import com.moraydata.general.primary.service.UserService;

/**
 * @ClassName AuthenticationUserDetailsService
 * @Description {@link org.springframework.security.core.userdetails.UserDetailsService} implementation that customizes the way of loading user by username inputed.
 * @author MingshuJian
 * @Date 2017年1月19日 下午4:28:32
 * @version 1.0.0
 */
public class AuthenticationUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	/*
	 * (非 Javadoc)  
	 * <p>Title: loadUserByUsername</p>  
	 * <p>Description: load user by user-name from DB, then package it to an {@link com.beamofsoul.rabbit.primary.entity.dto.UserExtension} object instance, return it finally.</p>
	 * @param username
	 * @return UserExtension
	 * @throws UsernameNotFoundException  
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getUser(username);
		return convertToUserExtension(user);
	}
	
	protected static UserExtension convertToUserExtension(final User user) {
		UserExtension userExtension = new UserExtension(
                user.getId(), user.getUsername(), user.getPassword(), user.getNickname(), user.getPhotoString(), user.getSceneId(),
                true, // if enable is true, otherwise false.
                true, // if accountNonExpired is true, otherwise false. 
                true, // if credentialsNonExpired is true, otherwise false.
                true, // if accountNonLocked is true, otherwise false.
                getAuthorities(user));
		return userExtension;
	}

	private User getUser(final String username) {
		if (StringUtils.isBlank(username))
            throw new UsernameNotFoundException("Username is null");
        User user = getUser0(username);
        if (user == null)
			throw new UsernameNotFoundException("User cannot found");
        if (user.getStatusEnum().equals(User.Status.LOCKED))
        	throw new UsernameNotFoundException("User has been locked");
        if (user.getRoles() == null || user.getRoles().size() == 0)
        	throw new UsernameNotFoundException("User has not been allotted any roles");
		return user;
	}
	
	protected User getUser0(final String username) {
		if (username.length() > Constants.WECHAT.SCAN_LOGIN_OPEN_ID_MIN_LENGTH) {
			try {
				// 2018-04-10 Addition support of using open id as user-name when user using QR code to scan to login the system
				// The default length of user-name is between 6 and 16
				// Ensured that current user is using open id as its' user-name
				User targetUser = userService.getByOpenId(username);
				targetUser.setSceneId(Constants.WECHAT.SCAN_LOGIN_SCENE_ID);
				return targetUser;
			} catch (Exception e) {
				throw new UsernameNotFoundException(e.getMessage());
			}
		}
		return userService.get(username);
	}

	protected static Set<GrantedAuthority> getAuthorities(User user) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Role role : user.getRoles())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
		return authorities;
	}

}
