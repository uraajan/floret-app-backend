package com.floret.floretappbackend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.floret.floretappbackend.cache.OrderCache;
import com.floret.floretappbackend.entity.OrderVO;
import com.floret.floretappbackend.service.OrderService;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderCache orderCache;

	@PostMapping("/orders/{username}")
	public ResponseEntity<String> placeOrder(@PathVariable String username) {
		long orderId = orderService.placeOrder(username);
		if (orderId > 0) {
			return ResponseEntity.ok(String.valueOf(orderId));
		} else {
			return ResponseEntity.status(500).body("Order could not be placed");
		}
	}

	@GetMapping("/orders/{username}")
	public ResponseEntity<List<OrderVO>> getOrders(@PathVariable String username) {
		List<OrderVO> orderVOs = orderCache.get_orderCacheForUser(username);
		if (orderVOs != null && orderVOs.size() > 0) {
			return ResponseEntity.ok(orderVOs);
		} else {
			return ResponseEntity.ok(new ArrayList<OrderVO>());
		}
	}

	@DeleteMapping("/orders/{username}/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable String username, @PathVariable String orderId) {
		boolean orderFound = orderService.cancelOrderForUser(username, Long.parseLong(orderId));
		if (orderFound) {
			return ResponseEntity.ok("Order found and deleted");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
		}
	}

}
