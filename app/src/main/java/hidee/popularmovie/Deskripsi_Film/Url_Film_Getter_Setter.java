package hidee.popularmovie.Deskripsi_Film;

/**
 * Created by Iman Satyarno on 28/11/2017.
 */

public class Url_Film_Getter_Setter {
    private String url_trailer;
    private String judul_trailer;

    public Url_Film_Getter_Setter(String url_trailer,String judul_trailer)
    {
        this.url_trailer = url_trailer;
        this.judul_trailer = judul_trailer;
    }


    public String getJudul_trailer() {
        return judul_trailer;
    }

    public void setJudul_trailer(String judul_trailer) {
        this.judul_trailer = judul_trailer;
    }



    public String getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }
}
