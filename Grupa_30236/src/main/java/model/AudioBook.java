package model;

import java.time.LocalDate;

public class AudioBook implements BookInterface {
    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;
    private int runTime; // Durata în minute a cărții audio
    /*
    public AudioBook(Long id, String author, String title, LocalDate publishedDate, int runTime) {
        super(id, author, title, publishedDate);
        this.runTime = runTime;
    }
    */
    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String getAuthor() {
        return author;
    }
    @Override
    public void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    @Override
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public String toString() {
        return super.toString() + " | Runtime: " + runTime + " minutes";
    }
}
