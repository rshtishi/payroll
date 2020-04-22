package com.github.rshtishi.department.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix="redis")
public class RedisConfigProperties {

		private String host;
		private int port;
}
