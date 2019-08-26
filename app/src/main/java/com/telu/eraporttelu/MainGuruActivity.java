package com.telu.eraporttelu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.telu.eraporttelu.fragment.inputNilaiFragment;
import com.telu.eraporttelu.fragment.lihatProfilGuruFragment;
import com.telu.eraporttelu.fragment.lihatProfilSiswaFragment;
import com.telu.eraporttelu.fragment.pengaturanFragment;

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

    lihatProfilGuruFragment lihatProfilGuruFragment;
    pengaturanFragment pengaturanFragment;
    inputNilaiFragment inputNilaiFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guru);

        initViewMainUser();
        initLoadActionBar();
        initNavigationDrawerLeft();

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbarGuru);
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

    @Override
    protected void onStart() {
        super.onStart();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGuruActivity.this);

        builder.setTitle("Logout Akun?")
                .setMessage("Apakah anda yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainGuruActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
