package com.eutophia.sound_helper;

import static android.content.Context.SENSOR_SERVICE;
import static com.eutophia.sound_helper.MainActivity.context;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ShowWorker extends Worker implements SensorEventListener {
    Context mContext = context;

    SensorManager mSensorManager;
    Sensor mAccelerometer;

    private long mShakeTime;
    private static final int SHAKE_SKIP_TIME = 500;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    public ShowWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }
    @Override
    public Result doWork() {
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,
                mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        return Result.success();
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
                Intent intent = new Intent(getApplicationContext(), TransmitActivity.class);
                context.startActivity(intent);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
