package com.al.diuroutinemanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.al.diuroutinemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPass extends AppCompatActivity {

    FirebaseAuth mauth  ;
    Button bt;
    EditText ed;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        mauth=FirebaseAuth.getInstance();
        bt= findViewById(R.id.loginBT);
        ed=findViewById(R.id.emailET);
        progressBar=findViewById(R.id.progresssbar);
        progressBar.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle("Password Reset");


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(ed.getText().toString())){

                    Toast.makeText(getApplicationContext(),"Please Enter Your Email",Toast.LENGTH_LONG).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    mauth.sendPasswordResetEmail(ed.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "Check Your Email", Toast.LENGTH_LONG).show();
                            }
                            else{
                                progressBar.setVisibility(View.INVISIBLE);

                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }
            }
        });

    }
}



