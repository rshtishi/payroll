package com.github.rshtishi.oauth2server.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	OAuth2ConfigParameters oauth2ConfigParameters;

	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private TokenEnhancer tokenEnhancer;
	@Autowired
	private DefaultTokenServices defaultTokenServices;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter));
		defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
		endpoints.tokenStore(tokenStore).tokenEnhancer(tokenEnhancerChain)
				// .accessTokenConverter(jwtAccessTokenConverter)
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(oauth2ConfigParameters.getClient().getId())
				.secret(passwordEncoder.encode(oauth2ConfigParameters.getClient().getSecret()))
				.authorizedGrantTypes("refresh_token", "password", "client_credentials")
				.scopes("webclient", "mobileclient");
	}
}
