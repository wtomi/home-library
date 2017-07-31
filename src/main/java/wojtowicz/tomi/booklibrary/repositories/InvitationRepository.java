package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Invitation;
import wojtowicz.tomi.booklibrary.domain.Library;

public interface InvitationRepository extends CrudRepository<Invitation, Integer> {
    Invitation findByAddedUserEmailAndLibrary(String addedUserEmail, Library library);
}
