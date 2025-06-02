package me.zogodo.zbox;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static MainActivity me;
    public static String indexUrl = "file:///android_asset/web/index.html";
    public static WebView webView = null;
    long exitTime = 0;
    public static SqliteHelper dbHelper;
    public static boolean isOwner;

    static DevicePolicyManager dpm;
    static ComponentName admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.me = this;
        dbHelper = new SqliteHelper(this);

        Intent intent = getIntent();
        String dataString = intent.getDataString();
        if (dataString != null) {
            dataString = dataString.replaceAll("://z/", "://");
            indexUrl = dataString;
        }
        Log.e("zurl", indexUrl, null);
        //Log.e("zzzc", stringFromJNI(), null);

        dpm = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        admin = new ComponentName(this, DeviceAdminReceiver.class);

        isOwner = dpm.isDeviceOwnerApp(this.getPackageName());
        Log.e("DeviceOwner", "Is device owner? " + isOwner);

        webView = new MyWebView(this);
        webView.loadUrl(indexUrl);
        this.setContentView(webView);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            // 判断是否可后退，是则后退，否则退出程序
            if (((System.currentTimeMillis() - exitTime) > 3000)) {
                Toast.makeText(getApplicationContext(), "再按一次返回 退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
        }
    }

    public void OpenApp(String pkg_name) {
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pkg_name);
        if (launchIntent != null) {
            startActivity(launchIntent); // 启动目标应用
        } else {
            Toast.makeText(this, "请先解冻此App", Toast.LENGTH_SHORT).show();
        }
    }
}
