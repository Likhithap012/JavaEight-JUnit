package com.gevernova.LibraryBookLendingSystem;

public class BookLimitExceededException extends Exception {
    public BookLimitExceededException(String msg) { super(msg); }
}
