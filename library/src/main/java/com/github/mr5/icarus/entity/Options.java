package com.github.mr5.icarus.entity;

public class Options {
    /**
     * Placeholder of Editor. Use the placeholder attribute value of the textarea by default.
     */
    private String placeholder = "Icarus editor.";
    /**
     * Default image placeholder. Used when inserting pictures in Edtior.
     */
    private String defaultImage = "images/image.png";
    /**
     * Remove all styles in paste content automatically.
     */
    private boolean cleanPaste = false;

    /**
     * Tags that are allowed in Editor.
     */
    private String[] allowedTags = {"br", "span", "a", "img", "b", "strong", "i", "strike", "u", "font", "p", "ul", "ol", "li", "blockquote", "pre", "code", "h1", "h2", "h3", "h4", "hr"};

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public boolean isCleanPaste() {
        return cleanPaste;
    }

    public void setCleanPaste(boolean cleanPaste) {
        this.cleanPaste = cleanPaste;
    }

    public String[] getAllowedTags() {
        return allowedTags;
    }

    public void setAllowedTags(String[] allowedTags) {
        this.allowedTags = allowedTags;
    }

}
