package com.example.herbal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by Алатиэль on 10.08.2017.
 */

public class GetScreen {

    public void take2(Activity activity, String fileName){
        store( takeScreenShot2(activity), fileName + "JPEG");
    }

    public void takeScreenshot(Activity activity) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        View v1 = activity.getWindow().getDecorView().getRootView();
        store(getScreenShot(v1), "new.JPEG");

    }
    public Bitmap takeScreenShot2(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;


        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();


        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
    private void shareImage(Activity activity, File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            activity.startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity.getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }
    public void store(/*Activity activity, */Bitmap bm, String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Herbal/Screenshots";
        //Log.d("mLog", Environment.getExternalStorageDirectory().getAbsolutePath() + "/Herbal/Screenshots");
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
           // Toast.makeText(activity.getApplicationContext(), "что-то выполено", Toast.LENGTH_SHORT).show();
            FileOutputStream fOut = new FileOutputStream(file);


            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();

            //openScreenshot(activity, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openScreenshot(Activity activity, File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        activity.startActivity(intent);
    }
}
