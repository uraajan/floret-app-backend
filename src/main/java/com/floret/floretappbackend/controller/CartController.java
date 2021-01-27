package com.floret.floretappbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.floret.floretappbackend.entity.CartVO;
import com.floret.floretappbackend.entity.StoreVO;
import com.floret.floretappbackend.service.CartService;

@RestController
//@CrossOrigin(origins = "${rest.cross.origin.exception.dns}")
@CrossOrigin(origins = "*")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping(path = "/carts/{username}")
	public ResponseEntity<Map<String, Object>> getCartDataForUser(@PathVariable String username) {
		List<CartVO> cartVOs = cartService.getCartVOsForUser(username);
		Map<String, Object> cartMap = new HashMap<String, Object>();
		cartMap.put("cartData", cartVOs);
		int cartItems = 0;
		int cartValue = 0;
		if (cartVOs != null && cartVOs.size() > 0) {
			for (CartVO cartVO : cartVOs) {
				cartValue += cartVO.getProductCount() * cartVO.getProductPrice();
				cartItems += cartVO.getProductCount();
			}
		}
		cartMap.put("cartValue", cartValue);
		cartMap.put("cartItems", cartItems);
		return ResponseEntity.ok(cartMap);
	}

	@PutMapping(path = "/carts")
	public void addToCart(@RequestBody StoreVO storeVO) {
		cartService.addToCartForUser(storeVO.getName(), storeVO.getId());
	}

	@DeleteMapping(path = "/carts/{username}/{productId}")
	public void removeFromCart(@PathVariable String username, @PathVariable String productId) {
		cartService.removeFromCartForUser(username, Integer.parseInt(productId));
	}

}
