package com.dm3d.xserial.ui.settings;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceFragmentCompat;

import com.dm3d.xserial.R;
import com.dm3d.xserial.Settings;
import com.dm3d.xserial.ui.settings.SettingsFragment;
import com.dm3d.xserial.ui.settings.SettingViewModel;

public class SettingsFragment extends Fragment {
    private SettingViewModel settingViewModel = null;

    private Spinner baudRateSpinner = null;
    private Button saveButton = null;
    private Button loadButton = null;

    public SettingsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        saveButton = root.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Settings.Save();
            }
        });

        loadButton = root.findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("SAVE", "" + Settings.BAUDRATE);
                UpdateSettings();
                Settings.Load();
            }
        });

        baudRateSpinner = root.findViewById(R.id.baudRateSpinner);

        baudRateSpinner.setOnItemSelectedListener (new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                Settings.BAUDRATE_INDEX = index;
                Settings.BAUDRATE = Integer.parseInt(getResources().getStringArray(R.array.baudrates)[Settings.BAUDRATE_INDEX]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        UpdateSettings();

        return root;
    }

    public void UpdateSettings() {
        baudRateSpinner.setSelection(Settings.BAUDRATE_INDEX);
    }
}