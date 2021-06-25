package com.al.diuroutinemanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.al.diuroutinemanager.R;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("About Developers");
        }

    }
}
