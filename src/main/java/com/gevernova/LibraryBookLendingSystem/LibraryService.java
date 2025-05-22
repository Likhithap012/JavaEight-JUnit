package com.gevernova.LibraryBookLendingSystem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryService {

    private List<Book> books;

    public LibraryService(List<Book> books) {
        this.books = books;
    }

    public List<Book> listAvailableBooks() {
        return books.stream()
                .filter(book -> !book.isBorrowed())
                .collect(Collectors.toList());
    }

    public List<Book> listBorrowedBooks() {
        return books.stream()
                .filter(Book::isBorrowed)
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public Optional<Book> findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    public void borrowBook(User user, String title) throws BookUnavailableException, BookLimitExceededException {
        Optional<Book> bookOpt = findBookByTitle(title);

        if (bookOpt.isEmpty()) {
            throw new BookUnavailableException("Book not found");
        }

        Book book = bookOpt.get();

        if (book.isBorrowed()) {
            throw new BookUnavailableException("Book already borrowed");
        }

        if (!user.canBorrowMore()) {
            throw new BookLimitExceededException("User cannot borrow more than 3 books");
        }

        book.borrow();
        user.borrowBook(book);
    }

    public void returnBook(User user, String title) throws BookUnavailableException {
        Optional<Book> bookOpt = findBookByTitle(title);

        if (bookOpt.isEmpty()) {
            throw new BookUnavailableException("Book not found");
        }

        Book book = bookOpt.get();

        if (!user.getBorrowedBooks().contains(book)) {
            throw new BookUnavailableException("User has not borrowed this book");
        }

        book.returned();
        user.returnBook(book);
    }
}
