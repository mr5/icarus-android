package com.github.mr5.icarus.button;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.entity.Html;
import com.github.mr5.icarus.entity.Link;
import com.google.gson.Gson;

public class HtmlButton extends TextViewButton {
    protected Context context;
    protected EditText htmlInput;
    protected Dialog dialog;
    protected android.widget.Button okButton;
    protected android.widget.Button cancelButton;

    public HtmlButton(TextView textView, Icarus icarus) {
        super(textView, icarus);
        setName("html");
        context = getTextView().getContext();
        initDialog();
    }


    protected void initDialog() {
        dialog = new Dialog(context);

        dialog.setTitle("插入 HTML 代码");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formView = inflater.inflate(R.layout.form_html, null);
        dialog.setContentView(formView);
        htmlInput = (EditText) formView.findViewById(R.id.edit_html);
        okButton = (android.widget.Button) formView.findViewById(R.id.button_ok);
        cancelButton = (android.widget.Button) formView.findViewById(R.id.button_cancel);
    }

    @Override
    public void popover(String params, final String callbackName) {
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
                        html.setContent(htmlInput.getText().toString());
                        getIcarus().jsCallback(callbackName, html, Html.class);
                    }
                });
            }
        });
        dialog.show();
    }
}