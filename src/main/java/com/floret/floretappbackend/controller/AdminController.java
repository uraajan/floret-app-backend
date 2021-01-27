package com.floret.floretappbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.floret.floretappbackend.basic.auth.AuthenticationCache;
import com.floret.floretappbackend.basic.auth.BasicAuthRequest;
import com.floret.floretappbackend.basic.auth.BasicAuthResponse;
import com.floret.floretappbackend.basic.exception.PasswordFailException;
import com.floret.floretappbackend.cache.StoreCache;
import com.floret.floretappbackend.entity.OrderVO;
import com.floret.floretappbackend.entity.StoreVO;
import com.floret.floretappbackend.exception.UserNotAllowedException;
import com.floret.floretappbackend.service.OrderService;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private AuthenticationCache authenticationCache;

	@Autowired
	private StoreCache storeCache;

	@Autowired
	private OrderService orderService;

	@PostMapping("/admin/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody BasicAuthRequest basicAuthRequest) {
		try {
			String authToken = authenticationCache.authenticateAdminUserAndGenerateToken(basicAuthRequest);
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

	@GetMapping(path = "/admin/products/{productId}")
	public ResponseEntity<StoreVO> getProductForId(@PathVariable String productId) {
		StoreVO storeVO = storeCache.getStoreVoForProductId(Integer.parseInt(productId));
		return ResponseEntity.ok(storeVO);
	}

	@PutMapping("/admin/products")
	public ResponseEntity<?> updateProduct(@RequestBody StoreVO storeVO) {
		if (storeVO.getId() > 0) {
			storeCache.addOrUpdateStoreCache(storeVO.getId(), storeVO);
			return ResponseEntity.ok("Product updated in store");
		} else {
			storeVO.setId(storeCache.generateProductId());
			storeCache.addOrUpdateStoreCache(storeVO.getId(), storeVO);
			return ResponseEntity.ok("Product added to store");
		}
	}

	@GetMapping("/admin/orders")
	public ResponseEntity<?> getUnprocessedOrders() {
		try {
			List<OrderVO> unprocessedOrders = orderService.getAllUnprocessedOrders();
			return ResponseEntity.ok(unprocessedOrders);
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred retrieving the unprocessed orders");
		}
	}

}
