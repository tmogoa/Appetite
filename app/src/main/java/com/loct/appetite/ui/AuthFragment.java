package com.loct.appetite.ui;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.loct.appetite.MainActivity;
import com.loct.appetite.R;
import com.loct.appetite.databinding.ActivityMainBinding;

public class AuthFragment extends Fragment {
    private ActivityMainBinding binding;
    private static final int RC_SIGN_IN=100;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding=ActivityMainBinding.inflate(inflater,container,false);
View v=binding.getRoot();


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_auth, container, false);
        ImageButton signin=(ImageButton) view.findViewById(R.id.googlelogin);

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //return v;
googleSignInClient=GoogleSignIn.getClient(getActivity(),googleSignInOptions);
firebaseAuth=FirebaseAuth.getInstance();
//
        progressBar=view.findViewById(R.id.progressbar);
signin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);

        //begin google signn=in
        Log.d(TAG,"onclick begin google signin");
        Intent intent=googleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }
});
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Log.d(TAG,"OnActivityResult:Google Signin intent result");
            Task<GoogleSignInAccount> accountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //google signin acccount now auth in firebase
                GoogleSignInAccount account=accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);

            }catch (Exception e){
                //failed google signin
                Log.d(TAG,"onActivityResult:"
                +e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG,"firebaseAuthWithGoogleAccount:begin firebase auth with google account");
        AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
//login success
                Log.d(TAG,"onSuccess Logged in");
//get Logged in user
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                //get user info
                String uid=firebaseUser.getUid();
                String email=firebaseUser.getEmail();

                Log.d(TAG,"OnSuccesEmail"+email);
                Log.d(TAG,"OnSuccesEmail"+uid);

                //check if user is new or existing
                if(authResult.getAdditionalUserInfo().isNewUser())
                {
                    Log.d(TAG,"OnSuccess:Account Created"+email);
                    Toast.makeText(getActivity(),"Account Created"+email,Toast.LENGTH_SHORT).show();
                }
                else{
                    //existing user
                    Log.d(TAG,"on success: Existing user...\n"+email);
                    Toast.makeText(getActivity(),"Account Created"+email,Toast.LENGTH_SHORT).show();

                }
startActivity(new Intent(getActivity(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//on Failure logged in
                Log.d(TAG,"onFailure Logged in");


            }
        });
    }
}