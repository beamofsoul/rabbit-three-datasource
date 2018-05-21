package com.moraydata.general.management.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SecurityConfigurationProperties
 * @Description A container to store customized security configuration properties that set in application.yml. 
 * @author MingshuJian
 * @Date 2017年1月19日 下午4:27:30
 * @version 1.0.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "project.base.security")
public class SecurityConfigurationProperties {

	private String[] adminRoleMatchers;
	private String[] adminRoles;
	private String[] nonAuthenticatedMatchers;
	private String loginPage;
	private String defaultLoginSuccessUrl;
	private boolean alwaysUseDefaultSuccessUrl;
	private String logoutUrl;
	private String defaultLogoutSuccessUrl;
	private int maximumSessions;
	private boolean maxSessionsPreventsLogin;
	private String expiredUrl;
	private int tokenValiditySeconds;
	private String rememberMeParameter;
	private String rememberMeCookieName;
	private String[] ignoringMatchers;
}
