package com.example.amst_monitoreodepenelessolares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Sensores extends AppCompatActivity {

    DatabaseReference mRootReference;
    Button mButtonActualizar;
    private DatabaseReference mDatabase;

    private TextView mEstadoVal;
    private TextView mCargaVal;
    private TextView mPosicionVal;
    private TextView mVoltVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        mButtonActualizar = findViewById(R.id.btn_actualizar);
        mEstadoVal = (TextView) findViewById(R.id.EstadoVal);
        mCargaVal = (TextView) findViewById(R.id.CargaVal);
        mPosicionVal = (TextView) findViewById(R.id.PosicionVal);
        mVoltVal = (TextView) findViewById(R.id.VoltVal);


        mRootReference = FirebaseDatabase.getInstance().getReference();
        mButtonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String x = mRootReference.child("Usuario").getKey();

                mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {


                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Usuario usuario = snapshot.getValue(Usuario.class);
                                String correo = usuario.getCorreo();
                                String nombre = usuario.getNombre();

                                mCargaVal.setText(usuario.getPaneles().getCarga());
                                mEstadoVal.setText(usuario.getPaneles().getEstado());
                                mPosicionVal.setText(usuario.getPaneles().getPosicion());
                                //mVoltVal.setText(voltaje);


                                //DataSnapshot paneles = snapshot.child("Usuario").child("paneles");
                                //String panelesValor = paneles.getValue().toString();
                            }

                            //String carga = dataSnapshot.child("carga").getValue().toString();

                                /*DataSnapshot paneles = dataSnapshot.child("paneles");
                                String carga = dataSnapshot.child("paneles").child("carga").getValue().toString();
                                String estado = dataSnapshot.child("estado").getValue().toString();
                                String posicion = dataSnapshot.child("posicion").getValue().toString();
                                String voltaje = dataSnapshot.child("voltaje").getValue().toString();
                                System.out.println(carga);
                                Log.d("aquiestoy", "hola");
                                mCargaVal.setText(carga);
                                mEstadoVal.setText(estado);
                                mPosicionVal.setText(posicion);
                                mVoltVal.setText(voltaje);*/
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

}
