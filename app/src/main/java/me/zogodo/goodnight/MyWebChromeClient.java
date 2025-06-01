package me.zogodo.goodnight;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

public class MyWebChromeClient extends WebChromeClient
{
    @Override
    public boolean onConsoleMessage(ConsoleMessage cm)
    {
        Log.d("MyApplication", cm.message() + " -- From line "
                + cm.lineNumber() + " of "
                + cm.sourceId());
        return true;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        MainActivity.me.setContentView(view);
        MainActivity.me.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Window win = MainActivity.me.getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        win.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        super.onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        MainActivity.me.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        Window win = MainActivity.me.getWindow();
        win.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        win.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        super.onHideCustomView();
    }
}
