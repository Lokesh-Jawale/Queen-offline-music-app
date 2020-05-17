package com.lokilabs.queen;

class ListContentProvider {

    private String songName;
    private String albumName;
    private int imageResource;
    private int songNo = -1;

    public ListContentProvider(String albumName, int imageResource){
        this.albumName = albumName;
        this.imageResource = imageResource;
    }

    public ListContentProvider(String songName, String albumName, int imageResource){
        this.albumName = albumName;
        this.songName = songName;
        this.imageResource = imageResource;
    }

    public ListContentProvider(String songName, String albumName, int imageResource, int songNo){
        this.albumName = albumName;
        this.songName = songName;
        this.imageResource = imageResource;
        this.songNo = songNo;
    }

    //methods for returning the string
    public String getSongName(){
        return songName;
    }
    public String getAlbumName(){
        return albumName;
    }
    //returns image resource for setting the particular image in listview
    public int getImageResource(){
        return imageResource;
    }
    public int getSongNo(){
        return songNo;
    }

}
