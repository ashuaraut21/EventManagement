package com.example.eventmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class owner extends AppCompatActivity {
    Button add_service,view_booking,update_service,delete_servicee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        view_booking=(Button)findViewById(R.id.view_booking);
        update_service=(Button)findViewById(R.id.update_service);
        delete_servicee=(Button)findViewById(R.id.delete_service);
        add_service=(Button)findViewById(R.id.add_services);
        view_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this,booking_view.class);
                startActivity(i1);
            }
        });
        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this,service_add.class);
                startActivity(i1);
            }
        });
        delete_servicee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this,service_delete.class);
                startActivity(i1);
            }
        });
        update_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(owner.this,service_update.class);
                startActivity(i1);
            }
        });

    }
}
