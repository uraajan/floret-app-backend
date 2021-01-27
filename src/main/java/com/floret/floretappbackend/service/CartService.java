package com.floret.floretappbackend.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.floret.floretappbackend.cache.CartCache;
import com.floret.floretappbackend.cache.StoreCache;
import com.floret.floretappbackend.entity.CartVO;
import com.floret.floretappbackend.entity.StoreVO;

@Service
public class CartService {

	@Autowired
	private CartCache cartCache;

	@Autowired
	private StoreCache storeCache;

	/**
	 * Method to intercept the pricing details and to get cart items for a given user <br/>
	 * Product prices are changeable, and if the product price is changed after it is added to user cart, it should be changed in the user cart too
	 * 
	 * @param username
	 * @return
	 */
	public List<CartVO> getCartVOsForUser(String username) {
		List<CartVO> cartVOs = cartCache.getCartVOsForUser(username);
		if (cartVOs != null && cartVOs.size() > 0) {
			Iterator<CartVO> cartIterator = cartVOs.iterator();
			int productDiscountedPrice;
			while (cartIterator.hasNext()) {
				CartVO cartVO = cartIterator.next();

				// Take the current product price form store map
				productDiscountedPrice = storeCache.getStoreVoForProductId(cartVO.getProductId()).getDiscountedPrice();

				// Manipulate cart item with the latest price
				cartVO.setProductPrice(productDiscountedPrice);
			}
		}
		return cartVOs;
	}

	public void addToCartForUser(String username, int productId) {
		System.out.println("Adding item to user cart. User: " + username + ", Product: " + productId);
		boolean productFoundInCart = false;
		List<CartVO> cartVOs = cartCache.getCartVOsForUser(username);
		if (cartVOs != null && cartVOs.size() > 0) {
			Iterator<CartVO> cartVoIterator = cartVOs.iterator();
			while (cartVoIterator.hasNext()) {
				CartVO cartVO = cartVoIterator.next();
				if (cartVO.getProductId() == productId) {
					StoreVO storeVO = storeCache.getStoreVoForProductId(productId);
					System.out.println("Item already exists in user cart. Adding one more");
					int productCount = cartVO.getProductCount();
					cartVO.setProductCount(++productCount);
					productFoundInCart = true;

					// Once if the product is added to user cart, it should be decremented from inventory
					decrementStoreAvailability(productId, storeVO);
				}
			}
			if (!productFoundInCart) {
				System.out.println("Cart has some items already. Adding an item");
				addProductToCart(productId, username, cartVOs);
			}
		} else {
			System.out.println("Cart empty for the user. Adding new item");
			cartVOs = new ArrayList<CartVO>();
			addProductToCart(productId, username, cartVOs);
		}
	}

	private void addProductToCart(int productId, String username, List<CartVO> cartVOs) {
		StoreVO storeVO = storeCache.getStoreVoForProductId(productId);
		CartVO cartVO = new CartVO();
		cartVO.setProductId(productId);
		cartVO.setProductName(storeVO.getName());
		cartVO.setProductCount(1);
		cartVO.setUsername(username);
		cartVOs.add(cartVO);
		cartCache.set_cartMap(username, cartVOs);

		// Once if the product is added to user cart, it should be decremented from inventory
		decrementStoreAvailability(productId, storeVO);
	}

	/**
	 * Method to decrement soap availability from the store whenever it is added to user cart
	 * 
	 * @param productId
	 * @param storeVO
	 */
	private void decrementStoreAvailability(int productId, StoreVO storeVO) {
		storeVO.setAvailabilityCount(storeVO.getAvailabilityCount() - 1);
		storeCache.addOrUpdateStoreCache(productId, storeVO);
		System.out.println("Store availability decremented");
	}

	public void removeFromCartForUser(String username, int productId) {
		System.out.println("Removing item from user cart. User: " + username + ", Product: " + productId);
		List<CartVO> cartVOs = cartCache.getCartVOsForUser(username);
		if (cartVOs != null && cartVOs.size() > 0) {
			int productCount;
			Iterator<CartVO> cartVoIterator = cartVOs.iterator();
			while (cartVoIterator.hasNext()) {
				CartVO cartVO = cartVoIterator.next();
				if (cartVO.getProductId() == productId) {

					// If multiple products of same type are added, decrement the count, else remove the product itself from the cart
					productCount = cartVO.getProductCount();
					if (productCount > 1) {
						System.out.println("Multiple products of this item is in user cart. Decementing 1 from cart");
						cartVO.setProductCount(--productCount);
					} else {
						System.out.println("Only one product of this item is in user cart. Removing it from cart");
						cartVoIterator.remove();
					}

					// Once if the product is removed from user cart, it should be incremented in inventory
					StoreVO storeVO = storeCache.getStoreVoForProductId(productId);
					incrementStoreAvailability(productId, storeVO);
				}
			}
		}
	}

	/**
	 * Method to increment soap availability in the store whenever it is removed from user cart
	 * 
	 * @param productId
	 * @param storeVO
	 */
	private void incrementStoreAvailability(int productId, StoreVO storeVO) {
		storeVO.setAvailabilityCount(storeVO.getAvailabilityCount() + 1);
		storeCache.addOrUpdateStoreCache(productId, storeVO);
		System.out.println("Store availability incremented");
	}

}
