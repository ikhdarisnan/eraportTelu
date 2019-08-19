package com.telu.eraporttelu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ImageView imgScreen = findViewById(R.id.img_splash_imgSplash);
        Animation splashanim = AnimationUtils.loadAnimation(this,R.anim.animation_splash);

        imgScreen.startAnimation(splashanim);
        timeSplash();
    }

    private void timeSplash(){

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    startActivity(new Intent(SplashscreenActivity.this,LoginActivity.class));
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {


                }
            }
        };
        timer.start();
    }
}
