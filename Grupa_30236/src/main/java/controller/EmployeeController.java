package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import service.book.BookService;
import view.EmployeeView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    private final EmployeeView employeeView;
    private final BookService bookService;

    public EmployeeController(EmployeeView employeeView, BookService bookService) {
        this.employeeView = employeeView;
        this.bookService = bookService;

        attachEventHandlers();
        displayBooksInTable();
    }
    private void displayBooksInTable() {
        List<Book> books = bookService.findAll(); // obține lista de cărți din serviciul BookService
        employeeView.displayBooks(books);
    }

    private void attachEventHandlers() {
        employeeView.addAddButtonListener(new AddButtonListener());
        employeeView.addSearchButtonListener(new SearchButtonListener());
        employeeView.addUpdateButtonListener(new UpdateButtonListener());
        employeeView.addDeleteButtonListener(new DeleteButtonListener());
    }

    private class AddButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // Implementează logica pentru adăugarea unei cărți
            Book newBook = employeeView.getBookDataFromFields();

            // Aici trebuie să implementezi logica pentru a adăuga cartea în baza de date
            boolean added = bookService.save(newBook);

            if (added) {
                // Actualizează tabela cu toate cărțile
                displayBooksInTable();
            }
        }
    }
    private class SearchButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String searchCriteria = employeeView.getSelectedSearchCriteria();

            switch (searchCriteria) {
                case "Id":
                    Long searchId = employeeView.getSearchId();
                    if (searchId >= 0) {
                        Book foundBook = bookService.findById(searchId);
                        List<Book> book_list = new ArrayList<>();
                        book_list.add(foundBook);
                        displaySearchResults(book_list);
                    }
                    break;
                case "Title":
                    String searchTitle = employeeView.getSearchTitle();
                    if (!searchTitle.isEmpty()) {
                        Book foundBook = bookService.findBooksByTitle(searchTitle);
                        if (foundBook != null) {
                            List<Book> foundBooks = new ArrayList<>();
                            foundBooks.add(foundBook);
                            displaySearchResults(foundBooks);
                        } else {
                            displaySearchResults(new ArrayList<>()); // Afiseaza o lista goala daca nu s-a gasit nicio carte
                        }
                    }
                    break;
                case "Author":
                    String searchAuthor = employeeView.getSearchAuthor();
                    if (!searchAuthor.isEmpty()) {
                        Book foundBook = bookService.findBooksByAuthor(searchAuthor);
                        if (foundBook != null) {
                            List<Book> foundBooks = new ArrayList<>();
                            foundBooks.add(foundBook);
                            displaySearchResults(foundBooks);
                        } else {
                            displaySearchResults(new ArrayList<>()); // Afiseaza o lista goala daca nu s-a gasit nicio carte
                        }
                    }
                    break;
                case "Published Date":
                    LocalDate searchDate = employeeView.getSearchDate();
                    Book foundBook = bookService.findBooksByPublishedDate(searchDate);
                    if (foundBook != null) {
                        List<Book> foundBooks = new ArrayList<>();
                        foundBooks.add(foundBook);
                        displaySearchResults(foundBooks);
                    } else {
                        displaySearchResults(new ArrayList<>()); // Afiseaza o lista goala daca nu s-a gasit nicio carte
                    }
                    break;
                default:
                    // Logica pentru situația în care nu s-a selectat niciun criteriu de căutare
                    break;
            }
        }
        private void displaySearchResults(List<Book> foundBooks) {
            employeeView.displayBooks(foundBooks);
        }

//        private void displaySearchResults(Book foundBook) {
//            List<Book> foundBooks = new ArrayList<>();
//            if (foundBook != null) {
//                foundBooks.add(foundBook);
//            }
//            employeeView.displayBooks(foundBooks);
//        }
    }
    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Long bookId = employeeView.getSearchId(); // Ia ID-ul cărții pentru actualizare
            String newTitle = employeeView.getSearchTitle(); // Ia noul titlu din TextField
            String newAuthor = employeeView.getSearchAuthor(); // Ia noul autor din TextField

            boolean updated = bookService.updateBook(bookId, newTitle, newAuthor);

            if (updated) {
                displayBooksInTable(); // Actualizează tabela cu cărți
            } else {
                // Tratează situația în care nu s-a putut face actualizarea
            }
        }
    }
    private class DeleteButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Long bookIdToDelete = employeeView.getSearchId(); // Obține ID-ul cărții pentru ștergere
            boolean deleted = bookService.deleteBookById(bookIdToDelete);
            if (deleted) {
                displayBooksInTable(); // Actualizează tabela cu cărți
            } else {
                // Tratează situația în care ștergerea nu a reușit
            }
        }
    }
}
