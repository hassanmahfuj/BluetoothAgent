package com.hum.bluetoothagent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView btState;
    private Button actionCheck;
    private Button actionActivate;

    private BluetoothAgent bluetoothAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btState = findViewById(R.id.bluetooth_state);
        actionCheck = findViewById(R.id.action_check);
        actionActivate = findViewById(R.id.action_activate);

        bluetoothAgent = new BluetoothAgent(this);

        checkBluetoothState();

        actionCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBluetoothState();
            }
        });

        actionActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothAgent.activateBluetooth();
            }
        });
    }

    private void checkBluetoothState() {
        if(bluetoothAgent.isBluetoothActivated()) {
            btState.setText("Bluetooth Switched ON");
        } else {
            btState.setText("Bluetooth Switched OFF");
        }
    }
}