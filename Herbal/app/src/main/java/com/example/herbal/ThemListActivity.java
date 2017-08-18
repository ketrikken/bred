package com.example.herbal;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class ThemListActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DELETE_ITEM = 1, UPDATE_ITEM = 2;
    private Database database;
    private ListView lvData;
    DialogFragment dlg1;
    SimpleCursorAdapter scAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_list);

        database = new Database(this);
        database.open();
        dlg1 = new DialogUpdateActivity();

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
        menu.add(0, DELETE_ITEM, 0, "Удалить запись");
        menu.add(0, UPDATE_ITEM, 0, "Изменить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // получаем из пункта контекстного меню данные по пункту списка
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()){
            case DELETE_ITEM:
                Toast.makeText(this, "удали их нажато", Toast.LENGTH_SHORT).show();
                // извлекаем id записи и удаляем соответствующую запись в БД
                database.delFromId(acmi.id);
                break;
            case UPDATE_ITEM:
                Bundle bundle = new Bundle();
                bundle.putString("id", Long.toString(acmi.id));
                bundle.putString("name", "name");
                dlg1.setArguments(bundle);
                dlg1.show(getFragmentManager(), "dlg2");
                break;
        }
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
