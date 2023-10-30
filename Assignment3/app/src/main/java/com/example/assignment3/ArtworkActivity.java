package com.example.assignment3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtworkActivity  extends AppCompatActivity {

    TextView title, year, artist, artistInfo, department,
        gallery, origin, type,  dimensions, acquisition;
    ImageView logo, art, linkIcon;
    Artwork piece;
    private static final String TAG = "ArtworkActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork);

        title = findViewById(R.id.title);
        year = findViewById(R.id.year);
        artist = findViewById(R.id.artist);
        artistInfo = findViewById(R.id.artist_info);
        department = findViewById(R.id.dep_name);
        gallery = findViewById(R.id.gallery);
        origin = findViewById(R.id.origin);
        type = findViewById(R.id.type);
        dimensions = findViewById(R.id.dim);
        acquisition = findViewById(R.id.acq);

        logo = findViewById(R.id.logo);
        art = findViewById(R.id.art);
        linkIcon = findViewById(R.id.link_icon);

        piece = getIntent().getParcelableExtra("piece");

        fill();

    }

    void fill() {

        title.setText(piece.getItem("title"));
        year.setText(piece.getItem("date_display"));
        artist.setText(piece.getItem("artist"));
        artistInfo.setText(piece.getItem("artist_display"));
        department.setText(piece.getItem("department_title"));

        SpannableString content = new SpannableString(piece.getItem("gallery_title"));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

        gallery.setText(content);
        origin.setText(piece.getItem("place_of_origin"));
        type.setText(piece.getItem("artwork_type_title") + " - " + piece.getItem("medium_display"));
        dimensions.setText(piece.getItem("dimensions"));
        acquisition.setText(piece.getItem("credit_line"));

        String urlString = "https://www.artic.edu/iiif/2/" + piece.getItem("image_id") + "/full/843,/0/default.jpg";
        Picasso.get().load(urlString).error(R.drawable.not_available).into(art);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(v);
            }
        });
        linkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(v);
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMain(v);
            }
        });
        art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage(v);
            }
        });

    }

    void openLink(View v) {

        String url = "https://www.artic.edu/galleries/" + piece.getItem("gallery_id");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d(TAG,"NULL when opening gallery link from ArtworkActivity.");
        }

    }

    void returnMain(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    void openImage(View v) {

        Intent intent = new Intent(this, ImageActivity.class);
        intent.putExtra("art", piece);

        startActivity(intent);

    }

}