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

// esta clase obtiene la informacion de la base de datos de firebase para mostrarlas en la aplicacion
// ademas configura el boton de actualizar el cual actualizará la informacion en tiempo real de la base de
// datos
public class Sensores extends AppCompatActivity {

    DatabaseReference mRootReference;
    Button mButtonActualizar;
    private DatabaseReference mDatabase;

    private TextView mEstadoVal;
    private TextView mCargaVal;
    private TextView mPosicionVal;
    private TextView mVoltVal;

    // verificara los datos en el xml para posteriormente darle su respectivo valor obtenido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        mButtonActualizar = findViewById(R.id.btn_actualizar);
        mEstadoVal = (TextView) findViewById(R.id.EstadoVal);
        mCargaVal = (TextView) findViewById(R.id.CargaVal);
        mPosicionVal = (TextView) findViewById(R.id.PosicionVal);
        mVoltVal = (TextView) findViewById(R.id.VoltVal);

        // boton para obtener la informacion de la base de datos y actualizarla

        mRootReference = FirebaseDatabase.getInstance().getReference();
        mButtonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String x = mRootReference.child("Usuario").getKey();

                mRootReference.child("Usuario").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // realiza una iteracion dentro de Usuario para seleccionar los datos dentro del grupo
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Usuario usuario = snapshot.getValue(Usuario.class);
                                String correo = usuario.getCorreo();
                                String nombre = usuario.getNombre();

                                // da un nuevo valor a la variable para ser posteriormente mostrada
                                mCargaVal.setText(usuario.getPaneles().getCarga());
                                mEstadoVal.setText(usuario.getPaneles().getEstado());
                                mPosicionVal.setText(usuario.getPaneles().getPosicion());
                                mVoltVal.setText(usuario.getPaneles().getVoltaje());
                            }

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
