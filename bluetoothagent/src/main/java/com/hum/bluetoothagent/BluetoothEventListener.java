package com.hum.bluetoothagent;

import java.util.HashMap;

public interface BluetoothEventListener {

    public void onConnected(String tag, HashMap<String, Object> deviceData);

    public void onDataReceived(String tag, byte[] data, int bytes);

    public void onDataSent(String tag, byte[] data);

    public void onConnectionLost(String tag, String connectionState, String message);

    public void onConnectionError(String tag, String connectionState, String message);

    public void onConnectionStopped(String tag);
}