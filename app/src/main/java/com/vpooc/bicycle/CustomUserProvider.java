package com.vpooc.bicycle;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.vpooc.bicycle.Dao.FriendDao;
import com.vpooc.bicycle.app.Application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by wli on 15/12/4.
 * 实现自定义用户体系
 */
public class CustomUserProvider implements LCChatProfileProvider {

    private static CustomUserProvider customUserProvider;

    public synchronized static CustomUserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new CustomUserProvider();
        }
        return customUserProvider;
    }

    private CustomUserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    // 此数据均为 fake，仅供参考
    static {
//        partUsers.add(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
//        partUsers.add(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
//        partUsers.add(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
//        partUsers.add(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
//        partUsers.add(new LCChatKitUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));

        partUsers.add(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
        partUsers.add(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
        partUsers.add(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
        partUsers.add(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
        partUsers.add(new LCChatKitUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));

        new Thread( ){
            @Override
            public void run() {
                super.run();
                Log.d("", "添加好友");
                FriendDao.getInstance(Application.getContext()).addFriend(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
                FriendDao.getInstance(Application.getContext()).addFriend(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));

                ArrayList<LCChatKitUser> s = FriendDao.getInstance(Application.getContext()).getFriends();
                Log.d("", "添加好友完成"+s.toString());
            }
        }.start();

//        FriendManager.newInstance().getAllFriendList(new FriendManager.OngetAllFriendList() {
//            @Override
//            public void OnAllFriendList(List<LCChatKitUser> list) {
//                partUsers=list;
//            }
//        });



//        FriendManager.newInstance().addFriendForUser(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"), new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//
//            }
//        });
//        FriendManager.newInstance().addFriendForUser(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"), new SaveCallback() {
//                    @Override
//                    public void done(AVException e) {
//
//                    }
//                }
//        );
//        FriendManager.newInstance().addFriendForUser(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"), new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//
//            }
//        });
//        FriendManager.newInstance().addFriendForUser(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"), new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//
//            }
//        });
//        FriendManager.newInstance().addFriendForUser(new LCChatKitUser("keke", "keke", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"), new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//
//                FriendManager.newInstance().getAllFriendList(new FriendManager.OngetAllFriendList() {
//                    @Override
//                    public void OnAllFriendList(List<LCChatKitUser> users) {
//                        partUsers = users;
//
//                    }
//                });
//
//
//            }
//        });



    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        callBack.done(userList, null);
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}
