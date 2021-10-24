package com.hfad.tasty;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import androidx.core.view.MenuItemCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.Menu;

import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener
{
    //navigation drawer Setup
    CustomView customView;
    LinearLayout headercontent;
    public CurvedBottomNavigationView bottomNavigationView; //custom bottom navigation thingyy
    DrawerLayout drawer;
    //recyclerview Setup
    public static RecyclerView recyclerViewHorizontal,recyclerViewVertical;
    public static TextView tv;//cart number
    public static List<GeneralFood> uniquecart = new ArrayList<>(); //unique carrt array of object
    public static VerticalAdapter mAdapter; //adapter to rotate the views with setthingy
    ArrayList<String> categories = new ArrayList<String>(); // horizontal categories buttons on top
    List<GeneralFood> popularFoods;
    public Toolbar toolbar;
    public static LottieAnimationView view; //cartview lottie of TOOLBAR
    private LottieAnimationView breakfast,lunch,netfail;
    public static LottieAnimationView lottieCart; //bottom navigation lottie

    //Night mode Setup
    TextView n1,n2; // night mode text color
    ConstraintLayout con1;   //night mode Panel color
    public  static boolean kys,nt,ad,nf=false; //night mode logic bolean

    // camera string id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar setup
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // +  Night mode setup
        n1=findViewById(R.id.texttView);
        n2=findViewById(R.id.teck);
        con1=findViewById(R.id.night);
        toolbar.setTitleTextColor(Color.BLACK);

        //Categories,  working with recyclerview a buttons
        categories.add(0,"Appetizers");
        categories.add(1,"BreakFast");
        categories.add(2,"Lunch");
        categories.add(3,"Dinner");
        categories.add(4,"Italian");

        //bottom Navigation setup
        bottomNavigationView = findViewById(R.id.asas);
        bottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu);
        bottomNavigationView.setSelectedItemId(R.id.page_2);

        //set event button on bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //lottie animation FOR bottom naviagation view holder
        breakfast= findViewById(R.id.leftL);//
        lottieCart= findViewById(R.id.rightL); //small cart
        lunch =findViewById(R.id.lunch);  // small burger

        //night mode logic
        if(ad&&nf)// if night mode button is pressed and activity returns on panel
        {
            n2.setTextColor(Color.WHITE);
            n1.setTextColor(Color.WHITE);
            con1.setBackgroundColor(Color.BLACK);
            toolbar.setTitleTextColor(Color.WHITE);
        }

        //recyclerview setup
        recyclerViewVertical = findViewById(R.id.vertical_recycler);
        recyclerViewHorizontal = findViewById(R.id.horizontal_recyclerview);
        LinearLayoutManager llm = new CenterZoom(this, CenterZoom.Gravity.START, CenterZoom.Orientation.HORIZONTAL, 3230, 2, false);
        LinearLayoutManager llsm = new CenterZoom(this, CenterZoom.Gravity.START, CenterZoom.Orientation.VERTICAL, 3230, 2, false);

        SnapHelper sa = new LinearSnapHelper();//centerview scroll
        SnapHelper saa = new LinearSnapHelper();
        netfail = findViewById(R.id.sasasa);

        sa.attachToRecyclerView(recyclerViewVertical);// attach Snap helper
        saa.attachToRecyclerView(recyclerViewHorizontal);

        recyclerViewHorizontal.setLayoutManager(llm);// attach to Rotational layout
        recyclerViewVertical.setLayoutManager(llsm);

        //get client interface
         RetrofitInterface retrofitService = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<Food> call = retrofitService.getFoods();
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                popularFoods = response.body().getRegularFood();
                recyclerViewHorizontal.setAdapter(new HorizontalAdapter(categories, R.layout.recyclerview_horizontal, MainActivity.this));
                mAdapter =new VerticalAdapter(popularFoods,R.layout.recyclerview_vertical,getApplicationContext());
                recyclerViewVertical.setAdapter(mAdapter);
                nf=true;
            }

            @Override
            public void onFailure(final Call<Food> call, Throwable t) {
                netfail.setVisibility(View.VISIBLE);
                nf=false;
                uniquecart.clear();
                netfail.setClickable(true);

                netfail.playAnimation();
                netfail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        RetrofitInterface retrofitService = RetrofitClient.getClient().create(RetrofitInterface.class);
                        Call<Food> call = retrofitService.getFoods();
                        call.enqueue(new Callback<Food>() {
                            @Override
                            public void onResponse(Call<Food> call, Response<Food> response) {
                                popularFoods = response.body().getRegularFood();
                                recyclerViewHorizontal.setAdapter(new HorizontalAdapter(categories, R.layout.recyclerview_horizontal, MainActivity.this));
                                mAdapter =new VerticalAdapter(popularFoods,R.layout.recyclerview_vertical,getApplicationContext());
                                recyclerViewVertical.setAdapter(mAdapter);
                                netfail.setVisibility(View.INVISIBLE);
                                netfail.setClickable(false);
                                nf=true;
                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                    drawer.openDrawer(GravityCompat.START);
                                    drawer.closeDrawer(GravityCompat.START);
                            }

                            @Override
                            public void onFailure(final Call<Food> call, Throwable t) {
                                Toast.makeText(MainActivity.this,"connect to net",Toast.LENGTH_LONG).show();
                            nf=false;
                            }});


                        //
                    }
                });

            }
        });

        //drawer SEtup
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        headercontent = (LinearLayout) header.findViewById(R.id.headercontent);
        // headercontent.animate().y(0);
        headercontent.setAlpha(0);
        customView = (CustomView) header.findViewById(R.id.customview);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
