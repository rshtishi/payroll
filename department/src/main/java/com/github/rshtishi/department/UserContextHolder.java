package com.github.rshtishi.department;

public class UserContextHolder {

	private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();

	public static final UserContext getContext() {
		UserContext context = userContext.get();

		if (context == null) {
			context = createEmptyContext();
			userContext.set(context);

		}
		return userContext.get();
	}

	public static final void setContext(UserContext context) {
		userContext.set(context);
	}

	public static final UserContext createEmptyContext() {
		return new UserContext();
	}

}
