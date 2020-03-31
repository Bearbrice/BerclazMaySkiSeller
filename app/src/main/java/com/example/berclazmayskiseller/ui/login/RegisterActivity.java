package com.example.berclazmayskiseller.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.berclazmayskiseller.ui.MainActivity;
import com.example.berclazmayskiseller.R;
import com.example.berclazmayskiseller.db.async.client.CreateClient;
import com.example.berclazmayskiseller.db.entity.ClientEntity;
import com.example.berclazmayskiseller.db.util.OnAsyncEventListener;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private Toast toast;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etPwd1;
    private EditText etPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeForm();
        toast = Toast.makeText(this, getString(R.string.client_created), Toast.LENGTH_LONG);

        //Get and set night mode
        SharedPreferences sharedPreferences = getSharedPreferences("darkMode", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("darkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void initializeForm() {
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etPwd1 = findViewById(R.id.password);
        etPwd2 = findViewById(R.id.passwordRep);
        Button saveBtn = findViewById(R.id.editButton);
        saveBtn.setOnClickListener(view -> saveChanges(
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etEmail.getText().toString(),
                etPwd1.getText().toString(),
                etPwd2.getText().toString()
        ));
    }

    private void saveChanges(String firstName, String lastName, String email, String pwd, String pwd2) {
        if (!pwd.equals(pwd2) || pwd.length() < 5) {
            etPwd1.setError(getString(R.string.error_invalid_password));
            etPwd1.requestFocus();
            etPwd1.setText("");
            etPwd2.setText("");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getString(R.string.error_invalid_email));
            etEmail.requestFocus();
            return;
        }
        ClientEntity newClient = new ClientEntity(email, firstName, lastName, pwd);

        new CreateClient(getApplication(), new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "createUserWithEmail: success");
                setResponse(true);

                SharedPreferences sharedPref = getSharedPreferences("email", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("emailSaved", email);
                editor.commit();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "createUserWithEmail: failure", e);
                setResponse(false);
            }
        }).execute(newClient);
    }

    private void setResponse(Boolean response) {
        if (response) {
//            final SharedPreferences.Editor editor = getSharedPreferences(BaseActivity.PREFS_NAME, 0).edit();
//            editor.putString(BaseActivity.PREFS_USER, etEmail.getText().toString());
//            editor.apply();
            toast.show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            etEmail.setError(getString(R.string.error_used_email));
            etEmail.requestFocus();
        }
    }
}
