package com.floret.floretappbackend.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.floret.floretappbackend.entity.OrderVO;

@Repository
public class OrderCache {

	private static long _orderNumber;

	private static Map<String, List<OrderVO>> _orderMap = new HashMap<String, List<OrderVO>>();

	public static long generateOrderNumber() {
		return ++_orderNumber;
	}

	public List<OrderVO> get_orderCacheForUser(String username) {
		return _orderMap.get(username);
	}

	public void set_orderCacheForUser(String username, List<OrderVO> orderVOs) {
		_orderMap.put(username, orderVOs);
	}

	public Map<String, List<OrderVO>> get_orderMap() {
		return _orderMap;
	}

}
