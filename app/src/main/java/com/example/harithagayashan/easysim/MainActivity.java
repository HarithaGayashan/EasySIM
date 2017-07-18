package com.example.harithagayashan.easysim;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.pm.PackageManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.Manifest;


public class MainActivity extends AppCompatActivity {
    ImageButton btnMobitel;
    EditText rechargeNumber;
    Button Recharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMobitel = (ImageButton)findViewById(R.id.imageButtonMobitel);
        rechargeNumber = (EditText)findViewById(R.id.editTextrechargeNumber);
        Recharge = (Button)findViewById(R.id.buttonrecharge);

        Recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_CALL);
                String Rnum = rechargeNumber.getText().toString();
                String encodedHash = Uri.encode("#");
                i.setData(Uri.parse("tel:"+"*102*"+Rnum+encodedHash));


                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(i);
                rechargeNumber.setText("");

            Toast.makeText(MainActivity.this,"Recharge clicked !!!",Toast.LENGTH_SHORT).show();
            }
        });


        btnMobitel.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Intent mobitelPage = new Intent(MainActivity.this, com.example.harithagayashan.easysim.mobitelPage.class);
                        startActivity(mobitelPage);
                    }
                }
        );
    }
}
