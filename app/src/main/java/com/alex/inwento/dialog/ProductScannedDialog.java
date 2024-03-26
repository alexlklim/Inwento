package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.dto.ProductDto;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ProductScannedDialog extends AppCompatDialogFragment {
    private static final String TAG = "ProductScannedDialog";

    private FragmentActivity fragmentActivity;
    private ProductDto productDto;
    private String barCode;

    TextView title, desc, code, price, liable, receiver;
    Button btnSave;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_product, null);
        builder.setView(view).setTitle("Product");

        title = view.findViewById(R.id.dp_title);
        desc = view.findViewById(R.id.dp_desc);
        code = view.findViewById(R.id.dp_code);
        price = view.findViewById(R.id.dp_price);
        liable = view.findViewById(R.id.dp_liable);
        receiver = view.findViewById(R.id.dp_receiver);
        btnSave = view.findViewById(R.id.dp_save);

        fragmentActivity = requireActivity();

        btnSave.setOnClickListener(v -> {
            /// send data to server
            dismiss();
        });

        return builder.create();
    }

    // Method to send GET request
    private void sendGetRequest(String barCode) {
        OkHttpClient client = new OkHttpClient();
        String url = "YOUR_GET_REQUEST_URL" + barCode; // Replace "YOUR_GET_REQUEST_URL" with your actual URL

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error response code: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    // Parse the JSON response using Gson or any other JSON parsing library
                    Gson gson = new Gson();
                    productDto = gson.fromJson(jsonResponse, ProductDto.class);
                    System.out.println(productDto.toString());
                } else {
                    Toast.makeText(requireDialog().getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Method to update UI with product data
    private void updateUI() {
        // Check if productDto is not null and update UI accordingly
        if (productDto != null) {
            // Update TextViews with productDto data
            fragmentActivity.runOnUiThread(() -> {
                title.setText(productDto.getTitle());
                desc.setText(productDto.getDescription());
                code.setText(productDto.getBar_code());
                price.setText(String.valueOf(productDto.getPrice()));
                liable.setText(productDto.getLiable());
                receiver.setText(productDto.getReceiver());
            });
        }
    }

    // Static method to create an instance of ProductScannedDialog with barcode data
    public static ProductScannedDialog newInstance(String code) {
        ProductScannedDialog dialog = new ProductScannedDialog();
        dialog.barCode = code;
        return dialog;
    }
}
