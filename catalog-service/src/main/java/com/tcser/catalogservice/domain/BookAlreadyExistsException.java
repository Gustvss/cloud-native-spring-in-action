package com.tcser.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException{
    public BookAlreadyExistsException(String isbn) {
        super("Um livro com ISBN " + isbn + " já existe.");
    }
}
