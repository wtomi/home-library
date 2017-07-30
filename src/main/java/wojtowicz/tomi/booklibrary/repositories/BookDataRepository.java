package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.BookData;

public interface BookDataRepository extends CrudRepository<BookData, Integer> {
}
