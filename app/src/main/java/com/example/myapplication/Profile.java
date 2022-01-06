package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class Profile extends AppCompatActivity {

    DBHelper DB;
    Button changePass,button;

    public void LogOut(View view){
        Intent i = new Intent(Profile.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        DB = new DBHelper(this);
        changePass = findViewById(R.id.btn_send);
        button = findViewById(R.id.retour1);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, PassModification.class);
                startActivity(i);
            }
        });

        TextView RIB = (TextView) findViewById(R.id.RIB);
        TextView email = (TextView) findViewById(R.id.EMAIL);
        TextView name = (TextView) findViewById(R.id.NAME);
        TextView tel = (TextView) findViewById(R.id.TEL);

        RIB.setText(DB.getID(MainActivity.EMail));
        email.setText(MainActivity.EMail);
        name.setText(DB.getName(MainActivity.EMail));
        tel.setText(DB.getTel(MainActivity.EMail));

        ImageView editImg = findViewById(R.id.imageEdit);
        ImageView editImgTel = findViewById(R.id.imageEditTel);
        EditText editEmail = findViewById(R.id.Email);
        TextInputLayout inputEmail = findViewById(R.id.textInputEmail);
        EditText editTel = findViewById(R.id.Tel);
        TextInputLayout inputTel = findViewById(R.id.textInputTel);

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedEmail = editEmail.getText().toString();

                if (editImg.getDrawable().equals(R.drawable.edit) || inputEmail.getVisibility()==View.GONE) {
                    email.setVisibility(View.GONE);
                    inputEmail.setVisibility(View.VISIBLE);
                    editImg.setImageResource(R.drawable.check_img);
                } else {
                    if (editedEmail.equals("")) {
                        Toast.makeText(getApplicationContext(), "le champ est vide", Toast.LENGTH_SHORT).show();
                        email.setVisibility(View.VISIBLE);
                        inputEmail.setVisibility(View.GONE);
                        editImg.setImageResource(R.drawable.edit);
                    } else {
                        Boolean checkUser = DB.checkUser(editedEmail);
                        if (!checkUser) {
                            DB.modifyEmail(MainActivity.EMail, editedEmail);
                            email.setVisibility(View.VISIBLE);
                            inputEmail.setVisibility(View.GONE);
                            editImg.setImageResource(R.drawable.edit);
                            MainActivity.EMail = editedEmail;
                            email.setText(MainActivity.EMail);
                            editEmail.getText().clear();
                            Toast.makeText(getApplicationContext(), "modified successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "User already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        editImgTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedTel = editTel.getText().toString();

                if (editImgTel.getDrawable().equals(R.drawable.edit) || inputTel.getVisibility()==View.GONE) {
                    tel.setVisibility(View.GONE);
                    inputTel.setVisibility(View.VISIBLE);
                    editImgTel.setImageResource(R.drawable.check_img);
                } else {
                    if (editedTel.equals("")) {
                        Toast.makeText(getApplicationContext(), "le champ est vide", Toast.LENGTH_SHORT).show();
                        tel.setVisibility(View.VISIBLE);
                        inputTel.setVisibility(View.GONE);
                        editImgTel.setImageResource(R.drawable.edit);
                    } else {
                        Boolean checkTel = DB.checkTel(editedTel);
                        if (!checkTel) {
                            DB.modifyTel(MainActivity.EMail, editedTel);
                            tel.setVisibility(View.VISIBLE);
                            inputTel.setVisibility(View.GONE);
                            editImgTel.setImageResource(R.drawable.edit);
                            tel.setText(editedTel);
                            editTel.getText().clear();
                            Toast.makeText(getApplicationContext(), "modified successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "User already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile.this, Home.class);
                startActivity(i);
                finish();
            }
        });

        }
    }
