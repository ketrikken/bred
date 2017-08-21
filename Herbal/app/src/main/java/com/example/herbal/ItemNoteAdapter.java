package com.example.herbal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Алатиэль on 21.08.2017.
 */

public class ItemNoteAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemNote> objects;

    ItemNoteAdapter(Context context, ArrayList<ItemNote> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_note, parent, false);
        }

        ItemNote p = getItemNote(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.textViewTheme)).setText(p.GetTheme());
        ((TextView) view.findViewById(R.id.textViewData)).setText(p.GetData());

        Bitmap bmImg = BitmapFactory.decodeFile(p.GetPath());
        ((ImageView) view.findViewById(R.id.imageViewPreview)).setImageBitmap(bmImg);

        return view;
    }



    // товар по позиции
    ItemNote getItemNote(int position) {
        return ((ItemNote) getItem(position));
    }






}
