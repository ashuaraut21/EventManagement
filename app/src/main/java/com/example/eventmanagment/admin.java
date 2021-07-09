package com.example.eventmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {
    Button add_location,update_location,view_location,delete_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        add_location=(Button)findViewById(R.id.add_Location);
        update_location=(Button)findViewById(R.id.update_location);
        view_location=(Button)findViewById(R.id.view_location);
        delete_location=(Button)findViewById(R.id.delete_location);
        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(admin.this,add_location.class);
                startActivity(i1);
            }
        });
        update_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(admin.this,location_update_show.class);
                startActivity(i1);
            }
        });
        view_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(admin.this,location_view.class);
                startActivity(i1);
            }
        });
        delete_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(admin.this,location_delete_show.class);
                startActivity(i1);
            }
        });
    }
}
