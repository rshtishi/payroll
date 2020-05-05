package com.github.rshtishi.department;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {

	public static final String AUTHORIZATION = "Authorization";

	private String authorization = new String();

}
