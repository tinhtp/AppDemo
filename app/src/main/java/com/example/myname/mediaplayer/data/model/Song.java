package com.example.myname.mediaplayer.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private int mId;
    private String mNameSong;
    private String mUrlSong;
    private String mDuration;
    private String mSingger;


    public Song(int id, String nameSong, String urlSong, String duration, String singger) {
        mId = id;
        mNameSong = nameSong;
        mUrlSong = urlSong;
        mDuration = duration;
        mSingger = singger;
    }

    protected Song(Parcel in) {
        mId = in.readInt();
        mNameSong = in.readString();
        mUrlSong = in.readString();
        mDuration = in.readString();
        mSingger = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getNameSong() {
        return mNameSong;
    }

    public void setNameSong(String nameSong) {
        mNameSong = nameSong;
    }

    public String getUrlSong() {
        return mUrlSong;
    }

    public void setUrlSong(String urlSong) {
        mUrlSong = urlSong;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getSingger() {
        return mSingger;
    }

    public void setSingger(String singger) {
        mSingger = singger;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mNameSong);
        dest.writeString(mUrlSong);
        dest.writeString(mDuration);
        dest.writeString(mSingger);
    }
}
