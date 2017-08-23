package com.example.herbal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
public class ListNoteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Note sentNote;
    private Database database;
    SimpleCursorAdapter scAdapter;
    static String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        sentNote = new Note();

        try{
            _id = getIntent().getStringExtra("id");
            sentNote._parentId = _id;
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
                database.getDataNoteFromIDTheme(_id),
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

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                database.delNoteFromId(Long.toString(id));
                getSupportLoaderManager().getLoader(0).forceLoad();
                return false;
            }
        });


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), activity_add_note.class);

                sentNote = ParseCursorNote(database.getOneNoteFromID(Long.toString(id)));
                intent.putExtra(Note.class.getCanonicalName(), sentNote);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    Note ParseCursorNote(Cursor cursor){

        Note note = new Note();
        if (cursor.moveToFirst()) {
            note._imagePath = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_IMAGE));
            note._text = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_TEXT));
            note._theme = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_NAME));
            note._data = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_CREATEDATA));
            note._id = cursor.getString(cursor.getColumnIndex(DBHelper.EXTERNAL_KEY_ID));
            note._parentId = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_HEADER));
        } else
            Log.d("mLog","Parse Cursor Error !!!!!!!!!!!!");

        return note;
    }

    protected void onDestroy() {
        super.onDestroy();
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /*  @Override
    public void createNote(Note note) {
        database.addRecNote(note, _id);
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public void updateNote(Note note) {
        database.updateRecNoteFromId(note);
        getSupportLoaderManager().getLoader(0).forceLoad();
    }
*/
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    static class MyCursorLoader extends android.support.v4.content.CursorLoader{

        Database database;

        public MyCursorLoader(Context context, Database db) {
            super(context);
            this.database = db;
        }

        @Override
        public Cursor loadInBackground() {
            //return super.loadInBackground();
            Cursor cursor = database.getDataNoteFromIDTheme(_id);
            return cursor;
        }

    }
}
