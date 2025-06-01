package me.zogodo.zbox;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class MyUsage
{
    @JavascriptInterface
    public static String GetAllEvent() {
        // Log.e("xx ", "zzzj" + json.toString(), null);
        return "{}";
    }

    @JavascriptInterface
    public static String Freeze() {
        Log.e("xx ", "Freeze zzz", null);
        //tv.danmaku.bili
        MainActivity.dpm.setApplicationHidden(MainActivity.admin, "tv.danmaku.bili", true); // 隐藏/禁用 App
        return "{}";
    }

    @JavascriptInterface
    public static String Unfreeze() {
        Log.e("xx ", "Unfreeze zzz", null);
        MainActivity.dpm.setApplicationHidden(MainActivity.admin, "tv.danmaku.bili", false);
        return "{}";
    }

}
