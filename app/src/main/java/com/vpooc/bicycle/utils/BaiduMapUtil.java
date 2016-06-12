package com.vpooc.bicycle.utils;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.vpooc.bicycle.app.Application;
import com.vpooc.bicycle.entity.BicycleInfomation;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/9.
 */
public class BaiduMapUtil {


    /**
     *显示单元Marker
     * @param bundle 需要增加的标注信息
     * @param latLng Marker的坐标
     * @param icon 地图上显示的图标的资源ID,
     * 创建单个标注Marker 开发者可根据自己实际的业务需求，利用标注覆盖物，在地图指定的位置上添加标注信息
     */
    public static void buildSingleMaker( Bundle bundle, LatLng latLng, int icon) {

        BaiduMap map = Application.getInstanceMap();
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(icon);
        // 构建MarkerOption，用于在地图上添加Marker

        MarkerOptions option = new MarkerOptions().position(latLng);
        option.extraInfo(bundle);
        option.icon(bitmap);

        // 在地图上添加Marker，并显示
        map.addOverlay(option);
    }
}
