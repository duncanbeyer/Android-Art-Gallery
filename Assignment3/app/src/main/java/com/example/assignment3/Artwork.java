package com.example.assignment3;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public class Artwork implements Parcelable {
    JSONObject data;
    private static final String TAG = "Artwork";
    private String title, date_display, artist, artist_display, medium_display,
            artwork_type_title, image_id, dimensions, department_title,
            credit_line, place_of_origin, gallery_title, gallery_id, id, api_link;

    public Artwork(JSONObject vals) {

        data = vals;
        try {
            title = data.getString("title");
            date_display = data.getString("date_display");

            String[] artistDisplay = data.getString("artist_display").split("\\n");
            if (artistDisplay.length > 1) {
                artist_display = artistDisplay[1];
            }
            else {
                artist_display = "";
            }
            artist = artistDisplay[0];

            medium_display = data.getString("medium_display");
            artwork_type_title = data.getString("artwork_type_title");
            image_id = data.getString("image_id");
            dimensions = data.getString("dimensions");
            department_title = data.getString("department_title");
            credit_line = data.getString("credit_line");
            place_of_origin = data.getString("place_of_origin");
            if (data.getString("gallery_title") == null) {
                gallery_title = "Not on Display";
                gallery_id = "";
            }
            else {
                gallery_title = data.getString("gallery_title");
                gallery_id = data.getString("gallery_id");
            }
            id = data.getString("id");
            api_link = data.getString("api_link");

        } catch (Exception e) {}
    }

    protected Artwork(Parcel in) {
        title = in.readString();
        date_display = in.readString();
        artist=in.readString();
        artist_display = in.readString();
        medium_display = in.readString();
        artwork_type_title = in.readString();
        image_id = in.readString();
        dimensions = in.readString();
        department_title = in.readString();
        credit_line = in.readString();
        place_of_origin = in.readString();
        gallery_title = in.readString();
        gallery_id = in.readString();
        id = in.readString();
        api_link = in.readString();
    }

    public String getItem(String x) {
        switch (x) {
            case "title":
                return title;
            case "date_display":
                return date_display;
            case "artist":
                return artist;
            case "artist_display":
                return artist_display;
            case "medium_display":
                return medium_display;
            case "artwork_type_title":
                return artwork_type_title;
            case "image_id":
                return image_id;
            case "dimensions":
                return dimensions;
            case "department_title":
                return department_title;
            case "credit_line":
                return credit_line;
            case "place_of_origin":
                return place_of_origin;
            case "gallery_title":
                return gallery_title;
            case "gallery_id":
                return gallery_id;
            case "id":
                return id;
            case "api_link":
                return api_link;
        }
        return null;
    }

    public JSONObject getData() {
        return data;
    }

    public static final Creator<Artwork> CREATOR = new Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel in) {
            return new Artwork(in);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(date_display);
        parcel.writeString(artist);
        parcel.writeString(artist_display);
        parcel.writeString(medium_display);
        parcel.writeString(artwork_type_title);
        parcel.writeString(image_id);
        parcel.writeString(dimensions);
        parcel.writeString(department_title);
        parcel.writeString(credit_line);
        parcel.writeString(place_of_origin);
        parcel.writeString(gallery_title);
        parcel.writeString(gallery_id);
        parcel.writeString(id);
        parcel.writeString(api_link);
    }


}
