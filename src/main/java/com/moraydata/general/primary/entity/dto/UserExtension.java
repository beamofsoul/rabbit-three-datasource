package com.moraydata.general.primary.entity.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName UserExtension
 * @Description An extension of User object.
 * <p>It will be stored in HTTP session as the type of current user model after user login.</p>
 * @author MingshuJian
 * @Date 2017年8月28日 下午4:00:22
 * @version 1.0.0
 */
@Setter
@Getter
public class UserExtension extends User {

	private static final long serialVersionUID = -5629976951526866748L;

	private Long userId;
	private String nickname;
	private String photoString;
	
	private String sceneId; // 2018-04-10 使用微信扫码登录时，openId作为用户名，sceneId作为登录密码。扫码登录场景默认Id为911

	public UserExtension(Long userId, String username, String password, String nickname, String photoString, String sceneId, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userId = userId;
		this.nickname = nickname;
		this.photoString = photoString;
		this.sceneId = sceneId;
	}
}
