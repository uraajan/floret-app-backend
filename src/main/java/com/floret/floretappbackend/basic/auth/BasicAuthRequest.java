package com.floret.floretappbackend.basic.auth;

import java.io.Serializable;

public class BasicAuthRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 892863878940252744L;

	private String username;

	private String password;

	private String firstName;

	private String lastName;

	private String address;

	private String mobileNo;

	public BasicAuthRequest() {
		super();
	}

	public BasicAuthRequest(String username, String password, String firstName, String lastName, String address, String mobileNo) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.mobileNo = mobileNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Override
	public String toString() {
		return "BasicAuthRequest [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", mobileNo=" + mobileNo + "]";
	}

}
