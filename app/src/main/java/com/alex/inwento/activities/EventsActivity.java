package com.alex.inwento.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.EventAdapter;
import com.alex.inwento.database.domain.Event;
import com.alex.inwento.database.domain.Inventory;
import com.alex.inwento.dialog.AddEventDialog;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.AddEventTask;
import com.alex.inwento.tasks.CheckInventoryActiveTask;
import com.alex.inwento.tasks.GetEventsTask;
import com.alex.inwento.tasks.GetInventoryTask;

import java.util.List;

public class EventsActivity extends AppCompatActivity
        implements
        CheckInventoryActiveTask.CheckInventoryActiveListener,
        GetInventoryTask.InventoryListener,
        GetEventsTask.EventsListener,
        EventAdapter.OnItemClickListener,
        AddEventTask.AddEventListener,
        AddEventDialog.AddEventListener{
    private static final String TAG = "InventoryActivity";
    SettingsMng settingsMng;
    RecyclerView recyclerView;

    EventAdapter eventAdapter;
    TextView tvStartData, tvProductAmount;
    ImageButton btnAddEvent;
    int inventoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        settingsMng = new SettingsMng(this);
        tvStartData = findViewById(R.id.ai_start_data);
        tvProductAmount = findViewById(R.id.ai_product_amount);
        btnAddEvent = findViewById(R.id.ai_btn_add_event);

        btnAddEvent.setOnClickListener(view -> {
            Log.i(TAG, "setOnClickListener");

            AddEventDialog dialog = AddEventDialog.newInstance(this,
                    settingsMng.getAccessToken(),
                    settingsMng.getFirstname(),
                    settingsMng.getLastname());
            dialog.show(getSupportFragmentManager(), "AddEventDialog");
        });

        // check if inventory is active now
        CheckInventoryActiveTask checkInventoryActiveTask = new CheckInventoryActiveTask(this, settingsMng.getAccessToken());
        checkInventoryActiveTask.execute();


    }


    @Override
    protected void onResume() {
        super.onResume();
        GetInventoryTask getInventoryTask = new GetInventoryTask(this, settingsMng.getAccessToken());
        getInventoryTask.execute();
    }


    @Override
    public void onCheckInventoryActiveResult(boolean isActive) {
        Log.i(TAG, "onCheckInventoryActiveResult: + " + isActive);
        if (!isActive) {
            finish();
//            startActivity(new Intent(InventoryActivity.this, InventoriesActivity.class));
        }
        GetInventoryTask getInventoryTask = new GetInventoryTask(this, settingsMng.getAccessToken());
        getInventoryTask.execute();
    }

    @Override
    public void onInventorySuccess(Inventory inventory) {
        Log.i(TAG, "onInventorySuccess: + " + inventory.getId());
        inventoryId = inventory.getId();
        System.out.println(inventory.toString());
        tvStartData.setText("Data: " + inventory.getStartDate());
        tvProductAmount.setText("Produkty " + inventory.getScannedProductAmount() + " / " + inventory.getTotalProductAmount());
        GetEventsTask getEventsTask = new GetEventsTask(this, settingsMng.getAccessToken(), inventory.getId());
        getEventsTask.execute();
    }

    @Override
    public void onInventoryFailure(String errorMessage) {
        Log.i(TAG, "onInventoryFailure: + " + errorMessage);

    }

    @Override
    public void onEventsSuccess(List<Event> events) {
        Log.i(TAG, "onEventsSuccess: + ");
        initializeRecyclerView(events);
    }

    @Override
    public void onEventsFailure(String errorMessage) {
        Log.i(TAG, "onEventsFailure: + " + errorMessage);
    }


    private void initializeRecyclerView(List<Event> events) {
        recyclerView = findViewById(R.id.rv_events);

        eventAdapter = new EventAdapter(events, this);
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
    public void onAddEventSuccess(Boolean answer) {
        Log.i(TAG, "onAddEventSuccess: + " + answer);

    }

    @Override
    public void onAddEventFailure(String errorMessage) {
        Log.i(TAG, "onAddEventSuccess: + " + errorMessage);

    }

    @Override
    public void onAddEventSave(Boolean result) {
        Log.i(TAG, "onAddEventSave: + ");

        CheckInventoryActiveTask checkInventoryActiveTask = new CheckInventoryActiveTask(this, settingsMng.getAccessToken());
        checkInventoryActiveTask.execute();

    }
}