package me.zogodo.zbox;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsServer {

    @JavascriptInterface
    public static boolean Freeze(String pkg_name, boolean hidden) {
        if (!MainActivity.isOwner) return false;
        Log.e("xx ", "Freeze zzz", null);
        MainActivity.dpm.setApplicationHidden(MainActivity.admin, pkg_name, hidden); // 隐藏/禁用 App
        return true;
    }

    @JavascriptInterface
    public static String GetAppList() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        PackageManager pm = MainActivity.me.getPackageManager();
        List<PackageInfo> allApps = pm.getInstalledPackages(PackageManager.MATCH_DISABLED_COMPONENTS | PackageManager.MATCH_UNINSTALLED_PACKAGES);
        for (PackageInfo app : allApps) {
            if (MainActivity.me.getPackageName().equals(app.packageName)) continue; //跳过自己
            if ((app.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) continue; //跳过系统应用

            boolean hidden = false;
            if (MainActivity.isOwner) {
                hidden = MainActivity.dpm.isApplicationHidden(MainActivity.admin, app.packageName);
            }
            if (hidden) {
                Log.e("HiddenApp", app.packageName + " is hidden");
            }

            JSONObject jsonObject = new JSONObject();
            char[] cs = app.applicationInfo.loadLabel(pm).toString().toCharArray();
            if (cs[0] >= 'a' && cs[0] <= 'z') cs[0] -= 32; //首字母大写
            jsonObject.put("pkg_name", app.packageName);
            jsonObject.put("app_name", String.valueOf(cs));
            jsonObject.put("is_hidden", hidden);
            jsonArray.put(jsonObject);
            //Log.e("zzz", "getAppList1 [" + app.packageName + "] [" + String.valueOf(cs) + "]");
        }
        //Log.e("xxx json ", "zzzj" + jsonArray.toString(), null);
        return jsonArray.toString();
    }

}
