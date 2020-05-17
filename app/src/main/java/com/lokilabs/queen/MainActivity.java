package com.lokilabs.queen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int ALBUM1 = R.drawable.operanightsquare;
    final int ALBUM2 = R.drawable.innuendosquare;
    final int ALBUM3 = R.drawable.top27square;
    final int ALBUM4 = R.drawable.br1square;
    int albumNumber = 1;
    int x = ALBUM1;

    private ImageView albumImage;
    private TextView albumname;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        albumImage = (ImageView)findViewById(R.id.albumimage);
//        albumImage.setImageResource(x);
//
//        Toast.makeText(MainActivity.this, "Click on buttons to change Albums", Toast.LENGTH_LONG).show();

        ArrayList<ListContentProvider> listContentProviderArrayList = new ArrayList<>();

        listContentProviderArrayList.add(new ListContentProvider("A Night at the Opera", ALBUM1));
        listContentProviderArrayList.add(new ListContentProvider("Innuendo", ALBUM2));
        listContentProviderArrayList.add(new ListContentProvider("Bohemian Rhapsody", ALBUM3));
        listContentProviderArrayList.add(new ListContentProvider("Top 27 songs~Queen",ALBUM4));

        ListArrayAdapterMain adapterMain = new ListArrayAdapterMain(this, listContentProviderArrayList);

        //setting the list view
        ListView listView = (ListView)findViewById(R.id.activity_main_list_view);
        listView.setAdapter(adapterMain);

        listView.setOnItemClickListener(
                new ListView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if(position == 0){
                            Intent intent = new Intent(MainActivity.this, Album1.class);
                            startActivity(intent);
                        }
                        if(position == 1){
                            Intent intent = new Intent(MainActivity.this, Album2.class);
                            startActivity(intent);
                        }
                        if(position == 2){
                            Intent intent = new Intent(MainActivity.this, Album3.class);
                            startActivity(intent);
                        }
                        if(position == 3) {
                            Intent intent = new Intent(MainActivity.this, Album4.class);
                            startActivity(intent);
                        }
                    }
                }
        );

    }

    //Creating the albumClicked method for opening of the album
//    public void albumClicked(View view){
//        Toast.makeText(MainActivity.this,"Album Clicked"+ x, Toast.LENGTH_SHORT).show();
//        //setting an intent to start other activity if album clicked
//        if(x == ALBUM1){
//            intent = new Intent(this, Album1.class);
//            startActivity(intent);
//        }
//        if(x == ALBUM2){
//            intent = new Intent(this, Album2.class);
//            startActivity(intent);
//        }
//        if(x == ALBUM3){
//            intent = new Intent(this, Album3.class);
//            startActivity(intent);
//        }
//        if(x == ALBUM4){
//            intent = new Intent(this, Album4.class);
//            startActivity(intent);
//        }
//    }
//
//    //Creating nextAlbum and lastAlbum method for changing the album
//    public void nextAlbum(View view){
//
//        //Creating a reference to the album name
//        albumname = (TextView)findViewById(R.id.albumname);
//
//        //code for changing the next image
//        albumNumber++;
//        if(albumNumber > 4){
//            albumNumber = 1;
//            x = ALBUM1;
//            albumname.setText("A Night At Opera");
//        }
//        else{
//            if(albumNumber == 1){
//                x = ALBUM1;
//                albumname.setText("A Night At Opera");
//            }
//            if(albumNumber == 2){
//                x = ALBUM2;
//                albumname.setText("Innuendo");
//            }
//            if(albumNumber == 3){
//                x = ALBUM3;
//                albumname.setText("Bohemian Rhapsody");
//            }
//            if(albumNumber == 4){
//                x = ALBUM4;
//                albumname.setText("Queen's top 27 songs");
//            }
//        }
//
//        //Creating a reference to the imageView to set the image
//        albumImage = (ImageView)findViewById(R.id.albumimage);
//        albumImage.setImageResource(x);
//    }
//
    // gets the last album
//    public void lastAlbum(View view){
//        albumNumber--;
//        if(albumNumber < 1){
//            albumNumber = 4;
//            x = ALBUM4;
//            albumname.setText("Queen's top 27 songs");
//        }
//        else{
//            if(albumNumber == 1){
//                x = ALBUM1;
//                albumname.setText("A Night At Opera");
//            }
//            if(albumNumber == 2){
//                x = ALBUM2;
//                albumname.setText("Innuendo");
//            }
//            if(albumNumber == 3){
//                x = ALBUM3;
//                albumname.setText("Bohemian Rhapsody");
//            }
//            if(albumNumber == 4){
//                x = ALBUM4;
//                albumname.setText("Queen's top 27 songs");
//            }
//        }
//
//        albumImage = (ImageView)findViewById(R.id.albumimage);
//        albumImage.setImageResource(x);
//
//    }
}

