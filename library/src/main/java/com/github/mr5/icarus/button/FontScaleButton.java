package com.github.mr5.icarus.button;

import android.widget.TextView;

import com.github.mr5.icarus.Icarus;

/**
 * Created by decent on 16/5/30.
 */
public class FontScaleButton extends TextViewButton {
    public String getName() {
        return NAME_FONT_SCALE;
    }

    public FontScaleButton(TextView textView, Icarus icarus) {
        super(textView, icarus);
    }

    public void command() {
        if (getPopover() != null) {
            getPopover().show("", "");
        }
    }
}