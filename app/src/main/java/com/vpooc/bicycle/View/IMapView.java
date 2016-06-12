package com.vpooc.bicycle.View;

import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.vpooc.bicycle.entity.BicycleInfomation;

import android.graphics.Bitmap;

import pl.droidsonroids.gif.GifImageView;

public interface IMapView {

	/**
	 * 开启交通图 全国范围内已支持多个城市实时路况查询
	 *
	 * @param b
	 */
	void setTrafficEnabled(boolean b);

	/**
	 * 在地图上开启百度城市热力图
	 *
	 * @param b
	 */
	void setBaiduHeatMapEnabled(boolean b);

	/**
	 * 标注所有的Marker
	 */
	void buildMaker(List<BicycleInfomation> markers,
					RequestStateDetailedData requestStateDetailedData);

	/**
	 * 定位我的地址，并回调传回BDLocation
	 */
	void setMyLocation(OnLocationCallback onLocationCallback);

	/**
	 * 定位完成后的回调接口
	 */
	interface OnLocationCallback{
		void LocationCallback(BDLocation location);
	}
	/**
	 * 暂停定位 暂停后需要关闭定位图层
	 */
	void locationStop();

	/**
	 * 关闭定位图层
	 */
	void setMyLocationEnabled();

	/**
	 * Marker点的具体详情Callback 可借车，可停车位数量的具体数据
	 */

	interface RequestStateDetailedData {
		void requestStateDetailedData(BicycleInfomation bicycleInfomation,
									  GifImageView ivGet, GifImageView ivStop);

	}


	/**
	 * 清除Marker
	 */
	void clearMarke();
}
