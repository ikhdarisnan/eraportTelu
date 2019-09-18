package com.telu.eraporttelu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.response.loadLogin;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private TextInputLayout TIusername, TIpassword;
    private AutoCompleteTextView username;
    private TextView lupaPassword;
    private EditText password;
    private Button btnLogin;
    private ProgressBar pd;

    APIInterface mApiInterface;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        initViewLogin();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString();
                String mPassword = password.getText().toString();
                onLoadLogin(mUsername,mPassword);
//                loginAuth();
            }
        });

        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Fitur ini belum didukung", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViewLogin(){
        TIusername = findViewById(R.id.layout_login_autocompleteUsername);
        TIpassword = findViewById(R.id.layout_login_etPassword);
        username = findViewById(R.id.autoComplete_login_username);
        password = findViewById(R.id.et_login_password);
        btnLogin = findViewById(R.id.btn_login_login);
        lupaPassword = findViewById(R.id.text_login_lupaPassword);
        pd = findViewById(R.id.pb_login);
    }

    private void loginAuth(){
        final String getUname = username.getText().toString();
        final String getPassword = password.getText().toString();
        if ((getUname.equals("guru")) && (getPassword.equals("guru"))){
            startActivity(new Intent(LoginActivity.this, MainGuruActivity.class));
            finish();
        }
        else if ((getUname.equals("siswa")) && (getPassword.equals("siswa"))){
            startActivity(new Intent(LoginActivity.this, MainUserActivity.class));
            finish();
        }else {
            Toast.makeText(LoginActivity.this, "Failed" , Toast.LENGTH_SHORT).show();
        }
    }

    private void onLoadLogin(final String username, String password){
        pd.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
        Call<loadLogin> loginCall = mApiInterface.onCallLogin(username, password);
        loginCall.enqueue(new Callback<loadLogin>() {
            @Override
            public void onResponse(Call<loadLogin> call, Response<loadLogin> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("Failed")){
                        btnLogin.setVisibility(View.VISIBLE);
                        popUpLogin("Username / Password yang anda masukkan tidak sesuai");
                        Toast.makeText(LoginActivity.this, "Credential Wrong", Toast.LENGTH_SHORT).show();
                    }else {
                        btnLogin.setVisibility(View.VISIBLE);
                        pd.setVisibility(View.GONE);
                        for (int i=0; i<response.body().getData().size();i++){
                            getSharedPreferences("DATALOGIN", MODE_PRIVATE).edit().putString("USERNAME",response.body().getData().get(i).getUsername()).apply();
                            getSharedPreferences("DATALOGIN", MODE_PRIVATE).edit().putString("STATUS",response.body().getData().get(i).getStatusLogin()).apply();
                            if (response.body().getData().get(i).getStatusLogin().equals("1")){
                                startActivity(new Intent(LoginActivity.this, MainGuruActivity.class));
                                finish();
                            }else {
                                startActivity(new Intent(LoginActivity.this, MainUserActivity.class));
                                finish();
                            }
                        }

                    }
                }else {
                    btnLogin.setVisibility(View.VISIBLE);
                    pd.setVisibility(View.GONE);
                    popUpLogin("Username / Password yang anda masukkan tidak sesuai");
                }
            }

            @Override
            public void onFailure(Call<loadLogin> call, Throwable t) {
                btnLogin.setVisibility(View.VISIBLE);
                pd.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Jaringan tidak terhubung. Periksa jaringan anda terlebih dahulu", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void popUpLogin(String message){
        Button btnClose;
        TextView textMessage;

        final Dialog popup = new Dialog(this);
        popup.setCancelable(false);
        popup.setCanceledOnTouchOutside(false);

        popup.setContentView(R.layout.dialog_popup_gagal);
        btnClose = popup.findViewById(R.id.btn_popupdialog_gagal);
        textMessage = popup.findViewById(R.id.text_popupdialog_gagal);

        textMessage.setText(message);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

        popup.show();
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Klik sekali lagi untuk keluar aplikasi", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
