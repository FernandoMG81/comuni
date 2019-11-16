package com.project.comuni.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.cardview.widget.CardView;

import com.project.comuni.R;

public class PopUp {
    private Dialog popUp;
    private CardView BotonSi;
    private CardView BotonNo;
    private Context context;

    private void setPopUp() {
        popUp = new Dialog(context);
        popUp.setContentView(R.layout.delete_pop_up);
        popUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popUp.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        popUp.getWindow().getAttributes().gravity = Gravity.TOP;

        //Inicio de los widgets

        CardView BotonSi = popUp.findViewById(R.id.DeleteButtonSi);
        CardView BotonNo = popUp.findViewById(R.id.DeleteButtonNo);

        //Agregar Listener
        BotonSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });

        BotonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });

    }

    public PopUp (Context context){
        this.context = context;
    }

    public CardView getButtonSi(){ return BotonSi; }

    public Dialog getPopUp(){ return popUp; }
}
