package com.moraydata.general.management.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.moraydata.general.management.util.Constants;
import com.moraydata.general.management.util.HttpSessionUtils;
import com.moraydata.general.primary.entity.Login;
import com.moraydata.general.primary.entity.User;
import com.moraydata.general.primary.entity.dto.UserExtension;

/**
 * @ClassName RememberMeServices
 * @Description {@link org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices}
 *              extension that customizes actions happen when onLoginSuccess and
 *              processAutoLoginCookie once logging user uses remember-me
 *              functionality.
 * @author MingshuJian
 * @Date 2017年8月28日 下午4:53:26
 * @version 1.0.0
 */
public class RememberMeServices extends TokenBasedRememberMeServices {

	@Autowired
	private AuthenticationUserDetailsService authenticationUserDetailsService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	public RememberMeServices(String key, UserDetailsService userDetailsService) {
		super(key, userDetailsService);
	}

	@Override
	public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		super.onLoginSuccess(request, response, successfulAuthentication);
		SecurityContextHolder.getContext().setAuthentication(successfulAuthentication);
		this.afterOnLoginSuccess(request, response, successfulAuthentication);
	}

	private void afterOnLoginSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication successfulAuthentication) {
		User currentUser = authenticationUserDetailsService.getUser0(successfulAuthentication.getName());
		saveSessionProperties(request, AuthenticationUserDetailsService.convertToUserExtension(currentUser),
				authenticationSuccessHandler.new LoginRecordHandler().saveLoginRecord(currentUser, request, response));
	}

	@Override
	protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
			HttpServletResponse response) {
		UserDetails userDetails = super.processAutoLoginCookie(cookieTokens, request, response);
		this.afterProcessAutoLoginCookie(userDetails, request, response);
		return userDetails;
	}

	private void afterProcessAutoLoginCookie(UserDetails userDetails, HttpServletRequest request,
			HttpServletResponse response) {
		User currentUser = authenticationUserDetailsService.getUser0(userDetails.getUsername());
		saveSessionProperties(request, AuthenticationUserDetailsService.convertToUserExtension(currentUser),
				authenticationSuccessHandler.new LoginRecordHandler().saveLoginRecord(currentUser, request, response));
	}

	private void saveSessionProperties(HttpServletRequest request, UserExtension userExtension, Login currentLogin) {
		HttpSessionUtils.saveCurrentUser(request.getSession(), userExtension);
		request.getSession().setAttribute(Constants.CURRENT.LOGIN, currentLogin);
	}
}
