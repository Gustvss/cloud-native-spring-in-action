package com.tcser.catalogservice.service;

import com.tcser.catalogservice.domain.Book;
import com.tcser.catalogservice.domain.BookAlreadyExistsException;
import com.tcser.catalogservice.domain.BookNotFoundException;
import com.tcser.catalogservice.domain.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> viewBookList(){
        return bookRepository.findAll();
    }

    public Book viewBookDetails(String isbn){
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public Book addBookToCatalog(Book book){
        if (bookRepository.existsByIsbn(book.isbn())){
            throw new BookAlreadyExistsException(book.isbn());
        }
        return bookRepository.save(book);
    }

    public void removeBookFromCatalog(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

    public Book editBookDetails(String isbn, Book book) {
        return bookRepository.findByIsbn(isbn).map(existingBook -> {
            var bookToUpdate = new Book(existingBook.isbn(),
                    book.title(),
                    book.author(),
                    book.price());
            return bookRepository.save(bookToUpdate);
        })
        .orElseGet(() -> addBookToCatalog(book));
    }
}
