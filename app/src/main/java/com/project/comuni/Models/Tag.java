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

    public Tag(String text, String backgroundColor, String textColor) {
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Tag() {
    }

    public Tag returnSmall(){
        return new Tag(
            this.text,
            this.backgroundColor,
            this.textColor
        );
    }

    public String validar (){
        if(text.isEmpty()){
            return "El tag debe tener nombre.";
        }
        return validarAmbosHex();
    }

    public String validarAmbosHex(){
        if(!validarHex(textColor).equals("Ok")) {
            return validarHex(textColor);
        }
        return validarHex(backgroundColor);
    }

    public String validarHex(String texto){
        String textoError = "";
        texto = texto.toUpperCase();
        String charsAdecuados = "0123456789ABCDEF";
        if (texto.length() != 6){
            return "El codigo hexagesimal no tiene 6 letras";
        }
        for (char c:texto.toCharArray()){
            String x = "";
            x += c;
            if (!charsAdecuados.contains(x)){
                textoError = textoError + x;
            }
        }
        if(!textoError.isEmpty()){
            return "Los caracteres" + textoError + "no son Hexagesimales.";
        }
        return  "Ok";
    }
    public String getColorT() {
        return "#" + textColor;
    }
    public String getColorB() {
        return "#" + backgroundColor;
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
