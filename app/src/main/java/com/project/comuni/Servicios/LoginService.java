package com.project.comuni.Servicios;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;

public class LoginService {
    private FirebaseAuth fireAuth;
    Db db = new Db();

    public interface data{
        static void url(String url){};
    }

    public LoginService(){
        fireAuth = FirebaseAuth.getInstance();
    }

    public Task<AuthResult> SignIn(String email, String contrasena){
        return fireAuth.signInWithEmailAndPassword(email,contrasena);
    }

    public FirebaseUser getUser(){
        return  fireAuth.getCurrentUser();
    }

    public Task<AuthResult> createUser(Go<Usuario> usuario, String contrasena, Uri foto) {
        return fireAuth.createUserWithEmailAndPassword(usuario.getObject().getEmail(), contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if (foto != null) {
                                //String url = uploadFoto(foto);
                                //usuario.getObject().setFoto(url);
                            }
                            else {
                                String url = "";
                                usuario.getObject().setFoto(url);
                            }
                            new UsuarioService(usuario).update();

                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

/*   public String uploadFoto(Uri foto){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS.ss-mm-hh-dd-MM-yyyy", Locale.getDefault());
        String fireUrl = "Fotos/FotosPerfil/" + simpleDateFormat.format(date);
        db.Storage(fireUrl).putFile(foto).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
        return data.url();
    }*/
}
