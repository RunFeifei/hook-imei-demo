package com.run.binder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 蔡小木 on 2016/5/4 0004.
 */
public class DbHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "binder.db";
    public final static String APP_TABLE_NAME = "bind";
    private final static int DB_VERSION = 2;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APP_TABLE = "CREATE TABLE IF NOT EXISTS " + APP_TABLE_NAME + "(package_name TEXT PRIMARY KEY," + "uuid text,manufacturer text,model text,oSVersion text,serialNumber text)";
        Log.e("TAG-->SQL",CREATE_APP_TABLE);
        db.execSQL(CREATE_APP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
