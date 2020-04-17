package com.example.berclazmayskiseller.ui.login;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.berclazmayskiseller.BaseApp;
import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.database.repository.ClientRepository;
import com.example.berclazmayskiseller.ui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView emailView;
    private EditText passwordView;
    private ProgressBar progressBar;

    private ClientRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_login);

        setContentView(R.layout.activity_login);

        repository = ((BaseApp) getApplication()).getClientRepository();
        progressBar = findViewById(R.id.progress);

        // Set up the login form.
        emailView = findViewById(R.id.email);

        passwordView = findViewById(R.id.password);

        Button emailSignInButton = findViewById(R.id.email_sign_in_button);
        emailSignInButton.setOnClickListener(view -> attemptLogin());

        Button registerButton = findViewById(R.id.register_button);
        registerButton.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        //Get and set night mode
        SharedPreferences sharedPreferences = getSharedPreferences("darkMode", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    /**
     * Attempts to sign in or register the client specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            passwordView.setText("");
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            repository.signIn(email, password, task -> {
                if (task.isSuccessful()) {

                    /* Store the time of the connexion */
                    SharedPreferences sharedPref = getSharedPreferences("time", Context.MODE_PRIVATE);
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String connexion = formatter.format(date);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("timeSaved", connexion);
                    editor.commit();

                    /* Store the email connected */
                    sharedPref = getSharedPreferences("email", Context.MODE_PRIVATE);
                    editor = sharedPref.edit();
                    editor.putString("emailSaved", email);
                    editor.commit();

                    /* Store the token connected */
                    sharedPref = getSharedPreferences("token", Context.MODE_PRIVATE);
                    editor = sharedPref.edit();
                    editor.putString("tokenSaved", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    emailView.setText("");
                    passwordView.setText("");
                } else {
                    emailView.setError(getString(R.string.error_invalid_email));
                    emailView.requestFocus();
                    passwordView.setText("");
                }
                progressBar.setVisibility(View.GONE);
            });
        }
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}

