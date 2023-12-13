package controller;

import javafx.event.ActionEvent;
import model.Book;
import service.book.BookService;
import view.CustomerView;

import java.util.List;

public class CustomerController {

    private final BookService bookService;
    private final CustomerView customerView;

    public CustomerController(BookService bookService, CustomerView customerView) {
        this.bookService = bookService;
        this.customerView = customerView;
        initialize();
    }

    public void show() {
        customerView.show();
    }

    private void initialize() {
        List<Book> books = bookService.findAll();
        customerView.displayBooks(books);
        customerView.addBuyBooksListener(this::handleBuyBooks);
    }

    private void handleBuyBooks(ActionEvent event) {
        List<String> selectedBookTitles = customerView.getSelectedBooks();
        for (String title : selectedBookTitles) {
            Book selectedBook = bookService.findBooksByTitle(title);
            if (selectedBook != null) {
                bookService.sellBook(selectedBook.getId(), selectedBook.getQuantity());
                List<Book> books = bookService.findAll();
                customerView.displayBooks(books);
            } else {
                //
            }
        }
    }
}


