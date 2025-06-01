package me.zogodo.zbox;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.*;

/**
 * Created by zogod on 17/2/19.
 */
public class MyWebViewClient extends WebViewClient
{
    MyWebView mywebview;

    public MyWebViewClient(MyWebView mywebview)
    {
        this.mywebview = mywebview;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.N)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req)
    {
        Log.e("zzz " + mywebview.hashCode(), "shouldOverrideUrlLoading " + req.getUrl().toString());
        return false;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onPageFinished(WebView view, String url)
    {
        Log.e("zzz " + mywebview.hashCode(), "onPageFinished " + url);
        this.mywebview.loadUrl("javascript:" + MyWebView.myJs);
        this.mywebview.injectCSS();
        super.onPageFinished(view, url);
        mywebview.requestFocus(View.FOCUS_DOWN);

        mywebview.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager)MainActivity.me.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mywebview, InputMethodManager.SHOW_IMPLICIT);
        }, 1); //不知道为什么直接调用不行,必须加一点点延时

    }

}
