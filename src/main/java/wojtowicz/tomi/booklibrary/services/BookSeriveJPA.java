package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookSeriveJPA implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<?> listAll() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book getById(Integer id) {
        return bookRepository.findOne(id);
    }

    @Override
    public Book saveOrUpdate(Book domainObject) {
        return bookRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        bookRepository.delete(id);
    }

    @Override
    public List<Book> getByAuthorFirstNameAndAuthorLastNameAllIgnoreCase(String firstName, String lastName) {
        return bookRepository.findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase(firstName, lastName);
    }

    @Override
    public List<Book> getByTitleIgnoreCase(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }

    @Override
    public List<Book> getByTitleContainingIgnoreCase(String word) {
        return bookRepository.findByTitleContainingIgnoreCase(word);
    }

    @Override
    public List<Book> searchBooks(String searchPhrase) {
        return bookRepository.findByTitleContainingOrAuthorFirstNameContainingOrAuthorLastNameContainingAllIgnoreCase(
                searchPhrase, searchPhrase, searchPhrase);
    }

    @Override
    public List<Book> searchBooks(String title, String authorFirstName, String authorLastName) {
        return bookRepository.findByTitleContainingAndAuthorFirstNameContainingAndAuthorLastNameContainingAllIgnoreCase(
                title, authorFirstName, authorLastName);
    }
}
