package com.al.diuroutinemanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.al.diuroutinemanager.Activity.LogIn;
import com.al.diuroutinemanager.Model.TeacherModel;
import com.al.diuroutinemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherProfile extends AppCompatActivity {

    EditText mail,pass,name,initial,mobile;
    Button save;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        mail=findViewById(R.id.t_mail);
        pass=findViewById(R.id.t_pass);
        name=findViewById(R.id.t_name);
        initial=findViewById(R.id.t_initial);
        mobile=findViewById(R.id.t_mobile);
        save=findViewById(R.id.t_save);
        progressBar=findViewById(R.id.progresssbar);
        progressBar.setVisibility(View.INVISIBLE);

        getSupportActionBar().setTitle("Registration (Teacher)");

        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Users");



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mail.getText().toString().equals("") && !pass.getText().toString().equals("")&&
                !name.getText().toString().equals("") && !initial.getText().toString().equals("")&&!mobile.getText().toString().equals("")){

                    if(pass.getText().toString().length() < 6){

                        pass.setError("minimum 6 character required");
                    }else {
                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.createUserWithEmailAndPassword(mail.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Toast.makeText(getApplicationContext(), "Please check email & verify ", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), LogIn.class));
                                                finish();

                                            } else {

                                                // If sign in fails, display a message to the user.
                                                Toast.makeText(getApplicationContext(),
                                                        "Error : " + task.getException(), Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                                    create_profile();
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.INVISIBLE);

                            }
                        });
                    }


                }else {

                    Toast.makeText(getApplicationContext(),"Fill up all the data properly",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    void create_profile(){

        String uid= firebaseAuth.getUid();


       TeacherModel teacherModel =new TeacherModel(name.getText().toString(),mobile.getText().toString(),"teacher",mail.getText().toString(),uid,
               pass.getText().toString(),initial.getText().toString().toUpperCase());

        databaseReference.child(uid).setValue(teacherModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getApplicationContext(),"Profile created",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),LogIn.class));
                finish();

            }
        });
    }



}
