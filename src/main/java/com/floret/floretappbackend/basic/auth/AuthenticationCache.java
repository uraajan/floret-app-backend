package com.floret.floretappbackend.basic.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.floret.floretappbackend.basic.exception.DuplicateUserException;
import com.floret.floretappbackend.basic.exception.PasswordFailException;
import com.floret.floretappbackend.constants.FloretConstants;
import com.floret.floretappbackend.exception.UserNotAllowedException;

@Repository
public class AuthenticationCache {

	@Autowired
	private BasicAuthUserDetailsService basicAuthUserDetailsService;

	private static long _tokenValidationLimit = 60 * 60 * 1000; // 60 minutes * 60 seconds * 1000 milliseconds

	private static Map<String, BasicAuthRequest> _tokenCache = new HashMap<String, BasicAuthRequest>();
	private static Map<String, Long> _tokenValidationCache = new HashMap<String, Long>();

	private static Map<String, BasicAuthRequest> _userCache = new HashMap<String, BasicAuthRequest>();
	private static Map<String, String> _userTokenCache = new HashMap<String, String>();

	static {
		String adminUsername = FloretConstants.ADMIN_USERNAME;
		String adminPassword = FloretConstants.ADMIN_PASSWORD;
		BasicAuthRequest basicAuthRequest = new BasicAuthRequest(adminUsername, adminPassword, "Floret", "Administrator", "", "");
		_userCache.put(adminUsername, basicAuthRequest);
		BasicAuthUserDetailsService.addUser(new BasicAuthUserDetails(System.currentTimeMillis(), adminUsername, adminPassword, FloretConstants.ADMINISTRATOR));
	}

	public String registerAndGenerateToken(BasicAuthRequest basicAuthRequest) throws Exception {
		System.out.println("basicAuthRequest: " + basicAuthRequest.toString());
		String username = basicAuthRequest.getUsername();
		if (_userCache.containsKey(username)) {
			throw new DuplicateUserException("User already exists. Please select a different username");
		}
		String authToken = generateAuthToken(username, basicAuthRequest.getPassword());
		_tokenCache.put(authToken, basicAuthRequest);
		_tokenValidationCache.put(authToken, System.currentTimeMillis());
		_userCache.put(username, basicAuthRequest);
		_userTokenCache.put(username, authToken);
		BasicAuthUserDetailsService.addUser(new BasicAuthUserDetails(System.currentTimeMillis(), username, basicAuthRequest.getPassword(), FloretConstants.USER));
		return authToken;
	}

	private String generateAuthToken(String username, String password) throws PasswordFailException {
		if (password != null && password.length() > 0) {
			String authToken = System.currentTimeMillis() + username + System.currentTimeMillis() + password + System.currentTimeMillis();
			System.out.println("authToken: " + authToken);
			return authToken;
		} else {
			throw new PasswordFailException("Password empty");
		}
	}

	public String authenticateAndGenerateToken(BasicAuthRequest basicAuthRequest) throws Exception {
		System.out.println("authenticateAndGenerateToken: basicAuthRequest: " + basicAuthRequest.toString());

		UserDetails userDetails = basicAuthUserDetailsService.loadUserByUsername(basicAuthRequest.getUsername());

		// Check if user details exist
		if (userDetails != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(userDetails.getAuthorities());
			// Check if the logged in user belong to normal user category (Should not be administrator user)
			if (grantedAuthorities.get(0).getAuthority().equals(FloretConstants.USER)) {
				return authenticate(basicAuthRequest);
			} else {
				throw new UserNotAllowedException("Please login with normal user credentials!");
			}
		} else {
			throw new UserNotAllowedException("User not found!");
		}

	}

	private String authenticate(BasicAuthRequest basicAuthRequest) throws Exception {
		// Check if user exists in user cache
		if (_userCache.containsKey(basicAuthRequest.getUsername())) {

			// Check if password non-empty
			if (basicAuthRequest.getPassword() != null && basicAuthRequest.getPassword().length() > 0) {
				// Check for password match
				if (basicAuthRequest.getPassword().equals(_userCache.get(basicAuthRequest.getUsername()).getPassword())) {
					String authToken = generateAuthToken(basicAuthRequest.getUsername(), basicAuthRequest.getPassword());
					_tokenCache.put(authToken, basicAuthRequest);
					_tokenValidationCache.put(authToken, System.currentTimeMillis());
					_userTokenCache.put(basicAuthRequest.getUsername(), authToken);
					return authToken;
				} else {
					throw new Exception("Invalid password");
				}
			} else {
				throw new PasswordFailException("Password empty");
			}
		} else {
			throw new Exception("User doesn't exist. Please register to continue!");
		}
	}

	public String authenticateAdminUserAndGenerateToken(BasicAuthRequest basicAuthRequest) throws Exception {
		System.out.println("authenticateAdminUserAndGenerateToken");
		UserDetails userDetails = basicAuthUserDetailsService.loadUserByUsername(basicAuthRequest.getUsername());
		if (userDetails != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(userDetails.getAuthorities());
			if (grantedAuthorities.get(0).getAuthority().equals(FloretConstants.ADMINISTRATOR)) {
				return authenticate(basicAuthRequest);
			} else {
				throw new UserNotAllowedException("Please login with admin user credentials!");
			}
		}
		return null;
	}

	public boolean validateToken(String token) {

		// Check whether the token exist, else token invalid
		if (_tokenCache.containsKey(token)) {

			// Check whether the token expired, if yes, token invalid
			if (System.currentTimeMillis() - _tokenValidationCache.get(token) < _tokenValidationLimit) {

				// Refresh last access time in token validation cache, to keep the session alive for prolonged access
				_tokenValidationCache.put(token, System.currentTimeMillis());

				return true;
			} else {
				invalidateSession(token);
				return false;
			}
		} else {
			return false;
		}
	}

	public BasicAuthRequest getUserDetailsFromTokenCache(String token) {
		return _tokenCache.get(token);
	}

	public BasicAuthRequest getUserByUsername(String username) {
		return _userCache.get(username);
	}

	public void logoutUser(String username) {
		if (_userTokenCache.containsKey(username)) {
			invalidateSession(_userTokenCache.get(username));
			_userTokenCache.remove(username);
		}
	}

	private void invalidateSession(String token) {
		if (_tokenCache.containsKey(token)) {
			_tokenCache.remove(token);
			_tokenValidationCache.remove(token);
		}
	}

}
