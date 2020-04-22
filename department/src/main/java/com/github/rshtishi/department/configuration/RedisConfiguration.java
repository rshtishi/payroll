package com.github.rshtishi.department.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {
	
	@Autowired
	private RedisConfigProperties redisConfigProperties;
	
	@Bean
	public RedisConfigProperties RedisConfigProperties() {
		return new RedisConfigProperties();
	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfig =new RedisStandaloneConfiguration();
		redisStandaloneConfig.setHostName(redisConfigProperties.getHost());
		redisStandaloneConfig.setPort(redisConfigProperties.getPort());
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfig);
		return jedisConnectionFactory;
	}
	
	@Primary
	@Bean
	public RedisTemplate<String,Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
	}

}