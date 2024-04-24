package com.alex.inwento.action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.UnknownProductAdapter;
import com.alex.inwento.http.inventory.UnknownProductDTO;
import com.alex.inwento.managers.SettingsMng;

import java.util.ArrayList;
import java.util.List;

public class RfidScanActivity extends AppCompatActivity {
    private static final String TAG = "RfidScanActivity";
    SettingsMng settingsMng;

    UnknownProductAdapter unknownProductAdapter;
    RecyclerView recyclerViewUnknownProduct;
    Button btnSend;
    String branch;
    List<String> rfidCodes;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid_scan);
        settingsMng = new SettingsMng(this);


        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);

        branch = (String) getIntent().getSerializableExtra("BRANCH_NAME");
        eventId = getIntent().getIntExtra("EVENT_ID", 0);


        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(view -> sendCodesToServerRequest(rfidCodes));

        // recycler view
        unknownProductAdapter = new UnknownProductAdapter(convertToUnknownProductDTO(rfidCodes));
        LinearLayoutManager unknownProductLayoutManager = new LinearLayoutManager(this);
        recyclerViewUnknownProduct.setLayoutManager(unknownProductLayoutManager);
        recyclerViewUnknownProduct.setAdapter(unknownProductAdapter);
    }



    private void sendCodesToServerRequest(List<String> rfidCodes) {
        System.out.println(rfidCodes.toString());
    }

    private List<UnknownProductDTO> convertToUnknownProductDTO(List<String> codes){
        List<UnknownProductDTO> list = new ArrayList<>();
        for (String code: codes){
            UnknownProductDTO dto = new UnknownProductDTO(code, "RFID");
            list.add(dto);
        }
        return  list;
    }


    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "BroadcastReceiver");
            String action = intent.getAction();
            assert action != null;
            if (action.equals(getResources().getString(R.string.activity_intent_filter_action))) {
                try {
                    handleScanResult(intent);
                } catch (Exception e) {
                    Log.e(TAG, "BroadcastReceiver FAIL");
                }
            }
        }
    };


    private void handleScanResult(Intent initiatingIntent) {
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        if (decodedSource != null && decodedData != null) {
            String[] codes = decodedData.split("\\r?\\n");
            for (String code : codes) {
                Log.i(TAG, "RFID code: " + code.trim());
                rfidCodes.add(code.trim());
            }
        }
        unknownProductAdapter.updateData(convertToUnknownProductDTO(rfidCodes));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

}