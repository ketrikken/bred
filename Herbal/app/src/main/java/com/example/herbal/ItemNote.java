package com.example.herbal;

/**
 * Created by Алатиэль on 21.08.2017.
 */

public class ItemNote {
    private String _theme;
    private String _data;
    private String _imagePath;


    ItemNote(String theme, String data, String path){
        _theme = theme;
        _data = data;
        _imagePath = path;
    }

    ItemNote Get()
    {
        return this;
    }
    void Set(String theme, String data, String path){
        _theme = theme;
        _data = data;
        _imagePath = path;
    }
    String GetTheme(){
        return _theme;
    }

    String GetData(){
        return _data;
    }
    String GetPath(){
        return _imagePath;
    }
}
