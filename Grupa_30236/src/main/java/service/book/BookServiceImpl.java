package service.book;

import model.Book;
import repository.book.BookRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookServiceImpl implements BookService{

    final BookRepository bookRepository;;
    public BookServiceImpl(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }
    @Override
    public Book findBooksByTitle(String Title) {
        return bookRepository.findBooksByTitle(Title).orElseThrow(() -> new IllegalArgumentException("Book with title: %s not found".formatted(Title)));
    }

    @Override
    public Book findBooksByAuthor(String Author) {
        return bookRepository.findBooksByAuthor(Author).orElseThrow(() -> new IllegalArgumentException("Book with title: %s not found".formatted(Author)));
    }
    @Override
    public boolean updateBook(Long id, String newTitle, String newAuthor, Double newPrice, int newQuantity)
    {
        return bookRepository.updateBook(id, newTitle, newAuthor, newPrice, newQuantity);
    }
    @Override
    public boolean sellBook(Long bookId, int newQuantity) {
        return bookRepository.sellBook(bookId, newQuantity);
    }
    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }
    @Override
    public boolean deleteBookById(Long id)
    {
        return bookRepository.deleteBookById(id);
    }
    @Override
    public Book findBooksByPublishedDate(LocalDate Date)
    {
        return bookRepository.findBooksByPublishedDate(Date).orElseThrow(() -> new IllegalArgumentException("Book with published date: %s not found".formatted(Date)));
    }
    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);

        LocalDate now = LocalDate.now();

        return (int)ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }
}
