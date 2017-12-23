package hidee.popularmovie.Deskripsi_Film;

/**
 * Created by Iman Satyarno on 02/12/2017.
 */

public class Review_Getter_Setter {
    private String author;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Review_Getter_Setter(String author,String content)
    {
        this.author=author;
        this.content=content;
    }



}
