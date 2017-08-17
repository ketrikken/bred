package com.example.herbal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ThemListActivity extends AppCompatActivity {

    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_list);


        database = new Database(this);
    }
}
