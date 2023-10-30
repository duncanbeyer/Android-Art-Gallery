package com.example.assignment3;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CopyrightActivity extends AppCompatActivity {
    private static final String TAG = "CopyrightActivity";
    ImageView logo;
    TextView apiLink, fontLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copyright);

        logo = findViewById(R.id.logo);
        apiLink = findViewById(R.id.copy_box12);
        fontLink = findViewById(R.id.copy_box3);

        setters();


    }

    void setters() {

        SpannableString content = new SpannableString(getString(R.string.copy_box12));
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        apiLink.setText(content);

        SpannableString content1 = new SpannableString(getString(R.string.copy_box3));
        content1.setSpan(new UnderlineSpan(), 0, content1.length(), 0);
        fontLink.setText(content1);

        apiLink.setTextColor(ResourcesCompat.getColor(getResources(), R.color.link_purple, null));
        fontLink.setTextColor(ResourcesCompat.getColor(getResources(), R.color.link_purple, null));

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMain(v);
            }
        });

        apiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(v, getString(R.string.copy_box12));
            }
        });

        fontLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(v, getString(R.string.copy_box3));
            }
        });
    }

    void returnMain(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    void openLink(View v, String s) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Log.d(TAG,"NULL when opening copright link.");
        }
    }
}

