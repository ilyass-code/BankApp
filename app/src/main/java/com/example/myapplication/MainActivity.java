package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static String EMail;

    DBHelper DB;

    public void Login (View view){
        EditText Email, password;
        Email = (EditText) findViewById(R.id.Email);
        password = (EditText) findViewById(R.id.montant);

        String email = Email.getText().toString();
        String pass = password.getText().toString();

        if(email.equals("")||pass.equals(""))
            Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkEmailPassword = DB.checkEmailPassword(email, pass);
            if(checkEmailPassword){
                Toast.makeText(getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                Intent I  = new Intent(getApplicationContext(), Home.class);
                startActivity(I);
                EMail = email;
            }else{
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Register(View view){
        Intent i = new Intent(MainActivity.this, Register.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);


        mAuth = FirebaseAuth.getInstance();
    }



}