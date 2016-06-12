package com.vpooc.bicycle.presenter;

import java.util.ArrayList;
import java.util.List;


import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.vpooc.bicycle.Modle.MapModel;
import com.vpooc.bicycle.Modle.IMapModel;
import com.vpooc.bicycle.Modle.bicycleModle.IBicycleModle;
import com.vpooc.bicycle.Modle.bicycleModle.Impl.BicycleModle;
import com.vpooc.bicycle.View.IMapView;
import com.vpooc.bicycle.View.MapView;
import com.vpooc.bicycle.app.Application;
import com.vpooc.bicycle.entity.BicycleInfomation;
import com.vpooc.bicycle.utils.L;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import pl.droidsonroids.gif.GifImageView;
//东方希望：纬度:30.628364,经度:104.048771
public class Presenter {

	private Context context;
	private IMapView view;
	private Application app;
	private Handler handler = new Handler();
	private IBicycleModle bicycleModle;
	private MapModel mapModel;
	private String userID="Tom";

	public Presenter(Context context, IMapView view) {
		super();
		this.view = view;
		this.context = context;
		app = (Application) context.getApplicationContext();
		initModle();
		initView();


		// getStateList("sz", "广场");

	}

	private void initModle() {

		bicycleModle = new BicycleModle(context, handler,
				app.getRrequestQueue());
		mapModel = new MapModel(context, userID);
	}
int i=0;
	private void initView() {
		// TODO Auto-generated method stub

		System.out.println("开始定位");
		//定位并传回定位信息
		view = new MapView(context, Application.getInstanceMap());
		view.setMyLocation(new IMapView.OnLocationCallback() {
			@Override
			public void LocationCallback(final BDLocation location) {
				//获取附近好友
			Log.d("开始雷达","回调");
				if(i%10==0) {
					mapModel.uploadInfo(new LatLng(location.getLatitude(), location.getLongitude()), "第一次试验");
				}
				i++;
			}
		});
	}

	public void buildMaker(List<BicycleInfomation> markers) {
		view.buildMaker(markers, new IMapView.RequestStateDetailedData() {

			@Override
			public void requestStateDetailedData(
					BicycleInfomation bicycleInfomation, GifImageView ivGet,
					GifImageView ivStop) {
				// TODO Auto-generated method stub
				getStateDetailFromBicycleModle(bicycleInfomation, ivGet, ivStop);
			}
		});

	}

	/**
	 * 获取搜索自行车站台的列表
	 *
	 * @param district
	 * @param state
	 */
	public void getStateList(String district, String state) {
		//清除Marker
		clearMarker();

		bicycleModle.getStateList(district, state, new IBicycleModle.StateListCallback() {
			// 返回信息
			@Override
			public void OnGetStateList(List<BicycleInfomation> state) {
				// 获取经纬度列表
				List<LatLng> markers = new ArrayList<LatLng>();
				for (BicycleInfomation info : state) {
					LatLng ll = new LatLng(Double.valueOf(info.getLat()),
							Double.valueOf(info.getLng()));
					markers.add(ll);
				}


				// 标记Maker
				buildMaker(state);
			}
		});
	}

	//清除已有的Marker
	protected void clearMarker() {
		// TODO Auto-generated method stub
		view.clearMarke();
	}

	/**
	 * 获取到站点详细信息并显示出来
	 *
	 * @param bicycleInfomation
	 */
	private void getStateDetailFromBicycleModle(
			BicycleInfomation bicycleInfomation, GifImageView ivGet,
			GifImageView ivStop) {

		bicycleModle.getStateDetail(bicycleInfomation, ivGet, ivStop);

	}
}