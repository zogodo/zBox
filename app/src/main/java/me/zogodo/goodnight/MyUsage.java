package me.zogodo.goodnight;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class MyUsage
{
    public static void TestEvent(Context context)
    {
        long beginTime = 0; //最近一天
        long endTime = System.currentTimeMillis();
        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = manager.queryEvents(beginTime, endTime);

        SQLiteDatabase db = MainActivity.dbHelper.getWritableDatabase();

        UsageEvents.Event eventOut;
        Log.e("zzze0", "a", null);
        while (usageEvents.hasNextEvent()) {
            eventOut = new UsageEvents.Event();
            usageEvents.getNextEvent(eventOut);
            Timestamp ts = new Timestamp(eventOut.getTimeStamp());
            int t = eventOut.getEventType();
            //t == UsageEvents.Event.SCREEN_INTERACTIVE           //亮屏15
            //t == UsageEvents.Event.SCREEN_NON_INTERACTIVE       //灭屏16
            if (t == UsageEvents.Event.KEYGUARD_SHOWN             //锁屏17
                || t == UsageEvents.Event.KEYGUARD_HIDDEN)        //解锁18
            {
                //Log.e("zzze1", ts.toString() + " t = " + eventOut.getEventType(), null);
                String sql = "select * from `event` where `time`=?";
                String[] pras = { Long.valueOf(eventOut.getTimeStamp()).toString() };
                if (!SqliteHelper.Exist(db, sql, pras)) {
                    sql = "insert into event(`time`, `type`) values(?, ?)";
                    Object[] pras2 = new Object[]{eventOut.getTimeStamp(), t};
                    db.execSQL(sql, pras2);
                }
            }
        }
        Log.e("zzze0", "z", null);
    }

    @JavascriptInterface
    public static String GetAllEvent() {
        SQLiteDatabase db = MainActivity.dbHelper.getWritableDatabase();
        String sql = "select * from event where `time`>=?";
        long now = System.currentTimeMillis();
        long day_ms = 24*60*60*1000;
        long day_ms_21day = (now / day_ms - 21) * day_ms;
        String[] pras = { Long.valueOf(day_ms_21day).toString() };
        Cursor cursor = db.rawQuery(sql, pras);

        Map<String, Integer> events = new HashMap<>();
        try {
            while (cursor.moveToNext()) {
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                int type = cursor.getInt(cursor.getColumnIndexOrThrow("type"));
                events.put(time, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();

        JSONObject json = new JSONObject(events);
        // Log.e("xx ", "zzzj" + json.toString(), null);
        return json.toString();
    }

}
