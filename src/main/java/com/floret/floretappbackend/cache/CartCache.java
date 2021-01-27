package com.floret.floretappbackend.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.floret.floretappbackend.entity.CartVO;

@Repository
public class CartCache {

	private static Map<String, List<CartVO>> _cartMap = new HashMap<String, List<CartVO>>();

	public List<CartVO> getCartVOsForUser(String username) {
		return _cartMap.get(username);
	}

	public void set_cartMap(String username, List<CartVO> cartVOs) {
		_cartMap.put(username, cartVOs);
	}

	public void removeCartItemsOfUser(String username) {
		_cartMap.remove(username);
	}

}
