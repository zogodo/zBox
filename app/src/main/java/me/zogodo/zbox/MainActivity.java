package me.zogodo.zbox;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
    static {
        //System.loadLibrary("native-lib");
    }

    public static MainActivity me;
    public static String indexUrl = "file:///android_asset/web/index.html";
    public static WebView webView = null;
    long exitTime = 0;
    public static SqliteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        MainActivity.me = this;
        dbHelper = new SqliteHelper(this);

        Intent intent = getIntent();
        String dataString = intent.getDataString();
        if (dataString != null)
        {
            dataString = dataString.replaceAll("://z/", "://");
            indexUrl = dataString;
        }
        Log.e("zurl", indexUrl, null);
        //Log.e("zzzc", stringFromJNI(), null);

        DevicePolicyManager dpm = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(this, DeviceAdminReceiverImpl.class);
        dpm.setApplicationHidden(admin, "tv.danmaku.bili", true); // 隐藏/禁用 App
        //dpm.setApplicationHidden(admin, "tv.danmaku.bili", false);

        /*
        try {
            Process process = Runtime.getRuntime().exec("pm disable-user --user 0 tv.danmaku.bili");
            Log.e("zzz", "disable ok? ", null);

            // 读取标准输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("DisableApp", "OUT: " + line);
            }

            // 读取错误输出
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = error.readLine()) != null) {
                Log.e("DisableApp", "ERR: " + line);
            }

            int resultCode = process.waitFor();
            Log.e("DisableApp", "Process finished with code " + resultCode);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("zzz", "printStackTrace " + e.toString(), null);
        }
        */

        //String allEvents = MyUsage.GetAllEvent();
        //Log.e("zzz", "allEvents = " + allEvents, null);

        webView = new MyWebView(this);
        webView.loadUrl(indexUrl);
        this.setContentView(webView);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isGrantedUsagePermission(@NonNull Context context) {
        boolean granted;
        AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        return granted;
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        if (webView != null && webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            // 判断是否可后退，是则后退，否则退出程序
            if (((System.currentTimeMillis() - exitTime) > 3000))
            {
                Toast.makeText(getApplicationContext(), "再按一次返回 退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else
            {
                finish();
                System.exit(0);
            }
        }
    }

    //public native String stringFromJNI();
}
