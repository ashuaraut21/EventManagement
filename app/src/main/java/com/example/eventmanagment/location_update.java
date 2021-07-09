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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class location_update extends AppCompatActivity {
    EditText location,owner,address,pincode,lati,langi,area,contact,email,pass;
    Button register_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_update);
        location=(EditText)findViewById(R.id.location_name);
        owner=(EditText)findViewById(R.id.owner_name);
        address=(EditText)findViewById(R.id.address);
        pincode=(EditText)findViewById(R.id.pincode);
        lati=(EditText)findViewById(R.id.lati);
        langi=(EditText)findViewById(R.id.langi);
        area=(EditText)findViewById(R.id.area);
        contact=(EditText)findViewById(R.id.contact);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
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
                if (area.length()>0) {
                    if (location.length() > 0) {
                        if (owner.length() > 0) {
                            if (address.length() > 0) {
                                if (pincode.length() == 6) {
                                    if (lati.length() > 0) {
                                        if (langi.length() > 0) {
                                            if (contact.length() == 10) {
                                                if (pass.length() > 0) {

                                                    Location gettrans = new Location();
                                                    String url1 = "http://mahavidyalay.in/AcademicDevelopment/EventManagment/location_update.php?name=" + URLEncoder.encode(location.getText().toString())
                                                            + "&owner=" + URLEncoder.encode(owner.getText().toString())
                                                            + "&address=" + URLEncoder.encode(address.getText().toString())
                                                            + "&pincode=" + URLEncoder.encode(pincode.getText().toString())
                                                            + "&lati=" + URLEncoder.encode(lati.getText().toString())
                                                            + "&langi=" + URLEncoder.encode(langi.getText().toString())
                                                            + "&area=" + URLEncoder.encode(area.getText().toString())
                                                            + "&contact=" + URLEncoder.encode(contact.getText().toString())
                                                            + "&email=" + URLEncoder.encode(email.getText().toString())
                                                            + "&password=" + URLEncoder.encode(pass.getText().toString())
                                                            + "&username=" + URLEncoder.encode("@123" + location.getText().toString())
                                                            + "&id=" + URLEncoder.encode(extras.getString("location_id"));
                                                    gettrans.execute(url1);


                                                } else {
                                                    pass.setError("Enter valid password");
                                                    pass.requestFocus(20);
                                                }

                                            } else {
                                                contact.setError("Enter valid Contact Number");
                                                contact.requestFocus(20);
                                            }

                                        } else {
                                            langi.setError("Enter valid value");
                                            langi.requestFocus(20);
                                        }
                                    } else {
                                        lati.setError("Enter valid value");
                                        lati.requestFocus(20);
                                    }

                                } else {
                                    pincode.setError("pincode length must be 6 digit");
                                    pincode.requestFocus(20);
                                }

                            } else {
                                address.setError("Enter valid address");

                                address.requestFocus(20);
                            }

                        } else {
                            owner.setError("Enter valid Owner Name");
                            owner.requestFocus(20);
                        }

                    } else {
                        location.setError("Enter valid Lcatin");
                        location.requestFocus(20);
                    }
                }else{
                    area.setError("Enter valid AREA");
                    area.requestFocus(20);
                }

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
            progress = ProgressDialog.show(location_update.this, null,
                    "Updating Register Information...");

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            Toast.makeText(location_update.this, ""+result, Toast.LENGTH_LONG).show();
            progress.dismiss();
            String pass1="@123"+location.getText().toString();
            //SmsManager smgr = SmsManager.getDefault();
            //smgr.sendTextMessage(contact.getText().toString(),null,"user name: "+pass1+" password: "+pass.getText().toString(),null,null);
            //transactions.setText("Transactions :"+count);

            Intent register_i1 = new Intent(location_update.this, admin.class);
            startActivity(register_i1);
            finish();
        }



    }
}
