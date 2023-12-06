package service;

import model.Book;
import model.Sale;

import java.util.List;

public interface SaleService {
    List<Sale> getAllSales();
    boolean recordSale(Long bookId, int soldQuantity);
    List<Book> getSoldBooks();
    // alte metode necesare pentru gestionarea vânzărilor
}
