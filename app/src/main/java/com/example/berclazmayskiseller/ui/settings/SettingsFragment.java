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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;


import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private String CHANNEL_ID = "test_notification  ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Get shared preferences for switches
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        //NOTIFICATIONS SWITCH----------------------------------------------
        Switch notifications = view.findViewById(R.id.switch_notifications);
        //Set switch state
        boolean notifications_defaultValue = getResources().getBoolean(R.bool.notifications_default_value);
        boolean notifications_switchSaverIsChecked = sharedPref.getBoolean(String.valueOf(R.bool.notifications_checked), notifications_defaultValue);
        notifications.setChecked(notifications_switchSaverIsChecked);
        //Set switch listener
        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Save switch state
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(String.valueOf(R.bool.notifications_checked), isChecked);
                editor.commit();

                if (isChecked) {
                    Snackbar.make(getView(), "Notifications activated.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        //POSITION SWITCH-----------------------------------------
        Switch position = view.findViewById(R.id.switch_position);
        //Set switch state
        boolean position_defaultValue = getResources().getBoolean(R.bool.position_default_value);
        boolean position_switchSaverIsChecked = sharedPref.getBoolean(String.valueOf(R.bool.position_checked), position_defaultValue);
        position.setChecked(position_switchSaverIsChecked);
        //Set switch listener
        position.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Save switch state
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(String.valueOf(R.bool.position_checked), isChecked);
                editor.commit();

                if (isChecked) {
                    Snackbar.make(getView(), "Position activated.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        //NEWSLETTER SWITCH------------------------------------------------
        final Switch newsletter = view.findViewById(R.id.switch_newsletter);
        //Set switch state
        boolean newsletter_defaultValue = getResources().getBoolean(R.bool.newsletter_default_value);
        boolean newsletter_switchSaverIsChecked = sharedPref.getBoolean(String.valueOf(R.bool.newsletter_checked), newsletter_defaultValue);
        newsletter.setChecked(newsletter_switchSaverIsChecked);
        //Set switch listener
        newsletter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Save switch state
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(String.valueOf(R.bool.newsletter_checked), isChecked);
                editor.commit();

                if (isChecked) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Enter your email address");

                    // Set up the input
                    final EditText input = new EditText(getContext());
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String emailAddressEntered = "";
                            emailAddressEntered = input.getText().toString();

                            //Setting the confirmation message
                            String confirmationMessage = "Confirmation sended to " + emailAddressEntered;
                            SpannableStringBuilder biggerConfirmationMessage = new SpannableStringBuilder(confirmationMessage);
                            biggerConfirmationMessage.setSpan(new RelativeSizeSpan(1.35f), 0, confirmationMessage.length(), 0);
                            Toast confirmationToast = Toast.makeText(getContext(), biggerConfirmationMessage, Toast.LENGTH_LONG);
                            confirmationToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);

                            //Setting the error message
                            String errorMessage = emailAddressEntered + " is not a valid mail.";
                            SpannableStringBuilder biggerErrorMessage = new SpannableStringBuilder(errorMessage);
                            biggerErrorMessage.setSpan(new RelativeSizeSpan(1.35f), 0, errorMessage.length(), 0);
                            Toast wrongMailToast = Toast.makeText(getContext(), biggerErrorMessage, Toast.LENGTH_LONG);
                            wrongMailToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);

                            //SEND MAIL ALGO
                            //TOAST WILL BE SEND ACCORDING TO EXCEPTION OF THE MAIL

                            if (emailAddressEntered.contains("@")) {
                                confirmationToast.show();
                            } else {
                                wrongMailToast.show();
                                newsletter.setChecked(false);
                            }
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newsletter.setChecked(false);
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            }
        });

        //DARK MODE SWITCH----------------------------------------
        Switch darkMode = view.findViewById(R.id.switch_darkMode);
        //Set switch state
        boolean darkMode_defaultValue = getResources().getBoolean(R.bool.darkMode_default_value);
        boolean darkMode_switchSaverIsChecked = sharedPref.getBoolean(String.valueOf(R.bool.darkMode_checked), darkMode_defaultValue);
        darkMode.setChecked(darkMode_switchSaverIsChecked);
        //Set switch listener
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Save switch state
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
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
        newBlockButton.setOnClickListener((View.OnClickListener) this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        System.out.println("DISCONNECTED");
    }
}
