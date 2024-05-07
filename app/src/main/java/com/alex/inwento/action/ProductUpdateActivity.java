package com.alex.inwento.action;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alex.inwento.R;
import com.alex.inwento.database.RoomDB;
import com.alex.inwento.dialog.MoveProductDialog;
import com.alex.inwento.dialog.ScrapProductDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.managers.FilterMng;
import com.alex.inwento.managers.SettingsMng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductUpdateActivity extends AppCompatActivity {
    private static final String TAG = "ProductMoveActivity";
    SettingsMng settingsMng;
    int action;
    RoomDB roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_update);
        roomDB = RoomDB.getInstance(this);
        settingsMng = new SettingsMng(this);
        action = getIntent().getIntExtra("ACTION", 1);

        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action_bar_code));
        registerReceiver(myBroadcastReceiver, filter);


    }


    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "BroadcastReceiver");
            String action = intent.getAction();
            assert action != null;
            if (action.equals(getResources().getString(R.string.activity_intent_filter_action_bar_code))) {
                try {
                    handleScanResult(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };




    private void handleScanResult(Intent initiatingIntent) {
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        if (decodedSource == null) {
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }

        if (settingsMng.isFilter()) {
            if (FilterMng.filteringData(decodedData, settingsMng) == null) {
                Toast.makeText(this, "Code doesn't match the established pattern", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        sendGetFullProductRequest(decodedData);

    }


    private void openMoveProductDialog(ProductDTO productDTO) {
        MoveProductDialog dialog = MoveProductDialog.newInstance(
                settingsMng.getAccessToken(),
                productDTO,
                roomDB);
        dialog.show(getSupportFragmentManager(), "MoveProductDialog");
    }

    private void openScrapProductDialog(ProductDTO productDTO) {
        ScrapProductDialog dialog = ScrapProductDialog.newInstance(
                settingsMng.getAccessToken(),
                productDTO,
                roomDB);
        dialog.show(getSupportFragmentManager(), "ScrapProductDialog");
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    private void sendGetFullProductRequest(String barCode) {
        Log.i(TAG, "sendGetFullProductRequest bar code " + barCode);
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<ProductDTO> call;
        call = apiClient.getFullProductByCode("Bearer " + settingsMng.getAccessToken(), barCode, "null");
        call.enqueue(new Callback<ProductDTO>() {
            @Override
            public void onResponse(@NonNull Call<ProductDTO> call, @NonNull Response<ProductDTO> response) {
                if (response.isSuccessful()) {
                    if (action == 1) openMoveProductDialog(response.body());
                    else if (action == 2) openScrapProductDialog(response.body());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetFullProductRequest onFailure", t);
            }
        });
    }
}