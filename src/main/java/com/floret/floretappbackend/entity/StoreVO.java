package com.floret.floretappbackend.entity;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class StoreVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -924564055878754441L;

	private int id;

	private String name;

	private int price;

	private int discountPercentage;

	private int discountedPrice;

	private String category;

	private int availabilityCount;

	/**
	 * Mandatory to avoid "Parameter 0 of constructor required a bean of type 'java.lang.String' that could not be found" exception at start up
	 */
	public StoreVO() {
		super();
	}

	public StoreVO(int id, String name, int price, int discountPercentage, int discountedPrice, String category, int availabilityCount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.discountPercentage = discountPercentage;
		this.discountedPrice = discountedPrice;
		this.category = category;
		this.availabilityCount = availabilityCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public int getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAvailabilityCount() {
		return availabilityCount;
	}

	public void setAvailabilityCount(int availabilityCount) {
		this.availabilityCount = availabilityCount;
	}

	@Override
	public String toString() {
		return "StoreVO [id=" + id + ", name=" + name + ", price=" + price + ", discountPercentage=" + discountPercentage + ", discountedPrice=" + discountedPrice + ", category=" + category
				+ ", availabilityCount=" + availabilityCount + "]";
	}

}
