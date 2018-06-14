package com.example.yeshu.moviemagazine;

/*
 * Created by Yeshu on 23-05-2018.
 */

import android.os.Parcel;
import android.os.Parcelable;

class JsonDataClass implements Parcelable {
    private int vote_count;
    private String id;
    private String title;
    private String poster_path;
    private String original_language;
    private String original_title;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private double vote_average;
    private double popularity;
    private boolean video;
    private boolean adult;
    JsonDataClass(int vote_count, String id, String title, String poster_path, String original_language, String original_title, String backdrop_path, String overview, String release_date, double vote_average, double popularity, boolean video, boolean adult) {
        this.vote_count = vote_count;
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.video = video;
        this.adult = adult;
    }

    private JsonDataClass(Parcel in) {
        vote_count = in.readInt();
        id = in.readString();
        title = in.readString();
        poster_path = in.readString();
        original_language = in.readString();
        original_title = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        popularity = in.readDouble();
        video = in.readByte() != 0;
        adult = in.readByte() != 0;
    }

    public static final Creator<JsonDataClass> CREATOR = new Creator<JsonDataClass>() {
        @Override
        public JsonDataClass createFromParcel(Parcel in) {
            return new JsonDataClass(in);
        }

        @Override
        public JsonDataClass[] newArray(int size) {
            return new JsonDataClass[size];
        }
    };

    int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(vote_count);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(poster_path);
        parcel.writeString(original_language);
        parcel.writeString(original_title);
        parcel.writeString(backdrop_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeDouble(vote_average);
        parcel.writeDouble(popularity);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeByte((byte) (adult ? 1 : 0));
    }
}
