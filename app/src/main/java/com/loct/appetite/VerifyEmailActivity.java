package com.loct.appetite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmailActivity extends AppCompatActivity {
    private TextView verifytext;
    private Button verifybtn;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        verifytext=findViewById(R.id.verifyText);
        verifybtn=findViewById(R.id.cirVerifyButton);
        auth=FirebaseAuth.getInstance();
        if(!auth.getCurrentUser().isEmailVerified()){
            verifybtn.setVisibility(View.VISIBLE);
            verifytext.setVisibility(View.VISIBLE);

        }
        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(VerifyEmailActivity.this,"Verification email sent",Toast.LENGTH_SHORT).show();
                        verifybtn.setVisibility(View.GONE);
                        verifytext.setVisibility(View.GONE);
                        startActivity(new Intent(VerifyEmailActivity.this,LoginActivity.class ));

                    }
                });

            }
        });
    }
}