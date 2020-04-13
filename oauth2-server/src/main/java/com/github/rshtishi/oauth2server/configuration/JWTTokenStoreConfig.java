package com.github.rshtishi.oauth2server.configuration;

import java.security.KeyPair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class JWTTokenStoreConfig {

	@Autowired
	OAuth2ConfigParameters oAuth2ConfigParameters;

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(oAuth2ConfigParameters.getJwt().getKeyStore(),
				oAuth2ConfigParameters.getJwt().getKeyStorePassword().toCharArray());
		KeyPair keyPair = keyStoreKeyFactory.getKeyPair(oAuth2ConfigParameters.getJwt().getKeyPairAlias());
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		// jwtAccessTokenConverter.setSigningKey("345345fsdgsf5345");
		jwtAccessTokenConverter.setKeyPair(keyPair);
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	@Primary
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public OAuth2ConfigParameters oAuth2ConfigParameters() {
		return new OAuth2ConfigParameters();
	}

}
