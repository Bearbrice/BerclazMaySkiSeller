package com.example.berclazmayskiseller.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.berclazmayskiseller.R;

public class BriceFragment extends Fragment {
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_brice, container, false);
        return view;
    }
}
