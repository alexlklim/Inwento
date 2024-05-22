package com.alex.inwento.activities;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.alex.inwento.database.RoomDB;
import com.alex.inwento.database.domain.ProductLocation;
import com.alex.inwento.dialog.ProductDialog;
import com.alex.inwento.dialog.ResultDialog;
import com.alex.inwento.dialog.UnknownProductDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.EventDTO;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.http.inventory.UnknownProductDTO;
import com.alex.inwento.managers.FilterMng;
import com.alex.inwento.managers.SettingsMng;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity
        implements
        ProductAdapter.OnItemProductClickListener,
        ProductDialog.ProductDialogListener,
        UnknownProductDialog.UnknownProductScannedListener,
        ResultDialog.ResultDialogListener {
    private static final String TAG = "EventActivity";
    private Button btnScanned, btnNotScanned;
    private RecyclerView recyclerViewProduct, recyclerViewUnknownProduct;
    private Spinner locationSpinner;
    private UnknownProductAdapter unknownProductAdapter;
    private ProductAdapter productAdapter;
    private SettingsMng settingsMng;
    private RoomDB roomDB;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    EventDTO event;

    private String chosenProductLocation;
    boolean isScanned, isLocationErrorDialogOpen;
    private String allLocations = "Wybierz lokalizacjię";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_event);
        settingsMng = new SettingsMng(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        roomDB = RoomDB.getInstance(this);
        getLastLocation();
        chosenProductLocation = allLocations;
        isScanned = false;
        isLocationErrorDialogOpen = false;

        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action_bar_code));
        registerReceiver(myBroadcastReceiver, filter);


        sendGetEventById(getIntent().getIntExtra("EVENT_ID", 0));


    }

    private void sendGetEventById(int eventId) {
        Log.i(TAG, "sendGetEventById " + eventId);
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<EventDTO> call = apiClient.getEventById("Bearer " + settingsMng.getAccessToken(), eventId);
        call.enqueue(new Callback<EventDTO>() {
            @Override
            public void onResponse(@NonNull Call<EventDTO> call, @NonNull Response<EventDTO> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    event = response.body();
                    firstInit();
                } else Log.e(TAG, "Something wrong:");
            }

            @Override
            public void onFailure(@NonNull Call<EventDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetEventById onFailure", t);
            }
        });
    }

    private void updateAmounts() {
        Log.i(TAG, "updateAmounts ");

        btnScanned.setText(getString(R.string.scanned) + "\n" +
                getProductAmountsForLocation(event.getScannedProducts(), chosenProductLocation));
        btnNotScanned.setText("POZOSTAŁE" + "\n" +
                getProductAmountsForLocation(event.getNotScannedProducts(), chosenProductLocation));
    }

    private void firstInit() {
        Log.i(TAG, "firstInit ");

        TextView aeBranch = findViewById(R.id.aeBranch);
        btnScanned = findViewById(R.id.btnScanned);
        btnNotScanned = findViewById(R.id.btnNotScanned);
        locationSpinner = findViewById(R.id.aeLocations);
        recyclerViewProduct = findViewById(R.id.rv_products);
        recyclerViewUnknownProduct = findViewById(R.id.rv_unknown_products);

        aeBranch.setText(event.getBranch());


        // init recyclers: new products
        unknownProductAdapter = new UnknownProductAdapter(event.getUnknownProducts());
        LinearLayoutManager unknownProductLayoutManager = new LinearLayoutManager(this);
        recyclerViewUnknownProduct.setLayoutManager(unknownProductLayoutManager);
        recyclerViewUnknownProduct.setAdapter(unknownProductAdapter);

        // init recycler products
        System.out.println(event.getNotScannedProducts());
        productAdapter = new ProductAdapter(this, true,
                isScanned, event.getNotScannedProducts(), this);
        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(productLayoutManager);
        recyclerViewProduct.setAdapter(productAdapter);

        // init location spinner
        List<String> locationsList = new ArrayList<>();
        List<ProductLocation> productLocationList = roomDB.locationDAO().getAllByBranchId(
                roomDB.branchDAO().getBranchByName(event.getBranch()).getId()
        );
        locationsList.add(allLocations);
        locationsList.addAll(productLocationList.stream().map(ProductLocation::getLocation).collect(Collectors.toList()));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.custom_spinner_item, locationsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);

        locationSpinner.setFocusable(false);

        initRecyclerProducts();


        btnScanned.setFocusable(false);


        btnScanned.setOnClickListener(view -> {
            isScanned = true;
            initRecyclerProducts();
        });

        btnNotScanned.setOnClickListener(view -> {
            isScanned = false;
            initRecyclerProducts();
        });

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                chosenProductLocation = (String) parentView.getItemAtPosition(position);
                isScanned = false;
                initRecyclerProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                chosenProductLocation = allLocations;
                isScanned = false;
                initRecyclerProducts();
            }
        });
    }



    private void initRecyclerProducts() {
        Log.i(TAG, "initRecyclerProducts ");
        List<ProductShortDTO> dtos = new ArrayList<>((isScanned) ?
                filterProductsByLocation(event.getScannedProducts(), chosenProductLocation) :
                filterProductsByLocation(event.getNotScannedProducts(), chosenProductLocation));
        productAdapter = new ProductAdapter(this, true, isScanned, dtos, this);
        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this);
        recyclerViewProduct.setLayoutManager(productLayoutManager);
        recyclerViewProduct.setAdapter(productAdapter);

        changeRecyclerVisibility(recyclerViewProduct, recyclerViewUnknownProduct);
        updateAmounts();
    }


    public List<ProductShortDTO> filterProductsByLocation(List<ProductShortDTO> allProducts, String location) {
        Log.d(TAG, "filterProductsByLocation ");
        if (location.equalsIgnoreCase(allLocations)) {
            return allProducts;
        } else {
            List<ProductShortDTO> dtos = new ArrayList<>();
            for (ProductShortDTO dto : allProducts) {
                if (dto.getLocation().equalsIgnoreCase(location)) {
                    dtos.add(dto);
                }
            }
            return dtos;
        }
    }

    private void initRecyclerNewProducts() {
        Log.i(TAG, "initRecyclerNewProducts ");

        unknownProductAdapter.updateData(new ArrayList<>(event.getUnknownProducts()));
        unknownProductAdapter.notifyDataSetChanged();
        changeRecyclerVisibility(recyclerViewUnknownProduct, recyclerViewProduct);
        updateAmounts();
    }


    private void changeRecyclerVisibility(RecyclerView visibleRV, RecyclerView invisibleRV) {
        Log.i(TAG, "changeRecyclerVisibility ");
        visibleRV.setVisibility(View.VISIBLE);
        invisibleRV.setVisibility(View.INVISIBLE);
    }


    private void openProductDialog(ProductDTO productDTO) {
        Log.i(TAG, "openProductDialog");
        // if product.branch != branch
        // if product.location != location



        ProductDialog
                .newInstance(this, event.getBranch(), chosenProductLocation, true, productDTO)
                .show(getSupportFragmentManager(), "product_dialog");
    }

    private void openUnknownProductDialog(String barCode) {
        Log.i(TAG, "openUnknownProductDialog");
        UnknownProductDialog
                .newInstance(this, barCode)
                .show(getSupportFragmentManager(), "new_product_dialog");
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

    private void handleScanResult(Intent initiatingIntent) {
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));
        if (decodedSource == null) {
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }
        if (chosenProductLocation.equalsIgnoreCase(allLocations)) {
            Log.e(TAG, "Location is not chosen : ");
            openLocationErrorDialog();
            return;
        }


        String[] codes = decodedData.split("\\r?\\n");
        for (String code : codes) {
            Log.e(TAG, "handle the code: " + code);

            if (settingsMng.isRfidScan()) {
                // if we use RFID scanner
                Log.e(TAG, "We use RFID scan: ");
                locationSpinner.clearFocus();

                // if code not match the pattern -> ignore
                // if code not found in this location and branch -> ignore
                // if code is already scanned in this location and branch -> ignore

                // if code not scanned in this location and branch -> send code to the server
                // update product recycler

                if (settingsMng.isFilter() && FilterMng.filteringData(code, settingsMng) == null) {
                    Log.e(TAG, "Code doesn't match the established pattern: " + code);
                    continue;
                }

                if (FilterMng.getProductByRFIDAndLocation(event.getScannedProducts(), code, chosenProductLocation) != null) {
                    Log.e(TAG, "Code already scanned for this location " + code);
                    continue;
                }
                if (FilterMng.getProductByRFIDAndLocation(event.getNotScannedProducts(), code, chosenProductLocation) != null) {
                    Log.e(TAG, "!!!!!!!!!!!!!!! code will be scanned" + code);
                    sendPutScannedProductByRfidRequest(code);
                }
                Log.e(TAG, "!!!!!!!!!!!!!!!  Code not found : " + code);
                locationSpinner.clearFocus();


            } else {
                // if we use Bar code scanner
                if (settingsMng.isFilter() && code != null) {
                    if (FilterMng.filteringData(code, settingsMng) == null) {
                        Toast.makeText(this, "Code doesn't match the established pattern", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (FilterMng.isProductExistsByBarCode(event.getScannedProducts(), code) ||
                        FilterMng.isUnknownProductExistsByBarCode(event.getUnknownProducts(), code)) {
                    Toast.makeText(this, "Product is already scanned", Toast.LENGTH_SHORT).show();
                } else sendGetFullProductRequest(code, null);

            }
        }

    }


    private void sendGetFullProductRequest(String barCode, Integer productId) {
        Log.i(TAG, "sendGetFullProductRequest");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<ProductDTO> call;
        if (productId != null)
            call = apiClient.getFullProductById(
                    "Bearer " + settingsMng.getAccessToken(),
                    productId);
        else
            call = apiClient.getFullProductByCode(
                    "Bearer " + settingsMng.getAccessToken(),
                    barCode);

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


    @Override
    public void onItemProductClick(int orderId) {
        Log.i(TAG, "onItemProductClick");
        sendGetFullProductRequest(null, orderId);
    }

    @Override
    public void onSentScannedProduct(ProductDTO productDTO) {
        Log.i(TAG, "onSentScannedProduct");
        sendPutScannedProductRequest(productDTO, null);
        if (chosenProductLocation.equalsIgnoreCase(allLocations)) {
            openLocationErrorDialog();
        } else {
            ProductShortDTO dto = FilterMng.getProductById(
                    event.getNotScannedProducts(),
                    productDTO.getId());
            if (dto != null) {
                event.getNotScannedProducts().remove(dto);
                event.getScannedProducts().add(dto);
                initRecyclerProducts();
            }
        }
    }

    @Override
    public void onSentScannedUnknownProduct(String barCode) {
        Log.i(TAG, "onSentScannedUnknownProduct");
        sendPutScannedProductRequest(null, barCode);
        if (chosenProductLocation.equalsIgnoreCase(allLocations)) {
            openLocationErrorDialog();
        } else {
            if (!FilterMng.isUnknownProductExistsByBarCode(event.getUnknownProducts(), barCode)) {
                event.getUnknownProducts().add(new UnknownProductDTO(barCode, "EAN"));
                initRecyclerNewProducts();
            }
        }
    }
    private void sendPutScannedProductByRfidRequest(String rfidCode) {
        Log.i(TAG, "sendPutScannedProductByRfidRequest");
        List<String> listOfRfidCodes = new ArrayList<>();
        listOfRfidCodes.add(rfidCode);

        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);

        Call<Void> call = apiClient.putScannedRfidCode(
                "Bearer " + settingsMng.getAccessToken(),
                event.getId(),
                listOfRfidCodes);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.e(TAG, "sendPutScannedProductByRfidRequest SUCCESS");

                if (chosenProductLocation.equalsIgnoreCase(allLocations)) {
                    openLocationErrorDialog();
                } else {
                    ProductShortDTO dto = FilterMng.getProductByRFIDAndLocation(event.getNotScannedProducts(), rfidCode, chosenProductLocation);
                    if (dto != null) {
                        event.getNotScannedProducts().remove(dto);
                        event.getScannedProducts().add(dto);
                        isScanned = false;
                        initRecyclerProducts();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "sendPutScannedProductByRfidRequest ON FAILURE", t);
            }
        });
    }


    private void sendPutScannedProductRequest(ProductDTO dto, String barCode) {
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
        if (chosenProductLocation.equalsIgnoreCase(allLocations)) return;
        Call<Void> call = apiClient.putScannedCode(
                "Bearer " + settingsMng.getAccessToken(),
                event.getId(),
                roomDB.locationDAO().getLocationByName(locationSpinner.getSelectedItem().toString()).getId(),
                list);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.e(TAG, "senPutScannedProductProductRequest SUCCESS");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "senPutScannedProductProductRequest ON FAILURE", t);
            }
        });
    }


    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    private int getProductAmountsForLocation(List<ProductShortDTO> dtos, String location) {
        Log.i(TAG, "getProductAmountsForLocation");
        if (location.equalsIgnoreCase(allLocations)) {
            return dtos.size();
        }
        return (int) dtos.stream()
                .filter(dto -> dto.getLocation().equalsIgnoreCase(location))
                .count();
    }


    private void openLocationErrorDialog() {
        Log.i(TAG, "openLocationErrorDialog");
        if (!isLocationErrorDialogOpen){
            isLocationErrorDialogOpen = true;
            ResultDialog
                    .newInstance("Lokalizacjia nie została wybrana", false, this)
                    .show(getSupportFragmentManager(), "location_error_dialog");
        }
    }

    @Override
    public void onOkClicked() {
        isLocationErrorDialogOpen = false;
    }
}