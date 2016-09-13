package com.vpooc.bicycle.DB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vpooc.bicycle.utils.Const;

/**
 * 数据库访问助手
 * Created by ChenYao on 2016/8/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE "+Const.TABLE_NAME+
                " ("
                + Const.TABLE_FRIEND_ID + " varchar(20),"
                + Const.TABLE_FRIEND_NAME + " varchar(20),"
                + Const.TABLE_FRIEND_PIC_URL + " varchar(100)"
                +")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
