package com.intilligenz.amst_monitoreodepenelessolares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

// clase donde se muestra la informacíón del usuario que se ha logeado en la aplicacion
// ademas tres botones, datos del panel, historial y cierre de sesion

public class PerfilUsuario extends AppCompatActivity {

    TextView txt_id, txt_name, txt_email;
    ImageView imv_photo;
    DatabaseReference db_reference;
    String token = "";

    // obtiene la informacion del usuario logeado para mostrarlo en pantalla,
    // los datos a mostrar son de userId, nombre, correo y una foto.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        Intent intent = getIntent();
        Intent login = getIntent();
        HashMap<String, String> info_user = (HashMap<String, String>)intent.getSerializableExtra("info_user");
        this.token = (String)login.getExtras().get("token");

        txt_id = findViewById(R.id.txt_userId);
        txt_name = findViewById(R.id.txt_nombre);
        txt_email = findViewById(R.id.txt_correo);
        imv_photo = findViewById(R.id.imv_foto);

        txt_id.setText(info_user.get("user_id"));
        txt_name.setText(info_user.get("user_name"));
        txt_email.setText(info_user.get("user_email"));
        String photo = info_user.get("user_photo");
        Picasso.with(getApplicationContext()).load(photo).into(imv_photo);

        iniciarBaseDeDatos();

    }

    // inicia la base de datos y obtiene los valores dentro de grupos
    private void iniciarBaseDeDatos() {
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupos");
    }

    // Boton que redirige hacia la actividad Sensores donde se muestra la información recopilada
    // por el panel solar
    public void revisarSensores(View v){
        Intent red_sensores = new Intent(getBaseContext(), Sensores.class);
        red_sensores.putExtra("token", token);
        startActivity(red_sensores);
    }

    //activa el boton de cierre de sesion el cual desvincula la cuenta de google con la aplicacion
    //lo que permite el ingreso de un nuevo usuario

    public void cerrarSesion(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg", "cerrarSesion");
        startActivity(intent);
    }
}