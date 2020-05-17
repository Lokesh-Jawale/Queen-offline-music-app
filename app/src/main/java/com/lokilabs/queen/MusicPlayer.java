package com.lokilabs.queen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.media.AudioManager;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;


import java.util.Random;


public class MusicPlayer extends AppCompatActivity{

    private boolean keeplaying;

    public static boolean shufflestate;
    public static boolean playstate;
    public static boolean repeatstate;

    int totalTime;
    SeekBar progressBar;
    TextView elapsedTimeView;
    TextView endTimeView;

    private static int position;
    private static int songid;
    private ImageButton imageButton, skipNextButton, skipPreviousButton, repeatButton, shuffleButton;
    private static int position1;
    private static int albumNo;
    private boolean pausestate = true;
    private String musicName;
    private static int imgResource = R.drawable.top27square;

    private ImageView imageView;
    public static MediaPlayer mediaPlayer;

    private AudioManager audioManager;
    private AudioFocusRequest audioFocusRequest;
    private AudioAttributes audioAttributes;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;

    public MusicPlayer(){

    }
    public MusicPlayer(int position, int albumNo){
        position1 = position;
        MusicPlayer.albumNo = albumNo;
        Log.d("aDebugTag", "Constructor called sameApp "+ position1);
        //songid = getsongid(position1);
    }
    public MusicPlayer(int position, int albumNo, boolean playstate){
        position1 = position;
        MusicPlayer.albumNo = albumNo;
        if((playstate)&&(MusicPlayer.shufflestate)) {
            MusicPlayer.playstate = true;
        }
        Log.d("aDebugTag", "Constructor called "+ position1);
        //songid = getsongid(position1);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_activity);

        //Setting references to different buttons in music_player_activity in xml
        imageButton = (ImageButton) findViewById(R.id.playpausebutton);
        skipNextButton = (ImageButton) findViewById(R.id.skipnextbutton);
        skipPreviousButton = (ImageButton) findViewById(R.id.skippreviousbutton);
        repeatButton = (ImageButton) findViewById(R.id.repeatbutton);
        shuffleButton = (ImageButton)findViewById(R.id.shufflebutton);

        // setting the background color for the repeat button
//        if (repeatstate) {
//            repeatButton.setBackgroundColor(Color.GRAY);
//            playnextrepeat(MusicPlayer.position1);
//        }
//        else if(shufflestate){
//            shuffleButton.setBackgroundColor(Color.GRAY);
//        }

        // When the REPEAT is ON
        Log.d("aDebugTag", "The value of playstate and repeastate when new activity starts"+playstate+repeatstate);
        if((!playstate)&&(repeatstate)){
            repeatButton.setColorFilter(Color.RED);
            MusicPlayer.position = MusicPlayer.position1;
        }
        // When the SHUFFLE is ON
        if((MusicPlayer.shufflestate)&&(MusicPlayer.playstate)){
            Log.d("aDebugTag", "THe color is change the playState ////"+MusicPlayer.playstate);
            shuffleButton.setColorFilter(Color.RED);
        }else {
            if (MusicPlayer.shufflestate) {
                shuffleButton.setColorFilter(Color.RED);
                playShuffle();
            }
        }

        //setting the textViews for timeELAPSED and TimeRemained
        elapsedTimeView = (TextView)findViewById(R.id.time1);
        endTimeView = (TextView)findViewById(R.id.time2);
        //calling the setmediaplayer method to set the media player for playing the song
        setMediaPlayer();

        //Setting the progress bar
        setProgressBar();

        //method for setting the views in xml file
        setViews();

        //methods for setting the PLAY or PAUSE BUTTON  /  skip next/previous BUTTON
        setImageButton();

        //calling the checkFinished() method for checking whether the song is finished or not
        checkFinished();

        //adding notificationCompact.mediastyle to the app
        createNotification();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotification(){

        //Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = musicName;
            String description = musicName;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("101", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder notification = new Notification.Builder(MusicPlayer.this, "101")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(musicName)
                .setContentText("Queen");


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(101, notification.build());

    }
//    @TargetApi(Build.VERSION_CODES.O)
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void setNotification(){
//        // creating an object of the notifiation using notification.builder
//
//        MediaSession mediaSession = new MediaSession(this, "aDebugTag");
//
//        Notification notification = new Notification.Builder(this, "101")
//                // Show controls on lock screen even when user hides sensitive content.
//                .setVisibility(Notification.VISIBILITY_PUBLIC)
//                .setSmallIcon(imgResource)
//                // Add media control buttons that invoke intents in your media service
//                .addAction(R.drawable.ic_skip_previous_black_24dp, "Previous", playprevious()) // #0
//                .addAction(R.drawable.ic_pause_circle_filled_black_24dp, "Pause", pause())  // #1
//                .addAction(R.drawable.ic_skip_next_black_24dp, "Next", playnext())     // #2
//                // Apply the media style template
//                .setStyle(new Notification.MediaStyle()
//                        .setShowActionsInCompactView(1 /* #1: pause button */)
//                        .setMediaSession(mediaSession.getSessionToken()))
//                .setContentTitle(musicName)
//                .build();
//
//
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = musicName;
//            String description = musicName;
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel("101", name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }

    public void setProgressBar(){

        progressBar = (SeekBar) findViewById(R.id.progressBar);
        progressBar.setMax(totalTime);

        //on cliking the seek bar
        progressBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser) {
                            mediaPlayer.seekTo(progress);
                            progressBar.setProgress(progress);
                        }
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
        //Thread for updating the progressBar and the time elapsed
        new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    while(MusicPlayer.mediaPlayer != null) {
                        Message msg = new Message();
                        msg.what = MusicPlayer.mediaPlayer.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            //updating the progressBar
            progressBar.setProgress(currentPosition);

            //updating the time elapsed
            String time = getElapsedTime(currentPosition);
            elapsedTimeView.setText(time);

            //update the end time
            time = getElapsedTime(totalTime);
            endTimeView.setText(time);

//            if(currentPosition == totalTime){
//                playnext();
//            }
        }
    };

    public String getElapsedTime(int currentPosition){
        String time = "";
        int min = (currentPosition / 1000) / 60;
        int sec = (currentPosition / 1000) % 60;

        time = min+":";
        if(sec < 10)
            time += "0"+sec;
        else
            time += sec;

        return time;
    }

    public void playShuffle(){
        if(MusicPlayer.albumNo == 1){
            MusicPlayer.position1 = (int)(Math.random()*12);
        }
        if(MusicPlayer.albumNo == 2){
            MusicPlayer.position1 = (int)(Math.random()*11);
        }
        if(MusicPlayer.albumNo == 3){
            MusicPlayer.position1 = (int)(Math.random()*20);
        }
        if(MusicPlayer.albumNo == 4)
            MusicPlayer.position1 = (int)(Math.random()*28);
        else
            MusicPlayer.position1 = (int)(Math.random()*11);
    }

    @TargetApi(Build.VERSION_CODES.O)
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setMediaPlayer(){
        releaseMedia();
        Log.d("aDebugTag", mediaPlayer + " is mediaPlayer BEFORE started in Music player class");

        //Setting the AudioManager, AudioAttributes and AudioFocusRequest object
        audioManager = (AudioManager)this.getSystemService(AUDIO_SERVICE);

        // Starting the MediaPlayer and calling getSongid for getting the songid and passing it as resource to raw song
        mediaPlayer = MediaPlayer.create(MusicPlayer.this, getSongid(position1, albumNo));
        mediaPlayer.seekTo(0);
        totalTime = mediaPlayer.getDuration();
        Log.d("aDebugTag", mediaPlayer + " is mediaPlayer started in Music player class");
        mediaPlayer.start();

        // setting the audioAttributes
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                    mediaPlayer.start();
                }
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                    audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
                    mediaPlayer.pause();
                }
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT){
                    mediaPlayer.pause();
//                                    audioManager.abandonAudioFocusRequest(audioFocusRequest);
                }
                if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                    audioFocusRequest.willPauseWhenDucked();
                }
            }
        };

        // making the object of audioFocusRequest
        audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttributes)
                .setOnAudioFocusChangeListener(mOnAudioFocusChangeListener)
                .setAcceptsDelayedFocusGain(true)
                .build();

        int focuschange = audioManager.requestAudioFocus(audioFocusRequest);
        switch (focuschange){
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                mediaPlayer.start();
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                mediaPlayer.pause();
                break;
        }

    }

    //method to called for checking whether the media has done playing the song or not
    //if musicPlayer is done playing the song then it will call the checkfinished method and check whether the media has finished the
    // playing or not and playnext song or play shuffle song
    public void checkFinished(){
        //adding the method for what happens on song completion
        mediaPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(keeplaying){
                            releaseMedia();

                            if((!playstate)&&(repeatstate)){
                                Log.d("aDebugTag", "REPEAT ON");
                                repeatButton.setColorFilter(Color.RED);
                                MusicPlayer.position = MusicPlayer.position1;
                            }
                            else{
                                position1++;
                            }
                            // When the SHUFFLE is ON
                            if((MusicPlayer.shufflestate)&&(MusicPlayer.playstate)){
                                Log.d("aDebugTag", "THe color is change the playState ////"+MusicPlayer.playstate);
                                shuffleButton.setColorFilter(Color.RED);
                            }else {
                                if (MusicPlayer.shufflestate) {
                                    shuffleButton.setColorFilter(Color.RED);
                                    playShuffle();
                                }
                            }
                            Log.d("aDebugTag", "MUSIC is ON ------"+position1);
                            mediaPlayer = MediaPlayer.create(MusicPlayer.this, getSongid(position1, albumNo));
                            setViews();
                            setProgressBar();
                            mediaPlayer.start();
                        }

                        //for repeat mode
                        if(position == position1){
                            MusicPlayer.position1--;
                            MusicPlayer.position--;
                        }
                        else{
                            MusicPlayer.playstate = false;
                            stopMusic();
                            playnext();
                        }

                    }
                }
        );
    }

    // method for repeating the same song
    // we only wanted to add repeat mode on the song currently playing
    // we dont want to play the same song if skipbuttons are clicked(manually)
    // but we want to just keep playing the current song
    // manually the song can change but is on repeat mode automatically
    // due to this method which is only called upon song completion
