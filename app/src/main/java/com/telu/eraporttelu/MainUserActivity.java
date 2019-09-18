package com.telu.eraporttelu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.telu.eraporttelu.fragment.lihatNilaiFragment;
import com.telu.eraporttelu.fragment.lihatProfilSiswaFragment;
import com.telu.eraporttelu.fragment.pengaturanFragment;
import com.telu.eraporttelu.model.modelSiswa;
import com.telu.eraporttelu.response.loadGuru;
import com.telu.eraporttelu.response.loadSiswa;
import com.telu.eraporttelu.service.APIClient;
import com.telu.eraporttelu.service.APIInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainUserActivity extends AppCompatActivity {

    private static final String TAG = "MainUserActivity";

    //TODO VAR LEFT DRAWER
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout leftDrawerLayout;
    private Button btnCloseleftDrawer;
    private TextView namaUser;
    private TextView nisUser;
    private LinearLayout menuLihatProfil, menuLihatNilai, menuPengaturan, logout;
    private ProgressDialog pd;

    private ArrayList<modelSiswa> listDataSiswa;

    boolean doubleBackToExitPressedOnce = false;

    APIInterface mApiInterface;
    pengaturanFragment pengaturanFragment;
    lihatProfilSiswaFragment lihatProfilSiswaFragment;
    lihatNilaiFragment lihatNilaiFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        listDataSiswa = new ArrayList<>();
        mApiInterface = APIClient.getClient().create(APIInterface.class);
        String NIS = getSharedPreferences("DATALOGIN",MODE_PRIVATE).getString("USERNAME",null);

        initViewMainUser();
        initLoadActionBar();
        initNavigationDrawerLeft();
        openLihatAkunFragment();

        onLoadProfileSiswa(NIS);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        menuPengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPengaturanFragment();
            }
        });
        menuLihatProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLihatAkunFragment();
            }
        });
        menuLihatNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLihatNilai();
            }
        });
    }

    private void initViewMainUser(){
        leftDrawerLayout = findViewById(R.id.layout__drawerLayout);
        btnCloseleftDrawer = findViewById(R.id.btn_leftDrawer_Close);
        namaUser = findViewById(R.id.text_namaSiswa);
        nisUser = findViewById(R.id.text__NIS);
        menuLihatProfil = findViewById(R.id.linearLayout__profilAkun);
        menuLihatNilai = findViewById(R.id.linearLayout__lihatNilai);
        menuPengaturan = findViewById(R.id.linearLayout__pengaturan);
        logout = findViewById(R.id.linearLayout__logout);
        pd = new ProgressDialog(this);
    }

    private void initLoadActionBar(){
        //TODO Toolbar Main Activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initNavigationDrawerLeft(){
        //TODO Open Navigation Drawer
        drawerToggle = new ActionBarDrawerToggle(this,leftDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        leftDrawerLayout.addDrawerListener(drawerToggle);

        //TODO Close Navigation Drawer
        btnCloseleftDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
            }
        });
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

    public void openPengaturanFragment(){
        pengaturanFragment = pengaturanFragment.newInstance(MainUserActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout__frameLayout, pengaturanFragment,"Pengaturan Fragment").commit();
        leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
    }

    public void openLihatAkunFragment(){
        lihatProfilSiswaFragment = lihatProfilSiswaFragment.newInstance(MainUserActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout__frameLayout, lihatProfilSiswaFragment,"Lihat Profil Fragment").commit();
        leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
    }

    public void openLihatNilai(){
        lihatNilaiFragment = lihatNilaiFragment.newInstance(MainUserActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout__frameLayout, lihatNilaiFragment,"Lihat Nilai").commit();
        leftDrawerLayout.closeDrawer(Gravity.LEFT,true);
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

    private void onLoadProfileSiswa(String NIS){
        pd.setMessage("Loading..");
        pd.setCancelable(false);
        Call<loadSiswa> loadSiswaProfileCall = mApiInterface.getDataSiswa(NIS);
        loadSiswaProfileCall.enqueue(new Callback<loadSiswa>() {
            @Override
            public void onResponse(Call<loadSiswa> call, Response<loadSiswa> response) {
                if (response.isSuccessful()){
                    if (response.body().getData().size() > 0){
                        for (int i=0; i<response.body().getData().size(); i++){
                            pd.cancel();
                            listDataSiswa.add(response.body().getData().get(i));

                            namaUser.setText(listDataSiswa.get(i).getNamaSiswa());
                            nisUser.setText(listDataSiswa.get(i).getNISSiswa());

                            getSharedPreferences("SISWA:DATADIRI", MODE_PRIVATE).edit().putString("NISSiswa",listDataSiswa.get(i).getNISSiswa()).apply();
                            getSharedPreferences("SISWA:DATADIRI", MODE_PRIVATE).edit().putString("NamaSiswa",listDataSiswa.get(i).getNamaSiswa()).apply();
                            getSharedPreferences("SISWA", MODE_PRIVATE).edit().putString("isLogin",listDataSiswa.get(i).getIsLogin()).apply();
                        }
                    }else {
                        pd.cancel();
                        Toast.makeText(MainUserActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    pd.cancel();
                    Toast.makeText(MainUserActivity.this, "Error 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loadSiswa> call, Throwable t) {
                pd.cancel();
                Toast.makeText(MainUserActivity.this, "Error: "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainUserActivity.this);

        builder.setTitle("Logout Akun?")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainUserActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
