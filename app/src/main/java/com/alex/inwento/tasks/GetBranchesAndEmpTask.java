package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.dto.DataModelDto;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.util.Endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetBranchesAndEmpTask extends AsyncTask<Void, Void, DataModelDto> {
    private static final String TAG = "GetBranchesAndEmpTask";

    private OnDataReceivedListener listener;
    private String token;

    public interface OnDataReceivedListener {
        void onDataReceived(Employee[] employees, Branch[] branches);

        void onError(String errorMessage);
    }

    public GetBranchesAndEmpTask(OnDataReceivedListener listener, String token) {
        this.listener = listener;
        this.token = token;
    }

    @Override
    protected DataModelDto doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_FIELDS);
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

                return JsonMng.parseJsonToDataModelDto(response.toString());
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
    protected void onPostExecute(DataModelDto data) {
        if (data != null) {
            // Extract employees and branches
            Employee[] employees = data.getEmployees();
            Branch[] branches = data.getBranches();
            listener.onDataReceived(employees, branches);
        } else {
            listener.onError("Failed to fetch data");
        }
    }
}