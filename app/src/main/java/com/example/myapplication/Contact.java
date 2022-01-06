package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class Contact extends AppCompatActivity {

    Button cancel,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextInputEditText message = findViewById(R.id.message);
        cancel=findViewById(R.id.btn_cancel);
        send=findViewById(R.id.btn_send);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Contact.this, Home.class);
                startActivity(i);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String[] TO = {"infoprojet2021@gmail.com"};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                String Message = message.getText().toString();

                emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "J'ai un problème.");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, Message);

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Envoi de mail..."));
                        finish();
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "Aucun client de messagerie n'est installé.", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}