//          Passing slideOffset value to rotateRect() method for rotation of rectangles in custom view
                customView.rotateRect(slideOffset);
                headercontent.animate().alpha(slideOffset);
                headercontent.animate().yBy(slideOffset);
            }


            @Override
            public void onDrawerOpened(View drawerView) {

                headercontent.animate().y(150);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                headercontent.setAlpha(0);
                headercontent.animate().y(0);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        cartUpdate();
        MenuItem item = menu.findItem(R.id.action_addcart);
        MenuItemCompat.setActionView(item, R.layout.cart_count_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        tv = notifCount.findViewById(R.id.hotlist_hot);
          view = notifCount.findViewById(R.id.hotlist_bell);

        cartUpdate();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uniquecart.size()==0){
                    Toast.makeText(MainActivity.this,"make order",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent myIntent = new Intent(MainActivity.this, CartActivity.class);
                    view.playAnimation();
                    startActivity(myIntent);
                }

            }});

        return true;
    }

    public static void cartUpdate() {
        if (tv != null && uniquecart != null)
            tv.setText(Integer.toString(uniquecart.size()));
    }

    @Override
    protected void onResume() {
        invalidateOptionsMenu();
        super.onResume();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan) {
            Intent myIntent = new Intent(MainActivity.this, Mainn.class);
            MainActivity.this.startActivity(myIntent);
        } else if (id == R.id.nav_cart) {
            if(uniquecart.size()==0) {
                Toast.makeText(this, "Add Food First", Toast.LENGTH_LONG).show();
            }else {
                Intent myIntent = new Intent(MainActivity.this, CartActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        } else if (id == R.id.nav_orders) {
            if(uniquecart.size()==0) {
                Toast.makeText(this, "Make Order First", Toast.LENGTH_LONG).show();
            }
            else{
                Intent myIntent = new Intent(MainActivity.this,OrderActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        } else if (id == R.id.nav_history) {
            Toast.makeText(this, "yet to be made", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_info) {
            Toast.makeText(this, " Restaurant description demo yet to be made", Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_night) {

            if(!ad&&nf) {
                n2.setTextColor(Color.WHITE);
                n1.setTextColor(Color.WHITE);
                con1.setBackgroundColor(Color.BLACK);
                mAdapter.notifyDataSetChanged();
                toolbar.setTitleTextColor(Color.WHITE);
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.getMenu().findItem(id).setTitle("Light mode");



            }
            if(ad&&nf){
                n2.setTextColor(Color.BLACK);
                n1.setTextColor(Color.BLACK);
                con1.setBackgroundColor(Color.WHITE);
                mAdapter.notifyDataSetChanged();
                toolbar.setTitleTextColor(Color.BLACK);
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.getMenu().findItem(id).setTitle("Night mode");


            }
            ad=!ad;
            nt=!nt;

        }
        switch (id) {
            case R.id.page_1:
                breakfast.playAnimation();
                //animation
                recyclerViewVertical.smoothScrollToPosition(0);

                break;
            case R.id.page_2:
                //animation
                if(kys){
                    lunch.setSpeed(1f);
                    netfail.setSpeed(1f);
                }
                if(!kys){
                    lunch.setSpeed(-1f);
                    netfail.setSpeed((-1f));
                }
                kys=!kys;
                recyclerViewVertical.smoothScrollToPosition(9);
                break;
            case R.id.action_customer:
                //order
                if(uniquecart.size()==0) {
                    Toast.makeText(this, "Select an order first", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent myIntent = new Intent(MainActivity.this,CartActivity.class);
                    MainActivity.this.startActivity(myIntent);
                }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
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
}