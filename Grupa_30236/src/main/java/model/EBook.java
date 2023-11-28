package model;

import java.time.LocalDate;

public class EBook implements BookInterface {
    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;
    private String format;
    /*
    public EBook(Long id, String author, String title, LocalDate publishedDate, String format) {
        super(id, author, title, publishedDate);
        this.format = format;
    }
    */
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return super.toString() + " | Format: " + format;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}

