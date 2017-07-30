package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Rental;

public interface RentalRepository extends CrudRepository<Rental, Integer> {
}
