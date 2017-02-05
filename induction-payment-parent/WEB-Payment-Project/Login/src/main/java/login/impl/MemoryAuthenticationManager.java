package login.impl;

import java.util.HashMap;
import java.util.Map;

import login.AuthenticateRequest;
import login.AuthenticationManager;
import login.UserInfo;

public class MemoryAuthenticationManager implements AuthenticationManager {

	private Map<String, Credentials> credentials = new HashMap<>();

	public MemoryAuthenticationManager() {
		Credentials admin = new Credentials();
		admin.username = "admin";
		admin.fullName = "Administrator";
		admin.password = "pass1234";
		credentials.put("admin", admin);

		Credentials manager = new Credentials();
		manager.username = "manager";
		manager.fullName = "System Manager";
		manager.password = "pass1234";
		credentials.put("manager", manager);
	}

	@Override
	public UserInfo authenticate(AuthenticateRequest request) throws AuthenticationException {
		String username = request.getUsername();
		if (username != null && credentials.containsKey(username)) {
			Credentials info = credentials.get(username);
			if (info.password.equals(request.getPassword())) {
				return info.getUserInfo();
			}
		}
		throw new BadCredentialsException("Bad credentials");
	}

	private class Credentials {
		private String username;
		private String fullName;
		private String password;

		UserInfo getUserInfo() {
			return new UserInfo() {

				private static final long serialVersionUID = 1L;

				@Override
				public String getUsername() {
					return username;
				}

				@Override
				public String getFullName() {
					return fullName;
				}
			};
		}
	}
}
