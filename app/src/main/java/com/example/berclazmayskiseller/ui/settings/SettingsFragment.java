package com.example.berclazmayskiseller.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;


import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.ui.home.HomeFragment;
import com.example.berclazmayskiseller.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

import static com.example.berclazmayskiseller.ui.AddFragment.addFragment;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private String CHANNEL_ID = "test_notification  ";

    private TextView email;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Get shared preferences for switches
        SharedPreferences sharedPref = getActivity().getSharedPreferences("email", Context.MODE_PRIVATE);
        String user = sharedPref.getString("emailSaved", "NotFound");
        email = view.findViewById(R.id.textViewEmail);
        email.setText(user);

//        String defaultValue = String.valueOf(getResources().getText(R.string.user_connected));
//        String actualValue = sharedPref.getString(String.valueOf(R.string.user_connected), defaultValue);
//        email = view.findViewById(R.id.textViewEmail);
//        email.setText(actualValue);

//        String user = (String) getResources().getText(R.string.user_connected);
//        email.setText(user);
//        view.findViewById(R.id.textViewEmail);

        //DARK MODE SWITCH----------------------------------------
        Switch darkMode = view.findViewById(R.id.switch_darkMode);
        //Set switch state
        SharedPreferences sharedPref2 = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean darkMode_defaultValue = getResources().getBoolean(R.bool.darkMode_default_value);
        boolean darkMode_switchSaverIsChecked = sharedPref2.getBoolean(String.valueOf(R.bool.darkMode_checked), darkMode_defaultValue);
        darkMode.setChecked(darkMode_switchSaverIsChecked);
        //Set switch listener
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Save switch state
                SharedPreferences.Editor editor = sharedPref2.edit();
                editor.putBoolean(String.valueOf(R.bool.darkMode_checked), isChecked);
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
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
