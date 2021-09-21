package com.loct.appetite;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

public class SignOutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();
        requireActivity().finish();
        //NavHostFragment.findNavController(this).navigate(SignOutFragmentDirections.actionNavSignOutToNavHome());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_out, container, false);
    }
}