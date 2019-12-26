package com.myapp.android.smartirrigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText username,date;
    private Button submitprofile;
    private DatabaseReference databaseReference;
    private String sowdate="";
    private String mobileno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username=findViewById(R.id.name);
        date=findViewById(R.id.date);
        mobileno=getIntent().getStringExtra("mobile");
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datepicker=new DatePickFragment();
                datepicker.show(getSupportFragmentManager(),"datepicker");
            }
        });
        submitprofile=findViewById(R.id.Continue);
        databaseReference= FirebaseDatabase.getInstance().getReference("Farmers");
        submitprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=username.getText().toString().trim();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(sowdate))
                {
                    username.setError("Name  and sow date is mandatory");
                    username.requestFocus();
                    date.requestFocus();
                }
                else {
                    addData(name);
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    protected void addData(String name)
    {

            String id=FirebaseAuth.getInstance().getCurrentUser().getUid();


            Farmer farmer=new Farmer(id,name,sowdate,mobileno);
            databaseReference.child(id).setValue(farmer);

    }

    @Override
    protected void onStart() {
        super.onStart();
        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Farmers").child(id);
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    Intent intent=new Intent(ProfileActivity.this,MainActivity.class);

                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Coming up..........");
                intent.setType("text/plain");
                startActivity(intent);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(ProfileActivity.this, LoginActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        sowdate= sdf.format(calendar.getTime());
        date.setText(sowdate);
    }
}
