package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Ownership;

public interface OwnershipRepository extends CrudRepository<Ownership, Integer>{

}

