package com.example.i340281.knowme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.i340281.knowme.LoginActivity.MY_PREFS_NAME;

public class SignUp extends AppCompatActivity {

    AutoCompleteTextView email;
    EditText password;
    EditText name;
    Button signUp;
    Intent intent;
    ProgressDialog pDialog;
    SharedPreferences prefs;
    String category;
    String createdBy;

    String TAG = "Register";

    String url = "http://10.42.0.1:5000/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        signUp = findViewById(R.id.email_sign_up_button);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        category = prefs.getString("category", "0");//"No name defined" is the default value.
        createdBy = prefs.getString("id", "0"); //0 is the default value.

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog.show();
                StringRequest strReq = new StringRequest(Request.Method.POST,
                        url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                Toast.makeText(getApplication(),"SignUp Done!",Toast.LENGTH_SHORT).show();
                                intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplication(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        pDialog.hide();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());
                        params.put("category", category);
                        params.put("createdBy", createdBy);
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(strReq, TAG);
            }
        });
    }
}
