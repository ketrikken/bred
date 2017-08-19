package com.example.herbal;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.example.herbal.DialogUpdateActivity.onSomeEventListener;
import com.example.herbal.DialogCreateActivity.onCreateItemEventListener;



public class ThemListActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, onSomeEventListener, onCreateItemEventListener {

    private static final int DELETE_ITEM = 1, UPDATE_ITEM = 2;
    private Database database;
    private ListView lvData;
    DialogFragment dialogChange, dialogCreateItem;
    SimpleCursorAdapter scAdapter;
    private long idItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_list);

        database = new Database(this);
        database.open();
        dialogChange = new DialogUpdateActivity();

        dialogCreateItem = new DialogCreateActivity();

        // формируем столбцы сопоставления
        String[] from = new String[] { DBHelper.THEM_NOTE_KEY_HEADER};
        int[] to = new int[] { R.id.tvText};


        // создаем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.text, database.getAllDataThemeNote(), from, to, 0);
        lvData = (ListView) findViewById(R.id.list3);
        lvData.setAdapter(scAdapter);

        registerForContextMenu(lvData);
        getSupportLoaderManager().initLoader(0, null, ThemListActivity.this);

        FAB();

    }


    @Override
    protected void onStart() {
        super.onStart();
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
                database.delFromIdThemeNote(acmi.id);
                break;
            case UPDATE_ITEM:
                idItem = acmi.id;
                dialogChange.show(getFragmentManager(), "dlg1");
                break;
        }
            // получаем новый курсор с данными
        getSupportLoaderManager().getLoader(0).forceLoad();


        return true;

    }
    private void FAB(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAddNewTheme);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "BD is fill in", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                dialogCreateItem.show(getFragmentManager(), "dlg1");
            }
        });
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

    @Override
    public void someEvent(String s) {
        Log.d("mLog", " someEvent ");
        database.UpdateHeaders(Long.toString(idItem), s);
        getSupportLoaderManager().getLoader(0).forceLoad();
    }

    @Override
    public void createItem(String s) {
        //добавить проверку существования
        if (database.findTheme(s) == false){
            Log.d("mLog", " createItem ");
            database.addRecTheme(s);
            getSupportLoaderManager().getLoader(0).forceLoad();
        }else{
            Toast.makeText(this, "данная тема уже существует", Toast.LENGTH_SHORT).show();
        }

    }

    static class MyCursorLoader extends CursorLoader{

        Database database;

        public MyCursorLoader(Context context, Database db) {
            super(context);
            this.database = db;
        }

        @Override
        public Cursor loadInBackground() {
            //return super.loadInBackground();
            Cursor cursor = database.getAllDataThemeNote();
            return cursor;
        }
    }
}
