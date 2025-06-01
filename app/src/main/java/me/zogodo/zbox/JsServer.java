package me.zogodo.zbox;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsServer
{
    @JavascriptInterface
    public static String Freeze(String pkg_name) {
        Log.e("xx ", "Freeze zzz", null);
        MainActivity.dpm.setApplicationHidden(MainActivity.admin, pkg_name, true); // 隐藏/禁用 App
        return "{}";
    }

    @JavascriptInterface
    public static String Unfreeze(String pkg_name) {
        Log.e("xx ", "Unfreeze zzz", null);
        MainActivity.dpm.setApplicationHidden(MainActivity.admin, pkg_name, false);
        return "{}";
    }

    @JavascriptInterface
    public static String GetAppList() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        PackageManager pm = MainActivity.me.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo pinfo : packages) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pkg_name", pinfo.packageName);
            char[] cs = pinfo.applicationInfo.loadLabel(pm).toString().toCharArray();
            if (cs[0] >= 'a' && cs[0] <= 'z') cs[0] -= 32; //首字母大写
            jsonObject.put("app_name", String.valueOf(cs));
            jsonObject.put("is_sys", pinfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM);
            Log.e("zzz", "getAppList1 [" + pinfo.packageName + "] [" + String.valueOf(cs) + "]");
            jsonArray.put(jsonObject);
        }
         //Log.e("xxx json ", "zzzj" + jsonArray.toString(), null);
        return jsonArray.toString();
    }

}
