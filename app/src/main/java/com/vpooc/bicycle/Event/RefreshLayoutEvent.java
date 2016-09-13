package com.vpooc.bicycle.Event;

import java.util.ArrayList;

import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by ChenYao on 2016/8/16.
 */
public class RefreshLayoutEvent {
    private ArrayList<LCChatKitUser> friends;

    public RefreshLayoutEvent(ArrayList<LCChatKitUser> friends){
        this.friends=friends;
    }

    public ArrayList<LCChatKitUser> getFriends(){return friends;}


}
