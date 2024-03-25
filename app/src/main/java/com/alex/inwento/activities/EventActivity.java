package com.alex.inwento.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.database.domain.Event;
import com.alex.inwento.database.domain.Product;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.ProductsTask;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity
        implements ProductsTask.ProductsListener,
        ProductAdapter.OnItemProductClickListener {
    private static final String TAG = "EventActivity";

    ImageButton btnSynch;
    Button btnShowAll, btnShowScanned, btnShowNotScanned, btnShowUnknown;
    RecyclerView recyclerView;

    SettingsMng settingsMng;

    ProductAdapter productAdapter;
    Event event;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        settingsMng = new SettingsMng(this);

        initializeButtons();
        setOnClickListenersToBtn();

        Intent intent = getIntent();
        eventId = intent.getIntExtra("EVENT_ID", 0);

        ProductsTask productsTask = new ProductsTask(this, settingsMng.getAccessToken(), eventId);
        productsTask.execute();
    }

    private void setOnClickListenersToBtn() {
        btnShowAll.setOnClickListener(view -> {
            setGreenColorToBtn(btnShowAll);
            setGrayColorToBtn(btnShowScanned, btnShowNotScanned, btnShowUnknown);
            initializeRecyclerView(event.getProducts());

        });
        btnShowScanned.setOnClickListener(view -> {
            setGreenColorToBtn(btnShowScanned);
            setGrayColorToBtn(btnShowAll, btnShowNotScanned, btnShowUnknown);
            initializeRecyclerView(filterProductsByStatus(event.getProducts(), "SCANNED"));

        });

        btnShowNotScanned.setOnClickListener(view -> {
            setGreenColorToBtn(btnShowNotScanned);
            setGrayColorToBtn(btnShowScanned, btnShowAll, btnShowUnknown);
            initializeRecyclerView(filterProductsByStatus(event.getProducts(), "NOT_SCANNED"));

        });
        btnShowUnknown.setOnClickListener(view -> {
            setGreenColorToBtn(btnShowUnknown);
            setGrayColorToBtn(btnShowScanned, btnShowNotScanned, btnShowAll);

        });
    }

    private void initializeButtons() {
        btnShowAll = findViewById(R.id.ev_show_all);
        setGreenColorToBtn(btnShowAll);
        btnShowScanned = findViewById(R.id.ev_show_scanned);
        btnShowNotScanned = findViewById(R.id.ev_show_not_scanned);
        btnShowUnknown = findViewById(R.id.ev_show_unknown);
        btnSynch = findViewById(R.id.ev_synch);

    }

    private void setGreenColorToBtn(Button button) {
        button.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
    }

    private void setGrayColorToBtn(Button... buttons) {
        for (Button button : buttons) {
            button.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }


    private void initializeRecyclerView(List<Product> products) {
        recyclerView = findViewById(R.id.rv_products);
        productAdapter = new ProductAdapter(products, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);
    }


    @Override
    public void onProductsSuccess(Event eventWithProducts) {
        Log.i(TAG, "onProductsSuccess: + " + eventWithProducts.toString());
        event = eventWithProducts;
        initializeRecyclerView(event.getProducts());
    }

    @Override
    public void onProductsFailure(String errorMessage) {
        Log.i(TAG, "onProductsFailure: + " + errorMessage);
        finish();
    }

    @Override
    public void onItemProductClick(int orderId) {
        Log.e(TAG, "onItemClick: + " + orderId);
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("PRODUCT_ID", orderId);
        startActivity(intent);

    }

    private List<Product> filterProductsByStatus(List<Product> products, String status) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (status.equals(product.getInventoryStatus())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }
}