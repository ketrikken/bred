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
import android.support.v7.widget.ContentFrameLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class activity_add_note extends AppCompatActivity {

  /*  public interface onChangeNoteEventListener {
        public void createNote(Note note);
        public void updateNote(Note note);
    }

    onChangeNoteEventListener ChangeNoteEventListener;*/

   // private String _parentId, _currentId;
    private Note currentNote;
    private Database database;
    private EditText noteMainText, noteTheme;
    private TextView noteData;
    private Button btnSave;
    private ImageView noteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        database = new Database(this);
        database.open();

        noteData = (TextView) findViewById(R.id.textViewCreateData);
        noteMainText = (EditText) findViewById(R.id.editTextNodeMainInformation);
        noteTheme = (EditText) findViewById(R.id.headerText);
        btnSave = (Button) findViewById(R.id.buttonSaveNote);
        noteImage = (ImageView)findViewById(R.id.imageViewLoadedFromMemory);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateFileds();
                if (currentNote._id == null || currentNote._id.equals(""))
                {
                    // сохраняем новую запись
                    // не тестено
                    database.addRecNote(currentNote);
                    //ChangeNoteEventListener.createNote(currentNote);
                }else {
                    // изменяем текущую
                    database.updateRecNoteFromId(currentNote);
                    Log.d("mLog", currentNote._id + " " + currentNote._theme);
                    //ChangeNoteEventListener.updateNote(currentNote);
                }
                finish();
            }
        });

        FillInForm();
        FAB();
    }

    protected void onDestroy() {
        super.onDestroy();
        database.close();
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
                        currentNote._imagePath = fileName;

                        Bitmap bmImg = BitmapFactory.decodeFile(fileName);
                        ((ImageView)findViewById(R.id.imageViewLoadedFromMemory)).setImageBitmap(bmImg);
                    }
                });
        fileDialog.show();
    }

    void FillInForm(){
        //получить строку бд
        //currentNote = ParseCursorNote(database.getOneNoteFromID(_currentId));
        currentNote = (Note) getIntent().getParcelableExtra(
                Note.class.getCanonicalName());
        if (currentNote._id == null || currentNote._id.equals("")){
            SetCurrentData();
            return;
        }


        noteMainText.setText(currentNote._text);
        noteTheme.setText(currentNote._theme);
        noteData.setText(currentNote._data);
        if (!CheckImagePath(currentNote._imagePath)){
            currentNote._imagePath = null;
            SetDefaultImage();
        }else{
            SetImageFromBD();
        }

    }
    void UpdateFileds(){
        currentNote._theme = noteTheme.getText().toString();
        currentNote._text = noteMainText.getText().toString();
    }


    void SetImageFromBD(){
        File fileTemp = new File(currentNote._imagePath);
        Bitmap myBitmapp = BitmapFactory.decodeFile(fileTemp.getAbsolutePath());
        noteImage.setImageBitmap(myBitmapp);
    }
    void SetDefaultImage(){
        noteImage.setImageResource(R.mipmap.ic_witch);
    }
    boolean CheckImagePath(String path)
    {
        File imgFile = new File(path);
        if(imgFile.exists()) {
            return true;
        }
        return false;
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
