package com.github.rshtishi.payroll.employee.security;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JWTTokenStoreConfig {

	@Autowired
	private OAuth2ConfigProperties oAuth2ConfigProperties;

	@Bean
	public OAuth2ConfigProperties oAuth2ConfigProperties() {
		return new OAuth2ConfigProperties();
	}

	private String getPublicKeyAsString() {
		try {
			return IOUtils.toString(oAuth2ConfigProperties.getJwt().getPublicKey().getInputStream(),
					StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setVerifierKey(getPublicKeyAsString());
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new JWTTokenEnhancer();
	}

	@Bean
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setTokenEnhancer(tokenEnhancer());
		return defaultTokenServices;
	}

}
