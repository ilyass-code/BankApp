package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    DBHelper DB;

    public void Registration(View view){
        EditText name = findViewById(R.id.name);
        EditText tel = findViewById(R.id.tel);
        EditText email = findViewById(R.id.email_signup);
        EditText pass = findViewById(R.id.pass_signup);

        String Name = name.getText().toString();
        String Tel = tel.getText().toString();
        String Pass = pass.getText().toString();
        String Email = email.getText().toString();

        if(Name.equals("")||Pass.equals("")||Email.equals("")||Tel.equals(""))
            Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else{
            Boolean checkUser = DB.checkUser(Email);
            if(!checkUser){
                Boolean insert = DB.insertData(Name, Pass, Tel, Email);
                if(insert){
                    Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "User already exists! please sign in", Toast.LENGTH_SHORT).show();
            }
        }

        Log.i("button","working");
    }

    public void LogIn(View view){
        Intent i = new Intent(Register.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DB = new DBHelper(this);
    }
}