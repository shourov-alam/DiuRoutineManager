package com.al.diuroutinemanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.al.diuroutinemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText email,pass;
    Button login,register;
    TextView forget_pass;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        mAuth=FirebaseAuth.getInstance();

        email=findViewById(R.id.emailET);
        pass=findViewById(R.id.passET);
        login=findViewById(R.id.loginBT);
        register=findViewById(R.id.registerBT);
        forget_pass=findViewById(R.id.forgetTV);
        progressBar=findViewById(R.id.progresssbar);
        progressBar.setVisibility(View.INVISIBLE);

        //getSupportActionBar().setTitle("Login");


        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ForgetPass.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);


                if(!email.getText().toString().equals("") && !pass.getText().toString().equals("")){

                    mAuth.signInWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                progressBar.setVisibility(View.INVISIBLE);


                                if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Please Verify your Email",Toast.LENGTH_LONG).show();
                                }


                           /* Query userQuery = myRef.orderByChild("mail").equalTo(email.getText().toString());

                            userQuery.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if(snapshot.exists()){

                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));



                                    }else {

                                        startActivity(new Intent(getApplicationContext(),Register.class));
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });  */

                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });

                }else {

                    Toast.makeText(getApplicationContext(),"Enter both email & password",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                }


            }
        });









    }
}
