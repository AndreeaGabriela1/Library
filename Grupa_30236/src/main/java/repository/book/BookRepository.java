package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> findBooksByTitle(String title);

    boolean save(Book book);

    void removeAll();

    Optional<Book> findBooksByAuthor(String author);
    boolean updateBook(Long id, String newTitle, String newAuthor, Double newPrice, int newQuantity);
    Optional<Book> findBooksByPublishedDate(LocalDate publishedDate);
    boolean deleteBookById(Long id);
    boolean sellBook(Long bookId, int soldQuantity);
}
