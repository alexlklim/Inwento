package com.alex.inwento.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alex.inwento.R;
import com.alex.inwento.dto.AuthDto;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.LoginTask;

public class LoginActivity extends AppCompatActivity
        implements LoginTask.LoginListener{
    private static final String TAG = "LoginActivity";

    SettingsMng settingsMng;
    EditText login, password;

    CheckBox is_remember_me;

    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settingsMng = new SettingsMng(this);

        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        is_remember_me = findViewById(R.id.is_remember_me);


        // Set the checkbox state based on the stored value
        is_remember_me.setChecked(settingsMng.getIsRememberMe());
        if (settingsMng.getIsRememberMe()) {
            login.setText(settingsMng.getEmail());
            password.setText(settingsMng.getPassword());
        }

        // Set listener for checkbox state change
        is_remember_me.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                settingsMng.setIsRememberMe(true);
                settingsMng.setLoginDetails(login.getText().toString(), password.getText().toString());
            } else {
                settingsMng.setIsRememberMe(false);
                settingsMng.setLoginDetails("", ""); // Clear saved login details
            }
        });

        btn_login.setOnClickListener(v -> {
            LoginTask loginTask = new LoginTask(this, login.getText().toString(), password.getText().toString());
            loginTask.execute();
        });
    }


    @Override
    public void onLoginSuccess(String response) {
        Log.i(TAG, "Login Successfully + ");
        AuthDto authDto = JsonMng.getAuthDtoFromResponse(response);
        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
        assert authDto != null;
        settingsMng.setAuthInfo(
                authDto.getFirstName(), authDto.getLastName(),
                authDto.getAccessToken(), authDto.getRefreshToken());
        finish();
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        Log.i(TAG, errorMessage);
        Toast.makeText(this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}