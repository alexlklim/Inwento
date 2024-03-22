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

public class AuthTask extends AsyncTask<Void, Void, String> {
    private AuthListener listener;
    private final String email;
    private final String refreshToken;

    public interface AuthListener {
        void onAuthSuccess(String response);
        void onAuthFailure(String errorMessage);
    }

    public AuthTask(AuthListener listener, String email, String refreshToken) {
        this.listener = listener;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(Endpoints.REFRESH_TOKEN).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            JSONObject jsonParam = new JSONObject().put("refresh_token", refreshToken).put("email", email);
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
            listener.onAuthFailure(response);
        } else {
            listener.onAuthSuccess(response);
        }
    }
}
