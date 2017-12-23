package hidee.popularmovie.Deskripsi_Film;

import java.util.Comparator;

import hidee.popularmovie.CardView.Album;

/**
 * Created by Iman Satyarno on 18/11/2017.
 */

public class User {
    private String Nama;
    private String Hobi;
    private String URL;

    public static Comparator<User> COMPARE_BY_TITLE = new Comparator<User>() {
        public int compare(User one,User other) {
            return one.Nama.compareToIgnoreCase(other.Nama);
        }
    };

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getHobi() {
        return Hobi;
    }

    public void setHobi(String hobi) {
        Hobi = hobi;
    }
}
