package com.example.herbal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Алатиэль on 23.08.2017.
 */

public class Note implements Parcelable{
    String _theme;
    String _text;
    String _imagePath;
    String _data;
    String _id;
    String _parentId;

    public Note(){}

    protected Note(Parcel in) {
        _theme = in.readString();
        _text = in.readString();
        _imagePath = in.readString();
        _data = in.readString();
        _id = in.readString();
        _parentId = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_theme);
        dest.writeString(_text);
        dest.writeString(_imagePath);
        dest.writeString(_data);
        dest.writeString(_id);
        dest.writeString(_parentId);
    }
}
