package com.floret.floretappbackend.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class CartVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8235261311770425181L;

	private String username;

	private int productId;

	private String productName;

	private int productCount;

	private int productPrice;

	/**
	 * Mandatory constructor <br/>
	 * Needed to avoid "Parameter 0 of constructor required a bean of type 'java.lang.String' that could not be found" exception at start up
	 */
	public CartVO() {
		super();
	}

	public CartVO(String username, int productId, String productName, int productCount, int productPrice) {
		super();
		this.username = username;
		this.productId = productId;
		this.productName = productName;
		this.productCount = productCount;
		this.productPrice = productPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CartVO [username=" + username + ", productId=" + productId + ", productName=" + productName + ", productCount=" + productCount + ", productPrice=" + productPrice + "]";
	}

}
