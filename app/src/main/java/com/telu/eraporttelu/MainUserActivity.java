package com.telu.eraporttelu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

public class MainUserActivity extends AppCompatActivity {

    private static final String TAG = "MainUserActivity";

    //TODO VAR LEFT DRAWER
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout leftDrawerLayout;
    private Button btnCloseleftDrawer;
    private TextView namaUser;
    private TextView nisUser;
    private LinearLayout menuLihatProfil;
    private LinearLayout menuLihatNilai;
    private LinearLayout menuPengaturan;
    private LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        initViewMainUser();
        initLoadActionBar();
        initNavigationDrawerLeft();
        logout();
    }

    private void initViewMainUser(){
        leftDrawerLayout = findViewById(R.id.layout__drawerLayout);
        btnCloseleftDrawer = findViewById(R.id.btn_leftDrawer_Close);
        namaUser = findViewById(R.id.text_namaSiswa);
        nisUser = findViewById(R.id.text_profil_nisSiswa);
        menuLihatProfil = findViewById(R.id.linearLayout__profilAkun);
        menuLihatNilai = findViewById(R.id.linearLayout__lihatNilai);
        menuPengaturan = findViewById(R.id.linearLayout__pengaturan);
        logout = findViewById(R.id.linearLayout__logout);
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

    private void logout(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainUserActivity.this);

                builder.setTitle("Logout Akun?")
                        .setMessage("Apakah anda yakin ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainUserActivity.this, "Testing Yes", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Tidak", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
