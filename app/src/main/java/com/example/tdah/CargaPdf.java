package com.example.tdah;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CargaPdf extends AppCompatActivity {

    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser usuario_actual;

    private ImageView iv_carga_pdf;
    private Uri uri_pdf = null;
    private ProgressDialog pd_dialogo;
    private String string_url_curriculum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_pdf);
        inicializa_firebase();
        iv_carga_pdf = findViewById(R.id.iv_carga_pdf);
        iv_carga_pdf.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // We will be redirected to choose pdf
            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            pd_dialogo = new ProgressDialog(this);

            pd_dialogo.setMessage("Cargando archivo");

            pd_dialogo.show();

            uri_pdf = data.getData();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String timestamp = "" + System.currentTimeMillis();
            final String messagePushID = timestamp;
            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child("cv-"+messagePushID + "." + "pdf");

            Toast.makeText(CargaPdf.this, filepath.getName(), Toast.LENGTH_SHORT).show();

            filepath.putFile(uri_pdf).continueWithTask((Continuation) task -> {

                if (!task.isSuccessful()) {

                    throw task.getException();

                }

                return filepath.getDownloadUrl();

            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                if (task.isSuccessful()) {

                    pd_dialogo.dismiss();

                    Uri uri = task.getResult();

                    string_url_curriculum = uri.toString();
                    databaseReference.child("Psicologo").child(usuario_actual.getUid()).child("string_perfilProfesional").setValue(string_url_curriculum).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(CargaPdf.this, "Cargado exitosamente", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(CargaPdf.this,PsicologoPrincipal.class));
                            }
                        }
                    });

                } else {

                    pd_dialogo.dismiss();

                    Toast.makeText(CargaPdf.this, "Error al cargar archivo", Toast.LENGTH_SHORT).show();

                }

            });
        }

    }
    /**
     * Crea he inicializa las instancias de Firebase Authentication y obtiene la referencia de
     * Firebase Database
     */
    private void inicializa_firebase() {
        FirebaseApp.initializeApp(this);

        FirebaseDatabase firebase_database = FirebaseDatabase.getInstance();
        databaseReference = firebase_database.getReference();
    }
}