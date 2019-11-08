package com.project.comuni.Servicios;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.UploadTask;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Persistencia.UsuarioDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginService {
    private FirebaseAuth fireAuth;
    Db db = new Db();

    public LoginService(){
        fireAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> SignIn(String email, String contrasena){
        return fireAuth.signInWithEmailAndPassword(email,contrasena);
    }

    public FirebaseUser getUser(){
        return  fireAuth.getCurrentUser();
    }

    public void createUser(Go<Usuario> usuario, Uri foto) {
        fireAuth.createUserWithEmailAndPassword(usuario.getObject().getEmail(), usuario.getObject().getContrasena())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if (foto != null) {

                                UsuarioDAO.getInstance().subirFotoUri(foto, new UsuarioDAO.IDevolverURLfoto() {
                                    @Override
                                    public void devolverUrlString(String url) {
                                        usuario.getObject().setFoto(url);
                                        new UsuarioService(usuario).update();
                                        //finish();
                                    }
                                });


                            } else {
                                new UsuarioService(usuario).update();
                                //finish();
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

  /*  public String subirFotoUri(Uri foto){
        final String[] url = {""};
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS.ss-mm-hh-dd-MM-yyyy", Locale.getDefault());
        url[0] = simpleDateFormat.format(date);
        db.Storage(url[0]).putFile(foto).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                 return task.getResult().getStorage().getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri uri = task.getResult();
                    data.url(uri.toString());
                }
            }
        });
        return url;
    }*/

}
