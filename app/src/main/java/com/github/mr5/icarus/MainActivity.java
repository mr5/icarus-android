package com.github.mr5.icarus;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.mr5.icarus.button.HtmlButton;
import com.github.mr5.icarus.button.TextViewButton;
import com.github.mr5.icarus.button.ImageButton;
import com.github.mr5.icarus.button.LinkButton;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    WebView webView;

    protected Icarus icarus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.editor);
        TextViewToolbar toolbar = new TextViewToolbar();
        icarus = new Icarus(toolbar, webView);
        prepareToolbar(toolbar, icarus);
        icarus.render();
    }

    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "richeditor/styles/simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put("bold", R.id.button_bold);
        generalButtons.put("ol", R.id.button_list_ol);
        generalButtons.put("blockquote", R.id.button_blockquote);
        generalButtons.put("hr", R.id.button_hr);
        generalButtons.put("ul", R.id.button_list_ul);
        generalButtons.put("alignLeft", R.id.button_align_left);
        generalButtons.put("alignCenter", R.id.button_align_center);
        generalButtons.put("alignRight", R.id.button_align_right);
        generalButtons.put("italic", R.id.button_italic);
        generalButtons.put("indent", R.id.button_indent);
        generalButtons.put("outdent", R.id.button_outdent);
        generalButtons.put("code", R.id.button_math);
        generalButtons.put("underline", R.id.button_underline);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }
        TextView linkButtonTextView = (TextView) findViewById(R.id.button_link);
        linkButtonTextView.setTypeface(iconfont);
        LinkButton linkButton = new LinkButton(linkButtonTextView, icarus);
        toolbar.addButton(linkButton);

        TextView imageButtonTextView = (TextView) findViewById(R.id.button_image);
        imageButtonTextView.setTypeface(iconfont);
        ImageButton imageButton = new ImageButton(imageButtonTextView, icarus);
        toolbar.addButton(imageButton);

        TextView htmlButtonTextView = (TextView) findViewById(R.id.button_html5);
        htmlButtonTextView.setTypeface(iconfont);
        HtmlButton htmlButton = new HtmlButton(htmlButtonTextView, icarus);
        toolbar.addButton(htmlButton);
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            webView.reload();
            return true;
        }
        if (id == R.id.action_get_content) {
            if (icarus == null) {
                return true;
            }
            icarus.getContent(new Callback() {
                @Override
                public void run(String params) {
                    Log.d("content", params);
                }
            });


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDestroy() {
        webView.destroy();
        super.onDestroy();
    }
}