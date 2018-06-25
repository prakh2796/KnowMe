package com.example.i340281.knowme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import static com.example.i340281.knowme.LoginActivity.MY_PREFS_NAME;

public class MainActivity extends AppCompatActivity {

    Button button;
    Intent intent;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
//                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, StudentForm.class);
                startActivity(intent);
            }
        });

    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                try {

                    JSONObject obj = new JSONObject(result.getContents().toString());
                    Log.d("My App", obj.toString());
//                    Log.d("Data", obj.getString("Teacher").toString());
//                    JSONObject jsonObject = obj.getJSONObject("Teacher");
//                    System.out.println(jsonObject);
                    intent = new Intent(MainActivity.this, StudentDetails.class);
                    intent.putExtra("name", obj.getString("name"));
                    intent.putExtra("email", obj.getString("email"));
//                    intent.putExtra("bloodgroup", obj.getString("Blood Group"));
                    intent.putExtra("phone", obj.getString("phone"));
                    intent.putExtra("dob", obj.getString("dob"));
                    intent.putExtra("address", obj.getString("address"));
                    intent.putExtra("allergies", obj.getString("allergies"));
                    startActivity(intent);

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + result.getContents().toString() + "\"");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu, this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.add_user:
//                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
//                        .show();
                intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                break;
            // action with ID action_settings was selected
            case R.id.myQR:
//                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
//                        .show();
                intent = new Intent(MainActivity.this, QRCodeView.class);
                startActivity(intent);
                break;
            case R.id.logout:
//                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
//                        .show();
                editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("name", "null");
                editor.putString("category", "null");
                editor.putString("id", null);
                editor.apply();
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        return true;

    }
}
