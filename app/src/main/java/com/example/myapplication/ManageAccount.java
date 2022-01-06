package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAccount extends AppCompatActivity {

    DBHelper DB;
    Button retour1,delete;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        DB = new DBHelper(this);
        retour1 = findViewById(R.id.retour1);
        delete=findViewById(R.id.btnDelete);
        info=findViewById(R.id.ClientInfo);
        info.setText(DB.UserAccount(Gestion.ID_Client));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB.deleteUser(Gestion.ID_Client)){
                    Toast.makeText(getApplicationContext(), "Account deleted!", Toast.LENGTH_SHORT).show();
                    Intent I  = new Intent(getApplicationContext(), Gestion.class);
                    startActivity(I);
                    finish();
                }
            }
        });

        retour1.setOnClickListener(v -> {
            Intent I  = new Intent(getApplicationContext(), Home.class);
            startActivity(I);
            finish();
        });
    }
}