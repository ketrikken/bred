package com.example.herbal;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ThemListActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CM_DELETE_ID = 1;
    private Database database;
    private ListView lvData;
    SimpleCursorAdapter scAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_list);

        database = new Database(this);
        database.open();


        // формируем столбцы сопоставления
        String[] from = new String[] { DBHelper.CONTACTS_KEY_NAME};
        int[] to = new int[] { R.id.tvText};

        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.text, database.getAllDataContacts(), from, to, 0);
        lvData = (ListView) findViewById(R.id.list3);
        lvData.setAdapter(scAdapter);

        registerForContextMenu(lvData);
        getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                    .getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            database.delFromId(acmi.id);
            // получаем новый курсор с данными
            getSupportLoaderManager().getLoader(0).forceLoad();


        return true;

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
            Cursor cursor = database.getAllDataContacts();
            return cursor;
        }
    }
}
