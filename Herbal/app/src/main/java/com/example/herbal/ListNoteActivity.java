package com.example.herbal;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ListNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private Database database;
    SimpleCursorAdapter scAdapter;
    static String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);


        try{
            _id = getIntent().getStringExtra("id");
            Toast.makeText(getApplicationContext(), "id: " + _id, Toast.LENGTH_LONG).show();

        } catch(Throwable t) {
            Toast.makeText(getApplicationContext(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            Log.d("mLog", "Exception: " + t.toString());
        }

        database = new Database(this);
        database.open();

       // database.addRecNote("какой-то текст", "/storage/sdcard/Herbal/Screenshots/aaa.jpeg");
        database.PrintAllNote();
        scAdapter = new SimpleCursorAdapter(this,
                R.layout.list_note,
                database.getDataNoteFromID(_id),
                new String[] {DBHelper.NOTE_KEY_NAME, DBHelper.NOTE_KEY_CREATEDATA, DBHelper.NOTE_KEY_IMAGE},
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
        getSupportLoaderManager().initLoader(0, null, ListNoteActivity.this);
    }



    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        database.close();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, database);
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
            Cursor cursor = database.getDataNoteFromID(_id);
            return cursor;
        }

    }
}
