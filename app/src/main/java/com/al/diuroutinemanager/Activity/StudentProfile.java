package com.al.diuroutinemanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.al.diuroutinemanager.Activity.LogIn;
import com.al.diuroutinemanager.Model.StudentModel;
import com.al.diuroutinemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentProfile extends AppCompatActivity {

    EditText name,id,mail,pass;
    Spinner sp_section,sp_shift,sp_level,sp_term;
    Button save;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        progressBar=findViewById(R.id.progresssbar);
        progressBar.setVisibility(View.INVISIBLE
        );

        getSupportActionBar().setTitle("Registration (Student)");

        databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        firebaseAuth=FirebaseAuth.getInstance();


        name=findViewById(R.id.st_name);
        id=findViewById(R.id.st_id);
        mail=findViewById(R.id.st_mail);
        pass=findViewById(R.id.st_pass);

        save=findViewById(R.id.st_save);

        sp_section=findViewById(R.id.section);
        sp_shift=findViewById(R.id.shift);
        sp_level=findViewById(R.id.level);
        sp_term=findViewById(R.id.term);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(!name.getText().toString().equals("") && !id.getText().toString().equals("") &&
                !mail.getText().toString().equals("") && !pass.getText().toString().equals(""))
                {

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

                    Toast.makeText(getApplicationContext(),"Fill up all the fields properly",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    void create_profile(){
        String lv= level(sp_level.getSelectedItemPosition());
        String tr= term(sp_term.getSelectedItemPosition());
        String sh= shift(sp_shift.getSelectedItemPosition());
        String se= section(sp_section.getSelectedItemPosition());

        String uid= firebaseAuth.getUid();

        String mch=sh+lv+tr+se;

        StudentModel studentModel=new StudentModel(name.getText().toString(),id.getText().toString()
                ,"student",uid,mail.getText().toString(),pass.getText().toString(),se,lv,tr,sh,mch);

        databaseReference.child(uid).setValue(studentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"Profile created",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),LogIn.class));
            }
        });
    }

   String level(int pos){

        String result;
        if(pos==0){
            result="LEVEL-1";
        }else if(pos==1){
            result="LEVEL-2";
        }else if(pos==2){
            result="LEVEL-3";
        }else{
            result="LEVEL-4";
        }

      return result;
    }

    String term(int pos){

        String result;

        if(pos==0){
            result="TERM-1";
        }else if(pos==1){
            result="TERM-2";
        }else{
            result="TERM-3";
        }

        return result;
    }

    String shift(int pos){
        String result;
        if(pos==0){
            result="Day";
        }else{
            result="Evening";
        }

        return result;
    }

    String section(int pos){

        String result;
        if(pos==0){
            result="A";
        }else{
            result="B";
        }

        return result;
    }
}
