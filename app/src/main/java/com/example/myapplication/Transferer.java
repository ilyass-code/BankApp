package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Transferer extends AppCompatActivity {

    DBHelper DB;
    Button valider,annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferer);
        DB=new DBHelper(this);

        TextView rib = findViewById(R.id.RiB);
        EditText montant = findViewById(R.id.montant);
        EditText targetedID = findViewById(R.id.targetedID);
        EditText Receivername = findViewById(R.id.targetedName);
        annuler = findViewById(R.id.annuler);
        valider = findViewById(R.id.valider);
        String SenderID = DB.getID(MainActivity.EMail);

        rib.setText("Votre RIB : "+SenderID);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double Montant = Double.parseDouble(montant.getText().toString());
                String targetedRib = targetedID.getText().toString();
                String ReceiverName = Receivername.getText().toString();

                if(DB.checkRIB(targetedRib)){
                    if(DB.getSolde(MainActivity.EMail)>=Montant){
                        DB.tranfer(targetedRib,Montant,DB.getSolde(MainActivity.EMail));
                        DB.addHistorique(SenderID,targetedRib,Montant,ReceiverName,DB.getName(MainActivity.EMail));
                        Toast.makeText(getApplicationContext(), "Operation effectuée!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Transferer.this, Home.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Votre solde est insuffisant!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Ce numero de compte n'existe pas.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transferer.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }
}