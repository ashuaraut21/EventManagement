package com.example.eventmanagment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class search_location extends AppCompatActivity {

    EditText location_search;
    Button search_button;
    ListView listView;
    public SharedPreferences sharedpreferences;
    String content;
    public static String[] location_id,name,owner,address,pincode,lati,langi,area,contact,email,username,password;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        location_search = (EditText) findViewById(R.id.serch_remove);
        search_button = (Button) findViewById(R.id.remove_button);

        listView=(ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=listView.getItemAtPosition(position).toString();
                Intent show_i1= new Intent(search_location.this,location_search_info.class);
                Bundle bundle = new Bundle();
                bundle.putString("location_id", location_id[position]);
                bundle.putString("name", name[position]);
                bundle.putString("owner", owner[position]);
                bundle.putString("address", address[position]);
                bundle.putString("pincode", pincode[position]);
                bundle.putString("lati", lati[position]);
                bundle.putString("langi", langi[position]);
                bundle.putString("area", area[position]);
                bundle.putString("contact", contact[position]);
                bundle.putString("email", email[position]);
                bundle.putString("username", username[position]);
                bundle.putString("password", password[position]);
                show_i1.putExtras(bundle);
                startActivity(show_i1);

            }
        });
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*LedOnOff gettrans = new LedOnOff();
                String url1 = "http://mahavidyalay.in/AcademicDevelopment/MedicineSynonymFinder/search_medicine.php?medicine_name="+medicine_search.getText().toString();
                gettrans.execute(url1);
*/                try {
                    getJSON("http://mahavidyalay.in/AcademicDevelopment/EventManagment/location_info.php?location="+location_search.getText().toString());
                }catch (Exception e){
                    Toast.makeText(search_location.this,""+e,Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    //code for getting all information of medicine
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);

        location_id = new String[jsonArray.length()];
        name = new String[jsonArray.length()];
        owner = new String[jsonArray.length()];
        address = new String[jsonArray.length()];
        pincode = new String[jsonArray.length()];
        lati = new String[jsonArray.length()];
        langi = new String[jsonArray.length()];
        area = new String[jsonArray.length()];
        contact = new String[jsonArray.length()];
        email = new String[jsonArray.length()];
        username = new String[jsonArray.length()];
        password = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            location_id[i] = obj.getString("location_id");
            name[i] = obj.getString("name");
            owner[i] = obj.getString("owner");
            address[i] = obj.getString("address");
            pincode[i] = obj.getString("pincode");
            lati[i] = obj.getString("lati");
            langi[i] = obj.getString("langi");
            area[i] = obj.getString("area");
            contact[i] = obj.getString("contact");
            email[i] = obj.getString("email");
            username[i] = obj.getString("username");
            password[i] = obj.getString("password");

        }
        LevelAdapter1 leveladapter=new LevelAdapter1(search_location.this,name,owner,address);
        listView.setAdapter(leveladapter);


    }

}
