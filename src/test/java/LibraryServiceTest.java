
import com.gevernova.LibraryBookLendingSystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {

    private LibraryService service;
    private User user;
    private Book book1;
    private Book book2;
    private Book book3;
    private Book book4;

    @BeforeEach
    void setUp() {
        book1 = new Book("Java Basics", "Alice", "Programming");
        book2 = new Book("Spring Boot", "Bob", "Programming");
        book3 = new Book("Harry Potter", "J.K. Rowling", "Fantasy");
        book4 = new Book("Lord of the Rings", "Tolkien", "Fantasy");

        service = new LibraryService(List.of(book1, book2, book3, book4));
        user = new User("Likhitha");
    }

    @Test
    void testBorrowBookSuccess() throws Exception {
        service.borrowBook(user, "Java Basics");

        assertTrue(book1.isBorrowed());
        assertEquals(1, user.getBorrowedBooks().size());
        assertEquals(book1, user.getBorrowedBooks().get(0));
    }

    @Test
    void testBorrowBookUnavailable() throws Exception {
        service.borrowBook(user, "Java Basics");

        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            service.borrowBook(new User("AnotherUser"), "Java Basics");
        });
        assertEquals("Book already borrowed", ex.getMessage());
    }

    @Test
    void testBorrowBookLimitExceeded() throws Exception {
        service.borrowBook(user, "Java Basics");
        service.borrowBook(user, "Spring Boot");
        service.borrowBook(user, "Harry Potter");

        BookLimitExceededException ex = assertThrows(BookLimitExceededException.class, () -> {
            service.borrowBook(user, "Lord of the Rings");
        });
        assertEquals("User cannot borrow more than 3 books", ex.getMessage());
    }

    @Test
    void testReturnBookSuccess() throws Exception {
        service.borrowBook(user, "Harry Potter");
        service.returnBook(user, "Harry Potter");

        assertFalse(book3.isBorrowed());
        assertTrue(user.getBorrowedBooks().isEmpty());
    }

    @Test
    void testReturnBookNotBorrowedByUser() throws Exception {
        service.borrowBook(new User("AnotherUser"), "Spring Boot");

        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            service.returnBook(user, "Spring Boot");
        });
        assertEquals("User has not borrowed this book", ex.getMessage());
    }

    @Test
    void testFindBookByTitleOptional() {
        assertTrue(service.findBookByTitle("Harry Potter").isPresent());
        assertFalse(service.findBookByTitle("Nonexistent Book").isPresent());
    }

    @Test
    void testFilterByAuthor() {
        List<Book> result = service.filterBooksByAuthor("Alice");
        assertEquals(1, result.size());
        assertEquals(book1, result.get(0));
    }

    @Test
    void testFilterByGenre() {
        List<Book> fantasyBooks = service.filterBooksByGenre("Fantasy");
        assertEquals(2, fantasyBooks.size());
    }
}
