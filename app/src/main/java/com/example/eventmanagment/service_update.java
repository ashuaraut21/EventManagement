package com.example.eventmanagment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class service_update extends AppCompatActivity {
    ListView listView;
    public  static String service_id[],owner_id[],price[],describ[],service_name[];
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update);
        listView=(ListView)findViewById(R.id.listview);
        sharedpreferences = getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
        String owner_id1 =sharedpreferences.getString("OWNER", "");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i1 =new Intent(service_update.this,service_update_info.class);
                Bundle bndl=new Bundle();
                bndl.putString("service_id",service_id[position]);
                bndl.putString("owner_id",owner_id[position]);
                bndl.putString("price",price[position]);
                bndl.putString("describ",describ[position]);
                bndl.putString("service_name",service_name[position]);
                i1.putExtras(bndl);
                startActivity(i1);
            }
        });

        try {
            getJSON("http://mahavidyalay.in/AcademicDevelopment/EventManagment/service_show.php?owner_id="+owner_id1);
        }catch (Exception e){
            //Toast.makeText(show_update_medical.this,""+e,Toast.LENGTH_LONG).show();
        }
    }
    //ADD FOLLOWING line in manifest android:usesCleartextTraffic="true"
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
        service_id = new String[jsonArray.length()];
        owner_id = new String[jsonArray.length()];
        service_name = new String[jsonArray.length()];
        price = new String[jsonArray.length()];
        describ = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            service_id[i] = obj.getString("id");
            owner_id[i] = obj.getString("owner_id");
            service_name[i] = obj.getString("name");
            price[i] = obj.getString("price");
            describ[i] = obj.getString("describ");
        }
        LevelAdapter leveladapter=new LevelAdapter(service_update.this,service_name,price,describ);
        listView.setAdapter(leveladapter);
    }
}
