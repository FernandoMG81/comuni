package com.project.comuni;

import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.project.comuni.Activities.LoginActivity;
import com.project.comuni.Models.Usuario;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Probando {
    /*
    @Override
    public void onClick(View v) {
        String correo = editTextLogin.getText().toString();
        if(isValidEmail(correo) && validarContraseña()) {

            String contraseña = editTextPassword.getText().toString();

            RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());

            String url = "http://10.0.2.2:50115/api/login/";
            Gson gson = new Gson();
            JsonObjectRequest request = new JsonObjectRequest
                    (Request.Method.GET, url, null,new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Usuario usuario = new Usuario();
                            usuario = gson.fromJson(response.toString(), Usuario.class);
                            Toast.makeText(LoginActivity.this, usuario.getNombre(), Toast.LENGTH_SHORT).show();
                            nextActivity();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Email", correo);
                    params.put("Contrasena", contraseña);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Host", "10.0.0.2:50115");
                    headers.put("User-Agent", "PostmanRuntime/7.19.0");
                    headers.put("Cache-Control", "no-cache");
                    headers.put("Accept-Encoding", "gzip, deflate");
                    headers.put("Content-Length", "60");
                    headers.put("Connection", "keep-alive");
                    //headers.put("apiKey", "xxxxxxxxxxxxxxx");
                    return headers;
                }

            };
            requestQueue.add(request);
        }
    });*/
}
