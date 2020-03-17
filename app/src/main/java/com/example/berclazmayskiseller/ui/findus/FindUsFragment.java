package com.example.berclazmayskiseller.ui.findus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.ui.map.MapsActivity;

public class FindUsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findus, container, false);

        Button button_displayProducts = (Button) view.findViewById(R.id.button_verbier);
        button_displayProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MapsActivity.class);
                in.putExtra("some", "some data");
                startActivity(in);
            }
        });

        return view;
    }
}
