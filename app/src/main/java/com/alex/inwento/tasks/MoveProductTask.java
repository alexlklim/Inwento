package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
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

public class MoveProductTask extends AsyncTask<List<String>, Void, Boolean> {
    private static final String TAG = "PostProductsTask";

    private MoveProductTask.ProductMoveListener listener;
    String token;

    Branch branch;
    Employee liable;
    String receiver;
    int productId;

    public interface ProductMoveListener {
        void onProductMoveSuccess(Boolean answer);

        void onProductMoveFailure(String errorMessage);
    }

    public MoveProductTask(
            MoveProductTask.ProductMoveListener listener,
            String token,
            int productId,
            Branch branch,
            Employee liable,
            String receiver) {
        this.listener = listener;
        this.token = token;
        this.branch = branch;
        this.liable = liable;
        this.receiver = receiver;
        this.productId = productId;
    }

    @Override
    protected Boolean doInBackground(List<String>... lists) {
        try {

            JSONObject jsonArray = new JSONObject()
                    .put("id", productId)
                    .put("branch_id", branch.getId())
                    .put("liable_id", liable.getId())
                    .put("receiver", receiver);

            HttpURLConnection connection = (HttpURLConnection) new URL(Endpoints.UPDATE_PRODUCT).openConnection();
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
                return null;
            } else {
                String errorMessage = "Error Response Code: " + responseCode;
                return null;
            }
        } catch (IOException e) {
            String errorMessage = "Error: " + e.getMessage();
            listener.onProductMoveFailure(errorMessage);
            return null;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        Log.i(TAG, "onPostExecute: + ");
        if (success) {
            listener.onProductMoveSuccess(true);
        } else {
            listener.onProductMoveFailure("No inventory found.");
        }
    }


}
