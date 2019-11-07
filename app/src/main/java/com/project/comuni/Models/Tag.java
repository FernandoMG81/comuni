package com.project.comuni.Models;

import com.project.comuni.Models.Firebase.Go;

public class Tag {

    private Go<Espacio> espacio;
    private String text;
    private String backgroundColor;
    private String textColor;

    public Tag(Go<Espacio> espacio, String text, String backgroundColor, String textColor) {
        this.espacio = espacio;
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Tag() {
    }


    public Go<Espacio> getEspacio() {
        return espacio;
    }

    public void setEspacio(Go<Espacio> espacio) {
        this.espacio = espacio;
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
