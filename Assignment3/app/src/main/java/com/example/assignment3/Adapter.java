package com.example.assignment3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.squareup.picasso.Callback;

import org.json.JSONException;
import org.json.JSONObject;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ArtworkHolder> {


    MainActivity act;
    private static final String TAG = "Adapter";
    ArrayList<Artwork> works;

    public Adapter(ArrayList<Artwork> art, MainActivity ma) {
        works = art;
        act = ma;
    }


    @NonNull
    @Override
    public ArtworkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thumbnail_view, parent, false);
        itemView.setOnClickListener(act);

        return new ArtworkHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkHolder holder, int position) {

        Artwork artwork = works.get(position);

        holder.title.setText(artwork.getItem("title"));

        String urlString = "https://www.artic.edu/iiif/2/" + artwork.getItem("image_id") + "/full/200,/0/default.jpg";

        Picasso.get().load(urlString).error(R.drawable.not_available).into(holder.pic);

    }

    @Override
    public int getItemCount() {
        return works.size();
    }
}

