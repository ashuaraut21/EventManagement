package com.example.eventmanagment;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class location_search_info extends AppCompatActivity {
    ListView listView;
    Button getChoice;
    int count;
    public  static  String[] owner_id,name,price,describ,s_name,s_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search_info);
        listView = (ListView)findViewById(R.id.listview);
        getChoice=(Button)findViewById(R.id.select_service_button);
        getChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected = "";
                int cntChoice = listView.getCount();
                SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();
                for(int i = 0; i < cntChoice; i++){
                    if(sparseBooleanArray.get(i)) {
                        try {
                            s_name[i] = listView.getItemAtPosition(i).toString();
                            s_price[i] = price[i];
                        }catch (Exception e){
                            Toast.makeText(location_search_info.this,""+e,Toast.LENGTH_LONG).show();
                        }
                        selected += listView.getItemAtPosition(i).toString() + "\n";
                        count= count+Integer.parseInt(price[i]);
                    }
                }
                Toast.makeText(location_search_info.this,count , Toast.LENGTH_LONG).show();
                /*Intent i1=new Intent(add_disease.this,disease_name_prescription.class);
                Bundle bundle=new Bundle(10);
                bundle.putString("select",selected);
                i1.putExtras(bundle);
                startActivity(i1);*/

            }
        });
        Bundle b1=getIntent().getExtras();
        try {

            getJSON("http://mahavidyalay.in/AcademicDevelopment/EventManagment/service_show.php?owner_id="+ URLEncoder.encode(b1.getString("location_id")));
        }catch (Exception e){
            Toast.makeText(location_search_info.this,""+e,Toast.LENGTH_LONG).show();
        }
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        name = new String[jsonArray.length()];
        price = new String[jsonArray.length()];
        describ = new String[jsonArray.length()];
        owner_id = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            name[i] = obj.getString("name");
            price[i] = obj.getString("price");
            describ[i] = obj.getString("describ");
            owner_id[i] = obj.getString("owner_id");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, name);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(arrayAdapter);
    }
}
