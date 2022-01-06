package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Solde extends AppCompatActivity {

    Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solde);
        DBHelper DB = new DBHelper(this);

        retour = findViewById(R.id.retour);
        TextView RIB = findViewById(R.id.Rib);
        TextView solde = findViewById(R.id.Solde);

        RIB.setText("230 320 "+DB.getID(MainActivity.EMail)+" 70");
        solde.setText(DB.getSolde(MainActivity.EMail) + " DHS");

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Solde.this, Home.class);
                startActivity(i);
                finish();
            }
        });
    }
}