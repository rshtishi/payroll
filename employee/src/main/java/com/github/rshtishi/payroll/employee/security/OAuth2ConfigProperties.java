package com.github.rshtishi.payroll.employee.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix="security",ignoreUnknownFields = true)
@Getter
@Setter
public class OAuth2ConfigProperties {
	
	private Jwt jwt;
	
	@Getter
	@Setter
    public static class Jwt {
        private Resource publicKey;
	}
}
