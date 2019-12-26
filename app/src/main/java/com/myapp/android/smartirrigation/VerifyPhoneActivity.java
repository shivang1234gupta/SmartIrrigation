package com.myapp.android.smartirrigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private EditText otp;
    private Button submitotp;
    private String VerificationId;
    private FirebaseAuth mauth;
    private ProgressBar progressBar;
    private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        otp=findViewById(R.id.otp);
        submitotp=findViewById(R.id.submitotp);
        progressBar=findViewById(R.id.progress);

        number=getIntent().getStringExtra("mobile");

        mauth=FirebaseAuth.getInstance();

        sendVerificationCode(number);

        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=otp.getText().toString().trim();

                if(code.isEmpty()||code.length()<6)
                {
                    otp.setError("Enter valid otp");
                    otp.requestFocus();
                    return;
                }

                //progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        });
    }

    private void verifyCode(String code)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,code);
        signInWithcredential(credential);
    }

    private void signInWithcredential(PhoneAuthCredential credential) {

        mauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    Intent intent=new Intent(VerifyPhoneActivity.this,ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("mobile",number);
                    startActivity(intent);
                }
                else
                    Toast.makeText(VerifyPhoneActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendVerificationCode(String number)
    {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VerificationId=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code != null) {
                verifyCode(code);
            }
            //else

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

        }
    };
}
