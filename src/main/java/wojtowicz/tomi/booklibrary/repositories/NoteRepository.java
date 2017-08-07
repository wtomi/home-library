package wojtowicz.tomi.booklibrary.repositories;

import org.springframework.data.repository.CrudRepository;
import wojtowicz.tomi.booklibrary.domain.Note;

public interface NoteRepository extends CrudRepository<Note, Integer>{
}
