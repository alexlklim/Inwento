package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.dto.ProductDto;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.util.Endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetProductTask extends AsyncTask<Void, Void, ProductDto> {

    private static final String TAG = "GetProductByBarCodeTask";


    private GetProductTask.GetProductByBarCodeListener listener;

    private String token;
    private String barCode;

    public interface GetProductByBarCodeListener {
        void onProductByBarCodeSuccess(ProductDto productDto);

        void onProductByBarCodeFailure(String errorMessage);
    }

    public GetProductTask(GetProductTask.GetProductByBarCodeListener listener, String token, String barCode) {
        this.token = token;
        this.listener = listener;
        this.barCode = barCode;
    }

    @Override
    protected ProductDto doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_SHORT_PRODUCT_BY_BAR_CODE + barCode);
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

                return JsonMng.parseJsonToProductDto(String.valueOf(response));
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
    protected void onPostExecute(ProductDto productDto) {
        Log.i(TAG, "onPostExecute: + ");
        if (productDto != null) {
            listener.onProductByBarCodeSuccess(productDto);
        } else {
            listener.onProductByBarCodeFailure("No Product found.");
        }
    }
}
