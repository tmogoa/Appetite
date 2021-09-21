package com.loct.appetite.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loct.appetite.R;
import com.loct.appetite.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static String TAG = "HomeFragment";

    private FirebaseAuth fa;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        fa = FirebaseAuth.getInstance();
        authenticate();
        return root;
    }

    private void authenticate() {
        FirebaseUser currentUser = fa.getCurrentUser();
        if(currentUser == null){
            NavController navController = NavHostFragment.findNavController(HomeFragment.this);
            navController.navigate(HomeFragmentDirections.actionNavHomeToNavAuth());
        }
    }
}