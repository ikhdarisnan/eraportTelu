package com.telu.eraporttelu.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.LoginActivity;
import com.telu.eraporttelu.R;
import com.telu.eraporttelu.model.modelLogin;
import com.telu.eraporttelu.response.LoadCPass;
import com.telu.eraporttelu.response.loadLogin;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.Objects;
import java.util.concurrent.atomic.DoubleAccumulator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pengaturanFragment extends Fragment {

    private static final String TAG = "pengaturanFragment";
    private static Context context;
    private TextView textGantiUsername, textGantiPassword;
    private LinearLayout layoutGantiUsername, layoutGantiPassword;
    private APIInterface mApiInterface;
    private Dialog changePassword;

    private EditText etUsername, etPassword;
    private ProgressBar pb;

    private String Uname, Pass;
    private String userPass;

    public pengaturanFragment() {

    }

    public static pengaturanFragment newInstance(Context c){
        pengaturanFragment fragment = new pengaturanFragment();
        context = c;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = context.getSharedPreferences("DATALOGIN",Context.MODE_PRIVATE);
        userPass = sharedPreferences.getString("PASSWORD", null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View pengaturanView = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        layoutGantiUsername = pengaturanView.findViewById(R.id.layout_pengaturan_gantiUsername);
        textGantiUsername = pengaturanView.findViewById(R.id.text_pengaturan_gantiUsername);
        layoutGantiPassword = pengaturanView.findViewById(R.id.layout_pengaturan_gantiPassword);
        textGantiPassword = pengaturanView.findViewById(R.id.text_pengaturan_gantiPassword);

        layoutGantiUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Fitur ini belum didukung", Toast.LENGTH_SHORT).show();
            }
        });

        layoutGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDialogGantiPassword();
//                Toast.makeText(context, "Fitur ini belum didukung", Toast.LENGTH_SHORT).show();
            }
        });
        return pengaturanView;
    }

    private void popUpDialogGantiPassword(){
        Button btnClose, btnOk;

        mApiInterface = APIClient.getClient().create(APIInterface.class);

        changePassword = new Dialog(context);
        changePassword.setCancelable(false);
        changePassword.setCanceledOnTouchOutside(false);

        changePassword.setContentView(R.layout.dialog_ganti_password);
        pb = changePassword.findViewById(R.id.pb_dialog_cPass);
        btnClose = changePassword.findViewById(R.id.btn_dialog_cPass_cancel);
        btnOk = changePassword.findViewById(R.id.btn_dialog_cPass_ok);
        etUsername = changePassword.findViewById(R.id.et_dialog_cPass_username);
        etPassword = changePassword.findViewById(R.id.et_dialog_cPass_pass);

        Uname = etUsername.getText().toString();
        Pass = etPassword.getText().toString();



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().length()<6 || etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Password Harus lebih dari 6 karakter");
                    if (etPassword.getText().toString().contains(" ")){
                        etPassword.setError("Password tidak dapat menggunakan spasi");
                    }
                }else {
                    Uname = etUsername.getText().toString();
                    Pass = etPassword.getText().toString();
                    validateCPass();
                }

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Batal")
                        .setMessage("Apakah anda yakin batal mengubah password?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changePassword.dismiss();
                            }
                        })
                        .setNegativeButton("Tidak", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        changePassword.show();
        Objects.requireNonNull(changePassword.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void validateCPass() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Validasi Ganti Password")
                .setMessage("Apakah anda yakin data yang diinputkan sudah benar ?")
                .setNegativeButton("Tidak", null)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkingCredential();
                        changePassword.dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void checkingCredential(){
        pb.setVisibility(View.VISIBLE);
        Call<loadLogin> loginCall = mApiInterface.onCallLogin(Uname,userPass);
        loginCall.enqueue(new Callback<loadLogin>() {
            @Override
            public void onResponse(Call<loadLogin> call, Response<loadLogin> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size() > 0){
                        updatePassword();
                    }
                    else {
                        popUpGagal("Gagal, Username tidak ditemukan");
                        Toast.makeText(context, "Username tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    popUpGagal("Gagal, Tidak berhasil");
                    Log.d(TAG, "onResponse: "+response.message());
                    Toast.makeText(context, "Gagal Tidak Berhasil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadLogin> call, Throwable t) {
                popUpGagal("Gagal , Harap Periksa Jaringan Internet");
                Toast.makeText(context, "Gagal, Harap periksa jaringan internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePassword(){
        pb.setVisibility(View.VISIBLE);
        Call<LoadCPass> passwordCall = mApiInterface.updatePass(Uname, Pass);
        passwordCall.enqueue(new Callback<LoadCPass>() {
            @Override
            public void onResponse(Call<LoadCPass> call, Response<LoadCPass> response) {
                if (response.isSuccessful()) {
                    pb.setVisibility(View.INVISIBLE);
                    if (response.body().getMessage().equals("Success")) {
                        popUpBerhasil("Berhasil Update Password, Silahkan login kembali dari awal");
                        context.getSharedPreferences("DATALOGIN",Context.MODE_PRIVATE).edit().clear().commit();
                        Toast.makeText(context, "Berhasil Update Password", Toast.LENGTH_SHORT).show();
                    } else {
                        popUpGagal("Gagal Update Password");
                        Toast.makeText(context, "Gagal Update Password", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    popUpGagal("Gagal Update Password");
                    Toast.makeText(context, "Gagal Update Password", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoadCPass> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
//                Toast.makeText(context, "Error: "+ t.toString(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "onFailure: "+t.toString());
                popUpGagal("Gagal Update Password, Harap Periksa Jaringan Internet" + t.toString());
            }
        });
    }

    private void popUpBerhasil(String message){
        Button btnClose;
        TextView textMessage;

        final Dialog popup = new Dialog(context);
        popup.setCancelable(false);
        popup.setCanceledOnTouchOutside(false);

        popup.setContentView(R.layout.dialog_popup_berhasil);
        btnClose = popup.findViewById(R.id.btn_popupdialog_berhasil_view2);
        textMessage = popup.findViewById(R.id.text_popupdialog_berhasil);

        textMessage.setText(message);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
                getActivity().finish();
                context.getSharedPreferences("DATALOGIN",Context.MODE_PRIVATE).edit().clear().commit();
                popup.dismiss();
            }
        });

        popup.show();
        Objects.requireNonNull(popup.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void popUpGagal(String message){
        Button btnClose;
        TextView textMessage;

        final Dialog popup = new Dialog(context);
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
        Objects.requireNonNull(popup.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
