package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    DBHelper DB;
    ImageView icon;
    TextView icon_text;

    public void checkSolde(View view){
        Intent i = new Intent(Home.this, Solde.class);
        startActivity(i);
    }

    public void Virement(View view){
        Intent i = new Intent(Home.this, Transferer.class);
        startActivity(i);
    }

    public void Pay(View view){
        Intent i = new Intent(Home.this, Register.class);
        startActivity(i);
    }

    public void History(View view){
        Intent i = new Intent(Home.this, Historique.class);
        startActivity(i);
    }

    public void checkProfile(View view){
        Intent i = new Intent(Home.this, Profile.class);
        startActivity(i);
    }

    public void Contact(View view){
        Intent i;
        if(MainActivity.EMail.equals("admin@gmail.com")){
            i = new Intent(Home.this, Gestion.class);
        }else{
            i = new Intent(Home.this, Contact.class);
        }
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DB = new DBHelper(this);

        TextView name = (TextView) findViewById(R.id.NAME);
        TextView email = (TextView) findViewById(R.id.EMAIL);
        TextView tel = (TextView) findViewById(R.id.TEL);

        icon=findViewById(R.id.icon_img);
        icon_text=findViewById(R.id.icon_text);
        email.setText(MainActivity.EMail);
        name.setText(DB.getName(MainActivity.EMail));
        tel.setText(DB.getTel(MainActivity.EMail));

        if(MainActivity.EMail.equals("admin@gmail.com")){
            icon.setImageResource(R.drawable.ic_baseline_manage_accounts_24);
            icon_text.setText("Gestion des comptes");
        }else{
            icon.setImageResource(R.drawable.ic_baseline_mail_outline_24);
            icon_text.setText("Contactez-nous");
        }



    }
}