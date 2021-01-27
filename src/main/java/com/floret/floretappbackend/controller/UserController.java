package com.floret.floretappbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.floret.floretappbackend.basic.auth.AuthenticationCache;
import com.floret.floretappbackend.basic.auth.BasicAuthRequest;

@RestController
//@CrossOrigin(origins = "${rest.cross.origin.exception.dns}")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private AuthenticationCache authenticationCache;

	@GetMapping(path = "/users/{username}")
	public ResponseEntity<BasicAuthRequest> getUser(@PathVariable String username) {
		BasicAuthRequest basicAuthRequest = authenticationCache.getUserByUsername(username);
		if (basicAuthRequest != null) {
			return ResponseEntity.ok(basicAuthRequest);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
