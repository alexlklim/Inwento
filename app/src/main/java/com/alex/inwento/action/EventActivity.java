package com.alex.inwento.action;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.adapter.UnknownProductAdapter;
import com.alex.inwento.dialog.ProductDialog;
import com.alex.inwento.dialog.UnknownProductDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.EventDTO;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.http.inventory.UnknownProductDTO;
import com.alex.inwento.managers.SettingsMng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity
        implements
        ProductAdapter.OnItemProductClickListener,
        ProductDialog.ProductDialogListener,
        UnknownProductDialog.UnknownProductScannedListener {
    private static final String TAG = "EventActivity";


    Button btnScanned, btnNotScanned, btnNew;

    RecyclerView recyclerViewProduct, recyclerViewUnknownProduct;
    SettingsMng settingsMng;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    UnknownProductAdapter unknownProductAdapter;
    ProductAdapter productAdapter;
    EventDTO event;
    Spinner branchesSpinner;
    TextView aeBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event);
        settingsMng = new SettingsMng(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // check if user want to send data about gps to server during inventory
        getLastLocation();


        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action_bar_code));
        registerReceiver(myBroadcastReceiver, filter);


        btnScanned = findViewById(R.id.btnScanned);
        btnNotScanned = findViewById(R.id.btnNotScanned);
        btnNew = findViewById(R.id.btnNew);
        aeBranch = findViewById(R.id.aeBranch);
        branchesSpinner = findViewById(R.id.aeLocations);
        recyclerViewProduct = findViewById(R.id.rv_products);
        recyclerViewUnknownProduct = findViewById(R.id.rv_unknown_products);


        btnScanned.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            initializeProductRecycler(event.getScannedProducts(), true);
        });

        btnNotScanned.setOnClickListener(view -> {
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
            initializeProductRecycler(event.getNotScannedProducts(), false);
        });
        btnNew.setOnClickListener(view -> {
            initializeNewProductRecycler(event.getUnknownProducts());
            setVisibilityToRecyclers(recyclerViewUnknownProduct, recyclerViewProduct);
        });



        sendGetEventById(getIntent().getIntExtra("EVENT_ID", 0));


    }


    private void sendGetEventById(int eventId) {
        Log.e(TAG, "sendGetEventById " + eventId);
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<EventDTO> call = apiClient.getEventById("Bearer " + settingsMng.getAccessToken(), eventId);
        call.enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDTO> call, @NonNull Response<EventDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    event = response.body();
                    initialize();
                } else Log.e(TAG, "Something wrong:");
            }

            @Override
            public void onFailure(@NonNull Call<EventDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetEventById onFailure", t);
            }
        });
    }

    private void initialize() {
        Log.i(TAG, "initialize");
        aeBranch.setText(event.getBranch());
        btnScanned.setText(R.string.scanned);
        String notScanned = getString(R.string.to_scan) + (event.getTotalProductAmount() - event.getScannedProductAmount());
        String scanned = getString(R.string.new_unknown) + event.getUnknownProductAmount();
        btnNotScanned.setText(notScanned);
        btnNew.setText(scanned);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                Arrays.asList("Loc 1", "Loc 2"));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchesSpinner.setAdapter(adapter);

        initializeProductRecycler(event.getNotScannedProducts(), false);
        setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
    }

    public void initializeProductRecycler(List<ProductShortDTO> productShortDTOS, Boolean isScanned) {
        productAdapter = new ProductAdapter(true, isScanned, productShortDTOS, this);
        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(productLayoutManager);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    public void initializeNewProductRecycler(List<UnknownProductDTO> unknownProductDTOS) {
        unknownProductAdapter = new UnknownProductAdapter(unknownProductDTOS);
        LinearLayoutManager unknownProductLayoutManager = new LinearLayoutManager(this);
        recyclerViewUnknownProduct.setLayoutManager(unknownProductLayoutManager);
        recyclerViewUnknownProduct.setAdapter(unknownProductAdapter);
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
            if (action.equals(getResources().getString(R.string.activity_intent_filter_action_bar_code))) {
                try {
                    handleScanResult(intent);
                } catch (Exception e) {
                    Log.e(TAG, "BroadcastReceiver FAIL");
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
            if (location != null) currentLocation = location;
        });
    }

    private void handleScanResult(Intent initiatingIntent) {
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        if (decodedSource == null) {
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }

        System.out.println("Label: " + decodedLabelType);


        if (ProductShortDTO.doesProductExist(event.getScannedProducts(), decodedData) || UnknownProductDTO.doesProductExist(event.getUnknownProducts(), decodedData)) {
            showToast();
        } else sendGetFullProductRequest(decodedData, null);

    }


    private void sendGetFullProductRequest(String barCode, Integer productId) {
        Log.i(TAG, "sendGetFullProductRequest");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<ProductDTO> call;
        if (productId != null)
            call = apiClient.getFullProductById("Bearer " + settingsMng.getAccessToken(), productId);
        else
            call = apiClient.getFullProductByCode("Bearer " + settingsMng.getAccessToken(), barCode, "null");

        call.enqueue(new Callback<ProductDTO>() {
            @Override
            public void onResponse(@NonNull Call<ProductDTO> call, @NonNull Response<ProductDTO> response) {
                if (response.isSuccessful()) openProductDialog(response.body());
                else openUnknownProductDialog(barCode);
            }

            @Override
            public void onFailure(@NonNull Call<ProductDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetFullProductRequest onFailure", t);
            }
        });
    }

    private void openUnknownProductDialog(String barCode) {
        Log.i(TAG, "openUnknownProductDialog");
        UnknownProductDialog.newInstance(this, barCode).show(getSupportFragmentManager(), "new_product_dialog");
    }

    private void openProductDialog(ProductDTO productDTO) {
        Log.i(TAG, "openProductDialog");
        ProductDialog.newInstance(this, event.getBranch(), true, productDTO).show(getSupportFragmentManager(), "product_dialog");

    }

    private void showToast() {
        Toast.makeText(this, "Product is already scanned", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemProductClick(int orderId) {
        Log.i(TAG, "onItemProductClick");
        sendGetFullProductRequest(null, orderId);
    }


    @Override
    public void onSentScannedProduct(ProductDTO productDTO) {
        Log.i(TAG, "onSentScannedProduct");
        sendPutScannedProductProductRequest(productDTO, null);
        ProductShortDTO dto = ProductShortDTO.getIndexByIdInList(
                event.getNotScannedProducts(),
                productDTO.getId());
        if (dto != null) {
            event.getNotScannedProducts().remove(dto);
            event.getScannedProducts().add(dto);

            event.setScannedProductAmount(event.getScannedProductAmount() + 1);
            String notScanned = "DO ZESKANOWANIA \n" + (event.getTotalProductAmount() - event.getScannedProductAmount());
            btnNotScanned.setText(notScanned);

            initializeProductRecycler(event.getNotScannedProducts(), false);
            setVisibilityToRecyclers(recyclerViewProduct, recyclerViewUnknownProduct);
        }


    }

    @Override
    public void onSentScannedUnknownProduct(String barCode) {
        Log.i(TAG, "onSentScannedUnknownProduct");
        sendPutScannedProductProductRequest(null, barCode);
        if (!UnknownProductDTO.doesProductExist(event.getUnknownProducts(), barCode)) {
            event.getUnknownProducts().add(new UnknownProductDTO(barCode, "EAN"));
        }
        initializeNewProductRecycler(event.getUnknownProducts());
        setVisibilityToRecyclers(recyclerViewUnknownProduct, recyclerViewProduct);
        event.setUnknownProductAmount(event.getUnknownProductAmount() + 1);
        String unknown = "NOWE\n" + event.getUnknownProductAmount();
        btnNew.setText(unknown);
    }


    private void sendPutScannedProductProductRequest(ProductDTO dto, String barCode) {
        Log.i(TAG, "sendPutScannedProductProductRequest");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (dto == null) {
            map.put("code", barCode);
        } else {
            map.put("id", dto.getId());
            if (dto.getBarCode() != null) map.put("code", dto.getBarCode());
        }
        map.put("longitude", currentLocation.getLongitude());
        map.put("latitude", currentLocation.getLatitude());
        list.add(map);

        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<Void> call = apiClient.putScannedCode("Bearer " + settingsMng.getAccessToken(),
                event.getId(), 1, list);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "senPutScannedProductProductRequest onFailure", t);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}