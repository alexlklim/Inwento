package com.alex.inwento.tasks;

import android.os.AsyncTask;

import com.alex.inwento.util.Endpoints;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class AddEventTask extends AsyncTask<Void, Void, String> {
    private AddEventTask.AddEventListener listener;
    private int branchId;
    private String info;

    String token;
    public interface AddEventListener {
        void onAddEventSuccess(Boolean answer);
        void onAddEventFailure(String errorMessage);
    }

    public AddEventTask(
            AddEventTask.AddEventListener listener,
            String token,
            int branchId,
            String info) {
        this.listener = listener;
        this.branchId = branchId;
        this.token = token;
        this.info = info;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(Endpoints.ADD_EVENT).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);


            JSONObject jsonParam = new JSONObject().put("branch_id", branchId).put("info", info);


            OutputStream os = connection.getOutputStream();
            os.write(jsonParam.toString().getBytes("UTF-8"));
            os.close();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return new BufferedReader(new InputStreamReader(connection.getInputStream()))
                        .lines().collect(Collectors.joining("\n"));
            } else {
                return "Error Response Code: " + responseCode;
            }
        } catch (IOException | JSONException e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if (response.startsWith("Error")) {
            listener.onAddEventFailure(response);
        } else {
            listener.onAddEventSuccess(true);
        }
    }
}
