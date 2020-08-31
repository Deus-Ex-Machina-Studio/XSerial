package com.dm3d.xserial.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.dm3d.xserial.R;
import com.dm3d.xserial.Settings;
import com.dm3d.xserial.UsbTerminal;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel = new HomeViewModel(root);

        homeViewModel.startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickStart();
            }
        });

        homeViewModel.stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickStop();
            }
        });

        homeViewModel.sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickSend();
            }
        });

        homeViewModel.clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickClear();
            }
        });

        homeViewModel.sendText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Settings.LAST_COMMAND = editable.toString();
                Settings.Save();
            }
        });

        if (Settings.USB_TERMINAL == null) Settings.USB_TERMINAL = new UsbTerminal(homeViewModel.terminalText, getActivity());
        else Settings.USB_TERMINAL.UpdateText(homeViewModel.terminalText);

        return root;
    }

    public void onClickStart() {
        Settings.USB_TERMINAL.Connection();
    }

    public void onClickStop() {
        Settings.USB_TERMINAL.Disconnection();
    }

    public void onClickSend() {
        Settings.USB_TERMINAL.Send(homeViewModel.sendText.getText().toString());
    }

    public void onClickClear() {
        Settings.TERMINAL_TEXT = "";
        homeViewModel.terminalText.setText(Settings.TERMINAL_TEXT);
        Settings.Save();
    }
}