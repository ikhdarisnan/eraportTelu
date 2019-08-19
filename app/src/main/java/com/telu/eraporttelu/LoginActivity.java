package com.telu.eraporttelu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout TIusername, TIpassword;
    private AutoCompleteTextView username;
    private EditText password;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViewLogin();
        loginAuth();
    }

    private void initViewLogin(){
        TIusername = findViewById(R.id.layout_login_autocompleteUsername);
        TIpassword = findViewById(R.id.layout_login_etPassword);
        username = findViewById(R.id.autoComplete_login_username);
        password = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        TextView lupaPassword = findViewById(R.id.text_login_lupaPassword);
    }

    private void loginAuth(){
        final String getUname = username.getText().toString();
        final String getPassword = password.getText().toString();
        if ((getUname.equals("guru")) && (getPassword.equals("guru"))){
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this, username.toString() + getPassword.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainGuruActivity.class));
                }
            });
        }
        else if ((username.getText().equals("siswa")) && (password.getText().equals("siswa"))){
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this, username.toString() + getPassword.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainUserActivity.class));
                }
            });
        }else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(LoginActivity.this, "apa + " +getUname.toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainGuruActivity.class));
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
