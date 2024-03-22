package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.database.domain.Inventory;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.util.Endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InventoryTask extends AsyncTask<Void, Void, Inventory> {

    private static final String TAG = "InventoryTask";


    private InventoryListener listener;

    private String token;

    public interface InventoryListener {
        void onInventorySuccess(Inventory inventory);

        void onInventoryFailure(String errorMessage);
    }

    public InventoryTask(InventoryListener listener, String token) {
        this.token = token;
        this.listener = listener;
    }

    @Override
    protected Inventory doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_CURRENT_INVENTORY);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                inputStream.close();
                return JsonMng.getInventoryFromJson(String.valueOf(response));
            } else {
                Log.e(TAG, "Error response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(Inventory inventory) {
        if (inventory != null) {
            listener.onInventorySuccess(inventory);
        } else {
            listener.onInventoryFailure("No inventory found.");
        }
    }
}
