package repository.book;

import model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{
    private Cache<Book> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<Book> cache){
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<Book> findAll() {
        if (cache.hasResult()){
           return cache.load();
        }

        List<Book> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {

        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id);
    }

    @Override
    public Optional<Book> findBooksByTitle(String title) {
        if (cache.hasResult()) {
            return cache.load()
                    .stream()
                    .filter(book -> book.getTitle().equals(title))
                    .findFirst();
        }
        return decoratedRepository.findBooksByTitle(title); // În cazul în care nu aveți date în cache
    }


    @Override
    public boolean save(Book book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }

    @Override
    public Optional<Book> findBooksByAuthor(String author) {
        if (cache.hasResult()) {
            return cache.load()
                    .stream()
                    .filter(book -> book.getAuthor().equals(author))
                    .findFirst();
        }
        return decoratedRepository.findBooksByAuthor(author);
    }
    @Override
    public boolean updateBook(Long id, String newTitle, String newAuthor, Double newPrice, int newQuantity)
    {
        cache.invalidateCache();
        return decoratedRepository.updateBook(id, newTitle, newAuthor, newPrice, newQuantity);
    }

    @Override
    public Optional<Book> findBooksByPublishedDate(LocalDate publishedDate) {
        if (cache.hasResult()) {
            return cache.load()
                    .stream()
                    .filter(book -> book.getPublishedDate().equals(publishedDate))
                    .findFirst();
        }
        return decoratedRepository.findBooksByPublishedDate(publishedDate);
    }

    @Override
    public boolean deleteBookById(Long id) {
        cache.invalidateCache();
        return decoratedRepository.deleteBookById(id);
    }
    @Override
    public boolean sellBook(Long bookId, int soldQuantity) {
        boolean bookSold = decoratedRepository.sellBook(bookId, soldQuantity);

        if (bookSold) {
            cache.invalidateCache();
        }

        return bookSold;
    }
}
