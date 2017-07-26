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
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
    String[] emails = { "Иван1", "Марья2", "Петр3", "Антон5", "Даша55", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей" };
    DBHelper dbHelper;
    ListView mainList;
    SQLiteDatabase database;
    ArrayAdapter<String> adapter, adapterID;
    List<String> listOfNames, listOfId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainList =(ListView)findViewById(R.id.listOnMainPage);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = adapter.getItem(position);

                Toast.makeText(getApplicationContext(), "id: " + position, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), Main2Activity.class);
                intent.putExtra("id", /*String.valueOf(id)*/value);
                startActivity(intent);
            }
        });
        dbHelper = new DBHelper(this);
        database = dbHelper.getReadableDatabase();
        try {

            listOfNames= dbHelper.selectAll(database); //забиваю данные из БД в лист
            listOfId = dbHelper.selectAllID(database);
            adapter = new ArrayAdapter<String>(this,   R.layout.text, listOfNames); //прикручиваю адаптер
            adapterID = new ArrayAdapter<String>(this,   R.layout.text, listOfId); //прикручиваю адаптер
           // mainList.setAdapter(adapterID);
            mainList.setAdapter(adapter);
        }
        catch(Throwable t) {  //и тут фейл: NullPointerException
            Toast.makeText(getApplicationContext(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }

      mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

              String selectedItem = parent.getItemAtPosition(position).toString();

              dbHelper.delFromId(database, listOfId.get(position));

              adapter.remove(selectedItem);
              adapter.notifyDataSetChanged();
              listOfId.remove(position);/*удалили элемент из списка*/
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
               /* for(int i = 0; i < 5; ++i){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_NAME, names[i]);
                    contentValues.put(DBHelper.KEY_EMAIL, emails[i]);

                    database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
                }*/
///////////////////////////////////////////////////////////////////////////////////////
                Cursor cursor = database.query(dbHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                Log.d("mLog", "-----------------------------------------");
                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                Log.d("mLog", "-----------------------------------------");
                for(int i = 0; i < listOfId.size(); ++i)
                {
                    Log.d("mLog", listOfId.get(i));
                }


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
