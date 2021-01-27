package com.floret.floretappbackend.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class OrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6728367386858160999L;

	private long id;

	private String username;

	private List<OrderProductVO> orderProductVOs;

	private boolean orderProcessed;

	private boolean orderDelivered;

	private boolean orderCancelled;

	private int itemCount;

	private int orderAmount;

	public OrderVO() {
		super();
	}

	public OrderVO(long id, String username, List<OrderProductVO> orderProductVOs, boolean orderProcessed, boolean orderDelivered, boolean orderCancelled, int itemCount, int orderAmount) {
		super();
		this.id = id;
		this.username = username;
		this.orderProductVOs = orderProductVOs;
		this.orderProcessed = orderProcessed;
		this.orderDelivered = orderDelivered;
		this.orderCancelled = orderCancelled;
		this.itemCount = itemCount;
		this.orderAmount = orderAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<OrderProductVO> getOrderProductVOs() {
		return orderProductVOs;
	}

	public void setOrderProductVOs(List<OrderProductVO> orderProductVOs) {
		this.orderProductVOs = orderProductVOs;
	}

	public boolean isOrderProcessed() {
		return orderProcessed;
	}

	public void setOrderProcessed(boolean orderProcessed) {
		this.orderProcessed = orderProcessed;
	}

	public boolean isOrderDelivered() {
		return orderDelivered;
	}

	public void setOrderDelivered(boolean orderDelivered) {
		this.orderDelivered = orderDelivered;
	}

	public boolean isOrderCancelled() {
		return orderCancelled;
	}

	public void setOrderCancelled(boolean orderCancelled) {
		this.orderCancelled = orderCancelled;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public int getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(int orderAmount) {
		this.orderAmount = orderAmount;
	}

	@Override
	public String toString() {
		return "OrderVO [id=" + id + ", username=" + username + ", orderProductVOs=" + orderProductVOs + ", orderProcessed=" + orderProcessed + ", orderDelivered=" + orderDelivered
				+ ", orderCancelled=" + orderCancelled + ", itemCount=" + itemCount + ", orderAmount=" + orderAmount + "]";
	}

}
