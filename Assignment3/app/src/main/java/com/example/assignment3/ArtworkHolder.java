package com.example.assignment3;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtworkHolder extends RecyclerView.ViewHolder{

    private static final String TAG = "ArtworkHolder";
    TextView title;
    ImageView pic;


    public ArtworkHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.thumbnail_text);
        pic = itemView.findViewById(R.id.thumbnail_image);


    }
}
