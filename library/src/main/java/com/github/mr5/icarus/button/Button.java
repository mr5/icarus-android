package com.github.mr5.icarus.button;


import com.github.mr5.icarus.popover.Popover;

public interface Button {
    public static final String NAME_TITLE = "title";
    public static final String NAME_BOLD = "bold";
    public static final String NAME_ITALIC = "italic";
    public static final String NAME_UNDERLINE = "underline";
    public static final String NAME_STRIKETHROUGH = "strikethrough";
    public static final String NAME_FONT_SCALE = "fontScale";
    public static final String NAME_COLOR = "color";
    public static final String NAME_OL = "ol";
    public static final String NAME_UL = "ul";
    public static final String NAME_BLOCKQUOTE = "blockquote";
    public static final String NAME_CODE = "code";
    public static final String NAME_TABLE = "table";
    public static final String NAME_LINK = "link";
    public static final String NAME_IMAGE = "image";
    public static final String NAME_HR = "hr";
    public static final String NAME_INDENT = "indent";
    public static final String NAME_OUTDENT = "outdent";
    public static final String NAME_ALIGN_LEFT = "alignLeft";
    public static final String NAME_ALIGN_CENTER = "alignCenter";
    public static final String NAME_ALIGN_RIGHT = "alignRight";
    public static final String NAME_HTML = "html";

    public void setName(String name);

    public String getName();

    public boolean isEnabled();

    public void setEnabled(boolean enabled);

    public boolean isActivated();

    public void setActivated(boolean activated);

    public void command();

    public void resetStatus();

    public void setPopover(Popover popover);

    public Popover getPopover();
}