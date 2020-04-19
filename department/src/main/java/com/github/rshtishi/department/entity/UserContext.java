package com.github.rshtishi.department.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {

	public static final String AUTHORIZATION = "Authorization";
	public static final String CORRELATION_ID = "correlation-id";

	private String authorization = new String();
	private String correlationId = new String();

}
