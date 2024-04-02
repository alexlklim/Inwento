package com.alex.inwento.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.alex.inwento.dialog.ProductScannedDialog;
import com.alex.inwento.dialog.UnknownProductDialog;
import com.alex.inwento.dto.ProductDto;
import com.alex.inwento.managers.SettingsMng;
import com.alex.inwento.tasks.GetProductListTask;
import com.alex.inwento.tasks.GetProductTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class EventActivity
        extends AppCompatActivity
        implements
        GetProductListTask.ProductsListener,
        ProductAdapter.OnItemProductClickListener,
        UnknownProductAdapter.OnItemUnknownProductClickListener,
        GetProductTask.GetProductByBarCodeListener,
        ProductScannedDialog.ProductScannedListener,
        UnknownProductDialog.UnknownProductScannedListener {
    private static final String TAG = "EventActivity";

    ImageButton btnSynch;
    Button btnShowAll, btnShowScanned, btnShowNotScanned, btnShowUnknown;
    RecyclerView recyclerViewProduct, recyclerViewUnknownProduct;
    String decodedData;
    SettingsMng settingsMng;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    UnknownProductAdapter unknownProductAdapter;
    ProductAdapter productAdapter;
    Event event;
    int eventId;

    TextView ev_branch_name;

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

        GetProductListTask getProductListTask = new GetProductListTask(this, settingsMng.getAccessToken(), eventId);
        getProductListTask.execute();
    }

    private void setOnClickListenersToBtn() {
        Log.i(TAG, "setOnClickListenersToBtn: ");
        btnShowAll.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            setGrayColorToBtn(btnShowAll);
            setBlackColorToBtn(btnShowScanned, btnShowNotScanned, btnShowUnknown);
            initializeRecyclerView(event.getProducts());

        });
        btnShowScanned.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            setGrayColorToBtn(btnShowScanned);
            setBlackColorToBtn(btnShowAll, btnShowNotScanned, btnShowUnknown);
            initializeRecyclerView(filterProductsByStatus(event.getProducts(), "SCANNED"));
        });

        btnShowNotScanned.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            setGrayColorToBtn(btnShowNotScanned);
            setBlackColorToBtn(btnShowScanned, btnShowAll, btnShowUnknown);
            initializeRecyclerView(filterProductsByStatus(event.getProducts(), "NOT_SCANNED"));
        });
        btnShowUnknown.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewUnknownProduct, recyclerViewProduct);
            setGrayColorToBtn(btnShowUnknown);
            setBlackColorToBtn(btnShowScanned, btnShowNotScanned, btnShowAll);
        });
    }

    private void initializeButtons() {
        Log.i(TAG, "initializeButtons: ");
        btnShowAll = findViewById(R.id.ev_show_all);
        btnShowScanned = findViewById(R.id.ev_show_scanned);
        btnShowNotScanned = findViewById(R.id.ev_show_not_scanned);
        btnShowUnknown = findViewById(R.id.ev_show_unknown);
        btnSynch = findViewById(R.id.ev_synch);
        ev_branch_name = findViewById(R.id.ev_branch_name);
    }

    private void setGrayColorToBtn(Button button) {
        int btnColor = getResources().getColor(R.color.btnColorDark);
        button.setBackgroundTintList(ColorStateList.valueOf(btnColor));
    }

    private void setBlackColorToBtn(Button... buttons) {
        int btnColor = getResources().getColor(R.color.btnColor);
        for (Button button : buttons) {
            button.setBackgroundTintList(ColorStateList.valueOf(btnColor));
        }
    }


    private void initializeRecyclerView(List<Product> products) {
        Log.i(TAG, "initializeRecyclerView: + " + event.getProducts().size());

        ev_branch_name.setText(event.getBranch());
        event.setTotalProductsAmount(event.getProducts().size());
        event.setScannedProductsAmount((int) event.getProducts()
                .stream()
                .filter(one -> one.getInventoryStatus().equals("SCANNED"))
                .count());
        btnShowAll.setText("all (" + event.getTotalProductsAmount() + ")");
        btnShowScanned.setText("ok (" + event.getScannedProductsAmount() + ")");
        btnShowNotScanned.setText("to scan (" + (event.getTotalProductsAmount() - event.getScannedProductsAmount()) + ")");

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
        Log.i(TAG, "onProductsSuccess: + ");
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
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
            }
        });
    }

    private void handleScanResult(Intent initiatingIntent) {
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        if (decodedSource == null) {
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC: " + decodedData);

        boolean isProductAlreadyScanned = false;
        // send request
        // check if product already scanned
        for (UnknownProduct unknownProduct : event.getUnknownProducts()) {
            if (unknownProduct.getCode().equalsIgnoreCase(decodedData)) {
                Toast.makeText(this, "Product already scanned: ", Toast.LENGTH_SHORT).show();
                isProductAlreadyScanned = true;
            }
        }
        for (Product product : event.getProducts()) {
            if (product.getBarCode().equalsIgnoreCase(decodedData) && product.getInventoryStatus().equalsIgnoreCase("SCANNED")) {
                Toast.makeText(this, "Product already scanned: ", Toast.LENGTH_SHORT).show();
                isProductAlreadyScanned = true;
            }
        }
        if (!isProductAlreadyScanned) {
            GetProductTask getProductTask = new GetProductTask(this, settingsMng.getAccessToken(), decodedData);
            getProductTask.execute();
        }


    }


    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }


    @Override
    public void onProductByBarCodeSuccess(ProductDto productDto) {
        Log.i(TAG, "onProductByBarCodeSuccess");
        boolean found = event.getProducts().stream().anyMatch(product -> product.getBarCode().equalsIgnoreCase(productDto.getBar_code()));
        ProductScannedDialog.newInstance(this, productDto, found, eventId, settingsMng.getAccessToken())
                .show(getSupportFragmentManager(), "product_scanned_dialog");
    }


    @Override
    public void onProductByBarCodeFailure(String errorMessage) {
        Log.e(TAG, "onProductByBarCodeFailure: ");
        UnknownProductDialog.newInstance(this, decodedData, eventId, settingsMng.getAccessToken())
                .show(getSupportFragmentManager(), "product_scanned_dialog");
    }


    // listener for dialog  (if product saved, send this code to server (it is not matter is it existing or unknown product)
    @Override
    public void onProductSaved(String code) {
        Log.i(TAG, "onProductSaved");
        sendCodeToServer(code);
        for (Product product : event.getProducts()) {
            if (product.getBarCode().equalsIgnoreCase(code)) {
                product.setInventoryStatus("SCANNED");
                initializeRecyclerView(event.getProducts());
            }
        }
    }

    @Override
    public void onUnknownProductSaved(String code) {
        Log.i(TAG, "onUnknownProductSaved");
        sendCodeToServer(code);
        // save code to unknown products
        event.getUnknownProducts().add(new UnknownProduct(0, code));

        initializeRecyclerView(event.getProducts());
        setVisibilityToRecyclers(recyclerViewUnknownProduct, recyclerViewProduct);
        setGrayColorToBtn(btnShowUnknown);
        setBlackColorToBtn(btnShowScanned, btnShowNotScanned, btnShowAll);
    }


    public void sendCodeToServer(String code) {
        Log.i(TAG, "saveProduct");
        // send code to the server


    }
}