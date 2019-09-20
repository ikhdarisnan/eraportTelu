package com.telu.eraporttelu;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashscreenActivity extends AppCompatActivity {

    String isLoginStat,isStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        isLoginStat = getSharedPreferences("DATALOGIN",MODE_PRIVATE).getString("isLOGIN","0");
        isStatus = getSharedPreferences("DATALOGIN",MODE_PRIVATE).getString("STATUS","0");
        ImageView imgScreen = findViewById(R.id.img_splash_imgSplash);
        Animation splashanim = AnimationUtils.loadAnimation(this,R.anim.animation_splash);

        imgScreen.startAnimation(splashanim);
        timeSplash();
    }

    private void timeSplash(){

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(1300);
                    if (isLoginStat.equals("1") && isStatus.equals("1")){
                        startActivity(new Intent(SplashscreenActivity.this, MainGuruActivity.class));
                        finish();
                    }else if (isLoginStat.equals("1") && isStatus.equals("2")){
                        startActivity(new Intent(SplashscreenActivity.this, MainUserActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashscreenActivity.this,LoginActivity.class));
                        finish();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {


                }
            }
        };
        timer.start();
    }
}
