package com.hfad.digitalnomads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {    //класс предназначенный для стартовой картинки, перед запуском программы
    private final int SPLASH_SCREEN = 5000; //5 секунд  //переменная отвечающая за продолжительность показа времени нашей стартовой картинки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        },SPLASH_SCREEN);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
