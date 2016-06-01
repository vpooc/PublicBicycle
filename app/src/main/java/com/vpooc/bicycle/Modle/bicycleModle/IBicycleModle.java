package com.vpooc.bicycle.Modle.bicycleModle;

import com.vpooc.bicycle.entity.BicycleInfomation;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/5/19.
 */

public interface IBicycleModle {
    /**
     * 请求自行车查询
     */
    void getStateList(String district, String state,
                      StateListCallback stateListCallback);

    interface StateListCallback {
        void OnGetStateList(List<BicycleInfomation> state);
    }

    void getStateDetail(BicycleInfomation state, GifImageView ivGet,
                        GifImageView ivStop);

}