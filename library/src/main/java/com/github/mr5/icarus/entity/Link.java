package com.github.mr5.icarus.entity;

import java.util.HashMap;
import java.util.Map;

public class Link {
    private String text = "";
    private Map<String, String> attributes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return getAttribute("href");
    }

    public void setUrl(String url) {
        setAttribute("href", url);
    }

    public String getTarget() {
        return getAttribute("target");
    }

    public void setTarget(String target) {
        setAttribute("target", target);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(String name) {
        if (attributes == null) {
            return null;
        }

        return attributes.get(name);
    }

    public void setAttribute(String name, String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        attributes.put(name, value);
    }

    public void removeAttribute(String name, String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }

        attributes.remove(name);
    }
}
