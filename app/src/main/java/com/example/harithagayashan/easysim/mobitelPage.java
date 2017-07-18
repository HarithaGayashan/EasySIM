package com.example.harithagayashan.easysim;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.net.Uri;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.firebase.database.ValueEventListener;

import android.Manifest;




import java.util.ArrayList;

public class mobitelPage extends AppCompatActivity {

    private Button checkBalance;
    private Button actDataPackage;
    private DatabaseReference mDatabase;
    private Spinner dataPackagesList;
    private ListView dPackages;
    private EditText receversNumber;
    private EditText amount;
    private Button shareCredit;

  ///String[] packages = {"D3 | Rs 3 | 20MB","D5 | Rs 5 | 25MB","D29 | Rs 29 | 160MB","D39 | Rs 39 | 200MB","D49 | Rs 49 | 400MB","D99 | Rs 99 | 800MB","D199 | Rs 199 | 2GB","D299 | Rs 299 | 3GB","D399 | Rs 399 | 4.5GB","D499 | Rs 499 | 5GB","D699 | Rs 699 | 7GB","D999 | Rs 999 | 10GB","D1999 | Rs 1999 | 24GB"};

  

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobitel_page);
        mDatabase = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easysim-e7d53.firebaseio.com/Mtel/prePaidData");


        dPackages = (ListView)findViewById(R.id.dpackages);
        receversNumber = (EditText)findViewById(R.id.editTextRecNum);
        amount = (EditText)findViewById(R.id.editText2);
        shareCredit = (Button)findViewById(R.id.buttonShareCredits);
        checkBalance = (Button)findViewById(R.id.buttonCheckBalance);
         mDatabase.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                    String datapackString = dataSnapshot.getValue().toString();
                final String[]  packages = datapackString.split(",");

                 // start array adapter filing

                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(mobitelPage.this,android.R.layout.simple_list_item_1,packages);
                 dPackages.setAdapter(adapter);
                 dPackages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         String selected_package = packages[position];
                         final String[] splited = selected_package.split("\\s+");
                         Toast.makeText(mobitelPage.this,splited[0],Toast.LENGTH_SHORT).show();  // shows the selected package
//// start

                         AlertDialog.Builder builder = new AlertDialog.Builder(mobitelPage.this);
                         builder.setCancelable(true);
                         builder.setTitle("Confirmation");
                         builder.setMessage("Are you sure \n This will charge "+ splited[2]+" "+splited[3]);
                         builder.setPositiveButton("Confirm",
                                 new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         Toast.makeText(mobitelPage.this,splited[0],Toast.LENGTH_SHORT).show();
                                         SmsManager smsManager = SmsManager.getDefault();
                                         smsManager.sendTextMessage("7678",null,splited[0].toString(),null,null);

                                     }
                                 });
                         builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 Toast.makeText(mobitelPage.this,"Service not activated",Toast.LENGTH_SHORT).show();
                             }
                         });

                         AlertDialog dialog = builder.create();
                         dialog.show();



                         //// end

                     }
                 });


                 //end array adapter filling
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });


        checkBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_CALL);

                String encodedHash = Uri.encode("#");
                i.setData(Uri.parse("tel:"+"*100"+encodedHash));


                if (ActivityCompat.checkSelfPermission(mobitelPage.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(i);
                
        

            }
        });


        shareCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                String RecNumber = receversNumber.getText().toString();
                String RS = amount.getText().toString();
                String encodedHash = Uri.encode("#");
                i.setData(Uri.parse("tel:"+"*448*"+RecNumber+"*"+RS+encodedHash));

                if (ActivityCompat.checkSelfPermission(mobitelPage.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(i);
            }



        });




      /* final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,packages);
        dPackages.setAdapter(arrayAdapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    String value = dataSnapshot.getValue(String.class);
                packages.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/


    }
}
