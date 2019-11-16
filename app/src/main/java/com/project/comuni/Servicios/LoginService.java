package com.project.comuni.Servicios;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.Utils.Constantes;

public class LoginService {
    private FirebaseAuth fireAuth;
    Db db = new Db();
    String fotoUrl ="";

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

    public Go<Usuario> getGoUser(){
        FirebaseUser usuariox = fireAuth.getCurrentUser();
        Go<Usuario> usuario = new Go<>(usuariox.getUid(), new Usuario(usuariox));
        return usuario;
    }

    public Task<AuthResult> createUser(Go<Usuario> usuario, String contrasena, Uri foto) {
        return fireAuth.createUserWithEmailAndPassword(usuario.getObject().getEmail(), contrasena)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (foto != null) {
                                String url = uploadFoto(foto);
                                usuario.getObject().setFotoPerfilURL(url);
                            }
                            else {
                                usuario.getObject().setFotoPerfilURL(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS);
                            }
                            usuario.setKey(getUser().getUid());
                            new UsuarioService(usuario).update();

                            //Asocia los datos del usuario a firebase, nombre y fotoUrl
                            actualizarDatosUsuario(usuario.getObject().getNombre()+" "+usuario.getObject().getApellido(),foto,getUser());

                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    private void actualizarDatosUsuario(String nombre, Uri foto, FirebaseUser usuario) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child(Constantes.NODO_FOTOS_PERFIL+getUser().getUid());
        StorageReference imagenFilePath = mStorage.child(foto.getLastPathSegment());
        imagenFilePath.putFile(foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Imagen subida correctamente

                imagenFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nombre)
                                .setPhotoUri(foto)
                                .build();

                        usuario.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                         if(task.isSuccessful()){
                                             //actualizar los datos del usuario

                                         }
                                    }
                                });

                    }
                });
            }
        });
    }

    public Task<Void> changePassword(String contrasena){
        FirebaseUser user = fireAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(user.getEmail(), contrasena);
        user.reauthenticate(credential);
        return user.updatePassword(contrasena);
    }

    public Task<Void> changeEmail(Go<Usuario> usuario,String emailViejo, String contrasena){
        FirebaseUser user = fireAuth.getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(emailViejo, contrasena);
        user.reauthenticate(credential);
        return user.updateEmail(usuario.getObject().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    new UsuarioService(usuario).update();
                }
            }
        });

    }

    public String uploadFoto(Uri foto){
        //Date date = new Date();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("SSS.ss-mm-hh-dd-MM-yyyy", Locale.getDefault());

       String fireUrl = "Fotos/FotoPerfil/"+ getUser().getUid();
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
                    fotoUrl = uri.toString();
                }
            }
        });
        return fotoUrl;
    }
}
