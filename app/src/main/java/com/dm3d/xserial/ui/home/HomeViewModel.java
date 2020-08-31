package com.dm3d.xserial.ui.home;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dm3d.xserial.R;
import com.dm3d.xserial.Settings;
import com.dm3d.xserial.UsbTerminal;

public class HomeViewModel extends ViewModel {
    public TextView terminalText = null;
    public EditText sendText = null;

    public Button startButton = null;
    public Button stopButton = null;
    public Button sendButton = null;
    public Button clearButton = null;

    public HomeViewModel(View root) {
        terminalText = root.findViewById(R.id.terminalText);
        terminalText.setText(Settings.TERMINAL_TEXT);

        sendText = root.findViewById(R.id.writableText);
        sendText.setText(Settings.LAST_COMMAND);

        startButton = root.findViewById(R.id.startButton);
        stopButton = root.findViewById(R.id.stopButton);
        sendButton = root.findViewById(R.id.sendButton);
        clearButton = root.findViewById(R.id.button4);
    }

    public void Save() {

    }

    public void Load() {

    }
}