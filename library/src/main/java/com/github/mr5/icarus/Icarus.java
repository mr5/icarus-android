package com.github.mr5.icarus;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.mr5.icarus.entity.Options;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Icarus {
    protected HashMap<String, Callback> callbacks = new HashMap<>();
    protected Toolbar toolbar;
    protected WebView webView;
    protected Gson gson = new Gson();
    protected String content;
    protected boolean initialized = false;
    protected Options options;
    protected LinkedBlockingQueue<Runnable> onReadyCallbacks = new LinkedBlockingQueue<>();
    protected boolean editorReady = false;

    public Icarus(Toolbar toolbar, Options options, WebView webView) {
        this.toolbar = toolbar;
        this.webView = webView;
        this.options = options;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * Add native callback.
     *
     * @param callback Native callback
     * @return Callback name, use for method `callback`.
     */
    public String addCallback(Callback callback) {
        String callbackName = UUID.randomUUID().toString();
        callbacks.put(callbackName, callback);
        return callbackName;
    }

    /**
     * Remove native callback.
     *
     * @param callbackName Native callback.
     */
    public void removeCallback(String callbackName) {
        if (callbacks.containsKey(callbackName)) {
            callbacks.remove(callbackName);
        }
    }

    /**
     * Natively execute callback function that defined in javascript runtime.
     *
     * @param callbackName Callback name.
     * @param params       Params that passed to javascript runtime, will be converted to string by Gson.
     * @param typeOfParams The specific genericized type of `params` (typeOfSrc for gson).
     */
    public void jsCallback(String callbackName, Object params, Class typeOfParams) {
        Log.d("jsCallbackName", callbackName);

        String paramsString = "";
        if (typeOfParams != null) {
            paramsString = gson.toJson(params, typeOfParams);
        } else {
            paramsString = gson.toJson(params);
        }
        String jsCode = "editor.callback(" + gson.toJson(callbackName) + "," + paramsString + ");";
        jsExec(jsCode);
    }

    /**
     * Natively execute callback function that defined in javascript runtime.
     *
     * @param callbackName Callback name.
     * @param params       Params pass to javascript runtime, will be converted to string by Gson.
     */
    public void jsCallback(String callbackName, Object params) {
        jsCallback(callbackName, params, null);
    }

    /**
     * Natively remove callback function that defined in javascript runtime.
     *
     * @param callbackName Callback function name.
     */
    public void jsRemoveCallback(String callbackName) {
        jsExec("editor.removeCallback(" + gson.toJson(callbackName) + ");");
    }

    /**
     * Execute javascript codes under webView.
     *
     * @param jsCode Javascript codes for running.
     */
    public void jsExec(String jsCode) {
        jsCode = "javascript: " + jsCode;
        Log.d("@execJS", jsCode);
        final String finalJsCode = jsCode;
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(finalJsCode);
            }
        });
    }

    /**
     * Initialize Icarus.
     */
    protected void initialize() {
        if (initialized) {
            return;
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                jsExec(
                        " $(function () {\n" +
                                "        var options = $.extend(defaultOptions, " + gson.toJson(options) + ");\n" +
                                "        window.editor = new Simditor(options);\n" +
                                "    });" +
                                ""
                );
                editorReady = true;
                if (onReadyCallbacks.size() > 0) {
                    Runnable runnable = onReadyCallbacks.poll();
                    while (runnable != null) {
                        runnable.run();
                        runnable = onReadyCallbacks.poll();
                    }
                }
            }

        });

        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.addJavascriptInterface(this, "IcarusBridge");
        initialized = true;
    }


    /**
     * Get content in rich editor.
     *
     * @param callback Callable will be called after content got.
     */
    public void getContent(Callback callback) {
        final String callbackName = addCallback(callback);
        webView.post(new Runnable() {
            @Override
            public void run() {
                jsExec("editor.getContentAsync(\"" + callbackName + "\");");
            }
        });
    }

    /**
     * Set HTML content to rich editor, method `render` must be called again after setContent.
     *
     * @param content HTML string
     */
    public void setContent(final String content) {
        runAfterReady(new Runnable() {
            @Override
            public void run() {
                jsExec("editor.setValue(" + gson.toJson(content) + ");");
                toolbar.resetButtonsStatus();
            }
        });
    }

    /**
     * Initialize and load WebView data. Must be called again after `setContent` method called.
     */
    public void render() {
        editorReady = false;
        initialize();
        webView.loadUrl("file:///android_asset/icarus-editor/editor.html");
//        webView.loadUrl("http://192.168.11.44:8080/editor.html");
        toolbar.resetButtonsStatus();
    }

    /**
     * Insert raw html code to current selection range of editor.
     *
     * @param html
     */
    public void insertHtml(String html) {
        jsExec("editor.toolbar.buttons['html'].insertHtml(" + gson.toJson(html) + ")");
    }

    /**
     * Call native `Callback` under javascript runtime.
     *
     * @param callbackName You can get `callbackName` by `addCallback` calling.
     * @param params       Passed from javascript runtime as callback param, must be STRING.
     */
    @JavascriptInterface
    public void callback(String callbackName, String params) {
        if (!callbacks.containsKey(callbackName)) {
            return;
        }

        Callback callback = callbacks.get(callbackName);
        callback.run(params);
        callbacks.remove(callbackName);
    }

    /**
     * Set activated status of given button name.
     *
     * @param buttonName Button name.
     * @param activated  Activated status.
     */
    @JavascriptInterface
    public void setButtonActivated(String buttonName, final boolean activated) {
        toolbar.setButtonActivated(buttonName, activated);
    }

    /**
     * Set enabled status of given button name.
     *
     * @param buttonName Button name.
     * @param enabled    Enabled status.
     */
    @JavascriptInterface
    public void setButtonEnabled(String buttonName, final boolean enabled) {
        toolbar.setButtonEnabled(buttonName, enabled);
    }

    /**
     * Show popover of given button name.
     *
     * @param buttonName   Button name.
     * @param params       Passed from javascript runtime as callback param, must be STRING.
     * @param callbackName You can get `callbackName` by `editor.addCallback` calling under javascript runtime.
     */
    @JavascriptInterface
    public void popover(String buttonName, final String params, final String callbackName) {
        toolbar.popover(buttonName, params, callbackName);
    }

    /**
     * Do something one time after the editor ready.
     *
     * @param runnable Callback
     */
    public void runAfterReady(Runnable runnable) {
        if (editorReady) {
            runnable.run();
        } else {
            try {
                onReadyCallbacks.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load css file.
     *
     * @param cssUrl Css file url that you want to load
     */
    public void loadCSS(String cssUrl) {

        final String js = String.format(
                "$(function() {" +
                        "$('head').append(" +
                        "$('<link></link>', " +
                        "{rel:'stylesheet', type:'text/css', href:'%s', media:'all'})" +
                        ")" +
                        "});",
                cssUrl
        );

        runAfterReady(new Runnable() {
            @Override
            public void run() {
                jsExec(js);
            }
        });
    }

    /**
     * Load javascript file
     *
     * @param jsUrl Javascript file url that you want to load
     */
    public void loadJs(String jsUrl) {

        final String js = String.format(
                "        var body  = document.getElementsByTagName(\"body\")[0];\n" +
                        "        var script  = document.createElement(\"script\");\n" +
                        "        script.type = \"text/javascript\";\n" +
                        "        script.src = \"%s\";\n" +
                        "        body.appendChild(script);",
                jsUrl
        );

        runAfterReady(new Runnable() {
            @Override
            public void run() {
                jsExec(js);
            }
        });
    }
}
