package com.example.herbal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase database;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        listView = (ListView) findViewById(R.id.list2);


        try {
            /*long id = Long.parseLong(getIntent().getStringExtra("id")) + 1;*/
            String id = getIntent().getStringExtra("id");
            Toast.makeText(getApplicationContext(), "id: " + id, Toast.LENGTH_LONG).show();

            dbHelper = new DBHelper(this);
            database = dbHelper.getReadableDatabase();


            List<String> list = dbHelper.selectFromNames(database, id); //забиваю данные из БД в лист
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text, list); //прикручиваю адаптер
            listView.setAdapter(adapter);

        }
        catch(Throwable t) {
            Toast.makeText(getApplicationContext(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            Log.d("mLog", "Exception: " + t.toString());
        }

    }
}
