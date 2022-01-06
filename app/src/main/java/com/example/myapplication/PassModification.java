package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PassModification extends AppCompatActivity {

    DBHelper db;
    Button goBack, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_modification);
        db = new DBHelper(this);
        goBack = findViewById(R.id.btn_return);
        save = findViewById(R.id.btn_send);
        EditText OldPass = findViewById(R.id.oldPass);
        EditText NewPass = findViewById(R.id.newPass);
        EditText Repass = findViewById(R.id.repass);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PassModification.this, Profile.class);
                startActivity(i);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = OldPass.getText().toString();
                String newPass = NewPass.getText().toString();
                String repass = Repass.getText().toString();

                if (oldPass.equals("") || newPass.equals("") || repass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (newPass.equals(repass)) {
                        Boolean checkPass = db.checkPass(oldPass);
                        if (checkPass) {
                            db.modifyPass(newPass);
                            Toast.makeText(getApplicationContext(), "modified successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(PassModification.this, Profile.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong password!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Confirmation echoue", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

