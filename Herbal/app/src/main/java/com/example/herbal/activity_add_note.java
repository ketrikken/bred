package com.example.herbal;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class activity_add_note extends AppCompatActivity {

    private String pathToPicture;
    private String _id;
    private Note currentNote;
    private Database database;
    private EditText noteMainText, noteTheme;
    private TextView noteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        FAB();
        database = new Database(this);
        database.open();

        noteData = (TextView) findViewById(R.id.textViewCreateData);
        noteMainText = (EditText) findViewById(R.id.editTextNodeMainInformation);
        noteTheme = (EditText) findViewById(R.id.headerText);

        try{
            _id = getIntent().getStringExtra("id");

        } catch(Throwable t) {
            Toast.makeText(getApplicationContext(), "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
            Log.d("mLog", "Exception: " + t.toString());
        }

        FillInForm();
        database.PrintAllNote();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings_add_note:
                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void FAB(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAddPicturePath);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v, "BD is fill in", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OnOpenFileClick(v);
            }
        });
    }

    public void OnOpenFileClick(View view) {
        OpenFileDialog fileDialog = new OpenFileDialog(this)
                .setFilter(".*\\.jpeg")
                .setOpenDialogListener(new OpenFileDialog.OpenDialogListener() {
                    @Override
                    public void OnSelectedFile(String fileName) {
                        //Log.d("mLog", fileName);
                        Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_LONG).show();
                        pathToPicture = fileName;

                        Bitmap bmImg = BitmapFactory.decodeFile(fileName);
                        ((ImageView)findViewById(R.id.imageViewLoadedFromMemory)).setImageBitmap(bmImg);
                    }
                });
        fileDialog.show();
    }

    void FillInForm(){
        if (_id == null || _id.equals("")){
            SetCurrentData();
            return;
        }
        currentNote = ParseCursorNote(database.getOneNoteFromID(_id));
        noteMainText.setText(currentNote._text);
        noteTheme.setText(currentNote._theme);
        noteData.setText(currentNote._data);

    }
    Note ParseCursorNote(Cursor cursor){

        Note note = new Note();
        if (cursor.moveToFirst()) {
            note._imagePath = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_IMAGE));
            note._text = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_TEXT));
            note._theme = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_NAME));
            note._data = cursor.getString(cursor.getColumnIndex(DBHelper.NOTE_KEY_CREATEDATA));
        } else
            Log.d("mLog","Parse Cursor Error !!!!!!!!!!!!");



        return note;
    }

    boolean CheckImagePath()
    {
        return true;
    }
    void SetCurrentData(){
        final Calendar c = Calendar.getInstance();
        int yy = c.get(Calendar.YEAR);
        int mm = c.get(Calendar.MONTH);
        int dd = c.get(Calendar.DAY_OF_MONTH);
        noteData.setText(new StringBuilder()
                .append(yy).append(" ").append("-").append(mm + 1).append("-")
                .append(dd));
        currentNote._data = noteData.getText().toString();
    }
}
