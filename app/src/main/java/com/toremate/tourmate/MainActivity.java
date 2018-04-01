package com.toremate.tourmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FrameLayout frameLayout;
   FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    public static final int GELERY_PIC = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
         currentUser = firebaseAuth.getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialization();
        homeFragmentCall();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void homeFragmentCall() {
        Fragment fragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.layoutframe, fragment);
        ft.commit();
    }

    private void initialization() {
        frameLayout = (FrameLayout) findViewById(R.id.layoutframe);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.AccountSetting:

                break;
            case R.id.AboutApp:

                break;
            case R.id.Share:
                Intent myintent = new Intent(Intent.ACTION_SEND);
                myintent.setType("text/plain");
                String sharebody = "https://www.facebook.com/rezaulkarim21";
                String sharetsub = "your sub here";
                myintent.putExtra(Intent.EXTRA_SUBJECT, sharetsub);
                myintent.putExtra(Intent.EXTRA_TEXT, sharebody);
                startActivity(Intent.createChooser(myintent, "share using"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.Home) {
            fragment = new HomeFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.layoutframe, fragment);
            ft.commit();

        } else if (id == R.id.TravelEvent) {
            Intent intent = new Intent(MainActivity.this, TravelEvent.class);
            startActivity(intent);

        } else if (id == R.id.ShowEvent) {
            fragment = new ViewAllEvent();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.layoutframe, fragment);
            ft.commit();

        }   else if (id == R.id.Import) {
          Intent intent=new Intent(MainActivity.this,Camera.class);
            startActivity(intent);
            } else if (id==R.id.Gallery){

        }
            else if (id == R.id.About) {

            } else if (id == R.id.LogOut) {
            firebaseAuth.signOut();
            checkCurrentUser();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    public void onStart() {
        super.onStart();
        if (currentUser == null) {
            checkCurrentUser();
        }
    }
    private void checkCurrentUser() {
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}