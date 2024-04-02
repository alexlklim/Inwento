package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.util.Endpoints;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class PostProductsTask extends AsyncTask<List<String>, Void, Boolean> {
    private static final String TAG = "PostProductsTask";

    private ProductUploadListener listener;
    private List<String> productBarCodes;
    private int eventId;
    String token;

    public interface ProductUploadListener {
        void onUploadSuccess(Boolean answer);

        void onUploadFailure(String errorMessage);
    }

    public PostProductsTask(ProductUploadListener listener, List<String> productBarCodes, int eventId, String token) {
        this.listener = listener;
        this.productBarCodes = productBarCodes;
        this.eventId = eventId;
        this.token = token;
    }

    @Override
    protected Boolean doInBackground(List<String>... lists) {
        try {

            JSONArray jsonArray = new JSONArray(productBarCodes);

            HttpURLConnection connection = (HttpURLConnection) new URL(Endpoints.ADD_PRODUCTS + eventId + "/products/bar-code").openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            OutputStream os = connection.getOutputStream();
            os.write(jsonArray.toString().getBytes("UTF-8"));
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                        .lines().collect(Collectors.joining("\n"));
                return true;
            } else {
                String errorMessage = "Error Response Code: " + responseCode;
                return false;
            }
        } catch (IOException e) {
            String errorMessage = "Error: " + e.getMessage();
            listener.onUploadFailure(errorMessage);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        Log.i(TAG, "onPostExecute: + ");
        if (success) {
            listener.onUploadSuccess(true);
        } else {
            listener.onUploadFailure("No inventory found.");
        }
    }
}
