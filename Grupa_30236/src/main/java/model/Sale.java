package model;

import java.time.LocalDate;

public class Sale {
    private Long bookId;
    private int soldQuantity;
    private LocalDate dateSold;

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public void setDateSold(LocalDate now) {
        this.dateSold = now;
    }

    public Long getBookId() {
        return this.bookId;
    }

    // Constructor, getteri și setteri
}

