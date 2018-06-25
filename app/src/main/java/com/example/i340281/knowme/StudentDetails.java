package com.example.i340281.knowme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class StudentDetails extends AppCompatActivity {

    Intent intent;
    String name, email, bg, phone, dob, address, allergies;
    TextView t1, t2, t3, t4, t5, t6, t7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        name = "NA";
        email = "NA";
        bg = "NA";
        phone = "NA";
        dob = "NA";
        address = "NA";
        allergies = "NA";

        intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        bg = intent.getStringExtra("bloodgroup");
        phone = intent.getStringExtra("phone");
        dob = intent.getStringExtra("dob");
        address = intent.getStringExtra("address");
        allergies = intent.getStringExtra("allergies");
//        Log.d("data", name);

        String abc = decodeBase64(name);
        Log.d("Name", abc);

        t1 = findViewById(R.id.name);
        t2 = findViewById(R.id.email);
        t3 = findViewById(R.id.bg);
        t4 = findViewById(R.id.phone);
        t5 = findViewById(R.id.birthday);
        t6 = findViewById(R.id.address);
        t7 = findViewById(R.id.allergy);

        t1.setText(name);
        t2.setText(email);
        t3.setText(bg);
        t4.setText(phone);
        t5.setText(dob);
        t6.setText(address);
        t7.setText(allergies);
    }

    private String decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), 12345);
        } catch (UnsupportedEncodingException e) {
        }
        return new String(valueDecoded);
    }
}
