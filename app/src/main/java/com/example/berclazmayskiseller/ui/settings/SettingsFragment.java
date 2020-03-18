package com.example.berclazmayskiseller.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.example.berclazmayskiseller.MainActivity;
import com.example.berclazmayskiseller.R;

public class SettingsFragment extends Fragment {

    private String CHANNEL_ID = "test_notification  ";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Get shared preferences for switches
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        //Switch creator
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
            }
        });

        //Switch creator
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
            }
        });

        //Switch creator
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

                            String confirmationMessage = "Confirmation sended to " + emailAddressEntered;
                            SpannableStringBuilder biggerConfirmationMessage = new SpannableStringBuilder(confirmationMessage);
                            biggerConfirmationMessage.setSpan(new RelativeSizeSpan(1.35f), 0, confirmationMessage.length(), 0);
                            Toast confirmationToast = Toast.makeText(getContext(), biggerConfirmationMessage, Toast.LENGTH_LONG);
                            confirmationToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);

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

        return view;
    }
}
