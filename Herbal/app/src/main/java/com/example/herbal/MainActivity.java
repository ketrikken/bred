package com.example.herbal;

import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.support.v4.app.LoaderManager.LoaderCallbacks;





public class MainActivity extends AppCompatActivity  implements LoaderCallbacks<Cursor>{
    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
    String[] emails = { "Иван1", "Марья2", "Петр3", "Антон5", "Даша55", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    android.widget.SimpleCursorAdapter adapter;
    Cursor cursor;
    ListView mainList;
    Database database;

    List<String> listOfNames, listOfId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new Database(this);
        database.open();
        // получаем курсор
        cursor = database.getAllDataContacts();
        startManagingCursor(cursor);


        // формируем столбцы сопоставления
        String[] from = new String[] { DBHelper.CONTACTS_KEY_NAME};
        int[] to = new int[] { R.id.tvText};

        // создааем адаптер и настраиваем список
        adapter = new android.widget.SimpleCursorAdapter(this, R.layout.text, cursor, from, to);
        mainList =(ListView)findViewById(R.id.listOnMainPage);
        mainList.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null, this);


        MainListFunction();
        FAB();


    }
    private void MainListFunction(){
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = ((TextView) view.findViewById(R.id.tvText)).getText().toString();

                Toast.makeText(getApplicationContext(), "value: " + value, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(view.getContext(), Main2Activity.class);
                intent.putExtra("id", value);
                startActivity(intent);
            }
        });


        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), Long.toString(id), Toast.LENGTH_LONG).show();
                database.delFromId(id);
                cursor.requery();
                return true;
            }
        });
    }
    private void FAB(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "BD is fill in", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                for(int i = 0; i < 11; ++i){
                    database.addRecContacts(names[i], emails[i]);
                }
                cursor.requery();
                database.PrintAllContacts();
                Log.d("mLog", Integer.toString(database.GET_VERSION()));
            }
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
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
        adapter.swapCursor(cursor);
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
