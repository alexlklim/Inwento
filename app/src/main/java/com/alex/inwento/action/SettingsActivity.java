package com.alex.inwento.action;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.inwento.R;
import com.alex.inwento.managers.SettingsMng;

public class SettingsActivity extends AppCompatActivity {

    TextView sName, sEmail;
    EditText sServerAddress, sPrefix, sSuffix, sPostfix, sLength, sLengthMax, sLengthMin;
    Button btnSave;
    CheckBox sFilter;
    SettingsMng sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
        sm = new SettingsMng(this);

        sName = findViewById(R.id.sName);
        sEmail = findViewById(R.id.sEmail);
        sServerAddress = findViewById(R.id.sServerAddress);
        sFilter = findViewById(R.id.sFilter);
        sPrefix = findViewById(R.id.sPrefix);
        sSuffix = findViewById(R.id.sSuffix);
        sPostfix = findViewById(R.id.sPostfix);
        sLength = findViewById(R.id.sLength);
        sLengthMax = findViewById(R.id.sLengthMax);
        sLengthMin = findViewById(R.id.sLengthMin);
        btnSave = findViewById(R.id.btnSave);

        setValues();

        btnSave.setOnClickListener(view -> applyNewConfig());

    }

    private void setValues() {
        sName.setText(sm.getFirstname() + " " + sm.getLastname());
        sEmail.setText(sm.getEmail());
        sServerAddress.setText(sm.getServerAddress());

        sFilter.setChecked(sm.isFilter());

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

}