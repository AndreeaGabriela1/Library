package service;

import model.Book;
import model.Sale;
import service.book.BookService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleServiceImpl implements SaleService {
    private List<Sale> sales;
    private BookService bookService; // Trebuie să folosim și serviciul pentru cărți

    public SaleServiceImpl(BookService bookService) {
        this.sales = new ArrayList<>();
        this.bookService = bookService;
    }

    @Override
    public List<Sale> getAllSales() {
        return sales;
    }

    @Override
    public boolean recordSale(Long bookId, int soldQuantity) {
        Sale sale = new Sale();
        sale.setBookId(bookId);
        sale.setSoldQuantity(soldQuantity);
        sale.setDateSold(LocalDate.now());
        return this.sales.add(sale);
    }
    @Override
    public List<Book> getSoldBooks() {
        List<Sale> sales = getAllSales(); // Corectare pentru a obține lista de vânzări
        List<Book> soldBooks = new ArrayList<>();
        for (Sale sale : sales) {
            Long bookId = sale.getBookId();
            Book soldBook = bookService.findById(bookId); // Folosim serviciul pentru cărți pentru a găsi cartea
            if (soldBook != null) {
                soldBooks.add(soldBook);
            }
        }

        return soldBooks;
    }
}
