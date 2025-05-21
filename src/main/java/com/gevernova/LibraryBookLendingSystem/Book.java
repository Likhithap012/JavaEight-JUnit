package com.gevernova.LibraryBookLendingSystem;

public class Book {
    private String title;
    private String author;
    private String genre;
    private boolean isBorrowed;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isBorrowed = false;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public boolean isBorrowed() { return isBorrowed; }

    public void borrow() {
        this.isBorrowed = true;
    }

    public void returned() {
        this.isBorrowed = false;
    }
}
