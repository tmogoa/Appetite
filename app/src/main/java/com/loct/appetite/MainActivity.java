package com.loct.appetite;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loct.appetite.databinding.ActivityMainBinding;
import com.loct.appetite.ui.AuthFragment;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private TextView emailtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//int firebase auth

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //int firebase auth
        firebaseAuth=FirebaseAuth.getInstance();
        checkuser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

       // emailtv=(TextView) findViewById(R.id.emailtv);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View innerview =  navigationView.getHeaderView(0);
       // emailtext= (TextView) LayoutInflater.from(this).inflate(R.layout.nav_header_main,null);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void checkuser() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser ==null){
            //user not logged in
//            Intent intent = new Intent(MainActivity.this, AuthFragment.class);
//            startActivity(intent);
            Fragment mFragment = null;
            mFragment = new AuthFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, mFragment).commit();
        }
      else{
           String email=firebaseUser.getEmail();
           //set email
            //emailtext.setText(email);



       }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void openDrawer(View view) {
        if(drawer.isOpen()){
            drawer.closeDrawer(Gravity.LEFT);
        }else{
            drawer.openDrawer(Gravity.LEFT);
        }
    }

    public void goBack(View view) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}