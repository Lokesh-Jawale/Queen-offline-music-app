package com.lokilabs.queen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Album1 extends AppCompatActivity{

    final private String ALBUMNAME1="Album: "+"A Night at the Opera";
    final private int INTRESOURCE = R.drawable.operanight;
    public int songid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list_view);

        //Creating an arraylist to be passed for adapter
        //arraylist include 2 textViews and 1 image
        //passing the contentProvieder as the arraylist type
        ArrayList<ListContentProvider> listArrayList = new ArrayList<>();

        //adding the elements to the arraylist
        listArrayList.add(new ListContentProvider("Bohemian Rhapsody", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Love of My Life", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("39", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("You are my bestfriend", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Good Company", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("I'm in love with my car", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Lazing on a Sunday Afternoon", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Seaside Rendezvous", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Sweet Lady", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("The Prophets song", ALBUMNAME1, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Death on two legs", ALBUMNAME1, INTRESOURCE));

        ListArrayAdapter adapter = new ListArrayAdapter(this, listArrayList);

        ListView listView =(ListView)findViewById(R.id.songs_list_view);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
               new AdapterView.OnItemClickListener(){
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Toast.makeText(Album1.this, id+"listView Clicked"+position, Toast.LENGTH_SHORT).show();

                       if((MusicPlayer.shufflestate)&&(!MusicPlayer.playstate)){
                           MusicPlayer.playstate = true;
                           Log.d("aDebugTag","The playstate in Album3 class is ------ "+MusicPlayer.playstate);
                           //passing the position of the song and album no to the MusicPlayer class
                           MusicPlayer musicPlayer = new MusicPlayer(position, 1, MusicPlayer.playstate);
                       }
                       else{
                           MusicPlayer musicPlayer = new MusicPlayer(position, 1);
                       }

                       Log.d("aDebugTag", "The state of mediaPlayer in Album3 is"+MusicPlayer.mediaPlayer);
                       if(MusicPlayer.mediaPlayer != null){
                           MusicPlayer.mediaPlayer.stop();
                       }


                       Intent intent = new Intent(Album1.this, MusicPlayer.class);
                       startActivity(intent);
                   }
               }
        );
    }
}
