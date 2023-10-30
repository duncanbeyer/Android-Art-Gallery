package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageActivity  extends AppCompatActivity {

    Artwork art;
    ImageView logo;
    TextView title, artist, artistInfo;
    private PhotoView photoView;
    private static final String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);
        artist = findViewById(R.id.artist);
        artistInfo = findViewById(R.id.artist_info);
        photoView = findViewById(R.id.zoomable);

        art = getIntent().getParcelableExtra("art");

        setters();
    }

    void setters() {
        title.setText(art.getItem("title"));


        artist.setText(art.getItem("artist"));

        artistInfo.setText(art.getItem("artist_display"));


        String urlString = "https://www.artic.edu/iiif/2/" + art.getItem("image_id") + "/full/843,/0/default.jpg";
        Picasso.get().load(urlString).error(R.drawable.not_available).into(photoView);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMain(v);
            }
        });

    }

    void returnMain(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);


    }


}
