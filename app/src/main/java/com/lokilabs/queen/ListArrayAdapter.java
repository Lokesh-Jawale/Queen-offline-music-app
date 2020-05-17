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

class ListArrayAdapter extends ArrayAdapter<ListContentProvider> {

    private ListContentProvider mlist;

    public ListArrayAdapter(Context context, ArrayList<ListContentProvider> list){
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //getting the position of the element
        mlist = getItem(position);

        //taking care of null convertView by inflating a layout to it
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.songs_list_layout, parent, false);
        }

        //Adding the textViews and imageViews which are going to take the input as the element from the arraylist
        TextView albumname = (TextView)convertView.findViewById(R.id.text2);
        if (mlist == null) throw new AssertionError();
        albumname.setText(mlist.getAlbumName());

        TextView songname = (TextView)convertView.findViewById(R.id.text1);
        songname.setText(mlist.getSongName());

        ImageView albumimage = (ImageView)convertView.findViewById(R.id.image);
        albumimage.setImageResource(mlist.getImageResource());

        //Setting a condition so that only the Album four should be able to access it
        if(mlist.getSongNo() != -1) {
            TextView songno = (TextView) convertView.findViewById(R.id.songNo);
            songno.setText(""+mlist.getSongNo());
        }
        else{
            TextView songno = (TextView) convertView.findViewById(R.id.songNo);
            songno.setVisibility(View.GONE);
        }

        return convertView;
    }
}
