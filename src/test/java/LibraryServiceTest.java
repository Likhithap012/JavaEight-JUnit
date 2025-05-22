import static org.junit.jupiter.api.Assertions.*;

import com.gevernova.LibraryBookLendingSystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class LibraryServiceTest {

    private LibraryService libraryService;
    private User user;
    private Book book1, book2, book3, book4;

    @BeforeEach
    public void setup() {
        book1 = new Book("Java Programming", "Alice", "Education");
        book2 = new Book("Spring Boot Guide", "Bob", "Technology");
        book3 = new Book("Cooking 101", "Carol", "Cooking");
        book4 = new Book("Mystery Novel", "Dan", "Fiction");
        libraryService = new LibraryService(List.of(book1, book2, book3, book4));
        user = new User("John");
    }

    // Positive Test Cases

    @Test
    public void testBorrowBookSuccessfully() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        assertTrue(book1.isBorrowed());
        assertTrue(user.getBorrowedBooks().contains(book1));
    }

    @Test
    public void testReturnBookSuccessfully() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        libraryService.returnBook(user, "Java Programming");
        assertFalse(book1.isBorrowed());
        assertFalse(user.getBorrowedBooks().contains(book1));
    }

    @Test
    public void testListAvailableBooks() {
        book1.borrow(); // mark book1 as borrowed
        List<Book> availableBooks = libraryService.listAvailableBooks();
        assertFalse(availableBooks.contains(book1));
        assertTrue(availableBooks.contains(book2));
        assertTrue(availableBooks.contains(book3));
        assertTrue(availableBooks.contains(book4));
    }

    @Test
    public void testFilterBooksByAuthor() {
        List<Book> aliceBooks = libraryService.filterBooksByAuthor("Alice");
        assertEquals(1, aliceBooks.size());
        assertEquals("Java Programming", aliceBooks.get(0).getTitle());
    }

    @Test
    public void testFilterBooksByGenre() {
        List<Book> techBooks = libraryService.filterBooksByGenre("Technology");
        assertEquals(1, techBooks.size());
        assertEquals("Spring Boot Guide", techBooks.get(0).getTitle());
    }

    // Negative Test Cases

    @Test
    public void testBorrowBookNotFound() {
        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            libraryService.borrowBook(user, "Nonexistent Book");
        });
        assertEquals("Book not found", ex.getMessage());
    }

    @Test
    public void testBorrowBookAlreadyBorrowed() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        User user2 = new User("Jane");
        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            libraryService.borrowBook(user2, "Java Programming");
        });
        assertEquals("Book already borrowed", ex.getMessage());
    }

    @Test
    public void testBorrowBookLimitExceeded() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        libraryService.borrowBook(user, "Spring Boot Guide");
        libraryService.borrowBook(user, "Cooking 101");
        BookLimitExceededException ex = assertThrows(BookLimitExceededException.class, () -> {
            libraryService.borrowBook(user, "Mystery Novel");
        });
        assertEquals("User cannot borrow more than 3 books", ex.getMessage());
    }

    @Test
    public void testReturnBookNotFound() {
        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            libraryService.returnBook(user, "Nonexistent Book");
        });
        assertEquals("Book not found", ex.getMessage());
    }

    @Test
    public void testReturnBookNotBorrowedByUser() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        User user2 = new User("Jane");
        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            libraryService.returnBook(user2, "Java Programming");
        });
        assertEquals("User has not borrowed this book", ex.getMessage());
    }
}
