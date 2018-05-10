package ru.startandroid.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //String url = "http://176.101.221.189:8008/admin/menuItems";
    String url = "http://lap.drc.ngo:8008/admin/menuItems";
    Button btn_Login;
    TextView tv;
    EditText et_Login, et_Password;
    ProgressBar pb_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = findViewById(R.id.tv);
        btn_Login = findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(this);
        et_Login = findViewById(R.id.et_Login);
        et_Password = findViewById(R.id.et_Password);
        pb_request = findViewById(R.id.pb_request);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_Login:
                String login = et_Login.getText().toString();
                String password = et_Password.getText().toString();

                StrictMode.ThreadPolicy policy = new StrictMode.
                        ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                String percievedJSON;
                String resultHttp  = HttpGet(url, login, password);
                if (!resultHttp.isEmpty()){
                    if (resultHttp == getString(R.string.mistake_401)){
                        break;
                    }
                    percievedJSON = resultHttp;
                    saveJSON(percievedJSON);
                }
                else{
                    percievedJSON = getSavedJSON();
                }

                if (!percievedJSON.isEmpty()) {
                    Intent intent = new Intent(this, Users_list.class);
                    intent.putExtra("responceJSON", percievedJSON);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void saveJSON(String percievedJSON) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_JSON), percievedJSON);
        editor.commit();
    }

    // Connect Http Get //
    private String HttpGet(String url, String user, String pwd) {

        String textMistake = "";
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Authorization", "Basic " + Base64.encodeToString((user + ":" + pwd).getBytes(), Base64.NO_WRAP));

        try {
            HttpResponse response = client.execute(httpGet);
            org.apache.http.StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else if (statusCode == 401) {
                Toast.makeText(this, "Incorrect login or password. Try again.", Toast.LENGTH_SHORT).show();
                return getString(R.string.mistake_401);
            } else {
                Toast.makeText(this, "Failed to download result..", Toast.LENGTH_SHORT).show();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            if (textMistake.isEmpty()) {
                textMistake = e.getCause().toString();
            } else {
                textMistake = textMistake + "/n" + e.getCause().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (textMistake.isEmpty()) {
                textMistake = e.getCause().toString();
            } else {
                textMistake = textMistake + "/n" + e.getCause().toString();
            }

        }
        if (!textMistake.isEmpty()) {
            Toast.makeText(this, textMistake, Toast.LENGTH_SHORT).show();
        }

        return builder.toString();
    }

    private String getSavedJSON() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String savedJSON = sharedPref.getString(getString(R.string.saved_JSON), "");
        return savedJSON;
    }


}






