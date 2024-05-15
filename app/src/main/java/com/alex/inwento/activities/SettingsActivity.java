package com.alex.inwento.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.inwento.R;
import com.alex.inwento.database.RoomDB;
import com.alex.inwento.dialog.ResultDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.DataDTO;
import com.alex.inwento.managers.SettingsMng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity
implements ResultDialog.ResultDialogListener{
    private static final String TAG = "SettingsActivity";

    TextView sName, sEmail;
    EditText sServerAddress, sPrefix, sSuffix, sPostfix, sLength, sLengthMax, sLengthMin;
    Button btnSave;
    CheckBox sFilter, sIsRfidScan;
    SettingsMng sm;
    RoomDB roomDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        sm = new SettingsMng(this);
        roomDB = RoomDB.getInstance(this);


        sName = findViewById(R.id.sName);
        sEmail = findViewById(R.id.sEmail);
        sServerAddress = findViewById(R.id.sServerAddress);
        sFilter = findViewById(R.id.sFilter);
        sIsRfidScan = findViewById(R.id.sIsRfidScan);
        sPrefix = findViewById(R.id.sPrefix);
        sSuffix = findViewById(R.id.sSuffix);
        sPostfix = findViewById(R.id.sPostfix);
        sLength = findViewById(R.id.sLength);
        sLengthMax = findViewById(R.id.sLengthMax);
        sLengthMin = findViewById(R.id.sLengthMin);
        btnSave = findViewById(R.id.btnSave);

        setValues();
        sendGetDataRequest();

        btnSave.setOnClickListener(view -> {
            applyNewConfig();
            openResultDialog();
        });

    }

    private void openResultDialog() {
        Log.i(TAG, "openResultDialog");
        runOnUiThread(() -> {
            ResultDialog
                    .newInstance("Nowe ustawienia zachowane", true, this)
                    .show(getSupportFragmentManager(), "result_dialog");
        });
    }


    private void setValues() {
        sName.setText(sm.getFirstname() + " " + sm.getLastname());
        sEmail.setText(sm.getEmail());
        sServerAddress.setText(sm.getServerAddress());

        sFilter.setChecked(sm.isFilter());
        sIsRfidScan.setChecked(sm.isRfidScan());

        sPrefix.setText(sm.getCodePrefix());
        sSuffix.setText(sm.getCodeSuffix());
        sPostfix.setText(sm.getCodePostfix());

        sLength.setText(Integer.toString(sm.getCodeLength()));
        sLengthMax.setText(Integer.toString(sm.getCodeMaxLength()));
        sLengthMin.setText(Integer.toString(sm.getCodeMinLength()));

    }


    public void applyNewConfig() {
        sm.setIsFilter(sFilter.isChecked());
        sm.setServerAddress(sServerAddress.getText().toString());
        sm.setIsRfidScan(sIsRfidScan.isChecked());
        sm.setCodeSettings(
                sPrefix.getText().toString(),
                sSuffix.getText().toString(),
                sPostfix.getText().toString(),
                parseInteger(sLength.getText().toString()),
                parseInteger(sLengthMax.getText().toString()),
                parseInteger(sLengthMin.getText().toString()));
    }

    private Integer parseInteger(String value) {
        return TextUtils.isEmpty(value) ? null : Integer.parseInt(value);
    }


    private void sendGetDataRequest() {
        Log.e(TAG, "sendGetDataRequest");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<DataDTO> call = apiClient.getFields("Bearer " + sm.getAccessToken());
        call.enqueue(new Callback<DataDTO>() {
            @Override
            public void onResponse(@NonNull Call<DataDTO> call, @NonNull Response<DataDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    DataDTO dto = response.body();
                    System.out.println(dto);
                    roomDB.branchDAO().insert(dto.getBranches());
                    roomDB.locationDAO().insert(dto.getProductLocations());
                    roomDB.employeeDAO().insert(dto.getEmployees());
                }
                else Log.e(TAG, "Get data failed:");
            }
            @Override
            public void onFailure(@NonNull Call<DataDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendLoginRequestRetrofit onFailure", t);
            }
        });
    }


    @Override
    public void onOkClicked() {

    }
}