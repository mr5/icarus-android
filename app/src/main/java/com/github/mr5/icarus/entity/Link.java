package com.github.mr5.icarus.entity;

import java.util.HashMap;
import java.util.Map;

public class Link {
    private String text = "";
    private String url = "";
    private String target = "_blank";
    private Map<String, String> attributes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String name, String value) {
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
