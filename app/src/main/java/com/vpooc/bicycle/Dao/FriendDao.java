package com.vpooc.bicycle.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.vpooc.bicycle.DB.DatabaseHelper;
import com.vpooc.bicycle.utils.Const;
import java.util.ArrayList;
import cn.leancloud.chatkit.LCChatKitUser;

/**
 * 访问好友信息表
 * Created by ChenYao on 2016/8/16.
 */
public class FriendDao {
    private static String Tag = "FriendDao";
    private static FriendDao friendDao;
    private static SQLiteDatabase DB;

    private FriendDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context, Const.DB_NAME, null, 1);
        DB = dbHelper.getWritableDatabase();

    }

    public static FriendDao getInstance(Context context) {
        if (friendDao == null) {
            return friendDao = new FriendDao(context);
        }
        return friendDao;
    }

    /**
     * 添加好友
     *
     * @param user
     */
    public static void addFriend(LCChatKitUser user) {
        try {
            String sql="INSERT INTO "+Const.TABLE_NAME+" ("
                    +Const.TABLE_FRIEND_ID+" CHAR(20),"
                    +Const.TABLE_FRIEND_NAME+" CHAR(20),"
                    +Const.TABLE_FRIEND_PIC_URL+" CHAR(100)"
                    +")"
                    +" VALUES ("
                    +user.getUserId()+","
                    +user.getUserName()+","
                    +user.getAvatarUrl()
                    +")";
            DB.execSQL(sql);
        } catch (Exception e) {
            Log.d(Tag, e.toString());
        } finally {

        }
    }

    /**
     * 删除friendID好友
     *
     * @param friendID
     */
    public static void deleteFriend(String friendID) {
        try {
            DB.delete(Const.TABLE_NAME, friendID, new String[]{Const.TABLE_FRIEND_ID});
            DB.close();
            DB.delete(Const.TABLE_NAME, friendID, new String[]{Const.TABLE_FRIEND_ID});
        } finally {

        }


    }

    /**
     * 获取本地好友列表
     * @return
     */
    public static ArrayList<LCChatKitUser> getFriends() {
        ArrayList<LCChatKitUser> friends = new ArrayList<>();
        try {
            Cursor c = DB.query(Const.TABLE_NAME, null, null, null, null, null, null);
            if (c.isBeforeFirst()) {
                while (c.moveToNext()) {
                    String id = c.getString(c.getColumnIndex(Const.TABLE_FRIEND_ID));
                    String name = c.getString(c.getColumnIndex(Const.TABLE_FRIEND_NAME));
                    String url = c.getString(c.getColumnIndex(Const.TABLE_FRIEND_PIC_URL));
                    friends.add(new LCChatKitUser(id, name, url));
                }
            }
            c.close();
            return friends;
        } finally {

        }
    }

}
