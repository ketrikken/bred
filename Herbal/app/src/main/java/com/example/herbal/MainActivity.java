package com.example.herbal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity {
    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
    String[] emails = { "Иван1", "Марья2", "Петр3", "Антон5", "Даша55", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };

    SimpleCursorAdapter adapter;
    Cursor cursor;
    DBHelper dbHelper;
    ListView mainList;
    SQLiteDatabase database;

    List<String> listOfNames, listOfId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();

        // получаем курсор
        cursor = dbHelper.getAllData(database);
        startManagingCursor(cursor);


        // формируем столбцы сопоставления
        String[] from = new String[] { dbHelper.KEY_NAME};
        int[] to = new int[] { R.id.textText};



        // создааем адаптер и настраиваем список
        adapter = new SimpleCursorAdapter(this, R.layout.text, cursor, from, to);
        mainList =(ListView)findViewById(R.id.listOnMainPage);
        mainList.setAdapter(adapter);

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = ((TextView) view.findViewById(R.id.textText)).getText().toString();

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
              dbHelper.delFromId(database, id);
              cursor.requery();
              return true;
          }
      });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "BD is fill in", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               // database.delete(DBHelper.TABLE_CONTACTS, null, null);
               /* for(int i = 0; i < 11; ++i){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_NAME, names[i]);
                    contentValues.put(DBHelper.KEY_EMAIL, emails[i]);

                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                }
                cursor.requery();*/
///////////////////////////////////////////////////////////////////////////////////////
                Cursor cursorr = database.query(dbHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                Log.d("mLog", "-----------------------------------------");
                if (cursorr.moveToFirst()) {
                    int idIndex = cursorr.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursorr.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursorr.getColumnIndex(DBHelper.KEY_EMAIL);
                    do {
                        Log.d("mLog", "ID = " + cursorr.getInt(idIndex) +
                                ", name = " + cursorr.getString(nameIndex) +
                                ", email = " + cursorr.getString(emailIndex));
                    } while (cursorr.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursorr.close();



            }
        });


    }

    @Override
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
    }


}
