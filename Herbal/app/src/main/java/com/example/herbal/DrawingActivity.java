package com.example.herbal;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class DrawingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener, View.OnClickListener{


    private Integer markIdImage;
    private ImageView imageDel;
    private Integer idForNewItem;
    private RelativeLayout relativeMoveLayout;
    private SparseArrayCompat<ParametersGeneratedImage> mapGeneratedImage;
    private int leftX, bottomY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        InitOnCreate();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawing, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            NavCamera();
        }else if (id == R.id.colorRad){
            SetFilterIcons(R.color.colorIconsRad);
        }else if (id == R.id.colorNone){
            if (IsInMap()){
                ((ImageView)mapGeneratedImage.get(markIdImage).view).setColorFilter(null);
            }
        }else if (id == R.id.colorBlue){
            SetFilterIcons(R.color.colorIconsBlue);
        }else if (id == R.id.colorGreen){
            SetFilterIcons(R.color.colorIconsGreen);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        int xx, yy;
        int X = (int) event.getRawX();
        int Y = (int) event.getRawY();
        v.bringToFront();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            //ACTION_DOWN срабатывает при прикосновении к экрану,
            //здесь определяется начальное стартовое положение объекта:
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                mapGeneratedImage.get(id).coordinateX = X - lParams.leftMargin;
                mapGeneratedImage.get(id).coordinateY = Y - lParams.topMargin;


                leftX = imageDel.getLeft();
                bottomY = imageDel.getBottom();

                break;

            //ACTION_MOVE обрабатывает случившиеся в процессе прикосновения изменения, здесь
            //содержится информация о последней точке, где находится объект после окончания действия прикосновения ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin = X - mapGeneratedImage.get(id).coordinateX;
                layoutParams.topMargin = Y - mapGeneratedImage.get(id).coordinateY;
                layoutParams.bottomMargin = 0 - (int)1e5 ;
                layoutParams.rightMargin = 0 - (int)1e5 ;
                v.setLayoutParams(layoutParams);
                xx = v.getRight();
                yy = v.getTop();
                if (  xx > leftX && yy < bottomY) {
                    try {
                        //получаем родительский view и удаляем его
                        ((RelativeLayout) v.getParent()).removeView(v);
                        //удаляем эту же запись из массива что бы не оставалось мертвых записей
                        mapGeneratedImage.remove(id);
                    } catch(IndexOutOfBoundsException ex) {
                        Toast.makeText(this, "не удалилось", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (markIdImage == id){
            mapGeneratedImage.get(id).view.setBackgroundColor(Color.TRANSPARENT);
            markIdImage = Constants.INIT_VALUE_ID;
        } else if (mapGeneratedImage.get(markIdImage) != null){
            mapGeneratedImage.get(markIdImage).view.setBackgroundColor(Color.TRANSPARENT);
            markIdImage = id;
            mapGeneratedImage.get(id).view.setBackgroundColor(Color.parseColor("#ff64c2f4"));
        }else{
            markIdImage = id;
            mapGeneratedImage.get(id).view.setBackgroundColor(Color.parseColor("#ff64c2f4"));
        }

    }

    private void InitOnCreate() {
        ButtonScreen();
        markIdImage = Constants.INIT_VALUE_ID;
        ZoomClicks();
        idForNewItem = 0;
        mapGeneratedImage = new SparseArrayCompat<ParametersGeneratedImage>();
        imageDel = (ImageView) findViewById(R.id.imageDel);
        relativeMoveLayout = (RelativeLayout) findViewById(R.id.drawingGreed);
        InitImageButton();
    }
    private void ButtonScreen(){
        Button btnTakeScreenshot;
        btnTakeScreenshot= (Button) findViewById(R.id.btnTakeScreenshot);

        btnTakeScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetScreen screen = new GetScreen();
                screen.take2(DrawingActivity.this);
            }
        });
    }

    private void InitImageButton(){
        ImageButton btnRotate = (ImageButton) findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsInMap()){
                    return;
                }
                mapGeneratedImage.get(markIdImage).view.setRotation(mapGeneratedImage.get(markIdImage).view.getRotation() + 15);
            }
        });
    }
    private void SetFilterIcons(int color){
        if (IsInMap()){
            ((ImageView)mapGeneratedImage.get(markIdImage).view).setColorFilter(getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
        }
    }
    private boolean IsInMap(){
        if (mapGeneratedImage.get(markIdImage) == null){
            Toast.makeText(getApplicationContext(), "объект не выбран", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void NavCamera(){
        // Handle the camera action
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Constants.START_POSITION, Constants.START_POSITION);
        View view = getLayoutInflater().inflate(R.layout.im_view, null);
        view.setLayoutParams(layoutParams);

        (view.findViewById(R.id.imm)).setId(idForNewItem);

        mapGeneratedImage.put(idForNewItem, new ParametersGeneratedImage(view));
        relativeMoveLayout.addView(view);

        view.setOnClickListener(this);
        view.setOnTouchListener(this);


        Toast.makeText(getApplicationContext(), "id: " + view.getId(), Toast.LENGTH_SHORT).show();
        idForNewItem++;
    }
    private void ZoomClicks(){
        ImageButton plus = (ImageButton)findViewById(R.id.imageButtonPlus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsInMap()){
                    return;
                }

                float x = mapGeneratedImage.get(markIdImage).view.getScaleX();
                float y = mapGeneratedImage.get(markIdImage).view.getScaleY();

                mapGeneratedImage.get(markIdImage).view.setScaleX((float) (x+0.1));
                mapGeneratedImage.get(markIdImage).view.setScaleY((float) (y+0.1));
            }
        });

        ImageButton minus = (ImageButton) findViewById(R.id.imageButtonMinus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsInMap()){
                    return;
                }
                float x = mapGeneratedImage.get(markIdImage).view.getScaleX();
                float y = mapGeneratedImage.get(markIdImage).view.getScaleY();

                if (x - 0.1 > 0){

                    mapGeneratedImage.get(markIdImage).view.setScaleX((float) (x-0.1));
                    mapGeneratedImage.get(markIdImage).view.setScaleY((float) (y-0.1));
                }
            }
        });
    }
}
