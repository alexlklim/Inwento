package com.alex.inwento.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.adapter.UnknownProductAdapter;
import com.alex.inwento.database.domain.Event;
import com.alex.inwento.database.domain.Product;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.ProductsTask;

import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity
        implements ProductsTask.ProductsListener,
        ProductAdapter.OnItemProductClickListener,
        UnknownProductAdapter.OnItemUnknownProductClickListener {
    private static final String TAG = "EventActivity";

    ImageButton btnSynch;
    Button btnShowAll, btnShowScanned, btnShowNotScanned, btnShowUnknown;
    RecyclerView recyclerViewProduct, recyclerViewUnknownProduct;

    SettingsMng settingsMng;

    UnknownProductAdapter unknownProductAdapter;
    ProductAdapter productAdapter;
    Event event;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
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
        Log.i(TAG, "setOnClickListenersToBtn: ");
        btnShowAll.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            initializeRecyclerView(event.getProducts());

        });
        btnShowScanned.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            initializeRecyclerView(filterProductsByStatus(event.getProducts(), "SCANNED"));
        });

        btnShowNotScanned.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            initializeRecyclerView(filterProductsByStatus(event.getProducts(), "NOT_SCANNED"));
        });
        btnShowUnknown.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewUnknownProduct, recyclerViewProduct);
        });
    }

    private void initializeButtons() {
        Log.i(TAG, "initializeButtons: ");
        btnShowAll = findViewById(R.id.ev_show_all);
        btnShowScanned = findViewById(R.id.ev_show_scanned);
        btnShowNotScanned = findViewById(R.id.ev_show_not_scanned);
        btnShowUnknown = findViewById(R.id.ev_show_unknown);
        btnSynch = findViewById(R.id.ev_synch);

    }


    private void initializeRecyclerView(List<Product> products) {
        Log.i(TAG, "initializeRecyclerView: + " + products.size());

// Initialize RecyclerView for products
        recyclerViewProduct = findViewById(R.id.rv_products);
        productAdapter = new ProductAdapter(products, this);
        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(productLayoutManager);
        recyclerViewProduct.setAdapter(productAdapter);

// Initialize RecyclerView for unknown products
        recyclerViewUnknownProduct = findViewById(R.id.rv_unknown_products);
        unknownProductAdapter = new UnknownProductAdapter(event.getUnknownProducts(), this);
        LinearLayoutManager unknownProductLayoutManager = new LinearLayoutManager(this);
        recyclerViewUnknownProduct.setLayoutManager(unknownProductLayoutManager);
        recyclerViewUnknownProduct.setAdapter(unknownProductAdapter);

        setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);

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
        Log.e(TAG, "onItemProductClick: + " + orderId);
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("PRODUCT_ID", orderId);
        startActivity(intent);

    }

    private List<Product> filterProductsByStatus(List<Product> products, String status) {
        Log.e(TAG, "filterProductsByStatus: + " + status);
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (status.equals(product.getInventoryStatus())) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    @Override
    public void onItemUnknownProductClick(int orderId) {
        Log.e(TAG, "onItemUnknownProductClick: + " + orderId);
    }

    public void setVisibilityToRecyclers(RecyclerView visibleRV, RecyclerView invisibleRV) {
        visibleRV.setVisibility(View.VISIBLE);
        invisibleRV.setVisibility(View.INVISIBLE);
    }
}