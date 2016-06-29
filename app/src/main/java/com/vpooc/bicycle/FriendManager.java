package com.vpooc.bicycle;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by Administrator on 2016/6/18.
 */
public class FriendManager {
    private static String clientID;
    private static FriendManager friendManager;
    private ArrayList<LCChatKitUser> users;


    private FriendManager() {
        clientID = LCChatKit.getInstance().getCurrentUserId();
        Log.d("fManager_clientID", clientID);
    }

    public static synchronized FriendManager newInstance() {
        if (friendManager == null) {
            friendManager = new FriendManager();
        }
        return friendManager;
    }

    /**
     * 添加好友到Friend表中
     *
     * @param friend 好友实例
     */
    public void addFriendForUser(LCChatKitUser friend) {
        int i = 0;
        for (LCChatKitUser user : users) {
            if (friend.getUserId().equals(user.getUserId())) {
                i++;
            }
        }
        if (i > 0) return;

        final AVObject O = new AVObject("Friend");// 深圳
        O.put("userID", clientID);
        O.put("FriendProfile", friend);
        //忽略保存不成功
        O.saveInBackground();
    }

    public void addFriendForUser(String friendName) {
        addFriendForUser(new LCChatKitUser(null, friendName, null));
    }

    public void getAllFriendList(final OngetAllFriendList ongetAllFriendList) {

        AVQuery<AVObject> query = new AVQuery<>("Friend");
        //1111处换为clientID
        query.whereStartsWith("userID", clientID);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {

                if (list != null) {
                    users = new ArrayList<LCChatKitUser>();
//                    Log.d("查询结果1", list.get(0).toString());
                    for (int i = 0; i < list.size(); i++) {
                        Gson gson = new Gson();
                        LCChatKitUser O = gson.fromJson(list.get(i).get("FriendProfile").toString(), LCChatKitUser.class);

                        Log.d("FriendProfile", O.toString());
                        users.add(O);
                    }
                    ongetAllFriendList.OnAllFriendList(users);

                } else {
                    Log.d("查询结果为null",e.toString());
                }
            }
        });


    }


    interface OngetAllFriendList {
        void OnAllFriendList(List<LCChatKitUser> list);
    }
}
