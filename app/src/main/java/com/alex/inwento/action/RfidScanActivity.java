package com.alex.inwento.action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.managers.SettingsMng;

import java.util.List;

public class RfidScanActivity extends AppCompatActivity {
    private static final String TAG = "RfidScanActivity";
    SettingsMng settingsMng;
    String branch;
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
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));

        if (decodedSource != null && decodedData != null) {
            Log.i(TAG, "Source: " + decodedSource + ", Data: " + decodedData);
            // Process the source and data as needed
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

}