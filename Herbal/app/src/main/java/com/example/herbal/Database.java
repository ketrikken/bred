package com.example.herbal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.ContactsContract;
import android.util.Log;

import org.w3c.dom.Text;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public List<String> getURLImages() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.query(DBHelper.TABLE_NOTE, new String[] { DBHelper.NOTE_KEY_IMAGE },
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

    void delFromImages() {
        database.delete(DBHelper.TABLE_NOTE, DBHelper.EXTERNAL_KEY_ID + " >= 0" , null);
    }
    void delFromFigurs() {
        database.delete(DBHelper.TABLE_FIGURS, DBHelper.EXTERNAL_KEY_ID + " >= 0" , null);
    }
    void delNoteFromId(String id){
        database.delete(DBHelper.TABLE_NOTE, DBHelper.EXTERNAL_KEY_ID + " = " + id , null);
    }


    // получить все данные из таблицы DB_TABLE
    Cursor getAllDataContacts() {
        return database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
    }

    Cursor getAllDataNote() {
        return database.query(DBHelper.TABLE_NOTE, null, null, null, null, null, null);
    }



    Cursor getDataNoteFromIDTheme(String id){
        return database.query(DBHelper.TABLE_NOTE,
                null, //список возвращаемых полей
                DBHelper.NOTE_KEY_HEADER + " = " + id,
                null,
                null,
                null,
                null);
    }
    Cursor getOneNoteFromID(String id){
        Log.d("mLog", id);
        return database.query(DBHelper.TABLE_NOTE,
                null, //список возвращаемых полей
                DBHelper.EXTERNAL_KEY_ID + " = " + id,
                null,
                null,
                null,
                null);
    }


    Cursor getAllDataThemeNote(){
        return database.query(DBHelper.TABLE_THEME_NOTE, null, null, null, null, null, null);
    }
    Cursor getAllDataFigurs(){
        return database.query(DBHelper.TABLE_FIGURS, null, null, null, null, null, null);
    }
    void addRecTheme(String text){
        database.execSQL("INSERT INTO "+ DBHelper.TABLE_THEME_NOTE + " VALUES ( null, " + "'" + text + "' );");
        PrintAllTheme();
    }

    boolean findTheme(String s){
        Cursor cursor = getAllDataThemeNote();


        if (cursor.moveToFirst()) {
            int Index = cursor.getColumnIndex(DBHelper.THEM_NOTE_KEY_HEADER);
            do {
                if (cursor.getString(Index).equals(s)){
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursor.close();
        return false;
    }

    void delFromId(long id) {
        // db.delete(TABLE_CONTACTS, KEY_ID + " = ? ", new String[] {id});
        Log.d("mLog", id + " ");
        database.delete(DBHelper.TABLE_CONTACTS, DBHelper.EXTERNAL_KEY_ID + " = " + id, null);
        PrintAllContacts();
    }

    void delFromIdThemeNote(long id){
        Log.d("mLog", id + " ");
        database.delete(DBHelper.TABLE_THEME_NOTE, DBHelper.EXTERNAL_KEY_ID + " = " + id, null);
        PrintAllTheme();
    }

    public void PrintAllContacts()
    {
        Cursor cursorr = getAllDataContacts();
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

    public void PrintAllNote()
    {
        Cursor cursorr = getAllDataNote();
        // startManagingCursor(cursorr);
        Log.d("mLog", "-----------------------------------------");
        if (cursorr.moveToFirst()) {
            int id = cursorr.getColumnIndex(DBHelper.EXTERNAL_KEY_ID);
            int image = cursorr.getColumnIndex(DBHelper.NOTE_KEY_IMAGE);
            int data = cursorr.getColumnIndex(DBHelper.NOTE_KEY_CREATEDATA);
            int text = cursorr.getColumnIndex(DBHelper.NOTE_KEY_TEXT);
            int name = cursorr.getColumnIndex(DBHelper.NOTE_KEY_NAME);
            do {
                Log.d("mLog", "ID = " + cursorr.getInt(id) +
                        ", data = " + cursorr.getString(data) +
                        ", image = " + cursorr.getString(image) +
                        ", text = " + cursorr.getString(text) +
                        ", name = " + cursorr.getString(name));
            } while (cursorr.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursorr.close();
    }


    public void PrintAllTheme()
    {
        Cursor cursorr = getAllDataThemeNote();
        // startManagingCursor(cursorr);
        Log.d("mLog", "-----------------------------------------");
        if (cursorr.moveToFirst()) {
            int idIndex = cursorr.getColumnIndex(DBHelper.EXTERNAL_KEY_ID);
            int nameIndex = cursorr.getColumnIndex(DBHelper.THEM_NOTE_KEY_HEADER);
            do {
                Log.d("mLog", "ID = " + cursorr.getInt(idIndex) +
                        ", theme = " + cursorr.getString(nameIndex));
            } while (cursorr.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursorr.close();
    }



    public void printFigurs()
    {
        Cursor cursorr = getAllDataFigurs();
        // startManagingCursor(cursorr);
        Log.d("mLog", "----------------Figurs-------------------------");
        if (cursorr.moveToFirst()) {
            int idIndex = cursorr.getColumnIndex(DBHelper.EXTERNAL_KEY_ID);
            int parent = cursorr.getColumnIndex(DBHelper.FIGURS_ID_PICTURE);
            int color = cursorr.getColumnIndex(DBHelper.FIGURS_COLOR);
            int rotation = cursorr.getColumnIndex(DBHelper.FIGURS_ROTATION);
            int xx = cursorr.getColumnIndex(DBHelper.FIGURS_COORD_X);
            int yy = cursorr.getColumnIndex(DBHelper.FIGURS_COORD_Y);
            int start_x = cursorr.getColumnIndex(DBHelper.FIGURS_START_COORD_X);
            int start_y = cursorr.getColumnIndex(DBHelper.FIGURS_START_COORD_Y);
            do {
                Log.d("mLog", "ID = " + cursorr.getInt(idIndex) +
                        ", parent = " + cursorr.getString(parent) +
                        ", color = " + cursorr.getString(color) +
                        ", x = " + cursorr.getString(xx) +
                        ", y = " + cursorr.getString(yy) +
                        ", start x = " + cursorr.getString(start_x) +
                        ", start y = " + cursorr.getString(start_y) +
                        ", rotation = " + cursorr.getString(rotation));
            } while (cursorr.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursorr.close();
    }





    // добавить запись в DB_TABLE
    public void addRecContacts(String name, String email) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.CONTACTS_KEY_NAME, name);
        cv.put(DBHelper.CONTACTS_KEY_EMAIL, email);
        database.insert(DBHelper.TABLE_CONTACTS, null, cv);
    }


    public void addRecNote(Note note) {
        //id
        //
        database.execSQL("INSERT INTO "+ DBHelper.TABLE_NOTE + " VALUES ( " +
                "null, " +
                note._parentId + " , " +
                " ' " + note._theme + " '" + ", " +
                "'"+ note._text + "'" + ", " +
                "'" + note._imagePath + "'" +
                ","  + "' " + note._data + " '" + " )");

    }


    public void insertFigure(ParametersGeneratedImage image, int parent) {
        //id
        //
        database.execSQL("INSERT INTO "+ DBHelper.TABLE_FIGURS + " VALUES ( " +
                "null, " +
                parent + " , " +
                image._type + ", " +
                "'"+ image._rotation + "'" + ", " +
                image._color + ", "  +
                image.startPosX + ", "  +
                image.startPosY  + ", "  +
                image.coordinateX + ", "  +
                image.coordinateY  +
                " )");

    }







    public void updateRecNoteFromId(Note note){
        database.execSQL("UPDATE "+ DBHelper.TABLE_NOTE + " SET " +
                DBHelper.NOTE_KEY_IMAGE + " = " + "'" + note._imagePath + "' " + ", " +
                DBHelper.NOTE_KEY_NAME + " = " + "'" + note._theme + "' " + ", " +
                DBHelper.NOTE_KEY_TEXT + " = " + "'" + note._text + "' "  +
                " WHERE  " + DBHelper.EXTERNAL_KEY_ID + " = " + note._id + " ;");
        Log.d("mLog", "update method " +
            "name = " + note._theme +
            ", id = " + note._id);
    }
    public int CountNotes() {
        Cursor cursor = getAllDataNote();
        int res = 0;
        if (cursor.moveToFirst()) {
            do {
                res++;

            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return res;
    }
    public void UpdateHeaders(String id, String name){
        database.execSQL("UPDATE "+ DBHelper.TABLE_THEME_NOTE + " SET " + DBHelper.THEM_NOTE_KEY_HEADER + " = " +  " '" + name + "' "  + " where " + DBHelper.EXTERNAL_KEY_ID + " =" + id + " ;");
        PrintAllTheme();
    }

    public void UpdatePersonsNames(String id, String name){
        database.execSQL("UPDATE "+ DBHelper.TABLE_CONTACTS + " SET " + DBHelper.CONTACTS_KEY_NAME + " = " + "'" + name + "' " + " where " + DBHelper.EXTERNAL_KEY_ID + " =" + id + " ;");
        PrintAllContacts();
    }

    public int GET_VERSION(){
        return DBHelper.DATABASE_VERSION;
    }

}
