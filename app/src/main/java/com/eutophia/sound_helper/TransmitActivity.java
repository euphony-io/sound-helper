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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import euphony.lib.transmitter.EuTxManager;

public class TransmitActivity extends AppCompatActivity implements SensorEventListener {
    private String TAG = "Transmit";
    Person person = new Person();

    private String _sendMsg = "HelloWorld!";

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

        infoTV = findViewById(R.id.transmit_infoTV);
        msgTV = findViewById(R.id.transmit_msgTV);
        stopBtn = findViewById(R.id.transmit_button);
        stopBtn.setVisibility(View.GONE);

        msgTV.setText(_sendMsg);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTransmit();
                finish();
            }
        });

        mTxManager = new EuTxManager();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        stopTransmit();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Person");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stopTransmit();
                person = dataSnapshot.getValue(Person.class);

                _sendMsg = "Name : " + person.getName() +  "\nTel : " + person.getTel() +
                        "\nBirth : " + person.getBirthOfDate() + "\nDisease : " + person.getDiseaseName();
                msgTV.setText(_sendMsg);
                transmitMsg(_sendMsg);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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

                ////
                //// 이 부분이 흔들림이 감지되었을 때 호출되는 부분
                //// 여기서 액티비티 시작
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
        infoTV.setText("Transmitting Data");
    }

    void stopTransmit(){ // stop transmission, start detection
        mTxManager.stop();

        mSensorManager.registerListener(this,
                mAccelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        stopBtn.setVisibility(View.GONE);
        infoTV.setText("Waiting for Data from DB");
    }
}
