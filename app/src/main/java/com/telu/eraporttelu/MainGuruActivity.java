package com.telu.eraporttelu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.fragment.inputNilaiFragment;
import com.telu.eraporttelu.fragment.lihatProfilGuruFragment;
import com.telu.eraporttelu.fragment.pengaturanFragment;
import com.telu.eraporttelu.model.modelDataGuru;
import com.telu.eraporttelu.response.loadGuru;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainGuruActivity extends AppCompatActivity {

    private static final String TAG = "MainGuruActivity";

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout leftDrawerLayout;
    private Button btnCloseleftDrawerGuru;
    private TextView namaGuru;
    private TextView nipGuru;
    private LinearLayout menuLihatProfilGuru;
    private LinearLayout menuLihatNilaiGuru;
    private LinearLayout menuPengaturanGuru;
    private LinearLayout logoutGuru;
    private ProgressDialog pd;

    private ArrayList<modelDataGuru> listDataDiriGuru;

    boolean doubleBackToExitPressedOnce = false;

    lihatProfilGuruFragment lihatProfilGuruFragment;
    pengaturanFragment pengaturanFragment;
    inputNilaiFragment inputNilaiFragment;

    APIInterface mApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guru);

        initViewMainUser();
        initLoadActionBar();
        initNavigationDrawerLeft();
        openProfilGuruFragment();

        mApiInterface = APIClient.getClient().create(APIInterface.class);
        String NIP = getSharedPreferences("DATALOGIN",MODE_PRIVATE).getString("USERNAME",null);
        onLoadProfileGuru(NIP);

        listDataDiriGuru = new ArrayList<>();

        logoutGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        menuLihatProfilGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfilGuruFragment();
            }
        });
        menuPengaturanGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPengaturanFragment();
            }
        });

        menuLihatNilaiGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInputNilaiFragment();
            }
        });

    }

    private void initLoadActionBar() {
        //TODO Toolbar Main Activity
        Toolbar toolbar = findViewById(R.id.appbarGuru);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private void initViewMainUser(){
        leftDrawerLayout = findViewById(R.id.layout_mainGuru_drawerLayout);
        btnCloseleftDrawerGuru = findViewById(R.id.btn_leftDrawerGuru_Close);
        namaGuru = findViewById(R.id.text_mainGuru_namaGuru);
        nipGuru = findViewById(R.id.text_mainGuru_NIP);
        menuLihatProfilGuru = findViewById(R.id.linearLayout_mainGuru_profilAkun);
        menuLihatNilaiGuru = findViewById(R.id.linearLayout_mainGuru_lihatNilai);
        menuPengaturanGuru = findViewById(R.id.linearLayout_mainGuru_pengaturan);
        logoutGuru = findViewById(R.id.linearLayout_mainGuru_logout);
        pd = new ProgressDialog(MainGuruActivity.this);
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        leftDrawerLayout.openDrawer(Gravity.LEFT,true);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
        Log.d(TAG, "onPostCreate: ");
    }


    private void initNavigationDrawerLeft(){
        //TODO Open Navigation Drawer
        drawerToggle = new ActionBarDrawerToggle(this,leftDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        leftDrawerLayout.addDrawerListener(drawerToggle);

        //TODO Close Navigation Drawer
        btnCloseleftDrawerGuru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
            }
        });
    }



    public void openPengaturanFragment(){
        pengaturanFragment = pengaturanFragment.newInstance(MainGuruActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frameLayout_GuruContainer, pengaturanFragment,"Pengaturan Fragment").commit();
        leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
    }

    private void openProfilGuruFragment(){
        lihatProfilGuruFragment = lihatProfilGuruFragment.newInstance(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frameLayout_GuruContainer, lihatProfilGuruFragment,"Lihat Profil Fragment").commit();
        leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
    }

    private void openInputNilaiFragment(){
        inputNilaiFragment = inputNilaiFragment.newInstance(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_frameLayout_GuruContainer, inputNilaiFragment,"Input Nilai Fragment").commit();
        leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
    }

    private void onLoadProfileGuru(String NIP){
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        Call<loadGuru> loadGuruProfileCall = mApiInterface.getDataGuru(NIP);
        loadGuruProfileCall.enqueue(new Callback<loadGuru>() {
            @Override
            public void onResponse(Call<loadGuru> call, Response<loadGuru> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size() > 0){
                        for (int i=0; i<response.body().getData().size(); i++){
                            pd.cancel();
                            listDataDiriGuru.add(response.body().getData().get(i));

                            namaGuru.setText(listDataDiriGuru.get(i).getNamaGuru());
                            nipGuru.setText(listDataDiriGuru.get(i).getNIPGuru());

                            getSharedPreferences("GURU:DATADIRI", MODE_PRIVATE).edit().putString("NIPGuru",listDataDiriGuru.get(i).getNIPGuru()).apply();
                            getSharedPreferences("GURU:DATADIRI", MODE_PRIVATE).edit().putString("NamaGuru",listDataDiriGuru.get(i).getNamaGuru()).apply();
                            getSharedPreferences("GURU", MODE_PRIVATE).edit().putString("isLogin",listDataDiriGuru.get(i).getIsLogin()).apply();

                        }
                    }else {
                        pd.cancel();
                        Toast.makeText(MainGuruActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.cancel();
                    Toast.makeText(MainGuruActivity.this, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadGuru> call, Throwable t) {
                pd.cancel();
                Toast.makeText(MainGuruActivity.this, "Error: "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGuruActivity.this);

        builder.setTitle("Logout Akun?")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreferences("DATALOGIN",MODE_PRIVATE).edit().putString("isLOGIN","0").apply();
                        startActivity(new Intent(MainGuruActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
