package com.github.mr5.icarus.button;


public interface Button {
    public void setName(String name);

    public String getName();

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public boolean isActivated();

    public void setActivated(boolean activated);

    public void command();

    public void popover(String params, String callbackName);

    public void resetStatus();
}