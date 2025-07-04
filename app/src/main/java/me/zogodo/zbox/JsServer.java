package me.zogodo.zbox;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsServer {

    @JavascriptInterface
    public static boolean Disable(String pkg_name, boolean disable) {
        if (!MainActivity.isOwner) {
            Toast.makeText(MainActivity.me, "操作失败, 请检查zBox是否设备管理员", Toast.LENGTH_SHORT).show();
            return false;
        }
        Log.e("xx ", "Disable zzz", null);
        MainActivity.dpm.setPackagesSuspended(MainActivity.admin, new String[]{pkg_name}, disable); //停用
        MainActivity.dpm.setApplicationHidden(MainActivity.admin, pkg_name, disable); // 隐藏
        return true;
    }

    @JavascriptInterface
    public static boolean OpenApp(String pkg_name) {
        Log.e("xx ", "OpenApp zzz", null);
        MainActivity.me.OpenApp(pkg_name);
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

            boolean disabled = false;
            if (MainActivity.isOwner) {
                disabled = MainActivity.dpm.isApplicationHidden(MainActivity.admin, app.packageName);
            } else {
                disabled = MainActivity.me.getPackageManager().getLaunchIntentForPackage(app.packageName) == null;
            }
            if (disabled) {
                Log.e("HiddenApp", app.packageName + " is hidden");
            }

            JSONObject jsonObject = new JSONObject();
            char[] cs = app.applicationInfo.loadLabel(pm).toString().toCharArray();
            if (cs[0] >= 'a' && cs[0] <= 'z') cs[0] -= 32; //首字母大写
            jsonObject.put("pkg_name", app.packageName);
            jsonObject.put("app_name", String.valueOf(cs));
            jsonObject.put("disabled", disabled);
            jsonArray.put(jsonObject);
            //Log.e("zzz", "getAppList1 [" + app.packageName + "] [" + String.valueOf(cs) + "]");
        }
        //Log.e("xxx json ", "zzzj" + jsonArray.toString(), null);
        return jsonArray.toString();
    }

}
