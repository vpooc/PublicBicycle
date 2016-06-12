package com.vpooc.bicycle.Modle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.vpooc.bicycle.R;
import com.vpooc.bicycle.activity.AVSingleChatActivity;
import com.vpooc.bicycle.activity.MainActivity;
import com.vpooc.bicycle.utils.BaiduMapUtil;
import com.vpooc.bicycle.utils.L;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public class MapModel {
    private Context context;
private String userID;
    private int pageIndex;
    private int curPage;
    private int totalPage;
    private RadarNearbyResult listResult;
    private static InnerNearbyInfoListener innerNearbyInfoListener;
    private LatLng pt;



    public MapModel(Context context, String userID) {
        this.context=context;
       this.userID =userID;
       initRadar();
    }

//初始化雷达
    private void initRadar() {
        // 周边雷达设置监听
        innerNearbyInfoListener=new InnerNearbyInfoListener();
        RadarSearchManager.getInstance().addNearbyInfoListener(innerNearbyInfoListener);
// 周边雷达设置用户，id为空默认是设备标识
        RadarSearchManager.getInstance().setUserID(userID);

    }

    class InnerNearbyInfoListener implements RadarSearchListener {
        @Override
        public void onGetNearbyInfoList(RadarNearbyResult result, RadarSearchError error) {

            if (error == RadarSearchError.RADAR_NO_ERROR) {
                Log.d( "查询周边成功",result.infoList.toString());
                Toast.makeText(context, "查询周边成功", Toast.LENGTH_LONG).show();
                // 获取成功
                listResult = result;
                // 当前页码
                curPage = result.pageIndex;
                // 总页数
                totalPage = result.pageNum;
                // 结果总数
                // totalNum=result.totalNum;
                // 处理数据
                parseResultToMap(listResult);

            } else {
                // 获取失败
                Log.d( "获取失败","");
                curPage = 0;
                totalPage = 0;
                Toast.makeText(context, "查询周边失败", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onGetUploadState(RadarSearchError error) {

            if (error == RadarSearchError.RADAR_NO_ERROR) {
                // 上传成功
               Log.d( "单次上传位置成功","");
                    Toast.makeText(context, "单次上传位置成功", Toast.LENGTH_LONG)
                            .show();
searchNearby(pt);

            } else {
                // 上传失败
                Log.d( "单次上传位置失败","");
                    Toast.makeText(context, "单次上传位置失败", Toast.LENGTH_LONG)
                            .show();
            }
        }

        @Override
        public void onGetClearInfoState(RadarSearchError error) {
            if (error == RadarSearchError.RADAR_NO_ERROR) {
                // 清除成功
                L.d( "清除位置成功");
                Toast.makeText(context, "清除位置成功", Toast.LENGTH_LONG).show();
            } else {
                // 清除失败
                L.d( "清除位置失败");
                Toast.makeText(context, "清除位置失败", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * 上传一次位置
     * @param pt 地址坐标
     * @param userComment
     */

    int i=0;
    public void uploadInfo(LatLng pt,String userComment) {
        i++;
        if (pt == null) {
            Toast.makeText(context, "未获取到位置", Toast.LENGTH_LONG).show();
            return;
        }
        this.pt=pt;
        RadarUploadInfo info = new RadarUploadInfo();
        info.comments = userComment;
        info.pt = pt;

        RadarSearchManager.getInstance().setUserID(userID+i);


        RadarSearchManager.getInstance().uploadInfoRequest(info);
    }

    /**
     * 清除自己当前的信息
     */
    public void clearInfo() {
        RadarSearchManager.getInstance().clearUserInfo();
    }

    /**
     * 查找周边的人
     *
     * @param pt
     */
    public void searchNearby(LatLng pt) {
        pageIndex = 0;
        curPage = 0;
        totalPage = 0;

        RadarNearbySearchOption option = new RadarNearbySearchOption()
                .centerPt(pt).pageNum(pageIndex).radius(2000);
        RadarSearchManager.getInstance().nearbyInfoRequest(option);
    }

    /**
     * 释放周边雷达相关
     */
    public  static void onDestroyRadar() {
        // 释放周边雷达相关
        RadarSearchManager.getInstance().removeNearbyInfoListener(innerNearbyInfoListener);
        RadarSearchManager.getInstance().clearUserInfo();
        RadarSearchManager.getInstance().destroy();
    }


    /**
     * 更新结果地图
     * @param res
     */
    public void parseResultToMap(RadarNearbyResult res) {
        if (res != null && res.infoList != null && res.infoList.size() > 0) {
            for (RadarNearbyInfo info:res.infoList) {
//                MarkerOptions option = new MarkerOptions().icon(ff3).position(
//                        res.infoList.get(i).pt);
//                Bundle des = new Bundle();
//                if (res.infoList.get(i).comments == null
//                        || res.infoList.get(i).comments.equals("")) {
//                    des.putString("des", "没有备注");
//                } else {
//                    des.putString("des", res.infoList.get(i).comments);
//                }
//
//                option.extraInfo(des);
//                mBaiduMap.addOverlay(option);

                Bundle bundle = new Bundle();
                bundle.putString("userID",info.userID);
                BaiduMapUtil.buildSingleMaker(bundle,info.pt, R.drawable.ic_launcher);

            }
        }


    }
}