//    public void playnextrepeat(int position){
//        MusicPlayer.position = position;
//        Log.d("aDebugTag", "The playnextrepeat method has been executed with position "+position);
//        releaseMedia();
//        Intent intent = new Intent(MusicPlayer.this, MusicPlayer.class);
//        MusicPlayer musicPlayer = new MusicPlayer(position, albumNo);
//        startActivity(intent);
//    }

    //method for playing the next song
    public PendingIntent playnext(){
        position1++;
        position++;
        MusicPlayer.playstate = false;

        releaseMedia();

        MusicPlayer musicPlayer = new MusicPlayer(position1, albumNo);
        Intent intent = new Intent(MusicPlayer.this, MusicPlayer.class);
        startActivity(intent);

        Random generator = new Random();

        PendingIntent intent1 = PendingIntent.getActivity(MusicPlayer.this
                , generator.nextInt()
                , intent
                ,PendingIntent.FLAG_UPDATE_CURRENT);

        return intent1;
        //finish();
        //mediaPlayer = MediaPlayer.create(MusicPlayer.this, getSongid(position1, albumNo));
        //mediaPlayer.start();
        //setViews();
        //setImageButton();
        //checkFinished();
    }

    //method for playing previous song if skip previous button pressed
    public PendingIntent playprevious(){
        position1--;
        position--;
        MusicPlayer.playstate = false;

        releaseMedia();

        Intent intent = new Intent(MusicPlayer.this, MusicPlayer.class);
        MusicPlayer musicPlayer = new MusicPlayer(position1, albumNo);
        startActivity(intent);

        Random generator = new Random();

        PendingIntent intent1 = PendingIntent.getActivity(MusicPlayer.this
                , generator.nextInt()
                , intent
                ,PendingIntent.FLAG_UPDATE_CURRENT);

        return intent1;
        //finish();
        //mediaPlayer = MediaPlayer.create(MusicPlayer.this, getSongid(position1, albumNo));
        //mediaPlayer.start();
        //setViews();
        //setImageButton();
        //checkFinished();
    }

    public PendingIntent pause(){
        releaseMedia();

        mediaPlayer.pause();

        Random generator = new Random();

        Intent intent = new Intent(MusicPlayer.this, MainActivity.class);

        PendingIntent intent1 = PendingIntent.getActivity(MusicPlayer.this
                , generator.nextInt()
                , intent
                ,PendingIntent.FLAG_UPDATE_CURRENT);

        return intent1;
    }

    public void setImageButton(){
        //setting onClick listener for the play/pause button
        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if (pausestate) {
                            imageButton.setImageResource(R.drawable.ic_play_circle_filled_24px);
                            mediaPlayer.pause();
                            pausestate = false;
                        } else {
                            imageButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                            mediaPlayer.start();
                            pausestate = true;
                        }
                    }
                }
        );
        //setting onClick for the skipNext button
        skipNextButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        if(mediaPlayer != null){
                            stopMusic();
                        }
                        playnext();
                    }
                }
        );
        //setting onClick for the skipPrevious button
        skipPreviousButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        if(mediaPlayer != null){
                            stopMusic();
                        }
                        playprevious();
                    }
                }
        );
        //setting onClick for the repeat button making the position1 a Constant
        repeatButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
