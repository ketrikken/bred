package com.example.herbal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDrive, btnList, btnAddNote, btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnDrive = (Button) findViewById(R.id.buttonDraw);
        btnDrive.setOnClickListener(this);

        btnList = (Button) findViewById(R.id.buttonList);
        btnList.setOnClickListener(this);

       /* btnAddNote = (Button) findViewById(R.id.buttonAddNote);
        btnAddNote.setOnClickListener(this);*/


        btn = (Button)findViewById(R.id.ListBdNew);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.buttonDraw:
                intent = new Intent(v.getContext(), DrawingActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonList:
                intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                break;
            /*case R.id.buttonAddNote:
                intent = new Intent(v.getContext(), activity_add_note.class);
                startActivity(intent);
                break;*/
            case R.id.ListBdNew:
                intent = new Intent(v.getContext(), ThemListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
