package com.example.berclazmayskiseller.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private String CHANNEL_ID = "test_notification  ";

    private TextView email;
    private TextView time;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Get shared preferences for the connexion time
        SharedPreferences sharedPref = getActivity().getSharedPreferences("time", Context.MODE_PRIVATE);
        String timeConnexion = sharedPref.getString("timeSaved", "NotFound");
        time = view.findViewById(R.id.tvTime);
        time.setText("Connected since : "+timeConnexion);

        //Get shared preferences for the email of the client connected
        sharedPref = getActivity().getSharedPreferences("email", Context.MODE_PRIVATE);
        String user = sharedPref.getString("emailSaved", "NotFound");
        email = view.findViewById(R.id.textViewEmail);
        email.setText(user);

        //DARK MODE SWITCH----------------------------------------
        Switch darkMode = view.findViewById(R.id.switch_darkMode);
        //Set switch state
        SharedPreferences sharedPref2 = getActivity().getSharedPreferences("darkMode", Context.MODE_PRIVATE);
        boolean darkMode_switchSaverIsChecked = sharedPref2.getBoolean("darkMode", false);
        darkMode.setChecked(darkMode_switchSaverIsChecked);
        //Set switch listener
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //Save switch state
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("darkMode", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("darkMode", isChecked);
                editor.commit();

                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button newBlockButton = (Button) getActivity().findViewById(
                R.id.button_logout);
        newBlockButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //Log out from firebase authentication
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