//                        if(MusicPlayer.repeatstate){
//                            repeatButton = (ImageButton)findViewById(R.id.repeatbutton);
//                            MusicPlayer.repeatstate = false;
//                        }
//                        else{
//                            MusicPlayer.repeatstate = true;
//                            repeatButton.setBackgroundColor(Color.GRAY);
//                            playnextrepeat(MusicPlayer.position1);
//                        }
                        if(repeatstate){
                            //Making the REPEAT OFF
                            MusicPlayer.position = 100;
                            repeatButton.setColorFilter(Color.WHITE);
                            MusicPlayer.repeatstate = false;
                            Toast.makeText(MusicPlayer.this, "repeatButton Clicked repeatSTATE TRUE", Toast.LENGTH_LONG).show();
                            Log.d("aDebugTag", "The value of playstate and repeastate when button clicked and repeatstate is true"+playstate+repeatstate);
                        }
                        else{
                            // CODE to make REPEAT ON
                            repeatButton.setColorFilter(Color.RED);
                            MusicPlayer.position = MusicPlayer.position1;
                            MusicPlayer.repeatstate = true;
                            Log.d("aDebugTag", "The value of playstate and repeastate when button clicked and repeatstate is false"+playstate+repeatstate);
                        }
                    }
                }
        );
        //setting the onClick for the shuffle Button
        shuffleButton.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View view){
                    if(MusicPlayer.shufflestate){
                        shuffleButton.setColorFilter(Color.WHITE);
                        MusicPlayer.shufflestate = false;
                    }
                    else{
                        MusicPlayer.shufflestate = true;
                        shuffleButton.setColorFilter(Color.RED);
                        playShuffle();
                    }
                }
            }
        );

    }

    //method for setting the views like songName albumImage in the xml file
    public void setViews(){
        //Setting music Image to be displayed in the music player
        imageView = (ImageView)findViewById(R.id.musicImage);
        imageView.setImageResource(imgResource);

        //setting border to the album 3 and 4
        if((albumNo ==3 )||(albumNo == 4)){
            imageView.setBackgroundResource(R.drawable.image_border);
        }

        //Setting the music name and image button to the musicPlayer class
        TextView textView = (TextView)findViewById(R.id.musicName);
        textView.setText(musicName);
    }

    // method for checking if the mediaPlayer is null or not if not null then make it
    public void releaseMedia(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void stopMusic(){
        //playstate = false;
        audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        finish();
    }

    /*
    //method for relesing the mediaPlayer after song completion
    public void mediaPlayerRelease(MediaPlayer m) {
        if(m != null){
            m.release();
            m = null;
            imageButton.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
        }
    }*/

    //method for resuming the activity where it was stoped
    @Override
    protected void onStop() {
        keeplaying = true;
        super.onStop();
        if(MusicPlayer.mediaPlayer == null){
            Log.d("aDebugTag", "The setMediaPlayer method got called when activity STOPED");
        }
        else
            Log.d("aDebugTag", "The activity is STOPED");
    }

    @Override
    protected void onPause() {
        keeplaying = true;
        super.onPause();
        if(MusicPlayer.mediaPlayer == null){
            Log.d("aDebugTag", "The setMediaPlayer method got called when activity PAUSED");
        }
        else
            Log.d("aDebugTag", "The activity is PAUSED");
    }

    @Override
    protected void onResume() {
        super.onResume();
        keeplaying = false;
        Log.d("aDebugTag", "The activity is RESUMED");
    }

    public int getSongid(int position1, int albumNo){

        // getting the songid (getting raw resource) of Album1 using position
        if(albumNo ==1) {
            imgResource = R.drawable.operanightsquare;
            if (position1 == 0) {
                songid = R.raw.bohemian_rhapsody;
                musicName = "Bohemian Rhapsody";
            }
            if (position1 == 1) {
                songid = R.raw.love_of_my_life;
                musicName = "Love of My Life";
            }
            if (position1 == 2) {
                songid = R.raw.q39;
                musicName = "39";
            }
            if (position1 == 3) {
                songid = R.raw.you_are_my_bestfriend;
                musicName = "You are my bestfriend";
            }
            if (position1 == 4) {
                songid = R.raw.good_company;
                musicName = "Good Company";
            }
            if (position1 == 5) {
                songid = R.raw.i_m_in_love_with_my_car;
                musicName = "Im in love with my car";
            }
            if (position1 == 6) {
                songid = R.raw.lazing_on_a_sunday_afternoon;
                musicName = "Lazing on a sunday afternoon";
            }
            if (position1 == 7) {
                songid = R.raw.seaside_rendezvous;
                musicName = "Seaside Rendezvous";
            }
            if (position1 == 8) {
                songid = R.raw.sweet_lady;
                musicName = "Sweet Lady";
            }
            if (position1 == 9) {
                songid = R.raw.the_prophets_song;
                musicName = "The Prophets song";
            }
            if (position1 == 10) {
                songid = R.raw.death_on_two_legs;
                musicName = "Death on two legs";
            }
            if (position1 > 10){
                songid = R.raw.bohemian_rhapsody;
                musicName = "Bohemian Rhapsody";
                MusicPlayer.position1 = 0;
            }
            if (position1 < 0){
                songid = R.raw.death_on_two_legs;
                musicName = "Death on two legs";
                MusicPlayer.position1 = 10;
            }
        }


        //setting the raw resources for the second album
        if(albumNo == 2) {
            imgResource = R.drawable.innuendosquare;
            if (position1 == 0) {
                songid = R.raw.innuendo;
                musicName = "Innuendo";
            }
            if (position1 == 1) {
                songid = R.raw.the_show_must_go_on;
                musicName = "The Show must go on";
            }
            if (position1 == 2) {
                songid = R.raw.bijou;
                musicName = "Bijou";
            }
            if (position1 == 3) {
                songid = R.raw.i_cant_live_without_you;
                musicName = "I cant live without you";
            }
            if (position1 == 4) {
                songid = R.raw.headlong;
                musicName = "Headlong";
            }
            if (position1 == 5) {
                songid = R.raw.i_m_going_slightly_mad;
                musicName = "I m going slightly mad";
            }
            if (position1 == 6) {
                songid = R.raw.ride_the_wild_wind;
                musicName = "Ride the wild wind";
            }
            if (position1 == 7) {
                songid = R.raw.the_hitman;
                musicName = "The Hitman";
            }
            if (position1 == 8) {
                songid = R.raw.dont_try_so_hard;
                musicName = "Dont try so hard";
            }
            if (position1 == 9) {
                songid = R.raw.all_gods_people;
                musicName = "All gods people";
            }
            if (position1 > 9){
                songid = R.raw.innuendo;
                musicName = "Innuendo";
                MusicPlayer.position1 = 0;
            }
            if (position1 < 0){
                songid = R.raw.all_gods_people;
                musicName = "All gods people";
                MusicPlayer.position1 = 9;
            }
        }

        if(albumNo == 3){
            imgResource = R.drawable.brsquare;
            if (position1 == 0) {
                songid = R.raw.bohemian_rhapsody;
                musicName = "Bohemian Rhapsody";
            }
            if (position1 == 1) {
                songid = R.raw.another_one_bites_the_dust;
                musicName = "Another one bites the dust";
            }
            if (position1 == 2) {
                songid = R.raw.the_show_must_go_on;
                musicName = "The show must go on";
            }
            if (position1 == 3) {
                songid = R.raw.crazy_little_thing_called_love;
                musicName = "Crazy little things called love";
            }
            if (position1 == 4) {
                songid = R.raw.fat_bottomed_girls;
                musicName = "Fat Bottomed girls";
            }
            if (position1 == 5) {
                songid = R.raw.hammer_to_fall;
                musicName = "Hammer to fall";
            }
            if (position1 == 6) {
                songid = R.raw.killer_queen;
                musicName = "Killer Queen";
            }
            if (position1 == 7) {
                songid = R.raw.i_want_to_break_free;
                musicName = "I want to break free";
            }
            if (position1 == 8) {
                songid = R.raw.dont_stop_me_now;
                musicName = "Don't stop me now";
            }
            if (position1 == 9) {
                songid = R.raw.under_pressure;
                musicName = "Under Pressure";
            }
            if (position1 == 10) {
                songid = R.raw.somebody_to_love;
                musicName = "Somebody to love";
            }
            if (position1 == 11) {
                songid = R.raw.we_are_the_champions;
                musicName = "We are the Champions";
            }
            if (position1 == 12) {
                songid = R.raw.we_will_rock_you;
                musicName = "We will rock you";
            }
            if (position1 == 13) {
                songid = R.raw.radio_ga_ga;
                musicName = "Radio Ga Ga";
            }
            if (position1 == 14) {
                songid = R.raw.doing_all_right;
                musicName = "Doing all right";
            }
            if (position1 == 15) {
                songid = R.raw.who_wants_to_live_forever;
                musicName = "Who wants to live forever";
            }
            if (position1 == 16) {
                songid = R.raw.ay_oh;
                musicName = "Ay-Oh";
            }
            if (position1 == 17) {
                songid = R.raw.now_i_m_here;
                musicName = "Now I'm here";
            }
            if (position1 == 18) {
                songid = R.raw.keep_yourself_alive;
                musicName = "Keep yourself alive";
            }
            if (position1 > 18){
                songid = R.raw.bohemian_rhapsody;
                musicName = "Bohemian Rhapsody";
                MusicPlayer.position1 = 0;
            }
            if (position1 < 0){
                songid = R.raw.keep_yourself_alive;
                musicName = "Keep yourself alive";
                MusicPlayer.position1 = 18;
            }
        }


        //setting the Album4
        if(albumNo == 4){
            imgResource = R.drawable.br1square;
            if (position1 == 0) {
                songid = R.raw.bohemian_rhapsody;
                musicName = "Bohemian Rhapsody";
            }
            if (position1 == 1) {
                songid = R.raw.under_pressure;
                musicName = "Under Pressure";
            }
            if (position1 == 2) {
                songid = R.raw.we_will_rock_you;
                musicName = "We will Rock you";
            }
            if (position1 == 3) {
                songid = R.raw.fat_bottomed_girls;
                musicName = "Fat Bottomed girls";
            }
            if (position1 == 4) {
                songid = R.raw.killer_queen;
                musicName = "Killer Queen";
            }
            if (position1 == 5) {
                songid = R.raw.tie_your_mother_down;
                musicName = "Tie Your mother down";
            }
            if (position1 == 6) {
                songid = R.raw.we_are_the_champions;
                musicName = "We are the champions";
            }
            if (position1 == 7) {
                songid = R.raw.crazy_little_thing_called_love;
                musicName = "Crazy little things called love";
            }
            if (position1 == 8) {
                songid = R.raw.you_are_my_bestfriend;
                musicName = "You are my bestfriend";
            }
            if (position1 == 9) {
                songid = R.raw.stone_cold_crazy;
                musicName = "Stone cold crazy";
            }
            if (position1 == 10) {
                songid = R.raw.another_one_bites_the_dust;
                musicName = "Another one bites the dust";
            }
            if (position1 == 11) {
                songid = R.raw.radio_ga_ga;
                musicName = "Radio ga ga";
            }
            if (position1 == 12) {
                songid = R.raw.now_i_m_here;
                musicName = "Now Im here";
            }
            if (position1 == 13) {
                songid = R.raw.i_want_it_all;
                musicName = "I want it all";
            }
            if (position1 == 14) {
                songid = R.raw.who_wants_to_live_forever;
                musicName = "Who wants to live forever";
            }
            if (position1 == 15) {
                songid = R.raw.brighton_rock;
                musicName = "Brington rock";
            }
            if (position1 == 16) {
                songid = R.raw.keep_yourself_alive;
                musicName = "Keep yourself alive";
            }
            if (position1 == 17) {
                songid = R.raw.somebody_to_love;
                musicName = "Somebody to love";
            }
            if (position1 == 18) {
                songid = R.raw.seven_seas_of_rhye;
                musicName = "Seven seas of Rhye";
            }
            if (position1 == 19) {
                songid = R.raw.need_your_loving_tonight;
                musicName = "Need your loving tonight";
            }
            if (position1 == 20) {
                songid = R.raw.q39;
                musicName = "39";
            }
            if (position1 == 21) {
                songid = R.raw.dont_stop_me_now;
                musicName = "Dont stop me now";
            }
            if (position1 == 22) {
                songid = R.raw.hammer_to_fall;
                musicName = "Hammer to fall";
            }
            if (position1 == 23) {
                songid = R.raw.in_the_lap_of_the_gods;
                musicName = "In the lap of the gods";
            }
            if (position1 == 24) {
                songid = R.raw.tenement_funster;
                musicName = "Tenement Funster";
            }
            if (position1 == 25) {
                songid = R.raw.i_want_to_break_free;
                musicName = "I want to break free";
            }
            if (position1 == 26) {
                songid = R.raw.love_of_my_life;
                musicName = "Love of my life";
            }
            if (position1 > 26){
                songid = R.raw.bohemian_rhapsody;
                musicName = "Bohemian Rhapsody";
                MusicPlayer.position1 = 0;
            }
            if (position1 < 0){
                songid = R.raw.love_of_my_life;
                musicName = "Love of my life";
                MusicPlayer.position1 = 26;
            }
        }
        return songid;
    }

}
