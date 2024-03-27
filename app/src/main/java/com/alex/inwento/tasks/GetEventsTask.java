package com.alex.inwento.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.alex.inwento.database.domain.Event;
import com.alex.inwento.managers.JsonMng;
import com.alex.inwento.util.Endpoints;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GetEventsTask extends AsyncTask<Void, Void, List<Event>> {
    private static final String TAG = "EventsTask";


    private EventsListener listener;

    private String token;
    private int inventoryId;

    public interface EventsListener {
        void onEventsSuccess(List<Event> events);

        void onEventsFailure(String errorMessage);
    }

    public GetEventsTask(EventsListener listener, String token, int inventoryId) {
        this.token = token;
        this.listener = listener;
        this.inventoryId = inventoryId;
    }

    @Override
    protected List<Event> doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoints.GET_ALL_MY_EVENTS + inventoryId);
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

                System.out.println(response.toString());
                inputStream.close();

                return JsonMng.parseJsonToEvents(String.valueOf(response));
            } else {
                Log.e(TAG, "Error response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<Event> events) {
        if (!events.isEmpty()) {
            listener.onEventsSuccess(events);
        } else {
            listener.onEventsFailure("No events found.");
        }
    }
}
