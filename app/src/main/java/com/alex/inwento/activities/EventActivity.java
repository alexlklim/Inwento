package com.alex.inwento.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.adapter.UnknownProductAdapter;
import com.alex.inwento.database.domain.Event;
import com.alex.inwento.database.domain.Product;
import com.alex.inwento.database.domain.UnknownProduct;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.ProductsTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

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
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
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
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);


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

    private final BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        // get events (codes)
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "BroadcastReceiver");
            String action = intent.getAction();
            assert action != null;
            if (action.equals(getResources().getString(R.string.activity_intent_filter_action))) {
                try {
                    handleScanResult(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private void getLastLocation() {
        Log.i(TAG, "getLastLocation");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            int FINE_PERMISSION_CODE = 1;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;}
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {if (location != null) {currentLocation = location;}});
    }

    private void handleScanResult(Intent initiatingIntent) {
        // get code and label type from event
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        if (decodedSource == null) {
            Log.d(TAG, "handleScanResult: decodedSource == null");
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }
        System.out.println("CCCCCCCCCCCCCCCCCCCC: " + decodedData);
        handleCode(decodedData);

    }

    private void handleCode(String code) {
        boolean found = false;
        for (Product product : event.getProducts()) {
            if (product.getBarCode().equalsIgnoreCase(code)) {
                product.setInventoryStatus("SCANNED");
                found = true;
                break;
            }
        }
        if (!found) event.getUnknownProducts().add(new UnknownProduct(0, code));

        initializeRecyclerView(event.getProducts());

    }


    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}