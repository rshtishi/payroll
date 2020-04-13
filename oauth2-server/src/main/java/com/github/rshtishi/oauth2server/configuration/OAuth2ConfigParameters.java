package com.github.rshtishi.oauth2server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
public class OAuth2ConfigParameters {
	
	private Client client;
	private Jwt jwt;

	@Getter
	@Setter
	public static class Client {
		private String id;
		private String secret;
	}
	
	@Getter
	@Setter
    public static class Jwt {
        private Resource keyStore;
        private String keyStorePassword;
        private String keyPairAlias;
        private String keyPairPassword;   
	}

	
}
