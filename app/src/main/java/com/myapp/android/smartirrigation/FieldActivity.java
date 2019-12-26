package com.myapp.android.smartirrigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FieldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
