package com.github.gr33nowl.springproject2.services;

import com.github.gr33nowl.springproject2.models.Book;
import com.github.gr33nowl.springproject2.models.Person;
import com.github.gr33nowl.springproject2.repositories.BooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;


    public BooksService(BooksRepository bookRepository) {
        this.booksRepository = bookRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).orElse(null);

        updatedBook.setId(id);
        if (bookToBeUpdated != null) {
            updatedBook.setOwner(bookToBeUpdated.getOwner());
        }

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void releaseBook(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
        });
    }

    @Transactional
    public void assignBook(int id, Person person) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(person);
        });
    }
}
