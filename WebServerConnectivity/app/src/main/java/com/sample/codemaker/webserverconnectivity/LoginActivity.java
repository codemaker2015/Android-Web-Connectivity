package com.sample.codemaker.webserverconnectivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sample.codemaker.webserverconnectivity.classes.AppConfig;
import com.sample.codemaker.webserverconnectivity.classes.WebConnectivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button buttonLogin;
    EditText editTextUsername,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute(editTextUsername.getText().toString(),editTextPassword.getText().toString());
            }
        });
    }

    private class LoginTask extends AsyncTask<String,Integer,String>{
        String res="";
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
//            progressDialog.setTitle("Response");
//            progressDialog.setMessage("Waiting for response");
//            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            WebConnectivity connectivity = new WebConnectivity();

            String url = AppConfig.SERVER_URL + AppConfig.LOGIN_PAGE;
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("username",params[0]);
                jsonObject.put("password",params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            res  = connectivity.sendReqeust(url,jsonObject);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s.equals("success")){
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("")){
                Toast.makeText(LoginActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(LoginActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
        }
    }
}
