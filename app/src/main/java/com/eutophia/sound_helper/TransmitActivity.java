package com.eutophia.sound_helper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import euphony.lib.transmitter.EuTxManager;

public class TransmitActivity extends AppCompatActivity implements SensorEventListener {
    private String _sendMsg = "";

    //

    TextView infoTV;
    TextView msgTV;
    Button stopBtn;

    //

    EuTxManager mTxManager;

    //

    SensorManager mSensorManager;
    Sensor mAccelerometer;

    private long mShakeTime;
    private static final int SHAKE_SKIP_TIME = 500;
    private static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);

        _sendMsg = "encoded msg from intent";

        infoTV = findViewById(R.id.transmit_infoTV);
        msgTV = findViewById(R.id.transmit_msgTV);
        stopBtn = findViewById(R.id.transmit_button);
        stopBtn.setVisibility(View.GONE);

        msgTV.setText(_sendMsg);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTransmit();
            }
        });

        mTxManager = new EuTxManager();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        stopTransmit();
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

                Log.e("Transmit","Shake Detected");
                transmitMsg(_sendMsg); // call transmit function
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    void transmitMsg(String tmp){ // start transmission, stop detection
        mTxManager.euInitTransmit(tmp); // To generate acoustic data
        mTxManager.process(-1); // generate sound infinite.

        mSensorManager.unregisterListener(this);

        stopBtn.setVisibility(View.VISIBLE);
        infoTV.setText("데이터 전송중");
    }

    void stopTransmit(){ // stop transmission, start detection
        mTxManager.stop();

        mSensorManager.registerListener(this,
                mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        stopBtn.setVisibility(View.GONE);
        infoTV.setText("핸드폰을 흔들어서 데이터 전송 시작");
    }
}
