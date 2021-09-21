package com.loct.appetite;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        backBtn = findViewById(R.id.back_btn);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(navController.getCurrentDestination().getId() != R.id.nav_home){
                backBtn.setVisibility(View.VISIBLE);
            }else{
                backBtn.setVisibility(View.INVISIBLE);
            }
        });
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
}