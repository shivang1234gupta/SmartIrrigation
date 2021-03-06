package com.myapp.android.smartirrigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private DatabaseReference databaseReference;
    private String currdate,sowdate,username,mobileno;
    private TextView person,days;
    private Button field,pond;
    private Farmer f;
    private static final String CHANNEL_NAME="Smart Irrigation";
    private static final String CHANNEL_ID="Smart Irrigation";
    private static final String CHANNEL_DESC="Smart Irrigation notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference= FirebaseDatabase.getInstance().getReference("Farmers");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        currdate = sdf.format(c.getTime());
        person=findViewById(R.id.user);
        days=findViewById(R.id.days);
        field=findViewById(R.id.field);
        pond=findViewById(R.id.pond);
        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,FieldActivity.class);
                startActivity(intent);
            }
        });
        pond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PondActivity.class);
                startActivity(intent);
            }
        });


        /*Using FCM for activating notification*/
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("updates");
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference= FirebaseDatabase.getInstance().getReference("Farmers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Farmer f=new Farmer();
                f=dataSnapshot.child(FirebaseAuth.getInstance().getUid()).getValue(Farmer.class);
                sowdate=f.getFarmerdate();
                username=f.getFarmername();
                mobileno=f.getMobileno();
                /*FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful())
                        {
                            String token=task.getResult().getToken();
                            f.setToken(token);
                        }
                    }
                });*/
                person.setText(username);
                String s=sowdate+"  "+currdate;
                days.setText(s);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Failed to fetch data",Toast.LENGTH_LONG).show();
            }
        });
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
            case R.id.sowdate:
                DialogFragment datepicker=new DatePickFragment();
                datepicker.show(getSupportFragmentManager(),"datepicker");
                String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference;
                databaseReference=FirebaseDatabase.getInstance().getReference().child("Farmers").child(id);
                f.setFarmerdate(sowdate);
                databaseReference.setValue(f);
                sowdate=f.getFarmerdate();
                username=f.getFarmername();
                mobileno=f.getMobileno();
                person.setText(username);
                String s=sowdate+"  "+currdate;
                days.setText(s);
                return true;
            case R.id.delete:
                String id1=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference databaseReference1;
                databaseReference1=FirebaseDatabase.getInstance().getReference().child("Farmers").child(id1);
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this,"account removed",Toast.LENGTH_LONG).show();
                Intent intent2=new Intent(MainActivity.this,LoginActivity.class);
                intent2.putExtra("main","123");
                startActivity(intent2);
                databaseReference1.removeValue();
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
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
    }
}
