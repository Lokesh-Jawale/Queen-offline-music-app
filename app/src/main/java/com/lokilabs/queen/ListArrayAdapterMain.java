package com.lokilabs.queen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListArrayAdapterMain extends ArrayAdapter<ListContentProvider> {

    public ListArrayAdapterMain(Context context, ArrayList<ListContentProvider> list){
        super(context, 0, list);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main_list, parent, false);
        }

        ListContentProvider mlist = getItem(position);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.albumImage);
        assert mlist != null;
        imageView.setImageResource(mlist.getImageResource());

        TextView textView = (TextView)convertView.findViewById(R.id.albumName);
        textView.setText(mlist.getAlbumName());

        return convertView;
    }
}
