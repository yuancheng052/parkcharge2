package com.yc.parkcharge2.entity;

/**
 * @author CHW  AUTO GENERAT
 * @version 1.0
 * @since 1.0
 */


import java.lang.System;


/**
 * SysUser对象信息
 *
 * @author chw
 *
 */

public class SysUser {

	private static final long serialVersionUID = 3148176768559230877L;


	private String id;
	private String phone;
	private String name;
	private String pwd;
	private String role;
	private String parkId;
	private String parkName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
}

