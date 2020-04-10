package com.github.rshtishi.oauth2server.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@GetMapping(produces="application/json")
	public Map<String,Object> getUser(OAuth2Authentication user){
		Map<String,Object> userInfo = new HashMap<>();
		userInfo.put("user",user.getUserAuthentication().getPrincipal());
		userInfo.put("authorities", user.getUserAuthentication().getAuthorities());
		return userInfo;
	}

}
