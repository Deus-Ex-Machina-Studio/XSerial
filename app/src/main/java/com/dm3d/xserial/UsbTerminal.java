package com.dm3d.xserial;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

public class UsbTerminal {
    private final String ACTION_USB_PERMISSION = "com.dm3d.xserial.USB_PERMISSION";

    private UsbManager usbManager = null;
    private UsbSerialDriver driver = null;
    private UsbDeviceConnection connection = null;
    private UsbSerialPort serialPort = null;
    private SerialInputOutputManager usbIOManager = null;

    private Date currentTime = Calendar.getInstance().getTime();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");

    private List<UsbSerialDriver> availableDrivers = null;

    private TextView terminalText = null;
    private FragmentActivity fragmentActivity = null;

    public UsbTerminal(TextView _terminalText, FragmentActivity _fragmentActivity) {
        terminalText = _terminalText;
        fragmentActivity = _fragmentActivity;

        usbManager = (UsbManager)_fragmentActivity.getSystemService(Context.USB_SERVICE);
    }

    public void Connection() {
        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);

        for (UsbSerialDriver usbSerialDriver: availableDrivers)
            TextViewAppend(terminalText, "Driver: " + usbSerialDriver.getDevice().getDeviceName() + "\n");

        if (availableDrivers != null && availableDrivers.isEmpty()) {
            TextViewAppend(terminalText, "Not Drivers!\n");
            return;
        }

        driver = availableDrivers.get(0);
        connection = usbManager.openDevice(driver.getDevice());
        if (connection == null) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(fragmentActivity.getBaseContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
            usbManager.requestPermission(driver.getDevice(), pendingIntent);
        }

        serialPort = driver.getPorts().get(0);
        try {
            serialPort.open(connection);
            serialPort.setParameters(Settings.BAUDRATE, Settings.DATA_BIT, Settings.STOP_BIT, Settings.PARITY);

            usbIOManager = new SerialInputOutputManager(serialPort, serialInputOutputListener);
            Executors.newSingleThreadExecutor().submit(usbIOManager);

            TextViewAppend(terminalText, "Connection opened with baudrate: " + Settings.BAUDRATE + "\n");
        } catch (IOException e) {
            TextViewAppend(terminalText, e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    public void Disconnection() {
        if (serialPort == null) return;

        try {
            serialPort.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);

        driver = null;
        connection = null;
        serialPort = null;
        usbIOManager = null;

        TextViewAppend(terminalText, "Connection was close\n");
    }

    public void Send(String string) {
        if (serialPort != null && serialPort.isOpen() && usbIOManager != null) {
            try {
                // usbIOManager.writeAsync((sendText.getText().toString() + "\r\n").getBytes());
                serialPort.write((string + "\r\n").getBytes(), 0);
                TextViewAppend(terminalText, "[POST] " + string + "\n");
            } catch (Exception exception) {
                TextViewAppend(terminalText, exception.getMessage() + "\n");
                exception.printStackTrace();
            }
        }
    }

    private SerialInputOutputManager.Listener serialInputOutputListener = new SerialInputOutputManager.Listener() {
        @Override
        public void onNewData(byte[] data) {
            TextViewAppend(terminalText,  "[GET]\n" + new String(data) + "\n");
        }

        @Override
        public void onRunError(Exception e) {
            TextViewAppend(terminalText, e.getMessage() + "\n");
        }
    };

    public void TextViewAppend(TextView textView, String string) {
        currentTime = Calendar.getInstance().getTime();

        final TextView ftextView = textView;
        final CharSequence fstring = dateFormat.format(currentTime) + " --> " + string;
        fragmentActivity.runOnUiThread(new Runnable() {
            @Override public void run() {
                Settings.TERMINAL_TEXT += fstring;
                ftextView.setText(Settings.TERMINAL_TEXT);
            }
        });
    }

    public void UpdateText(TextView _terminalText) {
        terminalText = _terminalText;
    }
}
