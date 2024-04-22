package com.alex.inwento.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.dialog.ProductScannedDialog;
import com.alex.inwento.dto.ProductDto;
import com.alex.inwento.managers.SettingsMng;

import java.util.List;

public class SearchActivity
        extends
        AppCompatActivity
        implements
        ProductScannedDialog.ProductScannedListener {
    private static final String TAG = "SearchActivity";
    String decodedData;
    SettingsMng settingsMng;
    RecyclerView recyclerView;


    List<ProductDto> productDtoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        settingsMng = new SettingsMng(this);




        // for getting and filtering codes
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getResources().getString(R.string.activity_intent_filter_action));
        registerReceiver(myBroadcastReceiver, filter);

    }


    private void initializeRecyclerView(List<ProductDto> productDtoList) {
        Log.i(TAG, "initializeRecyclerView: ");
        recyclerView = findViewById(R.id.rv_short_products);
        productV2Adapter = new ProductV2Adapter(productDtoList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(productV2Adapter);

    }


    @Override
    public void onItemProductV2Click(int orderId) {
        Log.i(TAG, "onItemProductV2Click: ");
        openProductDialog(productDtoList.get(orderId));

    }


    public void openProductDialog(ProductDto productDto) {
        Log.i(TAG, "initializeRecyclerView: ");
        ProductScannedDialog.newInstance(this, productDto, true, 1, settingsMng.getAccessToken(), true)
                .show(getSupportFragmentManager(), "product_scanned_dialog");
    }



    @Override
    public void onProductSaved(ProductDto productDto) {
        Log.i(TAG, "onProductSaved: ");
    }


    // for scanner

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

        ProductDto dto = ProductDto.hasProductWithBarcode(productDtoList, decodedData);
        if (dto != null) openProductDialog(dto);
    }


}