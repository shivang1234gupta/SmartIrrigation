package com.myapp.android.smartirrigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
