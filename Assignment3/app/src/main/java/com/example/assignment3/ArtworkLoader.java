package com.example.assignment3;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArtworkLoader {
    private static final String TAG = "ArtworkLoader";

    public static void getSearchData(MainActivity mainActivity, String query) {

        String DATA_URL = "https://api.artic.edu/api/v1/artworks/search?q=" + query + "&limit=15&page=1&fields=title%\n" +
                "2C%20date_display%2C%20artist_display%2C%20medium_display%2C%20artwork_type_ti\n" +
                "tle%2C%20image_id%2C%20dimensions%2C%20department_title%2C%20credit_line%2C%20\n" +
                "place_of_origin%2C%20gallery_title%2C%20gallery_id%2C%20id%2C%20api_link";

        RequestQueue queue = Volley.newRequestQueue(mainActivity);

        Log.e(TAG, "Before request");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, DATA_URL,
                null,
                response -> {
                    handleResults(mainActivity, response);
                },
                error -> {
                    Log.e(TAG, "Error getting JSON data: " + error.getMessage());
                    handleResults(mainActivity, null);
                });

        queue.add(jsonObjectRequest);
    }

    private static void handleResults(MainActivity mainActivity, JSONObject x) {
        mainActivity.update(x);
    }
}
