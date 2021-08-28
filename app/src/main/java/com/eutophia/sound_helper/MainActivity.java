package com.eutophia.sound_helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


public class MainActivity extends AppCompatActivity {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Button activity_info= (Button)findViewById(R.id.enter_inform_button);
        WorkRequest showWorkRequest =
                new OneTimeWorkRequest.Builder(ShowWorker.class)
                        .build();
        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(showWorkRequest);
        activity_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
            }
        });

        Button activity_listener= (Button)findViewById(R.id.enter_listener_button);
        activity_listener.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListenerActivity.class);
                startActivity(intent);
            }
        });
    }
}