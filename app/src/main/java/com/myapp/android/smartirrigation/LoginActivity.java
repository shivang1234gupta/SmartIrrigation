package com.myapp.android.smartirrigation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText mobile;
    private Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobile=findViewById(R.id.mobile);
        mobile.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i== EditorInfo.IME_ACTION_DONE)
                {
                    Submit.performClick();
                    return true;
                }
                else
                    return false;
            }
        });
        Submit=findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number=mobile.getText().toString().trim();
                if(number.isEmpty()||number.length()<10)
                {
                    mobile.setError("enter a valid number");
                    mobile.requestFocus();
                    return;
                }
                else {
                    Intent intent=new Intent(LoginActivity.this,VerifyPhoneActivity.class);
                    number="+91"+number;
                    intent.putExtra("mobile",number);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent i=getIntent();
        if(i.getStringExtra("main")==null)
        {
        if (FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }}
    }
}
