import static org.junit.jupiter.api.Assertions.*;

import com.gevernova.LibraryBookLendingSystem.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class LibraryServiceTest {

    private LibraryService libraryService;
    private User user;
    private Book bookOne, bookTwo, bookThree, bookFour;

    @BeforeEach
    public void setup() {
        bookOne = new Book("Java Programming", "Alice", "Education");
        bookTwo = new Book("Spring Boot Guide", "Bob", "Technology");
        bookThree = new Book("Cooking 101", "Carol", "Cooking");
        bookFour = new Book("Mystery Novel", "Dan", "Fiction");
        libraryService = new LibraryService(List.of(bookOne, bookTwo, bookThree, bookFour));
        user = new User("John");
    }

    // Positive Test Cases

    @Test
    public void testBorrowBookSuccessfully() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        assertTrue(bookOne.isBorrowed());
        assertTrue(user.getBorrowedBooks().contains(bookOne));
    }

    @Test
    public void testReturnBookSuccessfully() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        libraryService.returnBook(user, "Java Programming");
        assertFalse(bookOne.isBorrowed());
        assertFalse(user.getBorrowedBooks().contains(bookOne));
    }

    @Test
    public void testListAvailableBooks() {
        bookOne.borrow(); // mark book1 as borrowed
        List<Book> availableBooks = libraryService.listAvailableBooks();
        assertFalse(availableBooks.contains(bookOne));
        assertTrue(availableBooks.contains(bookTwo));
        assertTrue(availableBooks.contains(bookThree));
        assertTrue(availableBooks.contains(bookFour));
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
        BookUnavailableException exception = assertThrows(BookUnavailableException.class, () -> {
            libraryService.borrowBook(user, "Nonexistent Book");
        });
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    public void testBorrowBookAlreadyBorrowed() throws Exception {
        libraryService.borrowBook(user, "Java Programming");
        User userTwo = new User("Jane");
        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            libraryService.borrowBook(userTwo, "Java Programming");
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
        User userTwo = new User("Jane");
        BookUnavailableException ex = assertThrows(BookUnavailableException.class, () -> {
            libraryService.returnBook(userTwo, "Java Programming");
        });
        assertEquals("User has not borrowed this book", ex.getMessage());
    }
}
