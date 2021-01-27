package com.floret.floretappbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.floret.floretappbackend.basic.auth.BasicAuthRequest;
import com.floret.floretappbackend.basic.auth.BasicAuthenticationController;
import com.floret.floretappbackend.constants.FloretConstants;
import com.floret.floretappbackend.controller.AdminController;

@SpringBootTest
class FloretappBackendApplicationTests {

	@Autowired
	private AdminController adminController;

	@Autowired
	private BasicAuthenticationController basicAuthenticationController;

	@Test
	void contextLoads() {
		System.out.println("FloretappBackendApplicationTests: contextLoads");
		System.out.println();

		testUserRegister();
		testAdminLogin();

	}

	private void testUserRegister() {
		System.out.println("Normal user register test");

		BasicAuthRequest basicAuthRequest = new BasicAuthRequest("uraajan", "123", "Udhayakumar", "Raajan", "Dharapuram", "9176331476");
		ResponseEntity<?> responseEntity = basicAuthenticationController.register(basicAuthRequest);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		basicAuthRequest = new BasicAuthRequest("uraajan", "", "Udhayakumar", "Raajan", "Dharapuram", "9176331476");
		responseEntity = basicAuthenticationController.register(basicAuthRequest);
		assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());

		basicAuthRequest = new BasicAuthRequest("uk", "", "Udhayakumar", "Raajan", "Dharapuram", "9176331476");
		responseEntity = basicAuthenticationController.register(basicAuthRequest);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		System.out.println();
	}

	private void testAdminLogin() {
		System.out.println("Administrator user login test");

		BasicAuthRequest basicAuthRequest = new BasicAuthRequest(FloretConstants.ADMIN_USERNAME, FloretConstants.ADMIN_PASSWORD, "", "", "", "");
		ResponseEntity<?> responseEntity = adminController.authenticate(basicAuthRequest);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		basicAuthRequest = new BasicAuthRequest(FloretConstants.ADMIN_USERNAME, "123", "", "", "", "");
		responseEntity = adminController.authenticate(basicAuthRequest);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
		
		basicAuthRequest = new BasicAuthRequest(FloretConstants.ADMIN_USERNAME, "", "", "", "", "");
		responseEntity = adminController.authenticate(basicAuthRequest);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		basicAuthRequest = new BasicAuthRequest("uraajan", "", "", "", "", "");
		responseEntity = adminController.authenticate(basicAuthRequest);
		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, responseEntity.getStatusCode());

		System.out.println();
	}

}
