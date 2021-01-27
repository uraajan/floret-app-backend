package com.floret.floretappbackend.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class OrderProductVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3888588557507038461L;

	private long orderId;

	private int productId;

	private String productName;

	private int productCount;

	public OrderProductVO() {
		super();
	}

	public OrderProductVO(long orderId, int productId, String productName, int productCount) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.productCount = productCount;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	@Override
	public String toString() {
		return "OrderProductVO [orderId=" + orderId + ", productId=" + productId + ", productName=" + productName + ", productCount=" + productCount + "]";
	}

}
