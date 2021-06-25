package com.al.diuroutinemanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.al.diuroutinemanager.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(user!=null){

                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }else {

                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                    finish();
                }

               // startActivity(new Intent(getApplicationContext(),MainActivity.class));

            }
        },1500);

    }
}
