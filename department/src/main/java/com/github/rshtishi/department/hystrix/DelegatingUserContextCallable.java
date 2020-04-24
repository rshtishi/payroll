package com.github.rshtishi.department.hystrix;

import java.util.concurrent.Callable;

import com.github.rshtishi.department.UserContext;
import com.github.rshtishi.department.UserContextHolder;

public class DelegatingUserContextCallable<V> implements Callable<V> {

	private final Callable<V> delegate;
	private UserContext originalUserContext;

	public DelegatingUserContextCallable(Callable<V> delegate, UserContext userContext) {
		this.delegate = delegate;
		this.originalUserContext = userContext;
	}

	public DelegatingUserContextCallable(Callable<V> delegate) {
		this(delegate, UserContextHolder.getContext());
	}

	@Override
	public V call() throws Exception {
		UserContextHolder.setContext(originalUserContext);
		try {
			return delegate.call();
		} finally {
			this.originalUserContext = null;
		}
	}

	public String toString() {
		return delegate.toString();
	}

	public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext) {
		return new DelegatingUserContextCallable<V>(delegate, userContext);
	}

}
