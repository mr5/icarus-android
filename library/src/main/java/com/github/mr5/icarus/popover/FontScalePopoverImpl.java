package com.github.mr5.icarus.popover;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.entity.Html;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class FontScalePopoverImpl implements Popover {
    protected Context context;
    protected Dialog dialog;
    protected android.widget.Button okButton;
    protected android.widget.Button cancelButton;
    protected TextView textView;
    protected Icarus icarus;
    protected Handler mainLopperHandler;
    protected RadioGroup fontScales;

    protected Map<Integer, Integer> radioFontSizeMapping;

    public FontScalePopoverImpl(TextView textView, Icarus icarus) {
        this.textView = textView;
        this.icarus = icarus;
        context = textView.getContext();
        mainLopperHandler = new Handler(Looper.getMainLooper());
        initDialog();
    }

    public Map<Integer, Integer> getRadioFontSizeMapping() {
        if (radioFontSizeMapping == null) {
            radioFontSizeMapping = new HashMap<>();
            radioFontSizeMapping.put(R.id.font_scale_x_large, 5);
            radioFontSizeMapping.put(R.id.font_scale_large, 4);
            radioFontSizeMapping.put(R.id.font_scale_normal, 3);
            radioFontSizeMapping.put(R.id.font_scale_small, 2);
            radioFontSizeMapping.put(R.id.font_scale_x_small, 1);
        }
        return radioFontSizeMapping;
    }

    public void setRadioFontSizeMapping(Map<Integer, Integer> radioFontSizeMapping) {
        this.radioFontSizeMapping = radioFontSizeMapping;
    }


    protected void initDialog() {
        dialog = new Dialog(context);

        dialog.setTitle("Font scale");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View formView = inflater.inflate(R.layout.form_font_scale, null);
        dialog.setContentView(formView);
        fontScales = (RadioGroup) formView.findViewById(R.id.font_scale_radio_group);
        okButton = (android.widget.Button) formView.findViewById(R.id.font_button_ok);
        cancelButton = (android.widget.Button) formView.findViewById(R.id.font_button_cancel);
    }


    @Override
    public void show(String params, final String callbackName) {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLopperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLopperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        int checkedButtonId = fontScales.getCheckedRadioButtonId();
                        if (!getRadioFontSizeMapping().containsKey(checkedButtonId)) {
                            return;
                        }
                        icarus.jsExec(
                                String.format(
                                        "javascript: editor.toolbar.execCommand('%s', %s)",
                                        Button.NAME_FONT_SCALE,
                                        getRadioFontSizeMapping().get(checkedButtonId)
                                )
                        );
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
