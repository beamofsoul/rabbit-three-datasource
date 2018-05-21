package com.moraydata.general.management.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * Resource Server Configuration
 * @author Mingshu Jian
 * @date 2018-04-05
 */
@ConditionalOnProperty(name = "project.base.oauth2.enabled", havingValue = "true")
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Value("${project.base.oauth2.resourceIds}")
	private String resourceIds;
	
	@Value("${project.base.oauth2.filterUrl}")
	private String filterUrl;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceIds).stateless(false);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
        	.csrf()
        		.disable()
        	.anonymous()
        		.disable()
	        .requestMatchers()
	        	.antMatchers(filterUrl)
	        	.and()
	        .authorizeRequests()
	        	.antMatchers(filterUrl)
	        	.authenticated()
	        	.and()
	        .exceptionHandling()
	        	.accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
 
}
