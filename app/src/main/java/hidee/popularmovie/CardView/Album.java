package hidee.popularmovie.CardView;

import java.util.Comparator;

/**
 * Created by Iman Satyarno on 19/10/2017.
 */

public class Album {
    private String name;
    private String numOfSongs;
    private String thumbnail;
    private String id;
    private String gambar_poster_lengkap;



    public static Comparator<Album> COMPARE_BY_TITLE = new Comparator<Album>() {
        public int compare(Album one,Album other) {
            return one.name.compareToIgnoreCase(other.name);
        }
    };

    public Album() {
    }

    public Album(String name, String numOfSongs, String thumbnail, String id, String gambar_poster_lengkap) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.id = id;
        this.gambar_poster_lengkap = gambar_poster_lengkap;
    }
    public Album(String name,String numOfSongs,String thumbnail)
    {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }
    public Album(String id)
    {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGambar_poster_lengkap() {
        return gambar_poster_lengkap;
    }

    public void setGambar_poster_lengkap(String gambar_poster_lengkap) {
        this.gambar_poster_lengkap = gambar_poster_lengkap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(String numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
