package com.alex.inwento.managers;

import android.util.Log;

import com.alex.inwento.database.domain.Event;
import com.alex.inwento.database.domain.Inventory;
import com.alex.inwento.dto.AuthDto;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static List<Event> parseJsonToEvents(String json) {


        return null;
    }


}
