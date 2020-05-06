package com.github.rshtishi.department;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContext {

	public static final String AUTHORIZATION = "Authorization";
	public static final String TRACE_ID = "trace-id";

	private String authorization = new String();
	private String traceId = new String();

}
