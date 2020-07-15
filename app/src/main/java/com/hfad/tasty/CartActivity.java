package com.hfad.tasty;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.SnapHelper;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static com.hfad.tasty.MainActivity.kys;
import static com.hfad.tasty.MainActivity.lottieCart;
import static com.hfad.tasty.MainActivity.uniquecart;

public class CartActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener
{

    RecyclerView recyclerviewCart;
    static TextView cartPrice;
    public CurvedBottomNavigationView bottomNavigationView; //custom bottom navigation thingyy
    private LottieAnimationView breakfast,lunch;
    public static LottieAnimationView cartLottie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle("Your Cart");
        bottomNavigationView = findViewById(R.id.customlint);
        bottomNavigationView.inflateMenu(R.menu.menu_cartt);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //bottom navigation
        breakfast= findViewById(R.id.leftL);//
        cartLottie= findViewById(R.id.rightL); //small cart
        lunch =findViewById(R.id.lunch);  // small burger

        uniqify();//call for unique cart
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.super.onBackPressed();
            }
        });
        cartPrice = findViewById(R.id.cart_price);
        cartPrice.setText(Double.toString(grandTotal(uniquecart)));
        recyclerviewCart = findViewById(R.id.cart_recyclerview);
        LinearLayoutManager llsm = new Spiral(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCart.setLayoutManager(llsm);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerviewCart);
        recyclerviewCart.setNestedScrollingEnabled(false);
        recyclerviewCart.setAdapter(new CartAdapter(uniquecart, R.layout.recyclerview_cart, getApplicationContext()));

    }

   public static void uniqify() {

   /*    for (GeneralFood event : cart) {
           boolean isFound = false;
           // check if the event name exists in noRepeat
           for (GeneralFood e : uniquecart) {
               if (e.getTitle().equals(event.getTitle()) || (e.equals(event))) {
                   isFound = true;
                   break;
               }
           }
           if (!isFound) uniquecart.add(event);
       }*/
       for (int i=0;i<uniquecart.size();i++){
           for(int j=0;j<uniquecart.size();j++){
               if(i!=j&& uniquecart.get(i).getTitle().equals(uniquecart.get(j).getTitle())){
                   uniquecart.remove(j);
               }
           }
       }

}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.order_button){
            if(uniquecart.size()==0)
            {
                Toast.makeText(this,"Make an order First",Toast.LENGTH_SHORT).show();

            }
            else
                {
                    Intent intent = new Intent(CartActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
        }

        return super.onOptionsItemSelected(item);
    }

    public static int grandTotal(List< GeneralFood> cartFoods){

        int totalPrice = 0;
        for(int i = 0 ; i < cartFoods.size(); i++) {
            totalPrice += cartFoods.get(i).getPrice()*cartFoods.get(i).getQuantity();
        }
        return totalPrice;
    }
    public static List<String> nameData(List< GeneralFood> cartFoods){
        List<String> ss = new ArrayList<>();
        for(int i = 0 ; i < cartFoods.size(); i++) {
            ss.add((cartFoods.get(i).getQuantity())+" "+cartFoods.get(i).getTitle());
        }
        return ss;
    }

    public static void priceAdjust(){

        cartPrice.setText(Double.toString(grandTotal(uniquecart)));
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.page_1:
                breakfast.playAnimation();
                CartActivity.super.onBackPressed();
                break;
            case R.id.page_2:
                //animation
                if(kys){
                    lunch.setSpeed(-1f);
                }
                if(!kys){
                    lunch.setSpeed(1f);
                }
                kys=!kys;
                break;
            case R.id.action_customer:
                //order
                if(uniquecart.size()==0) {
                    Toast.makeText(this, "Select an order first", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent myJint = new Intent(CartActivity.this,OrderActivity.class);
                    CartActivity.this.startActivity(myJint);
                }

        }
        return true;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
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
    @Override
    public void onBackPressed()
    {
            super.onBackPressed();
    }

}
