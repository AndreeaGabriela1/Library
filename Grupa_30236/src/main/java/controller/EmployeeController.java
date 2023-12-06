package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.Book;
import model.PdfReportGenerator;
import service.SaleService;
import service.SaleServiceImpl;
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
        employeeView.addSellButtonListener(new SellButtonListener());
    }
    private void generatePdfReport(List<Book> soldBooks) {
        String filePath = "C:\\Users\\LENOVO\\IdeaProjects\\lab5_is\\Grupa_30236\\report.pdf"; // Specifică calea și numele fișierului PDF generat
        PdfReportGenerator.generateReport(soldBooks, filePath);
    }

    // Metodă în care obții lista de cărți vândute și apelezi funcția de generare a raportului
    public void generateSoldBooksReport() {
        SaleService saleService = new SaleServiceImpl(bookService);
        List<Book> soldBooks = saleService.getSoldBooks(); // Presupunând că există o metodă pentru a obține cărțile vândute
        generatePdfReport(soldBooks);
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
    }
    private class UpdateButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Long bookId = employeeView.getSearchId();
            String newTitle = employeeView.getSearchTitle();
            String newAuthor = employeeView.getSearchAuthor();
            String priceString = employeeView.getPrice();
            String quantityString = employeeView.getQuantity();

            if (!priceString.isEmpty() && !quantityString.isEmpty()) {
                try {
                    double newPrice = Double.parseDouble(priceString);
                    int newQuantity = Integer.parseInt(quantityString);

                    boolean updated = bookService.updateBook(bookId, newTitle, newAuthor, newPrice, newQuantity);

                    if (updated) {
                        displayBooksInTable(); // Actualizează tabela cu cărți
                    } else {
                        // Tratează situația în care nu s-a putut face actualizarea
                    }
                } catch (NumberFormatException e) {
                    // Tratează cazul în care nu s-a putut converti prețul sau cantitatea în numere valide
                    e.printStackTrace();
                    // Poți afișa un mesaj utilizatorului sau trata altfel această situație
                }
            } else {
                // Poți afișa un mesaj utilizatorului că trebuie să completeze toate câmpurile
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
    private class SellButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Long bookId = employeeView.getSearchId();
            int sellQuantity = Integer.parseInt(employeeView.getQuantity());
            if (sellQuantity > 0) {
                // Obține cărțile corespunzătoare bookId din baza de date
                Book book = bookService.findById(bookId);

                if (book != null) {
                    int availableQuantity = book.getQuantity();

                    if (sellQuantity <= availableQuantity) {
                        // Actualizează cantitatea disponibilă în baza de date
                        int newQuantity = availableQuantity - sellQuantity;
                        boolean sold = bookService.sellBook(bookId, newQuantity);
                        if (sold) {
                            // Actualizează tabela cu cărți pentru a reflecta schimbările
                            SaleService saleService = new SaleServiceImpl(bookService);
                            saleService.recordSale(bookId,sellQuantity);
                            displayBooksInTable();

                        } else {
                            // Tratează cazul în care nu s-a putut realiza vânzarea
                        }
                    } else {
                        // Tratează cazul în care cantitatea de vândut depășește cantitatea disponibilă
                    }
                } else {
                    // Tratează cazul în care cartea nu a fost găsită în baza de date
                }
            } else {
                // Tratează cazul în care cantitatea de vândut nu este validă (<= 0)
            }
            displayBooksInTable();
            generateSoldBooksReport();
        }
    }
}
