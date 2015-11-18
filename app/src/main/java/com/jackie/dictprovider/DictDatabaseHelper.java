package com.jackie.dictprovider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Law on 2015/11/18.
 */
public class DictDatabaseHelper extends SQLiteOpenHelper {
    public final static String CREATE_DICT_SQL = "create table word(" +
            "_id integer primary key autoincrement," +
            "word," +
            "detail)";

    public DictDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DictDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次使用数据库时自动建表
        db.execSQL(CREATE_DICT_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
