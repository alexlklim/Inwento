package com.alex.inwento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.inwento.activities.EventsActivity;
import com.alex.inwento.activities.LoginActivity;
import com.alex.inwento.activities.ProductUpdateActivity;
import com.alex.inwento.dto.AuthDto;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.AuthTask;

public class MainActivity extends AppCompatActivity implements AuthTask.AuthListener {
    private static final String TAG = "MainActivity";
    ImageButton btn_settings, btn_home, btn_search;
    Button btn_inventory, btn_scrap, btn_moved;
    SettingsMng settingsMng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingsMng = new SettingsMng(this);

        initializeButtons();

        AuthTask authTask = new AuthTask(this, settingsMng.getEmail(), settingsMng.getRefreshToken());
        authTask.execute();


    }


    @Override
    public void onAuthSuccess(String response) {
        Log.i(TAG, "onAuthSuccess");
        AuthDto authDto = JsonMng.getAuthDtoFromResponse(response);
        Toast.makeText(this, "Auth successfully", Toast.LENGTH_SHORT).show();
        assert authDto != null;
        settingsMng.setAuthInfo(
                authDto.getFirstName(), authDto.getLastName(),
                authDto.getAccessToken(), authDto.getRefreshToken());
    }

    @Override
    public void onAuthFailure(String errorMessage) {
        Log.i(TAG, "onAuthSuccess: " + errorMessage);
        Toast.makeText(this, "Login required" + errorMessage, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }


    private void addBtnListeners() {


        btn_home.setOnClickListener(view -> {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                }
        );

        btn_inventory.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, EventsActivity.class))
        );

        btn_moved.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProductUpdateActivity.class);
            intent.putExtra("ACTION", 1);
            startActivity(intent);
        });
        btn_scrap.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProductUpdateActivity.class);
            intent.putExtra("ACTION", 2);
            startActivity(intent);
        });

    }

    private void initializeButtons() {
        btn_settings = findViewById(R.id.btn_settings);
        btn_home = findViewById(R.id.btn_home);
        btn_search = findViewById(R.id.btn_search);
        btn_inventory = findViewById(R.id.btn_inventory);
        btn_scrap = findViewById(R.id.btn_scrap);
        btn_moved = findViewById(R.id.btn_moved);

        addBtnListeners();
    }


}