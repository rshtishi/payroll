package com.github.rshtishi.department.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.rshtishi.department.UserContext;
import com.github.rshtishi.department.UserContextHolder;

@Component
public class UserContextFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		UserContextHolder.getContext().setAuthorization(httpServletRequest.getHeader(UserContext.AUTHORIZATION));
		UserContextHolder.getContext().setTraceId(httpServletRequest.getHeader(UserContext.TRACE_ID));
		chain.doFilter(httpServletRequest, response);
	}

}
