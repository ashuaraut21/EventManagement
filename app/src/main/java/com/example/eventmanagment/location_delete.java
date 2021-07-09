package com.example.eventmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class location_delete extends AppCompatActivity {
    TextView location,owner,address,pincode,lati,langi,area,contact,email,pass;
    Button register_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_delete);
        location=(TextView)findViewById(R.id.location_name);
        owner=(TextView)findViewById(R.id.owner_name);
        address=(TextView)findViewById(R.id.address);
        pincode=(TextView)findViewById(R.id.pincode);
        lati=(TextView)findViewById(R.id.lati);
        langi=(TextView)findViewById(R.id.langi);
        area=(TextView)findViewById(R.id.area);
        contact=(TextView)findViewById(R.id.contact);
        email=(TextView)findViewById(R.id.email);
        pass=(TextView)findViewById(R.id.pass);
        register_location=(Button)findViewById(R.id.register_location);

        final Bundle extras=getIntent().getExtras();
        location.setText(extras.getString("name"));
        owner.setText(extras.getString("owner"));
        address.setText(extras.getString("address"));
        pincode.setText(extras.getString("pincode"));
        lati.setText(extras.getString("lati"));
        langi.setText(extras.getString("langi"));
        area.setText(extras.getString("area"));
        contact.setText(extras.getString("contact"));
        email.setText(extras.getString("email"));
        pass.setText(extras.getString("password"));


        register_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Location gettrans = new Location();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/EventManagment/location_delete.php?id=" + URLEncoder.encode(extras.getString("location_id"));
                gettrans.execute(url1);

            }
        });


    }

    //code for send data to the server
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class Location extends AsyncTask<String, Integer, String> {
        private ProgressDialog progress = null;
        String out="";
        int count=0;
        @Override
        protected String doInBackground(String... geturl) {


            try{
                //	String url= ;


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
            progress = ProgressDialog.show(location_delete.this, null,
                    "Deleting Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(location_delete.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            Intent register_i1 = new Intent(location_delete.this, admin.class);
            startActivity(register_i1);
            finish();
        }



    }
}
