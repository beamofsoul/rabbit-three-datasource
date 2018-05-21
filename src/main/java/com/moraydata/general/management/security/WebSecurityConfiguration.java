package com.moraydata.general.management.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.moraydata.general.primary.service.LoginService;
import com.moraydata.general.primary.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityConfigurationProperties props;
	
	@Autowired
	private AccessDeniedHandler defaultAccessDeniedHandler;
	
	@Bean
	@ConditionalOnMissingBean(PasswordEncoder.class)
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@ConditionalOnBean(LoginService.class)
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler();
	}
	
	@Bean
	@ConditionalOnBean(UserService.class)
	public AuthenticationUserDetailsService authenticationUserDetailsService() {
		return new AuthenticationUserDetailsService();
	}
	
	@Bean
	@ConditionalOnBean({AuthenticationUserDetailsService.class, PasswordEncoder.class})
	public SecretKeyAuthenticationProvider secretKeyAuthenticationProvider() {
		return new SecretKeyAuthenticationProvider();
	}
	
	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new RememberMeServices(props.getRememberMeCookieName(), authenticationUserDetailsService());
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(corsFilter(), ChannelProcessingFilter.class)
			.csrf()
				.disable()
			.headers()
				.frameOptions().disable()
				.and()
			.authorizeRequests()
				.antMatchers(props.getAdminRoleMatchers()).hasAnyRole(props.getAdminRoles())
				.antMatchers(props.getNonAuthenticatedMatchers()).permitAll()
				.and()
			.formLogin()
				.loginPage(props.getLoginPage())
				.permitAll()
				.defaultSuccessUrl(props.getDefaultLoginSuccessUrl(), props.isAlwaysUseDefaultSuccessUrl())
				.successHandler(authenticationSuccessHandler())
				.and()
			.logout()
				.logoutUrl(props.getLogoutUrl())
				.logoutSuccessUrl(props.getDefaultLogoutSuccessUrl())
				.and()
			.sessionManagement()
				.maximumSessions(props.getMaximumSessions())
				.maxSessionsPreventsLogin(props.isMaxSessionsPreventsLogin())
				.expiredUrl(props.getExpiredUrl())
				.and()
				.and()
			.rememberMe()
				.tokenValiditySeconds(props.getTokenValiditySeconds())
				.rememberMeParameter(props.getRememberMeParameter())
				.rememberMeServices(rememberMeServices())
				.and()
			.exceptionHandling()
				.accessDeniedHandler(defaultAccessDeniedHandler); // response as 403 when access denied by Ajax
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(secretKeyAuthenticationProvider());
	}
	
	@Bean
	public Filter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("x-auth-token");
        config.addExposedHeader("x-total-count");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
