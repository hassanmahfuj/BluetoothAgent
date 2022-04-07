package com.hum.bluetoothagent;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class BluetoothAgent {
    private static final String DEFAULT_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    private Activity activity;

    private BluetoothAdapter bluetoothAdapter;

    public BluetoothAgent(Activity activity) {
        this.activity = activity;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isBluetoothEnabled() {
        return bluetoothAdapter != null;
    }

    public boolean isBluetoothActivated() {
        if (bluetoothAdapter == null) return false;

        return bluetoothAdapter.isEnabled();
    }

    public void activateBluetooth() {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivity(intent);
    }

    public String getRandomUUID() {
        return String.valueOf(UUID.randomUUID());
    }

    public void getPairedDevices(ArrayList<HashMap<String, Object>> results) {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                HashMap<String, Object> result = new HashMap<>();
                result.put("name", device.getName());
                result.put("address", device.getAddress());

                results.add(result);
            }
        }
    }

    public void readyConnection(BluetoothEventListener listener, String tag) {
        if (BluetoothHandler.getInstance().getState().equals(BluetoothHandler.STATE_NONE)) {
            BluetoothHandler.getInstance().start(this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);
        }
    }

    public void readyConnection(BluetoothEventListener listener, String uuid, String tag) {
        if (BluetoothHandler.getInstance().getState().equals(BluetoothHandler.STATE_NONE)) {
            BluetoothHandler.getInstance().start(this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);
        }
    }

    public void startConnection(BluetoothEventListener listener, String address, String tag) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

        BluetoothHandler.getInstance().connect(device, this, listener, tag, UUID.fromString(DEFAULT_UUID), bluetoothAdapter);
    }

    public void startConnection(BluetoothEventListener listener, String uuid, String address, String tag) {
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

        BluetoothHandler.getInstance().connect(device, this, listener, tag, UUID.fromString(uuid), bluetoothAdapter);
    }

    public void stopConnection(BluetoothEventListener listener, String tag) {
        BluetoothHandler.getInstance().stop(this, listener, tag);
    }

    public void sendData(BluetoothEventListener listener, String data, String tag) {
        String state = BluetoothHandler.getInstance().getState();

        if (!state.equals(BluetoothHandler.STATE_CONNECTED)) {
            listener.onConnectionError(tag, state, "Bluetooth is not connected yet");
            return;
        }

        BluetoothHandler.getInstance().write(data.getBytes());
    }

    public Activity getActivity() {
        return activity;
    }
}
