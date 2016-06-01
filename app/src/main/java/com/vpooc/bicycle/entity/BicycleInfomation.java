package com.vpooc.bicycle.entity;

import java.io.Serializable;

public class BicycleInfomation implements Serializable {
	/**
	 * 站台名称
	 */
	private String name;
	/**
	 * 可借车数
	 */
	private String get;
	/**
	 * 可停车数
	 */
	private String stop;
	/**
	 * 站台地址
	 */
	private String address;
	/**
	 * 站台纬度
	 */

	private String lat;
	/**
	 * 站台经度
	 */
	private String lng;

	public BicycleInfomation(String name, String get, String stop,
							 String address, String lat, String lng) {
		super();
		this.name = name;
		this.get = get;
		this.stop = stop;
		this.address = address;
		this.lat = lat;
		this.lng = lng;
	}

	/**
	 * 获取站台名称
	 *
	 * @return
	 */
	public String getState() {
		return name;
	}

	/**
	 * 设置站台名称
	 *
	 * @param state
	 */
	public void setState(String state) {
		this.name = state;
	}

	/**
	 * 获取站台可用车数量
	 *
	 * @return
	 */
	public String getGet() {
		return get;
	}

	/**
	 * 设置站台可用车数量
	 *
	 * @param get
	 */
	public void setGet(String get) {
		this.get = get;
	}

	/**
	 * 获取站台可停车位数量
	 *
	 * @return
	 */
	public String getStop() {
		return stop;
	}

	/**
	 * 设置站台可停车位数量
	 *
	 * @param stop
	 */
	public void setStop(String stop) {
		this.stop = stop;
	}

	/**
	 * 获取站台地址
	 *
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置站台地址
	 *
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取站台纬度
	 *
	 * @return
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * 设置站台纬度
	 *
	 * @param lat
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * 获取站台经度
	 *
	 * @return
	 */
	public String getLng() {
		return lng;
	}

	/**
	 * 设置站台经度
	 *
	 * @param lng
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "BicycleInfomation [name=" + name + ", get=" + get + ", stop="
				+ stop + ", address=" + address + ", lat=" + lat + ", lng="
				+ lng + "]";
	}

}