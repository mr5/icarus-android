package com.github.mr5.icarus.button;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.entity.Link;
import com.google.gson.Gson;

public class LinkButton extends TextViewButton {
    protected Context context;
    protected EditText textInput;
    protected EditText urlInput;
    protected Dialog dialog;
    protected android.widget.Button okButton;
    protected android.widget.Button cancelButton;

    public LinkButton(TextView textView, Icarus icarus) {
        super(textView, icarus);
        setName("link");
        context = getTextView().getContext();
        initDialog();
    }


    protected void initDialog() {
        dialog = new Dialog(context);

        dialog.setTitle("插入链接");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formView = inflater.inflate(R.layout.form_link, null);
        dialog.setContentView(formView);
        textInput = (EditText) formView.findViewById(R.id.edit_text);
        urlInput = (EditText) formView.findViewById(R.id.edit_url);
        okButton = (android.widget.Button) formView.findViewById(R.id.button_ok);
        cancelButton = (android.widget.Button) formView.findViewById(R.id.button_cancel);
    }

    @Override
    public void popover(String params, final String callbackName) {
        Gson gson = new Gson();
        Log.d("@popover params", params);
        final Link link = gson.fromJson(params, Link.class);


        textInput.setText(link.getText());
        urlInput.setText(link.getUrl());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLopperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                getIcarus().jsRemoveCallback(callbackName);
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLopperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        link.setText(textInput.getText().toString());
                        link.setUrl(urlInput.getText().toString());
                        getIcarus().jsCallback(callbackName, link, Link.class);
                    }
                });
            }
        });
        dialog.show();
    }
}