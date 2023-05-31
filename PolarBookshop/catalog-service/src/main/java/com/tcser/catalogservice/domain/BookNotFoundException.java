package com.tcser.catalogservice.domain;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(String isbn) {
        super("O livro com ISBN " + isbn + " não foi encontrado.");
    }
}
