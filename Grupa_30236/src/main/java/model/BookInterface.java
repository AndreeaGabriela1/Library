package model;
import java.time.LocalDate;
public interface BookInterface {
    Long getId();
    String getAuthor();
    String getTitle();
    LocalDate getPublishedDate();
    void setAuthor(String author);
    void setTitle(String title);
    void setId(Long id);
    void setPublishedDate(LocalDate publishedDate);
}
