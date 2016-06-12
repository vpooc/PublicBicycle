package com.vpooc.bicycle.Modle;

import com.avos.avoscloud.AVObject;
import com.baidu.location.BDLocation;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */
public interface IMapModel {
    void getNearbyFriends(BDLocation location, NearbyFriedsCallback nearbyFriedsCallback);

    interface NearbyFriedsCallback {
        void nearbyFrieds(List<AVObject> AVObjects);
    }
}
