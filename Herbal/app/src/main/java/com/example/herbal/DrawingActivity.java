package com.example.herbal;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;





public class DrawingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener, View.OnClickListener{


    private Integer markIdImage;
    private ImageView ImDel;
    private Integer idd;
    private RelativeLayout mMoveLayout;
    private SparseArrayCompat< Ppopa> mapView;
    private int leftX, bottomY;
    private ZoomControls zoomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        markIdImage = -250;
        zoomBtn = (ZoomControls) findViewById(R.id.btnZoom);
        ZoomClicks();
        idd = 0;
        mapView = new SparseArrayCompat<Ppopa>();
        ImDel = (ImageView) findViewById(R.id.imageDel);
        mMoveLayout = (RelativeLayout) findViewById(R.id.drawingGreed);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void ZoomClicks(){
        zoomBtn.setOnZoomInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsInMap()){
                    return;
                }

                float x = mapView.get(markIdImage).view.getScaleX();
                float y = mapView.get(markIdImage).view.getScaleY();

                mapView.get(markIdImage).view.setScaleX((float) (x+0.1));
                mapView.get(markIdImage).view.setScaleY((float) (y+0.1));
            }
        });

        zoomBtn.setOnZoomOutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!IsInMap()){
                    return;
                }
                float x = mapView.get(markIdImage).view.getScaleX();
                float y = mapView.get(markIdImage).view.getScaleY();

                if (x - 0.1 > 0){

                    mapView.get(markIdImage).view.setScaleX((float) (x-0.1));
                    mapView.get(markIdImage).view.setScaleY((float) (y-0.1));
                }
            }
        });
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            NavCamera();

        }else if (id == R.id.colorRad){
            SetFilterIcons(R.color.colorIconsRad);
        }else if (id == R.id.colorNone){
            if (IsInMap()){
                ((ImageView)mapView.get(markIdImage).view).setColorFilter(null);
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

    void SetFilterIcons(int color){
        if (IsInMap()){
            ((ImageView)mapView.get(markIdImage).view).setColorFilter(getResources().getColor(color), PorterDuff.Mode.MULTIPLY);
        }
    }
    boolean IsInMap(){
        if (mapView.get(markIdImage) == null){
            Toast.makeText(getApplicationContext(), "объект не выбран", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    void NavCamera(){
        // Handle the camera action
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        View view = getLayoutInflater().inflate(R.layout.im_view, null);
        view.setLayoutParams(layoutParams);

        (view.findViewById(R.id.imm)).setId(idd);

        mapView.put(idd, new Ppopa(view));
        mMoveLayout.addView(view);

        view.setOnClickListener(this);
        view.setOnTouchListener(this);


        Toast.makeText(getApplicationContext(), "id: " + view.getId(), Toast.LENGTH_SHORT).show();
        idd++;
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
                mapView.get(id).xx = X - lParams.leftMargin;
                mapView.get(id).yy = Y - lParams.topMargin;


                leftX = ImDel.getLeft();
                bottomY = ImDel.getBottom();

                break;

            //ACTION_MOVE обрабатывает случившиеся в процессе прикосновения изменения, здесь
            //содержится информация о последней точке, где находится объект после окончания действия прикосновения ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                layoutParams.leftMargin = X - mapView.get(id).xx;
                layoutParams.topMargin = Y - mapView.get(id).yy;
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
                        mapView.remove(id);
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
            mapView.get(id).view.setBackgroundColor(Color.TRANSPARENT);
            markIdImage = -250;
        } else if (mapView.get(markIdImage) != null){
            mapView.get(markIdImage).view.setBackgroundColor(Color.TRANSPARENT);
            markIdImage = id;
            mapView.get(id).view.setBackgroundColor(Color.parseColor("#ff64c2f4"));
        }else{
            markIdImage = id;
            mapView.get(id).view.setBackgroundColor(Color.parseColor("#ff64c2f4"));
        }

    }
}
