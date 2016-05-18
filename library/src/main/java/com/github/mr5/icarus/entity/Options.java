package com.github.mr5.icarus.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, List<String>> allowedAttributes = new HashMap<>();

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

    public void addAllowedTag(String tagName) {
        if (allowedTags == null) {
            allowedTags = new String[]{};
        }
        int length = allowedTags.length;
        if (length <= 0) {
            length = -1;
        }
        allowedTags[length + 1] = tagName;
    }

    public Map<String, List<String>> getAllowedAttributes() {
        return allowedAttributes;
    }

    public void setAllowedAttributes(Map<String, List<String>> allowedAttributes) {
        this.allowedAttributes = allowedAttributes;
    }

    public void addAllowedAttributes(String tag, List<String> attributes) {
        allowedAttributes.put(tag, attributes);
    }
}
