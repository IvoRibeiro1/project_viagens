package com.example.project_viagens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

public class criarconta extends AppCompatActivity {

    EditText editTextusername, editTextEmail, editTextPassword;
    Button btnCriarconta;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_criarconta);


        auth = FirebaseAuth.getInstance();

        editTextusername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        btnCriarconta = findViewById(R.id.btncriarconta);

        btnCriarconta.setOnClickListener(view -> {
            String username, email, password;
            username = String.valueOf(editTextusername.getText());
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(criarconta.this, "Enter username", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(criarconta.this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(criarconta.this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                    ( this, (OnCompleteListener<AuthResult>) task -> {
                if (task.isSuccessful()) {

                    Toast.makeText(this, "Conta criada", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(criarconta.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }

    public void paginalogin(View view) {
        Intent in = new Intent(this,login.class);
        startActivity(in);
    }
}



