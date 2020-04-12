package com.github.rshtishi.oauth2server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
public class OAuth2ConfigParameters {

	@Getter
	@Setter
	public static class Client {
		private String id;
		private String secret;
	}

	private Client client;
}
