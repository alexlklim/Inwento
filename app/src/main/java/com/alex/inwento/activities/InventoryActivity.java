package com.alex.inwento.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.EventAdapter;
import com.alex.inwento.dialog.ResultDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.InventoryDTO;
import com.alex.inwento.managers.SettingsMng;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryActivity extends AppCompatActivity
        implements
        EventAdapter.OnItemClickListener,
        ResultDialog.ResultDialogListener {
    private static final String TAG = "InventoryActivity";
    SettingsMng settingsMng;
    RecyclerView recyclerView;


    EventAdapter eventAdapter;
    TextView tvStartData, tvProductAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inventory);
        settingsMng = new SettingsMng(this);
        sendGetCurrentInventory();
    }


    public void openInventoryNotActiveDialog() {
        runOnUiThread(() -> {
            ResultDialog
                    .newInstance("Inventaryzacja została zakończona", false, this)
                    .show(getSupportFragmentManager(), "result_dialog");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGetCurrentInventory();
        // get data from DB about event
    }


    private void sendGetCurrentInventory() {
        Log.e(TAG, "sendGetCurrentInventory");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<InventoryDTO> call = apiClient.getCurrentInventory(
                "Bearer " + settingsMng.getAccessToken());
        call.enqueue(new Callback<InventoryDTO>() {
            @Override
            public void onResponse(@NonNull Call<InventoryDTO> call, @NonNull Response<InventoryDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    initialize(response.body());
                } else {
                    openInventoryNotActiveDialog();
                    Log.e(TAG, "Something wrong:");
                }
            }

            @Override
            public void onFailure(@NonNull Call<InventoryDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetCurrentInventory onFailure", t);
                openInventoryNotActiveDialog();
            }
        });
    }

    private void initialize(InventoryDTO inventoryDTO) {
        tvStartData = findViewById(R.id.aiDataInventory);
        tvProductAmount = findViewById(R.id.aiAmountProducts);

        tvStartData.setText(inventoryDTO.getStartDate());
        String amount = "Produkty " + inventoryDTO.getScannedProductAmount() + " / " + inventoryDTO.getTotalProductAmount();
        tvProductAmount.setText(amount);
        recyclerView = findViewById(R.id.rv_events);
        eventAdapter = new EventAdapter(inventoryDTO.getEvents(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(eventAdapter);
    }

    @Override
    public void onItemClick(int eventId) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("EVENT_ID", eventId);
        startActivity(intent);
    }

    @Override
    public void onOkClicked() {
        finish();
    }
}