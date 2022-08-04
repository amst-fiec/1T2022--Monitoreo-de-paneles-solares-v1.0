package com.example.amst_monitoreodepenelessolares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PerfilUsuario extends AppCompatActivity {

    TextView txt_id, txt_name, txt_email;
    ImageView imv_photo;
    DatabaseReference db_reference;
    String token = "";

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
        leerTweets();
        escribirTweets(info_user.get("user_name"));
    }
    private void escribirTweets(String user_name) {
        String tweet = "hola mundo firebase 2";
        String fecha = "19/06/2022"; //Fecha actual
        String autor= "Holguin, Moran, Tumbaco";
        Map<String, String> hola_tweet = new HashMap<String, String>();
        hola_tweet.put("autor", autor);
        hola_tweet.put("fecha", fecha);
        DatabaseReference tweets = db_reference.child("Grupo2").child("tweets");
        tweets.setValue(tweet);
        tweets.child(tweet).child("autor").setValue(autor);
        tweets.child(tweet).child("fecha").setValue(fecha);
    }

    private void leerTweets() {
        db_reference.child("Grupo2").child("tweets")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            System.out.println(snapshot);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.out.println(error.toException());
                    }
                });
    }

    private void iniciarBaseDeDatos() {
        db_reference = FirebaseDatabase.getInstance().getReference().child("Grupos");
    }

    public void revisarSensores(View v){
        Intent red_sensores = new Intent(getBaseContext(), RedSensores.class);
        red_sensores.putExtra("token", token);
        startActivity(red_sensores);
    }
    public void cerrarSesion(View view){
        FirebaseAuth.getInstance().signOut();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("msg", "cerrarSesion");
        startActivity(intent);
    }
}