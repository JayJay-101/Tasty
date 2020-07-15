package com.hfad.tasty;

import android.content.Intent;

import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gms.phenotype.ExperimentTokens;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.hfad.tasty.CartActivity.uniqify;
import static com.hfad.tasty.MainActivity.cartUpdate;
import static com.hfad.tasty.MainActivity.mAdapter;
import static com.hfad.tasty.MainActivity.tv;
import static com.hfad.tasty.MainActivity.uniquecart;


public class Details extends AppCompatActivity {

    public static List<GeneralFood> mFood = new ArrayList<>();

    TextView foodTitle, foodPrice, foodCalories, foodDescription, foodFat, foodSodium, foodProtein;
    KenBurnsView foodImage;
    RatingBar ratingBar;
    ImageButton foodDetailsPlus;
    int position;
    LottieAnimationView views;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setTitle("Details");
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//TOOLBAR CLICK LOTTIE
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.super.onBackPressed();
                tv.setText(Integer.toString(uniquecart.size()));
            }
        });
// REFERENCING VIEWS ON PLACEHOLDERS
        foodTitle = findViewById(R.id.food_title);
        foodPrice = findViewById(R.id.food_price);
        foodCalories = findViewById(R.id.food_calories);
        foodDescription = findViewById(R.id.food_description);
        foodFat = findViewById(R.id.food_fat);
        foodSodium = findViewById(R.id.food_sodium);
        foodProtein = findViewById(R.id.food_protein);
        foodImage = findViewById(R.id.food_image);
        foodDetailsPlus = findViewById(R.id.regular_food_plus_details);
        ratingBar = findViewById(R.id.food_rating);
     // PUTING JSON DATA ON VIEWS ACCORDINGLY
        Intent h = getIntent();
        position= h.getIntExtra("position",position);

        ratingBar.setRating(mFood.get(0).getRating());
        foodTitle.setText(mFood.get(0).getTitle());
        foodPrice.setText(Double.toString(mFood.get(0).getPrice()));
        foodCalories.setText(Integer.toString(mFood.get(0).getCalory()));
        foodDescription.setText(mFood.get(0).getDescription());
        Picasso.get().load(mFood.get(0).getImage()).fit().into(foodImage);
        foodFat.setText(Integer.toString(mFood.get(0).getFat()));
        foodSodium.setText(Integer.toString(mFood.get(0).getSodium()));
        foodProtein.setText(Integer.toString(mFood.get(0).getProtein()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuu, menu);

        MenuItem item = menu.findItem(R.id.order_button);
        MenuItemCompat.setActionView(item, R.layout.cart_count_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
         views = notifCount.findViewById(R.id.hotlist_bell);

        tv = notifCount.findViewById(R.id.hotlist_hot);
        cartUpdate();

        views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Details.this, CartActivity.class);
                startActivity(myIntent);


            }});

        return true;
    }
    public void onClicky(View view){
        final Animation myAnim = AnimationUtils.loadAnimation(this,R.anim.bounce);
        double animationDuration = 2000;
        myAnim.setDuration((long)animationDuration);
        views.setMinAndMaxFrame(30,89);
        views.playAnimation();
        // Use custom animation interpolator to achieve the bounce effect
        MyBounceInterpolator interpolator = new MyBounceInterpolator(.20, 20);
        myAnim.setInterpolator(interpolator);
        // Animate the button
        foodDetailsPlus.startAnimation(myAnim);
        uniquecart.add(mFood.get(0));
        mFood.get(0).setQuantity(mFood.get(0).getQuantity()+1);
        uniqify();
        mAdapter.notifyDataSetChanged();
        tv.setText(Integer.toString(uniquecart.size()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFood.removeAll(mFood);
    }
}
