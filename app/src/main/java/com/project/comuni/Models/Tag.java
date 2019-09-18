package com.project.comuni.Models;

public class Tag {

    private int id;
    private String text;
    private String backgroundColor;
    private String textColor;

    public Tag(int id,  String text, String backgroundColor, String textColor) {
        this.id = id;
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Tag() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
}
