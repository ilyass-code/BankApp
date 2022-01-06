package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Gestion extends AppCompatActivity {

    public static String ID_Client;

    DBHelper DB;
    ListView users;
    Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        DB = new DBHelper(this);
        retour=findViewById(R.id.retour1);
        users=findViewById(R.id.users);
        ArrayList Users = DB.Users();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,Users);

        users.setAdapter(arrayAdapter);
        users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ID_Client=arrayAdapter.getItem(position).substring(1,16);
                Intent I  = new Intent(getApplicationContext(), ManageAccount.class);
                startActivity(I);
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent I  = new Intent(getApplicationContext(), Home.class);
                startActivity(I);
                finish();
            }
        });

    }
}
