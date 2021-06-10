package com.example.chatbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private static int splash_timer = 3000;
    TextView powerByLine;
    //animation
    Animation sideanim, bottomanim;
    LottieAnimationView lottieAnimationView;
    TextView txt1, txt2, manu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        lottieAnimationView = findViewById(R.id.lottie);
        manu = findViewById(R.id.txtmanu);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.text2);
        //animation
        sideanim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomanim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        lottieAnimationView.setAnimation(sideanim);
        manu.setAnimation(bottomanim);
        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          Intent intent = new Intent(MainActivity.this, loginacitivity.class);
                                          startActivity(intent);
                                          finish();
                                      }
                                  },
                splash_timer);
    }
}

  

