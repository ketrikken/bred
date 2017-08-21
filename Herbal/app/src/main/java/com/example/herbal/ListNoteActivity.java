package com.example.herbal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListNoteActivity extends AppCompatActivity {



    ArrayList<ItemNote> products = new ArrayList<ItemNote>();
    ItemNoteAdapter ItemAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);


        // создаем адаптер
        fillData();
        ItemAdapter = new ItemNoteAdapter(this, products);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.listViewNote);
        lvMain.setAdapter(ItemAdapter);

    }

    // генерируем данные для адаптера
    void fillData() {
        for (int i = 1; i <= 20; i++) {
            products.add(new ItemNote(
                    "Product " + i,
                    "11.05.1996",
                    "/storage/sdcard/Herbal/Screenshots/123.jpeg"));
        }
    }


}
