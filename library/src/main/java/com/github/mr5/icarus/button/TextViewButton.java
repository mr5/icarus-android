package com.github.mr5.icarus.button;

import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.github.mr5.icarus.Icarus;
import com.github.mr5.icarus.R;
import com.github.mr5.icarus.popover.Popover;
import com.google.gson.Gson;

public class TextViewButton implements Button {
    protected TextView textView;
    protected Icarus icarus;
    protected String name;
    protected boolean enabled = true;
    protected boolean activated = false;
    protected Gson gson = new Gson();
    protected Handler mainLopperHandler;
    protected int enabledColor;
    protected int disabledColor;
    protected int activatedColor;
    protected int deactivatedColor;
    protected Popover popover;

    public int getEnabledColor() {
        return enabledColor;
    }

    public void setEnabledColor(int enabledColor) {
        this.enabledColor = enabledColor;
    }

    public int getDisabledColor() {
        return disabledColor;
    }

    public void setDisabledColor(int disabledColor) {
        this.disabledColor = disabledColor;
    }

    public int getActivatedColor() {
        return activatedColor;
    }

    public void setActivatedColor(int activatedColor) {
        this.activatedColor = activatedColor;
    }

    public int getDeactivatedColor() {
        return deactivatedColor;
    }

    public void setDeactivatedColor(int deactivatedColor) {
        this.deactivatedColor = deactivatedColor;
    }

    public TextViewButton(TextView textView, Icarus icarus) {
        this.textView = textView;
//        this.icarus = icarus;
        this.icarus = icarus;
        mainLopperHandler = new Handler(Looper.getMainLooper());

        setEnabledColor(textView.getResources().getColor(R.color.button_enabled));
        setDisabledColor(textView.getResources().getColor(R.color.button_disabled));
        setActivatedColor(textView.getResources().getColor(R.color.button_activated));
        setDeactivatedColor(textView.getResources().getColor(R.color.button_deactivated));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Icarus getIcarus() {
        return icarus;
    }

    public void setIcarus(Icarus icarus) {
        this.icarus = icarus;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (this.enabled) {
            textView.setTextColor(getEnabledColor());
        } else {
            textView.setTextColor(getDisabledColor());
        }
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
        if (this.activated) {
            textView.setTextColor(getActivatedColor());
        } else {
            textView.setTextColor(getDeactivatedColor());
        }
    }

    public void command() {
        icarus.jsExec("javascript: editor.toolbar.execCommand('" + getName() + "')");
    }

    public void resetStatus() {
        setActivated(false);
        setEnabled(true);
    }

    @Override
    public void setPopover(Popover popover) {
        this.popover = popover;
    }

    @Override
    public Popover getPopover() {
        return popover;
    }
}
