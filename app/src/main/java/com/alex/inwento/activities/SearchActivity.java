package com.alex.inwento.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.database.RoomDB;
import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.ProductLocation;
import com.alex.inwento.dialog.ProductDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.managers.FilterMng;
import com.alex.inwento.managers.SettingsMng;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity
        extends
        AppCompatActivity
        implements
        ProductAdapter.OnItemProductClickListener,
        ProductDialog.ProductDialogListener {
    private static final String TAG = "SearchActivity";
    SettingsMng settingsMng;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    Spinner asBranch, asLocations;
    List<ProductShortDTO> productDtoList;
    RoomDB roomDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        settingsMng = new SettingsMng(this);
        roomDB = RoomDB.getInstance(this);

        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action_bar_code));
        registerReceiver(myBroadcastReceiver, filter);


        asBranch = findViewById(R.id.asBranch);
        asLocations = findViewById(R.id.asLocations);


        // replace it with taking data from DB
        sendGetProductsRequest();

        // Clear focus from RecyclerView
        View rootLayout = findViewById(android.R.id.content);
        rootLayout.requestFocus();
    }


    List<Branch> branchList;
    List<ProductLocation> productLocationList;

    List<String> branches, locations;


    private void initBranchSpinners(){
        branchList = roomDB.branchDAO().getAll();
        branches = branchList.stream().map(Branch::getBranch).collect(Collectors.toList());


        ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branches);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        asBranch.setAdapter(adapterBranch);

        initLocationSpinner();
        asBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                initLocationSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    private void initLocationSpinner() {
        int branchId = roomDB.branchDAO().getBranchByName(asBranch.getSelectedItem().toString()).getId();
        locations.add("Wszystkie");
        locations.addAll(roomDB.locationDAO().getAllByBranchId(branchId)
                .stream()
                .map(ProductLocation::getLocation)
                .collect(Collectors.toList()));

        ArrayAdapter<String> adapterLocation = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locations);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        asLocations.setAdapter(adapterLocation);


//        initializeRecyclerView();
    }

    private void sendGetProductsRequest() {
        Log.e(TAG, "sendGetProductsRequest");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<List<ProductShortDTO>> call = apiClient.getShortProducts("Bearer " + settingsMng.getAccessToken());
        call.enqueue(new Callback<List<ProductShortDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductShortDTO>> call, @NonNull Response<List<ProductShortDTO>> response) {
                if (response.isSuccessful()) {
                    productDtoList = response.body();
                    initializeRecyclerView(productDtoList);
                } else Log.e(TAG, "Something wrong:");
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductShortDTO>> call, @NonNull Throwable t) {
                Log.e(TAG, "sendLoginRequestRetrofit onFailure", t);
            }
        });
    }


    private void initializeRecyclerView(List<ProductShortDTO> productDtoList) {
        Log.i(TAG, "initializeRecyclerView: ");
        recyclerView = findViewById(R.id.rv_products);
        productAdapter = new ProductAdapter(this, false, false, productDtoList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productAdapter);

    }

    @Override
    public void onItemProductClick(int orderId) {
        Log.i(TAG, "onItemProductClick: " + orderId);
        sendGetFullProductRequest(null, orderId);
    }

    private void sendGetFullProductRequest(String barCode, Integer productId) {
        Log.i(TAG, "sendGetFullProductRequest bar code " + barCode);
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Call<ProductDTO> call;
        System.out.println("TOKEN : " + settingsMng.getAccessToken());
        System.out.println("BAR_CODE : " + barCode);
        System.out.println("BAR_CODE : " + productId);

        if (productId != null) {
            call = apiClient.getFullProductById(
                    "Bearer " + settingsMng.getAccessToken(),
                    productId);
        }
        else {
            call = apiClient.getFullProductByCode(
                    "Bearer " + settingsMng.getAccessToken(),
                    barCode);
        }

        call.enqueue(new Callback<ProductDTO>() {
            @Override
            public void onResponse(@NonNull Call<ProductDTO> call, @NonNull Response<ProductDTO> response) {
                recyclerView.clearFocus();
                if (response.isSuccessful()) openProductDialog(response.body());
                else showToast();
            }

            @Override
            public void onFailure(@NonNull Call<ProductDTO> call, @NonNull Throwable t) {
                Log.e(TAG, "sendGetFullProductRequest onFailure", t);
            }
        });
    }

    private void showToast() {
        Log.e(TAG, "Something failed:");
        Toast.makeText(this, "Product wasn't found ", Toast.LENGTH_SHORT).show();
    }


    public void openProductDialog(ProductDTO productDTO) {
        if (!isFinishing() && !isDestroyed()) {
            Log.i(TAG, "openProductDialog: ");
            System.out.println(productDTO);
            ProductDialog.newInstance(this, "null", null, false, productDTO).show(getSupportFragmentManager(), "product_dialog");
        } else {
            Log.e(TAG, "Activity is finishing or destroyed, cannot show dialog.");
        }
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
        productAdapter.setHandlingScanEvent(true);
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        String decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type));

        if (decodedSource == null) {
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
            decodedLabelType = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_label_type_legacy));
        }
        System.out.println("____________________");
        System.out.println(decodedData);

        sendGetFullProductRequest(decodedData, null);
        recyclerView.clearFocus();

        productAdapter.setHandlingScanEvent(false);
        recyclerView.clearFocus();
    }



    @Override
    public void onSentScannedProduct(ProductDTO productDTO) {
    }
}