package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.project.comuni.Models.Espacio;
import com.project.comuni.Models.Firebase.User;
import com.project.comuni.Persistencia.UsuarioDAO;
import com.project.comuni.R;
import com.project.comuni.Utils.Constantes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroActivityAnterior extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextInputEditText txtClaveProfesional;
    private TextInputEditText txtNombre;
    private TextInputEditText txtApellido;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtContraseña;
    private TextInputEditText txtContraseñaRepetida;
    private Button btnRegistrar;
    private TextView btnIngresar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ImagePicker imagePicker;
    private CameraImagePicker cameraPicker;
    private Uri fotoPerfilUri;
    private String pickerPath;
    private ProgressBar regProgreso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        fotoPerfil = findViewById(R.id.idRegistroFotoPerfil);
        txtClaveProfesional = findViewById(R.id.idRegistroClaveProfesional);
        txtNombre = findViewById(R.id.idRegistroNombre);
        txtCorreo = findViewById(R.id.idRegistroCorreo);
        txtContraseña = findViewById(R.id.idRegistroContraseña);
        txtContraseñaRepetida = findViewById(R.id.idRegistroRepiteContraseña);
        btnRegistrar = findViewById(R.id.idBotonRegistro);
        btnIngresar = findViewById(R.id.buttonIrAIngreso);
        regProgreso = findViewById(R.id.idRegistroProgressBar);
        regProgreso.setVisibility(View.INVISIBLE);
        txtApellido = findViewById(R.id.idRegistroApellido);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        imagePicker = new ImagePicker(this);
        cameraPicker = new CameraImagePicker(this);

        cameraPicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivityAnterior.this, LoginActivity.class));
            }
        });

        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                if(!list.isEmpty()){
                    String path = list.get(0).getOriginalPath();
                    fotoPerfilUri = Uri.parse(path);
                    fotoPerfil.setImageURI(fotoPerfilUri);
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(RegistroActivityAnterior.this,"Error: "+s, Toast.LENGTH_LONG).show();
            }
        });

        cameraPicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                String path = list.get(0).getOriginalPath();
                fotoPerfilUri = Uri.fromFile(new File(path));
                fotoPerfil.setImageURI(fotoPerfilUri);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(RegistroActivityAnterior.this,"Error: "+s, Toast.LENGTH_LONG).show();
            }
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(RegistroActivityAnterior.this);
                dialog.setTitle("Foto de Perfil");

                String[] items = {"Galeria","Camara"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:
                                imagePicker.pickImage();
                                break;
                            case 1:
                                pickerPath = cameraPicker.pickImage();
                                break;
                        }
                    }
                });

                AlertDialog dialogConstruido = dialog.create();
                dialogConstruido.show();
            }
        });



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regProgreso.setVisibility(View.VISIBLE);
                btnRegistrar.setEnabled(false);

                String correo = txtCorreo.getText().toString();
                String nombre = txtNombre.getText().toString();
                String apellido = txtApellido.getText().toString();
                if(isValidEmail(correo) && validarContraseña() && validaNombre(nombre)){
                    String contraseña = txtContraseña.getText().toString();

                    mAuth.createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(RegistroActivityAnterior.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        if(fotoPerfilUri!=null){

                                            UsuarioDAO.getInstance().subirFotoUri(fotoPerfilUri, new UsuarioDAO.IDevolverURLfoto() {
                                                @Override
                                                public void devolverUrlString(String url) {
                                                    FirebaseInstanceId.getInstance().getInstanceId()
                                                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                                    if (task.isSuccessful()){
                                                                        User usuario = new User();
                                                                        usuario.setEmail(correo);
                                                                        usuario.setNombre(nombre);
                                                                        usuario.setApellido(apellido);
                                                                        usuario.setFotoPerfilURL(url);
                                                                        usuario.setToken(task.getResult().getToken());
                                                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                                                        DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                                                        reference.setValue(usuario);
                                                                        actualizarDatosUsuario(usuario.getNombre()+" "+usuario.getApellido(),fotoPerfilUri,mAuth.getCurrentUser());
                                                                        finish();
                                                                        Toast.makeText(RegistroActivityAnterior.this, "Se registro correctamente", Toast.LENGTH_LONG).show();
                                                                    }else{
                                                                        Toast.makeText(RegistroActivityAnterior.this, "Error al registrar ID", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }
                                                            });
                                                }
                                            });


                                        }else{
                                            FirebaseInstanceId.getInstance().getInstanceId()
                                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                            if (task.isSuccessful()){
                                                                User usuario = new User();
                                                                usuario.setEmail(correo);
                                                                usuario.setNombre(nombre);
                                                                usuario.setApellido(apellido);
                                                                usuario.setToken(task.getResult().getToken());
                                                                usuario.setFotoPerfilURL(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS);
                                                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                                                DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                                                reference.setValue(usuario);
                                                                actualizarDatosUsuario(usuario.getNombre()+" "+usuario.getApellido(),fotoPerfilUri,mAuth.getCurrentUser());
                                                                finish();
                                                                Toast.makeText(RegistroActivityAnterior.this, "Se registro correctamente", Toast.LENGTH_LONG).show();
                                                            }else{
                                                                Toast.makeText(RegistroActivityAnterior.this, "Error al registrar ID", Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        regProgreso.setVisibility(View.INVISIBLE);
                                        btnRegistrar.setEnabled(true);

                                        Toast.makeText(RegistroActivityAnterior.this, "Error al registrarse", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                } else Toast.makeText(RegistroActivityAnterior.this, "Validacion funcionando", Toast.LENGTH_LONG).show();

            }
        });

        Glide.with(this).load(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS).into(fotoPerfil);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Picker.PICK_IMAGE_DEVICE && resultCode == RESULT_OK){
            imagePicker.submit(data);
        }else if(requestCode == Picker.PICK_IMAGE_CAMERA && resultCode == RESULT_OK){

            cameraPicker.reinitialize(pickerPath);
            cameraPicker.submit(data);
        }
    }

    private boolean isValidEmail(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña(){
        String contraseña, contraseñaRepetida;
        contraseña = txtContraseña.getText().toString();
        contraseñaRepetida = txtContraseñaRepetida.getText().toString();
        if(contraseña.equals(contraseñaRepetida)){
            if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            } else return false;
        }else return false;
    }

    public boolean validaNombre(String nombre){
        return !nombre.isEmpty();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // You have to save path in case your activity is killed.
        // In such a scenario, you will need to re-initialize the CameraImagePicker
        outState.putString("picker_path", pickerPath);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // After Activity recreate, you need to re-intialize these
        // two values to be able to re-intialize CameraImagePicker
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("picker_path")) {
                pickerPath = savedInstanceState.getString("picker_path");
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }


    private void actualizarDatosUsuario(String nombre, Uri foto, FirebaseUser usuario) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child(Constantes.NODO_FOTOS_PERFIL + mAuth.getUid());
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
                                .setPhotoUri(uri)
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

    public String obtieneToken () {

        final String[] token = {""};
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            token[0] = task.getResult().getToken();
                        } else {

                        }
                    }
                });

        return token[0];
    }
}