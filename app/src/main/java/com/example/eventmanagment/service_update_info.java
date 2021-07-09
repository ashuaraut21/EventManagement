package com.example.eventmanagment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class service_update_info extends AppCompatActivity {
    EditText service_name,price,description;
    SharedPreferences sharedpreferences;
    Button add_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update_info);
        service_name=(EditText)findViewById(R.id.service_name);
        price=(EditText)findViewById(R.id.price);
        description=(EditText)findViewById(R.id.description);
        add_service=(Button)findViewById(R.id.add_service_button);
        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                String owner_id =sharedpreferences.getString("OWNER", "");
                Service gettrans = new Service();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/EventManagment/service_add.php?name="
                        + URLEncoder.encode(service_name.getText().toString())
                        + "&price=" + URLEncoder.encode(price.getText().toString())
                        + "&describ=" + URLEncoder.encode(description.getText().toString())
                        + "&owner_id=" + owner_id;
                gettrans.execute(url1);
            }
        });
    }
    //code for send data to the server
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class Service extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {
            try{
                HttpClient http=new DefaultHttpClient();
                HttpPost http_get= new HttpPost(geturl[0]);
                HttpResponse response=http.execute(http_get);
                HttpEntity http_entity=response.getEntity();
                BufferedReader br= new BufferedReader(new InputStreamReader(http_entity.getContent()));
                out = br.readLine();
            }catch (Exception e){
                out= e.toString();
            }
            return out;
        }
        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(service_update_info.this, null, "Updating Register Information...");
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(service_update_info.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            Intent register_i1 = new Intent(service_update_info.this, owner.class);
            startActivity(register_i1);
            finish();
        }



    }
}
