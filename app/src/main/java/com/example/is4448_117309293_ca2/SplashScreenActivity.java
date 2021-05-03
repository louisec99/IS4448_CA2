package com.example.is4448_117309293_ca2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;
//https://www.youtube.com/watch?v=gt1WYT0Qpfk
//https://www.youtube.com/watch?v=Q0gRqbtFLcw&t=86s
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        EasySplashScreen config = new EasySplashScreen(SplashScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#527096"))
//                .withHeaderText("Header")
//                .withFooterText("Footer")
//                .withBeforeLogoText("Before Logo Text")
//                .withAfterLogoText("After Logo Text")
                .withLogo(R.drawable.logo5);

//        config.getHeaderTextView().setTextColor(Color.WHITE);
//        config.getFooterTextView().setTextColor(Color.WHITE);
//        config.getBeforeLogoTextView().setTextColor(Color.WHITE);
//        config.getAfterLogoTextView().setTextColor(Color.WHITE);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}