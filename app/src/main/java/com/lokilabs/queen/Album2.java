package com.lokilabs.queen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Album2 extends AppCompatActivity{

    final private String ALBUM2 = "Album: Innuendo";
    final private int INTRESOURCE = R.drawable.innuendo;
    public int songid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list_view);

        ArrayList<ListContentProvider> listArrayList = new ArrayList<>();

        listArrayList.add(new ListContentProvider("Innuendo", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("The Show must go on", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Bijou", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("I can't live without you", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Headlong", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("I'm going slightly mad", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Ride the wild wind", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("The Hitman", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("Don't try so hard", ALBUM2, INTRESOURCE));
        listArrayList.add(new ListContentProvider("All God's People", ALBUM2, INTRESOURCE));

        ListArrayAdapter adapter = new ListArrayAdapter(this, listArrayList);

        ListView listView = (ListView)findViewById(R.id.songs_list_view);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(Album2.this, ALBUM2+"listView"+position, Toast.LENGTH_SHORT).show();
                        if((MusicPlayer.shufflestate)&&(!MusicPlayer.playstate)){
                            MusicPlayer.playstate = true;
                            Log.d("aDebugTag","The playstate in Album3 class is ------ "+MusicPlayer.playstate);
                            //passing the position of the song and album no to the MusicPlayer class
                            MusicPlayer musicPlayer = new MusicPlayer(position, 2, MusicPlayer.playstate);
                        }
                        else{
                            MusicPlayer musicPlayer = new MusicPlayer(position, 2);
                        }

                        Log.d("aDebugTag", "The state of mediaPlayer in Album3 is"+MusicPlayer.mediaPlayer);
                        if(MusicPlayer.mediaPlayer != null){
                            MusicPlayer.mediaPlayer.stop();
                        }

                        Intent intent = new Intent(Album2.this, MusicPlayer.class);
                        startActivity(intent);
                    }
                }
        );

    }
}
