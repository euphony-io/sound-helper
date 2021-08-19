package com.eutophia.sound_helper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button activity_inform= (Button)findViewById(R.id.informButton);
        activity_inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InformActivity.class);
                startActivity(intent);
            }
        });

        Button activity_listener= (Button)findViewById(R.id.listenerButton);
        activity_listener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListenerActivity.class);
                startActivity(intent);
            }
        });
    }
}