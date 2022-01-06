package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

public class Historique extends AppCompatActivity {

    DBHelper DB;
    Button retour;
    RadioButton recu,envoye;

    public void history(View view){
        ListView historique = findViewById(R.id.users);
        recu=findViewById(R.id.historique_Received);
        envoye=findViewById(R.id.historique_Sent);
        if(recu.isChecked()) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, DB.historiqueReceived(DB.getID(MainActivity.EMail)));
            historique.setAdapter(arrayAdapter);
        }else if(envoye.isChecked()){
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, DB.historiqueSent(DB.getID(MainActivity.EMail)));
            historique.setAdapter(arrayAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        DB = new DBHelper(this);
        retour = findViewById(R.id.retour1);
        recu=findViewById(R.id.historique_Received);
        recu.toggle();
        history(recu);
        retour.setOnClickListener(v -> {
            Intent intent = new Intent(Historique.this, Home.class);
            startActivity(intent);
            finish();
        });
    }
}