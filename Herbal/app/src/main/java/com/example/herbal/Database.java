package com.example.herbal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Алатиэль on 27.07.2017.
 */

public class Database {

    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private final Context myContext;

    public Database(Context ctx) {
        myContext = ctx;
    }

    // открыть подключение
    public void open() {
        dbHelper = new DBHelper(myContext, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION);
        database = dbHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (dbHelper!=null) dbHelper.close();
    }

    public List<String> selectAll() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, new String[] { "name" },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    public List<String> selectFromNames(String id) {
        List<String> list = new ArrayList<String>();

        String selection = DBHelper.CONTACTS_KEY_NAME + " = ?";
        String[] selectionArgs =  new String[]{ /*String.valueOf(id)*/ id};
        Cursor c = database.query(DBHelper.TABLE_CONTACTS, new String[] { DBHelper.CONTACTS_KEY_NAME }, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0));

            } while (c.moveToNext());
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        return list;

    }

    public List<String> selectAllID() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, new String[] { "_id" },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;

    }

    // получить все данные из таблицы DB_TABLE
    Cursor getAllData() {
        return database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
    }
    void delFromId(long id) {
        // db.delete(TABLE_CONTACTS, KEY_ID + " = ? ", new String[] {id});
        database.delete(DBHelper.TABLE_CONTACTS, DBHelper.EXTERNAL_KEY_ID + " = " + id, null);
    }
    public void PrintAll()
    {
        Cursor cursorr = getAllData();
       // startManagingCursor(cursorr);
        Log.d("mLog", "-----------------------------------------");
        if (cursorr.moveToFirst()) {
            int idIndex = cursorr.getColumnIndex(DBHelper.EXTERNAL_KEY_ID);
            int nameIndex = cursorr.getColumnIndex(DBHelper.CONTACTS_KEY_NAME);
            int emailIndex = cursorr.getColumnIndex(DBHelper.CONTACTS_KEY_EMAIL);
            do {
                Log.d("mLog", "ID = " + cursorr.getInt(idIndex) +
                        ", name = " + cursorr.getString(nameIndex) +
                        ", email = " + cursorr.getString(emailIndex));
            } while (cursorr.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursorr.close();
    }
    // добавить запись в DB_TABLE
    public void addRec(String name, String email) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.CONTACTS_KEY_NAME, name);
        cv.put(DBHelper.CONTACTS_KEY_EMAIL, email);
        database.insert(DBHelper.TABLE_CONTACTS, null, cv);
    }
    public int GETVERSION(){
        return DBHelper.DATABASE_VERSION;
    }

}
