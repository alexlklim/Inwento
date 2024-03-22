package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;


import com.alex.inwento.util.Endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckInventoryActiveTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "CheckInventActiveTask";

    private  CheckInventoryActiveListener listener;

    private String token;


    public interface CheckInventoryActiveListener {
        void onCheckInventoryActiveResult(boolean isActive);
    }

    public CheckInventoryActiveTask(CheckInventoryActiveListener listener, String token) {
        this.token = token;
        this.listener = listener;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_IS_INVENTORY_ACTIVE);
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
                return response.toString().equalsIgnoreCase("true");
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
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        listener.onCheckInventoryActiveResult(result);
    }
}
