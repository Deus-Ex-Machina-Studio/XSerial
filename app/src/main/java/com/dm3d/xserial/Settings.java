package com.dm3d.xserial;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

public class Settings {
    public static int BAUDRATE = 300;
    public static int BAUDRATE_INDEX = 0;
    public static int STOP_BIT = 1;
    public static int DATA_BIT = 8;
    public static int PARITY = 0;

    public static UsbTerminal USB_TERMINAL = null;

    public static String TERMINAL_TEXT = "";
    public static String LAST_COMMAND = "";

    public static String SHARED_PREFERENCES_NAME = "Settings";
    public static SharedPreferences SHARED_PREFERENCES = null;

    public static void Save() {
        SharedPreferences.Editor editor = Settings.SHARED_PREFERENCES.edit();

        editor.putString("TERMINAL_TEXT", TERMINAL_TEXT);
        editor.putString("LAST_COMMAND", LAST_COMMAND);
        editor.putInt("BAUDRATE", BAUDRATE);
        editor.putInt("BAUDRATE_INDEX", BAUDRATE_INDEX);
        editor.putInt("STOP_BIT", STOP_BIT);
        editor.putInt("DATA_BIT", DATA_BIT);
        editor.putInt("PARITY", PARITY);

        editor.apply();
    }

    public static void Load() {
        if (SHARED_PREFERENCES.contains("TERMINAL_TEXT")) TERMINAL_TEXT = SHARED_PREFERENCES.getString("TERMINAL_TEXT", "");
        if (SHARED_PREFERENCES.contains("TERMINAL_TEXT")) LAST_COMMAND = SHARED_PREFERENCES.getString("LAST_COMMAND", "");
        if (SHARED_PREFERENCES.contains("BAUDRATE")) BAUDRATE = SHARED_PREFERENCES.getInt("BAUDRATE", 300);
        if (SHARED_PREFERENCES.contains("BAUDRATE_INDEX")) BAUDRATE_INDEX = SHARED_PREFERENCES.getInt("BAUDRATE_INDEX", 0);
        if (SHARED_PREFERENCES.contains("STOP_BIT")) STOP_BIT = SHARED_PREFERENCES.getInt("STOP_BIT", 1);
        if (SHARED_PREFERENCES.contains("DATA_BIT")) DATA_BIT = SHARED_PREFERENCES.getInt("DATA_BIT", 8);
        if (SHARED_PREFERENCES.contains("PARITY")) PARITY = SHARED_PREFERENCES.getInt("PARITY", 0);
    }
}
