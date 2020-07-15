package com.hfad.tasty;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.airbnb.lottie.LottieAnimationView;

import java.util.List;


import static com.hfad.tasty.CartActivity.grandTotal;
import static com.hfad.tasty.CartActivity.nameData;
import static com.hfad.tasty.MainActivity.uniquecart;
import static com.hfad.tasty.Scann.resid;
import static com.hfad.tasty.Scann.tableid;

public class OrderActivity extends AppCompatActivity {
    public String ll;
    public String hah;
    public String F;
    public int sf=0;
    TextView mOrderTotal;
    MediaPlayer mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.super.onBackPressed();
            }
        });

        mOrderTotal = findViewById(R.id.order_price_total);
        mOrderTotal.setText(Double.toString(grandTotal(uniquecart)));
        Intent h = getIntent();
        ll= resid;
        hah=tableid;
        final List<String> sa=nameData(uniquecart);
        StringBuilder sb= new StringBuilder();
        int d=0;
        sb.append("ResID ");sb.append(ll);sb.append(",TableID ");sb.append(hah);
        sb.append("\n");
        while (d<sa.size()-1){
            sb.append(sa.get(d));
            sb.append(",");
            d++;
        }
        sb.append(sa.get(d));
        F =sb.toString();
        animategButton();

    }

    @Override
    public void onDestroy() {
        // Stop the sound
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer = null;
        }

        super.onDestroy();
    }
    public void didTapgPlayButton(View view) {
        sf=1;
        animategButton();
    }
    void animategButton(){
        // Load the animation
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        double animationDuration = 2000;
        myAnim.setDuration((long)animationDuration);

        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(.20, 20);

        myAnim.setInterpolator(interpolator);

        // Animate the button
        LottieAnimationView button =  findViewById(R.id.email_button);
        button.startAnimation(myAnim);
        if(sf>=1){
            playSound();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"Khana@khana"});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "myOrder");
                    intent.putExtra(Intent.EXTRA_TEXT,F);
                    startActivity(intent);
                }
            }, 1200);
        }


    }
    void playSound() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
        }
        mPlayer = MediaPlayer.create(OrderActivity.this, R.raw.watersplash);
        mPlayer.start();
    }
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
