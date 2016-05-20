package com.github.mr5.icarus;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.github.mr5.icarus.button.Button;
import com.github.mr5.icarus.button.TextViewButton;

import java.util.HashMap;

public class TextViewToolbar implements Toolbar {
    protected Handler mainLopperHandler = new Handler(Looper.getMainLooper());
    protected HashMap<String, TextViewButton> buttons = new HashMap<String, TextViewButton>();


    @Override
    public Button getButton(String buttonName) {
        return null;
    }

    public void addButton(final TextViewButton button) {
        buttons.put(button.getName(), button);
        button.getTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.isEnabled()) {
                    mainLopperHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("native.command", button.getName());
                            button.command();
                        }
                    });
                }
            }
        });
    }

    public void setButtonActivated(String buttonName, final boolean activated) {
        final TextViewButton button = buttons.get(buttonName);
        if (button != null) {
            mainLopperHandler.post(new Runnable() {
                @Override
                public void run() {
                    button.setActivated(activated);
                }
            });
        }
    }

    @Override
    public void resetButtonsStatus() {
        for (String name : buttons.keySet()) {
            Button button = buttons.get(name);
            if (button != null) {
                button.resetStatus();
            }
        }
    }

    public void setButtonEnabled(String buttonName, final boolean enabled) {
        final TextViewButton button = buttons.get(buttonName);
        if (button != null) {
            mainLopperHandler.post(new Runnable() {
                @Override
                public void run() {
                    button.setEnabled(enabled);
                }
            });
        }
    }

    public void popover(String buttonName, final String params, final String callbackName) {
        final TextViewButton button = buttons.get(buttonName);
        Log.d("@popover", buttonName);
        if (button != null && button.getPopover() != null) {
            mainLopperHandler.post(new Runnable() {
                @Override
                public void run() {
                    button.getPopover().show(params, callbackName);
                }
            });
        }
    }
}