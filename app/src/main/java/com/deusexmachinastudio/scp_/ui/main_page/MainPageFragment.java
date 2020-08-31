package com.deusexmachinastudio.scp_.ui.main_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.deusexmachinastudio.scp_.R;
import com.deusexmachinastudio.scp_.ui.home.HomeViewModel;

public class MainPageFragment extends Fragment {
    private MainPageViewModel main_pageViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main_pageViewModel = ViewModelProviders.of(this).get(MainPageViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_page, container, true);
        return root;
    }
}
