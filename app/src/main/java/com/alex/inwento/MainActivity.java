package com.alex.inwento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.inwento.action.InventoryActivity;
import com.alex.inwento.action.LoginActivity;
import com.alex.inwento.action.SearchActivity;
import com.alex.inwento.action.SettingsActivity;
import com.alex.inwento.action.ProductUpdateActivity;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.auth.AuthDTO;
import com.alex.inwento.http.auth.RefreshTokenDTO;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.util.Endpoints;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ImageButton btnSearch, btnInventory, btnMove, btnScrap, btnSettings;
    SettingsMng settingsMng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        settingsMng = new SettingsMng(this);


        sendRefreshTokenRequest();



        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        btnInventory = findViewById(R.id.btnInventory);
        btnInventory.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, InventoryActivity.class)));

        btnMove = findViewById(R.id.btnMove);
        btnMove.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProductUpdateActivity.class);
            intent.putExtra("ACTION", 1);
            startActivity(intent);
        });


        btnScrap = findViewById(R.id.btnScrap);
        btnScrap.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProductUpdateActivity.class);
            intent.putExtra("ACTION", 2);
            startActivity(intent);
        });
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, SettingsActivity.class)));

    }


    private void sendRefreshTokenRequest() {
        Log.e(TAG, "sendRefreshTokenRequest");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        RefreshTokenDTO dto = new RefreshTokenDTO(settingsMng.getEmail(), settingsMng.getRefreshToken());
        Call<AuthDTO> call = apiClient.getAuthDTORefresh(dto);
        call.enqueue(new Callback<AuthDTO>() {
            @Override
            public void onResponse(@NonNull Call<AuthDTO> call, @NonNull Response<AuthDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    settingsMng.setAuthInfo(response.body());
                } else handleError(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<AuthDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendLoginRequestRetrofit onFailure", t);
            }
        });
    }

    private void handleError(int errorCode) {
        Log.e(TAG, "sendLoginRequestRetrofit onResponse: Error - " + errorCode);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}