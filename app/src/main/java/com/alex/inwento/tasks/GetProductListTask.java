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
import java.util.List;

public class GetProductListTask extends AsyncTask<Void, Void, List<ProductDto>> {
    private static final String TAG = "GetProductListTask";
    private GetProductListTask.GetProductListListener listener;
    private String token;

    public interface GetProductListListener {
        void onGetProductListSuccess(List<ProductDto> productDtoList);

        void onGetProductListFailure(String errorMessage);
    }

    public GetProductListTask(
            GetProductListTask.GetProductListListener listener,
            String token) {
        this.token = token;
        this.listener = listener;
    }

    @Override
    protected List<ProductDto> doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_LIST_SHORT_PRODUCTS);
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

                return JsonMng.parseJsonToProductDtoList(response.toString());
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
    protected void onPostExecute(List<ProductDto> productDtoList) {
        Log.i(TAG, "onPostExecute: + ");
        if (productDtoList != null) {
            listener.onGetProductListSuccess(productDtoList);
        } else {
            listener.onGetProductListFailure("No Product found.");
        }
    }
}
