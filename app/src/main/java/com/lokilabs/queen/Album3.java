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

public class Album3 extends AppCompatActivity{

    final private String ALBUM3 = "Album: Bohemian Rhapsody";
    final private int INTRESOURCE = R.drawable.top27;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list_view);

        ArrayList<ListContentProvider> listContentProviders = new ArrayList<>();

        listContentProviders.add(new ListContentProvider("Bohemian Rhapsody", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Another one bites the dust", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("The show must go on", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Crazy little things called love", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Fat Bottomed girls", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Hammer to fall", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Killer Queen", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("I want to break free", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Don't stop me now", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Under Pressure", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Somebody to love", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("We are the Champions", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("We will rock you", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Radio Ga Ga", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Doing all right", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Who wants to live forever", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Ah-Oh", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Now I'm here", ALBUM3, INTRESOURCE));
        listContentProviders.add(new ListContentProvider("Keep yourself alive", ALBUM3, INTRESOURCE));

        ListArrayAdapter listAdapter = new ListArrayAdapter(this, listContentProviders);

        ListView listView = (ListView)findViewById(R.id.songs_list_view);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if((MusicPlayer.shufflestate)&&(!MusicPlayer.playstate)){
                            MusicPlayer.playstate = true;
                            Log.d("aDebugTag","The playstate in Album3 class is ------ "+MusicPlayer.playstate);
                            //passing the position of the song and album no to the MusicPlayer class
                            MusicPlayer musicPlayer = new MusicPlayer(position, 3, MusicPlayer.playstate);
                        }
                        else{
                            MusicPlayer musicPlayer = new MusicPlayer(position, 3);
                        }

                        Log.d("aDebugTag", "The state of mediaPlayer in Album3 is"+MusicPlayer.mediaPlayer);
                        if(MusicPlayer.mediaPlayer != null){
                            MusicPlayer.mediaPlayer.stop();
                        }

                        //intent to the MusicPlayer Class
                        Intent intent = new Intent(Album3.this, MusicPlayer.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
