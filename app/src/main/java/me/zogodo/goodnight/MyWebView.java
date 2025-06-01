package me.zogodo.goodnight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

/**
 * Created by zogod on 17/2/19.
 */
public class MyWebView extends WebView
{
    //region 共有变量
    public static String myJs = null;
    public static String myCss = "";
    //endregion

    //region 构造器
    public MyWebView(final Context context)
    {
        super(context);
        this.WebViewInit();
    }
    public MyWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    //endregion

    @Override
    public void loadUrl(String url)
    {
        super.loadUrl(url);
    }

    //https://stackoverflow.com/questions/52028940
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility != View.GONE) super.onWindowVisibilityChanged(View.VISIBLE);
    }

    public void injectCSS()
    {
        this.loadUrl("javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                "style.innerHTML = '" + MyWebView.myCss + "';" +
                "parent.appendChild(style)" +
                "})()");
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void WebViewInit()
    {
        this.getSettings().setJavaScriptEnabled(true);
        this.addJavascriptInterface(new MyUsage(), "MyUsage");
        this.getSettings().setSupportMultipleWindows(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setAppCacheEnabled(true);
        this.setWebViewClient(new MyWebViewClient(this));
        //这两个是要在 Chrome inspect 调试时用的
        this.setWebChromeClient(new MyWebChromeClient());
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            this.setWebContentsDebuggingEnabled(true);
        }
    }
}


