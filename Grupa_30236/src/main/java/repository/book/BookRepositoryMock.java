package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{

    private List<Book> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Book> findBooksByTitle(String title) {
        return books.parallelStream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public boolean updateBook(Long id, String newTitle, String newAuthor)
    {
        Optional<Book> bookToUpdate = findById(id);
        if (bookToUpdate.isPresent()) {
            Book book = bookToUpdate.get();
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Book> findBooksByPublishedDate(LocalDate publishedDate) {
        return books.parallelStream()
                .filter(book -> book.getPublishedDate().equals(publishedDate))
                .findFirst();
    }

    @Override
    public boolean deleteBookById(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public Optional<Book> findBooksByAuthor(String author) {
        return books.parallelStream()
                .filter(book -> book.getAuthor().equals(author))
                .findFirst();
    }
}
