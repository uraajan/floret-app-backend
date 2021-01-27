package com.floret.floretappbackend.basic.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.floret.floretappbackend.basic.exception.DuplicateUserException;
import com.floret.floretappbackend.basic.exception.PasswordFailException;
import com.floret.floretappbackend.exception.UserNotAllowedException;

@RestController
//@CrossOrigin(origins = "${rest.cross.origin.exception.dns}")
@CrossOrigin(origins = "*")
public class BasicAuthenticationController {

	@Autowired
	private AuthenticationCache authenticationCache;

	@PostMapping(path = "/register")
	public ResponseEntity<?> register(@RequestBody BasicAuthRequest basicAuthRequest) {
		System.out.println("Entering the method: register");
		try {
			String authToken = authenticationCache.registerAndGenerateToken(basicAuthRequest);
			System.out.println("Exiting the method: register");
			return ResponseEntity.ok(new BasicAuthResponse(authToken));
		} catch (PasswordFailException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (DuplicateUserException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping(path = "/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody BasicAuthRequest basicAuthRequest) {
		System.out.println("Entering the method: authenticate");
		try {
			String authToken = authenticationCache.authenticateAndGenerateToken(basicAuthRequest);
			return ResponseEntity.ok(new BasicAuthResponse(authToken));
		} catch (UserNotAllowedException | UsernameNotFoundException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
		} catch (PasswordFailException e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping(path = "/logout/{username}")
	public ResponseEntity<?> logout(@PathVariable String username) {
		System.out.println("Entering the method: logout");
		authenticationCache.logoutUser(username);
		System.out.println("Exiting the method: logout");
		return ResponseEntity.ok("Logged out successfully");
	}

}
