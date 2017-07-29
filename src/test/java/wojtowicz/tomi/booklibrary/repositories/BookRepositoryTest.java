package wojtowicz.tomi.booklibrary.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import wojtowicz.tomi.booklibrary.domain.Book;
import wojtowicz.tomi.booklibrary.domain.Library;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    private Book book;

    @Before
    public void setUp() {
        Library library = new Library();
        library = entityManager.persist(library);

        book = new Book();
        book.setTitle("Thinking in Java");
        book.setAuthorFirstName("Bruce");
        book.setAuthorLastName("Eckel");
        book.setLibrary(library);
        entityManager.persist(book);
    }

    @Test
    public void test() {
        List<Book> foundBooks = bookRepository.findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase("Bruce", "Eckel");
        assertThat(foundBooks).isNotEmpty();
        assertThat(book).isEqualToComparingFieldByFieldRecursively(foundBooks.get(0));
    }

    @Test
    public void testForNotDifferentLetterCase() {
        List<Book> foundBooks = bookRepository.findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase("BrUce", "EckeL");
        assertThat(foundBooks).isNotEmpty();
        assertThat(book).isEqualToComparingFieldByFieldRecursively(foundBooks.get(0));
    }

    @Test
    public void testForWrongFirstName() {
        List<Book> foundBooks = bookRepository.findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase("Bruca", "Eckel");
        assertThat(foundBooks).isEmpty();
    }

    @Test
    public void testForWrongLastName() {
        List<Book> foundBooks = bookRepository.findByAuthorFirstNameAndAuthorLastNameAllIgnoreCase("Bruca", "Eckell");
        assertThat(foundBooks).isEmpty();
    }

    @Test
    public void testForValidTitle() {
        List<Book> foundBooks = bookRepository.findByTitleIgnoreCase("Thinking in java");
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.get(0)).isEqualToComparingFieldByFieldRecursively(book);
    }

    @Test
    public void testForInvalidTitle() {
        List<Book> foundBooks = bookRepository.findByTitleIgnoreCase("Thinkingin java");
        assertThat(foundBooks).isEmpty();
    }

    @Test
    public void testForTitleContaining() {
        List<Book> foundBooks = bookRepository.findByTitleContainingIgnoreCase("java");
        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.get(0)).isEqualToComparingFieldByFieldRecursively(book);
    }

}
