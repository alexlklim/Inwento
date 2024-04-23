package com.alex.inwento.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.adapter.ProductAdapter;
import com.alex.inwento.dialog.ProductDialog;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.managers.SettingsMng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity
        extends
        AppCompatActivity
        implements
        ProductAdapter.OnItemProductClickListener {
    private static final String TAG = "SearchActivity";
    SettingsMng settingsMng;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    List<ProductShortDTO> productDtoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        settingsMng = new SettingsMng(this);

        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);

        // replace it with taking data from DB
        sendGetProductsRequest();


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
        productAdapter = new ProductAdapter(false, false, productDtoList, this);
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
        if (productId != null) call = apiClient.getFullProductById("Bearer " + settingsMng.getAccessToken(), productId);
        else call = apiClient.getFullProductByCode("Bearer " + settingsMng.getAccessToken(), barCode, "null");

        call.enqueue(new Callback<ProductDTO>() {
            @Override
            public void onResponse(@NonNull Call<ProductDTO> call, @NonNull Response<ProductDTO> response) {
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
        Log.i(TAG, "initializeRecyclerView: ");
        ProductDialog.newInstance(productDTO).show(getSupportFragmentManager(), "product_dialog");
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
                    Log.e(TAG, "BroadcastReceiver FAIL");
                }
            }
        }
    };


    private void handleScanResult(Intent initiatingIntent) {
        Log.i(TAG, "handleScanResult");
        String decodedSource = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_source));
        String decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data));
        if (decodedSource == null) {
            decodedData = initiatingIntent.getStringExtra(getResources().getString(R.string.datawedge_intent_key_data_legacy));
        }
        sendGetFullProductRequest(decodedData, null);
    }


}