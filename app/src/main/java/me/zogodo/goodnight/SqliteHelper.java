package me.zogodo.goodnight;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "screen_usage.db";

    private static final String SQL_CREATE_EVENT =
            "CREATE TABLE event(`time` datetime PRIMARY KEY, `type` INTEGER);";

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static boolean Exist(SQLiteDatabase db, String sql, String[] pras) {
        Cursor cursor = db.rawQuery(sql, pras);
        boolean has = false;
        try {
            has = cursor.moveToNext();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return has;
    }

    public void onCreate(SQLiteDatabase db) {
        Log.e("zzz", "db onCreate()");
        db.execSQL(SQL_CREATE_EVENT);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("zzz", "db onUpgrade()");
        //升级时改动数据库
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("zzz", "db onDowngrade()");
        this.onUpgrade(db, oldVersion, newVersion);
    }

}
