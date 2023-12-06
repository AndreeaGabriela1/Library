package service.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findById(Long id);

    Book findBooksByTitle(String Title);

    boolean save(Book book);
    boolean updateBook(Long id, String newTitle, String newAuthor);

    int getAgeOfBook(Long id);

    Book findBooksByAuthor(String Author);
    Book findBooksByPublishedDate(LocalDate Date);
    boolean deleteBookById(Long id);
}
