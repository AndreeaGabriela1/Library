package model;

import java.time.LocalDate;

public class EBook extends Book {
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
}

