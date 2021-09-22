package com.loct.appetite;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loct.appetite.ui.HomeFragmentDirections;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private View headerLayout;

    private FirebaseAuth fa;

    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");

        backBtn = findViewById(R.id.back_btn);

        fa = FirebaseAuth.getInstance();

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerLayout = navigationView.getHeaderView(0);

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

            if(navController.getCurrentDestination().getId() == R.id.nav_auth){
                toolbar.setVisibility(View.GONE);
            }else{
                toolbar.setVisibility(View.VISIBLE);
            }

            if(navController.getCurrentDestination().getId() == R.id.nav_auth){
                setUserDetailsInNavHeader();
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

    private void authenticate() {
        FirebaseUser currentUser = fa.getCurrentUser();
        if(currentUser == null){
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(HomeFragmentDirections.actionNavHomeToNavAuth());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //authenticate();
        setUserDetailsInNavHeader();
    }

    private void hideToolbar(){
        toolbar.setVisibility(View.GONE);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "options selectec");


        drawer.closeDrawer(Gravity.LEFT);
        return true;
    }

    private void setUserDetailsInNavHeader(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            ((TextView) headerLayout.findViewById(R.id.user_name_display)).setText(firebaseUser.getDisplayName());
            ((TextView) headerLayout.findViewById(R.id.email_display)).setText(firebaseUser.getEmail());
        }

    }
}