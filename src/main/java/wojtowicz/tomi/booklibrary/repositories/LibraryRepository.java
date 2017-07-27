package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Library;

public interface LibraryRepository extends CrudRepository<Library, Integer> {
    Library findByOwnerUsername(String username);
}
