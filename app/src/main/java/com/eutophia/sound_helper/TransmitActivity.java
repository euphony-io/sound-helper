package com.eutophia.sound_helper;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import euphony.lib.transmitter.EuTxManager;

public class TransmitActivity extends AppCompatActivity {
    private String _sendMsg = "";

    EuTxManager mTxManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);

        _sendMsg = "encoded msg from intent";

        mTxManager = new EuTxManager();
    }

    void transmitMsg(String tmp){
        mTxManager.euInitTransmit(tmp); // To generate acoustic data
        mTxManager.process(-1); // generate sound infinite.
    }

    void stopTransmit(){
        mTxManager.stop();
    }
}
