package com.alex.inwento.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alex.inwento.MainActivity;
import com.alex.inwento.R;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.auth.AuthDTO;
import com.alex.inwento.http.auth.LoginDTO;
import com.alex.inwento.managers.NetworkClient;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.util.Endpoints;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    SettingsMng settingsMng;
    EditText etLogin, etPassword, etServerAddress;

    CheckBox isRememberMe;

    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        settingsMng = new SettingsMng(this);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        isRememberMe = findViewById(R.id.isRememberMe);

        btnLogin.setOnClickListener(v -> {
            sendLoginRequest();
        });


        // Set the checkbox state based on the stored value
        isRememberMe.setChecked(settingsMng.getIsRememberMe());
        if (settingsMng.getIsRememberMe()) {
            etLogin.setText(settingsMng.getEmail());
            etPassword.setText(settingsMng.getPassword());
        }

        // Set listener for checkbox state change
        isRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                settingsMng.setIsRememberMe(true);
                settingsMng.setLoginDetails(etLogin.getText().toString(), etPassword.getText().toString());
            } else {
                settingsMng.setIsRememberMe(false);
                settingsMng.setLoginDetails("", "");
            }
        });

    }
    private void sendLoginRequest() {
        Log.e(TAG, "sendLoginRequest");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        LoginDTO dto = new LoginDTO(settingsMng.getEmail(), settingsMng.getPassword());
        Call<AuthDTO> call = apiClient.getAuthDTOLogin(dto);
        call.enqueue(new Callback<AuthDTO>() {
            @Override
            public void onResponse(@NonNull Call<AuthDTO> call, @NonNull Response<AuthDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    settingsMng.setAuthInfo(response.body());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else Log.e(TAG, getString(R.string.login_failed));
            }
            @Override
            public void onFailure(@NonNull Call<AuthDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendLoginRequestRetrofit onFailure", t);
            }
        });
    }


}