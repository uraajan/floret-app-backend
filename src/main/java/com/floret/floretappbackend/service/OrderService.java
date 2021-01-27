package com.floret.floretappbackend.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.floret.floretappbackend.cache.CartCache;
import com.floret.floretappbackend.cache.OrderCache;
import com.floret.floretappbackend.cache.StoreCache;
import com.floret.floretappbackend.entity.CartVO;
import com.floret.floretappbackend.entity.OrderProductVO;
import com.floret.floretappbackend.entity.OrderVO;
import com.floret.floretappbackend.entity.StoreVO;

@Service
public class OrderService {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderCache orderCache;

	@Autowired
	private CartCache cartCache;

	@Autowired
	private StoreCache storeCache;

	public long placeOrder(String username) {

		// Get user cart
		List<CartVO> cartVOs = cartService.getCartVOsForUser(username);

		if (cartVOs != null && cartVOs.size() > 0) {
			// Generate order id
			long orderId = OrderCache.generateOrderNumber();
			int productCountInCart = 0;
			int itemCount = 0;
			int orderAmount = 0;

			// Frame order from cart
			List<OrderProductVO> orderProductVOs = new ArrayList<OrderProductVO>();
			for (CartVO cartVO : cartVOs) {
				productCountInCart = cartVO.getProductCount();
				OrderProductVO orderProductVO = new OrderProductVO();
				orderProductVO.setOrderId(orderId);
				orderProductVO.setProductId(cartVO.getProductId());
				orderProductVO.setProductName(cartVO.getProductName());
				orderProductVO.setProductCount(productCountInCart);
				orderProductVOs.add(orderProductVO);
				itemCount += productCountInCart;
				orderAmount += productCountInCart * cartVO.getProductPrice();
			}

			// Generate order VO
			OrderVO orderVO = new OrderVO();
			orderVO.setId(orderId);
			orderVO.setUsername(username);
			orderVO.setOrderProductVOs(orderProductVOs);
			orderVO.setOrderProcessed(false); // Will be set to true only when the order started processing by administrator
			orderVO.setOrderDelivered(false);
			orderVO.setOrderCancelled(false);
			orderVO.setItemCount(itemCount);
			orderVO.setOrderAmount(orderAmount);

			// Get previous orders of user
			List<OrderVO> orderVOs = orderCache.get_orderCacheForUser(username);

			// Set order to order cache
			if (orderVOs == null) {
				orderVOs = new ArrayList<OrderVO>();
			}
			orderVOs.add(orderVO);
			orderCache.set_orderCacheForUser(username, orderVOs);

			// Remove user cart
			cartCache.removeCartItemsOfUser(username);

			// Send order id to user
			return orderId;
		}
		return 0;
	}

	public boolean cancelOrderForUser(String username, long orderId) {
		List<OrderVO> orderVOs = orderCache.get_orderCacheForUser(username);
		Iterator<OrderVO> orderIterator = orderVOs.iterator();
		boolean orderFound = false;

		while (orderIterator.hasNext()) {
			OrderVO orderVO = orderIterator.next();
			if (orderVO.getId() == orderId) {

				// Add availability count in the store
				if (orderVO.getOrderProductVOs() != null && orderVO.getOrderProductVOs().size() > 0) {
					StoreVO storeVO;
					for (OrderProductVO orderProductVO : orderVO.getOrderProductVOs()) {
						storeVO = storeCache.getStoreVoForProductId(orderProductVO.getProductId());
						storeVO.setAvailabilityCount(storeVO.getAvailabilityCount() + orderProductVO.getProductCount());
						storeCache.addOrUpdateStoreCache(orderProductVO.getProductId(), storeVO);
					}
				}

				// Remove entry from orders list
				orderIterator.remove();
				orderFound = true;
			}
		}
		return orderFound;
	}

	public List<OrderVO> getAllUnprocessedOrders() {
		Map<String, List<OrderVO>> orderMap = orderCache.get_orderMap();
		List<OrderVO> unprocessedOrders = new ArrayList<OrderVO>();
		List<OrderVO> ordersList;

		for (String username : orderMap.keySet()) {
			ordersList = orderMap.get(username);
			for (OrderVO orderVO : ordersList) {
				if (!orderVO.isOrderDelivered() && !orderVO.isOrderCancelled() && !orderVO.isOrderProcessed()) {
					unprocessedOrders.add(orderVO);
				}
			}
		}

		return unprocessedOrders;

	}

}
