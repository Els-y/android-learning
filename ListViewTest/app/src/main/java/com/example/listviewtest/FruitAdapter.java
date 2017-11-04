package com.example.listviewtest;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by els on 17-3-4.
 */

public class FruitAdapter extends ArrayAdapter {
    private int resourceId;
    public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        Fruit item = (Fruit) getItem(position);
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.textView = (TextView) view.findViewById(R.id.fruit_text);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setImageResource(item.getImageId());
        viewHolder.textView.setText(item.getName());

        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
