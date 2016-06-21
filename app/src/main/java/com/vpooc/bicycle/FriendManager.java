package com.vpooc.bicycle;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by Administrator on 2016/6/18.
 */
public class FriendManager {
    private static String clientID;
    private static FriendManager friendManager;


    private FriendManager() {
//        clientID= LCChatKit.getInstance().getClient().getClientId();
        clientID="573d65a11532bc006579bbd1";
        Log.d("fManager_clientID",clientID);
    }

    public static synchronized FriendManager newInstance(){
        if(friendManager==null){
            friendManager=new FriendManager();
        }
        return friendManager;
    }

    /**
     * 添加好友到Friend表中
     * @param friend 好友实例
     */
    public void addFriendForUser(LCChatKitUser friend) {

        final AVObject O= new AVObject("Friend");// 深圳
        O.put("userID",clientID);
        O.put("FriendProfile",friend);
        //忽略保存不成功
        O.saveInBackground();
    }


    public void getAllFriendList(final OngetAllFriendList ongetAllFriendList){
        // 找出开头是「早餐」的 Todo
        AVQuery<AVObject> query = new AVQuery<>("Friend");
        //1111处换为clientID
        query.whereStartsWith("userID",clientID);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                Log.d("查询结果1",list.get(0).toString());
                ongetAllFriendList.OnAllFriendList(list);
            }
        });



    }


    interface OngetAllFriendList{
        void OnAllFriendList(List<AVObject> list);
    }
}
