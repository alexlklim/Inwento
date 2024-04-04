package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.util.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ScrapProductTask extends AsyncTask<List<String>, Void, Boolean> {
    private static final String TAG = "ScrapProductTask";

    private ScrapProductTask.ScrapProductListener listener;
    String token;
    int productId, month, year, day;
    String scrapReason;

    public interface ScrapProductListener {
        void onProductScrapSuccess(Boolean answer);

        void onProductScrapFailure(String errorMessage);
    }

    public ScrapProductTask(ScrapProductTask.ScrapProductListener listener, String token, int productId, int day, int month, int year, String scrapReason) {
        this.listener = listener;
        this.token = token;
        this.productId = productId;
        this.day = day;
        this.month = month + 1;
        this.year = year;
        this.scrapReason = scrapReason;
    }

    @Override
    protected Boolean doInBackground(List<String>... lists) {
        try {
            String monthString, dayString;
            if (day <= 10) {
                dayString = "0" + day;
            } else {
                dayString = String.valueOf(day);

            }

            if (month <= 10) {
                monthString = "0" + month;
            } else {
                monthString = String.valueOf(month);
            }
            JSONObject jsonArray = new JSONObject()
                    .put("id", productId)
                    .put("scrapping", true).
                    put("scrapping_date", year + "-" + monthString + "-" + dayString)
                    .put("scrapping_reason", scrapReason);

            HttpURLConnection connection = (HttpURLConnection) new URL(Endpoints.UPDATE_PRODUCT).openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            OutputStream os = connection.getOutputStream();
            os.write(jsonArray.toString().getBytes("UTF-8"));
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String response = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()))
                        .lines()
                        .collect(Collectors.joining("\n"));
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            String errorMessage = "Error: " + e.getMessage();
            listener.onProductScrapFailure(errorMessage);
            return false;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        Log.i(TAG, "onPostExecute: + ");
        if (success) {
            listener.onProductScrapSuccess(true);
        } else {
            listener.onProductScrapFailure("No inventory found.");
        }
    }

}