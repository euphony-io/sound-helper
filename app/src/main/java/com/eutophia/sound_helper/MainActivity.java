package com.eutophia.sound_helper;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static Context context;

    SensorManager mSensorManager;
    Sensor mAccelerometer;

    private long mShakeTime;
    private static final int SHAKE_SKIP_TIME = 500;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Button activity_info= (Button)findViewById(R.id.enter_inform_button);
        onResume();
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

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,
                mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float axX = sensorEvent.values[0];
            float axY = sensorEvent.values[1];
            float axZ = sensorEvent.values[2];

            float gravityX = axX / SensorManager.GRAVITY_EARTH;
            float gravityY = axY / SensorManager.GRAVITY_EARTH;
            float gravityZ = axZ / SensorManager.GRAVITY_EARTH;

            Float rawForce = gravityX * gravityX + gravityY * gravityY + gravityZ * gravityZ;
            double squaredForce = Math.sqrt(rawForce.doubleValue());
            float gForce = (float) squaredForce;

            if(gForce > SHAKE_THRESHOLD_GRAVITY){
                long currentTime  = System.currentTimeMillis();

                if(mShakeTime + SHAKE_SKIP_TIME > currentTime)
                    return;
                mShakeTime = currentTime;

                WorkRequest showWorkRequest =
                        new OneTimeWorkRequest.Builder(ShowWorker.class)
                                .build();
                WorkManager
                        .getInstance(getApplicationContext())
                        .enqueue(showWorkRequest);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}