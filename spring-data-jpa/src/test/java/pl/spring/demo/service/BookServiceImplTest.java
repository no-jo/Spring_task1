package pl.spring.demo.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() {
        cacheManager.getCache("booksCache").clear();
    }

    @Test
    public void testShouldFindAllBooks() {
        // when
        List<BookTo> allBooks = bookService.findAllBooks();
        // then
        assertNotNull(allBooks);
        assertFalse(allBooks.isEmpty());
        assertEquals(6, allBooks.size());
    }

    @Test
    @Ignore
    public void testShouldFindAllBooksByTitle() {
        // given
        final String title = "Opium w rosole";
        // when
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
    }

    @Test(expected = BookNotNullIdException.class)
    public void testShouldThrowBookNotNullIdException() {
        // given
        final BookTo bookToSave = new BookTo();
        bookToSave.setId(22L);
        // when
        bookService.saveBook(bookToSave);
        // then
        fail("test should throw BookNotNullIdException");
    }
    
    @Test
    @Ignore // TODO jak tworzyc kilka instancji obiektow, tak aby testy byly od siebie niezalezne
    public void testShouldGenerateID() {
        // given
        final BookTo bookToSave = new BookTo(null, "login", "pass");
        Long l = 7L;
        // when
        BookTo saved = bookService.saveBook(bookToSave);
        // then
        assertEquals(l, saved.getId());
    }
}
