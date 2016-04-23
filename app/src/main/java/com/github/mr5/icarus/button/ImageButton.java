package com.github.mr5.icarus.button;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.entity.Image;
import com.google.gson.Gson;

public class ImageButton extends TextViewButton {
    protected Context context;
    protected EditText srcInput;
    protected EditText altInput;
    protected Dialog dialog;
    protected android.widget.Button okButton;
    protected android.widget.Button cancelButton;

    public ImageButton(TextView textView, Icarus icarus) {
        super(textView, icarus);
        setName("image");
        context = getTextView().getContext();
        initDialog();
    }


    protected void initDialog() {
        dialog = new Dialog(context);

        dialog.setTitle("插入图片");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formView = inflater.inflate(R.layout.form_image, null);
        dialog.setContentView(formView);
        srcInput = (EditText) formView.findViewById(R.id.edit_src);
        altInput = (EditText) formView.findViewById(R.id.edit_alt);
        okButton = (android.widget.Button) formView.findViewById(R.id.button_ok);
        cancelButton = (android.widget.Button) formView.findViewById(R.id.button_cancel);
    }

    @Override
    public void popover(String params, final String callbackName) {
        Gson gson = new Gson();

        final Image image = gson.fromJson(params, Image.class);


        srcInput.setText(image.getSrc());
        altInput.setText(image.getAlt());

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
                        image.setSrc(srcInput.getText().toString());
                        image.setAlt(altInput.getText().toString());
                        getIcarus().jsCallback(callbackName, image, Image.class);
                    }
                });
            }
        });
        dialog.show();
    }
}