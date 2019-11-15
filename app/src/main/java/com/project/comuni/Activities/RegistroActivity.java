package com.project.comuni.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.project.comuni.Models.Firebase.Go;
import com.project.comuni.Models.Usuario;
import com.project.comuni.R;
import com.project.comuni.Servicios.LoginService;
import com.project.comuni.Utils.Constantes;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegistroActivity extends AppCompatActivity {

    //Proximamente en desuso
    private Spinner tipoSeleccionado;

    //Variables
    private Go<Usuario> usuario = new Go<>(new Usuario());

    //Layout
    private CircleImageView fotoPerfil;
    private TextInputEditText txtClaveProfesional;
    private TextInputEditText txtNombre;
    private TextInputEditText txtCorreo;
    private TextInputEditText txtContraseña;
    private TextInputEditText txtContraseñaRepetida;
    private Button btnRegistrar;
    private TextView btnIngresar;
    private ProgressBar regProgreso;

    //Imagenes
    private ImagePicker imagePicker;
    private CameraImagePicker cameraPicker;
    private Uri fotoPerfilUri;
    private String pickerPath;

    private void setLayout() {
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
    }

    private void setBtnLoginClick() {
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            }
        });
    }

    private void setImagePicker() {
        imagePicker = new ImagePicker(this);
        imagePicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                if (!list.isEmpty()) {
                    String path = list.get(0).getOriginalPath();
                    fotoPerfilUri = Uri.parse(path);
                    fotoPerfil.setImageURI(fotoPerfilUri);
                }
            }

            @Override
            public void onError(String s) {
                Toast.makeText(RegistroActivity.this, "Error: " + s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setCamaraPicker() {
        cameraPicker = new CameraImagePicker(this);
        cameraPicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);
        cameraPicker.setImagePickerCallback(new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                String path = list.get(0).getOriginalPath();
                fotoPerfilUri = Uri.fromFile(new File(path));
                fotoPerfil.setImageURI(fotoPerfilUri);
            }

            @Override
            public void onError(String s) {
                Toast.makeText(RegistroActivity.this, "Error: " + s, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setFotoPerfilClick() {
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(RegistroActivity.this);
                dialog.setTitle("Foto de Perfil");

                String[] items = {"Galeria", "Camara"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
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
    }

    private void setBtnRegistrarClick() {
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.getObject().setEmail(txtCorreo.getText().toString());
                usuario.getObject().setNombre(txtNombre.getText().toString());
                usuario.getObject().setToken(obtieneToken());
                String contrasena = txtContraseña.getText().toString();
                if (usuario.getObject().validarRegistro(contrasena, txtContraseñaRepetida.getText().toString())) {
                    new LoginService().createUser(usuario, contrasena, fotoPerfilUri)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegistroActivity.this, "Se registro correctamente", Toast.LENGTH_LONG).show();


                                        finish();
                                    } else {
                                        if(task.getException() instanceof FirebaseAuthUserCollisionException){

                                        }else{
                                            Toast.makeText(RegistroActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }
                            });

                } else
                    Toast.makeText(RegistroActivity.this, "Completo erroneamente algun dato", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setLayout();
        setBtnLoginClick();
        setImagePicker();
        setCamaraPicker();
        setFotoPerfilClick();
        setBtnRegistrarClick();

        Glide.with(this).load(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS).into(fotoPerfil);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Picker.PICK_IMAGE_DEVICE && resultCode == RESULT_OK) {
            imagePicker.submit(data);
        } else if (requestCode == Picker.PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {

            cameraPicker.reinitialize(pickerPath);
            cameraPicker.submit(data);
        }
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

    private void setSpinner() {
        //Tipo de usuario
        ArrayList<String> listaTipos = new ArrayList<>();
        listaTipos.add(" ");
        listaTipos.add("Profesional");
        listaTipos.add("Estudiante");
        ArrayAdapter<String> tipoAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaTipos);
        tipoSeleccionado.setAdapter(tipoAdapter);
        tipoSeleccionado.setSelection(0);
        tipoSeleccionado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (tipoSeleccionado.getSelectedItem().equals("Profesional")) {
                    txtClaveProfesional.setVisibility(View.VISIBLE);
                }
                if (tipoSeleccionado.getSelectedItem().equals("Estudiante")) {
                    txtClaveProfesional.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public String obtieneToken (){

        final String[] token = {""};
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            token[0] = task.getResult().getToken();
                        }else{

                        }
                    }
                });

        return token[0];
    }

}
//Viejo

  /*  // Sign in success, update UI with the signed-in user's information
                                        if(fotoPerfilUri!=null){
        UsuarioDAO.getInstance().subirFotoUri(fotoPerfilUri, new UsuarioDAO.IDevolverURLfoto() {
            @Override
            public void devolverUrlString(String url) {
                Toast.makeText(RegistroActivity.this, "Se registro correctamente", Toast.LENGTH_LONG).show();

                usuario.getObject().setFotoPerfilURL(url);

                FirebaseUser currentUser = mAuth.getCurrentUser();
                DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                reference.setValue(usuario);

                finish();
            }
        });


    }else{
        Toast.makeText(RegistroActivity.this, "Se registro correctamente", Toast.LENGTH_LONG).show();
        usuario.getObject().setFotoPerfilURL(Constantes.URL_FOTO_POR_DEFECTO_USUARIOS);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
        reference.setValue(usuario);

        finish();
    }


} else {
        // If sign in fails, display a message to the user.
        Toast.makeText(RegistroActivity.this, "Error al registrarse", Toast.LENGTH_LONG).show();
        }
        }
        });

        } else Toast.makeText(RegistroActivity.this, "Validacion funcionando", Toast.LENGTH_LONG).show();


        }*/
