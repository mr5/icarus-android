package com.github.mr5.icarus.popover;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.entity.Html;
import com.google.gson.Gson;

public class HtmlPopoverImpl implements Popover {
    protected Context context;
    protected EditText htmlInput;
    protected Dialog dialog;
    protected android.widget.Button okButton;
    protected android.widget.Button cancelButton;
    protected TextView textView;
    protected Icarus icarus;
    protected Handler mainLopperHandler;

    public HtmlPopoverImpl(TextView textView, Icarus icarus) {
        this.textView = textView;
        this.icarus = icarus;
        context = textView.getContext();
        mainLopperHandler = new Handler(Looper.getMainLooper());
        initDialog();
    }


    protected void initDialog() {
        dialog = new Dialog(context);

        dialog.setTitle("Insert raw HTML codes.");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formView = inflater.inflate(R.layout.form_html, null);
        dialog.setContentView(formView);
        htmlInput = (EditText) formView.findViewById(R.id.edit_html);
        okButton = (android.widget.Button) formView.findViewById(R.id.button_ok);
        cancelButton = (android.widget.Button) formView.findViewById(R.id.button_cancel);
    }


    @Override
    public void show(String params, final String callbackName) {
        Gson gson = new Gson();

        final Html html = gson.fromJson(params, Html.class);


        htmlInput.setText(html.getContent());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLopperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                icarus.jsRemoveCallback(callbackName);
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLopperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        html.setContent(htmlInput.getText().toString());
                        icarus.jsCallback(callbackName, html, Html.class);
                    }
                });
            }
        });
        dialog.show();
    }

    @Override
    public void hide() {
        dialog.dismiss();
    }
}
