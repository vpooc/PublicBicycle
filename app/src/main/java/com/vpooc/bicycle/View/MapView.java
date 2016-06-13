package com.vpooc.bicycle.View;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.LogUtil;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.vpooc.bicycle.R;
import com.vpooc.bicycle.activity.AVSingleChatActivity;
import com.vpooc.bicycle.entity.BicycleInfomation;
import com.vpooc.bicycle.utils.BaiduMapUtil;

import pl.droidsonroids.gif.GifImageView;

public class MapView implements IMapView {
    private BaiduMap mMap;

    private Context context;
    private LocationClient mLocClient;
    private boolean isFirstLoc = true;
    private RequestStateDetailedData requestStateDetailedData;
    public View detailedView;
    // 标注图标，当地图阶级小于14时，显示大图标
    private int resIcon = R.drawable.icon_openmap_mark;

    private List<BicycleInfomation> markers;
    private OnLocationCallback locationCallback;

    public MapView(Context context, final BaiduMap mMap) {

        super();
        this.mMap = mMap;
        this.context = context;
        this.mMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                // TODO Auto-generated method stub
                if (arg0.zoom < 13) {
                    if (resIcon == R.drawable.icon_openmap_mark && requestStateDetailedData != null) {
                        resIcon = R.drawable.icon_mark_pt;
                        mMap.clear();
                        buildMaker(markers, requestStateDetailedData);
                    }
                } else {
                    if (resIcon == R.drawable.icon_mark_pt && requestStateDetailedData != null) {
                        resIcon = R.drawable.icon_openmap_mark;

                        mMap.clear();
                        buildMaker(markers, requestStateDetailedData);
                    }

                }

            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });
        mMap.setOnMarkerClickListener(new MarkerOnClickListener());
    }

    @Override
    public void setTrafficEnabled(boolean b) {
        // 开启交通图
        mMap.setTrafficEnabled(b);
    }

    @Override
    public void setBaiduHeatMapEnabled(boolean b) {
        mMap.setBaiduHeatMapEnabled(b);
    }

    @Override
    public void setMyLocation(OnLocationCallback onLocationCallback) {
        locationCallback = onLocationCallback;
        // 高精度定位模式：这种定位模式下，会同时使用网络定位和GPS定位，优先返回最高精度的定位结果；
        // 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位（Wi-Fi和基站定位）；
        // 仅用设备定位模式：这种定位模式下，不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位。

        // mLocClient = new LocationClient(context); // 声明LocationClient类
        //
        // // 注册监听函数
        // mLocClient.registerLocationListener(new MyLocationListenner());
        //
        // // LocationClientOption类，该类用来设置定位SDK的定位方式
        // LocationClientOption option = mLocClient.getLocOption();
        // // 可选，默认gcj02，设置返回的定位结果坐标系
        // option.setCoorType("bd09ll");
        // // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        // option.setLocationMode(LocationMode.Hight_Accuracy);
        // // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        // int span = 1000;
        // option.setScanSpan(span);
        //
        // // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        // option.setLocationNotify(true);
        //
        // // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，
        // // 设置是否在stop的时候杀死这个进程，默认不杀死
        // option.setIgnoreKillProcess(false);
        //
        // // 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        // option.setEnableSimulateGps(false);
        // mLocClient.setLocOption(option);
        // 开启定位图层
        mMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(new MyLocationListenner());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(2000);
        mLocClient.setLocOption(option);
        mLocClient.start();


    }

    @Override
    public void locationStop() {

        // 退出时销毁定位
        mLocClient.stop();

    }

    @Override
    public void setMyLocationEnabled() {

        // 关闭定位图层
        mMap.setMyLocationEnabled(false);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMap == null) {
                return;
            }

            locationCallback.LocationCallback(location);
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    /**
     * Marker监听实现类
     *
     * @author Administrator
     */
    class MarkerOnClickListener implements OnMarkerClickListener {

        private GifImageView ivGet;
        private GifImageView ivStop;

        @Override
        public boolean onMarkerClick(final Marker marker) {
            // TODO Auto-generated method stub
            Bundle bundle = marker.getExtraInfo();
            if (bundle == null) {

                mMap.hideInfoWindow();

                LayoutInflater layoutInflater = LayoutInflater.from(context);

                detailedView = layoutInflater.inflate(R.layout.mark_info, null);
                BicycleInfomation stateInfo = (BicycleInfomation) marker.getExtraInfo().getSerializable("stateInfo");

                ivGet = (GifImageView) detailedView.findViewById(R.id.mark_state_info_gif_iv_get);
                ivStop = (GifImageView) detailedView.findViewById(R.id.mark_state_info_gif_iv_stop);
                ((TextView) detailedView.findViewById(R.id.mark_state_info_name)).append(stateInfo.getState());
                requestStateDetailedData.requestStateDetailedData(stateInfo, ivGet, ivStop);
                InfoWindow mInfoWindow = new InfoWindow(detailedView, marker.getPosition(), -70);

                mMap.showInfoWindow(mInfoWindow);
            } else {
//单击用户标识
                Intent intent = new Intent(context, AVSingleChatActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

            return true;
        }

    }

    @Override
    public void buildMaker(List<BicycleInfomation> markers, RequestStateDetailedData requestStateDetailedData) {

        this.markers = markers;
        this.requestStateDetailedData = requestStateDetailedData;
        for (BicycleInfomation marker : markers) {
            buildMaker(marker, R.drawable.icon_openmap_mark);
        }

    }

    @Override
    public void clearMarke() {
        // TODO Auto-generated method stub
        mMap.clear();

    }

    /**
     * 显示的图标
     */
    public void buildMaker(BicycleInfomation marker, int res) {
        // 定义Maker坐标点
        LatLng point = new LatLng(Double.valueOf(marker.getLat()), Double.valueOf(marker.getLng()));
        Bundle bundle = new Bundle();
        bundle.putSerializable("stateInfo", marker);

        // 在地图上添加Marker，并显示
        BaiduMapUtil.buildSingleMaker(bundle, point, res);
    }

}