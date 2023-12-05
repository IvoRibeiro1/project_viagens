package com.example.project_viagens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    CheckBox checkBoxRememberMe;
    Button iniciarsessao;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRememberMe = findViewById(R.id.checkBox);
        iniciarsessao = findViewById(R.id.buttonLogin);

        databaseManager = new DatabaseManager(this);

        // Verificar se há dados salvos no banco de dados
        if (hasSavedLoginInfo()) {
            autoLogin();
        }

        iniciarsessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(login.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkBoxRememberMe.isChecked()) {
                    saveLoginInfo(email, password);
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Login bem sucedido", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainPage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private boolean hasSavedLoginInfo() {
        databaseManager.open();
        boolean hasData = databaseManager.hasSavedLoginInfo();
        databaseManager.close();
        return hasData;
    }

    private void autoLogin() {
        // Obter dados salvos
        databaseManager.open();
        String email = databaseManager.getSavedEmail();
        String password = databaseManager.getSavedPassword();
        databaseManager.close();

        // Autenticar automaticamente
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Login automático bem sucedido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login.this, "Erro no login automático", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveLoginInfo(String email, String password) {
        databaseManager.open();
        databaseManager.insertLoginInfo(email, password);
        databaseManager.close();
    }

    public void adicionaruser(View view) {

        Intent in = new Intent(this,criarconta.class);
        startActivity(in);

    }
}


