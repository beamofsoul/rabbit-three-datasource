package com.moraydata.general.management.oauth2;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import com.moraydata.general.management.security.AuthenticationUserDetailsService;
import com.moraydata.general.management.security.SecretKeyAuthenticationProvider;

/**
 * Authorization Server Configuration
 * @author Mingshu Jian
 * @date 2018-04-05
 * @see https://blog.csdn.net/fanbojiayou/article/details/79323315
 * @see http://andaily.com/spring-oauth-server/db_table_description.html
 */
@ConditionalOnProperty(name = "project.base.oauth2.enabled", havingValue = "true")
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private SecretKeyAuthenticationProvider secretKeyAuthenticationProvider;
	
	@Autowired
	private AuthenticationUserDetailsService authenticationUserDetailsService;
	
    @Autowired
    private RedisConnectionFactory redisConnectionFactory; // Using Redis to store OAuth2 access information, including access_token and refresh_token
    
    @Autowired
    private DataSource dataSource; // Using MySQL database to store client information in table of oauth_client_details

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(new ApplicableRedisTokenStore(redisConnectionFactory))
                .authenticationManager(authentication -> secretKeyAuthenticationProvider.authenticate(authentication))
                .userDetailsService(authenticationUserDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.allowFormAuthenticationForClients();
    }
}
