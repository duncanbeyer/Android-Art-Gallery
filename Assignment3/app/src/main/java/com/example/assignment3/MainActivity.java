package com.example.assignment3;
// by Duncan Beyer

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    EditText searchBar;
    Adapter adapter;
    Button button;
    Button random;
    ImageView xButton;

    TextView copyRightLink;
    ProgressBar progressBar;
    ArrayList<Artwork> artworks = new ArrayList();
    ArrayList<JSONArray> randomList = new ArrayList();



    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBar = findViewById(R.id.search_edit_text);
        copyRightLink = findViewById(R.id.copyright_link);
        button = findViewById(R.id.search_button);
        xButton = findViewById(R.id.x);
        random = findViewById(R.id.random_button);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        SpannableString content = new SpannableString(getString(R.string.copy_link));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        copyRightLink.setText(content);

        setListeners();

        adapter = new Adapter(artworks, this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//        try {
//            ArrayList<Artwork> temp = getIntent().getParcelableArrayListExtra("list");
//            for (int i = 0;i < temp.size();i++) {
//                artworks.add(temp.get(i));
//            }
//            adapter.notifyDataSetChanged();
//            updateBackground();
//        } catch (Exception e) {
//            Log.e(TAG,"Error in onCreate", e);
//        }

        }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("searchText", searchBar.getText().toString());
        outState.putString("size", String.valueOf(artworks.size()));
        for (int i = 0;i < artworks.size();i++) {
            outState.putParcelable(String.valueOf(i), artworks.get(i));
        }

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        searchBar.setText(savedInstanceState.getString("searchText"));
        Integer size = Integer.valueOf(savedInstanceState.getString("size"));

        for (int i = 0;i < size;i++) {
            artworks.add(savedInstanceState.getParcelable(String.valueOf(i)));
        }
        if (size > 0) {
            adapter.notifyDataSetChanged();
        }
        updateBackground();
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, ArtworkActivity.class);
        intent.putExtra("piece", artworks.get(recyclerView.getChildLayoutPosition(v)));
        startActivity(intent);
    }

    void setListeners() {

        copyRightLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCopyright(v);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkValid(searchBar.getText().toString())) {
                    return;
                }
                while (artworks.size() != 0) {
                    artworks.remove(0);
                    adapter.notifyItemRemoved(0);

                }
                recyclerView.setBackgroundColor(getColor(R.color.tan_background));
                progressBar.setVisibility(View.VISIBLE);
                search();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setText("");
                while (artworks.size() != 0) {
                    artworks.remove(0);
                }
                adapter.notifyDataSetChanged();
                updateBackground();
                }
        });
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setBackgroundColor(getColor(R.color.tan_background));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                doRandom();

            }
        });

    }



    void makeDialog(String s) {

        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.alert_layout, null);

        TextView title = dialog.findViewById(R.id.title);
        TextView msg = dialog.findViewById(R.id.msg);
        ImageView logo = dialog.findViewById(R.id.logo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (s.equals("char")) {

            title.setText(getString(R.string.too_short_title));
            msg.setText(getString(R.string.too_short_msg));

            builder.setView(dialog)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }
        else if (s.equals("null")) {

            title.setText(getString(R.string.null_title));
            msg.setText(getString(R.string.null_msg1) +
                    searchBar.getText().toString() + getString(R.string.null_msg2));

            builder.setView(dialog)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        else {

            title.setText(getString(R.string.net_title));
            msg.setText(getString(R.string.net_msg));

            builder.setView(dialog)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        builder.show();
    }

    boolean checkValid(String s) {
        if (s.length() < 3) {
            makeDialog("char");
            return false;
        }
        if (!gotInternet()) {
            makeDialog("net");
            return false;
        }
        else return true;
    }

    boolean checkValid(JSONObject j) {
        int len;
        try {
            len = j.getJSONArray("data").length();
        } catch (Exception e) {len = 0;}

        if (len == 0) {
            makeDialog("null");
            return false;
        }
        return true;
    }

    void doRandom() {

        if (!gotInternet()) {
            makeDialog("net");
            return;
        }
        searchBar.setText("");
        while (artworks.size() > 0) {
            artworks.remove(0);
            adapter.notifyItemRemoved(0);
        }

        if (randomList.size() == 3) {
            Random random = new Random();
            int pos = random.nextInt(280);

            try {

                if (pos < 100) {
                    doArtwork(randomList.get(0).getJSONObject(pos).getInt("id"));
                    return;
                } else if (pos < 200) {
                    doArtwork(randomList.get(1).getJSONObject((pos-100)).getInt("id"));
                    return;
                } else {
                    doArtwork(randomList.get(2).getJSONObject((pos-200)).getInt("id"));
                    return;
                }
            } catch (Exception e) {
                Log.e(TAG,"Error when randomList.size() == 3 in doRandom", e);}
        }

        int i = 0;

        ArrayList<JSONArray> list = new ArrayList();

        while (i < 3) {

            String link = "https://api.artic.edu/api/v1/galleries?limit=100&fields=id&page=" + String.valueOf(i);
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link,
                    null,
                    response -> {
                        JSONArray temp1;
                        try {
                            temp1 = response.getJSONArray("data");
                        } catch (Exception e) {
                            temp1 = new JSONArray();
                        }
                        next(temp1);
                    },
                    error -> {
                        Log.e(TAG, "Error getting JSON data: " + error.getMessage());
                    });

            queue.add(jsonObjectRequest);
            i += 1;

        }
    }

    void next(JSONArray jsa) {

        randomList.add(jsa);
        if (randomList.size() == 3) {

            Random random = new Random();
            int pos = random.nextInt(280);

            try {

                if (pos < 100) {
                    doArtwork(randomList.get(0).getJSONObject(pos).getInt("id"));
                } else if (pos < 200) {
                    doArtwork(randomList.get(1).getJSONObject((pos-100)).getInt("id"));
                } else {
                    doArtwork(randomList.get(2).getJSONObject((pos-200)).getInt("id"));
                }
            } catch (Exception e) {
                Log.e(TAG,"Error moving into doArtwork", e);
            }
        }
    }

    void doArtwork(int id) {

        String url = "https://api.artic.edu/api/v1/artworks/search?query" +
                "[term][gallery_id]=" + id + "&limit=100&fields=title," +
                "date_display,artist_display,medium_display,artwork_type_title" +
                ",image_id,dimensions,department_title,credit_line," +
                "place_of_origin,gallery_title,gallery_id,id,api_link";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                response -> {
                    try {
                        if (response.getJSONArray("data") == null) {
                            Random random = new Random();
                            int pos = random.nextInt(280);
                            try {
                                if (pos < 100) {
                                    doArtwork(randomList.get(0).getJSONObject(pos).getInt("id"));
                                } else if (pos < 200) {
                                    doArtwork(randomList.get(1).getJSONObject((pos - 100)).getInt("id"));
                                } else {
                                    doArtwork(randomList.get(2).getJSONObject((pos - 200)).getInt("id"));
                                }
                            } catch (Exception e) {
                                doArtwork(id);
                            }
                        } else if (response.getJSONArray("data").length() == 0) {
                            Random random = new Random();
                            int pos = random.nextInt(280);
                            try {
                                if (pos < 100) {
                                    doArtwork(randomList.get(0).getJSONObject(pos).getInt("id"));
                                } else if (pos < 200) {
                                    doArtwork(randomList.get(1).getJSONObject((pos - 100)).getInt("id"));
                                } else {
                                    doArtwork(randomList.get(2).getJSONObject((pos - 200)).getInt("id"));
                                }
                            } catch (Exception e) {
                                doArtwork(id);
                            }
                        } else {
                            finalRandom(response.getJSONArray("data"));
                        }
                    } catch (Exception j) {
                        doArtwork(id);
                    }
                },
                error -> {
                    Log.e(TAG, "Error getting JSON data: " + error.getMessage());
                });
        queue.add(jsonObjectRequest);

    }
    void finalRandom(JSONArray x) {
        Random random = new Random();
        int pos = random.nextInt(x.length());
        JSONObject finalArt;
        try {
            finalArt = x.getJSONObject(pos);
        } catch (Exception e) {
            finalArt = new JSONObject();
        }
        artworks.add(new Artwork(finalArt));
        progressBar.setVisibility(View.GONE);
        adapter.notifyItemInserted(0);
    }

    boolean gotInternet() {

        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnectedOrConnecting());

    }

    void search() {
        ArtworkLoader.getSearchData(this, searchBar.getText().toString());
    }


    public void openCopyright(View view) {
        Intent intent = new Intent(this, CopyrightActivity.class);
        startActivity(intent);
    }




    public void update(JSONObject list) {
        if (!checkValid(list)) {
            progressBar.setVisibility(View.GONE);
            updateBackground();
            return;
        }
        try {
            JSONArray arr = list.getJSONArray("data");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject item = arr.getJSONObject(i);
                artworks.add(new Artwork(item));

            }
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            updateBackground();

        } catch (Exception e) {
            Log.e(TAG,"Error Updating Search Results", e);
        }


    }

    void updateBackground() {
        if (artworks.size() > 0) {
            recyclerView.setBackgroundColor(getColor(R.color.tan_background));
        }
        else {
            recyclerView.setBackground(getDrawable(R.drawable.bwlions));
        }
    }




}
