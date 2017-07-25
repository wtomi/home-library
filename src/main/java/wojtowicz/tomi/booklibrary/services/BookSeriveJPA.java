package wojtowicz.tomi.booklibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookSeriveJPA implements BookService {

    @Autowired
    private BookRepository bookRepository;

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
}
