package com.github.rshtishi.department.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix="security",ignoreUnknownFields = true)
@Getter
@Setter
public class SecurityProperties {

	private Jwt jwt;
	
	@Getter
	@Setter
    public static class Jwt {
        private Resource publicKey;
	}
}
