package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.dto.Event;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.util.Endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetEventWithProductsTask extends AsyncTask<Void, Void, Event> {

    private static final String TAG = "ProductTask";


    private GetEventWithProductsTask.ProductsListener listener;

    private String token;
    private int eventId;

    public interface ProductsListener {
        void onProductsSuccess(Event event);

        void onProductsFailure(String errorMessage);
    }

    public GetEventWithProductsTask(GetEventWithProductsTask.ProductsListener listener, String token, int eventId) {
        this.token = token;
        this.listener = listener;
        this.eventId = eventId;
    }

    @Override
    protected Event doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_EVENT_BY_ID + eventId);
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

                return JsonMng.parseJsonToEventAndProducts(String.valueOf(response));
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
    protected void onPostExecute(Event products) {
        Log.i(TAG, "onPostExecute: + " + products.toString());
        if (products != null) {
            listener.onProductsSuccess(products);
        } else {

            listener.onProductsFailure("No products found.");
        }
    }
}
