package com.telu.eraporttelu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

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
        username = findViewById(R.id.autoComplete_login_username);
        password = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        TextView lupaPassword = findViewById(R.id.text_login_lupaPassword);
    }

    private void loginAuth(){
        if ((username.getText().equals("guru")) && (password.getText().equals("guru"))){
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, MainGuruActivity.class));
                }
            });
        }
        else if ((username.getText().equals("siswa")) && (password.getText().equals("siswa"))){
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, MainGuruActivity.class));
                }
            });
        }
    }
}
