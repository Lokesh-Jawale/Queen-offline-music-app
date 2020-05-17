package com.lokilabs.queen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Album4 extends AppCompatActivity{

    final private String ALBUM4 = "Album: Queen's top 27 songs";
    final private int INTRESOURCE = R.drawable.br1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list_view);

        ArrayList<ListContentProvider> listContentProviders = new ArrayList<>();

        listContentProviders.add(new ListContentProvider("Bohemian Rhapsody", ALBUM4, INTRESOURCE, 1));
        listContentProviders.add(new ListContentProvider("Under Pressure", ALBUM4, INTRESOURCE, 2));
        listContentProviders.add(new ListContentProvider("We will Rock you", ALBUM4, INTRESOURCE,3));
        listContentProviders.add(new ListContentProvider("Fat Bottomed girls", ALBUM4, INTRESOURCE,4));
        listContentProviders.add(new ListContentProvider("Killer Queen", ALBUM4, INTRESOURCE,5));
        listContentProviders.add(new ListContentProvider("Tie your mother down", ALBUM4, INTRESOURCE,6));
        listContentProviders.add(new ListContentProvider("We are the champions", ALBUM4, INTRESOURCE,7));
        listContentProviders.add(new ListContentProvider("Crazy little things called love", ALBUM4, INTRESOURCE,8));
        listContentProviders.add(new ListContentProvider("You are my best friend", ALBUM4, INTRESOURCE,9));
        listContentProviders.add(new ListContentProvider("Stone cold crazy", ALBUM4, INTRESOURCE,10));
        listContentProviders.add(new ListContentProvider("Another one bites the dust", ALBUM4, INTRESOURCE,11));
        listContentProviders.add(new ListContentProvider("Radio Ga Ga", ALBUM4, INTRESOURCE,12));
        listContentProviders.add(new ListContentProvider("Now I'm here", ALBUM4, INTRESOURCE,13));
        listContentProviders.add(new ListContentProvider("I want it all", ALBUM4, INTRESOURCE,14));
        listContentProviders.add(new ListContentProvider("Who wants to live forever", ALBUM4, INTRESOURCE,15));
        listContentProviders.add(new ListContentProvider("Brington rock", ALBUM4, INTRESOURCE,16));
        listContentProviders.add(new ListContentProvider("Keep yourself alive", ALBUM4, INTRESOURCE,17));
        listContentProviders.add(new ListContentProvider("SomeBody to love", ALBUM4, INTRESOURCE,18));
        listContentProviders.add(new ListContentProvider("Seven seas of Rhye", ALBUM4, INTRESOURCE,19));
        listContentProviders.add(new ListContentProvider("Need your Loving Tonight", ALBUM4, INTRESOURCE,20));
        listContentProviders.add(new ListContentProvider("39", ALBUM4, INTRESOURCE,21));
        listContentProviders.add(new ListContentProvider("Don't stop me now", ALBUM4, INTRESOURCE,22));
        listContentProviders.add(new ListContentProvider("Hammer to fall", ALBUM4, INTRESOURCE,23));
        listContentProviders.add(new ListContentProvider("In the Lap of the Gods", ALBUM4, INTRESOURCE,24));
        listContentProviders.add(new ListContentProvider("Tenement Funster", ALBUM4, INTRESOURCE,25));
        listContentProviders.add(new ListContentProvider("I want to break free", ALBUM4, INTRESOURCE,26));
        listContentProviders.add(new ListContentProvider("Love of my life", ALBUM4, INTRESOURCE,27));

        ListArrayAdapter listArrayAdapter = new ListArrayAdapter(this, listContentProviders);

        ListView listView = (ListView)findViewById(R.id.songs_list_view);

        listView.setAdapter(listArrayAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if((MusicPlayer.shufflestate)&&(!MusicPlayer.playstate)){
                            MusicPlayer.playstate = true;
                            Log.d("aDebugTag","The playstate in Album3 class is ------ "+MusicPlayer.playstate);
                            //passing the position of the song and album no to the MusicPlayer class
                            MusicPlayer musicPlayer = new MusicPlayer(position, 4, MusicPlayer.playstate);
                        }
                        else{
                            MusicPlayer musicPlayer = new MusicPlayer(position, 4);
                        }

                        Log.d("aDebugTag", "The state of mediaPlayer in Album3 is"+MusicPlayer.mediaPlayer);
                        if(MusicPlayer.mediaPlayer != null){
                            MusicPlayer.mediaPlayer.stop();
                        }

                        //Setting and intent to the music player
                        Intent intent = new Intent(Album4.this, MusicPlayer.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
