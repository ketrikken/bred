package com.example.herbal;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class ListNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private Database database;
   // private ArrayList<ItemNote> products = new ArrayList<ItemNote>();
   // private ItemNoteAdapter ItemAdapter;
    SimpleCursorAdapter scAdapter;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);

        database = new Database(this);
        database.open();
        // создаем адаптер
        //fillData();
        //ItemAdapter = new ItemNoteAdapter(this, products);



        database.PrintAllNote();
        scAdapter = new SimpleCursorAdapter(this,
                R.layout.list_note,
                database.getAllDataNote(),
                new String[] {DBHelper.NOTE_KEY_HEADER, DBHelper.NOTE_KEY_CREATEDATA, DBHelper.NOTE_KEY_IMAGE},
                new int[] { R.id.textViewTheme, R.id.textViewData, R.id.imageViewPreview});


        scAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.imageViewPreview) {
                    // провеока, что картинка существует
                    Bitmap bmImg = BitmapFactory.decodeFile(cursor.getString(columnIndex));
                    ((ImageView) view.findViewById(R.id.imageViewPreview)).setImageBitmap(bmImg);

                    return true;
                }
                return false;
            }
        });

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.listViewNote);
        lvMain.setAdapter(scAdapter);

    }

    // генерируем данные для адаптера
    /*void fillData() {
        for (int i = 1; i <= 20; i++) {
            products.add(new ItemNote(
                    "Product " + i,
                    "11.05.1996",
                    "/storage/sdcard/Herbal/Screenshots/123.jpeg"));
        }
    }*/

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        database.close();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new ThemListActivity.MyCursorLoader(this, database);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
    static class MyCursorLoader extends android.support.v4.content.CursorLoader{

        Database database;

        public MyCursorLoader(Context context, Database db) {
            super(context);
            this.database = db;
        }

        @Override
        public Cursor loadInBackground() {
            //return super.loadInBackground();
            Cursor cursor = database.getAllDataContacts();
            return cursor;
        }

    }
}
