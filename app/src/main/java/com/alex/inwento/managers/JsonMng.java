package com.alex.inwento.managers;

import android.util.Log;

import com.alex.inwento.database.domain.Event;
import com.alex.inwento.database.domain.Inventory;
import com.alex.inwento.dto.AuthDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonMng {

    private static final String TAG = "JsonMng";


    public static AuthDto getAuthDtoFromResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return new AuthDto(
                    jsonResponse.getString("first_name"),
                    jsonResponse.getString("last_name"),
                    jsonResponse.getString("expires_at"),
                    jsonResponse.getString("role"),
                    jsonResponse.getString("access_token"),
                    jsonResponse.getString("refresh_token")
            );
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
        }
        return null;
    }


    public static Inventory getInventoryFromJson(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            return new Inventory(
                    jsonResponse.getInt("id"),
                    jsonResponse.getString("start_date"),
                    jsonResponse.getString("finish_date"),
                    jsonResponse.getString("info"),
                    false,
                    10,
                    10,
                    10
            );

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON response: " + e.getMessage());
        }
        return null;
    }

    public static List<Event> parseJsonToEvents(String json) throws JSONException {
        List<Event> events = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Event event = new Event();
            event.setId(jsonObject.getInt("id"));
            event.setBranch(jsonObject.getString("branch"));
            event.setUsername(jsonObject.getString("username"));
            event.setEmail(jsonObject.getString("email"));
            event.setUnknownProductAmount(jsonObject.isNull("unknown_products") ? 0 : jsonObject.getInt("unknown_product_amount"));
            event.setTotalProductAmount(jsonObject.getInt("total_product_amount"));
            event.setScannedProductAmount(jsonObject.getInt("scanned_product_amount"));
            events.add(event);
        }

        return events;

    }


}
